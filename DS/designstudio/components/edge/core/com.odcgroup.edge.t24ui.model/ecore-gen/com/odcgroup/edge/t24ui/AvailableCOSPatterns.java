/**
 */
package com.odcgroup.edge.t24ui;

import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Available COS Patterns</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.AvailableCOSPatterns#getPatterns <em>Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.T24UIPackage#getAvailableCOSPatterns()
 * @model
 * @generated
 */
public interface AvailableCOSPatterns extends EObject {
	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.cos.pattern.COSPattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getAvailableCOSPatterns_Patterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<COSPattern> getPatterns();

} // AvailableCOSPatterns
