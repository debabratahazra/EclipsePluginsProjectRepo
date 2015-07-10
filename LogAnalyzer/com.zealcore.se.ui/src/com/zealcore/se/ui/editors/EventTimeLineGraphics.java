package com.zealcore.se.ui.editors;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.graphics.IColor;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.graphics.figures.Horizon;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.util.TimeFormat;

class EventTimeLineGraphics implements IEventTimeLineRenderer {

    private static final class TimedComparator implements Comparator<ITimed> {
        public int compare(final ITimed o1, final ITimed o2) {
            return o1.getTimeReference().compareTo(o2.getTimeReference());
        }
    }

    private static final int MARKER_ARROW_SIZE = 8;

    private static final int OBJECT_SNAP_ALPHA = 100;

    private static final int X_TIMES_MARKERHEIGHT_RULER_Y_OFFSET = -1;

    private static final int TWO = 2;

    private static final int MARKER_HEIGHT = 16;

    private static final int MARKER_WIDTH = 3;
    
    private static long TIME_RANGE = 1000000000;

    private final Map<IType, Color> colorByTypeMap = new HashMap<IType, Color>();

    private final Map<Rectangle, ILogEvent> drawMap = new HashMap<Rectangle, ILogEvent>();

    private IDrawableRuler horizontalRuler;

    private boolean multipleEventsAtSameTs;

    private static final int OSNAP_X_MARGIN = 4;

    private static final int OSNAP_Y_MARGIN = 4;

    private static final int MIN_EVENT_HORIZON_WIDTH = 20;

    private static final String STAR = "*";

    public void draw(final EventTimeLineData data) {

        // Do not forget to clear the drawMap - if you do, this can lead to
        // strange bugs
        this.drawMap.clear();

        Collections.sort(data.getLogEvents(), new TimedComparator());
        if (data.getLogEvents() == null || data.getLogEvents().size() < 1) {
            drawEmptyInfo(data);
            return;
        }

        this.horizontalRuler = EventTimeLineGraphics.computeScaler(data
                .getScreenSize(), data);

        if (data.getScreenSize().width <= 0) {
            return;
        }

        final int timeLineY = drawTimeline(data);
        final int eventYStart = timeLineY - EventTimeLineGraphics.MARKER_HEIGHT
                / EventTimeLineGraphics.TWO;
        final int markerX = this.horizontalRuler
                .toScreen(data.getCurrentTime());

        if (markerX > MIN_EVENT_HORIZON_WIDTH) {
            drawMarker(data, eventYStart, markerX);
        }
        drawEvents(data, eventYStart);
        drawEventHorizon(data, eventYStart);

        if (data.getObjectSnap() != null) {
            drawObjectSnap(data, eventYStart);
        }
        drawRuler(data);
        LegendRenderer r = new LegendRenderer();

        r.drawLegend(data.getScreenSize().x + data.getScreenSize().width
                / EventTimeLineGraphics.TWO, data.getGc(), colorByTypeMap,
                multipleEventsAtSameTs);
        return;
    }

    private void drawEmptyInfo(final EventTimeLineData data) {
        final Color pushed = data.getGc().getBackground();
        data.getGc().setBackground(
                data.getGc().getDevice().getSystemColor(
                        SWT.COLOR_WIDGET_BACKGROUND));
        data.getGc().fillRectangle(data.getGc().getClipping());
        data.getGc().drawText("No event data available", 1, 1);
        data.getGc().setBackground(pushed);
    }

    private void drawEventHorizon(final EventTimeLineData data,
            final int eventYStart) {

        final int screenX = data.getScreenSize().x;
        final int screenWidth = data.getScreenSize().width;

        if (data.getLogEvents().size() < 1) {
            return;
        }

        data.getGc().setBackground(
                Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));

        final int eventHorizonHeight = EventTimeLineGraphics.MARKER_HEIGHT * 2;
        final int eventHorizonY = eventYStart
                - EventTimeLineGraphics.MARKER_HEIGHT / 2;

        int leftHorizonX = EventTimeLineGraphics.MIN_EVENT_HORIZON_WIDTH;

        final ILogEvent first = data.getLogEvents().get(0);
        leftHorizonX = Math.max(leftHorizonX, this.horizontalRuler
                .toScreen(first.getTs()) - 5);

        final Rectangle left = new Rectangle(screenX, eventHorizonY,
                leftHorizonX - screenX, eventHorizonHeight);
        final Horizon lh = new Horizon(left, SWT.LEFT);

        // Right Event Horizon

        final ILogEvent lastEvent = data.getLogEvents().get(
                data.getLogEvents().size() - 1);

        int rightHventHorizonX = data.getScreenSize().x
                + data.getScreenSize().width
                - EventTimeLineGraphics.MIN_EVENT_HORIZON_WIDTH;

        final int right = this.horizontalRuler.toScreen(lastEvent.getTs());
        if (right >= 0) {
            rightHventHorizonX = Math.min(rightHventHorizonX, right + 3);
        }

        final Horizon rh = new Horizon(new Rectangle(rightHventHorizonX,
                eventHorizonY, screenX + screenWidth - rightHventHorizonX,
                eventHorizonHeight), SWT.RIGHT);

        final SWTGraphics draw2d = new SWTGraphics(data.getGc().getSWT());
        rh.paint(draw2d);
        lh.paint(draw2d);

    }

    private void drawEvents(final EventTimeLineData data, final int y) {
        // only cache visible types
        this.colorByTypeMap.clear();
        long lastTs = -1;
        multipleEventsAtSameTs = false;
        for (final ILogEvent event : data.getLogEvents()) {
            if (lastTs == event.getTs()) {
                multipleEventsAtSameTs = true;
                drawLogEvent(data, y, event, true);
            } else {
                drawLogEvent(data, y, event, false);
            }
            lastTs = event.getTs();
        }
    }

    private void drawObjectSnap(final EventTimeLineData data, final int y) {
        final int x = this.horizontalRuler.toScreen(data.getObjectSnap()
                .getTs())
                - EventTimeLineGraphics.TWO;
        if (x <= this.horizontalRuler.min()) {
            return;
        }
        final Color lColor = data.getGc().getForeground();
        final int two = EventTimeLineGraphics.TWO;

        data.getGc().setBackground(
                Display.getDefault().getSystemColor(SWT.COLOR_GRAY));

        final int alpha = data.getGc().getAlpha();
        data.getGc().setAlpha(EventTimeLineGraphics.OBJECT_SNAP_ALPHA);
        final Rectangle osnap = new Rectangle(x
                - EventTimeLineGraphics.OSNAP_X_MARGIN, y
                - EventTimeLineGraphics.OSNAP_Y_MARGIN,
                EventTimeLineGraphics.MARKER_WIDTH
                        + EventTimeLineGraphics.OSNAP_X_MARGIN * two - 1,
                EventTimeLineGraphics.MARKER_HEIGHT
                        + EventTimeLineGraphics.OSNAP_Y_MARGIN * two - 1);
        data.getGc().fillRectangle(osnap);
        data.getGc().setForeground(
                Display.getDefault().getSystemColor(SWT.COLOR_RED));
        data.getGc().drawRectangle(osnap);
        data.getGc().setForeground(lColor);
        data.getGc().setAlpha(alpha);

        drawLogEvent(data, y, data.getObjectSnap(), false);
    }

    private void drawMarker(final EventTimeLineData data, final int timeLiney,
            final int markerX) {
        final IGraphics gc = data.getGc();

        final int arrowWidth = EventTimeLineGraphics.MARKER_ARROW_SIZE;

        final int arrowHeadY = timeLiney;
        final int[] polygon = new int[] { markerX, arrowHeadY,
                markerX + arrowWidth, arrowHeadY - arrowWidth,
                markerX - arrowWidth, arrowHeadY - arrowWidth, };

        gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        gc.fillPolygon(polygon);

        gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
        final String markerString = TimeFormat.format(data.getTimeFormat(),
                data.getCurrentTime());
        final int textLength = SWTUtil.textLength(data.getGc().getSWT(),
                markerString);
        gc.drawString(markerString, markerX - textLength
                / EventTimeLineGraphics.TWO, arrowHeadY
                - EventTimeLineGraphics.MARKER_HEIGHT - arrowWidth, true);
    }

    private void drawLogEvent(final EventTimeLineData data, final int y,
            final ILogEvent event, final boolean sameTsAsLastEvent) {
        if (!isVisible(data, event)) {
            return;
        }
        final int x = this.horizontalRuler.toScreen(event.getTs())
                - EventTimeLineGraphics.TWO;
        if (x < this.horizontalRuler.min()) {
            return;
        }
        Rectangle rect = new Rectangle(x, y,
                EventTimeLineGraphics.MARKER_WIDTH,
                EventTimeLineGraphics.MARKER_HEIGHT);

        this.drawMap.put(rect, event);

        // Actual element
        final IColor eventColor = data.getColorProvider().getColor(
                event.getType());
        IGraphics graphics = data.getGc();
        if (eventColor == null) {
            graphics.setBackground(Display.getDefault().getSystemColor(
                    SWT.COLOR_BLACK));
        } else {
            graphics.setBackground(eventColor.toColor());
        }
        this.colorByTypeMap.put(event.getType(), graphics.getBackground());

        if (sameTsAsLastEvent) {
            graphics.setLineWidth(2);
            graphics.drawRectangle(rect);
            graphics.setBackground(Display.getDefault().getSystemColor(
                    SWT.COLOR_WHITE));
            graphics.drawString(STAR, rect.x + rect.width + 2, rect.y + 14);
            graphics.setLineWidth(1);
        } else {
            graphics.fillRectangle(rect);
        }

    }

    private boolean isVisible(final EventTimeLineData data,
            final ILogEvent event) {
        final int eventX = this.horizontalRuler.toScreen(event.getTs()) - 1;
        return eventX >= data.getScreenSize().x
                && eventX <= data.getScreenSize().x
                        + data.getScreenSize().width;
    }

    static IDrawableRuler computeScaler(final Rectangle bounds,
            final EventTimeLineData data) {
        if (data.isLog()) {
            final Collection<Span> spans = Span.valueOf(data.getLogEvents());
            return new FixedMidPointRuler(bounds.x, bounds.width,
                    data.getTransform(), data.getCurrentTime(), spans);
        } else {
            long firstDuration = data.getLogspan().getStart();
            long lastDuration = 0;
            double zoomLevel = data.getZoomLevel();

            if (data.getCurrentTime() <= 0) {
                lastDuration = (long) (firstDuration + ((TIME_RANGE / zoomLevel) / 2));
            } else {
                firstDuration = (long) (data.getCurrentTime() - ((TIME_RANGE / zoomLevel) / 2));
                if (firstDuration < 0) {
                    firstDuration = data.getLogspan().getStart();
                }
                lastDuration = (long) (data.getCurrentTime() + ((TIME_RANGE / zoomLevel) / 2));
            }
            
            return new SimpleLinearScaleRuler(data.getCurrentTime(), Span.valueOf(
                    firstDuration, lastDuration), Span.valueOf(
                    bounds.x, bounds.width));
        }
    }

    private int drawTimeline(final EventTimeLineData data) {

        final int midY = data.getScreenSize().y + data.getScreenSize().height
                / EventTimeLineGraphics.TWO;

        // Draw timline
        final Color black = Display.getDefault()
                .getSystemColor(SWT.COLOR_BLACK);
        data.getGc().setForeground(black);
        data.getGc().drawLine(data.getScreenSize().x, midY,
                (data.getScreenSize().x + data.getScreenSize().width), midY);
        return midY;
    }

    private void drawRuler(final EventTimeLineData data) {

        final Transform tmp = new Transform(data.getGc().getDevice());
        data.getGc().getTransform(tmp);

        final Transform offset = new Transform(data.getGc().getDevice());
        // Translate the ruler 0 in x and to the middle then 3x marker_height up
        offset.translate(0, data.getScreenSize().height
                / EventTimeLineGraphics.TWO
                - EventTimeLineGraphics.MARKER_HEIGHT
                * EventTimeLineGraphics.X_TIMES_MARKERHEIGHT_RULER_Y_OFFSET);
        data.getGc().setTransform(offset);

        this.horizontalRuler.draw(data.getLogEvents(), data.getGc(), 0, data
                .getScreenSize().width);

        data.getGc().setTransform(tmp);
        offset.dispose();
        tmp.dispose();

    }

    /**
     * Gets the event at coordinate x,y (Bounds coordinates, not value
     * coordinates)
     * 
     * @param x
     *                the screen x coordinate
     * @param y
     *                the screen y coordinate
     * @return the AbstractLogEvent found at x,y
     */
    public ILogEvent getEventAt(final int x, final int y) {
        for (final Entry<Rectangle, ILogEvent> entry : this.drawMap.entrySet()) {
            if (entry.getKey().contains(x, y)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Gets the event at coordinate x,y (Bounds coordinates, not value
     * coordinates)
     * 
     * @param x
     *                the screen x coordinate
     * @param y
     *                the screen y coordinate
     * @return the AbstractLogEvent found at x,y
     */
    public ILogEvent getBoxedEventAt(final int x, final int y) {
        for (final Entry<Rectangle, ILogEvent> entry : this.drawMap.entrySet()) {
            final Rectangle box = new Rectangle(0, 0, 0, 0);
            box.x = entry.getKey().x;
            box.y = entry.getKey().y;
            box.height = entry.getKey().height;
            box.width = entry.getKey().width;

            box.x -= EventTimeLineGraphics.OSNAP_X_MARGIN;
            final int two = 2;
            box.width += two * EventTimeLineGraphics.OSNAP_X_MARGIN - 1;
            box.y -= EventTimeLineGraphics.OSNAP_Y_MARGIN;
            box.height += two * EventTimeLineGraphics.OSNAP_Y_MARGIN - 1;
            if (box.contains(x, y)) {
                return entry.getValue();
            }
        }
        return null;
    }

    void dispose() {
        this.drawMap.clear();
    }
    
    public IDrawableRuler getRuler() {
        return horizontalRuler;
    }
}
