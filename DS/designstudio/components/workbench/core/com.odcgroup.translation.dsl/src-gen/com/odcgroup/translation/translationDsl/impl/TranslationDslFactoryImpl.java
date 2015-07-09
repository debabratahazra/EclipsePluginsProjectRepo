/**
 */
package com.odcgroup.translation.translationDsl.impl;

import com.odcgroup.translation.translationDsl.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TranslationDslFactoryImpl extends EFactoryImpl implements TranslationDslFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static TranslationDslFactory init()
  {
    try
    {
      TranslationDslFactory theTranslationDslFactory = (TranslationDslFactory)EPackage.Registry.INSTANCE.getEFactory(TranslationDslPackage.eNS_URI);
      if (theTranslationDslFactory != null)
      {
        return theTranslationDslFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TranslationDslFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TranslationDslFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS: return createLocalTranslations();
      case TranslationDslPackage.LOCAL_TRANSLATION: return createLocalTranslation();
      case TranslationDslPackage.TRANSLATIONS: return createTranslations();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalTranslations createLocalTranslations()
  {
    LocalTranslationsImpl localTranslations = new LocalTranslationsImpl();
    return localTranslations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalTranslation createLocalTranslation()
  {
    LocalTranslationImpl localTranslation = new LocalTranslationImpl();
    return localTranslation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations createTranslations()
  {
    TranslationsImpl translations = new TranslationsImpl();
    return translations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TranslationDslPackage getTranslationDslPackage()
  {
    return (TranslationDslPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TranslationDslPackage getPackage()
  {
    return TranslationDslPackage.eINSTANCE;
  }

} //TranslationDslFactoryImpl
