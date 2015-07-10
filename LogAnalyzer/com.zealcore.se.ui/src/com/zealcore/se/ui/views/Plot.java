package com.zealcore.se.ui.views;

import java.util.Collection;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;

public class Plot {
    private ILogSessionWrapper uiLogset;

    private Collection<IObject> data;

    private PlotType plotType;

    public Plot(final ILogSessionWrapper uiLogset,
            final Collection<IObject> data, final PlotType plotType) {
        this.uiLogset = uiLogset;
        this.data = data;
        this.plotType = plotType;
    }

    public Collection<IObject> getData() {
        return data;
    }

    public PlotType getPlotType() {
        return plotType;
    }

    public ILogSessionWrapper getLogset() {
        return uiLogset;
    }
}
