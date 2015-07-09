package com.odcgroup.page.translation.generation.tests;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;
import com.odcgroup.workbench.generation.ui.action.CodeGenerationAction;

public class TranslationNLSGenerationTest extends AbstractDSGenerationTest {

	private static final String MODULE = "module/Default/module/DS4874.module";
	private static final String FRAGMENT1 = "fragment/Default/general/DS4874.fragment";
	private static final String FRAGMENT2 = "fragment/Default/general/DS4874_2.fragment";

	@Before
	public void setUp() throws Exception {
		createModelAndGenProject();
		copyResourceInModelsProject(MODULE, FRAGMENT1, FRAGMENT2);
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}	
	
	@Test
	public void testNlsGeneration() throws ModelNotFoundException, IOException, CoreException {
		IStructuredSelection selection = new StructuredSelection(OfsCore.getOfsProject(getProject()).getOfsModelResource(URI.createURI("resource:///Default/module/DS4874.module")));
		CodeGenerationAction action = new CodeGenerationAction();
		action.run(selection);

		IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(GenerationCore.getJavaProjectName(getProject(), CodeGenCategory.WUIBLOCK));
		IFile messages = genProject.getFile("src/wui_block/" + DEFAULT_MODELS_PROJECT + "/messages.xml");
		messages.refreshLocal(IResource.DEPTH_ZERO, null);
		String content = FileUtils.readFileToString(messages.getLocation().toFile());
		Assert.assertTrue("Fragment translations have not been generated", content.contains("TEST1"));
		Assert.assertTrue("Fragment translations have not been generated", content.contains("TEST2"));		
	}

}
