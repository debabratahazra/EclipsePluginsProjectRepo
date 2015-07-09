/**
 */
package com.odcgroup.translation.translationDsl.util;

import com.odcgroup.translation.translationDsl.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.translation.translationDsl.TranslationDslPackage
 * @generated
 */
public class TranslationDslAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TranslationDslPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TranslationDslAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = TranslationDslPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TranslationDslSwitch<Adapter> modelSwitch =
    new TranslationDslSwitch<Adapter>()
    {
      @Override
      public Adapter caseLocalTranslations(LocalTranslations object)
      {
        return createLocalTranslationsAdapter();
      }
      @Override
      public Adapter caseLocalTranslation(LocalTranslation object)
      {
        return createLocalTranslationAdapter();
      }
      @Override
      public Adapter caseTranslations(Translations object)
      {
        return createTranslationsAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.translation.translationDsl.LocalTranslations <em>Local Translations</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.translation.translationDsl.LocalTranslations
   * @generated
   */
  public Adapter createLocalTranslationsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.translation.translationDsl.LocalTranslation <em>Local Translation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.translation.translationDsl.LocalTranslation
   * @generated
   */
  public Adapter createLocalTranslationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.translation.translationDsl.Translations <em>Translations</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.translation.translationDsl.Translations
   * @generated
   */
  public Adapter createTranslationsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //TranslationDslAdapterFactory
