package com.odcgroup.workbench.editors.properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class AdvancedTabbedPropertySheetPage extends TabbedPropertySheetPage {
	
	private TabbedPropertyMessage messageComposite;
	private ISelectionAdapter selectionAdapter;
	
	private IStructuredSelection currentSelection;

	/**
	 * @param tabbedPropertySheetPageContributor
	 */
	public AdvancedTabbedPropertySheetPage(ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		TabbedPropertyComposite tComposite = (TabbedPropertyComposite) getControl();
		TabbedPropertyTitle title = tComposite.getTitle();
		if (title != null && title.getChildren().length > 0) {
			CLabel titleLabel = (CLabel) title.getChildren()[0];
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(15, 0);
			data.top = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(100, 0);
			titleLabel.setLayoutData(data);		
			
			messageComposite = new TabbedPropertyMessage(title, getWidgetFactory());
			messageComposite.setLayout(new FormLayout());
			data = new FormData();
			data.left = new FormAttachment(title.getChildren()[0], 0);
			data.right = new FormAttachment(100, 0);
			messageComposite.setLayoutData(data);
			
			messageComposite.setBackground(titleLabel.getBackground());
			
			//ResourcesPlugin.getWorkspace().addResourceChangeListener(messageComposite);
			
			title.layout();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		this.currentSelection = (IStructuredSelection) selection;
		refreshValidationMessages();
	}
	
	/**
	 * @param selection
	 */
	public void refreshValidationMessages() {
		if (selectionAdapter != null && currentSelection != null) {
			EObject model = selectionAdapter.adaptModel(currentSelection.getFirstElement());
			if (model == null) {
				return;
			}
			IMarker[] markers = ProblemMarkerManager.getProblemMarkers(model);
			messageComposite.updateMessage(markers);
		}
	}
	
	/**
	 * @return the selectionAdapter
	 */
	public ISelectionAdapter getSelectionAdapter() {
		return selectionAdapter;
	}

	/**
	 * @param selectionAdapter the selectionAdapter to set
	 */
	public void setSelectionAdapter(ISelectionAdapter selectionAdapter) {
		this.selectionAdapter = selectionAdapter;
	}	
	

}
