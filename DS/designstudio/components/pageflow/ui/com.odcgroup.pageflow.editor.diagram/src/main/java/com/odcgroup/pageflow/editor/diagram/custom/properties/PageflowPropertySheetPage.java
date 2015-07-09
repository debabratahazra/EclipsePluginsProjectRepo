package com.odcgroup.pageflow.editor.diagram.custom.properties;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditor;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.editors.properties.page.AdvancedPropertiesBrowserPage;

public class PageflowPropertySheetPage  extends AdvancedPropertiesBrowserPage
		implements IEditingDomainProvider {

	protected PageflowDiagramEditor pageflowEditor;
	private PropertiesViewActionHandler actionHandler;

	/**
	 * Creates a new WorkflowTabbedPropertySheetPage object.
	 * 
	 * @param workflowEditor
	 */
	public PageflowPropertySheetPage(PageflowDiagramEditor pageflowEditor) {
		super(pageflowEditor);
		this.pageflowEditor = pageflowEditor;
		setSelectionAdapter(new PageflowValidationProvider());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#setActionBars(org.eclipse.ui.IActionBars)
	 */
	public void setActionBars(IActionBars actionBars) {
		//DS-3992-REMOVED THE CUSTOM ACTIONS.REF FILE VERSION:32666
		super.setActionBars(actionBars);
		
	}

	/**
	 * @param control
	 */
	public void addControl(Control control) {
		actionHandler.addControl(control);
	}

	/**
	 * @param control
	 */
	public void removeControl(Control control) {
		actionHandler.removeControl(control);
	}

	/**
	 * @return
	 */
	public PageflowDiagramEditor getPageflowEditor() {
		return pageflowEditor;
	}

	/**
	 * @return
	 */
	public AdapterFactory getAdapterFactory() {
		return PageflowDiagramEditorPlugin
		.getInstance().getItemProvidersAdapterFactory();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return getPageflowEditor().getEditingDomain();
	}
}
