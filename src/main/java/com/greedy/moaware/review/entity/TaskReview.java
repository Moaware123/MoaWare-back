package com.greedy.moaware.review.entity;

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

import org.hibernate.annotations.DynamicUpdate;

import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.project.entity.ProjEmp;
import com.greedy.moaware.project.entity.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
@Table(name="REVIEW")
@DynamicUpdate
@SequenceGenerator(name="REVIEW_SEQ_GENERATOR", sequenceName="SEQ_REVIEW_CODE", initialValue=1, allocationSize=1)
public class TaskReview {
	
	@Id
	@Column(name="REVIEW_CODE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
	private Integer reviewCode;
	
	@Column(name="REVIEW_CONTENT")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="REVIEW_AUTHOR")
	private ReviewEmp emp;
	
	@Column(name="REVIEW_DATE")
	private Date date;
	
	@Column(name="REVIEW_TYPE")
	private String type;
	
	@ManyToOne
	@JoinColumn(name="TASK_CODE")
	private Task task;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	
	public void setDate() {
		this.date = new Date();
	}	
	
	public void update(String content) {
		this.content = content;
		setModifyDate(new Date());
	}

}
