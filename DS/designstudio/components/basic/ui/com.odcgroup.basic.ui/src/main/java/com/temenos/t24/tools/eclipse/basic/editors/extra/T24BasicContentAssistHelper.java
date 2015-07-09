package com.temenos.t24.tools.eclipse.basic.editors.extra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import org.eclipse.jface.text.IDocument;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.T24FilesStream;
import com.temenos.t24.tools.eclipse.basic.help.HelpCache;
import com.temenos.t24.tools.eclipse.basic.views.variables.VariablesListBuilder;

public class T24BasicContentAssistHelper {

    /** Content assist helper - Singleton */
    private static T24BasicContentAssistHelper contentAssistHelper = null;
    /** T24 Subroutines list */
    private static List<String> t24Subroutines = new ArrayList<String>();
    /** T24 Programs list */
    private static List<String> t24Programs = new ArrayList<String>();
    /** T24 Insert files list */
    private static List<String> t24Inserts = new ArrayList<String>();
    /** Keywords list */
    private static List<String> keywords = new ArrayList<String>();
    /** T24 Objects and methods Map */
    private static Map<String, String[]> t24Objects = new HashMap<String, String[]>();
    /** T24 Objects list */
    private static List<String> t24ObjectNames = new ArrayList<String>();
    /** File stream for different contexts - real and mock */
    private static T24FilesStream t24FileStream;
    /** Variable list builder singleton */
    private static VariablesListBuilder variableListBuilder;

    private T24BasicContentAssistHelper() {
        t24FileStream = (T24FilesStream) T24BasicPlugin.getSpringApplicationContext().getBean("t24SourceStream");
    }

    public static T24BasicContentAssistHelper getInstance() {
        if (contentAssistHelper == null) {
            contentAssistHelper = new T24BasicContentAssistHelper();
            loadT24SourceList();
            loadKeywordsList();
            loadObjectMap();
            variableListBuilder = VariablesListBuilder.getInstance();
        }
        return contentAssistHelper;
    }

    /**
     * Returns the list of T24 Subroutines which start with the given
     * startSequence
     * 
     * @param startSequence Start sequence of the Subroutine name
     * @return Subroutine list
     */
    public List<String> getSubroutineList(String startSequence) {
        return getProposalList(t24Subroutines, startSequence);
    }

    /**
     * Returns the list of T24 Program which start with the given startSequence
     * 
     * @param startSequence Start sequence of the Programs name
     * @return Program list
     */
    public List<String> getProgramList(String startSequence) {
        return getProposalList(t24Programs, startSequence);
    }

    /**
     * Returns the list of T24 Inserts which start with the given startSequence
     * 
     * @param startSequence Start sequence of the Inserts name
     * @return Inserts list
     */
    public List<String> getInsertList(String startSequence) {
        return getProposalList(t24Inserts, startSequence);
    }

    /**
     * Returns the list of Keywords which start with the given startSequence
     * 
     * @param startSequence Start sequence of the Keywords
     * @return Keywords list
     */
    public List<String> getKeywordList(String startSequence) {
        return getProposalList(keywords, startSequence);
    }

    /**
     * Returns the List of variables present in the document with start sequence
     * 
     * @param document
     * @param startSequence
     * @return variables list
     */
    public List<String> getVariableList(IDocument document, String startSequence) {
        return variableListBuilder.getVariables(document, startSequence);
    }

    /**
     * Returns the list of T24 Methods for the given Object
     * 
     * @param objectName Object name
     * @return Methods list
     */
    public String[] getObjectMethods(String objectName) {
        String[] methods;
        methods = t24Objects.get(objectName);
        if (methods == null) {
            methods = new String[0];
        }
        return methods;
    }

    /**
     * Returns the list of T24 Objects which start with the given startSequence
     * 
     * @param startSequence Start sequence of the Objects name
     * @return Objects list
     */
    public List<String> getObjectNames(String startSequence) {
        List<String> objList = new ArrayList<String>();
        int dotPos = startSequence.indexOf('.');
        if (dotPos > 0) {
            String objectName = startSequence.substring(0, dotPos);
            String[] objMethods = t24Objects.get(objectName);
            if (objMethods == null) {
                // The current word happens to be looking like an Object but
                // might be a variable!
                return null;
            }
            for (int methodCount = 0; methodCount < objMethods.length; methodCount++) {
                objList.add(objectName + "." + objMethods[methodCount]);
            }
            return getProposalList(objList, startSequence);
        }
        return getProposalList(t24ObjectNames, startSequence);
    }

    /**
     * loads the T24 Objects and their methods into a Map
     */
    private static void loadObjectMap() {
        InputStream is = null;
        try {
            Properties prop = new Properties();
            is = t24FileStream.getObjectsFileStream();
            if (is != null) {
                prop.load(is);
                Enumeration<Object> keys = prop.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String str = prop.getProperty(key);
                    t24ObjectNames.add(key);
                    t24Objects.put(key, str.split(":"));
                }
                Collections.sort(t24ObjectNames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * Loads the keywords into a List
     */
    private static void loadKeywordsList() {
        keywords = HelpCache.getInstance().getKeywordList();
    }

    /**
     * Loads the T24 source files into lists
     */
    private static void loadT24SourceList() {
        InputStream is = null;
        try {
            is = t24FileStream.getItemsFileStream();
            if (is != null) {
                Scanner scan = new Scanner(is);
                StringBuilder sourceName;
                int startIndex;
                char firstChar = ' ';
                while (scan.hasNextLine()) {
                    sourceName = new StringBuilder((scan.nextLine()).trim());
                    // We expect the source names in the file are like
                    // CALL$ACCOUNT, EXECUTE$T24.PRE.RELEASE and
                    // INSERT$I_F.ACCOUNT. We just want the actual file name
                    // which is after $ in every scanned line
                    startIndex = sourceName.indexOf("$") + 1;
                    firstChar = sourceName.charAt(0);
                    if (firstChar == 'C') {
                        t24Subroutines.add(sourceName.delete(0, startIndex).toString());
                    }
                    if (firstChar == 'I') {
                        t24Inserts.add(sourceName.delete(0, startIndex).toString());
                    }
                    if (firstChar == 'E') {
                        t24Programs.add(sourceName.delete(0, startIndex).toString());
                    }
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * Returns the proposal list from the given list with the start sequence
     * 
     * @param origList Original list
     * @param startSequence start sequence
     * @return Proposal list
     */
    private List<String> getProposalList(List<String> origList, String startSequence) {
        List<String> proposalList = new ArrayList<String>();
        boolean started = false;
        boolean inRange = false;
        // It is assumed that the items in the original list are sorted to their
        // natural order
        for (String subroutineName : origList) {
            if (subroutineName.startsWith(startSequence)) {
                started = true;
                inRange = true;
                proposalList.add(subroutineName);
            } else {
                inRange = false;
            }
            if (started == true && inRange == false) {
                return proposalList;
            }
        }
        return proposalList;
    }

    /**
     * Checks if the given type is a T24 branching type
     * 
     * @param type
     * @return true if it is a T24 branching type(CALL, $INSERT etc.,). false
     *         otherwise.
     */
    public boolean isT24BranchingWord(String type) {
        for (String item : PluginConstants.t24BranchingItems) {
            if (item.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
