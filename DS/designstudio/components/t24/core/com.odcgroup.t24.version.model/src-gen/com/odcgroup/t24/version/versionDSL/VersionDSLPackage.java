/**
 */
package com.odcgroup.t24.version.versionDSL;

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
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLFactory
 * @model kind="package"
 * @generated
 */
public interface VersionDSLPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "versionDSL";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/t24/version/VersionDSL";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "versionDSL";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VersionDSLPackage eINSTANCE = com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl.init();

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl <em>Version</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getVersion()
   * @generated
   */
  int VERSION = 0;

  /**
   * The feature id for the '<em><b>For Application</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FOR_APPLICATION = 0;

  /**
   * The feature id for the '<em><b>Short Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__SHORT_NAME = 1;

  /**
   * The feature id for the '<em><b>T24 Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__T24_NAME = 2;

  /**
   * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__METAMODEL_VERSION = 3;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__GROUP = 4;

  /**
   * The feature id for the '<em><b>Number Of Authorisers</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__NUMBER_OF_AUTHORISERS = 5;

  /**
   * The feature id for the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__DESCRIPTION = 6;

  /**
   * The feature id for the '<em><b>Exception Processing</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__EXCEPTION_PROCESSING = 7;

  /**
   * The feature id for the '<em><b>Interface Exception</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__INTERFACE_EXCEPTION = 8;

  /**
   * The feature id for the '<em><b>Business Day Control</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__BUSINESS_DAY_CONTROL = 9;

  /**
   * The feature id for the '<em><b>Key Sequence</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__KEY_SEQUENCE = 10;

  /**
   * The feature id for the '<em><b>Other Company Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__OTHER_COMPANY_ACCESS = 11;

  /**
   * The feature id for the '<em><b>Auto Company Change</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__AUTO_COMPANY_CHANGE = 12;

  /**
   * The feature id for the '<em><b>Override Approval</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__OVERRIDE_APPROVAL = 13;

  /**
   * The feature id for the '<em><b>Deal Slip Formats</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__DEAL_SLIP_FORMATS = 14;

  /**
   * The feature id for the '<em><b>Deal Slip Trigger</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__DEAL_SLIP_TRIGGER = 15;

  /**
   * The feature id for the '<em><b>Deal Slip Style Sheet</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__DEAL_SLIP_STYLE_SHEET = 16;

  /**
   * The feature id for the '<em><b>Records Per Page</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__RECORDS_PER_PAGE = 17;

  /**
   * The feature id for the '<em><b>Fields Per Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FIELDS_PER_LINE = 18;

  /**
   * The feature id for the '<em><b>Initial Cursor Position</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__INITIAL_CURSOR_POSITION = 19;

  /**
   * The feature id for the '<em><b>Browser Toolbar</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__BROWSER_TOOLBAR = 20;

  /**
   * The feature id for the '<em><b>Language Locale</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__LANGUAGE_LOCALE = 21;

  /**
   * The feature id for the '<em><b>Header1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__HEADER1 = 22;

  /**
   * The feature id for the '<em><b>Header2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__HEADER2 = 23;

  /**
   * The feature id for the '<em><b>Header</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__HEADER = 24;

  /**
   * The feature id for the '<em><b>Footer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FOOTER = 25;

  /**
   * The feature id for the '<em><b>Next Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__NEXT_VERSION = 26;

  /**
   * The feature id for the '<em><b>Next Version Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__NEXT_VERSION_FUNCTION = 27;

  /**
   * The feature id for the '<em><b>Next Transaction Reference</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__NEXT_TRANSACTION_REFERENCE = 28;

  /**
   * The feature id for the '<em><b>Associated Versions</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__ASSOCIATED_VERSIONS = 29;

  /**
   * The feature id for the '<em><b>Include Version Control</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__INCLUDE_VERSION_CONTROL = 30;

  /**
   * The feature id for the '<em><b>Authorization Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__AUTHORIZATION_ROUTINES = 31;

  /**
   * The feature id for the '<em><b>Authorization Routines After Commit</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT = 32;

  /**
   * The feature id for the '<em><b>Input Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__INPUT_ROUTINES = 33;

  /**
   * The feature id for the '<em><b>Input Routines After Commit</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__INPUT_ROUTINES_AFTER_COMMIT = 34;

  /**
   * The feature id for the '<em><b>Key Validation Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__KEY_VALIDATION_ROUTINES = 35;

  /**
   * The feature id for the '<em><b>Pre Process Validation Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__PRE_PROCESS_VALIDATION_ROUTINES = 36;

  /**
   * The feature id for the '<em><b>Web Validation Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__WEB_VALIDATION_ROUTINES = 37;

  /**
   * The feature id for the '<em><b>Confirm Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__CONFIRM_VERSION = 38;

  /**
   * The feature id for the '<em><b>Preview Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__PREVIEW_VERSION = 39;

  /**
   * The feature id for the '<em><b>Challenge Response</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__CHALLENGE_RESPONSE = 40;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__ATTRIBUTES = 41;

  /**
   * The feature id for the '<em><b>Publish Web Service</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__PUBLISH_WEB_SERVICE = 42;

  /**
   * The feature id for the '<em><b>Web Service Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__WEB_SERVICE_ACTIVITY = 43;

  /**
   * The feature id for the '<em><b>Web Service Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__WEB_SERVICE_FUNCTION = 44;

  /**
   * The feature id for the '<em><b>Web Service Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__WEB_SERVICE_DESCRIPTION = 45;

  /**
   * The feature id for the '<em><b>Web Service Names</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__WEB_SERVICE_NAMES = 46;

  /**
   * The feature id for the '<em><b>Generate IFP</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__GENERATE_IFP = 47;

  /**
   * The feature id for the '<em><b>Associated Versions Presentation Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN = 48;

  /**
   * The feature id for the '<em><b>Fields Layout Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FIELDS_LAYOUT_PATTERN = 49;

  /**
   * The feature id for the '<em><b>Fields</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FIELDS = 50;

  /**
   * The number of structural features of the '<em>Version</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_FEATURE_COUNT = 51;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl <em>Field</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.FieldImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getField()
   * @generated
   */
  int FIELD = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__NAME = 0;

  /**
   * The feature id for the '<em><b>Display Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__DISPLAY_TYPE = 1;

  /**
   * The feature id for the '<em><b>Input Behaviour</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__INPUT_BEHAVIOUR = 2;

  /**
   * The feature id for the '<em><b>Case Convention</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__CASE_CONVENTION = 3;

  /**
   * The feature id for the '<em><b>Max Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__MAX_LENGTH = 4;

  /**
   * The feature id for the '<em><b>Enrichment Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ENRICHMENT_LENGTH = 5;

  /**
   * The feature id for the '<em><b>Expansion</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__EXPANSION = 6;

  /**
   * The feature id for the '<em><b>Right Adjust</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__RIGHT_ADJUST = 7;

  /**
   * The feature id for the '<em><b>Enrichment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ENRICHMENT = 8;

  /**
   * The feature id for the '<em><b>Column</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__COLUMN = 9;

  /**
   * The feature id for the '<em><b>Row</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ROW = 10;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__MANDATORY = 11;

  /**
   * The feature id for the '<em><b>Rekey Required</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__REKEY_REQUIRED = 12;

  /**
   * The feature id for the '<em><b>Hyperlink</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__HYPERLINK = 13;

  /**
   * The feature id for the '<em><b>Hot Validate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__HOT_VALIDATE = 14;

  /**
   * The feature id for the '<em><b>Hot Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__HOT_FIELD = 15;

  /**
   * The feature id for the '<em><b>Web Validate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__WEB_VALIDATE = 16;

  /**
   * The feature id for the '<em><b>Selection Enquiry</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__SELECTION_ENQUIRY = 17;

  /**
   * The feature id for the '<em><b>Enquiry Parameter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ENQUIRY_PARAMETER = 18;

  /**
   * The feature id for the '<em><b>Popup Behaviour</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__POPUP_BEHAVIOUR = 19;

  /**
   * The feature id for the '<em><b>Defaults</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__DEFAULTS = 20;

  /**
   * The feature id for the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__LABEL = 21;

  /**
   * The feature id for the '<em><b>Tool Tip</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__TOOL_TIP = 22;

  /**
   * The feature id for the '<em><b>Validation Routines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__VALIDATION_ROUTINES = 23;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__ATTRIBUTES = 24;

  /**
   * The feature id for the '<em><b>MV</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__MV = 25;

  /**
   * The feature id for the '<em><b>SV</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD__SV = 26;

  /**
   * The number of structural features of the '<em>Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_FEATURE_COUNT = 27;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl <em>Default</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.DefaultImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDefault()
   * @generated
   */
  int DEFAULT = 2;

  /**
   * The feature id for the '<em><b>Mv</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT__MV = 0;

  /**
   * The feature id for the '<em><b>Sv</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT__SV = 1;

  /**
   * The feature id for the '<em><b>Default If Old Value Equals</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS = 2;

  /**
   * The feature id for the '<em><b>Default New Value Or At Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE = 3;

  /**
   * The number of structural features of the '<em>Default</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEFAULT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.AtRoutineImpl <em>At Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.AtRoutineImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getAtRoutine()
   * @generated
   */
  int AT_ROUTINE = 4;

  /**
   * The number of structural features of the '<em>At Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AT_ROUTINE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.RoutineImpl <em>Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.RoutineImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getRoutine()
   * @generated
   */
  int ROUTINE = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROUTINE__NAME = AT_ROUTINE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROUTINE_FEATURE_COUNT = AT_ROUTINE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl <em>Value Or At Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getValueOrAtRoutine()
   * @generated
   */
  int VALUE_OR_AT_ROUTINE = 5;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_OR_AT_ROUTINE__VALUE = 0;

  /**
   * The feature id for the '<em><b>At Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_OR_AT_ROUTINE__AT_ROUTINE = 1;

  /**
   * The number of structural features of the '<em>Value Or At Routine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_OR_AT_ROUTINE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.JBCRoutineImpl <em>JBC Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.JBCRoutineImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getJBCRoutine()
   * @generated
   */
  int JBC_ROUTINE = 6;

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
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.JavaRoutineImpl <em>Java Routine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.JavaRoutineImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getJavaRoutine()
   * @generated
   */
  int JAVA_ROUTINE = 7;

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
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl <em>Deal Slip</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlip()
   * @generated
   */
  int DEAL_SLIP = 8;

  /**
   * The feature id for the '<em><b>Format</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEAL_SLIP__FORMAT = 0;

  /**
   * The feature id for the '<em><b>Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEAL_SLIP__FUNCTION = 1;

  /**
   * The number of structural features of the '<em>Deal Slip</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DEAL_SLIP_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.YesNo <em>Yes No</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getYesNo()
   * @generated
   */
  int YES_NO = 9;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.PopupBehaviour <em>Popup Behaviour</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.PopupBehaviour
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getPopupBehaviour()
   * @generated
   */
  int POPUP_BEHAVIOUR = 10;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.CaseConvention <em>Case Convention</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.CaseConvention
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getCaseConvention()
   * @generated
   */
  int CASE_CONVENTION = 11;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.DisplayType <em>Display Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.DisplayType
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDisplayType()
   * @generated
   */
  int DISPLAY_TYPE = 12;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction <em>Deal Slip Format Function</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlipFormatFunction()
   * @generated
   */
  int DEAL_SLIP_FORMAT_FUNCTION = 13;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.DealSlipTrigger <em>Deal Slip Trigger</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.DealSlipTrigger
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlipTrigger()
   * @generated
   */
  int DEAL_SLIP_TRIGGER = 14;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.BusinessDayControl <em>Business Day Control</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.BusinessDayControl
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getBusinessDayControl()
   * @generated
   */
  int BUSINESS_DAY_CONTROL = 15;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.Function <em>Function</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getFunction()
   * @generated
   */
  int FUNCTION = 16;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.InputBehaviour <em>Input Behaviour</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.InputBehaviour
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getInputBehaviour()
   * @generated
   */
  int INPUT_BEHAVIOUR = 17;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.Expansion <em>Expansion</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.Expansion
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getExpansion()
   * @generated
   */
  int EXPANSION = 18;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getAssociatedVersionsPresentationPattern()
   * @generated
   */
  int ASSOCIATED_VERSIONS_PRESENTATION_PATTERN = 19;

  /**
   * The meta object id for the '{@link com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern <em>Fields Layout Pattern</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern
   * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getFieldsLayoutPattern()
   * @generated
   */
  int FIELDS_LAYOUT_PATTERN = 20;


  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.Version <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Version</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version
   * @generated
   */
  EClass getVersion();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.version.versionDSL.Version#getForApplication <em>For Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>For Application</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getForApplication()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_ForApplication();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getShortName <em>Short Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Short Name</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getShortName()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_ShortName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getT24Name <em>T24 Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>T24 Name</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getT24Name()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_T24Name();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getMetamodelVersion <em>Metamodel Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metamodel Version</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getMetamodelVersion()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_MetamodelVersion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Group</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getGroup()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_Group();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getNumberOfAuthorisers <em>Number Of Authorisers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Authorisers</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getNumberOfAuthorisers()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_NumberOfAuthorisers();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Version#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Description</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getDescription()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Description();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getExceptionProcessing <em>Exception Processing</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exception Processing</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getExceptionProcessing()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_ExceptionProcessing();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getInterfaceException <em>Interface Exception</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interface Exception</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getInterfaceException()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_InterfaceException();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getBusinessDayControl <em>Business Day Control</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Business Day Control</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getBusinessDayControl()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_BusinessDayControl();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.version.versionDSL.Version#getKeySequence <em>Key Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Key Sequence</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getKeySequence()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_KeySequence();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getOtherCompanyAccess <em>Other Company Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Other Company Access</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getOtherCompanyAccess()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_OtherCompanyAccess();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getAutoCompanyChange <em>Auto Company Change</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Auto Company Change</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAutoCompanyChange()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_AutoCompanyChange();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getOverrideApproval <em>Override Approval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Override Approval</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getOverrideApproval()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_OverrideApproval();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipFormats <em>Deal Slip Formats</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Deal Slip Formats</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipFormats()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_DealSlipFormats();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipTrigger <em>Deal Slip Trigger</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Deal Slip Trigger</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipTrigger()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_DealSlipTrigger();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipStyleSheet <em>Deal Slip Style Sheet</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Deal Slip Style Sheet</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipStyleSheet()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_DealSlipStyleSheet();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getRecordsPerPage <em>Records Per Page</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Records Per Page</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getRecordsPerPage()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_RecordsPerPage();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsPerLine <em>Fields Per Line</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fields Per Line</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getFieldsPerLine()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_FieldsPerLine();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getInitialCursorPosition <em>Initial Cursor Position</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Initial Cursor Position</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getInitialCursorPosition()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_InitialCursorPosition();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getBrowserToolbar <em>Browser Toolbar</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Browser Toolbar</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getBrowserToolbar()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_BrowserToolbar();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.version.versionDSL.Version#getLanguageLocale <em>Language Locale</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Language Locale</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getLanguageLocale()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_LanguageLocale();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader1 <em>Header1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Header1</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getHeader1()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Header1();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader2 <em>Header2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Header2</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getHeader2()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Header2();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader <em>Header</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Header</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getHeader()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Header();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Version#getFooter <em>Footer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Footer</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getFooter()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Footer();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersion <em>Next Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Next Version</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getNextVersion()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_NextVersion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersionFunction <em>Next Version Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Next Version Function</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getNextVersionFunction()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_NextVersionFunction();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getNextTransactionReference <em>Next Transaction Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Next Transaction Reference</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getNextTransactionReference()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_NextTransactionReference();

  /**
   * Returns the meta object for the reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersions <em>Associated Versions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Associated Versions</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersions()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_AssociatedVersions();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getIncludeVersionControl <em>Include Version Control</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Include Version Control</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getIncludeVersionControl()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_IncludeVersionControl();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutines <em>Authorization Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Authorization Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutines()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_AuthorizationRoutines();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutinesAfterCommit <em>Authorization Routines After Commit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Authorization Routines After Commit</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutinesAfterCommit()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_AuthorizationRoutinesAfterCommit();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getInputRoutines <em>Input Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Input Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getInputRoutines()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_InputRoutines();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getInputRoutinesAfterCommit <em>Input Routines After Commit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Input Routines After Commit</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getInputRoutinesAfterCommit()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_InputRoutinesAfterCommit();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getKeyValidationRoutines <em>Key Validation Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Key Validation Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getKeyValidationRoutines()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_KeyValidationRoutines();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getPreProcessValidationRoutines <em>Pre Process Validation Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Pre Process Validation Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getPreProcessValidationRoutines()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_PreProcessValidationRoutines();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getWebValidationRoutines <em>Web Validation Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Web Validation Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getWebValidationRoutines()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_WebValidationRoutines();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.version.versionDSL.Version#getConfirmVersion <em>Confirm Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Confirm Version</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getConfirmVersion()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_ConfirmVersion();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.t24.version.versionDSL.Version#getPreviewVersion <em>Preview Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Preview Version</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getPreviewVersion()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_PreviewVersion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getChallengeResponse <em>Challenge Response</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Challenge Response</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getChallengeResponse()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_ChallengeResponse();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.version.versionDSL.Version#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Attributes</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAttributes()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_Attributes();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getPublishWebService <em>Publish Web Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Publish Web Service</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getPublishWebService()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_PublishWebService();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceActivity <em>Web Service Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Service Activity</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceActivity()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_WebServiceActivity();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceFunction <em>Web Service Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Service Function</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceFunction()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_WebServiceFunction();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceDescription <em>Web Service Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Service Description</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceDescription()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_WebServiceDescription();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceNames <em>Web Service Names</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Web Service Names</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceNames()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_WebServiceNames();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getGenerateIFP <em>Generate IFP</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Generate IFP</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getGenerateIFP()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_GenerateIFP();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Associated Versions Presentation Pattern</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersionsPresentationPattern()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_AssociatedVersionsPresentationPattern();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsLayoutPattern <em>Fields Layout Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fields Layout Pattern</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getFieldsLayoutPattern()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_FieldsLayoutPattern();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Version#getFields <em>Fields</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fields</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Version#getFields()
   * @see #getVersion()
   * @generated
   */
  EReference getVersion_Fields();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.Field <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field
   * @generated
   */
  EClass getField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getName()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Name();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getDisplayType <em>Display Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Display Type</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getDisplayType()
   * @see #getField()
   * @generated
   */
  EAttribute getField_DisplayType();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getInputBehaviour <em>Input Behaviour</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Input Behaviour</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getInputBehaviour()
   * @see #getField()
   * @generated
   */
  EAttribute getField_InputBehaviour();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getCaseConvention <em>Case Convention</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Case Convention</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getCaseConvention()
   * @see #getField()
   * @generated
   */
  EAttribute getField_CaseConvention();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getMaxLength <em>Max Length</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Max Length</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getMaxLength()
   * @see #getField()
   * @generated
   */
  EAttribute getField_MaxLength();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichmentLength <em>Enrichment Length</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Enrichment Length</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getEnrichmentLength()
   * @see #getField()
   * @generated
   */
  EAttribute getField_EnrichmentLength();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getExpansion <em>Expansion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Expansion</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getExpansion()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Expansion();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getRightAdjust <em>Right Adjust</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Right Adjust</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getRightAdjust()
   * @see #getField()
   * @generated
   */
  EAttribute getField_RightAdjust();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichment <em>Enrichment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Enrichment</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getEnrichment()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Enrichment();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getColumn <em>Column</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Column</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getColumn()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Column();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getRow <em>Row</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Row</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getRow()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Row();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getMandatory <em>Mandatory</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mandatory</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getMandatory()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Mandatory();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getRekeyRequired <em>Rekey Required</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Rekey Required</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getRekeyRequired()
   * @see #getField()
   * @generated
   */
  EAttribute getField_RekeyRequired();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getHyperlink <em>Hyperlink</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hyperlink</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getHyperlink()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Hyperlink();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getHotValidate <em>Hot Validate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hot Validate</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getHotValidate()
   * @see #getField()
   * @generated
   */
  EAttribute getField_HotValidate();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getHotField <em>Hot Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hot Field</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getHotField()
   * @see #getField()
   * @generated
   */
  EAttribute getField_HotField();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getWebValidate <em>Web Validate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Web Validate</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getWebValidate()
   * @see #getField()
   * @generated
   */
  EAttribute getField_WebValidate();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getSelectionEnquiry <em>Selection Enquiry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Selection Enquiry</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getSelectionEnquiry()
   * @see #getField()
   * @generated
   */
  EAttribute getField_SelectionEnquiry();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getEnquiryParameter <em>Enquiry Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Enquiry Parameter</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getEnquiryParameter()
   * @see #getField()
   * @generated
   */
  EAttribute getField_EnquiryParameter();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getPopupBehaviour <em>Popup Behaviour</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Popup Behaviour</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getPopupBehaviour()
   * @see #getField()
   * @generated
   */
  EAttribute getField_PopupBehaviour();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Field#getDefaults <em>Defaults</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Defaults</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getDefaults()
   * @see #getField()
   * @generated
   */
  EReference getField_Defaults();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Field#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Label</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getLabel()
   * @see #getField()
   * @generated
   */
  EReference getField_Label();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Field#getToolTip <em>Tool Tip</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Tool Tip</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getToolTip()
   * @see #getField()
   * @generated
   */
  EReference getField_ToolTip();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.t24.version.versionDSL.Field#getValidationRoutines <em>Validation Routines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Validation Routines</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getValidationRoutines()
   * @see #getField()
   * @generated
   */
  EReference getField_ValidationRoutines();

  /**
   * Returns the meta object for the attribute list '{@link com.odcgroup.t24.version.versionDSL.Field#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Attributes</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getAttributes()
   * @see #getField()
   * @generated
   */
  EAttribute getField_Attributes();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getMV <em>MV</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>MV</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getMV()
   * @see #getField()
   * @generated
   */
  EAttribute getField_MV();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Field#getSV <em>SV</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>SV</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Field#getSV()
   * @see #getField()
   * @generated
   */
  EAttribute getField_SV();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.Default <em>Default</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Default</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Default
   * @generated
   */
  EClass getDefault();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Default#getMv <em>Mv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mv</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Default#getMv()
   * @see #getDefault()
   * @generated
   */
  EAttribute getDefault_Mv();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Default#getSv <em>Sv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sv</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Default#getSv()
   * @see #getDefault()
   * @generated
   */
  EAttribute getDefault_Sv();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultIfOldValueEquals <em>Default If Old Value Equals</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Default If Old Value Equals</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Default#getDefaultIfOldValueEquals()
   * @see #getDefault()
   * @generated
   */
  EAttribute getDefault_DefaultIfOldValueEquals();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.Default#getDefaultNewValueOrAtRoutine <em>Default New Value Or At Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Default New Value Or At Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Default#getDefaultNewValueOrAtRoutine()
   * @see #getDefault()
   * @generated
   */
  EReference getDefault_DefaultNewValueOrAtRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.Routine <em>Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Routine
   * @generated
   */
  EClass getRoutine();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.Routine#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Routine#getName()
   * @see #getRoutine()
   * @generated
   */
  EAttribute getRoutine_Name();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.AtRoutine <em>At Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>At Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.AtRoutine
   * @generated
   */
  EClass getAtRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine <em>Value Or At Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value Or At Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine
   * @generated
   */
  EClass getValueOrAtRoutine();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getValue()
   * @see #getValueOrAtRoutine()
   * @generated
   */
  EAttribute getValueOrAtRoutine_Value();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getAtRoutine <em>At Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>At Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine#getAtRoutine()
   * @see #getValueOrAtRoutine()
   * @generated
   */
  EReference getValueOrAtRoutine_AtRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.JBCRoutine <em>JBC Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>JBC Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.JBCRoutine
   * @generated
   */
  EClass getJBCRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.JavaRoutine <em>Java Routine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Java Routine</em>'.
   * @see com.odcgroup.t24.version.versionDSL.JavaRoutine
   * @generated
   */
  EClass getJavaRoutine();

  /**
   * Returns the meta object for class '{@link com.odcgroup.t24.version.versionDSL.DealSlip <em>Deal Slip</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Deal Slip</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DealSlip
   * @generated
   */
  EClass getDealSlip();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFormat <em>Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Format</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DealSlip#getFormat()
   * @see #getDealSlip()
   * @generated
   */
  EAttribute getDealSlip_Format();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.t24.version.versionDSL.DealSlip#getFunction <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Function</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DealSlip#getFunction()
   * @see #getDealSlip()
   * @generated
   */
  EAttribute getDealSlip_Function();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.YesNo <em>Yes No</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Yes No</em>'.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @generated
   */
  EEnum getYesNo();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.PopupBehaviour <em>Popup Behaviour</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Popup Behaviour</em>'.
   * @see com.odcgroup.t24.version.versionDSL.PopupBehaviour
   * @generated
   */
  EEnum getPopupBehaviour();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.CaseConvention <em>Case Convention</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Case Convention</em>'.
   * @see com.odcgroup.t24.version.versionDSL.CaseConvention
   * @generated
   */
  EEnum getCaseConvention();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.DisplayType <em>Display Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Display Type</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DisplayType
   * @generated
   */
  EEnum getDisplayType();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction <em>Deal Slip Format Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Deal Slip Format Function</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction
   * @generated
   */
  EEnum getDealSlipFormatFunction();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.DealSlipTrigger <em>Deal Slip Trigger</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Deal Slip Trigger</em>'.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipTrigger
   * @generated
   */
  EEnum getDealSlipTrigger();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.BusinessDayControl <em>Business Day Control</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Business Day Control</em>'.
   * @see com.odcgroup.t24.version.versionDSL.BusinessDayControl
   * @generated
   */
  EEnum getBusinessDayControl();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.Function <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Function</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @generated
   */
  EEnum getFunction();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.InputBehaviour <em>Input Behaviour</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Input Behaviour</em>'.
   * @see com.odcgroup.t24.version.versionDSL.InputBehaviour
   * @generated
   */
  EEnum getInputBehaviour();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.Expansion <em>Expansion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Expansion</em>'.
   * @see com.odcgroup.t24.version.versionDSL.Expansion
   * @generated
   */
  EEnum getExpansion();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Associated Versions Presentation Pattern</em>'.
   * @see com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern
   * @generated
   */
  EEnum getAssociatedVersionsPresentationPattern();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern <em>Fields Layout Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Fields Layout Pattern</em>'.
   * @see com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern
   * @generated
   */
  EEnum getFieldsLayoutPattern();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  VersionDSLFactory getVersionDSLFactory();

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
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl <em>Version</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getVersion()
     * @generated
     */
    EClass VERSION = eINSTANCE.getVersion();

    /**
     * The meta object literal for the '<em><b>For Application</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__FOR_APPLICATION = eINSTANCE.getVersion_ForApplication();

    /**
     * The meta object literal for the '<em><b>Short Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__SHORT_NAME = eINSTANCE.getVersion_ShortName();

    /**
     * The meta object literal for the '<em><b>T24 Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__T24_NAME = eINSTANCE.getVersion_T24Name();

    /**
     * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__METAMODEL_VERSION = eINSTANCE.getVersion_MetamodelVersion();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__GROUP = eINSTANCE.getVersion_Group();

    /**
     * The meta object literal for the '<em><b>Number Of Authorisers</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__NUMBER_OF_AUTHORISERS = eINSTANCE.getVersion_NumberOfAuthorisers();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__DESCRIPTION = eINSTANCE.getVersion_Description();

    /**
     * The meta object literal for the '<em><b>Exception Processing</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__EXCEPTION_PROCESSING = eINSTANCE.getVersion_ExceptionProcessing();

    /**
     * The meta object literal for the '<em><b>Interface Exception</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__INTERFACE_EXCEPTION = eINSTANCE.getVersion_InterfaceException();

    /**
     * The meta object literal for the '<em><b>Business Day Control</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__BUSINESS_DAY_CONTROL = eINSTANCE.getVersion_BusinessDayControl();

    /**
     * The meta object literal for the '<em><b>Key Sequence</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__KEY_SEQUENCE = eINSTANCE.getVersion_KeySequence();

    /**
     * The meta object literal for the '<em><b>Other Company Access</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__OTHER_COMPANY_ACCESS = eINSTANCE.getVersion_OtherCompanyAccess();

    /**
     * The meta object literal for the '<em><b>Auto Company Change</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__AUTO_COMPANY_CHANGE = eINSTANCE.getVersion_AutoCompanyChange();

    /**
     * The meta object literal for the '<em><b>Override Approval</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__OVERRIDE_APPROVAL = eINSTANCE.getVersion_OverrideApproval();

    /**
     * The meta object literal for the '<em><b>Deal Slip Formats</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__DEAL_SLIP_FORMATS = eINSTANCE.getVersion_DealSlipFormats();

    /**
     * The meta object literal for the '<em><b>Deal Slip Trigger</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__DEAL_SLIP_TRIGGER = eINSTANCE.getVersion_DealSlipTrigger();

    /**
     * The meta object literal for the '<em><b>Deal Slip Style Sheet</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__DEAL_SLIP_STYLE_SHEET = eINSTANCE.getVersion_DealSlipStyleSheet();

    /**
     * The meta object literal for the '<em><b>Records Per Page</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__RECORDS_PER_PAGE = eINSTANCE.getVersion_RecordsPerPage();

    /**
     * The meta object literal for the '<em><b>Fields Per Line</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__FIELDS_PER_LINE = eINSTANCE.getVersion_FieldsPerLine();

    /**
     * The meta object literal for the '<em><b>Initial Cursor Position</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__INITIAL_CURSOR_POSITION = eINSTANCE.getVersion_InitialCursorPosition();

    /**
     * The meta object literal for the '<em><b>Browser Toolbar</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__BROWSER_TOOLBAR = eINSTANCE.getVersion_BrowserToolbar();

    /**
     * The meta object literal for the '<em><b>Language Locale</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__LANGUAGE_LOCALE = eINSTANCE.getVersion_LanguageLocale();

    /**
     * The meta object literal for the '<em><b>Header1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__HEADER1 = eINSTANCE.getVersion_Header1();

    /**
     * The meta object literal for the '<em><b>Header2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__HEADER2 = eINSTANCE.getVersion_Header2();

    /**
     * The meta object literal for the '<em><b>Header</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__HEADER = eINSTANCE.getVersion_Header();

    /**
     * The meta object literal for the '<em><b>Footer</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__FOOTER = eINSTANCE.getVersion_Footer();

    /**
     * The meta object literal for the '<em><b>Next Version</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__NEXT_VERSION = eINSTANCE.getVersion_NextVersion();

    /**
     * The meta object literal for the '<em><b>Next Version Function</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__NEXT_VERSION_FUNCTION = eINSTANCE.getVersion_NextVersionFunction();

    /**
     * The meta object literal for the '<em><b>Next Transaction Reference</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__NEXT_TRANSACTION_REFERENCE = eINSTANCE.getVersion_NextTransactionReference();

    /**
     * The meta object literal for the '<em><b>Associated Versions</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__ASSOCIATED_VERSIONS = eINSTANCE.getVersion_AssociatedVersions();

    /**
     * The meta object literal for the '<em><b>Include Version Control</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__INCLUDE_VERSION_CONTROL = eINSTANCE.getVersion_IncludeVersionControl();

    /**
     * The meta object literal for the '<em><b>Authorization Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__AUTHORIZATION_ROUTINES = eINSTANCE.getVersion_AuthorizationRoutines();

    /**
     * The meta object literal for the '<em><b>Authorization Routines After Commit</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT = eINSTANCE.getVersion_AuthorizationRoutinesAfterCommit();

    /**
     * The meta object literal for the '<em><b>Input Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__INPUT_ROUTINES = eINSTANCE.getVersion_InputRoutines();

    /**
     * The meta object literal for the '<em><b>Input Routines After Commit</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__INPUT_ROUTINES_AFTER_COMMIT = eINSTANCE.getVersion_InputRoutinesAfterCommit();

    /**
     * The meta object literal for the '<em><b>Key Validation Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__KEY_VALIDATION_ROUTINES = eINSTANCE.getVersion_KeyValidationRoutines();

    /**
     * The meta object literal for the '<em><b>Pre Process Validation Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__PRE_PROCESS_VALIDATION_ROUTINES = eINSTANCE.getVersion_PreProcessValidationRoutines();

    /**
     * The meta object literal for the '<em><b>Web Validation Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__WEB_VALIDATION_ROUTINES = eINSTANCE.getVersion_WebValidationRoutines();

    /**
     * The meta object literal for the '<em><b>Confirm Version</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__CONFIRM_VERSION = eINSTANCE.getVersion_ConfirmVersion();

    /**
     * The meta object literal for the '<em><b>Preview Version</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__PREVIEW_VERSION = eINSTANCE.getVersion_PreviewVersion();

    /**
     * The meta object literal for the '<em><b>Challenge Response</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__CHALLENGE_RESPONSE = eINSTANCE.getVersion_ChallengeResponse();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__ATTRIBUTES = eINSTANCE.getVersion_Attributes();

    /**
     * The meta object literal for the '<em><b>Publish Web Service</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__PUBLISH_WEB_SERVICE = eINSTANCE.getVersion_PublishWebService();

    /**
     * The meta object literal for the '<em><b>Web Service Activity</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__WEB_SERVICE_ACTIVITY = eINSTANCE.getVersion_WebServiceActivity();

    /**
     * The meta object literal for the '<em><b>Web Service Function</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__WEB_SERVICE_FUNCTION = eINSTANCE.getVersion_WebServiceFunction();

    /**
     * The meta object literal for the '<em><b>Web Service Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__WEB_SERVICE_DESCRIPTION = eINSTANCE.getVersion_WebServiceDescription();

    /**
     * The meta object literal for the '<em><b>Web Service Names</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__WEB_SERVICE_NAMES = eINSTANCE.getVersion_WebServiceNames();

    /**
     * The meta object literal for the '<em><b>Generate IFP</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__GENERATE_IFP = eINSTANCE.getVersion_GenerateIFP();

    /**
     * The meta object literal for the '<em><b>Associated Versions Presentation Pattern</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN = eINSTANCE.getVersion_AssociatedVersionsPresentationPattern();

    /**
     * The meta object literal for the '<em><b>Fields Layout Pattern</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__FIELDS_LAYOUT_PATTERN = eINSTANCE.getVersion_FieldsLayoutPattern();

    /**
     * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION__FIELDS = eINSTANCE.getVersion_Fields();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl <em>Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.FieldImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getField()
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
     * The meta object literal for the '<em><b>Display Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__DISPLAY_TYPE = eINSTANCE.getField_DisplayType();

    /**
     * The meta object literal for the '<em><b>Input Behaviour</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__INPUT_BEHAVIOUR = eINSTANCE.getField_InputBehaviour();

    /**
     * The meta object literal for the '<em><b>Case Convention</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__CASE_CONVENTION = eINSTANCE.getField_CaseConvention();

    /**
     * The meta object literal for the '<em><b>Max Length</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__MAX_LENGTH = eINSTANCE.getField_MaxLength();

    /**
     * The meta object literal for the '<em><b>Enrichment Length</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ENRICHMENT_LENGTH = eINSTANCE.getField_EnrichmentLength();

    /**
     * The meta object literal for the '<em><b>Expansion</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__EXPANSION = eINSTANCE.getField_Expansion();

    /**
     * The meta object literal for the '<em><b>Right Adjust</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__RIGHT_ADJUST = eINSTANCE.getField_RightAdjust();

    /**
     * The meta object literal for the '<em><b>Enrichment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ENRICHMENT = eINSTANCE.getField_Enrichment();

    /**
     * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__COLUMN = eINSTANCE.getField_Column();

    /**
     * The meta object literal for the '<em><b>Row</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ROW = eINSTANCE.getField_Row();

    /**
     * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__MANDATORY = eINSTANCE.getField_Mandatory();

    /**
     * The meta object literal for the '<em><b>Rekey Required</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__REKEY_REQUIRED = eINSTANCE.getField_RekeyRequired();

    /**
     * The meta object literal for the '<em><b>Hyperlink</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__HYPERLINK = eINSTANCE.getField_Hyperlink();

    /**
     * The meta object literal for the '<em><b>Hot Validate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__HOT_VALIDATE = eINSTANCE.getField_HotValidate();

    /**
     * The meta object literal for the '<em><b>Hot Field</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__HOT_FIELD = eINSTANCE.getField_HotField();

    /**
     * The meta object literal for the '<em><b>Web Validate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__WEB_VALIDATE = eINSTANCE.getField_WebValidate();

    /**
     * The meta object literal for the '<em><b>Selection Enquiry</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__SELECTION_ENQUIRY = eINSTANCE.getField_SelectionEnquiry();

    /**
     * The meta object literal for the '<em><b>Enquiry Parameter</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ENQUIRY_PARAMETER = eINSTANCE.getField_EnquiryParameter();

    /**
     * The meta object literal for the '<em><b>Popup Behaviour</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__POPUP_BEHAVIOUR = eINSTANCE.getField_PopupBehaviour();

    /**
     * The meta object literal for the '<em><b>Defaults</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__DEFAULTS = eINSTANCE.getField_Defaults();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__LABEL = eINSTANCE.getField_Label();

    /**
     * The meta object literal for the '<em><b>Tool Tip</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__TOOL_TIP = eINSTANCE.getField_ToolTip();

    /**
     * The meta object literal for the '<em><b>Validation Routines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD__VALIDATION_ROUTINES = eINSTANCE.getField_ValidationRoutines();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__ATTRIBUTES = eINSTANCE.getField_Attributes();

    /**
     * The meta object literal for the '<em><b>MV</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__MV = eINSTANCE.getField_MV();

    /**
     * The meta object literal for the '<em><b>SV</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD__SV = eINSTANCE.getField_SV();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.DefaultImpl <em>Default</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.DefaultImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDefault()
     * @generated
     */
    EClass DEFAULT = eINSTANCE.getDefault();

    /**
     * The meta object literal for the '<em><b>Mv</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DEFAULT__MV = eINSTANCE.getDefault_Mv();

    /**
     * The meta object literal for the '<em><b>Sv</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DEFAULT__SV = eINSTANCE.getDefault_Sv();

    /**
     * The meta object literal for the '<em><b>Default If Old Value Equals</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS = eINSTANCE.getDefault_DefaultIfOldValueEquals();

    /**
     * The meta object literal for the '<em><b>Default New Value Or At Routine</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE = eINSTANCE.getDefault_DefaultNewValueOrAtRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.RoutineImpl <em>Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.RoutineImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getRoutine()
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
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.AtRoutineImpl <em>At Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.AtRoutineImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getAtRoutine()
     * @generated
     */
    EClass AT_ROUTINE = eINSTANCE.getAtRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl <em>Value Or At Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.ValueOrAtRoutineImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getValueOrAtRoutine()
     * @generated
     */
    EClass VALUE_OR_AT_ROUTINE = eINSTANCE.getValueOrAtRoutine();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VALUE_OR_AT_ROUTINE__VALUE = eINSTANCE.getValueOrAtRoutine_Value();

    /**
     * The meta object literal for the '<em><b>At Routine</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VALUE_OR_AT_ROUTINE__AT_ROUTINE = eINSTANCE.getValueOrAtRoutine_AtRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.JBCRoutineImpl <em>JBC Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.JBCRoutineImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getJBCRoutine()
     * @generated
     */
    EClass JBC_ROUTINE = eINSTANCE.getJBCRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.JavaRoutineImpl <em>Java Routine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.JavaRoutineImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getJavaRoutine()
     * @generated
     */
    EClass JAVA_ROUTINE = eINSTANCE.getJavaRoutine();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl <em>Deal Slip</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlip()
     * @generated
     */
    EClass DEAL_SLIP = eINSTANCE.getDealSlip();

    /**
     * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DEAL_SLIP__FORMAT = eINSTANCE.getDealSlip_Format();

    /**
     * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DEAL_SLIP__FUNCTION = eINSTANCE.getDealSlip_Function();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.YesNo <em>Yes No</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.YesNo
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getYesNo()
     * @generated
     */
    EEnum YES_NO = eINSTANCE.getYesNo();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.PopupBehaviour <em>Popup Behaviour</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.PopupBehaviour
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getPopupBehaviour()
     * @generated
     */
    EEnum POPUP_BEHAVIOUR = eINSTANCE.getPopupBehaviour();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.CaseConvention <em>Case Convention</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.CaseConvention
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getCaseConvention()
     * @generated
     */
    EEnum CASE_CONVENTION = eINSTANCE.getCaseConvention();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.DisplayType <em>Display Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.DisplayType
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDisplayType()
     * @generated
     */
    EEnum DISPLAY_TYPE = eINSTANCE.getDisplayType();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction <em>Deal Slip Format Function</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlipFormatFunction()
     * @generated
     */
    EEnum DEAL_SLIP_FORMAT_FUNCTION = eINSTANCE.getDealSlipFormatFunction();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.DealSlipTrigger <em>Deal Slip Trigger</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.DealSlipTrigger
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getDealSlipTrigger()
     * @generated
     */
    EEnum DEAL_SLIP_TRIGGER = eINSTANCE.getDealSlipTrigger();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.BusinessDayControl <em>Business Day Control</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.BusinessDayControl
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getBusinessDayControl()
     * @generated
     */
    EEnum BUSINESS_DAY_CONTROL = eINSTANCE.getBusinessDayControl();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.Function <em>Function</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.Function
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getFunction()
     * @generated
     */
    EEnum FUNCTION = eINSTANCE.getFunction();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.InputBehaviour <em>Input Behaviour</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.InputBehaviour
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getInputBehaviour()
     * @generated
     */
    EEnum INPUT_BEHAVIOUR = eINSTANCE.getInputBehaviour();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.Expansion <em>Expansion</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.Expansion
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getExpansion()
     * @generated
     */
    EEnum EXPANSION = eINSTANCE.getExpansion();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getAssociatedVersionsPresentationPattern()
     * @generated
     */
    EEnum ASSOCIATED_VERSIONS_PRESENTATION_PATTERN = eINSTANCE.getAssociatedVersionsPresentationPattern();

    /**
     * The meta object literal for the '{@link com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern <em>Fields Layout Pattern</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern
     * @see com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl#getFieldsLayoutPattern()
     * @generated
     */
    EEnum FIELDS_LAYOUT_PATTERN = eINSTANCE.getFieldsLayoutPattern();

  }

} //VersionDSLPackage
