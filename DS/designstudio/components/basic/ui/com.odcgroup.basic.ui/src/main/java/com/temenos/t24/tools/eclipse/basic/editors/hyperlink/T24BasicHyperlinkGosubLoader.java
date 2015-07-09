package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class T24BasicHyperlinkGosubLoader implements IHyperlinkLoader {

    private static IHyperlinkLoader loader = null;

    private T24BasicHyperlinkGosubLoader() {
    }

    public static IHyperlinkLoader getInstance() {
        if (loader == null) {
            loader = new T24BasicHyperlinkGosubLoader();
        }
        return loader;
    }

    /**
     * Loads the GOSUB/GOTO labels. Searches the entire document for the label
     * and reveals that range in the viewer.
     */
    public boolean load(ITextViewer viewer, String hoveredWord) {
        StringUtil su = new StringUtil();
        IDocument document = EditorDocumentUtil.getActiveDocument();
        String hoveredLabel = hoveredWord + ":";
        int lineNo = document.getNumberOfLines();
        boolean labelFound = false;
        String firstWord = "";
        IRegion lineRegion = null;
        int lineCount = -1;
        int lineStartOffset = 0;
        do {
            try {
                lineCount++;
                lineStartOffset = document.getLineOffset(lineCount);
                lineRegion = document.getLineInformationOfOffset(lineStartOffset);
                String line = document.get(lineRegion.getOffset(), lineRegion.getLength());
                // Checks the given line is a Label
                if (EditorDocumentUtil.isLabel(line)) {
                    firstWord = su.getFirstWordInLine(line);
                    if (firstWord.equals(hoveredLabel)) {
                        labelFound = true;
                    }
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
                return false;
            }
        } while (!labelFound && lineCount <= lineNo);
        if (labelFound && lineRegion != null) {
            // Label found and reveals the region
            viewer.revealRange(lineStartOffset, 0);
            viewer.setSelectedRange(lineStartOffset, 0);
            return true;
        }
        return false;
    }
}
