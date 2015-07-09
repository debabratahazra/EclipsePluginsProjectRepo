/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edit Policy Role</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.EditPolicyRole#getMode <em>Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.EditPolicyRole#getRole <em>Role</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.EditPolicyRole#getImplementationClass <em>Implementation Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditPolicyRole()
 * @model
 * @generated
 */
public interface EditPolicyRole extends EObject {
	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.page.uimodel.EditPolicyRoleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditPolicyRoleType
	 * @see #setRole(EditPolicyRoleType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditPolicyRole_Role()
	 * @model
	 * @generated
	 */
	EditPolicyRoleType getRole();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.EditPolicyRole#getRole <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditPolicyRoleType
	 * @see #getRole()
	 * @generated
	 */
	void setRole(EditPolicyRoleType value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.page.uimodel.EditorMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #setMode(EditorMode)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditPolicyRole_Mode()
	 * @model
	 * @generated
	 */
	EditorMode getMode();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.EditPolicyRole#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #getMode()
	 * @generated
	 */
	void setMode(EditorMode value);

	/**
	 * Returns the value of the '<em><b>Implementation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation Class</em>' attribute.
	 * @see #setImplementationClass(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditPolicyRole_ImplementationClass()
	 * @model
	 * @generated
	 */
	String getImplementationClass();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.EditPolicyRole#getImplementationClass <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Class</em>' attribute.
	 * @see #getImplementationClass()
	 * @generated
	 */
	void setImplementationClass(String value);

} // EditPolicyRole
