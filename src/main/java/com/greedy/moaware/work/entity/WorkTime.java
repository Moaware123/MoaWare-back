package com.greedy.moaware.work.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="WORK")
public class WorkTime {
	
	@EmbeddedId
	private WorkPk workPk;
	
	@Column(name="WORK_TIME")
	private Date workTime;
	
	@Column(name="QUIT_TIME")
	private Date quitTime;
	
	@Column(name="WORK_STATUS")
	private String workStatus;
	
}

