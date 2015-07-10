package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.GanttChart;

public class OpenGanttChartAction extends AbstractOpenBrowserAction {

    public OpenGanttChartAction() {}

    @Override
    protected String getBrowserId() {
        return GanttChart.BROWSER_ID;
    }
}
