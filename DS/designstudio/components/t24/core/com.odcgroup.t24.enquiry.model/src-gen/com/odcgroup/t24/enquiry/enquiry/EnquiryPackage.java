/**
 */
package com.odcgroup.t24.enquiry.enquiry;

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
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryFactory
 * @model kind="package"
 * @generated
 */
public interface EnquiryPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "enquiry";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/t24/Enquiry";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "enquiry";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EnquiryPackage eINSTANCE = com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl.init();

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl <em>Enquiry</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiry()
   * @generated
   */
  int ENQUIRY = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__NAME = 0;

  /**
   * The feature id for the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__FILE_NAME = 1;

  /**
   * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__METAMODEL_VERSION = 2;

  /**
   * The feature id for the '<em><b>Header</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__HEADER = 3;

  /**
   * The feature id for the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__DESCRIPTION = 4;

  /**
   * The feature id for the '<em><b>Server Mode</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__SERVER_MODE = 5;

  /**
   * The feature id for the '<em><b>Enquiry Mode</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__ENQUIRY_MODE = 6;

  /**
   * The feature id for the '<em><b>Companies</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__COMPANIES = 7;

  /**
   * The feature id for the '<em><b>Account Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__ACCOUNT_FIELD = 8;

  /**
   * The feature id for the '<em><b>Customer Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__CUSTOMER_FIELD = 9;

  /**
   * The feature id for the '<em><b>Zero Records Display</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__ZERO_RECORDS_DISPLAY = 10;

  /**
   * The feature id for the '<em><b>No Selection</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__NO_SELECTION = 11;

  /**
   * The feature id for the '<em><b>Show All Books</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__SHOW_ALL_BOOKS = 12;

  /**
   * The feature id for the '<em><b>Start Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__START_LINE = 13;

  /**
   * The feature id for the '<em><b>End Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__END_LINE = 14;

  /**
   * The feature id for the '<em><b>Build Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__BUILD_ROUTINES = 15;

  /**
   * The feature id for the '<em><b>Fixed Selections</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__FIXED_SELECTIONS = 16;

  /**
   * The feature id for the '<em><b>Fixed Sorts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__FIXED_SORTS = 17;

  /**
   * The feature id for the '<em><b>Custom Selection</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__CUSTOM_SELECTION = 18;

  /**
   * The feature id for the '<em><b>Fields</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__FIELDS = 19;

  /**
   * The feature id for the '<em><b>Toolbar</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__TOOLBAR = 20;

  /**
   * The feature id for the '<em><b>Tools</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__TOOLS = 21;

  /**
   * The feature id for the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__TARGET = 22;

  /**
   * The feature id for the '<em><b>Drill Downs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__DRILL_DOWNS = 23;

  /**
   * The feature id for the '<em><b>Security</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__SECURITY = 24;

  /**
   * The feature id for the '<em><b>Graph</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__GRAPH = 25;

  /**
   * The feature id for the '<em><b>Web Service</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__WEB_SERVICE = 26;

  /**
   * The feature id for the '<em><b>Generate IFP</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__GENERATE_IFP = 27;

  /**
   * The feature id for the '<em><b>File Version</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__FILE_VERSION = 28;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__ATTRIBUTES = 29;

  /**
   * The feature id for the '<em><b>Introspection Messages</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY__INTROSPECTION_MESSAGES = 30;

  /**
   * The number of structural features of the '<em>Enquiry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_FEATURE_COUNT = 31;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl <em>Companies</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompanies()
   * @generated
   */
  int COMPANIES = 1;

  /**
   * The feature id for the '<em><b>All</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANIES__ALL = 0;

  /**
   * The feature id for the '<em><b>Code</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANIES__CODE = 1;

  /**
   * The number of structural features of the '<em>Companies</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANIES_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl <em>Header</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryHeader()
   * @generated
   */
  int ENQUIRY_HEADER = 2;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_HEADER__LABEL = 0;

  /**
   * The feature id for the '<em><b>Column</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_HEADER__COLUMN = 1;

  /**
   * The feature id for the '<em><b>Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_HEADER__LINE = 2;

  /**
   * The number of structural features of the '<em>Header</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_HEADER_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl <em>Target</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTarget()
   * @generated
   */
  int TARGET = 3;

  /**
   * The feature id for the '<em><b>Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET__APPLICATION = 0;

  /**
   * The feature id for the '<em><b>Screen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET__SCREEN = 1;

  /**
   * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET__MAPPINGS = 2;

  /**
   * The number of structural features of the '<em>Target</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl <em>Target Mapping</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTargetMapping()
   * @generated
   */
  int TARGET_MAPPING = 4;

  /**
   * The feature id for the '<em><b>From Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET_MAPPING__FROM_FIELD = 0;

  /**
   * The feature id for the '<em><b>To Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET_MAPPING__TO_FIELD = 1;

  /**
   * The number of structural features of the '<em>Target Mapping</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TARGET_MAPPING_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl <em>Parameters</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getParameters()
   * @generated
   */
  int PARAMETERS = 5;

  /**
   * The feature id for the '<em><b>Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__FUNCTION = 0;

  /**
   * The feature id for the '<em><b>Auto</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__AUTO = 1;

  /**
   * The feature id for the '<em><b>Run Immediately</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__RUN_IMMEDIATELY = 2;

  /**
   * The feature id for the '<em><b>Pw Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__PW_ACTIVITY = 3;

  /**
   * The feature id for the '<em><b>Field Name</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__FIELD_NAME = 4;

  /**
   * The feature id for the '<em><b>Variable</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS__VARIABLE = 5;

  /**
   * The number of structural features of the '<em>Parameters</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETERS_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl <em>Drill Down</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDown()
   * @generated
   */
  int DRILL_DOWN = 6;

  /**
   * The feature id for the '<em><b>Drill name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__DRILL_NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Label Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__LABEL_FIELD = 2;

  /**
   * The feature id for the '<em><b>Image</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__IMAGE = 3;

  /**
   * The feature id for the '<em><b>Info</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__INFO = 4;

  /**
   * The feature id for the '<em><b>Criteria</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__CRITERIA = 5;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__PARAMETERS = 6;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN__TYPE = 7;

  /**
   * The number of structural features of the '<em>Drill Down</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_FEATURE_COUNT = 8;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownTypeImpl <em>Drill Down Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownType()
   * @generated
   */
  int DRILL_DOWN_TYPE = 7;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_TYPE__PROPERTY = 0;

  /**
   * The number of structural features of the '<em>Drill Down Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownStringTypeImpl <em>Drill Down String Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownStringTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownStringType()
   * @generated
   */
  int DRILL_DOWN_STRING_TYPE = 8;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_STRING_TYPE__PROPERTY = DRILL_DOWN_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_STRING_TYPE__VALUE = DRILL_DOWN_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Drill Down String Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_STRING_TYPE_FEATURE_COUNT = DRILL_DOWN_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ApplicationTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getApplicationType()
   * @generated
   */
  int APPLICATION_TYPE = 9;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int APPLICATION_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int APPLICATION_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Application Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int APPLICATION_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ScreenTypeImpl <em>Screen Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ScreenTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getScreenType()
   * @generated
   */
  int SCREEN_TYPE = 10;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCREEN_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCREEN_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Screen Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCREEN_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryTypeImpl <em>Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryType()
   * @generated
   */
  int ENQUIRY_TYPE = 11;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENQUIRY_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FromFieldTypeImpl <em>From Field Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FromFieldTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFromFieldType()
   * @generated
   */
  int FROM_FIELD_TYPE = 12;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FROM_FIELD_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FROM_FIELD_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>From Field Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FROM_FIELD_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenTypeImpl <em>Composite Screen Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompositeScreenType()
   * @generated
   */
  int COMPOSITE_SCREEN_TYPE = 13;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Composite Screen Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TabbedScreenTypeImpl <em>Tabbed Screen Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TabbedScreenTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTabbedScreenType()
   * @generated
   */
  int TABBED_SCREEN_TYPE = 14;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABBED_SCREEN_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABBED_SCREEN_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Tabbed Screen Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABBED_SCREEN_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ViewTypeImpl <em>View Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ViewTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getViewType()
   * @generated
   */
  int VIEW_TYPE = 15;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIEW_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIEW_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>View Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIEW_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.QuitSEETypeImpl <em>Quit SEE Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.QuitSEETypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getQuitSEEType()
   * @generated
   */
  int QUIT_SEE_TYPE = 16;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUIT_SEE_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUIT_SEE_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Quit SEE Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUIT_SEE_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BlankTypeImpl <em>Blank Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BlankTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBlankType()
   * @generated
   */
  int BLANK_TYPE = 17;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLANK_TYPE__PROPERTY = DRILL_DOWN_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLANK_TYPE__VALUE = DRILL_DOWN_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Blank Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLANK_TYPE_FEATURE_COUNT = DRILL_DOWN_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.PWProcessTypeImpl <em>PW Process Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.PWProcessTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getPWProcessType()
   * @generated
   */
  int PW_PROCESS_TYPE = 18;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PW_PROCESS_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PW_PROCESS_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>PW Process Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PW_PROCESS_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DownloadTypeImpl <em>Download Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DownloadTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDownloadType()
   * @generated
   */
  int DOWNLOAD_TYPE = 19;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOWNLOAD_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOWNLOAD_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Download Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOWNLOAD_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RunTypeImpl <em>Run Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RunTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRunType()
   * @generated
   */
  int RUN_TYPE = 20;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Run Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RUN_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.UtilTypeImpl <em>Util Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.UtilTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getUtilType()
   * @generated
   */
  int UTIL_TYPE = 21;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UTIL_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UTIL_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Util Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UTIL_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JavaScriptTypeImpl <em>Java Script Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.JavaScriptTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJavaScriptType()
   * @generated
   */
  int JAVA_SCRIPT_TYPE = 22;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JAVA_SCRIPT_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JAVA_SCRIPT_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Java Script Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JAVA_SCRIPT_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ShouldBeChangedTypeImpl <em>Should Be Changed Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ShouldBeChangedTypeImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getShouldBeChangedType()
   * @generated
   */
  int SHOULD_BE_CHANGED_TYPE = 23;

  /**
   * The feature id for the '<em><b>Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SHOULD_BE_CHANGED_TYPE__PROPERTY = DRILL_DOWN_STRING_TYPE__PROPERTY;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SHOULD_BE_CHANGED_TYPE__VALUE = DRILL_DOWN_STRING_TYPE__VALUE;

  /**
   * The number of structural features of the '<em>Should Be Changed Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SHOULD_BE_CHANGED_TYPE_FEATURE_COUNT = DRILL_DOWN_STRING_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownOptionImpl <em>Drill Down Option</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownOptionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownOption()
   * @generated
   */
  int DRILL_DOWN_OPTION = 24;

  /**
   * The number of structural features of the '<em>Drill Down Option</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DRILL_DOWN_OPTION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenOptionImpl <em>Composite Screen Option</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenOptionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompositeScreenOption()
   * @generated
   */
  int COMPOSITE_SCREEN_OPTION = 25;

  /**
   * The feature id for the '<em><b>Composite Screen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_OPTION__COMPOSITE_SCREEN = DRILL_DOWN_OPTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Reference</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_OPTION__REFERENCE = DRILL_DOWN_OPTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Field Parameter</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_OPTION__FIELD_PARAMETER = DRILL_DOWN_OPTION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Composite Screen Option</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPOSITE_SCREEN_OPTION_FEATURE_COUNT = DRILL_DOWN_OPTION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TabOptionImpl <em>Tab Option</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TabOptionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTabOption()
   * @generated
   */
  int TAB_OPTION = 26;

  /**
   * The feature id for the '<em><b>Tab Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TAB_OPTION__TAB_NAME = DRILL_DOWN_OPTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Reference</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TAB_OPTION__REFERENCE = DRILL_DOWN_OPTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Field Parameter</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TAB_OPTION__FIELD_PARAMETER = DRILL_DOWN_OPTION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Tab Option</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TAB_OPTION_FEATURE_COUNT = DRILL_DOWN_OPTION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ViewOptionImpl <em>View Option</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ViewOptionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getViewOption()
   * @generated
   */
  int VIEW_OPTION = 27;

  /**
   * The number of structural features of the '<em>View Option</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VIEW_OPTION_FEATURE_COUNT = DRILL_DOWN_OPTION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.QuitSEEOptionImpl <em>Quit SEE Option</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.QuitSEEOptionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getQuitSEEOption()
   * @generated
   */
  int QUIT_SEE_OPTION = 28;

  /**
   * The number of structural features of the '<em>Quit SEE Option</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUIT_SEE_OPTION_FEATURE_COUNT = DRILL_DOWN_OPTION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ReferenceImpl <em>Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ReferenceImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getReference()
   * @generated
   */
  int REFERENCE = 29;

  /**
   * The feature id for the '<em><b>File</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE__FILE = 0;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE__FIELD = 1;

  /**
   * The number of structural features of the '<em>Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ParameterImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 30;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__FIELD = 0;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl <em>Selection Criteria</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionCriteria()
   * @generated
   */
  int SELECTION_CRITERIA = 31;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_CRITERIA__FIELD = 0;

  /**
   * The feature id for the '<em><b>Operand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_CRITERIA__OPERAND = 1;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_CRITERIA__VALUES = 2;

  /**
   * The number of structural features of the '<em>Selection Criteria</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_CRITERIA_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl <em>Security</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSecurity()
   * @generated
   */
  int SECURITY = 32;

  /**
   * The feature id for the '<em><b>Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY__APPLICATION = 0;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY__FIELD = 1;

  /**
   * The feature id for the '<em><b>Abort</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY__ABORT = 2;

  /**
   * The number of structural features of the '<em>Security</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl <em>Graph</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGraph()
   * @generated
   */
  int GRAPH = 33;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__TYPE = 0;

  /**
   * The feature id for the '<em><b>Labels</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__LABELS = 1;

  /**
   * The feature id for the '<em><b>Dimension</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__DIMENSION = 2;

  /**
   * The feature id for the '<em><b>Margins</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__MARGINS = 3;

  /**
   * The feature id for the '<em><b>Scale</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__SCALE = 4;

  /**
   * The feature id for the '<em><b>Legend</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__LEGEND = 5;

  /**
   * The feature id for the '<em><b>XAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__XAXIS = 6;

  /**
   * The feature id for the '<em><b>YAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__YAXIS = 7;

  /**
   * The feature id for the '<em><b>ZAxis</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH__ZAXIS = 8;

  /**
   * The number of structural features of the '<em>Graph</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GRAPH_FEATURE_COUNT = 9;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl <em>Axis</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAxis()
   * @generated
   */
  int AXIS = 34;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AXIS__FIELD = 0;

  /**
   * The feature id for the '<em><b>Display Legend</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AXIS__DISPLAY_LEGEND = 1;

  /**
   * The feature id for the '<em><b>Show Grid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AXIS__SHOW_GRID = 2;

  /**
   * The number of structural features of the '<em>Axis</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AXIS_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DimensionImpl <em>Dimension</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DimensionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDimension()
   * @generated
   */
  int DIMENSION = 35;

  /**
   * The feature id for the '<em><b>Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIMENSION__WIDTH = 0;

  /**
   * The feature id for the '<em><b>Height</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIMENSION__HEIGHT = 1;

  /**
   * The feature id for the '<em><b>Orientation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIMENSION__ORIENTATION = 2;

  /**
   * The number of structural features of the '<em>Dimension</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIMENSION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LabelImpl <em>Label</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LabelImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLabel()
   * @generated
   */
  int LABEL = 36;

  /**
   * The feature id for the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LABEL__DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Position</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LABEL__POSITION = 1;

  /**
   * The number of structural features of the '<em>Label</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LABEL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.PositionImpl <em>Position</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.PositionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getPosition()
   * @generated
   */
  int POSITION = 37;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSITION__X = 0;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSITION__Y = 1;

  /**
   * The number of structural features of the '<em>Position</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POSITION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LegendImpl <em>Legend</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LegendImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLegend()
   * @generated
   */
  int LEGEND = 38;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LEGEND__X = 0;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LEGEND__Y = 1;

  /**
   * The number of structural features of the '<em>Legend</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LEGEND_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.MarginsImpl <em>Margins</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.MarginsImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getMargins()
   * @generated
   */
  int MARGINS = 39;

  /**
   * The feature id for the '<em><b>Top</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MARGINS__TOP = 0;

  /**
   * The feature id for the '<em><b>Bottom</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MARGINS__BOTTOM = 1;

  /**
   * The feature id for the '<em><b>Left</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MARGINS__LEFT = 2;

  /**
   * The feature id for the '<em><b>Right</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MARGINS__RIGHT = 3;

  /**
   * The number of structural features of the '<em>Margins</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MARGINS_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ScaleImpl <em>Scale</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ScaleImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getScale()
   * @generated
   */
  int SCALE = 40;

  /**
   * The feature id for the '<em><b>X</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCALE__X = 0;

  /**
   * The feature id for the '<em><b>Y</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCALE__Y = 1;

  /**
   * The number of structural features of the '<em>Scale</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCALE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RoutineImpl <em>Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RoutineImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRoutine()
   * @generated
   */
  int ROUTINE = 41;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROUTINE__NAME = 0;

  /**
   * The number of structural features of the '<em>Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROUTINE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JBCRoutineImpl <em>JBC Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.JBCRoutineImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJBCRoutine()
   * @generated
   */
  int JBC_ROUTINE = 42;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JBC_ROUTINE__NAME = ROUTINE__NAME;

  /**
   * The number of structural features of the '<em>JBC Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JBC_ROUTINE_FEATURE_COUNT = ROUTINE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JavaRoutineImpl <em>Java Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.JavaRoutineImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJavaRoutine()
   * @generated
   */
  int JAVA_ROUTINE = 43;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JAVA_ROUTINE__NAME = ROUTINE__NAME;

  /**
   * The number of structural features of the '<em>Java Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JAVA_ROUTINE_FEATURE_COUNT = ROUTINE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl <em>Fixed Selection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFixedSelection()
   * @generated
   */
  int FIXED_SELECTION = 44;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SELECTION__FIELD = 0;

  /**
   * The feature id for the '<em><b>Operand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SELECTION__OPERAND = 1;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SELECTION__VALUES = 2;

  /**
   * The number of structural features of the '<em>Fixed Selection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SELECTION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSortImpl <em>Fixed Sort</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FixedSortImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFixedSort()
   * @generated
   */
  int FIXED_SORT = 45;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SORT__FIELD = 0;

  /**
   * The feature id for the '<em><b>Order</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SORT__ORDER = 1;

  /**
   * The number of structural features of the '<em>Fixed Sort</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIXED_SORT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionExpressionImpl <em>Selection Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionExpressionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionExpression()
   * @generated
   */
  int SELECTION_EXPRESSION = 46;

  /**
   * The feature id for the '<em><b>Selection</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_EXPRESSION__SELECTION = 0;

  /**
   * The number of structural features of the '<em>Selection Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_EXPRESSION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl <em>Selection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelection()
   * @generated
   */
  int SELECTION = 47;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__FIELD = 0;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__MANDATORY = 1;

  /**
   * The feature id for the '<em><b>Popup Drop Down</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__POPUP_DROP_DOWN = 2;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__LABEL = 3;

  /**
   * The feature id for the '<em><b>Operands</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__OPERANDS = 4;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION__OPERATOR = 5;

  /**
   * The number of structural features of the '<em>Selection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FileVersionImpl <em>File Version</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FileVersionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFileVersion()
   * @generated
   */
  int FILE_VERSION = 48;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VERSION__VALUES = 0;

  /**
   * The number of structural features of the '<em>File Version</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VERSION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.OperationImpl <em>Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.OperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getOperation()
   * @generated
   */
  int OPERATION = 49;

  /**
   * The number of structural features of the '<em>Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakOperationImpl <em>Break Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakOperation()
   * @generated
   */
  int BREAK_OPERATION = 50;

  /**
   * The number of structural features of the '<em>Break Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakOnChangeOperationImpl <em>Break On Change Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakOnChangeOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakOnChangeOperation()
   * @generated
   */
  int BREAK_ON_CHANGE_OPERATION = 51;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_ON_CHANGE_OPERATION__FIELD = BREAK_OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Break On Change Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_ON_CHANGE_OPERATION_FEATURE_COUNT = BREAK_OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakLineOperationImpl <em>Break Line Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakLineOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakLineOperation()
   * @generated
   */
  int BREAK_LINE_OPERATION = 52;

  /**
   * The feature id for the '<em><b>Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_LINE_OPERATION__LINE = BREAK_OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Break Line Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_LINE_OPERATION_FEATURE_COUNT = BREAK_OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CalcOperationImpl <em>Calc Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CalcOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCalcOperation()
   * @generated
   */
  int CALC_OPERATION = 53;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALC_OPERATION__FIELD = OPERATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALC_OPERATION__OPERATOR = OPERATION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Calc Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALC_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConstantOperationImpl <em>Constant Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ConstantOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConstantOperation()
   * @generated
   */
  int CONSTANT_OPERATION = 54;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_OPERATION__VALUE = OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Constant Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LabelOperationImpl <em>Label Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LabelOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLabelOperation()
   * @generated
   */
  int LABEL_OPERATION = 55;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LABEL_OPERATION__LABEL = OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Label Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LABEL_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DateOperationImpl <em>Date Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DateOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDateOperation()
   * @generated
   */
  int DATE_OPERATION = 56;

  /**
   * The number of structural features of the '<em>Date Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATE_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl <em>Decision Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecisionOperation()
   * @generated
   */
  int DECISION_OPERATION = 57;

  /**
   * The feature id for the '<em><b>Left Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION__LEFT_FIELD = OPERATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION__OPERAND = OPERATION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION__RIGHT_FIELD = OPERATION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>First Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION__FIRST_FIELD = OPERATION_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Second Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION__SECOND_FIELD = OPERATION_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Decision Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECISION_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DescriptorOperationImpl <em>Descriptor Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DescriptorOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDescriptorOperation()
   * @generated
   */
  int DESCRIPTOR_OPERATION = 58;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTOR_OPERATION__FIELD = OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Descriptor Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTOR_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TodayOperationImpl <em>Today Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TodayOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTodayOperation()
   * @generated
   */
  int TODAY_OPERATION = 59;

  /**
   * The number of structural features of the '<em>Today Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TODAY_OPERATION_FEATURE_COUNT = DATE_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LWDOperationImpl <em>LWD Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LWDOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLWDOperation()
   * @generated
   */
  int LWD_OPERATION = 60;

  /**
   * The number of structural features of the '<em>LWD Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LWD_OPERATION_FEATURE_COUNT = DATE_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.NWDOperationImpl <em>NWD Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.NWDOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getNWDOperation()
   * @generated
   */
  int NWD_OPERATION = 61;

  /**
   * The number of structural features of the '<em>NWD Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NWD_OPERATION_FEATURE_COUNT = DATE_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldOperationImpl <em>Field Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldOperation()
   * @generated
   */
  int FIELD_OPERATION = 62;

  /**
   * The number of structural features of the '<em>Field Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ApplicationFieldNameOperationImpl <em>Application Field Name Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ApplicationFieldNameOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getApplicationFieldNameOperation()
   * @generated
   */
  int APPLICATION_FIELD_NAME_OPERATION = 63;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int APPLICATION_FIELD_NAME_OPERATION__FIELD = FIELD_OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Application Field Name Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int APPLICATION_FIELD_NAME_OPERATION_FEATURE_COUNT = FIELD_OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldNumberOperationImpl <em>Field Number Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldNumberOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldNumberOperation()
   * @generated
   */
  int FIELD_NUMBER_OPERATION = 64;

  /**
   * The feature id for the '<em><b>Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_NUMBER_OPERATION__NUMBER = FIELD_OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Field Number Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_NUMBER_OPERATION_FEATURE_COUNT = FIELD_OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldExtractOperationImpl <em>Field Extract Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldExtractOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldExtractOperation()
   * @generated
   */
  int FIELD_EXTRACT_OPERATION = 65;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_EXTRACT_OPERATION__FIELD = FIELD_OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Field Extract Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_EXTRACT_OPERATION_FEATURE_COUNT = FIELD_OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionOperationImpl <em>Selection Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionOperation()
   * @generated
   */
  int SELECTION_OPERATION = 66;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_OPERATION__FIELD = OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Selection Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECTION_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SystemOperationImpl <em>System Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.SystemOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSystemOperation()
   * @generated
   */
  int SYSTEM_OPERATION = 67;

  /**
   * The number of structural features of the '<em>System Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.UserOperationImpl <em>User Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.UserOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getUserOperation()
   * @generated
   */
  int USER_OPERATION = 68;

  /**
   * The number of structural features of the '<em>User Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER_OPERATION_FEATURE_COUNT = SYSTEM_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompanyOperationImpl <em>Company Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CompanyOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompanyOperation()
   * @generated
   */
  int COMPANY_OPERATION = 69;

  /**
   * The number of structural features of the '<em>Company Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPANY_OPERATION_FEATURE_COUNT = SYSTEM_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LanguageOperationImpl <em>Language Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LanguageOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLanguageOperation()
   * @generated
   */
  int LANGUAGE_OPERATION = 70;

  /**
   * The number of structural features of the '<em>Language Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LANGUAGE_OPERATION_FEATURE_COUNT = SYSTEM_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LocalCurrencyOperationImpl <em>Local Currency Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.LocalCurrencyOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLocalCurrencyOperation()
   * @generated
   */
  int LOCAL_CURRENCY_OPERATION = 71;

  /**
   * The number of structural features of the '<em>Local Currency Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_CURRENCY_OPERATION_FEATURE_COUNT = SYSTEM_OPERATION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TotalOperationImpl <em>Total Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.TotalOperationImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTotalOperation()
   * @generated
   */
  int TOTAL_OPERATION = 72;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOTAL_OPERATION__FIELD = OPERATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Total Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOTAL_OPERATION_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConversionImpl <em>Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConversion()
   * @generated
   */
  int CONVERSION = 73;

  /**
   * The number of structural features of the '<em>Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONVERSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl <em>Extract Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getExtractConversion()
   * @generated
   */
  int EXTRACT_CONVERSION = 74;

  /**
   * The feature id for the '<em><b>From</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRACT_CONVERSION__FROM = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>To</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRACT_CONVERSION__TO = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Delimiter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRACT_CONVERSION__DELIMITER = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Extract Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRACT_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DecryptConversionImpl <em>Decrypt Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.DecryptConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecryptConversion()
   * @generated
   */
  int DECRYPT_CONVERSION = 75;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECRYPT_CONVERSION__FIELD = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Decrypt Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECRYPT_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl <em>Replace Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getReplaceConversion()
   * @generated
   */
  int REPLACE_CONVERSION = 76;

  /**
   * The feature id for the '<em><b>Old Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLACE_CONVERSION__OLD_DATA = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>New Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLACE_CONVERSION__NEW_DATA = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Replace Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLACE_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConvertConversionImpl <em>Convert Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ConvertConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConvertConversion()
   * @generated
   */
  int CONVERT_CONVERSION = 77;

  /**
   * The feature id for the '<em><b>Old Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONVERT_CONVERSION__OLD_DATA = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>New Data</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONVERT_CONVERSION__NEW_DATA = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Convert Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONVERT_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl <em>Value Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getValueConversion()
   * @generated
   */
  int VALUE_CONVERSION = 78;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_CONVERSION__VALUE = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Sub Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_CONVERSION__SUB_VALUE = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Value Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JulianConversionImpl <em>Julian Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.JulianConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJulianConversion()
   * @generated
   */
  int JULIAN_CONVERSION = 79;

  /**
   * The number of structural features of the '<em>Julian Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JULIAN_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicConversionImpl <em>Basic Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicConversion()
   * @generated
   */
  int BASIC_CONVERSION = 80;

  /**
   * The feature id for the '<em><b>Instruction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_CONVERSION__INSTRUCTION = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Basic Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicOConversionImpl <em>Basic OConversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicOConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicOConversion()
   * @generated
   */
  int BASIC_OCONVERSION = 81;

  /**
   * The feature id for the '<em><b>Instruction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_OCONVERSION__INSTRUCTION = BASIC_CONVERSION__INSTRUCTION;

  /**
   * The number of structural features of the '<em>Basic OConversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_OCONVERSION_FEATURE_COUNT = BASIC_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicIConversionImpl <em>Basic IConversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicIConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicIConversion()
   * @generated
   */
  int BASIC_ICONVERSION = 82;

  /**
   * The feature id for the '<em><b>Instruction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_ICONVERSION__INSTRUCTION = BASIC_CONVERSION__INSTRUCTION;

  /**
   * The number of structural features of the '<em>Basic IConversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_ICONVERSION_FEATURE_COUNT = BASIC_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl <em>Get From Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFromConversion()
   * @generated
   */
  int GET_FROM_CONVERSION = 83;

  /**
   * The feature id for the '<em><b>Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FROM_CONVERSION__APPLICATION = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FROM_CONVERSION__FIELD = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Language</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FROM_CONVERSION__LANGUAGE = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Get From Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FROM_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RateConversionImpl <em>Rate Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RateConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRateConversion()
   * @generated
   */
  int RATE_CONVERSION = 84;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE_CONVERSION__FIELD = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Rate Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CalcFixedRateConversionImpl <em>Calc Fixed Rate Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CalcFixedRateConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCalcFixedRateConversion()
   * @generated
   */
  int CALC_FIXED_RATE_CONVERSION = 85;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALC_FIXED_RATE_CONVERSION__FIELD = RATE_CONVERSION__FIELD;

  /**
   * The number of structural features of the '<em>Calc Fixed Rate Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALC_FIXED_RATE_CONVERSION_FEATURE_COUNT = RATE_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFixedRateConversionImpl <em>Get Fixed Rate Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFixedRateConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFixedRateConversion()
   * @generated
   */
  int GET_FIXED_RATE_CONVERSION = 86;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FIXED_RATE_CONVERSION__FIELD = RATE_CONVERSION__FIELD;

  /**
   * The number of structural features of the '<em>Get Fixed Rate Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FIXED_RATE_CONVERSION_FEATURE_COUNT = RATE_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFixedCurrencyConversionImpl <em>Get Fixed Currency Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFixedCurrencyConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFixedCurrencyConversion()
   * @generated
   */
  int GET_FIXED_CURRENCY_CONVERSION = 87;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FIXED_CURRENCY_CONVERSION__FIELD = RATE_CONVERSION__FIELD;

  /**
   * The number of structural features of the '<em>Get Fixed Currency Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GET_FIXED_CURRENCY_CONVERSION_FEATURE_COUNT = RATE_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.AbsConversionImpl <em>Abs Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.AbsConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAbsConversion()
   * @generated
   */
  int ABS_CONVERSION = 88;

  /**
   * The number of structural features of the '<em>Abs Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABS_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.MatchFieldImpl <em>Match Field</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.MatchFieldImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getMatchField()
   * @generated
   */
  int MATCH_FIELD = 89;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MATCH_FIELD__PATTERN = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MATCH_FIELD__VALUE = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Match Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MATCH_FIELD_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CallRoutineImpl <em>Call Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.CallRoutineImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCallRoutine()
   * @generated
   */
  int CALL_ROUTINE = 90;

  /**
   * The feature id for the '<em><b>Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_ROUTINE__ROUTINE = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Call Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CALL_ROUTINE_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatConversionImpl <em>Repeat Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatConversion()
   * @generated
   */
  int REPEAT_CONVERSION = 91;

  /**
   * The number of structural features of the '<em>Repeat Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPEAT_CONVERSION_FEATURE_COUNT = CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatOnNullConversionImpl <em>Repeat On Null Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatOnNullConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatOnNullConversion()
   * @generated
   */
  int REPEAT_ON_NULL_CONVERSION = 92;

  /**
   * The number of structural features of the '<em>Repeat On Null Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPEAT_ON_NULL_CONVERSION_FEATURE_COUNT = REPEAT_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatEveryConversionImpl <em>Repeat Every Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatEveryConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatEveryConversion()
   * @generated
   */
  int REPEAT_EVERY_CONVERSION = 93;

  /**
   * The number of structural features of the '<em>Repeat Every Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPEAT_EVERY_CONVERSION_FEATURE_COUNT = REPEAT_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatSubConversionImpl <em>Repeat Sub Conversion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatSubConversionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatSubConversion()
   * @generated
   */
  int REPEAT_SUB_CONVERSION = 94;

  /**
   * The number of structural features of the '<em>Repeat Sub Conversion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPEAT_SUB_CONVERSION_FEATURE_COUNT = REPEAT_CONVERSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl <em>Field</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getField()
   * @generated
   */
  int FIELD = 95;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__NAME = 0;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__LABEL = 1;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__COMMENTS = 2;

  /**
   * The feature id for the '<em><b>Display Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__DISPLAY_TYPE = 3;

  /**
   * The feature id for the '<em><b>Format</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__FORMAT = 4;

  /**
   * The feature id for the '<em><b>Break Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__BREAK_CONDITION = 5;

  /**
   * The feature id for the '<em><b>Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__LENGTH = 6;

  /**
   * The feature id for the '<em><b>Alignment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ALIGNMENT = 7;

  /**
   * The feature id for the '<em><b>Comma Separator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__COMMA_SEPARATOR = 8;

  /**
   * The feature id for the '<em><b>Number Of Decimals</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__NUMBER_OF_DECIMALS = 9;

  /**
   * The feature id for the '<em><b>Escape Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ESCAPE_SEQUENCE = 10;

  /**
   * The feature id for the '<em><b>Fmt Mask</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__FMT_MASK = 11;

  /**
   * The feature id for the '<em><b>Display Section</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__DISPLAY_SECTION = 12;

  /**
   * The feature id for the '<em><b>Position</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__POSITION = 13;

  /**
   * The feature id for the '<em><b>Column Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__COLUMN_WIDTH = 14;

  /**
   * The feature id for the '<em><b>Spool Break</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__SPOOL_BREAK = 15;

  /**
   * The feature id for the '<em><b>Single Multi</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__SINGLE_MULTI = 16;

  /**
   * The feature id for the '<em><b>Hidden</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__HIDDEN = 17;

  /**
   * The feature id for the '<em><b>No Header</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__NO_HEADER = 18;

  /**
   * The feature id for the '<em><b>No Column Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__NO_COLUMN_LABEL = 19;

  /**
   * The feature id for the '<em><b>Operation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__OPERATION = 20;

  /**
   * The feature id for the '<em><b>Conversion</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__CONVERSION = 21;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ATTRIBUTES = 22;

  /**
   * The number of structural features of the '<em>Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_FEATURE_COUNT = 23;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakConditionImpl <em>Break Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakConditionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakCondition()
   * @generated
   */
  int BREAK_CONDITION = 96;

  /**
   * The feature id for the '<em><b>Break</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_CONDITION__BREAK = 0;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_CONDITION__FIELD = 1;

  /**
   * The number of structural features of the '<em>Break Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_CONDITION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl <em>Field Position</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldPosition()
   * @generated
   */
  int FIELD_POSITION = 97;

  /**
   * The feature id for the '<em><b>Page Throw</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION__PAGE_THROW = 0;

  /**
   * The feature id for the '<em><b>Column</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION__COLUMN = 1;

  /**
   * The feature id for the '<em><b>Relative</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION__RELATIVE = 2;

  /**
   * The feature id for the '<em><b>Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION__LINE = 3;

  /**
   * The feature id for the '<em><b>Multi Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION__MULTI_LINE = 4;

  /**
   * The number of structural features of the '<em>Field Position</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_POSITION_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FormatImpl <em>Format</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.FormatImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFormat()
   * @generated
   */
  int FORMAT = 98;

  /**
   * The feature id for the '<em><b>Format</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAT__FORMAT = 0;

  /**
   * The feature id for the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAT__FIELD = 1;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAT__PATTERN = 2;

  /**
   * The number of structural features of the '<em>Format</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ToolImpl <em>Tool</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.ToolImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTool()
   * @generated
   */
  int TOOL = 99;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOOL__NAME = 0;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOOL__LABEL = 1;

  /**
   * The feature id for the '<em><b>Command</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOOL__COMMAND = 2;

  /**
   * The number of structural features of the '<em>Tool</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOOL_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl <em>Web Service</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getWebService()
   * @generated
   */
  int WEB_SERVICE = 100;

  /**
   * The feature id for the '<em><b>Publish Web Service</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WEB_SERVICE__PUBLISH_WEB_SERVICE = 0;

  /**
   * The feature id for the '<em><b>Web Service Names</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WEB_SERVICE__WEB_SERVICE_NAMES = 1;

  /**
   * The feature id for the '<em><b>Web Service Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WEB_SERVICE__WEB_SERVICE_ACTIVITY = 2;

  /**
   * The feature id for the '<em><b>Web Service Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WEB_SERVICE__WEB_SERVICE_DESCRIPTION = 3;

  /**
   * The number of structural features of the '<em>Web Service</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WEB_SERVICE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryMode <em>Mode</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryMode
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryMode()
   * @generated
   */
  int ENQUIRY_MODE = 101;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.AlignmentKind <em>Alignment Kind</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.AlignmentKind
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAlignmentKind()
   * @generated
   */
  int ALIGNMENT_KIND = 102;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.BreakKind <em>Break Kind</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.BreakKind
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakKind()
   * @generated
   */
  int BREAK_KIND = 103;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.CurrencyPattern <em>Currency Pattern</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.CurrencyPattern
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCurrencyPattern()
   * @generated
   */
  int CURRENCY_PATTERN = 104;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperand <em>Decision Operand</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperand
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecisionOperand()
   * @generated
   */
  int DECISION_OPERAND = 105;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind <em>Display Section Kind</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDisplaySectionKind()
   * @generated
   */
  int DISPLAY_SECTION_KIND = 106;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.FieldFormat <em>Field Format</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.FieldFormat
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldFormat()
   * @generated
   */
  int FIELD_FORMAT = 107;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.FunctionKind <em>Function Kind</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.FunctionKind
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFunctionKind()
   * @generated
   */
  int FUNCTION_KIND = 108;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperator <em>Selection Operator</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperator
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionOperator()
   * @generated
   */
  int SELECTION_OPERATOR = 109;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.CriteriaOperator <em>Criteria Operator</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.CriteriaOperator
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCriteriaOperator()
   * @generated
   */
  int CRITERIA_OPERATOR = 110;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.ProcessingMode <em>Processing Mode</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.ProcessingMode
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getProcessingMode()
   * @generated
   */
  int PROCESSING_MODE = 111;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.SortOrder <em>Sort Order</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.SortOrder
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSortOrder()
   * @generated
   */
  int SORT_ORDER = 112;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.AndOr <em>And Or</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.AndOr
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAndOr()
   * @generated
   */
  int AND_OR = 113;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.FileVersionOption <em>File Version Option</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersionOption
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFileVersionOption()
   * @generated
   */
  int FILE_VERSION_OPTION = 114;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.EscapeSequence <em>Escape Sequence</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.EscapeSequence
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEscapeSequence()
   * @generated
   */
  int ESCAPE_SEQUENCE = 115;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.Orientation <em>Orientation</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.Orientation
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getOrientation()
   * @generated
   */
  int ORIENTATION = 116;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.enquiry.enquiry.ServerMode <em>Server Mode</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.enquiry.enquiry.ServerMode
   * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getServerMode()
   * @generated
   */
  int SERVER_MODE = 117;


  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry <em>Enquiry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enquiry</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry
   * @generated
   */
  EClass getEnquiry();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getName()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_Name();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileName <em>File Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileName()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_FileName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getMetamodelVersion <em>Metamodel Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metamodel Version</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getMetamodelVersion()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_MetamodelVersion();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getHeader <em>Header</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Header</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getHeader()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Header();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Description</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getDescription()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Description();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getServerMode <em>Server Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Server Mode</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getServerMode()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_ServerMode();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEnquiryMode <em>Enquiry Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Enquiry Mode</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getEnquiryMode()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_EnquiryMode();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCompanies <em>Companies</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Companies</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getCompanies()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Companies();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getAccountField <em>Account Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Account Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getAccountField()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_AccountField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomerField <em>Customer Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Customer Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomerField()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_CustomerField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getZeroRecordsDisplay <em>Zero Records Display</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Zero Records Display</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getZeroRecordsDisplay()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_ZeroRecordsDisplay();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getNoSelection <em>No Selection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>No Selection</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getNoSelection()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_NoSelection();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getShowAllBooks <em>Show All Books</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Show All Books</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getShowAllBooks()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_ShowAllBooks();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getStartLine <em>Start Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getStartLine()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_StartLine();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEndLine <em>End Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getEndLine()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_EndLine();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getBuildRoutines <em>Build Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Build Routines</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getBuildRoutines()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_BuildRoutines();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSelections <em>Fixed Selections</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fixed Selections</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSelections()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_FixedSelections();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSorts <em>Fixed Sorts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fixed Sorts</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSorts()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_FixedSorts();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomSelection <em>Custom Selection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Custom Selection</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomSelection()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_CustomSelection();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFields <em>Fields</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fields</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getFields()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Fields();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getToolbar <em>Toolbar</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Toolbar</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getToolbar()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_Toolbar();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getTools <em>Tools</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Tools</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getTools()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Tools();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Target</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getTarget()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Target();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getDrillDowns <em>Drill Downs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Drill Downs</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getDrillDowns()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_DrillDowns();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getSecurity <em>Security</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Security</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getSecurity()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Security();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGraph <em>Graph</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Graph</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getGraph()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_Graph();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getWebService <em>Web Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Web Service</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getWebService()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_WebService();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGenerateIFP <em>Generate IFP</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Generate IFP</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getGenerateIFP()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_GenerateIFP();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileVersion <em>File Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>File Version</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileVersion()
   * @see #getEnquiry()
   * @generated
   */
  EReference getEnquiry_FileVersion();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Attributes</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getAttributes()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_Attributes();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getIntrospectionMessages <em>Introspection Messages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Introspection Messages</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry#getIntrospectionMessages()
   * @see #getEnquiry()
   * @generated
   */
  EAttribute getEnquiry_IntrospectionMessages();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Companies <em>Companies</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Companies</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Companies
   * @generated
   */
  EClass getCompanies();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Companies#getAll <em>All</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>All</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Companies#getAll()
   * @see #getCompanies()
   * @generated
   */
  EAttribute getCompanies_All();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Companies#getCode <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Code</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Companies#getCode()
   * @see #getCompanies()
   * @generated
   */
  EAttribute getCompanies_Code();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader <em>Header</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Header</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryHeader
   * @generated
   */
  EClass getEnquiryHeader();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLabel()
   * @see #getEnquiryHeader()
   * @generated
   */
  EReference getEnquiryHeader_Label();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getColumn <em>Column</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Column</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getColumn()
   * @see #getEnquiryHeader()
   * @generated
   */
  EAttribute getEnquiryHeader_Column();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLine <em>Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryHeader#getLine()
   * @see #getEnquiryHeader()
   * @generated
   */
  EAttribute getEnquiryHeader_Line();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Target <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Target</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Target
   * @generated
   */
  EClass getTarget();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Target#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Target#getApplication()
   * @see #getTarget()
   * @generated
   */
  EAttribute getTarget_Application();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Target#getScreen <em>Screen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Screen</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Target#getScreen()
   * @see #getTarget()
   * @generated
   */
  EAttribute getTarget_Screen();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Target#getMappings <em>Mappings</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mappings</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Target#getMappings()
   * @see #getTarget()
   * @generated
   */
  EReference getTarget_Mappings();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping <em>Target Mapping</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Target Mapping</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TargetMapping
   * @generated
   */
  EClass getTargetMapping();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getFromField <em>From Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TargetMapping#getFromField()
   * @see #getTargetMapping()
   * @generated
   */
  EAttribute getTargetMapping_FromField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping#getToField <em>To Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TargetMapping#getToField()
   * @see #getTargetMapping()
   * @generated
   */
  EAttribute getTargetMapping_ToField();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Parameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameters</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters
   * @generated
   */
  EClass getParameters();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getFunction <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Function</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#getFunction()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_Function();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isAuto <em>Auto</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Auto</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#isAuto()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_Auto();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isRunImmediately <em>Run Immediately</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Run Immediately</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#isRunImmediately()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_RunImmediately();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getPwActivity <em>Pw Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pw Activity</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#getPwActivity()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_PwActivity();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getFieldName <em>Field Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Field Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#getFieldName()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_FieldName();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getVariable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Variable</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters#getVariable()
   * @see #getParameters()
   * @generated
   */
  EAttribute getParameters_Variable();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown <em>Drill Down</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Drill Down</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown
   * @generated
   */
  EClass getDrillDown();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDrill_name <em>Drill name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Drill name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getDrill_name()
   * @see #getDrillDown()
   * @generated
   */
  EAttribute getDrillDown_Drill_name();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Description</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getDescription()
   * @see #getDrillDown()
   * @generated
   */
  EReference getDrillDown_Description();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getLabelField <em>Label Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getLabelField()
   * @see #getDrillDown()
   * @generated
   */
  EAttribute getDrillDown_LabelField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getImage <em>Image</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Image</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getImage()
   * @see #getDrillDown()
   * @generated
   */
  EAttribute getDrillDown_Image();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getInfo <em>Info</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Info</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getInfo()
   * @see #getDrillDown()
   * @generated
   */
  EAttribute getDrillDown_Info();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getCriteria <em>Criteria</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Criteria</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getCriteria()
   * @see #getDrillDown()
   * @generated
   */
  EReference getDrillDown_Criteria();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Parameters</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getParameters()
   * @see #getDrillDown()
   * @generated
   */
  EReference getDrillDown_Parameters();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown#getType()
   * @see #getDrillDown()
   * @generated
   */
  EReference getDrillDown_Type();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownType <em>Drill Down Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Drill Down Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownType
   * @generated
   */
  EClass getDrillDownType();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownType#getProperty <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Property</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownType#getProperty()
   * @see #getDrillDownType()
   * @generated
   */
  EAttribute getDrillDownType_Property();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownStringType <em>Drill Down String Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Drill Down String Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownStringType
   * @generated
   */
  EClass getDrillDownStringType();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownStringType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownStringType#getValue()
   * @see #getDrillDownStringType()
   * @generated
   */
  EAttribute getDrillDownStringType_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ApplicationType <em>Application Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Application Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ApplicationType
   * @generated
   */
  EClass getApplicationType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ScreenType <em>Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Screen Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ScreenType
   * @generated
   */
  EClass getScreenType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryType
   * @generated
   */
  EClass getEnquiryType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FromFieldType <em>From Field Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>From Field Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FromFieldType
   * @generated
   */
  EClass getFromFieldType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenType <em>Composite Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Composite Screen Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenType
   * @generated
   */
  EClass getCompositeScreenType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.TabbedScreenType <em>Tabbed Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Tabbed Screen Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TabbedScreenType
   * @generated
   */
  EClass getTabbedScreenType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ViewType <em>View Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>View Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ViewType
   * @generated
   */
  EClass getViewType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.QuitSEEType <em>Quit SEE Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Quit SEE Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.QuitSEEType
   * @generated
   */
  EClass getQuitSEEType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BlankType <em>Blank Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Blank Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BlankType
   * @generated
   */
  EClass getBlankType();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BlankType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BlankType#getValue()
   * @see #getBlankType()
   * @generated
   */
  EAttribute getBlankType_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.PWProcessType <em>PW Process Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>PW Process Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.PWProcessType
   * @generated
   */
  EClass getPWProcessType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DownloadType <em>Download Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Download Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DownloadType
   * @generated
   */
  EClass getDownloadType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RunType <em>Run Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Run Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RunType
   * @generated
   */
  EClass getRunType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.UtilType <em>Util Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Util Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.UtilType
   * @generated
   */
  EClass getUtilType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.JavaScriptType <em>Java Script Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Java Script Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.JavaScriptType
   * @generated
   */
  EClass getJavaScriptType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType <em>Should Be Changed Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Should Be Changed Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType
   * @generated
   */
  EClass getShouldBeChangedType();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownOption <em>Drill Down Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Drill Down Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownOption
   * @generated
   */
  EClass getDrillDownOption();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption <em>Composite Screen Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Composite Screen Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption
   * @generated
   */
  EClass getCompositeScreenOption();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getCompositeScreen <em>Composite Screen</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Composite Screen</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getCompositeScreen()
   * @see #getCompositeScreenOption()
   * @generated
   */
  EAttribute getCompositeScreenOption_CompositeScreen();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getReference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Reference</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getReference()
   * @see #getCompositeScreenOption()
   * @generated
   */
  EReference getCompositeScreenOption_Reference();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getFieldParameter <em>Field Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Field Parameter</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getFieldParameter()
   * @see #getCompositeScreenOption()
   * @generated
   */
  EReference getCompositeScreenOption_FieldParameter();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.TabOption <em>Tab Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Tab Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TabOption
   * @generated
   */
  EClass getTabOption();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getTabName <em>Tab Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tab Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TabOption#getTabName()
   * @see #getTabOption()
   * @generated
   */
  EAttribute getTabOption_TabName();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getReference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Reference</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TabOption#getReference()
   * @see #getTabOption()
   * @generated
   */
  EReference getTabOption_Reference();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.TabOption#getFieldParameter <em>Field Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Field Parameter</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TabOption#getFieldParameter()
   * @see #getTabOption()
   * @generated
   */
  EReference getTabOption_FieldParameter();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ViewOption <em>View Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>View Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ViewOption
   * @generated
   */
  EClass getViewOption();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.QuitSEEOption <em>Quit SEE Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Quit SEE Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.QuitSEEOption
   * @generated
   */
  EClass getQuitSEEOption();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Reference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reference</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Reference
   * @generated
   */
  EClass getReference();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Reference#getFile <em>File</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Reference#getFile()
   * @see #getReference()
   * @generated
   */
  EAttribute getReference_File();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Reference#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Reference#getField()
   * @see #getReference()
   * @generated
   */
  EAttribute getReference_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Parameter#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameter#getField()
   * @see #getParameter()
   * @generated
   */
  EAttribute getParameter_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria <em>Selection Criteria</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Selection Criteria</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionCriteria
   * @generated
   */
  EClass getSelectionCriteria();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getField()
   * @see #getSelectionCriteria()
   * @generated
   */
  EAttribute getSelectionCriteria_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getOperand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operand</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getOperand()
   * @see #getSelectionCriteria()
   * @generated
   */
  EAttribute getSelectionCriteria_Operand();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionCriteria#getValues()
   * @see #getSelectionCriteria()
   * @generated
   */
  EAttribute getSelectionCriteria_Values();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Security <em>Security</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Security</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Security
   * @generated
   */
  EClass getSecurity();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Security#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Security#getApplication()
   * @see #getSecurity()
   * @generated
   */
  EAttribute getSecurity_Application();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Security#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Security#getField()
   * @see #getSecurity()
   * @generated
   */
  EAttribute getSecurity_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Security#isAbort <em>Abort</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Abort</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Security#isAbort()
   * @see #getSecurity()
   * @generated
   */
  EAttribute getSecurity_Abort();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Graph <em>Graph</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Graph</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph
   * @generated
   */
  EClass getGraph();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getType()
   * @see #getGraph()
   * @generated
   */
  EAttribute getGraph_Type();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getLabels <em>Labels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Labels</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getLabels()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_Labels();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getDimension <em>Dimension</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Dimension</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getDimension()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_Dimension();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getMargins <em>Margins</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Margins</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getMargins()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_Margins();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getScale <em>Scale</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Scale</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getScale()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_Scale();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getLegend <em>Legend</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Legend</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getLegend()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_Legend();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getXAxis <em>XAxis</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>XAxis</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getXAxis()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_XAxis();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getYAxis <em>YAxis</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>YAxis</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getYAxis()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_YAxis();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Graph#getZAxis <em>ZAxis</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>ZAxis</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph#getZAxis()
   * @see #getGraph()
   * @generated
   */
  EReference getGraph_ZAxis();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Axis <em>Axis</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Axis</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Axis
   * @generated
   */
  EClass getAxis();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Axis#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Axis#getField()
   * @see #getAxis()
   * @generated
   */
  EAttribute getAxis_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Axis#isDisplayLegend <em>Display Legend</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Legend</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Axis#isDisplayLegend()
   * @see #getAxis()
   * @generated
   */
  EAttribute getAxis_DisplayLegend();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Axis#isShowGrid <em>Show Grid</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Show Grid</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Axis#isShowGrid()
   * @see #getAxis()
   * @generated
   */
  EAttribute getAxis_ShowGrid();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Dimension <em>Dimension</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Dimension</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Dimension
   * @generated
   */
  EClass getDimension();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getWidth <em>Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Width</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Dimension#getWidth()
   * @see #getDimension()
   * @generated
   */
  EAttribute getDimension_Width();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getHeight <em>Height</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Height</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Dimension#getHeight()
   * @see #getDimension()
   * @generated
   */
  EAttribute getDimension_Height();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Dimension#getOrientation <em>Orientation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Orientation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Dimension#getOrientation()
   * @see #getDimension()
   * @generated
   */
  EAttribute getDimension_Orientation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Label <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Label
   * @generated
   */
  EClass getLabel();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Label#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Description</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Label#getDescription()
   * @see #getLabel()
   * @generated
   */
  EReference getLabel_Description();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Label#getPosition <em>Position</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Position</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Label#getPosition()
   * @see #getLabel()
   * @generated
   */
  EReference getLabel_Position();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Position <em>Position</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Position</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Position
   * @generated
   */
  EClass getPosition();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Position#getX <em>X</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>X</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Position#getX()
   * @see #getPosition()
   * @generated
   */
  EAttribute getPosition_X();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Position#getY <em>Y</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Y</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Position#getY()
   * @see #getPosition()
   * @generated
   */
  EAttribute getPosition_Y();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Legend <em>Legend</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Legend</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Legend
   * @generated
   */
  EClass getLegend();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Legend#getX <em>X</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>X</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Legend#getX()
   * @see #getLegend()
   * @generated
   */
  EAttribute getLegend_X();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Legend#getY <em>Y</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Y</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Legend#getY()
   * @see #getLegend()
   * @generated
   */
  EAttribute getLegend_Y();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Margins <em>Margins</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Margins</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins
   * @generated
   */
  EClass getMargins();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Margins#getTop <em>Top</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Top</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins#getTop()
   * @see #getMargins()
   * @generated
   */
  EAttribute getMargins_Top();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Margins#getBottom <em>Bottom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Bottom</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins#getBottom()
   * @see #getMargins()
   * @generated
   */
  EAttribute getMargins_Bottom();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Margins#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Left</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins#getLeft()
   * @see #getMargins()
   * @generated
   */
  EAttribute getMargins_Left();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Margins#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Right</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins#getRight()
   * @see #getMargins()
   * @generated
   */
  EAttribute getMargins_Right();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Scale <em>Scale</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Scale</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Scale
   * @generated
   */
  EClass getScale();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Scale#getX <em>X</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>X</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Scale#getX()
   * @see #getScale()
   * @generated
   */
  EAttribute getScale_X();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Scale#getY <em>Y</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Y</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Scale#getY()
   * @see #getScale()
   * @generated
   */
  EAttribute getScale_Y();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Routine <em>Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Routine</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Routine
   * @generated
   */
  EClass getRoutine();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Routine#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Routine#getName()
   * @see #getRoutine()
   * @generated
   */
  EAttribute getRoutine_Name();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.JBCRoutine <em>JBC Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>JBC Routine</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.JBCRoutine
   * @generated
   */
  EClass getJBCRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.JavaRoutine <em>Java Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Java Routine</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.JavaRoutine
   * @generated
   */
  EClass getJavaRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FixedSelection <em>Fixed Selection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fixed Selection</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSelection
   * @generated
   */
  EClass getFixedSelection();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FixedSelection#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSelection#getField()
   * @see #getFixedSelection()
   * @generated
   */
  EAttribute getFixedSelection_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FixedSelection#getOperand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operand</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSelection#getOperand()
   * @see #getFixedSelection()
   * @generated
   */
  EAttribute getFixedSelection_Operand();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.FixedSelection#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSelection#getValues()
   * @see #getFixedSelection()
   * @generated
   */
  EAttribute getFixedSelection_Values();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort <em>Fixed Sort</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fixed Sort</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSort
   * @generated
   */
  EClass getFixedSort();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSort#getField()
   * @see #getFixedSort()
   * @generated
   */
  EAttribute getFixedSort_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort#getOrder <em>Order</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Order</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSort#getOrder()
   * @see #getFixedSort()
   * @generated
   */
  EAttribute getFixedSort_Order();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionExpression <em>Selection Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Selection Expression</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionExpression
   * @generated
   */
  EClass getSelectionExpression();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.SelectionExpression#getSelection <em>Selection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Selection</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionExpression#getSelection()
   * @see #getSelectionExpression()
   * @generated
   */
  EReference getSelectionExpression_Selection();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Selection <em>Selection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Selection</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection
   * @generated
   */
  EClass getSelection();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getField()
   * @see #getSelection()
   * @generated
   */
  EAttribute getSelection_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getMandatory <em>Mandatory</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mandatory</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getMandatory()
   * @see #getSelection()
   * @generated
   */
  EAttribute getSelection_Mandatory();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getPopupDropDown <em>Popup Drop Down</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Popup Drop Down</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getPopupDropDown()
   * @see #getSelection()
   * @generated
   */
  EAttribute getSelection_PopupDropDown();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getLabel()
   * @see #getSelection()
   * @generated
   */
  EReference getSelection_Label();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getOperands <em>Operands</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Operands</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getOperands()
   * @see #getSelection()
   * @generated
   */
  EAttribute getSelection_Operands();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection#getOperator()
   * @see #getSelection()
   * @generated
   */
  EAttribute getSelection_Operator();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FileVersion <em>File Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Version</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersion
   * @generated
   */
  EClass getFileVersion();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.FileVersion#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersion#getValues()
   * @see #getFileVersion()
   * @generated
   */
  EAttribute getFileVersion_Values();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Operation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Operation
   * @generated
   */
  EClass getOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BreakOperation <em>Break Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Break Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakOperation
   * @generated
   */
  EClass getBreakOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation <em>Break On Change Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Break On Change Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation
   * @generated
   */
  EClass getBreakOnChangeOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation#getField()
   * @see #getBreakOnChangeOperation()
   * @generated
   */
  EAttribute getBreakOnChangeOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BreakLineOperation <em>Break Line Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Break Line Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakLineOperation
   * @generated
   */
  EClass getBreakLineOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BreakLineOperation#getLine <em>Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakLineOperation#getLine()
   * @see #getBreakLineOperation()
   * @generated
   */
  EAttribute getBreakLineOperation_Line();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CalcOperation <em>Calc Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Calc Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcOperation
   * @generated
   */
  EClass getCalcOperation();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.CalcOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcOperation#getField()
   * @see #getCalcOperation()
   * @generated
   */
  EAttribute getCalcOperation_Field();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.CalcOperation#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Operator</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcOperation#getOperator()
   * @see #getCalcOperation()
   * @generated
   */
  EAttribute getCalcOperation_Operator();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ConstantOperation <em>Constant Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constant Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ConstantOperation
   * @generated
   */
  EClass getConstantOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ConstantOperation#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ConstantOperation#getValue()
   * @see #getConstantOperation()
   * @generated
   */
  EAttribute getConstantOperation_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.LabelOperation <em>Label Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Label Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.LabelOperation
   * @generated
   */
  EClass getLabelOperation();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.LabelOperation#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.LabelOperation#getLabel()
   * @see #getLabelOperation()
   * @generated
   */
  EReference getLabelOperation_Label();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DateOperation <em>Date Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Date Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DateOperation
   * @generated
   */
  EClass getDateOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation <em>Decision Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Decision Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation
   * @generated
   */
  EClass getDecisionOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getLeftField <em>Left Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Left Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getLeftField()
   * @see #getDecisionOperation()
   * @generated
   */
  EAttribute getDecisionOperation_LeftField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getOperand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operand</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getOperand()
   * @see #getDecisionOperation()
   * @generated
   */
  EAttribute getDecisionOperation_Operand();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getRightField <em>Right Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Right Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getRightField()
   * @see #getDecisionOperation()
   * @generated
   */
  EAttribute getDecisionOperation_RightField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getFirstField <em>First Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>First Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getFirstField()
   * @see #getDecisionOperation()
   * @generated
   */
  EAttribute getDecisionOperation_FirstField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getSecondField <em>Second Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Second Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation#getSecondField()
   * @see #getDecisionOperation()
   * @generated
   */
  EAttribute getDecisionOperation_SecondField();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DescriptorOperation <em>Descriptor Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Descriptor Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DescriptorOperation
   * @generated
   */
  EClass getDescriptorOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DescriptorOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DescriptorOperation#getField()
   * @see #getDescriptorOperation()
   * @generated
   */
  EAttribute getDescriptorOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.TodayOperation <em>Today Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Today Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TodayOperation
   * @generated
   */
  EClass getTodayOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.LWDOperation <em>LWD Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>LWD Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.LWDOperation
   * @generated
   */
  EClass getLWDOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.NWDOperation <em>NWD Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>NWD Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.NWDOperation
   * @generated
   */
  EClass getNWDOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FieldOperation <em>Field Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldOperation
   * @generated
   */
  EClass getFieldOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation <em>Application Field Name Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Application Field Name Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation
   * @generated
   */
  EClass getApplicationFieldNameOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation#getField()
   * @see #getApplicationFieldNameOperation()
   * @generated
   */
  EAttribute getApplicationFieldNameOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation <em>Field Number Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field Number Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation
   * @generated
   */
  EClass getFieldNumberOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation#getNumber <em>Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation#getNumber()
   * @see #getFieldNumberOperation()
   * @generated
   */
  EAttribute getFieldNumberOperation_Number();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation <em>Field Extract Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field Extract Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation
   * @generated
   */
  EClass getFieldExtractOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation#getField()
   * @see #getFieldExtractOperation()
   * @generated
   */
  EAttribute getFieldExtractOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperation <em>Selection Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Selection Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperation
   * @generated
   */
  EClass getSelectionOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperation#getField()
   * @see #getSelectionOperation()
   * @generated
   */
  EAttribute getSelectionOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.SystemOperation <em>System Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>System Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SystemOperation
   * @generated
   */
  EClass getSystemOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.UserOperation <em>User Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>User Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.UserOperation
   * @generated
   */
  EClass getUserOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CompanyOperation <em>Company Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Company Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CompanyOperation
   * @generated
   */
  EClass getCompanyOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.LanguageOperation <em>Language Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Language Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.LanguageOperation
   * @generated
   */
  EClass getLanguageOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation <em>Local Currency Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Currency Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation
   * @generated
   */
  EClass getLocalCurrencyOperation();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.TotalOperation <em>Total Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Total Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TotalOperation
   * @generated
   */
  EClass getTotalOperation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.TotalOperation#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.TotalOperation#getField()
   * @see #getTotalOperation()
   * @generated
   */
  EAttribute getTotalOperation_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Conversion <em>Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Conversion
   * @generated
   */
  EClass getConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion <em>Extract Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Extract Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ExtractConversion
   * @generated
   */
  EClass getExtractConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getFrom <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getFrom()
   * @see #getExtractConversion()
   * @generated
   */
  EAttribute getExtractConversion_From();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getTo <em>To</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getTo()
   * @see #getExtractConversion()
   * @generated
   */
  EAttribute getExtractConversion_To();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getDelimiter <em>Delimiter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Delimiter</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ExtractConversion#getDelimiter()
   * @see #getExtractConversion()
   * @generated
   */
  EAttribute getExtractConversion_Delimiter();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.DecryptConversion <em>Decrypt Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Decrypt Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecryptConversion
   * @generated
   */
  EClass getDecryptConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.DecryptConversion#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecryptConversion#getField()
   * @see #getDecryptConversion()
   * @generated
   */
  EAttribute getDecryptConversion_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion <em>Replace Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Replace Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ReplaceConversion
   * @generated
   */
  EClass getReplaceConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getOldData <em>Old Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Old Data</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getOldData()
   * @see #getReplaceConversion()
   * @generated
   */
  EAttribute getReplaceConversion_OldData();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getNewData <em>New Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Data</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ReplaceConversion#getNewData()
   * @see #getReplaceConversion()
   * @generated
   */
  EAttribute getReplaceConversion_NewData();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ConvertConversion <em>Convert Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Convert Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ConvertConversion
   * @generated
   */
  EClass getConvertConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ConvertConversion#getOldData <em>Old Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Old Data</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ConvertConversion#getOldData()
   * @see #getConvertConversion()
   * @generated
   */
  EAttribute getConvertConversion_OldData();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ConvertConversion#getNewData <em>New Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Data</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ConvertConversion#getNewData()
   * @see #getConvertConversion()
   * @generated
   */
  EAttribute getConvertConversion_NewData();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion <em>Value Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ValueConversion
   * @generated
   */
  EClass getValueConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ValueConversion#getValue()
   * @see #getValueConversion()
   * @generated
   */
  EAttribute getValueConversion_Value();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion#getSubValue <em>Sub Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sub Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ValueConversion#getSubValue()
   * @see #getValueConversion()
   * @generated
   */
  EAttribute getValueConversion_SubValue();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.JulianConversion <em>Julian Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Julian Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.JulianConversion
   * @generated
   */
  EClass getJulianConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BasicConversion <em>Basic Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Basic Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicConversion
   * @generated
   */
  EClass getBasicConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BasicConversion#getInstruction <em>Instruction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Instruction</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicConversion#getInstruction()
   * @see #getBasicConversion()
   * @generated
   */
  EAttribute getBasicConversion_Instruction();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BasicOConversion <em>Basic OConversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Basic OConversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicOConversion
   * @generated
   */
  EClass getBasicOConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BasicIConversion <em>Basic IConversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Basic IConversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicIConversion
   * @generated
   */
  EClass getBasicIConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion <em>Get From Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Get From Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFromConversion
   * @generated
   */
  EClass getGetFromConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getApplication()
   * @see #getGetFromConversion()
   * @generated
   */
  EAttribute getGetFromConversion_Application();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFromConversion#getField()
   * @see #getGetFromConversion()
   * @generated
   */
  EAttribute getGetFromConversion_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion#isLanguage <em>Language</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Language</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFromConversion#isLanguage()
   * @see #getGetFromConversion()
   * @generated
   */
  EAttribute getGetFromConversion_Language();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RateConversion <em>Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Rate Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RateConversion
   * @generated
   */
  EClass getRateConversion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.RateConversion#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RateConversion#getField()
   * @see #getRateConversion()
   * @generated
   */
  EAttribute getRateConversion_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion <em>Calc Fixed Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Calc Fixed Rate Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion
   * @generated
   */
  EClass getCalcFixedRateConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion <em>Get Fixed Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Get Fixed Rate Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion
   * @generated
   */
  EClass getGetFixedRateConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion <em>Get Fixed Currency Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Get Fixed Currency Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion
   * @generated
   */
  EClass getGetFixedCurrencyConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.AbsConversion <em>Abs Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Abs Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.AbsConversion
   * @generated
   */
  EClass getAbsConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.MatchField <em>Match Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Match Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.MatchField
   * @generated
   */
  EClass getMatchField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pattern</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.MatchField#getPattern()
   * @see #getMatchField()
   * @generated
   */
  EAttribute getMatchField_Pattern();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.MatchField#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.MatchField#getValue()
   * @see #getMatchField()
   * @generated
   */
  EAttribute getMatchField_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.CallRoutine <em>Call Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Call Routine</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CallRoutine
   * @generated
   */
  EClass getCallRoutine();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.CallRoutine#getRoutine <em>Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Routine</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CallRoutine#getRoutine()
   * @see #getCallRoutine()
   * @generated
   */
  EReference getCallRoutine_Routine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatConversion <em>Repeat Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repeat Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatConversion
   * @generated
   */
  EClass getRepeatConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion <em>Repeat On Null Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repeat On Null Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion
   * @generated
   */
  EClass getRepeatOnNullConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion <em>Repeat Every Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repeat Every Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion
   * @generated
   */
  EClass getRepeatEveryConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion <em>Repeat Sub Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repeat Sub Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion
   * @generated
   */
  EClass getRepeatSubConversion();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Field <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field
   * @generated
   */
  EClass getField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getName()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Name();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Field#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getLabel()
   * @see #getField()
   * @generated
   */
  EReference getField_Label();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getComments <em>Comments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Comments</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getComments()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Comments();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplayType <em>Display Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Type</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getDisplayType()
   * @see #getField()
   * @generated
   */
  EAttribute getField_DisplayType();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Field#getFormat <em>Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Format</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getFormat()
   * @see #getField()
   * @generated
   */
  EReference getField_Format();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Field#getBreakCondition <em>Break Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Break Condition</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getBreakCondition()
   * @see #getField()
   * @generated
   */
  EReference getField_BreakCondition();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getLength <em>Length</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Length</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getLength()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Length();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getAlignment <em>Alignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Alignment</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getAlignment()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Alignment();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getCommaSeparator <em>Comma Separator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Comma Separator</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getCommaSeparator()
   * @see #getField()
   * @generated
   */
  EAttribute getField_CommaSeparator();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNumberOfDecimals <em>Number Of Decimals</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Decimals</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getNumberOfDecimals()
   * @see #getField()
   * @generated
   */
  EAttribute getField_NumberOfDecimals();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getEscapeSequence <em>Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Escape Sequence</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getEscapeSequence()
   * @see #getField()
   * @generated
   */
  EAttribute getField_EscapeSequence();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getFmtMask <em>Fmt Mask</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fmt Mask</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getFmtMask()
   * @see #getField()
   * @generated
   */
  EAttribute getField_FmtMask();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplaySection <em>Display Section</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Section</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getDisplaySection()
   * @see #getField()
   * @generated
   */
  EAttribute getField_DisplaySection();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Field#getPosition <em>Position</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Position</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getPosition()
   * @see #getField()
   * @generated
   */
  EReference getField_Position();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getColumnWidth <em>Column Width</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Column Width</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getColumnWidth()
   * @see #getField()
   * @generated
   */
  EAttribute getField_ColumnWidth();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getSpoolBreak <em>Spool Break</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Spool Break</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getSpoolBreak()
   * @see #getField()
   * @generated
   */
  EAttribute getField_SpoolBreak();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getSingleMulti <em>Single Multi</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Single Multi</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getSingleMulti()
   * @see #getField()
   * @generated
   */
  EAttribute getField_SingleMulti();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getHidden <em>Hidden</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hidden</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getHidden()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Hidden();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoHeader <em>No Header</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>No Header</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getNoHeader()
   * @see #getField()
   * @generated
   */
  EAttribute getField_NoHeader();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoColumnLabel <em>No Column Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>No Column Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getNoColumnLabel()
   * @see #getField()
   * @generated
   */
  EAttribute getField_NoColumnLabel();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Field#getOperation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getOperation()
   * @see #getField()
   * @generated
   */
  EReference getField_Operation();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.enquiry.enquiry.Field#getConversion <em>Conversion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Conversion</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getConversion()
   * @see #getField()
   * @generated
   */
  EReference getField_Conversion();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Field#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Attributes</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Field#getAttributes()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Attributes();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition <em>Break Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Break Condition</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakCondition
   * @generated
   */
  EClass getBreakCondition();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getBreak <em>Break</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Break</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakCondition#getBreak()
   * @see #getBreakCondition()
   * @generated
   */
  EAttribute getBreakCondition_Break();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakCondition#getField()
   * @see #getBreakCondition()
   * @generated
   */
  EAttribute getBreakCondition_Field();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition <em>Field Position</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field Position</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition
   * @generated
   */
  EClass getFieldPosition();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getPageThrow <em>Page Throw</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Page Throw</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition#getPageThrow()
   * @see #getFieldPosition()
   * @generated
   */
  EAttribute getFieldPosition_PageThrow();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getColumn <em>Column</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Column</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition#getColumn()
   * @see #getFieldPosition()
   * @generated
   */
  EAttribute getFieldPosition_Column();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getRelative <em>Relative</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Relative</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition#getRelative()
   * @see #getFieldPosition()
   * @generated
   */
  EAttribute getFieldPosition_Relative();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getLine <em>Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition#getLine()
   * @see #getFieldPosition()
   * @generated
   */
  EAttribute getFieldPosition_Line();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition#getMultiLine <em>Multi Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Multi Line</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition#getMultiLine()
   * @see #getFieldPosition()
   * @generated
   */
  EAttribute getFieldPosition_MultiLine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Format <em>Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Format</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Format
   * @generated
   */
  EClass getFormat();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Format#getFormat <em>Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Format</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Format#getFormat()
   * @see #getFormat()
   * @generated
   */
  EAttribute getFormat_Format();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Format#getField <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Field</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Format#getField()
   * @see #getFormat()
   * @generated
   */
  EAttribute getFormat_Field();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Format#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pattern</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Format#getPattern()
   * @see #getFormat()
   * @generated
   */
  EAttribute getFormat_Pattern();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.Tool <em>Tool</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Tool</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Tool
   * @generated
   */
  EClass getTool();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.Tool#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Tool#getName()
   * @see #getTool()
   * @generated
   */
  EAttribute getTool_Name();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.enquiry.enquiry.Tool#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Tool#getLabel()
   * @see #getTool()
   * @generated
   */
  EReference getTool_Label();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.Tool#getCommand <em>Command</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Command</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Tool#getCommand()
   * @see #getTool()
   * @generated
   */
  EAttribute getTool_Command();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.enquiry.enquiry.WebService <em>Web Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Web Service</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService
   * @generated
   */
  EClass getWebService();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getPublishWebService <em>Publish Web Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Publish Web Service</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService#getPublishWebService()
   * @see #getWebService()
   * @generated
   */
  EAttribute getWebService_PublishWebService();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceNames <em>Web Service Names</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Web Service Names</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceNames()
   * @see #getWebService()
   * @generated
   */
  EAttribute getWebService_WebServiceNames();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceActivity <em>Web Service Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Service Activity</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceActivity()
   * @see #getWebService()
   * @generated
   */
  EAttribute getWebService_WebServiceActivity();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceDescription <em>Web Service Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Service Description</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService#getWebServiceDescription()
   * @see #getWebService()
   * @generated
   */
  EAttribute getWebService_WebServiceDescription();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryMode <em>Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Mode</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryMode
   * @generated
   */
  EEnum getEnquiryMode();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.AlignmentKind <em>Alignment Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Alignment Kind</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.AlignmentKind
   * @generated
   */
  EEnum getAlignmentKind();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.BreakKind <em>Break Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Break Kind</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakKind
   * @generated
   */
  EEnum getBreakKind();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.CurrencyPattern <em>Currency Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Currency Pattern</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CurrencyPattern
   * @generated
   */
  EEnum getCurrencyPattern();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperand <em>Decision Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Decision Operand</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperand
   * @generated
   */
  EEnum getDecisionOperand();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind <em>Display Section Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Display Section Kind</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind
   * @generated
   */
  EEnum getDisplaySectionKind();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.FieldFormat <em>Field Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Field Format</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldFormat
   * @generated
   */
  EEnum getFieldFormat();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.FunctionKind <em>Function Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Function Kind</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FunctionKind
   * @generated
   */
  EEnum getFunctionKind();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperator <em>Selection Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Selection Operator</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperator
   * @generated
   */
  EEnum getSelectionOperator();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.CriteriaOperator <em>Criteria Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Criteria Operator</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.CriteriaOperator
   * @generated
   */
  EEnum getCriteriaOperator();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.ProcessingMode <em>Processing Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Processing Mode</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ProcessingMode
   * @generated
   */
  EEnum getProcessingMode();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.SortOrder <em>Sort Order</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Sort Order</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.SortOrder
   * @generated
   */
  EEnum getSortOrder();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.AndOr <em>And Or</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>And Or</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.AndOr
   * @generated
   */
  EEnum getAndOr();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.FileVersionOption <em>File Version Option</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>File Version Option</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersionOption
   * @generated
   */
  EEnum getFileVersionOption();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.EscapeSequence <em>Escape Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Escape Sequence</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.EscapeSequence
   * @generated
   */
  EEnum getEscapeSequence();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.Orientation <em>Orientation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Orientation</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.Orientation
   * @generated
   */
  EEnum getOrientation();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.enquiry.enquiry.ServerMode <em>Server Mode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Server Mode</em>'.
   * @see com.odcgroup.t24.enquiry.enquiry.ServerMode
   * @generated
   */
  EEnum getServerMode();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  EnquiryFactory getEnquiryFactory();

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
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl <em>Enquiry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiry()
     * @generated
     */
    EClass ENQUIRY = eINSTANCE.getEnquiry();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__NAME = eINSTANCE.getEnquiry_Name();

    /**
     * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__FILE_NAME = eINSTANCE.getEnquiry_FileName();

    /**
     * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__METAMODEL_VERSION = eINSTANCE.getEnquiry_MetamodelVersion();

    /**
     * The meta object literal for the '<em><b>Header</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__HEADER = eINSTANCE.getEnquiry_Header();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__DESCRIPTION = eINSTANCE.getEnquiry_Description();

    /**
     * The meta object literal for the '<em><b>Server Mode</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__SERVER_MODE = eINSTANCE.getEnquiry_ServerMode();

    /**
     * The meta object literal for the '<em><b>Enquiry Mode</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__ENQUIRY_MODE = eINSTANCE.getEnquiry_EnquiryMode();

    /**
     * The meta object literal for the '<em><b>Companies</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__COMPANIES = eINSTANCE.getEnquiry_Companies();

    /**
     * The meta object literal for the '<em><b>Account Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__ACCOUNT_FIELD = eINSTANCE.getEnquiry_AccountField();

    /**
     * The meta object literal for the '<em><b>Customer Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__CUSTOMER_FIELD = eINSTANCE.getEnquiry_CustomerField();

    /**
     * The meta object literal for the '<em><b>Zero Records Display</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__ZERO_RECORDS_DISPLAY = eINSTANCE.getEnquiry_ZeroRecordsDisplay();

    /**
     * The meta object literal for the '<em><b>No Selection</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__NO_SELECTION = eINSTANCE.getEnquiry_NoSelection();

    /**
     * The meta object literal for the '<em><b>Show All Books</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__SHOW_ALL_BOOKS = eINSTANCE.getEnquiry_ShowAllBooks();

    /**
     * The meta object literal for the '<em><b>Start Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__START_LINE = eINSTANCE.getEnquiry_StartLine();

    /**
     * The meta object literal for the '<em><b>End Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__END_LINE = eINSTANCE.getEnquiry_EndLine();

    /**
     * The meta object literal for the '<em><b>Build Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__BUILD_ROUTINES = eINSTANCE.getEnquiry_BuildRoutines();

    /**
     * The meta object literal for the '<em><b>Fixed Selections</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__FIXED_SELECTIONS = eINSTANCE.getEnquiry_FixedSelections();

    /**
     * The meta object literal for the '<em><b>Fixed Sorts</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__FIXED_SORTS = eINSTANCE.getEnquiry_FixedSorts();

    /**
     * The meta object literal for the '<em><b>Custom Selection</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__CUSTOM_SELECTION = eINSTANCE.getEnquiry_CustomSelection();

    /**
     * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__FIELDS = eINSTANCE.getEnquiry_Fields();

    /**
     * The meta object literal for the '<em><b>Toolbar</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__TOOLBAR = eINSTANCE.getEnquiry_Toolbar();

    /**
     * The meta object literal for the '<em><b>Tools</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__TOOLS = eINSTANCE.getEnquiry_Tools();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__TARGET = eINSTANCE.getEnquiry_Target();

    /**
     * The meta object literal for the '<em><b>Drill Downs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__DRILL_DOWNS = eINSTANCE.getEnquiry_DrillDowns();

    /**
     * The meta object literal for the '<em><b>Security</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__SECURITY = eINSTANCE.getEnquiry_Security();

    /**
     * The meta object literal for the '<em><b>Graph</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__GRAPH = eINSTANCE.getEnquiry_Graph();

    /**
     * The meta object literal for the '<em><b>Web Service</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__WEB_SERVICE = eINSTANCE.getEnquiry_WebService();

    /**
     * The meta object literal for the '<em><b>Generate IFP</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__GENERATE_IFP = eINSTANCE.getEnquiry_GenerateIFP();

    /**
     * The meta object literal for the '<em><b>File Version</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY__FILE_VERSION = eINSTANCE.getEnquiry_FileVersion();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__ATTRIBUTES = eINSTANCE.getEnquiry_Attributes();

    /**
     * The meta object literal for the '<em><b>Introspection Messages</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY__INTROSPECTION_MESSAGES = eINSTANCE.getEnquiry_IntrospectionMessages();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl <em>Companies</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CompaniesImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompanies()
     * @generated
     */
    EClass COMPANIES = eINSTANCE.getCompanies();

    /**
     * The meta object literal for the '<em><b>All</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPANIES__ALL = eINSTANCE.getCompanies_All();

    /**
     * The meta object literal for the '<em><b>Code</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPANIES__CODE = eINSTANCE.getCompanies_Code();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl <em>Header</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryHeader()
     * @generated
     */
    EClass ENQUIRY_HEADER = eINSTANCE.getEnquiryHeader();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENQUIRY_HEADER__LABEL = eINSTANCE.getEnquiryHeader_Label();

    /**
     * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY_HEADER__COLUMN = eINSTANCE.getEnquiryHeader_Column();

    /**
     * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENQUIRY_HEADER__LINE = eINSTANCE.getEnquiryHeader_Line();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl <em>Target</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TargetImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTarget()
     * @generated
     */
    EClass TARGET = eINSTANCE.getTarget();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TARGET__APPLICATION = eINSTANCE.getTarget_Application();

    /**
     * The meta object literal for the '<em><b>Screen</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TARGET__SCREEN = eINSTANCE.getTarget_Screen();

    /**
     * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TARGET__MAPPINGS = eINSTANCE.getTarget_Mappings();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl <em>Target Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TargetMappingImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTargetMapping()
     * @generated
     */
    EClass TARGET_MAPPING = eINSTANCE.getTargetMapping();

    /**
     * The meta object literal for the '<em><b>From Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TARGET_MAPPING__FROM_FIELD = eINSTANCE.getTargetMapping_FromField();

    /**
     * The meta object literal for the '<em><b>To Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TARGET_MAPPING__TO_FIELD = eINSTANCE.getTargetMapping_ToField();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl <em>Parameters</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ParametersImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getParameters()
     * @generated
     */
    EClass PARAMETERS = eINSTANCE.getParameters();

    /**
     * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__FUNCTION = eINSTANCE.getParameters_Function();

    /**
     * The meta object literal for the '<em><b>Auto</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__AUTO = eINSTANCE.getParameters_Auto();

    /**
     * The meta object literal for the '<em><b>Run Immediately</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__RUN_IMMEDIATELY = eINSTANCE.getParameters_RunImmediately();

    /**
     * The meta object literal for the '<em><b>Pw Activity</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__PW_ACTIVITY = eINSTANCE.getParameters_PwActivity();

    /**
     * The meta object literal for the '<em><b>Field Name</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__FIELD_NAME = eINSTANCE.getParameters_FieldName();

    /**
     * The meta object literal for the '<em><b>Variable</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETERS__VARIABLE = eINSTANCE.getParameters_Variable();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl <em>Drill Down</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDown()
     * @generated
     */
    EClass DRILL_DOWN = eINSTANCE.getDrillDown();

    /**
     * The meta object literal for the '<em><b>Drill name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN__DRILL_NAME = eINSTANCE.getDrillDown_Drill_name();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DRILL_DOWN__DESCRIPTION = eINSTANCE.getDrillDown_Description();

    /**
     * The meta object literal for the '<em><b>Label Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN__LABEL_FIELD = eINSTANCE.getDrillDown_LabelField();

    /**
     * The meta object literal for the '<em><b>Image</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN__IMAGE = eINSTANCE.getDrillDown_Image();

    /**
     * The meta object literal for the '<em><b>Info</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN__INFO = eINSTANCE.getDrillDown_Info();

    /**
     * The meta object literal for the '<em><b>Criteria</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DRILL_DOWN__CRITERIA = eINSTANCE.getDrillDown_Criteria();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DRILL_DOWN__PARAMETERS = eINSTANCE.getDrillDown_Parameters();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DRILL_DOWN__TYPE = eINSTANCE.getDrillDown_Type();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownTypeImpl <em>Drill Down Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownType()
     * @generated
     */
    EClass DRILL_DOWN_TYPE = eINSTANCE.getDrillDownType();

    /**
     * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN_TYPE__PROPERTY = eINSTANCE.getDrillDownType_Property();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownStringTypeImpl <em>Drill Down String Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownStringTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownStringType()
     * @generated
     */
    EClass DRILL_DOWN_STRING_TYPE = eINSTANCE.getDrillDownStringType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DRILL_DOWN_STRING_TYPE__VALUE = eINSTANCE.getDrillDownStringType_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ApplicationTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getApplicationType()
     * @generated
     */
    EClass APPLICATION_TYPE = eINSTANCE.getApplicationType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ScreenTypeImpl <em>Screen Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ScreenTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getScreenType()
     * @generated
     */
    EClass SCREEN_TYPE = eINSTANCE.getScreenType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryTypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryType()
     * @generated
     */
    EClass ENQUIRY_TYPE = eINSTANCE.getEnquiryType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FromFieldTypeImpl <em>From Field Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FromFieldTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFromFieldType()
     * @generated
     */
    EClass FROM_FIELD_TYPE = eINSTANCE.getFromFieldType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenTypeImpl <em>Composite Screen Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompositeScreenType()
     * @generated
     */
    EClass COMPOSITE_SCREEN_TYPE = eINSTANCE.getCompositeScreenType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TabbedScreenTypeImpl <em>Tabbed Screen Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TabbedScreenTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTabbedScreenType()
     * @generated
     */
    EClass TABBED_SCREEN_TYPE = eINSTANCE.getTabbedScreenType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ViewTypeImpl <em>View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ViewTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getViewType()
     * @generated
     */
    EClass VIEW_TYPE = eINSTANCE.getViewType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.QuitSEETypeImpl <em>Quit SEE Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.QuitSEETypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getQuitSEEType()
     * @generated
     */
    EClass QUIT_SEE_TYPE = eINSTANCE.getQuitSEEType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BlankTypeImpl <em>Blank Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BlankTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBlankType()
     * @generated
     */
    EClass BLANK_TYPE = eINSTANCE.getBlankType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BLANK_TYPE__VALUE = eINSTANCE.getBlankType_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.PWProcessTypeImpl <em>PW Process Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.PWProcessTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getPWProcessType()
     * @generated
     */
    EClass PW_PROCESS_TYPE = eINSTANCE.getPWProcessType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DownloadTypeImpl <em>Download Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DownloadTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDownloadType()
     * @generated
     */
    EClass DOWNLOAD_TYPE = eINSTANCE.getDownloadType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RunTypeImpl <em>Run Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RunTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRunType()
     * @generated
     */
    EClass RUN_TYPE = eINSTANCE.getRunType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.UtilTypeImpl <em>Util Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.UtilTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getUtilType()
     * @generated
     */
    EClass UTIL_TYPE = eINSTANCE.getUtilType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JavaScriptTypeImpl <em>Java Script Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.JavaScriptTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJavaScriptType()
     * @generated
     */
    EClass JAVA_SCRIPT_TYPE = eINSTANCE.getJavaScriptType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ShouldBeChangedTypeImpl <em>Should Be Changed Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ShouldBeChangedTypeImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getShouldBeChangedType()
     * @generated
     */
    EClass SHOULD_BE_CHANGED_TYPE = eINSTANCE.getShouldBeChangedType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownOptionImpl <em>Drill Down Option</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DrillDownOptionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDrillDownOption()
     * @generated
     */
    EClass DRILL_DOWN_OPTION = eINSTANCE.getDrillDownOption();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenOptionImpl <em>Composite Screen Option</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CompositeScreenOptionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompositeScreenOption()
     * @generated
     */
    EClass COMPOSITE_SCREEN_OPTION = eINSTANCE.getCompositeScreenOption();

    /**
     * The meta object literal for the '<em><b>Composite Screen</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPOSITE_SCREEN_OPTION__COMPOSITE_SCREEN = eINSTANCE.getCompositeScreenOption_CompositeScreen();

    /**
     * The meta object literal for the '<em><b>Reference</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPOSITE_SCREEN_OPTION__REFERENCE = eINSTANCE.getCompositeScreenOption_Reference();

    /**
     * The meta object literal for the '<em><b>Field Parameter</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPOSITE_SCREEN_OPTION__FIELD_PARAMETER = eINSTANCE.getCompositeScreenOption_FieldParameter();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TabOptionImpl <em>Tab Option</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TabOptionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTabOption()
     * @generated
     */
    EClass TAB_OPTION = eINSTANCE.getTabOption();

    /**
     * The meta object literal for the '<em><b>Tab Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TAB_OPTION__TAB_NAME = eINSTANCE.getTabOption_TabName();

    /**
     * The meta object literal for the '<em><b>Reference</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TAB_OPTION__REFERENCE = eINSTANCE.getTabOption_Reference();

    /**
     * The meta object literal for the '<em><b>Field Parameter</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TAB_OPTION__FIELD_PARAMETER = eINSTANCE.getTabOption_FieldParameter();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ViewOptionImpl <em>View Option</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ViewOptionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getViewOption()
     * @generated
     */
    EClass VIEW_OPTION = eINSTANCE.getViewOption();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.QuitSEEOptionImpl <em>Quit SEE Option</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.QuitSEEOptionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getQuitSEEOption()
     * @generated
     */
    EClass QUIT_SEE_OPTION = eINSTANCE.getQuitSEEOption();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ReferenceImpl <em>Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ReferenceImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getReference()
     * @generated
     */
    EClass REFERENCE = eINSTANCE.getReference();

    /**
     * The meta object literal for the '<em><b>File</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCE__FILE = eINSTANCE.getReference_File();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCE__FIELD = eINSTANCE.getReference_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ParameterImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER__FIELD = eINSTANCE.getParameter_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl <em>Selection Criteria</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionCriteria()
     * @generated
     */
    EClass SELECTION_CRITERIA = eINSTANCE.getSelectionCriteria();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION_CRITERIA__FIELD = eINSTANCE.getSelectionCriteria_Field();

    /**
     * The meta object literal for the '<em><b>Operand</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION_CRITERIA__OPERAND = eINSTANCE.getSelectionCriteria_Operand();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION_CRITERIA__VALUES = eINSTANCE.getSelectionCriteria_Values();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl <em>Security</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SecurityImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSecurity()
     * @generated
     */
    EClass SECURITY = eINSTANCE.getSecurity();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SECURITY__APPLICATION = eINSTANCE.getSecurity_Application();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SECURITY__FIELD = eINSTANCE.getSecurity_Field();

    /**
     * The meta object literal for the '<em><b>Abort</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SECURITY__ABORT = eINSTANCE.getSecurity_Abort();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl <em>Graph</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGraph()
     * @generated
     */
    EClass GRAPH = eINSTANCE.getGraph();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GRAPH__TYPE = eINSTANCE.getGraph_Type();

    /**
     * The meta object literal for the '<em><b>Labels</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__LABELS = eINSTANCE.getGraph_Labels();

    /**
     * The meta object literal for the '<em><b>Dimension</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__DIMENSION = eINSTANCE.getGraph_Dimension();

    /**
     * The meta object literal for the '<em><b>Margins</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__MARGINS = eINSTANCE.getGraph_Margins();

    /**
     * The meta object literal for the '<em><b>Scale</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__SCALE = eINSTANCE.getGraph_Scale();

    /**
     * The meta object literal for the '<em><b>Legend</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__LEGEND = eINSTANCE.getGraph_Legend();

    /**
     * The meta object literal for the '<em><b>XAxis</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__XAXIS = eINSTANCE.getGraph_XAxis();

    /**
     * The meta object literal for the '<em><b>YAxis</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__YAXIS = eINSTANCE.getGraph_YAxis();

    /**
     * The meta object literal for the '<em><b>ZAxis</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GRAPH__ZAXIS = eINSTANCE.getGraph_ZAxis();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl <em>Axis</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAxis()
     * @generated
     */
    EClass AXIS = eINSTANCE.getAxis();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AXIS__FIELD = eINSTANCE.getAxis_Field();

    /**
     * The meta object literal for the '<em><b>Display Legend</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AXIS__DISPLAY_LEGEND = eINSTANCE.getAxis_DisplayLegend();

    /**
     * The meta object literal for the '<em><b>Show Grid</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AXIS__SHOW_GRID = eINSTANCE.getAxis_ShowGrid();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DimensionImpl <em>Dimension</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DimensionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDimension()
     * @generated
     */
    EClass DIMENSION = eINSTANCE.getDimension();

    /**
     * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIMENSION__WIDTH = eINSTANCE.getDimension_Width();

    /**
     * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIMENSION__HEIGHT = eINSTANCE.getDimension_Height();

    /**
     * The meta object literal for the '<em><b>Orientation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIMENSION__ORIENTATION = eINSTANCE.getDimension_Orientation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LabelImpl <em>Label</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LabelImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLabel()
     * @generated
     */
    EClass LABEL = eINSTANCE.getLabel();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LABEL__DESCRIPTION = eINSTANCE.getLabel_Description();

    /**
     * The meta object literal for the '<em><b>Position</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LABEL__POSITION = eINSTANCE.getLabel_Position();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.PositionImpl <em>Position</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.PositionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getPosition()
     * @generated
     */
    EClass POSITION = eINSTANCE.getPosition();

    /**
     * The meta object literal for the '<em><b>X</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute POSITION__X = eINSTANCE.getPosition_X();

    /**
     * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute POSITION__Y = eINSTANCE.getPosition_Y();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LegendImpl <em>Legend</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LegendImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLegend()
     * @generated
     */
    EClass LEGEND = eINSTANCE.getLegend();

    /**
     * The meta object literal for the '<em><b>X</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LEGEND__X = eINSTANCE.getLegend_X();

    /**
     * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LEGEND__Y = eINSTANCE.getLegend_Y();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.MarginsImpl <em>Margins</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.MarginsImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getMargins()
     * @generated
     */
    EClass MARGINS = eINSTANCE.getMargins();

    /**
     * The meta object literal for the '<em><b>Top</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MARGINS__TOP = eINSTANCE.getMargins_Top();

    /**
     * The meta object literal for the '<em><b>Bottom</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MARGINS__BOTTOM = eINSTANCE.getMargins_Bottom();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MARGINS__LEFT = eINSTANCE.getMargins_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MARGINS__RIGHT = eINSTANCE.getMargins_Right();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ScaleImpl <em>Scale</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ScaleImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getScale()
     * @generated
     */
    EClass SCALE = eINSTANCE.getScale();

    /**
     * The meta object literal for the '<em><b>X</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SCALE__X = eINSTANCE.getScale_X();

    /**
     * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SCALE__Y = eINSTANCE.getScale_Y();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RoutineImpl <em>Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RoutineImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRoutine()
     * @generated
     */
    EClass ROUTINE = eINSTANCE.getRoutine();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROUTINE__NAME = eINSTANCE.getRoutine_Name();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JBCRoutineImpl <em>JBC Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.JBCRoutineImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJBCRoutine()
     * @generated
     */
    EClass JBC_ROUTINE = eINSTANCE.getJBCRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JavaRoutineImpl <em>Java Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.JavaRoutineImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJavaRoutine()
     * @generated
     */
    EClass JAVA_ROUTINE = eINSTANCE.getJavaRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl <em>Fixed Selection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFixedSelection()
     * @generated
     */
    EClass FIXED_SELECTION = eINSTANCE.getFixedSelection();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIXED_SELECTION__FIELD = eINSTANCE.getFixedSelection_Field();

    /**
     * The meta object literal for the '<em><b>Operand</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIXED_SELECTION__OPERAND = eINSTANCE.getFixedSelection_Operand();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIXED_SELECTION__VALUES = eINSTANCE.getFixedSelection_Values();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSortImpl <em>Fixed Sort</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FixedSortImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFixedSort()
     * @generated
     */
    EClass FIXED_SORT = eINSTANCE.getFixedSort();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIXED_SORT__FIELD = eINSTANCE.getFixedSort_Field();

    /**
     * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIXED_SORT__ORDER = eINSTANCE.getFixedSort_Order();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionExpressionImpl <em>Selection Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionExpressionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionExpression()
     * @generated
     */
    EClass SELECTION_EXPRESSION = eINSTANCE.getSelectionExpression();

    /**
     * The meta object literal for the '<em><b>Selection</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SELECTION_EXPRESSION__SELECTION = eINSTANCE.getSelectionExpression_Selection();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl <em>Selection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelection()
     * @generated
     */
    EClass SELECTION = eINSTANCE.getSelection();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION__FIELD = eINSTANCE.getSelection_Field();

    /**
     * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION__MANDATORY = eINSTANCE.getSelection_Mandatory();

    /**
     * The meta object literal for the '<em><b>Popup Drop Down</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION__POPUP_DROP_DOWN = eINSTANCE.getSelection_PopupDropDown();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SELECTION__LABEL = eINSTANCE.getSelection_Label();

    /**
     * The meta object literal for the '<em><b>Operands</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION__OPERANDS = eINSTANCE.getSelection_Operands();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION__OPERATOR = eINSTANCE.getSelection_Operator();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FileVersionImpl <em>File Version</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FileVersionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFileVersion()
     * @generated
     */
    EClass FILE_VERSION = eINSTANCE.getFileVersion();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_VERSION__VALUES = eINSTANCE.getFileVersion_Values();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.OperationImpl <em>Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.OperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getOperation()
     * @generated
     */
    EClass OPERATION = eINSTANCE.getOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakOperationImpl <em>Break Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakOperation()
     * @generated
     */
    EClass BREAK_OPERATION = eINSTANCE.getBreakOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakOnChangeOperationImpl <em>Break On Change Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakOnChangeOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakOnChangeOperation()
     * @generated
     */
    EClass BREAK_ON_CHANGE_OPERATION = eINSTANCE.getBreakOnChangeOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BREAK_ON_CHANGE_OPERATION__FIELD = eINSTANCE.getBreakOnChangeOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakLineOperationImpl <em>Break Line Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakLineOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakLineOperation()
     * @generated
     */
    EClass BREAK_LINE_OPERATION = eINSTANCE.getBreakLineOperation();

    /**
     * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BREAK_LINE_OPERATION__LINE = eINSTANCE.getBreakLineOperation_Line();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CalcOperationImpl <em>Calc Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CalcOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCalcOperation()
     * @generated
     */
    EClass CALC_OPERATION = eINSTANCE.getCalcOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CALC_OPERATION__FIELD = eINSTANCE.getCalcOperation_Field();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CALC_OPERATION__OPERATOR = eINSTANCE.getCalcOperation_Operator();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConstantOperationImpl <em>Constant Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ConstantOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConstantOperation()
     * @generated
     */
    EClass CONSTANT_OPERATION = eINSTANCE.getConstantOperation();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT_OPERATION__VALUE = eINSTANCE.getConstantOperation_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LabelOperationImpl <em>Label Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LabelOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLabelOperation()
     * @generated
     */
    EClass LABEL_OPERATION = eINSTANCE.getLabelOperation();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LABEL_OPERATION__LABEL = eINSTANCE.getLabelOperation_Label();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DateOperationImpl <em>Date Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DateOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDateOperation()
     * @generated
     */
    EClass DATE_OPERATION = eINSTANCE.getDateOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl <em>Decision Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DecisionOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecisionOperation()
     * @generated
     */
    EClass DECISION_OPERATION = eINSTANCE.getDecisionOperation();

    /**
     * The meta object literal for the '<em><b>Left Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECISION_OPERATION__LEFT_FIELD = eINSTANCE.getDecisionOperation_LeftField();

    /**
     * The meta object literal for the '<em><b>Operand</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECISION_OPERATION__OPERAND = eINSTANCE.getDecisionOperation_Operand();

    /**
     * The meta object literal for the '<em><b>Right Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECISION_OPERATION__RIGHT_FIELD = eINSTANCE.getDecisionOperation_RightField();

    /**
     * The meta object literal for the '<em><b>First Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECISION_OPERATION__FIRST_FIELD = eINSTANCE.getDecisionOperation_FirstField();

    /**
     * The meta object literal for the '<em><b>Second Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECISION_OPERATION__SECOND_FIELD = eINSTANCE.getDecisionOperation_SecondField();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DescriptorOperationImpl <em>Descriptor Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DescriptorOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDescriptorOperation()
     * @generated
     */
    EClass DESCRIPTOR_OPERATION = eINSTANCE.getDescriptorOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DESCRIPTOR_OPERATION__FIELD = eINSTANCE.getDescriptorOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TodayOperationImpl <em>Today Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TodayOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTodayOperation()
     * @generated
     */
    EClass TODAY_OPERATION = eINSTANCE.getTodayOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LWDOperationImpl <em>LWD Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LWDOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLWDOperation()
     * @generated
     */
    EClass LWD_OPERATION = eINSTANCE.getLWDOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.NWDOperationImpl <em>NWD Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.NWDOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getNWDOperation()
     * @generated
     */
    EClass NWD_OPERATION = eINSTANCE.getNWDOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldOperationImpl <em>Field Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldOperation()
     * @generated
     */
    EClass FIELD_OPERATION = eINSTANCE.getFieldOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ApplicationFieldNameOperationImpl <em>Application Field Name Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ApplicationFieldNameOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getApplicationFieldNameOperation()
     * @generated
     */
    EClass APPLICATION_FIELD_NAME_OPERATION = eINSTANCE.getApplicationFieldNameOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute APPLICATION_FIELD_NAME_OPERATION__FIELD = eINSTANCE.getApplicationFieldNameOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldNumberOperationImpl <em>Field Number Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldNumberOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldNumberOperation()
     * @generated
     */
    EClass FIELD_NUMBER_OPERATION = eINSTANCE.getFieldNumberOperation();

    /**
     * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_NUMBER_OPERATION__NUMBER = eINSTANCE.getFieldNumberOperation_Number();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldExtractOperationImpl <em>Field Extract Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldExtractOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldExtractOperation()
     * @generated
     */
    EClass FIELD_EXTRACT_OPERATION = eINSTANCE.getFieldExtractOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_EXTRACT_OPERATION__FIELD = eINSTANCE.getFieldExtractOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionOperationImpl <em>Selection Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SelectionOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionOperation()
     * @generated
     */
    EClass SELECTION_OPERATION = eINSTANCE.getSelectionOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECTION_OPERATION__FIELD = eINSTANCE.getSelectionOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.SystemOperationImpl <em>System Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.SystemOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSystemOperation()
     * @generated
     */
    EClass SYSTEM_OPERATION = eINSTANCE.getSystemOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.UserOperationImpl <em>User Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.UserOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getUserOperation()
     * @generated
     */
    EClass USER_OPERATION = eINSTANCE.getUserOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CompanyOperationImpl <em>Company Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CompanyOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCompanyOperation()
     * @generated
     */
    EClass COMPANY_OPERATION = eINSTANCE.getCompanyOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LanguageOperationImpl <em>Language Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LanguageOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLanguageOperation()
     * @generated
     */
    EClass LANGUAGE_OPERATION = eINSTANCE.getLanguageOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.LocalCurrencyOperationImpl <em>Local Currency Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.LocalCurrencyOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getLocalCurrencyOperation()
     * @generated
     */
    EClass LOCAL_CURRENCY_OPERATION = eINSTANCE.getLocalCurrencyOperation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.TotalOperationImpl <em>Total Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.TotalOperationImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTotalOperation()
     * @generated
     */
    EClass TOTAL_OPERATION = eINSTANCE.getTotalOperation();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TOTAL_OPERATION__FIELD = eINSTANCE.getTotalOperation_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConversionImpl <em>Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConversion()
     * @generated
     */
    EClass CONVERSION = eINSTANCE.getConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl <em>Extract Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getExtractConversion()
     * @generated
     */
    EClass EXTRACT_CONVERSION = eINSTANCE.getExtractConversion();

    /**
     * The meta object literal for the '<em><b>From</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRACT_CONVERSION__FROM = eINSTANCE.getExtractConversion_From();

    /**
     * The meta object literal for the '<em><b>To</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRACT_CONVERSION__TO = eINSTANCE.getExtractConversion_To();

    /**
     * The meta object literal for the '<em><b>Delimiter</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRACT_CONVERSION__DELIMITER = eINSTANCE.getExtractConversion_Delimiter();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.DecryptConversionImpl <em>Decrypt Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.DecryptConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecryptConversion()
     * @generated
     */
    EClass DECRYPT_CONVERSION = eINSTANCE.getDecryptConversion();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DECRYPT_CONVERSION__FIELD = eINSTANCE.getDecryptConversion_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl <em>Replace Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getReplaceConversion()
     * @generated
     */
    EClass REPLACE_CONVERSION = eINSTANCE.getReplaceConversion();

    /**
     * The meta object literal for the '<em><b>Old Data</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REPLACE_CONVERSION__OLD_DATA = eINSTANCE.getReplaceConversion_OldData();

    /**
     * The meta object literal for the '<em><b>New Data</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REPLACE_CONVERSION__NEW_DATA = eINSTANCE.getReplaceConversion_NewData();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ConvertConversionImpl <em>Convert Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ConvertConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getConvertConversion()
     * @generated
     */
    EClass CONVERT_CONVERSION = eINSTANCE.getConvertConversion();

    /**
     * The meta object literal for the '<em><b>Old Data</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONVERT_CONVERSION__OLD_DATA = eINSTANCE.getConvertConversion_OldData();

    /**
     * The meta object literal for the '<em><b>New Data</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONVERT_CONVERSION__NEW_DATA = eINSTANCE.getConvertConversion_NewData();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl <em>Value Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getValueConversion()
     * @generated
     */
    EClass VALUE_CONVERSION = eINSTANCE.getValueConversion();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALUE_CONVERSION__VALUE = eINSTANCE.getValueConversion_Value();

    /**
     * The meta object literal for the '<em><b>Sub Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALUE_CONVERSION__SUB_VALUE = eINSTANCE.getValueConversion_SubValue();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.JulianConversionImpl <em>Julian Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.JulianConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getJulianConversion()
     * @generated
     */
    EClass JULIAN_CONVERSION = eINSTANCE.getJulianConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicConversionImpl <em>Basic Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicConversion()
     * @generated
     */
    EClass BASIC_CONVERSION = eINSTANCE.getBasicConversion();

    /**
     * The meta object literal for the '<em><b>Instruction</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BASIC_CONVERSION__INSTRUCTION = eINSTANCE.getBasicConversion_Instruction();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicOConversionImpl <em>Basic OConversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicOConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicOConversion()
     * @generated
     */
    EClass BASIC_OCONVERSION = eINSTANCE.getBasicOConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BasicIConversionImpl <em>Basic IConversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BasicIConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBasicIConversion()
     * @generated
     */
    EClass BASIC_ICONVERSION = eINSTANCE.getBasicIConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl <em>Get From Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFromConversion()
     * @generated
     */
    EClass GET_FROM_CONVERSION = eINSTANCE.getGetFromConversion();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GET_FROM_CONVERSION__APPLICATION = eINSTANCE.getGetFromConversion_Application();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GET_FROM_CONVERSION__FIELD = eINSTANCE.getGetFromConversion_Field();

    /**
     * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GET_FROM_CONVERSION__LANGUAGE = eINSTANCE.getGetFromConversion_Language();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RateConversionImpl <em>Rate Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RateConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRateConversion()
     * @generated
     */
    EClass RATE_CONVERSION = eINSTANCE.getRateConversion();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RATE_CONVERSION__FIELD = eINSTANCE.getRateConversion_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CalcFixedRateConversionImpl <em>Calc Fixed Rate Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CalcFixedRateConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCalcFixedRateConversion()
     * @generated
     */
    EClass CALC_FIXED_RATE_CONVERSION = eINSTANCE.getCalcFixedRateConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFixedRateConversionImpl <em>Get Fixed Rate Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFixedRateConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFixedRateConversion()
     * @generated
     */
    EClass GET_FIXED_RATE_CONVERSION = eINSTANCE.getGetFixedRateConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.GetFixedCurrencyConversionImpl <em>Get Fixed Currency Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.GetFixedCurrencyConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getGetFixedCurrencyConversion()
     * @generated
     */
    EClass GET_FIXED_CURRENCY_CONVERSION = eINSTANCE.getGetFixedCurrencyConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.AbsConversionImpl <em>Abs Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.AbsConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAbsConversion()
     * @generated
     */
    EClass ABS_CONVERSION = eINSTANCE.getAbsConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.MatchFieldImpl <em>Match Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.MatchFieldImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getMatchField()
     * @generated
     */
    EClass MATCH_FIELD = eINSTANCE.getMatchField();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MATCH_FIELD__PATTERN = eINSTANCE.getMatchField_Pattern();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MATCH_FIELD__VALUE = eINSTANCE.getMatchField_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.CallRoutineImpl <em>Call Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.CallRoutineImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCallRoutine()
     * @generated
     */
    EClass CALL_ROUTINE = eINSTANCE.getCallRoutine();

    /**
     * The meta object literal for the '<em><b>Routine</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CALL_ROUTINE__ROUTINE = eINSTANCE.getCallRoutine_Routine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatConversionImpl <em>Repeat Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatConversion()
     * @generated
     */
    EClass REPEAT_CONVERSION = eINSTANCE.getRepeatConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatOnNullConversionImpl <em>Repeat On Null Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatOnNullConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatOnNullConversion()
     * @generated
     */
    EClass REPEAT_ON_NULL_CONVERSION = eINSTANCE.getRepeatOnNullConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatEveryConversionImpl <em>Repeat Every Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatEveryConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatEveryConversion()
     * @generated
     */
    EClass REPEAT_EVERY_CONVERSION = eINSTANCE.getRepeatEveryConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.RepeatSubConversionImpl <em>Repeat Sub Conversion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.RepeatSubConversionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getRepeatSubConversion()
     * @generated
     */
    EClass REPEAT_SUB_CONVERSION = eINSTANCE.getRepeatSubConversion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl <em>Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getField()
     * @generated
     */
    EClass FIELD = eINSTANCE.getField();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__NAME = eINSTANCE.getField_Name();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__LABEL = eINSTANCE.getField_Label();

    /**
     * The meta object literal for the '<em><b>Comments</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__COMMENTS = eINSTANCE.getField_Comments();

    /**
     * The meta object literal for the '<em><b>Display Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__DISPLAY_TYPE = eINSTANCE.getField_DisplayType();

    /**
     * The meta object literal for the '<em><b>Format</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__FORMAT = eINSTANCE.getField_Format();

    /**
     * The meta object literal for the '<em><b>Break Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__BREAK_CONDITION = eINSTANCE.getField_BreakCondition();

    /**
     * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__LENGTH = eINSTANCE.getField_Length();

    /**
     * The meta object literal for the '<em><b>Alignment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ALIGNMENT = eINSTANCE.getField_Alignment();

    /**
     * The meta object literal for the '<em><b>Comma Separator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__COMMA_SEPARATOR = eINSTANCE.getField_CommaSeparator();

    /**
     * The meta object literal for the '<em><b>Number Of Decimals</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__NUMBER_OF_DECIMALS = eINSTANCE.getField_NumberOfDecimals();

    /**
     * The meta object literal for the '<em><b>Escape Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ESCAPE_SEQUENCE = eINSTANCE.getField_EscapeSequence();

    /**
     * The meta object literal for the '<em><b>Fmt Mask</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__FMT_MASK = eINSTANCE.getField_FmtMask();

    /**
     * The meta object literal for the '<em><b>Display Section</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__DISPLAY_SECTION = eINSTANCE.getField_DisplaySection();

    /**
     * The meta object literal for the '<em><b>Position</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__POSITION = eINSTANCE.getField_Position();

    /**
     * The meta object literal for the '<em><b>Column Width</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__COLUMN_WIDTH = eINSTANCE.getField_ColumnWidth();

    /**
     * The meta object literal for the '<em><b>Spool Break</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__SPOOL_BREAK = eINSTANCE.getField_SpoolBreak();

    /**
     * The meta object literal for the '<em><b>Single Multi</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__SINGLE_MULTI = eINSTANCE.getField_SingleMulti();

    /**
     * The meta object literal for the '<em><b>Hidden</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__HIDDEN = eINSTANCE.getField_Hidden();

    /**
     * The meta object literal for the '<em><b>No Header</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__NO_HEADER = eINSTANCE.getField_NoHeader();

    /**
     * The meta object literal for the '<em><b>No Column Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__NO_COLUMN_LABEL = eINSTANCE.getField_NoColumnLabel();

    /**
     * The meta object literal for the '<em><b>Operation</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__OPERATION = eINSTANCE.getField_Operation();

    /**
     * The meta object literal for the '<em><b>Conversion</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__CONVERSION = eINSTANCE.getField_Conversion();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ATTRIBUTES = eINSTANCE.getField_Attributes();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.BreakConditionImpl <em>Break Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.BreakConditionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakCondition()
     * @generated
     */
    EClass BREAK_CONDITION = eINSTANCE.getBreakCondition();

    /**
     * The meta object literal for the '<em><b>Break</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BREAK_CONDITION__BREAK = eINSTANCE.getBreakCondition_Break();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BREAK_CONDITION__FIELD = eINSTANCE.getBreakCondition_Field();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl <em>Field Position</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldPosition()
     * @generated
     */
    EClass FIELD_POSITION = eINSTANCE.getFieldPosition();

    /**
     * The meta object literal for the '<em><b>Page Throw</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_POSITION__PAGE_THROW = eINSTANCE.getFieldPosition_PageThrow();

    /**
     * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_POSITION__COLUMN = eINSTANCE.getFieldPosition_Column();

    /**
     * The meta object literal for the '<em><b>Relative</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_POSITION__RELATIVE = eINSTANCE.getFieldPosition_Relative();

    /**
     * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_POSITION__LINE = eINSTANCE.getFieldPosition_Line();

    /**
     * The meta object literal for the '<em><b>Multi Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_POSITION__MULTI_LINE = eINSTANCE.getFieldPosition_MultiLine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.FormatImpl <em>Format</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.FormatImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFormat()
     * @generated
     */
    EClass FORMAT = eINSTANCE.getFormat();

    /**
     * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAT__FORMAT = eINSTANCE.getFormat_Format();

    /**
     * The meta object literal for the '<em><b>Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAT__FIELD = eINSTANCE.getFormat_Field();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAT__PATTERN = eINSTANCE.getFormat_Pattern();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.ToolImpl <em>Tool</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.ToolImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getTool()
     * @generated
     */
    EClass TOOL = eINSTANCE.getTool();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TOOL__NAME = eINSTANCE.getTool_Name();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TOOL__LABEL = eINSTANCE.getTool_Label();

    /**
     * The meta object literal for the '<em><b>Command</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TOOL__COMMAND = eINSTANCE.getTool_Command();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl <em>Web Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.impl.WebServiceImpl
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getWebService()
     * @generated
     */
    EClass WEB_SERVICE = eINSTANCE.getWebService();

    /**
     * The meta object literal for the '<em><b>Publish Web Service</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WEB_SERVICE__PUBLISH_WEB_SERVICE = eINSTANCE.getWebService_PublishWebService();

    /**
     * The meta object literal for the '<em><b>Web Service Names</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WEB_SERVICE__WEB_SERVICE_NAMES = eINSTANCE.getWebService_WebServiceNames();

    /**
     * The meta object literal for the '<em><b>Web Service Activity</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WEB_SERVICE__WEB_SERVICE_ACTIVITY = eINSTANCE.getWebService_WebServiceActivity();

    /**
     * The meta object literal for the '<em><b>Web Service Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WEB_SERVICE__WEB_SERVICE_DESCRIPTION = eINSTANCE.getWebService_WebServiceDescription();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryMode <em>Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.EnquiryMode
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEnquiryMode()
     * @generated
     */
    EEnum ENQUIRY_MODE = eINSTANCE.getEnquiryMode();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.AlignmentKind <em>Alignment Kind</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.AlignmentKind
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAlignmentKind()
     * @generated
     */
    EEnum ALIGNMENT_KIND = eINSTANCE.getAlignmentKind();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.BreakKind <em>Break Kind</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.BreakKind
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getBreakKind()
     * @generated
     */
    EEnum BREAK_KIND = eINSTANCE.getBreakKind();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.CurrencyPattern <em>Currency Pattern</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.CurrencyPattern
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCurrencyPattern()
     * @generated
     */
    EEnum CURRENCY_PATTERN = eINSTANCE.getCurrencyPattern();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperand <em>Decision Operand</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperand
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDecisionOperand()
     * @generated
     */
    EEnum DECISION_OPERAND = eINSTANCE.getDecisionOperand();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind <em>Display Section Kind</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getDisplaySectionKind()
     * @generated
     */
    EEnum DISPLAY_SECTION_KIND = eINSTANCE.getDisplaySectionKind();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.FieldFormat <em>Field Format</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.FieldFormat
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFieldFormat()
     * @generated
     */
    EEnum FIELD_FORMAT = eINSTANCE.getFieldFormat();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.FunctionKind <em>Function Kind</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.FunctionKind
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFunctionKind()
     * @generated
     */
    EEnum FUNCTION_KIND = eINSTANCE.getFunctionKind();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperator <em>Selection Operator</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperator
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSelectionOperator()
     * @generated
     */
    EEnum SELECTION_OPERATOR = eINSTANCE.getSelectionOperator();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.CriteriaOperator <em>Criteria Operator</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.CriteriaOperator
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getCriteriaOperator()
     * @generated
     */
    EEnum CRITERIA_OPERATOR = eINSTANCE.getCriteriaOperator();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.ProcessingMode <em>Processing Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.ProcessingMode
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getProcessingMode()
     * @generated
     */
    EEnum PROCESSING_MODE = eINSTANCE.getProcessingMode();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.SortOrder <em>Sort Order</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.SortOrder
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getSortOrder()
     * @generated
     */
    EEnum SORT_ORDER = eINSTANCE.getSortOrder();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.AndOr <em>And Or</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.AndOr
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getAndOr()
     * @generated
     */
    EEnum AND_OR = eINSTANCE.getAndOr();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.FileVersionOption <em>File Version Option</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.FileVersionOption
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getFileVersionOption()
     * @generated
     */
    EEnum FILE_VERSION_OPTION = eINSTANCE.getFileVersionOption();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.EscapeSequence <em>Escape Sequence</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.EscapeSequence
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getEscapeSequence()
     * @generated
     */
    EEnum ESCAPE_SEQUENCE = eINSTANCE.getEscapeSequence();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.Orientation <em>Orientation</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.Orientation
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getOrientation()
     * @generated
     */
    EEnum ORIENTATION = eINSTANCE.getOrientation();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.enquiry.enquiry.ServerMode <em>Server Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.enquiry.enquiry.ServerMode
     * @see com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl#getServerMode()
     * @generated
     */
    EEnum SERVER_MODE = eINSTANCE.getServerMode();

  }

} //EnquiryPackage
