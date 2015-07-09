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
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * Manages the display of buttons for the MDFClass editing view
 *
 * @author <a href="mailto:daguirre@odyssey-group">David Aguirre</a>
 */
public class MdfDomainButtonViewer implements MdfModelElementButtonViewer,
    SelectionListener {
    private Button deleteButton;
    private Button newClassButton;
    private Button newDatasetButton;
    private Button newEnumButton;
    private final MdfDomain parentDomain;
    private List selectedMdfModelElements = new ArrayList();

    /**
     * Creates a new MdfDomainButtonViewer object.
     *
     * @param domain
     */
    public MdfDomainButtonViewer(MdfDomain domain) {
        this.parentDomain = domain;
    }

    public void renderModelElementButtons(
        WidgetFactory factory, Composite parent) {
        Composite container = factory.createComposite(parent);
        container.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        container.setLayout(new GridLayout(1, true));

        // Add class button
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        newClassButton =
            factory.createButton(container, "New &Class...", SWT.NULL);
        newClassButton.setLayoutData(gridData);

        newEnumButton =
            factory.createButton(container, "New &Enum...", SWT.NULL);
        newEnumButton.setLayoutData(gridData);

        newDatasetButton =
            factory.createButton(container, "New &Dataset...", SWT.NULL);
        newDatasetButton.setLayoutData(gridData);

        // Remove classes button
        deleteButton = factory.createButton(container, "&Delete", SWT.NULL);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        deleteButton.setLayoutData(gridData);
        deleteButton.setEnabled(false);

        // Listen to all widgets
        newClassButton.addSelectionListener(this);
        newEnumButton.addSelectionListener(this);
        newDatasetButton.addSelectionListener(this);
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

                    if (sel instanceof MdfEntity) {
                        this.selectedMdfModelElements.add(sel);

                        // TODO: Add also all the hierarchy down
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
        if (e.getSource() == this.newClassButton) {
            handleAddClass();
        } else if (e.getSource() == this.newEnumButton) {
            handleAddEnum();
        } else if (e.getSource() == this.newDatasetButton) {
            handleAddDataset();
        } else if (e.getSource() == this.deleteButton) {
            handleRemove(e);
        }
    }

    private void handleAddClass() {
        EditionSupportFactory.INSTANCE().createClass(parentDomain);
    }

    private void handleAddDataset() {
    	EditionSupportFactory.INSTANCE().createDataset(parentDomain);
    }

    private void handleAddEnum() {
    	EditionSupportFactory.INSTANCE().createEnumeration(parentDomain);
    }

    /*
     * DS-1349
     */
    private void handleRemove(SelectionEvent e) {
        if (selectedMdfModelElements.size() > 0) {
            EditionSupport support = EditionSupportFactory.INSTANCE(deleteButton.getShell());
            Iterator it = selectedMdfModelElements.iterator();
            
            while (it.hasNext()) {
                support.delete((MdfModelElement) it.next());
            }
        }
    }
}
