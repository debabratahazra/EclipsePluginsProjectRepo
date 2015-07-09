/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getPopupDropDown <em>Popup Drop Down</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getOperands <em>Operands</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Selection#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection()
 * @model
 * @generated
 */
public interface Selection extends EObject
{
  /**
   * Returns the value of the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field</em>' attribute.
   * @see #setField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

  /**
   * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mandatory</em>' attribute.
   * @see #setMandatory(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_Mandatory()
   * @model
   * @generated
   */
  Boolean getMandatory();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getMandatory <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mandatory</em>' attribute.
   * @see #getMandatory()
   * @generated
   */
  void setMandatory(Boolean value);

  /**
   * Returns the value of the '<em><b>Popup Drop Down</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Popup Drop Down</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Popup Drop Down</em>' attribute.
   * @see #setPopupDropDown(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_PopupDropDown()
   * @model
   * @generated
   */
  Boolean getPopupDropDown();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getPopupDropDown <em>Popup Drop Down</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Popup Drop Down</em>' attribute.
   * @see #getPopupDropDown()
   * @generated
   */
  void setPopupDropDown(Boolean value);

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_Label()
   * @model containment="true"
   * @generated
   */
  Translations getLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getLabel <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' containment reference.
   * @see #getLabel()
   * @generated
   */
  void setLabel(Translations value);

  /**
   * Returns the value of the '<em><b>Operands</b></em>' attribute list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.SelectionOperator}.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.SelectionOperator}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operands</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operands</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperator
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_Operands()
   * @model unique="false"
   * @generated
   */
  EList<SelectionOperator> getOperands();

  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.AndOr}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.AndOr
   * @see #setOperator(AndOr)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getSelection_Operator()
   * @model
   * @generated
   */
  AndOr getOperator();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Selection#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.AndOr
   * @see #getOperator()
   * @generated
   */
  void setOperator(AndOr value);

} // Selection
