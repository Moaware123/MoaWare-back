package com.greedy.moaware.payment.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayAttachedFileDto {
	
	private Integer fileCode;
	private String originalFileName;
	private String filePath;
	private String savedFileName;
	
	@JsonIgnore
	private MultipartFile fileInfo;
	
	@JsonIgnore
	private PayFileCategoryDto payFileCategory;

	@Override
	public String toString() {
		return "PayAttachedFileDto [fileCode=" + fileCode + ", originalFileName=" + originalFileName + ", filePath="
				+ filePath + ", savedFileName=" + savedFileName + ", fileInfo=" + fileInfo + "]";
	}

	
}
