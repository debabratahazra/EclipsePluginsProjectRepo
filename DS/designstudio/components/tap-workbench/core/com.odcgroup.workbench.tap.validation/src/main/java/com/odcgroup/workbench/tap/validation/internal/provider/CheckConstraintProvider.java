package com.odcgroup.workbench.tap.validation.internal.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.internal.xtend.xtend.ast.Check;
import org.eclipse.internal.xtend.xtend.ast.ExtensionFile;
import org.eclipse.internal.xtend.xtend.parser.ParseFacade;
import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.expression.ExecutionContextImpl;
import org.eclipse.xtend.typesystem.emf.EmfMetaModel;
import org.osgi.framework.Bundle;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.tap.validation.ValidationCore;

public class CheckConstraintProvider extends AbstractConstraintProvider {
	
	public CheckConstraintProvider() {
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.AbstractConstraintProvider#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 */
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		super.setInitializationData(config, propertyName, data);
		
		for(IConstraintDescriptor descriptor : getCheckConstraintDescriptors()) {
			getConstraints().add(createModelConstraintProxy(descriptor));
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.AbstractConstraintProvider#createModelConstraint(org.eclipse.emf.validation.service.IConstraintDescriptor)
	 */
	@Override
	protected IModelConstraint createModelConstraint(IConstraintDescriptor descriptor) {
		if(descriptor instanceof CheckConstraintDescriptor) {
			CheckConstraintDescriptor checkDesc = (CheckConstraintDescriptor) descriptor;
			return new CheckConstraint(descriptor, checkDesc.getExecutionContext(), checkDesc.getCheck(), checkDesc.getNsURI());
		}
		return null;
	}
	
	/**
	 * @param nsURI
	 * @return
	 */
	static private ExecutionContextImpl getExecutionContext(String nsURI) {
		ExecutionContextImpl ctx = new ExecutionContextImpl();			
		Registry registry = EPackage.Registry.INSTANCE;
		ctx.registerMetaModel(new EmfMetaModel(EcorePackage.eINSTANCE));
		ctx.registerMetaModel(new EmfMetaModel(registry.getEPackage(nsURI)));
		return ctx;
	}

	static public Collection<IConstraintDescriptor> getCheckConstraintDescriptors() {
		Collection<IConstraintDescriptor> descriptors = new HashSet<IConstraintDescriptor>();
		
		IConfigurationElement[] extensions = OfsCore.getExtensions(ValidationCore.CHECK_EXTENSION);
		for(IConfigurationElement extension : extensions) {
			String bundleId = extension.getNamespaceIdentifier();
			Bundle bundle = Platform.getBundle(bundleId);
			String nsURI = extension.getAttribute("nsURI");
			for(IConfigurationElement child : extension.getChildren()) {
				String path = child.getAttribute("path");
				String category = child.getAttribute("category");
				InputStream is;
				try {
					is = FileLocator.openStream(bundle, new Path(path), false);
					ExtensionFile file = ParseFacade.file(new InputStreamReader(is), path);
					ExecutionContext executionContext = getExecutionContext(nsURI).cloneWithResource(file);
					for(Check check : file.getChecks()) {
						CheckConstraintDescriptor descriptor = new CheckConstraintDescriptor(check, nsURI, executionContext);
						descriptor.addCategory(CategoryManager.getInstance().findCategory(category));
						descriptors.add(descriptor);
					}
				} catch (IOException e) {
					// we cannot call ValidationCore.getDefault() here as we are in earlyStartup mode and the plugin is not
					// yet activated.
			    	IStatus status = new Status(IStatus.WARNING, ValidationCore.PLUGIN_ID, "Error loading check file " + path, e);
			    	Platform.getLog(bundle).log(status);
				}
			}
		}
		return descriptors;
	}
}
