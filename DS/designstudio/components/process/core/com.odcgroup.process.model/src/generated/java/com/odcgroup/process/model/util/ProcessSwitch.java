/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.process.model.Assignee;
import com.odcgroup.process.model.ComplexGateway;
import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.ExclusiveMerge;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.NamedElement;
import com.odcgroup.process.model.Pageflow;
import com.odcgroup.process.model.ParallelFork;
import com.odcgroup.process.model.ParallelMerge;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Property;
import com.odcgroup.process.model.Rule;
import com.odcgroup.process.model.Script;
import com.odcgroup.process.model.Service;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.Translation;
import com.odcgroup.process.model.UserTask;

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
 * @see com.odcgroup.process.model.ProcessPackage
 * @generated
 */
public class ProcessSwitch {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProcessPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessSwitch() {
		if (modelPackage == null) {
			modelPackage = ProcessPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ProcessPackage.PROCESS: {
				com.odcgroup.process.model.Process process = (com.odcgroup.process.model.Process)theEObject;
				Object result = caseProcess(process);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.NAMED_ELEMENT: {
				NamedElement namedElement = (NamedElement)theEObject;
				Object result = caseNamedElement(namedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.PROCESS_ITEM: {
				ProcessItem processItem = (ProcessItem)theEObject;
				Object result = caseProcessItem(processItem);
				if (result == null) result = caseNamedElement(processItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.POOL: {
				Pool pool = (Pool)theEObject;
				Object result = casePool(pool);
				if (result == null) result = caseNamedElement(pool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.START_EVENT: {
				StartEvent startEvent = (StartEvent)theEObject;
				Object result = caseStartEvent(startEvent);
				if (result == null) result = caseProcessItem(startEvent);
				if (result == null) result = caseNamedElement(startEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.END_EVENT: {
				EndEvent endEvent = (EndEvent)theEObject;
				Object result = caseEndEvent(endEvent);
				if (result == null) result = caseProcessItem(endEvent);
				if (result == null) result = caseNamedElement(endEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.TASK: {
				Task task = (Task)theEObject;
				Object result = caseTask(task);
				if (result == null) result = caseProcessItem(task);
				if (result == null) result = caseNamedElement(task);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.USER_TASK: {
				UserTask userTask = (UserTask)theEObject;
				Object result = caseUserTask(userTask);
				if (result == null) result = caseTask(userTask);
				if (result == null) result = caseProcessItem(userTask);
				if (result == null) result = caseNamedElement(userTask);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.SERVICE_TASK: {
				ServiceTask serviceTask = (ServiceTask)theEObject;
				Object result = caseServiceTask(serviceTask);
				if (result == null) result = caseTask(serviceTask);
				if (result == null) result = caseProcessItem(serviceTask);
				if (result == null) result = caseNamedElement(serviceTask);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.GATEWAY: {
				Gateway gateway = (Gateway)theEObject;
				Object result = caseGateway(gateway);
				if (result == null) result = caseProcessItem(gateway);
				if (result == null) result = caseNamedElement(gateway);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.FLOW: {
				Flow flow = (Flow)theEObject;
				Object result = caseFlow(flow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.COMPLEX_GATEWAY: {
				ComplexGateway complexGateway = (ComplexGateway)theEObject;
				Object result = caseComplexGateway(complexGateway);
				if (result == null) result = caseGateway(complexGateway);
				if (result == null) result = caseProcessItem(complexGateway);
				if (result == null) result = caseNamedElement(complexGateway);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.EXCLUSIVE_MERGE: {
				ExclusiveMerge exclusiveMerge = (ExclusiveMerge)theEObject;
				Object result = caseExclusiveMerge(exclusiveMerge);
				if (result == null) result = caseGateway(exclusiveMerge);
				if (result == null) result = caseProcessItem(exclusiveMerge);
				if (result == null) result = caseNamedElement(exclusiveMerge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.PARALLEL_FORK: {
				ParallelFork parallelFork = (ParallelFork)theEObject;
				Object result = caseParallelFork(parallelFork);
				if (result == null) result = caseGateway(parallelFork);
				if (result == null) result = caseProcessItem(parallelFork);
				if (result == null) result = caseNamedElement(parallelFork);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.PARALLEL_MERGE: {
				ParallelMerge parallelMerge = (ParallelMerge)theEObject;
				Object result = caseParallelMerge(parallelMerge);
				if (result == null) result = caseGateway(parallelMerge);
				if (result == null) result = caseProcessItem(parallelMerge);
				if (result == null) result = caseNamedElement(parallelMerge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.ASSIGNEE: {
				Assignee assignee = (Assignee)theEObject;
				Object result = caseAssignee(assignee);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.PAGEFLOW: {
				Pageflow pageflow = (Pageflow)theEObject;
				Object result = casePageflow(pageflow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.SERVICE: {
				Service service = (Service)theEObject;
				Object result = caseService(service);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.RULE: {
				Rule rule = (Rule)theEObject;
				Object result = caseRule(rule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.SCRIPT: {
				Script script = (Script)theEObject;
				Object result = caseScript(script);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.PROPERTY: {
				Property property = (Property)theEObject;
				Object result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessPackage.TRANSLATION: {
				Translation translation = (Translation)theEObject;
				Object result = caseTranslation(translation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProcess(com.odcgroup.process.model.Process object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProcessItem(ProcessItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pool</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePool(Pool object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Start Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStartEvent(StartEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>End Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>End Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEndEvent(EndEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTask(Task object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User Task</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUserTask(UserTask object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Task</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseServiceTask(ServiceTask object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Gateway</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Gateway</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGateway(Gateway object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFlow(Flow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Complex Gateway</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Complex Gateway</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComplexGateway(ComplexGateway object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exclusive Merge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exclusive Merge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExclusiveMerge(ExclusiveMerge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parallel Fork</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parallel Fork</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParallelFork(ParallelFork object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parallel Merge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parallel Merge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParallelMerge(ParallelMerge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assignee</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assignee</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAssignee(Assignee object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pageflow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pageflow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePageflow(Pageflow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseService(Service object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Script</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Script</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseScript(Script object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Translation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Translation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTranslation(Translation object) {
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
	public Object defaultCase(EObject object) {
		return null;
	}

} //ProcessSwitch
