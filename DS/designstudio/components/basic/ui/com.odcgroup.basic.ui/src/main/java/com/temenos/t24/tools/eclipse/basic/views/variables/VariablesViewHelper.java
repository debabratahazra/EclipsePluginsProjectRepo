package com.temenos.t24.tools.eclipse.basic.views.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.help.HelpCache;

/**
 * This helper class assists in variables list building. This class is primarily
 * used to check for keywords and source types.
 * 
 */
public class VariablesViewHelper {

    /** Singleton helper */
    private static VariablesViewHelper helper = new VariablesViewHelper();
    /** JBC source items */
    private final ArrayList<String> sourceDefineWords = new ArrayList<String>(Arrays.asList("PROGRAM", "SUBROUTINE", "CALL",
            "$INSERT", "$INCLUDE", "INCLUDE", "EXECUTE", "PERFORM", "DEFFUN", "GOSUB", "GOTO"));
    /** Keywords list */
    private static List<String> keywords = new ArrayList<String>();

    private VariablesViewHelper() {
    }

    /**
     * Returns the helper instance
     * 
     * @return helper
     */
    public static VariablesViewHelper getInstance() {
        loadKeywordsList();
        return helper;
    }

    /**
     * Loads the keywords into a List
     */
    private static void loadKeywordsList() {
        keywords = HelpCache.getInstance().getKeywordList();
    }

    /**
     * Checks whether the passed word is a Keyword or not.
     * 
     * @param line - word to be checked
     * @return true => if the passed word is a keyword false => otherwise
     * 
     */
    public boolean isKeyword(String word) {
        return keywords.contains(word);
    }

    /**
     * Checks if the passed word is a source type. For example, For example, if
     * the previous word is CALL or GOSUB etc., this return true to skip the
     * current word
     * 
     * @param previousWord
     * @return true if previousWord is either CALL, GOSUB, FUNCTION, $INSERT
     *         etc.,
     */
    public boolean isSource(String previousWord) {
        return sourceDefineWords.contains(previousWord);
    }
}
