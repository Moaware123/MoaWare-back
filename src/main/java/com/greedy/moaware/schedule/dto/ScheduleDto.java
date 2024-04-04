package com.greedy.moaware.schedule.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;

@Data
public class ScheduleDto {

	private Integer schCode;

	private String schName;

	private String schContent;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date schDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date schEndDate;

	private EmpDto schAuthor;

	private List<SchPrarticipantDto> schPrarticipant;

	private SchCategoryDto schType;
	
}
