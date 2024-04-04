package com.greedy.moaware.project.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.moaware.employee.dto.AuthEmpDto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreateProjectDto {
	
	private Integer projCode;
	private String projName;
	private String projContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date projStartDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date projEndDate;
	private String projStatus;
	private AuthEmpDto employee;
	private CreateProjectEmpDto emp;
	private List<ProjParticipantDto> projMember;

		
	
	
}
