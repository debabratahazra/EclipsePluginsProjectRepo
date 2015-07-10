/*
 * 
 */
package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IMemento;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.internal.ArtifactID;

/**
 * The Class ArtifactColorMap is a place holder for Artifact Colors.
 */
public final class ArtifactColorMap {
    static final String COLOR_BLUE_NODE = "blue";

    static final String COLOR_GREEN_NODE = "green";

    static final String COLOR_RED_NODE = "red";

    static final String MAPPED_COLOR_NODE = "mapped-color";

    private static final int D_RED = 243;

    private static final int D_GREEN = 238;

    private static final int D_BLUE = 143;

    private final RGB defaultColor = new RGB(ArtifactColorMap.D_RED,
            ArtifactColorMap.D_GREEN, ArtifactColorMap.D_BLUE);

    private final List<IChangeListener> listeners = new ArrayList<IChangeListener>();

    private final Map<IArtifactID, EColor> map = new HashMap<IArtifactID, EColor>();

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when the state of artifactcolormap is changed, by sending it one of the
     * messages defined in the <code>IStateChangeListener</code> interface.
     * 
     * @param listener
     *                the listener which should be notified
     */
    public void addChangeListener(final IChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Disposes all colors in the map.
     */
    public void dispose() {}

    /**
     * Gets the color for the given IArtifactID instance.
     * 
     * @param resource
     *                the resource (artifact)
     * 
     * @return the color
     */
    public RGB getColor(final IArtifactID resource) {
        final EColor color = map.get(resource);
        if (color != null) {
            return color.toColor();
        }
        return getDefaultColor();

    }

    /**
     * Gets the default color.
     * 
     * @return the default color
     */
    public RGB getDefaultColor() {
        return defaultColor;
    }

    /**
     * Initialize the color map from a previously saved memento. This
     * implementation does not generate any state change events
     * 
     * @param mem
     *                the memento
     */
    public void init(final IMemento mem) {

        for (final IMemento colorMap : mem
                .getChildren(ArtifactColorMap.MAPPED_COLOR_NODE)) {
            final int r = colorMap.getInteger(ArtifactColorMap.COLOR_RED_NODE);
            final int g = colorMap
                    .getInteger(ArtifactColorMap.COLOR_GREEN_NODE);
            final int b = colorMap.getInteger(ArtifactColorMap.COLOR_BLUE_NODE);

            final IArtifactID id = ArtifactID.valueOf(colorMap);

            final EColor color = EColor.valueOf(r, g, b);

            map.put(id, color);

        }

    }

    /**
     * Notifies all listeners with state change events.
     */
    private void notifyListeners() {
        for (final IChangeListener listener : listeners) {
            listener.update(true);
        }
    }

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when the state of artifactcolormap is changed, by sending it one
     * of the messages defined in the <code>IStateChangeListener</code>
     * interface.
     * 
     * @param listener
     *                the listener which should no longer be notified
     */
    public void removeChangeListener(final IChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Resets all the colors.
     */
    public void reset() {
        map.clear();
        notifyListeners();
    }

    /**
     * Saves the state of this {@link ArtifactColorMap} to a memento.
     * 
     * @param colorMem
     *                the color mem
     */
    public void saveState(final IMemento colorMem) {
        for (final Entry<IArtifactID, EColor> entry : map.entrySet()) {
            final IMemento entryMem = colorMem
                    .createChild(ArtifactColorMap.MAPPED_COLOR_NODE);
            ArtifactID.valueOf(entry.getKey()).save(entryMem);

            final int r = entry.getValue().r;
            final int g = entry.getValue().g;
            final int b = entry.getValue().b;

            entryMem.putInteger(ArtifactColorMap.COLOR_RED_NODE, r);
            entryMem.putInteger(ArtifactColorMap.COLOR_GREEN_NODE, g);
            entryMem.putInteger(ArtifactColorMap.COLOR_BLUE_NODE, b);

        }
    }

    /**
     * Sets the color for a {@link IArtifactID}.
     * 
     * @param color
     *                the color
     * @param resource
     *                the resource
     * 
     * @return the color
     */
    public RGB setColor(final IArtifactID resource, final RGB color) {
        final EColor put = map.put(resource, EColor.valueOf(color));
        notifyListeners();
        if (put == null) {
            return null;
        }
        return put.toColor();
    }

    /**
     * Resets the color for a {@link IArtifactID}.
     * 
     * @param resource
     *                the resource
     * 
     */
    public void resetColor(final IArtifactID resource) {
        map.remove(resource);
        notifyListeners();
    }

    /**
     * Resets all colors for a {@link IArtifactID}.
     * 
     * @param resource
     *                the resource
     * 
     */
    public void resetColors() {
        map.clear();
    }

    /**
     * Proxy for Colors
     */
    private static class EColor {

        private RGB proxy;

        private int r;

        private int g;

        private int b;

        /**
         * Returns a new EColor representing the specified r, g and b values.
         * 
         * @param r
         *                the red
         * @param g
         *                the green
         * @param b
         *                the blue
         * 
         * @return the EColor
         */
        private static EColor valueOf(final int r, final int g, final int b) {
            final EColor instance = new EColor();
            instance.r = r;
            instance.g = g;
            instance.b = b;
            return instance;
        }

        /**
         * Returns a new EColor representing the specified Color value.
         * 
         * @param color
         *                the color
         * 
         * @return the E color
         */
        private static EColor valueOf(final RGB rgb) {
            final EColor instance = new EColor();
            instance.proxy = null;
            instance.r = rgb.red;
            instance.g = rgb.green;
            instance.b = rgb.blue;
            return instance;
        }

        /**
         * A EColor represented as a Color value.
         * 
         * @return the color
         */
        RGB toColor() {
            if (proxy != null) {
                return proxy;
            }
            proxy = new RGB(r, g, b);
            return proxy;
        }
    }
}
