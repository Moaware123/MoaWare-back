package com.greedy.moaware.work.dto;

import java.util.Date;
import java.util.List;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.dto.RoleDto;

import lombok.Data;

@Data
public class WorkDto {

	private WorkPkDto workPk;
	
	private Date workTime;

	private Date quitTime;
	
	private String workStatus;
	
	private EmpDto emp;
	
	private List<RoleDto> roleList;
	
	private AuthEmpDto auth;
	
//	private EmpDto employee;	
	
//	private Date workDate;
}
