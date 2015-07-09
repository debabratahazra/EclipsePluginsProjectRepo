package com.odcgroup.process.model.localization;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.Task;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class ProcessLocalizationUtils {
	
	/** the logger */
	private static Logger logger = LoggerFactory.getLogger(ProcessLocalizationUtils.class);
	
    static public com.odcgroup.process.model.Process getProcess(ProcessItem item) {
    	EObject container = item.eContainer();
    	while (item.eContainer() != null && !(container instanceof com.odcgroup.process.model.Process)) {
    		container = container.eContainer();
    	}
    	return (com.odcgroup.process.model.Process)container;
    }
    
	/* Grabbed from MdfMarkerUtil */
	// TODO SAM: Put this in some generics utils
    static public IFile getFile(Resource resource) {
        URI uri = resource.getURI();
        uri = resource.getResourceSet().getURIConverter().normalize(uri);

        if ("platform".equals(uri.scheme()) && uri.segmentCount() > 1
                && "resource".equals(uri.segment(0))) {
            StringBuffer platformResourcePath = new StringBuffer();
            int j = 1;
            for (int size = uri.segmentCount(); j < size; j++) {
                platformResourcePath.append('/');
                platformResourcePath.append(uri.segment(j));
            }

            return ResourcesPlugin.getWorkspace().getRoot().getFile(
                    new Path(platformResourcePath.toString()));
        } else if ("file".equals(uri.scheme())) {
            StringBuffer fileResourcePath = new StringBuffer();
            int j = 1;
            for (int size = uri.segmentCount(); j < size; j++) {
                fileResourcePath.append('/');
                fileResourcePath.append(uri.segment(j));
            }

            return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
                    new Path(fileResourcePath.toString()));
        }

        return null;
    }

	private static final String TYPE_NAME = "name";

	/**
	 * @param eObject
	 * @param type
	 * @return
	 */
	private static String getLocalizedMessage(EObject eObject, String type) throws TranslationException {
		String text = null;
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(eObject.eResource());
		if (ofsProject != null) {
			ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
			ITranslation translation = manager.getTranslation(eObject);
			if (translation != null) {
				Locale locale = manager.getPreferences().getDefaultLocale();
				if (TYPE_NAME.equals(type)) {
					text = translation.getText(ITranslationKind.NAME, locale);
				} else {
					text = translation.getText(ITranslationKind.TEXT, locale);
				}
			}
		}
		return text; 	
	} 

	/**
	 * @param process
	 * @param type
	 * @return
	 */
	public static boolean localizationExists(Process process, String type){
		boolean exists = true;
		String text;
		try {
			text = getLocalizedMessage(process, type);
			exists = StringUtils.isNotEmpty(text);
		} catch (TranslationException ex) {
			logger.error("Unable to retrieve translation ["+type+"] for process ["+process.getName()+"]", ex);
			exists = false;
		}
		return exists;
	}

	/**
	 * @param task
	 * @param type
	 * @return
	 */
	public static boolean localizationExists(Task task, String type) {
		boolean exists = true;
		String text;
		try {
			text = getLocalizedMessage(task, type);
			exists = StringUtils.isNotEmpty(text);
		} catch (TranslationException ex) {
			logger.error("Unable to retrieve translation ["+type+"] for task ["+task.getName()+"]", ex);
			exists = false;
		}
		return exists;
	}

	/**
	 * @param process
	 * @param type
	 * @return
	 */
	public static String localizedMessage(Process process, String type) {
		String text = "";
		try {
			text = getLocalizedMessage(process, type);
		} catch (TranslationException ex) {
			logger.error("Unable to retrieve translation ["+type+"] for process ["+process.getName()+"]", ex);
		}
		return text;
	}    
}
