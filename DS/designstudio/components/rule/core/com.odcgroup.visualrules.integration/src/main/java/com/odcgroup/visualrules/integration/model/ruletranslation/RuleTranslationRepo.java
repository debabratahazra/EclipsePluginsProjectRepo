/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Translation Repo</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo#getCodeMap <em>Code Map</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getRuleTranslationRepo()
 * @model
 * @generated
 */
public interface RuleTranslationRepo extends EObject {
	/**
	 * Returns the value of the '<em><b>Code Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Map</em>' map.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#getRuleTranslationRepo_CodeMap()
	 * @model mapType="com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationMap<org.eclipse.emf.ecore.EString, com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy>"
	 * @generated
	 */
	EMap<String, RuleMessageProxy> getCodeMap();

} // RuleTranslationRepo
