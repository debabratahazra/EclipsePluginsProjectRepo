/**
 */
package com.odcgroup.edge.t24ui;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.T24UIPackage
 * @generated
 */
public interface T24UIFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	T24UIFactory eINSTANCE = com.odcgroup.edge.t24ui.impl.T24UIFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Available COS Patterns</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Available COS Patterns</em>'.
	 * @generated
	 */
	AvailableCOSPatterns createAvailableCOSPatterns();

	/**
	 * Returns a new object of class '<em>Composite Screen</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite Screen</em>'.
	 * @generated
	 */
	CompositeScreen createCompositeScreen();

	/**
	 * Returns a new object of class '<em>Bespoke Composite Screen</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bespoke Composite Screen</em>'.
	 * @generated
	 */
	BespokeCompositeScreen createBespokeCompositeScreen();

	/**
	 * Returns a new object of class '<em>Available Translation Languages</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Available Translation Languages</em>'.
	 * @generated
	 */
	AvailableTranslationLanguages createAvailableTranslationLanguages();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	T24UIPackage getT24UIPackage();

} //T24UIFactory
