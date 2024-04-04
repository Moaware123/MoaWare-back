package com.greedy.moaware.leave.dto;

import java.util.Date;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;

@Data
public class LeavePaymentDto2 {
	
	private Integer leaveCode;
	
	private Date leaveStartDay;
	
	private String leaveType;
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date leaveReqDate;
	
	private AuthEmpDto employee;
	
	private String lPayStatus;
	
	private EmpDto payEmp;
	
	private Date lPayDate;
	
	private Date lFinalPayDate;
	
	private EmpDto payFinalEmp;
	
	private Date leaveEndDate;
	
}
