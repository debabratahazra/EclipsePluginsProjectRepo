package com.odcgroup.workbench.core.helper;


public class StringHelper {

	static public String toPlural(String input) {
		if (input == null)
			return null;
		if (input.endsWith("s"))
			return input + "es";
		if (input.endsWith("y")) {
			return input.substring(0,input.length()-1)+"ies";
		}
		return input + "s";
	}

	static public String withoutExtension(String input) {
		if (input == null)
			return null;
		int pos = input.lastIndexOf('.');
		if (pos < 1) {
			return input;
		} else {
			return input.substring(0, pos);
		}
	}

	static public String toFirstUpper(String input) {
		if (input == null)
			return null;
		if (input.length() == 1)
			return input.toUpperCase();
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	static public String toFirstLower(String input) {
		if (input == null)
			return null;
		if (input.length() == 1)
			return input.toLowerCase();
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

	static public String toXMLString(String input) {
		if (input == null)
			return null;
		return input.replaceAll("&", "&amp;")
		            .replaceAll("\"", "&quot;")
		            .replaceAll("'", "&apos;")
		            .replaceAll("<","&lt;")
		            .replaceAll(">", "&gt;");
	}

	private static void addCharEntity(Integer aIdx, StringBuilder aBuilder) {
		String padding = "";
		if (aIdx <= 9) {
			padding = "00";
		} else if (aIdx <= 99) {
			padding = "0";
		}
		aBuilder.append("&#");
		aBuilder.append(padding);
		aBuilder.append(aIdx.toString());
		aBuilder.append(";");
	}

	public static String toHTMLString(String aText) {
		final StringBuilder result = new StringBuilder();
		char character = 0;
		for (int cx = 0; cx < aText.length(); cx++) {
			character = aText.charAt(cx);
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\t') {
				addCharEntity(9, result);
			} else if (character == '!') {
				addCharEntity(33, result);
			} else if (character == '#') {
				addCharEntity(35, result);
			} else if (character == '$') {
				addCharEntity(36, result);
			} else if (character == '%') {
				addCharEntity(37, result);
			} else if (character == '\'') {
				addCharEntity(39, result);
			} else if (character == '(') {
				addCharEntity(40, result);
			} else if (character == ')') {
				addCharEntity(41, result);
			} else if (character == '*') {
				addCharEntity(42, result);
			} else if (character == '+') {
				addCharEntity(43, result);
			} else if (character == ',') {
				addCharEntity(44, result);
			} else if (character == '-') {
				addCharEntity(45, result);
			} else if (character == '.') {
				addCharEntity(46, result);
			} else if (character == '/') {
				addCharEntity(47, result);
			} else if (character == ':') {
				addCharEntity(58, result);
			} else if (character == ';') {
				addCharEntity(59, result);
			} else if (character == '=') {
				addCharEntity(61, result);
			} else if (character == '?') {
				addCharEntity(63, result);
			} else if (character == '@') {
				addCharEntity(64, result);
			} else if (character == '[') {
				addCharEntity(91, result);
			} else if (character == '\\') {
				addCharEntity(92, result);
			} else if (character == ']') {
				addCharEntity(93, result);
			} else if (character == '^') {
				addCharEntity(94, result);
			} else if (character == '_') {
				addCharEntity(95, result);
			} else if (character == '`') {
				addCharEntity(96, result);
			} else if (character == '{') {
				addCharEntity(123, result);
			} else if (character == '|') {
				addCharEntity(124, result);
			} else if (character == '}') {
				addCharEntity(125, result);
			} else if (character == '~') {
				addCharEntity(126, result);
			} else {
				// the char is not a special one
				result.append(character);
			}
		}
		return result.toString();
	}

}
