package com.greedy.moaware.payment.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class PayEmpDto {
	
	private Integer empCode;
	private String empName;
	private String phone;
	private String empID;
	private String email;
	private String retireYn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDate;
	private String extensionNum;
	private String empSsi;
	private PayJobDto job;
	private PayDeptDto dept;
	private List<PayFileCategoryDto> payFileCategory;
	

}
