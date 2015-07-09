/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Message Proxy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getCode <em>Code</em>}</li>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getRuleMessageProxy()
 * @model
 * @generated
 */
public interface RuleMessageProxy extends EObject {
	/**
	 * Returns the value of the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(String)
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getRuleMessageProxy_Code()
	 * @model required="true" transient="true"
	 * @generated
	 */
	String getCode();

	/**
	 * Sets the value of the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(String value);

	/**
	 * Returns the value of the '<em><b>Translations</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translations</em>' containment reference list.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getRuleMessageProxy_Translations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getTranslations();

} // RuleMessageProxy
