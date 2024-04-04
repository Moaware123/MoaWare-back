package com.greedy.moaware.schedule.dto;

import java.util.List;

import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;

@Data
public class SchPrarticipantDto {

	private SchPrarPkDto schPrarPk;
	
	private EmpDto schMember;

}