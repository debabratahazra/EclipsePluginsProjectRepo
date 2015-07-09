package com.temenos.t24.tools.eclipse.basic.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.editors.util.T24BasicWhitespaceDetector;
import com.temenos.t24.tools.eclipse.basic.help.CallHoverHelpCache;

/**
 * Utility class operates on the document in the viewer
 * 
 * @author ssethupathi
 * 
 */
public class DocumentViewerUtil {

    private static final String documentStart = "<doc>";
    private static final String documentEnd = "</doc>";
    private static CallHoverHelpCache callHelper=new CallHoverHelpCache();

    /**
     * Returns the part of the word in the document till the given offset
     * 
     * @param document document
     * @param offset offset in the viewer
     * @return currentWord currently pointed word
     */
    public static String getCurrentWordPart(IDocument document, int offset) {
        String currentWord = null;
        int wordStartOffset = getWordStartOffset(document, offset);
        try {
            currentWord = document.get(wordStartOffset, offset - wordStartOffset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return currentWord;
    }

    /**
     * Returns the starting position of the current word in the given document
     * at the offset
     * 
     * @param currDocument document
     * @param offset offset in the viewer
     * @return offset starting offset of the current word
     */
    public static int getWordStartOffset(IDocument currDocument, int offset) {
        T24BasicWhitespaceDetector WSDetector = new T24BasicWhitespaceDetector();
        char ch = ' ';
        do {
            offset--;
            if (offset < 0) {
                break;
            }
            try {
                ch = currDocument.getChar(offset);
            } catch (BadLocationException e) {
                e.printStackTrace();
                break;
            }
        } while (!WSDetector.isWhitespace(ch) && !StringUtil.isNonWordChar(ch));
        offset++;
        return offset;
    }

    /**
     * Returns the word preceded to the current word in the given document at
     * the offset
     * 
     * @param IDocument document
     * @param int offset offset in the viewer
     * @return String preceding word
     */
    public static String getPrecedingWord(IDocument document, int offset) {
        T24BasicWhitespaceDetector wsDetector = new T24BasicWhitespaceDetector();
        String precedingWord = "";
        boolean isCurrentWord = true;
        int wordLength = 0;
        try {
            char ch = document.getChar(offset);
            while (wsDetector.isWhitespace(ch) || isCurrentWord) {
                if (wsDetector.isWhitespace(ch) || StringUtil.isNonWordChar(ch)) {
                    isCurrentWord = false;
                }
                offset--;
                if (offset < 0) {
                    break;
                }
                ch = document.getChar(offset);
            }
            while (offset >= 0 && !wsDetector.isWhitespace(ch) && !StringUtil.isNonWordChar(ch)) {
                wordLength++;
                offset--;
                if (offset < 0) {
                    break;
                }
                ch = document.getChar(offset);
            }
            precedingWord = document.get(offset + 1, wordLength);
        } catch (BadLocationException e) {
            return "";
        }
        return precedingWord;
    }

    /**
     * Checks if the passed word is either CALL, INSERT etc., to decide on what
     * follows
     * 
     * @param precedingWord word
     * @return true if the word either of CALL, INSERT etc.,. false otherwise.
     */
    public static boolean isT24BranchingWord(String precedingWord) {
        for (String item : PluginConstants.t24BranchingItems) {
            if (item.equals(precedingWord)) {
                return true;
            }
        }
        return false;
    }

    public static String getCommentDocument(IDocument document, IRegion region) {
        if (document == null) {
            return null;
        }
        int offsetHovered = region.getOffset();
        int lineOffset = 0;
        int lineNo = 0;
        String hoveredWord = "";
        hoveredWord = getWordHovered(document, region.getOffset());
        if ("GOSUB".equals(getPrecedingWord(document, offsetHovered))) {            
            int lableLineNo = getLableLine(document, hoveredWord);
            return checkForDocument(document, lableLineNo - 1);
        }
        if("CALL".equalsIgnoreCase(getPrecedingWord(document, offsetHovered)))
            return callHelper.getHelpForCall(hoveredWord);
        while (offsetHovered > lineOffset) {
            try {
                lineOffset = document.getLineOffset(lineNo++);
            } catch (BadLocationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lineNo = lineNo - 3;
        if (lineNo < 0) {
            return null;
        }
        String line = getLine(document, lineNo + 1);
        if (line != null && !StringUtil.isComment(line)) {
            return checkForDocument(document, lineNo);
        }
        return null;
    }

    private static int getLine(IDocument document) {
        int lineNo = 0;
        String line = null;
        int totalLines = document.getLength();
        while (lineNo < totalLines) {
            line = getLine(document, lineNo);
            if (line == null) {
                return -1;
            } else if (!StringUtil.isComment(line) && (line.contains("SUBROUTINE") || line.contains("PROGRAM"))) {
                return lineNo;
            }
            lineNo++;
        }
        return -1;
    }

    public static String getDocument(IFile file) {
        IDocument document = null;
        try {
            document = getComment(file);
        } catch (CoreException e) {
            return "";
        }
        return getComment(document);
    }

    private static IDocument getComment(IFile file) throws CoreException {
        IDocumentProvider documentProvider = new TextFileDocumentProvider();
        documentProvider.connect(file);
        IDocument document = documentProvider.getDocument(file);
        return document;
    }

    public static String getComment(IDocument document) {
        int lineNo = getLine(document);
        if (lineNo > 0) {
            return checkForDocument(document, lineNo - 1);
        }
        return "";
    }

    public static String checkForDocument(IDocument document, int lineNo) {
        String line = null;
        String comment = "";
        boolean inComment = false;
        boolean finished = false;
        while (lineNo > 0 && !finished) {
            line = getLine(document, lineNo);
            if (line == null || (!StringUtil.isEmpty(line.trim()) && !StringUtil.isComment(line))) {
                break;
            }
            if (!inComment) {
                int documentEndPos = getDocumentEnd(line);
                if (documentEndPos >= 0) {
                    inComment = true;
                    comment = trimComment(line.substring(0, documentEndPos)) + comment;
                }
            } else {
                int documentStartPos = getDocumentStart(line);
                if (documentStartPos >= 0) {
                    finished = true;
                    comment = trimComment(line.substring(documentStartPos + 5)) + comment;
                } else {
                    comment = trimComment(line) + comment;
                }
            }
            lineNo--;
        }
        if (finished) {
            return cleanComment(comment);
        }
        return null;
    }

    public static String cleanComment(String comment) {
     // removing the document start and end tag, happening only in if there
     // any exception and come with any of this tags.
        comment = comment.replaceAll(documentStart, "");
        comment = comment.replaceAll(documentEnd, "");
        comment = comment.replaceAll(">", "");
        comment = comment.replaceAll("\\*", "");        
        return comment;
    }

    private static String trimComment(String commentPart) {
        return commentPart;
    }

    public static String getLine(IDocument document, int lineNo) {
        int lineOffset;
        try {
            lineOffset = document.getLineOffset(lineNo);
            String line = document.get(lineOffset, document.getLineLength(lineNo));
            return line;
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static int getDocumentEnd(String line) {
        if (StringUtil.isComment(line)) {
            return line.indexOf(documentEnd);
        }
        return -1;
    }

    private static int getDocumentStart(String line) {
        if (StringUtil.isComment(line)) {
            return line.indexOf(documentStart);
        }
        return -1;
    }

    public static int getLableLine(IDocument document, String hoveredWord) {
        StringUtil su = new StringUtil();
        String hoveredLabel = hoveredWord + ":";
        int lineNo = document.getNumberOfLines();
        boolean labelFound = false;
        String firstWord = "";
        IRegion lineRegion = null;
        int lineCount = -1;
        int lineStartOffset = 0;
        do {
            try {
                lineCount++;
                lineStartOffset = document.getLineOffset(lineCount);
                lineRegion = document.getLineInformationOfOffset(lineStartOffset);
                String line = document.get(lineRegion.getOffset(), lineRegion.getLength());
                // Checks the given line is a Label
                if (EditorDocumentUtil.isLabel(line)) {
                    firstWord = su.getFirstWordInLine(line);
                    if (firstWord.equals(hoveredLabel)) {
                        labelFound = true;
                    }
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
                return -1;
            }
        } while (!labelFound && lineCount <= lineNo);
        if (labelFound && lineRegion != null) {
            return lineCount;
        }
        return -1;
    }

    private static String getWordHovered(IDocument document, int offset) {
        String docContent = document.get();
        String wordHovered = (new StringUtil()).getWord(docContent, offset);
        return wordHovered;
    }
}
