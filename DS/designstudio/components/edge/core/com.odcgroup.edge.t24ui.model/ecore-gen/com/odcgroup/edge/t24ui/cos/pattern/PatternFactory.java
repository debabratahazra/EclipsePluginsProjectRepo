/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage
 * @generated
 */
public interface PatternFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PatternFactory eINSTANCE = com.odcgroup.edge.t24ui.cos.pattern.impl.PatternFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Single Component Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Single Component Panel</em>'.
	 * @generated
	 */
	SingleComponentPanel createSingleComponentPanel();

	/**
	 * Returns a new object of class '<em>Multi Component Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multi Component Panel</em>'.
	 * @generated
	 */
	MultiComponentPanel createMultiComponentPanel();

	/**
	 * Returns a new object of class '<em>Initial COS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial COS</em>'.
	 * @generated
	 */
	InitialCOS createInitialCOS();

	/**
	 * Returns a new object of class '<em>Inline Initial COS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inline Initial COS</em>'.
	 * @generated
	 */
	InlineInitialCOS createInlineInitialCOS();

	/**
	 * Returns a new object of class '<em>Initial Bespoke COS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Bespoke COS</em>'.
	 * @generated
	 */
	InitialBespokeCOS createInitialBespokeCOS();

	/**
	 * Returns a new object of class '<em>Initial Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Enquiry</em>'.
	 * @generated
	 */
	InitialEnquiry createInitialEnquiry();

	/**
	 * Returns a new object of class '<em>Initial Screen</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Screen</em>'.
	 * @generated
	 */
	InitialScreen createInitialScreen();

	/**
	 * Returns a new object of class '<em>COS Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>COS Pattern</em>'.
	 * @generated
	 */
	COSPattern createCOSPattern();

	/**
	 * Returns a new object of class '<em>Initial URL</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial URL</em>'.
	 * @generated
	 */
    InitialURL createInitialURL();

    /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PatternPackage getPatternPackage();

} //PatternFactory
