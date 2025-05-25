package org.minisecond.project.board;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardVO {
	
	private int postNo; // 게시글번호
	private int boardNo; // 게시판번호
	private int memberNo; // 회원번호
	private String title; // 제목
	private String content; // 내용
	private String board; // 게시판명
	private int views; // 조회수
	private String id; // 작성자 아이디
	private Date createdAt; // 작성 일시
	private char deleteYn; // 삭제 여부
	private Date deletedAt; // 삭제 일시
	private int parentNo; // 부모글 번호
	private String password; // 비밀번호
	private String searchValue; // 검색어
	private String boardName; // 게시판	
	
}
