/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.Accountability;
import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Accountability</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.AccountabilityImpl#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountabilityImpl extends MinimalEObjectImpl.Container implements Accountability {
	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<AccountabilityRule> rules;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AccountabilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.ACCOUNTABILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AccountabilityRule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<AccountabilityRule>(AccountabilityRule.class, this, MetaModelPackage.ACCOUNTABILITY__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY__RULES:
				return getRules();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.ACCOUNTABILITY__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends AccountabilityRule>)newValue);
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
			case MetaModelPackage.ACCOUNTABILITY__RULES:
				getRules().clear();
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
			case MetaModelPackage.ACCOUNTABILITY__RULES:
				return rules != null && !rules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Finds the AccountabilityRule which associates the child WidgetType with the parent WidgetType. Since it is
	 * possible that two rules have been declared (a more generic one, and a more specific one for example) the
	 * following lookup strategy is used:
	 * 
	 * child -- parent child -- grandparent child -- great-grandparent ... parent -- parent parent -- grandparent parent --
	 * great-grandparent ... grandparent -- grandparent ...
	 * 
	 * In other words we first try to find a AccountabilityRule which specifically concerns the child and matches the
	 * parent. We then mount the ancestor tree of the parent looking for match. If none is found we start to mount the
	 * child's ancestor tree.
	 * 
	 * @param child
	 *            The child WidgetType
	 * @param parent
	 *            The parent WidgetType
	 * @return AccountabilityRule The rule which associates the child WidgetType with the parent WidgetType or null if
	 *         no rule can be found
	 */
	public AccountabilityRule findAccountabilityRule(WidgetType child, WidgetType parent) {
		Assert.isNotNull(child);
		Assert.isNotNull(parent);
		
		WidgetType c = child;
		// Climb the child tree. By using this in the outer loop we look for a specific match
		// to the child before the parent
		while (c != null) {
			WidgetType p = parent;
			// Climb the parent tree
			while (p != null) {
				AccountabilityRule ar = findSpecificAccountabilityRule(c, p);
				if (ar != null) {
					return ar;
				}
				if (parent.isStrictAccountability()) {
					break;
				}
				p = p.getParent();
			}
			if (child.isStrictAccountability()) {
				break;
			}
			c = c.getParent();
		}

		// Not found
		return null;
	}

	/**
	 * Finds the specific AccountabilityRule which matches the child and the parent. This method does not take into
	 * account superclasses.
	 * 
	 * @param child
	 *            The child WidgetType
	 * @param parent
	 *            The parent WidgetTpye
	 * @return AccountabilityRule The AccountabilityRule if no rule is found which matches
	 */
	private AccountabilityRule findSpecificAccountabilityRule(WidgetType child, WidgetType parent) {
		for (Iterator it = getRules().iterator(); it.hasNext();) {
			AccountabilityRule ar = (AccountabilityRule) it.next();
			if (ar.getChild().getName().equals(child.getName()) && ar.getParent().getName().equals(parent.getName())) {
				return ar;
			}
		}

		// Not found
		return null;
	}

} // AccountabilityImpl
