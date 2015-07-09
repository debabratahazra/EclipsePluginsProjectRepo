package com.odcgroup.domain.validation.tests.tap;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.domain.validation.tests.AbstractDomainXtextTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

@RunWith(XtextRunner2.class)
@InjectWith(DomainInjectorProvider.class)
public class TAPEnumMaskValidationTest extends AbstractDomainXtextTest {

	private static ClassLoader contextClassLoader;

    @BeforeClass
    public static void beforeTests() {
        final Thread currentThread = Thread.currentThread();
        contextClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(TAPEnumMaskValidationTest.class.getClassLoader());
    };

    @AfterClass
    public static void afterTests() {
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }
    
	@Before
    public void setUp() {
    	ignoreFormattingDifferences();
		suppressSerialization();
	}
    
    public TAPEnumMaskValidationTest() {
    	super("TAPEnumMaskValidationTest");
    	// The test models must be in the same package as this class.
    }

	@Test
	public void testClassAttributeInvalidMultiplicityForEnumMask() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		assertConstraints(
			testFile("ClassAttributeInvalidMultiplicityForEnumMask.domain")
				.sizeIs(1)
				.oneOfThemContains("The multiplicity must be Many when the underlying business type is EnumMask"));
	}

	@Test
	public void testEnumerationInvalidValueForEnumMask() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		assertConstraints(
			testFile("EnumerationInvalidValueForEnumMask.domain")
				.sizeIs(1)
				.oneOfThemContains("Negative value is not valid"));
	}
}
