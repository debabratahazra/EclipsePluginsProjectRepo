package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.temenos.t24.tools.eclipse.basic.editors.util.T24BasicWhitespaceDetector;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * This class represents a T24 Basic hyper link.
 * 
 * @author ssethupathi
 * 
 */
public class T24BasicHyperlink implements IHyperlink {

    private ITextViewer viewer = null;
    private IDocument document = null;
    private IRegion region = null;
    private String hoveredWord = "";

    public T24BasicHyperlink() {
    }

    public T24BasicHyperlink(ITextViewer viewer, IRegion region) {
        this.viewer = viewer;
        this.document = viewer.getDocument();
        this.region = region;
        this.hoveredWord = getHoveredWord();
    }

    /**
     * This method returns whether the selected word is a hyper link or not.
     * 
     * @return true if the word is a hyper link false otherwise.
     */
    public boolean isHyperlinkWord() {
        T24BasicWhitespaceDetector chDetector = new T24BasicWhitespaceDetector();
        String precedingWord = getPrecedingWord();
        try {
            if (chDetector.isWhitespace(document.getChar(region.getOffset()))) {
                return false;
            }
            if (precedingWord.equals("GOSUB") || precedingWord.equals("GOTO")) {
                return true;
            }
            if (RemoteSitesManager.getInstance().getDefaultSite()!=null && DocumentViewerUtil.isT24BranchingWord(precedingWord)) {
                return true;
            }
        } catch (BadLocationException ex) {
            return false;
        }
        return false;
    }

    public IRegion getHyperlinkRegion() {
        int offset = DocumentViewerUtil.getWordStartOffset(document, region.getOffset());
        return new Region(offset, hoveredWord.length());
    }

    public String getHyperlinkText() {
        return hoveredWord;
    }

    public String getTypeLabel() {
        return "";
    }

    /**
     * This method opens a hyper link.
     */
    public void open() {
        String precedingWord = getPrecedingWord();
        IHyperlinkLoader hyperlinkLoader = T24HyperlinkLoaderFactory.getHyperlinkLoader(precedingWord);
        if (hyperlinkLoader != null) {
            hyperlinkLoader.load(viewer, hoveredWord);
        }
    }

    /**
     * Extracts the source file name to be loaded from the selected region
     * 
     * @return String source file name
     */
    private String getHoveredWord() {
        String docContent = document.get();
        StringBuffer hoveredWord = new StringBuffer((new StringUtil()).getWord(docContent, region.getOffset()));
        return trimHoveredWord(hoveredWord.toString());
    }

    /**
     * Removes the characters like ( from the file name. and returns the exact
     * file name.
     * 
     * @param fileName original file name
     * @return file name trimmed
     */
    private static String trimHoveredWord(String hoveredWord) {
        int endPoint = hoveredWord.length();
        endPoint = hoveredWord.indexOf("(");
        if (endPoint == -1) {
            return hoveredWord.substring(0);
        } else {
            return hoveredWord.substring(0, endPoint);
        }
    }

    private String getPrecedingWord() {
        return DocumentViewerUtil.getPrecedingWord(document, region.getOffset());
    }
}
