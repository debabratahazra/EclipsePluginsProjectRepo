/**
 */
package com.odcgroup.translation.translationDsl.impl;

import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslFactory;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;
import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TranslationDslPackageImpl extends EPackageImpl implements TranslationDslPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localTranslationsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localTranslationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass translationsEClass = null;

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
   * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private TranslationDslPackageImpl()
  {
    super(eNS_URI, TranslationDslFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link TranslationDslPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static TranslationDslPackage init()
  {
    if (isInited) return (TranslationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TranslationDslPackage.eNS_URI);

    // Obtain or create and register package
    TranslationDslPackageImpl theTranslationDslPackage = (TranslationDslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TranslationDslPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TranslationDslPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theTranslationDslPackage.createPackageContents();

    // Initialize created meta-data
    theTranslationDslPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theTranslationDslPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(TranslationDslPackage.eNS_URI, theTranslationDslPackage);
    return theTranslationDslPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalTranslations()
  {
    return localTranslationsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLocalTranslations_Translations()
  {
    return (EReference)localTranslationsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalTranslation()
  {
    return localTranslationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalTranslation_Locale()
  {
    return (EAttribute)localTranslationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalTranslation_Text()
  {
    return (EAttribute)localTranslationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTranslations()
  {
    return translationsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TranslationDslFactory getTranslationDslFactory()
  {
    return (TranslationDslFactory)getEFactoryInstance();
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
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    localTranslationsEClass = createEClass(LOCAL_TRANSLATIONS);
    createEReference(localTranslationsEClass, LOCAL_TRANSLATIONS__TRANSLATIONS);

    localTranslationEClass = createEClass(LOCAL_TRANSLATION);
    createEAttribute(localTranslationEClass, LOCAL_TRANSLATION__LOCALE);
    createEAttribute(localTranslationEClass, LOCAL_TRANSLATION__TEXT);

    translationsEClass = createEClass(TRANSLATIONS);
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
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    localTranslationsEClass.getESuperTypes().add(this.getTranslations());

    // Initialize classes and features; add operations and parameters
    initEClass(localTranslationsEClass, LocalTranslations.class, "LocalTranslations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLocalTranslations_Translations(), this.getLocalTranslation(), null, "translations", null, 0, -1, LocalTranslations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localTranslationEClass, LocalTranslation.class, "LocalTranslation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalTranslation_Locale(), ecorePackage.getEString(), "locale", null, 0, 1, LocalTranslation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalTranslation_Text(), ecorePackage.getEString(), "text", null, 0, 1, LocalTranslation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(translationsEClass, Translations.class, "Translations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //TranslationDslPackageImpl
