package com.odcgroup.visualrules.integration.ui.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 *
 * @author pkk
 *
 */
public class RuleIntegrationMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] {new RuleIntegrationMarkerResolution()};
	}

}
