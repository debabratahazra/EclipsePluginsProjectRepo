package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import org.eclipse.jface.text.ITextViewer;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;

/**
 * Takes care of loading the clicked item into the editor.
 * 
 * @author ssethupathi
 * 
 */
public class T24BasicHyperlinkSourceFileLoader implements IHyperlinkLoader {

    private static IHyperlinkLoader loader = null;

    private T24BasicHyperlinkSourceFileLoader() {
    }

    public static IHyperlinkLoader getInstance() {
        if (loader == null) {
            loader = new T24BasicHyperlinkSourceFileLoader();
        }
        return loader;
    }

    /**
     * Loads the selected item into the active editor
     */
    public boolean load(ITextViewer viewer, String sourceFileName) {
        if (RemoteOperationsManager.getInstance().loadHyperlink(sourceFileName)) {
            return true;
        }
        return false;
    }
}
