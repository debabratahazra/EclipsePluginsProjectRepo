package com.odcgroup.domain.resource;

import static org.junit.Assert.assertNotNull;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfFactoryImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class DomainValueConverterServiceLoadingTest extends AbstractDSUnitTest {

	IFile file;
	private MdfDomain domainModel;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("domain/test/MyTestDomain.domain");
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactoryImpl.eINSTANCE.createMdfDomain();
		domain.setDocumentation("  ");
		domain.setMetamodelVersion("1.2.3");
		domain.setName("MyTestDomain");
		domain.setNamespace("http://odcgroup.com/test");
		
		MdfAnnotationImpl annotation = (MdfAnnotationImpl) MdfFactoryImpl.eINSTANCE.createMdfAnnotation();
		annotation.setName("myAnnotation");
		annotation.setNamespace("http://www.odcgroup.com/mdf/translation");

		MdfAnnotationImpl annotation2 = (MdfAnnotationImpl) MdfFactoryImpl.eINSTANCE.createMdfAnnotation();
		annotation2.setName("myAnnotation2");
		annotation2.setNamespace("http://unknown/namespace");

		domain.getAnnotations().add(annotation);
		domain.getAnnotations().add(annotation2);
		
		MdfAttributeImpl attr = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr.setName("attrname1._-");
		attr.setType(PrimitivesDomain.DOMAIN.getPrimitive("string"));
		attr.setDefault("&/%.-��������������");

		MdfAttributeImpl attr2 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr2.setName("attrname2");
		attr2.setType(PrimitivesDomain.DOMAIN.getPrimitive("string"));
		attr2.setDefault("abc*def");

		MdfAttributeImpl attr3 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr3.setName("attr3");
		attr3.setType(PrimitivesDomain.STRING);
		attr3.setDefault("PK");

		MdfClassImpl clazz = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
		clazz.setName("MyTestClass");
		clazz.setAbstract(true);
		clazz.getProperties().add(attr);
		clazz.getProperties().add(attr2);
		clazz.getProperties().add(attr3);
		
		domain.getClasses().add(clazz);
		
		getOfsProject().getModelResourceSet().createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
		Resource resource = getOfsProject().getModelResourceSet().getResource(ModelURIConverter.createModelURI((IResource)file), false);
		resource.getContents().add(domain);
		resource.save(null);
		resource.unload();
		IOfsModelResource mres = getModelResource(getOfsProject(), "/test/MyTestDomain.domain");
		assertNotNull(mres);
		domainModel = (MdfDomain) mres.getEMFModel().get(0);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testUnkownNamespace_DS4003() {
		Assert.assertNotNull(domainModel.getAnnotation("http://unknown/namespace", "myAnnotation2"));
	}
}
