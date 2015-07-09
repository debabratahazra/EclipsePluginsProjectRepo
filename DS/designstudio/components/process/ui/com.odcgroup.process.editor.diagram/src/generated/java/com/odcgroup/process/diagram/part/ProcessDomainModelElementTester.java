/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class ProcessDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args, Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == ProcessPackage.eINSTANCE.getProcess()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getNamedElement()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getProcessItem()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getPool()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getStartEvent()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getEndEvent()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getTask()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getUserTask()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getServiceTask()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getGateway()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getFlow()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getComplexGateway()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getExclusiveMerge()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getParallelFork()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getParallelMerge()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getAssignee()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getPageflow()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getService()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getRule()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getScript()) {
			return true;
		}
		if (eClass == ProcessPackage.eINSTANCE.getProperty()) {
			return true;
		}
		return false;
	}

}
