package com.greedy.moaware.organization.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrganizationEmpDto {

	
	private Integer empCode;
	private String empName;
	private String phone;
	private String empID;
	private String email;
	private String retireYn;
	private Date hireDate;
	private String extensionNum;
	private String empSsi;
	private OrganizationJobDto job;
}
