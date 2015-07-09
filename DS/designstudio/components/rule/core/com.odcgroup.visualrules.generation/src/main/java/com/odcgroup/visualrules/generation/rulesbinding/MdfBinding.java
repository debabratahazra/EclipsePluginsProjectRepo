/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.metamodel.MdfEntity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mdf Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage#getMdfBinding()
 * @model
 * @generated
 */
public interface MdfBinding extends EObject {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(MdfEntity)
	 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage#getMdfBinding_Entity()
	 * @model required="true"
	 * @generated
	 */
	MdfEntity getEntity();

	/**
	 * Sets the value of the '{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getEntity <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(MdfEntity value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.visualrules.generation.rulesbinding.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' reference list.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage#getMdfBinding_Rules()
	 * @model
	 * @generated
	 */
	EList<Rule> getRules();

} // MdfBinding
