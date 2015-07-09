/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Accountability Rule</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl#getChild <em>Child</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl#getMinOccurs <em>Min Occurs</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl#getMaxOccurs <em>Max Occurs</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityRuleImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountabilityRuleImpl extends MinimalEObjectImpl.Container implements AccountabilityRule {
	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected WidgetType child;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected WidgetType parent;

	/**
	 * The default value of the '{@link #getMinOccurs() <em>Min Occurs</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getMinOccurs()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_OCCURS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinOccurs() <em>Min Occurs</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getMinOccurs()
	 * @generated
	 * @ordered
	 */
	protected int minOccurs = MIN_OCCURS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxOccurs() <em>Max Occurs</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getMaxOccurs()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_OCCURS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMaxOccurs() <em>Max Occurs</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getMaxOccurs()
	 * @generated
	 * @ordered
	 */
	protected int maxOccurs = MAX_OCCURS_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AccountabilityRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.ACCOUNTABILITY_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getChild() {
		if (child != null && child.eIsProxy()) {
			InternalEObject oldChild = (InternalEObject)child;
			child = (WidgetType)eResolveProxy(oldChild);
			if (child != oldChild) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.ACCOUNTABILITY_RULE__CHILD, oldChild, child));
			}
		}
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @return WidgetType <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newChild
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(WidgetType newChild) {
		WidgetType oldChild = child;
		child = newChild;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.ACCOUNTABILITY_RULE__CHILD, oldChild, child));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (WidgetType)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.ACCOUNTABILITY_RULE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @return WidgetType <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(WidgetType newParent) {
		WidgetType oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.ACCOUNTABILITY_RULE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinOccurs() {
		return minOccurs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMinOccurs
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinOccurs(int newMinOccurs) {
		int oldMinOccurs = minOccurs;
		minOccurs = newMinOccurs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.ACCOUNTABILITY_RULE__MIN_OCCURS, oldMinOccurs, minOccurs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMaxOccurs
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxOccurs(int newMaxOccurs) {
		int oldMaxOccurs = maxOccurs;
		maxOccurs = newMaxOccurs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.ACCOUNTABILITY_RULE__MAX_OCCURS, oldMaxOccurs, maxOccurs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * This method is useful for the editors and enables one to
	 * see the parent-child relationship more easily.
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		WidgetType p = getParent();
		WidgetType c = getChild();
		String pStr = p != null ? p.getName() : "";
		String cStr = c != null ? c.getName() : "";
		return pStr + " --> " + cStr;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY_RULE__CHILD:
				if (resolve) return getChild();
				return basicGetChild();
			case MetaModelPackage.ACCOUNTABILITY_RULE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case MetaModelPackage.ACCOUNTABILITY_RULE__MIN_OCCURS:
				return getMinOccurs();
			case MetaModelPackage.ACCOUNTABILITY_RULE__MAX_OCCURS:
				return getMaxOccurs();
			case MetaModelPackage.ACCOUNTABILITY_RULE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY_RULE__CHILD:
				setChild((WidgetType)newValue);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__PARENT:
				setParent((WidgetType)newValue);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MIN_OCCURS:
				setMinOccurs((Integer)newValue);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MAX_OCCURS:
				setMaxOccurs((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY_RULE__CHILD:
				setChild((WidgetType)null);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__PARENT:
				setParent((WidgetType)null);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MIN_OCCURS:
				setMinOccurs(MIN_OCCURS_EDEFAULT);
				return;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MAX_OCCURS:
				setMaxOccurs(MAX_OCCURS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY_RULE__CHILD:
				return child != null;
			case MetaModelPackage.ACCOUNTABILITY_RULE__PARENT:
				return parent != null;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MIN_OCCURS:
				return minOccurs != MIN_OCCURS_EDEFAULT;
			case MetaModelPackage.ACCOUNTABILITY_RULE__MAX_OCCURS:
				return maxOccurs != MAX_OCCURS_EDEFAULT;
			case MetaModelPackage.ACCOUNTABILITY_RULE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (minOccurs: ");
		result.append(minOccurs);
		result.append(", maxOccurs: ");
		result.append(maxOccurs);
		result.append(')');
		return result.toString();
	}

} // AccountabilityRuleImpl
