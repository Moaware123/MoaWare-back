package com.greedy.moaware.organization.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.organization.dto.OrganizationDto;
import com.greedy.moaware.organization.dto.SearchOrganizationDto;
import com.greedy.moaware.organization.entity.Organization;
import com.greedy.moaware.organization.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class OrganizationService {
	
	private final OrganizationRepository organizationRepository;
	private final ModelMapper modelMapper;
	
	public OrganizationService( OrganizationRepository organizationRepository, ModelMapper modelMapper) {
		this.organizationRepository = organizationRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 조직도 상위부서 검색 */
	public List<OrganizationDto> selectOranizationList() {
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		List<Organization> orgList = organizationRepository.findAll();
		
		List<OrganizationDto> orgDtoList = orgList.stream().map( org -> modelMapper.map(org, OrganizationDto.class)).collect(Collectors.toList());
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		return orgDtoList;
	}
	
	/* 조직도 하위 부서 및 직원 검색 */
	public List<OrganizationDto> selectOranizationSubList( Integer refDeptCode) {
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		List<Organization> orgList = organizationRepository.findByRefDeptCode(refDeptCode);
		
		List<OrganizationDto> orgDtoList = orgList.stream().map( org -> modelMapper.map(org, OrganizationDto.class)).collect(Collectors.toList());
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		return orgDtoList;
	}

	/* 조직도 이름,직급,직책으로 검색 */
	public List<SearchOrganizationDto> selectOrgSearch(String search) {
		
		log.info("[EmpService] selectOrgDetail start ============================== ");
		log.info("[EmpService] search : {}" , search);
		
		List<Organization> orgList = organizationRepository.findBySearch(search);
		log.info("[EmpService] orgList : {}" , orgList);	
		
		List<SearchOrganizationDto> orgDtoList = orgList.stream().map( org -> modelMapper.map(org, SearchOrganizationDto.class)).collect(Collectors.toList());
		log.info("[EmpService] orgDtoList : {}" , orgDtoList);	
		
		log.info("[EmpService] selectOrgDetail end ================================ ");
		return orgDtoList;
	}

}
