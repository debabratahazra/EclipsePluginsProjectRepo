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
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.MdfModelValidator;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MdfModelValidatorTest extends AbstractDSUnitTest {

	private MdfModelValidator validator = new MdfModelValidator();
	private static final String DOMAIN_MODEL1="domain/ds4798/DS4798Duplic.domain";
	private static final String DOMAIN_MODEL2="domain/ds4798/DS4798.domain";
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL1, DOMAIN_MODEL2);	
		waitForAutoBuild();		
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testcheckDuplicateDomain() throws CoreException,
	ModelNotFoundException, IOException,
	InvalidMetamodelVersionException {
		Resource rs = getResource(DOMAIN_MODEL2);
		QualifiedName name = QualifiedName.create("DS4798");
		//check for the duplicate domain name
		IStatus status = validateModel(DomainRepositoryUtil.getMdfDomain(rs, name));
		String expString = "A domain with this name already exists";
	    Assert.assertEquals(expString, status.getMessage());
	}

	/**
	 * @return the validation status
	 */
	protected IStatus validateModel(Object obj) {
		IStatus status = null;
		if (obj instanceof MdfDomain) {
			status = validator.validate((MdfModelElement)obj);
		}
		return status;
	}
	
}
