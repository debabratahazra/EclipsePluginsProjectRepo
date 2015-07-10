package com.zealcore.se.ui.actions;

import com.zealcore.se.ui.editors.GanttChart;

public class OpenGanttChartActionSet extends AbstractActionSetOpenEditor {

    public OpenGanttChartActionSet() {}

    protected String getBrowserId() {
        return GanttChart.BROWSER_ID;
    }

    protected String getName() {
        return GanttChart.NAME;
    }
}
