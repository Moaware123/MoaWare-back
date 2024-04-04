package com.greedy.moaware.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectDto {

	private Integer projCode;
	private String projName;
	private String projContent;
	private Date startDate;
	private Date endDate;
	private String projStatus;
	private ProjEmpDto employee;
}

