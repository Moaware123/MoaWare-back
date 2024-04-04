package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class HighOrganizationDto {
	
	private Integer deptCode;
	private String deptName;
	private List<OrganizationEmpDto> orgEmp;

}
