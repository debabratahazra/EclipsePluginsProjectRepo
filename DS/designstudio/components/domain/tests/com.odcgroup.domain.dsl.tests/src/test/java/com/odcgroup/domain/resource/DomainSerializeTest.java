package com.odcgroup.domain.resource;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class DomainSerializeTest extends AbstractDSUnitTest {
	IFile file1;
	IFile file2;
	Resource resource1 = null;
	Resource resource2 = null;
	MdfClassImpl clazz = null;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file1 = getProject().getFile("domain/test/DomainXtextResourceTest.domain");

		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("DomainXtextResourceTest");
		domain.setMetamodelVersion(OfsCore.getVersionNumber());
		clazz = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		clazz.setName("NewClass");
		MdfAttributeImpl attr1 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr1.setName("a_attribute");
		attr1.setPrimaryKey(true);
		MdfAttributeImpl attr2 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr2.setName("C_attribute");
		attr2.setBusinessKey(true);
		attr2.setRequired(true);

		clazz.getProperties().addAll(Arrays.asList(new MdfProperty[] { attr1, attr2}));
		domain.getClasses().add(clazz);
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
		Injector injector = Guice.createInjector(new com.odcgroup.domain.DomainRuntimeModule());
		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resourceFactory);

		resourceSet.createOfsModelResource(file1, IOfsProject.SCOPE_PROJECT);
		resource1 = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file1), false);
		resource1.getContents().add(domain);

		waitForAutoBuild();
		resource1.save(null);
	}
	
	@After
	public void tearDown() throws Exception {
		waitForAutoBuild();
		deleteModelsProjects();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSerialization() throws Exception {
		file2 = getProject().getFile("domain/test/MyTestDomain.domain");
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("MyTestDomain");
		domain.setMetamodelVersion(OfsCore.getVersionNumber());
		MdfClassImpl clazz1 = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		clazz1.setName("NewClass");
		MdfAttributeImpl attr1 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr1.setName("a_attribute");
		attr1.setPrimaryKey(true);
		
		MdfAssociationImpl assoc1 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		assoc1.setName("TestAssociation");
		assoc1.setType(clazz);

		clazz1.getProperties().addAll(Arrays.asList(new MdfProperty[] { attr1, assoc1}));
		domain.getClasses().add(clazz1);
		
		ModelResourceSet resourceSet = getOfsProject().getModelResourceSet();
//		Injector injector = Guice.createInjector(new com.odcgroup.domain.DomainRuntimeModule());
//		XtextResourceFactory resourceFactory = injector.getInstance(XtextResourceFactory.class);
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resourceFactory);

		resourceSet.createOfsModelResource(file2, IOfsProject.SCOPE_PROJECT);
		resource2 = resourceSet.getResource(ModelURIConverter.createModelURI((IResource)file2), false);
		resource2.getContents().add(domain);

		
		waitForAutoBuild();
		resource2.save(null);
	}

}
