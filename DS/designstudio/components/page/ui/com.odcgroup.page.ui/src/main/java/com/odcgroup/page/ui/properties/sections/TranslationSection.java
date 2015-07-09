package com.odcgroup.page.ui.properties.sections;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;

/**
 * The Translation Section allows the user to manage a set of translations for
 * the current item selected in the current active editor.
 * 
 * @author atr
 */
public class TranslationSection extends AbstractPagePropertySection {
	
	/** */
	private Composite parent;

	/** The WidgetEditPart containing the Widget to be localized. */
	private WidgetEditPart widgetEditPart;
	
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
		if (input instanceof WidgetEditPart) {
			if (widgetEditPart != input) {
				// the selection has changed
				widgetEditPart = (WidgetEditPart) input;
				ITranslationModel model = widgetEditPart.getTranslationModel();
				if (model != null) {
					if (viewer == null) {
						IProject project = (IProject)widgetEditPart.getAdapter(IProject.class);
						viewer = TranslationUICore.getTranslationViewer(project, this.parent);
					}
					viewer.setTranslationModel(model);
				}
				filterTooltip(widgetEditPart);
			}
		}
	}
	
	/**filter the tooltip if the widegt parent is xtooltip.
	 * @param part
	 */
	private void filterTooltip(WidgetEditPart part) {
	    Widget root= part.getWidget().getRootWidget();
	    Property pro=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
	    if(pro!=null){
		if(pro.getValue().equals("xtooltip")){  
		    viewer.hideTranslationKind(ITranslationKind.TEXT);
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
		super.createControls(parent, aTabbedPropertySheetPage);
		TabbedPropertySheetWidgetFactory widgetFactory = getWidgetFactory();		
		Composite container = widgetFactory.createFlatFormComposite(parent);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		this.parent = container;
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		if (viewer != null) {
			viewer.dispose();
			viewer = null;
		}
		if (widgetEditPart != null) {
			widgetEditPart = null;
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

}
