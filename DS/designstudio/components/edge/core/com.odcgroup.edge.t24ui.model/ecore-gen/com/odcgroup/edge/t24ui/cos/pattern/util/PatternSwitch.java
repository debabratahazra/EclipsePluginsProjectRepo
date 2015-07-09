/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.util;

import com.odcgroup.edge.t24ui.cos.pattern.*;

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
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage
 * @generated
 */
public class PatternSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PatternPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternSwitch() {
		if (modelPackage == null) {
			modelPackage = PatternPackage.eINSTANCE;
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
			case PatternPackage.COS_PANEL: {
				COSPanel cosPanel = (COSPanel)theEObject;
				T result = caseCOSPanel(cosPanel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.SINGLE_COMPONENT_PANEL: {
				SingleComponentPanel singleComponentPanel = (SingleComponentPanel)theEObject;
				T result = caseSingleComponentPanel(singleComponentPanel);
				if (result == null) result = caseCOSPanel(singleComponentPanel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.MULTI_COMPONENT_PANEL: {
				MultiComponentPanel multiComponentPanel = (MultiComponentPanel)theEObject;
				T result = caseMultiComponentPanel(multiComponentPanel);
				if (result == null) result = caseChildResourceSpec(multiComponentPanel);
				if (result == null) result = caseCOSPanel(multiComponentPanel);
				if (result == null) result = caseCOSPatternContainer(multiComponentPanel);
				if (result == null) result = caseInitialPanelContentSpec(multiComponentPanel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_PANEL_CONTENT_SPEC: {
				InitialPanelContentSpec initialPanelContentSpec = (InitialPanelContentSpec)theEObject;
				T result = caseInitialPanelContentSpec(initialPanelContentSpec);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_COS: {
				InitialCOS initialCOS = (InitialCOS)theEObject;
				T result = caseInitialCOS(initialCOS);
				if (result == null) result = caseChildResourceSpec(initialCOS);
				if (result == null) result = caseInitialPanelContentSpec(initialCOS);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INLINE_INITIAL_COS: {
				InlineInitialCOS inlineInitialCOS = (InlineInitialCOS)theEObject;
				T result = caseInlineInitialCOS(inlineInitialCOS);
				if (result == null) result = caseAbstractCOS(inlineInitialCOS);
				if (result == null) result = caseChildResourceSpec(inlineInitialCOS);
				if (result == null) result = caseCOSPatternContainer(inlineInitialCOS);
				if (result == null) result = caseInitialPanelContentSpec(inlineInitialCOS);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_BESPOKE_COS: {
				InitialBespokeCOS initialBespokeCOS = (InitialBespokeCOS)theEObject;
				T result = caseInitialBespokeCOS(initialBespokeCOS);
				if (result == null) result = caseChildResourceSpec(initialBespokeCOS);
				if (result == null) result = caseInitialPanelContentSpec(initialBespokeCOS);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_ENQUIRY: {
				InitialEnquiry initialEnquiry = (InitialEnquiry)theEObject;
				T result = caseInitialEnquiry(initialEnquiry);
				if (result == null) result = caseChildResourceSpec(initialEnquiry);
				if (result == null) result = caseInitialPanelContentSpec(initialEnquiry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_SCREEN: {
				InitialScreen initialScreen = (InitialScreen)theEObject;
				T result = caseInitialScreen(initialScreen);
				if (result == null) result = caseChildResourceSpec(initialScreen);
				if (result == null) result = caseInitialPanelContentSpec(initialScreen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.ABSTRACT_COS: {
				AbstractCOS abstractCOS = (AbstractCOS)theEObject;
				T result = caseAbstractCOS(abstractCOS);
				if (result == null) result = caseCOSPatternContainer(abstractCOS);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.COS_PATTERN_CONTAINER: {
				COSPatternContainer cosPatternContainer = (COSPatternContainer)theEObject;
				T result = caseCOSPatternContainer(cosPatternContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.COS_PATTERN: {
				COSPattern cosPattern = (COSPattern)theEObject;
				T result = caseCOSPattern(cosPattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.CHILD_RESOURCE_SPEC: {
				ChildResourceSpec childResourceSpec = (ChildResourceSpec)theEObject;
				T result = caseChildResourceSpec(childResourceSpec);
				if (result == null) result = caseInitialPanelContentSpec(childResourceSpec);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PatternPackage.INITIAL_URL: {
				InitialURL initialURL = (InitialURL)theEObject;
				T result = caseInitialURL(initialURL);
				if (result == null) result = caseInitialPanelContentSpec(initialURL);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>COS Panel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>COS Panel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCOSPanel(COSPanel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single Component Panel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single Component Panel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleComponentPanel(SingleComponentPanel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multi Component Panel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multi Component Panel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiComponentPanel(MultiComponentPanel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Panel Content Spec</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Panel Content Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitialPanelContentSpec(InitialPanelContentSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial COS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial COS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitialCOS(InitialCOS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inline Initial COS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inline Initial COS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInlineInitialCOS(InlineInitialCOS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Bespoke COS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Bespoke COS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitialBespokeCOS(InitialBespokeCOS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Enquiry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Enquiry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitialEnquiry(InitialEnquiry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Screen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Screen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitialScreen(InitialScreen object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>COS Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>COS Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCOSPattern(COSPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Child Resource Spec</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Child Resource Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChildResourceSpec(ChildResourceSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial URL</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial URL</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseInitialURL(InitialURL object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract COS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract COS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractCOS(AbstractCOS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>COS Pattern Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>COS Pattern Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCOSPatternContainer(COSPatternContainer object) {
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

} //PatternSwitch
