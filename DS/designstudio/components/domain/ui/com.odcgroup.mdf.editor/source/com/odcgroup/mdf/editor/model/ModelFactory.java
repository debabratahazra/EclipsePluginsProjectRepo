package com.odcgroup.mdf.editor.model;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.mdf.ecore.ECoreModelFactory;


/**
 *
 */
public class ModelFactory extends ECoreModelFactory {
	
	private static final Logger LOGGER = Logger.getLogger(ModelFactory.class);	
	
	private static final String EXTENSION_POINT = "com.odcgroup.mdf.editor.modelfactory_override";
	
	public static final ModelFactory INSTANCE = getInstance();


    protected ModelFactory() {
    }

    public boolean isModelFile(IFile file) {
        // Must not be derived, have the right extension and belong to the
        // models folder
        if (!file.isDerived()) {
            if (isModelFile(file.getName())) {
                IFolder models = MdfUtility.getModelsFolder(file.getProject());
                return models.equals(file.getParent());
            }
        }

        return false;
    }
    
    /**
     * DS-1694
     * 
     * @param workbench
     * @param shell
     * @return
     */
    private static ModelFactory getInstance() {
    	ModelFactory mFactory = null;
    	IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
        
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if ("mdfModelFactory".equals(elements[j].getName())) {
					try {
						mFactory = (ModelFactory) elements[j].createExecutableExtension("class");
					} catch (CoreException e) {
						LOGGER.error(e.getLocalizedMessage(), e);
					}
				}
			}
		}
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
    	return mFactory;
    }

}
