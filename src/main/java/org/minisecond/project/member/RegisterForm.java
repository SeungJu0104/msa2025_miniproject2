package org.minisecond.project.member;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
	
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "이름은 필수 항목입니다.")
    @Size(max = 30, message = "이름은 30자 이내여야 합니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수 항목입니다.")
    private String phoneNumber;

    @NotEmpty(message = "우편번호는 필수 항목입니다.")
    @Size(min = 5, max = 5, message = "우편번호는 5자리 숫자여야 합니다.")
    private String postCode;

    @NotEmpty(message = "주소는 필수 항목입니다.")
    private String address;

    @NotEmpty(message = "상세 주소는 필수 항목입니다.")
    private String detailAddress;	
    
    @NotNull(message = "생년월일은 필수 항목입니다.")
    private LocalDate birthDate;
    // LocalDate와 같은 날짜 타입에는 NotEmpty가 아닌 NotNull을 사용해야한다.

}
