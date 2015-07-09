package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.domain.annotations.SQLAspectDS;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MdfAnnotationTests extends AbstractDSUnitTest {
	
	private static final String BUSINESS_TYPES="domain/aaa/entities/BusinessTypes.domain";
	private static final String ENUMERATIONS="domain/ds5879/AAAEnumerations.domain";
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(BUSINESS_TYPES, ENUMERATIONS);
	}
	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}
	@Test
	public void testDS5879MdfPropertyAnnotations() throws Exception{
		//copyResourceInModelsProject("domain/ds5879/bcventities.domain");
		copyResourceInModelsProject("domain/ds5879/BcvEntities(1).domain");
		waitForAutoBuild();
		MdfDomain model = (MdfDomain) getOfsProject().getOfsModelResource(URI.createURI("resource:///ds5879/BcvEntities(1).domain")).getEMFModel().get(0);
		MdfClass klass = model.getClass("PgProduct");
		//test business type attribute aaa-parm annotation
		MdfProperty idproperty = klass.getProperty("id");
		MdfEntity entity = idproperty.getType();
		Assert.assertTrue(entity instanceof MdfBusinessType);
		String bType = AAAAspectDS.getTripleABusinessType(((MdfBusinessType)entity));
		String parmType = AAAAspectDS.getAAAParamsType(idproperty);
        Assert.assertTrue(bType.equals(parmType));
        //test Sql annotation :@sql:SQLName(value = UDE tab Attribute SQL Name value)
        String attrSqlName = AAAAspectDS.getTripleAAttributeSQLName(idproperty);
        String sqlAnnotationValue = SQLAspectDS.getSqlName(idproperty);
        Assert.assertTrue(attrSqlName.equals(sqlAnnotationValue));
        //test enumeration type attribute aaa-parm annotation
        MdfProperty enumproperty = klass.getProperty("enumTypeAttribute");
		MdfEntity enumentity = enumproperty.getType();
		Assert.assertTrue(enumentity instanceof MdfEnumeration);
		String enumType = "enum_t" ; 
		String enumParmType = AAAAspectDS.getAAAParamsType(enumproperty);
        Assert.assertTrue(enumType.equals(enumParmType));
        
	}
	
}
