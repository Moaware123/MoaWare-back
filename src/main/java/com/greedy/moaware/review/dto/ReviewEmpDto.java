package com.greedy.moaware.review.dto;

import java.util.List;

import com.greedy.moaware.employee.dto.FileCategoryDto;

import lombok.Data;

@Data
public class ReviewEmpDto {

	private Integer empCode;
	private String empName;
	private String empID;
	private List<FileCategoryDto> fileCategory;

}
