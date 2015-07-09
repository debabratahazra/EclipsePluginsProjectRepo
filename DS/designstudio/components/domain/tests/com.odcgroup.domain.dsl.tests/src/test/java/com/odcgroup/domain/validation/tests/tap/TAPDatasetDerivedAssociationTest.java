package com.odcgroup.domain.validation.tests.tap;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.domain.validation.tests.AbstractDomainXtextTest;

@RunWith(XtextRunner2.class)
@InjectWith(DomainInjectorProvider.class)
public class TAPDatasetDerivedAssociationTest extends AbstractDomainXtextTest {

	private static ClassLoader contextClassLoader;

    @BeforeClass
    public static void beforeTests() {
        final Thread currentThread = Thread.currentThread();
        contextClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(TAPDatasetDerivedAssociationTest.class.getClassLoader());
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
    
    public TAPDatasetDerivedAssociationTest() {
    	super("TAPDatasetDerivedAssociationTest");
    	// The test models must be in the same package as this class.
    }

	@Test
	public void testTypeOfDerivedAttribute() {
		testFile("DS5741/DS5741.domain"/*, "DS5741/DS5741B.domain"*/);
	}

}
