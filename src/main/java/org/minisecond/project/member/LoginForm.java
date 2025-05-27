package org.minisecond.project.member;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
	
	@NotEmpty(message = "사용자 ID는 필수 항목입니다.")
	@NotNull
	@Size(min = 8, max = 10)
	private String id;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	@NotNull
	private String password;

}
