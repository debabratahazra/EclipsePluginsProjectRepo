package com.odcgroup.domain.validation.tests;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;

@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class DomainDslValidationTest extends AbstractDomainXtextTest {

	private static ClassLoader contextClassLoader;

    @BeforeClass
    public static void beforeTests() {
        final Thread currentThread = Thread.currentThread();
        contextClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(DomainDslValidationTest.class.getClassLoader());
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
    
    public DomainDslValidationTest() {
    	super("DomainDslValidationTest");
    	// The test models must be in the same package as this class.
    }
    
	@Test
	public void testDomainWithoutName() {
		try {
			testFile("DomainWithoutName.domain");
		} catch (Error ex) {
			String msg = ex.getMessage();
			Assert.assertTrue("Domain name is required", msg.contains("expecting RULE_ID"));
		}
	}

	@Test
	public void testDomainWithoutNamespace() {
		try {
			testFile("DomainWithoutNamespace.domain");
		} catch (Error ex) {
			String msg = ex.getMessage();
			Assert.assertTrue("Domain namespace is required", msg.contains("expecting 'namespace'"));
		}
	}

	@Test
	public void testDomainWithoutPackage() {
		assertConstraints(
			testFile("DomainWithoutPackage.domain")
				.sizeIs(1)
				.theOneAndOnlyContains("The package is required"));
	}

	@Ignore  // does not work on jenkins, no idea why
	@Test
	public void testDomainWithInvalidName() {
		assertConstraints(
			testFile("DomainWithInvalidName.domain")
				.sizeIs(2)
				.oneOfThemContains("'package' is a reserved Java keyword")
				.oneOfThemContains("'package' is not a valid name"));
	}

	@Test
	public void testDomainDuplicateDomain() {
		assertConstraints(
				testFile("DomainB.domain", "DomainA.domain")
				.sizeIs(1)
				.oneOfThemContains("A domain with the same name already exists"));
	}

	@Test
	public void testDomainWithDuplicateClasses() {
		assertConstraints(
			testFile("DomainWithDuplicateClasses.domain")
				.sizeIs(2)
				.oneOfThemContains("Duplicate MdfModelElement 'CLASS_A'"));
	}

	@Test 
	public void testDomainWithDuplicateClassAttributes() {
		assertConstraints(
			testFile("DomainWithDuplicateClassAttributes.domain")
				.sizeIs(2)
				.oneOfThemContains("Duplicate attribute attribute_A, please specify another name"));
	}

	@Test
	public void testDomainWithDuplicateEnumerations() {
		assertConstraints(
			testFile("DomainWithDuplicateEnumerations.domain")
				.sizeIs(2)
				.oneOfThemContains("Duplicate MdfModelElement 'Enumeration_A'"));
	}

	@Test 
	public void testDomainWithDuplicateEnumerationLiterals() {
		assertConstraints(
			testFile("DomainWithDuplicateEnumerationLiterals.domain")
				.sizeIs(2)
				.oneOfThemContains("Duplicate Enumerated value Value_1, please specify another name"));
		
	}
	
	@Test
	public void testDomainWithDuplicateEnumerationValues() {
		assertConstraints(
			testFile("DomainWithDuplicateEnumerationValues.domain")
				.sizeIs(2)
				.oneOfThemContains("The value true already exists in this enumeration, please specify another value"));
	}

	@Test
	public void testDomainWithDuplicateDatasets() {
		assertConstraints(
			testFile("DomainWithDuplicateDatasets.domain")
				.sizeIs(4)
				.oneOfThemContains("Duplicate MdfModelElement 'Dataset_A' in MdfDomain 'MyDomain'")
				.oneOfThemContains("Duplicate MdfModelElement 'Dataset_B' in MdfDomain 'MyDomain'"));
	}

	@Test //@Ignore
	public void testDomainWithDuplicateDatasetProperties() {
		assertConstraints(
			testFile("DomainWithDuplicateDatasetProperties.domain")
				.sizeIs(2)
				.oneOfThemContains("Duplicate dataset property property_A, please specify another name"));
	}

	@Test
	public void testDomainClassWithDuplicatePrimaryKeys() {
		assertConstraints(
			testFile("DomainClassWithDuplicatePrimaryKeys.domain")
				.sizeIs(1)
				.oneOfThemContains("The containing class already has a primary-key"));
	}
	
}


