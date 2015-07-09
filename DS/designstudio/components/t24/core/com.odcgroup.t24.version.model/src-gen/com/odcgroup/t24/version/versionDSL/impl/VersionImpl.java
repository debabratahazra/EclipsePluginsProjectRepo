/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.mdf.metamodel.MdfClass;

import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.BusinessDayControl;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipTrigger;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Function;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.t24.version.versionDSL.YesNo;

import com.odcgroup.translation.translationDsl.Translations;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getForApplication <em>For Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getT24Name <em>T24 Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getNumberOfAuthorisers <em>Number Of Authorisers</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getExceptionProcessing <em>Exception Processing</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getInterfaceException <em>Interface Exception</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getBusinessDayControl <em>Business Day Control</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getKeySequence <em>Key Sequence</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getOtherCompanyAccess <em>Other Company Access</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAutoCompanyChange <em>Auto Company Change</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getOverrideApproval <em>Override Approval</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getDealSlipFormats <em>Deal Slip Formats</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getDealSlipTrigger <em>Deal Slip Trigger</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getDealSlipStyleSheet <em>Deal Slip Style Sheet</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getRecordsPerPage <em>Records Per Page</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getFieldsPerLine <em>Fields Per Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getInitialCursorPosition <em>Initial Cursor Position</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getBrowserToolbar <em>Browser Toolbar</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getLanguageLocale <em>Language Locale</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getHeader1 <em>Header1</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getHeader2 <em>Header2</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getFooter <em>Footer</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getNextVersion <em>Next Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getNextVersionFunction <em>Next Version Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getNextTransactionReference <em>Next Transaction Reference</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAssociatedVersions <em>Associated Versions</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getIncludeVersionControl <em>Include Version Control</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAuthorizationRoutines <em>Authorization Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAuthorizationRoutinesAfterCommit <em>Authorization Routines After Commit</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getInputRoutines <em>Input Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getInputRoutinesAfterCommit <em>Input Routines After Commit</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getKeyValidationRoutines <em>Key Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getPreProcessValidationRoutines <em>Pre Process Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getWebValidationRoutines <em>Web Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getConfirmVersion <em>Confirm Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getPreviewVersion <em>Preview Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getChallengeResponse <em>Challenge Response</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getPublishWebService <em>Publish Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getWebServiceActivity <em>Web Service Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getWebServiceFunction <em>Web Service Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getWebServiceDescription <em>Web Service Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getWebServiceNames <em>Web Service Names</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getGenerateIFP <em>Generate IFP</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getAssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getFieldsLayoutPattern <em>Fields Layout Pattern</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.VersionImpl#getFields <em>Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VersionImpl extends MinimalEObjectImpl.Container implements Version
{
  /**
   * The cached value of the '{@link #getForApplication() <em>For Application</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getForApplication()
   * @generated
   * @ordered
   */
  protected MdfClass forApplication;

  /**
   * The default value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getShortName()
   * @generated
   * @ordered
   */
  protected static final String SHORT_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getShortName()
   * @generated
   * @ordered
   */
  protected String shortName = SHORT_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getT24Name() <em>T24 Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getT24Name()
   * @generated
   * @ordered
   */
  protected static final String T24_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getT24Name() <em>T24 Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getT24Name()
   * @generated
   * @ordered
   */
  protected String t24Name = T24_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetamodelVersion()
   * @generated
   * @ordered
   */
  protected static final String METAMODEL_VERSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetamodelVersion()
   * @generated
   * @ordered
   */
  protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

  /**
   * The default value of the '{@link #getGroup() <em>Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroup()
   * @generated
   * @ordered
   */
  protected static final String GROUP_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroup()
   * @generated
   * @ordered
   */
  protected String group = GROUP_EDEFAULT;

  /**
   * The default value of the '{@link #getNumberOfAuthorisers() <em>Number Of Authorisers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfAuthorisers()
   * @generated
   * @ordered
   */
  protected static final Integer NUMBER_OF_AUTHORISERS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNumberOfAuthorisers() <em>Number Of Authorisers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfAuthorisers()
   * @generated
   * @ordered
   */
  protected Integer numberOfAuthorisers = NUMBER_OF_AUTHORISERS_EDEFAULT;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected Translations description;

  /**
   * The default value of the '{@link #getExceptionProcessing() <em>Exception Processing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExceptionProcessing()
   * @generated
   * @ordered
   */
  protected static final Integer EXCEPTION_PROCESSING_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExceptionProcessing() <em>Exception Processing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExceptionProcessing()
   * @generated
   * @ordered
   */
  protected Integer exceptionProcessing = EXCEPTION_PROCESSING_EDEFAULT;

  /**
   * The default value of the '{@link #getInterfaceException() <em>Interface Exception</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterfaceException()
   * @generated
   * @ordered
   */
  protected static final Integer INTERFACE_EXCEPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInterfaceException() <em>Interface Exception</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterfaceException()
   * @generated
   * @ordered
   */
  protected Integer interfaceException = INTERFACE_EXCEPTION_EDEFAULT;

  /**
   * The default value of the '{@link #getBusinessDayControl() <em>Business Day Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBusinessDayControl()
   * @generated
   * @ordered
   */
  protected static final BusinessDayControl BUSINESS_DAY_CONTROL_EDEFAULT = BusinessDayControl.NONE;

  /**
   * The cached value of the '{@link #getBusinessDayControl() <em>Business Day Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBusinessDayControl()
   * @generated
   * @ordered
   */
  protected BusinessDayControl businessDayControl = BUSINESS_DAY_CONTROL_EDEFAULT;

  /**
   * The cached value of the '{@link #getKeySequence() <em>Key Sequence</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKeySequence()
   * @generated
   * @ordered
   */
  protected EList<String> keySequence;

  /**
   * The default value of the '{@link #getOtherCompanyAccess() <em>Other Company Access</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOtherCompanyAccess()
   * @generated
   * @ordered
   */
  protected static final YesNo OTHER_COMPANY_ACCESS_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getOtherCompanyAccess() <em>Other Company Access</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOtherCompanyAccess()
   * @generated
   * @ordered
   */
  protected YesNo otherCompanyAccess = OTHER_COMPANY_ACCESS_EDEFAULT;

  /**
   * The default value of the '{@link #getAutoCompanyChange() <em>Auto Company Change</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAutoCompanyChange()
   * @generated
   * @ordered
   */
  protected static final YesNo AUTO_COMPANY_CHANGE_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getAutoCompanyChange() <em>Auto Company Change</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAutoCompanyChange()
   * @generated
   * @ordered
   */
  protected YesNo autoCompanyChange = AUTO_COMPANY_CHANGE_EDEFAULT;

  /**
   * The default value of the '{@link #getOverrideApproval() <em>Override Approval</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverrideApproval()
   * @generated
   * @ordered
   */
  protected static final YesNo OVERRIDE_APPROVAL_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getOverrideApproval() <em>Override Approval</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverrideApproval()
   * @generated
   * @ordered
   */
  protected YesNo overrideApproval = OVERRIDE_APPROVAL_EDEFAULT;

  /**
   * The cached value of the '{@link #getDealSlipFormats() <em>Deal Slip Formats</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDealSlipFormats()
   * @generated
   * @ordered
   */
  protected EList<DealSlip> dealSlipFormats;

  /**
   * The default value of the '{@link #getDealSlipTrigger() <em>Deal Slip Trigger</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDealSlipTrigger()
   * @generated
   * @ordered
   */
  protected static final DealSlipTrigger DEAL_SLIP_TRIGGER_EDEFAULT = DealSlipTrigger.NONE;

  /**
   * The cached value of the '{@link #getDealSlipTrigger() <em>Deal Slip Trigger</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDealSlipTrigger()
   * @generated
   * @ordered
   */
  protected DealSlipTrigger dealSlipTrigger = DEAL_SLIP_TRIGGER_EDEFAULT;

  /**
   * The default value of the '{@link #getDealSlipStyleSheet() <em>Deal Slip Style Sheet</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDealSlipStyleSheet()
   * @generated
   * @ordered
   */
  protected static final String DEAL_SLIP_STYLE_SHEET_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDealSlipStyleSheet() <em>Deal Slip Style Sheet</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDealSlipStyleSheet()
   * @generated
   * @ordered
   */
  protected String dealSlipStyleSheet = DEAL_SLIP_STYLE_SHEET_EDEFAULT;

  /**
   * The default value of the '{@link #getRecordsPerPage() <em>Records Per Page</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRecordsPerPage()
   * @generated
   * @ordered
   */
  protected static final String RECORDS_PER_PAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRecordsPerPage() <em>Records Per Page</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRecordsPerPage()
   * @generated
   * @ordered
   */
  protected String recordsPerPage = RECORDS_PER_PAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getFieldsPerLine() <em>Fields Per Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldsPerLine()
   * @generated
   * @ordered
   */
  protected static final String FIELDS_PER_LINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFieldsPerLine() <em>Fields Per Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldsPerLine()
   * @generated
   * @ordered
   */
  protected String fieldsPerLine = FIELDS_PER_LINE_EDEFAULT;

  /**
   * The default value of the '{@link #getInitialCursorPosition() <em>Initial Cursor Position</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitialCursorPosition()
   * @generated
   * @ordered
   */
  protected static final String INITIAL_CURSOR_POSITION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInitialCursorPosition() <em>Initial Cursor Position</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitialCursorPosition()
   * @generated
   * @ordered
   */
  protected String initialCursorPosition = INITIAL_CURSOR_POSITION_EDEFAULT;

  /**
   * The default value of the '{@link #getBrowserToolbar() <em>Browser Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrowserToolbar()
   * @generated
   * @ordered
   */
  protected static final String BROWSER_TOOLBAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBrowserToolbar() <em>Browser Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrowserToolbar()
   * @generated
   * @ordered
   */
  protected String browserToolbar = BROWSER_TOOLBAR_EDEFAULT;

  /**
   * The cached value of the '{@link #getLanguageLocale() <em>Language Locale</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLanguageLocale()
   * @generated
   * @ordered
   */
  protected EList<String> languageLocale;

  /**
   * The cached value of the '{@link #getHeader1() <em>Header1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeader1()
   * @generated
   * @ordered
   */
  protected Translations header1;

  /**
   * The cached value of the '{@link #getHeader2() <em>Header2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeader2()
   * @generated
   * @ordered
   */
  protected Translations header2;

  /**
   * The cached value of the '{@link #getHeader() <em>Header</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeader()
   * @generated
   * @ordered
   */
  protected Translations header;

  /**
   * The cached value of the '{@link #getFooter() <em>Footer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFooter()
   * @generated
   * @ordered
   */
  protected Translations footer;

  /**
   * The cached value of the '{@link #getNextVersion() <em>Next Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextVersion()
   * @generated
   * @ordered
   */
  protected Version nextVersion;

  /**
   * The default value of the '{@link #getNextVersionFunction() <em>Next Version Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextVersionFunction()
   * @generated
   * @ordered
   */
  protected static final Function NEXT_VERSION_FUNCTION_EDEFAULT = Function.NONE;

  /**
   * The cached value of the '{@link #getNextVersionFunction() <em>Next Version Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextVersionFunction()
   * @generated
   * @ordered
   */
  protected Function nextVersionFunction = NEXT_VERSION_FUNCTION_EDEFAULT;

  /**
   * The default value of the '{@link #getNextTransactionReference() <em>Next Transaction Reference</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextTransactionReference()
   * @generated
   * @ordered
   */
  protected static final String NEXT_TRANSACTION_REFERENCE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNextTransactionReference() <em>Next Transaction Reference</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextTransactionReference()
   * @generated
   * @ordered
   */
  protected String nextTransactionReference = NEXT_TRANSACTION_REFERENCE_EDEFAULT;

  /**
   * The cached value of the '{@link #getAssociatedVersions() <em>Associated Versions</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssociatedVersions()
   * @generated
   * @ordered
   */
  protected EList<Version> associatedVersions;

  /**
   * The default value of the '{@link #getIncludeVersionControl() <em>Include Version Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIncludeVersionControl()
   * @generated
   * @ordered
   */
  protected static final YesNo INCLUDE_VERSION_CONTROL_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getIncludeVersionControl() <em>Include Version Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIncludeVersionControl()
   * @generated
   * @ordered
   */
  protected YesNo includeVersionControl = INCLUDE_VERSION_CONTROL_EDEFAULT;

  /**
   * The cached value of the '{@link #getAuthorizationRoutines() <em>Authorization Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAuthorizationRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> authorizationRoutines;

  /**
   * The cached value of the '{@link #getAuthorizationRoutinesAfterCommit() <em>Authorization Routines After Commit</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAuthorizationRoutinesAfterCommit()
   * @generated
   * @ordered
   */
  protected EList<Routine> authorizationRoutinesAfterCommit;

  /**
   * The cached value of the '{@link #getInputRoutines() <em>Input Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> inputRoutines;

  /**
   * The cached value of the '{@link #getInputRoutinesAfterCommit() <em>Input Routines After Commit</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputRoutinesAfterCommit()
   * @generated
   * @ordered
   */
  protected EList<Routine> inputRoutinesAfterCommit;

  /**
   * The cached value of the '{@link #getKeyValidationRoutines() <em>Key Validation Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKeyValidationRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> keyValidationRoutines;

  /**
   * The cached value of the '{@link #getPreProcessValidationRoutines() <em>Pre Process Validation Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPreProcessValidationRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> preProcessValidationRoutines;

  /**
   * The cached value of the '{@link #getWebValidationRoutines() <em>Web Validation Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebValidationRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> webValidationRoutines;

  /**
   * The cached value of the '{@link #getConfirmVersion() <em>Confirm Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConfirmVersion()
   * @generated
   * @ordered
   */
  protected Version confirmVersion;

  /**
   * The cached value of the '{@link #getPreviewVersion() <em>Preview Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPreviewVersion()
   * @generated
   * @ordered
   */
  protected Version previewVersion;

  /**
   * The default value of the '{@link #getChallengeResponse() <em>Challenge Response</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChallengeResponse()
   * @generated
   * @ordered
   */
  protected static final String CHALLENGE_RESPONSE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getChallengeResponse() <em>Challenge Response</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChallengeResponse()
   * @generated
   * @ordered
   */
  protected String challengeResponse = CHALLENGE_RESPONSE_EDEFAULT;

  /**
   * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttributes()
   * @generated
   * @ordered
   */
  protected EList<String> attributes;

  /**
   * The default value of the '{@link #getPublishWebService() <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPublishWebService()
   * @generated
   * @ordered
   */
  protected static final YesNo PUBLISH_WEB_SERVICE_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getPublishWebService() <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPublishWebService()
   * @generated
   * @ordered
   */
  protected YesNo publishWebService = PUBLISH_WEB_SERVICE_EDEFAULT;

  /**
   * The default value of the '{@link #getWebServiceActivity() <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceActivity()
   * @generated
   * @ordered
   */
  protected static final String WEB_SERVICE_ACTIVITY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWebServiceActivity() <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceActivity()
   * @generated
   * @ordered
   */
  protected String webServiceActivity = WEB_SERVICE_ACTIVITY_EDEFAULT;

  /**
   * The default value of the '{@link #getWebServiceFunction() <em>Web Service Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceFunction()
   * @generated
   * @ordered
   */
  protected static final Function WEB_SERVICE_FUNCTION_EDEFAULT = Function.NONE;

  /**
   * The cached value of the '{@link #getWebServiceFunction() <em>Web Service Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceFunction()
   * @generated
   * @ordered
   */
  protected Function webServiceFunction = WEB_SERVICE_FUNCTION_EDEFAULT;

  /**
   * The default value of the '{@link #getWebServiceDescription() <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceDescription()
   * @generated
   * @ordered
   */
  protected static final String WEB_SERVICE_DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getWebServiceDescription() <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceDescription()
   * @generated
   * @ordered
   */
  protected String webServiceDescription = WEB_SERVICE_DESCRIPTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getWebServiceNames() <em>Web Service Names</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebServiceNames()
   * @generated
   * @ordered
   */
  protected EList<String> webServiceNames;

  /**
   * The default value of the '{@link #getGenerateIFP() <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGenerateIFP()
   * @generated
   * @ordered
   */
  protected static final YesNo GENERATE_IFP_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getGenerateIFP() <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGenerateIFP()
   * @generated
   * @ordered
   */
  protected YesNo generateIFP = GENERATE_IFP_EDEFAULT;

  /**
   * The default value of the '{@link #getAssociatedVersionsPresentationPattern() <em>Associated Versions Presentation Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssociatedVersionsPresentationPattern()
   * @generated
   * @ordered
   */
  protected static final AssociatedVersionsPresentationPattern ASSOCIATED_VERSIONS_PRESENTATION_PATTERN_EDEFAULT = AssociatedVersionsPresentationPattern.NONE;

  /**
   * The cached value of the '{@link #getAssociatedVersionsPresentationPattern() <em>Associated Versions Presentation Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssociatedVersionsPresentationPattern()
   * @generated
   * @ordered
   */
  protected AssociatedVersionsPresentationPattern associatedVersionsPresentationPattern = ASSOCIATED_VERSIONS_PRESENTATION_PATTERN_EDEFAULT;

  /**
   * The default value of the '{@link #getFieldsLayoutPattern() <em>Fields Layout Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldsLayoutPattern()
   * @generated
   * @ordered
   */
  protected static final FieldsLayoutPattern FIELDS_LAYOUT_PATTERN_EDEFAULT = FieldsLayoutPattern.NONE;

  /**
   * The cached value of the '{@link #getFieldsLayoutPattern() <em>Fields Layout Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldsLayoutPattern()
   * @generated
   * @ordered
   */
  protected FieldsLayoutPattern fieldsLayoutPattern = FIELDS_LAYOUT_PATTERN_EDEFAULT;

  /**
   * The cached value of the '{@link #getFields() <em>Fields</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFields()
   * @generated
   * @ordered
   */
  protected EList<Field> fields;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VersionImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return VersionDSLPackage.Literals.VERSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfClass getForApplication()
  {
    if (forApplication != null && ((EObject)forApplication).eIsProxy())
    {
      InternalEObject oldForApplication = (InternalEObject)forApplication;
      forApplication = (MdfClass)eResolveProxy(oldForApplication);
      if (forApplication != oldForApplication)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersionDSLPackage.VERSION__FOR_APPLICATION, oldForApplication, forApplication));
      }
    }
    return forApplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MdfClass basicGetForApplication()
  {
    return forApplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setForApplication(MdfClass newForApplication)
  {
    MdfClass oldForApplication = forApplication;
    forApplication = newForApplication;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__FOR_APPLICATION, oldForApplication, forApplication));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getShortName()
  {
    return shortName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setShortName(String newShortName)
  {
    String oldShortName = shortName;
    shortName = newShortName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__SHORT_NAME, oldShortName, shortName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getT24Name()
  {
    return t24Name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setT24Name(String newT24Name)
  {
    String oldT24Name = t24Name;
    t24Name = newT24Name;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__T24_NAME, oldT24Name, t24Name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMetamodelVersion()
  {
    return metamodelVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetamodelVersion(String newMetamodelVersion)
  {
    String oldMetamodelVersion = metamodelVersion;
    metamodelVersion = newMetamodelVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getGroup()
  {
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGroup(String newGroup)
  {
    String oldGroup = group;
    group = newGroup;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__GROUP, oldGroup, group));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getNumberOfAuthorisers()
  {
    return numberOfAuthorisers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfAuthorisers(Integer newNumberOfAuthorisers)
  {
    Integer oldNumberOfAuthorisers = numberOfAuthorisers;
    numberOfAuthorisers = newNumberOfAuthorisers;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__NUMBER_OF_AUTHORISERS, oldNumberOfAuthorisers, numberOfAuthorisers));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDescription(Translations newDescription, NotificationChain msgs)
  {
    Translations oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__DESCRIPTION, oldDescription, newDescription);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(Translations newDescription)
  {
    if (newDescription != description)
    {
      NotificationChain msgs = null;
      if (description != null)
        msgs = ((InternalEObject)description).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__DESCRIPTION, null, msgs);
      if (newDescription != null)
        msgs = ((InternalEObject)newDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__DESCRIPTION, null, msgs);
      msgs = basicSetDescription(newDescription, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__DESCRIPTION, newDescription, newDescription));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getExceptionProcessing()
  {
    return exceptionProcessing;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExceptionProcessing(Integer newExceptionProcessing)
  {
    Integer oldExceptionProcessing = exceptionProcessing;
    exceptionProcessing = newExceptionProcessing;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__EXCEPTION_PROCESSING, oldExceptionProcessing, exceptionProcessing));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getInterfaceException()
  {
    return interfaceException;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInterfaceException(Integer newInterfaceException)
  {
    Integer oldInterfaceException = interfaceException;
    interfaceException = newInterfaceException;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__INTERFACE_EXCEPTION, oldInterfaceException, interfaceException));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BusinessDayControl getBusinessDayControl()
  {
    return businessDayControl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBusinessDayControl(BusinessDayControl newBusinessDayControl)
  {
    BusinessDayControl oldBusinessDayControl = businessDayControl;
    businessDayControl = newBusinessDayControl == null ? BUSINESS_DAY_CONTROL_EDEFAULT : newBusinessDayControl;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__BUSINESS_DAY_CONTROL, oldBusinessDayControl, businessDayControl));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getKeySequence()
  {
    if (keySequence == null)
    {
      keySequence = new EDataTypeEList<String>(String.class, this, VersionDSLPackage.VERSION__KEY_SEQUENCE);
    }
    return keySequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getOtherCompanyAccess()
  {
    return otherCompanyAccess;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOtherCompanyAccess(YesNo newOtherCompanyAccess)
  {
    YesNo oldOtherCompanyAccess = otherCompanyAccess;
    otherCompanyAccess = newOtherCompanyAccess == null ? OTHER_COMPANY_ACCESS_EDEFAULT : newOtherCompanyAccess;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__OTHER_COMPANY_ACCESS, oldOtherCompanyAccess, otherCompanyAccess));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getAutoCompanyChange()
  {
    return autoCompanyChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAutoCompanyChange(YesNo newAutoCompanyChange)
  {
    YesNo oldAutoCompanyChange = autoCompanyChange;
    autoCompanyChange = newAutoCompanyChange == null ? AUTO_COMPANY_CHANGE_EDEFAULT : newAutoCompanyChange;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__AUTO_COMPANY_CHANGE, oldAutoCompanyChange, autoCompanyChange));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getOverrideApproval()
  {
    return overrideApproval;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOverrideApproval(YesNo newOverrideApproval)
  {
    YesNo oldOverrideApproval = overrideApproval;
    overrideApproval = newOverrideApproval == null ? OVERRIDE_APPROVAL_EDEFAULT : newOverrideApproval;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__OVERRIDE_APPROVAL, oldOverrideApproval, overrideApproval));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<DealSlip> getDealSlipFormats()
  {
    if (dealSlipFormats == null)
    {
      dealSlipFormats = new EObjectContainmentEList<DealSlip>(DealSlip.class, this, VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS);
    }
    return dealSlipFormats;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DealSlipTrigger getDealSlipTrigger()
  {
    return dealSlipTrigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDealSlipTrigger(DealSlipTrigger newDealSlipTrigger)
  {
    DealSlipTrigger oldDealSlipTrigger = dealSlipTrigger;
    dealSlipTrigger = newDealSlipTrigger == null ? DEAL_SLIP_TRIGGER_EDEFAULT : newDealSlipTrigger;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__DEAL_SLIP_TRIGGER, oldDealSlipTrigger, dealSlipTrigger));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDealSlipStyleSheet()
  {
    return dealSlipStyleSheet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDealSlipStyleSheet(String newDealSlipStyleSheet)
  {
    String oldDealSlipStyleSheet = dealSlipStyleSheet;
    dealSlipStyleSheet = newDealSlipStyleSheet;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__DEAL_SLIP_STYLE_SHEET, oldDealSlipStyleSheet, dealSlipStyleSheet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRecordsPerPage()
  {
    return recordsPerPage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRecordsPerPage(String newRecordsPerPage)
  {
    String oldRecordsPerPage = recordsPerPage;
    recordsPerPage = newRecordsPerPage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__RECORDS_PER_PAGE, oldRecordsPerPage, recordsPerPage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFieldsPerLine()
  {
    return fieldsPerLine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFieldsPerLine(String newFieldsPerLine)
  {
    String oldFieldsPerLine = fieldsPerLine;
    fieldsPerLine = newFieldsPerLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__FIELDS_PER_LINE, oldFieldsPerLine, fieldsPerLine));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInitialCursorPosition()
  {
    return initialCursorPosition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInitialCursorPosition(String newInitialCursorPosition)
  {
    String oldInitialCursorPosition = initialCursorPosition;
    initialCursorPosition = newInitialCursorPosition;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__INITIAL_CURSOR_POSITION, oldInitialCursorPosition, initialCursorPosition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getBrowserToolbar()
  {
    return browserToolbar;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBrowserToolbar(String newBrowserToolbar)
  {
    String oldBrowserToolbar = browserToolbar;
    browserToolbar = newBrowserToolbar;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__BROWSER_TOOLBAR, oldBrowserToolbar, browserToolbar));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getLanguageLocale()
  {
    if (languageLocale == null)
    {
      languageLocale = new EDataTypeEList<String>(String.class, this, VersionDSLPackage.VERSION__LANGUAGE_LOCALE);
    }
    return languageLocale;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getHeader1()
  {
    return header1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHeader1(Translations newHeader1, NotificationChain msgs)
  {
    Translations oldHeader1 = header1;
    header1 = newHeader1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER1, oldHeader1, newHeader1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHeader1(Translations newHeader1)
  {
    if (newHeader1 != header1)
    {
      NotificationChain msgs = null;
      if (header1 != null)
        msgs = ((InternalEObject)header1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER1, null, msgs);
      if (newHeader1 != null)
        msgs = ((InternalEObject)newHeader1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER1, null, msgs);
      msgs = basicSetHeader1(newHeader1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER1, newHeader1, newHeader1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getHeader2()
  {
    return header2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHeader2(Translations newHeader2, NotificationChain msgs)
  {
    Translations oldHeader2 = header2;
    header2 = newHeader2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER2, oldHeader2, newHeader2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHeader2(Translations newHeader2)
  {
    if (newHeader2 != header2)
    {
      NotificationChain msgs = null;
      if (header2 != null)
        msgs = ((InternalEObject)header2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER2, null, msgs);
      if (newHeader2 != null)
        msgs = ((InternalEObject)newHeader2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER2, null, msgs);
      msgs = basicSetHeader2(newHeader2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER2, newHeader2, newHeader2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getHeader()
  {
    return header;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHeader(Translations newHeader, NotificationChain msgs)
  {
    Translations oldHeader = header;
    header = newHeader;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER, oldHeader, newHeader);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHeader(Translations newHeader)
  {
    if (newHeader != header)
    {
      NotificationChain msgs = null;
      if (header != null)
        msgs = ((InternalEObject)header).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER, null, msgs);
      if (newHeader != null)
        msgs = ((InternalEObject)newHeader).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__HEADER, null, msgs);
      msgs = basicSetHeader(newHeader, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__HEADER, newHeader, newHeader));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getFooter()
  {
    return footer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFooter(Translations newFooter, NotificationChain msgs)
  {
    Translations oldFooter = footer;
    footer = newFooter;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__FOOTER, oldFooter, newFooter);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFooter(Translations newFooter)
  {
    if (newFooter != footer)
    {
      NotificationChain msgs = null;
      if (footer != null)
        msgs = ((InternalEObject)footer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__FOOTER, null, msgs);
      if (newFooter != null)
        msgs = ((InternalEObject)newFooter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.VERSION__FOOTER, null, msgs);
      msgs = basicSetFooter(newFooter, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__FOOTER, newFooter, newFooter));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version getNextVersion()
  {
    if (nextVersion != null && nextVersion.eIsProxy())
    {
      InternalEObject oldNextVersion = (InternalEObject)nextVersion;
      nextVersion = (Version)eResolveProxy(oldNextVersion);
      if (nextVersion != oldNextVersion)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersionDSLPackage.VERSION__NEXT_VERSION, oldNextVersion, nextVersion));
      }
    }
    return nextVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version basicGetNextVersion()
  {
    return nextVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNextVersion(Version newNextVersion)
  {
    Version oldNextVersion = nextVersion;
    nextVersion = newNextVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__NEXT_VERSION, oldNextVersion, nextVersion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Function getNextVersionFunction()
  {
    return nextVersionFunction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNextVersionFunction(Function newNextVersionFunction)
  {
    Function oldNextVersionFunction = nextVersionFunction;
    nextVersionFunction = newNextVersionFunction == null ? NEXT_VERSION_FUNCTION_EDEFAULT : newNextVersionFunction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__NEXT_VERSION_FUNCTION, oldNextVersionFunction, nextVersionFunction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNextTransactionReference()
  {
    return nextTransactionReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNextTransactionReference(String newNextTransactionReference)
  {
    String oldNextTransactionReference = nextTransactionReference;
    nextTransactionReference = newNextTransactionReference;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__NEXT_TRANSACTION_REFERENCE, oldNextTransactionReference, nextTransactionReference));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Version> getAssociatedVersions()
  {
    if (associatedVersions == null)
    {
      associatedVersions = new EObjectResolvingEList<Version>(Version.class, this, VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS);
    }
    return associatedVersions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getIncludeVersionControl()
  {
    return includeVersionControl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIncludeVersionControl(YesNo newIncludeVersionControl)
  {
    YesNo oldIncludeVersionControl = includeVersionControl;
    includeVersionControl = newIncludeVersionControl == null ? INCLUDE_VERSION_CONTROL_EDEFAULT : newIncludeVersionControl;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__INCLUDE_VERSION_CONTROL, oldIncludeVersionControl, includeVersionControl));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getAuthorizationRoutines()
  {
    if (authorizationRoutines == null)
    {
      authorizationRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES);
    }
    return authorizationRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getAuthorizationRoutinesAfterCommit()
  {
    if (authorizationRoutinesAfterCommit == null)
    {
      authorizationRoutinesAfterCommit = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT);
    }
    return authorizationRoutinesAfterCommit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getInputRoutines()
  {
    if (inputRoutines == null)
    {
      inputRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__INPUT_ROUTINES);
    }
    return inputRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getInputRoutinesAfterCommit()
  {
    if (inputRoutinesAfterCommit == null)
    {
      inputRoutinesAfterCommit = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT);
    }
    return inputRoutinesAfterCommit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getKeyValidationRoutines()
  {
    if (keyValidationRoutines == null)
    {
      keyValidationRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES);
    }
    return keyValidationRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getPreProcessValidationRoutines()
  {
    if (preProcessValidationRoutines == null)
    {
      preProcessValidationRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES);
    }
    return preProcessValidationRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getWebValidationRoutines()
  {
    if (webValidationRoutines == null)
    {
      webValidationRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES);
    }
    return webValidationRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version getConfirmVersion()
  {
    if (confirmVersion != null && confirmVersion.eIsProxy())
    {
      InternalEObject oldConfirmVersion = (InternalEObject)confirmVersion;
      confirmVersion = (Version)eResolveProxy(oldConfirmVersion);
      if (confirmVersion != oldConfirmVersion)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersionDSLPackage.VERSION__CONFIRM_VERSION, oldConfirmVersion, confirmVersion));
      }
    }
    return confirmVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version basicGetConfirmVersion()
  {
    return confirmVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConfirmVersion(Version newConfirmVersion)
  {
    Version oldConfirmVersion = confirmVersion;
    confirmVersion = newConfirmVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__CONFIRM_VERSION, oldConfirmVersion, confirmVersion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version getPreviewVersion()
  {
    if (previewVersion != null && previewVersion.eIsProxy())
    {
      InternalEObject oldPreviewVersion = (InternalEObject)previewVersion;
      previewVersion = (Version)eResolveProxy(oldPreviewVersion);
      if (previewVersion != oldPreviewVersion)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, VersionDSLPackage.VERSION__PREVIEW_VERSION, oldPreviewVersion, previewVersion));
      }
    }
    return previewVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version basicGetPreviewVersion()
  {
    return previewVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPreviewVersion(Version newPreviewVersion)
  {
    Version oldPreviewVersion = previewVersion;
    previewVersion = newPreviewVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__PREVIEW_VERSION, oldPreviewVersion, previewVersion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getChallengeResponse()
  {
    return challengeResponse;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setChallengeResponse(String newChallengeResponse)
  {
    String oldChallengeResponse = challengeResponse;
    challengeResponse = newChallengeResponse;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__CHALLENGE_RESPONSE, oldChallengeResponse, challengeResponse));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getAttributes()
  {
    if (attributes == null)
    {
      attributes = new EDataTypeEList<String>(String.class, this, VersionDSLPackage.VERSION__ATTRIBUTES);
    }
    return attributes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getPublishWebService()
  {
    return publishWebService;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPublishWebService(YesNo newPublishWebService)
  {
    YesNo oldPublishWebService = publishWebService;
    publishWebService = newPublishWebService == null ? PUBLISH_WEB_SERVICE_EDEFAULT : newPublishWebService;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__PUBLISH_WEB_SERVICE, oldPublishWebService, publishWebService));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getWebServiceActivity()
  {
    return webServiceActivity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebServiceActivity(String newWebServiceActivity)
  {
    String oldWebServiceActivity = webServiceActivity;
    webServiceActivity = newWebServiceActivity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__WEB_SERVICE_ACTIVITY, oldWebServiceActivity, webServiceActivity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Function getWebServiceFunction()
  {
    return webServiceFunction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebServiceFunction(Function newWebServiceFunction)
  {
    Function oldWebServiceFunction = webServiceFunction;
    webServiceFunction = newWebServiceFunction == null ? WEB_SERVICE_FUNCTION_EDEFAULT : newWebServiceFunction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__WEB_SERVICE_FUNCTION, oldWebServiceFunction, webServiceFunction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getWebServiceDescription()
  {
    return webServiceDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebServiceDescription(String newWebServiceDescription)
  {
    String oldWebServiceDescription = webServiceDescription;
    webServiceDescription = newWebServiceDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__WEB_SERVICE_DESCRIPTION, oldWebServiceDescription, webServiceDescription));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getWebServiceNames()
  {
    if (webServiceNames == null)
    {
      webServiceNames = new EDataTypeEList<String>(String.class, this, VersionDSLPackage.VERSION__WEB_SERVICE_NAMES);
    }
    return webServiceNames;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getGenerateIFP()
  {
    return generateIFP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGenerateIFP(YesNo newGenerateIFP)
  {
    YesNo oldGenerateIFP = generateIFP;
    generateIFP = newGenerateIFP == null ? GENERATE_IFP_EDEFAULT : newGenerateIFP;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__GENERATE_IFP, oldGenerateIFP, generateIFP));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AssociatedVersionsPresentationPattern getAssociatedVersionsPresentationPattern()
  {
    return associatedVersionsPresentationPattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssociatedVersionsPresentationPattern(AssociatedVersionsPresentationPattern newAssociatedVersionsPresentationPattern)
  {
    AssociatedVersionsPresentationPattern oldAssociatedVersionsPresentationPattern = associatedVersionsPresentationPattern;
    associatedVersionsPresentationPattern = newAssociatedVersionsPresentationPattern == null ? ASSOCIATED_VERSIONS_PRESENTATION_PATTERN_EDEFAULT : newAssociatedVersionsPresentationPattern;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN, oldAssociatedVersionsPresentationPattern, associatedVersionsPresentationPattern));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldsLayoutPattern getFieldsLayoutPattern()
  {
    return fieldsLayoutPattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFieldsLayoutPattern(FieldsLayoutPattern newFieldsLayoutPattern)
  {
    FieldsLayoutPattern oldFieldsLayoutPattern = fieldsLayoutPattern;
    fieldsLayoutPattern = newFieldsLayoutPattern == null ? FIELDS_LAYOUT_PATTERN_EDEFAULT : newFieldsLayoutPattern;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.VERSION__FIELDS_LAYOUT_PATTERN, oldFieldsLayoutPattern, fieldsLayoutPattern));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Field> getFields()
  {
    if (fields == null)
    {
      fields = new EObjectContainmentEList<Field>(Field.class, this, VersionDSLPackage.VERSION__FIELDS);
    }
    return fields;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case VersionDSLPackage.VERSION__DESCRIPTION:
        return basicSetDescription(null, msgs);
      case VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS:
        return ((InternalEList<?>)getDealSlipFormats()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__HEADER1:
        return basicSetHeader1(null, msgs);
      case VersionDSLPackage.VERSION__HEADER2:
        return basicSetHeader2(null, msgs);
      case VersionDSLPackage.VERSION__HEADER:
        return basicSetHeader(null, msgs);
      case VersionDSLPackage.VERSION__FOOTER:
        return basicSetFooter(null, msgs);
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES:
        return ((InternalEList<?>)getAuthorizationRoutines()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT:
        return ((InternalEList<?>)getAuthorizationRoutinesAfterCommit()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__INPUT_ROUTINES:
        return ((InternalEList<?>)getInputRoutines()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT:
        return ((InternalEList<?>)getInputRoutinesAfterCommit()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES:
        return ((InternalEList<?>)getKeyValidationRoutines()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES:
        return ((InternalEList<?>)getPreProcessValidationRoutines()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES:
        return ((InternalEList<?>)getWebValidationRoutines()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.VERSION__FIELDS:
        return ((InternalEList<?>)getFields()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case VersionDSLPackage.VERSION__FOR_APPLICATION:
        if (resolve) return getForApplication();
        return basicGetForApplication();
      case VersionDSLPackage.VERSION__SHORT_NAME:
        return getShortName();
      case VersionDSLPackage.VERSION__T24_NAME:
        return getT24Name();
      case VersionDSLPackage.VERSION__METAMODEL_VERSION:
        return getMetamodelVersion();
      case VersionDSLPackage.VERSION__GROUP:
        return getGroup();
      case VersionDSLPackage.VERSION__NUMBER_OF_AUTHORISERS:
        return getNumberOfAuthorisers();
      case VersionDSLPackage.VERSION__DESCRIPTION:
        return getDescription();
      case VersionDSLPackage.VERSION__EXCEPTION_PROCESSING:
        return getExceptionProcessing();
      case VersionDSLPackage.VERSION__INTERFACE_EXCEPTION:
        return getInterfaceException();
      case VersionDSLPackage.VERSION__BUSINESS_DAY_CONTROL:
        return getBusinessDayControl();
      case VersionDSLPackage.VERSION__KEY_SEQUENCE:
        return getKeySequence();
      case VersionDSLPackage.VERSION__OTHER_COMPANY_ACCESS:
        return getOtherCompanyAccess();
      case VersionDSLPackage.VERSION__AUTO_COMPANY_CHANGE:
        return getAutoCompanyChange();
      case VersionDSLPackage.VERSION__OVERRIDE_APPROVAL:
        return getOverrideApproval();
      case VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS:
        return getDealSlipFormats();
      case VersionDSLPackage.VERSION__DEAL_SLIP_TRIGGER:
        return getDealSlipTrigger();
      case VersionDSLPackage.VERSION__DEAL_SLIP_STYLE_SHEET:
        return getDealSlipStyleSheet();
      case VersionDSLPackage.VERSION__RECORDS_PER_PAGE:
        return getRecordsPerPage();
      case VersionDSLPackage.VERSION__FIELDS_PER_LINE:
        return getFieldsPerLine();
      case VersionDSLPackage.VERSION__INITIAL_CURSOR_POSITION:
        return getInitialCursorPosition();
      case VersionDSLPackage.VERSION__BROWSER_TOOLBAR:
        return getBrowserToolbar();
      case VersionDSLPackage.VERSION__LANGUAGE_LOCALE:
        return getLanguageLocale();
      case VersionDSLPackage.VERSION__HEADER1:
        return getHeader1();
      case VersionDSLPackage.VERSION__HEADER2:
        return getHeader2();
      case VersionDSLPackage.VERSION__HEADER:
        return getHeader();
      case VersionDSLPackage.VERSION__FOOTER:
        return getFooter();
      case VersionDSLPackage.VERSION__NEXT_VERSION:
        if (resolve) return getNextVersion();
        return basicGetNextVersion();
      case VersionDSLPackage.VERSION__NEXT_VERSION_FUNCTION:
        return getNextVersionFunction();
      case VersionDSLPackage.VERSION__NEXT_TRANSACTION_REFERENCE:
        return getNextTransactionReference();
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS:
        return getAssociatedVersions();
      case VersionDSLPackage.VERSION__INCLUDE_VERSION_CONTROL:
        return getIncludeVersionControl();
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES:
        return getAuthorizationRoutines();
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT:
        return getAuthorizationRoutinesAfterCommit();
      case VersionDSLPackage.VERSION__INPUT_ROUTINES:
        return getInputRoutines();
      case VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT:
        return getInputRoutinesAfterCommit();
      case VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES:
        return getKeyValidationRoutines();
      case VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES:
        return getPreProcessValidationRoutines();
      case VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES:
        return getWebValidationRoutines();
      case VersionDSLPackage.VERSION__CONFIRM_VERSION:
        if (resolve) return getConfirmVersion();
        return basicGetConfirmVersion();
      case VersionDSLPackage.VERSION__PREVIEW_VERSION:
        if (resolve) return getPreviewVersion();
        return basicGetPreviewVersion();
      case VersionDSLPackage.VERSION__CHALLENGE_RESPONSE:
        return getChallengeResponse();
      case VersionDSLPackage.VERSION__ATTRIBUTES:
        return getAttributes();
      case VersionDSLPackage.VERSION__PUBLISH_WEB_SERVICE:
        return getPublishWebService();
      case VersionDSLPackage.VERSION__WEB_SERVICE_ACTIVITY:
        return getWebServiceActivity();
      case VersionDSLPackage.VERSION__WEB_SERVICE_FUNCTION:
        return getWebServiceFunction();
      case VersionDSLPackage.VERSION__WEB_SERVICE_DESCRIPTION:
        return getWebServiceDescription();
      case VersionDSLPackage.VERSION__WEB_SERVICE_NAMES:
        return getWebServiceNames();
      case VersionDSLPackage.VERSION__GENERATE_IFP:
        return getGenerateIFP();
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        return getAssociatedVersionsPresentationPattern();
      case VersionDSLPackage.VERSION__FIELDS_LAYOUT_PATTERN:
        return getFieldsLayoutPattern();
      case VersionDSLPackage.VERSION__FIELDS:
        return getFields();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case VersionDSLPackage.VERSION__FOR_APPLICATION:
        setForApplication((MdfClass)newValue);
        return;
      case VersionDSLPackage.VERSION__SHORT_NAME:
        setShortName((String)newValue);
        return;
      case VersionDSLPackage.VERSION__T24_NAME:
        setT24Name((String)newValue);
        return;
      case VersionDSLPackage.VERSION__METAMODEL_VERSION:
        setMetamodelVersion((String)newValue);
        return;
      case VersionDSLPackage.VERSION__GROUP:
        setGroup((String)newValue);
        return;
      case VersionDSLPackage.VERSION__NUMBER_OF_AUTHORISERS:
        setNumberOfAuthorisers((Integer)newValue);
        return;
      case VersionDSLPackage.VERSION__DESCRIPTION:
        setDescription((Translations)newValue);
        return;
      case VersionDSLPackage.VERSION__EXCEPTION_PROCESSING:
        setExceptionProcessing((Integer)newValue);
        return;
      case VersionDSLPackage.VERSION__INTERFACE_EXCEPTION:
        setInterfaceException((Integer)newValue);
        return;
      case VersionDSLPackage.VERSION__BUSINESS_DAY_CONTROL:
        setBusinessDayControl((BusinessDayControl)newValue);
        return;
      case VersionDSLPackage.VERSION__KEY_SEQUENCE:
        getKeySequence().clear();
        getKeySequence().addAll((Collection<? extends String>)newValue);
        return;
      case VersionDSLPackage.VERSION__OTHER_COMPANY_ACCESS:
        setOtherCompanyAccess((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__AUTO_COMPANY_CHANGE:
        setAutoCompanyChange((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__OVERRIDE_APPROVAL:
        setOverrideApproval((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS:
        getDealSlipFormats().clear();
        getDealSlipFormats().addAll((Collection<? extends DealSlip>)newValue);
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_TRIGGER:
        setDealSlipTrigger((DealSlipTrigger)newValue);
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_STYLE_SHEET:
        setDealSlipStyleSheet((String)newValue);
        return;
      case VersionDSLPackage.VERSION__RECORDS_PER_PAGE:
        setRecordsPerPage((String)newValue);
        return;
      case VersionDSLPackage.VERSION__FIELDS_PER_LINE:
        setFieldsPerLine((String)newValue);
        return;
      case VersionDSLPackage.VERSION__INITIAL_CURSOR_POSITION:
        setInitialCursorPosition((String)newValue);
        return;
      case VersionDSLPackage.VERSION__BROWSER_TOOLBAR:
        setBrowserToolbar((String)newValue);
        return;
      case VersionDSLPackage.VERSION__LANGUAGE_LOCALE:
        getLanguageLocale().clear();
        getLanguageLocale().addAll((Collection<? extends String>)newValue);
        return;
      case VersionDSLPackage.VERSION__HEADER1:
        setHeader1((Translations)newValue);
        return;
      case VersionDSLPackage.VERSION__HEADER2:
        setHeader2((Translations)newValue);
        return;
      case VersionDSLPackage.VERSION__HEADER:
        setHeader((Translations)newValue);
        return;
      case VersionDSLPackage.VERSION__FOOTER:
        setFooter((Translations)newValue);
        return;
      case VersionDSLPackage.VERSION__NEXT_VERSION:
        setNextVersion((Version)newValue);
        return;
      case VersionDSLPackage.VERSION__NEXT_VERSION_FUNCTION:
        setNextVersionFunction((Function)newValue);
        return;
      case VersionDSLPackage.VERSION__NEXT_TRANSACTION_REFERENCE:
        setNextTransactionReference((String)newValue);
        return;
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS:
        getAssociatedVersions().clear();
        getAssociatedVersions().addAll((Collection<? extends Version>)newValue);
        return;
      case VersionDSLPackage.VERSION__INCLUDE_VERSION_CONTROL:
        setIncludeVersionControl((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES:
        getAuthorizationRoutines().clear();
        getAuthorizationRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT:
        getAuthorizationRoutinesAfterCommit().clear();
        getAuthorizationRoutinesAfterCommit().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__INPUT_ROUTINES:
        getInputRoutines().clear();
        getInputRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT:
        getInputRoutinesAfterCommit().clear();
        getInputRoutinesAfterCommit().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES:
        getKeyValidationRoutines().clear();
        getKeyValidationRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES:
        getPreProcessValidationRoutines().clear();
        getPreProcessValidationRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES:
        getWebValidationRoutines().clear();
        getWebValidationRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.VERSION__CONFIRM_VERSION:
        setConfirmVersion((Version)newValue);
        return;
      case VersionDSLPackage.VERSION__PREVIEW_VERSION:
        setPreviewVersion((Version)newValue);
        return;
      case VersionDSLPackage.VERSION__CHALLENGE_RESPONSE:
        setChallengeResponse((String)newValue);
        return;
      case VersionDSLPackage.VERSION__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends String>)newValue);
        return;
      case VersionDSLPackage.VERSION__PUBLISH_WEB_SERVICE:
        setPublishWebService((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_ACTIVITY:
        setWebServiceActivity((String)newValue);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_FUNCTION:
        setWebServiceFunction((Function)newValue);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_DESCRIPTION:
        setWebServiceDescription((String)newValue);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_NAMES:
        getWebServiceNames().clear();
        getWebServiceNames().addAll((Collection<? extends String>)newValue);
        return;
      case VersionDSLPackage.VERSION__GENERATE_IFP:
        setGenerateIFP((YesNo)newValue);
        return;
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        setAssociatedVersionsPresentationPattern((AssociatedVersionsPresentationPattern)newValue);
        return;
      case VersionDSLPackage.VERSION__FIELDS_LAYOUT_PATTERN:
        setFieldsLayoutPattern((FieldsLayoutPattern)newValue);
        return;
      case VersionDSLPackage.VERSION__FIELDS:
        getFields().clear();
        getFields().addAll((Collection<? extends Field>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case VersionDSLPackage.VERSION__FOR_APPLICATION:
        setForApplication((MdfClass)null);
        return;
      case VersionDSLPackage.VERSION__SHORT_NAME:
        setShortName(SHORT_NAME_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__T24_NAME:
        setT24Name(T24_NAME_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__METAMODEL_VERSION:
        setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__GROUP:
        setGroup(GROUP_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__NUMBER_OF_AUTHORISERS:
        setNumberOfAuthorisers(NUMBER_OF_AUTHORISERS_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__DESCRIPTION:
        setDescription((Translations)null);
        return;
      case VersionDSLPackage.VERSION__EXCEPTION_PROCESSING:
        setExceptionProcessing(EXCEPTION_PROCESSING_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__INTERFACE_EXCEPTION:
        setInterfaceException(INTERFACE_EXCEPTION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__BUSINESS_DAY_CONTROL:
        setBusinessDayControl(BUSINESS_DAY_CONTROL_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__KEY_SEQUENCE:
        getKeySequence().clear();
        return;
      case VersionDSLPackage.VERSION__OTHER_COMPANY_ACCESS:
        setOtherCompanyAccess(OTHER_COMPANY_ACCESS_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__AUTO_COMPANY_CHANGE:
        setAutoCompanyChange(AUTO_COMPANY_CHANGE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__OVERRIDE_APPROVAL:
        setOverrideApproval(OVERRIDE_APPROVAL_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS:
        getDealSlipFormats().clear();
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_TRIGGER:
        setDealSlipTrigger(DEAL_SLIP_TRIGGER_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__DEAL_SLIP_STYLE_SHEET:
        setDealSlipStyleSheet(DEAL_SLIP_STYLE_SHEET_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__RECORDS_PER_PAGE:
        setRecordsPerPage(RECORDS_PER_PAGE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__FIELDS_PER_LINE:
        setFieldsPerLine(FIELDS_PER_LINE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__INITIAL_CURSOR_POSITION:
        setInitialCursorPosition(INITIAL_CURSOR_POSITION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__BROWSER_TOOLBAR:
        setBrowserToolbar(BROWSER_TOOLBAR_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__LANGUAGE_LOCALE:
        getLanguageLocale().clear();
        return;
      case VersionDSLPackage.VERSION__HEADER1:
        setHeader1((Translations)null);
        return;
      case VersionDSLPackage.VERSION__HEADER2:
        setHeader2((Translations)null);
        return;
      case VersionDSLPackage.VERSION__HEADER:
        setHeader((Translations)null);
        return;
      case VersionDSLPackage.VERSION__FOOTER:
        setFooter((Translations)null);
        return;
      case VersionDSLPackage.VERSION__NEXT_VERSION:
        setNextVersion((Version)null);
        return;
      case VersionDSLPackage.VERSION__NEXT_VERSION_FUNCTION:
        setNextVersionFunction(NEXT_VERSION_FUNCTION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__NEXT_TRANSACTION_REFERENCE:
        setNextTransactionReference(NEXT_TRANSACTION_REFERENCE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS:
        getAssociatedVersions().clear();
        return;
      case VersionDSLPackage.VERSION__INCLUDE_VERSION_CONTROL:
        setIncludeVersionControl(INCLUDE_VERSION_CONTROL_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES:
        getAuthorizationRoutines().clear();
        return;
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT:
        getAuthorizationRoutinesAfterCommit().clear();
        return;
      case VersionDSLPackage.VERSION__INPUT_ROUTINES:
        getInputRoutines().clear();
        return;
      case VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT:
        getInputRoutinesAfterCommit().clear();
        return;
      case VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES:
        getKeyValidationRoutines().clear();
        return;
      case VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES:
        getPreProcessValidationRoutines().clear();
        return;
      case VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES:
        getWebValidationRoutines().clear();
        return;
      case VersionDSLPackage.VERSION__CONFIRM_VERSION:
        setConfirmVersion((Version)null);
        return;
      case VersionDSLPackage.VERSION__PREVIEW_VERSION:
        setPreviewVersion((Version)null);
        return;
      case VersionDSLPackage.VERSION__CHALLENGE_RESPONSE:
        setChallengeResponse(CHALLENGE_RESPONSE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__ATTRIBUTES:
        getAttributes().clear();
        return;
      case VersionDSLPackage.VERSION__PUBLISH_WEB_SERVICE:
        setPublishWebService(PUBLISH_WEB_SERVICE_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_ACTIVITY:
        setWebServiceActivity(WEB_SERVICE_ACTIVITY_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_FUNCTION:
        setWebServiceFunction(WEB_SERVICE_FUNCTION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_DESCRIPTION:
        setWebServiceDescription(WEB_SERVICE_DESCRIPTION_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__WEB_SERVICE_NAMES:
        getWebServiceNames().clear();
        return;
      case VersionDSLPackage.VERSION__GENERATE_IFP:
        setGenerateIFP(GENERATE_IFP_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        setAssociatedVersionsPresentationPattern(ASSOCIATED_VERSIONS_PRESENTATION_PATTERN_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__FIELDS_LAYOUT_PATTERN:
        setFieldsLayoutPattern(FIELDS_LAYOUT_PATTERN_EDEFAULT);
        return;
      case VersionDSLPackage.VERSION__FIELDS:
        getFields().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case VersionDSLPackage.VERSION__FOR_APPLICATION:
        return forApplication != null;
      case VersionDSLPackage.VERSION__SHORT_NAME:
        return SHORT_NAME_EDEFAULT == null ? shortName != null : !SHORT_NAME_EDEFAULT.equals(shortName);
      case VersionDSLPackage.VERSION__T24_NAME:
        return T24_NAME_EDEFAULT == null ? t24Name != null : !T24_NAME_EDEFAULT.equals(t24Name);
      case VersionDSLPackage.VERSION__METAMODEL_VERSION:
        return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
      case VersionDSLPackage.VERSION__GROUP:
        return GROUP_EDEFAULT == null ? group != null : !GROUP_EDEFAULT.equals(group);
      case VersionDSLPackage.VERSION__NUMBER_OF_AUTHORISERS:
        return NUMBER_OF_AUTHORISERS_EDEFAULT == null ? numberOfAuthorisers != null : !NUMBER_OF_AUTHORISERS_EDEFAULT.equals(numberOfAuthorisers);
      case VersionDSLPackage.VERSION__DESCRIPTION:
        return description != null;
      case VersionDSLPackage.VERSION__EXCEPTION_PROCESSING:
        return EXCEPTION_PROCESSING_EDEFAULT == null ? exceptionProcessing != null : !EXCEPTION_PROCESSING_EDEFAULT.equals(exceptionProcessing);
      case VersionDSLPackage.VERSION__INTERFACE_EXCEPTION:
        return INTERFACE_EXCEPTION_EDEFAULT == null ? interfaceException != null : !INTERFACE_EXCEPTION_EDEFAULT.equals(interfaceException);
      case VersionDSLPackage.VERSION__BUSINESS_DAY_CONTROL:
        return businessDayControl != BUSINESS_DAY_CONTROL_EDEFAULT;
      case VersionDSLPackage.VERSION__KEY_SEQUENCE:
        return keySequence != null && !keySequence.isEmpty();
      case VersionDSLPackage.VERSION__OTHER_COMPANY_ACCESS:
        return otherCompanyAccess != OTHER_COMPANY_ACCESS_EDEFAULT;
      case VersionDSLPackage.VERSION__AUTO_COMPANY_CHANGE:
        return autoCompanyChange != AUTO_COMPANY_CHANGE_EDEFAULT;
      case VersionDSLPackage.VERSION__OVERRIDE_APPROVAL:
        return overrideApproval != OVERRIDE_APPROVAL_EDEFAULT;
      case VersionDSLPackage.VERSION__DEAL_SLIP_FORMATS:
        return dealSlipFormats != null && !dealSlipFormats.isEmpty();
      case VersionDSLPackage.VERSION__DEAL_SLIP_TRIGGER:
        return dealSlipTrigger != DEAL_SLIP_TRIGGER_EDEFAULT;
      case VersionDSLPackage.VERSION__DEAL_SLIP_STYLE_SHEET:
        return DEAL_SLIP_STYLE_SHEET_EDEFAULT == null ? dealSlipStyleSheet != null : !DEAL_SLIP_STYLE_SHEET_EDEFAULT.equals(dealSlipStyleSheet);
      case VersionDSLPackage.VERSION__RECORDS_PER_PAGE:
        return RECORDS_PER_PAGE_EDEFAULT == null ? recordsPerPage != null : !RECORDS_PER_PAGE_EDEFAULT.equals(recordsPerPage);
      case VersionDSLPackage.VERSION__FIELDS_PER_LINE:
        return FIELDS_PER_LINE_EDEFAULT == null ? fieldsPerLine != null : !FIELDS_PER_LINE_EDEFAULT.equals(fieldsPerLine);
      case VersionDSLPackage.VERSION__INITIAL_CURSOR_POSITION:
        return INITIAL_CURSOR_POSITION_EDEFAULT == null ? initialCursorPosition != null : !INITIAL_CURSOR_POSITION_EDEFAULT.equals(initialCursorPosition);
      case VersionDSLPackage.VERSION__BROWSER_TOOLBAR:
        return BROWSER_TOOLBAR_EDEFAULT == null ? browserToolbar != null : !BROWSER_TOOLBAR_EDEFAULT.equals(browserToolbar);
      case VersionDSLPackage.VERSION__LANGUAGE_LOCALE:
        return languageLocale != null && !languageLocale.isEmpty();
      case VersionDSLPackage.VERSION__HEADER1:
        return header1 != null;
      case VersionDSLPackage.VERSION__HEADER2:
        return header2 != null;
      case VersionDSLPackage.VERSION__HEADER:
        return header != null;
      case VersionDSLPackage.VERSION__FOOTER:
        return footer != null;
      case VersionDSLPackage.VERSION__NEXT_VERSION:
        return nextVersion != null;
      case VersionDSLPackage.VERSION__NEXT_VERSION_FUNCTION:
        return nextVersionFunction != NEXT_VERSION_FUNCTION_EDEFAULT;
      case VersionDSLPackage.VERSION__NEXT_TRANSACTION_REFERENCE:
        return NEXT_TRANSACTION_REFERENCE_EDEFAULT == null ? nextTransactionReference != null : !NEXT_TRANSACTION_REFERENCE_EDEFAULT.equals(nextTransactionReference);
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS:
        return associatedVersions != null && !associatedVersions.isEmpty();
      case VersionDSLPackage.VERSION__INCLUDE_VERSION_CONTROL:
        return includeVersionControl != INCLUDE_VERSION_CONTROL_EDEFAULT;
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES:
        return authorizationRoutines != null && !authorizationRoutines.isEmpty();
      case VersionDSLPackage.VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT:
        return authorizationRoutinesAfterCommit != null && !authorizationRoutinesAfterCommit.isEmpty();
      case VersionDSLPackage.VERSION__INPUT_ROUTINES:
        return inputRoutines != null && !inputRoutines.isEmpty();
      case VersionDSLPackage.VERSION__INPUT_ROUTINES_AFTER_COMMIT:
        return inputRoutinesAfterCommit != null && !inputRoutinesAfterCommit.isEmpty();
      case VersionDSLPackage.VERSION__KEY_VALIDATION_ROUTINES:
        return keyValidationRoutines != null && !keyValidationRoutines.isEmpty();
      case VersionDSLPackage.VERSION__PRE_PROCESS_VALIDATION_ROUTINES:
        return preProcessValidationRoutines != null && !preProcessValidationRoutines.isEmpty();
      case VersionDSLPackage.VERSION__WEB_VALIDATION_ROUTINES:
        return webValidationRoutines != null && !webValidationRoutines.isEmpty();
      case VersionDSLPackage.VERSION__CONFIRM_VERSION:
        return confirmVersion != null;
      case VersionDSLPackage.VERSION__PREVIEW_VERSION:
        return previewVersion != null;
      case VersionDSLPackage.VERSION__CHALLENGE_RESPONSE:
        return CHALLENGE_RESPONSE_EDEFAULT == null ? challengeResponse != null : !CHALLENGE_RESPONSE_EDEFAULT.equals(challengeResponse);
      case VersionDSLPackage.VERSION__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case VersionDSLPackage.VERSION__PUBLISH_WEB_SERVICE:
        return publishWebService != PUBLISH_WEB_SERVICE_EDEFAULT;
      case VersionDSLPackage.VERSION__WEB_SERVICE_ACTIVITY:
        return WEB_SERVICE_ACTIVITY_EDEFAULT == null ? webServiceActivity != null : !WEB_SERVICE_ACTIVITY_EDEFAULT.equals(webServiceActivity);
      case VersionDSLPackage.VERSION__WEB_SERVICE_FUNCTION:
        return webServiceFunction != WEB_SERVICE_FUNCTION_EDEFAULT;
      case VersionDSLPackage.VERSION__WEB_SERVICE_DESCRIPTION:
        return WEB_SERVICE_DESCRIPTION_EDEFAULT == null ? webServiceDescription != null : !WEB_SERVICE_DESCRIPTION_EDEFAULT.equals(webServiceDescription);
      case VersionDSLPackage.VERSION__WEB_SERVICE_NAMES:
        return webServiceNames != null && !webServiceNames.isEmpty();
      case VersionDSLPackage.VERSION__GENERATE_IFP:
        return generateIFP != GENERATE_IFP_EDEFAULT;
      case VersionDSLPackage.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        return associatedVersionsPresentationPattern != ASSOCIATED_VERSIONS_PRESENTATION_PATTERN_EDEFAULT;
      case VersionDSLPackage.VERSION__FIELDS_LAYOUT_PATTERN:
        return fieldsLayoutPattern != FIELDS_LAYOUT_PATTERN_EDEFAULT;
      case VersionDSLPackage.VERSION__FIELDS:
        return fields != null && !fields.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (shortName: ");
    result.append(shortName);
    result.append(", t24Name: ");
    result.append(t24Name);
    result.append(", metamodelVersion: ");
    result.append(metamodelVersion);
    result.append(", group: ");
    result.append(group);
    result.append(", numberOfAuthorisers: ");
    result.append(numberOfAuthorisers);
    result.append(", exceptionProcessing: ");
    result.append(exceptionProcessing);
    result.append(", interfaceException: ");
    result.append(interfaceException);
    result.append(", businessDayControl: ");
    result.append(businessDayControl);
    result.append(", keySequence: ");
    result.append(keySequence);
    result.append(", otherCompanyAccess: ");
    result.append(otherCompanyAccess);
    result.append(", autoCompanyChange: ");
    result.append(autoCompanyChange);
    result.append(", overrideApproval: ");
    result.append(overrideApproval);
    result.append(", dealSlipTrigger: ");
    result.append(dealSlipTrigger);
    result.append(", dealSlipStyleSheet: ");
    result.append(dealSlipStyleSheet);
    result.append(", recordsPerPage: ");
    result.append(recordsPerPage);
    result.append(", fieldsPerLine: ");
    result.append(fieldsPerLine);
    result.append(", initialCursorPosition: ");
    result.append(initialCursorPosition);
    result.append(", browserToolbar: ");
    result.append(browserToolbar);
    result.append(", languageLocale: ");
    result.append(languageLocale);
    result.append(", nextVersionFunction: ");
    result.append(nextVersionFunction);
    result.append(", nextTransactionReference: ");
    result.append(nextTransactionReference);
    result.append(", includeVersionControl: ");
    result.append(includeVersionControl);
    result.append(", challengeResponse: ");
    result.append(challengeResponse);
    result.append(", attributes: ");
    result.append(attributes);
    result.append(", publishWebService: ");
    result.append(publishWebService);
    result.append(", webServiceActivity: ");
    result.append(webServiceActivity);
    result.append(", webServiceFunction: ");
    result.append(webServiceFunction);
    result.append(", webServiceDescription: ");
    result.append(webServiceDescription);
    result.append(", webServiceNames: ");
    result.append(webServiceNames);
    result.append(", generateIFP: ");
    result.append(generateIFP);
    result.append(", associatedVersionsPresentationPattern: ");
    result.append(associatedVersionsPresentationPattern);
    result.append(", fieldsLayoutPattern: ");
    result.append(fieldsLayoutPattern);
    result.append(')');
    return result.toString();
  }

} //VersionImpl
