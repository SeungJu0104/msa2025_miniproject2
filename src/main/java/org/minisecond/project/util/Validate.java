package org.minisecond.project.util;

import java.util.regex.Pattern;

public class Validate {
	
	public static final String IDREGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,10}$";
	public static final String PWREGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,12}$";
	
    public static boolean isValid(String regex, String input) {
        return Pattern.matches(regex, input);
    }
}
