package com.management.user.util;

public class Utility {
	public static boolean isNullOrEmpty(String s) {
		boolean flag = false;
		if(s==null || s.isEmpty()) {
			flag=true;
		}
		return flag;
	}
}
