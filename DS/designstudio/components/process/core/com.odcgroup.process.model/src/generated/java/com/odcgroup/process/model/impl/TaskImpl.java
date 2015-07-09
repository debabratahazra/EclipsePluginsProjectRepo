/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.Translation;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.impl.TaskImpl#isInitial <em>Initial</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.TaskImpl#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TaskImpl extends ProcessItemImpl implements Task {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The default value of the '{@link #isInitial() <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitial()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INITIAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitial() <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitial()
	 * @generated
	 * @ordered
	 */
	protected boolean initial = INITIAL_EDEFAULT;

	/**
	 * This is true if the Initial attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean initialESet;

	/**
	 * The cached value of the '{@link #getTranslations() <em>Translations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslations()
	 * @generated
	 * @ordered
	 */
	protected EList translations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ProcessPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInitial() {
		return initial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitial(boolean newInitial) {
		boolean oldInitial = initial;
		initial = newInitial;
		boolean oldInitialESet = initialESet;
		initialESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.TASK__INITIAL, oldInitial, initial, !oldInitialESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInitial() {
		boolean oldInitial = initial;
		boolean oldInitialESet = initialESet;
		initial = INITIAL_EDEFAULT;
		initialESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProcessPackage.TASK__INITIAL, oldInitial, INITIAL_EDEFAULT, oldInitialESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInitial() {
		return initialESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTranslations() {
		if (translations == null) {
			translations = new EObjectContainmentEList(Translation.class, this, ProcessPackage.TASK__TRANSLATIONS);
		}
		return translations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessPackage.TASK__TRANSLATIONS:
				return ((InternalEList)getTranslations()).basicRemove(otherEnd, msgs);
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
			case ProcessPackage.TASK__INITIAL:
				return isInitial() ? Boolean.TRUE : Boolean.FALSE;
			case ProcessPackage.TASK__TRANSLATIONS:
				return getTranslations();
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
			case ProcessPackage.TASK__INITIAL:
				setInitial(((Boolean)newValue).booleanValue());
				return;
			case ProcessPackage.TASK__TRANSLATIONS:
				getTranslations().clear();
				getTranslations().addAll((Collection)newValue);
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
			case ProcessPackage.TASK__INITIAL:
				unsetInitial();
				return;
			case ProcessPackage.TASK__TRANSLATIONS:
				getTranslations().clear();
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
			case ProcessPackage.TASK__INITIAL:
				return isSetInitial();
			case ProcessPackage.TASK__TRANSLATIONS:
				return translations != null && !translations.isEmpty();
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
		result.append(" (initial: ");
		if (initialESet) result.append(initial); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TaskImpl
