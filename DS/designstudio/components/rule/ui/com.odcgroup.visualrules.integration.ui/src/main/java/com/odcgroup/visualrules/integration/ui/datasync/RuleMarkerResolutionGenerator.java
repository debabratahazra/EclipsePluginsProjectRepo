package com.odcgroup.visualrules.integration.ui.datasync;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class RuleMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] { new DeleteRuleMarkerResolution() };
	}

}
