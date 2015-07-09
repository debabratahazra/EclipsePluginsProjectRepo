package com.odcgroup.t24.mdf.editor.ui.dialogs.page;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class T24MdfTranslationDialogPagesFactory implements DialogPagesFactory {
    
    /**
     * Adds the pages for localizing elements.
     * 
     * @param element The MdfModelElement
     * @param pages The list to add the pages to
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addPages(MdfModelElement element, List pages) {
	    EObject eObj = (EObject) element;
		Resource resource = eObj.eResource(); 
		if ( resource != null) {
			IProject project = OfsResourceHelper.getProject(eObj.eResource());
			if(project!=null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(project);
				if(manager!=null) {
					ITranslation translation = manager.getTranslation(element);
					if (translation != null) {
						ITranslationPreferences prefs = manager.getPreferences();
						ITranslationModel model = new TranslationModel(prefs, translation);
						pages.add(new T24MdfTranslationDialogPage(project, model));
					}
				}
			}
		}
	}
}
