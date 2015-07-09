/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.mdf.ecore.custo.CustomizationSupport;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 */
public class MdfFactoryImpl extends EFactoryImpl implements MdfFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public static MdfFactory init() {
		try {
			MdfFactory theMdfFactory = (MdfFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/mdf"); 
			if (theMdfFactory != null) {
				return theMdfFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MdfFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MdfPackage.MDF_ENTITY: return (EObject)createMdfEntity();
			case MdfPackage.MDF_DOMAIN: return (EObject)createMdfDomain();
			case MdfPackage.MDF_CLASS: return (EObject)createMdfClass();
			case MdfPackage.MDF_ATTRIBUTE: return (EObject)createMdfAttribute();
			case MdfPackage.MDF_ASSOCIATION: return (EObject)createMdfAssociation();
			case MdfPackage.MDF_REVERSE_ASSOCIATION: return (EObject)createMdfReverseAssociation();
			case MdfPackage.MDF_PRIMITIVE: return (EObject)createMdfPrimitive();
			case MdfPackage.MDF_ENUMERATION: return (EObject)createMdfEnumeration();
			case MdfPackage.MDF_ENUM_VALUE: return (EObject)createMdfEnumValue();
			case MdfPackage.MDF_DATASET: return (EObject)createMdfDataset();
			case MdfPackage.MDF_ANNOTATION: return (EObject)createMdfAnnotation();
			case MdfPackage.MDF_ANNOTATION_PROPERTY: return (EObject)createMdfAnnotationProperty();
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY: return (EObject)createMdfDatasetDerivedProperty();
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY: return (EObject)createMdfDatasetMappedProperty();
			case MdfPackage.MDF_BUSINESS_TYPE: return (EObject)createMdfBusinessType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case MdfPackage.MDF_CONTAINMENT:
				return createMdfContainmentFromString(eDataType, initialValue);
			case MdfPackage.MDF_MULTIPLICITY:
				return createMdfMultiplicityFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case MdfPackage.MDF_CONTAINMENT:
				return convertMdfContainmentToString(eDataType, instanceValue);
			case MdfPackage.MDF_MULTIPLICITY:
				return convertMdfMultiplicityToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfEntity createMdfEntity() {
		MdfEntityImpl mdfEntity = new MdfEntityImpl();
		return mdfEntity;
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfDomain createMdfDomain() {
		MdfDomainImpl mdfDomain = new MdfDomainImpl();
		return mdfDomain;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfClass createMdfClass() {
		MdfClassImpl mdfClass = new MdfClassImpl();
		return mdfClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfAttribute createMdfAttribute() {
		MdfAttributeImpl mdfAttribute = new MdfAttributeImpl();
		return mdfAttribute;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfAssociation createMdfAssociation() {
		MdfAssociationImpl mdfAssociation = new MdfAssociationImpl();
		return mdfAssociation;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfReverseAssociation createMdfReverseAssociation() {
		MdfReverseAssociationImpl mdfReverseAssociation = new MdfReverseAssociationImpl();
		return mdfReverseAssociation;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfPrimitive createMdfPrimitive() {
		MdfPrimitiveImpl mdfPrimitive = new MdfPrimitiveImpl();
		return mdfPrimitive;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfEnumeration createMdfEnumeration() {
		MdfEnumerationImpl mdfEnumeration = new MdfEnumerationImpl();
		return mdfEnumeration;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfEnumValue createMdfEnumValue() {
		MdfEnumValueImpl mdfEnumValue = new MdfEnumValueImpl();
		return mdfEnumValue;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfDataset createMdfDataset() {
		MdfDatasetImpl mdfDataset = new MdfDatasetImpl();
		return mdfDataset;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfAnnotation createMdfAnnotation() {
		MdfAnnotationImpl mdfAnnotation = new MdfAnnotationImpl();
		return mdfAnnotation;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfAnnotationProperty createMdfAnnotationProperty() {
		MdfAnnotationPropertyImpl mdfAnnotationProperty = new MdfAnnotationPropertyImpl();
		return mdfAnnotationProperty;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfDatasetDerivedProperty createMdfDatasetDerivedProperty() {
		MdfDatasetDerivedPropertyImpl mdfDatasetDerivedProperty = new MdfDatasetDerivedPropertyImpl();
		return mdfDatasetDerivedProperty;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfDatasetMappedProperty createMdfDatasetMappedProperty() {
		MdfDatasetMappedPropertyImpl mdfDatasetMappedProperty = new MdfDatasetMappedPropertyImpl();
		return mdfDatasetMappedProperty;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfBusinessType createMdfBusinessType() {
		MdfBusinessTypeImpl mdfBusinessType = new MdfBusinessTypeImpl();
		return mdfBusinessType;
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfMultiplicity createMdfMultiplicityFromString(EDataType eDataType, String initialValue) {
		MdfMultiplicity result = MdfMultiplicity.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String convertMdfMultiplicityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfContainment createMdfContainmentFromString(EDataType eDataType, String initialValue) {
		MdfContainment result = MdfContainment.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String convertMdfContainmentToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfPackage getMdfPackage() {
		return (MdfPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @deprecated

	 */
    public static MdfPackage getPackage() {
		return MdfPackage.eINSTANCE;
	}
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfClass createMdfClass(IProject project) {
		return customize(createMdfClass());
	}
	
	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfDataset createMdfDataset(IProject project) {
		return customize(createMdfDataset());
	}
	
	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */	
	public MdfEnumeration createMdfEnumeration(IProject project) {
		return customize(createMdfEnumeration());
	}
	
	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfReverseAssociation createMdfReverseAssociation(IProject project) {
		return customize(createMdfReverseAssociation());
	}
	
	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfAssociation createMdfAssociation(IProject project) {
		return customize(createMdfAssociation());
	}

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfAttribute createMdfAttribute(IProject project) {
		return customize(createMdfAttribute(), project);
	}

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfDatasetDerivedProperty createMdfDatasetDerivedProperty(
			IProject project) {
		return customize(createMdfDatasetDerivedProperty(), project);
	}

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfDatasetMappedProperty createMdfDatasetMappedProperty(
			IProject project) {
		return customize(createMdfDatasetMappedProperty(), project);
	}

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfEnumValue createMdfEnumValue(IProject project) {
		return customize(createMdfEnumValue(), project);
	}

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	public MdfBusinessType createMdfBusinessType(IProject project) {
		return customize(createMdfBusinessType(), project);
	}
	
	/**
	 * DS-3053
	 * @param element
 NOT
	 */
	public MdfDatasetMappedProperty createMdfDatasetMappedProperty(IProject project, MdfModelElement element) {
		return customize(createMdfDatasetMappedProperty(), project);
	}

    private <T extends MdfModelElement> T customize(T object) {
        CustomizationSupport.setCustomElement((MdfModelElement) object);
        return object;
    }
    
    private <T extends MdfModelElement> T customize(T object, IProject project) {
        CustomizationSupport.setCustomElement((MdfModelElement) object, project);
        return object;
    }
    
} //MdfFactoryImpl
