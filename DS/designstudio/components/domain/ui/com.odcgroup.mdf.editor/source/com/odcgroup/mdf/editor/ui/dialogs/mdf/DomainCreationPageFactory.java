package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

public class DomainCreationPageFactory implements IDomainCreationPageCreator {

	/**  */
	public static final String EXTENSION_POINT = "com.odcgroup.mdf.editor.domaincreationpage";
	/**  */
	public static final String CONFIG_ELEMENT_NAME = "pagecreator";
	/**  */
	public static final String CLASS_ATTRIBUTE = "class";

	private static final Logger LOGGER = Logger
			.getLogger(DomainCreationPageFactory.class);

	/**  */
	private static DomainCreationPageFactory INSTANCE = null;

	/**
	 * private constructor
	 */
	private DomainCreationPageFactory() {
	}

	/**
	 * @return EventDialogFactory
	 */
	public static DomainCreationPageFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DomainCreationPageFactory();
		}
		return INSTANCE;
	}

	/**
	 * fetches the DomainCreationPageCreator from registry
	 * 
	 * @return IDomainCreationPageCreator
	 * 
	 */
	private IDomainCreationPageCreator getDomainCreationPageCreatorFromRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
		IDomainCreationPageCreator creationPage = null;
		IExtension[] extensions = point.getExtensions();
		if (extensions.length > 0) {
			IConfigurationElement[] elements = extensions[0]
					.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if (CONFIG_ELEMENT_NAME.equals(elements[j].getName())) {
					try {
						creationPage = (IDomainCreationPageCreator) elements[j]
								.createExecutableExtension(CLASS_ATTRIBUTE);
					} catch (CoreException e) {
						LOGGER.error("Error loading \'" + EXTENSION_POINT
								+ "\'", e);
					}
				}
			}
		}
		return creationPage;
	}

	/**
	 * @param pageName
	 * @param workbench
	 * @param containerFullPath
	 * @param initialDomain
	 * @param copyPage
	 * @param resource
	 * @return
	 */
	public static AbstractDomainCreationPage getCreationPage(String pageName,
			IWorkbench workbench, IPath containerFullPath,
			MdfDomain initialDomain, boolean copyPage, OfsModelResource resource) {
		return getInstance().getDomainCreationPage(pageName, workbench,
				containerFullPath, initialDomain, copyPage, resource);
	}

	@Override
	public AbstractDomainCreationPage getDomainCreationPage(String pageName,
			IWorkbench workbench, IPath containerFullPath,
			MdfDomain initialDomain, boolean copyPage, OfsModelResource resource) {
		IDomainCreationPageCreator creator = getDomainCreationPageCreatorFromRegistry();
		if (creator != null) {
			return creator.getDomainCreationPage(pageName, workbench,
					containerFullPath, initialDomain, copyPage, resource);
		} else {
			return new NewDomainCreationPage(pageName, workbench,
					containerFullPath, initialDomain, copyPage, resource);
		}
	}

}
