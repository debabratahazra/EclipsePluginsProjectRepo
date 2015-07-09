package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * helper class for comments strip out operation mainly used for reading the
 * basic file
 * 
 * @author sbharathraja
 * 
 */
class BasicFileReader {

    /** instance of {@link BasicFileReader} */
    private static BasicFileReader bFileReader = new BasicFileReader();
    /** buffered reader for reading the file */
    private BufferedReader bufferedReader;
    /** comments */
    private String comments = "";
    /** collection of stripped out comments container */
    private List<CommentsContainer> commentsList;
    /** xml builder */
    private CommentsXMLBuilder xmlBuilder;
    /** comments to be stored as more info */
    private String largeComments = "";
    /** flag for collecting short info */
    private boolean isReadyToCollectComment = false;
    /** flag for collecting history about the subroutine */
    private boolean isReadyToCollectHistory = false;
    /** line by line large comment */
    private String stepByStepLargeComment = "";

    /**
     * get the instance
     * 
     * @return instance of {@link BasicFileReader}
     */
    protected static BasicFileReader getInstance() {
        return bFileReader;
    }

    private BasicFileReader() {
        // block instance creation
    }

    protected void stripCommentsFromSingleFile(File basicFile, String targetPath) {
        if (basicFile.exists() && basicFile.isFile()) {
		DataInputStream inStream = null;
            try {
                inStream = new DataInputStream(new FileInputStream(basicFile));
                bufferedReader = new BufferedReader(new InputStreamReader(inStream));
                xmlBuilder = new CommentsXMLBuilder();
                xmlBuilder.buildXML(StringUtil.removeBasicExtension(basicFile.getName()), targetPath, buildComments());
                largeComments = "";
                stepByStepLargeComment = "";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * strip the comment from file and building the comments and store it in
     * container
     * 
     * @return
     */
    private ArrayList<CommentsContainer> buildComments() {
        commentsList = new ArrayList<CommentsContainer>();
        CommentsContainer container = new CommentsContainer();
        String line = "";
        if (bufferedReader != null) {
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (StringUtil.isComment(line) || line.equalsIgnoreCase("")) {
                        storeShortDesc(line);
                    } else {
                        container = getValidContainer(line, container);
                        if (!"".equalsIgnoreCase(container.getKeyword()))
                            commentsList.add(container);
                        container = new CommentsContainer();
                        comments = "";
                    }
                    storeLargeComments(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setLargeComments(commentsList);
        return (ArrayList<CommentsContainer>) commentsList;
    }

    /**
     * store the comments
     * 
     * @param statement - single line read from basic file
     */
    private void storeShortDesc(String statement) {
        CharSequence docStart = "<doc>";
        CharSequence docEnd = "</doc>";
        if (statement.contains(docStart))
            isReadyToCollectComment = true;
        else if (statement.contains(docEnd))
            isReadyToCollectComment = false;
        if (isReadyToCollectComment)
            comments += statement + GenerateDocConstants.LINE_BRK_SYMBOL;
    }

    /**
     * store the more info comments in a private variable
     * 
     * @param statement - single line read from basic file
     */
    private void storeLargeComments(String statement) {
        isReadyToCollectModificationHistory(statement);
        if (StringUtil.isComment(statement) && !statement.equalsIgnoreCase("")) {
            if (isReadyToCollectComment)
                stepByStepLargeComment += statement + GenerateDocConstants.LINE_BRK_SYMBOL;
            else if (isReadyToCollectHistory)
                largeComments += GenerateDocConstants.LINE_BRK_SYMBOL + statement.replaceAll("<doc @history>", "")
                        + GenerateDocConstants.LINE_BRK_SYMBOL;
        } else {
            String identifier = getIdentifier(statement);
            if (!identifier.equalsIgnoreCase("")) {
                largeComments += GenerateDocConstants.LINE_BRK_SYMBOL + identifier + GenerateDocConstants.LINE_BRK_SYMBOL
                        + stepByStepLargeComment;
                stepByStepLargeComment = "";
            } else
                stepByStepLargeComment = "";
        }
    }

    /**
     * checking whether the given statement/single line from basic file has
     * added as modification history or not and store it in private boolean
     * flag.
     * 
     * @param statement - single line from basic file
     * 
     */
    private void isReadyToCollectModificationHistory(String statement) {
        CharSequence modDocStart = "<doc @history>";
        CharSequence modDocEnd = "</doc>";
        if (statement.contains(modDocStart)) {
            isReadyToCollectHistory = true;
            largeComments += GenerateDocConstants.LINE_BRK_SYMBOL + "Modification History" + GenerateDocConstants.LINE_BRK_SYMBOL;
        } else if (statement.contains(modDocEnd))
            isReadyToCollectHistory = false;
    }

    /**
     * with given statement, validate the statement get the identifier, related
     * comments and proper keywords , put into the given container and return
     * it.
     * 
     * @param statement - single line read from basic file which must not be a
     *            commented one.
     * @param container - collection of comments
     * @return collection of valid comments
     */
    private CommentsContainer getValidContainer(String statement, CommentsContainer container) {
        boolean validationFlag = false;
        if (statement.contains("SUBROUTINE")) {
            container.setKeyword("SubRoutine");
            validationFlag = true;
        } else if (statement.contains("PROGRAM")) {
            container.setKeyword("Program");
            validationFlag = true;
        } else if (EditorDocumentUtil.isLabel(statement)) {
            container.setKeyword("GoSub");
            validationFlag = true;
        }
        if (validationFlag) {
            container.setIdentifier(getIdentifier(statement));
            container.setInfo(DocumentViewerUtil.cleanComment(comments));
        }
        return container;
    }

    /**
     * get the identifier from the given statement
     * 
     * @param statement - single line
     * @return identifier
     */
    private String getIdentifier(String statement) {
        StringUtil stringUtil = new StringUtil();
        statement = stringUtil.removeExtraSpaces(statement);
        if (EditorDocumentUtil.isLabel(statement)) {
            return statement.substring(0, statement.indexOf(":"));
        } else {
            if (statement.contains("SUBROUTINE"))
                // since the word subroutine has character 10 plus
                // space has at the prefix and suffix the substring
                // starting point is 12. code should be reviewed and
                // replace with proper logic.
                return removeArgsFromIdentifier(statement.substring(12, statement.length()));
            else if (statement.contains("PROGRAM"))
                // since the word program has character 7 plus
                // space has at the prefix and suffix the substring
                // starting point is 9. code should be reviewed and
                // replace with proper logic.
                return removeArgsFromIdentifier(statement.substring(9, statement.length()));
        }
        return "";
    }

    /**
     * remove the arguments from identifier
     * 
     * @param identifierWithArgs - identifier with args
     * @return identifier without args
     */
    private String removeArgsFromIdentifier(String identifierWithArgs) {
        if (!identifierWithArgs.contains("("))
            return identifierWithArgs;
        else
            return identifierWithArgs.substring(0, identifierWithArgs.indexOf("(")+1);
    }

    private void setLargeComments(List<CommentsContainer> commentsList) {
        for (ListIterator<CommentsContainer> listIteration = commentsList.listIterator(); listIteration.hasNext();) {
            CommentsContainer routineInContainer = listIteration.next();
            String keyword = routineInContainer.getKeyword();
            if (keyword.equalsIgnoreCase("SubRoutine") || keyword.equalsIgnoreCase("Program")) {
                routineInContainer.setMoreInfo(DocumentViewerUtil.cleanComment(largeComments));
            }
        }
    }
}
