package com.greedy.moaware.payment.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
	

	private Integer payCode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date draftDate;
	private String draftTitle;
	private String draftContent;
	private FormDto form;
	private String payStatus;
	private PayEmpDto emp;
	private PayFileCategoryDto payFileCategory;
	private List<PaymentMemberDto> paymentMember;
	private List<RefenceMemberDto> refenceMember;
	
	@Override
	public String toString() {
		return "PaymentDto [payCode=" + payCode + ", draftDate=" + draftDate + ", draftTitle=" + draftTitle
				+ ", payStatus=" + payStatus + ", emp=" + emp + ", paymentMember="
				+ paymentMember + "]";
	}
	
	

	
	

}
