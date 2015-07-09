/**
 */
package com.odcgroup.t24.version.versionDSL;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getDisplayType <em>Display Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getInputBehaviour <em>Input Behaviour</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getCaseConvention <em>Case Convention</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getMaxLength <em>Max Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichmentLength <em>Enrichment Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getExpansion <em>Expansion</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getRightAdjust <em>Right Adjust</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichment <em>Enrichment</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getRow <em>Row</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getRekeyRequired <em>Rekey Required</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getHyperlink <em>Hyperlink</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getHotValidate <em>Hot Validate</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getHotField <em>Hot Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getWebValidate <em>Web Validate</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getSelectionEnquiry <em>Selection Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getEnquiryParameter <em>Enquiry Parameter</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getPopupBehaviour <em>Popup Behaviour</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getDefaults <em>Defaults</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getValidationRoutines <em>Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getMV <em>MV</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.Field#getSV <em>SV</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField()
 * @model
 * @generated
 */
public interface Field extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Display Type</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.DisplayType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Display Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Display Type</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DisplayType
   * @see #setDisplayType(DisplayType)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_DisplayType()
   * @model
   * @generated
   */
  DisplayType getDisplayType();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getDisplayType <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Type</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.DisplayType
   * @see #getDisplayType()
   * @generated
   */
  void setDisplayType(DisplayType value);

  /**
   * Returns the value of the '<em><b>Input Behaviour</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.InputBehaviour}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Input Behaviour</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Input Behaviour</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.InputBehaviour
   * @see #setInputBehaviour(InputBehaviour)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_InputBehaviour()
   * @model
   * @generated
   */
  InputBehaviour getInputBehaviour();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getInputBehaviour <em>Input Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Input Behaviour</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.InputBehaviour
   * @see #getInputBehaviour()
   * @generated
   */
  void setInputBehaviour(InputBehaviour value);

  /**
   * Returns the value of the '<em><b>Case Convention</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.CaseConvention}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Case Convention</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Case Convention</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.CaseConvention
   * @see #setCaseConvention(CaseConvention)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_CaseConvention()
   * @model
   * @generated
   */
  CaseConvention getCaseConvention();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getCaseConvention <em>Case Convention</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Case Convention</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.CaseConvention
   * @see #getCaseConvention()
   * @generated
   */
  void setCaseConvention(CaseConvention value);

  /**
   * Returns the value of the '<em><b>Max Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Max Length</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max Length</em>' attribute.
   * @see #setMaxLength(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_MaxLength()
   * @model
   * @generated
   */
  Integer getMaxLength();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getMaxLength <em>Max Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max Length</em>' attribute.
   * @see #getMaxLength()
   * @generated
   */
  void setMaxLength(Integer value);

  /**
   * Returns the value of the '<em><b>Enrichment Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enrichment Length</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enrichment Length</em>' attribute.
   * @see #setEnrichmentLength(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_EnrichmentLength()
   * @model
   * @generated
   */
  Integer getEnrichmentLength();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichmentLength <em>Enrichment Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enrichment Length</em>' attribute.
   * @see #getEnrichmentLength()
   * @generated
   */
  void setEnrichmentLength(Integer value);

  /**
   * Returns the value of the '<em><b>Expansion</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.Expansion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expansion</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expansion</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Expansion
   * @see #setExpansion(Expansion)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Expansion()
   * @model
   * @generated
   */
  Expansion getExpansion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getExpansion <em>Expansion</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expansion</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.Expansion
   * @see #getExpansion()
   * @generated
   */
  void setExpansion(Expansion value);

  /**
   * Returns the value of the '<em><b>Right Adjust</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right Adjust</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right Adjust</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setRightAdjust(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_RightAdjust()
   * @model
   * @generated
   */
  YesNo getRightAdjust();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getRightAdjust <em>Right Adjust</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right Adjust</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getRightAdjust()
   * @generated
   */
  void setRightAdjust(YesNo value);

  /**
   * Returns the value of the '<em><b>Enrichment</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enrichment</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enrichment</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setEnrichment(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Enrichment()
   * @model
   * @generated
   */
  YesNo getEnrichment();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getEnrichment <em>Enrichment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enrichment</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getEnrichment()
   * @generated
   */
  void setEnrichment(YesNo value);

  /**
   * Returns the value of the '<em><b>Column</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Column</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Column</em>' attribute.
   * @see #setColumn(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Column()
   * @model
   * @generated
   */
  Integer getColumn();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getColumn <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Column</em>' attribute.
   * @see #getColumn()
   * @generated
   */
  void setColumn(Integer value);

  /**
   * Returns the value of the '<em><b>Row</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Row</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Row</em>' attribute.
   * @see #setRow(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Row()
   * @model
   * @generated
   */
  Integer getRow();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getRow <em>Row</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Row</em>' attribute.
   * @see #getRow()
   * @generated
   */
  void setRow(Integer value);

  /**
   * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mandatory</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setMandatory(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Mandatory()
   * @model
   * @generated
   */
  YesNo getMandatory();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getMandatory <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mandatory</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getMandatory()
   * @generated
   */
  void setMandatory(YesNo value);

  /**
   * Returns the value of the '<em><b>Rekey Required</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rekey Required</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rekey Required</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setRekeyRequired(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_RekeyRequired()
   * @model
   * @generated
   */
  YesNo getRekeyRequired();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getRekeyRequired <em>Rekey Required</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Rekey Required</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getRekeyRequired()
   * @generated
   */
  void setRekeyRequired(YesNo value);

  /**
   * Returns the value of the '<em><b>Hyperlink</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hyperlink</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hyperlink</em>' attribute.
   * @see #setHyperlink(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Hyperlink()
   * @model
   * @generated
   */
  String getHyperlink();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getHyperlink <em>Hyperlink</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hyperlink</em>' attribute.
   * @see #getHyperlink()
   * @generated
   */
  void setHyperlink(String value);

  /**
   * Returns the value of the '<em><b>Hot Validate</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hot Validate</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hot Validate</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setHotValidate(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_HotValidate()
   * @model
   * @generated
   */
  YesNo getHotValidate();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getHotValidate <em>Hot Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hot Validate</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getHotValidate()
   * @generated
   */
  void setHotValidate(YesNo value);

  /**
   * Returns the value of the '<em><b>Hot Field</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hot Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hot Field</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setHotField(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_HotField()
   * @model
   * @generated
   */
  YesNo getHotField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getHotField <em>Hot Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hot Field</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getHotField()
   * @generated
   */
  void setHotField(YesNo value);

  /**
   * Returns the value of the '<em><b>Web Validate</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.YesNo}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Validate</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Validate</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #setWebValidate(YesNo)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_WebValidate()
   * @model
   * @generated
   */
  YesNo getWebValidate();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getWebValidate <em>Web Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Validate</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.YesNo
   * @see #getWebValidate()
   * @generated
   */
  void setWebValidate(YesNo value);

  /**
   * Returns the value of the '<em><b>Selection Enquiry</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Selection Enquiry</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Selection Enquiry</em>' attribute.
   * @see #setSelectionEnquiry(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_SelectionEnquiry()
   * @model
   * @generated
   */
  String getSelectionEnquiry();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getSelectionEnquiry <em>Selection Enquiry</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Selection Enquiry</em>' attribute.
   * @see #getSelectionEnquiry()
   * @generated
   */
  void setSelectionEnquiry(String value);

  /**
   * Returns the value of the '<em><b>Enquiry Parameter</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enquiry Parameter</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enquiry Parameter</em>' attribute.
   * @see #setEnquiryParameter(String)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_EnquiryParameter()
   * @model
   * @generated
   */
  String getEnquiryParameter();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getEnquiryParameter <em>Enquiry Parameter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enquiry Parameter</em>' attribute.
   * @see #getEnquiryParameter()
   * @generated
   */
  void setEnquiryParameter(String value);

  /**
   * Returns the value of the '<em><b>Popup Behaviour</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.version.versionDSL.PopupBehaviour}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Popup Behaviour</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Popup Behaviour</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.PopupBehaviour
   * @see #setPopupBehaviour(PopupBehaviour)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_PopupBehaviour()
   * @model
   * @generated
   */
  PopupBehaviour getPopupBehaviour();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getPopupBehaviour <em>Popup Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Popup Behaviour</em>' attribute.
   * @see com.odcgroup.t24.version.versionDSL.PopupBehaviour
   * @see #getPopupBehaviour()
   * @generated
   */
  void setPopupBehaviour(PopupBehaviour value);

  /**
   * Returns the value of the '<em><b>Defaults</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Default}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Defaults</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Defaults</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Defaults()
   * @model containment="true"
   * @generated
   */
  EList<Default> getDefaults();

  /**
   * Returns the value of the '<em><b>Label</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' containment reference.
   * @see #setLabel(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Label()
   * @model containment="true"
   * @generated
   */
  Translations getLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getLabel <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' containment reference.
   * @see #getLabel()
   * @generated
   */
  void setLabel(Translations value);

  /**
   * Returns the value of the '<em><b>Tool Tip</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tool Tip</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tool Tip</em>' containment reference.
   * @see #setToolTip(Translations)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_ToolTip()
   * @model containment="true"
   * @generated
   */
  Translations getToolTip();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getToolTip <em>Tool Tip</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tool Tip</em>' containment reference.
   * @see #getToolTip()
   * @generated
   */
  void setToolTip(Translations value);

  /**
   * Returns the value of the '<em><b>Validation Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Validation Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Validation Routines</em>' containment reference list.
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_ValidationRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getValidationRoutines();

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
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_Attributes()
   * @model unique="false"
   * @generated
   */
  EList<String> getAttributes();

  /**
   * Returns the value of the '<em><b>MV</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>MV</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>MV</em>' attribute.
   * @see #setMV(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_MV()
   * @model
   * @generated
   */
  Integer getMV();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getMV <em>MV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>MV</em>' attribute.
   * @see #getMV()
   * @generated
   */
  void setMV(Integer value);

  /**
   * Returns the value of the '<em><b>SV</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>SV</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>SV</em>' attribute.
   * @see #setSV(Integer)
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#getField_SV()
   * @model
   * @generated
   */
  Integer getSV();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.version.versionDSL.Field#getSV <em>SV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>SV</em>' attribute.
   * @see #getSV()
   * @generated
   */
  void setSV(Integer value);

} // Field
