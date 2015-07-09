/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

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
 * @see com.odcgroup.page.metamodel.MetaModelFactory
 * @model kind="package"
 * @generated
 */
public interface MetaModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "metamodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/page/metamodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.odcgroup.page.metamodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MetaModelPackage eINSTANCE = com.odcgroup.page.metamodel.impl.MetaModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl <em>Named Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.NamedTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getNamedType()
	 * @generated
	 */
	int NAMED_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE__TRANSLATION_SUPPORT = 2;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE__PROPERTIES_HELP_ID = 3;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE__CONCEPT_HELP_ID = 4;

	/**
	 * The number of structural features of the '<em>Named Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl <em>Widget Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.WidgetTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetType()
	 * @generated
	 */
	int WIDGET_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__PARENT = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__PROPERTY_TYPES = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__LIBRARY = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Excluded Property Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__DISPLAY_NAME = NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Property Filter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__PROPERTY_FILTER_CLASS = NAMED_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Derivable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__DERIVABLE = NAMED_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Strict Accountability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__STRICT_ACCOUNTABILITY = NAMED_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Domain Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__DOMAIN_WIDGET = NAMED_TYPE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Can Drop Domain Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT = NAMED_TYPE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Richtext</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE__RICHTEXT = NAMED_TYPE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Widget Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl <em>Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.PropertyTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__DEFAULT_VALUE = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__DATA_TYPE = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__LIBRARY = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__CATEGORY = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__DISPLAY_NAME = NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Source Adapter</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_TYPE__SOURCE_ADAPTER = NAMED_TYPE_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>Sub Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__SUB_CATEGORY = NAMED_TYPE_FEATURE_COUNT + 6;

				/**
	 * The feature id for the '<em><b>Visible In Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__VISIBLE_IN_DOMAIN = NAMED_TYPE_FEATURE_COUNT + 7;

				/**
	 * The number of structural features of the '<em>Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl <em>Widget Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.WidgetTemplateImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetTemplate()
	 * @generated
	 */
	int WIDGET_TEMPLATE = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl <em>Accountability Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getAccountabilityRule()
	 * @generated
	 */
	int ACCOUNTABILITY_RULE = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.WidgetLibraryImpl <em>Widget Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.WidgetLibraryImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetLibrary()
	 * @generated
	 */
	int WIDGET_LIBRARY = 5;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.AccountabilityImpl <em>Accountability</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.AccountabilityImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getAccountability()
	 * @generated
	 */
	int ACCOUNTABILITY = 6;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.PropertyValueConverter <em>Property Value Converter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.PropertyValueConverter
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyValueConverter()
	 * @generated
	 */
	int PROPERTY_VALUE_CONVERTER = 7;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.DataTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 8;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.DataValueImpl <em>Data Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.DataValueImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataValue()
	 * @generated
	 */
	int DATA_VALUE = 10;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.MetaModelImpl <em>Meta Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.MetaModelImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getMetaModel()
	 * @generated
	 */
	int META_MODEL = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Widget Libraries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__WIDGET_LIBRARIES = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__VERSION = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Containability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__CONTAINABILITY = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Includeability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__INCLUDEABILITY = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Data Types</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__DATA_TYPES = NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Event Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__EVENT_MODEL = NAMED_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Snippet Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL__SNIPPET_MODEL = NAMED_TYPE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Meta Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_MODEL_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__TYPE = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__CONTENTS = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__PROPERTY_TEMPLATES = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Event Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE__EVENT_TEMPLATES = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Widget Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TEMPLATE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Child</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE__CHILD = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE__PARENT = 1;

	/**
	 * The feature id for the '<em><b>Min Occurs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE__MIN_OCCURS = 2;

	/**
	 * The feature id for the '<em><b>Max Occurs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE__MAX_OCCURS = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE__NAME = 4;

	/**
	 * The number of structural features of the '<em>Accountability Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_RULE_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Widget Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__WIDGET_TYPES = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__PROPERTY_TYPES = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Widget Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY__WIDGET_TEMPLATES = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Widget Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_LIBRARY_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY__RULES = 0;

	/**
	 * The number of structural features of the '<em>Accountability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTABILITY_FEATURE_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Property Value Converter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_VALUE_CONVERTER_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__VALUES = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Editor Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__EDITOR_NAME = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Validator Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__VALIDATOR_NAME = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Converter Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__CONVERTER_NAME = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Editable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__EDITABLE = NAMED_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Ordinal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE__ORDINAL = 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_VALUE__DISPLAY_NAME = 2;

    /**
	 * The number of structural features of the '<em>Data Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.PropertyTemplateImpl <em>Property Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.PropertyTemplateImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyTemplate()
	 * @generated
	 */
	int PROPERTY_TEMPLATE = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TEMPLATE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TEMPLATE__READONLY = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TEMPLATE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Property Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TEMPLATE_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.DataTypesImpl <em>Data Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.DataTypesImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataTypes()
	 * @generated
	 */
	int DATA_TYPES = 12;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Data Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPES_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl <em>Includeability Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getIncludeabilityRule()
	 * @generated
	 */
	int INCLUDEABILITY_RULE = 13;

	/**
	 * The feature id for the '<em><b>Child</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__CHILD = ACCOUNTABILITY_RULE__CHILD;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__PARENT = ACCOUNTABILITY_RULE__PARENT;

	/**
	 * The feature id for the '<em><b>Min Occurs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__MIN_OCCURS = ACCOUNTABILITY_RULE__MIN_OCCURS;

	/**
	 * The feature id for the '<em><b>Max Occurs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__MAX_OCCURS = ACCOUNTABILITY_RULE__MAX_OCCURS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__NAME = ACCOUNTABILITY_RULE__NAME;

	/**
	 * The feature id for the '<em><b>Inclusion Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__INCLUSION_TYPE = ACCOUNTABILITY_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Limited Accountability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY = ACCOUNTABILITY_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Includeability Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDEABILITY_RULE_FEATURE_COUNT = ACCOUNTABILITY_RULE_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.EventModelImpl <em>Event Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.EventModelImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventModel()
	 * @generated
	 */
	int EVENT_MODEL = 14;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_MODEL__FUNCTIONS = 0;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_MODEL__EVENTS = 1;

	/**
	 * The feature id for the '<em><b>Event Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_MODEL__EVENT_TYPES = 2;

	/**
	 * The number of structural features of the '<em>Event Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_MODEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.WidgetEventImpl <em>Widget Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.WidgetEventImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetEvent()
	 * @generated
	 */
	int WIDGET_EVENT = 15;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_EVENT__WIDGET_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Event Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_EVENT__EVENT_TYPES = 1;

	/**
	 * The number of structural features of the '<em>Widget Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_EVENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.FunctionTypeImpl <em>Function Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.FunctionTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getFunctionType()
	 * @generated
	 */
	int FUNCTION_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__PARAMETERS = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Event Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__EVENT_TYPES = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Allow User Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__ALLOW_USER_PARAMETERS = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.ParameterTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getParameterType()
	 * @generated
	 */
	int PARAMETER_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__DEFAULT_VALUE = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__TYPE = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl <em>Event Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.EventTemplateImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventTemplate()
	 * @generated
	 */
	int EVENT_TEMPLATE = 18;

	/**
	 * The feature id for the '<em><b>Parameter Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TEMPLATE__PARAMETER_TEMPLATES = 0;

	/**
	 * The feature id for the '<em><b>Function Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TEMPLATE__FUNCTION_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Event Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TEMPLATE__EVENT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TEMPLATE__NATURE = 3;

	/**
	 * The number of structural features of the '<em>Event Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TEMPLATE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl <em>Parameter Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.ParameterTemplateImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getParameterTemplate()
	 * @generated
	 */
	int PARAMETER_TEMPLATE = 19;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TEMPLATE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TEMPLATE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TEMPLATE__USER_DEFINED = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TEMPLATE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Parameter Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TEMPLATE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.InclusionType <em>Inclusion Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.InclusionType
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getInclusionType()
	 * @generated
	 */
	int INCLUSION_TYPE = 25;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.PropertyCategory <em>Property Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.PropertyCategory
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyCategory()
	 * @generated
	 */
	int PROPERTY_CATEGORY = 26;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.TranslationSupport <em>Translation Support</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.TranslationSupport
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getTranslationSupport()
	 * @generated
	 */
	int TRANSLATION_SUPPORT = 27;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.EventTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 20;


	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Property Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__PROPERTY_TYPES = 1;

	/**
	 * The number of structural features of the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.SnippetTypeImpl <em>Snippet Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.SnippetTypeImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getSnippetType()
	 * @generated
	 */
	int SNIPPET_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__NAME = NAMED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__DESCRIPTION = NAMED_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Translation Support</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__TRANSLATION_SUPPORT = NAMED_TYPE__TRANSLATION_SUPPORT;

	/**
	 * The feature id for the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__PROPERTIES_HELP_ID = NAMED_TYPE__PROPERTIES_HELP_ID;

	/**
	 * The feature id for the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__CONCEPT_HELP_ID = NAMED_TYPE__CONCEPT_HELP_ID;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__PARENT = NAMED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE__PROPERTY_TYPES = NAMED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Snippet Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TYPE_FEATURE_COUNT = NAMED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl <em>Snippet Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.SnippetModelImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getSnippetModel()
	 * @generated
	 */
	int SNIPPET_MODEL = 22;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_MODEL__SNIPPETS = 0;

	/**
	 * The feature id for the '<em><b>Property Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_MODEL__PROPERTY_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Widgets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_MODEL__WIDGETS = 2;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_MODEL__EVENTS = 3;

	/**
	 * The number of structural features of the '<em>Snippet Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_MODEL_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.WidgetSnippetImpl <em>Widget Snippet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.WidgetSnippetImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetSnippet()
	 * @generated
	 */
	int WIDGET_SNIPPET = 23;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_SNIPPET__WIDGET_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_SNIPPET__SNIPPETS = 1;

	/**
	 * The number of structural features of the '<em>Widget Snippet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_SNIPPET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.metamodel.impl.EventSnippetImpl <em>Event Snippet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.metamodel.impl.EventSnippetImpl
	 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventSnippet()
	 * @generated
	 */
	int EVENT_SNIPPET = 24;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_SNIPPET__SNIPPETS = 0;

	/**
	 * The number of structural features of the '<em>Event Snippet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_SNIPPET_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.WidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType
	 * @generated
	 */
	EClass getWidgetType();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.WidgetType#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getParent()
	 * @see #getWidgetType()
	 * @generated
	 */
	EReference getWidgetType_Parent();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.WidgetType#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getPropertyTypes()
	 * @see #getWidgetType()
	 * @generated
	 */
	EReference getWidgetType_PropertyTypes();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.metamodel.WidgetType#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getLibrary()
	 * @see #getWidgetType()
	 * @generated
	 */
	EReference getWidgetType_Library();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.WidgetType#getExcludedPropertyTypes <em>Excluded Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getExcludedPropertyTypes()
	 * @see #getWidgetType()
	 * @generated
	 */
	EReference getWidgetType_ExcludedPropertyTypes();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getDisplayName()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#getPropertyFilterClass <em>Property Filter Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Filter Class</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#getPropertyFilterClass()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_PropertyFilterClass();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#isDerivable <em>Derivable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Derivable</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#isDerivable()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_Derivable();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#isStrictAccountability <em>Strict Accountability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict Accountability</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#isStrictAccountability()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_StrictAccountability();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#isDomainWidget <em>Domain Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Widget</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#isDomainWidget()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_DomainWidget();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#isCanDropDomainElement <em>Can Drop Domain Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can Drop Domain Element</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#isCanDropDomainElement()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_CanDropDomainElement();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.WidgetType#isRichtext <em>Richtext</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Richtext</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetType#isRichtext()
	 * @see #getWidgetType()
	 * @generated
	 */
	EAttribute getWidgetType_Richtext();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Type</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType
	 * @generated
	 */
	EClass getPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getDefaultValue()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_DefaultValue();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.PropertyType#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Type</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getDataType()
	 * @see #getPropertyType()
	 * @generated
	 */
	EReference getPropertyType_DataType();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.metamodel.PropertyType#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getLibrary()
	 * @see #getPropertyType()
	 * @generated
	 */
	EReference getPropertyType_Library();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getCategory()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_Category();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getDisplayName()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#getSourceAdapter <em>Source Adapter</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Adapter</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getSourceAdapter()
	 * @see #getPropertyType()
	 * @generated
	 */
    EAttribute getPropertyType_SourceAdapter();

    /**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#getSubCategory <em>Sub Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sub Category</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#getSubCategory()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_SubCategory();

				/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyType#isVisibleInDomain <em>Visible In Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible In Domain</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyType#isVisibleInDomain()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_VisibleInDomain();

				/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.WidgetTemplate <em>Widget Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Template</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate
	 * @generated
	 */
	EClass getWidgetTemplate();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.WidgetTemplate#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate#getType()
	 * @see #getWidgetTemplate()
	 * @generated
	 */
	EReference getWidgetTemplate_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetTemplate#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate#getContents()
	 * @see #getWidgetTemplate()
	 * @generated
	 */
	EReference getWidgetTemplate_Contents();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetTemplate#getPropertyTemplates <em>Property Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Templates</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate#getPropertyTemplates()
	 * @see #getWidgetTemplate()
	 * @generated
	 */
	EReference getWidgetTemplate_PropertyTemplates();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetTemplate#getEventTemplates <em>Event Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Templates</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate#getEventTemplates()
	 * @see #getWidgetTemplate()
	 * @generated
	 */
	EReference getWidgetTemplate_EventTemplates();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.AccountabilityRule <em>Accountability Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Accountability Rule</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule
	 * @generated
	 */
	EClass getAccountabilityRule();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.AccountabilityRule#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Child</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule#getChild()
	 * @see #getAccountabilityRule()
	 * @generated
	 */
	EReference getAccountabilityRule_Child();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.AccountabilityRule#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule#getParent()
	 * @see #getAccountabilityRule()
	 * @generated
	 */
	EReference getAccountabilityRule_Parent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.AccountabilityRule#getMinOccurs <em>Min Occurs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Occurs</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule#getMinOccurs()
	 * @see #getAccountabilityRule()
	 * @generated
	 */
	EAttribute getAccountabilityRule_MinOccurs();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.AccountabilityRule#getMaxOccurs <em>Max Occurs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Occurs</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule#getMaxOccurs()
	 * @see #getAccountabilityRule()
	 * @generated
	 */
	EAttribute getAccountabilityRule_MaxOccurs();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.AccountabilityRule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule#getName()
	 * @see #getAccountabilityRule()
	 * @generated
	 */
	EAttribute getAccountabilityRule_Name();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.WidgetLibrary <em>Widget Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Library</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetLibrary
	 * @generated
	 */
	EClass getWidgetLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTypes <em>Widget Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Widget Types</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTypes()
	 * @see #getWidgetLibrary()
	 * @generated
	 */
	EReference getWidgetLibrary_WidgetTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetLibrary#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetLibrary#getPropertyTypes()
	 * @see #getWidgetLibrary()
	 * @generated
	 */
	EReference getWidgetLibrary_PropertyTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTemplates <em>Widget Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Widget Templates</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTemplates()
	 * @see #getWidgetLibrary()
	 * @generated
	 */
	EReference getWidgetLibrary_WidgetTemplates();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.Accountability <em>Accountability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Accountability</em>'.
	 * @see com.odcgroup.page.metamodel.Accountability
	 * @generated
	 */
	EClass getAccountability();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.Accountability#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see com.odcgroup.page.metamodel.Accountability#getRules()
	 * @see #getAccountability()
	 * @generated
	 */
	EReference getAccountability_Rules();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.PropertyValueConverter <em>Property Value Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Value Converter</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyValueConverter
	 * @generated
	 */
	EClass getPropertyValueConverter();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see com.odcgroup.page.metamodel.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.DataType#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see com.odcgroup.page.metamodel.DataType#getValues()
	 * @see #getDataType()
	 * @generated
	 */
	EReference getDataType_Values();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataType#getEditorName <em>Editor Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editor Name</em>'.
	 * @see com.odcgroup.page.metamodel.DataType#getEditorName()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_EditorName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataType#getValidatorName <em>Validator Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validator Name</em>'.
	 * @see com.odcgroup.page.metamodel.DataType#getValidatorName()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_ValidatorName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataType#getConverterName <em>Converter Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter Name</em>'.
	 * @see com.odcgroup.page.metamodel.DataType#getConverterName()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_ConverterName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataType#isEditable <em>Editable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editable</em>'.
	 * @see com.odcgroup.page.metamodel.DataType#isEditable()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_Editable();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.NamedType <em>Named Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Type</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType
	 * @generated
	 */
	EClass getNamedType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.NamedType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType#getName()
	 * @see #getNamedType()
	 * @generated
	 */
	EAttribute getNamedType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.NamedType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType#getDescription()
	 * @see #getNamedType()
	 * @generated
	 */
	EAttribute getNamedType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.NamedType#getTranslationSupport <em>Translation Support</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Translation Support</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType#getTranslationSupport()
	 * @see #getNamedType()
	 * @generated
	 */
	EAttribute getNamedType_TranslationSupport();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.NamedType#getPropertiesHelpID <em>Properties Help ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties Help ID</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType#getPropertiesHelpID()
	 * @see #getNamedType()
	 * @generated
	 */
	EAttribute getNamedType_PropertiesHelpID();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.NamedType#getConceptHelpID <em>Concept Help ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Concept Help ID</em>'.
	 * @see com.odcgroup.page.metamodel.NamedType#getConceptHelpID()
	 * @see #getNamedType()
	 * @generated
	 */
	EAttribute getNamedType_ConceptHelpID();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.DataValue <em>Data Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Value</em>'.
	 * @see com.odcgroup.page.metamodel.DataValue
	 * @generated
	 */
	EClass getDataValue();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.page.metamodel.DataValue#getValue()
	 * @see #getDataValue()
	 * @generated
	 */
	EAttribute getDataValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataValue#getOrdinal <em>Ordinal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ordinal</em>'.
	 * @see com.odcgroup.page.metamodel.DataValue#getOrdinal()
	 * @see #getDataValue()
	 * @generated
	 */
	EAttribute getDataValue_Ordinal();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.DataValue#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.odcgroup.page.metamodel.DataValue#getDisplayName()
	 * @see #getDataValue()
	 * @generated
	 */
    EAttribute getDataValue_DisplayName();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.MetaModel <em>Meta Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Model</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel
	 * @generated
	 */
	EClass getMetaModel();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.MetaModel#getWidgetLibraries <em>Widget Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Widget Libraries</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getWidgetLibraries()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_WidgetLibraries();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.MetaModel#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getVersion()
	 * @see #getMetaModel()
	 * @generated
	 */
	EAttribute getMetaModel_Version();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.MetaModel#getDataTypes <em>Data Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Types</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getDataTypes()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_DataTypes();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.metamodel.MetaModel#getEventModel <em>Event Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event Model</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getEventModel()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_EventModel();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.metamodel.MetaModel#getSnippetModel <em>Snippet Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Snippet Model</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getSnippetModel()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_SnippetModel();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.metamodel.MetaModel#getContainability <em>Containability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Containability</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getContainability()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_Containability();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.metamodel.MetaModel#getIncludeability <em>Includeability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Includeability</em>'.
	 * @see com.odcgroup.page.metamodel.MetaModel#getIncludeability()
	 * @see #getMetaModel()
	 * @generated
	 */
	EReference getMetaModel_Includeability();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.PropertyTemplate <em>Property Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Template</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyTemplate
	 * @generated
	 */
	EClass getPropertyTemplate();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyTemplate#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyTemplate#getValue()
	 * @see #getPropertyTemplate()
	 * @generated
	 */
	EAttribute getPropertyTemplate_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.PropertyTemplate#isReadonly <em>Readonly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Readonly</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyTemplate#isReadonly()
	 * @see #getPropertyTemplate()
	 * @generated
	 */
	EAttribute getPropertyTemplate_Readonly();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.PropertyTemplate#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyTemplate#getType()
	 * @see #getPropertyTemplate()
	 * @generated
	 */
	EReference getPropertyTemplate_Type();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.DataTypes <em>Data Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Types</em>'.
	 * @see com.odcgroup.page.metamodel.DataTypes
	 * @generated
	 */
	EClass getDataTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.DataTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see com.odcgroup.page.metamodel.DataTypes#getTypes()
	 * @see #getDataTypes()
	 * @generated
	 */
	EReference getDataTypes_Types();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.IncludeabilityRule <em>Includeability Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Includeability Rule</em>'.
	 * @see com.odcgroup.page.metamodel.IncludeabilityRule
	 * @generated
	 */
	EClass getIncludeabilityRule();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.IncludeabilityRule#getInclusionType <em>Inclusion Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inclusion Type</em>'.
	 * @see com.odcgroup.page.metamodel.IncludeabilityRule#getInclusionType()
	 * @see #getIncludeabilityRule()
	 * @generated
	 */
	EAttribute getIncludeabilityRule_InclusionType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.IncludeabilityRule#isLimitedAccountability <em>Limited Accountability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Limited Accountability</em>'.
	 * @see com.odcgroup.page.metamodel.IncludeabilityRule#isLimitedAccountability()
	 * @see #getIncludeabilityRule()
	 * @generated
	 */
	EAttribute getIncludeabilityRule_LimitedAccountability();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.EventModel <em>Event Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Model</em>'.
	 * @see com.odcgroup.page.metamodel.EventModel
	 * @generated
	 */
	EClass getEventModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.EventModel#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see com.odcgroup.page.metamodel.EventModel#getFunctions()
	 * @see #getEventModel()
	 * @generated
	 */
	EReference getEventModel_Functions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.EventModel#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see com.odcgroup.page.metamodel.EventModel#getEvents()
	 * @see #getEventModel()
	 * @generated
	 */
	EReference getEventModel_Events();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.EventModel#getEventTypes <em>Event Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Types</em>'.
	 * @see com.odcgroup.page.metamodel.EventModel#getEventTypes()
	 * @see #getEventModel()
	 * @generated
	 */
	EReference getEventModel_EventTypes();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.WidgetEvent <em>Widget Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Event</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetEvent
	 * @generated
	 */
	EClass getWidgetEvent();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.WidgetEvent#getWidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetEvent#getWidgetType()
	 * @see #getWidgetEvent()
	 * @generated
	 */
	EReference getWidgetEvent_WidgetType();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.WidgetEvent#getEventTypes <em>Event Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Event Types</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetEvent#getEventTypes()
	 * @see #getWidgetEvent()
	 * @generated
	 */
	EReference getWidgetEvent_EventTypes();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type</em>'.
	 * @see com.odcgroup.page.metamodel.FunctionType
	 * @generated
	 */
	EClass getFunctionType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.FunctionType#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.odcgroup.page.metamodel.FunctionType#getParameters()
	 * @see #getFunctionType()
	 * @generated
	 */
	EReference getFunctionType_Parameters();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.FunctionType#getEventTypes <em>Event Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Event Types</em>'.
	 * @see com.odcgroup.page.metamodel.FunctionType#getEventTypes()
	 * @see #getFunctionType()
	 * @generated
	 */
	EReference getFunctionType_EventTypes();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.FunctionType#isAllowUserParameters <em>Allow User Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow User Parameters</em>'.
	 * @see com.odcgroup.page.metamodel.FunctionType#isAllowUserParameters()
	 * @see #getFunctionType()
	 * @generated
	 */
	EAttribute getFunctionType_AllowUserParameters();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Type</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterType
	 * @generated
	 */
	EClass getParameterType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.ParameterType#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterType#getDefaultValue()
	 * @see #getParameterType()
	 * @generated
	 */
	EAttribute getParameterType_DefaultValue();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.ParameterType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterType#getType()
	 * @see #getParameterType()
	 * @generated
	 */
	EReference getParameterType_Type();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.EventTemplate <em>Event Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Template</em>'.
	 * @see com.odcgroup.page.metamodel.EventTemplate
	 * @generated
	 */
	EClass getEventTemplate();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.EventTemplate#getParameterTemplates <em>Parameter Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Templates</em>'.
	 * @see com.odcgroup.page.metamodel.EventTemplate#getParameterTemplates()
	 * @see #getEventTemplate()
	 * @generated
	 */
	EReference getEventTemplate_ParameterTemplates();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.EventTemplate#getFunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Function Type</em>'.
	 * @see com.odcgroup.page.metamodel.EventTemplate#getFunctionType()
	 * @see #getEventTemplate()
	 * @generated
	 */
	EReference getEventTemplate_FunctionType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.EventTemplate#getEventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Type</em>'.
	 * @see com.odcgroup.page.metamodel.EventTemplate#getEventType()
	 * @see #getEventTemplate()
	 * @generated
	 */
	EAttribute getEventTemplate_EventType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.EventTemplate#getNature <em>Nature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nature</em>'.
	 * @see com.odcgroup.page.metamodel.EventTemplate#getNature()
	 * @see #getEventTemplate()
	 * @generated
	 */
	EAttribute getEventTemplate_Nature();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.ParameterTemplate <em>Parameter Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Template</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate
	 * @generated
	 */
	EClass getParameterTemplate();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.ParameterTemplate#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate#getType()
	 * @see #getParameterTemplate()
	 * @generated
	 */
	EReference getParameterTemplate_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.ParameterTemplate#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate#getValue()
	 * @see #getParameterTemplate()
	 * @generated
	 */
	EAttribute getParameterTemplate_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.ParameterTemplate#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate#isUserDefined()
	 * @see #getParameterTemplate()
	 * @generated
	 */
	EAttribute getParameterTemplate_UserDefined();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.ParameterTemplate#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate#getName()
	 * @see #getParameterTemplate()
	 * @generated
	 */
	EAttribute getParameterTemplate_Name();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.metamodel.InclusionType <em>Inclusion Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Inclusion Type</em>'.
	 * @see com.odcgroup.page.metamodel.InclusionType
	 * @generated
	 */
	EEnum getInclusionType();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.metamodel.PropertyCategory <em>Property Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Category</em>'.
	 * @see com.odcgroup.page.metamodel.PropertyCategory
	 * @generated
	 */
	EEnum getPropertyCategory();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.metamodel.TranslationSupport <em>Translation Support</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Translation Support</em>'.
	 * @see com.odcgroup.page.metamodel.TranslationSupport
	 * @generated
	 */
	EEnum getTranslationSupport();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Type</em>'.
	 * @see com.odcgroup.page.metamodel.EventType
	 * @generated
	 */
	EClass getEventType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.metamodel.EventType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.metamodel.EventType#getName()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Name();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.EventType#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.EventType#getPropertyTypes()
	 * @see #getEventType()
	 * @generated
	 */
	EReference getEventType_PropertyTypes();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.SnippetType <em>Snippet Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet Type</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetType
	 * @generated
	 */
	EClass getSnippetType();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.SnippetType#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetType#getParent()
	 * @see #getSnippetType()
	 * @generated
	 */
	EReference getSnippetType_Parent();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.SnippetType#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetType#getPropertyTypes()
	 * @see #getSnippetType()
	 * @generated
	 */
	EReference getSnippetType_PropertyTypes();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.SnippetModel <em>Snippet Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet Model</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetModel
	 * @generated
	 */
	EClass getSnippetModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.SnippetModel#getSnippets <em>Snippets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Snippets</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetModel#getSnippets()
	 * @see #getSnippetModel()
	 * @generated
	 */
	EReference getSnippetModel_Snippets();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.SnippetModel#getPropertyTypes <em>Property Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Types</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetModel#getPropertyTypes()
	 * @see #getSnippetModel()
	 * @generated
	 */
	EReference getSnippetModel_PropertyTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.SnippetModel#getWidgets <em>Widgets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Widgets</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetModel#getWidgets()
	 * @see #getSnippetModel()
	 * @generated
	 */
	EReference getSnippetModel_Widgets();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.metamodel.SnippetModel#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see com.odcgroup.page.metamodel.SnippetModel#getEvents()
	 * @see #getSnippetModel()
	 * @generated
	 */
	EReference getSnippetModel_Events();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.WidgetSnippet <em>Widget Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Snippet</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetSnippet
	 * @generated
	 */
	EClass getWidgetSnippet();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.metamodel.WidgetSnippet#getWidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetSnippet#getWidgetType()
	 * @see #getWidgetSnippet()
	 * @generated
	 */
	EReference getWidgetSnippet_WidgetType();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.WidgetSnippet#getSnippets <em>Snippets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Snippets</em>'.
	 * @see com.odcgroup.page.metamodel.WidgetSnippet#getSnippets()
	 * @see #getWidgetSnippet()
	 * @generated
	 */
	EReference getWidgetSnippet_Snippets();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.metamodel.EventSnippet <em>Event Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Snippet</em>'.
	 * @see com.odcgroup.page.metamodel.EventSnippet
	 * @generated
	 */
	EClass getEventSnippet();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.page.metamodel.EventSnippet#getSnippets <em>Snippets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Snippets</em>'.
	 * @see com.odcgroup.page.metamodel.EventSnippet#getSnippets()
	 * @see #getEventSnippet()
	 * @generated
	 */
	EReference getEventSnippet_Snippets();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MetaModelFactory getMetaModelFactory();

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
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.WidgetTypeImpl <em>Widget Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.WidgetTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetType()
		 * @generated
		 */
		EClass WIDGET_TYPE = eINSTANCE.getWidgetType();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TYPE__PARENT = eINSTANCE.getWidgetType_Parent();

		/**
		 * The meta object literal for the '<em><b>Property Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TYPE__PROPERTY_TYPES = eINSTANCE.getWidgetType_PropertyTypes();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TYPE__LIBRARY = eINSTANCE.getWidgetType_Library();

		/**
		 * The meta object literal for the '<em><b>Excluded Property Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES = eINSTANCE.getWidgetType_ExcludedPropertyTypes();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__DISPLAY_NAME = eINSTANCE.getWidgetType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Property Filter Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__PROPERTY_FILTER_CLASS = eINSTANCE.getWidgetType_PropertyFilterClass();

		/**
		 * The meta object literal for the '<em><b>Derivable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__DERIVABLE = eINSTANCE.getWidgetType_Derivable();

		/**
		 * The meta object literal for the '<em><b>Strict Accountability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__STRICT_ACCOUNTABILITY = eINSTANCE.getWidgetType_StrictAccountability();

		/**
		 * The meta object literal for the '<em><b>Domain Widget</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__DOMAIN_WIDGET = eINSTANCE.getWidgetType_DomainWidget();

		/**
		 * The meta object literal for the '<em><b>Can Drop Domain Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT = eINSTANCE.getWidgetType_CanDropDomainElement();

		/**
		 * The meta object literal for the '<em><b>Richtext</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET_TYPE__RICHTEXT = eINSTANCE.getWidgetType_Richtext();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.PropertyTypeImpl <em>Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.PropertyTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyType()
		 * @generated
		 */
		EClass PROPERTY_TYPE = eINSTANCE.getPropertyType();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__DEFAULT_VALUE = eINSTANCE.getPropertyType_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_TYPE__DATA_TYPE = eINSTANCE.getPropertyType_DataType();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_TYPE__LIBRARY = eINSTANCE.getPropertyType_Library();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__CATEGORY = eINSTANCE.getPropertyType_Category();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__DISPLAY_NAME = eINSTANCE.getPropertyType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Source Adapter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_TYPE__SOURCE_ADAPTER = eINSTANCE.getPropertyType_SourceAdapter();

        /**
		 * The meta object literal for the '<em><b>Sub Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__SUB_CATEGORY = eINSTANCE.getPropertyType_SubCategory();

								/**
		 * The meta object literal for the '<em><b>Visible In Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__VISIBLE_IN_DOMAIN = eINSTANCE.getPropertyType_VisibleInDomain();

								/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.WidgetTemplateImpl <em>Widget Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.WidgetTemplateImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetTemplate()
		 * @generated
		 */
		EClass WIDGET_TEMPLATE = eINSTANCE.getWidgetTemplate();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TEMPLATE__TYPE = eINSTANCE.getWidgetTemplate_Type();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TEMPLATE__CONTENTS = eINSTANCE.getWidgetTemplate_Contents();

		/**
		 * The meta object literal for the '<em><b>Property Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TEMPLATE__PROPERTY_TEMPLATES = eINSTANCE.getWidgetTemplate_PropertyTemplates();

		/**
		 * The meta object literal for the '<em><b>Event Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_TEMPLATE__EVENT_TEMPLATES = eINSTANCE.getWidgetTemplate_EventTemplates();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl <em>Accountability Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getAccountabilityRule()
		 * @generated
		 */
		EClass ACCOUNTABILITY_RULE = eINSTANCE.getAccountabilityRule();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTABILITY_RULE__CHILD = eINSTANCE.getAccountabilityRule_Child();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTABILITY_RULE__PARENT = eINSTANCE.getAccountabilityRule_Parent();

		/**
		 * The meta object literal for the '<em><b>Min Occurs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNTABILITY_RULE__MIN_OCCURS = eINSTANCE.getAccountabilityRule_MinOccurs();

		/**
		 * The meta object literal for the '<em><b>Max Occurs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNTABILITY_RULE__MAX_OCCURS = eINSTANCE.getAccountabilityRule_MaxOccurs();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNTABILITY_RULE__NAME = eINSTANCE.getAccountabilityRule_Name();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.WidgetLibraryImpl <em>Widget Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.WidgetLibraryImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetLibrary()
		 * @generated
		 */
		EClass WIDGET_LIBRARY = eINSTANCE.getWidgetLibrary();

		/**
		 * The meta object literal for the '<em><b>Widget Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_LIBRARY__WIDGET_TYPES = eINSTANCE.getWidgetLibrary_WidgetTypes();

		/**
		 * The meta object literal for the '<em><b>Property Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_LIBRARY__PROPERTY_TYPES = eINSTANCE.getWidgetLibrary_PropertyTypes();

		/**
		 * The meta object literal for the '<em><b>Widget Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_LIBRARY__WIDGET_TEMPLATES = eINSTANCE.getWidgetLibrary_WidgetTemplates();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.AccountabilityImpl <em>Accountability</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.AccountabilityImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getAccountability()
		 * @generated
		 */
		EClass ACCOUNTABILITY = eINSTANCE.getAccountability();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTABILITY__RULES = eINSTANCE.getAccountability_Rules();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.PropertyValueConverter <em>Property Value Converter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.PropertyValueConverter
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyValueConverter()
		 * @generated
		 */
		EClass PROPERTY_VALUE_CONVERTER = eINSTANCE.getPropertyValueConverter();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.DataTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_TYPE__VALUES = eINSTANCE.getDataType_Values();

		/**
		 * The meta object literal for the '<em><b>Editor Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__EDITOR_NAME = eINSTANCE.getDataType_EditorName();

		/**
		 * The meta object literal for the '<em><b>Validator Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__VALIDATOR_NAME = eINSTANCE.getDataType_ValidatorName();

		/**
		 * The meta object literal for the '<em><b>Converter Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__CONVERTER_NAME = eINSTANCE.getDataType_ConverterName();

		/**
		 * The meta object literal for the '<em><b>Editable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__EDITABLE = eINSTANCE.getDataType_Editable();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl <em>Named Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.NamedTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getNamedType()
		 * @generated
		 */
		EClass NAMED_TYPE = eINSTANCE.getNamedType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TYPE__NAME = eINSTANCE.getNamedType_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TYPE__DESCRIPTION = eINSTANCE.getNamedType_Description();

		/**
		 * The meta object literal for the '<em><b>Translation Support</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TYPE__TRANSLATION_SUPPORT = eINSTANCE.getNamedType_TranslationSupport();

		/**
		 * The meta object literal for the '<em><b>Properties Help ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TYPE__PROPERTIES_HELP_ID = eINSTANCE.getNamedType_PropertiesHelpID();

		/**
		 * The meta object literal for the '<em><b>Concept Help ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TYPE__CONCEPT_HELP_ID = eINSTANCE.getNamedType_ConceptHelpID();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.DataValueImpl <em>Data Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.DataValueImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataValue()
		 * @generated
		 */
		EClass DATA_VALUE = eINSTANCE.getDataValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_VALUE__VALUE = eINSTANCE.getDataValue_Value();

		/**
		 * The meta object literal for the '<em><b>Ordinal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_VALUE__ORDINAL = eINSTANCE.getDataValue_Ordinal();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute DATA_VALUE__DISPLAY_NAME = eINSTANCE.getDataValue_DisplayName();

        /**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.MetaModelImpl <em>Meta Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.MetaModelImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getMetaModel()
		 * @generated
		 */
		EClass META_MODEL = eINSTANCE.getMetaModel();

		/**
		 * The meta object literal for the '<em><b>Widget Libraries</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__WIDGET_LIBRARIES = eINSTANCE.getMetaModel_WidgetLibraries();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_MODEL__VERSION = eINSTANCE.getMetaModel_Version();

		/**
		 * The meta object literal for the '<em><b>Data Types</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__DATA_TYPES = eINSTANCE.getMetaModel_DataTypes();

		/**
		 * The meta object literal for the '<em><b>Event Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__EVENT_MODEL = eINSTANCE.getMetaModel_EventModel();

		/**
		 * The meta object literal for the '<em><b>Snippet Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__SNIPPET_MODEL = eINSTANCE.getMetaModel_SnippetModel();

		/**
		 * The meta object literal for the '<em><b>Containability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__CONTAINABILITY = eINSTANCE.getMetaModel_Containability();

		/**
		 * The meta object literal for the '<em><b>Includeability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_MODEL__INCLUDEABILITY = eINSTANCE.getMetaModel_Includeability();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.PropertyTemplateImpl <em>Property Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.PropertyTemplateImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyTemplate()
		 * @generated
		 */
		EClass PROPERTY_TEMPLATE = eINSTANCE.getPropertyTemplate();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TEMPLATE__VALUE = eINSTANCE.getPropertyTemplate_Value();

		/**
		 * The meta object literal for the '<em><b>Readonly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TEMPLATE__READONLY = eINSTANCE.getPropertyTemplate_Readonly();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_TEMPLATE__TYPE = eINSTANCE.getPropertyTemplate_Type();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.DataTypesImpl <em>Data Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.DataTypesImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getDataTypes()
		 * @generated
		 */
		EClass DATA_TYPES = eINSTANCE.getDataTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_TYPES__TYPES = eINSTANCE.getDataTypes_Types();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl <em>Includeability Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getIncludeabilityRule()
		 * @generated
		 */
		EClass INCLUDEABILITY_RULE = eINSTANCE.getIncludeabilityRule();

		/**
		 * The meta object literal for the '<em><b>Inclusion Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDEABILITY_RULE__INCLUSION_TYPE = eINSTANCE.getIncludeabilityRule_InclusionType();

		/**
		 * The meta object literal for the '<em><b>Limited Accountability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY = eINSTANCE.getIncludeabilityRule_LimitedAccountability();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.EventModelImpl <em>Event Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.EventModelImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventModel()
		 * @generated
		 */
		EClass EVENT_MODEL = eINSTANCE.getEventModel();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_MODEL__FUNCTIONS = eINSTANCE.getEventModel_Functions();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_MODEL__EVENTS = eINSTANCE.getEventModel_Events();

		/**
		 * The meta object literal for the '<em><b>Event Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_MODEL__EVENT_TYPES = eINSTANCE.getEventModel_EventTypes();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.WidgetEventImpl <em>Widget Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.WidgetEventImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetEvent()
		 * @generated
		 */
		EClass WIDGET_EVENT = eINSTANCE.getWidgetEvent();

		/**
		 * The meta object literal for the '<em><b>Widget Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_EVENT__WIDGET_TYPE = eINSTANCE.getWidgetEvent_WidgetType();

		/**
		 * The meta object literal for the '<em><b>Event Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_EVENT__EVENT_TYPES = eINSTANCE.getWidgetEvent_EventTypes();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.FunctionTypeImpl <em>Function Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.FunctionTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getFunctionType()
		 * @generated
		 */
		EClass FUNCTION_TYPE = eINSTANCE.getFunctionType();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE__PARAMETERS = eINSTANCE.getFunctionType_Parameters();

		/**
		 * The meta object literal for the '<em><b>Event Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE__EVENT_TYPES = eINSTANCE.getFunctionType_EventTypes();

		/**
		 * The meta object literal for the '<em><b>Allow User Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_TYPE__ALLOW_USER_PARAMETERS = eINSTANCE.getFunctionType_AllowUserParameters();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.ParameterTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getParameterType()
		 * @generated
		 */
		EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TYPE__DEFAULT_VALUE = eINSTANCE.getParameterType_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_TYPE__TYPE = eINSTANCE.getParameterType_Type();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.EventTemplateImpl <em>Event Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.EventTemplateImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventTemplate()
		 * @generated
		 */
		EClass EVENT_TEMPLATE = eINSTANCE.getEventTemplate();

		/**
		 * The meta object literal for the '<em><b>Parameter Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_TEMPLATE__PARAMETER_TEMPLATES = eINSTANCE.getEventTemplate_ParameterTemplates();

		/**
		 * The meta object literal for the '<em><b>Function Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_TEMPLATE__FUNCTION_TYPE = eINSTANCE.getEventTemplate_FunctionType();

		/**
		 * The meta object literal for the '<em><b>Event Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TEMPLATE__EVENT_TYPE = eINSTANCE.getEventTemplate_EventType();

		/**
		 * The meta object literal for the '<em><b>Nature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TEMPLATE__NATURE = eINSTANCE.getEventTemplate_Nature();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.ParameterTemplateImpl <em>Parameter Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.ParameterTemplateImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getParameterTemplate()
		 * @generated
		 */
		EClass PARAMETER_TEMPLATE = eINSTANCE.getParameterTemplate();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_TEMPLATE__TYPE = eINSTANCE.getParameterTemplate_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TEMPLATE__VALUE = eINSTANCE.getParameterTemplate_Value();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TEMPLATE__USER_DEFINED = eINSTANCE.getParameterTemplate_UserDefined();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TEMPLATE__NAME = eINSTANCE.getParameterTemplate_Name();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.InclusionType <em>Inclusion Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.InclusionType
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getInclusionType()
		 * @generated
		 */
		EEnum INCLUSION_TYPE = eINSTANCE.getInclusionType();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.PropertyCategory <em>Property Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.PropertyCategory
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getPropertyCategory()
		 * @generated
		 */
		EEnum PROPERTY_CATEGORY = eINSTANCE.getPropertyCategory();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.TranslationSupport <em>Translation Support</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.TranslationSupport
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getTranslationSupport()
		 * @generated
		 */
		EEnum TRANSLATION_SUPPORT = eINSTANCE.getTranslationSupport();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.EventTypeImpl <em>Event Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.EventTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventType()
		 * @generated
		 */
		EClass EVENT_TYPE = eINSTANCE.getEventType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__NAME = eINSTANCE.getEventType_Name();

		/**
		 * The meta object literal for the '<em><b>Property Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_TYPE__PROPERTY_TYPES = eINSTANCE.getEventType_PropertyTypes();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.SnippetTypeImpl <em>Snippet Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.SnippetTypeImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getSnippetType()
		 * @generated
		 */
		EClass SNIPPET_TYPE = eINSTANCE.getSnippetType();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_TYPE__PARENT = eINSTANCE.getSnippetType_Parent();

		/**
		 * The meta object literal for the '<em><b>Property Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_TYPE__PROPERTY_TYPES = eINSTANCE.getSnippetType_PropertyTypes();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl <em>Snippet Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.SnippetModelImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getSnippetModel()
		 * @generated
		 */
		EClass SNIPPET_MODEL = eINSTANCE.getSnippetModel();

		/**
		 * The meta object literal for the '<em><b>Snippets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_MODEL__SNIPPETS = eINSTANCE.getSnippetModel_Snippets();

		/**
		 * The meta object literal for the '<em><b>Property Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_MODEL__PROPERTY_TYPES = eINSTANCE.getSnippetModel_PropertyTypes();

		/**
		 * The meta object literal for the '<em><b>Widgets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_MODEL__WIDGETS = eINSTANCE.getSnippetModel_Widgets();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_MODEL__EVENTS = eINSTANCE.getSnippetModel_Events();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.WidgetSnippetImpl <em>Widget Snippet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.WidgetSnippetImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getWidgetSnippet()
		 * @generated
		 */
		EClass WIDGET_SNIPPET = eINSTANCE.getWidgetSnippet();

		/**
		 * The meta object literal for the '<em><b>Widget Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_SNIPPET__WIDGET_TYPE = eINSTANCE.getWidgetSnippet_WidgetType();

		/**
		 * The meta object literal for the '<em><b>Snippets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET_SNIPPET__SNIPPETS = eINSTANCE.getWidgetSnippet_Snippets();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.metamodel.impl.EventSnippetImpl <em>Event Snippet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.metamodel.impl.EventSnippetImpl
		 * @see com.odcgroup.page.metamodel.impl.MetaModelPackageImpl#getEventSnippet()
		 * @generated
		 */
		EClass EVENT_SNIPPET = eINSTANCE.getEventSnippet();

		/**
		 * The meta object literal for the '<em><b>Snippets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_SNIPPET__SNIPPETS = eINSTANCE.getEventSnippet_Snippets();

	}

} //MetaModelPackage
