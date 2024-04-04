package com.greedy.moaware.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name="AUTH")
@Entity
public class Auth {
	
	@Id
	@Column(name="AUTH_CODE")
	private Integer authCode;
	
	@Column(name="AUTH_TITLE")	
	private String authTitle;
	
	@Column(name="AUTH_INFO")	
	private String AuthInfo;
}
