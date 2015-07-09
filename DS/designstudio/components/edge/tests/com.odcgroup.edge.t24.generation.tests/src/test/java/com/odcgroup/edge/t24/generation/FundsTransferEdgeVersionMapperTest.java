package com.odcgroup.edge.t24.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Test for EdgeMapper.
 * 
 * This test can not be run as a non-OSGi std JUnit, you must Run As > Plug-in Test.
 * 
 * @author Michael Vorburger, for Simon File.
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
public class FundsTransferEdgeVersionMapperTest extends AbstractEdgeGeneratorTest {

	@Override
	protected String[] getModelsNeededForTests() {
		return new String[] { "/domain/FT/FT_Contract.domain", "/version/edgepoc/FUNDS_TRANSFER,EDGE.version" };
	}
	
	@Ignore
	@Test
	public void testEdgeVersionMapper() throws Exception {
		Version version = getVersion("/version/edgepoc/FUNDS_TRANSFER,EDGE.version");
		MdfClass application = version.getForApplication();
 		assertFalse( ((EObject) application).eIsProxy());
		assertEquals("FUNDS_TRANSFER", application.getName());
		
		
		MdfProperty property = application.getProperty("TRANSACTION_TYPE");
		MdfAttribute attribute = (MdfAttribute) property;
		String t24Name = T24Aspect.getT24Name(attribute);
		assertEquals("TRANSACTION.TYPE", t24Name);
		
        //TemplateProject basicVersionTemplate = BasicVersionScreenIn3D.getDefaultBasicVersionTemplate();

        // Create the mapper and generate the version
        //
        //EdgeMapper<Version> mapper = new VersionMapper( "/tmp" /* p_componentsOutputLocation */, basicVersionTemplate );
        
        //ComponentMap componentMap = new ComponentMap();
        
        //String projectFileName = mapper.map( version, componentMap );

        // Save it
        //
        //mapper.saveProject( projectFileName );
        
        //File outputMap = new File("/tmp/HrefComponentMap.properties");
        
        //componentMap.store(new FileOutputStream(outputMap), "Dynamic Component Mapping File");

        //assertTrue( "HRef component properties map does not exist as expected " + outputMap.getPath(), outputMap.exists() );
        
        // Make sure there were no errors
        // TODO assertFalse( GenLogger.hasErrors() );
	}

}
