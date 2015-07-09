/**
 */
package com.odcgroup.translation.translationDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Translations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.translation.translationDsl.LocalTranslations#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#getLocalTranslations()
 * @model
 * @generated
 */
public interface LocalTranslations extends Translations
{
  /**
   * Returns the value of the '<em><b>Translations</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.translation.translationDsl.LocalTranslation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Translations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Translations</em>' containment reference list.
   * @see com.odcgroup.translation.translationDsl.TranslationDslPackage#getLocalTranslations_Translations()
   * @model containment="true"
   * @generated
   */
  EList<LocalTranslation> getTranslations();

} // LocalTranslations
