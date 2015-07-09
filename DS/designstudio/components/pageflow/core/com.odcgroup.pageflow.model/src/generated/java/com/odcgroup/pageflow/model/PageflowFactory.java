/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.pageflow.model.PageflowPackage
 * @generated
 */
public interface PageflowFactory extends EFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PageflowFactory eINSTANCE = com.odcgroup.pageflow.model.impl.PageflowFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>End State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>End State</em>'.
	 * @generated
	 */
	EndState createEndState();

	/**
	 * Returns a new object of class '<em>Pageflow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pageflow</em>'.
	 * @generated
	 */
	Pageflow createPageflow();

	/**
	 * Returns a new object of class '<em>Init State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Init State</em>'.
	 * @generated
	 */
	InitState createInitState();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
	Transition createTransition();

	/**
	 * Returns a new object of class '<em>View State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>View State</em>'.
	 * @generated
	 */
	ViewState createViewState();

	/**
	 * Returns a new object of class '<em>View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>View</em>'.
	 * @generated
	 */
	View createView();

	/**
	 * Returns a new object of class '<em>Decision State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision State</em>'.
	 * @generated
	 */
	DecisionState createDecisionState();

	/**
	 * Returns a new object of class '<em>Transition Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition Action</em>'.
	 * @generated
	 */
	TransitionAction createTransitionAction();

	/**
	 * Returns a new object of class '<em>Decision Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Action</em>'.
	 * @generated
	 */
	DecisionAction createDecisionAction();

	/**
	 * Returns a new object of class '<em>Sub Pageflow State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub Pageflow State</em>'.
	 * @generated
	 */
	SubPageflowState createSubPageflowState();

	/**
	 * Returns a new object of class '<em>Transition Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition Mapping</em>'.
	 * @generated
	 */
	TransitionMapping createTransitionMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PageflowPackage getPageflowPackage();

} //PageflowFactory
