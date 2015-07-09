package com.odcgroup.visualrules.integration.datasync;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;

import de.visualrules.integration.model.Attribute;
import de.visualrules.integration.model.Constant;
import de.visualrules.integration.model.Enumeration;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.JavaBeanTypeBinding;
import de.visualrules.integration.model.JavaConstantBinding;
import de.visualrules.integration.model.RulePackage;
import de.visualrules.integration.model.VRClass;

/**
 * This class provides the means to transform a DS domain model into VisualRules data types
 *
 * @author Kai Kreuzer
 *
 */
public class DataTypeTransformer {

	static public RulePackage transformToVRDataTypes(String domainName, EList<MdfEntity> entities) throws CoreException {
		RulePackage vrPackage = IntegrationFactory.eINSTANCE.createRulePackage();
		vrPackage.setName(domainName.toLowerCase());
		vrPackage.setExternal(true);
		for(MdfEntity entity : entities) {
			try {
				if(entity instanceof MdfEnumeration) 
					vrPackage.getTypes().add(mapEnumeration((MdfEnumeration) entity));
				if(entity instanceof MdfClass) 
					vrPackage.getTypes().add(mapClass((MdfClass) entity));
				if(entity instanceof MdfDataset) 
					vrPackage.getTypes().add(mapDataset((MdfDataset) entity));
			} catch(RuntimeException e) {
				// RuntimeException are thrown if a referenced domain cannot be loaded
				// (because it might be missing). We ignore this entity and try to import
				// the others.
			}
		}
		return vrPackage;
	}

	@SuppressWarnings("unchecked")
	private static VRClass mapClass(MdfClass clazz) {
		VRClass vrClass = IntegrationFactory.eINSTANCE.createVRClass();
		vrClass.setName(SyncHelper.makeVRcompliant(clazz.getName()));
		vrClass.setAbstract(clazz.isAbstract());
		if(clazz.getBaseClass()!=null) vrClass.setSuperclassName(clazz.getBaseClass().getName());
		vrClass.setDescription(clazz.getParentDomain().getName() + ": " + clazz.getDocumentation());
		// external means for VR that no bean should be generated as it already exists
		vrClass.setExternal(true);
		for(MdfProperty property : (List<MdfProperty>) clazz.getProperties()) {
			if(property instanceof MdfAttribute) {
				vrClass.getAttributes().add(mapProperty(property));
			} else if(property instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation) property;
				if(SyncHelper.isRefByValue(association)) {
					vrClass.getAttributes().add(mapProperty(association));
				}
			}
		}
		vrClass.getTypeBindings().add(mapEntityType(clazz));
		return vrClass;
	}

	@SuppressWarnings("unchecked")
	private static VRClass mapDataset(MdfDataset dataset) {
		VRClass vrClass = IntegrationFactory.eINSTANCE.createVRClass();
		vrClass.setName(SyncHelper.makeVRcompliant(dataset.getName()));
		vrClass.setAbstract(false);
		vrClass.setDescription(dataset.getParentDomain().getName() + ": " + dataset.getDocumentation());
		// external means for VR that no bean should be generated as it already exists
		vrClass.setExternal(true);
		for(MdfDatasetProperty property : (List<MdfDatasetProperty>) dataset.getProperties()) {
			vrClass.getAttributes().add(mapDatasetProperty(property));
		}
		vrClass.getTypeBindings().add(mapEntityType(dataset));
		return vrClass;
	}

	private static Attribute mapProperty(MdfProperty property) {
		Attribute vrAttribute = IntegrationFactory.eINSTANCE.createAttribute();
		vrAttribute.setName(SyncHelper.makeVRcompliant(property.getName()));
		vrAttribute.setTypeName(SyncHelper.mapType(property));
		vrAttribute.setTypeInfo(SyncHelper.getTypeInfo(property));
		vrAttribute.setIterable(!(property.getMultiplicity()==MdfMultiplicity.ONE));
		vrAttribute.setOrdered(!(property.getMultiplicity()==MdfMultiplicity.ONE));
		vrAttribute.setDescription(property.getDocumentation());
		return vrAttribute;
	}

	private static Attribute mapDatasetProperty(MdfDatasetProperty property) {
		Attribute vrAttribute = IntegrationFactory.eINSTANCE.createAttribute();
		vrAttribute.setName(SyncHelper.makeVRcompliant(property.getName()));
		vrAttribute.setIterable(!(property.getMultiplicity()==MdfMultiplicity.ONE));
		vrAttribute.setTypeName(SyncHelper.mapType(property));
		vrAttribute.setTypeInfo(SyncHelper.getTypeInfo(property));
		vrAttribute.setOrdered(!(property.getMultiplicity()==MdfMultiplicity.ONE));
		vrAttribute.setDescription(property.getDocumentation());

		return vrAttribute;
	}

	@SuppressWarnings("unchecked")
	private static Enumeration mapEnumeration(MdfEnumeration enumeration) {
		Enumeration vrEnumeration = IntegrationFactory.eINSTANCE.createEnumeration();
		vrEnumeration.setName(SyncHelper.makeVRcompliant(enumeration.getName()));
		vrEnumeration.setDescription(enumeration.getParentDomain().getName() + ": " + enumeration.getDocumentation());
		// external means for VR that no bean should be generated as it already exists
		vrEnumeration.setExternal(true);
		vrEnumeration.setCreatable(false);
		vrEnumeration.setAbstract(false);
		vrEnumeration.setFinal(true);
		vrEnumeration.setInterface(false);
		for(MdfEnumValue enumValue : (List<MdfEnumValue>) enumeration.getValues()) {
			vrEnumeration.getConstants().add(mapEnumValue(enumValue));
		}
		vrEnumeration.getTypeBindings().add(mapEntityType(enumeration));
		return vrEnumeration;
	}

	private static Constant mapEnumValue(MdfEnumValue enumValue) {
		Constant vrConstant = IntegrationFactory.eINSTANCE.createConstant();
		vrConstant.setName(SyncHelper.makeVRcompliant(enumValue.getName()));
		vrConstant.setTypeName(SyncHelper.mapType(enumValue.getParentEnumeration()));
		vrConstant.setLiteral(true);
		vrConstant.setExternal(true);
		vrConstant.getConstantBindings().add(mapConstantType(enumValue));
		return vrConstant;
	}

	private static JavaBeanTypeBinding mapEntityType(MdfEntity entity) {
		JavaBeanTypeBinding vrBinding = IntegrationFactory.eINSTANCE.createJavaBeanTypeBinding();
		vrBinding.setFullyQualifiedClassname(SyncHelper.getFQCN(entity));
		return vrBinding;
	}

	private static JavaConstantBinding mapConstantType(MdfEnumValue enumValue) {
		JavaConstantBinding vrBinding = IntegrationFactory.eINSTANCE.createJavaConstantBinding();
		vrBinding.setConstantName(enumValue.getName());
		vrBinding.setDeclaringClassName(SyncHelper.getFQCN(enumValue.getParentEnumeration()));
		return vrBinding;
	}

}
