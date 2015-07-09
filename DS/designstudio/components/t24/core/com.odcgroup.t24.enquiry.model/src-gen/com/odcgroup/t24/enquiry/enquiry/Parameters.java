/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getFunction <em>Function</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isAuto <em>Auto</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isRunImmediately <em>Run Immediately</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getPwActivity <em>Pw Activity</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getFieldName <em>Field Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters()
 * @model
 * @generated
 */
public interface Parameters extends EObject
{
  /**
   * Returns the value of the '<em><b>Function</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.FunctionKind}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Function</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.FunctionKind
   * @see #setFunction(FunctionKind)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_Function()
   * @model
   * @generated
   */
  FunctionKind getFunction();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getFunction <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Function</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.FunctionKind
   * @see #getFunction()
   * @generated
   */
  void setFunction(FunctionKind value);

  /**
   * Returns the value of the '<em><b>Auto</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Auto</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Auto</em>' attribute.
   * @see #setAuto(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_Auto()
   * @model
   * @generated
   */
  boolean isAuto();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isAuto <em>Auto</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Auto</em>' attribute.
   * @see #isAuto()
   * @generated
   */
  void setAuto(boolean value);

  /**
   * Returns the value of the '<em><b>Run Immediately</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Run Immediately</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Run Immediately</em>' attribute.
   * @see #setRunImmediately(boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_RunImmediately()
   * @model
   * @generated
   */
  boolean isRunImmediately();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#isRunImmediately <em>Run Immediately</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Run Immediately</em>' attribute.
   * @see #isRunImmediately()
   * @generated
   */
  void setRunImmediately(boolean value);

  /**
   * Returns the value of the '<em><b>Pw Activity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pw Activity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pw Activity</em>' attribute.
   * @see #setPwActivity(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_PwActivity()
   * @model
   * @generated
   */
  String getPwActivity();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Parameters#getPwActivity <em>Pw Activity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pw Activity</em>' attribute.
   * @see #getPwActivity()
   * @generated
   */
  void setPwActivity(String value);

  /**
   * Returns the value of the '<em><b>Field Name</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field Name</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field Name</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_FieldName()
   * @model unique="false"
   * @generated
   */
  EList<String> getFieldName();

  /**
   * Returns the value of the '<em><b>Variable</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variable</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getParameters_Variable()
   * @model unique="false"
   * @generated
   */
  EList<String> getVariable();

} // Parameters
