package com.greedy.moaware.board.controller;

import javax.transaction.Transactional;

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
import com.greedy.moaware.board.dto.BoardDto;
import com.greedy.moaware.board.dto.BoardPostDto;
import com.greedy.moaware.board.service.BoardService;
import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/board") 
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/* 1. 게시 판 목록 조회 - 페이징(사용자) */
	@GetMapping("/list")
		public ResponseEntity<ResponseDto> selectBoardList(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[BoardController] : selectBoardList start ==================================== ");
		log.info("[BoardController] : page : {}", page);
		
		Page<BoardDto> boardDtoList = boardService.selectBoardList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardDtoList);
		
		log.info("[BoardController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		//responseDtoWithPaging.setData(boardDtoList);
		responseDtoWithPaging.setData(boardDtoList.getContent()); //페이지 안에 있는 것을 그대로 보내는 것이 아니라 컨탠츠로 꺼내어 넣어 보낸다
		
		log.info("[BoardController] : selectBoardList end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	
	
	
	
	/* 게시판 등록 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDto> insertBoard(@ModelAttribute BoardDto boardDto) {
		//키&밸류 값의 형태를 띄어 url 인코디드 방식으로 전달->포스트맨에서는 바디-> 폼데이터 검색
		boardService.insertBoard(boardDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "게시판 등록 성공"));


	}

	
	/* 2. 게시 판 목록 조회 - 페이징(관리자) */
//	@GetMapping("/boards-management")
//	public ResponseEntity<ResponseDto> selectBoardListForAdmin(@RequestParam(name="page", defaultValue="1") int page) {
//		
//		log.info("[BoardController] : selectBoardListForAdmin start ==================================== ");
//		log.info("[BoardController] : page : {}", page);
//		
//		Page<BoardDto> boardDtoList = boardService.selectBoardListForAdmin(page);
//		
//		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardDtoList);
//		
//		log.info("[BoardController] : pageInfo : {}", pageInfo);
//		
//		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//		responseDtoWithPaging.setPageInfo(pageInfo);
//		//responseDtoWithPaging.setData(boardDtoList);
//		responseDtoWithPaging.setData(boardDtoList.getContent()); //페이지 안에 있는 것을 그대로 보내는 것이 아니라 컨탠츠로 꺼내어 넣어 보낸다
//		
//		log.info("[BoardController] : selectBoardListForAdmin end ==================================== ");
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
//		
//	}
//	}

	
	/* 8. 게시판 수정 */
	@PutMapping("/modify")
	public ResponseEntity<ResponseDto> updateBoard(@AuthenticationPrincipal AuthEmpDto authEmpDto, @ModelAttribute BoardDto boardDto) {
		//@ModelAttribute 키 밸류 값을 받되, url 인코디드 형식으로 받는 다는 뜻
		boardService.updateBoard(authEmpDto.getEmpCode(), boardDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "게시판 수정 성공"));
		
	}
	
	

/* 9. 게시판 Status N(삭제) */
@PutMapping("/delete/{boardCode}")
public ResponseEntity<ResponseDto> deleteBoard(@PathVariable(name="boardCode") long boardCode) {

	BoardDto boardDto = new BoardDto();

	boardDto.setBoardCode(boardCode);

	boardService.deleteBoard(boardDto);

	
	return ResponseEntity
			.ok()
			.body(new ResponseDto(HttpStatus.OK, "게시판 삭제 완료"));
	
}
}

