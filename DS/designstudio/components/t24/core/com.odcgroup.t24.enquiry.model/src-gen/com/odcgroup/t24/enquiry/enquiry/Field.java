/**
 */
package com.odcgroup.t24.enquiry.enquiry;

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
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getComments <em>Comments</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplayType <em>Display Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getFormat <em>Format</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getBreakCondition <em>Break Condition</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getLength <em>Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getAlignment <em>Alignment</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getCommaSeparator <em>Comma Separator</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getNumberOfDecimals <em>Number Of Decimals</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getEscapeSequence <em>Escape Sequence</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getFmtMask <em>Fmt Mask</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplaySection <em>Display Section</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getPosition <em>Position</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getColumnWidth <em>Column Width</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getSpoolBreak <em>Spool Break</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getSingleMulti <em>Single Multi</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getHidden <em>Hidden</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoHeader <em>No Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoColumnLabel <em>No Column Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getOperation <em>Operation</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getConversion <em>Conversion</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Field#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField()
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Label()
   * @model containment="true"
   * @generated
   */
  Translations getLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getLabel <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' containment reference.
   * @see #getLabel()
   * @generated
   */
  void setLabel(Translations value);

  /**
   * Returns the value of the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Comments</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Comments</em>' attribute.
   * @see #setComments(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Comments()
   * @model
   * @generated
   */
  String getComments();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getComments <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Comments</em>' attribute.
   * @see #getComments()
   * @generated
   */
  void setComments(String value);

  /**
   * Returns the value of the '<em><b>Display Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Display Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Display Type</em>' attribute.
   * @see #setDisplayType(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_DisplayType()
   * @model
   * @generated
   */
  String getDisplayType();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplayType <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Type</em>' attribute.
   * @see #getDisplayType()
   * @generated
   */
  void setDisplayType(String value);

  /**
   * Returns the value of the '<em><b>Format</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Format</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Format</em>' containment reference.
   * @see #setFormat(Format)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Format()
   * @model containment="true"
   * @generated
   */
  Format getFormat();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getFormat <em>Format</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Format</em>' containment reference.
   * @see #getFormat()
   * @generated
   */
  void setFormat(Format value);

  /**
   * Returns the value of the '<em><b>Break Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Break Condition</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Break Condition</em>' containment reference.
   * @see #setBreakCondition(BreakCondition)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_BreakCondition()
   * @model containment="true"
   * @generated
   */
  BreakCondition getBreakCondition();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getBreakCondition <em>Break Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Break Condition</em>' containment reference.
   * @see #getBreakCondition()
   * @generated
   */
  void setBreakCondition(BreakCondition value);

  /**
   * Returns the value of the '<em><b>Length</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Length</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Length</em>' attribute.
   * @see #setLength(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Length()
   * @model
   * @generated
   */
  Integer getLength();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getLength <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Length</em>' attribute.
   * @see #getLength()
   * @generated
   */
  void setLength(Integer value);

  /**
   * Returns the value of the '<em><b>Alignment</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.AlignmentKind}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Alignment</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Alignment</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.AlignmentKind
   * @see #setAlignment(AlignmentKind)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Alignment()
   * @model
   * @generated
   */
  AlignmentKind getAlignment();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getAlignment <em>Alignment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Alignment</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.AlignmentKind
   * @see #getAlignment()
   * @generated
   */
  void setAlignment(AlignmentKind value);

  /**
   * Returns the value of the '<em><b>Comma Separator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Comma Separator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Comma Separator</em>' attribute.
   * @see #setCommaSeparator(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_CommaSeparator()
   * @model
   * @generated
   */
  Boolean getCommaSeparator();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getCommaSeparator <em>Comma Separator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Comma Separator</em>' attribute.
   * @see #getCommaSeparator()
   * @generated
   */
  void setCommaSeparator(Boolean value);

  /**
   * Returns the value of the '<em><b>Number Of Decimals</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Number Of Decimals</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number Of Decimals</em>' attribute.
   * @see #setNumberOfDecimals(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_NumberOfDecimals()
   * @model
   * @generated
   */
  Integer getNumberOfDecimals();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNumberOfDecimals <em>Number Of Decimals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Number Of Decimals</em>' attribute.
   * @see #getNumberOfDecimals()
   * @generated
   */
  void setNumberOfDecimals(Integer value);

  /**
   * Returns the value of the '<em><b>Escape Sequence</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.EscapeSequence}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Escape Sequence</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Escape Sequence</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.EscapeSequence
   * @see #setEscapeSequence(EscapeSequence)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_EscapeSequence()
   * @model
   * @generated
   */
  EscapeSequence getEscapeSequence();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getEscapeSequence <em>Escape Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Escape Sequence</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.EscapeSequence
   * @see #getEscapeSequence()
   * @generated
   */
  void setEscapeSequence(EscapeSequence value);

  /**
   * Returns the value of the '<em><b>Fmt Mask</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fmt Mask</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fmt Mask</em>' attribute.
   * @see #setFmtMask(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_FmtMask()
   * @model
   * @generated
   */
  String getFmtMask();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getFmtMask <em>Fmt Mask</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fmt Mask</em>' attribute.
   * @see #getFmtMask()
   * @generated
   */
  void setFmtMask(String value);

  /**
   * Returns the value of the '<em><b>Display Section</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Display Section</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Display Section</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind
   * @see #setDisplaySection(DisplaySectionKind)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_DisplaySection()
   * @model
   * @generated
   */
  DisplaySectionKind getDisplaySection();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getDisplaySection <em>Display Section</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Display Section</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind
   * @see #getDisplaySection()
   * @generated
   */
  void setDisplaySection(DisplaySectionKind value);

  /**
   * Returns the value of the '<em><b>Position</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Position</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Position</em>' containment reference.
   * @see #setPosition(FieldPosition)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Position()
   * @model containment="true"
   * @generated
   */
  FieldPosition getPosition();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getPosition <em>Position</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Position</em>' containment reference.
   * @see #getPosition()
   * @generated
   */
  void setPosition(FieldPosition value);

  /**
   * Returns the value of the '<em><b>Column Width</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Column Width</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Column Width</em>' attribute.
   * @see #setColumnWidth(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_ColumnWidth()
   * @model
   * @generated
   */
  Integer getColumnWidth();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getColumnWidth <em>Column Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Column Width</em>' attribute.
   * @see #getColumnWidth()
   * @generated
   */
  void setColumnWidth(Integer value);

  /**
   * Returns the value of the '<em><b>Spool Break</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Spool Break</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Spool Break</em>' attribute.
   * @see #setSpoolBreak(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_SpoolBreak()
   * @model
   * @generated
   */
  Boolean getSpoolBreak();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getSpoolBreak <em>Spool Break</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Spool Break</em>' attribute.
   * @see #getSpoolBreak()
   * @generated
   */
  void setSpoolBreak(Boolean value);

  /**
   * Returns the value of the '<em><b>Single Multi</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.ProcessingMode}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Single Multi</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Single Multi</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.ProcessingMode
   * @see #setSingleMulti(ProcessingMode)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_SingleMulti()
   * @model
   * @generated
   */
  ProcessingMode getSingleMulti();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getSingleMulti <em>Single Multi</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Single Multi</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.ProcessingMode
   * @see #getSingleMulti()
   * @generated
   */
  void setSingleMulti(ProcessingMode value);

  /**
   * Returns the value of the '<em><b>Hidden</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hidden</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hidden</em>' attribute.
   * @see #setHidden(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Hidden()
   * @model
   * @generated
   */
  Boolean getHidden();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getHidden <em>Hidden</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hidden</em>' attribute.
   * @see #getHidden()
   * @generated
   */
  void setHidden(Boolean value);

  /**
   * Returns the value of the '<em><b>No Header</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>No Header</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>No Header</em>' attribute.
   * @see #setNoHeader(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_NoHeader()
   * @model
   * @generated
   */
  Boolean getNoHeader();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoHeader <em>No Header</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>No Header</em>' attribute.
   * @see #getNoHeader()
   * @generated
   */
  void setNoHeader(Boolean value);

  /**
   * Returns the value of the '<em><b>No Column Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>No Column Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>No Column Label</em>' attribute.
   * @see #setNoColumnLabel(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_NoColumnLabel()
   * @model
   * @generated
   */
  Boolean getNoColumnLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getNoColumnLabel <em>No Column Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>No Column Label</em>' attribute.
   * @see #getNoColumnLabel()
   * @generated
   */
  void setNoColumnLabel(Boolean value);

  /**
   * Returns the value of the '<em><b>Operation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operation</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operation</em>' containment reference.
   * @see #setOperation(Operation)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Operation()
   * @model containment="true"
   * @generated
   */
  Operation getOperation();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Field#getOperation <em>Operation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operation</em>' containment reference.
   * @see #getOperation()
   * @generated
   */
  void setOperation(Operation value);

  /**
   * Returns the value of the '<em><b>Conversion</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Conversion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Conversion</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Conversion</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Conversion()
   * @model containment="true"
   * @generated
   */
  EList<Conversion> getConversion();

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getField_Attributes()
   * @model unique="false"
   * @generated
   */
  EList<String> getAttributes();

} // Field
