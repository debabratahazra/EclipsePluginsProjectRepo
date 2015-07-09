package com.odcgroup.documentation.generation.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.documentation.generation.DocGenCartridge;
import com.odcgroup.documentation.generation.DocumentationCore;
import com.odcgroup.documentation.generation.ui.DocumentationUICore;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

public class PropertyHelper {

	/**
	 * @param project
	 * @return
	 */
	static public DocGenCartridge[] getSelectedDocGenCartridges(IProject project) {
		List<DocGenCartridge> selectedCartridges = new ArrayList<DocGenCartridge>();
		DocGenCartridge[] registeredCartridges = DocumentationCore.getRegisteredDocGenCartridges();
		
        ProjectPreferences preferences = new ProjectPreferences(project, DocumentationUICore.PLUGIN_ID);
		
		// check if enabled
		for(DocGenCartridge cartridge : registeredCartridges) {
			if(preferences.getBoolean(cartridge.getId(), cartridge.isEnabledByDefault())) {
				selectedCartridges.add(cartridge);
			}
		}
		return selectedCartridges.toArray(new DocGenCartridge[0]);
	}

}
