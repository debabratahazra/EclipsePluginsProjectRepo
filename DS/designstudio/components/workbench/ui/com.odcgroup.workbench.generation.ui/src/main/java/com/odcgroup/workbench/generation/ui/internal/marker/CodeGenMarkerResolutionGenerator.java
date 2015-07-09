package com.odcgroup.workbench.generation.ui.internal.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class CodeGenMarkerResolutionGenerator implements
		IMarkerResolutionGenerator {

	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] { 
				new ReconfigureProjectsResolution(), 
				new DeactivateCartridgesResolution(),
				};
	}

}
