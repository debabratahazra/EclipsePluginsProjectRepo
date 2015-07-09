/**
 */
package com.odcgroup.t24.application.internal.localref;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vetting Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.VettingTable#getVettingTable <em>Vetting Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.VettingTable#getRemarks <em>Remarks</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getVettingTable()
 * @model
 * @generated
 */
public interface VettingTable extends EObject {
	/**
	 * Returns the value of the '<em><b>Vetting Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vetting Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vetting Table</em>' attribute.
	 * @see #setVettingTable(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getVettingTable_VettingTable()
	 * @model required="true"
	 * @generated
	 */
	String getVettingTable();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.VettingTable#getVettingTable <em>Vetting Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vetting Table</em>' attribute.
	 * @see #getVettingTable()
	 * @generated
	 */
	void setVettingTable(String value);

	/**
	 * Returns the value of the '<em><b>Remarks</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.translation.translationDsl.Translations}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remarks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remarks</em>' containment reference list.
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getVettingTable_Remarks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translations> getRemarks();

} // VettingTable
