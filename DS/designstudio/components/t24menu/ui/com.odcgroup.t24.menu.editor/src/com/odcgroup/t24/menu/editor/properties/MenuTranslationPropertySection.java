package com.odcgroup.t24.menu.editor.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author scn
 *
 */
public class MenuTranslationPropertySection extends AbstractPropertySection {

	private Composite parent;
	
	Logger logger = LoggerFactory.getLogger(MenuTranslationPropertySection.class);

	/** Translation viewer */
	private ITranslationViewer viewer;

	/** the project that owns the edited model */
	private IProject project = null;

	/** The MenuItems translations, can be null if not supported */
	private ITranslation translation;

	/** The MenuItem to be localized. */
	private MenuItem menuItem;

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
		if (menuItem != null) {
			menuItem = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
	 * setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		if (input instanceof MenuItem) {
			if (menuItem != input) {
				menuItem = (MenuItem) input;
				ITranslationModel model = getTranslationModel(menuItem);
				if (model != null) {
					if (viewer == null) {
						viewer = TranslationUICore.getTranslationViewer(
								project, this.parent);
					}
					viewer.setTranslationModel(model);
				}
			}
		}
	}

	/**
	 * @param item
	 * @return
	 */
	private ITranslationModel getTranslationModel(MenuItem item) {
		project = OfsResourceHelper.getProject(item.eResource());
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		translation = manager.getTranslation(item);
		ITranslationModel model = null;
		if (translation != null) {
			model = new TranslationModel(manager.getPreferences(), translation);
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
