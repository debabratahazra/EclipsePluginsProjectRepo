package com.odcgroup.workbench.generation.ui.internal.marker;

import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.init.classpath.OutputPathChecker;

/**
 * This marker resolution iterates over all configured code generation cartridges and disables all of them
 * for which the java project that they generate into does not exist.
 * 
 * @author Kai Kreuzer
 *
 */
public class DeactivateCartridgesResolution implements IMarkerResolution {

	public String getLabel() {
		return "Deactivate concerned code generation cartridges";
	}

	public void run(IMarker marker) {
		IProject project = marker.getResource().getProject();
        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		
		for(CodeGenCategory category : CodeGenCategory.values()) {
			IFolder srcFolder = GenerationCore.getJavaSourceFolder(project, category);
			if(!srcFolder.exists() || !outputFolderIsCorrect(project, srcFolder)) {
				for(CodeCartridge cartridge : GenerationCore.getRegisteredCodeCartridges()) {
					if(cartridge.getCategory().equals(category)) {
						preferences.putBoolean(cartridge.getId(), false);
					}
				}
			}
		}
		preferences.flush();
	}

	private boolean outputFolderIsCorrect(IProject project, IFolder srcFolder) {
		for(String test : GenerationCore.getJavaProjectNames(project)) {
			IProject outputProject = ResourcesPlugin.getWorkspace().getRoot().getProject(test);
			IJavaProject javaProject = JavaCore.create(outputProject);
			IStatus status = new OutputPathChecker().check(javaProject, Arrays.asList(new String[]{srcFolder.getFullPath().toPortableString()}));
			if(!status.isOK()) {
				return false;
			}
		}
		return true;
	}
}
