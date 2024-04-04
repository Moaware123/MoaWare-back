package com.greedy.moaware.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AttachedFileDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.service.AuthEmpService;
import com.greedy.moaware.employee.service.EmpService;

@RestController	
@RequestMapping("/auth")
public class AuthController {

	private final AuthEmpService authService;
	private final EmpService empService;
	
	public AuthController(AuthEmpService authService, EmpService empService) {
		this.authService = authService;
		this.empService = empService;
	}
	
	
	/* 로그인 하기 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody AuthEmpDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인 완료", authService.login(emp)));
	}
	
	
	/* 아이디 찾기 */
	@PostMapping("/idfind")
	public ResponseEntity<ResponseDto> accountIdFind(@RequestBody AuthEmpDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"아이디 찾기 완료", authService.accountIdFind(emp)));
	}
	
	
	/* 비밀번호 찾기 */
	@PostMapping("/pwdfind")
	public ResponseEntity<ResponseDto> accountPwdFind(@RequestBody AuthEmpDto emp){
		
		authService.accountPwdFind(emp);
		 
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"비밀번호 찾기 완료"));
	}
	
	/* 메인 헤더 이름 */
	@GetMapping("/name")
	public ResponseEntity<ResponseDto> accountNameFind(@AuthenticationPrincipal AuthEmpDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "이름을 찾았다", emp.getEmpName()));
	}

	/* 회원 정보 수정 재로그인 */
	@PostMapping("/infoCheck")
	public ResponseEntity<ResponseDto> memberInfo(@AuthenticationPrincipal AuthEmpDto emp, @RequestBody AuthEmpDto empPwd){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 비밀번호 확인 완료", authService.memberCheck(emp, empPwd)));
	}
	
	
	/* 회원 정보 수정 */
	@PostMapping("/modify")
	public ResponseEntity<ResponseDto> memberModify(@AuthenticationPrincipal AuthEmpDto emp,
			@ModelAttribute AttachedFileDto fileDto) {
		
		authService.memberModify(emp, fileDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 수정 완료"));
	}
	
	

	
	
}
