/**
 */
package com.odcgroup.t24.application.internal.localref.impl;

import com.odcgroup.t24.application.internal.localref.LocalrefPackage;
import com.odcgroup.t24.application.internal.localref.VettingTable;

import com.odcgroup.translation.translationDsl.Translations;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vetting Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl#getVettingTable <em>Vetting Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.VettingTableImpl#getRemarks <em>Remarks</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VettingTableImpl extends EObjectImpl implements VettingTable {
	/**
	 * The default value of the '{@link #getVettingTable() <em>Vetting Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVettingTable()
	 * @generated
	 * @ordered
	 */
	protected static final String VETTING_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVettingTable() <em>Vetting Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVettingTable()
	 * @generated
	 * @ordered
	 */
	protected String vettingTable = VETTING_TABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRemarks() <em>Remarks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemarks()
	 * @generated
	 * @ordered
	 */
	protected EList<Translations> remarks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VettingTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LocalrefPackage.Literals.VETTING_TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVettingTable() {
		return vettingTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVettingTable(String newVettingTable) {
		String oldVettingTable = vettingTable;
		vettingTable = newVettingTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.VETTING_TABLE__VETTING_TABLE, oldVettingTable, vettingTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translations> getRemarks() {
		if (remarks == null) {
			remarks = new EObjectContainmentEList<Translations>(Translations.class, this, LocalrefPackage.VETTING_TABLE__REMARKS);
		}
		return remarks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LocalrefPackage.VETTING_TABLE__REMARKS:
				return ((InternalEList<?>)getRemarks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LocalrefPackage.VETTING_TABLE__VETTING_TABLE:
				return getVettingTable();
			case LocalrefPackage.VETTING_TABLE__REMARKS:
				return getRemarks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LocalrefPackage.VETTING_TABLE__VETTING_TABLE:
				setVettingTable((String)newValue);
				return;
			case LocalrefPackage.VETTING_TABLE__REMARKS:
				getRemarks().clear();
				getRemarks().addAll((Collection<? extends Translations>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LocalrefPackage.VETTING_TABLE__VETTING_TABLE:
				setVettingTable(VETTING_TABLE_EDEFAULT);
				return;
			case LocalrefPackage.VETTING_TABLE__REMARKS:
				getRemarks().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LocalrefPackage.VETTING_TABLE__VETTING_TABLE:
				return VETTING_TABLE_EDEFAULT == null ? vettingTable != null : !VETTING_TABLE_EDEFAULT.equals(vettingTable);
			case LocalrefPackage.VETTING_TABLE__REMARKS:
				return remarks != null && !remarks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (vettingTable: ");
		result.append(vettingTable);
		result.append(')');
		return result.toString();
	}

} //VettingTableImpl
