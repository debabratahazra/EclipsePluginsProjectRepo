/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.process.model.Assignee;
import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Service;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pool</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getAssignee <em>Assignee</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getAssigneeByService <em>Assignee By Service</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getStart <em>Start</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getEnd <em>End</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.PoolImpl#getGateways <em>Gateways</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PoolImpl extends NamedElementImpl implements Pool {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The default value of the '{@link #getTechDesc() <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTechDesc()
	 * @generated
	 * @ordered
	 */
	protected static final String TECH_DESC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTechDesc() <em>Tech Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTechDesc()
	 * @generated
	 * @ordered
	 */
	protected String techDesc = TECH_DESC_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAssignee() <em>Assignee</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignee()
	 * @generated
	 * @ordered
	 */
	protected EList assignee;

	/**
	 * The cached value of the '{@link #getAssigneeByService() <em>Assignee By Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssigneeByService()
	 * @generated
	 * @ordered
	 */
	protected Service assigneeByService;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected StartEvent start;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected EList end;

	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList tasks;

	/**
	 * The cached value of the '{@link #getGateways() <em>Gateways</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGateways()
	 * @generated
	 * @ordered
	 */
	protected EList gateways;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PoolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ProcessPackage.Literals.POOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTechDesc() {
		return techDesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTechDesc(String newTechDesc) {
		String oldTechDesc = techDesc;
		techDesc = newTechDesc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.POOL__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAssignee() {
		if (assignee == null) {
			assignee = new EObjectContainmentEList(Assignee.class, this, ProcessPackage.POOL__ASSIGNEE);
		}
		return assignee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Service getAssigneeByService() {
		return assigneeByService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssigneeByService(Service newAssigneeByService, NotificationChain msgs) {
		Service oldAssigneeByService = assigneeByService;
		assigneeByService = newAssigneeByService;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.POOL__ASSIGNEE_BY_SERVICE, oldAssigneeByService, newAssigneeByService);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssigneeByService(Service newAssigneeByService) {
		if (newAssigneeByService != assigneeByService) {
			NotificationChain msgs = null;
			if (assigneeByService != null)
				msgs = ((InternalEObject)assigneeByService).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.POOL__ASSIGNEE_BY_SERVICE, null, msgs);
			if (newAssigneeByService != null)
				msgs = ((InternalEObject)newAssigneeByService).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.POOL__ASSIGNEE_BY_SERVICE, null, msgs);
			msgs = basicSetAssigneeByService(newAssigneeByService, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.POOL__ASSIGNEE_BY_SERVICE, newAssigneeByService, newAssigneeByService));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartEvent getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStart(StartEvent newStart, NotificationChain msgs) {
		StartEvent oldStart = start;
		start = newStart;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.POOL__START, oldStart, newStart);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(StartEvent newStart) {
		if (newStart != start) {
			NotificationChain msgs = null;
			if (start != null)
				msgs = ((InternalEObject)start).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.POOL__START, null, msgs);
			if (newStart != null)
				msgs = ((InternalEObject)newStart).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.POOL__START, null, msgs);
			msgs = basicSetStart(newStart, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.POOL__START, newStart, newStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEnd() {
		if (end == null) {
			end = new EObjectContainmentEList(EndEvent.class, this, ProcessPackage.POOL__END);
		}
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList(Task.class, this, ProcessPackage.POOL__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGateways() {
		if (gateways == null) {
			gateways = new EObjectContainmentEList(Gateway.class, this, ProcessPackage.POOL__GATEWAYS);
		}
		return gateways;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessPackage.POOL__ASSIGNEE:
				return ((InternalEList)getAssignee()).basicRemove(otherEnd, msgs);
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
				return basicSetAssigneeByService(null, msgs);
			case ProcessPackage.POOL__START:
				return basicSetStart(null, msgs);
			case ProcessPackage.POOL__END:
				return ((InternalEList)getEnd()).basicRemove(otherEnd, msgs);
			case ProcessPackage.POOL__TASKS:
				return ((InternalEList)getTasks()).basicRemove(otherEnd, msgs);
			case ProcessPackage.POOL__GATEWAYS:
				return ((InternalEList)getGateways()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProcessPackage.POOL__TECH_DESC:
				return getTechDesc();
			case ProcessPackage.POOL__ASSIGNEE:
				return getAssignee();
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
				return getAssigneeByService();
			case ProcessPackage.POOL__START:
				return getStart();
			case ProcessPackage.POOL__END:
				return getEnd();
			case ProcessPackage.POOL__TASKS:
				return getTasks();
			case ProcessPackage.POOL__GATEWAYS:
				return getGateways();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProcessPackage.POOL__TECH_DESC:
				setTechDesc((String)newValue);
				return;
			case ProcessPackage.POOL__ASSIGNEE:
				getAssignee().clear();
				getAssignee().addAll((Collection)newValue);
				return;
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
				setAssigneeByService((Service)newValue);
				return;
			case ProcessPackage.POOL__START:
				setStart((StartEvent)newValue);
				return;
			case ProcessPackage.POOL__END:
				getEnd().clear();
				getEnd().addAll((Collection)newValue);
				return;
			case ProcessPackage.POOL__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection)newValue);
				return;
			case ProcessPackage.POOL__GATEWAYS:
				getGateways().clear();
				getGateways().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProcessPackage.POOL__TECH_DESC:
				setTechDesc(TECH_DESC_EDEFAULT);
				return;
			case ProcessPackage.POOL__ASSIGNEE:
				getAssignee().clear();
				return;
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
				setAssigneeByService((Service)null);
				return;
			case ProcessPackage.POOL__START:
				setStart((StartEvent)null);
				return;
			case ProcessPackage.POOL__END:
				getEnd().clear();
				return;
			case ProcessPackage.POOL__TASKS:
				getTasks().clear();
				return;
			case ProcessPackage.POOL__GATEWAYS:
				getGateways().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProcessPackage.POOL__TECH_DESC:
				return TECH_DESC_EDEFAULT == null ? techDesc != null : !TECH_DESC_EDEFAULT.equals(techDesc);
			case ProcessPackage.POOL__ASSIGNEE:
				return assignee != null && !assignee.isEmpty();
			case ProcessPackage.POOL__ASSIGNEE_BY_SERVICE:
				return assigneeByService != null;
			case ProcessPackage.POOL__START:
				return start != null;
			case ProcessPackage.POOL__END:
				return end != null && !end.isEmpty();
			case ProcessPackage.POOL__TASKS:
				return tasks != null && !tasks.isEmpty();
			case ProcessPackage.POOL__GATEWAYS:
				return gateways != null && !gateways.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (techDesc: ");
		result.append(techDesc);
		result.append(')');
		return result.toString();
	}

} //PoolImpl
