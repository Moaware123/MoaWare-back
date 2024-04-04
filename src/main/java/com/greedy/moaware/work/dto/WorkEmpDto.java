package com.greedy.moaware.work.dto;

import java.util.Date;
import java.util.List;

import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.Job;

import lombok.Data;

@Data
public class WorkEmpDto {
	
	private Integer empCode;
	private String empName;
	private Job job;
	private Dept dept;
	private Date hireDate;
	private WorkDto work;
}
