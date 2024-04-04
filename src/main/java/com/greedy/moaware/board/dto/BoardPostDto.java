package com.greedy.moaware.board.dto;



import java.sql.Date;

import com.greedy.moaware.boardPost.dto.BoardDTO;
import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.employee.dto.EmpDto;

import lombok.Data;





@Data
public class BoardPostDto {

	
	private Long postCode;
	private BoardDTO board;
	private String postCategory;
	private String postTitle;
	private String postContent;
	private Date createDate;
	private Date modifyDate;
	private String status;
	private Long views;
	private EmpDto writer;
	
	
	
		
	}
	
