package com.odcgroup.process.diagram.custom.translation;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.process.diagram.custom.parts.ProcessItemShapeNodeEditPart;
import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;

/**
 * @author yan
 */
public class TranslationSection extends AbstractPropertySection {
	
	/** The WidgetEditPart containing the Process to be localized. */
	private GraphicalEditPart graphicalEditPart;
	
	/** parent composite of the viewer */
	private Composite parent;
	
	/** Translation viewer */
	private ITranslationViewer viewer;

	/**
	 * Notifies the section that the workbench selection has changed.
	 * 
	 * @param part
	 *            The active workbench part.
	 * @param selection
	 *            The active selection in the workbench part.
	 * 
	 *            Implementation note: the selected table widget is wrapped into
	 *            a ITable adapter
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		if (graphicalEditPart != input) {
			if (input instanceof ProcessItemShapeNodeEditPart ||
					input instanceof ProcessEditPart) {
				// the selection has changed
				graphicalEditPart = (GraphicalEditPart) input;
				ITranslationModel model = null;
				if (input instanceof ProcessItemShapeNodeEditPart) {
					model = ((ProcessItemShapeNodeEditPart)graphicalEditPart).getTranslationModel();
				} else if (input instanceof ProcessEditPart) {
					model = ((ProcessEditPart)graphicalEditPart).getTranslationModel();
				}
				if (model != null) {
					if (viewer == null) {
						viewer = TranslationUICore.getTranslationViewer((IProject)graphicalEditPart.getAdapter(IProject.class), parent);
					}
					viewer.setTranslationModel(model);
				}
			}
		}
	}

	/**
	 * Create the controls of the TableSortSection.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param aTabbedPropertySheetPage
	 *            The property sheet page
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		this.parent = parent; // Used by the viewer creation in setInput
		GridLayout parentLayout = new GridLayout(1, false);
		parent.setLayout(parentLayout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH)); 
		super.createControls(parent, aTabbedPropertySheetPage);
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		if (viewer != null) {
			viewer.dispose();
			viewer = null;
		}
		if (graphicalEditPart != null) {
			graphicalEditPart = null;
		}
	}
}