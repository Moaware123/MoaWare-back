package com.greedy.moaware.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDto {

	/*
	 	TASK_CODE	NUMBER
		TASK_NAME	VARCHAR2(100 BYTE)
		TASK_NOTICE	VARCHAR2(500 BYTE)
		TASK_START_DATE	DATE
		TASK_END_DATE	DATE
		TASK_TYPE	VARCHAR2(100 BYTE)
		TASK_STAGE	VARCHAR2(100 BYTE)
		PROJ_CODE	NUMBER
		TASK_AUTHOR	NUMBER
		TASK_STATUS	VARCHAR2(100 BYTE)
		MODIFY_TIME	DATE
		TASK_PROGRESS	NUMBER
	 */
	
	private Integer taskCode;
	private String taskName;
	private String taskNotice;
	private Date startDate;
	private Date endDate;
	private String type;
	private String stage;
	private ProjectDto project;
	private ProjEmpDto author;
	private String status;
	private Date modifyTime;
	private Integer progress;
	
	
	
	
	
	
}
