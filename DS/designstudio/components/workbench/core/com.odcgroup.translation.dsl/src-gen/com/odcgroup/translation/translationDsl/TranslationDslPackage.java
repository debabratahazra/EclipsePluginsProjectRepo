/**
 */
package com.odcgroup.translation.translationDsl;

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
 * @see com.odcgroup.translation.translationDsl.TranslationDslFactory
 * @model kind="package"
 * @generated
 */
public interface TranslationDslPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "translationDsl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/translation/TranslationDsl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "translationDsl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TranslationDslPackage eINSTANCE = com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl.init();

  /**
   * The meta object id for the '{@link com.odcgroup.translation.translationDsl.impl.TranslationsImpl <em>Translations</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.translation.translationDsl.impl.TranslationsImpl
   * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getTranslations()
   * @generated
   */
  int TRANSLATIONS = 2;

  /**
   * The number of structural features of the '<em>Translations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSLATIONS_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.odcgroup.translation.translationDsl.impl.LocalTranslationsImpl <em>Local Translations</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.translation.translationDsl.impl.LocalTranslationsImpl
   * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getLocalTranslations()
   * @generated
   */
  int LOCAL_TRANSLATIONS = 0;

  /**
   * The feature id for the '<em><b>Translations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_TRANSLATIONS__TRANSLATIONS = TRANSLATIONS_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Local Translations</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_TRANSLATIONS_FEATURE_COUNT = TRANSLATIONS_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.odcgroup.translation.translationDsl.impl.LocalTranslationImpl <em>Local Translation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.translation.translationDsl.impl.LocalTranslationImpl
   * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getLocalTranslation()
   * @generated
   */
  int LOCAL_TRANSLATION = 1;

  /**
   * The feature id for the '<em><b>Locale</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_TRANSLATION__LOCALE = 0;

  /**
   * The feature id for the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_TRANSLATION__TEXT = 1;

  /**
   * The number of structural features of the '<em>Local Translation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_TRANSLATION_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link com.odcgroup.translation.translationDsl.LocalTranslations <em>Local Translations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Translations</em>'.
   * @see com.odcgroup.translation.translationDsl.LocalTranslations
   * @generated
   */
  EClass getLocalTranslations();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.translation.translationDsl.LocalTranslations#getTranslations <em>Translations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Translations</em>'.
   * @see com.odcgroup.translation.translationDsl.LocalTranslations#getTranslations()
   * @see #getLocalTranslations()
   * @generated
   */
  EReference getLocalTranslations_Translations();

  /**
   * Returns the meta object for class '{@link com.odcgroup.translation.translationDsl.LocalTranslation <em>Local Translation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Translation</em>'.
   * @see com.odcgroup.translation.translationDsl.LocalTranslation
   * @generated
   */
  EClass getLocalTranslation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.translation.translationDsl.LocalTranslation#getLocale <em>Locale</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Locale</em>'.
   * @see com.odcgroup.translation.translationDsl.LocalTranslation#getLocale()
   * @see #getLocalTranslation()
   * @generated
   */
  EAttribute getLocalTranslation_Locale();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.translation.translationDsl.LocalTranslation#getText <em>Text</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Text</em>'.
   * @see com.odcgroup.translation.translationDsl.LocalTranslation#getText()
   * @see #getLocalTranslation()
   * @generated
   */
  EAttribute getLocalTranslation_Text();

  /**
   * Returns the meta object for class '{@link com.odcgroup.translation.translationDsl.Translations <em>Translations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Translations</em>'.
   * @see com.odcgroup.translation.translationDsl.Translations
   * @generated
   */
  EClass getTranslations();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  TranslationDslFactory getTranslationDslFactory();

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
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.odcgroup.translation.translationDsl.impl.LocalTranslationsImpl <em>Local Translations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.translation.translationDsl.impl.LocalTranslationsImpl
     * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getLocalTranslations()
     * @generated
     */
    EClass LOCAL_TRANSLATIONS = eINSTANCE.getLocalTranslations();

    /**
     * The meta object literal for the '<em><b>Translations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LOCAL_TRANSLATIONS__TRANSLATIONS = eINSTANCE.getLocalTranslations_Translations();

    /**
     * The meta object literal for the '{@link com.odcgroup.translation.translationDsl.impl.LocalTranslationImpl <em>Local Translation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.translation.translationDsl.impl.LocalTranslationImpl
     * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getLocalTranslation()
     * @generated
     */
    EClass LOCAL_TRANSLATION = eINSTANCE.getLocalTranslation();

    /**
     * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_TRANSLATION__LOCALE = eINSTANCE.getLocalTranslation_Locale();

    /**
     * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_TRANSLATION__TEXT = eINSTANCE.getLocalTranslation_Text();

    /**
     * The meta object literal for the '{@link com.odcgroup.translation.translationDsl.impl.TranslationsImpl <em>Translations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.translation.translationDsl.impl.TranslationsImpl
     * @see com.odcgroup.translation.translationDsl.impl.TranslationDslPackageImpl#getTranslations()
     * @generated
     */
    EClass TRANSLATIONS = eINSTANCE.getTranslations();

  }

} //TranslationDslPackage
