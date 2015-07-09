/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Target#getApplication <em>Application</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Target#getScreen <em>Screen</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Target#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTarget()
 * @model
 * @generated
 */
public interface Target extends EObject
{
  /**
   * Returns the value of the '<em><b>Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Application</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Application</em>' attribute.
   * @see #setApplication(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTarget_Application()
   * @model
   * @generated
   */
  String getApplication();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Target#getApplication <em>Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application</em>' attribute.
   * @see #getApplication()
   * @generated
   */
  void setApplication(String value);

  /**
   * Returns the value of the '<em><b>Screen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Screen</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Screen</em>' attribute.
   * @see #setScreen(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTarget_Screen()
   * @model
   * @generated
   */
  String getScreen();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Target#getScreen <em>Screen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Screen</em>' attribute.
   * @see #getScreen()
   * @generated
   */
  void setScreen(String value);

  /**
   * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.TargetMapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mappings</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getTarget_Mappings()
   * @model containment="true"
   * @generated
   */
  EList<TargetMapping> getMappings();

} // Target
