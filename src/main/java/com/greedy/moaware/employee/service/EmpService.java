package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.greedy.moaware.employee.dto.AttachedFileDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.FileCategoryDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.DeptRepository;
import com.greedy.moaware.employee.repository.EmpRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpService {
	
	private final EmpRepository empRepository;
	private final DeptRepository deptRepository;
	private final ModelMapper modelMapper;
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	
	public EmpService( EmpRepository empRepository, ModelMapper modelMapper
			, DeptRepository deptRepository) {
		this.empRepository = empRepository;
		this.deptRepository = deptRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 사원 전체 조회*/
	public List<EmpDto> selectEmpList() {
		
		log.info("[EmpService] selectEmpList start ============================== ");
		
		List<Emp> empList = empRepository.findAll();
		
		log.info("{}", empList.get(0).getDept());
		
		List<EmpDto>empDtoList = empList.stream().map( emp -> modelMapper.map(emp, EmpDto.class)).collect(Collectors.toList());
		
		
		log.info("[EmpService] selectEmpList end ================================ ");
		
		return empDtoList;
		
	}

//	/*사원 상세조회*/
//	public EmpDto selectEmpDetail(Integer empCode) {
//		log.info("[EmpService] selectOrgDetail start ============================== ");
//		log.info("[EmpService] empCode : {}" , empCode);
//		
//		Emp empList = empRepository.findById(empCode)
//				.orElseThrow( ()-> new IllegalArgumentException("해당 사번을 가진 사원이 없습니다. 사번 = " + empCode));
//		log.info("[EmpService] empList : {}" , empList);
//		
//		EmpDto empDto = modelMapper.map(empList, EmpDto.class);
//		
//		empDto.getFileCategory().getFile().setFilePath(IMAGE_URL+empDto.getFileCategory().getFile().getFilePath());
//		
//		log.info("[EmpService] empDto : {}" , empDto);
//		
//		log.info("[EmpService] selectOrgDetail end ================================ ");
//		return empDto;
//		
//	}
	
	
	/* 회원정보 조회 */
	public EmpDto selectEmpDetail(Integer empCode) {
		log.info("[EmpService] selectEmpDetail start ============================== ");
		log.info("[EmpService] empCode : {}" , empCode);
		
		Emp emp = empRepository.findById(empCode)
				.orElseThrow( ()-> new IllegalArgumentException(empCode + " 사번을 가진 사원이 없습니다."));
		log.info("[EmpService] emp : {}" , emp);
		
		EmpDto empDto = modelMapper.map(emp, EmpDto.class);
		
		if(empDto.getFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("emp")).count() != 0) {
		List<FileCategoryDto> empFileCategoryDto = empDto.getFileCategory().stream().filter( fileCategory -> fileCategory.getFCategoryType().equals("emp")).collect(Collectors.toList());
		
		empFileCategoryDto.get(0).getFile().setFilePath(IMAGE_URL + empFileCategoryDto.get(0).getFile().getFilePath());
		
		log.info("[EmpService] empFileCategoryDto : {}" , empFileCategoryDto);
		
		empDto.setFileCategory(empFileCategoryDto);

		
		}
		log.info("[EmpService] empDto : {}" , empDto);
		log.info("[EmpService] selectEmpDetail end ================================ ");
		return empDto;
		
	}
	
  /* 이름으로 조회 페이징 처리*/
	public Page<EmpDto> findByEmpName(String empName, int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
		
		Page<Emp> empList = empRepository.findByEmpName(empName, pageable);
		
		Page<EmpDto> empDtoList = empList.map(emp -> modelMapper.map(emp, EmpDto.class));
		
		return empDtoList;
	}
	
	


	

}
