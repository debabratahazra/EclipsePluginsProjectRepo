package com.odcgroup.domain.validation.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.io.Resources;
import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.mdf.ecore.MdfDomainImpl;

/**
 * DomainDslValidationTest, new style, gangnam style.
 * 
 * @see DomainDslValidationTest
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class NewDomainDslValidationTest {
	
	@Inject ResourceSet resourceSet;
	@Inject ParseHelper<? extends EObject> parseHelper;
	@Inject ValidationTestHelper validationTestHelper; 
	
	@Test
	public void testEntitiesDomainReverseAssociationValidation_DS_7792() throws Exception {
		parse(NewDomainDslValidationTest.class, "/com/odcgroup/domain/validation/tests/tap/ds7577/BusinessTypes.domain");
		parse(NewDomainDslValidationTest.class, "/com/odcgroup/domain/validation/tests/tap/ds7577/Enumerations.domain");
		MdfDomainImpl model = parse(NewDomainDslValidationTest.class, "/com/odcgroup/domain/validation/tests/tap/ds7577/Entities.domain");
		validationTestHelper.assertNoIssues(model);
	}

	/**
	 * Parse utilitity.
	 * 
	 * @author Michael Vorburger
	 * 
	 * @param resourceName name of resource on classpath
	 * @param contextClass Test class
	 * @return loaded Resource
	 */
	// TODO I'm 100% sure having already written this somewhere else before.. unify?
	@SuppressWarnings("unchecked")
	private <T extends EObject> T parse(Class<?> contextClass, String resourceName) throws IOException {
		URL url = Resources.getResource(contextClass, resourceName);
		URI uriToUse = URI.createURI(resourceName);
		InputStream in = Resources.newInputStreamSupplier(url).getInput();
		return (T) parseHelper.parse(in, uriToUse, null, resourceSet);
	}
}
