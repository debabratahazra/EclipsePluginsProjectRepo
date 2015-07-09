/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage
 * @generated
 */
public interface RulesBindingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesBindingFactory eINSTANCE = com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Mdf Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mdf Binding</em>'.
	 * @generated
	 */
	MdfBinding createMdfBinding();

	/**
	 * Returns a new object of class '<em>Validation Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Validation Rule</em>'.
	 * @generated
	 */
	ValidationRule createValidationRule();

	/**
	 * Returns a new object of class '<em>Completion Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Completion Rule</em>'.
	 * @generated
	 */
	CompletionRule createCompletionRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulesBindingPackage getRulesBindingPackage();

} //RulesBindingFactory
