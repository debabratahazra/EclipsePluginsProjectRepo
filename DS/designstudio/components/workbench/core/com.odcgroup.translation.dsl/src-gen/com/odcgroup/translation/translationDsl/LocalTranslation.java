/**
 */
package com.odcgroup.translation.translationDsl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Translation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.translation.translationDsl.LocalTranslation#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.odcgroup.translation.translationDsl.LocalTranslation#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#getLocalTranslation()
 * @model
 * @generated
 */
public interface LocalTranslation extends EObject
{
  /**
   * Returns the value of the '<em><b>Locale</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Locale</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Locale</em>' attribute.
   * @see #setLocale(String)
   * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#getLocalTranslation_Locale()
   * @model
   * @generated
   */
  String getLocale();

  /**
   * Sets the value of the '{@link com.odcgroup.translation.translationDsl.LocalTranslation#getLocale <em>Locale</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Locale</em>' attribute.
   * @see #getLocale()
   * @generated
   */
  void setLocale(String value);

  /**
   * Returns the value of the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Text</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Text</em>' attribute.
   * @see #setText(String)
   * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#getLocalTranslation_Text()
   * @model
   * @generated
   */
  String getText();

  /**
   * Sets the value of the '{@link com.odcgroup.translation.translationDsl.LocalTranslation#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text</em>' attribute.
   * @see #getText()
   * @generated
   */
  void setText(String value);

} // LocalTranslation
