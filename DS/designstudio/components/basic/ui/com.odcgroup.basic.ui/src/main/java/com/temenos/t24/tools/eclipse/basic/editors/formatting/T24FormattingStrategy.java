package com.temenos.t24.tools.eclipse.basic.editors.formatting;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FormatUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Simple implementation of IFormattingStrategy
 */
public class T24FormattingStrategy {

    public static final String lineSeparator = System.getProperty("line.separator");

    public T24FormattingStrategy() {
    }

    public void formatterStarts(String initialIndentation) {
    }

    /**
     * Resets flags used during partitioning to their original values. It also
     * resets the indentation Level to 0.
     */
    public static void resetFormattingStrategy() {
    }

    /**
     * Formats the passed string by adding indentations following established
     * formatting rules.
     * 
     * @param originalText - String containing the text to be formatted.
     * @return String with formatted text.
     */
    public String format(String originalText) {
        // Define the indentation level, initially set to 0 (zero)
        int indLevel = 0;
        // SYSTEM_INDENTATION refers to the indentation set up the by user
        // through preferences page.
        String SYSTEM_INDENTATION = FormattingController.getIndentation();
        // LocalIndent is the indentation applied to each particular line
        String localIndent = "";
        // An indented line consists of several items:
        // [DefaultIndentation] + Indentation + Line
        // where:
        // DefaultIndentation = is a pre-indentation that is intended to by
        // applied to all lines
        // Indentation = is based on the indentation level
        // Line = is the line of code
        boolean addDefaultIndentation = true;
        boolean onlyDefaultIndentation = false;
        FormatUtil fu = new FormatUtil();
        StringUtil su = new StringUtil();
        if ("".equals(originalText) || originalText == null) {
            return "";
        }
        // Break up the partition into separate lines (each one separated by new
        // line)
        String[] lines = fu.breakUpText(originalText);
        // Iterate through the lines
        String line = "";
        StringBuffer sb = new StringBuffer();
        for (int lineIdx = 0; lineIdx < lines.length; lineIdx++) {
            // get new line
            line = lines[lineIdx];
            // The minimum indentation level allowed is 0. It is possible to
            // reach negative
            // indLevel in some malformed cases, e.g. a single END statement
            // lying around, can
            // cause indLevel to be <0.
            if (indLevel < 0)
                indLevel = 0;
            if (StringUtil.isEmpty(line)) {
                // This is an empty line (or partition)
                line = line.trim();
                localIndent = "";
            } else {
                localIndent = SYSTEM_INDENTATION;
                onlyDefaultIndentation = false;
            }
            // MAIN FORMATTING CHECK BLOCK
            // IF MORE SCENARIOS NEED TO BE ADDED, BE CAREFUL:
            // - THE ORDER IN WHICH SCENARIOS APPEAR IS IMPORTANT
            if (EditorDocumentUtil.isLineARegion(line)) {
                // e.g. *** <region name= AA>
                // *** <desc>FF</desc>
                // Put line right at the beginning of left hand side
                int regionIndLevel = 0;
                line = fu.getTotalIndentation(localIndent, regionIndLevel, false, false) + line;
            } else if (StringUtil.isComment(line)) {
                // e.g. "REM comment line" or "* comment line" or "*; comment
                // line"
                if (indLevel == 0) {
                    // Put line right at the beginning of left hand side
                    line = fu.getTotalIndentation(localIndent, indLevel, false, false) + line;
                } else {
                    // follow indentation level
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                }
            } else if (su.getFirstWordInLine(line).equals("IF")) {
                if (su.containsWholeWordNotInQuotes(line, "THEN") && su.containsWholeWordNotInQuotes(line, "END")) {
                    // IF - condition - THEN - action - END (*** ALL IN THE SAME
                    // LINE ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                } else if (su.containsWholeWordNotInQuotes(line, "THEN") && fu.thereIsActionAfterThen(line)) {
                    // There is an action after the THEN => no need for changing
                    // the indentation level (indLevel)
                    // IF - condition - THEN - action (*** ALL IN THE SAME LINE
                    // ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                } else if (su.containsWholeWordNotInQuotes(line, "ELSE")) {
                 // IF - condition - ELSE Condition (*** ALL IN THE SAME Line ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    if(line.endsWith("ELSE")) {
                        indLevel++;
                    }
                } 
                else {
                    // It is a standard IF condition THEN => increase the
                    // indentation level
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    indLevel++;
                }
            } else if (su.getFirstWordInLine(line).equals("LOCATE")) {
                if ((su.containsWholeWordNotInQuotes(line, "THEN") || su.containsWholeWordNotInQuotes(line, "ELSE"))){
                        //&& su.containsWholeWordNotInQuotes(line, "END")) {
                    // LOCATE - expression - IN expression - SETTING var
                    // THEN|ELSE statement END (*** ALL IN THE SAME LINE ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                } else {
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    indLevel++;
                }
            } else if (su.getFirstWordInLine(line).equals("FOR")) {
                if (su.containsWholeWordNotInQuotes(line, "NEXT")) {
                    // FOR - action - NEXT (*** ALL IN THE SAME LINE ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                } else {
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    indLevel++;
                }
            } else if (su.getFirstWordInLine(line).equals("LOOP")) {
                if ((su.containsWholeWordNotInQuotes(line, "UNTIL") || su.containsWholeWordNotInQuotes(line, "WHILE"))
                        && su.containsWholeWordNotInQuotes(line, "REPEAT")) {
                    // LOOP - action - (UNTIL/WHILE) - action - REPEAT (*** ALL
                    // IN THE SAME LINE ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                } else if (su.containsWholeWordNotInQuotes(line, "UNTIL") || su.containsWholeWordNotInQuotes(line, "WHILE")) {
                    // LOOP - action - (UNTIL/WHILE) - action (*** ALL IN THE
                    // SAME LINE ***)
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    indLevel++;
                } else {
                    // LOOP - action
                    line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                    indLevel++;
                }
            } else if ((su.getFirstWordInLine(line).equals("BEGIN") && su.containsWholeWordNotInQuotes(line, "CASE"))) {
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel += 2;
            } else if (su.containsWholeWordNotInQuotes(line, "THEN") && fu.thereIsActionAfterThen(line)) {
                // This rule is added here in case we have a partition that
                // follows a literal, in which case it'll only
                // contains THEN + action.
                // Since there must have been an IF before, which incremented
                // the indLevel. It is necessary to decrement it again.
                // IF "literal" THEN - action (*** ALL IN THE SAME LINE ***)
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel--;
            } else if ((su.containsWholeWordNotInQuotes(line, "END") && su.containsWholeWordNotInQuotes(line, "CASE"))) {
                indLevel -= 2;
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
            } else if ((su.containsWholeWordNotInQuotes(line, "WHILE") && (su.containsWholeWordNotInQuotes(line, "REPEAT")))) {
                // e.g. WHILE READNEXT ID FROM MY LIST DO REPEAT
                indLevel--;
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
            } else if ((su.containsWholeWordNotInQuotes(line, "END") && su.containsWholeWordNotInQuotes(line, "ELSE"))
                    || (su.containsWholeWordNotInQuotes(line, "UNTIL")) || (su.containsWholeWordNotInQuotes(line, "WHILE"))
                    || (su.containsWholeWordNotInQuotes(line, "CASE"))) {
                indLevel--;
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel++;
            } else if (su.containsWholeWordNotInQuotes(line, "END") || su.containsWholeWordNotInQuotes(line, "REPEAT")
                    || su.containsWholeWordNotInQuotes(line, "NEXT")) {
                indLevel--;
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
            } else if (su.containsWholeWordNotInQuotes(line, "$INSERT") || su.containsWholeWordNotInQuotes(line, "$INCLUDE")
                    || su.containsWholeWordNotInQuotes(line, "INCLUDE")) {
                line = fu.getTotalIndentation(localIndent, indLevel + 1, false, false) + line;
            } else if (EditorDocumentUtil.isLabel(line)) {
                // MY.LABEL: => no indentation at all
                // indLevel = 0;
                int labelIndentationLevel = 0;
                line = fu.getTotalIndentation(localIndent, labelIndentationLevel, false, false) + line;
            } else if ((su.containsWholeWordNotInQuotes(line, "OPEN") || su.containsWholeWordNotInQuotes(line, "OPENSEQ"))
                    && (su.containsWholeWordNotInQuotes(line, "ELSE") || su.containsWholeWordNotInQuotes(line, "THEN"))) {
                // e.g. OPEN FN.FILE TO F.FILE ELSE => like a standard IF, just
                // increase the indentation level by 1.
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel++;
            } else if ((su.containsWholeWordNotInQuotes(line, "READ") || su.containsWholeWordNotInQuotes(line, "READNEXT"))
                    && (su.containsWholeWordNotInQuotes(line, "ELSE") || su.containsWholeWordNotInQuotes(line, "THEN"))) {
                // e.g. READNEXT ID ELSE or READ R.BRANCH.COMPANY FROM
                // F.COMPANY, R.NEW(ST.ICP.COMPANY)<1,AV> THEN
                // => like a standard IF, just increase the indentation level by
                // 1.
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel++;
//            } else if ((su.containsWholeWordNotInQuotes(line, "RETURN"))){ 
//                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
//                indLevel--;
            } else if (su.getFirstWordInLine(line).equals("FINDSTR") 
                    || su.getFirstWordInLine(line).equals("READLIST")
                    || su.getFirstWordInLine(line).equals("READU")
                    || su.getFirstWordInLine(line).equals("WRITE")
                    || su.getFirstWordInLine(line).equals("DELETE")) {                
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
                indLevel++;
            }
            else {
                // it is a standard line (just follow current indentation level)
                line = fu.getTotalIndentation(localIndent, indLevel, addDefaultIndentation, onlyDefaultIndentation) + line;
            }
            sb.append(line + lineSeparator);
        }
        String result = sb.toString();
        return result;
    }

    public void formatterStops() {
    }
}
