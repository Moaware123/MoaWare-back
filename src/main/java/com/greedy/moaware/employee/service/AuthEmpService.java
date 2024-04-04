package com.greedy.moaware.employee.service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.moaware.common.configuration.MailSenderConfig;
import com.greedy.moaware.employee.dto.AttachedFileDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.FileCategoryDto;
import com.greedy.moaware.employee.dto.TokenDto;
import com.greedy.moaware.employee.entity.AttachedFile;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.employee.repository.FileRepository;
import com.greedy.moaware.exception.FindMyAccoutException;
import com.greedy.moaware.exception.LoginFailedException;
import com.greedy.moaware.jwt.TokenProvider;
import com.greedy.moaware.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthEmpService {
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	@Value("${image.image-dir}")
	private String IMAGE_DIR;


	private final AuthEmpRepository authEmpRepository;
	private final EmpRepository empRepository;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final MailSenderConfig mailSender;
	private final FileRepository fileRepository;
	
	public AuthEmpService(AuthEmpRepository authEmpRepository
			, ModelMapper modelMapper
			, TokenProvider tokenProvider
			, PasswordEncoder passwordEncoder
			, MailSenderConfig mailSender
			, EmpRepository empRepository
			, FileRepository fileRepository
			) {
		this.authEmpRepository = authEmpRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.mailSender = mailSender;
		this.empRepository = empRepository;
		this.fileRepository = fileRepository;
	}
	
	/* 로그인 */
	public TokenDto login(AuthEmpDto empDto) {
		
		log.info("[AuthService] login start ======================================");
		log.info("[AuthService] empDto : {}", empDto);

		/* 아이디 매칭 조회 */
		AuthEmp emp = authEmpRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailedException("아이디와 비밀번호를 다시 확인해주세요."));

		/* 비밀번호 매칭 확인 */
		if(!passwordEncoder.matches(empDto.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailedException("아이디와 비밀번호를 다시 확인해주세요.");
		}
		/* 토큰 발급 */
		TokenDto tokenDto = tokenProvider.generateTokenDto(modelMapper.map(emp, AuthEmpDto.class));
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}

	/* 아이디 찾기 */
	public String accountIdFind(AuthEmpDto emp) {

		log.info("[AuthService] accountIdFind start ======================================");
		log.info("[AuthService] empDto : {}", emp);
		
		AuthEmp employee = authEmpRepository.findById(emp.getEmpCode())
				.orElseThrow(()-> new FindMyAccoutException("입력하신 정보를 다시 확인해주세요."));
		
		if(!emp.getEmpName().equals(employee.getEmpName()) || !emp.getEmail().equals(employee.getEmail())) {
			throw new FindMyAccoutException("입력하신 정보를 다시 확인해주세요.");
		}
		
		log.info("[AuthService] accountIdFind end ======================================");
		return employee.getEmpId();
	}
	
	
	/* 비밀번호 찾기 - mailSender 통하여 임시 비밀번호 전송 및 비밀번호 설정 */
	@Transactional
	public void accountPwdFind(AuthEmpDto empDto) {
		
		log.info("[AuthService] accountPwdFind start ======================================");
		log.info("[AuthService] emp : {}", empDto);
		
		AuthEmp employee = authEmpRepository.findById(empDto.getEmpCode())
				.orElseThrow(()-> new FindMyAccoutException("입력하신 정보를 다시 확인해주세요."));
		
		if(!empDto.getEmpId().equals(employee.getEmpId()) || !empDto.getEmail().equals(employee.getEmail())) {
			throw new FindMyAccoutException("입력하신 정보를 다시 확인해주세요.");
		}
		

		String newPwd = randomPassword();
		
		employee.setEmpPwd(passwordEncoder.encode(newPwd));

		authEmpRepository.save(employee);
		
		mailSender.sendMail(employee.getEmail(), newPwd);
		
		log.info("[AuthService] sendEmail end ======================================");
	}
	
	
	/* 임시 비밀번호 생성 */
	public String randomPassword() {
		
		char[] charSet = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		};
		
		StringBuilder builder = new StringBuilder();
		
		Random random = new Random(new Date().getTime());
		
		int length = charSet.length;
		
		for (int i = 0; i < 12; i++) {
			builder.append(charSet[random.nextInt(length)]);
		}
		
		log.info("임시 비밀번호 : {}", builder.toString());
		
		return builder.toString();
	}

	
	/* 회원 정보 수정 재로그인 */
	public AuthEmpDto memberCheck(AuthEmpDto empDto, AuthEmpDto empPwd) {
		
		log.info("[AuthService] memberInfo start ======================================");
		log.info("[AuthService] empPwd : {}",empDto);
		log.info("[AuthService] empPwd : {}",empPwd);
		
		AuthEmp emp = authEmpRepository.findByEmpId(empDto.getEmpId())
				.orElseThrow(()-> new LoginFailedException("비밀번호를 다시 확인해주세요."));

		if(!passwordEncoder.matches(empPwd.getEmpPwd(), emp.getEmpPwd())) {
			throw new LoginFailedException("비밀번호를 다시 확인해주세요.");
		}
		
		log.info("[AuthService] memberInfo end ======================================");

		return modelMapper.map(emp, AuthEmpDto.class);
	}


	/* 회원정보 수정 */
	@Transactional
	public void memberModify(AuthEmpDto emp, AttachedFileDto fileDto) {

		log.info("[AuthService] memberModify start ======================================");
		log.info("emp : {}", emp);
		log.info("fileDto : {}", fileDto);
		
		Emp originalEmp = empRepository.findById(emp.getEmpCode())
					.orElseThrow(()-> new IllegalArgumentException("오류가 발생하였습니다. 다시 시도 해주세요."));
		
		EmpDto empDto = modelMapper.map(originalEmp, EmpDto.class);
		
		// 프로필 이미지 업로드 
		try {
			
			if( fileDto.getFileInfo() != null ) {
				
				String imgRandomName = UUID.randomUUID().toString().replace("-","");
				String uploadDir = IMAGE_DIR+"/profile/";
				log.info("try-catch originalEmp : ======================\n{}", originalEmp);

				AttachedFileDto originalFile = null;
				
				for (FileCategoryDto file : empDto.getFileCategory()) {
					
					String type = "emp";
					
				    if (file.getFCategoryType().equals(type)) {
				    	originalFile = file.getFile(); // fCategoryType이 'emp'인 AttachedFileDto를 담는다.
				    }
				} 
				
				String replaceImgName = FileUploadUtils.saveFile( uploadDir, imgRandomName, fileDto.getFileInfo() );
				
				FileUploadUtils.deleteFile(uploadDir, originalFile.getSavedFileName());
				
				originalFile.setFilePath("/profile/"+replaceImgName);
				originalFile.setSavedFileName(replaceImgName);
				originalFile.setOriginalFileName(fileDto.getFileInfo().getOriginalFilename());
				
				fileRepository.save(modelMapper.map(originalFile, AttachedFile.class));
			}
			
			EmpDto fileEmp = fileDto.getFileCategory().getEmp();
			
			originalEmp.update(
					  passwordEncoder.encode(fileEmp.getEmpPwd())
					, fileEmp.getEmail()
					, fileEmp.getPhone()
					, fileEmp.getExtensionNum()
				);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		log.info("[AuthService] memberModify end ======================================");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
