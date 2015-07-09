/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.util;

import com.odcgroup.edge.t24ui.contextEnquiry.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage
 * @generated
 */
public class ContextEnquirySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ContextEnquiryPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextEnquirySwitch() {
		if (modelPackage == null) {
			modelPackage = ContextEnquiryPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ContextEnquiryPackage.CONTEXT_ENQUIRY: {
				ContextEnquiry contextEnquiry = (ContextEnquiry)theEObject;
				T result = caseContextEnquiry(contextEnquiry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.DESCRIPTION: {
				Description description = (Description)theEObject;
				T result = caseDescription(description);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.SELECTION_CRITERIA: {
				SelectionCriteria selectionCriteria = (SelectionCriteria)theEObject;
				T result = caseSelectionCriteria(selectionCriteria);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.AUTO_LAUNCH: {
				AutoLaunch autoLaunch = (AutoLaunch)theEObject;
				T result = caseAutoLaunch(autoLaunch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.APPLIED_ENQUIRY: {
				AppliedEnquiry appliedEnquiry = (AppliedEnquiry)theEObject;
				T result = caseAppliedEnquiry(appliedEnquiry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.APPLICATION_CONTEXT_ENQUIRY: {
				ApplicationContextEnquiry applicationContextEnquiry = (ApplicationContextEnquiry)theEObject;
				T result = caseApplicationContextEnquiry(applicationContextEnquiry);
				if (result == null) result = caseContextEnquiry(applicationContextEnquiry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.VERSION_CONTEXT_ENQUIRY: {
				VersionContextEnquiry versionContextEnquiry = (VersionContextEnquiry)theEObject;
				T result = caseVersionContextEnquiry(versionContextEnquiry);
				if (result == null) result = caseContextEnquiry(versionContextEnquiry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.FUNCTION_AUTO_LAUNCH: {
				FunctionAutoLaunch functionAutoLaunch = (FunctionAutoLaunch)theEObject;
				T result = caseFunctionAutoLaunch(functionAutoLaunch);
				if (result == null) result = caseAutoLaunch(functionAutoLaunch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ContextEnquiryPackage.ON_CHANGE_AUTO_LAUNCH: {
				OnChangeAutoLaunch onChangeAutoLaunch = (OnChangeAutoLaunch)theEObject;
				T result = caseOnChangeAutoLaunch(onChangeAutoLaunch);
				if (result == null) result = caseAutoLaunch(onChangeAutoLaunch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context Enquiry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextEnquiry(ContextEnquiry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDescription(Description object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Selection Criteria</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Selection Criteria</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSelectionCriteria(SelectionCriteria object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Auto Launch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Auto Launch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAutoLaunch(AutoLaunch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Applied Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Applied Enquiry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAppliedEnquiry(AppliedEnquiry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Context Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Context Enquiry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationContextEnquiry(ApplicationContextEnquiry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Context Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Context Enquiry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionContextEnquiry(VersionContextEnquiry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Auto Launch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Auto Launch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionAutoLaunch(FunctionAutoLaunch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>On Change Auto Launch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>On Change Auto Launch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOnChangeAutoLaunch(OnChangeAutoLaunch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ContextEnquirySwitch
