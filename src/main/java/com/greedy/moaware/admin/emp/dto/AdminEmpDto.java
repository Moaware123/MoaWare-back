package com.greedy.moaware.admin.emp.dto;

import java.sql.Date;

import com.greedy.moaware.employee.dto.DeptDto;
import com.greedy.moaware.employee.dto.FileCategoryDto;
import com.greedy.moaware.employee.dto.JobDto;

import lombok.Data;

@Data
public class AdminEmpDto {

	private Integer empCode;
	private String empName;
	private String phone;
	private String empID;
	private String empPwd;
	private String email;
	private String retireYn;
	private Date hireDate;
	private String extensionNum;
	private String empSsi;
	private JobDto job;
	private DeptDto dept;
	
}