package com.greedy.moaware.project.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.AuthEmp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PROJ_PRATICIPANT")
public class ProjParticipant {
	
	@EmbeddedId
	private ProjParticipantPk projCode;

	@JoinColumn(name="PROJ_MEMBER", referencedColumnName="EMP_CODE", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private ProjEmp emp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJ_CODE", referencedColumnName = "PROJ_CODE", insertable = false, updatable = false)
	private CreateProject createProject;


}
