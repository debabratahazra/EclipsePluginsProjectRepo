package com.temenos.t24.tools.eclipse.basic.editors.util;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class T24BasicWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
