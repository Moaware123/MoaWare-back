package com.greedy.moaware.leave.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.Data;

@Embeddable
@Data
public class LeavePk implements Serializable {
	
	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Column(name="LEAVE_YEAR")
	private Integer leaveYear;

}
