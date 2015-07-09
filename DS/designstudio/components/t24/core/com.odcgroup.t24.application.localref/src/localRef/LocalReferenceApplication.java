/**
 */
package localRef;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Reference Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link localRef.LocalReferenceApplication#getLocalField <em>Local Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see localRef.LocalRefPackage#getLocalReferenceApplication()
 * @model extendedMetaData="name='localReferenceApplication' kind='elementOnly'"
 * @generated
 */
public interface LocalReferenceApplication extends EObject
{
  /**
   * Returns the value of the '<em><b>Local Field</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Local Field</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Local Field</em>' attribute list.
   * @see localRef.LocalRefPackage#getLocalReferenceApplication_LocalField()
   * @model unique="false" required="true"
   *        extendedMetaData="kind='element' name='localField'"
   * @generated
   */
  EList<String> getLocalField();

} // LocalReferenceApplication
