package com.greedy.moaware.payment.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.FileCategoryDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.payment.dto.FormDto;
import com.greedy.moaware.payment.dto.PayAttachedFileDto;
import com.greedy.moaware.payment.dto.PayEmpDto;
import com.greedy.moaware.payment.dto.PayFileCategoryDto;
import com.greedy.moaware.payment.dto.PaymentDto;
import com.greedy.moaware.payment.dto.PaymentMemberDto;
import com.greedy.moaware.payment.entity.Form;
import com.greedy.moaware.payment.entity.PayAttachedFile;
import com.greedy.moaware.payment.entity.PayEmp;
import com.greedy.moaware.payment.entity.Payment;
import com.greedy.moaware.payment.entity.PaymentMember;
import com.greedy.moaware.payment.entity.PaymentMemberPk;
import com.greedy.moaware.payment.repository.FormRepository;
import com.greedy.moaware.payment.repository.PayAttachedFileRepository;
import com.greedy.moaware.payment.repository.PayEmpRepository;
import com.greedy.moaware.payment.repository.PaymentMemberRepository;
import com.greedy.moaware.payment.repository.PaymentRepository;
import com.greedy.moaware.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final FormRepository formRepository;
	private final PayEmpRepository payEmpRepository;
	private final PayAttachedFileRepository payAttachedFileRepository;
	private final PaymentMemberRepository paymentMemberRepository;
	private final EmpRepository empRepository;
	private final ModelMapper modelMapper;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
	public PaymentService(ModelMapper modelMapper, PaymentRepository paymentRepository, FormRepository formRepository
			, PayEmpRepository payEmpRepository, PayAttachedFileRepository payAttachedFileRepository
			, PaymentMemberRepository paymentMemberRepository, EmpRepository empRepository) {
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		this.formRepository = formRepository;
		this.payEmpRepository = payEmpRepository;
		this.payAttachedFileRepository = payAttachedFileRepository;
		this.paymentMemberRepository = paymentMemberRepository;
		this.empRepository = empRepository;
		
	}
	
	/* 기안자 기안문 전체 조회 */
	public List<PaymentDto> paymentAllList(Integer empCode) {
		
		log.info("[PaymentService] payMentList start ============================== ");
		
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		
		List<Payment> payList = paymentRepository.findByEmp(emp);

		
		log.info("[PaymentService] payMentList payList : {}" , payList);
		
		List<PaymentDto> paysDto = payList.stream().map( pay -> modelMapper.map(pay, PaymentDto.class)).collect(Collectors.toList());
		
		
		
		log.info("[PaymentService] payMentList paysDto : {}" , paysDto);
		
		log.info("[PaymentService] payMentList end ============================== ");
		
		return paysDto;
	}
	

	
	/* 기안문 조회 */
	public Map<String, Object> formSelect(Integer empCode) {
		
		log.info("[PaymentService] formSelect start ============================== ");
		log.info("[PaymentService] formSelect EmpCode : {}" , empCode);
		
		List<Form> formList = formRepository.findAll();
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		
		log.info("[PaymentService] formSelect formList : {}" , formList);
		
		
		List<FormDto> formDtoList = formList.stream().map( form-> modelMapper.map(form, FormDto.class)).collect(Collectors.toList());
		
		PayEmpDto empDto = modelMapper.map(emp, PayEmpDto.class);
		
		log.info("[PaymentService] formSelect formDtoList : {}" , formDtoList);
		log.info("[PaymentService] formSelect empDto : {}" , empDto);
		
		if(empDto.getPayFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).count() != 0) {
			List<PayFileCategoryDto> empFileCategoryDto = empDto.getPayFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).collect(Collectors.toList());
			
			empFileCategoryDto.get(0).getFile().setFilePath(IMAGE_URL + empFileCategoryDto.get(0).getFile().getFilePath());
			
			log.info("[PaymentService] empFileCategoryDto : {}" , empFileCategoryDto);
			
			empDto.setPayFileCategory(empFileCategoryDto);
			}
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("payForm", formDtoList);
		result.put("payEmp", empDto);
		
		
		log.info("[PaymentService] formSelect end ============================== ");
		
		return result;
		
	}
	
	/* 기안문 저장*/
	@Transactional
	public void insertPayment(Integer empCode, PayAttachedFileDto payAttachedFile) {
		log.info("[PaymentService] insertPayment start ============================== ");
		log.info("[PaymentService] insertPayment payAttachedFile : {}" , payAttachedFile);
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		if(payAttachedFile.getFileInfo() != null) {
		
		String fileName = UUID.randomUUID().toString().replace("-","");
		
		String savedName = fileName + "." + FilenameUtils.getExtension(payAttachedFile.getOriginalFileName());
		
		payAttachedFile.setSavedFileName(savedName);
		
		try {
			
			String uploadDir = IMAGE_DIR+"/payment";
			
			log.info("업로드 dir  : {}", uploadDir);
			
			String replaceFileName = FileUploadUtils.saveFile(uploadDir, fileName, payAttachedFile.getFileInfo());
			String path = "/payment/" + replaceFileName;
			
			log.info("업로드 path  : {}", path);
			payAttachedFile.setFilePath(path);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
		
		PayAttachedFile file = modelMapper.map(payAttachedFile, PayAttachedFile.class);
		
		file.getPayFileCategory().getPay().setEmp(emp);
		
		
		log.info("[PaymentService] insertPayment file : {} ", file);
		
		payAttachedFileRepository.save(file);
				
		}
		else {
		Payment pay = modelMapper.map(payAttachedFile.getPayFileCategory().getPay(), Payment.class);
		
		pay.setEmp(emp);
		
		log.info("[PaymentService] insertPayment pay : {} ", pay);
		
		paymentRepository.save(pay);
		
		
		}
		
		
		log.info("[PaymentService] insertPayment end ============================== ");
		
	}
	
	
	/* 기안자로 기안문 진행중 조회 */
	public Page<PaymentDto> paymentList(Integer empCode, int page) {
		
		log.info("[PaymentService] payMentList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		String payStatus = "진행중";
		
		Page<Payment> payList = paymentRepository.findByEmpAndPayStatus(pageable, emp, payStatus);
		
		log.info("[PaymentService] payMentList payList : {}" , payList);
		
		Page<PaymentDto> payDtoList = payList.map( pay -> modelMapper.map(pay, PaymentDto.class));
			
		
		log.info("[PaymentService] payMentList payDtoList : {}" , payDtoList);
		
		log.info("[PaymentService] payMentList end ============================== ");
		
		return payDtoList;
	}

	
	/* 결재 대기 조회 */
	public Page<PaymentDto> paymentMemberList(Integer empCode, int page) {
		log.info("[PaymentService] paymentMemberList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		log.info("[PaymentService] paymentMemberList empCode : {} ", empCode);
		PaymentMemberPk paymentMemberPk = new PaymentMemberPk();
		
		paymentMemberPk.setEmpCode(empCode);
		
		String PayType = null;
		
		List<PaymentMember> paymentMemberList = paymentMemberRepository.findByPaymentMemberPkEmpCodeAndPayType(empCode, PayType);
		
		log.info("[PaymentService] paymentMemberList paymentMemberList : {} ", paymentMemberList);
		
		String payStatus = "진행중";
		
		Page<Payment> paymentList = paymentRepository.findByPayStatusAndPaymentMemberInOrderByPayCode(pageable, payStatus, paymentMemberList);
				
		log.info("[PaymentService] paymentMemberList paymentList : {} ", paymentList);
		
		Page<PaymentDto> paymentListDto = paymentList.map( pay -> modelMapper.map(pay, PaymentDto.class));

		List<PaymentDto> filterPayList = paymentListDto.getContent();
		
		List<PaymentDto> filterPay = filterPayList.stream().filter( p -> {

			List<PaymentMemberDto> filterPayMember = p.getPaymentMember().stream().filter( f -> {
				
				if(f.getPaymentMemberPk().getEmpCode() == empCode ) {
					Integer rank = f.getPayRank();
					if( f.getPayRank() == 1) {
						return true;
					} else if ( f.getPayRank() > 1 ) {
						
						return p.getPaymentMember().stream().anyMatch( pay -> pay.getPayRank() == rank-1 && "결재".equals(pay.getPayType()));
					}
				}
				return false;
			}).collect(Collectors.toList());
			
			return filterPayMember.size() > 0;
			
		}).collect(Collectors.toList());
		
		Page<PaymentDto> filteredPage = new PageImpl<>(filterPay, paymentListDto.getPageable(), filterPay.size());
		
		log.info("[PaymentService] paymentMemberList filterPay : {} ", filterPay);
		
		log.info("[PaymentService] paymentMemberList filteredPage : {} ", filteredPage	);
		
		log.info("[PaymentService] paymentMemberList end ============================== ");
		
		return filteredPage;
	}
	
	/* 결재 문서 상세 조회 */
	public PaymentDto paymentDetail ( Integer payCode) {
		
		log.info("[PaymentService] paymentDetail start ============================== ");
		
		Payment payment = paymentRepository.findById(payCode).orElseThrow( () -> new IllegalArgumentException("해당 결재문서가 없습니다. payCode=" + payCode) );
		
		log.info("[PaymentService] paymentDetail payment : {} " , payment);
		
		PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
		
		PayEmpDto payEmpDto = paymentDto.getEmp();
		
		
		if(payEmpDto.getPayFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).count() != 0) {
			List<PayFileCategoryDto> empFileCategoryDto = payEmpDto.getPayFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).collect(Collectors.toList());
			
			empFileCategoryDto.get(0).getFile().setFilePath(IMAGE_URL + empFileCategoryDto.get(0).getFile().getFilePath());
			
			log.info("[PaymentService] paymentDetail empFileCategoryDto : {}" , empFileCategoryDto);
			
			payEmpDto.setPayFileCategory(empFileCategoryDto);
		}
		
		paymentDto.setEmp(payEmpDto);
		
		List<PaymentMemberDto> payMember = paymentDto.getPaymentMember().stream().map(member -> {
			
			List<PayFileCategoryDto> payFileCategory =
			member.getEmp().getPayFileCategory().stream().filter( fileCategory -> {
				if(fileCategory.getFCategoryType().equals("sign"))
				{
					fileCategory.getFile().setFilePath(IMAGE_URL + fileCategory.getFile().getFilePath());
				
					return true;
				}
				
				return false;
			}).collect(Collectors.toList());
			
			log.info("[PaymentService] paymentDetail fileCategory : {} " , payFileCategory);
			member.getEmp().setPayFileCategory(payFileCategory);
			return member;
			}).collect(Collectors.toList());
		paymentDto.setPaymentMember(payMember);
		
		if(paymentDto.getPayFileCategory() != null) {
			paymentDto.getPayFileCategory().getFile().setFilePath(IMAGE_URL + paymentDto.getPayFileCategory().getFile().getFilePath());
		}
		
		log.info("[PaymentService] paymentDetail payMember : {} " , payMember);
		
		log.info("[PaymentService] paymentDetail paymentDto : {} " , paymentDto.getPayFileCategory());
		
		log.info("[PaymentService] paymentDetail end ============================== ");
		
		return paymentDto;
	}
	
	/* 결재 처리 진행 */
	@Transactional
	public void PaymentUpdate (Integer empCode, PaymentDto paymentDto) {
		
		log.info("[PaymentService] PaymentUpdate start ============================== ");
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		Payment payment = paymentRepository.findById(paymentDto.getPayCode()).orElseThrow( () -> new IllegalArgumentException("해당 결재 문서가 없습니다. payCode=" + paymentDto.getPayCode()) );
		
		log.info("[PaymentService] PaymentUpdate paymentMember : {} " , payment);
		
		
		 payment.getPaymentMember().stream().map( member ->{
		
		if(member.getPaymentMemberPk().getEmpCode() == empCode) {
			
			if(paymentDto.getPaymentMember().get(0).getPayType().equals("결재")) {
				
				
				member.setPayType(paymentDto.getPaymentMember().get(0).getPayType());
				member.setPayDate(paymentDto.getPaymentMember().get(0).getPayDate());
				
				if("Y".equals(paymentDto.getPaymentMember().get(0).getPayTotalYn())) {
					member.setPayTotalYn(paymentDto.getPaymentMember().get(0).getPayTotalYn());
					paymentDto.setPayStatus(paymentDto.getPayStatus());
				} else {

					log.info("[PaymentService] PaymentUpdate paymentMember12 : {} " , member.getPayFinalYn());
					log.info("[PaymentService] PaymentUpdate paymentMember12 : {} " , member.getPayFinalYn().equals("Y "));
					log.info("[PaymentService] PaymentUpdate paymentMember12 : {} " , member.getPayFinalYn()=="Y");
					if(member.getPayFinalYn().equals("Y")) {
						paymentDto.setPayStatus("결재완료");
					} else {
						paymentDto.setPayStatus("진행중");
					}
				}
				
				
			} else if(paymentDto.getPaymentMember().get(0).getPayType().equals("반려")) {
				member.setPayType(paymentDto.getPaymentMember().get(0).getPayType());
				member.setPayDate(paymentDto.getPaymentMember().get(0).getPayDate());
				member.setCancleReason(paymentDto.getPaymentMember().get(0).getCancleReason());
				
				paymentDto.setPayStatus("반려");

			}
		} else { member.setPayType("전결");}
		
			
		return member;
		
		}).collect(Collectors.toList());
	
		log.info("[PaymentService] PaymentUpdate paymentMember2 : {} " , paymentDto);
		
		payment.setPayStatus(paymentDto.getPayStatus());

		log.info("[PaymentService] PaymentUpdate paymentMember3 : {} " , payment);
		
		
		log.info("[PaymentService] PaymentUpdate end ============================== ");
		
	}
	
	
	/* 결재 완료 조회 */
	public Page<PaymentDto> PaymentCompleteList(Integer empCode, int page) {
		log.info("[PaymentService] PaymentCompleteList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		String payStatus = "결재완료";
		
		Page<Payment> payList = paymentRepository.findByEmpAndPayStatus(pageable, emp, payStatus);
		
		log.info("[PaymentService] PaymentCompleteList payList : {}" , payList);
		
		Page<PaymentDto> payDtoList = payList.map( pay -> modelMapper.map(pay, PaymentDto.class));
			
		
		log.info("[PaymentService] PaymentCompleteList payDtoList : {}" , payDtoList);
		
		log.info("[PaymentService] PaymentCompleteList end ============================== ");
		
		return payDtoList;
	}
	
	
	/* 결재 반려 조회 */
	public Page<PaymentDto> PaymentRefuseList(Integer empCode, int page) {
		log.info("[PaymentService] PaymentRefuseList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		String payStatus = "반려";
		
		Page<Payment> payList = paymentRepository.findByEmpAndPayStatus(pageable, emp, payStatus);
		
		log.info("[PaymentService] PaymentRefuseList payList : {}" , payList);
		
		Page<PaymentDto> payDtoList = payList.map( pay -> modelMapper.map(pay, PaymentDto.class));
			
		
		log.info("[PaymentService] PaymentRefuseList payDtoList : {}" , payDtoList);
		
		log.info("[PaymentService] PaymentRefuseList end ============================== ");
		
		return payDtoList;
	}

	/* 임시 저장 조회 */
	public Page<PaymentDto> PaymentStorageList(Integer empCode, int page) {
		log.info("[PaymentService] PaymentStorageList start ============================== ");
		
		Pageable pageable = PageRequest.of(page -1, 10, Sort.by("payCode").descending());
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		String payStatus = "임시";
		
		Page<Payment> payList = paymentRepository.findByEmpAndPayStatus(pageable, emp, payStatus);
		
		log.info("[PaymentService] PaymentStorageList payList : {}" , payList);
		
		Page<PaymentDto> payDtoList = payList.map( pay -> modelMapper.map(pay, PaymentDto.class));
			
		
		log.info("[PaymentService] PaymentStorageList payDtoList : {}" , payDtoList);
		
		log.info("[PaymentService] PaymentStorageList end ============================== ");
		
		return payDtoList;
	}

	/* 서명 조회*/
	public EmpDto paymentSign(Integer empCode) {
		
		PayEmp emp = payEmpRepository.findById(empCode).orElseThrow( 
							() -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		Emp empSign = empRepository.findById(empCode).orElseThrow(
							() -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		EmpDto empSignDto = modelMapper.map(empSign, EmpDto.class);
		
		
		log.info("[PaymentService] empFileCategoryDto : {}" , empSignDto.getFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).count() != 0);
		
		
		
		if(empSignDto.getFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).count() != 0) {
			List<FileCategoryDto> empFileCategoryDto = empSignDto.getFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("sign")).collect(Collectors.toList());
			
			empFileCategoryDto.get(0).getFile().setFilePath(IMAGE_URL + empFileCategoryDto.get(0).getFile().getFilePath());
			
			log.info("[PaymentService] empFileCategoryDto : {}" , empFileCategoryDto);
			
			empSignDto.setFileCategory(empFileCategoryDto);
			}
		
		
		log.info("[PaymentService] paymentSign empSignDto : {}" , empSignDto);
		return empSignDto;
	}
	
	
	/* 서명 저장 */
	@Transactional
	public void paymentSignSaved(Integer empCode, PayAttachedFileDto payAttachedFile) {
		
		log.info("[PaymentService] paymentSignSaved start ============================== ");
		log.info("[PaymentService] paymentSignSaved emp : {}" , payAttachedFile);
		
		PayEmp payEmp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		
		String fileName = UUID.randomUUID().toString().replace("-","");
	
		String savedName = fileName + "." + FilenameUtils.getExtension(	payAttachedFile.getOriginalFileName());
		
		
		payAttachedFile.setSavedFileName(savedName);
		payAttachedFile.getPayFileCategory().setFCategoryName( payEmp.getEmpName() + " 싸인");
		
		
		try {
			
			String uploadDir = IMAGE_DIR+"/sign";
			
			log.info("업로드 dir  : {}", uploadDir);
			
			String replaceFileName = FileUploadUtils.saveFile(uploadDir, fileName, payAttachedFile.getFileInfo());
			String path = "/sign/" + replaceFileName;
			
			log.info("업로드 path  : {}", path);
			
			payAttachedFile.setFilePath(path);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
		
		PayAttachedFile file = modelMapper.map(payAttachedFile, PayAttachedFile.class);
		
		file.getPayFileCategory().setPayEmp(payEmp);
		
		
		log.info("[PaymentService] insertPayment file : {} ", file);
		
		payAttachedFileRepository.save(file);
		
		log.info("[PaymentService] paymentSignSaved end ============================== ");
				
		}
	
	/* 서명 업데이트 */
	@Transactional
	public void paymentSignUpdate(Integer empCode, PayAttachedFileDto payAttachedFile) {
		
		log.info("[PaymentService] paymentSignUpdate start ============================== ");
		log.info("[PaymentService] paymentSignUpdate emp : {}" , payAttachedFile);
		
		PayEmp payEmp = payEmpRepository.findById(empCode).orElseThrow( () -> new IllegalArgumentException("해당 사원이 없습니다. empCode=" + empCode));
		
		PayAttachedFile originFile = payAttachedFileRepository.findById(payAttachedFile.getFileCode())
						.orElseThrow( () -> new IllegalArgumentException("해당 서명이 없습니다. FileCode=" + payAttachedFile.getFileCode()));
		
		String fileName = UUID.randomUUID().toString().replace("-","");
		
		String savedName = fileName + "." + FilenameUtils.getExtension(	payAttachedFile.getOriginalFileName());
		
		
		try {
			
			String uploadDir = IMAGE_DIR+"/sign";
			
			log.info("업로드 dir  : {}", uploadDir);
			
			String replaceFileName = FileUploadUtils.saveFile(uploadDir, fileName, payAttachedFile.getFileInfo());
			String path = "/sign/" + replaceFileName;
			
			log.info("업로드 path  : {}", path);
			
			FileUploadUtils.deleteFile(uploadDir, originFile.getSavedFileName());
			
			log.info("[PaymentService] paymentSignUpdate  payAttachedFile.getOriginalFileName() : {}", payAttachedFile.getOriginalFileName());
			
			originFile.update( payAttachedFile.getOriginalFileName(), path, savedName);
			
			log.info("[PaymentService] paymentSignUpdate  savedName : {}", savedName);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
		
		
		
		
		log.info("[PaymentService] paymentSignUpdate end ============================== ");
		
	}
	
	
		
}



