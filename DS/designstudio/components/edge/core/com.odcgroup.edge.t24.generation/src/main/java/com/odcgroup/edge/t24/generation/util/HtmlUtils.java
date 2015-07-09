package com.odcgroup.edge.t24.generation.util;

import com.acquire.util.StringUtils;


/**
 * <code>HtmlUtils</code> is a placeholder for static HTML-related utility methods
 * 
 * @author Simon Hayes
 */
public abstract class HtmlUtils
{
    public static String convertSpacesToHtmlNonBreakingSpaces(String s, boolean p_doPreTrim, boolean p_doPreCollapseWhitespace)
    {
        if ((s == null) || (s.length() == 0) || (s.indexOf(' ') < 0))
            return s;
        
        String result =
            p_doPreCollapseWhitespace ? StringUtils.collapseSpaces(s, false /* p_replaceNewlinesWidthSpaces */, p_doPreTrim) :
            p_doPreTrim ? s.trim() : s;

        return StringUtils.replaceAll(result, " ", "&nbsp;");
    }
}
