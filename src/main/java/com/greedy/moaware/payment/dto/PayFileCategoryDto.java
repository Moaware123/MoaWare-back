package com.greedy.moaware.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greedy.moaware.payment.entity.PayEmp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayFileCategoryDto {
	
	private Integer fCategoryCode;
	private String fCategoryName;
	private String fCategoryType;
	
	@JsonIgnore
	private PaymentDto pay;
    
	private PayAttachedFileDto file;
    

    
    
    
	@Override
	public String toString() {
		return "PayFileCategoryDto [fCategoryCode=" + fCategoryCode + ", fCategoryName=" + fCategoryName
				+ ", fCategoryType=" + fCategoryType + ", file=" + file + "]";
	}

    
    
    
}
