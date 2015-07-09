/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tool</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Tool#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Tool#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Tool#getCommand <em>Command</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTool()
 * @model
 * @generated
 */
public interface Tool extends EObject
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTool_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Tool#getName <em>Name</em>}' attribute.
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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTool_Label()
   * @model containment="true"
   * @generated
   */
  Translations getLabel();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Tool#getLabel <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' containment reference.
   * @see #getLabel()
   * @generated
   */
  void setLabel(Translations value);

  /**
   * Returns the value of the '<em><b>Command</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Command</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Command</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTool_Command()
   * @model unique="false"
   * @generated
   */
  EList<String> getCommand();

} // Tool
