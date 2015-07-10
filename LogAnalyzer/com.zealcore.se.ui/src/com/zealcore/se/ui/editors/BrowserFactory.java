package com.zealcore.se.ui.editors;

public final class BrowserFactory {

    private BrowserFactory() {

    }

    public static ILogsetBrowser valueOf(final String id) {

        if (id != null) {
            if (id.equals(GanttChart.BROWSER_ID)) {
                return new GanttChart();
            }
            if (id.equals(GanttOverviewChart.BROWSER_ID)) {
                return new GanttOverviewChart();
            }
            if (id.equals(EventTimelineBrowser.BROWSER_ID)) {
                return new EventTimelineBrowser();
            }
            if (id.equals(TextBrowser.BROWSER_ID)) {
                return new TextBrowser();
            }
            if (id.equals(SequenceDiagram.VIEW_ID)) {
                return new SequenceDiagram();
            }
        }
        return new NullBrowser("Unable to create browser-id: " + id);
    }
}
