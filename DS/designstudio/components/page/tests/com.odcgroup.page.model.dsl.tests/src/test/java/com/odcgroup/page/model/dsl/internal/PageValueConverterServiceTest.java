package com.odcgroup.page.model.dsl.internal;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class PageValueConverterServiceTest extends AbstractDSUnitTest {

	IFile file;
	private String content;

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("module/test/MyTestModule.module");

		Model model = ModelFactory.eINSTANCE.createModel();
		
		Widget widget = ModelFactory.eINSTANCE.createWidget();
		widget.setLibraryName("xgui");
		widget.setTypeName("Box");
		
		model.setWidget(widget);
		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("module"));
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Injector injector = Guice.createInjector(new com.odcgroup.page.PageRuntimeModule());
		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("module", resourceFactory);

		resourceSet.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
		Resource resource = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file), false);
		resource.getContents().add(model);
		resource.save(null);
		content = FileUtils.readFileToString(file.getLocation().toFile());
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testXguiRemoval() {
		Assert.assertFalse(content.contains("xgui:"));
	}
}
