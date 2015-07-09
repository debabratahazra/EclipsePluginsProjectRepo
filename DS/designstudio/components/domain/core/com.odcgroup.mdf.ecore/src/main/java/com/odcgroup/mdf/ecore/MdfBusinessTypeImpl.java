/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfBusinessTypeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfBusinessTypeImpl extends MdfPrimitiveImpl implements MdfBusinessType {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()

	 * @ordered
	 */
	protected MdfPrimitive type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
 NOT
	 */
	protected MdfBusinessTypeImpl() {
		super();
		//setName("NewBusinessType");
        setType((MdfPrimitive) PrimitivesDomain.DOMAIN.getEntity("mml:integer"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_BUSINESS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfPrimitive getType() {
		if (type != null && ((EObject)type).eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (MdfPrimitive)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_BUSINESS_TYPE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfPrimitive basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setType(MdfPrimitive newType) {
		MdfPrimitive oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_BUSINESS_TYPE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_BUSINESS_TYPE__TYPE:
				if (resolve) return getType();
				return basicGetType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_BUSINESS_TYPE__TYPE:
				setType((MdfPrimitive)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_BUSINESS_TYPE__TYPE:
				setType((MdfPrimitive)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_BUSINESS_TYPE__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

} //MdfBusinessTypeImpl
