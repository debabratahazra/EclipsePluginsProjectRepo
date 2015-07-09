package com.odcgroup.ocs.support.ui.m2eclipse.migration;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class OldM2EclipseProblemResolutionGenerator implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] {new OldM2EclipseProblemResolution()};
	}

}
