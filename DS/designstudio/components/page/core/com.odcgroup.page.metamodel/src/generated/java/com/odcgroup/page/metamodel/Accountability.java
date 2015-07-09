/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Accountability</b></em>'. <!-- end-user-doc
 * -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.odcgroup.page.metamodel.Accountability#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountability()
 * @model
 * @generated
 */
public interface Accountability extends EObject {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.AccountabilityRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountability_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<AccountabilityRule> getRules();

	/**
	 * Finds the AccountabilityRule which associates the child WidgetType with the parent WidgetType.
	 * Since it is possible that two rules have been declared (a more generic one, and a more specific one
	 * for example) the following lookup strategy is used:
	 * 
	 * Child Tree  -- Parent Tree
	 * child 	   -- parent
	 * child 	   -- grandparent
	 * child 	   -- great-grandparent
	 * ...
	 * parent 	   -- parent
	 * parent 	   -- grandparent
	 * parent 	   -- great-grandparent
	 * ...
	 * grandparent -- parent
	 * ...
	 * 
	 * In other words we first try to find a AccountabilityRule which specifically concerns the child
	 * and matches the parent. We then mount the ancestor tree of the parent looking for match. If none
	 * is found we start to mount the child's ancestor tree.
	 * 
	 * @param child The child WidgetType
	 * @param parent The parent WidgetType
	 * @return AccountabilityRule The rule which associates the child WidgetType with the parent WidgetType
	 * 			or null if no rule can be found
	 */
	public AccountabilityRule findAccountabilityRule(WidgetType child, WidgetType parent);
	
} // Accountability
