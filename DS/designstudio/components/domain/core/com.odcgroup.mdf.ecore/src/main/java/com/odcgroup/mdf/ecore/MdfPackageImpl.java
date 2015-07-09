/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 */
public class MdfPackageImpl extends EPackageImpl implements MdfPackage {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfDeprecationInfoEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfModelElementEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfEntityEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfDomainEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfClassEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfPropertyEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfAttributeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfAssociationEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfReverseAssociationEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfPrimitiveEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfEnumerationEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfEnumValueEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfDatasetEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfDatasetPropertyEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfAnnotationEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EClass mdfAnnotationPropertyEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	private EClass mdfDatasetDerivedPropertyEClass = null;

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	private EClass mdfDatasetMappedPropertyEClass = null;

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	private EClass mdfBusinessTypeEClass = null;

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EEnum mdfMultiplicityEEnum = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private EEnum mdfContainmentEEnum = null;

    /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.odcgroup.mdf.ecore.MdfPackage#eNS_URI
	 * @see #init()

	 */
    private MdfPackageImpl() {
		super(eNS_URI, MdfFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private static boolean isInited = false;

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MdfPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()

	 */
    public static MdfPackage init() {
		if (isInited) return (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);

		// Obtain or create and register package
		MdfPackageImpl theMdfPackage = (MdfPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MdfPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MdfPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMdfPackage.createPackageContents();

		// Initialize created meta-data
		theMdfPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMdfPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MdfPackage.eNS_URI, theMdfPackage);
		return theMdfPackage;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfDeprecationInfo() {
		return mdfDeprecationInfoEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfDeprecationInfo_Version() {
		return (EAttribute)mdfDeprecationInfoEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfDeprecationInfo_Comment() {
		return (EAttribute)mdfDeprecationInfoEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfModelElement() {
		return mdfModelElementEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfModelElement_DeprecationInfo() {
		return (EReference)mdfModelElementEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfModelElement_Documentation() {
		return (EAttribute)mdfModelElementEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfModelElement_Name() {
		return (EAttribute)mdfModelElementEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfModelElement_Annotations() {
		return (EReference)mdfModelElementEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfEntity() {
		return mdfEntityEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfDomain() {
		return mdfDomainEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDomain_ImportedDomains() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfDomain_Namespace() {
		return (EAttribute)mdfDomainEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDomain_Primitives() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDomain_MetamodelVersion() {
		return (EAttribute)mdfDomainEClass.getEStructuralFeatures().get(7);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDomain_Classes() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDomain_Datasets() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfDomain_BusinessTypes() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(4);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfDomain_Enumerations() {
		return (EReference)mdfDomainEClass.getEStructuralFeatures().get(5);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfClass() {
		return mdfClassEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfClass_BaseClass() {
		return (EReference)mdfClassEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfClass_Abstract() {
		return (EAttribute)mdfClassEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfClass_Properties() {
		return (EReference)mdfClassEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfClass_Secured() {
		return (EAttribute)mdfClassEClass.getEStructuralFeatures().get(3);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfClass_Referenceable() {
		return (EAttribute)mdfClassEClass.getEStructuralFeatures().get(4);
	}

                /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfProperty() {
		return mdfPropertyEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfProperty_BusinessKey() {
		return (EAttribute)mdfPropertyEClass.getEStructuralFeatures().get(0);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfProperty_Required() {
		return (EAttribute)mdfPropertyEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfProperty_Multiplicity() {
		return (EAttribute)mdfPropertyEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfProperty_PrimaryKey() {
		return (EAttribute)mdfPropertyEClass.getEStructuralFeatures().get(3);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfAttribute() {
		return mdfAttributeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfAttribute_Type() {
		return (EReference)mdfAttributeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAttribute_Default() {
		return (EAttribute)mdfAttributeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfAssociation() {
		return mdfAssociationEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfAssociation_Type() {
		return (EReference)mdfAssociationEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAssociation_Containment() {
		return (EAttribute)mdfAssociationEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfAssociation_Reverse() {
		return (EReference)mdfAssociationEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfReverseAssociation() {
		return mdfReverseAssociationEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfReverseAssociation_ReversedAssociation() {
		return (EReference)mdfReverseAssociationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMdfReverseAssociation_ReversedAssociationType() {
		return (EReference)mdfReverseAssociationEClass.getEStructuralFeatures().get(1);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfPrimitive() {
		return mdfPrimitiveEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfEnumeration() {
		return mdfEnumerationEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfEnumeration_Values() {
		return (EReference)mdfEnumerationEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfEnumeration_Type() {
		return (EReference)mdfEnumerationEClass.getEStructuralFeatures().get(1);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfEnumeration_AcceptNullValue() {
		return (EAttribute)mdfEnumerationEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfEnumValue() {
		return mdfEnumValueEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfEnumValue_Value() {
		return (EAttribute)mdfEnumValueEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfDataset() {
		return mdfDatasetEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDataset_BaseClass() {
		return (EReference)mdfDatasetEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfDataset_Properties() {
		return (EReference)mdfDatasetEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDataset_Linked() {
		return (EAttribute)mdfDatasetEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDataset_Sync() {
		return (EAttribute)mdfDatasetEClass.getEStructuralFeatures().get(3);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfDatasetProperty() {
		return mdfDatasetPropertyEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfAnnotation() {
		return mdfAnnotationEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAnnotation_Namespace() {
		return (EAttribute)mdfAnnotationEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAnnotation_Name() {
		return (EAttribute)mdfAnnotationEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EReference getMdfAnnotation_Properties() {
		return (EReference)mdfAnnotationEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EClass getMdfAnnotationProperty() {
		return mdfAnnotationPropertyEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAnnotationProperty_Name() {
		return (EAttribute)mdfAnnotationPropertyEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EAttribute getMdfAnnotationProperty_Value() {
		return (EAttribute)mdfAnnotationPropertyEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfAnnotationProperty_CDATA() {
		return (EAttribute)mdfAnnotationPropertyEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EClass getMdfDatasetDerivedProperty() {
		return mdfDatasetDerivedPropertyEClass;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDatasetDerivedProperty_Default() {
		return (EAttribute)mdfDatasetDerivedPropertyEClass.getEStructuralFeatures().get(0);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDatasetDerivedProperty_Multiplicity() {
		return (EAttribute)mdfDatasetDerivedPropertyEClass.getEStructuralFeatures().get(1);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfDatasetDerivedProperty_Type() {
		return (EReference)mdfDatasetDerivedPropertyEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EClass getMdfDatasetMappedProperty() {
		return mdfDatasetMappedPropertyEClass;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDatasetMappedProperty_Path() {
		return (EAttribute)mdfDatasetMappedPropertyEClass.getEStructuralFeatures().get(0);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDatasetMappedProperty_Unique() {
		return (EAttribute)mdfDatasetMappedPropertyEClass.getEStructuralFeatures().get(1);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EAttribute getMdfDatasetMappedProperty_SingleValued() {
		return (EAttribute)mdfDatasetMappedPropertyEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfDatasetMappedProperty_LinkDataset() {
		return (EReference)mdfDatasetMappedPropertyEClass.getEStructuralFeatures().get(3);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EClass getMdfBusinessType() {
		return mdfBusinessTypeEClass;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public EReference getMdfBusinessType_Type() {
		return (EReference)mdfBusinessTypeEClass.getEStructuralFeatures().get(0);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EEnum getMdfMultiplicity() {
		return mdfMultiplicityEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public EEnum getMdfContainment() {
		return mdfContainmentEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfFactory getMdfFactory() {
		return (MdfFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		mdfDeprecationInfoEClass = createEClass(MDF_DEPRECATION_INFO);
		createEAttribute(mdfDeprecationInfoEClass, MDF_DEPRECATION_INFO__VERSION);
		createEAttribute(mdfDeprecationInfoEClass, MDF_DEPRECATION_INFO__COMMENT);

		mdfModelElementEClass = createEClass(MDF_MODEL_ELEMENT);
		createEReference(mdfModelElementEClass, MDF_MODEL_ELEMENT__DEPRECATION_INFO);
		createEAttribute(mdfModelElementEClass, MDF_MODEL_ELEMENT__DOCUMENTATION);
		createEAttribute(mdfModelElementEClass, MDF_MODEL_ELEMENT__NAME);
		createEReference(mdfModelElementEClass, MDF_MODEL_ELEMENT__ANNOTATIONS);

		mdfEntityEClass = createEClass(MDF_ENTITY);

		mdfDomainEClass = createEClass(MDF_DOMAIN);
		createEReference(mdfDomainEClass, MDF_DOMAIN__IMPORTED_DOMAINS);
		createEAttribute(mdfDomainEClass, MDF_DOMAIN__NAMESPACE);
		createEReference(mdfDomainEClass, MDF_DOMAIN__CLASSES);
		createEReference(mdfDomainEClass, MDF_DOMAIN__DATASETS);
		createEReference(mdfDomainEClass, MDF_DOMAIN__BUSINESS_TYPES);
		createEReference(mdfDomainEClass, MDF_DOMAIN__ENUMERATIONS);
		createEReference(mdfDomainEClass, MDF_DOMAIN__PRIMITIVES);
		createEAttribute(mdfDomainEClass, MDF_DOMAIN__METAMODEL_VERSION);

		mdfClassEClass = createEClass(MDF_CLASS);
		createEReference(mdfClassEClass, MDF_CLASS__BASE_CLASS);
		createEAttribute(mdfClassEClass, MDF_CLASS__ABSTRACT);
		createEReference(mdfClassEClass, MDF_CLASS__PROPERTIES);
		createEAttribute(mdfClassEClass, MDF_CLASS__SECURED);
		createEAttribute(mdfClassEClass, MDF_CLASS__REFERENCEABLE);

		mdfPropertyEClass = createEClass(MDF_PROPERTY);
		createEAttribute(mdfPropertyEClass, MDF_PROPERTY__BUSINESS_KEY);
		createEAttribute(mdfPropertyEClass, MDF_PROPERTY__REQUIRED);
		createEAttribute(mdfPropertyEClass, MDF_PROPERTY__MULTIPLICITY);
		createEAttribute(mdfPropertyEClass, MDF_PROPERTY__PRIMARY_KEY);

		mdfAttributeEClass = createEClass(MDF_ATTRIBUTE);
		createEReference(mdfAttributeEClass, MDF_ATTRIBUTE__TYPE);
		createEAttribute(mdfAttributeEClass, MDF_ATTRIBUTE__DEFAULT);

		mdfAssociationEClass = createEClass(MDF_ASSOCIATION);
		createEReference(mdfAssociationEClass, MDF_ASSOCIATION__TYPE);
		createEAttribute(mdfAssociationEClass, MDF_ASSOCIATION__CONTAINMENT);
		createEReference(mdfAssociationEClass, MDF_ASSOCIATION__REVERSE);

		mdfReverseAssociationEClass = createEClass(MDF_REVERSE_ASSOCIATION);
		createEReference(mdfReverseAssociationEClass, MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION);
		createEReference(mdfReverseAssociationEClass, MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE);

		mdfPrimitiveEClass = createEClass(MDF_PRIMITIVE);

		mdfEnumerationEClass = createEClass(MDF_ENUMERATION);
		createEReference(mdfEnumerationEClass, MDF_ENUMERATION__VALUES);
		createEReference(mdfEnumerationEClass, MDF_ENUMERATION__TYPE);
		createEAttribute(mdfEnumerationEClass, MDF_ENUMERATION__ACCEPT_NULL_VALUE);

		mdfEnumValueEClass = createEClass(MDF_ENUM_VALUE);
		createEAttribute(mdfEnumValueEClass, MDF_ENUM_VALUE__VALUE);

		mdfDatasetEClass = createEClass(MDF_DATASET);
		createEReference(mdfDatasetEClass, MDF_DATASET__BASE_CLASS);
		createEReference(mdfDatasetEClass, MDF_DATASET__PROPERTIES);
		createEAttribute(mdfDatasetEClass, MDF_DATASET__LINKED);
		createEAttribute(mdfDatasetEClass, MDF_DATASET__SYNC);

		mdfDatasetPropertyEClass = createEClass(MDF_DATASET_PROPERTY);

		mdfAnnotationEClass = createEClass(MDF_ANNOTATION);
		createEAttribute(mdfAnnotationEClass, MDF_ANNOTATION__NAMESPACE);
		createEAttribute(mdfAnnotationEClass, MDF_ANNOTATION__NAME);
		createEReference(mdfAnnotationEClass, MDF_ANNOTATION__PROPERTIES);

		mdfAnnotationPropertyEClass = createEClass(MDF_ANNOTATION_PROPERTY);
		createEAttribute(mdfAnnotationPropertyEClass, MDF_ANNOTATION_PROPERTY__NAME);
		createEAttribute(mdfAnnotationPropertyEClass, MDF_ANNOTATION_PROPERTY__VALUE);
		createEAttribute(mdfAnnotationPropertyEClass, MDF_ANNOTATION_PROPERTY__CDATA);

		mdfDatasetDerivedPropertyEClass = createEClass(MDF_DATASET_DERIVED_PROPERTY);
		createEAttribute(mdfDatasetDerivedPropertyEClass, MDF_DATASET_DERIVED_PROPERTY__DEFAULT);
		createEAttribute(mdfDatasetDerivedPropertyEClass, MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY);
		createEReference(mdfDatasetDerivedPropertyEClass, MDF_DATASET_DERIVED_PROPERTY__TYPE);

		mdfDatasetMappedPropertyEClass = createEClass(MDF_DATASET_MAPPED_PROPERTY);
		createEAttribute(mdfDatasetMappedPropertyEClass, MDF_DATASET_MAPPED_PROPERTY__PATH);
		createEAttribute(mdfDatasetMappedPropertyEClass, MDF_DATASET_MAPPED_PROPERTY__UNIQUE);
		createEAttribute(mdfDatasetMappedPropertyEClass, MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED);
		createEReference(mdfDatasetMappedPropertyEClass, MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET);

		mdfBusinessTypeEClass = createEClass(MDF_BUSINESS_TYPE);
		createEReference(mdfBusinessTypeEClass, MDF_BUSINESS_TYPE__TYPE);

		// Create enums
		mdfContainmentEEnum = createEEnum(MDF_CONTAINMENT);
		mdfMultiplicityEEnum = createEEnum(MDF_MULTIPLICITY);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes
		mdfEntityEClass.getESuperTypes().add(this.getMdfModelElement());
		mdfDomainEClass.getESuperTypes().add(this.getMdfModelElement());
		mdfClassEClass.getESuperTypes().add(this.getMdfEntity());
		mdfPropertyEClass.getESuperTypes().add(this.getMdfModelElement());
		mdfAttributeEClass.getESuperTypes().add(this.getMdfProperty());
		mdfAssociationEClass.getESuperTypes().add(this.getMdfProperty());
		mdfReverseAssociationEClass.getESuperTypes().add(this.getMdfProperty());
		mdfPrimitiveEClass.getESuperTypes().add(this.getMdfEntity());
		mdfEnumerationEClass.getESuperTypes().add(this.getMdfPrimitive());
		mdfEnumValueEClass.getESuperTypes().add(this.getMdfModelElement());
		mdfDatasetEClass.getESuperTypes().add(this.getMdfEntity());
		mdfDatasetPropertyEClass.getESuperTypes().add(this.getMdfModelElement());
		mdfDatasetDerivedPropertyEClass.getESuperTypes().add(this.getMdfDatasetProperty());
		mdfDatasetMappedPropertyEClass.getESuperTypes().add(this.getMdfDatasetProperty());
		mdfBusinessTypeEClass.getESuperTypes().add(this.getMdfPrimitive());

		// Initialize classes and features; add operations and parameters
		initEClass(mdfDeprecationInfoEClass, EObject.class, "MdfDeprecationInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfDeprecationInfo_Version(), ecorePackage.getEString(), "version", null, 1, 1, EObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDeprecationInfo_Comment(), ecorePackage.getEString(), "comment", null, 1, 1, EObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfModelElementEClass, MdfModelElement.class, "MdfModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfModelElement_DeprecationInfo(), this.getMdfDeprecationInfo(), null, "deprecationInfo", null, 0, 1, MdfModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfModelElement_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1, MdfModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfModelElement_Name(), ecorePackage.getEString(), "name", "", 1, 1, MdfModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfModelElement_Annotations(), this.getMdfAnnotation(), null, "annotations", null, 0, -1, MdfModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfEntityEClass, MdfEntity.class, "MdfEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mdfDomainEClass, MdfDomain.class, "MdfDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfDomain_ImportedDomains(), this.getMdfDomain(), null, "importedDomains", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDomain_Namespace(), ecorePackage.getEString(), "namespace", null, 1, 1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDomain_Classes(), this.getMdfClass(), null, "classes", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDomain_Datasets(), this.getMdfDataset(), null, "datasets", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDomain_BusinessTypes(), this.getMdfBusinessType(), null, "businessTypes", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDomain_Enumerations(), this.getMdfEnumeration(), null, "enumerations", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDomain_Primitives(), this.getMdfPrimitive(), null, "primitives", null, 0, -1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDomain_MetamodelVersion(), ecorePackage.getEString(), "metamodelVersion", null, 0, 1, MdfDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfClassEClass, MdfClass.class, "MdfClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfClass_BaseClass(), this.getMdfClass(), null, "baseClass", null, 0, 1, MdfClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfClass_Abstract(), ecorePackage.getEBoolean(), "abstract", "false", 0, 1, MdfClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfClass_Properties(), this.getMdfProperty(), null, "properties", null, 0, -1, MdfClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfClass_Secured(), ecorePackage.getEBoolean(), "secured", "false", 0, 1, MdfClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfClass_Referenceable(), ecorePackage.getEBoolean(), "referenceable", null, 0, 1, MdfClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(mdfPropertyEClass, MdfProperty.class, "MdfProperty", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfProperty_BusinessKey(), ecorePackage.getEBoolean(), "businessKey", null, 0, 1, MdfProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfProperty_Required(), ecorePackage.getEBoolean(), "required", "false", 0, 1, MdfProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfProperty_Multiplicity(), this.getMdfMultiplicity(), "multiplicity", "0", 0, 1, MdfProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfProperty_PrimaryKey(), ecorePackage.getEBoolean(), "primaryKey", "false", 0, 1, MdfProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfAttributeEClass, MdfAttribute.class, "MdfAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfAttribute_Type(), this.getMdfPrimitive(), null, "type", null, 1, 1, MdfAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfAttribute_Default(), ecorePackage.getEString(), "default", null, 0, 1, MdfAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfAssociationEClass, MdfAssociation.class, "MdfAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfAssociation_Type(), this.getMdfClass(), null, "type", null, 1, 1, MdfAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfAssociation_Containment(), this.getMdfContainment(), "containment", "0", 0, 1, MdfAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfAssociation_Reverse(), this.getMdfReverseAssociation(), null, "reverse", null, 0, 1, MdfAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfReverseAssociationEClass, MdfReverseAssociation.class, "MdfReverseAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfReverseAssociation_ReversedAssociation(), this.getMdfAssociation(), null, "reversedAssociation", null, 0, 1, MdfReverseAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfReverseAssociation_ReversedAssociationType(), this.getMdfClass(), null, "reversedAssociationType", null, 0, 1, MdfReverseAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfPrimitiveEClass, MdfPrimitive.class, "MdfPrimitive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mdfEnumerationEClass, MdfEnumeration.class, "MdfEnumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfEnumeration_Values(), this.getMdfEnumValue(), null, "values", null, 0, -1, MdfEnumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfEnumeration_Type(), this.getMdfPrimitive(), null, "type", null, 0, 1, MdfEnumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfEnumeration_AcceptNullValue(), ecorePackage.getEBoolean(), "acceptNullValue", "false", 0, 1, MdfEnumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfEnumValueEClass, MdfEnumValue.class, "MdfEnumValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfEnumValue_Value(), ecorePackage.getEString(), "value", null, 1, 1, MdfEnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfDatasetEClass, MdfDataset.class, "MdfDataset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfDataset_BaseClass(), this.getMdfClass(), null, "baseClass", null, 1, 1, MdfDataset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDataset_Properties(), this.getMdfDatasetProperty(), null, "properties", null, 0, -1, MdfDataset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDataset_Linked(), ecorePackage.getEBoolean(), "linked", "false", 0, 1, MdfDataset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDataset_Sync(), ecorePackage.getEBoolean(), "sync", "false", 0, 1, MdfDataset.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfDatasetPropertyEClass, MdfDatasetProperty.class, "MdfDatasetProperty", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(mdfDatasetPropertyEClass, this.getMdfEntity(), "getType", 0, 1);

		addEOperation(mdfDatasetPropertyEClass, this.getMdfMultiplicity(), "getMultiplicity", 1, 1);

		initEClass(mdfAnnotationEClass, MdfAnnotation.class, "MdfAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfAnnotation_Namespace(), ecorePackage.getEString(), "namespace", null, 1, 1, MdfAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfAnnotation_Name(), ecorePackage.getEString(), "name", null, 1, 1, MdfAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfAnnotation_Properties(), this.getMdfAnnotationProperty(), null, "properties", null, 0, -1, MdfAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfAnnotationPropertyEClass, MdfAnnotationProperty.class, "MdfAnnotationProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfAnnotationProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, MdfAnnotationProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfAnnotationProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1, MdfAnnotationProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfAnnotationProperty_CDATA(), ecorePackage.getEBoolean(), "cDATA", "false", 0, 1, MdfAnnotationProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfDatasetDerivedPropertyEClass, MdfDatasetDerivedProperty.class, "MdfDatasetDerivedProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfDatasetDerivedProperty_Default(), ecorePackage.getEString(), "default", "", 0, 1, MdfDatasetDerivedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDatasetDerivedProperty_Multiplicity(), this.getMdfMultiplicity(), "multiplicity", "0", 0, 1, MdfDatasetDerivedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDatasetDerivedProperty_Type(), this.getMdfEntity(), null, "type", null, 1, 1, MdfDatasetDerivedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfDatasetMappedPropertyEClass, MdfDatasetMappedProperty.class, "MdfDatasetMappedProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMdfDatasetMappedProperty_Path(), ecorePackage.getEString(), "path", null, 1, 1, MdfDatasetMappedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDatasetMappedProperty_Unique(), ecorePackage.getEBoolean(), "unique", "true", 0, 1, MdfDatasetMappedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMdfDatasetMappedProperty_SingleValued(), ecorePackage.getEBoolean(), "singleValued", "false", 0, 1, MdfDatasetMappedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfDatasetMappedProperty_LinkDataset(), this.getMdfDataset(), null, "linkDataset", null, 0, 1, MdfDatasetMappedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mdfBusinessTypeEClass, MdfBusinessType.class, "MdfBusinessType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfBusinessType_Type(), this.getMdfPrimitive(), null, "type", null, 1, 1, MdfBusinessType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(mdfContainmentEEnum, MdfContainment.class, "MdfContainment");
		addEEnumLiteral(mdfContainmentEEnum, MdfContainment.BY_VALUE_LITERAL);
		addEEnumLiteral(mdfContainmentEEnum, MdfContainment.BY_REFERENCE_LITERAL);

		initEEnum(mdfMultiplicityEEnum, MdfMultiplicity.class, "MdfMultiplicity");
		addEEnumLiteral(mdfMultiplicityEEnum, MdfMultiplicity.ONE_LITERAL);
		addEEnumLiteral(mdfMultiplicityEEnum, MdfMultiplicity.MANY_LITERAL);

		// Create resource
		createResource(eNS_URI);
	}

} //MdfPackageImpl
