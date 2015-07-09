package com.odcgroup.t24.enquiry.properties.section;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.t24.enquiry.editor.part.AbstractEnquiryEditPart;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class DescriptionSection extends AbstractPropertySection {



	private Composite parent;
	

	/** Translation viewer */
	private ITranslationViewer viewer;

	/** the project that owns the edited model */
	private IProject project = null;

	/** The MenuItems translations, can be null if not supported */
	private ITranslation translation;
	
	private AbstractEnquiryEditPart editPart = null;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
	 * createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		TabbedPropertySheetWidgetFactory widgetFactory = getWidgetFactory();
		Composite container = widgetFactory.createFlatFormComposite(parent);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		this.parent = container;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#dispose()
	 */
	public void dispose() {
		if (viewer != null) {
			viewer.dispose();
			viewer = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
	 * setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (selection instanceof IStructuredSelection) {
			Object input = ((IStructuredSelection) selection).getFirstElement();
			if (input instanceof AbstractEnquiryEditPart) {
				if (editPart != input) {
					editPart = (AbstractEnquiryEditPart) input;
					ITranslationModel transationalModel = null;
					transationalModel = getTranslationModel((EObject) editPart.getModel());
					if (transationalModel != null) {
						if (viewer == null) {
							viewer = TranslationUICore.getTranslationViewer(project, this.parent);
						}
						viewer.setTranslationModel(transationalModel);
						transationalModel.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent arg0) {
								editPart.refresh();
								
							}
						});
					}
				}
			}
		}
	}

	/**
	 * @param item
	 * @return
	 */
	private ITranslationModel getTranslationModel(EObject item) {
		ITranslationModel model = null;
		project = OfsResourceHelper.getProject((item).eResource());
		if(project != null){
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			translation = manager.getTranslation(item);
			if (translation != null) {
				model = new TranslationModel(manager.getPreferences(), translation);
			}
		}
		return model;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

}
