package com.temenos.t24.tools.eclipse.basic.editors.hyperlink;

import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;

public class T24HyperlinkLoaderFactory {

    public static IHyperlinkLoader getHyperlinkLoader(String precedingWord) {
        if (precedingWord.equals("GOSUB") || precedingWord.equals("GOTO")) {
            return T24BasicHyperlinkGosubLoader.getInstance();
        }
        if (DocumentViewerUtil.isT24BranchingWord(precedingWord)) {
            return T24BasicHyperlinkSourceFileLoader.getInstance();
        }
        // as of now, only subroutines, programs, inserts and gosubs are
        // catered. New hyper link loaders could be created for other types such
        // as variables etc. with new IHyperlinkLoader implementations.
        return null;
    }
}
