package com.temenos.t24.tools.eclipse.basic.document.capture;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * This class is responsible for enforcing only XML friendly and valid HTML tags
 * are captured as part of the component document creation process.
 * 
 */
public class DocumentHtmlValidator {

    /** Buffer to hold the character of the passed in text */
    private StringBuilder buffer;
    /** Stack is used to manage the opening and closing of tags */
    private Stack<String> tagStack;
    /** Number of characters in the text */
    private int length;
    /** Indicates the position of the currently processed character */
    private int pointer;
    /** list of supported html tags */
    private static final List<String> SUPPORTED_TAGS = Arrays.asList(new String[] { "b", "p", "li", "br" });
    private String allowedChars = "[A-Za-z0-9\\.\\s<>\\\\/\\\n\\-\\_\\[\\]\\(\\)\\;\\:\\?\\+\\=\\!\\&\\*\\@\\{\\}\\'\"\\$\\,]*";

    /**
     * Parses the passed-in text to confirm there is no violation in terms of
     * XML and HTML tags.
     * <p>
     * If no invalid character or invalid tag found it keeps quite. Since it
     * doesn't return any value, the calling method has to check for {@code
     * InvalidDocumentException} to understand the error.
     * 
     * @param str string to be parsed
     * @throws InvalidDocumentException
     */
    public void parse(String str) throws InvalidDocumentException {
        if (str == null) {
            throw new InvalidDocumentException("Document is null");
        }
        initParser(str);
        // if (buffer.indexOf("<") == -1 && buffer.indexOf(">") == -1) {
        // // No HTML tags found
        // return;
        // }
        length = buffer.length();
        while (pointer < length) {
            char ch = buffer.charAt(pointer);
            if (ch == '<') {
                // starting of a tag encountered, check for valid html tags
                checkHtmlTags();
            } else {
                // check for valid characters
                checkInvalidChar(ch);
                pointer++;
            }
        }
        if (!tagStack.isEmpty()) {
            // tag stack is not empty - some tags did not match
            StringBuilder tags = new StringBuilder();
            while (!tagStack.isEmpty()) {
                tags.append("<");
                tags.append(tagStack.pop());
                tags.append(">");
                tags.append(" ");
            }
            throw new InvalidDocumentException("Tags not closed properly. Unclosed tags " + tags.toString());
        }
    }

    // Checks for invalid character
    private void checkInvalidChar(char ch) throws InvalidDocumentException {
        if (ch == '>') {
            throw new InvalidDocumentException("Invalid closing tag > found at line number " + getLineNumber() + " at position "
                    + pointer);
        }
        if (Character.toString(ch).matches(allowedChars)) {
            // do nothing
        } else {
            throw new InvalidDocumentException("Invalid character " + ch + " found at line number " + getLineNumber()
                    + " at position " + pointer);
        }
    }

    // initialises parser for a new parsing
    private void initParser(String str) {
        buffer = new StringBuilder(str);
        length = 0;
        pointer = 0;
        tagStack = new Stack<String>();
    }

    // Checks for html tags - only valid, matched and supported tags can survive
    private void checkHtmlTags() throws InvalidDocumentException {
        StringBuilder current = new StringBuilder();
        if (buffer.charAt(pointer + 1) == '/') {
            // A closing tag found
            int _pointer = pointer + 2;
            while (buffer.charAt(_pointer) != '>') {
                current.append(buffer.charAt(_pointer++));
                if (_pointer >= length) {
                    // this closing tag did not end until end of the text
                    throw new InvalidDocumentException("Invalid start tag < at line number " + getLineNumber() + " at position "
                            + pointer);
                }
            }
            // pop the opening tag to see if matches with this closing tag
            match(current.toString());
            pointer = _pointer + 1;
        } else {
            // An opening tag found
            int _pointer = pointer + 1;
            while (buffer.charAt(_pointer) != '>') {
                current.append(buffer.charAt(_pointer++));
                if (_pointer >= length) {
                    // this opening tag did not end until end of text
                    throw new InvalidDocumentException("Invalid start tag < at line number " + getLineNumber() + " at position "
                            + pointer);
                }
            }
            if (!current.toString().equals("br/")) {
                // push the opening tag (if not a special br tag) into the stack
                tagStack.push(current.toString());
            }
            pointer = _pointer + 1;
        }
    }

    // Checks for matching tags by popping from the tag stack
    private void match(String current) throws InvalidDocumentException {
        try {
            String popped = tagStack.pop();
            if (!popped.equals(current)) {
                // opening tag doesn't match with the closing tag
                throw new InvalidDocumentException("Invalid closing tag. expected <" + popped + "> found <" + current
                        + "> at line number " + getLineNumber() + " at position " + pointer);
            }
            if (!SUPPORTED_TAGS.contains(current)) {
                // the tag is not supported
                throw new InvalidDocumentException("Unsupported tag found  <" + current + "> at line number " + getLineNumber()
                        + " at position " + pointer);
            }
        } catch (EmptyStackException e) {
            // no opening tag found for this closing tag
            throw new InvalidDocumentException("No opening tag found for </" + current + "> at line number " + getLineNumber()
                    + " at position " + pointer);
        }
    }

    private DocumentHtmlValidator() {
    }

    /**
     * Creates a new validator instance.
     * 
     * @return validator
     */
    public static DocumentHtmlValidator newInstance() {
        return new DocumentHtmlValidator();
    }

    private int getLineNumber() {
        String[] lines = buffer.toString().split("\r\n");
        int count = 0;
        for (String str : lines) {
            count++;
            if (pointer <= str.length()) {
                pointer++;
                ;
                break;
            }
            pointer -= (str.length() + 2);// 2 -FOR CR & LF in each
            // line
        }
        return count;
    }
}
