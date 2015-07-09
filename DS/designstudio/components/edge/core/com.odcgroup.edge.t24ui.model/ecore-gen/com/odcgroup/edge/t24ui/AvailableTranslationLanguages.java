/**
 */
package com.odcgroup.edge.t24ui;

import com.odcgroup.edge.t24ui.common.Language;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Available Translation Languages</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.AvailableTranslationLanguages#getLanguages <em>Languages</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.T24UIPackage#getAvailableTranslationLanguages()
 * @model
 * @generated
 */
public interface AvailableTranslationLanguages extends EObject {
	/**
	 * Returns the value of the '<em><b>Languages</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.common.Language}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Languages</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Languages</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getAvailableTranslationLanguages_Languages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Language> getLanguages();

} // AvailableTranslationLanguages
