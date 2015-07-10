package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.TextBrowser;

public class OpenTextBrowserActionSet extends AbstractActionSetOpenEditor {

    public OpenTextBrowserActionSet() {}

    protected String getBrowserId() {
        return TextBrowser.BROWSER_ID;
    }

    protected String getName() {
        return TextBrowser.NAME;
    }

}
