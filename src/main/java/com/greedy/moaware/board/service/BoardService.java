package com.greedy.moaware.board.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.board.dto.BoardDto;
import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.repository.BoardPostRepository;
import com.greedy.moaware.boardPost.repository.BoardRepository;
import com.greedy.moaware.employee.entity.Emp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardPostRepository boardPostRepository;
	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;

	public BoardService(BoardPostRepository boardPostRepository, BoardRepository boardRepository,
			ModelMapper modelMapper) {
		this.boardPostRepository = boardPostRepository;
		this.boardRepository = boardRepository;
		this.modelMapper = modelMapper;
	}

	

	/* 게시 판 목록 조회 - 페이징(관리자) */
	public Page<BoardDto> selectBoardList(int page) {

		log.info("[BoardService] selectBoardList start ============================== ");

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").ascending());

		Page<Board> boardList = boardRepository.findAll(pageable);
		// 페이저블,
		Page<BoardDto> boardDtoList = boardList
				.map(board -> modelMapper.map(board, BoardDto.class));


		log.info("[BoardService] boardDtoList.getContent() : {}", boardDtoList.getContent());

		log.info("[BoardService] selectBoardList end ============================== ");

		return boardDtoList;
	}


	
	
	
	/* 게시판 작성 */
	public void insertBoard(BoardDto boardDto) {
		log.info("[BoardService] insertBoard Start ===================================");
		log.info("[BoardService] boardDto : {}", boardDto);
		
		boardRepository.save(modelMapper.map(boardDto, Board.class));
	
		log.info("[BoardService] insertBoard End ==============================");
	}



	/* 게시판 수정 */
	@Transactional
	public void updateBoard(Integer empCode, BoardDto boardDto) {
		
		log.info("[BoardService] updateBoard start ============================== ");
		log.info("[BoardService] boardDto : {}", boardDto);	
		
		
		if (empCode == 1) {

			Board originBoard = boardRepository.findById(boardDto.getBoardCode())
					.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시판이 없습니다. boardCode=" + boardDto.getBoardCode()));

			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
			originBoard.update(
									boardDto.getBoardCode(), 
									boardDto.getBoardName(),
									boardDto.getStatus()
									       
								    );
	
			
		} else {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
			log.info("[BoardPService] updateBoard end ============================== ");
	}

	

	

	
	
	
	
	
	
	
	
	/* 게시판 삭제 */
	
	
	@Transactional
	public void deleteBoard(BoardDto boardDto)  {


		try {
	
	
		Board findBoard = boardRepository.findById(boardDto.getBoardCode())		
				
				.orElseThrow(() -> new IllegalArgumentException("해당 게시판의 정보가 없습니다. findBoard=" + boardDto.getBoardCode()));
				boardDto.setStatus("N");
		
	    findBoard.update(
				boardDto.getStatus()
						);
	
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
	
	
	
	}
	}




	
	
	
	
	
	
	

//	/* 게시 판 목록 조회 - 게시`판`코드 기준, 페이징(사용자) */
//	public Page<BoardDto> selectBoardListByBoard(int page, Long boardCode) {
//
//		log.info("[BoardService] selectBoardListByBoard start ============================== ");
//
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardCode").descending());
//
//		/* 전달할 게시판 코드 엔티티를 먼저 조회한다. */
//		
//		  Board findBoard = boardRepository.findById(boardCode)
//				  .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. boardCode = "+ boardCode));
//		 
//
//		Page<Board> boardList = boardRepository.findByBoard(pageable, findBoard, "Y ");
//
//		Page<BoardDto> boardDtoList = boardList.map(board -> modelMapper.map(board, BoardDto.class));
//
//		
//		log.info("[BoardService] boardDtoList.getContent() : {}", boardDtoList.getContent());
//		
//		log.info("[BoardService] selectBoardListByStatus end ============================== ");
//		
//		return boardDtoList;
//
//	}
//
//	
//	/* 게시 판 목록 조회 - 게시 판 제목 검색 기준, 페이징(사용자) */
//	public Page<BoardDto> selectBoardListByBoardName(int page, String boardName) {
//		
//		log.info("[BoardService] selectBoardListByBoardName start ============================== ");	
//		
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardName").descending());
//
//		Page<Board> boardList = boardRepository.findByBoard (pageable, boardName);
//		Page<BoardDto> boardDtoList = boardList.map(board -> modelMapper.map(board,BoardDto.class));
//		
//	
//		
//		log.info("[BoardService] boardDtoList.getContent() : {}", boardDtoList.getContent());
//		
//		log.info("[BoardService] selectBoardListByBoardName end ============================== ");
//		
//		return boardDtoList;
//	}
//	
//	
//	
//	
	
	
	
	
	
//	
//	
//	
//
//	/* 게시글 상세 조회 - boardCode로 게시글 1개 조회, 조회 불가 게시물 제외(사용자) */
//	public BoardDto selectBoard(Long boardCode) {
//		//파라미터 값 1개가 넘어오고-> DTO 형태로 구현
//		log.info("[BoardService] selectBoard start ============================== ");
//		log.info("[BoardService] boardCode : {}", boardCode);
//		
//		Board board = boardRepository.findByBoardCode(boardCode)
//				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시판이 없습니다. boardCode=" + boardCode));
//		
//		BoardDto boardDto = modelMapper.map(board, BoardDto.class);
//		
//		
//		log.info("[BoardService] boardDto : {}", boardDto);
//		log.info("[BoardService] selectBoard end ============================== ");
//		
//		return boardDto;
//	}
//	
//	/* 게시 물 상세 조회 - boardCode로 게시글 1개 조회, 조회 불가 게시글 포함(관리자) => findById 메소드 사용 */
//	public BoardDto selectBoardForAdmin(Long boardCode) {
//		
//		log.info("[BoardService] selectBoardForAdmin start ============================== ");
//		log.info("[BoardService] boardCode : {}", boardCode);
//		
//		Board board = boardRepository.findById(boardCode)
//				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시판이 없습니다. boardCode=" + boardCode));
//		
//		BoardDto boardDto = modelMapper.map(board, BoardDto.class);
//			
//		log.info("[BoardService] boardDto : {}", boardDto);
//		log.info("[BoardService] selectBoardForAdmin end ============================== ");
//		
//		return boardDto;
//	}
//}
//
//
//	
//	
	
	
	
	
	
	
	
	
	
	
	
	
//----------------------------------------------------
//	/* 게시 판 생성 */
//	@Transactional
//	public void insertBoard(BoardDto boardDto) {
//		
//		log.info("[BoardService] insertBoard Start ===================================");
//		log.info("[BoardService] boardDto : {}", boardDto);
//		
//		boardRepository.save(modelMapper.map(boardDto, Board.class));
//	
//		log.info("[BoardService] insertBoard End ==============================");
//	}
//
//	/* 게시물 수정 */
//
//	@Transactional
//	public void updateBoard(BoardDto boardDto) {
//		
//		log.info("[BoardService] insertBoard start ============================== ");
//		log.info("[BoardService] boardDto : {}", boardDto);
//		
//		Board originBoard = boardPostRepository.findById(boardPostDto.getPostCode())
//				.orElseThrow(() -> new IllegalArgumentException("해당 코드의 게시물이 없습니다. postCode=" + boardPostDto.getPostCode()));
//		
//
//			/* 이미지를 변경하지 않는 경우에는 
//			 * 별도의 처리가 필요 없음 = 할 일이 없고 코드가 흘러가게 처리 */
//			
//			/* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
//			originBoard.update(
//					boardPostDto.getPostCode(),
//					modelMapper.map(boardPostDto.getBoard(), Board.class),
//					//엔티티로 값을 바꿔서 처리하는 과정
//					boardPostDto.getPostCategory(),
//					boardPostDto.getPostTitle(),
//					boardPostDto.getPostContent(),
//					boardPostDto.getCreateDate(),
//					boardPostDto.getModifyDate(),
//					boardPostDto.getStatus(),
//					boardPostDto.getViews(),
//					modelMapper.map(boardPostDto.getWriter(), Emp.class)
//					//엔티티로 값을 바꿔서 처리하는 과정
//			);
//		
////		} catch (IOException e) {
////			e.printStackTrace();
//		
//		
//		log.info("[BoardService] insertBoard end ============================== ");
//	}
//	
//	
//	
//	
//	
//	
//}
//
