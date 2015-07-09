package com.odcgroup.mdf.validation.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelVisitorsList;
import com.odcgroup.mdf.validation.MdfValidationCore;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class ValidatorsFactory {

	private static final Map<IConfigurationElement, Class<?>> validatorsCache = new HashMap<IConfigurationElement, Class<?>>();
	
	private static ModelValidator getNewModelValidator(IConfigurationElement iConfigurationElement) throws CoreException {
		Class<?> modelValidatorClass = validatorsCache.get(iConfigurationElement);
		if (modelValidatorClass == null) {
			ModelValidator modelValidator = (ModelValidator) iConfigurationElement.createExecutableExtension("class");
			modelValidatorClass = (Class<?>) modelValidator.getClass();
			validatorsCache.put(iConfigurationElement, modelValidatorClass);
		}
		try {
			return (ModelValidator) modelValidatorClass.newInstance();
		} catch (Exception e) {
			MdfValidationCore.getDefault().logError(e.getLocalizedMessage(), e);
			return null;
		}
	}

	public static ModelVisitor newInstance(IResource resource) throws CoreException {
		ModelVisitorsList visitor = newInstance(); 
		ValidationListener listener = new MarkersFactory(resource);
		setValidationListener(visitor, listener);
		return visitor;
	}

	public static ModelVisitorsList newInstance() {
		ModelVisitorsList validator = new ModelVisitorsList();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(ModelValidator.EXTENSION_POINT);
        
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if ("validator".equals(elements[j].getName())) {
					try {
						ModelValidator ext = getNewModelValidator(elements[j]);
						validator.add(ext);
					} catch (CoreException e) {
						MdfValidationCore.getDefault().logError(e.getLocalizedMessage(), e);
					}
				}
			}
		}
		return validator;
	}
	
	private static void setValidationListener(ModelVisitorsList instance, ValidationListener listener) {
		List<ModelVisitor> visitors = instance.getModelVisitors();
		for (ModelVisitor modelVisitor : visitors) {
			if (modelVisitor instanceof ModelValidator) {
				((ModelValidator) modelVisitor).setValidationListener(listener);
			}
		}
	}
	
	public static ModelVisitor newInstance(ValidationListener listener) {
		ModelVisitorsList visitor = newInstance(); 
		setValidationListener(visitor, listener);
		return visitor;
	}
}
