package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IDuration;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.generic.GenericTypePackage;
import com.zealcore.se.core.model.generic.IGenericLogItem;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.graphics.Graphics;
import com.zealcore.se.ui.graphics.IColor;
import com.zealcore.se.ui.graphics.figures.Event;
import com.zealcore.se.ui.graphics.figures.ObjectFigure;
import com.zealcore.se.ui.graphics.figures.TaskExecution;
import com.zealcore.se.ui.graphics.figures.TaskLaneEntry;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.preferences.GanttPreferencePage;
import com.zealcore.se.ui.util.TimeFormat;

class ClassicGantRenderer {

    private static final GanttDraw EMPTY_GANTT_DRAW = new GanttDraw(null, null);

    private static long TIME_RANGE = 1000000000;

    private static final double VERIFCAL_MARKER_ADJUST = 1.5;

    private static final int FONT_SIZE = 9;

    private static final int LABEL_OFFSET_X = 4;

    public static final int LEGEND_HEIGHT = 15;

    private static final int LANE_START = 40 + LEGEND_HEIGHT;

    private static final int DURATION_HEIGHT = 6;

    private static final int DURATION_MARGIN = 5;

    private static final int LANE_HEIGHT = ClassicGantRenderer.DURATION_HEIGHT
            + ClassicGantRenderer.DURATION_MARGIN
            + ClassicGantRenderer.DURATION_MARGIN;

    private final Map<String, TaskLaneEntry> lanes = new HashMap<String, TaskLaneEntry>();

    /** Used to scale figures, should eventually become an IRuler * */
    private IDrawableRuler ruler;

    private final Font font;

    private boolean doDrawEvents;

    private String[] typeArray;

    private ITypeRegistry typeReg;

    private final Map<IType, Color> colorByTypeMap = new HashMap<IType, Color>();

    ClassicGantRenderer() {
        font = new Font(Display.getDefault(), "Arial",
                ClassicGantRenderer.FONT_SIZE, SWT.NORMAL);
    }

    GanttDraw drawA(final GanttRenderingData data) {
        return draw(data, false);
    }

    GanttDraw drawB(final GanttRenderingData data) {
        return draw(data, true);
    }

    @SuppressWarnings("unchecked")
    GanttDraw draw(final GanttRenderingData data, final boolean drawB) {

        // Check pre-conditions JEKB ugly fix data.getDurations() == null
        if ((data.getDurations() == null) || (data.getDurations().size() < 1)) {
            drawEmpty(data);
            return ClassicGantRenderer.EMPTY_GANTT_DRAW;
        }

        Collections.sort(data.getDurations());
        
        //compute a ruler only when drawB is drawn.
        if (drawB) {
            ruler = computeScaler(data);
        }

        // Graphics Setup
        data.getGraphics().setAntialias(SWT.OFF);
        data.getGraphics().setFont(font);
        final GanttDraw ganttDrawData = new GanttDraw(data.getGraphics(), ruler);
        lanes.clear();
        int laneIndex = 0;
        for (final IArtifact user : data.getTasks()) {
            final TaskLaneEntry entry = new TaskLaneEntry(user);
            entry.setLane(laneIndex++);
            lanes.put(user.getName(), entry);
        }

        drawColumnNames(data, lanes, ganttDrawData);
        drawGrid(data, laneIndex);
        
        final Rectangle clipping = data.getGraphics().getClipping();
        data.getGraphics().setClipping(
                new Rectangle(data.getBounds().x, data.getBounds().y, data
                        .getBounds().width, data.getBounds().height));

        this.typeReg = TypeRegistry.getInstance();
        
        data.getGraphics().setForeground(
                Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

        data.getGraphics().setBackground(
                Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

        if (drawB) {
            drawDurations(data, ganttDrawData);
            drawMarker(data);
            drawRuler(data);
            LegendRenderer r = new LegendRenderer();
            r.drawLegend(data.getGraphics().getClipping().width / 2, Graphics
                    .valueOf(data.getGraphics()), colorByTypeMap, false);
        }
        
        data.getGraphics().setClipping(clipping);

        data.setDrawnSize(new Point(data.getBounds().width,
                ClassicGantRenderer.LANE_START + (lanes.size() + 1)
                        * ClassicGantRenderer.LANE_HEIGHT));

        // if (drawB) {
        ganttDrawData.setBounds(SWTUtil.convert(data.getBounds()));
        // } else {
        // }
        return ganttDrawData;
    }

    private void drawEmpty(final GanttRenderingData data) {
        final Color pushed = data.getGraphics().getBackground();
        data.getGraphics().setBackground(
                data.getGraphics().getDevice().getSystemColor(
                        SWT.COLOR_WIDGET_BACKGROUND));
        data.getGraphics().fillRectangle(data.getGraphics().getClipping());
        data.getGraphics().drawText("No Gantt data available", 1, 1);
        data.getGraphics().setBackground(pushed);
    }

    private void drawMarker(final GanttRenderingData data) {
        final int markerX = ruler.toScreen(data.getMarker())
                + data.getBounds().x / 2;
        if (markerX < ruler.min()) {
            return;
        }
        data.getGraphics().drawString(
                TimeFormat.format(data.getFormatString(), data.getMarker()),
                markerX + LABEL_OFFSET_X,
                (int) (LANE_HEIGHT * VERIFCAL_MARKER_ADJUST + LEGEND_HEIGHT),
                true);
        data.getGraphics().drawLine(markerX, LANE_HEIGHT + LEGEND_HEIGHT,
                markerX, LANE_START + lanes.size() * LANE_HEIGHT);

    }

    private void drawDurations(final GanttRenderingData data,
            final GanttDraw ganttDrawData) {
        colorByTypeMap.clear();
        for (final IDuration d : data.getDurations()) {
            drawDurationItem(data, d, ganttDrawData);
            if (d.contains(data.getMarker())) {
                data.setMarkerAt(d);
            }
        }
    }

    private void drawDurationItem(final GanttRenderingData data,
            final IDuration d, final GanttDraw ganttDrawData) {
        final TaskLaneEntry taskFigure = lanes.get(d.getOwner().getName());

        // if task is filtered out
        if (taskFigure == null) {
            return;
        }
        final int y = LANE_START + taskFigure.getLane() * LANE_HEIGHT
                + DURATION_MARGIN;

        int xStart = ruler.toScreen(d.getStartTime()) - data.getBounds().x / 2;

        final Rectangle dataArea = getDataArea(data);
        dataArea.x = 0;
        if (xStart < dataArea.x) {
            xStart = dataArea.x;
        }
        int xEnd = ruler.toScreen(d.getStartTime() + d.getDurationTime())
                - data.getBounds().x / 2;
        if (xEnd < dataArea.x) {
            return;
        }
        if (xEnd - xStart < 1) {
            xEnd = xStart + 1;
        }

        final Rectangle rect = new Rectangle(xStart, y, (xEnd - xStart),
                DURATION_HEIGHT);

        // If the rectangle width is enormous (when zooming a lot) the clipping
        // mechanism is not working, therefore limit the width below manually
        if (rect.width > dataArea.width) {
            rect.width = dataArea.width;
        }

        final ObjectFigure exec = new TaskExecution(d, rect);
        exec.setBackgroundColor(taskFigure.getBackgroundColor());

        doDrawEvents = SeUiPlugin.getDefault().getPreferenceStore().getBoolean(
                GanttPreferencePage.TAG_SHOW_EVENTS);
        if (doDrawEvents) {
            String idz = SeUiPlugin.getDefault().getPreferenceStore()
                    .getString(GanttPreferencePage.TAG_SHOW_EVENTS_IDS);
            if (typeReg != null && idz.length() > 0) {
                this.typeArray = idz.split(GanttPreferencePage.SEPARATOR);
            }
            if (typeArray != null && typeArray.length > 0) {
                drawLogEvents(data, d, ganttDrawData, y);
            }
        }
        ganttDrawData.add(exec);
    }

    @SuppressWarnings("unchecked")
	private void drawLogEvents(final GanttRenderingData data,
            final IDuration d, final GanttDraw ganttDrawData, final int y) {
        List events = data.getEvents();

        AbstractLogEvent seed = new AbstractLogEvent() {};
        seed.setTs(d.getStartTime());
        int index = Collections.binarySearch(events, seed);
        index = index < 0 ? -index - 1 : index;

        Object durationCore =0;
        Object genericCore =0;
        short durationCoreS = 0;
        short genericCoreS = 0;
        
        for (int i = index; i < data.getEvents().size(); i++) {
            ILogEvent e = data.getEvents().get(i);
            if (!d.contains(e.getTs())) {
                break;
            }
          
            if (d instanceof IGenericLogItem && e instanceof IGenericLogItem) {
                final IGenericLogItem durationEvent = (IGenericLogItem) d;
                durationCore = durationEvent.getProperty(GenericTypePackage.EXECUTION_UNIT);
                final IGenericLogItem genericEvent = (IGenericLogItem) e;
                genericCore = genericEvent.getProperty(GenericTypePackage.EXECUTION_UNIT);
                if(durationCore != null && genericCore != null) {
                	durationCoreS = (Short)durationCore;
                	genericCoreS = (Short)genericCore;
                	if(durationCoreS != genericCoreS) {
                    	continue;
                    }
                }
                
            }
            for (int ii = 0; ii < typeArray.length; ii++) {
                try {
                    IType type2 = typeReg.getType(typeArray[ii]);
                    if (type2 != null) {
                        if (e.getType() == type2) {
                            final int x = ruler.toScreen(e.getTs());
                            if (x < 0) {
                                break;
                            } else {
                                final Event event = new Event(e, x, y, 2,
                                        DURATION_HEIGHT);
                                ganttDrawData.add(event);
                                event.setZ(10000000);

                                // Actual element
                                final IColor eventColor = data
                                        .getColorProvider().getColor(
                                                e.getType());
                                colorByTypeMap.put(e.getType(), eventColor
                                        .toColor());
                                if (eventColor == null) {
                                    event.setBackgroundColor(SWTResourceManager
                                            .getColor(200, 20, 40));
                                } else {
                                    event.setBackgroundColor(eventColor
                                            .toColor());
                                }
                                break;
                            }
                        }
                    }
                } catch (IllegalArgumentException err) {
                    // Ignore type, does not exist any more.
                }
            }
        }
    }

    private IDrawableRuler computeScaler(final GanttRenderingData data) {
    	final Rectangle dataScreenArea = getDataArea(data);
    	if (data.isLog()) {
    		List<Span> spans = new ArrayList<Span>(Span.valueOfDurations(data
                    .getDurations()));

            if (data.getDurations().size() > 0) {
                ITaskDuration first = data.getDurations().get(0);
                ITaskDuration last = data.getDurations().get(
                        data.getDurations().size() - 1);
                Span emptyBefore = Span.valueOf(data.getLogspan().getStart(), first
                        .getStartTime());
                spans.add(0, emptyBefore);

                Span emptyAfter = Span.valueOf(last.getStartTime()
                        + last.getDurationTime(), data.getLogspan().getEnd());
                spans.add(emptyAfter);

            }
            return new FixedMidPointRuler(dataScreenArea.x, dataScreenArea.width,
                    data.getTransform(), data.getMarker(), spans);

        } else {
            long firstDuration = data.getLogspan().getStart();
            long lastDuration = 0;

            if (data.getMarker() <= 0) {
                lastDuration = (long) (firstDuration + ((TIME_RANGE / data
                        .getZoomLevel()) / 2));
            } else {
                firstDuration = (long) (data.getMarker() - ((TIME_RANGE / data
                        .getZoomLevel()) / 2));
                if (firstDuration < 0) {
                    firstDuration = data.getLogspan().getStart();
                }
                lastDuration = (long) (data.getMarker() + ((TIME_RANGE / data
                        .getZoomLevel()) / 2));
            }

            return new SimpleLinearScaleRuler(data.getMarker(), Span.valueOf(
                    firstDuration, lastDuration), Span.valueOf(
                    dataScreenArea.x, dataScreenArea.width));
        }
    }

    private Rectangle getDataArea(final GanttRenderingData data) {
        final int dataScreenX = data.getBounds().x;
        final int dataScreenWidth = data.getBounds().width - dataScreenX;

        final Rectangle dataScreenArea = new Rectangle(dataScreenX, 0,
                dataScreenWidth, LANE_START + LANE_HEIGHT * lanes.size());
        return dataScreenArea;
    }

    private void drawColumnNames(final GanttRenderingData data,
            final Map<String, TaskLaneEntry> lanes, final GanttDraw draw) {
        for (final Entry<String, TaskLaneEntry> entry : lanes.entrySet()) {
            final TaskLaneEntry taskEntry = entry.getValue();

            final RGB rgb = data.getColorMap().getColor(taskEntry.getUser());
            final Color resourceUserColor = new Color(data.getGraphics()
                    .getDevice(), rgb);
            taskEntry.setBackgroundColor(resourceUserColor);
            final int laneYStart = LANE_START + taskEntry.getLane()
                    * LANE_HEIGHT;
            final Rectangle resourceBoundingBox = new Rectangle(0, laneYStart
                    - data.getScrollOffset(), data.getSashXPos(), LANE_HEIGHT);
            taskEntry.setBounds(resourceBoundingBox);
            draw.add(taskEntry);
        }
    }

    private void drawRuler(final GanttRenderingData data) {
        ruler.draw(data.getDurations(), data.getGraphics(), data.getGraphics()
                .getClipping().x, data.getGraphics().getClipping().width);
    }

    private void drawGrid(final GanttRenderingData data, final int laneIndex) {

        final Rectangle clipping = data.getGraphics().getClipping();
        data.getGraphics().setClipping(getDataArea(data));
        data.getGraphics().setClipping(clipping);

        data.getGraphics().setForeground(
                Display.getDefault().getSystemColor(SWT.COLOR_GRAY));

        final int x2 = data.getBounds().x + data.getBounds().width;
        int y = LANE_START - data.getScrollOffset();
        data.getGraphics().drawLine(0, y, x2, y);

        for (int i = 1; i <= laneIndex; i++) {
            y += LANE_HEIGHT;
            data.getGraphics().drawLine(0, y, x2, y);
        }
        data.getGraphics().drawLine(-1, LANE_START, -1,
                LANE_START + laneIndex * LANE_HEIGHT);
    }
    
    public IDrawableRuler getRuler() {
        return ruler;
    }

}
