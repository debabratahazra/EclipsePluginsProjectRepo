package com.odcgroup.workbench.core.xtext.v26_ds7527;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;

/**
 * Code copy/pasted from Xtext v2.6 (when DS was still on 2.5.3). 
 * 
 * TODO: remove after migration to Xtext 2.6 or later.
 * @see http://rd.oams.com/browse/DS-7527
 *
 * @author kosyakov
 * @author Michael Vorburger
 */
public class AccessibleResourceSetBasedResourceDescriptions extends ResourceSetBasedResourceDescriptions {
	
	@Override
	public boolean hasDescription(URI uri) {
		return super.hasDescription(uri);
	}

}
