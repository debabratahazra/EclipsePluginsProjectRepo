/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.visualrules.generation.rulesbinding.CompletionRule;
import com.odcgroup.visualrules.generation.rulesbinding.MdfBinding;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingFactory;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage;
import com.odcgroup.visualrules.generation.rulesbinding.ValidationRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulesBindingFactoryImpl extends EFactoryImpl implements RulesBindingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RulesBindingFactory init() {
		try {
			RulesBindingFactory theRulesBindingFactory = (RulesBindingFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/rules/binding"); 
			if (theRulesBindingFactory != null) {
				return theRulesBindingFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RulesBindingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesBindingFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case RulesBindingPackage.MDF_BINDING: return createMdfBinding();
			case RulesBindingPackage.VALIDATION_RULE: return createValidationRule();
			case RulesBindingPackage.COMPLETION_RULE: return createCompletionRule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfBinding createMdfBinding() {
		MdfBindingImpl mdfBinding = new MdfBindingImpl();
		return mdfBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValidationRule createValidationRule() {
		ValidationRuleImpl validationRule = new ValidationRuleImpl();
		return validationRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompletionRule createCompletionRule() {
		CompletionRuleImpl completionRule = new CompletionRuleImpl();
		return completionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesBindingPackage getRulesBindingPackage() {
		return (RulesBindingPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulesBindingPackage getPackage() {
		return RulesBindingPackage.eINSTANCE;
	}

} //RulesBindingFactoryImpl
