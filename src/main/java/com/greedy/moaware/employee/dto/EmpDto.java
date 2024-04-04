package com.greedy.moaware.employee.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmpDto {

	private Integer empCode;
	private String empName;
	private String phone;
	private String empPwd;
	private String empID;
	private String email;
	private String retireYn;
	private Date hireDate;
	private String extensionNum;
	private String empSsi;
	private JobDto job;
	private DeptDto dept;
	private List<FileCategoryDto> fileCategory;
	
}