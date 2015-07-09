package com.odcgroup.domain.resource;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class SyntaxTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/ds4241/DS4241.domain");
		copyResourceInModelsProject("domain/general/Domain.domain");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testNewline_DS4241() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///ds4241/DS4241.domain"));
		MdfDomain domain = (MdfDomain) modelResource.getEMFModel().get(0);
		Assert.assertTrue(domain.getClasses().size() > 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveOnIncompleteBusinessType_DS4240() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///general/Domain.domain"));
		MdfDomainImpl domain = (MdfDomainImpl) modelResource.getEMFModel().get(0);
		MdfBusinessTypeImpl bt = (MdfBusinessTypeImpl) MdfFactory.eINSTANCE.createMdfBusinessType();
		bt.setName("BusinessTypeWithoutBaseType");
		domain.getBusinessTypes().add(bt);
		try {
			domain.eResource().save(null);
		} catch(Exception e) {
			Assert.fail("Failed serializing incomplete business type: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveOnSpecialCharactersInName_DS4836() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///general/Domain.domain"));
		MdfDomainImpl domain = (MdfDomainImpl) modelResource.getEMFModel().get(0);
		MdfClassImpl c = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
		c.setName("Invalid_$%&_Name");
		domain.getClasses().add(c);
		try {
			domain.eResource().save(null);
		} catch(Exception e) {
			Assert.fail("Failed serializing special characters in class name: " + e.getMessage());
		}
	}
}
