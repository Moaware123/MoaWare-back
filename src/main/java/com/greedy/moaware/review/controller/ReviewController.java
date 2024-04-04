package com.greedy.moaware.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.review.dto.TaskReviewDto;
import com.greedy.moaware.review.entity.TaskReview;
import com.greedy.moaware.review.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
	
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	
	@GetMapping("/{taskCode}")
	public ResponseEntity<ResponseDto> selectTaskReviews(@PathVariable int taskCode
//			, @RequestParam(name = "page", defaultValue = "1") int page
			){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 댓글 조회 완료", reviewService.selectTaskReviews(taskCode)));
	}
	
	
	@PostMapping("/{taskCode}")
	public ResponseEntity<ResponseDto> taskReviewRegist(
			  @PathVariable int taskCode
			, @RequestBody TaskReviewDto review
			, @AuthenticationPrincipal AuthEmpDto emp){
		
		reviewService.taskReviewRegist(taskCode, review, emp);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 댓글 등록 완료"));
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> taskReviewUpdate( 
			  @AuthenticationPrincipal AuthEmpDto logEmp
			, @RequestBody TaskReviewDto review ){
		
		log.info("TaskReviewDto : {}", review );
		
		if(logEmp.getEmpCode() == review.getEmp().getEmpCode()) {
			reviewService.taskReviewUpdate(logEmp, review);
		} else {
			new IllegalArgumentException("댓글 작성자만 수정이 가능합니다.");
		}
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 댓글 수정 완료"));
	}
	
	
	@PutMapping("/delete/{reviewCode}")
	public ResponseEntity<ResponseDto> taskReviewDelete( 
			  @AuthenticationPrincipal AuthEmpDto logEmp
			, @PathVariable int reviewCode ){
		
		log.info("reviewCode : {}", reviewCode );
		
		reviewService.taskReviewDelete(logEmp, reviewCode); 	
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 댓글 수정 완료"));
	}
	

}
