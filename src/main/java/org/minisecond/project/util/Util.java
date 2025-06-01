package org.minisecond.project.util;

public class Util {
		
	public static final int SIZEDEFAULTVAL = 10;
	public static final int PAGENODEFAULTVAL = 1;
	public static final int PARSERDEFAULTVAL= 5;
	
	public static int parseInt(String str, int defaultValue) {
		try {
			if (str != null && str.length() != 0) {
				return Integer.parseInt(str);
			}
		} catch (Exception e) {}
		return defaultValue;
	}	
	
	public static int parseInt(String str, String errMsg) throws Exception {
		try {
			if (str != null && str.length() != 0) {
				return Integer.parseInt(str);
			}
		} catch (Exception e) {
			throw new Exception(errMsg);
		}
		throw new Exception(errMsg); 
	}




}
