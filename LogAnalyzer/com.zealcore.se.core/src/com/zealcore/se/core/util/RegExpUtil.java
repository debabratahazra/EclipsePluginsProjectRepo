/**
 * 
 */
package com.zealcore.se.core.util;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Utility class for regular expression related tasks
 * 
 * @author pasa
 * 
 */
public final class RegExpUtil {

    private RegExpUtil() {}

    /**
     * WS is a string for using white spaces. Defined to "\\s*"
     */
    public static final String WS = "\\s*";

    /**
     * @param line
     *            is the string with the contents that should be matched.
     * @param regExp
     *            is the regular expression that should be used on the line
     * @return the result as a MatchResult that can be used to split the matches
     *         made.
     */
    public static MatchResult scanRegExp(final String line, final String regExp) {
        final Scanner s = new Scanner(line);
        MatchResult result = null;

        s.findInLine(regExp);

        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("Unable to parse: " + line
                    + ", with regular expression" + regExp, e);
        }
        return result;
    }
}
