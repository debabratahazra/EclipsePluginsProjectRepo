package com.odcgroup.aaa.ui.internal.page.validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.testframework.headless.TestFrameworkHeadlessCore;

/**
 * Tests Validation of UDE domain models.
 * 
 * @author phanikumark initial version (extends AbstractDSUIUnitTest, etc.)
 * @author Michael Vorburger rewritten as standard Xtext test
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class UDEModelValidationTest {
	
	private static String DOMAIN_MODEL = "domain/ds5195/DS5195.domain";
	private static String DEP_DOMAIN_MODEL = "domain/general/BusinessTypes.domain";
	
	@Inject Provider<XtextResourceSet> rsProvider;
	
	@Test
	public void testDS5195UDEValidations() throws Exception {
		XtextResourceSet rs = rsProvider.get();
		
		// We must do this so that Xtext EMF cross reference resolution from the next DS5195.domain to BTs works:
		Resource btRes = getTestTankResource(rs, DEP_DOMAIN_MODEL);
			MdfDomain btMdfDomain = (MdfDomain) btRes.getContents().get(0);
			Assert.assertEquals("AAABusinessTypes", btMdfDomain.getName());
			
		Resource resource = getTestTankResource(rs, DOMAIN_MODEL);
			MdfDomain domain = (MdfDomain) resource.getContents().get(0);
			Assert.assertNotNull("Domain Model not found!", domain);
			MdfClass klass = domain.getClass("NewClassTest");
			Assert.assertNotNull("Specified MDF Class not found in domain", klass);
			
			IStatus status = new UDEModelValidatorFactory().validateEntityNamePrefix(klass, klass);
			Assert.assertFalse("Shouldn't be valid", status.isOK());
			Assert.assertEquals("Entity SQL Name must always start with 'udt_'", status.getMessage());
			
			MdfProperty property = klass.getProperty("EnumTest");
			Assert.assertNotNull("Specified MDF Property not found in class", property);
			
			status = new UDEModelValidatorFactory().validateAttributeBusinessType(property, null);
			Assert.assertFalse("Shouldn't be valid", status.isOK());
			Assert.assertEquals("No attribute of UDE class can have business type Enum or Flag", status.getMessage());
	}

	private Resource getTestTankResource(XtextResourceSet rs, String resourceLocationInTestTank) throws MalformedURLException, URISyntaxException {
		URI uri = getTestTankURI(resourceLocationInTestTank);
		return rs.getResource(uri , true);
	}

	// TODO move me elsewhere...
	// TODO integrate this with my com.odcgroup.workbench.dsl.xml.EIO helper - which offers convenience, better error reporting, incl. validation
	protected static final String RESOURCES_FOLDER = "resources/test-tank-models"; // copy/paste from AbstractDSUIUnitTest.. TODO refactor?
	private URI getTestTankURI(String resourceLocationInTestTank) throws MalformedURLException, URISyntaxException {
		URL fullURL;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			URL baseURL = FileLocator.find(TestFrameworkHeadlessCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER), null);
			String fullURLAsString = baseURL.toExternalForm() + resourceLocationInTestTank;
			fullURL = new URL(fullURLAsString);
		} else {
			fullURL = getClass().getResource("/resources/test-tank-models/" + resourceLocationInTestTank);
		}
		if (fullURL == null)
			throw new IllegalArgumentException("Did not find: " + resourceLocationInTestTank);
		java.net.URI jdkURI = fullURL.toURI();
		URI uri = URI.createURI(jdkURI.toString());
		return uri;
	}
	// TODO offer another similar method to load models from classpath of current project?
}
