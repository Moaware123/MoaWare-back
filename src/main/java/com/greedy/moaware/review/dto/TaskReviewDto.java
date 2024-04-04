package com.greedy.moaware.review.dto;

import java.util.Date;

import com.greedy.moaware.project.dto.TaskDto;

import lombok.Data;

@Data
public class TaskReviewDto {
	
	private Integer reviewCode;
	private String content;
	private ReviewEmpDto emp;
	private Date date;
	private String type;
	private TaskDto task;
	private String status;
	private Date modifyDate;

}

