package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

/**
 * This class implements the hyper link detector which detects the hyper links
 * in the region of the given text viewer.
 * 
 * @author ssethupathi
 * 
 */
public class T24HyperlinkDetector implements IHyperlinkDetector {

    /**
     * Detects whether the word hovered is a hyper link or not. Only one hyper
     * link sent for processing at a time.
     * 
     */
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IHyperlink[] basicHyperlinkWords = new IHyperlink[1];
        T24BasicHyperlink basicHoveredWord = new T24BasicHyperlink(textViewer, region);
        // If the hovered word could be treated as a hyper link as per the
        // T24Basic rules, return the hyper link
        if (basicHoveredWord.isHyperlinkWord()) {
            basicHyperlinkWords[0] = basicHoveredWord;
            return basicHyperlinkWords;
        } else {
            return null;
        }
    }
}
