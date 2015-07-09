/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.Function#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends EObject {

	/**
	 * Returns the value of the '<em><b>Function</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum
	 * @see #setFunction(FunctionEnum)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getFunction_Function()
	 * @model
	 * @generated
	 */
	FunctionEnum getFunction();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.Function#getFunction <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionEnum
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(FunctionEnum value);
} // Function
