package com.zealcore.se.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.ui.util.RangeSelector;

public class TimingStatisticsAnalyzer {

    private static final int SELECTOR_LINE_HEIGHT_START = 30;

    private static final int SELECTOR_LINE_HEIGHT_END_OFFSET = 20;

    private static final int DECORATION_HEIGHT_START = SELECTOR_LINE_HEIGHT_START + 5;

    private static final int DECORATION_HEIGHT_END_OFFSET = 70;

    private boolean isRangeSelectorSelected;

    private Composite statisticsComposite;

    Canvas drawingArea;

    RangeSelector itemSelector;

    Text startTime;

    Text endTime;

    Text distance;

    ILogsetBrowser chart;

    boolean enabled;

    private IChangeListener changeListener;

    TimingStatisticsAnalyzer(ILogsetBrowser chart, Canvas drawingArea) {
        this.chart = chart;
        if (!(chart.getName().equalsIgnoreCase(GanttChart.NAME) || chart
                .getName().equalsIgnoreCase(EventTimelineBrowser.NAME))) {
            // Not supported on other charts
            throw new RuntimeException(
                    "Not supported for non-time based charts");
        }
        this.drawingArea = drawingArea;
    }

    public void initialize(int minimum, int maximum) {
        if (itemSelector != null) {
            itemSelector.setEnabled(this.enabled);
            if (enabled) {
                itemSelector.setMinimum(minimum);
                itemSelector.setMaximum(maximum - 1);
                itemSelector.setSelection(0, maximum);
            } else {
                itemSelector.setMinimum(0);
                itemSelector.setMaximum(1);
                itemSelector.setSelection(0, 2);
            }
        }
    }

    public void createTimingAnalysisControls(Composite parent) {
        statisticsComposite = new Composite(parent.getParent(), SWT.NULL);
        statisticsComposite.setBackground(statisticsComposite.getDisplay()
                .getSystemColor(SWT.COLOR_WHITE));
        GridLayout rowLayout = new GridLayout();
        rowLayout.numColumns = 6;
        statisticsComposite.setLayout(rowLayout);
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.top = new FormAttachment(parent);
        formData.bottom = new FormAttachment(100, 0);
        statisticsComposite.setLayoutData(formData);

        FormToolkit toolkit = new FormToolkit(statisticsComposite.getDisplay());

        createItemSelector();

        Label label = toolkit.createLabel(statisticsComposite,
                "Start Time (ns):");
        GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalIndent = 10;
        label.setLayoutData(gd);

        startTime = toolkit.createText(statisticsComposite, null, SWT.SINGLE
                | SWT.RIGHT | SWT.READ_ONLY | SWT.BORDER);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        startTime.setLayoutData(gd);

        label = toolkit.createLabel(statisticsComposite, "End Time (ns):");
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalIndent = 20;
        label.setLayoutData(gd);

        endTime = toolkit.createText(statisticsComposite, null, SWT.SINGLE
                | SWT.RIGHT | SWT.READ_ONLY | SWT.BORDER);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        endTime.setLayoutData(gd);

        label = toolkit.createLabel(statisticsComposite, "Distance (ns):");
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalIndent = 20;
        label.setLayoutData(gd);

        distance = toolkit.createText(statisticsComposite, null, SWT.SINGLE
                | SWT.RIGHT | SWT.READ_ONLY | SWT.BORDER);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        distance.setLayoutData(gd);

        changeListener = new IChangeListener() {

            public void update(boolean changed) {
                if (changed) {
                    if (chart.getInput().getLogset().getNumberOfEvents() != 0) {
                        setEnabled(true);
                    } else {
                        setEnabled(false);
                    }
                    initialize(0, drawingArea.getClientArea().width);
                }
            }
        };

        IFWFacade.addChangeListener(changeListener);
    }

    private void createItemSelector() {
        if (statisticsComposite != null) {
            itemSelector = new RangeSelector(statisticsComposite,
                    SWT.HORIZONTAL);
            GridData data = new GridData(GridData.FILL_HORIZONTAL);
            data.grabExcessHorizontalSpace = true;
            data.horizontalSpan = 6;
            itemSelector.setLayoutData(data);
            itemSelector.addSelectionListener(new ItemRangeSelectorHandler());
            itemSelector.setBackground(itemSelector.getDisplay()
                    .getSystemColor(SWT.COLOR_WHITE));
            itemSelector.setModifierKey(SWT.CONTROL);
        }
    }

    class ItemRangeSelectorHandler extends SelectionAdapter {

        public void widgetSelected(SelectionEvent e) {
            RangeSelector selector = (RangeSelector) e.getSource();

            drawingArea.redraw();
            isRangeSelectorSelected = true;
        }
    }

    
    public void updateStatistics(IDrawableRuler ruler) {
        if(enabled && ruler != null) {
            long startTimeValue = getSelectorStartTime(ruler);
            long endTimeValue = getSelectorEndTime(ruler);

            startTime.setText(Long.toString(startTimeValue));
            endTime.setText(Long.toString(endTimeValue));
            distance.setText(Long.toString(endTimeValue - startTimeValue));
        } else {
            startTime.setText("");
            endTime.setText("");
            distance.setText("");
        }                
    }

    public void drawSelectors(GC gc) {

        if (gc == null) {
            return;
        }
        if (enabled) {

            gc.setForeground(gc.getDevice().getSystemColor(
                    SWT.COLOR_DARK_MAGENTA));
            gc.setLineStyle(SWT.LINE_SOLID);
            gc.setLineWidth(1);

            int lpos = 0, rpos = gc.getClipping().width;
            int selectionOffset = itemSelector.getSelectionOffset();
            int selectionLength = itemSelector.getSelectionLength();

            if ((selectionOffset + selectionLength) == rpos) {
                lpos = selectionOffset;
            } else {
                rpos = (selectionOffset + selectionLength);
                lpos = selectionOffset;
            }

            gc.drawLine(lpos, SELECTOR_LINE_HEIGHT_START, lpos,
                    gc.getClipping().height - SELECTOR_LINE_HEIGHT_END_OFFSET);
            gc.drawLine(lpos, SELECTOR_LINE_HEIGHT_START, lpos + 5,
                    SELECTOR_LINE_HEIGHT_START);
            gc.drawLine(lpos, gc.getClipping().height
                    - SELECTOR_LINE_HEIGHT_END_OFFSET, lpos + 5,
                    gc.getClipping().height - SELECTOR_LINE_HEIGHT_END_OFFSET);

            gc.drawLine(rpos, SELECTOR_LINE_HEIGHT_START, rpos,
                    gc.getClipping().height - SELECTOR_LINE_HEIGHT_END_OFFSET);
            gc.drawLine(rpos, SELECTOR_LINE_HEIGHT_START, rpos - 5,
                    SELECTOR_LINE_HEIGHT_START);
            gc.drawLine(rpos, gc.getClipping().height
                    - SELECTOR_LINE_HEIGHT_END_OFFSET, rpos - 5,
                    gc.getClipping().height - SELECTOR_LINE_HEIGHT_END_OFFSET);

            gc.setLineStyle(SWT.LINE_SOLID);
            gc.setLineWidth(1);
            gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
        }
    }

    public void decorateSelectedArea(GC gc) {
        if (gc == null) {
            return;
        }

        if (enabled) {
            int lpos = 0, rpos = gc.getClipping().width;
            int selectionOffset = itemSelector.getSelectionOffset();
            int selectionLength = itemSelector.getSelectionLength();

            if ((selectionOffset + selectionLength) == rpos) {
                lpos = selectionOffset;
            } else {
                rpos = (selectionOffset + selectionLength);
                lpos = selectionOffset;
            }

            Rectangle completeArea = gc.getClipping();
            Rectangle selectedArea = new Rectangle(lpos + 2,
                    DECORATION_HEIGHT_START, (rpos - lpos),
                    gc.getClipping().height - DECORATION_HEIGHT_END_OFFSET);
            
            gc.setClipping(selectedArea);

            gc.setBackground(SWTResourceManager.getColor(229, 229, 229));

            /*
             * gc.setBackground(gc.getDevice().getSystemColor(
             * SWT.COLOR_INFO_BACKGROUND));
             */

            gc.fillRectangle(selectedArea);
            gc.setClipping(completeArea);
        }

    }

    public boolean isRangeSelectorSelected() {
        return isRangeSelectorSelected;
    }

    public void setRangeSelectorSelected(boolean selected) {
        this.isRangeSelectorSelected = selected;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        update();
    }

    public void dispose() {
        if (changeListener != null)
            IFWFacade.removeChangeListener(changeListener);
    }

    private void update() {
        itemSelector.setEnabled(enabled);
    }

    long getSelectorStartTime(IDrawableRuler ruler) {
        int selectionOffset = itemSelector.getSelectionOffset();
        return ruler.toTimestamp(selectionOffset);
    }

    long getSelectorEndTime(IDrawableRuler ruler) {
        int rpos = drawingArea.getClientArea().width;
        int selectionOffset = itemSelector.getSelectionOffset();
        int selectionLength = itemSelector.getSelectionLength();

        if (!(selectionOffset + selectionLength == (rpos + 1))) {
            rpos = selectionOffset + selectionLength;
        } 
        return ruler.toTimestamp(rpos);
    }
    
    public void handleKeyEvents(final KeyEvent e) {
        Event event = getKeyEventData(e);
        itemSelector.setModifierKey(SWT.CONTROL);
        itemSelector.notifyListeners(SWT.KeyDown, event);
    }

    private Event getKeyEventData(final KeyEvent e) {
        Event event = new Event();
        event.stateMask = SWT.CONTROL;
        switch (e.keyCode) {
        case SWT.ARROW_UP: {
            event.keyCode = SWT.ARROW_UP;
            break;
        }
        case SWT.ARROW_DOWN: {
            event.keyCode = SWT.ARROW_DOWN;
            break;
        }
        case SWT.ARROW_LEFT: {
            event.keyCode = SWT.ARROW_LEFT;
            break;
        }
        case SWT.ARROW_RIGHT: {
            event.keyCode = SWT.ARROW_RIGHT;
            break;
        }
        }
        return event;
    }
}
