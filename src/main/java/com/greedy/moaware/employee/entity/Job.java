package com.greedy.moaware.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="JOB")
@SequenceGenerator(name="JOB_SEQ_GENERATOR", sequenceName="SEQ_JOB_CODE", initialValue=1, allocationSize=1)
public class Job {
	
	@Id
	@Column(name="JOB_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOB_SEQ_GENERATOR")
	private Integer jobCode;
	
	@Column(name="JOB_NAME")
	private String jobName;

}
