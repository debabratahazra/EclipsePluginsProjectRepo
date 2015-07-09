/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.TransitionMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sub Pageflow State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.SubPageflowStateImpl#getSubPageflow <em>Sub Pageflow</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.SubPageflowStateImpl#getTransitionMappings <em>Transition Mappings</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.SubPageflowStateImpl#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubPageflowStateImpl extends StateImpl implements SubPageflowState {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getSubPageflow() <em>Sub Pageflow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubPageflow()
	 * @generated
	 * @ordered
	 */
	protected Pageflow subPageflow;

	/**
	 * The cached value of the '{@link #getTransitionMappings() <em>Transition Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionMappings()
	 * @generated
	 * @ordered
	 */
	protected EList transitionMappings;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubPageflowStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.SUB_PAGEFLOW_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pageflow getSubPageflow() {
		if (subPageflow != null && subPageflow.eIsProxy()) {
			InternalEObject oldSubPageflow = (InternalEObject)subPageflow;
			subPageflow = (Pageflow)eResolveProxy(oldSubPageflow);
			if (subPageflow != oldSubPageflow) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW, oldSubPageflow, subPageflow));
			}
		}
		return subPageflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pageflow basicGetSubPageflow() {
		return subPageflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubPageflow(Pageflow newSubPageflow) {
		Pageflow oldSubPageflow = subPageflow;
		subPageflow = newSubPageflow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW, oldSubPageflow, subPageflow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransitionMappings() {
		if (transitionMappings == null) {
			transitionMappings = new EObjectContainmentEList(TransitionMapping.class, this, PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS);
		}
		return transitionMappings;
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.SUB_PAGEFLOW_STATE__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS:
				return ((InternalEList)getTransitionMappings()).basicRemove(otherEnd, msgs);
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
			case PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW:
				if (resolve) return getSubPageflow();
				return basicGetSubPageflow();
			case PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS:
				return getTransitionMappings();
			case PageflowPackage.SUB_PAGEFLOW_STATE__TECH_DESC:
				return getTechDesc();
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
			case PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW:
				setSubPageflow((Pageflow)newValue);
				return;
			case PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS:
				getTransitionMappings().clear();
				getTransitionMappings().addAll((Collection)newValue);
				return;
			case PageflowPackage.SUB_PAGEFLOW_STATE__TECH_DESC:
				setTechDesc((String)newValue);
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
			case PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW:
				setSubPageflow((Pageflow)null);
				return;
			case PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS:
				getTransitionMappings().clear();
				return;
			case PageflowPackage.SUB_PAGEFLOW_STATE__TECH_DESC:
				setTechDesc(TECH_DESC_EDEFAULT);
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
			case PageflowPackage.SUB_PAGEFLOW_STATE__SUB_PAGEFLOW:
				return subPageflow != null;
			case PageflowPackage.SUB_PAGEFLOW_STATE__TRANSITION_MAPPINGS:
				return transitionMappings != null && !transitionMappings.isEmpty();
			case PageflowPackage.SUB_PAGEFLOW_STATE__TECH_DESC:
				return TECH_DESC_EDEFAULT == null ? techDesc != null : !TECH_DESC_EDEFAULT.equals(techDesc);
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

} //SubPageflowStateImpl
