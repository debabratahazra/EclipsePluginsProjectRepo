/**
 */
package com.odcgroup.translation.translationDsl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.translation.translationDsl.TranslationDslPackage
 * @generated
 */
public interface TranslationDslFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TranslationDslFactory eINSTANCE = com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Local Translations</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Translations</em>'.
   * @generated
   */
  LocalTranslations createLocalTranslations();

  /**
   * Returns a new object of class '<em>Local Translation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Translation</em>'.
   * @generated
   */
  LocalTranslation createLocalTranslation();

  /**
   * Returns a new object of class '<em>Translations</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Translations</em>'.
   * @generated
   */
  Translations createTranslations();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  TranslationDslPackage getTranslationDslPackage();

} //TranslationDslFactory
