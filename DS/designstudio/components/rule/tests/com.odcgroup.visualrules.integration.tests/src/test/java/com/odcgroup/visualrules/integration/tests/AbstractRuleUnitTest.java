package com.odcgroup.visualrules.integration.tests;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.visualrules.integration.generation.VisualRulesCodeGenerator;
import com.odcgroup.visualrules.integration.init.RulesInitializer;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.generation.GenerationCore;

public class AbstractRuleUnitTest extends AbstractDSUnitTest {

	public IOfsProject createRuleProject() throws CoreException {
		return createNamedRuleProject(DEFAULT_MODELS_PROJECT);
	}

	public IOfsProject createNamedRuleProject(String modelProjectName) throws CoreException {
		IOfsProject ofsProject = super.createNamedModelsProject(modelProjectName);

		ProjectPreferences preferences = new ProjectPreferences(ofsProject.getProject(), GenerationCore.PLUGIN_ID);
		preferences.putBoolean(VisualRulesCodeGenerator.CARTRIDGE_ID, true);
		RulesInitializer initializer = new RulesInitializer();
		initializer.updateConfiguration(ofsProject.getProject(), null);
		
		return ofsProject;
	}

}
