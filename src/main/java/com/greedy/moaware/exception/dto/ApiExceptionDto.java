package com.greedy.moaware.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiExceptionDto {

	private int state;
	private String message;
	
	public ApiExceptionDto(HttpStatus status, String message) {
		this.state = status.value();
		this.message = message;
	}
	
}
