/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Screen Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getCompositeScreen <em>Composite Screen</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getReference <em>Reference</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getFieldParameter <em>Field Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompositeScreenOption()
 * @model
 * @generated
 */
public interface CompositeScreenOption extends DrillDownOption
{
  /**
   * Returns the value of the '<em><b>Composite Screen</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Composite Screen</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Composite Screen</em>' attribute.
   * @see #setCompositeScreen(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompositeScreenOption_CompositeScreen()
   * @model
   * @generated
   */
  String getCompositeScreen();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption#getCompositeScreen <em>Composite Screen</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Composite Screen</em>' attribute.
   * @see #getCompositeScreen()
   * @generated
   */
  void setCompositeScreen(String value);

  /**
   * Returns the value of the '<em><b>Reference</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Reference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Reference</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Reference</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompositeScreenOption_Reference()
   * @model containment="true"
   * @generated
   */
  EList<Reference> getReference();

  /**
   * Returns the value of the '<em><b>Field Parameter</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Parameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field Parameter</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field Parameter</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getCompositeScreenOption_FieldParameter()
   * @model containment="true"
   * @generated
   */
  EList<Parameter> getFieldParameter();

} // CompositeScreenOption
