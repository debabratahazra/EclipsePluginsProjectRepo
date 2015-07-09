package com.odcgroup.mdf.validation.internal.constraint.tests;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.MdfModelValidator;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MdfEnumMaskTest extends AbstractDSUnitTest {

	private MdfModelValidator validator = null;
	private static final String BUSINESS_TYPES="domain/aaa/entities/BusinessTypes.domain";
	private static final String MODEL="domain/ds4858/DS4858.domain";
	
	@Before
	public void setUp() throws Exception {
		validator = new MdfModelValidator();
		createModelsProject();
		copyResourceInModelsProject(BUSINESS_TYPES, MODEL);
		waitForAutoBuild();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testEnumMaskExists() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		MdfDomain domain = getDomain("AAABusinessTypes", getResource(BUSINESS_TYPES));
		MdfBusinessType bt = domain.getBusinessType("EnumMask");
		Assert.assertTrue("BusinessType EnumMask does not exist", bt != null);
		
	}

	@Test
	public void testEnumMaskAttributeWithMultiplicityOne() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {		
		MdfDomain domain = getDomain("DS4858", getResource(MODEL));
		MdfClass cls = domain.getClass("Class4858");
		
		IStatus status = validator.validate((MdfModelElement)cls.getProperty("atr"));
		String expString = "The multiplicity must be Many when the underlying business type is EnumMask.";
	    Assert.assertEquals(expString, status.getMessage());
	    
	}

	@Test
	public void testEnumMaskEnumerationWithNegativeValid() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		MdfDomain domain = getDomain("DS4858", getResource(MODEL));
		
	    MdfEnumeration enumeration = domain.getEnumeration("Enum4858");
	    IStatus status = validator.validate((MdfModelElement)enumeration.getValue("Value1"));
	    String expString = "Negative value is not valid.";
	    Assert.assertEquals(expString, status.getMessage());
	    
	}
	
	public MdfDomain getDomain(String name, Resource resource) {
		QualifiedName qname = QualifiedName.create(name);
		return DomainRepositoryUtil.getMdfDomain(resource, qname);
	}
}
