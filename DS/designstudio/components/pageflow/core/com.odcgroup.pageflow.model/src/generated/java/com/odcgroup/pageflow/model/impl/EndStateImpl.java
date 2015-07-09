/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>End State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.impl.EndStateImpl#getExitUrl <em>Exit Url</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.EndStateImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.odcgroup.pageflow.model.impl.EndStateImpl#getTechDesc <em>Tech Desc</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EndStateImpl extends StateImpl implements EndState {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The default value of the '{@link #getExitUrl() <em>Exit Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String EXIT_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExitUrl() <em>Exit Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitUrl()
	 * @generated
	 * @ordered
	 */
	protected String exitUrl = EXIT_URL_EDEFAULT;

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
	protected EndStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PageflowPackage.Literals.END_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExitUrl() {
		return exitUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExitUrl(String newExitUrl) {
		String oldExitUrl = exitUrl;
		exitUrl = newExitUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.END_STATE__EXIT_URL, oldExitUrl, exitUrl));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.END_STATE__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PageflowPackage.END_STATE__TECH_DESC, oldTechDesc, techDesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PageflowPackage.END_STATE__EXIT_URL:
				return getExitUrl();
			case PageflowPackage.END_STATE__ID:
				return getId();
			case PageflowPackage.END_STATE__TECH_DESC:
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
			case PageflowPackage.END_STATE__EXIT_URL:
				setExitUrl((String)newValue);
				return;
			case PageflowPackage.END_STATE__ID:
				setId((String)newValue);
				return;
			case PageflowPackage.END_STATE__TECH_DESC:
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
			case PageflowPackage.END_STATE__EXIT_URL:
				setExitUrl(EXIT_URL_EDEFAULT);
				return;
			case PageflowPackage.END_STATE__ID:
				setId(ID_EDEFAULT);
				return;
			case PageflowPackage.END_STATE__TECH_DESC:
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
			case PageflowPackage.END_STATE__EXIT_URL:
				return EXIT_URL_EDEFAULT == null ? exitUrl != null : !EXIT_URL_EDEFAULT.equals(exitUrl);
			case PageflowPackage.END_STATE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PageflowPackage.END_STATE__TECH_DESC:
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
		result.append(" (exitUrl: ");
		result.append(exitUrl);
		result.append(", id: ");
		result.append(id);
		result.append(", techDesc: ");
		result.append(techDesc);
		result.append(')');
		return result.toString();
	}

} //EndStateImpl
