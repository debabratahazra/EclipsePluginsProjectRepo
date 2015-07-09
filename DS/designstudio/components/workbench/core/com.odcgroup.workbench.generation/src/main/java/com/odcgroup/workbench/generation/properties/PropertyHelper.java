package com.odcgroup.workbench.generation.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.ModelCartridge;
import com.odcgroup.workbench.generation.headless.RunGeneration;

public class PropertyHelper {
	private static final Logger logger = LoggerFactory.getLogger(RunGeneration.class);

	static public CodeCartridge[] getSelectedCodeCartridges(IProject project) {
		List<CodeCartridge> selectedCartridges = new ArrayList<CodeCartridge>();
		CodeCartridge[] registeredCartridges = GenerationCore.getRegisteredCodeCartridges();
		
        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		
		// check which of the registered cartridges are enabled on this project
		for(CodeCartridge cartridge : registeredCartridges) {
			if(preferences.getBoolean(cartridge.getId(), cartridge.isEnabledByDefault())) {
				selectedCartridges.add(cartridge);
			}
		}
		
		// check if there are cartridges enabled on this project which are not (no longer) available
		for (String projectCartridgeID : preferences.getAllKeys()) {
			if (preferences.getBoolean(projectCartridgeID, false)
					&& !isAvailable(registeredCartridges, projectCartridgeID)) {
				logger.error("Project configuration has an active code generator cartridge which does not yet/anymore exist (or which isn't included in this headless generator product definition): " + projectCartridgeID);
			}
		}
		
		return selectedCartridges.toArray(new CodeCartridge[0]);
	}

	private static boolean isAvailable(CodeCartridge[] registeredCartridges, String cartridgeID) {
		boolean isAvailable = false;
		for(CodeCartridge cartridge : registeredCartridges) {
			if (cartridge.getId().equals(cartridgeID))
				isAvailable = true;
		}
		return isAvailable;
	}

	static public ModelCartridge[] getSelectedModelCartridges(IProject project) {
		List<ModelCartridge> selectedCartridges = new ArrayList<ModelCartridge>();
		ModelCartridge[] registeredCartridges = GenerationCore.getRegisteredModelCartridges();
		
        ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);

		for(ModelCartridge cartridge : registeredCartridges) {
			if(preferences.getBoolean(cartridge.getId(), true)) {
				selectedCartridges.add(cartridge);
			}
		}
		return selectedCartridges.toArray(new ModelCartridge[0]);
	}
	
	static public boolean isCodeCartridgeEnabled(IProject project, String cartridgeId) {
		for(CodeCartridge cartridge : getSelectedCodeCartridges(project)) {
			if(cartridge.getId().equals(cartridgeId)) return true;
		}
		return false;
	}
}
