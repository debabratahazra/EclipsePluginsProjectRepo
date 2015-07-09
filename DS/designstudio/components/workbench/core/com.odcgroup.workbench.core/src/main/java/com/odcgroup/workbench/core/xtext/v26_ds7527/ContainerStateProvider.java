package com.odcgroup.workbench.core.xtext.v26_ds7527;

import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;

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
public class ContainerStateProvider implements IAllContainersState.Provider {

	@Inject
	private IAllContainersState containerState;

	@Inject
	private LiveShadowedAllContainerState.Provider liveShadowedAllContainerStateProvider;

	public IAllContainersState get(IResourceDescriptions context) {
		if (context instanceof LiveShadowedResourceDescriptions) {
			IResourceDescriptions local = ((LiveShadowedResourceDescriptions) context).getLocalDescriptions();
			return liveShadowedAllContainerStateProvider.get(local, containerState);
		}
		return containerState;
	}

}