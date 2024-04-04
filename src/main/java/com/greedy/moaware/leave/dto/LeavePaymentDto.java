package com.greedy.moaware.leave.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;

@Data
public class LeavePaymentDto {
	
	private Integer leaveCode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveStartDay;
	
	private String leaveType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveReqDate;
	
	private AuthEmpDto employee;
	
	private String lPayStatus;
	
	private EmpDto payEmp;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lPayDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lFinalPayDate;
	
	private EmpDto payFinalEmp;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveEndDate;
	
}
