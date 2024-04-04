package com.greedy.moaware.project.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.greedy.moaware.employee.entity.AuthEmp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PROJECT")
@SequenceGenerator(name="PROJ_SEQ_GENERATOR", sequenceName="SEQ_PROJ_CODE", initialValue=1, allocationSize=1)
public class CreateProject {

	@Id
	@Column(name="PROJ_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJ_SEQ_GENERATOR")
	private Integer projCode;
	
	@Column(name="PROJ_NAME")
	private String projName;
	
	@Column(name="PROJ_CONTENT")
	private String projContent;
	
	@Column(name="PROJ_START_DATE")
	private Date projStartDate;
	
	@Column(name="PROJ_END_DATE")
	private Date projEndDate;
	
	@Column(name="PROJ_STATUS")
	private String projStatus;		// 진행중 , 완료 , 삭제
	
	@ManyToOne
	@JoinColumn(name = "PROJ_AUTHOR", referencedColumnName = "EMP_CODE")
	private AuthEmp employee;
	
	/*AuthEmp에 없는 직급, 부서 코드를 알아오기 위해서*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJ_AUTHOR", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
	private CreateProjectEmp emp;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name="PROJ_CODE", referencedColumnName="PROJ_CODE") 
	private List<ProjParticipant> projMember;

	public void update(String projStatus) {
		
		this.projStatus = projStatus;
	}

	
	
	
	
}
