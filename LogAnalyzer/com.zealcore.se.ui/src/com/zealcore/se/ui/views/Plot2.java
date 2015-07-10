
package com.zealcore.se.ui.views;

import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;

public class Plot2 {
    private ILogSessionWrapper uiLogset;

    private PlotSearchQuery query;

    private PlotType plotType;

    public Plot2(final ILogSessionWrapper uiLogset,
            final PlotSearchQuery query, final PlotType plotType) {
        this.uiLogset = uiLogset;
        this.query = query;
        this.plotType = plotType;
    }

    public PlotSearchQuery getQuery() {
        return query;
    }

    public PlotType getPlotType() {
        return plotType;
    }

    public ILogSessionWrapper getLogset() {
        return uiLogset;
    }
}

