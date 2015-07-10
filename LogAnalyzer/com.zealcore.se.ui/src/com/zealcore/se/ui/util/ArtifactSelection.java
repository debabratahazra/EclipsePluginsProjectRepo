/*
 * 
 */
package com.zealcore.se.ui.util;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.views.properties.IPropertySource;

import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.editors.ILogViewInput;

/**
 * The ArtifactSelection is a selection which wraps a selected AbstractObject
 * together with a {@link ILogViewInput}. It can also adaptapt to a
 * ProperySource.
 */
public class ArtifactSelection implements IAdaptable, IActionFilter {

    private PropertySource properties;

    private final ILogViewInput source;

    private final IObject item;

    private boolean visibleGoto = true;

    private static final String GOTO = "visibleGoto";

    private static final String JUMP_TO = "visibleJumpTo";

    private boolean jumpTo = true;

    public boolean isJumpTo() {
        return jumpTo;
    }

    public void setJumpTo(final boolean jumpTo) {
        this.jumpTo = jumpTo;
    }

    public boolean isVisibleGoto() {
        return visibleGoto;
    }

    public void setVisibleGoto(final boolean visibleGoto) {
        this.visibleGoto = visibleGoto;
    }

    public ArtifactSelection(final ILogViewInput source, final IObject item) {
        super();
        this.source = source;
        this.item = item;
    }

    /**
     * Gets the actual selected item.
     * 
     * @return the item
     */
    public IObject getItem() {
        return this.item;
    }

    // This method was not directly used in this package
    // void setItem(final IArtifact item) {
    // this.item = item;
    // }

    /**
     * Gets the source, the {@link ILogViewInput} which was used to get this
     * selection.
     * 
     * @return the source
     */
    public ILogViewInput getSource() {
        return this.source;
    }

    // This method was not directly used in this package
    // void setSource(final ILogViewInput source) {
    // this.source = source;
    // }

    /**
     * Checks if is empty (there is no item selected). if true, getItem() will
     * return null
     * 
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return this.item == null;
    }

    @Override
    public String toString() {
        return " [source=" + this.source + ",artifact=" + this.item + "]";
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        if (adapter == IPropertySource.class) {
            return this.getPropertySource();
        }

        if (adapter == IObject.class) {
            return this.item;
        }

        return null;
    }

    /**
     * Gets the property source representation of the getItem() instance.
     * 
     * @return the property source
     */
    private Object getPropertySource() {
        if (this.properties == null) {
            this.properties = new PropertySource(this.item);
        }
        return this.properties;
    }

    public boolean testAttribute(final Object target, final String name,
            final String value) {
        if (name.equals(GOTO)) {
            return ((ArtifactSelection) target).isVisibleGoto();
        }

        if (name.equals(JUMP_TO)) {
            return ((ArtifactSelection) target).isJumpTo();
        }

        return true;
    }

}
