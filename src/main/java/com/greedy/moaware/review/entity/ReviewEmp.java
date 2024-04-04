package com.greedy.moaware.review.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.FileCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="EMPLOYEE")
public class ReviewEmp {

	@Id
	@Column(name="EMP_CODE")
	private Integer empCode;

	@Column(name="EMP_NAME")
	private String empName;

	@Column(name="EMP_ID")
	private String empID;

	@OneToMany
	@JoinColumn(name="EMP_CODE")
	private List<FileCategory> fileCategory;

}
