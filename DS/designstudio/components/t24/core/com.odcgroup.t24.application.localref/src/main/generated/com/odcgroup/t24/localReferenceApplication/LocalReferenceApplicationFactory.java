/**
 */
package com.odcgroup.t24.localReferenceApplication;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage
 * @generated
 */
public interface LocalReferenceApplicationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LocalReferenceApplicationFactory eINSTANCE = com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Local Reference Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Reference Application</em>'.
	 * @generated
	 */
	LocalReferenceApplication createLocalReferenceApplication();

	/**
	 * Returns a new object of class '<em>Local Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Field</em>'.
	 * @generated
	 */
	LocalField createLocalField();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LocalReferenceApplicationPackage getLocalReferenceApplicationPackage();

} //LocalReferenceApplicationFactory
