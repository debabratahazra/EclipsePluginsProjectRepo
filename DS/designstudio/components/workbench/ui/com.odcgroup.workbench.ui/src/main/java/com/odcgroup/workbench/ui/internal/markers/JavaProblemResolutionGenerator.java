package com.odcgroup.workbench.ui.internal.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * This class is used to collect all possible resolutions that can be fixed on Workspace Java Project
 * Possible resolution for Marker type: com.odcgroup.eds.plugin.javaProblem 
 */
public class JavaProblemResolutionGenerator implements IMarkerResolutionGenerator {

	public IMarkerResolution[] getResolutions(IMarker marker) {
		IMarkerResolution[] resultArray = {new JavaProblemResolution()};
		return resultArray;
	}

}
