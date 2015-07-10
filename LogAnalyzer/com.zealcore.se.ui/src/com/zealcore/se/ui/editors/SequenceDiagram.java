package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.internal.layout.CellLayout;
import org.eclipse.ui.progress.UIJob;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.IQueryFilter;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.SequenceQuery;
import com.zealcore.se.core.ifw.TaskQuery;
import com.zealcore.se.core.ifw.TrackingQuery;
import com.zealcore.se.core.ifw.SequenceQuery.Data;
import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.ImageReportItem;
import com.zealcore.se.ui.dialogs.SelectOrderDialog;
import com.zealcore.se.ui.editors.GanttChart.TaskFilter;
import com.zealcore.se.ui.editors.SequenceDiagramRenderer.SequenceRenderingData;
import com.zealcore.se.ui.graphics.Graphics;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.ArtifactID;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.util.ArtifactColorMap;
import com.zealcore.se.ui.util.ArtifactSelection;
import com.zealcore.se.ui.util.PropertySource;

public class SequenceDiagram extends AbstractLogsetBrowser implements
        IStepable, IZoomable, IReportContributor, IQueryFilter {

    private static final String UNLOCK = "Unlock Order";

    private static final String LOCK = "Lock Order";

    public static final String NAME = "Sequence Diagram";

    public static final String VIEW_ID = "com.zealcore.se.ui.views.SequenceDiagramView";

    public static final String HELP_ID = "com.zealcore.se.ui.editors_SequenceDiagramBrowser";

    private static final int HUNDRED_PERCENT = 100;

    private static final int MOUSE_FIRST = 1;

    private static final int MOUSE_THIRD = 3;

    private static final int DEFAULT_VIEW_WINDOW = 1000;

    private static final int REPORT_MIN_HEIGHT = 600;

    private boolean isLocked;

    private final int viewWindow = SequenceDiagram.DEFAULT_VIEW_WINDOW;

    private final SequenceDiagramRenderer renderer = new SequenceDiagramRenderer();

    private Canvas canvas;

    private final Set<ISequenceMember> selectedItems = new LinkedHashSet<ISequenceMember>();

    private ScrolledComposite scroll;

    static final String SEQUENCE_DIAGRAM_SELECTED_ITEMS_ORDER = "selected_items_order";

    private static final String GENERIC_RECEIVE_EVENT = "class com.zealcore.se.core.model.generic.GenericReceiveEvent";

    private MenuManager menuManager;

    private SequenceQuery query;

    private ISequenceMember tracked;

    private final IChangeListener importListener;

    private TrackingQuery tracker;

    private Button lockButton;

    private Button compactActors;

    private Label spacingBetweenObjects;

    private Collection<IFilter<IObject>> filterOutThese = new ArrayList<IFilter<IObject>>();

    private final UIJob refresher;
    
    private final Image zoomOutImage;
    private final Image zoomInImage;

    /**
     * The Constructor.
     */
    public SequenceDiagram() {
        refresher = new AbstractSafeUIJob("GanttChart_UIJob") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                if (canvas.isDisposed()) {
                    // TODO ugly fix.
                    return Status.OK_STATUS;
                }
                canvas.redraw();
                return Status.OK_STATUS;
            }
        };
        refresher.setSystem(true);

        importListener = new IChangeListener() {
            /*
             * Updates the editor when logfile is deconfigured and there is not
             * data to display
             */
            public void update(final boolean changed) {
                if (getLogset().getLogs().size() == 0) {
                    query = null;
                } else {
                    if (query == null) {
                        query = new SequenceQuery(getSequence(), getInput()
                                .getTimeCluster());
                        getLogset().addQuery(query);
                    }
                }
                refresh();
            }
        };
        zoomInImage = IconManager.getImageDescriptor(IconManager.ZOOM_IN).createImage();
		zoomOutImage = IconManager.getImageDescriptor(IconManager.ZOOM_OUT).createImage(); 
    }

    public ImageDescriptor getImageDescriptor() {
        return IconManager.getImageDescriptor(IconManager.SEQUENCE_SMALL);
    }

    public void saveState(final IMemento savedState) {
        savedState.putString(CommonConstants.TAG_MATCH_FILTER, this.renderer
                .getData().getFilterText());

        savedState.putString(CommonConstants.ZOOM_LEVEL_LOG,
                Double.toString(renderer.getData().getZoomLevelLog()));
        savedState.putString(CommonConstants.ZOOM_LEVEL_LIN,
                Double.toString(renderer.getData().getZoomLevelLin()));

        for (final ISequenceMember member : this.selectedItems) {
            final IMemento entryMem = savedState
                    .createChild(SequenceDiagram.SEQUENCE_DIAGRAM_SELECTED_ITEMS_ORDER);
            final ArtifactID id = ArtifactID.valueOf(member);
            id.save(entryMem);
        }
    }

    public void init(final IMemento savedState) {
        final String match = savedState
                .getString(CommonConstants.TAG_MATCH_FILTER);
        if (match != null) {
            this.renderer.getData().setFilterText(match);
        }

        final String zoomLevelLog = savedState
                .getString(CommonConstants.ZOOM_LEVEL_LOG);
        if (zoomLevelLog != null) {
            renderer.getData().setZoomLevelLog(Double.valueOf(zoomLevelLog));
        }

        final String zoomLevelLin = savedState
                .getString(CommonConstants.ZOOM_LEVEL_LIN);
        if (zoomLevelLin != null) {
            renderer.getData().setZoomLevelLin(Double.valueOf(zoomLevelLin));
        }

        for (final IMemento idMemento : savedState
                .getChildren(SequenceDiagram.SEQUENCE_DIAGRAM_SELECTED_ITEMS_ORDER)) {
            final IArtifactID id = ArtifactID.valueOf(idMemento);
            // TODO Check if connection is null => bail, artifact is null =>
            // bail, != ISeqMem => bail

            if (getLogset() != null) {
                final IArtifact member = getLogset().getArtifact(id);
                if (member instanceof ISequenceMember) {
                    this.selectedItems.add((ISequenceMember) member);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void createControl(final Composite owner) {

        final String helpId = SequenceDiagram.HELP_ID;
        this.setHelp(owner, helpId);

        owner.setLayout(new FormLayout());

        final Composite zealBar = new Composite(owner, SWT.NULL);
        zealBar.setLayout(new RowLayout());

        spacingBetweenObjects = new Label(zealBar, SWT.NONE);
        spacingBetweenObjects.setText("");
        spacingBetweenObjects.setVisible(false);

        final Label lbl = new Label(zealBar, SWT.NULL);
        lbl.setText("Match: ");
        final Text text = new Text(zealBar, SWT.BORDER);

        text.setLayoutData(new RowData(CommonConstants.ZEALBAR_FORMAT_WIDTH,
                SWT.DEFAULT));

        if (this.renderer.getData().getFilterText() != null) {
            text.setText(this.renderer.getData().getFilterText());
        }

        text.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent event) {
                renderer.getData().setFilterText(text.getText());
                refresh();
            }

        });

        text.setToolTipText("Filter SignalID/Message");

        Composite zoomComposite = new Composite(zealBar, SWT.NONE);
        zoomComposite.setLayout(new CellLayout(2).setMargins(10, 10));

        Label zoomLabel = new Label(zealBar, SWT.None);
        zoomLabel.setText("Zoom:");

        Button down = new Button(zealBar, SWT.PUSH);
        down.setImage(zoomOutImage);
        down.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
                zoomOut();
            }
        });

        Button up = new Button(zealBar, SWT.PUSH);
        up.setImage(zoomInImage);
        up.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
                zoomIn();
            }
        });

        Composite dummySpacer = new Composite(zealBar, SWT.NONE);
        dummySpacer.setLayout(new CellLayout(2).setMargins(10, 10));

        compactActors = new Button(zealBar, SWT.CHECK);
        compactActors.setText("Compact Object Layout");
        compactActors.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(final SelectionEvent e) {}

            public void widgetSelected(final SelectionEvent e) {
                refresh();
            }
        });

        spacingBetweenObjects = new Label(zealBar, SWT.NONE);
        spacingBetweenObjects.setText(" ");
        spacingBetweenObjects.setVisible(false);

        lockButton = new Button(zealBar, SWT.CHECK);
        lockButton.setText("Lock Object Order");
        lockAllActors();
        lockButton.setSelection(true);
        lockButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(final SelectionEvent e) {}

            public void widgetSelected(final SelectionEvent e) {

                if (lockButton.getSelection()) {
                    lockAllActors();
                } else {
                    unlockAllActors();
                }
            }
        });

        this.scroll = new ScrolledComposite(owner, SWT.HORIZONTAL);
        this.scroll.setAlwaysShowScrollBars(true);
        this.scroll.setLayout(new FillLayout());

        this.canvas = new Canvas(this.scroll, SWT.DOUBLE_BUFFERED);
        this.canvas.setBackground(this.scroll.getShell().getDisplay()
                .getSystemColor(SWT.COLOR_WHITE));

        this.scroll.setContent(this.canvas);
        this.scroll.setExpandHorizontal(true);
        this.scroll.setExpandVertical(true);

        this.makeActions();
        this.initContextMenu();

        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.right = new FormAttachment(SequenceDiagram.HUNDRED_PERCENT, 0);
        zealBar.setLayoutData(formData);

        formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.right = new FormAttachment(SequenceDiagram.HUNDRED_PERCENT, 0);
        formData.top = new FormAttachment(zealBar);
        formData.bottom = new FormAttachment(SequenceDiagram.HUNDRED_PERCENT, 0);
        this.scroll.setLayoutData(formData);

        query = new SequenceQuery(getSequence(), getInput().getTimeCluster());
        getLogset().addQuery(query);

        IFWFacade.addChangeListener(importListener);
    }

    public boolean getCompactActors() {
        return compactActors.getSelection();
    }

    private void makeActions() {
        this.canvas.addPaintListener(new PaintListener() {

            public void paintControl(final PaintEvent e) {
                if (e.count > 0) {
                    return;
                }
                draw(Graphics.valueOf(e.gc), createRenderingData());
            }
        });

        this.canvas.addKeyListener(new KeyHandler());

        this.canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(final MouseEvent e) {
                if (e.button == SequenceDiagram.MOUSE_FIRST) {
                    final Object elementAt = renderer.getElementAt(e.x, e.y);
                    if (elementAt instanceof IActivity) {
                        // removed this since it seems like the actor gets
                        // "locked" here, and it should not, just in the else if
                        // below
                        // IActivity activity = (IActivity) elementAt;
                        // setTracked(activity.getOwner());
                    } else if (elementAt instanceof ISequenceMember) {
                        setTracked((ISequenceMember) elementAt);
                    }

                    if (elementAt instanceof ITimed) {
                        final ITimed timed = (ITimed) elementAt;
                        getInput().getTimeCluster().setCurrentTime(
                                timed.getTimeReference());
                    }
                }
            }

            @Override
            public void mouseDown(final MouseEvent e) {
                // 1 == First button (Normally left)
                final Object elementAt = renderer.getElementAt(e.x, e.y);
                if (e.button == MOUSE_THIRD) {
                    final Point p = canvas.toDisplay(e.x, e.y);
                    menuManager.getMenu().setLocation(p);

                    if (elementAt instanceof IObject) {
                        final IObject object = (IObject) elementAt;
                        final ArtifactSelection selection = new ArtifactSelection(
                                getInput(), object);
                        if (elementAt instanceof GenericTask) {
                            selection.setVisibleGoto((false));
                        }
                        setSelection(selection);
                    } else {
                        setSelection(elementAt);
                    }
                } else if (e.button == MOUSE_FIRST) {
                    setSelection(elementAt);
                }
            }
        });

        this.canvas.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
            public void mouseHover(final MouseEvent e) {
                final Object elementAt = renderer.getElementAt(e.x, e.y);
                if ((elementAt != null) && (elementAt instanceof IObject)) {
                    refresh();
                    canvas.setToolTipText(PropertySource
                            .toString((IObject) elementAt));
                } else {
                    canvas.setToolTipText(null);
                    refresh();
                }

            }
        });
    }

    private ISequence getSequence() {
        return (ISequence) getInput().getData();
    }

    /**
     * {@inheritDoc}
     */
    public void synch(final TimeEvent source) {
        if (this.canvas.isDisposed()) {
            source.getSource().removeSynchable(this);
        } else {
            this.refresh();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void refresh() {
        if (!getLogset().isLocked()) {
            refresher.schedule(100);
        }
    }

    void draw(final IGraphics gc, final SequenceRenderingData renderingData) {
        if (this.getInput() == null || query == null) {
            return;
        }

        final IServiceProvider serviceProvider = getServiceProvider();
        final IEventColorProvider colorProvider = serviceProvider
                .getService(IEventColorProvider.class);

        this.renderer.draw(gc, renderingData, colorProvider);

        if (this.scroll.getMinWidth() != renderingData.getPreferedWidth()) {
            this.scroll.setMinWidth(renderingData.getPreferedWidth());
        }
        if (this.scroll.getMinHeight() != renderingData.getPreferedHeight()) {
            this.scroll.setMinHeight(renderingData.getPreferedHeight());
        }
        // new LockCurrentSelectionAction("").run();
    }

    private SequenceRenderingData createRenderingData() {
        final ILogViewInput input = getInput();
        if (input != null && query != null) {
            final long currentTime = input.getCurrentTime();
            Data data = query.getData(viewWindow, viewWindow);
            renderer.getData().setColorMap(input.getLog().getColorMap());
            renderer.getData().setElements(data.getElements());
            renderer.getData().setMessages(data.getMessages());
            renderer.getData().setMarkerValue(currentTime);
            renderer.getData().setWidgetSize(scroll.getClientArea());
            renderer.getData().setBounds(canvas.getParent().getClientArea());
            if (isLocked) {
                selectedItems.addAll(renderer.getData().getMembers());
            }

            renderer.getData().getMembers().clear();
            if (data.getElements().size() > 0 && selectedItems.size() > 0) {
                renderer.getData().getMembers().addAll(selectedItems);

            } else {
                selectedItems.clear();
            }
            renderer.getData().setContext(tracked);
            renderer.getData().setIsLogarithmic(true);
            renderer.getData().setDrawCompactActors(getCompactActors());
        }
        return this.renderer.getData();
    }

    /**
     * {@inheritDoc}
     */
    public void stepBack() {
        stepReturn(false);
    }

    private void stepBack(final boolean stepOnActivityOnly) {
        stepReturn(stepOnActivityOnly);
    }

    private void stepPrevious(final boolean stepOnActivityOnly) {
        if (this.getInput() == null) {
            return;
        }
        Long previous = null;

        long currentTime = this.getInput().getTimeCluster().getCurrentTime();

        ArrayList<ITimed> elements = createRenderingData().getElements();
        for (ITimed element : elements) {
            long timeStamp = element.getTimeReference();

            if (stepOnActivityOnly) {
                if (timeStamp < currentTime) {
                    if (element instanceof IActivity
                            || element instanceof ISequenceMessage) {
                        previous = timeStamp;
                    }
                } else {
                    ITimed activeElement = getActiveElement(previous);
                    if (activeElement instanceof IActivity
                            || activeElement instanceof ISequenceMessage
                            || element.getClass().toString()
                                    .equals(GENERIC_RECEIVE_EVENT)) {
                        previous = activeElement.getTimeReference();
                        break;
                    }
                }
            } else {
                if (timeStamp < currentTime) {
                    previous = timeStamp;
                } else {
                    break;
                }
            }
        }
        if (previous != null) {
            this.stepTo(previous);
        }
    }

    private void stepNext(final boolean stepOnActivityOnly) {
        if (this.getInput() == null) {
            return;
        }
        final ITimeCluster timeCluster = this.getInput().getTimeCluster();

        long currentTime = timeCluster.getCurrentTime();
        for (final Long timeStamp : this.getTimeStamps()) {
            if (timeStamp > currentTime) {
                if (stepOnActivityOnly) {
                    ITimed activeElement = getActiveElement(timeStamp);

                    if (activeElement instanceof IActivity) {
                        IActivity act = (IActivity) activeElement;
                        if (act.getStartTime() == timeStamp
                                || act.getEndTime() == timeStamp) {
                            this.stepTo(timeStamp);
                            return;
                        }
                    } else if (activeElement instanceof ISequenceMessage) {
                        this.stepTo(timeStamp);
                        return;
                    }
                } else {
                    this.stepTo(timeStamp);
                    return;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void stepForward() {
        stepOver(false);
    }

    private void stepForward(final boolean stepOnActivityOnly) {
        stepOver(stepOnActivityOnly);
    }

    public void jumptToNext() {
        /*
         * get current Send (if any) if no current sent, stand still if current
         * Sent.getDeliveryTime() >= 0, stepTo(sent.getDelivieryTime) else stand
         * still
         */
        SequenceRenderingData data = createRenderingData();
        for (ISequenceMessage message : data.getMessages()) {
            if (message.getTimeReference() == getInput().getTimeCluster()
                    .getCurrentTime()) {
                if (message.getDeliveryTime() >= 0) {
                    stepTo(message.getDeliveryTime());
                }
                return;
            }
        }
    }

    public void jumptToPrevious() {
        /*
         * get current activity (if any) (current activity is if we are AT
         * exactly the beginning of a Activity if no current, stand still
         * 
         * if current.getTriggerTime() >= 0 step to(current.getTriggerTime()
         * else stand still
         */
        SequenceRenderingData data = createRenderingData();
        for (ITimed element : data.getElements()) {
            if (element.getTimeReference() == getInput().getTimeCluster()
                    .getCurrentTime()) {
                if (element.getTimeReference() >= 0) {
                    stepTo(element.getTimeReference());
                }
                return;
            }
        }
    }

    private void stepTo(final Long next) {
        ITimed item = getActiveElement(next);
        setSelection(item);
        this.getInput().getTimeCluster().setCurrentTime(next);
    }

    /**
     * Gets the item which would be active at a specified timestamp (around this
     * ts, the specified ts can be somewhere inside the searched item) Note that
     * this method only works if the supposed active element is within the cache
     * at it's the current timestamp
     * 
     * @param timestamp
     * @return
     */
    private ITimed getActiveElement(final Long timestamp) {
    	if (timestamp == null) {
    		return null;
    	}
        ITimed item = null;
        final SequenceRenderingData data = this.createRenderingData();
        for (final ITimed element : data.getElements()) {
            if (element instanceof IActivity) {
                IActivity act = (IActivity) element;
                if (Span.valueOf(act).contains(timestamp)) {
                    item = element;
                }
            }
        }
        for (final ISequenceMessage message : data.getMessages()) {
            if (message.getTs() == timestamp) {
                item = message;
            }
        }
        return item;
    }

    private long getFirstElementTs() {
        getInput().getTimeCluster().setCurrentTime(0L);
        long activityTs = 0;
        long messageTs = 0;

        final SequenceRenderingData data = this.createRenderingData();
        for (final ITimed element : data.getElements()) {
            if (element instanceof IActivity) {
                activityTs = ((IActivity) element).getStartTime();
                break;
            }
        }

        ITimed message = data.getMessages().size() != 0 ? data.getMessages()
                .get(0) : null;
        if (message != null) {
            messageTs = message.getTimeReference();
        }

        return Math.min(messageTs, activityTs);
    }

    private long getLastElementTs() {
        getInput().getTimeCluster().setCurrentTime(Long.MAX_VALUE);
        long activityTs = 0;
        long messageTs = 0;

        final SequenceRenderingData data = this.createRenderingData();

        ArrayList<ITimed> elements = data.getElements();
        for (int i = elements.size() - 1; i >= 0; i--) {
            final ITimed element = elements.get(i);
            if (element instanceof IActivity) {
                activityTs = ((IActivity) element).getEndTime();
                break;
            }
        }

        ISequenceMessage message = data.getMessages().size() != 0 ? data
                .getMessages().get(data.getMessages().size() - 1) : null;
        if (message != null) {
            messageTs = message.getTs();
        }

        return Math.max(messageTs, activityTs);
    }

    private List<Long> getTimeStamps() {
        final List<Long> timestamps = new ArrayList<Long>(this
                .createRenderingData().getTimestamps());
        for (final ISequenceMessage message : this.createRenderingData()
                .getMessages()) {
            timestamps.add(message.getTs());
        }
        Collections.sort(timestamps);
        return timestamps;
    }

    /**
     * Initializes a context menu and registers it for Objectcontributions.
     * 
     */
    private void initContextMenu() {
        this.menuManager = new MenuManager();
        this.menuManager.add(new GroupMarker(
                IWorkbenchActionConstants.MB_ADDITIONS));
        this.menuManager.setRemoveAllWhenShown(true);
        this.menuManager.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(final IMenuManager manager) {
                manager.add(new GroupMarker(
                        IWorkbenchActionConstants.MB_ADDITIONS));

                manager.add(new Separator());
                manager.add(new ConfigureOrderAction("Configure Order ..."));

                if (isLocked) {
                    manager.add(new UnlockCurrentSelectionAction(UNLOCK));
                } else {
                    manager.add(new LockCurrentSelectionAction(LOCK));
                }
            }
        });

        this.canvas.setMenu(this.menuManager.createContextMenu(this.canvas));

        if (null != this.getInput()) {
            final ArtifactColorMap colorMap = this.getInput().getLog()
                    .getColorMap();
            colorMap.addChangeListener(new IChangeListener() {

                public void update(final boolean changed) {
                    if ((canvas == null) || canvas.isDisposed()) {
                        // colorMap.removeChangeListener(this);
                        return;
                    }
                    canvas.redraw();
                }
            });
        }
        this.getSite().registerContextMenu(this.menuManager, this);
        this.menuManager.getMenu().addListener(SWT.Hide, new Listener() {
            public void handleEvent(final Event event) {
                // When hidden, refresh the diagram so it won't leave "holes"
                refresh();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void zoomIn() {
        if (this.renderer.getData() != null) {
            this.renderer.getData().setZoomLevel(
                    this.renderer.getData().getZoomLevel() * 1.25);
            this.renderer.getData().setZoomLevel(
                    Math.min(this.renderer.getData().getZoomLevel(),
                            CommonConstants.MAX_ZOOM_LEVEL));
            this.refresh();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void zoomOut() {
        if (this.renderer.getData() != null) {
            this.renderer.getData().setZoomLevel(
                    this.renderer.getData().getZoomLevel() / 1.25f);
            this.renderer.getData().setZoomLevel(
                    Math.max(this.renderer.getData().getZoomLevel(),
                            CommonConstants.MIN_ZOOM_LEVEL));
            this.refresh();
        }
        this.refresh();
    }

    public void fillReport(final AbstractReport report) {

        final Rectangle rectangle = new Rectangle(0, 0, scroll.getMinWidth(),
                Math.max(scroll.getMinHeight(),
                        SequenceDiagram.REPORT_MIN_HEIGHT));
        final Image image = new Image(getSite().getShell().getDisplay(),
                rectangle);
        final IGraphics imageGc = Graphics.valueOf(image);
        draw(imageGc, createRenderingData());

        final ImageReportItem item = new ImageReportItem(image.getImageData());
        item.setName(getTitle());
        item.setDescription(getTitleToolTip());
        image.dispose();
        report.addReportData(item);
    }

    public void setFocus() {
        this.canvas.setFocus();
    }

    private final class LockCurrentSelectionAction extends Action {
        private LockCurrentSelectionAction(final String text) {
            super(text);
        }

        @Override
        public void run() {
            lockAllActors();
        }
    }

    private final class UnlockCurrentSelectionAction extends Action {
        private UnlockCurrentSelectionAction(final String text) {
            super(text);
        }

        @Override
        public void run() {
            unlockAllActors();
        }
    }

    private final class ConfigureOrderAction extends Action {
        private ConfigureOrderAction(final String text) {
            super(text);
        }

        @Override
        public void run() {
            final ILogViewInput input = getInput();
            if (input == null) {
                return;
            }

            final Collection<ISequenceMember> members = query.getMembers();

            final SelectOrderDialog<ISequenceMember> dlg = SelectOrderDialog
                    .newInstance(getSite().getShell());
            dlg.setInitialElements(members);
            dlg.setInitialSelection(selectedItems);

            if (dlg.open() == Window.OK) {
                selectedItems.clear();
                selectedItems.addAll(dlg.getSelectedItems());
                refresh();
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent e) {
            if (!canvas.isFocusControl()) {
                return;
            }

            if (e.character == SWT.ESC) {
                tracked = null;
                refresh();
            }
            if (e.character == '\n' || e.character == '\r') {
                stepInto(false);
            }
            if (e.character == ' ') {
                ITimed element = getActiveElement(getInput().getCurrentTime());
                if (element == null) {
                    setTracked(null);
                } else if (element instanceof IActivity) {
                    IActivity a = (IActivity) element;
                    setTracked(a.getOwner());
                } else if (element instanceof ISequenceMessage) {
                    ISequenceMessage msg = (ISequenceMessage) element;
                    setTracked(msg.getSender());
                }
            }
            if (e.character == 'n') {
                stepOver((e.stateMask & SWT.CONTROL) == 0);
            } else if (e.character == 'b') {
                stepReturn((e.stateMask & SWT.CONTROL) == 0);
            } else if (e.character == 'm') {
                stepInto((e.stateMask & SWT.CONTROL) == 0);
            }

            if ((e.stateMask & SWT.SHIFT) != 0) {
                if (e.keyCode == SWT.ARROW_DOWN) {
                    jumptToNext();
                }
                if (e.keyCode == SWT.ARROW_UP) {
                    jumptToPrevious();
                }
            } else {
                if (e.keyCode == SWT.ARROW_DOWN) {
                    stepForward((e.stateMask & SWT.CONTROL) == 0);
                }
                if (e.keyCode == SWT.ARROW_UP) {
                    stepBack((e.stateMask & SWT.CONTROL) == 0);
                }
            }

            if (e.character == '+'
                    || (e.stateMask == SWT.NONE && e.keyCode == 122)) {
                zoomIn();
            }
            if (e.character == '-'
                    || (e.stateMask == SWT.SHIFT && e.keyCode == 122)) {
                zoomOut();
            }

            if ((e.keyCode == SWT.ARROW_LEFT) || (e.keyCode == SWT.ARROW_RIGHT)) {
                handleHScrollBar(e.keyCode);
            }

            if (getInput() == null) {
                return;
            }
            ITimeCluster viewset = getInput().getTimeCluster();
            if (e.keyCode == SWT.HOME) {
                viewset.setCurrentTime(getFirstElementTs());
                stepForward();
                stepBack();
            } else if (e.keyCode == SWT.END) {
                viewset.setCurrentTime(getLastElementTs());
                stepBack();
                stepForward();
            }

            super.keyPressed(e);
        }
    }

    private void handleHScrollBar(final int keyCode) {
        if (keyCode == SWT.ARROW_LEFT) {
            scroll.getHorizontalBar().setSelection(
                    scroll.getHorizontalBar().getSelection() - 10);
        }
        if (keyCode == SWT.ARROW_RIGHT) {
            scroll.getHorizontalBar().setSelection(
                    scroll.getHorizontalBar().getSelection() + 10);
        }
        canvas.setLocation(-scroll.getHorizontalBar().getSelection(),
                canvas.getLocation().y);

    }

    public String getName() {
        return SequenceDiagram.NAME;
    }

    protected void setTracked(final ISequenceMember owner) {
        if (tracked != null && tracked.equals(owner)) {
            tracked = null;
        } else {
            tracked = owner;
        }
        refresh();
    }

    private void setCursor(final Cursor cursor) {
        canvas.setCursor(cursor);
    }

    private void resetCursor() {
        canvas.setCursor(null);
    }

    private void stepOver(final boolean stepOnActivityOnly) {
        setCursor(SWTResourceManager.getCursor(SWT.CURSOR_WAIT));
        if (tracked != null) {
            Long next = getTracker(tracked).next();
            if (next != null) {
                stepTo(next);
            }
        } else {
            stepNext(stepOnActivityOnly);
        }
        resetCursor();
    }

    private void stepReturn(final boolean stepOnActivityOnly) {
        setCursor(SWTResourceManager.getCursor(SWT.CURSOR_WAIT));
        if (tracked != null) {
            Long next = getTracker(tracked).previous();
            if (next != null) {
                stepTo(next);
            }
        } else {
            stepPrevious(stepOnActivityOnly);
        }
        resetCursor();
    }

    private void stepInto(final boolean stepOnActivityOnly) {
        if (tracked == null) {
            stepForward();
            return;
        }
        setCursor(SWTResourceManager.getCursor(SWT.CURSOR_WAIT));

        Data data = query.getData(2, 2);
        for (ISequenceMessage message : data.getMessages()) {
            if (message.getTs() == getInput().getCurrentTime()
                    && message.getSender().equals(tracked)) {
                setTracked(message.getRecipent());
                if (message.getDeliveryTime() >= 0) {
                    stepTo(message.getDeliveryTime());
                    resetCursor();
                    return;
                }
            }
        }
        stepOver(stepOnActivityOnly);
        resetCursor();
    }

    private TrackingQuery getTracker(final ISequenceMember tracked) {
        if (tracker == null || !tracker.getTracked().equals(tracked)) {
            if (tracker != null) {
                getLogset().removeQuery(tracker);
            }

            tracker = new TrackingQuery(tracked, getInput());
            getLogset().addQuery(tracker);
        }
        return tracker;
    }

    @Override
    public void dispose() {
        lockButton.dispose();
        super.dispose();
        getLogset().removeQuery(query);
        if(importListener != null) {
            IFWFacade.removeChangeListener(importListener);
        }
        zoomInImage.dispose();
		zoomOutImage.dispose();
    }

    private void lockAllActors() {
        if (renderer.getData() != null) {
            selectedItems.clear();
            selectedItems.addAll(renderer.getData().getDrawnActors());
            isLocked = true;
        }
    }

    private void unlockAllActors() {
        selectedItems.clear();
        isLocked = false;
    }

    public Collection<IFilter<IObject>> getFilter() {
        TaskQuery taskQuery = TaskQuery.valueOf(getInput().getLogset());

        List<IFilter<IObject>> tasks = new ArrayList<IFilter<IObject>>();
        for (final ITask iTask : taskQuery.getExecutedTasks()) {
            tasks.add(new TaskFilter(iTask));
        }
        for (final ITask iTask : taskQuery.getNotExecutedTasks()) {
            tasks.add(new TaskFilter(iTask));
        }
        return tasks;
    }

    public Collection<IFilter<IObject>> getFilterSelections() {
        return filterOutThese;
    }

    public void setFilter(final Collection<IFilter<IObject>> filter) {
        this.filterOutThese = filter;
        if (this.query != null) {
            this.query.setFiltered(this.filterOutThese);
            this.renderer.setFilteredActors(getFilteredActors());
        }
    }

    public Collection<ISequenceMember> getFilteredActors() {
        List<ISequenceMember> filteredActors = new ArrayList<ISequenceMember>();
        if (this.query != null) {
            TaskQuery taskQuery = TaskQuery.valueOf(getInput().getLogset());

            final List<IObject> tasks = new ArrayList<IObject>();
            for (final ITask iTask : taskQuery.getExecutedTasks()) {
                tasks.add(iTask);
            }

            Collection<IFilter<IObject>> filterOutThese = this.filterOutThese;
            if (filterOutThese.size() > 0) {
                for (IObject task : tasks) {
                    for (final IFilter<IObject> f : filterOutThese) {
                        if (!f.filter(task)) {
                            filteredActors.add((ISequenceMember) task);
                            break;
                        }
                    }
                }
            }
        }
        return filteredActors;

    }

    public static class ActorFilter implements IFilter<IObject> {

        private final IObject out;

        public ActorFilter(final IObject a) {
            super();
            out = a;
        }

        public boolean filter(final IObject x) {
            if (x instanceof ISequenceMember) {
                boolean isEq = out.equals(x);
                return !isEq;

            }
            return true;
        }

        @Override
        public String toString() {
            return out.toString();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (out == null ? 0 : out.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ActorFilter other = (ActorFilter) obj;
            if (out == null) {
                if (other.out != null) {
                    return false;
                }
            } else if (!out.equals(other.out)) {
                return false;
            }
            return true;
        }
    }
}
