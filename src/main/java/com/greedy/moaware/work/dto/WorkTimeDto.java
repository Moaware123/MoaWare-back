package com.greedy.moaware.work.dto;

import java.util.Date;

import lombok.Data;

@Data
public class WorkTimeDto {
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//	private LocalTime workTime;
//	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//	private LocalTime quitTime;
	
	private Date workTime;
	private Date quitTime;
	
	private String workStatus;
	private WorkPkDto workPk;
	public void update() {
		
		
		
	}

}
