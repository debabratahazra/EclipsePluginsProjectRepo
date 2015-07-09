/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection Criteria</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getAppField <em>App Field</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getSortOrder <em>Sort Order</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getSelectionCriteria()
 * @model
 * @generated
 */
public interface SelectionCriteria extends EObject {
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
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getSelectionCriteria_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>App Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>App Field</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>App Field</em>' attribute.
	 * @see #setAppField(String)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getSelectionCriteria_AppField()
	 * @model
	 * @generated
	 */
	String getAppField();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getAppField <em>App Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>App Field</em>' attribute.
	 * @see #getAppField()
	 * @generated
	 */
	void setAppField(String value);

	/**
	 * Returns the value of the '<em><b>Operand</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.contextEnquiry.Operand}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operand</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Operand
	 * @see #setOperand(Operand)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getSelectionCriteria_Operand()
	 * @model
	 * @generated
	 */
	Operand getOperand();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getOperand <em>Operand</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Operand
	 * @see #getOperand()
	 * @generated
	 */
	void setOperand(Operand value);

	/**
	 * Returns the value of the '<em><b>Sort Order</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.contextEnquiry.SortOrder}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sort Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sort Order</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SortOrder
	 * @see #setSortOrder(SortOrder)
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getSelectionCriteria_SortOrder()
	 * @model
	 * @generated
	 */
	SortOrder getSortOrder();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria#getSortOrder <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort Order</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SortOrder
	 * @see #getSortOrder()
	 * @generated
	 */
	void setSortOrder(SortOrder value);

} // SelectionCriteria
