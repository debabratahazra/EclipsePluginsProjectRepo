package com.odcgroup.workbench.ui.internal.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class OfsMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] { new OfsMarkerResolution() };
	}

}
