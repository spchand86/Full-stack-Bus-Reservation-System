package com.yellowbus.springmvc.util;

public class RandomUtil {
	public String capitaliseFirstLetter(String str) {
		if (str == null) {
			return null;
		} else if (str.equals("")) {
			return "";
		} else {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
			for (int i = 1; i < str.length(); i++) {
				if (str.charAt(i - 1) == ' ' && str.charAt(i) != ' ') {
					str = str.substring(0, i)
							+ str.substring(i, i + 1).toUpperCase()
							+ str.substring(i + 1);
				}
			}
			return str;
		}
	}
	public String capitalizeFirstLetter(String s)
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
