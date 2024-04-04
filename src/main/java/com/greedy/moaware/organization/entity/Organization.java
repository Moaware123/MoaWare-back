package com.greedy.moaware.organization.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="DEPARTMENT")
@SequenceGenerator(name="DEPT_SEQ_GENERATOR", sequenceName="SEQ_DEPT_CODE", initialValue=1, allocationSize=1)
public class Organization {
	
	private static final String mappedBy = null;

	@Id
	@Column(name="DEPT_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPT_SEQ_GENERATOR")
	private Integer deptCode;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="REF_DEPT_CODE")
	private Integer refDeptCode;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_DEPT_CODE", referencedColumnName="DEPT_CODE", insertable = false, updatable = false )
	private Organization highDept;
	
	@OneToMany(mappedBy="highDept")
	private List<Organization> subDept;

	@OneToMany
	@JoinColumn(name="DEPT_CODE")
	private List<OrganizationEmp> orgEmp;
}
