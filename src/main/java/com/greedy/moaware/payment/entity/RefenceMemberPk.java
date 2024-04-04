package com.greedy.moaware.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
public class RefenceMemberPk implements Serializable {
	
	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Column(name="PAY_CODE")
	private Integer payCode;
	
	

}
