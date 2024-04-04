package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchOrganizationDto {
	
	private Integer deptCode;
	private String deptName;
	private List<HighOrganizationDto> highDept;
	private List<HighOrganizationDto> subDept;
	private List<OrganizationEmpDto> orgEmp;


}
