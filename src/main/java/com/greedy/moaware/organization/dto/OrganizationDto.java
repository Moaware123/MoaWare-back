package com.greedy.moaware.organization.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrganizationDto {
	

	private Integer deptCode;
	private String deptName;
	private Integer refDeptCode;
	private List<HighOrganizationDto> subDept;
	private List<OrganizationEmpDto> orgEmp;
	

}
