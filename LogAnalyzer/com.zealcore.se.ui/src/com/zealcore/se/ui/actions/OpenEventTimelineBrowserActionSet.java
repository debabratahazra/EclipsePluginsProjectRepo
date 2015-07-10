package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.EventTimelineBrowser;

public class OpenEventTimelineBrowserActionSet extends AbstractActionSetOpenEditor {

    public OpenEventTimelineBrowserActionSet() {}

    protected String getBrowserId() {
        return EventTimelineBrowser.BROWSER_ID;
    }

    protected String getName() {
        return EventTimelineBrowser.NAME;
    }
}
