/**
 */
package com.odcgroup.t24.application.internal.localref;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage
 * @generated
 */
public interface LocalrefFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LocalrefFactory eINSTANCE = com.odcgroup.t24.application.internal.localref.impl.LocalrefFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Local Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Ref</em>'.
	 * @generated
	 */
	LocalRef createLocalRef();

	/**
	 * Returns a new object of class '<em>Vetting Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vetting Table</em>'.
	 * @generated
	 */
	VettingTable createVettingTable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LocalrefPackage getLocalrefPackage();

} //LocalrefFactory
