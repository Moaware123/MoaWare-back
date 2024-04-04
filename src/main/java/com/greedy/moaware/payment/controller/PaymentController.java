package com.greedy.moaware.payment.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.payment.dto.PayAttachedFileDto;
import com.greedy.moaware.payment.dto.PaymentDto;
import com.greedy.moaware.payment.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;	
	}	
	
	
	/* 기안자 기안문 전체 조회 */
	@GetMapping("/main")
	public ResponseEntity<ResponseDto> PaymentAllList(@AuthenticationPrincipal AuthEmpDto payEmp){
		
		log.info("[PaymentController] PaymentList start ============================== ");

		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 조회.", paymentService.paymentAllList(payEmp.getEmpCode()) ));
	}
	
	
	/* 기안자로 기안문 진행중 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> PaymentList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.paymentList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 진행 문서 조회.", responseDtoPage ));
	}
	
	
	
	/* 기안서 양식 조회*/
	@GetMapping("/draft")
	public ResponseEntity<ResponseDto> FormSelect(@AuthenticationPrincipal AuthEmpDto payEmp){
		
		log.info("[PaymentController] FormSelect start ============================== ");
		
		log.info("[PaymentController] FormSelect payEmp :  {}", payEmp.getEmpCode());
				
		Map<String, Object> resultMap = paymentService.formSelect(payEmp.getEmpCode());
		
		log.info("[PaymentController] FormSelect pay :  {}", resultMap);

		
		log.info("[PaymentController] FormSelect end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 양식 조회.", resultMap));
	}
	
	
	/* 기안서 저장*/
	@PostMapping("/draft")
	public ResponseEntity<ResponseDto> insertPayment(@AuthenticationPrincipal AuthEmpDto payEmp, @ModelAttribute PayAttachedFileDto payAttachedFile){
		
		log.info("[PaymentController] insertPayment start ============================== ");
		
		log.info("[PaymentController] insertPayment payEmp :  {}", payEmp);
		log.info("[PaymentController] insertPayment payAttachedFile :  {}", payAttachedFile);
				
		paymentService.insertPayment(payEmp.getEmpCode(), payAttachedFile);
		
		log.info("[PaymentController] insertPayment end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, " 기안문 저장 완료"));
	}
	
	/* 결재 대기 문서 전체 조회 */
	@GetMapping("/memberList")
	public ResponseEntity<ResponseDto> PaymentMemberList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.paymentMemberList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		long totalElements = paymentDtoList.getTotalElements();
		log.info("[PaymentController] PaymentList 총 요소 수 : {} ", totalElements);
	
		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 대기 문서 조회.", responseDtoPage ));
	}
	
	@GetMapping("/payDetail/{payCode}")
	public ResponseEntity<ResponseDto> paymentDetail(@PathVariable Integer payCode){
		
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 문서 상세 조회", paymentService.paymentDetail(payCode)));
	}
	
	
	/* 결재 처리 진행 */
	@PutMapping("/updateSign")
	public ResponseEntity<ResponseDto> PaymentUpdate (@AuthenticationPrincipal AuthEmpDto payEmp, @RequestBody PaymentDto paymentDto) {
		
		log.info("[PaymentController] PaymentUpdateSign start ============================== ");
		log.info("[PaymentController] PaymentUpdateSign paymentDto {} ", paymentDto);
		
		paymentService.PaymentUpdate(payEmp.getEmpCode(), paymentDto);
		
		log.info("[PaymentController] PaymentUpdateSign end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 처리 완료"));
	};
	
	
	
	/* 결재 완료 문서 전체 조회 */
	@GetMapping("/complete")
	public ResponseEntity<ResponseDto> PaymentCompleteList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentCompleteList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.PaymentCompleteList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		long totalElements = paymentDtoList.getTotalElements();
		log.info("[PaymentController] PaymentCompleteList 총 요소 수 : {} ", totalElements);
	
		
		log.info("[PaymentController] PaymentCompleteList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 완료 문서 조회.", responseDtoPage ));
	}
	
	/* 결재 반려 문서 전체 조회 */
	@GetMapping("/refuse")
	public ResponseEntity<ResponseDto> PaymentRefuseList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentCompleteList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.PaymentRefuseList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		long totalElements = paymentDtoList.getTotalElements();
		log.info("[PaymentController] PaymentCompleteList 총 요소 수 : {} ", totalElements);
	
		
		log.info("[PaymentController] PaymentCompleteList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 반려 문서 조회.", responseDtoPage ));
	}
	
	
	/* 결재 임시 저장 문서 전체 조회 */
	@GetMapping("/storage")
	public ResponseEntity<ResponseDto> PaymentStorageList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentStorageList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.PaymentStorageList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		long totalElements = paymentDtoList.getTotalElements();
		log.info("[PaymentController] PaymentStorageList 총 요소 수 : {} ", totalElements);
	
		
		log.info("[PaymentController] PaymentStorageList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 임시 저장 문서 조회.", responseDtoPage ));
	}
	
	/* 서명 조회 */
	@GetMapping("/sign")
	public ResponseEntity<ResponseDto> PaymentSign( @AuthenticationPrincipal AuthEmpDto payEmp){
		
		log.info("[PaymentController] PaymentSign start ============================== ");
		
		EmpDto empsign = paymentService.paymentSign(payEmp.getEmpCode());
		
		log.info("[PaymentController] PaymentSign end ============================== ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사인 조회.", empsign ));
				
	}
	
	/* 서명 저장 */
	@PostMapping("/sign")
	public ResponseEntity<ResponseDto> PaymentSignSaved( @AuthenticationPrincipal AuthEmpDto payEmp, @ModelAttribute PayAttachedFileDto payAttachedFile){
		
		log.info("[PaymentController] PaymentSign start ============================== ");
		log.info("[PaymentController] PaymentSign emp : {} " , payAttachedFile);
		
		paymentService.paymentSignSaved(payEmp.getEmpCode(), payAttachedFile);
		
		log.info("[PaymentController] PaymentSign end ============================== ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사인 저장 완료." ));
				
	}
	
	/* 서명 수정 */
	@PutMapping("/sign")
	public ResponseEntity<ResponseDto> PaymentSignUpdate( @AuthenticationPrincipal AuthEmpDto payEmp, @ModelAttribute PayAttachedFileDto payAttachedFile){
		
		log.info("[PaymentController] PaymentSign start ============================== ");
		log.info("[PaymentController] PaymentSign emp : {} " , payAttachedFile);
		
		paymentService.paymentSignUpdate(payEmp.getEmpCode(), payAttachedFile);
		
		log.info("[PaymentController] PaymentSign end ============================== ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사인 수정 완료." ));
				
	}
	
	
	
}
