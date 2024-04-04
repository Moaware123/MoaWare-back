package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.PdeptDto;
import com.greedy.moaware.employee.entity.Pdept;
import com.greedy.moaware.employee.repository.PdeptRepository;
import com.greedy.moaware.project.dto.CreateProjectEmpDto;
import com.greedy.moaware.project.entity.CreateProjectEmp;
import com.greedy.moaware.project.repository.CreateProjectEmpRepository;
import com.greedy.moaware.project.repository.CreateProjectRepository;

@Service
public class PdeptService {
	
	private final ModelMapper modelMapper;
	private final PdeptRepository pdeptRepository;
	private final CreateProjectEmpRepository createProjectEmpRepository;
	
	public PdeptService(ModelMapper modelMapper, PdeptRepository pdeptRepository, CreateProjectEmpRepository createProjectEmpRepository) {
		this.modelMapper = modelMapper;
		this.pdeptRepository = pdeptRepository;
		this.createProjectEmpRepository = createProjectEmpRepository;
	}
	
	public List<PdeptDto> findAllDept() {
		
		List<Pdept> deptList = pdeptRepository.findAll();
		
		List<PdeptDto> deptDtoList = deptList.stream().map( dept -> modelMapper.map(dept, PdeptDto.class)).collect(Collectors.toList());
		
		return deptDtoList;
	}


	public List<CreateProjectEmpDto> findAllDeptMember(Integer deptCode) {
		List<CreateProjectEmp> deptEmpList = createProjectEmpRepository.findByDeptDeptCode(deptCode);
		
		List<CreateProjectEmpDto> deptEmpDtoList = deptEmpList.stream().map(emp -> modelMapper.map(emp, CreateProjectEmpDto.class)).collect(Collectors.toList());
		
		
		return deptEmpDtoList;
	}


//	public List<CreateProjectEmpDto> findAllDeptMember(String deptName) {
//		
//		List<CreateProjectEmp> deptEmpList = createProjectEmpRepository.findByDeptDeptName(deptName);
//		
//		List<CreateProjectEmpDto> deptEmpDtoList = deptEmpList.stream().map(emp -> modelMapper.map(emp, CreateProjectEmpDto.class)).collect(Collectors.toList());
//		
//		
//		return deptEmpDtoList;
//	}
}
