package com.odcgroup.process.diagram.custom.properties;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;

import com.odcgroup.process.diagram.part.ProcessDiagramEditor;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.workbench.editors.properties.page.AdvancedPropertiesBrowserPage;

/**
 * 
 * @version 1.0
 * @author <a href="mailto:pkkotaprolu@odyssey-group">Phani Kumar K</a>
 */
public class ProcessTabbedPropertySheetPage extends AdvancedPropertiesBrowserPage
		implements IEditingDomainProvider {

	protected ProcessDiagramEditor processEditor;

	private PropertiesViewActionHandler actionHandler;

	/**
	 * Creates a new WorkflowTabbedPropertySheetPage object.
	 * 
	 * @param workflowEditor
	 */
	public ProcessTabbedPropertySheetPage(ProcessDiagramEditor workflowEditor) {
		super(workflowEditor);
		this.processEditor = workflowEditor;
		setSelectionAdapter(new ProcessValidationProvider());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#setActionBars(org.eclipse.ui.IActionBars)
	 */
	public void setActionBars(IActionBars actionBars) {
		actionBars.clearGlobalActionHandlers();
		actionHandler = new PropertiesViewActionHandler(actionBars);
		actionBars.updateActionBars();
	}

	public void addControl(Control control) {
		actionHandler.addControl(control);
	}

	public void removeControl(Control control) {
		actionHandler.removeControl(control);
	}

	/**
	 * @return
	 */
	public ProcessDiagramEditor getWorkflowEditor() {
		return processEditor;
	}

	/**
	 * @return
	 */
	public AdapterFactory getAdapterFactory() {
		return ProcessDiagramEditorPlugin
		.getInstance().getItemProvidersAdapterFactory();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return getWorkflowEditor().getEditingDomain();
	}
}
