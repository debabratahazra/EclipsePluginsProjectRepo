package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import org.eclipse.jface.text.ITextViewer;

/**
 * Interface to be implemented by the T24 basic hyper link loaders
 * 
 * @author ssethupathi
 * 
 */
public interface IHyperlinkLoader {

    /**
     * This method loads the hyper link.
     */
    public boolean load(ITextViewer viewer, String hoveredWord);

}
