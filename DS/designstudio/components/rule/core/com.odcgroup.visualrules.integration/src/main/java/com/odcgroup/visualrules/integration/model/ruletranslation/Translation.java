/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Translation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getMessage <em>Message</em>}</li>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getTranslation()
 * @model
 * @generated
 */
public interface Translation extends EObject {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getTranslation_Language()
	 * @model required="true"
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getTranslation_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind
	 * @see #setKind(TranslationKind)
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getTranslation_Kind()
	 * @model required="true"
	 * @generated
	 */
	TranslationKind getKind();

	/**
	 * Sets the value of the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(TranslationKind value);

} // Translation
