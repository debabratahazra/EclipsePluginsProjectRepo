/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.View;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pageflow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getStates <em>States</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getAbortView <em>Abort View</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getErrorView <em>Error View</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getTechDesc <em>Tech Desc</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.PageflowImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageflowImpl extends MinimalEObjectImpl.Container implements Pageflow {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList states;

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
	 * The cached value of the '{@link #getAbortView() <em>Abort View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbortView()
	 * @generated
	 * @ordered
	 */
	protected View abortView;

	/**
	 * The cached value of the '{@link #getErrorView() <em>Error View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorView()
	 * @generated
	 * @ordered
	 */
	protected View errorView;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList transitions;

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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String METAMODEL_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PageflowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.PAGEFLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getStates() {
		if (states == null) {
			states = new EObjectContainmentEList(State.class, this, PageflowPackage.PAGEFLOW__STATES);
		}
		return states;
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__DESC, oldDesc, desc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View getAbortView() {
		return abortView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAbortView(View newAbortView, NotificationChain msgs) {
		View oldAbortView = abortView;
		abortView = newAbortView;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__ABORT_VIEW, oldAbortView, newAbortView);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbortView(View newAbortView) {
		if (newAbortView != abortView) {
			NotificationChain msgs = null;
			if (abortView != null)
				msgs = ((InternalEObject)abortView).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.PAGEFLOW__ABORT_VIEW, null, msgs);
			if (newAbortView != null)
				msgs = ((InternalEObject)newAbortView).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.PAGEFLOW__ABORT_VIEW, null, msgs);
			msgs = basicSetAbortView(newAbortView, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__ABORT_VIEW, newAbortView, newAbortView));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View getErrorView() {
		return errorView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetErrorView(View newErrorView, NotificationChain msgs) {
		View oldErrorView = errorView;
		errorView = newErrorView;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__ERROR_VIEW, oldErrorView, newErrorView);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorView(View newErrorView) {
		if (newErrorView != errorView) {
			NotificationChain msgs = null;
			if (errorView != null)
				msgs = ((InternalEObject)errorView).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.PAGEFLOW__ERROR_VIEW, null, msgs);
			if (newErrorView != null)
				msgs = ((InternalEObject)newErrorView).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PageflowPackage.PAGEFLOW__ERROR_VIEW, null, msgs);
			msgs = basicSetErrorView(newErrorView, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__ERROR_VIEW, newErrorView, newErrorView));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList(Transition.class, this, PageflowPackage.PAGEFLOW__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList(Property.class, this, PageflowPackage.PAGEFLOW__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__FILE_NAME, oldFileName, fileName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMetamodelVersion() {
		return metamodelVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetamodelVersion(String newMetamodelVersion) {
		String oldMetamodelVersion = metamodelVersion;
		metamodelVersion = newMetamodelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.PAGEFLOW__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PageflowPackage.PAGEFLOW__STATES:
				return ((InternalEList)getStates()).basicRemove(otherEnd, msgs);
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
				return basicSetAbortView(null, msgs);
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
				return basicSetErrorView(null, msgs);
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
				return ((InternalEList)getTransitions()).basicRemove(otherEnd, msgs);
			case PageflowPackage.PAGEFLOW__PROPERTY:
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
			case PageflowPackage.PAGEFLOW__STATES:
				return getStates();
			case PageflowPackage.PAGEFLOW__DESC:
				return getDesc();
			case PageflowPackage.PAGEFLOW__NAME:
				return getName();
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
				return getAbortView();
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
				return getErrorView();
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
				return getTransitions();
			case PageflowPackage.PAGEFLOW__PROPERTY:
				return getProperty();
			case PageflowPackage.PAGEFLOW__ID:
				return getId();
			case PageflowPackage.PAGEFLOW__FILE_NAME:
				return getFileName();
			case PageflowPackage.PAGEFLOW__TECH_DESC:
				return getTechDesc();
			case PageflowPackage.PAGEFLOW__METAMODEL_VERSION:
				return getMetamodelVersion();
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
			case PageflowPackage.PAGEFLOW__STATES:
				getStates().clear();
				getStates().addAll((Collection)newValue);
				return;
			case PageflowPackage.PAGEFLOW__DESC:
				setDesc((String)newValue);
				return;
			case PageflowPackage.PAGEFLOW__NAME:
				setName((String)newValue);
				return;
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
				setAbortView((View)newValue);
				return;
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
				setErrorView((View)newValue);
				return;
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection)newValue);
				return;
			case PageflowPackage.PAGEFLOW__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection)newValue);
				return;
			case PageflowPackage.PAGEFLOW__ID:
				setId((String)newValue);
				return;
			case PageflowPackage.PAGEFLOW__FILE_NAME:
				setFileName((String)newValue);
				return;
			case PageflowPackage.PAGEFLOW__TECH_DESC:
				setTechDesc((String)newValue);
				return;
			case PageflowPackage.PAGEFLOW__METAMODEL_VERSION:
				setMetamodelVersion((String)newValue);
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
			case PageflowPackage.PAGEFLOW__STATES:
				getStates().clear();
				return;
			case PageflowPackage.PAGEFLOW__DESC:
				setDesc(DESC_EDEFAULT);
				return;
			case PageflowPackage.PAGEFLOW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
				setAbortView((View)null);
				return;
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
				setErrorView((View)null);
				return;
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
				getTransitions().clear();
				return;
			case PageflowPackage.PAGEFLOW__PROPERTY:
				getProperty().clear();
				return;
			case PageflowPackage.PAGEFLOW__ID:
				setId(ID_EDEFAULT);
				return;
			case PageflowPackage.PAGEFLOW__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
				return;
			case PageflowPackage.PAGEFLOW__TECH_DESC:
				setTechDesc(TECH_DESC_EDEFAULT);
				return;
			case PageflowPackage.PAGEFLOW__METAMODEL_VERSION:
				setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
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
			case PageflowPackage.PAGEFLOW__STATES:
				return states != null && !states.isEmpty();
			case PageflowPackage.PAGEFLOW__DESC:
				return DESC_EDEFAULT == null ? desc != null : !DESC_EDEFAULT.equals(desc);
			case PageflowPackage.PAGEFLOW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PageflowPackage.PAGEFLOW__ABORT_VIEW:
				return abortView != null;
			case PageflowPackage.PAGEFLOW__ERROR_VIEW:
				return errorView != null;
			case PageflowPackage.PAGEFLOW__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case PageflowPackage.PAGEFLOW__PROPERTY:
				return property != null && !property.isEmpty();
			case PageflowPackage.PAGEFLOW__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PageflowPackage.PAGEFLOW__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
			case PageflowPackage.PAGEFLOW__TECH_DESC:
				return TECH_DESC_EDEFAULT == null ? techDesc != null : !TECH_DESC_EDEFAULT.equals(techDesc);
			case PageflowPackage.PAGEFLOW__METAMODEL_VERSION:
				return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
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
		result.append(", id: ");
		result.append(id);
		result.append(", fileName: ");
		result.append(fileName);
		result.append(", techDesc: ");
		result.append(techDesc);
		result.append(", metamodelVersion: ");
		result.append(metamodelVersion);
		result.append(')');
		return result.toString();
	}

} //PageflowImpl
