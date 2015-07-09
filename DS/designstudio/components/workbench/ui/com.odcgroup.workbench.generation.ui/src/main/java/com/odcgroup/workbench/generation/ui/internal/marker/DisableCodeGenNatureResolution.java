package com.odcgroup.workbench.generation.ui.internal.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.workbench.generation.GenerationCore;

/**
 * This marker resolution reinitializes the code generation nature of the project and therefore makes sure that
 * all required projects exist and that they are correctly set up.
 * 
 * @author Kai Kreuzer
 *
 */
public class DisableCodeGenNatureResolution implements IMarkerResolution {

	public String getLabel() {
		return "Change project type to 'Sketching'";
	}

	public void run(IMarker marker) {
		IProject project = (IProject) marker.getResource();
		
		GenerationCore.toggleNature(project);
	}
}
