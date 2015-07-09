/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.uimodel.EditPolicyRole;
import com.odcgroup.page.uimodel.EditPolicyRoleType;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edit Policy Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl#getRole <em>Role</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.EditPolicyRoleImpl#getImplementationClass <em>Implementation Class</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EditPolicyRoleImpl extends MinimalEObjectImpl.Container implements EditPolicyRole {
	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final EditorMode MODE_EDEFAULT = EditorMode.ALL;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected EditorMode mode = MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final EditPolicyRoleType ROLE_EDEFAULT = EditPolicyRoleType.DIRECT_EDIT_POLICY;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected EditPolicyRoleType role = ROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected String implementationClass = IMPLEMENTATION_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditPolicyRoleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.EDIT_POLICY_ROLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EditPolicyRoleType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditPolicyRoleType getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newRole
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(EditPolicyRoleType newRole) {
		EditPolicyRoleType oldRole = role;
		role = newRole == null ? ROLE_EDEFAULT : newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.EDIT_POLICY_ROLE__ROLE, oldRole, role));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EditorMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorMode getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(EditorMode newMode) {
		EditorMode oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.EDIT_POLICY_ROLE__MODE, oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationClass() {
		return implementationClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newImplementationClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationClass(String newImplementationClass) {
		String oldImplementationClass = implementationClass;
		implementationClass = newImplementationClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS, oldImplementationClass, implementationClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.EDIT_POLICY_ROLE__MODE:
				return getMode();
			case UIModelPackage.EDIT_POLICY_ROLE__ROLE:
				return getRole();
			case UIModelPackage.EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS:
				return getImplementationClass();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UIModelPackage.EDIT_POLICY_ROLE__MODE:
				setMode((EditorMode)newValue);
				return;
			case UIModelPackage.EDIT_POLICY_ROLE__ROLE:
				setRole((EditPolicyRoleType)newValue);
				return;
			case UIModelPackage.EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS:
				setImplementationClass((String)newValue);
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
			case UIModelPackage.EDIT_POLICY_ROLE__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case UIModelPackage.EDIT_POLICY_ROLE__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case UIModelPackage.EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS:
				setImplementationClass(IMPLEMENTATION_CLASS_EDEFAULT);
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
			case UIModelPackage.EDIT_POLICY_ROLE__MODE:
				return mode != MODE_EDEFAULT;
			case UIModelPackage.EDIT_POLICY_ROLE__ROLE:
				return role != ROLE_EDEFAULT;
			case UIModelPackage.EDIT_POLICY_ROLE__IMPLEMENTATION_CLASS:
				return IMPLEMENTATION_CLASS_EDEFAULT == null ? implementationClass != null : !IMPLEMENTATION_CLASS_EDEFAULT.equals(implementationClass);
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
		result.append(" (mode: ");
		result.append(mode);
		result.append(", role: ");
		result.append(role);
		result.append(", implementationClass: ");
		result.append(implementationClass);
		result.append(')');
		return result.toString();
	}

} //EditPolicyRoleImpl
