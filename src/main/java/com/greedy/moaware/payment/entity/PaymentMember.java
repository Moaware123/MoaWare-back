package com.greedy.moaware.payment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity
@Table(name="PAYMENT_MEMBER")
public class PaymentMember {
	
	@EmbeddedId
	private PaymentMemberPk paymentMemberPk;
	
	@Column(name="PAY_TOTAL_YN")
	private String payTotalYn;
	
	@Column(name="PAY_FINAL_YN")
	private String payFinalYn;
	
	@Column(name="PAY_DATE")
	private Date payDate;
	
	@Column(name="PAY_TYPE")
	private String payType;
	
	@Column(name="CANCLE_REASON")
	private String cancleReason;
	
	@Column(name="payRank")
	private Integer payRank;
	
	@OneToOne
	@JoinColumn(name="EMP_CODE", insertable = false, updatable = false)
	private PayEmp emp;
	
	

}
