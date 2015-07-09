package com.temenos.t24.tools.eclipse.basic.protocols;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jface.preference.IPreferenceStore;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.utils.Base64;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;
import com.temenos.t24.tools.eclipse.basic.views.tasks.TaskItem;

/**
 * Misc of utilities for the protocol layer.
 * The protocol package is layered in a stack as follows:
 * 1.- GUI                    => interfaces with user. The GUI comprises a set of Dialogs and Menus.
 * 2.- RemoteSessionManager   => holds sesssion keys. It's the main interface
 * 3.- Actions                => all the action bussiness logic are in here
 * 4.- HttpProtocolManager    => responsible for handling http comms.
 */
public class ProtocolUtil {

    private static IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");

    /**
     * Returns the channel that the user has previously selected. If none
     * found, it'll return "" (empty string).
     * @return String - the current channel used by the IDE.
     */
    public String getCurrentChannel() {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String curChannel = mu.getProperty("t24.remote.channel.name");
        return curChannel;
    }

    /**
     * Stores the passed channel.
     */
    public void setCurrentChannel(String channel) {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        mu.updateProperty("t24.remote.channel.name", channel);
    }

    /**
     * @return true => both current dir and default project are not null or empty.
     * false => at least one of them is either null or empty ("")
     */
    public static boolean isLocalDirAndProjectSet() {
        boolean setOk = false;
        String localCurrentDir = EclipseUtil.getLocalCurDirectory(EclipseUtil.isSaveLocallyOn());
        String localDefaultProject = EclipseUtil.getLocalDefaultProject();
        if ((!"".equals(localCurrentDir) && (localCurrentDir != null))
                && (!"".equals(localDefaultProject) && (localDefaultProject != null))) {
            setOk = true;
        }
        return setOk;
    }

    /*
     * Builds the URL that points to Browser application.
     * http://hostname:port/path/resource
     */
    public synchronized static String buildUrlString() {
        String t24BrowserURL = "";
        String hostname = store.getString(PluginConstants.T24_REMOTE_HOSTNAME);
        String port = store.getString(PluginConstants.T24_REMOTE_PORT);
        // Open URLConnection for reading
        t24BrowserURL = "http://" + hostname + ":" + port + "/";
        return t24BrowserURL;
    }

    /**
     * Transforms all the tabs "\t" within the string passed 
     * into a block of spaces. E.g. "A\tB" will be transformed into "A  B"
     * assuming 3 spaces per tab.
     * @param str - string with tabs "\t" to be transformed
     * @param tabSize - current size of tab.
     * @return - string with tabs transformed
     */
    /**
     * Transforms new line "\r\n" or "\n" into \<FM\> string.
     * ToolBox does this before sending a string down to the remote
     * Server 
     * @param str
     * @return
     */
    public String transformNewLinesIntoFM(String str) {
        String res = str.replaceAll("\\r\\n", "<FM>");
        res = res.replaceAll("\\n", "<FM>");
        return res;
    }

    /**
     * Transforms \<FM\> or !FM! into \r\n
     * @param str
     * @return
     */
    public String transformFMintoNewLines(String str) {
        String res = str.replaceAll("<FM>", "\r\n");
        res = res.replaceAll("!FM!", "\r\n");
        return res;
    }

    /**
     * Transforms all the isolated \r or \n, or combinations of \r and \n
     * into the windows accepted new line: \r\n 
     * Windows files finish lines with CR(carry return) LF(line feed) - in java \r\n
     * @param str
     * @return
     */
    public String transformInsolated_CR_LF_IntoWinNewLines(String stra) {
        String res0 = stra.replaceAll("(\\r\\n)", "<FM>"); // preserve existing new lines (\r\n)  
        String res1 = res0.replaceAll("\\r", "\r\n"); // expand \r
        String res2 = res1.replaceAll("\\n", "\r\n"); // expand \n
        String res3 = res2.replaceAll("\\r+", "\r"); // purge extra \r
        String res4 = res3.replaceAll("\\n+", "\n"); // purge extra \n
        String res5 = res4.replaceAll("<FM>", "\r\n"); // recover the existing \r\n
        return res5;
    }

    /**
     * Transforms (CR) \r into new line \r\n
     * Unix files finish lines with LF (line feed) - in java \n
     * Windows files finish lines with CR LF (carry return) (line feed) - in java \r \n
     * @param str
     * @return
     */
    public String transformIsolated_CR_intoWinNewLines(String str) {
        String res = str.replaceAll("\\r", "\r\n");
        return res;
    }

    /**
     * Transforms (LF) \n into new line \r\n
     * Unix files finish lines with LF (line feed) - in java \n
     * Windows files finish lines with CR LF (carry return) (line feed) - in java \r \n
     * @param str
     * @return
     */
    public String transformIsolated_LF_intoNewWinLines(String str) {
        String res = str.replaceAll("\\n", "\r\n");
        return res;
    }

    /**
     * Encodes the passed String into another one using base64 encoder.
     * Base 64 encoding is a way of encoding arbitrary binary data using only 
     * printable ASCII characters. The size in bytes of encoded data will be 
     * approximately 135% of the original data.
     * 
     * Uses a 64-character alphabet consisting of upper- and lower-case Roman alphabet 
     * characters (A–Z, a–z), the numerals (0–9), and the "+" and "/" symbols. The "=" 
     * symbol is also used as a special suffix code.
     * 
     * @param fileContents - normal String
     * @return String encoded in base64
     */
    public String encode64(String fileContents) {
        // Encode file to base64
        String file64 = Base64.encodeBytes(fileContents.getBytes(), Base64.DONT_BREAK_LINES);
        return file64;
    }

    /**
     * Decodes the passed file using a base64 decoder
     * @param fileBase64 - String encoded with base64
     * @return String decoded
     */
    public String decode64(String fileBase64) {
        String fileNormal = new String(Base64.decode(fileBase64));
        return fileNormal;
    }

    /**
     * Encodes a string following URL encoding conventions:
     * - The alphanumeric characters "a" through "z", "A" through "Z" and "0" through "9" remain the same.
     * - The special characters ".", "-", "*", and "_" remain the same.
     * - The space character " " is converted into a plus sign "+".
     * - All other characters are unsafe and are first converted into one or more bytes using some 
     * encoding scheme. Then each byte is represented by the 3-character string "%xy", where xy is 
     * the two-digit hexadecimal representation of the byte. The recommended encoding scheme to 
     * use is UTF-8. However, for compatibility reasons, if an encoding is not specified, then the 
     * default encoding of the platform is used.
     * 
     * Example: "test word=" => "test+word%3D"
     * @param str - string to be url encoded
     * @return url encoded string
     */
    public String encodeURL(String str) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    public String decodeURL(String str) {
        String decoded = "";
        try {
            decoded = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;
    }

    /**
     * Encodes a String (typically the contents of a BASIC module written with 
     * the editor), using a multi-staged encoding process, leaving the String ready
     * to be sent down to Browser. 
     * 
     * The encoding stages include:
     * (The string to be encoded is "COND\r\n> 3")
     * - Transform the \r\n : e.g. "COND\r\n> 3" => "COND\<FM\>> 3"
     * - Base64 transform:    e.g. "COND\<FM\>> 3" => "Q09ORDxGTT4+IDM="
     * - Get string without trailing "="s (if any): e.g.  "Q09ORDxGTT4+IDM"
     * - URL encode: e.g. "Q09ORDxGTT4+IDM" => "Q09ORDxGTT4%2BIDM"
     * - Finally attach trailing "="s: e.g. "Q09ORDxGTT4%2BIDM="
     *                                       
     * @param textUndecoded
     * @return String encoded - if an empty "" or null string is passed, it'll return ""
     */
    public String encodeComplete(String textUndecoded) {
        if (!"".equals(textUndecoded) && (textUndecoded != null)) {
            // 1.- BASE64 ENCODING.
            // Here there are 2 substages
            // 1.a Transform "new lines" (\r\n) into "<FM>" string. ToolBox is doing this, so
            // this method replicates the same functionality.
            // e.g. "COND\r\n> 3" => "COND<FM>> 3"
            textUndecoded = transformNewLinesIntoFM(textUndecoded);
            // 1.b Base64 encoding
            // e.g. "COND<FM>> 3" => "Q09ORDxGTT4+IDM="
            String text64 = encode64(textUndecoded);
            // 2.- URL ENCODING.
            // e.g. "Q09ORDxGTT4+IDM=" => "Q09ORDxGTT4%2BIDM="
            // There are three substages
            // 2.a ToolBox does not encoded the ending "=" (if any). So all the trailing '=' are removed (and
            // later attached again).
            // Note: the '=' is a special character in base64 to indicate extra
            // padding added.
            String equalsStr = "";
            String text64WithoutEquals = "";
            if (text64.indexOf('=') != -1) {
                equalsStr = text64.substring(text64.indexOf('='), text64.length());
                text64WithoutEquals = text64.substring(0, text64.indexOf('='));
            } else {
                text64WithoutEquals = text64;
            }
            // 2.b URL encode the string without the trailing "="s
            String text64url = encodeURL(text64WithoutEquals);
            // 2.c Append the string with the "="s
            return text64url + equalsStr;
        } else {
            return "";
        }
    }

    /**
     * Decodes a String (typically the contents of a BASIC module)using a multi-staged 
     * encoding process, leaving the String ready to be displayed or saved in a local
     * file. 
     * 
     * This decoding stages include:
     * (The string received from Browser is: "Q09ORA0+IDM="
     * - NO URL DECODING (this is mentioned just for documentation and clarification)
     * - Base64 decode:  e.g. "Q09ORA0+IDM=" => "COND\r> 3"
     * - Restore new lines: e.g. "COND\r> 3" => "COND\r\n> 3"
     * 
     * @param textUndecoded
     * @return String encoded - if an empty "" or null string is passed, it'll return ""
     */
    public String decodeComplete(String text64url) {
        if (!"".equals(text64url) && (text64url != null)) {
            // Follows the reverse order operations as encodeFile
            // 1. URL DECODING
            // Note: the strings received from Browser is not URL-encoded
            // e.g. "Q09ORA0+IDM="
            // Note that the "+" is not encoded as %2B. So NO URL DECODING is done here at this stage.            
            // 2. BASE64 DECODING
            // 2.a Base64 decoding
            // e.g. "Q09ORA0+IDM=" => "COND\r> 3"
            String textUndecoded = decode64(text64url);
            // 2.b restore the new lines (\r\n) from "\n" or "\r"
            // e.g. "COND\n> 3" => "COND\r\n> 3"
            String textUndecodedWithNewLines = this.transformInsolated_CR_LF_IntoWinNewLines(textUndecoded);
            // 2.c transform tabs if needed ("\t" to spaces)
            return textUndecodedWithNewLines;
        } else {
            return "";
        }
    }

    /**
     * Example of compilation outocome from the server:
     * <compileout>
     * Some messages regarding compilation settings 
     * <compileoutput>
     * <errors>
     *   <error>
     *       <msg>Error in TEST Found 'PROGRAM/SUBROUTINE'</msg>
     *       <line>5</line>
     *       <correct>Program name and SUBROUTINE/PROGRAM statement must be the same</correct>
     *   </error>
     * </errors>
     * <warnings>
     *   <warning>
     *       <msg>Warning - found 'CRT'</msg>
     *       <line>6</line>
     *       <type>Incompatible with Browser, Desktop and OFS. Do not use</type>
     *   </warning>
     * </warnings>
     * <codereviews>
     *   <codereview>
     *       <msg>Code review message in here</msg>
     *       <line>6</line>
     *       <reason>Incompatible with Browser, Desktop and OFS. Do not use</reason>   
     *   </codereview>
     * </codereviews>
     * <standards>
     *    <standard>
     *      <msg>Failure to comply with Standards. File variables mustbe F., record variables R., do not break call overmultiple lines. CALL OPF(FN.FT.NORATE,FV.FT.NORATE) 2 times</msg>
     *      <line>77</line>
     *    </standard>
     * </standards> 
     * <RatingDetail>
     *   <CodeLines>0</CodeLines>
     *   <ParaLines>0</ParaLines>
     *   <Nests>0</Nests>
     *   <Conditions>0</Conditions>
     *   <Gotos>0</Gotos>
     *   <SameLine>0</SameLine>
     *   <Labels>0</Labels>
     *   <Comments>0</Comments>
     *   <Rating>0</Rating>
     * </RatingDetail>
     *</compileoutput>
     *</compileout>
     * @param compileoutXml
     * @param oldRating - old rating from a previous compilation
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String processCompileOutcome(String compileoutXml, String oldRating) {
        StringBuffer results = new StringBuffer();
        if (!"".equals(compileoutXml) && (compileoutXml != null)) {
            // transform !FM! to \r\n if needed
            compileoutXml = this.transformFMintoNewLines(compileoutXml);
            int startCompileoutIdx = compileoutXml.indexOf("<compileoutput>");
            int endCompileoutIdx = compileoutXml.indexOf("</compileoutput>");
            String header = "";
            if (startCompileoutIdx < 0 || endCompileoutIdx < 0) {
                // There are no well-formed compileoutput contents embedded in the compilation response.
                // There maybe only a header.
                header = compileoutXml.replaceAll("<compileoutput>", "");
                header = compileoutXml.replaceAll("</compileoutput>", "");
                return header;
            }
            header = compileoutXml.substring(0, startCompileoutIdx);
            results.append(header);
            /*********************************************************************************
             * Analyse \<compileoutput\> element:
             */
            // GET <compileoutput> XML CONTENTS
            String compileoutputXml = compileoutXml.substring(startCompileoutIdx, endCompileoutIdx + "</compileoutput>".length());
            try {
                // Build a dom4j Document with the compileoutput element
                Document doc = DocumentHelper.parseText(compileoutputXml);
                // ERRORS 
                List list = doc.selectNodes("//error");
                for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                    Element errEl = (Element) iter.next();
                    String errPath = errEl.getUniquePath();
                    String errMsg = XmlUtil.getSafeText(compileoutputXml, errPath + "//msg");
                    String errLine = XmlUtil.getSafeText(compileoutputXml, errPath + "//line");
                    String errCorrect = XmlUtil.getSafeText(compileoutputXml, errPath + "//correct");
                    results.append("--------------------------------------------------------\n");
                    results.append("ERROR\n");
                    results.append("Message: " + errMsg + "\n");
                    results.append("Line: " + errLine + "\n");
                    results.append("Correct: " + errCorrect + "\n");
                }
                // WARNINGS  
                list = doc.selectNodes("//warning");
                for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                    Element warEl = (Element) iter.next();
                    String warPath = warEl.getUniquePath();
                    String warMsg = XmlUtil.getSafeText(compileoutputXml, warPath + "//msg");
                    String warLine = XmlUtil.getSafeText(compileoutputXml, warPath + "//line");
                    String warType = XmlUtil.getSafeText(compileoutputXml, warPath + "//type");
                    results.append("--------------------------------------------------------\n");
                    results.append("WARNING\n");
                    results.append("Message: " + warMsg + "\n");
                    results.append("Line: " + warLine + "\n");
                    results.append("Type: " + warType + "\n");
                }
                // STANDARDS  
                list = doc.selectNodes("//standard");
                for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                    Element stdEl = (Element) iter.next();
                    String stdPath = stdEl.getUniquePath();
                    String stdMsg = XmlUtil.getSafeText(compileoutputXml, stdPath + "//msg");
                    String stdLine = XmlUtil.getSafeText(compileoutputXml, stdPath + "//line");
                    results.append("--------------------------------------------------------\n");
                    results.append("STANDARDS\n");
                    results.append("Message: " + stdMsg + "\n");
                    results.append("Line: " + stdLine + "\n");
                }
                // CODE REVIEW
                list = doc.selectNodes("//codereview");
                for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                    Element crEl = (Element) iter.next();
                    String crPath = crEl.getUniquePath();
                    String crMsg = XmlUtil.getSafeText(compileoutputXml, crPath + "//msg");
                    String crLine = XmlUtil.getSafeText(compileoutputXml, crPath + "//line");
                    String crReason = XmlUtil.getSafeText(compileoutputXml, crPath + "//reason");
                    results.append("--------------------------------------------------------\n");
                    results.append("CODE REVIEWS\n");
                    results.append("Message: " + crMsg + "\n");
                    results.append("Line: " + crLine + "\n");
                    results.append("Reason: " + crReason + "\n");
                }
            } catch (DocumentException de) {
                de.printStackTrace();
            }
            results.append("--------------------------------------------------------\n");
            results.append("RATING DETAILS\n");
            // RATING DETAILS 
            results.append("CodeLines: " + XmlUtil.getSafeText(compileoutputXml, "//CodeLines") + "\n");
            results.append("Nests: " + XmlUtil.getSafeText(compileoutputXml, "//Nests") + "\n");
            results.append("Conditions: " + XmlUtil.getSafeText(compileoutputXml, "//Conditions") + "\n");
            results.append("Gotos: " + XmlUtil.getSafeText(compileoutputXml, "//Gotos") + "\n");
            results.append("SameLine: " + XmlUtil.getSafeText(compileoutputXml, "//SameLine") + "\n");
            results.append("Labels: " + XmlUtil.getSafeText(compileoutputXml, "//Labels") + "\n");
            results.append("Comments: " + XmlUtil.getSafeText(compileoutputXml, "//Comments") + "\n");
            results.append("New Rating: " + XmlUtil.getSafeText(compileoutputXml, "//Rating") + "\n");
            results.append("Old rating: " + oldRating + "\n");
        }
        return results.toString();
    }

    /**
     * Typical outcome from a compilation looks like: 
     * <compileout> 
     * This is the header, with some messages regarding compilation settings 
     * <compileoutput> 
     * <errors> ... errors details ... </errors> 
     * <warnings> ... warning details ... </warnings> 
     * <RatingDetails> ... rating details ... </RatingDetails>
     * </compileoutput> 
     * </compileout>
     * 
     * @param compileoutXml
     * @return This is the header, with some messages regarding compilation settings
     * or "" if empty. 
     */
    public String extractHeader(String compileoutXml) {
        String header = "";
        if (!"".equals(compileoutXml) && (compileoutXml != null)) {
            // transform !FM! to \r\n if needed
            compileoutXml = this.transformFMintoNewLines(compileoutXml);
            int startCompileoutIdx = compileoutXml.indexOf("<compileoutput>");
            int endCompileoutIdx = compileoutXml.indexOf("</compileoutput>");
            if (startCompileoutIdx < 0 || endCompileoutIdx < 0) {
                // There are no well-formed compileoutput contents embedded in the compilation response.
                // There maybe only a header.
                header = compileoutXml.replaceAll("<compileoutput>", "");
                header = compileoutXml.replaceAll("</compileoutput>", "");
                return header;
            }
            header = compileoutXml.substring(0, startCompileoutIdx);
        }
        return header;
    }

    /**
     * Typical outcome from a compilation looks like: 
     * <compileout> Some messages regarding compilation settings 
     * <compileoutput> 
     * <errors> ... errors details ... </errors> 
     * <warnings> ... warning details ... </warnings> 
     * <RatingDetails> ... rating details ... </RatingDetails>
     * </compileoutput> 
     * </compileout>
     * 
     * @param compileoutXml
     * @return compileoutput element, or "" 
     */
    public String extractCompileoutput(String compileoutXml) {
        if (!"".equals(compileoutXml) && (compileoutXml != null)) {
            // transform !FM! to \r\n if needed
            compileoutXml = this.transformFMintoNewLines(compileoutXml);
            int startCompileoutIdx = compileoutXml.indexOf("<compileoutput>");
            int endCompileoutIdx = compileoutXml.indexOf("</compileoutput>");
            // GET <compileoutput> XML CONTENTS
            if (startCompileoutIdx != -1 && endCompileoutIdx != -1) {
                String compileoutputXml = compileoutXml.substring(startCompileoutIdx, endCompileoutIdx
                        + "</compileoutput>".length());
                return compileoutputXml;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * Given the contents of &lt;compileoutput&gt; node returned from the server, after a 
     * compilation, it returns either &lt;Rating&gt; or &lt;GlobusBpRating&gt; element, depending
     * on the value of the parameter newRating.
     *   
     * Note: A program is typically compiled in a separate directory to GLOBUS.BP, e.g. in BP.
     * After compiling the program, the compilation routine will search inside GLOBUS.BP for
     * a program with the same name. If found, the routine will include the element &lt;GlobusBpRating&gt;
     * in the xml response (holding the old rating), otherwise it won't be included.
     * 
     * If no Rating is available it will return "".
     * 
     * @param compileoutputXml
     * @param newRating: true => the new compilation rating; false => the compilation rating from
     * the same program (see Note above) in GLOBUS.BP 
     * @return
     */
    public String getCompilationRating(String compileoutXml, boolean newRating) {
        String rating = "";
        String compileoutputXml = this.extractCompileoutput(compileoutXml);
        if (!"".equals(compileoutputXml)) {
            try {
                // Build a dom4j Document with the compileoutput element
                DocumentHelper.parseText(compileoutputXml);
                if (newRating) {
                    rating = XmlUtil.getSafeText(compileoutputXml, "//Rating");
                } else {
                    rating = XmlUtil.getSafeText(compileoutputXml, "//CurrentRating");
                }
                if ("".equals(rating)) {
                    rating = "";
                }
            } catch (DocumentException de) {
                de.printStackTrace();
                rating = "";
            }
        } else {
            rating = "";
        }
        return rating;
    }

    /**
     * Breaks down the compileOutput response returned from the Server after
     * a file has been sent for compilation.
     * Builds an array of TaskItems ready to be sent to TaskView for display. 
     * @param compileXmlResult
     * @return Array of TaskItem
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TaskItem[] getCompileTasks() {
        ArrayList<TaskItem> taskAList = new ArrayList<TaskItem>();
        TaskItem task;
        // The structure of this property is:
        // t24.remote.compile.xml.response=isLocal<<NR>>local_work_space<<NR>>file_name.b<<NR>>xml_compile_output
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String compilationResponse = mu.getProperty("t24.remote.compile.xml.response");
        if (!"".equals(compilationResponse) && compilationResponse != null) {
            String[] values = compilationResponse.split(mu.getProperty("t24.key.record.separator"));
            boolean isLocal = Boolean.parseBoolean(values[0]);
            String fullPath = values[1];
            String filename = values[2];
            String compXml = values[3];
            // transform !FM! to \r\n if needed
            compXml = this.transformFMintoNewLines(compXml);
            int startCompileoutIdx = compXml.indexOf("<compileoutput>");
            int endCompileoutIdx = compXml.indexOf("</compileoutput>");
            // GET <compileoutput> XML CONTENTS
            if (startCompileoutIdx > 0 && endCompileoutIdx > 0) {
                String compileoutputXml = compXml.substring(startCompileoutIdx, endCompileoutIdx + "</compileoutput>".length());
                try {
                    // Build a dom4j Document with the compileoutput element
                    Document doc = DocumentHelper.parseText(compileoutputXml);
                    // ERRORS
                    List list = doc.selectNodes("//error");
                    for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                        Element errEl = (Element) iter.next();
                        String errPath = errEl.getUniquePath();
                        String errMsg = XmlUtil.getSafeText(compileoutputXml, errPath + "//msg");
                        String errLine = XmlUtil.getSafeText(compileoutputXml, errPath + "//line");
                        String errCorrect = XmlUtil.getSafeText(compileoutputXml, errPath + "//correct");
                        if ("".equals(errLine)) {
                            // no line available
                            errLine = "-1";
                        }
                        // Create a new Task
                        task = new TaskItem(errMsg, errCorrect, Integer.parseInt(errLine));
                        task.setCategory(T24_VIEW_ITEM_CATEGORY.COMPILE_ERROR_ITEM);
                        task.setLocal(isLocal);
                        task.setFullPath(fullPath);
                        task.setFilename(filename);
                        taskAList.add(task);
                    }
                    // WARNINGS
                    list = doc.selectNodes("//warning");
                    for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                        Element warEl = (Element) iter.next();
                        String warPath = warEl.getUniquePath();
                        String warMsg = XmlUtil.getSafeText(compileoutputXml, warPath + "//msg");
                        String warLine = XmlUtil.getSafeText(compileoutputXml, warPath + "//line");
                        String warType = XmlUtil.getSafeText(compileoutputXml, warPath + "//type");
                        if ("".equals(warLine)) {
                            // no line available
                            warLine = "-1";
                        }
                        // Create a new Task
                        task = new TaskItem(warMsg, warType, Integer.parseInt(warLine));
                        task.setCategory(T24_VIEW_ITEM_CATEGORY.COMPILE_WARNING_ITEM);
                        task.setLocal(isLocal);
                        task.setFullPath(fullPath);
                        task.setFilename(filename);
                        taskAList.add(task);
                    }
                    // STANDARDS
                    list = doc.selectNodes("//standard");
                    for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                        Element stdEl = (Element) iter.next();
                        String stdPath = stdEl.getUniquePath();
                        String stdMsg = XmlUtil.getSafeText(compileoutputXml, stdPath + "//msg");
                        String stdLine = XmlUtil.getSafeText(compileoutputXml, stdPath + "//line");
                        if ("".equals(stdLine)) {
                            // no line available
                            stdLine = "-1";
                        }
                        // Create a new Task
                        String codingStandardsTxt = "Ensure your code adheres to coding standards";
                        task = new TaskItem(stdMsg, codingStandardsTxt, Integer.parseInt(stdLine));
                        task.setCategory(T24_VIEW_ITEM_CATEGORY.COMPILE_STANDARD_ITEM);
                        task.setLocal(isLocal);
                        task.setFullPath(fullPath);
                        task.setFilename(filename);
                        taskAList.add(task);
                    }
                    // CODE REVIEWS
                    list = doc.selectNodes("//codereview");
                    for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
                        Element crEl = (Element) iter.next();
                        String crPath = crEl.getUniquePath();
                        String crMsg = XmlUtil.getSafeText(compileoutputXml, crPath + "//msg");
                        String crLine = XmlUtil.getSafeText(compileoutputXml, crPath + "//line");
                        String crReason = XmlUtil.getSafeText(compileoutputXml, crPath + "//reason");
                        if ("".equals(crLine)) {
                            // no line available
                            crLine = "-1";
                        }
                        // Create a new Task
                        task = new TaskItem(crMsg, crReason, Integer.parseInt(crLine));
                        task.setCategory(T24_VIEW_ITEM_CATEGORY.COMPILE_CODEREVIEW_ITEM);
                        task.setLocal(isLocal);
                        task.setFullPath(fullPath);
                        task.setFilename(filename);
                        taskAList.add(task);
                    }
                } catch (DocumentException de) {
                    de.printStackTrace();
                }
            }
        }
        int arraySize = taskAList.size();
        TaskItem[] items = new TaskItem[arraySize];
        return ((TaskItem[]) taskAList.toArray(items));
    }
}
