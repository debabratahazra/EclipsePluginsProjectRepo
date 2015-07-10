/*
 * 
 */
package com.zealcore.se.ui.core.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.ui.core.IEventColorProvider;
import com.zealcore.se.ui.graphics.ColorAdapter;
import com.zealcore.se.ui.graphics.IColor;

public final class EventColorProvider implements IEventColorProvider {

    static final String EVENT_COLOR_MAPPING = "event-color-mapping";

    static final String COLOR_BLUE_NODE = "blue";

    static final String COLOR_GREEN_NODE = "green";

    static final String COLOR_RED_NODE = "red";

    static final String MAPPED_COLOR_NODE = "mapped-color";

    static final String EVENT_TYPE_ID = "class";

    private final Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

    private final Map<String, IColor> colorProvider = new HashMap<String, IColor>();

    private final IPath mementoPath;

    private final IMementoService mementoService;

    private static int colorIndex = 14;

    /*
     * Colormap from
     * http://www.oracle.com/technology/tech/blaf/specs/colorPalette.html
     */

    private enum eventType {
        Send, Receive, Create, Kill, Reset, User, Timeout, Alloc, Free, Bind, Swap, TaskCompletion, TaskRelease, Error, Unknown

    };

    static final int[][] PREDEF_COLORS = { { 153, 204, 255 },
            { 204, 204, 204 }, { 153, 204, 51 }, { 200, 51, 0 },
            { 51, 102, 204 }, { 204, 204, 153 }, { 102, 102, 102 },
            { 153, 153, 204 }, { 102, 153, 153 }, { 204, 204, 102 },
            { 204, 102, 0 }, { 153, 153, 255 }, { 0, 102, 204 },
            { 153, 153, 153 }, { 255, 204, 0 }, { 0, 153, 153 },
            { 153, 204, 51 }, { 255, 153, 0 }, { 153, 153, 102 },
            { 102, 204, 204 }, { 51, 153, 102 }, { 204, 204, 51 },
            { 51, 102, 204 }, { 153, 204, 255 }, { 153, 153, 51 },
            { 102, 102, 153 }, { 204, 153, 51 }, { 0, 102, 102 },
            { 51, 153, 255 }, { 204, 204, 153 }, { 102, 102, 102 },
            { 255, 204, 102 }, { 102, 153, 204 }, { 102, 51, 102 },
            { 153, 153, 204 }, { 204, 204, 204 }, { 102, 153, 153 },
            { 204, 204, 102 }, { 204, 102, 0 }, { 153, 153, 255 },
            { 0, 102, 204 }, { 153, 153, 153 }, { 255, 204, 0 },
            { 0, 153, 153 }, { 255, 153, 0 }, { 153, 153, 102 },
            { 102, 204, 204 }, { 51, 153, 102 }, { 204, 204, 51 },
            { 51, 153, 255 }, { 102, 153, 204 }, { 153, 153, 51 },
            { 204, 153, 51 }, { 102, 102, 153 }, { 0, 102, 102 },
            { 102, 51, 102 }, { 255, 204, 102 }, };

    private static IColor getPredefColor(final eventType e) {

        int[] rgb = { 255, 255, 255, };

        // Defining Default color values for known events.

        if (e == eventType.Send) {
            rgb = PREDEF_COLORS[0];
        } else if (e == eventType.Receive) {
            rgb = PREDEF_COLORS[1];
        } else if (e == eventType.Create) {
            rgb = PREDEF_COLORS[2];
        } else if (e == eventType.Kill) {
            rgb = PREDEF_COLORS[3];
        } else if (e == eventType.Reset) {
            rgb = PREDEF_COLORS[4];
        } else if (e == eventType.User) {
            rgb = PREDEF_COLORS[5];
        } else if (e == eventType.Timeout) {
            rgb = PREDEF_COLORS[6];
        } else if (e == eventType.Alloc) {
            rgb = PREDEF_COLORS[7];
        } else if (e == eventType.Free) {
            rgb = PREDEF_COLORS[8];
        } else if (e == eventType.Bind) {
            rgb = PREDEF_COLORS[9];
        } else if (e == eventType.Swap) {
            rgb = PREDEF_COLORS[10];
        } else if (e == eventType.TaskCompletion) {
            rgb = PREDEF_COLORS[11];
        } else if (e == eventType.TaskRelease) {
            rgb = PREDEF_COLORS[12];
        } else if (e == eventType.Error) {
            rgb = PREDEF_COLORS[13];
        } else if (e == eventType.Unknown) {
            if (colorIndex < PREDEF_COLORS.length) {
                rgb = PREDEF_COLORS[colorIndex++];
            }
        }
        return ColorAdapter.valueOf(rgb[0], rgb[1], rgb[2]);
    }

    public EventColorProvider(final IMementoService mementoService,
            final IPath mementoPath) {
        this.mementoService = mementoService;
        this.mementoPath = mementoPath;

        try {
            if (mementoPath.toFile().exists()) {
                restoreInstance(mementoService.createReadRoot(mementoPath));
            }
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final WorkbenchException e) {
            throw new RuntimeException(e);
        }
    }

    public void addChangeListener(final IChangeListener listener) {
        this.changeListeners.add(listener);
        listener.update(false);
    }

    public IColor getColor(final IType type) {
        IColor color = this.colorProvider.get(type.getId());
        if (color == null) {
            if (type.getId().contains("Send")) {
                color = getPredefColor(eventType.Send);
            } else if (type.getId().contains("Receive")) {
                color = getPredefColor(eventType.Receive);
            } else if (type.getId().contains("Create")) {
                color = getPredefColor(eventType.Create);
            } else if (type.getId().contains("Kill")) {
                color = getPredefColor(eventType.Kill);
            } else if (type.getId().contains("Reset")) {
                color = getPredefColor(eventType.Reset);
            } else if (type.getId().contains("User")) {
                color = getPredefColor(eventType.User);
            } else if (type.getId().contains("Timeout")) {
                color = getPredefColor(eventType.Timeout);
            } else if (type.getId().contains("Alloc")) {
                color = getPredefColor(eventType.Alloc);
            } else if (type.getId().contains("Free")) {
                color = getPredefColor(eventType.Free);
            } else if (type.getId().contains("Bind")) {
                color = getPredefColor(eventType.Bind);
            } else if (type.getId().contains("Swap")) {
                color = getPredefColor(eventType.Swap);
            } else if (type.getId().contains("TaskCompletion")) {
                color = getPredefColor(eventType.TaskCompletion);
            } else if (type.getId().contains("TaskRelease")) {
                color = getPredefColor(eventType.TaskRelease);
            } else if (type.getId().contains("Error")) {
                color = getPredefColor(eventType.Error);
            } else {
                color = getPredefColor(eventType.Unknown);
            }

            this.colorProvider.put(type.getId(), color);
            try {
                saveInstance(this.mementoService.createWriteRoot(
                        EventColorProvider.EVENT_COLOR_MAPPING,
                        this.mementoPath));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return color;
    }

    public void removeChangeListener(final IChangeListener listener) {
        this.changeListeners.remove(listener);
    }

    public IColor setColor(final IType type, final IColor color) {
        if (color == null) {
            throw new IllegalArgumentException("Color may not be null [color="
                    + color + ",type=" + type + "]");
        }

        final IColor old = this.colorProvider.put(type.getId(), color);
        try {
            saveInstance(this.mementoService.createWriteRoot(
                    EventColorProvider.EVENT_COLOR_MAPPING, this.mementoPath));
        } catch (final IOException e) {
            // Ensure failure consistency
            if (old != null) {
                this.colorProvider.put(type.getId(), old);
            } else {
                this.colorProvider.remove(type);
            }
            throw new RuntimeException(e);
        }
        notifyChangeListeners();
        return old;
    }

    private void notifyChangeListeners() {
        for (final IChangeListener listener : this.changeListeners) {
            listener.update(true);
        }
    }

    /**
     * Saves the current instance data to a memento2
     * 
     * @param memento
     *            the memento
     * @throws IOException
     */
    void saveInstance(final IMemento2 memento) throws IOException {
        for (final Entry<String, IColor> entry : this.colorProvider.entrySet()) {
            final IMemento instance = memento
                    .createChild(EventColorProvider.MAPPED_COLOR_NODE);
            final IColor color = entry.getValue();
            instance.putInteger(EventColorProvider.COLOR_RED_NODE, color.r());
            instance.putInteger(EventColorProvider.COLOR_GREEN_NODE, color.g());
            instance.putInteger(EventColorProvider.COLOR_BLUE_NODE, color.b());
            instance
                    .putString(EventColorProvider.EVENT_TYPE_ID, entry.getKey());

        }
        memento.save();
    }

    void restoreInstance(final IMemento memento) {
        for (final IMemento iMemento : memento
                .getChildren(EventColorProvider.MAPPED_COLOR_NODE)) {
            final String eventClassName = iMemento
                    .getString(EventColorProvider.EVENT_TYPE_ID);
            final Integer red = iMemento
                    .getInteger(EventColorProvider.COLOR_RED_NODE);
            final Integer green = iMemento
                    .getInteger(EventColorProvider.COLOR_GREEN_NODE);
            final Integer blue = iMemento
                    .getInteger(EventColorProvider.COLOR_BLUE_NODE);
            final IColor color = ColorAdapter.valueOf(red, green, blue);
            this.colorProvider.put(eventClassName, color);
        }
    }

    public void clearColor(final IType type) {
        this.colorProvider.remove(type.getId());
        try {
            saveInstance(this.mementoService.createWriteRoot(
                    EventColorProvider.EVENT_COLOR_MAPPING, this.mementoPath));
            notifyChangeListeners();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearColors() {
        this.colorProvider.clear();
        try {
            saveInstance(this.mementoService.createWriteRoot(
                    EventColorProvider.EVENT_COLOR_MAPPING, this.mementoPath));
            notifyChangeListeners();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
