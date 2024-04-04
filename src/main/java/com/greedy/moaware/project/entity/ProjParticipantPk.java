package com.greedy.moaware.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ProjParticipantPk implements Serializable {

	@Column(name="PROJ_CODE")
	private Integer projCode;
	
	@Column(name="PROJ_MEMBER")
	private Integer projMember;
	
}
