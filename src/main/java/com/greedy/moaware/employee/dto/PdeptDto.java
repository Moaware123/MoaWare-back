package com.greedy.moaware.employee.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greedy.moaware.project.dto.CreateProjectEmpDto;

import lombok.Data;

@Data
public class PdeptDto {
	
	private Integer deptCode;
	private String deptName;
	private String refDeptCode;
//	private List<CreateProjectEmpDto> emp;
//	
//	   @Override
//	    public String toString() {
//	        return "PdeptDto{" +
//	                "deptCode=" + deptCode +
//	                ", deptName='" + deptName + '\'' +
//	                ", refDeptCode='" + refDeptCode + '\'' +
//	                '}';
//	    }
}
