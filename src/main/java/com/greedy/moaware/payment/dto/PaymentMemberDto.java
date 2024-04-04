package com.greedy.moaware.payment.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PaymentMemberDto {

	private PaymentMemberPkDto paymentMemberPk;
	private String payTotalYn;
	private String payFinalYn;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDate;
	private String payType;
	private String cancleReason;
	private Integer payRank;
	
	private PayEmpDto emp;

}
