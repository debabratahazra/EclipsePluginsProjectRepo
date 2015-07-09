package com.odcgroup.edge.t24.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Test for EdgeMapper.
 * 
 * This test can not be run as a non-OSGi std JUnit, you must Run As > Plug-in Test.
 * 
 * @see com.odcgroup.edge.t24.generation.tests.ESONResourceLoadingProblemTest
 * 
 * @author Michael Vorburger, for Simon File.
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
public class TellerLcyCashinDenomFieldTest extends AbstractEdgeGeneratorTest {

	@Override
	protected String[] getModelsNeededForTests() {
		return new String[] { "/domain/TT/TT_Contract.domain", "/version/edgepoc/TELLER,LCY_CASHIN_DENOM.version" };
	}
	
	@Ignore
	@Test
	public void testEdgeVersionMapper() throws Exception {
	    Version version = getVersion("/version/edgepoc/TELLER,LCY_CASHIN_DENOM.version");
	    MdfClass application = version.getForApplication();
	    assertFalse(((EObject) application).eIsProxy());
		assertEquals("TELLER", application.getName());
		MdfProperty appProperty = application.getProperty("TRANSACTION_NUMBER");
		assertNotNull( "application property for TRANSACTION_NUMBER is null", appProperty );
		assertFalse( "application property for TRANSACTION_NUMBER is a EMF proxy", ((EObject)appProperty).eIsProxy() );
		
		// This doesn't work like this, because it doesn't handle multi value fields, which are only inside the __ sub value classes in domain..
//		EList<Field> fields = version.getFields();
//		for (Field field : fields) {
//		    if  ( ! "*".equals( field.getName() )) {
//	            MdfProperty appProperty = version.getForApplication().getProperty(field.getName());
//		        assertNotNull( "Could not get application property for: " + field.getName(), appProperty );
//		    }
//        }
	}

}
