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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getFromState <em>From State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getToState <em>To State</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.TransitionImpl#isIsIdempotent <em>Is Idempotent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends MinimalEObjectImpl.Container implements Transition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList actions;

	/**
	 * The default value of the '{@link #getDesc() <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesc()
	 * @generated
	 * @ordered
	 */
	protected static final String DESC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDesc() <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesc()
	 * @generated
	 * @ordered
	 */
	protected String desc = DESC_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFromState() <em>From State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromState()
	 * @generated
	 * @ordered
	 */
	protected State fromState;

	/**
	 * The cached value of the '{@link #getToState() <em>To State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToState()
	 * @generated
	 * @ordered
	 */
	protected State toState;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

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
	 * The default value of the '{@link #isIsIdempotent() <em>Is Idempotent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsIdempotent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_IDEMPOTENT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsIdempotent() <em>Is Idempotent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsIdempotent()
	 * @generated
	 * @ordered
	 */
	protected boolean isIdempotent = IS_IDEMPOTENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList(TransitionAction.class, this, PageflowPackage.TRANSITION__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesc(String newDesc) {
		String oldDesc = desc;
		desc = newDesc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__DESC, oldDesc, desc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getFromState() {
		if (fromState != null && fromState.eIsProxy()) {
			InternalEObject oldFromState = (InternalEObject)fromState;
			fromState = (State)eResolveProxy(oldFromState);
			if (fromState != oldFromState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PageflowPackage.TRANSITION__FROM_STATE, oldFromState, fromState));
			}
		}
		return fromState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetFromState() {
		return fromState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromState(State newFromState) {
		State oldFromState = fromState;
		fromState = newFromState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__FROM_STATE, oldFromState, fromState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getToState() {
		if (toState != null && toState.eIsProxy()) {
			InternalEObject oldToState = (InternalEObject)toState;
			toState = (State)eResolveProxy(oldToState);
			if (toState != oldToState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PageflowPackage.TRANSITION__TO_STATE, oldToState, toState));
			}
		}
		return toState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetToState() {
		return toState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToState(State newToState) {
		State oldToState = toState;
		toState = newToState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__TO_STATE, oldToState, toState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__DISPLAY_NAME, oldDisplayName, displayName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsIdempotent() {
		return isIdempotent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsIdempotent(boolean newIsIdempotent) {
		boolean oldIsIdempotent = isIdempotent;
		isIdempotent = newIsIdempotent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.TRANSITION__IS_IDEMPOTENT, oldIsIdempotent, isIdempotent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PageflowPackage.TRANSITION__ACTIONS:
				return ((InternalEList)getActions()).basicRemove(otherEnd, msgs);
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
			case PageflowPackage.TRANSITION__ACTIONS:
				return getActions();
			case PageflowPackage.TRANSITION__DESC:
				return getDesc();
			case PageflowPackage.TRANSITION__NAME:
				return getName();
			case PageflowPackage.TRANSITION__FROM_STATE:
				if (resolve) return getFromState();
				return basicGetFromState();
			case PageflowPackage.TRANSITION__TO_STATE:
				if (resolve) return getToState();
				return basicGetToState();
			case PageflowPackage.TRANSITION__DISPLAY_NAME:
				return getDisplayName();
			case PageflowPackage.TRANSITION__TECH_DESC:
				return getTechDesc();
			case PageflowPackage.TRANSITION__IS_IDEMPOTENT:
				return isIsIdempotent() ? Boolean.TRUE : Boolean.FALSE;
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
			case PageflowPackage.TRANSITION__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection)newValue);
				return;
			case PageflowPackage.TRANSITION__DESC:
				setDesc((String)newValue);
				return;
			case PageflowPackage.TRANSITION__NAME:
				setName((String)newValue);
				return;
			case PageflowPackage.TRANSITION__FROM_STATE:
				setFromState((State)newValue);
				return;
			case PageflowPackage.TRANSITION__TO_STATE:
				setToState((State)newValue);
				return;
			case PageflowPackage.TRANSITION__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case PageflowPackage.TRANSITION__TECH_DESC:
				setTechDesc((String)newValue);
				return;
			case PageflowPackage.TRANSITION__IS_IDEMPOTENT:
				setIsIdempotent(((Boolean)newValue).booleanValue());
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
			case PageflowPackage.TRANSITION__ACTIONS:
				getActions().clear();
				return;
			case PageflowPackage.TRANSITION__DESC:
				setDesc(DESC_EDEFAULT);
				return;
			case PageflowPackage.TRANSITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PageflowPackage.TRANSITION__FROM_STATE:
				setFromState((State)null);
				return;
			case PageflowPackage.TRANSITION__TO_STATE:
				setToState((State)null);
				return;
			case PageflowPackage.TRANSITION__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case PageflowPackage.TRANSITION__TECH_DESC:
				setTechDesc(TECH_DESC_EDEFAULT);
				return;
			case PageflowPackage.TRANSITION__IS_IDEMPOTENT:
				setIsIdempotent(IS_IDEMPOTENT_EDEFAULT);
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
			case PageflowPackage.TRANSITION__ACTIONS:
				return actions != null && !actions.isEmpty();
			case PageflowPackage.TRANSITION__DESC:
				return DESC_EDEFAULT == null ? desc != null : !DESC_EDEFAULT.equals(desc);
			case PageflowPackage.TRANSITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PageflowPackage.TRANSITION__FROM_STATE:
				return fromState != null;
			case PageflowPackage.TRANSITION__TO_STATE:
				return toState != null;
			case PageflowPackage.TRANSITION__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case PageflowPackage.TRANSITION__TECH_DESC:
				return TECH_DESC_EDEFAULT == null ? techDesc != null : !TECH_DESC_EDEFAULT.equals(techDesc);
			case PageflowPackage.TRANSITION__IS_IDEMPOTENT:
				return isIdempotent != IS_IDEMPOTENT_EDEFAULT;
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
		result.append(" (desc: ");
		result.append(desc);
		result.append(", name: ");
		result.append(name);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", techDesc: ");
		result.append(techDesc);
		result.append(", isIdempotent: ");
		result.append(isIdempotent);
		result.append(')');
		return result.toString();
	}

} //TransitionImpl
