package com.odcgroup.t24.version.model.translation.tests;

import java.util.List;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.core.TranslationException;

@RunWith(XtextRunner.class)
@InjectWith(VersionWithDependencyInjectorProvider.class)
public class MultiValueFieldExistTest {
	
	private Field field;
	private Version version;
	private MdfAttribute attribute;
	private MdfClass application;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		application = MdfFactory.eINSTANCE.createMdfClass();
		attribute = MdfFactory.eINSTANCE.createMdfAttribute();
		((MdfAttributeImpl)attribute).setName("DESCRIPTION");
		application.getProperties().add(attribute);
		
		version = VersionDSLFactory.eINSTANCE.createVersion();
		version.setForApplication(application);
		field = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createField();
		field.setName("DESCRIPTION-1");
		version.getFields().add(field);
	}
	
	@Test
	public void testIsMultiValueFieldExist() throws TranslationException {
		List<MdfProperty> propertyList = VersionUtils.getAllProperties((MdfClass) version.getForApplication());
		boolean isFieldExistInClazz = com.odcgroup.t24.version.utils.VersionUtils.isFieldExistInClazz(propertyList, field);
		Assert.assertTrue(isFieldExistInClazz);
	}
	
}
