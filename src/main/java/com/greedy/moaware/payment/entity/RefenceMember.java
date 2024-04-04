package com.greedy.moaware.payment.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "REFENCE_MEMBER")
public class RefenceMember {
	
	@EmbeddedId
	private RefenceMemberPk refenceMemberPk;
	
	@OneToOne
	@JoinColumn(name="EMP_CODE", insertable = false, updatable = false)
	private PayEmp emp;

}
	