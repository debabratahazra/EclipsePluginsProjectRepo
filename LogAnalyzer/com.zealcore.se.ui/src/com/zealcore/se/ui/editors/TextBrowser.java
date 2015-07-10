package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.progress.UIJob;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.EventQuery;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.TableReportItem;
import com.zealcore.se.ui.core.report.TableReportItem.TableRow;
import com.zealcore.se.ui.graphics.IColor;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.internal.TimeEvent;
import com.zealcore.se.ui.util.StringMatcher;
import com.zealcore.se.ui.util.TimeFormat;

public final class TextBrowser extends AbstractLogsetBrowser implements
        IReportContributor, IStepable {

    private static final int SIZE_OF_UPPER_FRAME_STUFF_AND_ZEALBAR = 44;

    public static final String NAME = "Text Browser";

    public void setFocus() {
        viewer.getTable().setFocus();
    }

    private static final int SELECTION_BOX_MARGIN = 5;

    private static final int HALF_EVENT_COUNT = 50;

    public static final String BROWSER_ID = "com.zealcore.se.ui.views.EventTextualView";

    public static final String HELP_ID = "com.zealcore.se.ui.editors_TextBrowser";

    private TableViewer viewer;

    private final UIJob refresher;

    private MenuManager menuManager;

    private String filterText = "";

    private static final CharSequence PROPERTY_SEPARATOR = " | ";

    private final TextFilter textFilter = new TextFilter();

    private String formatString = CommonConstants.DEFAULT_FORMAT_STRING;

    private EventQuery query;

    private final IChangeListener importListener;

    private boolean homeKeyPressed;

    private boolean endKeyPressed;

    public TextBrowser() {
        refresher = new AbstractSafeUIJob("TextBrowser_UIJob") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                if (viewer.getTable().isDisposed()) {
					if (query != null) {
						getLogset().removeQuery(query);
					}
					query = null;
					return Status.OK_STATUS;
                }
                final boolean hadFocus = viewer.getTable().isFocusControl();

                TextBrowser.this.doUpdateTable();
                if (hadFocus) {
                    viewer.getTable().setFocus();
                }
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
    }

    public ImageDescriptor getImageDescriptor() {
        return IconManager.getImageDescriptor(IconManager.TEXT_BROWSER);
    }

    private void setFormatString(final String format) {
        formatString = format;
        if (getLabelProvider() != null) {
            getLabelProvider().setFormatString(format);
            refresh();
        }
    }

    private EventLabelProvider getLabelProvider() {
        if (viewer == null) {
            return null;
        }
        return (EventLabelProvider) viewer.getLabelProvider();
    }

    private String getFormatString() {
        return formatString;
    }

    public void saveState(final IMemento savedState) {
        savedState.putString(CommonConstants.TAG_FORMAT_STRING,
                getFormatString());

        savedState.putString(CommonConstants.TAG_MATCH_FILTER, filterText);
    }

    public void init(final IMemento savedState) {
        final String format = savedState
                .getString(CommonConstants.TAG_FORMAT_STRING);
        if (format != null) {
            setFormatString(format);
        }
        filterText = savedState.getString(CommonConstants.TAG_MATCH_FILTER);
    }

    TableColumn tableColumn;
    public void createControl(final Composite parent) {
        final int scrolls = SWT.H_SCROLL | SWT.V_SCROLL;
        final int styles = SWT.FULL_SELECTION | SWT.BORDER;

        final String helpId = TextBrowser.HELP_ID;
        setHelp(parent, helpId);

        viewer = new TableViewer(parent, styles | scrolls);
        viewer.setContentProvider(new ArrayContentProvider());
        final IEventColorProvider colorProvider = SeCorePlugin.getDefault()
                .getService(IEventColorProvider.class);
        viewer.setLabelProvider(new EventLabelProvider(colorProvider));
        tableColumn = new TableColumn(viewer.getTable(), SWT.LEFT);
        new TableColumn(viewer.getTable(), SWT.NULL).setText("Time");
        new TableColumn(viewer.getTable(), SWT.NULL).setText("Type");
        new TableColumn(viewer.getTable(), SWT.NULL).setText("Logfile");
        new TableColumn(viewer.getTable(), SWT.NULL).setText("Properties");
        viewer.getTable().setHeaderVisible(true);

        //jihi- TOOLSCR-1841: A vertical slider exists in the Text Browser
        viewer.getTable().getVerticalBar().setVisible(false);

        getSite().setSelectionProvider(viewer);
        final IChangeListener colorListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (changed) {
                    viewer.refresh(true);
                }
            }
        };

        viewer.getControl().getParent()
                .addControlListener(new ControlAdapter() {
                    @Override
                    public void controlResized(final ControlEvent e) {
                        if (e != null) {
                            doUpdateTable();
                        }
                    }
                });

        colorProvider.addChangeListener(colorListener);
        viewer.getTable().addDisposeListener(new DisposeListener() {
            public void widgetDisposed(final DisposeEvent arg0) {
                colorProvider.removeChangeListener(colorListener);
            }
        });

        makeActions();
        makeZealBar();

        viewer.addFilter(textFilter);
        makeCustomSelectionDrawing();
        createContextMenu();
        setFormatString(getFormatString());

        query = new EventQuery(getInput().getTimeCluster());
        getLogset().addQuery(query);
        IFWFacade.addChangeListener(importListener);
    }

    private void makeZealBar() {
        final Label lbl = new Label(getZealBar(), SWT.NULL);
        lbl.setText("Match: ");
        final Text text = new Text(getZealBar(), SWT.BORDER);
        text.setLayoutData(new RowData(CommonConstants.ZEALBAR_FORMAT_WIDTH,
                SWT.DEFAULT));
        text.setText(filterText == null ? "" : filterText);

        text.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent event) {
                viewer.getTable().setRedraw(false);
                filterText = text.getText();
                viewer.removeFilter(textFilter);
                viewer.addFilter(textFilter);
                viewer.getTable().setRedraw(true);
            }
        });

        new Label(getZealBar(), SWT.NONE).setText("Format: ");
        final Text timeFormat = new Text(getZealBar(), SWT.BORDER);
        timeFormat.setText(getFormatString());
        timeFormat.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                if (viewer == null) {
                    return;
                }
                final String format = timeFormat.getText();
                TextBrowser.this.setFormatString(format);
            }

        });
        timeFormat.setLayoutData(new RowData(
                CommonConstants.ZEALBAR_FORMAT_WIDTH, SWT.DEFAULT));
    }

    private void makeActions() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            private Object oldSelection;

            public void doubleClick(final DoubleClickEvent event) {
                final IStructuredSelection sel = (IStructuredSelection) event
                        .getSelection();
                if (sel.getFirstElement() == oldSelection) {
                    return;
                }
                if (sel.getFirstElement() instanceof ITimed) {

                    final ITimed timed = (ITimed) sel.getFirstElement();
                    TextBrowser.this.stepTo(timed);
                    viewer.getTable().setFocus();
                    oldSelection = timed;
                    viewer.setSelection(new StructuredSelection(timed));
                }
            }

        });

        viewer.getTable().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.keyCode) {
                case SWT.HOME:
                    getInput().getTimeCluster().setCurrentTime(
                            getInput().getTimeCluster().getMin());
                    stepForward();
                    stepBack();
                    homeKeyPressed = true;
                    e.doit = false;
                    break;
                case SWT.END:
                    getInput().getTimeCluster().setCurrentTime(
                            getInput().getTimeCluster().getMax());
                    stepBack();
                    stepForward();
                    endKeyPressed = true;
                    e.doit = false;
                    break;
                case SWT.ARROW_DOWN:
                    TextBrowser.this.stepForward();
                    e.doit = false;
                    break;
                case SWT.ARROW_UP:
                    TextBrowser.this.stepBack();
                    e.doit = false;
                    break;
                default:
                    break;
                }
            }
        });
    }

    private void makeCustomSelectionDrawing() {
        viewer.getTable().addListener(SWT.EraseItem, new Listener() {
            public void handleEvent(final Event event) {
                if ((event.detail & SWT.SELECTED) == 0) {
                    return;
                }
                final GC gc = event.gc;
                final ITableColorProvider fontColor = (ITableColorProvider) viewer
                        .getLabelProvider();
                final TableItem tableItem = (TableItem) event.item;
                final Object data = tableItem.getData();
                final Color bg = fontColor.getBackground(data, event.index);

                if (bg != null) {
                    gc.setBackground(bg);
                } else {
                    gc.setBackground(gc.getDevice().getSystemColor(
                            SWT.COLOR_WHITE));
                }
                gc.fillRectangle(event.x, event.y, event.width, event.height);

                gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));

                int width = 0;
                for (final TableColumn column : viewer.getTable().getColumns()) {
                    width += column.getWidth();
                }
                // This is a silly workaround. If the obvious solution is
                // implemented, the line that is commented below, just to draw a
                // rectangle, is used. A vertical red line is painted when
                // horizontal scrolling is made. Therefore, just paint the red
                // line if the view is scrolled to the very left.
                // gc.drawRectangle(0, event.y, width
                // - TextBrowser.SELECTION_BOX_MARGIN, event.height - 1);
                gc.drawLine(0, event.y, width
                        - TextBrowser.SELECTION_BOX_MARGIN, event.y);
                gc.drawLine(0, event.y + event.height - 1, width
                        - TextBrowser.SELECTION_BOX_MARGIN, event.y
                        + event.height - 1);
                if (viewer.getTable().getHorizontalBar().getSelection() == 0) {
                    gc.drawLine(0, event.y, 0, event.y + event.height);
                }
                gc.drawLine(0, event.y, width
                        - TextBrowser.SELECTION_BOX_MARGIN, event.y);

                final Color foreground = fontColor.getForeground(data,
                        event.index);
                if (foreground != null) {
                    gc.setForeground(foreground);
                } else {
                    gc.setForeground(gc.getDevice().getSystemColor(
                            SWT.COLOR_BLACK));

                }
                event.detail &= ~SWT.SELECTED;
            }
        });
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
                manager.add(new GroupMarker(
                        IWorkbenchActionConstants.MB_ADDITIONS));
            }

        });
        final Menu menu = menuManager.createContextMenu(viewer.getTable());

        viewer.getTable().setMenu(menu);
        getSite().registerContextMenu(menuManager, viewer);
    }

    public void refresh() {
        if (!getLogset().isLocked()) {
            refresher.schedule(100);
        }
    }

    private void doUpdateTable() {
        if (getLogset().isLocked()) {
            return;
        }
        ILogEvent closest = null;
        Rectangle windowArea = viewer.getControl().getParent().getClientArea();
        int height = windowArea.height - SIZE_OF_UPPER_FRAME_STUFF_AND_ZEALBAR;
        if (height < 0) {
            return;
        }
        int noOfEventsThatCanBeShown = (height / viewer.getTable()
                .getItemHeight()) + 1;
        List<ILogEvent> logEvents = new ArrayList<ILogEvent>();
        if (query != null) {
            logEvents = query.getEvents(noOfEventsThatCanBeShown,
                    noOfEventsThatCanBeShown);
        }
        int currentIndex = getLogEventAtCurrentTs(logEvents);
        /* Nane changes for Issue TOOLSCR-1027 start */
        // Handling when current time not matching the time of any log event
        // in the log events array. This case occurs when synch event come
        // from plot view.
        // Plot view calculates time based on where user double clicked cursor
        // location in the X-Y chart area. This value not necessary the time
        // value of log event.
        if (currentIndex == -1) {
            currentIndex = noOfEventsThatCanBeShown;
        }
        /* Nane changes for Issue TOOLSCR-1027 end */
        int availableForw = (noOfEventsThatCanBeShown / 2);
        int availableBack = noOfEventsThatCanBeShown - availableForw;
        int eventsBack = currentIndex < availableBack ? currentIndex
                : availableBack;
        int eventsForward = Math.min(availableForw,
                (logEvents.size() - currentIndex));

        if (currentIndex < availableBack
                && (logEvents.size() - currentIndex > availableForw)) {
            eventsForward += availableBack - eventsBack;
        } else if ((logEvents.size() - currentIndex < availableForw)
                && (currentIndex > availableBack)) {
            eventsBack += availableForw - eventsForward;
        }

        int fromIndex = (currentIndex - eventsBack > 0) ? currentIndex
                - eventsBack : 0;
        int toIndex = currentIndex + eventsForward;

        if ((fromIndex < toIndex)
                && ((currentIndex + eventsForward) <= logEvents.size())) {
            logEvents = logEvents.subList(fromIndex, toIndex);
        }
        final List<ILogEvent> marked = new ArrayList<ILogEvent>();
        // Logic added for finding selection index for the current event to
        // select in the log events table.
        int selectionIndex = -1;
        boolean isSameTsExists = false;
        int i = 0;
        for (final ILogEvent le : logEvents) {
            if (getVisibleTime() == le.getTs()) {
                marked.add(le);
                isSameTsExists = true;
                if ((selectionIndex != -1) && homeKeyPressed) {
                	continue;
                }
                selectionIndex = i;
            } else if (getVisibleTime() > le.getTs()) {
                closest = le;
                if (!isSameTsExists) {
                    selectionIndex = i;
                }
            } else {
                break;
            }
            i++;
        }
        if (marked.size() == 0) {
            marked.add(closest);
        }
        getLabelProvider().setActive(marked);
        viewer.getTable().setVisible(false);
        viewer.getTable().setRedraw(false);
        Object[] array = logEvents.toArray();
        viewer.setInput(array);
        for (final TableColumn column : viewer.getTable().getColumns()) {
            column.pack();
        }

        viewer.refresh(true);
        viewer.getTable().setRedraw(true);
        viewer.getTable().setVisible(true);
        tableColumn.setAlignment(SWT.LEFT);
        tableColumn.setImage(IconManager.getImageDescriptor(IconManager.CURREENT_SELECTION_ICON).createImage());
        tableColumn.setWidth(30);
        
        int noOfItems = viewer.getTable().getItems().length;
        /* Nane changes for Issue TOOLSCR-1027 */
        // Checking for the selection index is greater than the items
        // in the table.
        if (selectionIndex < noOfItems) {
            viewer.getTable().setSelection(selectionIndex);
        } else {
            // Selecting the last item in the table
            viewer.getTable().setSelection(noOfItems - 1);
        }

        if (homeKeyPressed || endKeyPressed) {
            homeKeyPressed = false;
            endKeyPressed = false;
            TextBrowser.this.stepCurrent();
        }
    }

    /**
     * Returns the index in the List where current time stamp is found.
     * 
     * @param logEvents
     * @return index in array where current ts is found.
     */
    private int getLogEventAtCurrentTs(final List<ILogEvent> logEvents) {
        final long currentTime = getInput().getTimeCluster().getCurrentTime();
        int index = 0;
        for (ILogEvent logEvent : logEvents) {
            if (logEvent.getTs() >= currentTime) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void synch(final TimeEvent source) {
        refresh();
    }

    @Override
    public void dispose() {
        getSite().setSelectionProvider(null);
        super.dispose();
        getLogset().removeQuery(query);
        if(importListener != null) {
            IFWFacade.removeChangeListener(importListener);
        }
    }

    private final class TextFilter extends ViewerFilter {
        @Override
        public boolean select(final Viewer view, final Object parentElement,
                final Object element) {
            if (filterText == null || filterText.length() < 1) {
                return true;
            }
            final int max = viewer.getTable().getColumnCount();
            final ITableLabelProvider labels = (ITableLabelProvider) viewer
                    .getLabelProvider();
            final StringMatcher matcher = new StringMatcher(filterText + "*",
                    true, false);
            for (int i = 0; i < max; i++) {
                final String columnText = labels.getColumnText(element, i);
                if (matcher.match(columnText)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class EventLabelProvider extends LabelProvider implements
            ITableLabelProvider, ITableColorProvider {


        private static final String EMPTY_STRING = "";

        private static final int COLUMN_ACTIVE = 0;

        private static final int COLUMN_0 = 1;

        private static final int COLUMN_1 = 2;

        private static final int COLUMN_2 = 3;

        private final IEventColorProvider colorProvider;

        private String formatString = CommonConstants.DEFAULT_FORMAT_STRING;

        private List<ILogEvent> closest = new ArrayList<ILogEvent>(0);

        EventLabelProvider(final IEventColorProvider colorProvider) {
            this.colorProvider = colorProvider;
        }

        public void setFormatString(final String formatString) {
            this.formatString = formatString;
        }

        public void setActive(final List<ILogEvent> marked) {
            if (marked == null) {
                closest = new ArrayList<ILogEvent>(0);
                return;
            }
            closest = marked;
        }

        public Image getColumnImage(final Object element, final int columnIndex) {
        	final ILogEvent logEvent = (ILogEvent) element;
           if(columnIndex == COLUMN_ACTIVE){
                if (closest.contains(logEvent)) {
                    return IconManager.getImageDescriptor(IconManager.STEP_FORWARD_IMG).createImage();
                }
           }
                return null;          
        }

        public String getColumnText(final Object element, final int columnIndex) {
            final ILogEvent logEvent = (ILogEvent) element;
            switch (columnIndex) {
            case COLUMN_ACTIVE:
                if (closest.contains(logEvent)) {
                    return null;
                }
                return EventLabelProvider.EMPTY_STRING;
            case COLUMN_0:
                return TimeFormat.format(formatString, logEvent.getTs());
            case COLUMN_1:
                return logEvent.getType().getName();
            case COLUMN_2:
                final LogFile logFile = logEvent.getLogFile();
                if (logFile == null) {
                    return "";
                }
                if (logFile.getFileName() != null
                        && logFile.getFileName().length() > 0) {
                    return logFile.getFileName();
                }
                return logFile.getFileName();
            default:
                final String propertyString = buildPropertyString(logEvent);
                return propertyString;
            }
        }

        private String buildPropertyString(final ILogEvent logEvent) {
            final StringBuilder builder = new StringBuilder();

            for (final SEProperty property : logEvent.getZPropertyAnnotations()) {
                if (property.getName().equals(IObject.LOGFILE_PROPERTY)
                        || property.getName().equals(ILogEvent.TS_PROPERTY)) {
                    continue;
                }
                if (!IObject.DATE_PROPERTY.equals(property.getName())
                        && !IObject.LOGFILE_PROPERTY.equals(property.getName())
                        && !IObject.LOGSET_PROPERTY.equals(property.getName())) {
                    if (builder.length() > 0) {
                        builder.append(TextBrowser.PROPERTY_SEPARATOR);
                    }
                    builder.append(property.getData());

                }
            }
            final String propertyString = builder.toString();
            return propertyString;
        }

        public Color getBackground(final Object element, final int columnIndex) {
            final IColor color = colorProvider.getColor(((ILogEvent) element)
                    .getType());
            if (color == null) {
                return null;
            }
            return color.toColor();
        }

        public Color getForeground(final Object element, final int columnIndex) {
            final Color background = getBackground(element, columnIndex);
            if (background == null) {
                return null;
            }
            return SWTUtil.getTextColor(background);
        }
    }

    public void fillReport(final AbstractReport report) {

        final TableRow header = new TableRow();
        final TableReportItem item = new TableReportItem(header);
        item.setName(getTitle());
        item.setDescription(getTitleToolTip());
        final int columnCount = viewer.getTable().getColumnCount();

        for (final TableColumn column : viewer.getTable().getColumns()) {
            header.addValue(column.getText());
        }

        for (final TableItem tableItem : viewer.getTable().getItems()) {
            final TableRow row = new TableRow();
            for (int i = 0; i < columnCount; i++) {

                row.addValue(tableItem.getText(i));
            }
            item.addRow(row);
        }
        report.addReportData(item);

    }

    void stepTo(final ITimed timed) {
        if (timed == null) {
            return;
        }

        viewer.setSelection(new StructuredSelection(timed));
        final ITimeCluster viewSet = getInput().getTimeCluster();
        if (viewSet.getCurrentTime() != timed.getTimeReference()) {
            viewSet.setCurrentTime(timed.getTimeReference());
        }
    }

    public void stepBack() {

        ITimed stepTo = null;
        for (final ITimed iTimed : query.getEvents(
                TextBrowser.HALF_EVENT_COUNT, 1)) {
            if (iTimed.getTimeReference() < getInput().getTimeCluster()
                    .getCurrentTime()) {
                stepTo = iTimed;
            }
        }
        stepTo(stepTo);
        viewer.getTable().setFocus();

    }

    public void stepForward() {

        final List<ILogEvent> events = query.getEvents(1,
                TextBrowser.HALF_EVENT_COUNT);

        final long currentTime = getInput().getTimeCluster().getCurrentTime();
        for (final ITimed iTimed : events) {
            if (iTimed.getTimeReference() > currentTime) {
                stepTo(iTimed);
                viewer.getTable().setFocus();
                return;
            }
        }
    }

    public void stepCurrent() {
        final long currentTime = getInput().getTimeCluster().getCurrentTime();
        final List<ILogEvent> events = query.getEvents(1,
                TextBrowser.HALF_EVENT_COUNT);
        for (final ITimed iTimed : events) {
            if (iTimed.getTimeReference() == currentTime) {
                viewer.setSelection(new StructuredSelection(iTimed));
                viewer.getTable().setFocus();
                return;
            }
        }
    }

    public String getName() {
        return TextBrowser.NAME;
    }
}
