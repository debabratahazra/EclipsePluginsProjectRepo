package com.odcgroup.domain.resource;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class DomainValueConverterServiceSavingTest extends AbstractDSUnitTest {

	IFile file;
	private String content;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		file = getProject().getFile("domain/test/MyTestDomain.domain");
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactoryImpl.eINSTANCE.createMdfDomain();
		domain.setDocumentation("  ");
		domain.setMetamodelVersion("1.2.3");
		domain.setName("Enumerations");
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
		attr.setDefault("\u0026/%.-\u00E8\u00E9\u00E0\u00E4\u00F6\u00FC\u00C4\u00D6\u00DC\u00C9\u00C8\u00C0\u00E7\u00DF");

		MdfAttributeImpl attr2 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr2.setName("attrname2");
		attr2.setType(PrimitivesDomain.DOMAIN.getPrimitive("string"));
		attr2.setDefault("abc*def");

		MdfAttributeImpl attr3 = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attr3.setName("attr3");
		attr3.setType(PrimitivesDomain.DOMAIN.getPrimitive("string"));
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
		content = FileUtils.readFileToString(file.getLocation().toFile(), "UTF-8");
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testDocumentationRemoval() throws IOException {
		Assert.assertFalse(content, content.contains("/*"));
	}

	@Test
	public void testValidCharsInID() {
		Assert.assertTrue(content, content.contains("attrname1._- "));
		Assert.assertFalse(content, content.contains("\"attrname1._-\""));
	}

	//DS-5635-Fixed
	@Test
	public void testValidCharsInValue() {
		Assert.assertTrue(content, content.contains("default=\u0026/%.-\u00E8\u00E9\u00E0\u00E4\u00F6\u00FC\u00C4\u00D6\u00DC\u00C9\u00C8\u00C0\u00E7\u00DF"));
	}

	@Test
	public void testSpecialCharsInValue() {
		Assert.assertTrue(content, content.contains("default=\"abc*def\""));
	}
	
	@Test
	public void testKeywordInValue() {
		Assert.assertTrue(content, content.contains("default=\"PK\""));
	}
	
	@Test
	public void testNamespaceReplacement() {
		Assert.assertTrue(content, content.contains("@i18n:"));
	}

	@Test
	public void testUnkownNamespace_DS4003() {
		Assert.assertTrue(content, content.contains("@\"http://unknown/namespace\""));
	}
	
	@Test
	public void testEscapingEnumerationsKeywordAsDomainName_DS4624() {
		Assert.assertTrue(content, content.contains("Domain ^Enumerations"));
	}
}
