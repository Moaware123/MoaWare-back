package com.greedy.moaware.boardPost.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.boardPost.entity.Board;
import com.greedy.moaware.boardPost.entity.BoardPost;


public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

	/* 1. 게시글 목록 조회 - 페이징, 조회 불가 게시물 제외(사용자) */
	/* 연관 관계가 지연 로딩으로 되어 있을 경우 엔티티를 하나 조회하고 다시 다른 엔티티에 대해서 여러번 조회를 별도로 하게 되는 
	 * N + 1 문제가 발생하게 된다. (성능 이슈) fetch 조인을 사용하게 되면 한 번에 조인해서 결과를 가져오게 된다.
	 * @EntityGraph는 Data JPA에서 fetch 조인은 어노테이션으로 사용할 수 있도록 만들어준 기능이다.*/
	
	
	@EntityGraph(attributePaths= {"board"})
	Page<BoardPost> findByStatus(Pageable pageable, String status);
	
	/* 2. 게시글 목록 조회 - 페이징, 조회 불가 게시물 제외(관리자) */
	/* JpaRepository에 이미 정의 되어 있는 findAll(Pageable pageable) 메소드 사용 가능하므로 별도 정의 필요 없음 */
	@EntityGraph(attributePaths= {"board"})
	Page<BoardPost> findAll(Pageable pageable);
	
	/* 3. 게시글 목록 조회 - 게시`판` 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	Page<BoardPost> findByBoardAndStatus(Pageable pageable, Board board, String status);
	
	/* 4. 게시글 목록 조회 - 게시물제목 검색 기준, 페이징, 조회 불가 게시물 제외(사용자) */
	@EntityGraph(attributePaths= {"board"})
	Page<BoardPost> findByPostTitleContainsAndStatus(Pageable pageable, String postTitle, String status);
	
	/* 5. 게시물 상세 조회 - postCode로 게시물 1개 조회, 조회 불가 게시물 제외(사용자) 
	 * 쿼리 메소드로 구현 가능 findByPostCodeAndStatus(Long postCode, String status) 
	 * JPQL을 사용해서 구현해보기 */
	@Query("SELECT b " +
		   "  FROM BoardPost b " +
		   //"  JOIN fetch b.board " +
		   " WHERE b.postCode = :postCode " +
		   "   AND b.status = 'Y'")
	Optional<BoardPost> findByPostCode(@Param("postCode") Long postCode);

	
			
		
	
	/* 6. 게시글 상세 조회 - postCode로 게시글 1개 조회, 조회 불가 게시글 포함(관리자) => findById 메소드 사용 */
	
	/* 7. 게시글 등록(관리자) => save 메소드가 이미 정의 되어 있으므로 별도 정의 필요 없음 */
	/*
	 * @Query("SELECT b FROM BoardPost b " +
	 * "JOIN fetch b.boardPost JOIN fetch b.writer " +
	 * "WHERE b.boardPost.postCode = :postCode AND b.writer.empCode = :empCode")
	 * Optional<BoardPost> findByBoardPostAndWriter(@Param("postCode") Long
	 * postCode, @Param("empCode") Integer empCode);
	 */

	/* 8. 게시글 수정(관리자) => findById 메소드로 조회 후 필드 값 수정하면 변화를 감지하여 update 구문이 생성 되므로 별도의 정의 필요 없음 */
}

	
	

