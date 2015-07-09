package com.odcgroup.translation.ui.editor.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;

/**
 *
 * @author pkk
 *
 */
public abstract class BaseTranslationCollector implements ITranslationCollector {
	
	/** */
	private IOfsProject ofsProject;
	/** */
	private ITranslationManager translationManager;
	/** */
	private String[] modelNames;

	/**
	 * @param ofsProject
	 */
	public BaseTranslationCollector(IOfsProject ofsProject, String[] modelNames) {
		this.ofsProject = ofsProject;
		this.translationManager = TranslationCore.getTranslationManager(ofsProject.getProject());
		this.modelNames = modelNames;
	}
	
	/**
	 * @param eObject
	 * @return
	 */
	final protected ITranslation getTranslation(EObject eObject) {
		return getTranslationManager().getTranslation(eObject);
	}
	
	/**
	 * @return the ofsProject
	 */
	protected final IOfsProject getOfsProject() {
		return ofsProject;
	}

	/**
	 * @return the translationManager
	 */
	protected final ITranslationManager getTranslationManager() {
		return translationManager;
	}
	
	protected final String[] getModelNames() {
		return modelNames;
	}
	
	/**
	 * @param list
	 */
	public List<ITranslation> collectTranslations(int scope) throws CoreException {
		
		final List<ITranslation> translations = new ArrayList<ITranslation>();
		IOfsProject ofsProject = getOfsProject();
		ofsProject.refresh();
		ModelResourceSet rs = ofsProject.getModelResourceSet();
		Set<IOfsModelResource> ofsResources = rs.getAllOfsModelResources(getModelNames(), scope);
		
		for (IOfsModelResource res : ofsResources) {
			try {
				List<EObject> eObjList = res.getEMFModel();
				if (eObjList.size() > 0) {
					EObject model = eObjList.get(0);
					ITranslationModelVisitor visitor = getTranslationManager().getTranslationModelVisitor(model);
					if (visitor != null) {
						visitor.visit(new ITranslationModelVisitorHandler() {
							public void notify(ITranslation translation) {
								translations.add(translation);
							}
						});
					}
				}
			} catch (IOException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID, 
						"Error while collecting translations for model "+res.getURI(), ex);
				throw new CoreException(status);
			} catch (InvalidMetamodelVersionException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID, 
						ex.getLocalizedMessage(), ex);
				throw new CoreException(status);
			}
		}
		
		return translations;
	}
	
}
