package org.minisecond.project.board;

import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostForm {
	
	@NotNull(message="잘못된 접근입니다.")
	private int postNo;
	
	@NotEmpty(message="제목은 필수 항목입니다.")
	private String title; // 제목
	
	@NotEmpty(message="내용은 필수 항목입니다.")
	private String content; // 내용
	
	private String password;

}
