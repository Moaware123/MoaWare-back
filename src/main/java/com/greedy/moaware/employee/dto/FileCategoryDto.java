package com.greedy.moaware.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FileCategoryDto {
	
	private Integer fCategoryCode;
	private String fCategoryName;
	private String fCategoryType;
	private AttachedFileDto file;
	
	@JsonIgnore
	private EmpDto emp;

	@Override
	public String toString() {
		return "FileCategoryDto [fCategoryCode=" + fCategoryCode + ", fCategoryName=" + fCategoryName
				+ ", fCategoryType=" + fCategoryType + ", file=" + file + "]";
	}
	
	
    
}
