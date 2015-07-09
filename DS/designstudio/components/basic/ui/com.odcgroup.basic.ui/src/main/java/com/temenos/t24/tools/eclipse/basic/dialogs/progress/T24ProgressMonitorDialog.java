package com.temenos.t24.tools.eclipse.basic.dialogs.progress;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class T24ProgressMonitorDialog extends ProgressMonitorDialog {

    private boolean cancellable;

    public T24ProgressMonitorDialog(Shell parent, boolean cancellable) {
        super(parent);
        this.cancellable = cancellable;
    }

    public Composite createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        setCancelable(cancellable);
        return container;
    }
}
