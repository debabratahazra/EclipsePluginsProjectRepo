/*
 * 
 */
package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.generic.GenericTypePackage;
import com.zealcore.se.core.model.generic.IGenericLogItem;
import com.zealcore.se.core.util.Span;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.graphics.IColor;
import com.zealcore.se.ui.graphics.IGraphics;
import com.zealcore.se.ui.internal.IDistanceTransformer;
import com.zealcore.se.ui.internal.LogaritmicDistanceTransformer;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.util.ArtifactColorMap;
import com.zealcore.se.ui.util.StringMatcher;

/**
 * A renderer for SequenceDiagrams
 * 
 * @author stch, cafa
 * 
 */
class SequenceDiagramRenderer {

    private static final int SHORT_ACTOR_NAME_LEN = 10;

    private static final String STAR = "*";

    private static final String GENERIC_RECEIVE_EVENT = "class com.zealcore.se.core.model.generic.GenericReceiveEvent";

    private static final int ACTOR_TOP_MARGIN = 15;

    private static final int ACTOR_ACTIVITY_MARGIN = 15;

    private static final int ACTOR_LABEL_Y_MARGIN = 13;

    private static final int ACTOR_LABEL_X_MARGIN = 10;

    private static final int ACTOR_Y_MARGIN = 12;

    private static final int ACTOR_LEFT_MARGIN = 8;

    private static final int ACTIVITY_LINE_DASH = 5;

    private static final int HALF = 2;

    private static final int TWO = 2;

    private static final int ELEMENT_SIZE = 6;

    public static final float DEFAULT_ZOOM_LEVEL = 0;

    private static final int MINIMUM_ACTOR_WIDTH = 62;

    private static final int DEFAULT_ACTOR_BOX_HEIGHT = 50;

    private static final int DEFAULT_DELTA = 10000;

    private Transform translate;

    private final ActivityBoxRenderer activityRenderer = new ActivityBoxRenderer();

    private final MessageRenderer messageRenderer = new MessageRenderer();

    private final List<ObjectEntry> entities = new ArrayList<ObjectEntry>();

    private IRuler verticalRuler;

    private final Map<ISequenceMember, Point> actorPositions = new HashMap<ISequenceMember, Point>();

    private final Map<IType, Color> colorByTypeMap = new HashMap<IType, Color>();

    private boolean multipleEventsAtSameTs;

    private int actorActivityMargin = 54;

    private int lastMaxActorBoxHeight;

    private int newMaxActorBoxHeight;

    /**
     * Draws the activities, actors and messages onto the IGraphics context. If
     * the activities is empty, nothing will be drawn.
     * 
     * @param graphics
     *            the graphics may not be null
     * @param activities
     *            the activities may not be null
     * @param markerValue
     *            the marker value
     */
    void draw(final IGraphics graphics, final SequenceRenderingData data,
            final IEventColorProvider colorProvider) {
        entities.clear();
        actorPositions.clear();
        actorActivityMargin = ACTOR_ACTIVITY_MARGIN + ACTOR_Y_MARGIN
                + lastMaxActorBoxHeight;

        if (data.elements == null) {
            data.elements = new ArrayList<ITimed>(0);
        }

        final Set<ISequenceMember> actors = new LinkedHashSet<ISequenceMember>();
        actors.addAll(extractActors(data, actors));
        if (actors.isEmpty() || data.getTimestamps().size() < 1) {
            drawEmptyInfo(graphics);
            return;
        }
        // apply filters
        if (filteredActors.size() > 0) {
            actors.removeAll(filteredActors);
        }

        // Do the actual drawing
        draw(graphics, actors, data, colorProvider);
    }

    private void drawEmptyInfo(final IGraphics graphics) {
        final Color pushed = graphics.getBackground();
        graphics.setBackground(graphics.getDevice().getSystemColor(
                SWT.COLOR_WIDGET_BACKGROUND));
        graphics.fillRectangle(graphics.getClipping());
        graphics.drawText("No sequence diagram data available", 1, 1);
        graphics.setBackground(pushed);
    }

    /**
     * Extracts actors and time span from the {@link SequenceRenderingData}. The
     * span is the min/max timestamp contained within the data
     * 
     * @param data
     *            the data
     * @param actors
     *            the actors to add found other actors to.
     * 
     * @return the time span
     */
    @SuppressWarnings("unchecked")
    Collection<? extends ISequenceMember> extractActors(
            final SequenceRenderingData data, final Set<ISequenceMember> actors) {
        // Loop through all members, and the messages of these members
        // To see for all actors that should be visible

        final Set<ISequenceMember> unsortedMembers = new LinkedHashSet<ISequenceMember>();
        unsortedMembers.addAll(data.getMembers());
        final Collection<ITimed> elements = data.elements == null ? Collections.EMPTY_LIST
                : data.elements;

        for (final ISequenceMessage message : data.getMessages()) {
            unsortedMembers.add(message.getSender());
            unsortedMembers.add(message.getRecipent());
        }
        for (final ITimed element : elements) {
            if (element instanceof IActivity) {
                IActivity box = (IActivity) element;
                unsortedMembers.add(box.getOwner());
            }
        }
        return unsortedMembers;
    }

    private void draw(final IGraphics graphics,
            final Set<ISequenceMember> actors,
            final SequenceRenderingData data,
            final IEventColorProvider colorProvider) {

        verticalRuler = getTransform(data);

        data.setPreferedWidth(0);
        graphics.setAntialias(SWT.ON);
        graphics.setBackground(graphics.getDevice().getSystemColor(
                SWT.COLOR_BLACK));

        drawMarkerValue(graphics, data.markerValue);
        drawActors(actors, data, graphics);
        graphics.setTransform(null);
        drawActivities(data, graphics, colorProvider);
        drawMessages(data, graphics);
        LegendRenderer r = new LegendRenderer();
        r.drawLegend(400, graphics, colorByTypeMap, multipleEventsAtSameTs);
    }

    private IRuler getTransform(final SequenceRenderingData data) {

        final List<Long> timeStamps = data.getTimestamps();
        final Collection<Span> spans = new ArrayList<Span>();
        Long previous = timeStamps.get(0);
        for (int i = 1; i < timeStamps.size(); i++) {
            final Long current = timeStamps.get(i);
            spans.add(Span.valueOf(previous, current));
            previous = current;
        }

        IDistanceTransformer transform = getTransform();
        return new FixedMidPointRuler(actorActivityMargin,
                data.getWidgetSize().height, transform, data.getMarkerValue(),
                spans);
    }

    public IDistanceTransformer getTransform() {
        if (data.isLogarithmic()) {
            return new LogaritmicDistanceTransformer(
                    CommonConstants.LOG_TRANSFORM, data.getZoomLevel());

        }
        return new IDistanceTransformer() {
            public double transform(final double delta) {
                return (delta / 10000) * 2 * data.getZoomLevel();
            }
        };
    }

    private void drawMarkerValue(final IGraphics graphics,
            final long markerValue) {
        final int pos = verticalRuler.toScreen(markerValue);
        if (pos < verticalRuler.min()) {
            return;
        }
        final int[] dash = graphics.getLineDash();
        graphics.setLineDash(new int[] { ACTIVITY_LINE_DASH
                + ACTIVITY_LINE_DASH, });
        final Rectangle bounds = graphics.getClipping();
        graphics.drawLine(0, pos, bounds.x + bounds.width, pos);
        graphics.setLineDash(dash);
    }

    /**
     * Returns the element that have a rectangle painted around the cordinate
     * represented by x and y. The objects that are an event are searched first
     * since an Activity have a wider rectangle.
     * 
     * @param x
     * @param y
     * @return The object found that have a rectangle around the specified point
     *         x,y, otherwise null.
     */
    Object getElementAt(final int x, final int y) {
        for (final ObjectEntry entry : entities) {
            Object element = entry.getElement();
            if (!(element instanceof IActivity)
                    || (element.getClass().toString()
                            .equals(GENERIC_RECEIVE_EVENT))) {
                if (entry.containts(x, y)) {
                    return element;
                }
            }
        }
        for (final ObjectEntry entry : entities) {
            if (entry.containts(x, y)) {
                return entry.getElement();
            }
        }
        return null;
    }

    /**
     * Draws the messages for the given diagram onto the graphics.
     * 
     * @param graphics
     *            the graphics
     * @param diagram
     *            the diagram
     */
    private void drawMessages(final SequenceRenderingData data,
            final IGraphics graphics) {
        /*
         * Clipping causes problems on Solaris 10
         */
        // final Rectangle clip = graphics.getClipping();
        // graphics.setClipping(clip.x, actorActivityMargin, clip.width,
        // clip.height);
        translate = new Transform(graphics.getDevice());
        graphics.setTransform(translate);
        translate.dispose();

        final StringMatcher matcher = new StringMatcher(data.getFilterText()
                + STAR, true, false);
        final List<ISequenceMessage> messages = new ArrayList<ISequenceMessage>(
                data.getMessages());

        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).setDrawingTs(messages.get(i).getTs());
        }
        for (int i = 0; i < messages.size(); i++) {
            int startIndex = i;
            int endIndex = i;
            for (int j = i + 1; j < messages.size(); j++) {
                if (messages.get(i).getDrawingTs() == messages.get(j)
                        .getDrawingTs()) {
                    if (startIndex == i) {
                        startIndex = j;
                    }
                } else if ((startIndex != i)
                        && (messages.get(i).getDrawingTs() < messages.get(j)
                                .getDrawingTs())) {
                    endIndex = j;
                    break;
                }
            }
            if ((endIndex == i) && (startIndex != i)) {
                endIndex = messages.size();
            }
            if (endIndex - startIndex > 0) {
                long delta = DEFAULT_DELTA;
                if (endIndex < messages.size()) {
                    delta = (messages.get(endIndex).getDrawingTs() - messages
                            .get(i).getDrawingTs())
                            / (endIndex - startIndex + 1);
                }
                for (int j = startIndex; j < endIndex; j++) {
                    messages.get(j).setDrawingTs(
                            messages.get(j).getDrawingTs() + delta);
                }
            }
        }

        for (final ISequenceMessage message : messages) {
            // Check for matches (or null message)
            if (message.getMessage() == null
                    || matcher.match(message.getMessage())) {
                messageRenderer.drawMessage(graphics, message, entities,
                        verticalRuler);
            }
        }
        // graphics.setClipping(clip);
    }

    /**
     * Draws activities for a given diagram onto the graphics. this method also
     * puts entries into the entities.
     * 
     * @param graphics
     *            the graphics
     * @param diagram
     *            the diagram
     */
    private void drawActivities(final SequenceRenderingData data,
            final IGraphics graphics, final IEventColorProvider colorProvider) {

        multipleEventsAtSameTs = false;
        /*
         * Clipping causes problems on Solaris 10
         */
        // final Rectangle clip = graphics.getClipping();
        // graphics.setClipping(clip.x, actorActivityMargin, clip.width,
        // clip.height);
        // TODO Event Horizons for first and last
        data.setPreferedHeight(data.getWidgetSize().height);
        colorByTypeMap.clear();
        int lastActivityX = 0;
        long lastTimeStamp = -1;
        long lastPaintedActivityEndTS = -1;
        short activityCore = 0;
        Object genericCore =0;
        short genericCoreS = 0;
        
        for (final ITimed element : data.elements) {
            if ((element instanceof IActivity)
                    && !(element.getClass().toString()
                            .equals(GENERIC_RECEIVE_EVENT))) {
                IActivity box = (IActivity) element;
                lastPaintedActivityEndTS = box.getEndTime();
                final Rectangle rectangle = activityRenderer.drawActivity(
                        graphics, box, data.colorMap);
                final int y = rectangle.y + rectangle.height
                        + actorActivityMargin;
                if (y > data.getPreferedHeight()) {
                    data.setPreferedHeight(y);
                }
                final ObjectEntry entry = new ObjectEntry(rectangle, box);
                entities.add(entry);

                if (rectangle.x == 0) {
                    final ISequenceMember parent = box.getOwner();
                    final Point point = actorPositions.get(parent);
                    final int boxXcoord = point.x
                            + getActorBoxWidth(graphics, data, parent) / HALF;
                    lastActivityX = boxXcoord - 4;
                } else {
                    lastActivityX = rectangle.x;
                }
                if(element instanceof IGenericLogItem) {
                	IGenericLogItem item = (IGenericLogItem)element;
                	Object object = item.getProperty(GenericTypePackage.EXECUTION_UNIT);
                	if(object != null) {
                		activityCore = (Short)object;
                	}
                }
                
            }
            if (lastActivityX != 0
                    && lastPaintedActivityEndTS >= element.getTimeReference()) {
                int y = verticalRuler.toScreen(element.getTimeReference());
                if (y <= actorActivityMargin) {
                    continue;
                }
                
                if (element instanceof ILogEvent) {
                	if (element instanceof IGenericLogItem) {
                        final IGenericLogItem genericEvent = (IGenericLogItem) element;
                        genericCore = genericEvent.getProperty(GenericTypePackage.EXECUTION_UNIT);
                        if(genericCore != null) {
                        	genericCoreS = (Short)genericCore;
                        	if(activityCore != genericCoreS) {
                            	continue;
                            }
                        }
                        
                    }
               
                    ILogEvent event = (ILogEvent) element;

                    // Actual element
                    final IColor eventColor = colorProvider.getColor(event
                            .getType());

                    Color color;
                    if (eventColor == null) {
                        color = Display.getDefault().getSystemColor(
                                SWT.COLOR_BLACK);
                    } else {
                        graphics.setForeground(eventColor.toColor());
                        color = eventColor.toColor();
                    }
                    this.colorByTypeMap.put(event.getType(), color);
                    final Rectangle rect = new Rectangle(lastActivityX, y, 10,
                            1);
                    graphics.drawRectangle(rect);
                    rect.x -= 1;
                    rect.y -= 1;
                    rect.width += 3;
                    rect.height += 3;
                    graphics.setForeground(Display.getDefault().getSystemColor(
                            SWT.COLOR_BLACK));
                    final ObjectEntry entry = new ObjectEntry(rect, event);
                    entities.add(entry);
                    if (lastTimeStamp == element.getTimeReference()) {
                        graphics.setLineWidth(2);
                        graphics.drawRectangle(rect);
                        graphics.setBackground(Display.getDefault()
                                .getSystemColor(SWT.COLOR_WHITE));
                        graphics.drawString(STAR, rect.x + rect.width + 3,
                                rect.y + 3);
                        graphics.setLineWidth(1);
                        multipleEventsAtSameTs = true;
                    }
                    lastTimeStamp = element.getTimeReference();
                }
            }
        }
        // graphics.setClipping(clip);
    }

    private void drawActors(final Set<ISequenceMember> actors,
            final SequenceRenderingData data, final IGraphics graphics) {
        translate = new Transform(graphics.getDevice());
        final float[] elements = new float[ELEMENT_SIZE];
        translate.getElements(elements);

        graphics.getTransform(translate);
        translate.translate(0, ACTOR_TOP_MARGIN);
        graphics.setTransform(translate);
        if (lastMaxActorBoxHeight == 0) {
            lastMaxActorBoxHeight = DEFAULT_ACTOR_BOX_HEIGHT;
        }
        newMaxActorBoxHeight = 0;
        int totalWidth = 0;
        for (final ISequenceMember actor : actors) {
            totalWidth += getActorBoxWidth(graphics, data, actor)
                + ACTOR_LEFT_MARGIN;
        }

        int additionalWidth = 0;
        if ((totalWidth < data.getBounds().width) && (actors.size() > 1)) {
            int diff = data.getBounds().width - totalWidth;
            additionalWidth = diff / (actors.size() - 1);
        }
        int totalActor = actors.size();
        for (final ISequenceMember actor : actors) {
            totalActor--;
            drawActor(actor, data, graphics);

            final float[] xy = new float[TWO];
            translate.transform(xy);

            final Point p = new Point((int) xy[0], (int) xy[1]);

            actorPositions.put(actor, p);
            int xTranslation;
            if (totalActor < 1) {
                xTranslation = getActorBoxWidth(graphics, data, actor);
            } else {
                xTranslation = getActorBoxWidth(graphics, data, actor)
                        + ACTOR_LEFT_MARGIN + additionalWidth;
            }

            data.setPreferedWidth(data.getPreferedWidth() + xTranslation);

            translate.translate(xTranslation, 0);

            graphics.setTransform(translate);

        }

        lastMaxActorBoxHeight = newMaxActorBoxHeight;
        translate.dispose();

        data.drawnActors.clear();

        data.drawnActors.addAll(actors);
    }

    boolean isVisible(final Rectangle clipping, final Rectangle rect) {
        return clipping.intersects(rect);
    }

    private void drawActor(final ISequenceMember actor,
            final SequenceRenderingData data, final IGraphics gc) {

        final Rectangle rect = new Rectangle(0, 0, getActorBoxWidth(gc, data,
                actor), lastMaxActorBoxHeight + ACTOR_Y_MARGIN);
        if (getActorBoxHeight(gc, data, actor) > newMaxActorBoxHeight) {
            newMaxActorBoxHeight = getActorBoxHeight(gc, data, actor);
        }

        if (data.context != null && actor.equals(data.context)) {
            gc.setBackground(SWTResourceManager.getColor(240, 240, 240));
            final Rectangle shadeRect = new Rectangle(0, 0, getActorBoxWidth(
                    gc, data, actor), gc.getClipping().height);
            gc.fillRectangle(shadeRect);
        } else {
            gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
        }
        final Color foreground = gc.getForeground();
        // Draw the actor
        final RGB rgb = data.colorMap.getColor(actor);
        final Color actorColor = new Color(gc.getDevice(), rgb);

        gc.setBackground(actorColor);

        SWTUtil.fillGlossRect(gc, rect, true);
        gc.drawRectangle(rect);
        Transform tf = new Transform(gc.getDevice());
        gc.getTransform(tf);
        Rectangle onScreen = SWTUtil.transform(rect, tf);
        this.entities.add(new ObjectEntry(onScreen, actor));
        tf.dispose();

        final FontMetrics fontMetrics = gc.getFontMetrics();

        // Calculate the label x coordinate
        final int textLength = getFormattedStringLenght(gc, actor.getName(),
                data);
        final int x = textLength / HALF;
        final int middle = (rect.x + rect.width) / HALF;

        gc.setForeground(SWTUtil.getTextColor(actorColor));

        List<String> stringList = getFormattedString(actor.getName(), data
                .getDrawCompactActors());
        if (stringList != null) {
            for (int i = 0; i < stringList.size(); i++) {
                gc.drawString(stringList.get(i), middle - x, getActorTextYpos(
                        data, getActorBoxHeight(gc, data, actor), rect,
                        fontMetrics, actor.getName())
                        + (i * fontMetrics.getHeight()), true);
            }
            stringList.clear();
        } else {
            gc.drawString(actor.getName(), middle - x, getActorTextYpos(data,
                    getActorBoxHeight(gc, data, actor), rect, fontMetrics,
                    actor.getName()), true);
        }

        gc.setForeground(foreground);
        // Draw life line
        final int[] lineDash = gc.getLineDash();

        gc.setLineDash(new int[] { ACTIVITY_LINE_DASH, ACTIVITY_LINE_DASH, });

        gc.drawLine(middle, rect.y + rect.height, middle, gc.getClipping().y
                + gc.getClipping().height);
        gc.setLineDash(lineDash);

        actorColor.dispose();
        gc.setLineWidth(1);
    }

    private int getActorTextYpos(final SequenceRenderingData data,
            final int boxheight, final Rectangle rect,
            final FontMetrics fontMetrics, final String name) {
        if (data.getDrawCompactActors()) {
            // calculate the height of the complete text
            int textHeight = fontMetrics.getHeight()
                    * (name.length() / SHORT_ACTOR_NAME_LEN);
            // calculate the margin between rectangle and text
            int diff = boxheight - textHeight;
            return (diff / 2);
        }
        return ((rect.height - fontMetrics.getHeight()) / HALF);
    }

    private int getFormattedStringLenght(final IGraphics gc, final String name,
            final SequenceRenderingData data) {
        if (data.getDrawCompactActors()) {
            return SWTUtil.textLength(gc, getCompactActorString(gc, name));
        }
        return SWTUtil.textLength(gc, name);
    }

    private static List<String> getFormattedString(final String name,
            final boolean drawCompactActors) {
        if (drawCompactActors) {
            return getWrapString(name);
        } else {
            return null;
        }
    }

    private static int getActorBoxWidth(final IGraphics gc,
            final SequenceRenderingData data, final ISequenceMember actor) {

        final String name = data.getDrawCompactActors() ? getCompactActorString(
                gc, actor.getName())
                : actor.getName();
        final int width = SWTUtil.textLength(gc, name) + ACTOR_LABEL_X_MARGIN;
        return Math.max(width, MINIMUM_ACTOR_WIDTH);

    }

    private static int getActorBoxHeight(final IGraphics gc,
            final SequenceRenderingData data, final ISequenceMember actor) {
        int height = 0;

        if (data.getDrawCompactActors()) {
            height = gc.getFontMetrics().getHeight()
                    * ((actor.getName().length() / SHORT_ACTOR_NAME_LEN) + 1);
        } else {
            height = gc.getFontMetrics().getHeight() + ACTOR_LABEL_Y_MARGIN;
        }
        return height;
    }

    /*
     * Return the longest string
     */
    private static String getCompactActorString(final IGraphics gc,
            final String name) {
        int width = 0;
        int selected = 0;
        List<String> strList = getWrapString(name);
        if (strList != null) {
            for (int i = 0; i < strList.size(); i++) {
                if (width <= SWTUtil.textLength(gc, strList.get(i))) {
                    width = SWTUtil.textLength(gc, strList.get(i));
                    selected = i;
                }
            }
        }
        return strList.get(selected);
    }

    /*
     * Build the list of substrings
     */
    private static List<String> getWrapString(final String name) {
        List<String> stringList = new ArrayList<String>();
        String formattedName = name;
        String after = formattedName;
        for (int i = SHORT_ACTOR_NAME_LEN; after.length() > SHORT_ACTOR_NAME_LEN; i += SHORT_ACTOR_NAME_LEN) {
            String before = formattedName
                    .substring(i - SHORT_ACTOR_NAME_LEN, i);
            after = formattedName.substring(i);
            stringList.add(before);
        }
        stringList.add(after);
        return stringList;
    }

    final class ActivityBoxRenderer {

        private static final int ACTIVITY_WIDTH = 10;

        private ActivityBoxRenderer() {
        // UTility - no visible constructor
        }

        /**
         * Draws the activity on the IGraphics context.
         * 
         * @param box
         *            the activity box to draw
         * @param map
         *            the color map to determine activity color
         * @param gc
         *            the graphics context
         * 
         * @return the actual drawn rectangle in screen coordinates
         */
        Rectangle drawActivity(final IGraphics gc, final IActivity box,
                final ArtifactColorMap map) {

            gc.setAntialias(SWT.OFF);
            final Color background = gc.getBackground();
            // FIXME Need to use a scaler to fit in long values to screen
            // coordinates

            final long startTime = box.getStartTime();
            int y0 = verticalRuler.toScreen(startTime);
            if (y0 <= actorActivityMargin) {
                y0 = actorActivityMargin;
            }
            final int y1 = verticalRuler.toScreen(startTime
                    + box.getDurationTime());

            if (y0 > y1) {
                return new Rectangle(0, 0, 0, 0);
            }
            int min = verticalRuler.min();
            if (y0 < min) {
                y0 = min;
            }

            final ISequenceMember parent = box.getOwner();
            final Point point = actorPositions.get(parent);
            final int boxXcoord = point.x
                    + SequenceDiagramRenderer
                            .getActorBoxWidth(gc, data, parent) / HALF;

            // Create a rect
            final Rectangle rect = new Rectangle(boxXcoord
                    + -ActivityBoxRenderer.ACTIVITY_WIDTH / HALF, y0,
                    ActivityBoxRenderer.ACTIVITY_WIDTH, (y1 - y0));

            final Rectangle clipping = gc.getClipping();

            if (isVisible(clipping, rect)) {
                final RGB rgb = map.getColor(parent);
                final Color c = new Color(gc.getDevice(), rgb);
                gc.setBackground(c);

                int lineWidth = gc.getLineWidth();

                if (Span.valueOf(box).contains(data.markerValue)) {
                    gc.setLineWidth(2);
                } else {
                    gc.setLineWidth(1);
                }
                gc.fillRectangle(rect);
                gc.drawRectangle(rect);
                gc.setLineWidth(lineWidth);

                String endNote = box.getEndNote();
                if (!endNote.equals(IActivity.UNKNOWN)) {
                    final String name = '<' + endNote + '>';
                    final int x = rect.x - SWTUtil.textLength(gc, name) / TWO
                            + ActivityBoxRenderer.ACTIVITY_WIDTH;
                    gc.setBackground(gc.getDevice().getSystemColor(
                            SWT.COLOR_WHITE));
                    gc.drawString(name, x, rect.y + rect.height + 1, false);
                }

                c.dispose();
            }
            // Transform the box-rect with the current transform
            // and return this as the drawn box
            final Transform current = new Transform(gc.getDevice());
            gc.getTransform(current);
            final Rectangle drawnRect = SWTUtil.transform(rect, current);
            current.dispose();
            gc.setBackground(background);
            return drawnRect;
        }
    }

    private final class MessageRenderer {
        private static final int SELF_MESSAGE_LOOP_SIZE = 20;

        private static final int HALF = 2;

        private static final int ARROW_LENGTH = 5;

        private static final int SIGNAL_MARGIN = 8;

        private MessageRenderer() {

        }

        void drawMessage(final IGraphics gc, final ISequenceMessage message,
                final List<ObjectEntry> entities, final IRuler verticalRuler) {
            final long sentTime = message.getDrawingTs();
            final int sendTime = verticalRuler.toScreen(sentTime);

            if (sendTime <= actorActivityMargin) {
                return;
            }
            final int xSender = actorPositions.get(message.getSender()).x;
            final int xRecipent = actorPositions.get(message.getRecipent()).x;
            final int recipentWidth = getActorBoxWidth(gc, data, message
                    .getRecipent());
            final int senderWidth = getActorBoxWidth(gc, data, message
                    .getSender());
            final boolean leftToRight = xSender < xRecipent;

            // TODO Refactor msgEnd/msgStart to more generic
            if (message.getSender().equals(message.getRecipent())) {

                final int msgEnd = xRecipent
                        + (recipentWidth + ActivityBoxRenderer.ACTIVITY_WIDTH)
                        / MessageRenderer.HALF;
                gc.drawOval(msgEnd + MessageRenderer.ARROW_LENGTH, sendTime
                        - MessageRenderer.SELF_MESSAGE_LOOP_SIZE
                        / MessageRenderer.HALF,
                        MessageRenderer.SELF_MESSAGE_LOOP_SIZE,
                        MessageRenderer.SELF_MESSAGE_LOOP_SIZE);

                drawSignal(gc, message, sendTime, msgEnd, msgEnd);
                final int[] arrow = new int[] { msgEnd + 1, sendTime,
                        msgEnd + MessageRenderer.ARROW_LENGTH,
                        sendTime - MessageRenderer.ARROW_LENGTH,
                        msgEnd + MessageRenderer.ARROW_LENGTH,
                        sendTime + MessageRenderer.ARROW_LENGTH, };
                gc.setBackground(gc.getForeground());
                gc.fillPolygon(arrow);
            } else if (leftToRight) {

                int msgStart = xSender + senderWidth / MessageRenderer.HALF;
                msgStart += ActivityBoxRenderer.ACTIVITY_WIDTH
                        / MessageRenderer.HALF;

                int msgEnd = xRecipent + recipentWidth / MessageRenderer.HALF;
                msgEnd -= ActivityBoxRenderer.ACTIVITY_WIDTH
                        / MessageRenderer.HALF;

                gc.drawLine(msgStart, sendTime, msgEnd, sendTime);

                drawSignal(gc, message, sendTime, msgStart, msgEnd);

                final int[] arrow = new int[] { msgEnd, sendTime,
                        msgEnd - MessageRenderer.ARROW_LENGTH,
                        sendTime - MessageRenderer.ARROW_LENGTH,
                        msgEnd - MessageRenderer.ARROW_LENGTH,
                        sendTime + MessageRenderer.ARROW_LENGTH, };
                gc.setBackground(gc.getForeground());
                gc.fillPolygon(arrow);

            } else {

                int msgStart = xSender + senderWidth / MessageRenderer.HALF;
                msgStart -= ActivityBoxRenderer.ACTIVITY_WIDTH
                        / MessageRenderer.HALF;

                int msgEnd = xRecipent + recipentWidth / MessageRenderer.HALF;
                msgEnd += ActivityBoxRenderer.ACTIVITY_WIDTH
                        / MessageRenderer.HALF;

                gc.drawLine(msgStart, sendTime, msgEnd, sendTime);
                drawSignal(gc, message, sendTime, msgStart, msgEnd);

                final int[] arrow = new int[] { msgEnd + 1, sendTime,
                        msgEnd + MessageRenderer.ARROW_LENGTH,
                        sendTime - MessageRenderer.ARROW_LENGTH,
                        msgEnd + MessageRenderer.ARROW_LENGTH,
                        sendTime + MessageRenderer.ARROW_LENGTH, };
                gc.setBackground(gc.getForeground());
                gc.fillPolygon(arrow);
            }
        }

        private void drawSignal(final IGraphics gc,
                final ISequenceMessage message, final int y, final int msgX0,
                final int msgX1) {
            final int height = gc.getFontMetrics().getHeight();
            if (y - height <= actorActivityMargin) {
                return;
            }
            final int msgGCLength = SWTUtil
                    .textLength(gc, message.getMessage());

            int messageX = msgX0 - msgGCLength - MessageRenderer.SIGNAL_MARGIN;
            if (msgX0 < msgX1) {
                messageX = msgX0 + MessageRenderer.SIGNAL_MARGIN;
            }

            gc.drawString(message.getMessage(), messageX, y - height, true);
            final Rectangle bb = new Rectangle(messageX, y - height,
                    msgGCLength, height);

            entities.add(new ObjectEntry(bb, message));
        }
    }

    private static final class ObjectEntry {
        private final Rectangle rect;

        private final Object element;

        public ObjectEntry(final Rectangle rect, final Object element) {
            super();
            this.rect = rect;
            this.element = element;
        }

        boolean containts(final int x, final int y) {
            return rect.contains(x, y);
        }

        public Object getElement() {
            return element;
        }

    }

    private final SequenceRenderingData data = new SequenceRenderingData();

    private Collection<ISequenceMember> filteredActors = new ArrayList<ISequenceMember>();

    SequenceRenderingData getData() {
        return data;
    }

    static class SequenceRenderingData {

        private final Set<ISequenceMember> drawnActors = new LinkedHashSet<ISequenceMember>();

        private Rectangle widgetSize;

        private int preferedWidth;

        private int preferedHeight;

        private ArtifactColorMap colorMap;

        private Set<ISequenceMember> members;

        private ISequenceMember context;

        private ArrayList<ITimed> elements;

        private long markerValue;

        private String filterText;

        static final double DEFAULT_ZOOM_LEVEL_LOG = 10;

        private static final double DEFAULT_ZOOM_LEVEL_LIN = 500;

        private List<ISequenceMessage> messages;

        private boolean log;

        private double logZoomLevel = DEFAULT_ZOOM_LEVEL_LOG;

        private double linZoomLevel = DEFAULT_ZOOM_LEVEL_LIN;

        private boolean drawCompactActors;

        private Rectangle bounds;

        public boolean getDrawCompactActors() {
            return drawCompactActors;
        }

        public void setDrawCompactActors(final boolean drawCompactActors) {
            this.drawCompactActors = drawCompactActors;
        }

        List<Long> getTimestamps() {
            final List<Long> timeStamps = new ArrayList<Long>();
            for (final ITimed element : elements) {
                if (element instanceof IActivity) {
                    IActivity act = (IActivity) element;
                    final long start = act.getStartTime();
                    final long end = start + act.getDurationTime();
                    if (!timeStamps.contains(start) && start != -1) {
                        timeStamps.add(start);
                    }
                    if (!timeStamps.contains(end) && end != -1) {
                        timeStamps.add(end);
                    }
                } else {
                    long timeReference = element.getTimeReference();
                    if (!timeStamps.contains(timeReference)
                            && timeReference != -1) {
                        timeStamps.add(timeReference);
                    }
                }
            }
            Collections.sort(timeStamps);
            return timeStamps;
        }

        ArrayList<ITimed> getElements() {
            return elements;
        }

        void setElements(final ArrayList<ITimed> elements) {
            this.elements = elements;
        }

        long getMarkerValue() {
            return markerValue;
        }

        void setMarkerValue(final long markerValue) {
            this.markerValue = markerValue;
        }

        Set<ISequenceMember> getMembers() {
            if (members == null) {
                members = new LinkedHashSet<ISequenceMember>();

            }
            return members;
        }

        Rectangle getWidgetSize() {
            return widgetSize;
        }

        void setWidgetSize(final Rectangle widgetSize) {
            this.widgetSize = widgetSize;
        }

        int getPreferedWidth() {
            return preferedWidth;
        }

        private void setPreferedWidth(final int preferedWidth) {
            this.preferedWidth = preferedWidth;
        }

        void setColorMap(final ArtifactColorMap colorMap) {
            this.colorMap = colorMap;
        }

        int getPreferedHeight() {
            return preferedHeight;
        }

        private void setPreferedHeight(final int preferedHeight) {
            this.preferedHeight = preferedHeight;
        }

        void setFilterText(final String filterText) {
            this.filterText = filterText;
        }

        String getFilterText() {
            return filterText == null ? "" : filterText;
        }

        Set<ISequenceMember> getDrawnActors() {
            return drawnActors;
        }

        void setMessages(final List<ISequenceMessage> messages) {
            this.messages = messages;
        }

        @SuppressWarnings("unchecked")
        List<ISequenceMessage> getMessages() {
            return messages == null ? Collections.EMPTY_LIST : messages;
        }

        public void setContext(final ISequenceMember tracked) {
            context = tracked;

        }

        public void setIsLogarithmic(final boolean logarithmic) {
            this.log = logarithmic;
        }

        public boolean isLogarithmic() {
            return log;
        }

        public double getZoomLevel() {
            if (isLogarithmic()) {
                return logZoomLevel;
            }
            return linZoomLevel;
        }

        public void setZoomLevel(final double zoomLevel) {
            if (isLogarithmic()) {
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

        public void setLog(final Boolean zoomIsLog) {
            this.log = true;
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public void setBounds(Rectangle bounds) {
            this.bounds = bounds;
        }
    }

    public void setFilteredActors(
            final Collection<ISequenceMember> filteredActors) {
        this.filteredActors = filteredActors;
    }
}
