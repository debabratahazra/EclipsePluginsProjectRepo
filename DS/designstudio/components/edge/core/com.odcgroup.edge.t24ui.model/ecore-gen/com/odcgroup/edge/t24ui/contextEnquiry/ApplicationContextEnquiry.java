/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import com.odcgroup.mdf.metamodel.MdfClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Context Enquiry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesToField <em>Applies To Field</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesTo <em>Applies To</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getApplicationContextEnquiry()
 * @model
 * @generated
 */
public interface ApplicationContextEnquiry extends ContextEnquiry {
	/**
	 * Returns the value of the '<em><b>Applies To Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applies To Field</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applies To Field</em>' attribute.
	 * @see #setAppliesToField(String)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getApplicationContextEnquiry_AppliesToField()
	 * @model
	 * @generated
	 */
	String getAppliesToField();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesToField <em>Applies To Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Applies To Field</em>' attribute.
	 * @see #getAppliesToField()
	 * @generated
	 */
	void setAppliesToField(String value);

	/**
	 * Returns the value of the '<em><b>Applies To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applies To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applies To</em>' reference.
	 * @see #setAppliesTo(MdfClass)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getApplicationContextEnquiry_AppliesTo()
	 * @model
	 * @generated
	 */
	MdfClass getAppliesTo();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry#getAppliesTo <em>Applies To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Applies To</em>' reference.
	 * @see #getAppliesTo()
	 * @generated
	 */
	void setAppliesTo(MdfClass value);

} // ApplicationContextEnquiry
