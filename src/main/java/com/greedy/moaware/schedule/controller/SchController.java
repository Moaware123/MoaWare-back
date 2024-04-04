package com.greedy.moaware.schedule.controller;

import java.util.List;

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
import com.greedy.moaware.schedule.dto.ScheduleDto;
import com.greedy.moaware.schedule.service.SchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/schedule")
public class SchController {

	private final SchService schService;

	public SchController(SchService schService) {
		this.schService = schService;
	}
	
	@GetMapping("/hello")
	public String hellowolrd() {
		return "안냥 이거는 한번해보는 실험이야";
	}
	
	/* 전체 캘린더 조회 */
	@GetMapping("/calendar")
	public ResponseEntity<ResponseDto> mySchList(@AuthenticationPrincipal AuthEmpDto authEmp) {
	
		log.info("[SchController] : mySchList start ===================================== ");
		
		List<ScheduleDto> schedules = schService.getScheduleListByUser(authEmp.getEmpCode());
		
		log.info("[SchController] : mySchList  end  ===================================== ");
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "전체 캘린더 조회 완료", schService.getScheduleListByUser(authEmp.getEmpCode())));

	}
	
	/* 상세 캘린더 조회 */
	@GetMapping("/calendar/{schCode}")
	public ResponseEntity<ResponseDto> mySchDetail(@AuthenticationPrincipal AuthEmpDto authEmp, @PathVariable("schCode") Integer schCode) {

		log.info("[SchController] : mySchDetail start ===================================== ");

	    log.info("[SchController] : mySchDetail  end  ===================================== ");

	    return ResponseEntity 
	            .ok()
	            .body(new ResponseDto(HttpStatus.OK, "상세 일정 조회 완료", schService.getScheduleByCodeAndUser(schCode, authEmp.getEmpCode())));
	}
	
	/* 일정 등록 */
	@PostMapping("/calendar")
	public ResponseEntity<ResponseDto> insertSchedule(@AuthenticationPrincipal AuthEmpDto authEmp, @RequestBody ScheduleDto scheduleDto) {
	    log.info("[SchController] : insertSchedule start ===================================== ");

	    log.info("[SchController] : {}", scheduleDto);
	    
	    schService.insertSchedule(authEmp.getEmpCode(), scheduleDto);

	    log.info("[SchController] : insertSchedule  end  ===================================== ");

	    return ResponseEntity
	            .ok()
	            .body(new ResponseDto(HttpStatus.OK, "일정 등록 완료"));
	}
	
	/* 일정 삭제 */
	@PutMapping("/calendar/{schCode}")
	public ResponseEntity<ResponseDto> deleteSchedule(@AuthenticationPrincipal AuthEmpDto authEmp, @PathVariable("schCode") Integer schCode) {
	    
		log.info("[SchController] : deleteSchedule start ===================================== ");
	    
		log.info("schCode : {} ", schCode);
	    
	    // Integer authEmpId = authEmp.getEmpCode(); // 로그인한 사용자의 사원 번호 가져오기
	    
	    schService.deleteSchedule(schCode, authEmp.getEmpCode());
	    
	    log.info("[SchController] : deleteSchedule end ===================================== ");
	    
	    return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 삭제 완료"));
	}
	
	/* 일정 수정 */
	@PutMapping("calendar")
	public ResponseEntity<ResponseDto> modifySchedule(@AuthenticationPrincipal AuthEmpDto authEmp, @RequestBody ScheduleDto scheduleDto) {
	    
		log.info("[SchController] : modifySchedule start ===================================== ");
		
		schService.modifySchedule(authEmp.getEmpCode(), scheduleDto);
		
		log.info("[SchController] : modifySchedule  end  ===================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 수정 완료"));
	}	
	
	/* 일정 참여자 조회 */
	@GetMapping("/emp/{empCode}")
	public ResponseEntity<ResponseDto> findPrarEmpList() {
		
		log.info("[SchController] : findPrarEmpList start ===================================== ");

//		schService.findPrarEmpList;
		
	    log.info("[SchController] : findPrarEmpList  end  ===================================== ");
	    
	    return ResponseEntity 
	            .ok()
	            .body(new ResponseDto(HttpStatus.OK, "일정 참여자 조회 성공"));
	}
	
}
