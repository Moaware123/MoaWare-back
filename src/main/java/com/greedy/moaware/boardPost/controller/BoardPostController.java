package com.greedy.moaware.boardPost.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.boardPost.dto.BoardPostDto;
import com.greedy.moaware.boardPost.service.BoardPostService;
import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.project.dto.CreateProjectDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BoardPostController {

	private final BoardPostService boardPostService;

	public BoardPostController(BoardPostService boardPostService) {
		this.boardPostService = boardPostService;
	}
	
	/* 1. 게시글 목록 조회 - 페이징, 조회 불가 게시글 제외(사용자) */
	@GetMapping("/boardPosts")
public ResponseEntity<ResponseDto> selectBoardPostList(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[BoardPostController] : selectBoardPostList start ==================================== ");
		log.info("[BoardPostController] : page : {}", page);
		
		Page<BoardPostDto> boardPostDtoList = boardPostService.selectBoardPostList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardPostDtoList);
		
		log.info("[BoardPostController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		//responseDtoWithPaging.setData(boardPostDtoList);
		responseDtoWithPaging.setData(boardPostDtoList.getContent()); //페이지 안에 있는 것을 그대로 보내는 것이 아니라 컨탠츠로 꺼내어 넣어 보낸다
		
		log.info("[BoardPostController] : selectBoardPostList end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	/* 2. 게시글 목록 조회 - 페이징, 조회 불가 게시글 포함(관리자) */
	@GetMapping("/boardPosts-management")
	public ResponseEntity<ResponseDto> selectBoardPostListForAdmin(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[BoardPostController] : selectBoardPostListForAdmin start ==================================== ");
		log.info("[BoardPostController] : page : {}", page);
		
		Page<BoardPostDto> boardPostDtoList = boardPostService.selectBoardPostListForAdmin(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardPostDtoList);
		
		log.info("[BoardPostController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		//responseDtoWithPaging.setData(boardPostDtoList);
		responseDtoWithPaging.setData(boardPostDtoList.getContent()); //페이지 안에 있는 것을 그대로 보내는 것이 아니라 컨탠츠로 꺼내어 넣어 보낸다
		
		log.info("[BoardPostController] : selectBoardPostListForAdmin end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}


/* 3. 게시글 목록 조회 - 게시판코드 기준, 페이징, 조회 불가 게시물 제외(사용자) */
@GetMapping("/boardPosts/boards/{boardCode}")
public ResponseEntity<ResponseDto> selectBoardPostListByBoard(
		@RequestParam(name="page", defaultValue="1") int page, @PathVariable Long boardCode){
	
	log.info("[BoardPostController] : selectBoardPostListByBoard start ==================================== ");
	log.info("[BoardPostController] : page : {}", page);
	
	Page<BoardPostDto> boardPostDtoList = boardPostService.selectBoardPostListByBoard(page, boardCode);
	
	PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardPostDtoList);
	
	log.info("[BoardPostController] : pageInfo : {}", pageInfo);
	
	ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
	responseDtoWithPaging.setPageInfo(pageInfo);
	responseDtoWithPaging.setData(boardPostDtoList.getContent());
	
	log.info("[BoardPostController] : selectBoardPosttListByBoard end ==================================== ");
	
	return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
}

	/* 4. 게시물 목록 조회 - 게시물제목 검색 기준, 페이징, 조회 불가 게시물 제외(사용자) 
	 * postTitle 값은 parameter로 전달 받도록 하여 URL 설정 */
		@GetMapping("/boardPosts/search")
			public ResponseEntity<ResponseDto> selectBoardPostListByPostTitle(
					@RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="search") String postTitle) {
	
			log.info("[BoardPostController] : selectBoardPostListByPostTitle start ==================================== ");
			log.info("[BoardPostController] : page : {}", page);
			log.info("[BoardPostController] : postTitle : {}", postTitle);
	
			Page<BoardPostDto> boardPostDtoList = boardPostService.selectBoardPostListByPostTitle(page, postTitle);
	
			PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(boardPostDtoList);
	
			log.info("[BoardPostController] : pageInfo : {}", pageInfo);
	
			ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
			responseDtoWithPaging.setPageInfo(pageInfo);
			responseDtoWithPaging.setData(boardPostDtoList.getContent());
	
			log.info("[BoardPostController] : selectBoardPostListByPostTitle end ==================================== ");
	
			return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
			}

		/* 5. 게시물 상세 조회 - postCode로 게시물 1개 조회, 조회 불가 게시물 제외(사용자) */
		@GetMapping("boardPosts/{postCode}") //특정 고유값을 pk 통해 조회 할 경우 path v.
			public ResponseEntity<ResponseDto> selectBoardPostDetail(@PathVariable Long postCode) {
	
			return ResponseEntity
			.ok()
			.body(new ResponseDto(HttpStatus.OK, "조회 성공", boardPostService.selectBoardPost(postCode)));
		} // 가공하는 처리 없이 넣는 PROCESS

		/* 6. 게시물 상세 조회 - postCode로 게시물 1개 조회, 조회 불가 게시물 포함(관리자) => findById 메소드 사용 */
		@GetMapping("boardPosts-management/{postCode}") //url 매핑 주소 가 다르면 문제-> 중복 없게 수정
		public ResponseEntity<ResponseDto> selectBoardPostDetailForAdmin(@PathVariable Long postCode) {
			
			return ResponseEntity
					.ok()
					.body(new ResponseDto(HttpStatus.OK, "조회 성공", boardPostService.selectBoardPostForAdmin(postCode)));
		}
		
		/* 7.게시물 등록 */
		@PostMapping("/boardPosts")
		public ResponseEntity<ResponseDto> insertBoardPost(@AuthenticationPrincipal AuthEmpDto authEmpDto, @ModelAttribute BoardPostDto boardPostDto) {
			//키&밸류 값의 형태를 띄어 url 인코디드 방식으로 전달->포스트맨에서는 바디-> 폼데이터 검색
			
			boardPostService.insertBoardPost(authEmpDto.getEmpCode(), boardPostDto);
			
			return ResponseEntity
					.ok()
					.body(new ResponseDto(HttpStatus.OK, "게시물 등록 성공"));

	
		}
		
		/* 8. 게시물 수정 */
		@PutMapping("/boardPosts")
		public ResponseEntity<ResponseDto> updateBoardPost(@AuthenticationPrincipal AuthEmpDto authEmpDto, @ModelAttribute BoardPostDto boardPostDto) {
			//@ModelAttribute 키 밸류 값을 받되, url 인코디드 형식으로 받는 다는 뜻
			boardPostService.updateBoardPost(authEmpDto.getEmpCode(), boardPostDto);
			
			return ResponseEntity
					.ok()
					.body(new ResponseDto(HttpStatus.OK, "게시물 수정 성공"));
			
		}

//		
//		/* 8. 게시물 수정 */
//		@PutMapping("/boardPosts")
//		public ResponseEntity<ResponseDto> updateBoardPost(@ModelAttribute BoardPostDto boardPostDto) {
//			//@ModelAttribute 키 밸류 값을 받되, url 인코디드 형식으로 받는 다는 뜻
//			boardPostService.updateBoardPost(boardPostDto);
//			
//			return ResponseEntity
//					.ok()
//					.body(new ResponseDto(HttpStatus.OK, "게시물 수정 성공"));
//		}
		
//		
		/* 9. 게시물 Status N(삭제) */
		@PutMapping("/delete/{postCode}")
		public ResponseEntity<ResponseDto> deleteBoardPost(@PathVariable(name="postCode") long postCode) {

			BoardPostDto boardPostDto = new BoardPostDto();

			boardPostDto.setPostCode(postCode);
		
			boardPostService.deleteBoardPost(boardPostDto);

			
			return ResponseEntity
					.ok()
					.body(new ResponseDto(HttpStatus.OK, "게시물 삭제 완료"));
			
		}
		
		
		
		}












		
		
		
		
		
		
		
		
		
		






