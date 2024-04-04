package com.greedy.moaware.organization.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.organization.dto.OrganizationDto;
import com.greedy.moaware.organization.dto.SearchOrganizationDto;
import com.greedy.moaware.organization.service.OrganizationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/org")
public class OrganizationController {
	
	private OrganizationService organizationService;
	
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	/* 조직도 상위부서 검색 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectOrganizationList(){
		
		log.info("[OrganizationController] selectOrganizationList start ============================== ");
		
		List<OrganizationDto> orgDtoList = organizationService.selectOranizationList();
		
		
		log.info("[OrganizationController] selectOrganizationList orgDtoList : {}", orgDtoList );
		log.info("[OrganizationController] selectOrganizationList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 상위 부서 조회가 성공하였습니다.", orgDtoList ));
		
	}
	
	/* 조직도 하위 부서 및 직원 검색 */
	@GetMapping("/subList/{deptCode}")
	public ResponseEntity<ResponseDto> selectOrganizationList(@PathVariable(name="deptCode") Integer refdeptCode){
		
		log.info("[OrganizationController] selectOrganizationList start ============================== ");
		log.info("[OrganizationController] selectOrganizationList org : {}", refdeptCode );
		

		
		List<OrganizationDto> orgDtoList = organizationService.selectOranizationSubList(refdeptCode);
		
		
		log.info("[OrganizationController] selectOrganizationList orgDtoList : {}", orgDtoList );
		log.info("[OrganizationController] selectOrganizationList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 조직도 하위 부서 및 직원 검색 조회가 성공하였습니다.", orgDtoList ));
		
	}

	
	/* 조직도 이름,직급,직책으로 검색 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDto> selectOrgSearch(@RequestParam(name="search") String search){
		
		log.info("[EmpController] selectOrgSearch start ============================== ");
		log.info("search : {}" , search);
		
		List<SearchOrganizationDto> orgDtoList = organizationService.selectOrgSearch(search);
		
		
		log.info("[EmpController] selectOrgSearch orgDtoList : {}", orgDtoList );
		log.info("[EmpController] selectOrgSearch end ================================ ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", orgDtoList ));
	}
	

}
