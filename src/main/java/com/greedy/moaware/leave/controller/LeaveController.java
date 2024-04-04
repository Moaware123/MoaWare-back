package com.greedy.moaware.leave.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.leave.dto.LeavePaymentDto;
import com.greedy.moaware.leave.entity.Leave;
import com.greedy.moaware.leave.entity.LeavePk;
import com.greedy.moaware.leave.service.LeaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveController {

	private final LeaveService leaveService;

	public LeaveController(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	/* 연차 내역 조회 */
	@GetMapping("done")
	public ResponseEntity<ResponseDto> leaveList(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		

		
		Page<LeavePaymentDto> LeavePaymentDtoList = leaveService.myLeaveList(emp.getEmpCode(), page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeavePaymentDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeavePaymentDtoList.getContent());

		log.info("[LeaveController] : leaveList end =========================================================");

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 조회 완료", responseDtoWithPaging));
	}
	
	@GetMapping("done/{workDate}")
	public ResponseEntity<ResponseDto> leaveList(@AuthenticationPrincipal AuthEmpDto emp,
			@PathVariable String workDate,
			@RequestParam(name = "page", defaultValue = "1") int page) {
	    Date parsedDate = null;
	    try {
	        parsedDate = new SimpleDateFormat("yyyy").parse(workDate);
	    } catch (ParseException e) {

	        e.printStackTrace();

	    }

		
		Page<LeavePaymentDto> LeavePaymentDtoList = leaveService.selectMyLeaveList(emp.getEmpCode(), parsedDate , page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeavePaymentDtoList);
		
		log.info("[LeaveController] : leaveList Start =========================================================");
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeavePaymentDtoList.getContent());


		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 조회 완료", responseDtoWithPaging));
	}


//	/* 년도별 연차 일 수 조회 */
	@GetMapping("year/{year}")
	public ResponseEntity<ResponseDto> leaveList2(@AuthenticationPrincipal AuthEmpDto emp, @PathVariable String year) {

		log.info("[LeaveController] : leaveList2 Start =========================================================");
		
		Integer leaveYear = Integer.parseInt(year);

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 조회 성공",
				leaveService.selectMyLeaveYearList(emp.getEmpCode(), leaveYear)));
	}
	
	/* 연차 일수 삽입 */
	@PostMapping("insert")
	public ResponseEntity<ResponseDto> InsertLeave(@AuthenticationPrincipal AuthEmpDto emp, @RequestBody String year) {
		
		
		
		log.info("[LeaveController] : InsertLeave Start =========================================================");
		
		log.info("입사일 확인 ㅇㅇㅇㅇㅇㅇㅇㅇemp.getHireDate(){},", emp.getHireDate());
		
	    
		Integer leaveYear = Integer.parseInt(year);
		
		LeavePk leavePk = new LeavePk();
		leavePk.setEmpCode(emp.getEmpCode());
		leavePk.setLeaveYear(leaveYear);
		
		Leave leave = new Leave();
		leave.setLeavePk(leavePk);
		
		leaveService.insertLeave(leave, emp);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "연차삽입 완료"));
	}
	
	@PostMapping("request")
	public ResponseEntity<ResponseDto> requestLeave(@AuthenticationPrincipal AuthEmpDto emp, @ModelAttribute LeavePaymentDto leavePayDto){
		
		log.info("연차신청 시작 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.info("리브페이먼트 DTO 값 leavePayDto {}", leavePayDto);
		
		leaveService.insertLeaveRequest(leavePayDto, emp);
		
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 신청 성공"));
	}
	
	@GetMapping("request/list")
	public ResponseEntity<ResponseDto> leaveRequestList(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		

		
		Page<LeavePaymentDto> LeavePaymentDtoList = leaveService.myLeaveRequestList(emp.getEmpCode(), page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeavePaymentDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeavePaymentDtoList.getContent());

		log.info("[LeaveController] : leaveList end =========================================================");

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 신청 조회 완료", responseDtoWithPaging));
	}
	
	@GetMapping("/request/{leaveCode}")
	public ResponseEntity<ResponseDto> selectLeaveDetail (@PathVariable Integer leaveCode) {
		
//		LeavePaymentDto LeavePaymentDtoList = leaveService.selectLeaveDetail(leaveCode);

	
		log.info("[LeaveController] : selectLeaveDetail start =========================================================");

		return ResponseEntity.ok().body(
				new ResponseDto(HttpStatus.OK, "조회 성공", leaveService.selectLeaveDetail(leaveCode)));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

