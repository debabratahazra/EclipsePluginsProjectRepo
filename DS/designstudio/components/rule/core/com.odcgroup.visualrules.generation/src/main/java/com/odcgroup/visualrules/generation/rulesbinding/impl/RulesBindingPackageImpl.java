/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.visualrules.generation.rulesbinding.CompletionRule;
import com.odcgroup.visualrules.generation.rulesbinding.MdfBinding;
import com.odcgroup.visualrules.generation.rulesbinding.Rule;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingFactory;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage;
import com.odcgroup.visualrules.generation.rulesbinding.ValidationRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulesBindingPackageImpl extends EPackageImpl implements RulesBindingPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mdfBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass validationRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass completionRuleEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RulesBindingPackageImpl() {
		super(eNS_URI, RulesBindingFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RulesBindingPackage init() {
		if (isInited) return (RulesBindingPackage)EPackage.Registry.INSTANCE.getEPackage(RulesBindingPackage.eNS_URI);

		// Obtain or create and register package
		RulesBindingPackageImpl theRulesBindingPackage = (RulesBindingPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof RulesBindingPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new RulesBindingPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		MdfPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRulesBindingPackage.createPackageContents();

		// Initialize created meta-data
		theRulesBindingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulesBindingPackage.freeze();

		return theRulesBindingPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMdfBinding() {
		return mdfBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMdfBinding_Entity() {
		return (EReference)mdfBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMdfBinding_Rules() {
		return (EReference)mdfBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRule() {
		return ruleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRule_Name() {
		return (EAttribute)ruleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValidationRule() {
		return validationRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompletionRule() {
		return completionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesBindingFactory getRulesBindingFactory() {
		return (RulesBindingFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		mdfBindingEClass = createEClass(MDF_BINDING);
		createEReference(mdfBindingEClass, MDF_BINDING__ENTITY);
		createEReference(mdfBindingEClass, MDF_BINDING__RULES);

		ruleEClass = createEClass(RULE);
		createEAttribute(ruleEClass, RULE__NAME);

		validationRuleEClass = createEClass(VALIDATION_RULE);

		completionRuleEClass = createEClass(COMPLETION_RULE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		validationRuleEClass.getESuperTypes().add(this.getRule());
		completionRuleEClass.getESuperTypes().add(this.getRule());

		// Initialize classes and features; add operations and parameters
		initEClass(mdfBindingEClass, MdfBinding.class, "MdfBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMdfBinding_Entity(), theMdfPackage.getMdfEntity(), null, "entity", null, 1, 1, MdfBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMdfBinding_Rules(), this.getRule(), null, "rules", null, 0, -1, MdfBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleEClass, Rule.class, "Rule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRule_Name(), ecorePackage.getEString(), "name", null, 1, 1, Rule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(validationRuleEClass, ValidationRule.class, "ValidationRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(completionRuleEClass, CompletionRule.class, "CompletionRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //RulesBindingPackageImpl
