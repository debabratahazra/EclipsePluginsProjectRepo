package com.odcgroup.mdf.validation.validator;

public class MdfValidatorUtil {

	public static boolean isNullOrEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}
}
