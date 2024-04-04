package com.greedy.moaware.admin.emp.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.FileCategory;
import com.greedy.moaware.employee.entity.Job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name="EMPLOYEE")
@SequenceGenerator(name="EMP_SEQ_GENERATOR", sequenceName="SEQ_EMP_CODE", initialValue=1, allocationSize=1)
public class AdminEmp {
	
	@Id
	@Column(name="EMP_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ_GENERATOR")
	private Integer empCode;
	
	@Column(name="EMP_NAME")
	private String empName;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="EMP_ID")
	private String empID;
	
	@Column(name="EMP_PWD")
	private String empPwd;
	 
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="RETIRE_YN")
	private String retireYn;
	
	@Column(name="HIRE_DATE")
	private Date hireDate;
	
	@Column(name="EXTENSION_NUM")
	private String extensionNum;
	
	@Column(name="EMP_SSI")
	private String empSsi;
	
	@ManyToOne
	@JoinColumn(name="JOB_CODE")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="DEPT_CODE")
	private Dept dept;
	

	
	

}