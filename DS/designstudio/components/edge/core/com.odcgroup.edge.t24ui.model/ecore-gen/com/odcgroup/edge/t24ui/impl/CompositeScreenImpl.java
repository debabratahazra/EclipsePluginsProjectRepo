/**
 */
package com.odcgroup.edge.t24ui.impl;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.T24UIPackage;

import com.odcgroup.edge.t24ui.common.Translation;
import com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Screen</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.CompositeScreenImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.CompositeScreenImpl#getMenuDslName <em>Menu Dsl Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.CompositeScreenImpl#isTopLevelCOS <em>Top Level COS</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompositeScreenImpl extends AbstractCOSImpl implements CompositeScreen {
	/**
	 * The default value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected String domain = DOMAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getMenuDslName() <em>Menu Dsl Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMenuDslName()
	 * @generated
	 * @ordered
	 */
	protected static final String MENU_DSL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMenuDslName() <em>Menu Dsl Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMenuDslName()
	 * @generated
	 * @ordered
	 */
	protected String menuDslName = MENU_DSL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isTopLevelCOS() <em>Top Level COS</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isTopLevelCOS()
	 * @generated
	 * @ordered
	 */
    protected static final boolean TOP_LEVEL_COS_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isTopLevelCOS() <em>Top Level COS</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isTopLevelCOS()
	 * @generated
	 * @ordered
	 */
    protected boolean topLevelCOS = TOP_LEVEL_COS_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositeScreenImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return T24UIPackage.Literals.COMPOSITE_SCREEN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(String newDomain) {
		String oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, T24UIPackage.COMPOSITE_SCREEN__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMenuDslName() {
		return menuDslName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMenuDslName(String newMenuDslName) {
		String oldMenuDslName = menuDslName;
		menuDslName = newMenuDslName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, T24UIPackage.COMPOSITE_SCREEN__MENU_DSL_NAME, oldMenuDslName, menuDslName));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isTopLevelCOS() {
		return topLevelCOS;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTopLevelCOS(boolean newTopLevelCOS) {
		boolean oldTopLevelCOS = topLevelCOS;
		topLevelCOS = newTopLevelCOS;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, T24UIPackage.COMPOSITE_SCREEN__TOP_LEVEL_COS, oldTopLevelCOS, topLevelCOS));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case T24UIPackage.COMPOSITE_SCREEN__DOMAIN:
				return getDomain();
			case T24UIPackage.COMPOSITE_SCREEN__MENU_DSL_NAME:
				return getMenuDslName();
			case T24UIPackage.COMPOSITE_SCREEN__TOP_LEVEL_COS:
				return isTopLevelCOS();
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
			case T24UIPackage.COMPOSITE_SCREEN__DOMAIN:
				setDomain((String)newValue);
				return;
			case T24UIPackage.COMPOSITE_SCREEN__MENU_DSL_NAME:
				setMenuDslName((String)newValue);
				return;
			case T24UIPackage.COMPOSITE_SCREEN__TOP_LEVEL_COS:
				setTopLevelCOS((Boolean)newValue);
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
			case T24UIPackage.COMPOSITE_SCREEN__DOMAIN:
				setDomain(DOMAIN_EDEFAULT);
				return;
			case T24UIPackage.COMPOSITE_SCREEN__MENU_DSL_NAME:
				setMenuDslName(MENU_DSL_NAME_EDEFAULT);
				return;
			case T24UIPackage.COMPOSITE_SCREEN__TOP_LEVEL_COS:
				setTopLevelCOS(TOP_LEVEL_COS_EDEFAULT);
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
			case T24UIPackage.COMPOSITE_SCREEN__DOMAIN:
				return DOMAIN_EDEFAULT == null ? domain != null : !DOMAIN_EDEFAULT.equals(domain);
			case T24UIPackage.COMPOSITE_SCREEN__MENU_DSL_NAME:
				return MENU_DSL_NAME_EDEFAULT == null ? menuDslName != null : !MENU_DSL_NAME_EDEFAULT.equals(menuDslName);
			case T24UIPackage.COMPOSITE_SCREEN__TOP_LEVEL_COS:
				return topLevelCOS != TOP_LEVEL_COS_EDEFAULT;
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
		result.append(" (domain: ");
		result.append(domain);
		result.append(", menuDslName: ");
		result.append(menuDslName);
		result.append(", topLevelCOS: ");
		result.append(topLevelCOS);
		result.append(')');
		return result.toString();
	}

} //CompositeScreenImpl
