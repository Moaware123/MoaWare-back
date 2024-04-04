package com.greedy.moaware.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="PROJECT")
public class Project {

	
	@Id
	@Column(name="PROJ_CODE")
	private Integer projCode;
	
	@Column(name="PROJ_NAME")
	private String projName;
	
	@Column(name="PROJ_CONTENT")
	private String projContent;
	
	@Column(name="PROJ_START_DATE")
	private Date startDate;
	
	@Column(name="PROJ_END_DATE")
	private Date endDate;
	
	@Column(name="PROJ_STATUS")
	private String projStatus;		// 진행중 , 완료 , 삭제
	
	@ManyToOne
	@JoinColumn(name="PROJ_AUTHOR")
	private ProjEmp employee;
	
}
