package com.odcgroup.domain.resource;

import static org.junit.Assert.assertNotNull;

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
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

public class DomainDSLResourceTest extends AbstractDSUnitTest {

	IFile file;
	private String content;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("domain/test/DomainXtextResourceTest.domain");

		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("NewDomain");
		domain.setMetamodelVersion(OfsCore.getVersionNumber());
		MdfClassImpl clazz = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		clazz.setName("NewClass");
		MdfAttributeImpl attr1 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr1.setName("a_attribute");
		MdfAttributeImpl attr2 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr2.setName("C_attribute");
		MdfAttributeImpl attr3 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr3.setName("e_attribute");
		MdfAssociationImpl assoc1 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		assoc1.setName("b_association");
		assoc1.setType(clazz);
		MdfAssociationImpl assoc2 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		assoc2.setName("D_association");
		assoc2.setType(clazz);

		clazz.getProperties().addAll(Arrays.asList(new MdfProperty[] { attr1, attr2, attr3, assoc1, assoc2 }));
		domain.getClasses().add(clazz);
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Injector injector = Guice.createInjector(new com.odcgroup.domain.DomainRuntimeModule());
		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resourceFactory);

		resourceSet.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
		Resource resource = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file), false);
		resource.getContents().add(domain);
		resource.save(null);
		content = FileUtils.readFileToString(file.getLocation().toFile());
	}

	@After
	public void tearDown() throws Exception {
		waitForAutoBuild();
		deleteModelsProjects();
	}
	
	
	// DEACTIVATED due to DS-4813
	@Ignore
	@Test
	public void testRemoveAST_DS3643() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		copyResourceInModelsProject("domain/ds3643/DS3643Domain.domain");
		IOfsModelResource modelResource = getModelResource(getOfsProject(), "/ds3643/DS3643Domain.domain");
		assertNotNull(modelResource);
		
		// first we check that the resource does contain the parse result anymore
		AbstractDSLResource resource = (AbstractDSLResource) modelResource.getEMFModel().get(0).eResource();
		Assert.assertNull(resource.getParseResult());
	}
	
	@Test
	public void testPropertyOrder_DS3977() {
		Assert.assertTrue(content.indexOf("a_attribute") < content.indexOf("b_association"));
		Assert.assertTrue(content.indexOf("b_association") > content.indexOf("C_attribute"));
		Assert.assertTrue(content.indexOf("C_attribute") < content.indexOf("D_association"));
		Assert.assertTrue(content.indexOf("D_association") > content.indexOf("e_attribute"));
	}
	
	@Test
	public void testCommentHeader() {
		Assert.assertTrue(content.startsWith(AbstractDSLResource.DSL_FILE_HEADER_COMMENT));
	}
	
	@Test
	public void testReverseAssociation_DS4561() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		copyResourceInModelsProject("domain/ds4561/DS4561.domain");
		IOfsModelResource modelResource = getModelResource(getOfsProject(), "/ds4561/DS4561.domain");
		assertNotNull(modelResource);
		MdfDomain domain = (MdfDomain) modelResource.getEMFModel().get(0);
		((MdfDomainImpl)domain).setName("DS4561");
		Assert.assertNotNull(domain.getClass("DS4561ClassA").getProperty("reverse"));
	}
}
