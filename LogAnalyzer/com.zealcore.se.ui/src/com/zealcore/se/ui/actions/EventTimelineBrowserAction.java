package com.zealcore.se.ui.actions;

public class EventTimelineBrowserAction extends AbstractOpenBrowserAction {

    public EventTimelineBrowserAction() {}

    @Override
    protected String getBrowserId() {
        return com.zealcore.se.ui.editors.EventTimelineBrowser.BROWSER_ID;
    }

}
