package com.odcgroup.page.translation.generation.internal;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.generation.cartridge.SelectionProvider;

/**
 * This selection provider adds all included fragments to selected modules.
 * see http://rd.oams.com/browse/DS-4874
 * 
 * @author kkr
 *
 */
public class FragmentSelectionProvider implements SelectionProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(FragmentSelectionProvider.class);

	public FragmentSelectionProvider() {}

	@Override
	public void completeModelResourceSelection(Map<IProject, Collection<IOfsModelResource>> selectedResourcesPerProject) {
		Set<IOfsModelResource> referencedFragments = new HashSet<IOfsModelResource>();
		
		Set<URI> uriVisited = new HashSet<URI>();
		for(IOfsModelResource modelResource : Iterables.concat(selectedResourcesPerProject.values())) {
			if(modelResource.getURI().fileExtension().equals(PageConstants.MODULE_FILE_EXTENSION)) {
				loadReferencedFragments(uriVisited, referencedFragments, modelResource);
			}
		}
		IProject project = null;
		for(IOfsModelResource modelResource : referencedFragments) {
			project = modelResource.getOfsProject().getProject();
			Collection<IOfsModelResource> projectResources = selectedResourcesPerProject.get(project);
			if(projectResources==null) {
				projectResources = new HashSet<IOfsModelResource>();
				selectedResourcesPerProject.put(project, projectResources);
			}
			projectResources.add(modelResource);
		}
	}
	
	private void loadReferencedFragments(Set<URI> uriVisited, Set<IOfsModelResource> referencedFragments, IOfsModelResource modelResource) {
		Stack<Widget> stack = new Stack<Widget>();
		try {
			Model model = (Model) modelResource.getEMFModel().get(0);
			if (model != null) {
				Widget rootWidget = model.getWidget();
				if (rootWidget != null) {
					stack.push(rootWidget);
					while (! stack.isEmpty()) {
						Widget widget = stack.pop();
						if(WidgetTypeConstants.INCLUDE.equals(widget.getTypeName())) {
							Property include = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
							if(include!=null) {
								URI uri = include.getModelURI();
								if(uri!=null && !uriVisited.contains(uri) && PageConstants.FRAGMENT_FILE_EXTENSION.equals(uri.fileExtension())) {
									uriVisited.add(uri);
									try {
										IOfsModelResource fragmentResource = modelResource.getOfsProject().getOfsModelResource(uri);
										referencedFragments.add(fragmentResource);
										model = (Model)fragmentResource.getEMFModel().get(0);
										if (model != null) {
											rootWidget = model.getWidget();
											if (rootWidget != null) {
												stack.push(rootWidget);
											}
										}
									} catch (ModelNotFoundException e) {
										logger.debug("Didn't find included fragment '{}'", uri.toString());
									} catch (IOException e) {
										logger.debug("Error while accessing model resource '{}'", uri.toString(), e);
									} catch (InvalidMetamodelVersionException e) {
										logger.debug("Error while accessing model resource '{}'", uri.toString(), e);
									}
								}
							}
						}
						for (Widget containedWidget : widget.getContents()) {
							stack.push(containedWidget);
						}
					}
				}
			}
		} catch (IOException e) {
			logger.debug("Error while accessing model resource '{}'", modelResource.getURI().toString(), e);
		} catch (InvalidMetamodelVersionException e) {
			logger.debug("Error while accessing model resource '{}'", modelResource.getURI().toString(), e);
		}
	}

}
