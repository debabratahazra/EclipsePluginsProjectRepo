/**
 */
package com.odcgroup.t24.version.versionDSL;

import com.odcgroup.mdf.metamodel.MdfClass;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getForApplication <em>For Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getT24Name <em>T24 Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getGroup <em>Group</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getNumberOfAuthorisers <em>Number Of Authorisers</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getExceptionProcessing <em>Exception Processing</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getInterfaceException <em>Interface Exception</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getBusinessDayControl <em>Business Day Control</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getKeySequence <em>Key Sequence</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getOtherCompanyAccess <em>Other Company Access</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAutoCompanyChange <em>Auto Company Change</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getOverrideApproval <em>Override Approval</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipFormats <em>Deal Slip Formats</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipTrigger <em>Deal Slip Trigger</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipStyleSheet <em>Deal Slip Style Sheet</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getRecordsPerPage <em>Records Per Page</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsPerLine <em>Fields Per Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getInitialCursorPosition <em>Initial Cursor Position</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getBrowserToolbar <em>Browser Toolbar</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getLanguageLocale <em>Language Locale</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getHeader1 <em>Header1</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getHeader2 <em>Header2</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getHeader <em>Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getFooter <em>Footer</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersion <em>Next Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersionFunction <em>Next Version Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getNextTransactionReference <em>Next Transaction Reference</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersions <em>Associated Versions</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getIncludeVersionControl <em>Include Version Control</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutines <em>Authorization Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutinesAfterCommit <em>Authorization Routines After Commit</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getInputRoutines <em>Input Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getInputRoutinesAfterCommit <em>Input Routines After Commit</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getKeyValidationRoutines <em>Key Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getPreProcessValidationRoutines <em>Pre Process Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getWebValidationRoutines <em>Web Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getConfirmVersion <em>Confirm Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getPreviewVersion <em>Preview Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getChallengeResponse <em>Challenge Response</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getPublishWebService <em>Publish Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceActivity <em>Web Service Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceFunction <em>Web Service Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceDescription <em>Web Service Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceNames <em>Web Service Names</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getGenerateIFP <em>Generate IFP</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsLayoutPattern <em>Fields Layout Pattern</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Version#getFields <em>Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion()
 * @model
 * @generated
 */
public interface Version extends EObject
{
  /**
   * Returns the value of the '<em><b>For Application</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>For Application</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>For Application</em>' reference.
   * @see #setForApplication(MdfClass)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_ForApplication()
   * @model
   * @generated
   */
  MdfClass getForApplication();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getForApplication <em>For Application</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>For Application</em>' reference.
   * @see #getForApplication()
   * @generated
   */
  void setForApplication(MdfClass value);

  /**
   * Returns the value of the '<em><b>Short Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Short Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Short Name</em>' attribute.
   * @see #setShortName(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_ShortName()
   * @model
   * @generated
   */
  String getShortName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getShortName <em>Short Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Short Name</em>' attribute.
   * @see #getShortName()
   * @generated
   */
  void setShortName(String value);

  /**
   * Returns the value of the '<em><b>T24 Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>T24 Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>T24 Name</em>' attribute.
   * @see #setT24Name(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_T24Name()
   * @model
   * @generated
   */
  String getT24Name();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getT24Name <em>T24 Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>T24 Name</em>' attribute.
   * @see #getT24Name()
   * @generated
   */
  void setT24Name(String value);

  /**
   * Returns the value of the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metamodel Version</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metamodel Version</em>' attribute.
   * @see #setMetamodelVersion(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_MetamodelVersion()
   * @model
   * @generated
   */
  String getMetamodelVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metamodel Version</em>' attribute.
   * @see #getMetamodelVersion()
   * @generated
   */
  void setMetamodelVersion(String value);

  /**
   * Returns the value of the '<em><b>Group</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Group</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group</em>' attribute.
   * @see #setGroup(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Group()
   * @model
   * @generated
   */
  String getGroup();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getGroup <em>Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Group</em>' attribute.
   * @see #getGroup()
   * @generated
   */
  void setGroup(String value);

  /**
   * Returns the value of the '<em><b>Number Of Authorisers</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Number Of Authorisers</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number Of Authorisers</em>' attribute.
   * @see #setNumberOfAuthorisers(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_NumberOfAuthorisers()
   * @model
   * @generated
   */
  Integer getNumberOfAuthorisers();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getNumberOfAuthorisers <em>Number Of Authorisers</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number Of Authorisers</em>' attribute.
   * @see #getNumberOfAuthorisers()
   * @generated
   */
  void setNumberOfAuthorisers(Integer value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' containment reference.
   * @see #setDescription(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Description()
   * @model containment="true"
   * @generated
   */
  Translations getDescription();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getDescription <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' containment reference.
   * @see #getDescription()
   * @generated
   */
  void setDescription(Translations value);

  /**
   * Returns the value of the '<em><b>Exception Processing</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exception Processing</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exception Processing</em>' attribute.
   * @see #setExceptionProcessing(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_ExceptionProcessing()
   * @model
   * @generated
   */
  Integer getExceptionProcessing();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getExceptionProcessing <em>Exception Processing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exception Processing</em>' attribute.
   * @see #getExceptionProcessing()
   * @generated
   */
  void setExceptionProcessing(Integer value);

  /**
   * Returns the value of the '<em><b>Interface Exception</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interface Exception</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interface Exception</em>' attribute.
   * @see #setInterfaceException(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_InterfaceException()
   * @model
   * @generated
   */
  Integer getInterfaceException();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getInterfaceException <em>Interface Exception</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interface Exception</em>' attribute.
   * @see #getInterfaceException()
   * @generated
   */
  void setInterfaceException(Integer value);

  /**
   * Returns the value of the '<em><b>Business Day Control</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.BusinessDayControl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Business Day Control</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Business Day Control</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.BusinessDayControl
   * @see #setBusinessDayControl(BusinessDayControl)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_BusinessDayControl()
   * @model
   * @generated
   */
  BusinessDayControl getBusinessDayControl();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getBusinessDayControl <em>Business Day Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Business Day Control</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.BusinessDayControl
   * @see #getBusinessDayControl()
   * @generated
   */
  void setBusinessDayControl(BusinessDayControl value);

  /**
   * Returns the value of the '<em><b>Key Sequence</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Key Sequence</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Key Sequence</em>' attribute list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_KeySequence()
   * @model unique="false"
   * @generated
   */
  EList<String> getKeySequence();

  /**
   * Returns the value of the '<em><b>Other Company Access</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Other Company Access</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Other Company Access</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setOtherCompanyAccess(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_OtherCompanyAccess()
   * @model
   * @generated
   */
  YesNo getOtherCompanyAccess();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getOtherCompanyAccess <em>Other Company Access</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Other Company Access</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getOtherCompanyAccess()
   * @generated
   */
  void setOtherCompanyAccess(YesNo value);

  /**
   * Returns the value of the '<em><b>Auto Company Change</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Auto Company Change</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Auto Company Change</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setAutoCompanyChange(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_AutoCompanyChange()
   * @model
   * @generated
   */
  YesNo getAutoCompanyChange();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getAutoCompanyChange <em>Auto Company Change</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Auto Company Change</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getAutoCompanyChange()
   * @generated
   */
  void setAutoCompanyChange(YesNo value);

  /**
   * Returns the value of the '<em><b>Override Approval</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Override Approval</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Override Approval</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setOverrideApproval(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_OverrideApproval()
   * @model
   * @generated
   */
  YesNo getOverrideApproval();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getOverrideApproval <em>Override Approval</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Override Approval</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getOverrideApproval()
   * @generated
   */
  void setOverrideApproval(YesNo value);

  /**
   * Returns the value of the '<em><b>Deal Slip Formats</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.DealSlip}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Deal Slip Formats</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Deal Slip Formats</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_DealSlipFormats()
   * @model containment="true"
   * @generated
   */
  EList<DealSlip> getDealSlipFormats();

  /**
   * Returns the value of the '<em><b>Deal Slip Trigger</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.DealSlipTrigger}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Deal Slip Trigger</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Deal Slip Trigger</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipTrigger
   * @see #setDealSlipTrigger(DealSlipTrigger)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_DealSlipTrigger()
   * @model
   * @generated
   */
  DealSlipTrigger getDealSlipTrigger();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipTrigger <em>Deal Slip Trigger</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Deal Slip Trigger</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DealSlipTrigger
   * @see #getDealSlipTrigger()
   * @generated
   */
  void setDealSlipTrigger(DealSlipTrigger value);

  /**
   * Returns the value of the '<em><b>Deal Slip Style Sheet</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Deal Slip Style Sheet</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Deal Slip Style Sheet</em>' attribute.
   * @see #setDealSlipStyleSheet(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_DealSlipStyleSheet()
   * @model
   * @generated
   */
  String getDealSlipStyleSheet();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getDealSlipStyleSheet <em>Deal Slip Style Sheet</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Deal Slip Style Sheet</em>' attribute.
   * @see #getDealSlipStyleSheet()
   * @generated
   */
  void setDealSlipStyleSheet(String value);

  /**
   * Returns the value of the '<em><b>Records Per Page</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Records Per Page</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Records Per Page</em>' attribute.
   * @see #setRecordsPerPage(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_RecordsPerPage()
   * @model
   * @generated
   */
  String getRecordsPerPage();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getRecordsPerPage <em>Records Per Page</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Records Per Page</em>' attribute.
   * @see #getRecordsPerPage()
   * @generated
   */
  void setRecordsPerPage(String value);

  /**
   * Returns the value of the '<em><b>Fields Per Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields Per Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fields Per Line</em>' attribute.
   * @see #setFieldsPerLine(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_FieldsPerLine()
   * @model
   * @generated
   */
  String getFieldsPerLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsPerLine <em>Fields Per Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fields Per Line</em>' attribute.
   * @see #getFieldsPerLine()
   * @generated
   */
  void setFieldsPerLine(String value);

  /**
   * Returns the value of the '<em><b>Initial Cursor Position</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initial Cursor Position</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initial Cursor Position</em>' attribute.
   * @see #setInitialCursorPosition(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_InitialCursorPosition()
   * @model
   * @generated
   */
  String getInitialCursorPosition();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getInitialCursorPosition <em>Initial Cursor Position</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial Cursor Position</em>' attribute.
   * @see #getInitialCursorPosition()
   * @generated
   */
  void setInitialCursorPosition(String value);

  /**
   * Returns the value of the '<em><b>Browser Toolbar</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Browser Toolbar</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Browser Toolbar</em>' attribute.
   * @see #setBrowserToolbar(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_BrowserToolbar()
   * @model
   * @generated
   */
  String getBrowserToolbar();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getBrowserToolbar <em>Browser Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Browser Toolbar</em>' attribute.
   * @see #getBrowserToolbar()
   * @generated
   */
  void setBrowserToolbar(String value);

  /**
   * Returns the value of the '<em><b>Language Locale</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Language Locale</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Language Locale</em>' attribute list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_LanguageLocale()
   * @model unique="false"
   * @generated
   */
  EList<String> getLanguageLocale();

  /**
   * Returns the value of the '<em><b>Header1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Header1</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Header1</em>' containment reference.
   * @see #setHeader1(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Header1()
   * @model containment="true"
   * @generated
   */
  Translations getHeader1();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader1 <em>Header1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Header1</em>' containment reference.
   * @see #getHeader1()
   * @generated
   */
  void setHeader1(Translations value);

  /**
   * Returns the value of the '<em><b>Header2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Header2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Header2</em>' containment reference.
   * @see #setHeader2(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Header2()
   * @model containment="true"
   * @generated
   */
  Translations getHeader2();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader2 <em>Header2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Header2</em>' containment reference.
   * @see #getHeader2()
   * @generated
   */
  void setHeader2(Translations value);

  /**
   * Returns the value of the '<em><b>Header</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Header</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Header</em>' containment reference.
   * @see #setHeader(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Header()
   * @model containment="true"
   * @generated
   */
  Translations getHeader();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getHeader <em>Header</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Header</em>' containment reference.
   * @see #getHeader()
   * @generated
   */
  void setHeader(Translations value);

  /**
   * Returns the value of the '<em><b>Footer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Footer</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Footer</em>' containment reference.
   * @see #setFooter(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Footer()
   * @model containment="true"
   * @generated
   */
  Translations getFooter();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getFooter <em>Footer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Footer</em>' containment reference.
   * @see #getFooter()
   * @generated
   */
  void setFooter(Translations value);

  /**
   * Returns the value of the '<em><b>Next Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Next Version</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Next Version</em>' reference.
   * @see #setNextVersion(Version)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_NextVersion()
   * @model
   * @generated
   */
  Version getNextVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersion <em>Next Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next Version</em>' reference.
   * @see #getNextVersion()
   * @generated
   */
  void setNextVersion(Version value);

  /**
   * Returns the value of the '<em><b>Next Version Function</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.Function}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Next Version Function</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Next Version Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @see #setNextVersionFunction(Function)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_NextVersionFunction()
   * @model
   * @generated
   */
  Function getNextVersionFunction();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getNextVersionFunction <em>Next Version Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next Version Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @see #getNextVersionFunction()
   * @generated
   */
  void setNextVersionFunction(Function value);

  /**
   * Returns the value of the '<em><b>Next Transaction Reference</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Next Transaction Reference</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Next Transaction Reference</em>' attribute.
   * @see #setNextTransactionReference(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_NextTransactionReference()
   * @model
   * @generated
   */
  String getNextTransactionReference();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getNextTransactionReference <em>Next Transaction Reference</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next Transaction Reference</em>' attribute.
   * @see #getNextTransactionReference()
   * @generated
   */
  void setNextTransactionReference(String value);

  /**
   * Returns the value of the '<em><b>Associated Versions</b></em>' reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Version}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Associated Versions</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Associated Versions</em>' reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_AssociatedVersions()
   * @model
   * @generated
   */
  EList<Version> getAssociatedVersions();

  /**
   * Returns the value of the '<em><b>Include Version Control</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Include Version Control</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Include Version Control</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setIncludeVersionControl(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_IncludeVersionControl()
   * @model
   * @generated
   */
  YesNo getIncludeVersionControl();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getIncludeVersionControl <em>Include Version Control</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Include Version Control</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getIncludeVersionControl()
   * @generated
   */
  void setIncludeVersionControl(YesNo value);

  /**
   * Returns the value of the '<em><b>Authorization Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Authorization Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Authorization Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_AuthorizationRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getAuthorizationRoutines();

  /**
   * Returns the value of the '<em><b>Authorization Routines After Commit</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Authorization Routines After Commit</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Authorization Routines After Commit</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_AuthorizationRoutinesAfterCommit()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getAuthorizationRoutinesAfterCommit();

  /**
   * Returns the value of the '<em><b>Input Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Input Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Input Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_InputRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getInputRoutines();

  /**
   * Returns the value of the '<em><b>Input Routines After Commit</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Input Routines After Commit</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Input Routines After Commit</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_InputRoutinesAfterCommit()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getInputRoutinesAfterCommit();

  /**
   * Returns the value of the '<em><b>Key Validation Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Key Validation Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Key Validation Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_KeyValidationRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getKeyValidationRoutines();

  /**
   * Returns the value of the '<em><b>Pre Process Validation Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pre Process Validation Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pre Process Validation Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_PreProcessValidationRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getPreProcessValidationRoutines();

  /**
   * Returns the value of the '<em><b>Web Validation Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Validation Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Validation Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_WebValidationRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getWebValidationRoutines();

  /**
   * Returns the value of the '<em><b>Confirm Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Confirm Version</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Confirm Version</em>' reference.
   * @see #setConfirmVersion(Version)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_ConfirmVersion()
   * @model
   * @generated
   */
  Version getConfirmVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getConfirmVersion <em>Confirm Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Confirm Version</em>' reference.
   * @see #getConfirmVersion()
   * @generated
   */
  void setConfirmVersion(Version value);

  /**
   * Returns the value of the '<em><b>Preview Version</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Preview Version</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Preview Version</em>' reference.
   * @see #setPreviewVersion(Version)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_PreviewVersion()
   * @model
   * @generated
   */
  Version getPreviewVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getPreviewVersion <em>Preview Version</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Preview Version</em>' reference.
   * @see #getPreviewVersion()
   * @generated
   */
  void setPreviewVersion(Version value);

  /**
   * Returns the value of the '<em><b>Challenge Response</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Challenge Response</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Challenge Response</em>' attribute.
   * @see #setChallengeResponse(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_ChallengeResponse()
   * @model
   * @generated
   */
  String getChallengeResponse();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getChallengeResponse <em>Challenge Response</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Challenge Response</em>' attribute.
   * @see #getChallengeResponse()
   * @generated
   */
  void setChallengeResponse(String value);

  /**
   * Returns the value of the '<em><b>Attributes</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attributes</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributes</em>' attribute list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Attributes()
   * @model unique="false"
   * @generated
   */
  EList<String> getAttributes();

  /**
   * Returns the value of the '<em><b>Publish Web Service</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Publish Web Service</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Publish Web Service</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setPublishWebService(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_PublishWebService()
   * @model
   * @generated
   */
  YesNo getPublishWebService();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getPublishWebService <em>Publish Web Service</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Publish Web Service</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getPublishWebService()
   * @generated
   */
  void setPublishWebService(YesNo value);

  /**
   * Returns the value of the '<em><b>Web Service Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Activity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Activity</em>' attribute.
   * @see #setWebServiceActivity(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_WebServiceActivity()
   * @model
   * @generated
   */
  String getWebServiceActivity();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceActivity <em>Web Service Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service Activity</em>' attribute.
   * @see #getWebServiceActivity()
   * @generated
   */
  void setWebServiceActivity(String value);

  /**
   * Returns the value of the '<em><b>Web Service Function</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.Function}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Function</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @see #setWebServiceFunction(Function)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_WebServiceFunction()
   * @model
   * @generated
   */
  Function getWebServiceFunction();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceFunction <em>Web Service Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service Function</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Function
   * @see #getWebServiceFunction()
   * @generated
   */
  void setWebServiceFunction(Function value);

  /**
   * Returns the value of the '<em><b>Web Service Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Description</em>' attribute.
   * @see #setWebServiceDescription(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_WebServiceDescription()
   * @model
   * @generated
   */
  String getWebServiceDescription();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getWebServiceDescription <em>Web Service Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service Description</em>' attribute.
   * @see #getWebServiceDescription()
   * @generated
   */
  void setWebServiceDescription(String value);

  /**
   * Returns the value of the '<em><b>Web Service Names</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service Names</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service Names</em>' attribute list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_WebServiceNames()
   * @model unique="false"
   * @generated
   */
  EList<String> getWebServiceNames();

  /**
   * Returns the value of the '<em><b>Generate IFP</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generate IFP</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generate IFP</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setGenerateIFP(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_GenerateIFP()
   * @model
   * @generated
   */
  YesNo getGenerateIFP();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getGenerateIFP <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Generate IFP</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getGenerateIFP()
   * @generated
   */
  void setGenerateIFP(YesNo value);

  /**
   * Returns the value of the '<em><b>Associated Versions Presentation Pattern</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Associated Versions Presentation Pattern</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Associated Versions Presentation Pattern</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern
   * @see #setAssociatedVersionsPresentationPattern(AssociatedVersionsPresentationPattern)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_AssociatedVersionsPresentationPattern()
   * @model
   * @generated
   */
  AssociatedVersionsPresentationPattern getAssociatedVersionsPresentationPattern();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersionsPresentationPattern <em>Associated Versions Presentation Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Associated Versions Presentation Pattern</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern
   * @see #getAssociatedVersionsPresentationPattern()
   * @generated
   */
  void setAssociatedVersionsPresentationPattern(AssociatedVersionsPresentationPattern value);

  /**
   * Returns the value of the '<em><b>Fields Layout Pattern</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields Layout Pattern</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fields Layout Pattern</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern
   * @see #setFieldsLayoutPattern(FieldsLayoutPattern)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_FieldsLayoutPattern()
   * @model
   * @generated
   */
  FieldsLayoutPattern getFieldsLayoutPattern();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Version#getFieldsLayoutPattern <em>Fields Layout Pattern</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fields Layout Pattern</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern
   * @see #getFieldsLayoutPattern()
   * @generated
   */
  void setFieldsLayoutPattern(FieldsLayoutPattern value);

  /**
   * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Field}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fields</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getVersion_Fields()
   * @model containment="true"
   * @generated
   */
  EList<Field> getFields();

} // Version
