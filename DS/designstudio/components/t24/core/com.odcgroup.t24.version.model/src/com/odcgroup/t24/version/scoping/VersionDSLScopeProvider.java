package com.odcgroup.t24.version.scoping;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * This class contains custom scoping description.
 * @see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on how and when to use it.
 */
public class VersionDSLScopeProvider extends AbstractDeclarativeScopeProvider {
	
	@Inject 
	private IResourceServiceProvider resourceServiceProvider;
	
	@Inject 
	private IResourceDescriptions globalIndex;
	
	IScope scope_forApplication(Version version, EReference ref) {
		IContainer.Manager containerManager = resourceServiceProvider.getContainerManager();
		IResourceDescription resourceDescription = globalIndex.getResourceDescription(version.eResource().getURI());
		List<IContainer> containers = containerManager.getVisibleContainers(resourceDescription, globalIndex);
		List<IEObjectDescription> candidates = Lists.newArrayList();
		for (IContainer container : containers) {
			for (IResourceDescription desc : container.getResourceDescriptions()) {
				Iterables.addAll(candidates, desc.getExportedObjectsByType(MdfPackage.Literals.MDF_CLASS));
			}
		}
		return new SimpleScope(candidates);
	}
	
}
