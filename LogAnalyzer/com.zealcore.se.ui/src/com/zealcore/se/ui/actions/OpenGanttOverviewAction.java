package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.GanttOverviewChart;

public class OpenGanttOverviewAction extends AbstractOpenBrowserAction {

    public OpenGanttOverviewAction() {}

    @Override
    protected String getBrowserId() {
        return GanttOverviewChart.BROWSER_ID;
    }
}
