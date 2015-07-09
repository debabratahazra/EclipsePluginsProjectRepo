package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.List;

import org.eclipse.xtext.resource.IEObjectDescription;

/**
 *
 * @author phanikumark
 *
 */
public interface ResourceSelectionFilter {	
	
	List<IEObjectDescription> filter(Iterable<IEObjectDescription> matches);

}
