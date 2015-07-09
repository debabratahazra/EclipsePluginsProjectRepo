package com.odcgroup.mdf.validation.validator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Factory for MdfModelValidator
 * 
 * @author pkk
 * DS-1691
 */
public class MdfModelValidatorFactory {
	
	private static MdfModelValidator mdfModelValidator = null;
	private static final String EXTENSION_ID = "com.odcgroup.mdf.validation.mdfmodelvalidator_override";
	private static final Logger LOGGER = Logger.getLogger(MdfModelValidatorFactory.class);
	
	/**
	 * @param validateNew
	 * @return
	 */
	public static MdfModelValidator getMdfModelValidator(boolean validateNew) {
		if (mdfModelValidator != null) {
			return mdfModelValidator;
		}
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);
        
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if ("mdfModelValidator".equals(elements[j].getName())) {
					try {
						mdfModelValidator =
							(MdfModelValidator) elements[j].createExecutableExtension(
								"class");
						mdfModelValidator.setValidateNew(validateNew);
						
					} catch (CoreException e) {
						LOGGER.error(e, e);
					}
				}
			}
		}
		if (mdfModelValidator == null){
			mdfModelValidator = new MdfModelValidator(validateNew);
		}
		return mdfModelValidator;
	}

}
