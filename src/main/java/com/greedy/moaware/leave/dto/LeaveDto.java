package com.greedy.moaware.leave.dto;

import java.util.List;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;

@Data
public class LeaveDto {

	private LeavePkDto leavePk;
	
	private String leaveTotalDay;
		
	private String leaveUseDay;
	
	private EmpDto emp;
	
	private AuthEmpDto auth;

}
