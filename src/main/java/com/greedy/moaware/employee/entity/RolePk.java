package com.greedy.moaware.employee.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class RolePk implements Serializable{

	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Column(name="AUTH_CODE")
	private Integer authCode;
	
}
