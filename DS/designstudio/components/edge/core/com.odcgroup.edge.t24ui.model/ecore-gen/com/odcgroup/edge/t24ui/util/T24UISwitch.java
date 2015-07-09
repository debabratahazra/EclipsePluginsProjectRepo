/**
 */
package com.odcgroup.edge.t24ui.util;

import com.odcgroup.edge.t24ui.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.odcgroup.edge.t24ui.AvailableCOSPatterns;
import com.odcgroup.edge.t24ui.AvailableTranslationLanguages;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;

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
 * @see com.odcgroup.edge.t24ui.T24UIPackage
 * @generated
 */
public class T24UISwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static T24UIPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T24UISwitch() {
		if (modelPackage == null) {
			modelPackage = T24UIPackage.eINSTANCE;
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
			case T24UIPackage.AVAILABLE_COS_PATTERNS: {
				AvailableCOSPatterns availableCOSPatterns = (AvailableCOSPatterns)theEObject;
				T result = caseAvailableCOSPatterns(availableCOSPatterns);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case T24UIPackage.COMPOSITE_SCREEN: {
				CompositeScreen compositeScreen = (CompositeScreen)theEObject;
				T result = caseCompositeScreen(compositeScreen);
				if (result == null) result = caseAbstractCOS(compositeScreen);
				if (result == null) result = caseCOSPatternContainer(compositeScreen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN: {
				BespokeCompositeScreen bespokeCompositeScreen = (BespokeCompositeScreen)theEObject;
				T result = caseBespokeCompositeScreen(bespokeCompositeScreen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES: {
				AvailableTranslationLanguages availableTranslationLanguages = (AvailableTranslationLanguages)theEObject;
				T result = caseAvailableTranslationLanguages(availableTranslationLanguages);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Available COS Patterns</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Available COS Patterns</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAvailableCOSPatterns(AvailableCOSPatterns object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite Screen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite Screen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeScreen(CompositeScreen object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bespoke Composite Screen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bespoke Composite Screen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBespokeCompositeScreen(BespokeCompositeScreen object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Available Translation Languages</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Available Translation Languages</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAvailableTranslationLanguages(AvailableTranslationLanguages object) {
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

} //T24UISwitch
