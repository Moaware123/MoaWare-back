package com.greedy.moaware.payment.dto;

import lombok.Data;

@Data
public class PayDeptDto {

	private Integer deptCode;
	private String deptName;
	private String refDeptCode;
	private PayDeptDto highDept;
}
