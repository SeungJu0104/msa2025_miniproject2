package org.minisecond.project.util;

import java.util.HashMap;
import java.util.Map;

public class Util {
	
	public static Map<String, Object> createMap() {
		return new HashMap<String, Object> ();
	}
	
	public static Map<String, String> createMapString(){
		return new HashMap<String, String>();
	}
	
	public static Map<String, String> putMsg(String key, String message, Map<String, String> map) {
		map.put(key, message);
		return map;
	}
	
	public static int parseInt(String str, int defaultValue) {
		try {
			if (str != null && str.length() != 0) {
				return Integer.parseInt(str);
			}
		} catch (Exception e) {}
		return defaultValue;
	}	

}
