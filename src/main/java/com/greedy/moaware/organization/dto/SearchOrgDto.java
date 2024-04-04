package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchOrgDto {
	private Integer deptCode;
	private String deptName;
	private List<OrganizationEmpDto> orgEmp;
	
}
