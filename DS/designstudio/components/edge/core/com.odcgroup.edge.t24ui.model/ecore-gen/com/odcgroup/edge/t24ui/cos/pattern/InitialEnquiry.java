/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initial Enquiry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry#getEnquiry <em>Enquiry</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getInitialEnquiry()
 * @model
 * @generated
 */
public interface InitialEnquiry extends ChildResourceSpec {
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
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getInitialEnquiry_Enquiry()
	 * @model
	 * @generated
	 */
	Enquiry getEnquiry();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry#getEnquiry <em>Enquiry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enquiry</em>' reference.
	 * @see #getEnquiry()
	 * @generated
	 */
	void setEnquiry(Enquiry value);

} // InitialEnquiry
