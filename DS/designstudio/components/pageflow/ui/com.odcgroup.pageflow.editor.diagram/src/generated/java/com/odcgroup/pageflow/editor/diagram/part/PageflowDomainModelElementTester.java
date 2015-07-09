/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated
 */
public class PageflowDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args, Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == PageflowPackage.eINSTANCE.getEndState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getPageflow()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getInitState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getAction()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getProperty()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getTransition()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getViewState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getView()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getDecisionState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getTransitionAction()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getDecisionAction()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getSubPageflowState()) {
			return true;
		}
		if (eClass == PageflowPackage.eINSTANCE.getTransitionMapping()) {
			return true;
		}
		return false;
	}

}
