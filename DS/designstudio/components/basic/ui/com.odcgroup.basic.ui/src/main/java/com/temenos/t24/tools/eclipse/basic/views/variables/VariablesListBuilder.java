package com.temenos.t24.tools.eclipse.basic.views.variables;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.T24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * This class is responsible for extracting the JBC variables from the given
 * IDocument. It parses each line to get the variables. This class constructs an
 * ArrayList of IT24ViewItem objects using which the ViewManager would build the
 * view.
 * 
 * @author ssethupathi
 * 
 */
public class VariablesListBuilder {

    /** Singleton builder */
    private static VariablesListBuilder variablesListBuilder = new VariablesListBuilder();
    /** Helper to assist in the process */
    private VariablesViewHelper helper = VariablesViewHelper.getInstance();
    /** StringUtil used in String manipulations */
    private StringUtil su = new StringUtil();
    /** Variables list to be built and returned */
    private ArrayList<IT24ViewItem> variablesList = new ArrayList<IT24ViewItem>();
    /** Variables used in content assist */
    private ArrayList<String> variables = new ArrayList<String>();
    /** Indicates whether the scan is for variables and their positions */
    private boolean variablesOnly;
    /** Starting sequence of the variable */
    private String startSequence = "";

    private VariablesListBuilder() {
    }

    /**
     * Returns the singleton instance
     * 
     * @return Instance of VariableListBuilder
     */
    public static VariablesListBuilder getInstance() {
        return variablesListBuilder;
    }

    /**
     * Extracts the variables from the IDocument passed and returns IT24ViewItem
     * objects constructed from the variables and their positions
     * 
     * @param document Document
     * @return Variables List
     */
    public ArrayList<IT24ViewItem> getVariableViewItems(IDocument document) {
        variablesOnly = false;
        variablesList.clear();
        scanDocument(document);
        return variablesList;
    }

    /**
     * Extracts and returns the variables with the startSequence from the
     * IDocument passed
     * 
     * @param document Document
     * @param startSequence of the variable
     * @return variables in an ArrayList
     */
    public ArrayList<String> getVariables(IDocument document, String startSequence) {
        this.startSequence = startSequence;
        variablesOnly = true;
        variables.clear();
        scanDocument(document);
        return variables;
    }

    /**
     * Scans the document, for each line, extracts the variables
     * 
     * @param document
     */
    private void scanDocument(IDocument document) {
        if (document == null) {
            return;
        }
        String word = null;
        String previousWord = null;
        int noOfLines = document.getNumberOfLines();
        int lineOffset = 0;
        int lineLength = 0;
        int offset = -1;
        String line = null;
        try {
            for (int lineNo = 0; lineNo < noOfLines; lineNo++) {
                lineOffset = document.getLineOffset(lineNo);
                lineLength = document.getLineLength(lineNo);
                line = document.get(lineOffset, lineLength);
                if (canSkipLine(line)) {
                    continue;
                }
                line = StringUtil.removeJbcNonwordChar(StringUtil.removeJbcFunction(StringUtil.removeJbcLiteral(StringUtil
                        .removeJbcCommentPart(line))));
                offset = su.getBeginningOfNextWord(line, -1);
                while (offset != -1) {
                    word = su.getWord(line, offset);
                    if (isVariable(previousWord, word)) {
                        buildVariables(lineOffset + offset, word);
                    }
                    previousWord = word;
                    offset = su.getBeginningOfNextWord(line, offset);
                }
                previousWord = null;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the passed line could be skipped from further parsing. Ex.,
     * Comments, empty lines etc.,
     * 
     * @param line
     * @return true if the line could be skipped. false otherwise.
     */
    private boolean canSkipLine(String line) {
        if (StringUtil.isComment(line) || StringUtil.isEmpty(line) || StringUtil.isLabel(line)) {
            return true;
        }
        return false;
    }

    /**
     * Builds the extracted variables into the List of View Items or List of
     * Strings for the content assist proposals
     * 
     * @param offset of the variable
     * @param word variable
     */
    private void buildVariables(int offset, String word) {
        if (!variablesOnly) {
            variablesList.add(new T24ViewItem(word, new Position(offset, word.length()), T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM));
            return;
        }
        if (!variables.contains(word) && !word.equals(startSequence) && word.startsWith(startSequence)) {
            variables.add(word);
        }
    }

    /**
     * Checks if the passed word is a variable.
     * 
     * @param word
     * @return true if it is a variable. false otherwise.
     */
    private boolean isVariable(String previousWord, String word) {
        if (word == null || word.length() <= 0 || StringUtil.isJbcLiteral(word) || helper.isKeyword(word)
                || helper.isSource(previousWord)) {
            return false;
        }
        return true;
    }
}
