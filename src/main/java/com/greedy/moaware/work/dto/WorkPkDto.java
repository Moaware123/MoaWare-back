package com.greedy.moaware.work.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkPkDto {
	
	private Integer empCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date workDate;
}
