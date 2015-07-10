package com.zealcore.se.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IMemento;

import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.internal.TimeEvent;

public final class NullBrowser extends AbstractLogsetBrowser {

    private static final String NAME = "Null Browser";

    private Label label;

    private final String message;

    public NullBrowser(final String message) {
        this.message = message == null ? "NULL" : message;
    }

    public void createControl(final Composite parent) {
        this.label = new Label(parent, SWT.NULL);
        this.label.setText(this.message);

    }

    public void refresh() {}

    public void setFocus() {
        this.label.setFocus();
    }

    public void synch(final TimeEvent source) {}

    public void init(final IMemento memento) {}

    public void saveState(final IMemento memento) {}

    public ImageDescriptor getImageDescriptor() {
        return IconManager.getImageDescriptor(IconManager.BLOB_IMAGE);
    }

    public String getName() {
        return NullBrowser.NAME;
    }
}
