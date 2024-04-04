package com.greedy.moaware.work.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.Job;
import com.greedy.moaware.employee.entity.Pdept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="EMPLOYEE")
@SequenceGenerator(name="EMP_SEQ_GENERATOR", sequenceName="SEQ_EMP_CODE", initialValue=1, allocationSize=1)
public class WorkEmp2{
	
	  @Id
	    @Column(name = "EMP_CODE")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ_GENERATOR")
	    private Integer empCode;

	    @Column(name = "EMP_NAME")
	    private String empName;

	    @Column(name = "PHONE")
	    private String phone;

	    @Column(name = "EMP_ID")
	    private String empId;

	    @Column(name = "EMP_PWD")
	    private String empPwd;

	    @Column(name = "EMAIL")
	    private String email;

	    @Column(name = "RETIRE_YN")
	    private String retireYn;

	    @Column(name = "HIRE_DATE")
	    private Date hireDate;

	    @Column(name = "RETIRE_DATE")
	    private Date retireDate;

	    @Column(name = "EXTENSION_NUM")
	    private String extensionNum;

	    @Column(name = "EMP_SSI")
	    private String empSsi;

	    @ManyToOne
	    @JoinColumn(name = "JOB_CODE")
	    private Job job;
	    
	    @ManyToOne
	    @JoinColumn(name = "DEPT_CODE")
	    private Pdept dept;

	    // 추가된 필드
	    @ManyToOne
	    @JoinColumns({
	            @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false),
	            @JoinColumn(name = "WORK_DATE", referencedColumnName = "WORK_DATE", insertable = false, updatable = false)
	    })
	    private WorkTime work;

//	    @Column(name = "WORK_DATE")
//	    private Date workDate;

	}
