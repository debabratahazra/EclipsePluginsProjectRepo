/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage
 * @generated
 */
public interface BespokeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BespokeFactory eINSTANCE = com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Section</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Section</em>'.
	 * @generated
	 */
	Section createSection();

	/**
	 * Returns a new object of class '<em>Version Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Panel</em>'.
	 * @generated
	 */
	VersionPanel createVersionPanel();

	/**
	 * Returns a new object of class '<em>Enquiry Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enquiry Panel</em>'.
	 * @generated
	 */
	EnquiryPanel createEnquiryPanel();

	/**
	 * Returns a new object of class '<em>Application Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Panel</em>'.
	 * @generated
	 */
	ApplicationPanel createApplicationPanel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BespokePackage getBespokePackage();

} //BespokeFactory
