package org.minisecond.project.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validate {
	
	public static final String IDREGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,10}$";
	public static final String PWREGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,12}$";
	public static final String PHONEREGEX = "^010-\\d{4}-\\d{4}$";
	public static final String POSTPWREGEX = "/^\\d{4}$/";
	public static final String IDREGEXERRMSG = "아이디는 8~10자 사이이며, 영문자와 숫자만 포함해야 합니다.";
	public static final String PWREGEXERRMSG = "비밀번호는 8~12자 사이이며, 영문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.";
	public static final String PHONEREGEXERRMSG = "전화번호는 010-0000-0000 형식이어야 합니다.";
	public static final String IDPWEMPTYERRMSG = "아이디 또는 비밀번호를 확인해주세요.";
	public static final String LOGINCNTERRMSG = "로그인을 5회 이상 실패하셨습니다. 관리자에게 문의하세요.";
	public static final String BIRTHDATEERRMSG = "생년월일을 다시 선택해주세요.";
	public static final String POSTPWERRMSG = "비밀번호는 숫자 4자리입니다.";
	
    public static boolean isValid(String regex, String input) {
        return Pattern.matches(regex, input);
    }
    
    public static boolean isValidBirthDate(LocalDate birthDate) {
    	try {
        	LocalDate minDate = LocalDate.of(1950, 1, 1);
        	LocalDate maxDate = LocalDate.now();
        	return !birthDate.isBefore(minDate) && !birthDate.isAfter(maxDate);
    	} catch(DateTimeParseException e) {
    		return false;
    	}

    }
}
