package com.greedy.moaware.work.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class WorkPk implements Serializable {
	
	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Column(name="WORK_DATE")
	private Date workDate;
	
}
