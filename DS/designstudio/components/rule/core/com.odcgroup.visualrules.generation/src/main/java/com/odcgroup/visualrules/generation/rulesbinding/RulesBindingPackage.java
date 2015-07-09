/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingFactory
 * @model kind="package"
 * @generated
 */
public interface RulesBindingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rulesbinding";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/rules/binding";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rule";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesBindingPackage eINSTANCE = com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl <em>Mdf Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getMdfBinding()
	 * @generated
	 */
	int MDF_BINDING = 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_BINDING__ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_BINDING__RULES = 1;

	/**
	 * The number of structural features of the '<em>Mdf Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MDF_BINDING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RuleImpl
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.ValidationRuleImpl <em>Validation Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.ValidationRuleImpl
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getValidationRule()
	 * @generated
	 */
	int VALIDATION_RULE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_RULE__NAME = RULE__NAME;

	/**
	 * The number of structural features of the '<em>Validation Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.CompletionRuleImpl <em>Completion Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.CompletionRuleImpl
	 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getCompletionRule()
	 * @generated
	 */
	int COMPLETION_RULE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLETION_RULE__NAME = RULE__NAME;

	/**
	 * The number of structural features of the '<em>Completion Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLETION_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding <em>Mdf Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mdf Binding</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.MdfBinding
	 * @generated
	 */
	EClass getMdfBinding();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getEntity()
	 * @see #getMdfBinding()
	 * @generated
	 */
	EReference getMdfBinding_Entity();

	/**
	 * Returns the meta object for the reference list '{@link com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rules</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.MdfBinding#getRules()
	 * @see #getMdfBinding()
	 * @generated
	 */
	EReference getMdfBinding_Rules();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.generation.rulesbinding.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.visualrules.generation.rulesbinding.Rule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.Rule#getName()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Name();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.generation.rulesbinding.ValidationRule <em>Validation Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Validation Rule</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.ValidationRule
	 * @generated
	 */
	EClass getValidationRule();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.generation.rulesbinding.CompletionRule <em>Completion Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Completion Rule</em>'.
	 * @see com.odcgroup.visualrules.generation.rulesbinding.CompletionRule
	 * @generated
	 */
	EClass getCompletionRule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RulesBindingFactory getRulesBindingFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl <em>Mdf Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getMdfBinding()
		 * @generated
		 */
		EClass MDF_BINDING = eINSTANCE.getMdfBinding();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MDF_BINDING__ENTITY = eINSTANCE.getMdfBinding_Entity();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MDF_BINDING__RULES = eINSTANCE.getMdfBinding_Rules();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RuleImpl
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__NAME = eINSTANCE.getRule_Name();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.ValidationRuleImpl <em>Validation Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.ValidationRuleImpl
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getValidationRule()
		 * @generated
		 */
		EClass VALIDATION_RULE = eINSTANCE.getValidationRule();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.generation.rulesbinding.impl.CompletionRuleImpl <em>Completion Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.CompletionRuleImpl
		 * @see com.odcgroup.visualrules.generation.rulesbinding.impl.RulesBindingPackageImpl#getCompletionRule()
		 * @generated
		 */
		EClass COMPLETION_RULE = eINSTANCE.getCompletionRule();

	}

} //RulesBindingPackage
