package com.odcgroup.aaa.connector.mdfmml;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.internal.util.AAAWidgetRulesUtil;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;

@Ignore
public class AAAWidgetRulesCheckerTest {

	DataType2Primitives dataType2Primitives;
	AAAWidgetRulesUtil ru;
	MetaDictDomains metaDictDomains;
	DictAttribute dictAttribute;
	MdfPropertyImpl aaaAttribute;
	DictDataType dictDataType;
	MdfBusinessType mdfBusinessType;
	MdfAnnotation mdfAnnotation;
	MdfAnnotation mdfAnnotationImpl;
	MdfAnnotationProperty mdfAnnotationProperty;
	MdfAnnotationProperty mdfAnnotationProperty2;
	
	@Before
	public void setUp() throws Exception {
		dataType2Primitives = mock(DataType2Primitives.class);
		ru = new AAAWidgetRulesUtil(dataType2Primitives);
		metaDictDomains = mock(MetaDictDomains.class);
		dictAttribute = mock(DictAttribute.class);
		aaaAttribute = mock(MdfPropertyImpl.class);
		dictDataType = mock(DictDataType.class);
		mdfBusinessType = mock(MdfBusinessType.class);
		mdfAnnotation = mock(MdfAnnotation.class);
		mdfAnnotationImpl = mock(MdfAnnotationImpl.class);
		mdfAnnotationProperty = mock(MdfAnnotationProperty.class);
		mdfAnnotationProperty2 = mock(MdfAnnotationProperty.class);
	}
	
	@Test
	public void testLengthShouldChange() {
		when(aaaAttribute.getAnnotation(anyString(), anyString())).thenReturn(mdfAnnotationImpl);
		//mdfAnnotation.addProperty(any(MdfAnnotationProperty.class));
		when(dictAttribute.getMaxDbLen()).thenReturn(20);
		when(dictAttribute.getDefaultDisplayLen()).thenReturn(20);
		when(mdfAnnotationProperty.getValue()).thenReturn("code_t");
		when(mdfAnnotationProperty2.getValue()).thenReturn("60");
		when(mdfAnnotation.getProperty("BType")).thenReturn(mdfAnnotationProperty);
		when(mdfAnnotation.getProperty("maxLength")).thenReturn(mdfAnnotationProperty2);
		when(dataType2Primitives.getBusinessType(dictDataType, metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		when(mdfBusinessType.getAnnotation(anyString(),anyString())).thenReturn(mdfAnnotation);
		//when(dictDataType.getBusinessType(metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		when(dictAttribute.getDatatype()).thenReturn(dictDataType);
		//when(dictAttribute.getBusinessType(metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		ru.checkLongCodeForNameAndCodeBusinessTypes(metaDictDomains.businessTypesDomain, dictAttribute, aaaAttribute);
		verify(mdfAnnotationImpl, times(2)).getProperties();
	}

	@Test
	public void testDbLengthIsZero() throws Exception {
		when(aaaAttribute.getAnnotation(anyString(), anyString())).thenReturn(mdfAnnotationImpl);
		//mdfAnnotation.addProperty(any(MdfAnnotationProperty.class));
		when(dictAttribute.getMaxDbLen()).thenReturn(0);
		when(dictAttribute.getDefaultDisplayLen()).thenReturn(0);
		when(mdfAnnotationProperty.getValue()).thenReturn("code_t");
		when(mdfAnnotationProperty2.getValue()).thenReturn("60");
		when(mdfAnnotation.getProperty("BType")).thenReturn(mdfAnnotationProperty);
		when(mdfAnnotation.getProperty("maxLength")).thenReturn(mdfAnnotationProperty2);
		when(mdfBusinessType.getAnnotation(anyString(),anyString())).thenReturn(mdfAnnotation);
		when(dataType2Primitives.getBusinessType(dictDataType, metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		//when(dictAttribute.getBusinessType(metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		when(dictAttribute.getDatatype()).thenReturn(dictDataType);
	
		ru.checkLongCodeForNameAndCodeBusinessTypes(metaDictDomains.businessTypesDomain, dictAttribute, aaaAttribute);
		verify(mdfAnnotationImpl, times(0)).getProperties();
	}
	
	@Test
	public void testDisplayLengthIsGreaterThanMaxDBlength() throws Exception {
		when(aaaAttribute.getAnnotation(anyString(), anyString())).thenReturn(mdfAnnotationImpl);
		//mdfAnnotation.addProperty(any(MdfAnnotationProperty.class));
		when(dictAttribute.getMaxDbLen()).thenReturn(20);
		when(dictAttribute.getDefaultDisplayLen()).thenReturn(60);
		when(mdfAnnotationProperty.getValue()).thenReturn("code_t");
		when(mdfAnnotationProperty2.getValue()).thenReturn("60");
		when(mdfAnnotation.getProperty("BType")).thenReturn(mdfAnnotationProperty);
		when(mdfAnnotation.getProperty("maxLength")).thenReturn(mdfAnnotationProperty2);
		when(mdfBusinessType.getAnnotation(anyString(),anyString())).thenReturn(mdfAnnotation);
		when(dataType2Primitives.getBusinessType(dictDataType, metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		//when(dictAttribute.getBusinessType(metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		when(dictAttribute.getDatatype()).thenReturn(dictDataType);
	
		ru.checkLongCodeForNameAndCodeBusinessTypes(metaDictDomains.businessTypesDomain, dictAttribute, aaaAttribute);
		verify(mdfAnnotationImpl, times(0)).getProperties();
	}
	
	@Test
	public void testDifferentBuisnessTypeWillNotRun() throws Exception {
		when(aaaAttribute.getAnnotation(anyString(), anyString())).thenReturn(mdfAnnotationImpl);
		//mdfAnnotation.addProperty(any(MdfAnnotationProperty.class));
		when(dictAttribute.getMaxDbLen()).thenReturn(20);
		when(dictAttribute.getDefaultDisplayLen()).thenReturn(60);
		when(mdfAnnotationProperty.getValue()).thenReturn("false_t");
		when(mdfAnnotationProperty2.getValue()).thenReturn("60");
		when(mdfAnnotation.getProperty("BType")).thenReturn(mdfAnnotationProperty);
		when(mdfAnnotation.getProperty("maxLength")).thenReturn(mdfAnnotationProperty2);
		when(mdfBusinessType.getAnnotation(anyString(),anyString())).thenReturn(mdfAnnotation);
		when(dataType2Primitives.getBusinessType(dictDataType, metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		//when(dictAttribute.getBusinessType(metaDictDomains.businessTypesDomain)).thenReturn(mdfBusinessType);
		when(dictAttribute.getDatatype()).thenReturn(dictDataType);
	
		ru.checkLongCodeForNameAndCodeBusinessTypes(metaDictDomains.businessTypesDomain, dictAttribute, aaaAttribute);
		verify(mdfAnnotationImpl, times(0));
	}
}
