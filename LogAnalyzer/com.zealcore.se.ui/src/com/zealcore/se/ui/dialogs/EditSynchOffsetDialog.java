package com.zealcore.se.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.util.SynchOffsetTable;

public class EditSynchOffsetDialog extends Dialog {

    ITimeCluster timeCluster;
    
    IStructuredSelection selection;

    private SynchOffsetTable table;
    
    /**
     * Creates a pop-up dialog for user to configure synch offsets
     */
    public EditSynchOffsetDialog(final Shell parentShell,
            final ITimeCluster baseCluster, final IStructuredSelection selection) {
        super(parentShell);
        if (baseCluster == null) {
            throw new IllegalArgumentException();
        }

        setShellStyle(SWT.DIALOG_TRIM);
        this.selection = selection;
        this.timeCluster = baseCluster;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout gridLayout = (GridLayout) composite.getLayout();
        gridLayout.marginTop = 5;
        gridLayout.numColumns = 2;

        Label synchOffsetLabel = new Label(composite, SWT.NONE);
        synchOffsetLabel.setLayoutData(new GridData(SWT.BEGINNING,
                SWT.BEGINNING, false, false, 2, 1));
        synchOffsetLabel
                .setText("Specify synchronization offsets relative to '"
                        + timeCluster.getParent().getFolder().getName() + "': ");

        table = new SynchOffsetTable(timeCluster, selection, false);
        table.createContents(composite);

        return composite;
    }

    @Override
    public void okPressed() {
        table.update();
        super.okPressed();
    }

    @Override
    public void cancelPressed() {
        super.cancelPressed();
    }

    @Override
    protected void configureShell(Shell newShell) {
        newShell.setText("Set Synchronization Offset");
        super.configureShell(newShell);
    }
}
