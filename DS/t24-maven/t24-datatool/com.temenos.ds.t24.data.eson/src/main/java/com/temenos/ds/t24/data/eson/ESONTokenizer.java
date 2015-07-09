package com.temenos.ds.t24.data.eson;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author yandenmatten
 */
public class ESONTokenizer {
	
	private final char[] SINGLE_CHAR_TOKEN = "{}[]:".toCharArray();

	protected String[] tokens;
	private int index = 0;
	
	public ESONTokenizer(String input) {
		tokens = tokenize(input);
	}
	
	public boolean hasNext() {
		return index < tokens.length;
	}
	
	public String nextToken() {
		return tokens[index++];
	}

	public String peekNextToken() {
		if (index < tokens.length) {
			return tokens[index];
		} else {
			return null;
		}
	}

	public String peekSecondNextToken() {
		if (index+1 < tokens.length) {
			return tokens[index+1];
		} else {
			return null;
		}
	}

	protected String[] tokenize(String input) {
		List<String> result = new ArrayList<String>();
		
		StringBuilder currentToken = new StringBuilder();
		for (int i=0; i<input.length(); i++) {
			char c = input.charAt(i);
			if (isWhiteSpace(c)) {
				if (currentToken.length() > 0) {
					result.add(currentToken.toString());
					currentToken = new StringBuilder();
				}
			} else if (c == '\'' || c == '\"') {
				if (currentToken.length() > 0) {
					result.add(currentToken.toString());
					currentToken = new StringBuilder();
				}
				
				// Read the whole string at once
				result.add("" + c);
				int nextQuote = input.indexOf(c, i+1);
				int indexEndOfLineOrFile = indexEndOfLineOrFile(input, i);
				if (nextQuote > indexEndOfLineOrFile) {
					throw new IllegalStateException("The following string doesn't end : " + c + input.substring(i+1, indexEndOfLineOrFile));
				}
				if (nextQuote == -1) {
					throw new IllegalStateException("The following string is invalid: " + c + input.substring(i+1));
				}
				result.add(input.substring(i+1, nextQuote));
				result.add("" + c);
				i = nextQuote;
			} else if (isNewToken(c)) {
				if (currentToken.length() > 0) {
					result.add(currentToken.toString());
					currentToken = new StringBuilder();
				}
				if (c == '/') {
					if (i+1 >= input.length()) {
						throw new IllegalStateException("Illegal character at the end of the ESON");
					} else {
						if (input.charAt(i+1) == '/') {
							// Ignore up to end of line
							i = indexEndOfLineOrFile(input, i);
						} else if (input.charAt(i+1) == '*') {
							// Ignore up to */
							int endOfCommentIndex = input.indexOf("*/", i);
							i = endOfCommentIndex + 1;
						} else {
							throw new IllegalStateException("Illegal character (" + c + ")");
						}
					}
				} else {
					currentToken.append(c);
					result.add(currentToken.toString());
					currentToken = new StringBuilder();
				}
				
			} else {
				currentToken.append(c);
			}
		}
		if (currentToken.length() > 0) {
			result.add(currentToken.toString());
		}
		
		return result.toArray(new String[result.size()]);
	}

	private int indexEndOfLineOrFile(String input, int startIndex) {
		int indexOfN = input.indexOf('\n', startIndex);
		int indexOfR = input.indexOf('\r', startIndex);
		if (indexOfN == -1 && indexOfR == -1) {
			return input.length()-1;
		} else {
			if (indexOfN == -1 && indexOfR != -1) {
				return indexOfR;
			} else if (indexOfR == -1 && indexOfN != -1) {
				return indexOfN;
			} else if (indexOfR < indexOfN) {
				return indexOfR;
			} else {
				return indexOfN;
			}
		}
	}

	private boolean isNewToken(char c) {
		return ArrayUtils.contains(SINGLE_CHAR_TOKEN, c) ||
				c == '/' ||
				c == '\"' ||
				c == '\'';
	}

	private boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	
}
