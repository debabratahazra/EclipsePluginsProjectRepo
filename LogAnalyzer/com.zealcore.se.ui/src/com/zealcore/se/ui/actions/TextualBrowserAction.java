package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.TextBrowser;

public class TextualBrowserAction extends AbstractOpenBrowserAction {

    @Override
    protected String getBrowserId() {
        return TextBrowser.BROWSER_ID;
    }

}
