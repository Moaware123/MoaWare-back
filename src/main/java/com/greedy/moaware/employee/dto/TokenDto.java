package com.greedy.moaware.employee.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
	
	private String grantType;
	private String empName;
	private String accessToken;
	private Long accessTokenExpiresIn;
	
}
