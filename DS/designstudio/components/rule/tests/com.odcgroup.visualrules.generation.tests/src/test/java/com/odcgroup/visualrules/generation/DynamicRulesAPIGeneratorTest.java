package com.odcgroup.visualrules.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;

public class DynamicRulesAPIGeneratorTest extends AbstractRuleUnitTest {

	@Before
	public void setUp() throws Exception {
		createRuleProject();
		
		// activate our cartridge (as it is disabled by default)
		ProjectPreferences preferences = new ProjectPreferences(getProject(), GenerationCore.PLUGIN_ID);
		preferences.putBoolean("com.odcgroup.visualrules.generation.DynamicRulesAPIGenerator", true);
		preferences.flush();

		copyResourceInModelsProject("domain/ds3453/DS3453.domain");
		OfsCore.addNature(getProject(), GenerationCore.TECHNICAL_NATURE_ID);
		CodeGenInitializer initializer = new CodeGenInitializer();
		initializer.updateConfiguration(getProject(), null);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testDoRun() throws ModelNotFoundException, CoreException, IOException {
		IOfsModelResource domainResource = getModelResource(getOfsProject(), "ds3453/DS3453.domain");
		Set<IOfsModelResource> resources = Collections.singleton(domainResource);
		
		// now generate our dynamic API
		DynamicRulesAPIGenerator generator = new DynamicRulesAPIGenerator();
		IFolder outputFolder = GenerationCore.getJavaSourceFolder(getProject(), CodeGenCategory.API_R);
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.doRun(getProject(), outputFolder, resources, nonOkStatuses);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		
		IFile generatedJavaFile = outputFolder.getFile((new Path("ds3453/rules/RuleModel.java")));
		Assert.assertTrue(generatedJavaFile.exists());

		// check some important lines of the content of the generated file to get a rough impression, if it looks alright
		// we do not do any compilation tests here as this would go far beyond a unit test; this is covered by the SWTBot
		// tests anyhow.
		String fileContents = IOUtils.toString(generatedJavaFile.getContents(), generatedJavaFile.getCharset(true));
		Assert.assertTrue(fileContents.contains("public interface RuleModel"));
		String ruleModelName = RulesIntegrationPlugin.getVRBasePath(getProject()).substring("vrpath:/rules_".length());
		Assert.assertTrue(fileContents.contains("IRuleModel INSTANCE = new Rules_" + ruleModelName + "RuleModel();"));
	}

}
