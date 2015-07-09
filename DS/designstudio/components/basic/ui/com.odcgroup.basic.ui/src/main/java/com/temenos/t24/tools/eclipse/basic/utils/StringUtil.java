package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;

/**
 * Utilities for handling strings
 */
public class StringUtil {

    /**
     * Checks whether the passed line is a comment. Either the keyword REM, the
     * asterisk (*) or (; *) at the beginning of the line: >REM comment line >*
     * comment line >;* comment line
     * 
     * @param line - String to be checked
     * @return true/false
     */
    public static synchronized boolean isComment(String line) {
        /** Patterns */
        String str1 = "(^\\s*REM\\s+)"; // e.g. "REM comment"
        String str2 = "(^\\s*[\\*])"; // e.g. " * comment"
        String str3 = "(^\\s*;[\\s*\\*])"; // e.g. "; * comment"
        Pattern ptrn = Pattern.compile(str1);
        Matcher matcher = ptrn.matcher(line);
        if (matcher.find()) {
            return true;
        }
        ptrn = Pattern.compile(str2);
        matcher = ptrn.matcher(line);
        if (matcher.find()) {
            return true;
        }
        ptrn = Pattern.compile(str3);
        matcher = ptrn.matcher(line);
        if (matcher.find()) {
            return true;
        }
        /** This point is reached, then no match found, so it's not a comment */
        return false;
    }

    /**
     * Finds out if the offset passed is within quotes ("" or '') inside the
     * line, i.e. whether it is a literal.
     * 
     * @param line - whole line within a document. It is important that this is
     *            a line, not the whole document contents.
     * @param offset - offset within the line, the line starts with index 0.
     * @return true = it is within quotes, false = it is NOT within quotes
     */
    public boolean isWithinQuotes(String line, int offset) {
        String leftLine = line.substring(0, offset);
        String rightLine = line.substring(offset + 1, line.length());
        // The offset is within quotes if there are odd number of quotes to the
        // left
        // and to the right of it.
        // count number of quotes to the left
        int lcnt = 0, rcnt = 0;
        lcnt = noOfCharsInString(leftLine, '\"');
        lcnt += noOfCharsInString(leftLine, '\'');
        rcnt = noOfCharsInString(rightLine, '\"');
        rcnt += noOfCharsInString(rightLine, '\'');
        if (!((lcnt % 2) == 0) && !((rcnt % 2) == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the number of times a given char appears in the passed string.
     * 
     * @param line
     * @param ch
     * @return
     */
    private int noOfCharsInString(String line, char ch) {
        int cnt = 0;
        int idx = -1;
        while ((idx = line.indexOf(ch, ++idx)) != -1) {
            cnt++;
        }
        return cnt;
    }

    /**
     * @param name - basic module name (e.g. ACCOUNT.b)
     * @return true if prefix is ".b" or ".B", false otherwise
     */
    public boolean isBasicFile(String filename) {
        String basicPrefix = ".B";
        if (filename != null && (filename.length() > basicPrefix.length())) {
            String filePrefix = filename.substring(filename.length() - basicPrefix.length());
            if (filePrefix.toUpperCase().equals(basicPrefix)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param name - test file name
     * @return true if file name begins with Test_ and also a basic file , false
     *         otherwise
     */
    public boolean isTUnitTestFile(String filename) {
        String basicPrefix = ".B";
        if (filename != null && (filename.length() > basicPrefix.length())) {
            String filePrefix = filename.substring(filename.length() - basicPrefix.length());
            String fileNameWithoutExtension = filename.substring(0, (filename.length() - basicPrefix.length()));
            if (filePrefix.toUpperCase().equals(basicPrefix) && fileNameWithoutExtension.contains("Test")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Removes the trailing ".b" extension of the passed filename
     * 
     * @param basicFilenameWithPrefix - e.g. ACCOUNT.MOUDULE.b
     * @return filename without trailing .b e.g.: "MY.MODULE.b" return
     *         "MY.MODULE" e.g.: "MY.MODULE.b.b" return "MY.MODULE.b"
     */
    public static String removeBasicExtension(String basicFilenameWithPrefix) {
        String prefix = PluginConstants.LOCAL_BASIC_DOT_PREFIX;
        if (basicFilenameWithPrefix != null && basicFilenameWithPrefix.length() > prefix.length()) {
            // The filename is large enough
            if (basicFilenameWithPrefix.substring(basicFilenameWithPrefix.length() - prefix.length()).toUpperCase().equals(
                    prefix.toUpperCase())) {
                // The filename ends with .b(or .B)
                return basicFilenameWithPrefix.substring(0, basicFilenameWithPrefix.length() - prefix.length());
            } else {
                // The filename doesn't end with .b
                return basicFilenameWithPrefix;
            }
        } else {
            // The filename is not big enough, or is null
            return basicFilenameWithPrefix;
        }
    }

    /**
     * @param is
     * @return String from the InputStream passed.
     */
    public String getStringFromInputStream(InputStream is) {
        StringBuffer sb = new StringBuffer();
        Reader in = null;
        try {
            in = new InputStreamReader(is);
            int c;
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            Closeables.closeQuietly(in);
        }
        return sb.toString();
    }

    public InputStream getInputStreamFromString(String s) {
        InputStream is = new ByteArrayInputStream(s.getBytes());
        return is;
    }

    /**
     * Compares two version numbers. Versions are expected to have the following
     * formats: either X.Y.Z (e.g. 1.2.5) or X.Y.Z.vddMMyyHHmmss (e.g.
     * 1.2.5.v121007011024)
     * 
     * @param newVer - New version string (X.Y.Z or X.Y.Z.vddMMyyHHmmss)
     * @param oldVer - Old version string (X.Y.Z or X.Y.Z.vddMMyyHHmmss)
     * @return true if newVer is newer than oldVer, false otherwise.
     */
    public boolean isNewThan(String newVer, String oldVer) {
        // X.Y.Z.vddMMyyHHmmss (majorVersion.vDateVersion)
        String newMajorVerString = getMajorVersion(newVer);
        String oldMajorVerString = getMajorVersion(oldVer);
        if (!newMajorVerString.equals(oldMajorVerString)) {
            return isMajorVersionGreaterThanMinor(newMajorVerString, oldMajorVerString);
        } else {
            // Major versions (X.Y.Z) are identical.
            // Try to find out if there are date versions (vddMMyyHHmmss)
            String newDateVerString = "";
            String oldDateVerString = "";
            if (newVer.length() > newMajorVerString.length()) {
                newDateVerString = newVer.substring(newMajorVerString.length() + 2);
            }
            if (oldVer.length() > oldMajorVerString.length()) {
                oldDateVerString = oldVer.substring(oldMajorVerString.length() + 2);
            }
            if ((!"".equals(newDateVerString)) && (!"".equals(oldDateVerString))) {
                DateFormat sf = new SimpleDateFormat("ddMMyyHHmmss");
                boolean dateVerIsNewer = false;
                try {
                    Date dateNew = sf.parse(newDateVerString);
                    Date dateOld = sf.parse(oldDateVerString);
                    dateVerIsNewer = dateNew.after(dateOld);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return dateVerIsNewer;
            } else if ("".equals(newDateVerString) && ("".equals(oldDateVerString))) {
                return false;
            } else if ("".equals(newDateVerString)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Compares newMajorVer with oldMajorVer.
     * 
     * @param newMajorVer
     * @param oldMajorVer
     * @return true if newMajorVer > oldMajorVer, false otherwise
     */
    private boolean isMajorVersionGreaterThanMinor(String newMajorVer, String oldMajorVer) {
        // Major versions (X.Y.Z) are not the same
        String[] newItems = newMajorVer.split("\\.");
        String[] oldItems = oldMajorVer.split("\\.");
        int oldItemsLength = oldItems.length;
        int newItemsLength = newItems.length;
        int shorterItemlength = oldItemsLength < newItemsLength ? oldItemsLength: newItemsLength;
        if (newItemsLength != oldItemsLength) {
            if (compareVersions(newItems, oldItems, shorterItemlength)) {
                return true;
            } else if (newItemsLength > oldItemsLength) {
                return true;
            }
            return false;
        }
        return compareVersions(newItems, oldItems, shorterItemlength);
    }

   
  private boolean compareVersions(String[] newItems, String[] oldItems, int length) {
      for (int i = 0; i < length; i++) {
          if (Long.parseLong(newItems[i]) > Long.parseLong(oldItems[i])) {
             return true;
          }
      }
     return false;
    }
 
    /**
     * Extracts major version.
     * 
     * @param version could be: X.Y.Z or X.Y.Z.vddMMyyHHmmss
     * @return X.Y.Z
     */
    private String getMajorVersion(String version) {
        String majorVer;
        int vIdx = version.indexOf(".v");
        if (vIdx != -1) {
            /** There is a minor version, vddMMyyHHmmss embedded */
            majorVer = version.substring(0, vIdx);
        } else {
            /** No minor version embedded */
            majorVer = version;
        }
        return majorVer;
    }

    /**
     * Pads out a string upto padlen with pad chars
     * 
     * @param Object.toString() to be padded
     * @param length of pad (+ve = pad on right, -ve pad on left)
     * @param pad character
     * @return - padded string
     */
    public String pad(Object str, int padlen, String pad) {
        String padding = new String();
        int len = Math.abs(padlen) - str.toString().length();
        if (len < 1)
            return str.toString();
        for (int i = 0; i < len; ++i)
            padding = padding + pad;
        return (padlen < 0 ? padding + str : str + padding);
    }

    /**
     * Replaces all extra spaces for just one. So " WORD TEST" would become "
     * WORD TEST". It also removes \r and \n (they are considered as spaces)
     * Examples: " TEST \r\nWORD \r\n" will become " TEST WORD "
     * 
     * @param text - input string
     * @return String - string with extra spaces replaced.
     */
    public String removeExtraSpaces(String text) {
        text = text.replaceAll("\\s+", " "); // replace extra spaces by just
        // one space
        // System.out.println(text+"-");
        return text;
    }

    /**
     * Removes leading spaces
     * 
     * @param text
     * @return String - text without the leading spaces
     */
    public String trimLeft(String text) {
        String result = "";
        int start = this.getBeginningOfNextWord(text, -1);
        if (start >= 0) {
            result = text.substring(start);
        }
        return result;
    }

    /**
     * Finds out whether the passed line contains the passed whole word (i.e.
     * surrounded by spaces or at the beginning or end of line) Note: if the
     * word is within quotes ("" or '') it'll return false, it will be regarded
     * as just another literal.
     * 
     * @param line - String where the word is to be found
     * @param word - word
     * @return boolean - true/false
     */
    public boolean containsWholeWordNotInQuotes(String line, String word) {
        // regular exp. = word
        String reg = "(^|\\s)" + word + "($|\\s)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(line);
        if (m.find()) {
            int start = m.start();
            if (!isWithinQuotes(line, start)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Finds out whether the passed line contains a passed whole word (i.e.
     * surrounded by spaces or at the beginning or end of line) Note: if the
     * word is within quotes ("" or '') it'll return TRUE.
     * 
     * @param line - String where the word is to be found
     * @param word - word
     * @return boolean - true/false
     */
    public boolean containsWholeWord(String line, String word) {
        // regular exp. = word
        String reg = "(^|\\s|\"|')" + word + "($|\\s|\"|')";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(line);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether at least one of the parameters passed is empty (either ""
     * or null)
     * 
     * @param params - list of parameters
     * @return boolean - true: at least one param is empty. false: all the
     *         params are non-empty.
     */
    public boolean atLeastOneEmpty(String... params) {
        boolean areEmpty = false;
        for (int i = 0; i < params.length; i++) {
            if (isEmpty(params[i])) {
                areEmpty = true;
                break;
            }
        }
        return areEmpty;
    }

    /**
     * A String is considered empty if is equal to "", or it only contains
     * spaces, \r \n \t or null
     * 
     * @param text
     * @return boolean
     */
    public static synchronized boolean isEmpty(String text) {
        if (text == null) {
            return true;
        } else {
            text.replaceAll("(\\s|\\r|\\n|\\t)", "");
            text = text.trim();
            return ((text.length() == 0));
        }
    }

    /**
     * Unix files finish lines with LF (line feed) - in java \n Windows files
     * finish lines with CR LF (carry return) (line feed) - in java \r \n Checks
     * whether a String finishes with \n
     * 
     * @param text - String under test
     * @return boolean - true= it does finish with \n
     */
    public boolean finishesWithNewLine(String text) {
        int length = text.length();
        if (length > 0 && "\n".equals(text.substring(length - 1, length))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Strips a string of leading and trailing white spaces
     * 
     * @return String with trailing white spaces removed, or "" if there are no
     *         remaining characters
     * @param value String to be trimmed
     */
    public String trim(String value) {
        if (value != null) {
            value = value.trim();
            if (value.length() == 0) {
                value = "";
            }
        }
        // System.out.println(value);
        return value;
    }

    /**
     * Replacement utility - substitutes <b>all</b> occurrences of 'src' with
     * 'dest' in the string 'name'
     * 
     * @param name the string that the substitution is going to take place on
     * @param src the string that is going to be replaced
     * @param dest the string that is going to be substituted in
     * @return String with the substituted strings
     */
    public String substitute(String name, String src, String dest) {
        if (name == null || src == null || name.length() == 0) {
            return name;
        }
        if (dest == null) {
            dest = "";
        }
        int index = name.indexOf(src);
        if (index == -1) {
            return name;
        }
        StringBuffer buf = new StringBuffer();
        int lastIndex = 0;
        while (index != -1) {
            buf.append(name.substring(lastIndex, index));
            buf.append(dest);
            lastIndex = index + src.length();
            index = name.indexOf(src, lastIndex);
        }
        buf.append(name.substring(lastIndex));
        return buf.toString();
    }

    /**
     * Converts an exception into a string
     * 
     * @param t the exception to be converted
     * @return String a string representation of the exception
     */
    public String exceptionToString(Throwable t) {
        if (t != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println(t.getMessage());
            pw.println("\n=====================\n");
            t.printStackTrace(pw);
            pw.println("\n=====================\n");
            pw.close();
            return sw.toString();
        } else {
            return "";
        }
    }

    /**
     * Finds out the min value within an Array on Integers
     * 
     * @param vals
     * @return int - the min value. If the ArrayList is empty returns 0
     */
    public int min(int... vals) {
        if (vals.length > 0) {
            int min = Integer.MAX_VALUE; // Start pre-populating the min
            // value with a big number
            int val = 0;
            for (int i = 0; i < vals.length; i++) {
                val = vals[i];
                if (val < min)
                    min = val;
            }
            return min;
        } else {
            return 0;
        }
    }

    /**
     * Finds out the max value within an Array of integers
     * 
     * @param vals
     * @return int - max value. If ArrayList is empty returns 0
     */
    public int max(int... vals) {
        if (vals.length > 0) {
            int max = Integer.MIN_VALUE; // Start pre-populating the max
            // value with a very small negative
            // number.
            int val = 0;
            for (int i = 0; i < vals.length; i++) {
                val = vals[i];
                if (val > max)
                    max = val;
            }
            return max;
        } else {
            return 0;
        }
    }

    /**
     * Finds out the offset of the next word within a text. Examples: "WORD
     * TEST", OFFSET=-1 => returns 0 (beginning of WORD) "WORD TEST", OFFSET=0 =>
     * returns 5 (beginning of TEST) "WORD TEST", OFFSET=6 => returns -1 (there
     * are no more words after TEST)
     * 
     * @param text - text where search is performed. It can be empty "", but
     *            must not be NULL.
     * @param offset - offset within the text where the search will start
     * @return int - index within the text where the next word begins. If no
     *         word is found it returns -1. When searching for next word the
     *         following characters are ignored: blank space, CR \r and LF \n
     */
    public int getBeginningOfNextWord(String text, int offset) {
        int result = -1;
        int next = 0;
        if (offset >= 0) {
            // Find the next occurrence of either ' ', \r, \n, \t or EOF
            int blankIdx = text.indexOf(' ', offset);
            int CRIdx = text.indexOf('\r', offset);
            int LFIdx = text.indexOf('\n', offset);
            int TabIdx = text.indexOf('\t', offset);
            int EOF = text.length();
            int MAX = Integer.MAX_VALUE;
            next = min((blankIdx < 0 ? MAX : blankIdx), (CRIdx < 0 ? MAX : CRIdx), (LFIdx < 0 ? MAX : LFIdx), (TabIdx < 0 ? MAX
                    : TabIdx), (EOF < 0 ? MAX : EOF));
        } else {
            next = 0;
        }
        if (next >= text.length() || next < 0) {
            // EOF reched ==> no more words found
            result = -1;
        } else {
            char ch = text.charAt(next);
            // find the next character which is not a ' ','\r','\n',EOF
            while ((ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t') && (next < text.length())) {
                ch = text.charAt(next++);
            }
            if (next == text.length()) {
                // the end of text was reached without any character being found
                result = -1;
            } else {
                if (next == 0)
                    result = 0;
                else
                    result = --next;
            }
        }
        return result;
    }

    /**
     * Finds out whether the first character of a line, except blank spaces, is
     * the passed character
     * 
     * @param doc - document where the line lies
     * @param offset - document offset that lies within the line under
     *            investigation
     * @param ch - character
     * @return E.g. if the we are looking for the '*' character, then: * text
     *         text text => returns true * text text text => returns true
     *         (though there are a few leading blank spaces) .* text text text =>
     *         returns false (first character is '.'
     */
    public boolean isFirstCharOfLine(IDocument doc, int offset, char ch) {
        boolean isFirstChar = false;
        try {
            // get the line text
            int lineNumber = doc.getLineOfOffset(offset);
            IRegion lineRegion = doc.getLineInformation(lineNumber);
            String line = doc.get(lineRegion.getOffset(), lineRegion.getLength());
            isFirstChar = isFirstCharOfLine(line, ch);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return isFirstChar;
    }

    /**
     * Finds out whether the first character of a line, except blank spaces, is
     * the passed character
     * 
     * @param line - line under investigation
     * @param offset - document offset that lies within the line under
     *            investigation
     * @param ch - character
     * @return E.g. if the we are looking for the '*' character, then: * text
     *         text text => returns true * text text text => returns true
     *         (though there are a few leading blank spaces) .* text text text =>
     *         returns false (first character is '.'
     */
    public boolean isFirstCharOfLine(String line, char ch) {
        boolean isFirstChar = false;
        // Find out if the first character of the line is the ch passed
        // (blank spaces are allowed)
        boolean allSpaces = true;
        int chIdx = line.indexOf(ch);
        if (chIdx != -1) {
            // iterate through the line
            for (int i = 0; i < chIdx; i++) {
                if (line.charAt(i) != ' ') {
                    // A non blank space was found
                    allSpaces = false;
                    break;
                }
            }
            if (allSpaces)
                isFirstChar = true;
            else
                isFirstChar = false;
        } else {
            // The ch passed was not found in the line
            isFirstChar = false;
        }
        return isFirstChar;
    }

    /**
     * @param doc - document which contains the line under investigation
     * @param offset - offset within the line under investigaion
     * @return String - the first word within the line, or "" if no word found
     */
    public String getFirstWordInLine(IDocument doc, int offset) {
        String word = "";
        try {
            IRegion lineRegion = doc.getLineInformationOfOffset(offset);
            String lineTxt = doc.get(lineRegion.getOffset(), lineRegion.getLength());
            word = this.getFirstWordInLine(lineTxt);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return word;
    }

    /**
     * @param str - string
     * @return String - the first word in the str, or "" if no word found
     */
    public String getFirstWordInLine(String str) {
        String word = "";
        String lineTxt = str.trim();
        int beginning = this.getBeginningOfNextWord(lineTxt, -1);
        if (beginning < 0) {
            // No word found in the line
            word = "";
        } else {
            int spaceIdx = lineTxt.indexOf(' ', beginning);
            int end = Math.min((spaceIdx < 0 ? Integer.MAX_VALUE : spaceIdx), lineTxt.length());
            if (end >= beginning)
                word = lineTxt.substring(beginning, end);
            else
                word = "";
        }
        return word;
    }

    /**
     * Given a text and an offset within the text, get the word (if any) where
     * the offset lies
     * 
     * @param str - document's whole content
     * @param offset - offset within the document. It points to a position
     *            within a word
     * @return String - the word that fits the document's offset, or "" if no
     *         word if found
     */
    public String getWord(String str, int offset) {
        IRegion region = getWordRegion(str, offset);
        if (region == null) {
            return "";
        } else {
            int wordStart = region.getOffset();
            int length = region.getLength();
            String word = str.substring(wordStart, wordStart + length);
            return word;
        }
    }

    /**
     * Given a text and an offset within the text, get the word's Region (if
     * any) where the offset lies
     * 
     * @param str - document's whole content
     * @param offset - offset within the document. It points to a position
     *            within a word
     * @return Region - the Region where the word with the given document's
     *         offset has been found, or null if no word found
     */
    public IRegion getWordRegion(String str, int offset) {
        if (str == null || "".equals(str) || offset < 0 || offset >= str.length()) {
            return null;
        } else {
            int startBlankIdx = str.lastIndexOf(" ", offset);
            int startLFIdx = str.lastIndexOf("\n", offset);
            int startCRIdx = str.lastIndexOf("\r", offset);
            int startTabIdx = str.lastIndexOf("\t", offset);
            int startSemicolonIdx = str.lastIndexOf(":", offset);
            int endBlankIdx = str.indexOf(" ", offset);
            int endLFIdx = str.indexOf("\n", offset);
            int endCRIdx = str.indexOf("\r", offset);
            int endTabIdx = str.indexOf("\t", offset);
            int endSqBrkIdx = str.indexOf("[", offset);
            int endAngleBrkIdx = str.indexOf("<", offset);
            int endRndBrkIdx = str.indexOf("(", offset);
            int endCrlBrkIdx = str.indexOf("{", offset);
            int endSemicolonIdx = str.indexOf(":", offset);
            int endEOFIdx = str.length(); // The position of the last
            // character in the file
            StringUtil su = new StringUtil();
            int startIdx = su.max((startBlankIdx < 0 ? -1 : startBlankIdx), (startLFIdx < 0 ? -1 : startLFIdx),
                    (startCRIdx < 0 ? -1 : startCRIdx), (startTabIdx < 0 ? -1 : startTabIdx), (startSemicolonIdx < 0 ? -1
                            : startSemicolonIdx));
            int endIdx = su.min((endBlankIdx < 0 ? Integer.MAX_VALUE : endBlankIdx), (endLFIdx < 0 ? Integer.MAX_VALUE : endLFIdx),
                    (endCRIdx < 0 ? Integer.MAX_VALUE : endCRIdx), (endTabIdx < 0 ? Integer.MAX_VALUE : endTabIdx),
                    (endSqBrkIdx < 0 ? Integer.MAX_VALUE : endSqBrkIdx), (endAngleBrkIdx < 0 ? Integer.MAX_VALUE : endAngleBrkIdx),
                    (endRndBrkIdx < 0 ? Integer.MAX_VALUE : endRndBrkIdx), (endCrlBrkIdx < 0 ? Integer.MAX_VALUE : endCrlBrkIdx),
                    (endSemicolonIdx < 0 ? Integer.MAX_VALUE : endSemicolonIdx), endEOFIdx);
            if (startIdx >= endIdx || startIdx < -1 || endIdx <= 0 || endIdx > endEOFIdx) {
                return null;
            } else {
                return new Region(startIdx + 1, (endIdx - startIdx - 1));
            }
        }
    }

    /**
     * @param document - document within where the words are to be replaced
     * @param offset - position of the word that will be replaced within the
     *            document. It can be the beginning, anywhere in the middle of
     *            the end.
     * @param newWord - word that will replace the word where the offset lies.
     */
    public void replaceWord(IDocument document, int offset, String newWord) {
        if (document != null && offset > -1) {
            // Substitute the words
            IRegion basicWordRegion = (new StringUtil()).getWordRegion(document.get(), offset);
            int offsetWord = basicWordRegion.getOffset();
            int lengthWord = basicWordRegion.getLength();
            try {
                document.replace(offsetWord, lengthWord, newWord);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param word - String to be transform. E.g. MODULE.ACCOUNT.CUSTOMER
     * @return String - transformed: e.g. moduleAccountCustomer If word is null,
     *         will return ""
     */
    public String transformToOONaming(String word) {
        if (word == null)
            return "";
        StringBuffer sb = new StringBuffer();
        if (word.indexOf('.') == -1) {
            // No . found, so just lower case the word
            sb.append(word.toLowerCase());
        } else {
            String[] tokens = word.split("\\.");
            for (int i = 0; i < tokens.length; i++) {
                String lowerC = tokens[i].toLowerCase();
                if (i > 0) {
                    lowerC = capitaliseFirstLetter(lowerC);
                }
                sb.append(lowerC);
            }
        }
        return sb.toString();
    }

    /**
     * @param word - input word
     * @return String - word with first letter capitalised
     */
    private String capitaliseFirstLetter(String word) {
        char firstLetter = word.charAt(0);
        if (Character.isLowerCase(firstLetter)) {
            char firstLetterUpper = Character.toUpperCase(firstLetter);
            word = firstLetterUpper + word.substring(1);
        }
        return word;
    }

    /**
     * @return String with now timestamp, following DateFormat.MEDIUM format.
     *         (hh:mm:ss)
     */
    public static String getTimeStamp() {
        Date now = new Date();
        return (DateFormat.getTimeInstance(DateFormat.MEDIUM).format(now));
    }

    /**
     * Parses a RGB string into its three constituent color ints.
     * 
     * @param colorString - e.g. "0;100;255"
     * @return int[] = {0, 100, 255}
     */
    public int[] parseColor(String colorString) {
        int[] colorVals = new int[3];
        String[] colors = colorString.split(";");
        for (int i = 0; i < colors.length; i++) {
            colorVals[i] = Integer.parseInt(colors[i]);
        }
        return colorVals;
    }

    /**
     * Removes the JBC non word characters such as (,*,|,#,* etc., This excludes
     * theses characters: $,",',.
     * 
     * @param text
     * @return text non word characters replaced with space
     */
    public static String removeJbcNonwordChar(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[\\W&&[^$\"'.]]", " ");
    }

    /**
     * Checks whether the passed word is a JBC literal.
     * 
     * @param text
     * @return true if it is a literal. false otherwise.
     */
    public static boolean isJbcLiteral(String text) {
        if (text == null) {
            return false;
        } else if (text.matches("-?\\.?[0-9]*\\.?[0-9]*")) {
            return true;
        } else if (text.length() == 0 || (text.startsWith("\"") || text.endsWith("\""))
                || (text.startsWith("'") || text.endsWith("'"))) {
            return true;
        }
        return false;
    }

    /**
     * Removes the comment part from the passed line.
     * 
     * @param line
     * @return line comment part removed
     */
    public static String removeJbcCommentPart(String line) {
        if (line == null) {
            return null;
        }
        int beginIndex = line.indexOf(';', 0);
        if (beginIndex > 0) {
            line = line.substring(0, beginIndex);
            return line;
        }
        return line;
    }

    /**
     * Removes JBC literals from the passed in line
     * 
     * @param line
     * @return line literals removed
     */
    public static String removeJbcLiteral(String line) {
        if (line != null) {
            return line.replaceAll("\".*?\"|'.*?'", "");
        }
        return line;
    }

    /**
     * Checks whether the passed in line is a label
     * 
     * @param line
     * @return true if it is a label. false otherwise.
     */
    public static boolean isLabel(String line) {
        if (line == null) {
            return false;
        }
        String firstWord = new StringUtil().getFirstWordInLine(line.replaceFirst(":", " ")).trim();
        if (firstWord == null || firstWord.length() <= 0) {
            return false;
        }
        line = StringUtil.removeJbcCommentPart(line).trim();
        return line.equals(firstWord + ":");
    }

    /**
     * Removed JBC function from the passed in line
     * 
     * @param line
     * @return line with functions removed
     */
    public static String removeJbcFunction(String line) {
        line = line.replaceAll("(=).*\\(", " ");
        return line;
    }

    public static boolean isNonWordChar(char ch) {
        char[] str = { ch };
        if (Pattern.matches("[\\W&&[^$.]]", new String(str))) {
            return true;
        }
        return false;
    }

    /**
     * Returns the folder name for the remote path
     * 
     * @param serverDirectory
     * @return folder name
     */
    public static String getFolderFromServerDirectory(String serverDirectory) {
        String folderName = "";
        if (serverDirectory.equals("/")) {
            return ".";
        }
        int index = serverDirectory.lastIndexOf("/");
        folderName = serverDirectory.substring(index + 1);
        return folderName;
    }
}
