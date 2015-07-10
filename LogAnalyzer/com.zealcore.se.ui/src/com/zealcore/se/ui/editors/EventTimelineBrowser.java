package com.zealcore.se.ui.editors;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.layout.CellLayout;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ifw.EventQuery;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.StatisticQuery;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.ImageReportItem;
import com.zealcore.se.ui.graphics.Graphics;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.util.PropertySource;

@SuppressWarnings("restriction")
public final class EventTimelineBrowser extends AbstractLogsetBrowser implements
        IZoomable, IStepable, IReportContributor, IToggleable {

    private static final String CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE = "Changes between linear and logaritmic time base";

    private static final int DEFAULT_VIEW_WIDTH = 500;

    public static final String BROWSER_ID = "com.zealcore.se.ui.editors.TimelineBrowser";

    public static final String HELP_ID = "com.zealcore.se.ui.editors_TimelineBrowser";

    public static final String NAME = "Event Time Line";

    private static final int REPORT_WIDTH = 1024;

    private static final int REPORT_HEIGHT = 400;

    private static final double MAX_ZOOM_LEVEL = Integer.MAX_VALUE / 2;

    private static final double MIN_ZOOM_LEVEL = 0.001;

    private Canvas drawingArea;

    private final IEventTimeLineRenderer renderer = new EventTimeLineGraphics();

    private Object lastClickedObject;

    private final int viewWidth = EventTimelineBrowser.DEFAULT_VIEW_WIDTH;

    private MenuManager menuManager;

    private Menu menu;

    private String formatString = CommonConstants.DEFAULT_FORMAT_STRING;

    private final EventTimeLineData data = new EventTimeLineData();

    private EventQuery query;

    private List<ILogEvent> drawnEvents;

    private final IChangeListener importListener;

    private boolean isLog;

    private final UIJob refresher;

    private Button[] radioButtons;

    private TimingStatisticsAnalyzer analyzer;
    
    private final Image zoomOutImage;
    private final Image zoomInImage;

    public EventTimelineBrowser() {
        super();
        refresher = new AbstractSafeUIJob("EventTimelineBrowser_UIJob") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                if (drawingArea.isDisposed()) {
                    // TODO ugly fix
                    return Status.OK_STATUS;
                }
                drawingArea.redraw();
                
                return Status.OK_STATUS;
            }
        };
        refresher.setSystem(true);
        importListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (getLogset().getLogs().size() == 0) {
					if (query != null) {
						getLogset().removeQuery(query);
					}
					query = null;
                } else {
                    if (query == null) {
                        query = new EventQuery(getInput().getTimeCluster());
                        getLogset().addQuery(query);
                    }
                }
                
                refresh();                
            }
        };
        
        
        zoomInImage = IconManager.getImageDescriptor(IconManager.ZOOM_IN).createImage();
        zoomOutImage = IconManager.getImageDescriptor(IconManager.ZOOM_OUT).createImage();
    }

    public void setFocus() {
        drawingArea.setFocus();
    }

    public ImageDescriptor getImageDescriptor() {
        return IconManager.getImageDescriptor(IconManager.EVENT_SMALL_IMG);
    }

    private void draw(final PaintEvent e) {
        if (drawingArea.isDisposed()) {
            return;
        }
        drawTimeLine(Graphics.valueOf(e.gc), drawingArea.getClientArea());
    }

    void drawTimeLine(final IGraphics graphics, final Rectangle clientArea) {

        final ILogViewInput clusterInput = getInput();
        if (clusterInput == null || query == null) {
            analyzer.setEnabled(false);
            return;
        }

        drawnEvents = query.getEvents(viewWidth, viewWidth);
        
        long currentTime = clusterInput.getTimeCluster().getCurrentTime();
        
        if (drawnEvents == null || drawnEvents.size() < 1) {
            analyzer.setEnabled(false);
        } else {
            analyzer.setEnabled(true);
            if(currentTime == 0) {
                clusterInput.getTimeCluster().setCurrentTime(
                        Logset.valueOf(clusterInput.getLog().getId())
                                .getEventAtIndex(0).getTimeReference());
            }
        }
        
        StatisticQuery statsQuery = StatisticQuery.valueOf(getLogset());
        
        data.setTimeFormat(formatString);
        data.setCurrentTime(currentTime);
        data.setGc(graphics);
        data.setLogEvents(drawnEvents);
        data.setScreenSize(clientArea);
        data.setLog(isLogarithmic());
        data.setLogSpan(statsQuery.getMinTs(), statsQuery.getMaxTs());

        final IServiceProvider serviceProvider = getServiceProvider();
        final IEventColorProvider colorProvider = serviceProvider
                .getService(IEventColorProvider.class);

        data.setColorProvider(colorProvider);

        renderer.draw(data);
    }

    public void saveState(final IMemento savedState) {
        savedState.putString(CommonConstants.TAG_FORMAT_STRING, formatString);

        savedState.putString(CommonConstants.ZOOM_LEVEL_LOG,
                Double.toString(data.getZoomLevelLog()));
        savedState.putString(CommonConstants.ZOOM_LEVEL_LIN,
                Double.toString(data.getZoomLevelLin()));

        savedState.putString(CommonConstants.ZOOM_DISTANCE_IS_LOG,
                Boolean.toString(this.isLogarithmic()));
    }

    public void init(final IMemento savedState) {
        final String format = savedState
                .getString(CommonConstants.TAG_FORMAT_STRING);
        if (format != null) {
            formatString = format;
        }

        final String zoomLevelLog = savedState
                .getString(CommonConstants.ZOOM_LEVEL_LOG);
        if (zoomLevelLog != null) {
            data.setZoomLevelLog(Double.valueOf(zoomLevelLog));
        }

        final String zoomLevelLin = savedState
                .getString(CommonConstants.ZOOM_LEVEL_LIN);
        if (zoomLevelLin != null) {
            data.setZoomLevelLin(Double.valueOf(zoomLevelLin));
        }

        String s = savedState.getString(CommonConstants.ZOOM_DISTANCE_IS_LOG);
        final Boolean zoomIsLog = Boolean.parseBoolean(s);
        if (zoomIsLog != null) {
            data.setLog(zoomIsLog);
            this.setLogarithmic(zoomIsLog);
        }
    }

    protected void createViewControl(final Composite parent) {

        new Label(getZealBar(), SWT.NONE).setText("Format: ");
        final Text timeFormat = new Text(getZealBar(), SWT.BORDER);
        timeFormat.setText(formatString);
        timeFormat.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                formatString = timeFormat.getText();
                EventTimelineBrowser.this.refresh();
            }

        });
        timeFormat.setLayoutData(new RowData(
                CommonConstants.ZEALBAR_FORMAT_WIDTH, SWT.DEFAULT));

        drawingArea = new Canvas(parent, SWT.DOUBLE_BUFFERED);
        drawingArea.setBackground(Display.getCurrent().getSystemColor(
                SWT.COLOR_WHITE));
        
        //Listen to resize events and initialize the range selector with the new size of the
        //drawing area.
        drawingArea.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(ControlEvent e) {
                
                if(e.getSource() instanceof Canvas) {
                    Rectangle clientArea = drawingArea.getClientArea();
                    clientArea.width = (clientArea.width % 2) == 0 ? clientArea.width : clientArea.width - 1;
                    drawingArea.setBounds(clientArea);
                    
                    if (query == null
                            || query.getEvents(viewWidth, viewWidth).size() < 1) {
                        analyzer.setEnabled(false);
                    } else {
                        analyzer.setEnabled(true);
                    }
                    analyzer.initialize(0, drawingArea.getClientArea().width);                    
                }
            }
        });

        final IWorkbenchHelpSystem helpSystem = getSite().getWorkbenchWindow()
                .getWorkbench().getHelpSystem();
        helpSystem.setHelp(parent, EventTimelineBrowser.HELP_ID);
        this.makeActions();

        final Logset logset = getLogset();
        query = new EventQuery(getInput().getTimeCluster());
        logset.addQuery(query);

        Composite zoomComposite = new Composite(getZealBar(), SWT.NONE);
        zoomComposite.setLayout(new CellLayout(2).setMargins(10, 10));

        Label zoomLabel = new Label(getZealBar(), SWT.None);
        zoomLabel.setText("Zoom:");

        Button down = new Button(getZealBar(), SWT.PUSH);
        down.setImage(zoomOutImage);
        down.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
                zoomOut();
            }
        });

        Button up = new Button(getZealBar(), SWT.PUSH);
        up.setImage(zoomInImage);
        up.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
                zoomIn();
            }
        });

        Composite dummySpacer = new Composite(getZealBar(), SWT.NONE);
        dummySpacer.setLayout(new CellLayout(2).setMargins(10, 10));

        radioButtons = new Button[2];
        radioButtons[0] = new Button(getZealBar(), SWT.RADIO | SWT.RIGHT);
        radioButtons[0].setText("Linear time scale  ");
        radioButtons[0].setSelection(!isLog);
        radioButtons[0]
                .setToolTipText(CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE);
        radioButtons[0].addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(final SelectionEvent e) {}

            public void widgetSelected(final SelectionEvent e) {
                setLogarithmic(radioButtons[1].getSelection());
                refresh();
            }
        });
        radioButtons[1] = new Button(getZealBar(), SWT.RADIO | SWT.RIGHT);
        radioButtons[1].setText("Logaritmic time scale");
        radioButtons[1]
                .setToolTipText(CHANGES_BETWEEN_LINEAR_AND_LOGARITMIC_TIME_BASE);
        radioButtons[1].setSelection(isLog);
    }

    private void makeActions() {
        drawingArea.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent event) {
                try {
                    //draw animation
                   // analyzer.decorateSelectedArea(event.gc);
                    EventTimelineBrowser.this.draw(event);
                    analyzer.drawSelectors(event.gc);
                    
                    analyzer.updateStatistics(                       
                                ((EventTimeLineGraphics) (EventTimelineBrowser.this.renderer))
                                        .getRuler());
                    
                } catch (final RuntimeException e) {
                    SeUiPlugin.reportUnhandledRuntimeException(this.getClass(),
                            e, true);
                }
            }
        });

        // Set selection for property inspection on click
        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(final MouseEvent e) {
                if (getLogset().isLocked()) {
                    return;
                }
                lastClickedObject = renderer.getBoxedEventAt(e.x, e.y);
                if (lastClickedObject instanceof ITimed) {
                    final ITimed item = (ITimed) lastClickedObject;
                    EventTimelineBrowser.this.getInput().getTimeCluster()
                            .setCurrentTime(item.getTimeReference());
                } else {
                    EventTimelineBrowser.this.setSelection(new Object() {});
                }
            }

            @Override
            public void mouseUp(final MouseEvent e) {
                super.mouseUp(e);
                menuManager.setVisible(true);
            }

            @Override
            public void mouseDown(final MouseEvent e) {
                super.mouseDown(e);
                lastClickedObject = renderer.getBoxedEventAt(e.x, e.y);
                if (lastClickedObject instanceof IObject) {
                    final IObject item = (IObject) lastClickedObject;
                    EventTimelineBrowser.this.setSelection(item);
                } else {
                    EventTimelineBrowser.this.setSelection(new Object() {});
                }
            }
        });

        // Tooltip text for hovering on Events on the line
        drawingArea.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
            public void mouseHover(final MouseEvent e) {
                if (getLogset().isLocked()) {
                    return;
                }
                final ILogEvent event = renderer.getBoxedEventAt(e.x, e.y);
                if (event != null) {
                    drawingArea.setToolTipText(PropertySource.toString(event));
                    if (data.getObjectSnap() != event) {
                        data.setObjectSnap(event);
                        drawingArea.redraw();
                    }
                } else {
                    drawingArea.setToolTipText(null);
                    data.setObjectSnap(null);
                }
                drawingArea.redraw();
            }
        });

        drawingArea.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(final MouseEvent e) {
                if (getLogset().isLocked()) {
                    return;
                }
                final ILogEvent event = renderer.getBoxedEventAt(e.x, e.y);
                if (event != null) {
                    if (data.getObjectSnap() != event) {
                        data.setObjectSnap(event);
                        drawingArea.redraw();
                    }
                } else {
                    if (data.getObjectSnap() != null) {
                        data.setObjectSnap(null);
                        drawingArea.redraw();
                    }
                }
            }
        });

        final IServiceProvider serviceProvider = getServiceProvider();
        final IEventColorProvider colorProvider = serviceProvider
                .getService(IEventColorProvider.class);

        final IChangeListener colorListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (changed) {
                    EventTimelineBrowser.this.refresh();
                }
            }
        };
        colorProvider.addChangeListener(colorListener);
        drawingArea.addDisposeListener(new DisposeListener() {

            public void widgetDisposed(final DisposeEvent e) {
                colorProvider.removeChangeListener(colorListener);
            }
        });

        // Key adapter for stepping forward/backward and HOME/END keys.
        drawingArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!drawingArea.isFocusControl()) {
                    return;
                }
                
                if (e.stateMask == SWT.CONTROL) {
                    if ((e.keyCode == SWT.ARROW_UP)
                            || (e.keyCode == SWT.ARROW_DOWN)
                            || (e.keyCode == SWT.ARROW_LEFT)
                            || (e.keyCode == SWT.ARROW_RIGHT)) {
                        analyzer.handleKeyEvents(e);
                        return;
                    }
                }

                if (e.keyCode == SWT.ARROW_RIGHT) {
                    stepForward();
                }
                if (e.keyCode == SWT.ARROW_LEFT) {
                    stepBack();
                }

                if (e.character == '+'
                        || (e.stateMask == SWT.NONE && e.keyCode == 122)) {
                    zoomIn();
                }
                if (e.character == '-'
                        || (e.stateMask == SWT.SHIFT && e.keyCode == 122)) {
                    zoomOut();
                }
                final ITimeCluster viewset = getInput().getTimeCluster();
                // Get the logset to find out the event.
                Logset logset = getInput().getLogset();
                if (e.keyCode == SWT.HOME) {
                    viewset.setCurrentTime(viewset.getMin());
                    // Get the first event from the logset.
                    ILogEvent event = logset.getEventAtIndex(0);
                    // Make selection on first event to update property view
                    // with event detail.
                    setSelection(new PropertySource(event));
                } else if (e.keyCode == SWT.END) {
                    viewset.setCurrentTime(viewset.getMax());
                    // Get the last event from the logset.
                    ILogEvent event = logset.getEventAtIndex(logset
                            .getNumberOfEvents() - 1);
                    // Make selection on last event to update property view with
                    // event detail.
                    setSelection(new PropertySource(event));
                }
                super.keyPressed(e);
            }
        });
        createContextMenu();
    }

    /**
     * Creates the contextMenu. Requires drawingArea != null
     * 
     */
    private void createContextMenu() {
        menuManager = new MenuManager();

        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(final IMenuManager manager) {
                EventTimelineBrowser.this.makeActions(manager);
            }
        });

        menu = menuManager.createContextMenu(drawingArea);
        drawingArea.setMenu(menu);
        getSite().registerContextMenu(menuManager, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.ISynchable#synch(com.zealcore.se.ui.TimeEvent)
     */
    public void synch(final TimeEvent event) {
        if (getLogset().isLocked()) {
            return;
        }
        if (drawingArea.isDisposed()) {
            event.getSource().removeSynchable(this);
        }
        drawingArea.redraw();
    }

    public String getName() {
        return NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.ILogView#refresh()
     */
    public void refresh() {
        refresher.schedule(100);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IStepable#stepBack()
     */
    public void stepBack() {

        if (drawnEvents == null || drawnEvents.size() < 1) {
            return;
        }
        final long ts = getInput().getTimeCluster().getCurrentTime();
        ILogEvent nextEvent = drawnEvents.get(0);
        for (final ILogEvent event : drawnEvents) {
            if (event.getTs() >= ts) {
                break;
            }
            nextEvent = event;
        }

        final long nextTs = nextEvent.getTs();
        if (nextTs < ts) {
            this.setSelection(new PropertySource(nextEvent));
            setCurrentTime(nextTs);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IStepable#stepForward()
     */
    public void stepForward() {
        if (drawnEvents == null || drawnEvents.size() < 1) {
            return;
        }
        final long ts = getInput().getTimeCluster().getCurrentTime();
        ILogEvent nextEvent = drawnEvents.get(0);
        for (final ILogEvent event : drawnEvents) {
            if (event.getTs() > ts) {
                nextEvent = event;
                break;
            }
        }

        final long nextTs = nextEvent.getTs();
        if (nextTs > ts) {
            this.setSelection(new PropertySource(nextEvent));
            setCurrentTime(nextTs);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        drawingArea.dispose();
        getLogset().removeQuery(query);
        if(importListener != null) {
            IFWFacade.removeChangeListener(importListener);
        }
        zoomInImage.dispose();
        zoomOutImage.dispose();
        
        analyzer.dispose();
        
    }

    public void zoomIn() {
        data.setZoomLevel(Math.min(EventTimelineBrowser.MAX_ZOOM_LEVEL,
                data.getZoomLevel() * 1.25d));
        refresh();
    }

    public void zoomOut() {
        data.setZoomLevel(Math.max(EventTimelineBrowser.MIN_ZOOM_LEVEL,
                data.getZoomLevel() / 1.25d));
        refresh();
    }

    /**
     * Make the actions for the view menu manager
     * 
     * @param manager
     */
    private void makeActions(final IMenuManager manager) {
        if (lastClickedObject instanceof ILogEvent) {
            final ILogEvent abstractLogEvent = (ILogEvent) lastClickedObject;
            manager.add(new Action("Goto") {
                @Override
                public void run() {
                    EventTimelineBrowser.this.getInput().getTimeCluster()
                            .setCurrentTime(abstractLogEvent.getTs());
                }
            });
        }
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    public void fillReport(final AbstractReport report) {

        final Rectangle rectangle = new Rectangle(0, 0,
                EventTimelineBrowser.REPORT_WIDTH,
                EventTimelineBrowser.REPORT_HEIGHT);
        final Image image = new Image(getSite().getShell().getDisplay(),
                rectangle);
        final IGraphics imageGc = Graphics.valueOf(image);
        drawTimeLine(imageGc, rectangle);

        final ImageReportItem item = new ImageReportItem(image.getImageData());
        item.setName(getTitle());
        item.setDescription(getTitleToolTip());
        image.dispose();
        report.addReportData(item);
    }

    public void createControl(final Composite parent) {
        createViewControl(parent);
        analyzer = new TimingStatisticsAnalyzer(this, drawingArea);
        analyzer.createTimingAnalysisControls(parent);
        IFWFacade.addChangeListener(importListener);
    }

    public boolean isLogarithmic() {
        return isLog;
    }

    public void setLogarithmic(final boolean isLog) {
        this.isLog = isLog;

    }

    public void toggleScale() {
        this.isLog = !this.isLog;
    }
}
