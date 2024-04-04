package com.greedy.moaware.leave.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.work.entity.WorkEmp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="LEAVE")
public class Leave {

	@EmbeddedId
	private LeavePk leavePk;
	
	@Column(name="LEAVE_TOTAL_DAY")
	private String leaveTotalDay;

	@Column(name="LEAVE_USE_DAY")
	private String leaveUseDay;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMP_CODE", referencedColumnName="EMP_CODE", insertable=false, updatable=false)
	private WorkEmp emp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMP_CODE", referencedColumnName="EMP_CODE", insertable=false, updatable=false)
	private AuthEmp auth;

}
