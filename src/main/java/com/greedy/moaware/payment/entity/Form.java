package com.greedy.moaware.payment.entity;

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
@Table(name="FORM")
@SequenceGenerator(name="FORM_SEQ_GENERATOR", sequenceName="SEQ_FORM_CODE", initialValue=1, allocationSize=1)
public class Form {
	
	@Id
	@Column(name="FORM_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORM_SEQ_GENERATOR")
	private Integer formCode;
	
	@Column(name="FORM_TITLE")
	private String formTitle;
	
	@Column(name="FORM_STRING")
	private String formString;

}
