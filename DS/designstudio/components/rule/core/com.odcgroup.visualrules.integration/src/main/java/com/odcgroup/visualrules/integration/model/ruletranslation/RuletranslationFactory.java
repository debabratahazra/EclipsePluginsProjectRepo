/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage
 * @generated
 */
public interface RuletranslationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuletranslationFactory eINSTANCE = com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Translation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Translation</em>'.
	 * @generated
	 */
	Translation createTranslation();

	/**
	 * Returns a new object of class '<em>Rule Message Proxy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Message Proxy</em>'.
	 * @generated
	 */
	RuleMessageProxy createRuleMessageProxy();

	/**
	 * Returns a new object of class '<em>Rule Translation Repo</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Translation Repo</em>'.
	 * @generated
	 */
	RuleTranslationRepo createRuleTranslationRepo();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RuletranslationPackage getRuletranslationPackage();

} //RuletranslationFactory
