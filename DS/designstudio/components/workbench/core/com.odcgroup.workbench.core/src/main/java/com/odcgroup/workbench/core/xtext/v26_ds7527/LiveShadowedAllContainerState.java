/*******************************************************************************
 * Copyright (c) 2014 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.odcgroup.workbench.core.xtext.v26_ds7527;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Code copy/pasted from Xtext v2.6 (when DS was still on 2.5.3). 
 * 
 * TODO: remove after migration to Xtext 2.6 or later.
 * @see http://rd.oams.com/browse/DS-7527
 *
 * @author kosyakov
 * @author Michael Vorburger
 */
public class LiveShadowedAllContainerState implements IAllContainersState {

	public static class Provider {
		// we're not implementing IAllContainerState.Provider here because its get() expects a global instance of IResourceDescrions. 
		// Here, we expect one that only holds an IResourceDescrion for every resource from a ResourceSet.  

		@Inject
		private com.google.inject.Provider<LiveShadowedAllContainerState> provider;

		public IAllContainersState get(IResourceDescriptions localDescriptions, IAllContainersState globalState) {
			LiveShadowedAllContainerState result = provider.get();
			result.globalState = globalState;
			result.localDescriptions = localDescriptions;
			return result;
		}
	}

	private IAllContainersState globalState;

	private IResourceDescriptions localDescriptions;

	public Collection<URI> getContainedURIs(String containerHandle) {
		Set<URI> result = Sets.newLinkedHashSet();
		for (IResourceDescription descriptions : localDescriptions.getAllResourceDescriptions()) {
			String computedHandle = getContainerHandle(descriptions.getURI());
			if (computedHandle != null && computedHandle.equals(containerHandle))
				result.add(descriptions.getURI());
		}
		result.addAll(globalState.getContainedURIs(containerHandle));
		return result;
	}

	public String getContainerHandle(URI uri) {
		return globalState.getContainerHandle(uri);
	}

	public List<String> getVisibleContainerHandles(String handle) {
		return globalState.getVisibleContainerHandles(handle);
	}

	public boolean isEmpty(String containerHandle) {
		for (IResourceDescription descriptions : localDescriptions.getAllResourceDescriptions()) {
			String computedHandle = getContainerHandle(descriptions.getURI());
			if (computedHandle != null && computedHandle.equals(containerHandle))
				return false;
		}
		return globalState.isEmpty(containerHandle);
	}

}
