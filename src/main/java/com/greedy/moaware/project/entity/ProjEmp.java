package com.greedy.moaware.project.entity;

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
@Table(name="EMPLOYEE")
@SequenceGenerator(name="EMP_SEQ_GENERATOR", sequenceName="SEQ_EMP_CODE", initialValue=1, allocationSize=1)
public class ProjEmp {

	@Id
	@Column(name="EMP_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ_GENERATOR")
	private Integer empCode;

	@Column(name="EMP_NAME")
	private String empName;

	@Column(name="EMP_ID")
	private String empID;


}
