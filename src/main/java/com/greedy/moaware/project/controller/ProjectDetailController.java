package com.greedy.moaware.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.project.dto.TaskDto;
import com.greedy.moaware.project.service.ProjDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/proj")
public class ProjectDetailController {

	private final ProjDetailService projService;
	
	public ProjectDetailController(ProjDetailService projService) {
		this.projService = projService;
	}
	
	
	
	/* 프로젝트의 업무 리스트 조회 */
	@GetMapping("/tasks/{projCode}")
	public ResponseEntity<ResponseDto> selectTaskList(@PathVariable int projCode, @AuthenticationPrincipal AuthEmpDto emp){
		log.info("principal : {} ", emp.getEmpCode());
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 리스트 조회 성공", projService.selectTaskList(projCode)));
	}
	
	
	/* 업무 상세 조회 */
	@GetMapping("/task/{taskCode}")
	public ResponseEntity<ResponseDto> selectTask(@PathVariable int taskCode){
		log.info("taskCode : {} ",taskCode);
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 상세 조회 성공", projService.selectTask(taskCode)));
	}
	
	
	/* 업무 등록 */
	@PostMapping("/task/regist")
	public ResponseEntity<ResponseDto> taskRegist(@AuthenticationPrincipal AuthEmpDto emp, @RequestBody TaskDto task){
		log.info("task : {} ", task);
		log.info("emp : {} ", emp);
		
		projService.taskRegist(emp, task);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 등록 성공"));
	}
	
	
	/* 프로젝트 상세 조회 */
	@GetMapping("/detail/{projCode}")
	public ResponseEntity<ResponseDto> selectProjDetail(@PathVariable int projCode){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "프로젝트 상세 조회 성공", projService.selectProjDetail(projCode)));
	}
	
	
	/* 업무 수정 */
	@PutMapping("/task/update")
	public ResponseEntity<ResponseDto> taskUpdate(
			@AuthenticationPrincipal AuthEmpDto emp,
			@RequestBody TaskDto task){
		log.info("task : {} ", task);
		
		projService.taskUpdate(task);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 수정 성공"));
	}
	
	
	/* 업무 삭제 */
	@PutMapping("/task/delete/{taskCode}")
	public ResponseEntity<ResponseDto> taskDelete(@PathVariable int taskCode){
		log.info("taskCode : {} ", taskCode);
		
		projService.taskDelete(taskCode);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 삭제 완료 ."));
	}
}
 

























//	/* 업무 리스트 조회 */
//	@GetMapping("/task/stage/{projCode}/{stage}")
//	public ResponseEntity<ResponseDto> selectTodoList(@PathVariable int projCode, @PathVariable String stage){
//		
//		log.info("[ProjectDetailController] selectTodoList stage : {}", stage);
//
//		return ResponseEntity
//				.ok()
//				.body(new ResponseDto(HttpStatus.OK, "task stage 리스트 조회 성공", projService.selectTaskStageList(projCode, stage)));
//	}
//	



//	