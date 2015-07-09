package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
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
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * Manages the display of buttons for the MDFClass editing view
 *
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 */
public class MdfDatasetButtonViewer implements MdfModelElementButtonViewer,
    SelectionListener {
    private Button deleteButton;
    private Button newPropertyButton;
    private final MdfDataset parentDataset;
    private List selectedMdfModelElements = new ArrayList();

    /**
     * Creates a new MdfClassButtonViewer object.
     */
    public MdfDatasetButtonViewer(MdfDataset dataset) {
        this.parentDataset = dataset;
    }

    public void renderModelElementButtons(
        WidgetFactory factory, Composite parent) {
        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        container.setLayout(new GridLayout(1, true));

        // Add property button
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        newPropertyButton =
            factory.createButton(container, "New &Property...", SWT.NULL);
        newPropertyButton.setLayoutData(gridData);

        // Remove property button
        deleteButton = factory.createButton(container, "&Delete", SWT.NULL);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        deleteButton.setLayoutData(gridData);
        deleteButton.setEnabled(false);

        // Listen to all widgets
        newPropertyButton.addSelectionListener(this);
        deleteButton.addSelectionListener(this);
    }

    /**
     * Listen to MdfModelElementTable selection changes.
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

    public void widgetDefaultSelected(SelectionEvent e) {
        this.widgetSelected(e);
    }

    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == newPropertyButton) {
            handleAddProperty();
        } else if (e.getSource() == deleteButton) {
            handleRemoveAttribute(e);
        }
    }

    // TODO SAM: check this: probably need another method for derived props
    private void handleAddProperty() {
        EditionSupportFactory.INSTANCE().createDatasetMappedProperty(parentDataset);
    }
    
    /*
     * DS-1349
     */
    private void handleRemoveAttribute(SelectionEvent e) {
        if (selectedMdfModelElements.size() > 0) {
            EditionSupport support = EditionSupportFactory.INSTANCE(deleteButton.getShell());
            Iterator it = selectedMdfModelElements.iterator();
            
            while (it.hasNext()) {
                support.delete((MdfModelElement) it.next());
            }
        }
    }
}
