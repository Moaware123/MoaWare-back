package com.greedy.moaware.project.dto;

import com.greedy.moaware.employee.dto.JobDto;
import com.greedy.moaware.employee.dto.PdeptDto;

import lombok.Data;

@Data
public class CreateProjectEmpDto {

	private Integer empCode;
	private String empName;
	private String empID;
	private String email;
	private JobDto job;
	private PdeptDto dept;
}
