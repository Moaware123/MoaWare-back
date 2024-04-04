package com.greedy.moaware.schedule.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.Emp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SCH_PRARTICIPANT")
public class SchPrarticipant implements Serializable {

	@EmbeddedId
	private SchPrarPk schPrarPk;
	
	@ManyToOne
	@JoinColumn(name="SCH_MEMBER", insertable = false, updatable = false)
	private Emp schMember;
 
}
