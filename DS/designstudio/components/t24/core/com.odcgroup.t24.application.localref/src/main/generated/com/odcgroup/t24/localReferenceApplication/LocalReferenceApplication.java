/**
 */
package com.odcgroup.t24.localReferenceApplication;

import com.odcgroup.mdf.metamodel.MdfClass;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Reference Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getLocalField <em>Local Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getForApplication <em>For Application</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalReferenceApplication()
 * @model
 * @generated
 */
public interface LocalReferenceApplication extends EObject {
	/**
	 * Returns the value of the '<em><b>Local Field</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.t24.localReferenceApplication.LocalField}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Field</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Field</em>' containment reference list.
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalReferenceApplication_LocalField()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<LocalField> getLocalField();

	/**
	 * Returns the value of the '<em><b>For Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For Application</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Application</em>' reference.
	 * @see #setForApplication(MdfClass)
	 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage#getLocalReferenceApplication_ForApplication()
	 * @model
	 * @generated
	 */
	MdfClass getForApplication();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.localReferenceApplication.LocalReferenceApplication#getForApplication <em>For Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For Application</em>' reference.
	 * @see #getForApplication()
	 * @generated
	 */
	void setForApplication(MdfClass value);

} // LocalReferenceApplication
