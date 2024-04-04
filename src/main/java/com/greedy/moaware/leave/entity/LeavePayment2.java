package com.greedy.moaware.leave.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="LEAVE_PAYMENT")
@SequenceGenerator(name="LEAVE_SEQ_GENERATOR", sequenceName="SEQ_LEAVE_CODE", initialValue=1, allocationSize=1)
public class LeavePayment2 implements Serializable {
	
	//leave 에서 leavePayment 정보를 받기 위해 새로운 Serializable 객체 생성 해서 leave에 참조하도록 추가
	
	@Id
	@Column(name="LEAVE_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LEAVE_SEQ_GENERATOR")
	private Integer leaveCode;
	
	@Column(name="LEAVE_START_DAY")
	private Date leaveStartDay;
	
	@Column(name="LEAVE_TYPE")
	private String leaveType;
	
	@Column(name="LEAVE_REQ_DATE")
	private Date leaveReqDate;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private AuthEmp employee;
	
	@Column(name="L_PAY_STATUS")
	private String lPayStatus;
	
	@ManyToOne
	@JoinColumn(name="L_PAY_EMP")
	private Emp lPayEmp;
	
	@Column(name="L_PAY_DATE")
	private Date lPayDate;
	
	@Column(name="L_FINAL_PAY_DATE")
	private Date lFinalPayDate;
	
	@ManyToOne
	@JoinColumn(name="L_FINAL_PAY_EMP")
	private Emp lFinalPayEmp;
	
	@Column(name="LEAVE_END_DATE")
	private Date leaveEndDate;
	
}
