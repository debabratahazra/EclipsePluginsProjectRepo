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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.mdf.ecore.MdfFactory
 * @model kind="package"
 */
public interface MdfPackage extends EPackage {
    /**
	 * The package name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    String eNAME = "mdf";

    /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    String eNS_URI = "http://www.odcgroup.com/mdf";

    /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    String eNS_PREFIX = "mdf";

    /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    MdfPackage eINSTANCE = com.odcgroup.mdf.ecore.MdfPackageImpl.init();

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDeprecationInfoImpl <em>Deprecation Info</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDeprecationInfoImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDeprecationInfo()

	 */
    int MDF_DEPRECATION_INFO = 0;

    /**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DEPRECATION_INFO__VERSION = 0;

    /**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DEPRECATION_INFO__COMMENT = 1;

    /**
	 * The number of structural features of the '<em>Deprecation Info</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DEPRECATION_INFO_FEATURE_COUNT = 2;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfModelElementImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfModelElement()

	 */
    int MDF_MODEL_ELEMENT = 1;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_MODEL_ELEMENT__DEPRECATION_INFO = 0;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_MODEL_ELEMENT__DOCUMENTATION = 1;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_MODEL_ELEMENT__NAME = 2;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_MODEL_ELEMENT__ANNOTATIONS = 3;

    /**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_MODEL_ELEMENT_FEATURE_COUNT = 4;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfEntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfEntityImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEntity()

	 */
    int MDF_ENTITY = 2;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENTITY__DEPRECATION_INFO = MDF_MODEL_ELEMENT__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENTITY__DOCUMENTATION = MDF_MODEL_ELEMENT__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENTITY__NAME = MDF_MODEL_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENTITY__ANNOTATIONS = MDF_MODEL_ELEMENT__ANNOTATIONS;

    /**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENTITY_FEATURE_COUNT = MDF_MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDomainImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDomain()

	 */
    int MDF_DOMAIN = 3;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__DEPRECATION_INFO = MDF_MODEL_ELEMENT__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__DOCUMENTATION = MDF_MODEL_ELEMENT__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__NAME = MDF_MODEL_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__ANNOTATIONS = MDF_MODEL_ELEMENT__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Imported Domains</b></em>' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__IMPORTED_DOMAINS = MDF_MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__NAMESPACE = MDF_MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__CLASSES = MDF_MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Datasets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__DATASETS = MDF_MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Business Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DOMAIN__BUSINESS_TYPES = MDF_MODEL_ELEMENT_FEATURE_COUNT + 4;

				/**
	 * The feature id for the '<em><b>Enumerations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DOMAIN__ENUMERATIONS = MDF_MODEL_ELEMENT_FEATURE_COUNT + 5;

				/**
	 * The feature id for the '<em><b>Primitives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN__PRIMITIVES = MDF_MODEL_ELEMENT_FEATURE_COUNT + 6;

				/**
	 * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DOMAIN__METAMODEL_VERSION = MDF_MODEL_ELEMENT_FEATURE_COUNT + 7;

				/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DOMAIN_FEATURE_COUNT = MDF_MODEL_ELEMENT_FEATURE_COUNT + 8;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfClassImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfClass()

	 */
    int MDF_CLASS = 4;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__DEPRECATION_INFO = MDF_ENTITY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__DOCUMENTATION = MDF_ENTITY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__NAME = MDF_ENTITY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__ANNOTATIONS = MDF_ENTITY__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__BASE_CLASS = MDF_ENTITY_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__ABSTRACT = MDF_ENTITY_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__PROPERTIES = MDF_ENTITY_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Secured</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_CLASS__SECURED = MDF_ENTITY_FEATURE_COUNT + 3;

				/**
	 * The feature id for the '<em><b>Referenceable</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS__REFERENCEABLE = MDF_ENTITY_FEATURE_COUNT + 4;

                /**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_CLASS_FEATURE_COUNT = MDF_ENTITY_FEATURE_COUNT + 5;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfPropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfPropertyImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfProperty()

	 */
    int MDF_PROPERTY = 5;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__DEPRECATION_INFO = MDF_MODEL_ELEMENT__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__DOCUMENTATION = MDF_MODEL_ELEMENT__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__NAME = MDF_MODEL_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__ANNOTATIONS = MDF_MODEL_ELEMENT__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Business Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_PROPERTY__BUSINESS_KEY = MDF_MODEL_ELEMENT_FEATURE_COUNT + 0;

				/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__REQUIRED = MDF_MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY__MULTIPLICITY = MDF_MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_PROPERTY__PRIMARY_KEY = MDF_MODEL_ELEMENT_FEATURE_COUNT + 3;

				/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PROPERTY_FEATURE_COUNT = MDF_MODEL_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfAttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfAttributeImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAttribute()

	 */
    int MDF_ATTRIBUTE = 6;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__DEPRECATION_INFO = MDF_PROPERTY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__DOCUMENTATION = MDF_PROPERTY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__NAME = MDF_PROPERTY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__ANNOTATIONS = MDF_PROPERTY__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Business Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ATTRIBUTE__BUSINESS_KEY = MDF_PROPERTY__BUSINESS_KEY;

				/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__REQUIRED = MDF_PROPERTY__REQUIRED;

    /**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__MULTIPLICITY = MDF_PROPERTY__MULTIPLICITY;

    /**
	 * The feature id for the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ATTRIBUTE__PRIMARY_KEY = MDF_PROPERTY__PRIMARY_KEY;

				/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__TYPE = MDF_PROPERTY_FEATURE_COUNT + 0;

				/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE__DEFAULT = MDF_PROPERTY_FEATURE_COUNT + 1;

				/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ATTRIBUTE_FEATURE_COUNT = MDF_PROPERTY_FEATURE_COUNT + 2;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfAssociationImpl <em>Association</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfAssociationImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAssociation()

	 */
    int MDF_ASSOCIATION = 7;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__DEPRECATION_INFO = MDF_PROPERTY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__DOCUMENTATION = MDF_PROPERTY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__NAME = MDF_PROPERTY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__ANNOTATIONS = MDF_PROPERTY__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Business Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ASSOCIATION__BUSINESS_KEY = MDF_PROPERTY__BUSINESS_KEY;

				/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__REQUIRED = MDF_PROPERTY__REQUIRED;

    /**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__MULTIPLICITY = MDF_PROPERTY__MULTIPLICITY;

    /**
	 * The feature id for the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ASSOCIATION__PRIMARY_KEY = MDF_PROPERTY__PRIMARY_KEY;

				/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__TYPE = MDF_PROPERTY_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Containment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__CONTAINMENT = MDF_PROPERTY_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Reverse</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION__REVERSE = MDF_PROPERTY_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Association</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ASSOCIATION_FEATURE_COUNT = MDF_PROPERTY_FEATURE_COUNT + 3;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfReverseAssociationImpl <em>Reverse Association</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfReverseAssociationImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfReverseAssociation()

	 */
    int MDF_REVERSE_ASSOCIATION = 8;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__DEPRECATION_INFO = MDF_PROPERTY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__DOCUMENTATION = MDF_PROPERTY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__NAME = MDF_PROPERTY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__ANNOTATIONS = MDF_PROPERTY__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Business Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_REVERSE_ASSOCIATION__BUSINESS_KEY = MDF_PROPERTY__BUSINESS_KEY;

				/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__REQUIRED = MDF_PROPERTY__REQUIRED;

    /**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_REVERSE_ASSOCIATION__MULTIPLICITY = MDF_PROPERTY__MULTIPLICITY;

    /**
	 * The feature id for the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_REVERSE_ASSOCIATION__PRIMARY_KEY = MDF_PROPERTY__PRIMARY_KEY;

	/**
	 * The feature id for the '<em><b>Reversed Association</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION = MDF_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reversed Association Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE = MDF_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Reverse Association</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_REVERSE_ASSOCIATION_FEATURE_COUNT = MDF_PROPERTY_FEATURE_COUNT + 2;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfPrimitiveImpl <em>Primitive</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfPrimitiveImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfPrimitive()

	 */
    int MDF_PRIMITIVE = 9;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PRIMITIVE__DEPRECATION_INFO = MDF_ENTITY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PRIMITIVE__DOCUMENTATION = MDF_ENTITY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PRIMITIVE__NAME = MDF_ENTITY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PRIMITIVE__ANNOTATIONS = MDF_ENTITY__ANNOTATIONS;

    /**
	 * The number of structural features of the '<em>Primitive</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_PRIMITIVE_FEATURE_COUNT = MDF_ENTITY_FEATURE_COUNT + 0;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfEnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfEnumerationImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEnumeration()

	 */
    int MDF_ENUMERATION = 10;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__DEPRECATION_INFO = MDF_PRIMITIVE__DEPRECATION_INFO;

				/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__DOCUMENTATION = MDF_PRIMITIVE__DOCUMENTATION;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__NAME = MDF_PRIMITIVE__NAME;

				/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__ANNOTATIONS = MDF_PRIMITIVE__ANNOTATIONS;

				/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUMERATION__VALUES = MDF_PRIMITIVE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__TYPE = MDF_PRIMITIVE_FEATURE_COUNT + 1;

				/**
	 * The feature id for the '<em><b>Accept Null Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ENUMERATION__ACCEPT_NULL_VALUE = MDF_PRIMITIVE_FEATURE_COUNT + 2;

				/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUMERATION_FEATURE_COUNT = MDF_PRIMITIVE_FEATURE_COUNT + 3;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfEnumValueImpl <em>Enum Value</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfEnumValueImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEnumValue()

	 */
    int MDF_ENUM_VALUE = 11;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE__DEPRECATION_INFO = MDF_MODEL_ELEMENT__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE__DOCUMENTATION = MDF_MODEL_ELEMENT__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE__NAME = MDF_MODEL_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE__ANNOTATIONS = MDF_MODEL_ELEMENT__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE__VALUE = MDF_MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Enum Value</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ENUM_VALUE_FEATURE_COUNT = MDF_MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDatasetImpl <em>Dataset</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDatasetImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDataset()

	 */
    int MDF_DATASET = 12;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__DEPRECATION_INFO = MDF_ENTITY__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__DOCUMENTATION = MDF_ENTITY__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__NAME = MDF_ENTITY__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__ANNOTATIONS = MDF_ENTITY__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__BASE_CLASS = MDF_ENTITY_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET__PROPERTIES = MDF_ENTITY_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Linked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET__LINKED = MDF_ENTITY_FEATURE_COUNT + 2;

				/**
	 * The feature id for the '<em><b>Sync</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET__SYNC = MDF_ENTITY_FEATURE_COUNT + 3;

				/**
	 * The number of structural features of the '<em>Dataset</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_FEATURE_COUNT = MDF_ENTITY_FEATURE_COUNT + 4;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl <em>Dataset Property</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetProperty()

	 */
    int MDF_DATASET_PROPERTY = 13;

    /**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_PROPERTY__DEPRECATION_INFO = MDF_MODEL_ELEMENT__DEPRECATION_INFO;

    /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_PROPERTY__DOCUMENTATION = MDF_MODEL_ELEMENT__DOCUMENTATION;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_PROPERTY__NAME = MDF_MODEL_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_PROPERTY__ANNOTATIONS = MDF_MODEL_ELEMENT__ANNOTATIONS;

    /**
	 * The number of structural features of the '<em>Dataset Property</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_DATASET_PROPERTY_FEATURE_COUNT = MDF_MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfAnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfAnnotationImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAnnotation()

	 */
    int MDF_ANNOTATION = 14;

    /**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION__NAMESPACE = 0;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION__NAME = 1;

    /**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION__PROPERTIES = 2;

    /**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl <em>Annotation Property</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAnnotationProperty()

	 */
    int MDF_ANNOTATION_PROPERTY = 15;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION_PROPERTY__NAME = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION_PROPERTY__VALUE = 1;

    /**
	 * The feature id for the '<em><b>CDATA</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_ANNOTATION_PROPERTY__CDATA = 2;

				/**
	 * The number of structural features of the '<em>Annotation Property</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    int MDF_ANNOTATION_PROPERTY_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl <em>Dataset Derived Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetDerivedProperty()

	 */
	int MDF_DATASET_DERIVED_PROPERTY = 16;

				/**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__DEPRECATION_INFO = MDF_DATASET_PROPERTY__DEPRECATION_INFO;

				/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__DOCUMENTATION = MDF_DATASET_PROPERTY__DOCUMENTATION;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__NAME = MDF_DATASET_PROPERTY__NAME;

				/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__ANNOTATIONS = MDF_DATASET_PROPERTY__ANNOTATIONS;

				/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__DEFAULT = MDF_DATASET_PROPERTY_FEATURE_COUNT + 0;

				/**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY = MDF_DATASET_PROPERTY_FEATURE_COUNT + 1;

				/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY__TYPE = MDF_DATASET_PROPERTY_FEATURE_COUNT + 2;

				/**
	 * The number of structural features of the '<em>Dataset Derived Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_DERIVED_PROPERTY_FEATURE_COUNT = MDF_DATASET_PROPERTY_FEATURE_COUNT + 3;

				/**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl <em>Dataset Mapped Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetMappedProperty()

	 */
	int MDF_DATASET_MAPPED_PROPERTY = 17;

				/**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__DEPRECATION_INFO = MDF_DATASET_PROPERTY__DEPRECATION_INFO;

				/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__DOCUMENTATION = MDF_DATASET_PROPERTY__DOCUMENTATION;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__NAME = MDF_DATASET_PROPERTY__NAME;

				/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__ANNOTATIONS = MDF_DATASET_PROPERTY__ANNOTATIONS;

				/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__PATH = MDF_DATASET_PROPERTY_FEATURE_COUNT + 0;

				/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__UNIQUE = MDF_DATASET_PROPERTY_FEATURE_COUNT + 1;

				/**
	 * The feature id for the '<em><b>Single Valued</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED = MDF_DATASET_PROPERTY_FEATURE_COUNT + 2;

				/**
	 * The feature id for the '<em><b>Link Dataset</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET = MDF_DATASET_PROPERTY_FEATURE_COUNT + 3;

				/**
	 * The number of structural features of the '<em>Dataset Mapped Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_DATASET_MAPPED_PROPERTY_FEATURE_COUNT = MDF_DATASET_PROPERTY_FEATURE_COUNT + 4;

				/**
	 * The meta object id for the '{@link com.odcgroup.mdf.ecore.MdfBusinessTypeImpl <em>Business Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.ecore.MdfBusinessTypeImpl
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfBusinessType()

	 */
	int MDF_BUSINESS_TYPE = 18;

				/**
	 * The feature id for the '<em><b>Deprecation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE__DEPRECATION_INFO = MDF_PRIMITIVE__DEPRECATION_INFO;

				/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE__DOCUMENTATION = MDF_PRIMITIVE__DOCUMENTATION;

				/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE__NAME = MDF_PRIMITIVE__NAME;

				/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE__ANNOTATIONS = MDF_PRIMITIVE__ANNOTATIONS;

				/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE__TYPE = MDF_PRIMITIVE_FEATURE_COUNT + 0;

				/**
	 * The number of structural features of the '<em>Business Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 * @ordered
	 */
	int MDF_BUSINESS_TYPE_FEATURE_COUNT = MDF_PRIMITIVE_FEATURE_COUNT + 1;

				/**
	 * The meta object id for the '{@link com.odcgroup.mdf.metamodel.MdfMultiplicity <em>Multiplicity</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.metamodel.MdfMultiplicity
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfMultiplicity()

	 */
    int MDF_MULTIPLICITY = 20;


    /**
	 * The meta object id for the '{@link com.odcgroup.mdf.metamodel.MdfContainment <em>Containment</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.odcgroup.mdf.metamodel.MdfContainment
	 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfContainment()

	 */
    int MDF_CONTAINMENT = 19;


    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDeprecationInfo <em>Deprecation Info</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deprecation Info</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDeprecationInfo

	 */
    EClass getMdfDeprecationInfo();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDeprecationInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDeprecationInfo#getVersion()
	 * @see #getMdfDeprecationInfo()

	 */
    EAttribute getMdfDeprecationInfo_Version();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDeprecationInfo#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDeprecationInfo#getComment()
	 * @see #getMdfDeprecationInfo()

	 */
    EAttribute getMdfDeprecationInfo_Comment();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement

	 */
    EClass getMdfModelElement();

    /**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.mdf.metamodel.MdfModelElement#getDeprecationInfo <em>Deprecation Info</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deprecation Info</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement#getDeprecationInfo()
	 * @see #getMdfModelElement()

	 */
    EReference getMdfModelElement_DeprecationInfo();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfModelElement#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement#getDocumentation()
	 * @see #getMdfModelElement()

	 */
    EAttribute getMdfModelElement_Documentation();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfModelElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement#getName()
	 * @see #getMdfModelElement()

	 */
    EAttribute getMdfModelElement_Name();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfModelElement#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement#getAnnotations()
	 * @see #getMdfModelElement()

	 */
    EReference getMdfModelElement_Annotations();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEntity

	 */
    EClass getMdfEntity();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain

	 */
    EClass getMdfDomain();

    /**
	 * Returns the meta object for the reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getImportedDomains <em>Imported Domains</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported Domains</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getImportedDomains()
	 * @see #getMdfDomain()

	 */
    EReference getMdfDomain_ImportedDomains();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDomain#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getNamespace()
	 * @see #getMdfDomain()

	 */
    EAttribute getMdfDomain_Namespace();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getPrimitives <em>Primitives</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Primitives</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getPrimitives()
	 * @see #getMdfDomain()

	 */
    EReference getMdfDomain_Primitives();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDomain#getMetamodelVersion <em>Metamodel Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metamodel Version</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getMetamodelVersion()
	 * @see #getMdfDomain()

	 */
	EAttribute getMdfDomain_MetamodelVersion();

				/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getClasses()
	 * @see #getMdfDomain()

	 */
    EReference getMdfDomain_Classes();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getDatasets <em>Datasets</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Datasets</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getDatasets()
	 * @see #getMdfDomain()

	 */
    EReference getMdfDomain_Datasets();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getBusinessTypes <em>Business Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Business Types</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getBusinessTypes()
	 * @see #getMdfDomain()

	 */
	EReference getMdfDomain_BusinessTypes();

				/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDomain#getEnumerations <em>Enumerations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enumerations</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain#getEnumerations()
	 * @see #getMdfDomain()

	 */
	EReference getMdfDomain_Enumerations();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfClass <em>Class</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass

	 */
    EClass getMdfClass();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfClass#getBaseClass <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass#getBaseClass()
	 * @see #getMdfClass()

	 */
    EReference getMdfClass_BaseClass();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfClass#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass#isAbstract()
	 * @see #getMdfClass()

	 */
    EAttribute getMdfClass_Abstract();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfClass#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass#getProperties()
	 * @see #getMdfClass()

	 */
    EReference getMdfClass_Properties();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfClass#isSecured <em>Secured</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secured</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass#isSecured()
	 * @see #getMdfClass()

	 */
	EAttribute getMdfClass_Secured();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfClass#isReferenceable <em>Referenceable</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenceable</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfClass#isReferenceable()
	 * @see #getMdfClass()

	 */
    EAttribute getMdfClass_Referenceable();

                /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty

	 */
    EClass getMdfProperty();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfProperty#isBusinessKey <em>Business Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Business Key</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty#isBusinessKey()
	 * @see #getMdfProperty()

	 */
	EAttribute getMdfProperty_BusinessKey();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfProperty#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty#isRequired()
	 * @see #getMdfProperty()

	 */
    EAttribute getMdfProperty_Required();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfProperty#getMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplicity</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty#getMultiplicity()
	 * @see #getMdfProperty()

	 */
    EAttribute getMdfProperty_Multiplicity();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfProperty#isPrimaryKey <em>Primary Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Key</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty#isPrimaryKey()
	 * @see #getMdfProperty()

	 */
	EAttribute getMdfProperty_PrimaryKey();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAttribute

	 */
    EClass getMdfAttribute();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfAttribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAttribute#getType()
	 * @see #getMdfAttribute()

	 */
    EReference getMdfAttribute_Type();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAttribute#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAttribute#getDefault()
	 * @see #getMdfAttribute()

	 */
    EAttribute getMdfAttribute_Default();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfAssociation <em>Association</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAssociation

	 */
    EClass getMdfAssociation();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfAssociation#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAssociation#getType()
	 * @see #getMdfAssociation()

	 */
    EReference getMdfAssociation_Type();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAssociation#getContainment <em>Containment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Containment</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAssociation#getContainment()
	 * @see #getMdfAssociation()

	 */
    EAttribute getMdfAssociation_Containment();

    /**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.mdf.metamodel.MdfAssociation#getReverse <em>Reverse</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reverse</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAssociation#getReverse()
	 * @see #getMdfAssociation()

	 */
    EReference getMdfAssociation_Reverse();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfReverseAssociation <em>Reverse Association</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reverse Association</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfReverseAssociation

	 */
    EClass getMdfReverseAssociation();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfReverseAssociation#getReversedAssociation <em>Reversed Association</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reversed Association</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfReverseAssociation#getReversedAssociation()
	 * @see #getMdfReverseAssociation()

	 */
	EReference getMdfReverseAssociation_ReversedAssociation();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfReverseAssociation#getReversedAssociationType <em>Reversed Association Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reversed Association Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfReverseAssociation#getReversedAssociationType()
	 * @see #getMdfReverseAssociation()
	 * @generated
	 */
	EReference getMdfReverseAssociation_ReversedAssociationType();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfPrimitive <em>Primitive</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfPrimitive

	 */
    EClass getMdfPrimitive();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfEnumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumeration

	 */
    EClass getMdfEnumeration();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfEnumeration#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumeration#getValues()
	 * @see #getMdfEnumeration()

	 */
    EReference getMdfEnumeration_Values();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfEnumeration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumeration#getType()
	 * @see #getMdfEnumeration()

	 */
	EReference getMdfEnumeration_Type();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfEnumeration#isAcceptNullValue <em>Accept Null Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Accept Null Value</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumeration#isAcceptNullValue()
	 * @see #getMdfEnumeration()

	 */
	EAttribute getMdfEnumeration_AcceptNullValue();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfEnumValue <em>Enum Value</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Value</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumValue

	 */
    EClass getMdfEnumValue();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfEnumValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumValue#getValue()
	 * @see #getMdfEnumValue()

	 */
    EAttribute getMdfEnumValue_Value();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDataset <em>Dataset</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dataset</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset

	 */
    EClass getMdfDataset();

    /**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfDataset#getBaseClass <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset#getBaseClass()
	 * @see #getMdfDataset()

	 */
    EReference getMdfDataset_BaseClass();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfDataset#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset#getProperties()
	 * @see #getMdfDataset()

	 */
    EReference getMdfDataset_Properties();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDataset#isLinked <em>Linked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Linked</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset#isLinked()
	 * @see #getMdfDataset()

	 */
	EAttribute getMdfDataset_Linked();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDataset#isSync <em>Sync</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset#isSync()
	 * @see #getMdfDataset()

	 */
	EAttribute getMdfDataset_Sync();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDatasetProperty <em>Dataset Property</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dataset Property</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetProperty

	 */
    EClass getMdfDatasetProperty();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfAnnotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotation

	 */
    EClass getMdfAnnotation();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAnnotation#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotation#getNamespace()
	 * @see #getMdfAnnotation()

	 */
    EAttribute getMdfAnnotation_Namespace();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAnnotation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotation#getName()
	 * @see #getMdfAnnotation()

	 */
    EAttribute getMdfAnnotation_Name();

    /**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.mdf.metamodel.MdfAnnotation#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotation#getProperties()
	 * @see #getMdfAnnotation()

	 */
    EReference getMdfAnnotation_Properties();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfAnnotationProperty <em>Annotation Property</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Property</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotationProperty

	 */
    EClass getMdfAnnotationProperty();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAnnotationProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotationProperty#getName()
	 * @see #getMdfAnnotationProperty()

	 */
    EAttribute getMdfAnnotationProperty_Name();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAnnotationProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotationProperty#getValue()
	 * @see #getMdfAnnotationProperty()

	 */
    EAttribute getMdfAnnotationProperty_Value();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfAnnotationProperty#isCDATA <em>CDATA</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>CDATA</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotationProperty#isCDATA()
	 * @see #getMdfAnnotationProperty()

	 */
	EAttribute getMdfAnnotationProperty_CDATA();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty <em>Dataset Derived Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dataset Derived Property</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty

	 */
	EClass getMdfDatasetDerivedProperty();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getDefault()
	 * @see #getMdfDatasetDerivedProperty()

	 */
	EAttribute getMdfDatasetDerivedProperty_Default();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplicity</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getMultiplicity()
	 * @see #getMdfDatasetDerivedProperty()

	 */
	EAttribute getMdfDatasetDerivedProperty_Multiplicity();

				/**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty#getType()
	 * @see #getMdfDatasetDerivedProperty()

	 */
	EReference getMdfDatasetDerivedProperty_Type();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty <em>Dataset Mapped Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dataset Mapped Property</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty

	 */
	EClass getMdfDatasetMappedProperty();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#getPath()
	 * @see #getMdfDatasetMappedProperty()

	 */
	EAttribute getMdfDatasetMappedProperty_Path();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#isUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#isUnique()
	 * @see #getMdfDatasetMappedProperty()

	 */
	EAttribute getMdfDatasetMappedProperty_Unique();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#isSingleValued <em>Single Valued</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Single Valued</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#isSingleValued()
	 * @see #getMdfDatasetMappedProperty()

	 */
	EAttribute getMdfDatasetMappedProperty_SingleValued();

				/**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#getLinkDataset <em>Link Dataset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Link Dataset</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty#getLinkDataset()
	 * @see #getMdfDatasetMappedProperty()

	 */
	EReference getMdfDatasetMappedProperty_LinkDataset();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.mdf.metamodel.MdfBusinessType <em>Business Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfBusinessType

	 */
	EClass getMdfBusinessType();

				/**
	 * Returns the meta object for the reference '{@link com.odcgroup.mdf.metamodel.MdfBusinessType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfBusinessType#getType()
	 * @see #getMdfBusinessType()

	 */
	EReference getMdfBusinessType_Type();

				/**
	 * Returns the meta object for enum '{@link com.odcgroup.mdf.metamodel.MdfMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Multiplicity</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfMultiplicity

	 */
    EEnum getMdfMultiplicity();

    /**
	 * Returns the meta object for enum '{@link com.odcgroup.mdf.metamodel.MdfContainment <em>Containment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Containment</em>'.
	 * @see com.odcgroup.mdf.metamodel.MdfContainment

	 */
    EEnum getMdfContainment();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.

	 */
    MdfFactory getMdfFactory();

    /**
	 * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->

	 */
    interface Literals  {
        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDeprecationInfoImpl <em>Deprecation Info</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDeprecationInfoImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDeprecationInfo()
	
		 */
        EClass MDF_DEPRECATION_INFO = eINSTANCE.getMdfDeprecationInfo();

        /**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_DEPRECATION_INFO__VERSION = eINSTANCE.getMdfDeprecationInfo_Version();

        /**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_DEPRECATION_INFO__COMMENT = eINSTANCE.getMdfDeprecationInfo_Comment();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfModelElementImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfModelElement()
	
		 */
        EClass MDF_MODEL_ELEMENT = eINSTANCE.getMdfModelElement();

        /**
		 * The meta object literal for the '<em><b>Deprecation Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_MODEL_ELEMENT__DEPRECATION_INFO = eINSTANCE.getMdfModelElement_DeprecationInfo();

        /**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_MODEL_ELEMENT__DOCUMENTATION = eINSTANCE.getMdfModelElement_Documentation();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_MODEL_ELEMENT__NAME = eINSTANCE.getMdfModelElement_Name();

        /**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_MODEL_ELEMENT__ANNOTATIONS = eINSTANCE.getMdfModelElement_Annotations();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfEntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfEntityImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEntity()
	
		 */
        EClass MDF_ENTITY = eINSTANCE.getMdfEntity();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDomainImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDomain()
	
		 */
        EClass MDF_DOMAIN = eINSTANCE.getMdfDomain();

        /**
		 * The meta object literal for the '<em><b>Imported Domains</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DOMAIN__IMPORTED_DOMAINS = eINSTANCE.getMdfDomain_ImportedDomains();

        /**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_DOMAIN__NAMESPACE = eINSTANCE.getMdfDomain_Namespace();

        /**
		 * The meta object literal for the '<em><b>Primitives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DOMAIN__PRIMITIVES = eINSTANCE.getMdfDomain_Primitives();

        /**
		 * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DOMAIN__METAMODEL_VERSION = eINSTANCE.getMdfDomain_MetamodelVersion();

								/**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DOMAIN__CLASSES = eINSTANCE.getMdfDomain_Classes();

        /**
		 * The meta object literal for the '<em><b>Datasets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DOMAIN__DATASETS = eINSTANCE.getMdfDomain_Datasets();

        /**
		 * The meta object literal for the '<em><b>Business Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_DOMAIN__BUSINESS_TYPES = eINSTANCE.getMdfDomain_BusinessTypes();

								/**
		 * The meta object literal for the '<em><b>Enumerations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_DOMAIN__ENUMERATIONS = eINSTANCE.getMdfDomain_Enumerations();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfClassImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfClass()
	
		 */
        EClass MDF_CLASS = eINSTANCE.getMdfClass();

        /**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_CLASS__BASE_CLASS = eINSTANCE.getMdfClass_BaseClass();

        /**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_CLASS__ABSTRACT = eINSTANCE.getMdfClass_Abstract();

        /**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_CLASS__PROPERTIES = eINSTANCE.getMdfClass_Properties();

        /**
		 * The meta object literal for the '<em><b>Secured</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_CLASS__SECURED = eINSTANCE.getMdfClass_Secured();

								/**
		 * The meta object literal for the '<em><b>Referenceable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_CLASS__REFERENCEABLE = eINSTANCE.getMdfClass_Referenceable();

                                /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfPropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfPropertyImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfProperty()
	
		 */
        EClass MDF_PROPERTY = eINSTANCE.getMdfProperty();

        /**
		 * The meta object literal for the '<em><b>Business Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_PROPERTY__BUSINESS_KEY = eINSTANCE.getMdfProperty_BusinessKey();

								/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_PROPERTY__REQUIRED = eINSTANCE.getMdfProperty_Required();

        /**
		 * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_PROPERTY__MULTIPLICITY = eINSTANCE.getMdfProperty_Multiplicity();

        /**
		 * The meta object literal for the '<em><b>Primary Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_PROPERTY__PRIMARY_KEY = eINSTANCE.getMdfProperty_PrimaryKey();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfAttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfAttributeImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAttribute()
	
		 */
        EClass MDF_ATTRIBUTE = eINSTANCE.getMdfAttribute();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_ATTRIBUTE__TYPE = eINSTANCE.getMdfAttribute_Type();

        /**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ATTRIBUTE__DEFAULT = eINSTANCE.getMdfAttribute_Default();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfAssociationImpl <em>Association</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfAssociationImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAssociation()
	
		 */
        EClass MDF_ASSOCIATION = eINSTANCE.getMdfAssociation();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_ASSOCIATION__TYPE = eINSTANCE.getMdfAssociation_Type();

        /**
		 * The meta object literal for the '<em><b>Containment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ASSOCIATION__CONTAINMENT = eINSTANCE.getMdfAssociation_Containment();

        /**
		 * The meta object literal for the '<em><b>Reverse</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_ASSOCIATION__REVERSE = eINSTANCE.getMdfAssociation_Reverse();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfReverseAssociationImpl <em>Reverse Association</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfReverseAssociationImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfReverseAssociation()
	
		 */
        EClass MDF_REVERSE_ASSOCIATION = eINSTANCE.getMdfReverseAssociation();

        /**
		 * The meta object literal for the '<em><b>Reversed Association</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION = eINSTANCE.getMdfReverseAssociation_ReversedAssociation();

		/**
		 * The meta object literal for the '<em><b>Reversed Association Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE = eINSTANCE.getMdfReverseAssociation_ReversedAssociationType();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfPrimitiveImpl <em>Primitive</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfPrimitiveImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfPrimitive()
	
		 */
        EClass MDF_PRIMITIVE = eINSTANCE.getMdfPrimitive();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfEnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfEnumerationImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEnumeration()
	
		 */
        EClass MDF_ENUMERATION = eINSTANCE.getMdfEnumeration();

        /**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_ENUMERATION__VALUES = eINSTANCE.getMdfEnumeration_Values();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_ENUMERATION__TYPE = eINSTANCE.getMdfEnumeration_Type();

								/**
		 * The meta object literal for the '<em><b>Accept Null Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_ENUMERATION__ACCEPT_NULL_VALUE = eINSTANCE.getMdfEnumeration_AcceptNullValue();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfEnumValueImpl <em>Enum Value</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfEnumValueImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfEnumValue()
	
		 */
        EClass MDF_ENUM_VALUE = eINSTANCE.getMdfEnumValue();

        /**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ENUM_VALUE__VALUE = eINSTANCE.getMdfEnumValue_Value();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDatasetImpl <em>Dataset</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDatasetImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDataset()
	
		 */
        EClass MDF_DATASET = eINSTANCE.getMdfDataset();

        /**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DATASET__BASE_CLASS = eINSTANCE.getMdfDataset_BaseClass();

        /**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_DATASET__PROPERTIES = eINSTANCE.getMdfDataset_Properties();

        /**
		 * The meta object literal for the '<em><b>Linked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET__LINKED = eINSTANCE.getMdfDataset_Linked();

								/**
		 * The meta object literal for the '<em><b>Sync</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET__SYNC = eINSTANCE.getMdfDataset_Sync();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl <em>Dataset Property</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetProperty()
	
		 */
        EClass MDF_DATASET_PROPERTY = eINSTANCE.getMdfDatasetProperty();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfAnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfAnnotationImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAnnotation()
	
		 */
        EClass MDF_ANNOTATION = eINSTANCE.getMdfAnnotation();

        /**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ANNOTATION__NAMESPACE = eINSTANCE.getMdfAnnotation_Namespace();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ANNOTATION__NAME = eINSTANCE.getMdfAnnotation_Name();

        /**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EReference MDF_ANNOTATION__PROPERTIES = eINSTANCE.getMdfAnnotation_Properties();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl <em>Annotation Property</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfAnnotationProperty()
	
		 */
        EClass MDF_ANNOTATION_PROPERTY = eINSTANCE.getMdfAnnotationProperty();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ANNOTATION_PROPERTY__NAME = eINSTANCE.getMdfAnnotationProperty_Name();

        /**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	
		 */
        EAttribute MDF_ANNOTATION_PROPERTY__VALUE = eINSTANCE.getMdfAnnotationProperty_Value();

        /**
		 * The meta object literal for the '<em><b>CDATA</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_ANNOTATION_PROPERTY__CDATA = eINSTANCE.getMdfAnnotationProperty_CDATA();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl <em>Dataset Derived Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetDerivedProperty()
	
		 */
		EClass MDF_DATASET_DERIVED_PROPERTY = eINSTANCE.getMdfDatasetDerivedProperty();

								/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET_DERIVED_PROPERTY__DEFAULT = eINSTANCE.getMdfDatasetDerivedProperty_Default();

								/**
		 * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY = eINSTANCE.getMdfDatasetDerivedProperty_Multiplicity();

								/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_DATASET_DERIVED_PROPERTY__TYPE = eINSTANCE.getMdfDatasetDerivedProperty_Type();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl <em>Dataset Mapped Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfDatasetMappedProperty()
	
		 */
		EClass MDF_DATASET_MAPPED_PROPERTY = eINSTANCE.getMdfDatasetMappedProperty();

								/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET_MAPPED_PROPERTY__PATH = eINSTANCE.getMdfDatasetMappedProperty_Path();

								/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET_MAPPED_PROPERTY__UNIQUE = eINSTANCE.getMdfDatasetMappedProperty_Unique();

								/**
		 * The meta object literal for the '<em><b>Single Valued</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EAttribute MDF_DATASET_MAPPED_PROPERTY__SINGLE_VALUED = eINSTANCE.getMdfDatasetMappedProperty_SingleValued();

								/**
		 * The meta object literal for the '<em><b>Link Dataset</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET = eINSTANCE.getMdfDatasetMappedProperty_LinkDataset();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.ecore.MdfBusinessTypeImpl <em>Business Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.ecore.MdfBusinessTypeImpl
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfBusinessType()
	
		 */
		EClass MDF_BUSINESS_TYPE = eINSTANCE.getMdfBusinessType();

								/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
	
		 */
		EReference MDF_BUSINESS_TYPE__TYPE = eINSTANCE.getMdfBusinessType_Type();

								/**
		 * The meta object literal for the '{@link com.odcgroup.mdf.metamodel.MdfMultiplicity <em>Multiplicity</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.metamodel.MdfMultiplicity
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfMultiplicity()
	
		 */
        EEnum MDF_MULTIPLICITY = eINSTANCE.getMdfMultiplicity();

        /**
		 * The meta object literal for the '{@link com.odcgroup.mdf.metamodel.MdfContainment <em>Containment</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see com.odcgroup.mdf.metamodel.MdfContainment
		 * @see com.odcgroup.mdf.ecore.MdfPackageImpl#getMdfContainment()
	
		 */
        EEnum MDF_CONTAINMENT = eINSTANCE.getMdfContainment();

    }

} //MdfPackage
