package com.greedy.moaware.payment.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
@Table(name="PAYMENT")
@SequenceGenerator(name="PAY_SEQ_GENERATOR", sequenceName="SEQ_PAY_CODE", initialValue=1, allocationSize=1)
public class Payment {
	
	@Id
	@Column(name="PAY_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAY_SEQ_GENERATOR")
	private Integer payCode;
	
	@Column(name="DRAFT_DATE")
	private Date draftDate;
	
	@ManyToOne
	@JoinColumn(name="EMP_CODE")
	private PayEmp emp;
	
	@Column(name="DRAFT_TITLE")
	private String draftTitle;
	
	@Column(name="DRAFT_CONTENT")
	private String draftContent;
	
	@Column(name="PAY_STATUS")
	private String payStatus;
	
	@ManyToOne
	@JoinColumn(name="FORM_CODE")
	private Form form;
	
	@OneToOne(mappedBy = "pay") 
	private PayFileCategory payFileCategory;
	
	@OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="PAY_CODE", referencedColumnName="PAY_CODE")  
	private List<PaymentMember> paymentMember;
	
	@OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="PAY_CODE", referencedColumnName="PAY_CODE")  
	private List<RefenceMember> refenceMember;

	@Override
	public String toString() {
		return "Payment [payCode=" + payCode + ", draftDate=" + draftDate + ", emp=" + emp + ", draftTitle="
				+ draftTitle  + ", payStatus=" + payStatus + ", payFileCategory="
				+ payFileCategory + ", paymentMember=" + paymentMember + ", refenceMember=" + refenceMember + "]";
	}
	
	
	
	
	
}
