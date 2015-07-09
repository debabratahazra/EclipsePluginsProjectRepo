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

import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.ActionImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.ActionImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.ActionImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.ActionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.ActionImpl#isStopOnFailure <em>Stop On Failure</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ActionImpl extends MinimalEObjectImpl.Container implements Action {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList property;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

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
	 * The default value of the '{@link #isStopOnFailure() <em>Stop On Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStopOnFailure()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STOP_ON_FAILURE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isStopOnFailure() <em>Stop On Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStopOnFailure()
	 * @generated
	 * @ordered
	 */
	protected boolean stopOnFailure = STOP_ON_FAILURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList(Property.class, this, PageflowPackage.ACTION__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.ACTION__URI, oldUri, uri));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.ACTION__DESC, oldDesc, desc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.ACTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStopOnFailure() {
		return stopOnFailure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStopOnFailure(boolean newStopOnFailure) {
		boolean oldStopOnFailure = stopOnFailure;
		stopOnFailure = newStopOnFailure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.ACTION__STOP_ON_FAILURE, oldStopOnFailure, stopOnFailure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PageflowPackage.ACTION__PROPERTY:
				return ((InternalEList)getProperty()).basicRemove(otherEnd, msgs);
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
			case PageflowPackage.ACTION__PROPERTY:
				return getProperty();
			case PageflowPackage.ACTION__URI:
				return getUri();
			case PageflowPackage.ACTION__DESC:
				return getDesc();
			case PageflowPackage.ACTION__NAME:
				return getName();
			case PageflowPackage.ACTION__STOP_ON_FAILURE:
				return isStopOnFailure() ? Boolean.TRUE : Boolean.FALSE;
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
			case PageflowPackage.ACTION__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection)newValue);
				return;
			case PageflowPackage.ACTION__URI:
				setUri((String)newValue);
				return;
			case PageflowPackage.ACTION__DESC:
				setDesc((String)newValue);
				return;
			case PageflowPackage.ACTION__NAME:
				setName((String)newValue);
				return;
			case PageflowPackage.ACTION__STOP_ON_FAILURE:
				setStopOnFailure(((Boolean)newValue).booleanValue());
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
			case PageflowPackage.ACTION__PROPERTY:
				getProperty().clear();
				return;
			case PageflowPackage.ACTION__URI:
				setUri(URI_EDEFAULT);
				return;
			case PageflowPackage.ACTION__DESC:
				setDesc(DESC_EDEFAULT);
				return;
			case PageflowPackage.ACTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PageflowPackage.ACTION__STOP_ON_FAILURE:
				setStopOnFailure(STOP_ON_FAILURE_EDEFAULT);
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
			case PageflowPackage.ACTION__PROPERTY:
				return property != null && !property.isEmpty();
			case PageflowPackage.ACTION__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case PageflowPackage.ACTION__DESC:
				return DESC_EDEFAULT == null ? desc != null : !DESC_EDEFAULT.equals(desc);
			case PageflowPackage.ACTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PageflowPackage.ACTION__STOP_ON_FAILURE:
				return stopOnFailure != STOP_ON_FAILURE_EDEFAULT;
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(", desc: ");
		result.append(desc);
		result.append(", name: ");
		result.append(name);
		result.append(", stopOnFailure: ");
		result.append(stopOnFailure);
		result.append(')');
		return result.toString();
	}

} //ActionImpl
