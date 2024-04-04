package com.greedy.moaware.admin.emp.controller;

import java.util.List;

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

import com.greedy.moaware.admin.emp.dto.AdminEmpDto;
import com.greedy.moaware.admin.emp.service.AdminEmpService;
import com.greedy.moaware.boardPost.dto.BoardPostDto;
import com.greedy.moaware.common.ResponseDto;

import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/emp")
public class AdminEmpController {
	
	private AdminEmpService adminEmpService;
	
	public AdminEmpController(AdminEmpService adminEmpService) {
		this.adminEmpService = adminEmpService;
	}
	
	/* 계정(회원) 전체 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectAdminEmpList(@RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[AdminEmpController] selectAdmninEmpList start ============================== ");
		log.info("[BoardController] : page : {}", page);

		
		Page<AdminEmpDto> adminEmpDtoList =  adminEmpService.selectAdminEmpList(page);
		
		log.info("adminEmpDtoList : {}" , adminEmpDtoList);
		log.info("[AdminEmpController] selectAdminEmpList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", adminEmpDtoList ));
		
}
	
	/* 계정(회원) 상세 조회*/
	@GetMapping("/list/{empCode}")
	public ResponseEntity<ResponseDto> selectAdminEmpDetail(@PathVariable Integer empCode){
		
		log.info("[AdminEmpController] selectAdminEmpDetail start ============================== ");
		log.info("empCode : {}" , empCode);
		
		
		log.info("[AdminEmpController] selectAdminEmpDetail end ================================ ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", adminEmpService.selectAdminEmpDetail(empCode) ));
	}	
	
	/* 계정(회원) 등록 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDto> insertAdminEmp(@ModelAttribute AdminEmpDto adminEmpDto) {
		//키&밸류 값의 형태를 띄어 url 인코디드 방식으로 전달->포스트맨에서는 바디-> 폼데이터 검색
		adminEmpService.insertAdminEmp(adminEmpDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "게시물 등록 성공"));


	}
	
	
	
//	
//	
//    /* 이름으로 조회 */
//    @GetMapping("/{name}")
//    public ResponseEntity<ResponseDto> getEmployeeByName(@PathVariable String name,
//    		 @RequestParam(name = "page", defaultValue = "1") int page) {
//    	Page<EmpDto> empDtoList =  empService.findByEmpName(name, page);
//    	
//    	PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(empDtoList);
//	
//		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//		responseDtoWithPaging.setPageInfo(pageInfo);
//		responseDtoWithPaging.setData(empDtoList.getContent());
//    	
//    	
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회성공", responseDtoWithPaging));   
//    }
//    
//	
//	/* 회원 정보 조회 */
//	@GetMapping("/member/info")
//	public ResponseEntity<ResponseDto> selectMemberInfo(@AuthenticationPrincipal AuthEmpDto empDto){
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 조회 완료", empService.selectEmpDetail(empDto.getEmpCode())));
//	}
//	
//	
//	/* 회원 정보 수정 */
//	@PutMapping("/modify")
//	public ResponseEntity<ResponseDto> infoModify(@RequestBody AuthEmpDto empDto){
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 수정 완료", empService.infoModify(empDto)));
//	}
//	

}