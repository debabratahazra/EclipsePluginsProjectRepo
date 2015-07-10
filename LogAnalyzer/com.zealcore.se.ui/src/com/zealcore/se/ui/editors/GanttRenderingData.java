package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.internal.IDistanceTransformer;
import com.zealcore.se.ui.internal.LogaritmicDistanceTransformer;
import com.zealcore.se.ui.util.ArtifactColorMap;

/**
 * Data structure used as parameter to the ZGantRenderer
 * 
 * @author stch
 * 
 */
final class GanttRenderingData {
    /** OUT variable: the size of the rendered graphics */
    private Point size;

    /** IN variable: durations to draw */
    private List<ITaskDuration> durations;

    private SortedSet<ITask> tasks;

    private Comparator<ITask> taskSorter;

    private IEventColorProvider colorProvider;

    private long marker;

    private boolean markerVisibile = true;

    private Rectangle bounds;

    private GC graphics;

    private Object markerAt;

    /* Should be same as the one for logfile */
    private ArtifactColorMap colorMap;

    private String formatString = CommonConstants.DEFAULT_FORMAT_STRING;

    private double logZoomLevel = 100;

    private double linZoomLevel = 100;

    private Span logspan;

    private int scrollOffset;

    private int sashXPos;

    /**
     * @param durations
     *            the durations to set
     */
    void setDurations(final List<ITaskDuration> durations) {
        this.durations = durations;
    }

    /**
     * @return the durations
     */
    List<ITaskDuration> getDurations() {
        return durations;
    }

    long getWidth() {
        return getEnd() - getStart();
    }

    /**
     * @return the start
     */
    private long getStart() {
        if (getDurations().size() == 0) {
            return 0;
        }
        return getDurations().get(0).getStartTime();

    }

    /**
     * @return the end
     */
    private long getEnd() {
        if (getDurations().size() == 0) {
            return 0;
        }
        final ITaskDuration last = getDurations()
                .get(getDurations().size() - 1);
        return last.getStartTime() + last.getDurationTime();
    }

    /**
     * @param marker
     *            the marker to set
     * @param scale
     */
    void setMarker(final long marker) {
        this.marker = marker;
    }

    /**
     * @return the marker
     */
    long getMarker() {
        return marker;
    }

    /**
     * @param markerVisibile
     *            the markerVisibile to set
     */
    void setMarkerVisibile(final boolean markerVisibile) {
        this.markerVisibile = markerVisibile;
    }

    /**
     * @return the markerVisibile
     */
    boolean isMarkerVisibile() {
        return markerVisibile;
    }

    /**
     * @param bounds
     *            the bounds to set
     */
    void setBounds(final Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * @return the bounds
     */
    Rectangle getBounds() {
        return bounds;
    }

    /**
     * @param graphics
     *            the graphics to set
     */
    void setGraphics(final GC graphics) {
        this.graphics = graphics;
    }

    /**
     * @return the graphics
     */
    GC getGraphics() {
        return graphics;
    }

    IEventColorProvider getColorProvider() {
        return colorProvider;
    }

    void setColorProvider(final IEventColorProvider colorProvider) {
        this.colorProvider = colorProvider;
    }

    /**
     * @param markerAt
     *            the markerAt to set
     */
    void setMarkerAt(final Object markerAt) {
        this.markerAt = markerAt;
    }

    /**
     * @return the markerAt
     */
    Object getMarkerAt() {
        return markerAt;
    }

    /**
     * @param colorMap
     *            the colorMap to set
     */
    void setColorMap(final ArtifactColorMap colorMap) {
        this.colorMap = colorMap;
    }

    /**
     * @return the colorMap
     */
    ArtifactColorMap getColorMap() {
        return colorMap;
    }

    /**
     * @param size
     *            the size to set
     */
    void setDrawnSize(final Point size) {
        this.size = size;
    }

    /**
     * @return the size
     */
    Point drawnSize() {
        return size;
    }

    public Comparator<ITask> getTaskSorter() {
        return taskSorter;
    }

    public void setTaskSorter(final Comparator<ITask> taskSorter) {
        this.taskSorter = taskSorter;
        tasks = new TreeSet<ITask>(getTaskSorter());
    }

    void setFormatString(final String formatString) {
        this.formatString = formatString;
    }

    String getFormatString() {
        return formatString;
    }

    private boolean log;

    private List<ILogEvent> events;

    private boolean doDrawEvents;

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
                return delta / 1000000 * 2 * getZoomLevel();
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

    public void setLogSpan(final long start, final long end) {
        logspan = Span.valueOf(start, end);
    }

    public Span getLogspan() {
        return logspan;
    }

    public void setEvents(final List<ILogEvent> events) {
        this.events = events;
    }

    public List<ILogEvent> getEvents() {
        return events;
    }

    public void setDoDrawEvents(final boolean doDrawEvents) {
        this.doDrawEvents = doDrawEvents;
    }

    public boolean isDoDrawEvents() {
        return doDrawEvents;
    }

    public double getZoomLevelLog() {
        return logZoomLevel;
    }

    public double getZoomLevelLin() {
        return linZoomLevel;
    }

    public void setZoomLevelLog(final Double logZoomLevel) {
        this.logZoomLevel = logZoomLevel;
    }

    public void setZoomLevelLin(final Double linZoomLevel) {
        this.linZoomLevel = linZoomLevel;
    }

    public List<ITask> getTasks() {
        return new ArrayList<ITask>(tasks);
    }

    public void setTasks(final List<ITask> tasks) {
        // for (ITask task : tasks) {
        // this.tasks.add(task);
        // }
        if (this.tasks != null) {
            this.tasks.clear();
            this.tasks.addAll(tasks);
        }
    }

    public void setScrollOffset(final int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setSashXPos(final int sashXPos) {
        this.sashXPos = sashXPos;
    }

    public int getSashXPos() {
        return sashXPos;
    }

    static class TaskByNameSort implements Comparator<ITask> {
        public int compare(final ITask o1, final ITask o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    static class TaskBySwitchInSort implements Comparator<IArtifact> {
        private final List<IArtifact> taskOrder = new ArrayList<IArtifact>();

        TaskBySwitchInSort(final List<ITaskDuration> durations) {
            for (final ITaskDuration duration : durations) {
                if (!taskOrder.contains(duration.getOwner())) {
                    taskOrder.add(duration.getOwner());
                }
            }
        }

        public int compare(final IArtifact o1, final IArtifact o2) {
            return taskOrder.indexOf(o1) - taskOrder.indexOf(o2);
        }
    }

    static class TaskByPrioritySort implements Comparator<ITask> {
        public int compare(final ITask o1, final ITask o2) {
            if (o1.getPriority() > o2.getPriority()) {
                return 1;
            }
            if (o1.getPriority() < o2.getPriority()) {
                return -1;
            }
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
}
