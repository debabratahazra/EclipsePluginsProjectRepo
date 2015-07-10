package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.IQueryFilter;
import com.zealcore.se.core.ifw.GanttOverviewQuery;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.StatisticQuery;
import com.zealcore.se.core.ifw.TaskQuery;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.util.SimpleScaler;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.ImageReportItem;
import com.zealcore.se.ui.editors.GanttChart.TaskFilter;
import com.zealcore.se.ui.editors.GanttRenderingData.TaskByPrioritySort;
import com.zealcore.se.ui.internal.TimeEvent;

public final class GanttOverviewChart extends AbstractLogsetBrowser implements
IReportContributor, IQueryFilter, IToggleable {

    private static final int INDRAG = 2;

    private static final String NAME = "Gantt Chart Overview";

    private static final int DEFAULT_ZOOM_LEVEL = 1;

    public static final String BROWSER_ID = "com.zealcore.se.ui.views.ZGanttOverviewChart";

    public static final String HELP_ID = "com.zealcore.se.ui.editors_GanttOverviewChart";

    private static final int REPORT_WIDTH = 1024;

    private static final int REPORT_HEIGHT = 400;

    private boolean isLog = true;

    private final ClassicGantOverviewRenderer renderer = new ClassicGantOverviewRenderer();

    private final GanttRenderingData renderingData = new GanttRenderingData();

    private Canvas drawingArea;

    private ScrolledComposite scroll;

    private final Set<ILogBrowserContribution> possibleContributors = new LinkedHashSet<ILogBrowserContribution>();

    private GanttOverviewDraw ganttDraw;
    
    private GanttOverviewQuery query;

    private final IChangeListener importListener;

    private final IChangeListener artifactColorChangeListener;

    private TaskQuery taskQuery;

    private Image image;

    private Image scaledImage;

    private boolean recreateImage;

    private SimpleScaler sRuler;

    private double imageScale = 1.0;

    private final UIJob refresher;

    private boolean refresh;

    public GanttOverviewChart() {
        super();
        refresher = new AbstractSafeUIJob("GanttChartOverview_UIJob") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                if ((drawingArea == null) || drawingArea.isDisposed()) {
                    // TODO ugly fix
                    return Status.OK_STATUS;
                }
                drawingArea.redraw();
                refresh = true;
                return Status.OK_STATUS;
            }
        };
        refresher.setSystem(true);

        renderingData.setMarkerVisibile(true);
        renderingData.setTaskSorter(new TaskByPrioritySort());
        renderingData.setZoomLevel(GanttOverviewChart.DEFAULT_ZOOM_LEVEL);

        importListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (changed) {
                    if (getLogset().getLogs().size() == 0) {
                        query = null;
                    } else {
                        if (query == null) {
                            query = new GanttOverviewQuery(getInput()
                                    .getTimeCluster(), sRuler);
                            getLogset().addQuery(query);
                        }
                    }
                }
                refresh();
            }
        };
        
        artifactColorChangeListener = new IChangeListener() {
            public void update(final boolean changed) {
                recreateImage = true;
                drawingArea.redraw();
            }
        };
        IFWFacade.addChangeListener(importListener);
    }

    public ImageDescriptor getImageDescriptor() {
        return IconManager.getImageDescriptor(IconManager.GANTT_SMALL);
    }

    void addContributor(final ILogBrowserContribution contribution) {
        possibleContributors.add(contribution);
    }

    void draw(final PaintEvent e, final GC graphics, final Rectangle bound) {
        // Pre Conditions
        if (getInput() == null) {
            return;
        }
        if (graphics == null) {
            throw new IllegalArgumentException("Graphics may NOT be null");
        }

        if (drawingArea.isDisposed()) {
            return;
        }

        taskQuery = TaskQuery.valueOf(getInput().getLogset());
        long firstDuration = taskQuery.getFirstTs();
        long lastDuration = taskQuery.getLastTs();

        Span span;

        if (firstDuration != 0 && lastDuration != 0) {
            span = Span.valueOf(firstDuration, lastDuration);
        } else {
            span = Span.valueOf(new IDuration() {

                public boolean contains(final long value) {
                    return false;
                }

                public long getDurationTime() {
                    return 0;
                }

                public long getEndTime() {
                    return 0;
                }

                public IArtifact getOwner() {
                    return null;
                }

                public long getStartTime() {
                    return 0;
                }

                public Long getTimeReference() {

                    return 0L;
                }

                public int compareTo(final Object arg0) {
                    return 0;
                }

                public LogFile getLogFile() {
                    return null;
                }

                public IType getType() {
                    return null;
                }

                public List<SEProperty> getZPropertyAnnotations() {
                    return null;
                }

                public Collection<IArtifact> referencedArtifacts() {
                    return null;
                }

                public void setLogFile(final LogFile logFile) {
                }

                public void substitute(final IArtifact oldArtifact,
                        final IArtifact newArtifact) {
                }
            });
        }
        sRuler = new SimpleScaler(span, Span.valueOf(0, this.drawingArea.getSize().x));
        drawChart(e, graphics, bound);
    }

    private void drawChart(final PaintEvent e, final GC graphics, final Rectangle bound) {


            //renderingData.setDurations(filterCollection(getExecutions(), getFilters()));
            
            renderingData.setColorMap(getInput().getLog().getColorMap());
            renderingData.setBounds(bound);

            renderingData.setGraphics(graphics);

            final StatisticQuery stat = StatisticQuery.valueOf(getInput()
                    .getLogset());

            renderer.setRuler(sRuler);

            renderingData.setTasks(new ArrayList<ITask>(taskQuery
                    .getFilteredTasks()));

            renderingData.setLogSpan(stat.getMinTs(), stat.getMaxTs());
            renderingData.setLog(isLogarithmic());


            ganttDraw = renderer.draw( drawingArea, getSite().getShell().getDisplay(), renderingData, getLogset(), sRuler);

           // ganttDraw.paint();
            Point drawnSize = renderingData.drawnSize();
            if (drawnSize != null) {
                final int minHeight = drawnSize.y;
                if (minHeight != scroll.getMinHeight()) {
                    scroll.setMinHeight(minHeight);
                }
            }
            applyDecorations(ganttDraw);
            
    }

    private void applyDecorations(final GanttOverviewDraw figure) {
        for (final ILogBrowserContribution contribution : possibleContributors) {
            contribution.decorateFigures(figure.getGraphics(), figure);
        }
    }

    /**
     * Tracks and executes logic based on mouse events (click on durations)
     * 
     * @author stch
     * 
     */
    private final class GantMouseListener extends MouseAdapter implements
    MouseMoveListener, MouseTrackListener {

        private static final int LEFT_MOUSE_BUTTON = 1;

        @Override
        public void mouseDoubleClick(final MouseEvent e) {
            if (getLogset().isLocked()) {
                return;
            }
            if (e.button == GantMouseListener.LEFT_MOUSE_BUTTON) {
                long ts2 = (long) (sRuler.toTimestamp(e.x, imageScale));
                getInput().getTimeCluster().setCurrentTime(ts2);
            }
        }

        public void mouseMove(final MouseEvent e) {}

        public void mouseEnter(final MouseEvent e) {}

        public void mouseExit(final MouseEvent e) {}

        public void mouseHover(final MouseEvent e) {}
    }

    public void saveState(final IMemento savedState) {
        savedState.putString(CommonConstants.TAG_FORMAT_STRING, renderingData
                .getFormatString());

        savedState.putString(CommonConstants.ZOOM_LEVEL_LOG, Double
                .toString(renderingData.getZoomLevelLog()));
        savedState.putString(CommonConstants.ZOOM_LEVEL_LIN, Double
                .toString(renderingData.getZoomLevelLin()));

        savedState.putString(CommonConstants.ZOOM_DISTANCE_IS_LOG, Boolean
                .toString(this.isLogarithmic()));
    }

    public void init(final IMemento savedState) {
        final String format = savedState
        .getString(CommonConstants.TAG_FORMAT_STRING);
        if (format != null) {
            renderingData.setFormatString(format);
        }

        final String zoomLevelLog = savedState
        .getString(CommonConstants.ZOOM_LEVEL_LOG);
        if (zoomLevelLog != null) {
            renderingData.setZoomLevelLog(Double.valueOf(zoomLevelLog));
        }

        final String zoomLevelLin = savedState
        .getString(CommonConstants.ZOOM_LEVEL_LIN);
        if (zoomLevelLin != null) {
            renderingData.setZoomLevelLin(Double.valueOf(zoomLevelLin));
        }

        String s = savedState.getString(CommonConstants.ZOOM_DISTANCE_IS_LOG);
        final Boolean zoomIsLog = Boolean.parseBoolean(s);
        if (zoomIsLog != null) {
            renderingData.setLog(zoomIsLog);
            this.setLogarithmic(zoomIsLog);
        }
    }

    public void createControl(final Composite parent) {
        /* | SWT.H_SCROLL */
        scroll = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
        // scroll.setMinWidth(1900);
        scroll.setLayout(new FillLayout());

        drawingArea = new Canvas(scroll, SWT.DOUBLE_BUFFERED);

        //

        // setHelp(drawingArea, GanttOverviewChart.HELP_ID);
        //
        scroll.setContent(drawingArea);
        scroll.setExpandHorizontal(true);
        scroll.setExpandVertical(true);

        // drawingArea = new Canvas(scroll, SWT.DOUBLE_BUFFERED);
        drawingArea.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

        final GantMouseListener gantMouseListener = new GantMouseListener();
        drawingArea.addMouseListener(gantMouseListener);

        drawingArea.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent e) {

                if (e.count > 0) {
                    // Frameskipping - there are pending draws
                    return;
                }
                if (refresh 
                        || image == null
                        || recreateImage) {
                    
                    refresh = false;
                    taskQuery = TaskQuery.valueOf(getInput().getLogset());
                    final Rectangle bounds = new Rectangle(0, 0,
                            (int) drawingArea.getSize().x, 
                            ClassicGantOverviewRenderer.LANE_START 
                            + taskQuery.getExecutedTasks().size() * ClassicGantOverviewRenderer.LANE_HEIGHT);
                    if (image != null) {
                    	image.dispose();
                    }
                    image = new Image(getSite().getShell().getDisplay(), bounds);
                    GC tempGC = new GC(image);
                    draw(e, tempGC, bounds);
                    if(tempGC != null){
                    	tempGC.dispose();
                    }
                    e.gc.drawImage(image, 1, 0);    
                    imageScale = 1.0;
                    
                } else {
                    if (scaledImage == null
                            || (scaledImage.getBounds().width) != drawingArea.getSize().x - INDRAG) {
                        if (scaledImage != null) {
                            // Dispose image before creating a new
                            scaledImage.dispose();
                        }
                        imageScale = (double) (image.getBounds().width - INDRAG) / (double) drawingArea.getSize().x;


                        scaledImage = new Image(getSite().getShell().getDisplay(), 
                        		image.getImageData().scaledTo(drawingArea.getSize().x, image.getBounds().height));
                    }
                    e.gc.drawImage(scaledImage, 1, 0);
                }
                if(ganttDraw.getGraphics() != null){
                	drawMarker(e.gc);
                }
            }
        });

        if (null != getInput()) {
            getInput().getLog().getColorMap().addChangeListener(
                    artifactColorChangeListener);
        }
    }

    protected void drawMarker(final GC gc) {
        // draw marker
        long currentTime = (long) getInput().getTimeCluster().getCurrentTime();
        renderingData.setMarker(currentTime);
        Collection<ITask> executedTasks = taskQuery.getExecutedTasks();
        if (executedTasks != null && executedTasks.size() > 0) {
            renderer.setRuler(sRuler);
            renderer.drawMarker(gc, renderingData, imageScale,
                    drawingArea.getSize().x - INDRAG);
        }        
    }

    @Override
    public void dispose() {
        drawingArea.dispose();
        if (scaledImage != null) {
            scaledImage.dispose();
        }
        if (image != null) {
            image.dispose();
        }
        getInput().getLog().getColorMap().removeChangeListener(
                artifactColorChangeListener);
        IFWFacade.removeChangeListener(importListener);
    }

    public void refresh() {
    	refresher.schedule(100);
    }

    public static String getNextViewId() {
        return GanttOverviewChart.BROWSER_ID + "_" + System.currentTimeMillis();
    }

    public void synch(final TimeEvent event) {
        if (drawingArea.isDisposed()) {

            event.getSource().removeSynchable(this);
            return;
        }
        drawingArea.redraw();
    }

    /*private List<ITaskDuration> getExecutions() {
        return query.getExecutions();
    }*/
    
    public void fillReport(final AbstractReport report) {
        final Rectangle rectangle = new Rectangle(0, 0,
                GanttOverviewChart.REPORT_WIDTH,
                GanttOverviewChart.REPORT_HEIGHT);
        final Image img = new Image(getSite().getShell().getDisplay(),
                rectangle);
        final GC gc = new GC(img);
        draw(null, gc, rectangle);
        gc.dispose();
        final ImageReportItem item = new ImageReportItem(img.getImageData());
        item.setName(getTitle());
        item.setDescription(getTitleToolTip());
        img.dispose();
        report.addReportData(item);
    }

    public void setFocus() {
        drawingArea.setFocus();
    }

    public String getName() {
        return GanttOverviewChart.NAME;
    }

    public Collection<IFilter<IObject>> getFilter() {
        final List<IFilter<IObject>> tasks = new ArrayList<IFilter<IObject>>();
        for (final ITask iTask : taskQuery.getExecutedTasks()) {
            tasks.add(new TaskFilter(iTask));
        }
        for (final ITask iTask : taskQuery.getNotExecutedTasks()) {
            tasks.add(new TaskFilter(iTask));
        }
        return tasks;
    }

    public Collection<IFilter<IObject>> getFilterSelections() {
        Collection<IFilter<IObject>> filter = taskQuery.getFilter();
        return filter;
    }

    public void setFilter(final Collection<IFilter<IObject>> newFilters) {
        taskQuery.setFilter(newFilters);
        GanttOverviewChart.this.refresh();
        recreateImage = true;
    }

    public boolean isLogarithmic() {
        return this.isLog;
    }

    public void setLogarithmic(final boolean isLog) {
        this.isLog = isLog;
    }

    public void toggleScale() {
        this.isLog = !this.isLog;
    }
}
