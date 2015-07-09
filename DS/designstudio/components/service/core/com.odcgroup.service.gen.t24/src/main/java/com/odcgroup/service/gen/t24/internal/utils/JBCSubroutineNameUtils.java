package com.odcgroup.service.gen.t24.internal.utils;

public class JBCSubroutineNameUtils {
	public static String toTAFJClassName(String subroutineName) {
		StringBuilder tafjName = new StringBuilder(); 
		int noLowerChars = 0;
		for ( int i = 0; i < subroutineName.length(); i++ ) {
			char c = convertInvalidChar(subroutineName.charAt(i));
			tafjName.append(c);
			if ( isLowerCaseChar(c)) {
			    noLowerChars++;
			}
		}

		if ( noLowerChars > 0 ) {
			tafjName.append('_');
			tafjName.append(noLowerChars);
		}

		return tafjName.append("_cl").toString();
	}
	
	private static boolean isLowerCaseChar(char c) {
		// is c a lower case character?
		return (c >= 'a') && (c <= 'z');
	}
	
	private static char convertInvalidChar(char c) {
		if (c == '.') {
			return '_';
		} else {
			return c;
		}
	}
	
	public static String toTAFCFunctionName(String subroutineName) {
		return "JBC_" 
			+ subroutineName.replace(".", "_2E");
	}
	
	
	
}
