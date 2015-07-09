package com.temenos.t24.tools.eclipse.basic.utils;


public class FormatUtil {

    /**
     * Breaks up the String passed into lines
     * @param content - String to be broken up
     * @param eliminateSpaces - true = will trim the spaces 
     * @return String[] - array with lines broken up
     */
    public String[] breakUpText(String content){
        // Split into lines separated by LF (\n)
        String[] res = content.split("\n");
        // Trim each line
        for(int i=0; i<res.length; i++){
            String s = trimText(res[i]);
            res[i] = s;
        }
        return res;        
    }    
    
    /**
     * Removes \r \n characters.
     * Removes extra white spaces to the left and right of the string.
     * e.g. "  TEST   WORD  \r\n" => "TEST  WORD"
     * @param content
     * @return
     */
    public String trimText(String content) {
        if ((content != null) && (content.length() > 0)) {
            content = content.replaceAll("(\\r|\\n)", ""); // remove \r \n 
            content = content.trim();
            return content;
            
        } else {
            return "";
        }
    }        
    
    /**
     * Breaks up the String passed into lines
     * @param content - String to be broken up
     * @param eliminateSpaces - true = will trim the spaces 
     * @return String[] - array with lines broken up
     */
    public String[] breakUpText2(String content){
        String[] res = content.split("\n");
        String s;
        for(int i=0; i<res.length; i++){
            s = formatText(res[i]);
            res[i] = s;
        }
        return res;        
    }
    
    /**
     * Breaks up the String containing comments. It follows different 
     * rules than standard text code
     * @param content - String to be broken up
     * @param eliminateSpaces - true = will trim the spaces 
     * @return String[] - array with lines broken up
     */
    public String[] breakUpComments(String content){
        String[] res = content.split("\n");
        String s;
        for(int i=0; i<res.length; i++){
            s = formatComment(res[i]);
            res[i] = s;
        }
        return res;        
    }
    
    /**
     * If eliminateSpaces = true => Removes all extra whitespaces, \r and \n from a string. 
     * Eg. "TEST       WORD \r\n MAP" will be transformed into "TEST WORD MAP"
     * Else if eliminateSpaces = false => Only removes \r and \n
     * @param content
     * @param eliminateSpaces - true = will trim the spaces 
     * @return String - return the transformed string. If parameter passed
     * is either null or "", it'll return ""
     */
    public String formatContent(String content, int a) {
        StringUtil su = new StringUtil();
        String regEx;
        if ((content != null) && (content.length() > 0)) {
            regEx = "\\s+|\r|\n";
            String[] contentParts = content.split(regEx);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < contentParts.length; i++) {
                buffer.append(su.trimLeft(contentParts[i])).append(" ");
            }

            if (buffer.length() > 0)
                buffer.delete(buffer.length() - 1, buffer.length());
            String res = buffer.toString();
            // trim the initial space if needed
            if (res.length() > 0 && res.charAt(0) == ' ')
                res = res.substring(1);
            return res;
        } else {
            return "";
        }
    }

    
    public String formatText(String content) {
        StringUtil su = new StringUtil();
        if ((content != null) && (content.length() > 0)) {
            content = content.replaceAll("(\\r|\\n)", ""); // remove \r \n 
            content = su.trimLeft(content);
            int length1 = content.length();
            content = content.trim();
            int length2 = content.length();
            content = su.removeExtraSpaces(content);
            for(int i=0; i<(length1-length2); i++){
                content = content + " ";
            }
            return content;
            
        } else {
            return "";
        }
    }    

    
    /**
     * Remove leading spaces, keep internal spaces, remove \r \n
     * e.g. "    REM   TEST \n REM TEST2" will be transformed into
     * "REM   TEST REM TEST2"
     * @param content
     * @return
     */
    public String formatComment(String comment) {
        String result; 
        // trim leading and trailing spaces if needed
        StringUtil su = new StringUtil();
        result = su.trim(comment);
        return result;
    }    
    
    /**
     * Checks whether there is an action after the THEN statement.
     * An action is defined as a piece of code which is not within a comment.
     * e.g.1: IF COND THEN => false
     * e.g.2: IF COND THEN CALL THIS => true
     * e.g.3: IF COND1 THEN IF COND2 THEN => false
     * @param line
     * @return boolean
     */
    public boolean thereIsActionAfterThen(String line){
        // Find the first occurrence of THEN (note there might be more than one)
        int i = line.indexOf("THEN",0);
        
        // Get any string after it
        String action = line.substring(i+"THEN".length());
        
        if(StringUtil.isEmpty(action)){
            // Line is empty => not an action
            return false;
        } else {
            if(StringUtil.isComment(action)){
                // Line is a comment => not an action
                return false;
            } else {
                // Line is neither emtpy nor a comment
                // It can be another IF ... THEN block
                i = action.indexOf("IF",0);
                int j = action.indexOf("THEN",i+2);
                if(i>0 && j>0){
                    return thereIsActionAfterThen(action);
                } else {
                    // There is a true action after the THEN
                    return true;
                }
            }
        }
    }
    
    /**
     * Calculates the total indentation string based on the indentation level.
     * 
     * @param indentationString - string with spaces representing the basic amount of indentation. e.g. "   "
     * @param indentationLevel - will indicate how many times indentationString is to be applied
     * @param addDefaultIndentation - true = a default pre-indentation is
     *            included, false = it is not
     * @param defaultIndOnly - true = only the default indentation at front will
     *            be added
     * @return String - contains the total indentation to be added to a string
     */
    public String getTotalIndentation(String indentationString, int indentationLevel, 
                                      boolean addDefaultIndentation, boolean defaultIndOnly){
        String result = "";
        // no negative values allowed
        if(indentationLevel<0){
            indentationLevel = 0;
        }
        if(addDefaultIndentation)
            result += indentationString;
        
        if (!defaultIndOnly) {
            for (int i = 0; i < indentationLevel; i++) {
                result += indentationString;
            }
        }
        
        //System.out.println("\""+result+"\" length: "+result.length());
        return result; 
    }      
    
}
