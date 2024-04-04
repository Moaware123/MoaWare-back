package com.greedy.moaware.employee.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="EMP_ROLE")
public class Role {
	
	@EmbeddedId
	private RolePk role;
	
	@ManyToOne
	@JoinColumn(name="AUTH_CODE", insertable=false, updatable=false)
	private Auth auth;
	
/*	@Id
	@Column(name="EMP_CODE")
	private Integer empCode;
	
	@Id
	@Column(name="AUTH_CODE")
	private Integer authCode; 
*/

}
