package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupport;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * Manages the display of buttons for the MDFEnumeration editing view
 *
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 */
public class MdfEnumerationButtonViewer implements MdfModelElementButtonViewer,
    SelectionListener, ISelectionChangedListener {
    private Button deleteButton;
    private Button newEnumValueButton;
    private final MdfEnumeration parentEnumeration;
    private List selectedMdfModelElements = new ArrayList();

    public MdfEnumerationButtonViewer(MdfEnumeration parentEnumeration) {
        this.parentEnumeration = parentEnumeration;
    }

    /**
     * @see com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfModelElementButtonViewer#renderModelElementButtons(com.odcgroup.mdf.editor.ui.WidgetFactory, org.eclipse.swt.widgets.Composite)
     */
    public void renderModelElementButtons(
        WidgetFactory factory, Composite parent) {
        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        container.setLayout(new GridLayout(1, true));

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        newEnumValueButton =
            factory.createButton(container, "&New Value...", SWT.NULL);
        newEnumValueButton.setLayoutData(gridData);
        deleteButton = factory.createButton(container, "&Delete", SWT.NULL);
        deleteButton.setEnabled(false);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        deleteButton.setLayoutData(gridData);

        newEnumValueButton.addSelectionListener(this);
        deleteButton.addSelectionListener(this);
    }

    /**
     * Listen to MdfModelElementTable selection changes.
     *
     * <p>
     * We save the current MdfModelElement elements and eventually activate
     * Remove Attribute button
     * </p>
     *
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();

        this.selectedMdfModelElements.clear();

        if ((selection != null) && !selection.isEmpty()) {
            if (selection instanceof IStructuredSelection) {
                for (
                    Iterator it = ((IStructuredSelection) selection).iterator();
                        it.hasNext();) {
                    Object sel = it.next();

                    if (sel instanceof MdfModelElement) {
                        this.selectedMdfModelElements.add(sel);
                    }
                }
            }
        }

        deleteButton.setEnabled(this.selectedMdfModelElements.size() > 0);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        this.widgetSelected(e);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == this.newEnumValueButton) {
            handleAddEnumeratedValue();
        } else if (e.getSource() == this.deleteButton) {
            handleRemoveEnumValues(e);
        }
    }

    private void handleAddEnumeratedValue() {
    	EditionSupportFactory.INSTANCE().createEnumValue(parentEnumeration);
    }

    /*
     * DS-1349
     */
    private void handleRemoveEnumValues(SelectionEvent e) {
        if (selectedMdfModelElements.size() > 0) {
            EditionSupport support = EditionSupportFactory.INSTANCE(deleteButton.getShell());
            Iterator it = selectedMdfModelElements.iterator();
            
            while (it.hasNext()) {
                support.delete((MdfModelElement) it.next());
            }
        }
    }
}
