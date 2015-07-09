/**
 */
package com.odcgroup.translation.translationDsl.impl;

import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Translations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.translation.translationDsl.impl.LocalTranslationsImpl#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalTranslationsImpl extends TranslationsImpl implements LocalTranslations
{
  /**
   * The cached value of the '{@link #getTranslations() <em>Translations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTranslations()
   * @generated
   * @ordered
   */
  protected EList<LocalTranslation> translations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalTranslationsImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return TranslationDslPackage.Literals.LOCAL_TRANSLATIONS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LocalTranslation> getTranslations()
  {
    if (translations == null)
    {
      translations = new EObjectContainmentEList<LocalTranslation>(LocalTranslation.class, this, TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS);
    }
    return translations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS:
        return ((InternalEList<?>)getTranslations()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS:
        return getTranslations();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS:
        getTranslations().clear();
        getTranslations().addAll((Collection<? extends LocalTranslation>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS:
        getTranslations().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case TranslationDslPackage.LOCAL_TRANSLATIONS__TRANSLATIONS:
        return translations != null && !translations.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //LocalTranslationsImpl
