package com.odcgroup.menu.model.scoping;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class contains custom scoping description. We simply create the scope
 * based on the resourceset the model element is in.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class MenuGlobalScopeProvider extends DefaultGlobalScopeProvider {

	@Override
	protected List<IContainer> getVisibleContainers(Resource resource) {
		// work around as the index knows platform uri for the
		// resourcedescription
		URI uri = resource.getURI();
		if (ModelURIConverter.isModelUri(uri)) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			ModelURIConverter uriConverter = new ModelURIConverter(ofsProject);
			uri = uriConverter.toPlatformURI(uri);
			resource.setURI(uri);
		}
		return super.getVisibleContainers(resource);
	}

}
