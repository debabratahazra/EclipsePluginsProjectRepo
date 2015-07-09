package com.odcgroup.page.model.dsl.internal;

import java.io.IOException;
import java.util.Arrays;

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
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

public class PageDSLResourceTest extends AbstractDSUnitTest {

	private String content;

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		String modulePath = "module/test/PageXtextResourceTest.module";
		IFile file = getProject().getFile(modulePath);
		Model model = createModelWithWidget();
		saveModel(file, model);
		content = FileUtils.readFileToString(file.getLocation().toFile());
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	private Resource saveModel(IFile file, Model model) throws IOException {
		Resource resource = createResource(file);
		resource.getContents().add(model);
		resource.save(null);
		return resource;
	}

	private Resource createResource(IFile file) {
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Injector injector = Guice.createInjector(new com.odcgroup.page.PageRuntimeModule());
		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("module", resourceFactory);

		resourceSet.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
		Resource resource = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file), false);
		return resource;
	}

	private Model createModelWithWidget() {
		Model model = createEmptyModel();
		
		Widget widget = createRootWidget();
		model.setWidget(widget);
		return model;
	}

	private Model createEmptyModel() {
		Model model = ModelFactory.eINSTANCE.createModel();
		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("module"));
		return model;
	}

	private Widget createRootWidget() {
		Widget widget = ModelFactory.eINSTANCE.createWidget();
		widget.setLibraryName("xgui");
		widget.setTypeName("Box");
		
		Property p1 = ModelFactory.eINSTANCE.createProperty();
		p1.setLibraryName("xgui");
		p1.setTypeName("aggregation");
		p1.setValue("true");

		Property p2 = ModelFactory.eINSTANCE.createProperty();
		p2.setTypeName("constraints");
		p2.setValue("10");

		Property p3 = ModelFactory.eINSTANCE.createProperty();
		p3.setTypeName("tid");
		p3.setValue("101");

		Property p4 = ModelFactory.eINSTANCE.createProperty();
		p4.setTypeName("height");
		p4.setValue("1000");

		Property p5 = ModelFactory.eINSTANCE.createProperty();
		p5.setTypeName("width");
		p5.setValue("1001");

		Property p6 = ModelFactory.eINSTANCE.createProperty();
		p6.setTypeName("widgetBorder");
		p6.setValue("maybe");

		Property p7 = ModelFactory.eINSTANCE.createProperty();
		p7.setTypeName("boxType");
		p7.setValue("Vertical");

		Property p8 = ModelFactory.eINSTANCE.createProperty();
		p8.setTypeName("isWidgetSelectable");
		p8.setValue("false");
		
		Property p9 = ModelFactory.eINSTANCE.createProperty();
		p9.setTypeName("cssClass");
		p9.setValue("testClass");
		
		
		Property p10 = ModelFactory.eINSTANCE.createProperty();
		p10.setTypeName("documentation");
		p10.setValue("testdocs");
		widget.getProperties().addAll(Arrays.asList(new Property[] { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10}));

		Translation translation = ModelFactory.eINSTANCE.createTranslation();
		translation.setLanguage("en");
		translation.setMessage("english");
		
		widget.getLabels().add(translation);
		return widget;
	}

	@Test
	public void testPropertySorting() {
		Assert.assertTrue(content.indexOf("labels") < content.indexOf("boxType"));
		Assert.assertTrue(content.indexOf("boxType") < content.indexOf("cssClass"));
		Assert.assertTrue(content.indexOf("cssClass") < content.indexOf("height"));
		Assert.assertTrue(content.indexOf("height") < content.indexOf("widgetBorder"));
		Assert.assertTrue(content.indexOf("widgetBorder") < content.indexOf("width"));
		Assert.assertTrue(content.indexOf("width") < content.indexOf("aggregation"));
//		Assert.assertTrue(content.indexOf("documentation") < content.indexOf("aggregation"));
		Assert.assertTrue(content.indexOf("aggregation") < content.indexOf("constraints"));
		Assert.assertTrue(content.indexOf("constraints") < content.indexOf("isWidgetSelectable"));
		Assert.assertTrue(content.indexOf("isWidgetSelectable") < content.indexOf("tid"));
		
	}
	
	@Test
	public void testCommentHeader() {
		Assert.assertTrue(content.startsWith(AbstractDSLResource.DSL_FILE_HEADER_COMMENT));
	}

}
