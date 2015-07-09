/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context Enquiry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getEnquiries <em>Enquiries</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getContextEnquiry()
 * @model abstract="true"
 * @generated
 */
public interface ContextEnquiry extends EObject {
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
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getContextEnquiry_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Descriptions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.contextEnquiry.Description}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptions</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getContextEnquiry_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

	/**
	 * Returns the value of the '<em><b>Enquiries</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enquiries</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enquiries</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getContextEnquiry_Enquiries()
	 * @model containment="true"
	 * @generated
	 */
	EList<AppliedEnquiry> getEnquiries();

} // ContextEnquiry
