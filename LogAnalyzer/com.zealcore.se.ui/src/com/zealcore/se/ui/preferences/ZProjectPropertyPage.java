package com.zealcore.se.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class ZProjectPropertyPage extends PropertyPage {

    public ZProjectPropertyPage() {
        setDescription("Enter project configuration");

    }

    @Override
    protected Control createContents(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FillLayout());
        return container;
    }

    @Override
    public boolean performOk() {
        return super.performOk();
    }

    @Override
    public void applyData(final Object data) {}
}
