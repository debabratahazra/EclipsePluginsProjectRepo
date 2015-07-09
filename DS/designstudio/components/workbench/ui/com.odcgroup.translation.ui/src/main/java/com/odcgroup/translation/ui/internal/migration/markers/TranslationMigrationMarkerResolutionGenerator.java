package com.odcgroup.translation.ui.internal.migration.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * Marker resolution for migration of translation (not standard translation)
 * @author yan
 */
public class TranslationMigrationMarkerResolutionGenerator implements
		IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] {new TranslationMigrationMarkerResolution()};
	}

}
