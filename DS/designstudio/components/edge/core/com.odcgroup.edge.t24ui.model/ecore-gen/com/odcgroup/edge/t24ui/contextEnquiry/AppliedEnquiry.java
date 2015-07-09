/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Applied Enquiry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getEnquiry <em>Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getSelectionFields <em>Selection Fields</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getDescriptions <em>Descriptions</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getAutoLaunch <em>Auto Launch</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getAppliedEnquiry()
 * @model
 * @generated
 */
public interface AppliedEnquiry extends EObject {
	/**
	 * Returns the value of the '<em><b>Enquiry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enquiry</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enquiry</em>' reference.
	 * @see #setEnquiry(Enquiry)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getAppliedEnquiry_Enquiry()
	 * @model
	 * @generated
	 */
	Enquiry getEnquiry();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getEnquiry <em>Enquiry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enquiry</em>' reference.
	 * @see #getEnquiry()
	 * @generated
	 */
	void setEnquiry(Enquiry value);

	/**
	 * Returns the value of the '<em><b>Selection Fields</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selection Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selection Fields</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getAppliedEnquiry_SelectionFields()
	 * @model containment="true"
	 * @generated
	 */
	EList<SelectionCriteria> getSelectionFields();

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
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getAppliedEnquiry_Descriptions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Description> getDescriptions();

	/**
	 * Returns the value of the '<em><b>Auto Launch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Launch</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Launch</em>' containment reference.
	 * @see #setAutoLaunch(AutoLaunch)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getAppliedEnquiry_AutoLaunch()
	 * @model containment="true"
	 * @generated
	 */
	AutoLaunch getAutoLaunch();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry#getAutoLaunch <em>Auto Launch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Launch</em>' containment reference.
	 * @see #getAutoLaunch()
	 * @generated
	 */
	void setAutoLaunch(AutoLaunch value);

} // AppliedEnquiry
