package org.minisecond.project.util;

import java.util.regex.Pattern;

public class Validate {
	
    public static boolean isValid(String regex, String input) {
        return Pattern.matches(regex, input);
    }
}
