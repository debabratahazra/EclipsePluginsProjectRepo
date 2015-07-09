/**
 */
package com.odcgroup.edge.t24ui;

import com.odcgroup.edge.t24ui.cos.bespoke.Section;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bespoke Composite Screen</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen#getSections <em>Sections</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.T24UIPackage#getBespokeCompositeScreen()
 * @model
 * @generated
 */
public interface BespokeCompositeScreen extends EObject {
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
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getBespokeCompositeScreen_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Sections</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.cos.bespoke.Section}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sections</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getBespokeCompositeScreen_Sections()
	 * @model containment="true"
	 * @generated
	 */
	EList<Section> getSections();

} // BespokeCompositeScreen
