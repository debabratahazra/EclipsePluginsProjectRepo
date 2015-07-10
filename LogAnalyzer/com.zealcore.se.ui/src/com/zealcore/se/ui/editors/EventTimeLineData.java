package com.zealcore.se.ui.editors;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Rectangle;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.IDistanceTransformer;
import com.zealcore.se.ui.internal.LogaritmicDistanceTransformer;

class EventTimeLineData {
    private List<ILogEvent> logEvents;

    private IEventColorProvider colorProvider;

    private Rectangle screenSize;

    private IGraphics gc;

    private long currentTime;

    private double logZoomLevel = 100;

    private double linZoomLevel = 100;

    private ILogEvent objectSnap;

    private String timeFormat = CommonConstants.DEFAULT_FORMAT_STRING;
    
    private boolean log;

    private Span logspan;

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(final String timeFormat) {
        this.timeFormat = timeFormat;
    }

    private Map<ILogEvent, Rectangle> outScreenCoord;

    long getCurrentTime() {
        return currentTime;
    }

    void setCurrentTime(final long currentTime) {
        this.currentTime = currentTime;
    }

    IGraphics getGc() {
        return gc;
    }

    void setGc(final IGraphics gc) {
        this.gc = gc;
    }

    List<ILogEvent> getLogEvents() {
        return logEvents;
    }

    void setLogEvents(final List<ILogEvent> logEvents) {
        this.logEvents = logEvents;
    }

    ILogEvent getObjectSnap() {
        return objectSnap;
    }

    void setObjectSnap(final ILogEvent objectSnap) {
        this.objectSnap = objectSnap;
    }

    Map<ILogEvent, Rectangle> getOutScreenCoord() {
        return outScreenCoord;
    }

    void setOutScreenCoord(final Map<ILogEvent, Rectangle> outScreenCoord) {
        this.outScreenCoord = outScreenCoord;
    }

    Rectangle getScreenSize() {
        return screenSize;
    }

    void setScreenSize(final Rectangle screenSize) {
        this.screenSize = screenSize;
    }

    IEventColorProvider getColorProvider() {
        return colorProvider;
    }

    void setColorProvider(final IEventColorProvider colorProvider) {
        this.colorProvider = colorProvider;
    }

    
    public void setLog(final boolean log) {
        this.log = log;
    }

    public boolean isLog() {
        return log;
    }

    public IDistanceTransformer getTransform() {

        if (isLog()) {
            return new LogaritmicDistanceTransformer(
                    CommonConstants.LOG_TRANSFORM, getZoomLevel());

        }
        return new IDistanceTransformer() {
            public double transform(final double delta) {
                return (delta / 1000000) * 2 * getZoomLevel();
            }
        };
    }

    public double getZoomLevel() {
        if (isLog()) {
            return logZoomLevel;
        }
        return linZoomLevel;
    }

    public void setZoomLevel(final double zoomLevel) {
        if (isLog()) {
            logZoomLevel = zoomLevel;
        } else {
            linZoomLevel = zoomLevel;
        }
    }

    public void setZoomLevelLog(final Double zoomLevel) {
        logZoomLevel = zoomLevel;

    }

    public void setZoomLevelLin(final Double zoomLevel) {
        linZoomLevel = zoomLevel;

    }

    public double getZoomLevelLog() {
        return logZoomLevel;
    }

    public double getZoomLevelLin() {
        return linZoomLevel;
    }
    
    public void setLogSpan(final long start, final long end) {
        logspan = Span.valueOf(start, end);
    }

    public Span getLogspan() {
        return logspan;
    }
}
