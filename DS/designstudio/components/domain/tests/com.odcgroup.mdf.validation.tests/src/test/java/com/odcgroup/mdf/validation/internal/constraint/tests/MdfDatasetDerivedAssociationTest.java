package com.odcgroup.mdf.validation.internal.constraint.tests;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.MdfModelValidator;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MdfDatasetDerivedAssociationTest extends AbstractDSUnitTest {

	private MdfModelValidator validator = null;
	private static final String BUSINESS_TYPES="domain/aaa/entities/BusinessTypes.domain";
	private static final String DS5741_MODEL="domain/ds5741/DS5741.domain";
	private static final String DS5741B_MODEL="domain/ds5741/DS5741B.domain";
	
	@Before
	public void setUp() throws Exception {
		validator = new MdfModelValidator();
		createModelsProject();
		copyResourceInModelsProject(BUSINESS_TYPES, DS5741_MODEL, DS5741B_MODEL);	
		waitForAutoBuild();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testTypeOfDerivedAttribute() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		
		MdfDomain domain = DomainRepositoryUtil.getMdfDomain(getResource(DS5741_MODEL), QualifiedName.create("DS5741"));
		MdfDataset dataset = domain.getDataset("DatasetA");
		
		IStatus status = validator.validate((MdfModelElement)dataset.getProperty("refB"));
	    Assert.assertTrue(status.isOK());
	    
		status = validator.validate((MdfModelElement)dataset.getProperty("refC"));
	    Assert.assertTrue(status.isOK());
	}

}
