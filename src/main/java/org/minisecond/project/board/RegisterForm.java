package org.minisecond.project.board;

import java.sql.Date;

import org.minisecond.project.util.Validate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
	
	@NotEmpty(message="제목은 필수항목입니다.")
	private String title; // 제목
	
	@NotEmpty(message="내용은 필수항목입니다.")
	private String content; // 내용
	
	@NotEmpty(message="게시판을 선택해주세요.")
	private String boardName; // 게시판명
	
	@NotEmpty(message="다시 시도해주세요.")
	@Size(min = 8, max = 10, message=Validate.IDREGEXERRMSG)
	private String id; // 작성자 아이디
	
	@NotEmpty(message="비밀번호는 필수항목입니다.")
	@Size(min = 4, max = 4)
	private String password; // 비밀번호
	
}
