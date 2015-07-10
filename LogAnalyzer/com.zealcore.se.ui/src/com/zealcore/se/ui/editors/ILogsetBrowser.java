/*
 * 
 */
package com.zealcore.se.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Composite;

import com.zealcore.se.core.IPersistable;
import com.zealcore.se.ui.ISynchable;

public interface ILogsetBrowser extends ISynchable, ISelectionChangedListener,
        ISelectionProvider, IPersistable {

    /**
     * Returns the cluster input, or null if none is set yet
     * 
     * @return the cluster input or null if none is set
     */
    ILogViewInput getInput();

    /**
     * Refreshes the view.
     */
    void refresh();

    void setFocus();

    void createControl(final Composite parent);

    void setEditorPart(final LogsetEditor logsetEditor);

    void dispose();

    String getName();

    ImageDescriptor getImageDescriptor();

    void gotoNextLikeSelected();

    void gotoPreviousLikeSelected();
}
