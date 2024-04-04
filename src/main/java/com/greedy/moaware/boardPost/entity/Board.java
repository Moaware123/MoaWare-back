package com.greedy.moaware.boardPost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@DynamicInsert
@Setter
@Getter
@ToString
@Entity
@Table(name="BOARD")
public class Board {

	@Id
	@Column(name="BOARD_CODE")
	private Long boardCode;
	
	@Column(name="BOARD_NAME")
	private String boardName;

	@Column(name="STATUS")
	private String status;

	
	
	
	
	public void update(String status) {
		this.status = status;
	}




	/* Board entity 수정 용도의 메소드를 별도로 정의 */
	public void update(Long boardCode, String boardName, String status) {
		this.boardCode= boardCode;
		this.boardName = boardName;
		this.status = status;
	
	}
	
	
}
