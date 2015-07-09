/**
 */
package com.odcgroup.t24.localReferenceApplication;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalField#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLocalRefID <em>Local Ref ID</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalField#getToolTip <em>Tool Tip</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalField()
 * @model
 * @generated
 */
public interface LocalField extends EObject {
	/**
	 * Returns the value of the '<em><b>Group Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Name</em>' attribute.
	 * @see #setGroupName(String)
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalField_GroupName()
	 * @model extendedMetaData="kind='attribute' name='groupName'"
	 * @generated
	 */
	String getGroupName();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getGroupName <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Name</em>' attribute.
	 * @see #getGroupName()
	 * @generated
	 */
	void setGroupName(String value);

	/**
	 * Returns the value of the '<em><b>Local Ref ID</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Ref ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Ref ID</em>' attribute.
	 * @see #setLocalRefID(String)
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalField_LocalRefID()
	 * @model default="" required="true"
	 *        extendedMetaData="kind='attribute' name='localRefID'"
	 * @generated
	 */
	String getLocalRefID();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLocalRefID <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Ref ID</em>' attribute.
	 * @see #getLocalRefID()
	 * @generated
	 */
	void setLocalRefID(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' containment reference.
	 * @see #setLabel(Translations)
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalField_Label()
	 * @model containment="true"
	 * @generated
	 */
	Translations getLabel();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getLabel <em>Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' containment reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Translations value);

	/**
	 * Returns the value of the '<em><b>Tool Tip</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tool Tip</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tool Tip</em>' containment reference.
	 * @see #setToolTip(Translations)
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalField_ToolTip()
	 * @model containment="true"
	 * @generated
	 */
	Translations getToolTip();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.localReferenceApplication.LocalField#getToolTip <em>Tool Tip</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tool Tip</em>' containment reference.
	 * @see #getToolTip()
	 * @generated
	 */
	void setToolTip(Translations value);

} // LocalField
