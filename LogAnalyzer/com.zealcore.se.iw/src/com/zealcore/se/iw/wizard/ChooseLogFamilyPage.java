package com.zealcore.se.iw.wizard;

import java.util.Collections;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

class ChooseLogFamilyPage extends WizardPage {

    protected ChooseLogFamilyPage(final String pageName) {
        super(pageName);
    }

    public void createControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        setControl(container);

        container.setLayout(new FillLayout(SWT.VERTICAL));

        final TreeViewer familyChoose = new TreeViewer(container);
        familyChoose.setLabelProvider(new LabelProvider());
        familyChoose.setContentProvider(new FamilyContentProvider());

        final Composite propertyChoose = new Composite(container, SWT.NULL);
        propertyChoose.setLayout(new RowLayout(SWT.VERTICAL));

        final Button hasHeader = new Button(propertyChoose, SWT.CHECK);
        hasHeader.setText("This logfile contains a header");

    }

    private static class FamilyContentProvider extends ArrayContentProvider
            implements ITreeContentProvider {

        public Object[] getChildren(final Object parentElement) {
            return Collections.EMPTY_LIST.toArray();
        }

        public Object getParent(final Object element) {
            return null;
        }

        public boolean hasChildren(final Object element) {
            return false;
        }

    }

}
