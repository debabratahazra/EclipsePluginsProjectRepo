package com.odcgroup.workbench.migration.ui.internal.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class MigrationMarkerResolutionGenerator implements
		IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] {new MigrationMarkerResolution()};
	}

}
