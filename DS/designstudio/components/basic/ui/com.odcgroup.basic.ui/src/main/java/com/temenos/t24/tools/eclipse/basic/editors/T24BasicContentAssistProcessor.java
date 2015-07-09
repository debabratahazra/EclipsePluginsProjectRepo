package com.temenos.t24.tools.eclipse.basic.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.editors.extra.T24BasicContentAssistHelper;
import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * A content assist processor proposes completions and computes context
 * information for a particular content type. A content assist processor is a
 * {@link org.eclipse.jface.text.contentassist.IContentAssistant} plug-in.
 */
public class T24BasicContentAssistProcessor implements IContentAssistProcessor {

    /** The helper object which helps the content assist processor */
    private T24BasicContentAssistHelper helper = T24BasicContentAssistHelper.getInstance();
    /** Word in the document for which content assist is requested */
    private static String currentWord = "";
    /** Position in the Text viewer where content assist is requested from */
    private int position;
    /** The current text viewer */
    private ITextViewer viewer;
    /** Document of the active editor */
    private IDocument document = null;
    /** New cursor position */
    private int newCursorPoition;

    /**
     * Returns a list of completion proposals based on the specified location
     * within the document that corresponds to the current cursor position
     * within the text viewer.
     * 
     * @param viewer the viewer whose document is used to compute the proposals
     * @param offset an offset within the document for which completions should
     *            be computed
     * @return an array of completion proposals or <code>null</code> if no
     *         proposals are possible
     */
    public ICompletionProposal[] computeCompletionProposals(ITextViewer textViewer, int offset) {
        viewer = textViewer;
        position = offset;
        document = viewer.getDocument();
        currentWord = DocumentViewerUtil.getCurrentWordPart(document, position);
        if (currentWord == null || currentWord.length() <= 0) {
            return null;
        }
        // FIXME - This logic should be changed once we can differentiate the
        // auto activation key trigger to ctrl+space trigger
       // if (currentWord.endsWith(".")) {
       //     if (!currentWord.toUpperCase().equals(currentWord)) {
                String[] methodNames = getMethodNames();
                if (methodNames != null && methodNames.length > 0) {
                    return getMethodProposals(methodNames);
                }
                // If content assist is triggered by '.' and current word is not
                // an Object, null is returned so that only CTRL+Space would
                // trigger for candidates other than Objects
//                else if (isAutoActivated || !currentWord.equals(previousWord)) {
//                    isAutoActivated = false;
//                    return null;
//                } else {
//                    isAutoActivated = true;
//                }
//            } else if (isAutoActivated || !currentWord.equals(previousWord)) {
//                isAutoActivated = false;
//                return null;
//            } else {
//                isAutoActivated = true;
//            }
    //    }
        String precedingWord = DocumentViewerUtil.getPrecedingWord(document, position - 1);
        if (helper.isT24BranchingWord(precedingWord)) {
            return getT24SourceProposals(precedingWord);
        }
        if (!currentWord.toUpperCase().equals(currentWord)) {
            List<String> proposals = new ArrayList<String>();
            proposals = helper.getObjectNames(currentWord);
            if (proposals != null && proposals.size() > 0) {
                Image image = EclipseUtil.getImage("/icons/object.png");
                return getFinalProposals(getCompletionProposals(proposals, image));
            }
        }
        return getVariableAndKeywordProposals();
    }

    /**
     * {@inheritDoc}
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
        return null;
    }

    /**
     * Returns the characters which when entered by the user should
     * automatically trigger the presentation of possible completions.
     * 
     * @return the auto activation characters for completion proposal or
     *         <code>null</code> if no auto activation is desired
     */
    public char[] getCompletionProposalAutoActivationCharacters() {
        // '.' is used as an auto activation character for ObjectName.methodName
    //    return new char[] { '.' };
   //Auto activation for dot is stopped.   	
    	return null;
    }

    /**
     * {@inheritDoc}
     */
    public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getErrorMessage() {
        return "T24Basic Content Assist is not available for the current cursor location";
    }

    /**
     * Returns relevant proposals from the T24 source file list - Subroutines,
     * Programs and Inserts.
     * 
     * @param precedingWord Word preceded by the current word in the document
     * @return Completion proposals array
     */
    private ICompletionProposal[] getT24SourceProposals(String precedingWord) {
        List<String> proposals = new ArrayList<String>();
        Image image = null;
        if (precedingWord.equals("CALL")) {
            proposals = helper.getSubroutineList(currentWord);
            image = EclipseUtil.getImage("/icons/subroutine.png");
        } else if (precedingWord.equals("EXECUTE")) {
            proposals = helper.getProgramList(currentWord);
            image = EclipseUtil.getImage("/icons/execute.png");
        } else {
            proposals = helper.getInsertList(currentWord);
            image = EclipseUtil.getImage("/icons/insert.png");
        }
        return getFinalProposals(getCompletionProposals(proposals, image));
    }

    /**
     * Returns the array of method names for the current word proposal
     * 
     * @return method names
     */
    private String[] getMethodNames() {
        String objectName = currentWord.substring(0, currentWord.length() - 1);
        String[] methodNames = helper.getObjectMethods(objectName);
        return methodNames;
    }

    /**
     * Returns proposals for the T24 methods for the supplied methods.
     * 
     * @return Completion proposals array
     */
    private ICompletionProposal[] getMethodProposals(String[] methodNames) {
        List<ICompletionProposal> completionProposals = new ArrayList<ICompletionProposal>();
        Image image = EclipseUtil.getImage("/icons/function.png");
        for (String methodName : methodNames) {
            ICompletionProposal proposal = new CompletionProposal(methodName, position, 0, methodName.length(), image, methodName,
                    null, null);
            completionProposals.add(proposal);
        }
        return completionProposals.toArray(new ICompletionProposal[completionProposals.size()]);
    }

    /**
     * Returns proposals for the JBC keywords and variables in the document.
     * 
     * @return array of CompletionProposal object for keyword and variables
     */
    private ICompletionProposal[] getVariableAndKeywordProposals() {
        List<ICompletionProposal> completionProposals = new ArrayList<ICompletionProposal>();
        completionProposals.addAll(getKeywordProposals());
        completionProposals.addAll(getVariableProposals());
        Collections.sort(completionProposals, new CompletionProposalComparator());
        return getFinalProposals(completionProposals);
    }

    /**
     * Returns completion proposals for the keywords.
     * 
     * @return Completion proposals
     */
    private List<ICompletionProposal> getKeywordProposals() {
        List<String> proposals = new ArrayList<String>();
        Image image = EclipseUtil.getImage("/icons/key.png");
        proposals = helper.getKeywordList(currentWord);
        return getCompletionProposals(proposals, image);
    }

    private List<ICompletionProposal> getVariableProposals() {
        List<String> proposals = new ArrayList<String>();
        Image image = EclipseUtil.getImage("/icons/variable.png");
        proposals = helper.getVariableList(document, currentWord);
        return getCompletionProposals(proposals, image);
    }

    /**
     * Builds the completion proposals commonly for all types of content assist
     * requests.
     * 
     * @param proposals List of proposed items
     * @param image Image to be displayed with the proposals
     * @return Completion proposals
     */
    private List<ICompletionProposal> getCompletionProposals(List<String> proposals, Image image) {
        List<ICompletionProposal> completionProposals = new ArrayList<ICompletionProposal>();
        int currentWordLength = currentWord.length();
        if (proposals != null && proposals.size() > 0) {
            ICompletionProposal proposal;
            Iterator<String> listIterator = proposals.iterator();
            int lengthToMove = 0;
            while (listIterator.hasNext()) {
                String itemName = listIterator.next();
                String replacementString = itemName.substring(currentWordLength);
                lengthToMove = replacementString.length();
                proposal = new CompletionProposal(replacementString, position, 0, lengthToMove, image, itemName, null, null);
                completionProposals.add(proposal);
            }
            // used to move the cursor when a single proposal is applied in the
            // document
            newCursorPoition = position + lengthToMove;
        }
        return completionProposals;
    }

    /**
     * Produces the default completion proposal to be used when no proposals
     * possible
     * 
     * @return {@link ICompletionProposal[]} no proposal message
     */
    private ICompletionProposal getNoProposal() {
        // No proposals available for this position
        return new CompletionProposal("", position, 0, position, null, "No Default Proposals", null, null);
    }

    /**
     * Returns the final array of CompletionProposal objects. If only one
     * matching present, it replaces with proposed word in the document.
     * 
     * @param proposals
     * @return array of ICompletionProposal
     */
    private ICompletionProposal[] getFinalProposals(List<ICompletionProposal> proposals) {
        if (proposals == null) {
            return null;
        }
        if (proposals.size() == 1) {
            proposals.get(0).apply(document);
            viewer.setSelectedRange(newCursorPoition, 0);
            return null;
        }
        if (proposals.size() == 0) {
            proposals.add(getNoProposal());
        }
        return proposals.toArray(new ICompletionProposal[proposals.size()]);
    }

    /**
     * Private comparator for ICompletionProposal. Used in sorting keyword and
     * variable proposals.
     * 
     */
    private class CompletionProposalComparator implements Comparator<ICompletionProposal> {

        public int compare(ICompletionProposal first, ICompletionProposal second) {
            return first.getDisplayString().compareToIgnoreCase(second.getDisplayString());
        }
    }
}
