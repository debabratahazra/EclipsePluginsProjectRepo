package com.odcgroup.aaa.connector.mdfmml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeCalculated;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictRepository;
import com.odcgroup.aaa.connector.internal.metadictreader.TAVersion;
import com.odcgroup.aaa.connector.util.BusinessTypesUtil;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;

/**
 * Unit Tests for MetaDict2Mml without using DB & JPA at all.
 * 
 * @author Michael Vorburger
 */
@SuppressWarnings("unchecked")
public class MetaDict2MmlNoDBTest {

	@Test
	public void testLogicalWithUDProblem_SUPPORT3997() throws Exception {
		MetaDict2Mml metaDict2Mml = new MetaDict2Mml();

		MdfClassImpl klass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
		klass.setName("ThirdParty");
		
		MdfClassImpl referencedClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
		referencedClass.setName("Address");
		MdfAssociationImpl backRef1 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		referencedClass.getProperties().add(backRef1);
		backRef1.setName("backRef1");
		backRef1.setType(klass);

		MdfAssociationImpl backRef2 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		backRef2.setName("backRef2");
		referencedClass.getProperties().add(backRef2);
		backRef2.setType(klass);
		metaDict2Mml.doAnnotate(backRef2, MetaDict2Mml.CUSTOM_FIELD_NAMESPACE, MetaDict2Mml.CUSTOM_FIELD_ANNOTATION_NAME);
		
		DictEntity mainDictEntity = new DictEntity();
		mainDictEntity.setSQLName("third_party");
		
		DictEntity referencedDictEntity = new DictEntity();
		metaDict2Mml.entity2class.put(referencedDictEntity, referencedClass);
		DictAttribute logicalDictAttribute = new DictAttribute();
		logicalDictAttribute.setDictEntity(mainDictEntity);
		logicalDictAttribute.setReferencedDictEntity(referencedDictEntity);
		logicalDictAttribute.setName("address");
		logicalDictAttribute.setSQLName("address");
		DictDataType datatype = new DictDataType();
		datatype.setName("id_t");
		logicalDictAttribute.setDatatype(datatype);
		logicalDictAttribute.setCalculated(DictAttributeCalculated.PHYSICAL);
		
		metaDict2Mml.addLogicalDictAttribute(klass, logicalDictAttribute);
	}
	
	@Test
	public void testDS5826SortingPropertiesAndAnnotations() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// Given
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("NewDomain");
		MdfClassImpl mdfClassA = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		mdfClassA.setName("ClassA");
		MdfClassImpl mdfClassB = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		mdfClassB.setName("ClassB");
		
		domain.getClasses().add(mdfClassB);
		domain.getClasses().add(mdfClassA);
		
		Assert.assertEquals("precondition failed: the order of classes is wrong", mdfClassA, domain.getClasses().get(1));
		Assert.assertEquals("precondition failed: the order of classes is wrong", mdfClassB, domain.getClasses().get(0));
		
		MdfAttributeImpl attributeA = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
		attributeA.setName("attributeA");
		MdfAttributeImpl attributeB = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
		attributeB.setName("attributeB");
		
		mdfClassA.getProperties().add(attributeB);
		mdfClassA.getProperties().add(attributeA);
		
		Assert.assertEquals("precondition failed: the order of attributes is wrong", attributeA, mdfClassA.getProperties().get(1));
		Assert.assertEquals("precondition failed: the order of attributes is wrong", attributeB, mdfClassA.getProperties().get(0));

		MdfAnnotationImpl annotationNamespaceANameA = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
		annotationNamespaceANameA.setNamespace("namespaceA");
		annotationNamespaceANameA.setName("nameA");
		
		MdfAnnotationImpl annotationNamespaceANameB = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
		annotationNamespaceANameB.setNamespace("namespaceA");
		annotationNamespaceANameB.setName("nameB");
		
		MdfAnnotationImpl annotationNamespaceBNameC = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
		annotationNamespaceBNameC.setNamespace("namespaceB");
		annotationNamespaceBNameC.setName("nameC");
		
		attributeB.getAnnotations().add(annotationNamespaceBNameC);
		attributeB.getAnnotations().add(annotationNamespaceANameB);
		attributeB.getAnnotations().add(annotationNamespaceANameA);

		Assert.assertEquals("precondition failed: the order of annotations is wrong", annotationNamespaceBNameC, attributeB.getAnnotations().get(0));
		Assert.assertEquals("precondition failed: the order of annotations is wrong", annotationNamespaceANameB, attributeB.getAnnotations().get(1));
		Assert.assertEquals("precondition failed: the order of annotations is wrong", annotationNamespaceANameA, attributeB.getAnnotations().get(2));

		MetaDict2Mml metaDict2Mml = new MetaDict2Mml();
		Method methodToTest = MetaDict2Mml.class.getDeclaredMethod("sort", MdfDomainImpl.class);
		methodToTest.setAccessible(true);
		
		// When
		methodToTest.invoke(metaDict2Mml, domain);
		
		// Then
		Assert.assertEquals("The classes are not sorted properlty", mdfClassA, domain.getClasses().get(0));
		Assert.assertEquals("The classes are not sorted properlty", mdfClassB, domain.getClasses().get(1));
		
		Assert.assertEquals("The order of attributes is wrong", attributeA, mdfClassA.getProperties().get(0));
		Assert.assertEquals("The order of attributes is wrong", attributeB, mdfClassA.getProperties().get(1));
		
		Assert.assertEquals("The order of annotations is wrong", annotationNamespaceANameA, attributeB.getAnnotations().get(0));
		Assert.assertEquals("The order of annotations is wrong", annotationNamespaceANameB, attributeB.getAnnotations().get(1));
		Assert.assertEquals("The order of annotations is wrong", annotationNamespaceBNameC, attributeB.getAnnotations().get(2));
	}
	
	@Test
	public void testDS6121AddDictPermValuesForEnum() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// Given
		MdfDomainImpl enumDomain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		enumDomain.setName("AAAEnumerations");
		enumDomain.setNamespace("http://www.odcgroup.com/aaa-enumerations");
		
		MdfDomainImpl businessTypesDomain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		businessTypesDomain.setName("AAABusinessTypes");
		businessTypesDomain.setNamespace("http://www.odcgroup.com/aaa-business-types");
		
		Map<Long, List<DictPermValue>> dictPermVals = new HashMap<Long, List<DictPermValue>>();
		List<DictPermValue> permValueLst = new LinkedList<DictPermValue>();
		
		DictEntity dictEntity = new DictEntity();
		dictEntity.setSQLName("position");
		DictDataType datatype = new DictDataType();
		datatype.setName("enum_t");
		datatype.setEquivType("tinyint");
		datatype.setSqlname("enum_t");
		
		final DictAttribute attribute = new DictAttribute();
		attribute.setDatatype(datatype);
		attribute.setDictEntity(dictEntity);
		attribute.setSQLName("reverse_op_name");
		
		DictPermValue permValue = new DictPermValue() {
			public String getName() {
				return "$ test$Perm$  Name@ ";
			}

			public DictAttribute getAttribute() {
				return attribute;
			}
		};
		
		permValue.getName();
		permValueLst.add(permValue);
		dictPermVals.put(new Long(1200284), permValueLst);
		
		MetaDict2Mml metaDict2Mml = new MetaDict2Mml();
		Method methodToTest = MetaDict2Mml.class.getDeclaredMethod("addDictPermValues", MdfDomain.class, Map.class, MdfDomain.class);
		methodToTest.setAccessible(true);
		
		// When
		Map<Object, Object> mdfEnum = (Map<Object, Object>)methodToTest.invoke(metaDict2Mml, enumDomain, dictPermVals, businessTypesDomain);
		
		// Then
		MdfEnumerationImpl enumValue = (MdfEnumerationImpl)mdfEnum.get(attribute);
		String dictPermValue = ((MdfEnumValueImpl)enumValue.getValues().get(0)).getName();
		Assert.assertEquals("Value expected is correct.", "_Test_Perm_Name_", dictPermValue);
	}
	
	@Test
	public void testDS6653EnumMask() throws Exception {
		// Given
		Collection<DictEntity> entities = new ArrayList<DictEntity>();

		DictDataType datatype = new DictDataType();
		datatype.setName("enummask_t");
		datatype.setSqlname("enummask_t");
		datatype.setEquivType("bigint");
		
		DictAttribute testAttribute = new DictAttribute();
		testAttribute.setName("testAttribute");
		testAttribute.setSQLName("TEST_ATTRIBUTE");
		testAttribute.setDatatype(datatype);
		testAttribute.setCalculated(DictAttributeCalculated.PHYSICAL);
		
		DictPermValue permVal1 = new DictPermValue();
		permVal1.setName("A");
		permVal1.setAttribute(testAttribute);
		testAttribute.getPermValues().add(permVal1);
		DictPermValue permVal2 = new DictPermValue();
		permVal2.setName("B");
		permVal2.setAttribute(testAttribute);
		testAttribute.getPermValues().add(permVal2);
		testAttribute.setIsPermVal(true);
		
		DictEntity testEntity = new DictEntity(1);
		testEntity.setName("TestEntity");
		testEntity.setSQLName("TEST_ENTITY");
		
		testEntity.addAttribute(testAttribute);
		testAttribute.setDictEntity(testEntity);
		entities.add(testEntity);
		TAVersion taVersion = new TAVersion("X1.0");
		MetaDictRepository aaaMetaDict = new MetaDictRepository(entities, taVersion);

		// When
		MetaDictDomains domains = new MetaDictDomains();
		domains.businessTypesDomain = BusinessTypesUtil.getBusinessTypesDomain(new XtextResourceSet());
		MetaDict2Mml metaDict2Mml = new MetaDict2Mml();
		AAACore.setRootAAAFolder("aaa");
		metaDict2Mml.createAAADomainFromMetaDict(aaaMetaDict, domains);
		
		// Then
		MdfClass mdfClass = domains.entitiesDomain.getClass("TestEntity");
		MdfAttribute mdfAttribute = (MdfAttribute) mdfClass.getProperty("testAttribute");
		
		assertTrue(mdfAttribute.getMultiplicity() == MdfMultiplicity.MANY);
		
		MdfEnumeration theEnum = (MdfEnumeration) mdfAttribute.getType();
		MdfBusinessType enumTypeBT = (MdfBusinessType) theEnum.getType();
		assertTrue(enumTypeBT.getName().equals("EnumMask"));
		assertEquals(domains.businessTypesDomain.getBusinessType("EnumMask"), enumTypeBT);
		
		// TODO assertEquals("???", mdfAttribute.getDefault());
	}
}
