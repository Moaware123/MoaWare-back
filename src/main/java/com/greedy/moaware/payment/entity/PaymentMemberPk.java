package com.greedy.moaware.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class PaymentMemberPk implements Serializable {
	
	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Column(name="PAY_CODE")
	private Integer payCode;
	
	

}
