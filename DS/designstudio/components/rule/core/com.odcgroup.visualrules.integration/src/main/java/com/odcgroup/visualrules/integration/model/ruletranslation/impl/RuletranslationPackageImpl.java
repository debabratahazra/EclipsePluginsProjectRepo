/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation.impl;

import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage;
import com.odcgroup.visualrules.integration.model.ruletranslation.Translation;
import com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuletranslationPackageImpl extends EPackageImpl implements RuletranslationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTranslationMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass translationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleMessageProxyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTranslationRepoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum translationKindEEnum = null;

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
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuletranslationPackageImpl() {
		super(eNS_URI, RuletranslationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link RuletranslationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RuletranslationPackage init() {
		if (isInited) return (RuletranslationPackage)EPackage.Registry.INSTANCE.getEPackage(RuletranslationPackage.eNS_URI);

		// Obtain or create and register package
		RuletranslationPackageImpl theRuletranslationPackage = (RuletranslationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RuletranslationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RuletranslationPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theRuletranslationPackage.createPackageContents();

		// Initialize created meta-data
		theRuletranslationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuletranslationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuletranslationPackage.eNS_URI, theRuletranslationPackage);
		return theRuletranslationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTranslationMap() {
		return ruleTranslationMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTranslationMap_Key() {
		return (EAttribute)ruleTranslationMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTranslationMap_Value() {
		return (EReference)ruleTranslationMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTranslation() {
		return translationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTranslation_Language() {
		return (EAttribute)translationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTranslation_Message() {
		return (EAttribute)translationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTranslation_Kind() {
		return (EAttribute)translationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleMessageProxy() {
		return ruleMessageProxyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMessageProxy_Code() {
		return (EAttribute)ruleMessageProxyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleMessageProxy_Translations() {
		return (EReference)ruleMessageProxyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTranslationRepo() {
		return ruleTranslationRepoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTranslationRepo_CodeMap() {
		return (EReference)ruleTranslationRepoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTranslationKind() {
		return translationKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuletranslationFactory getRuletranslationFactory() {
		return (RuletranslationFactory)getEFactoryInstance();
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
		ruleTranslationMapEClass = createEClass(RULE_TRANSLATION_MAP);
		createEAttribute(ruleTranslationMapEClass, RULE_TRANSLATION_MAP__KEY);
		createEReference(ruleTranslationMapEClass, RULE_TRANSLATION_MAP__VALUE);

		translationEClass = createEClass(TRANSLATION);
		createEAttribute(translationEClass, TRANSLATION__LANGUAGE);
		createEAttribute(translationEClass, TRANSLATION__MESSAGE);
		createEAttribute(translationEClass, TRANSLATION__KIND);

		ruleMessageProxyEClass = createEClass(RULE_MESSAGE_PROXY);
		createEAttribute(ruleMessageProxyEClass, RULE_MESSAGE_PROXY__CODE);
		createEReference(ruleMessageProxyEClass, RULE_MESSAGE_PROXY__TRANSLATIONS);

		ruleTranslationRepoEClass = createEClass(RULE_TRANSLATION_REPO);
		createEReference(ruleTranslationRepoEClass, RULE_TRANSLATION_REPO__CODE_MAP);

		// Create enums
		translationKindEEnum = createEEnum(TRANSLATION_KIND);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(ruleTranslationMapEClass, Map.Entry.class, "RuleTranslationMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleTranslationMap_Key(), ecorePackage.getEString(), "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTranslationMap_Value(), this.getRuleMessageProxy(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(translationEClass, Translation.class, "Translation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTranslation_Language(), ecorePackage.getEString(), "language", null, 1, 1, Translation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTranslation_Message(), ecorePackage.getEString(), "message", null, 0, 1, Translation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTranslation_Kind(), this.getTranslationKind(), "kind", null, 1, 1, Translation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleMessageProxyEClass, RuleMessageProxy.class, "RuleMessageProxy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleMessageProxy_Code(), ecorePackage.getEString(), "code", null, 1, 1, RuleMessageProxy.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleMessageProxy_Translations(), this.getTranslation(), null, "translations", null, 0, -1, RuleMessageProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleTranslationRepoEClass, RuleTranslationRepo.class, "RuleTranslationRepo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleTranslationRepo_CodeMap(), this.getRuleTranslationMap(), null, "codeMap", null, 0, -1, RuleTranslationRepo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(translationKindEEnum, TranslationKind.class, "TranslationKind");
		addEEnumLiteral(translationKindEEnum, TranslationKind.NAME);

		// Create resource
		createResource(eNS_URI);
	}

} //RuletranslationPackageImpl
