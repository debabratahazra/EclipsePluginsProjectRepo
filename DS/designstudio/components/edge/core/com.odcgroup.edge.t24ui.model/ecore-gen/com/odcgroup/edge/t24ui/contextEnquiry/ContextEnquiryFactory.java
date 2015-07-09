/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage
 * @generated
 */
public interface ContextEnquiryFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ContextEnquiryFactory eINSTANCE = com.odcgroup.edge.t24ui.contextEnquiry.impl.ContextEnquiryFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Description</em>'.
	 * @generated
	 */
	Description createDescription();

	/**
	 * Returns a new object of class '<em>Selection Criteria</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Selection Criteria</em>'.
	 * @generated
	 */
	SelectionCriteria createSelectionCriteria();

	/**
	 * Returns a new object of class '<em>Applied Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Applied Enquiry</em>'.
	 * @generated
	 */
	AppliedEnquiry createAppliedEnquiry();

	/**
	 * Returns a new object of class '<em>Application Context Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Context Enquiry</em>'.
	 * @generated
	 */
	ApplicationContextEnquiry createApplicationContextEnquiry();

	/**
	 * Returns a new object of class '<em>Version Context Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Context Enquiry</em>'.
	 * @generated
	 */
	VersionContextEnquiry createVersionContextEnquiry();

	/**
	 * Returns a new object of class '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function</em>'.
	 * @generated
	 */
	Function createFunction();

	/**
	 * Returns a new object of class '<em>Function Auto Launch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Auto Launch</em>'.
	 * @generated
	 */
	FunctionAutoLaunch createFunctionAutoLaunch();

	/**
	 * Returns a new object of class '<em>On Change Auto Launch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>On Change Auto Launch</em>'.
	 * @generated
	 */
	OnChangeAutoLaunch createOnChangeAutoLaunch();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ContextEnquiryPackage getContextEnquiryPackage();

} //ContextEnquiryFactory
