/**
 */
package localRef;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link localRef.LocalField#getLabel <em>Label</em>}</li>
 *   <li>{@link localRef.LocalField#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link localRef.LocalField#getForApplication <em>For Application</em>}</li>
 *   <li>{@link localRef.LocalField#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link localRef.LocalField#getLocalRefId <em>Local Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see localRef.LocalRefPackage#getLocalField()
 * @model extendedMetaData="name='localField' kind='elementOnly'"
 * @generated
 */
public interface LocalField extends EObject
{
  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see #setLabel(String)
   * @see localRef.LocalRefPackage#getLocalField_Label()
   * @model unique="false"
   *        extendedMetaData="kind='element' name='label'"
   * @generated
   */
  String getLabel();

  /**
   * Sets the value of the '{@link localRef.LocalField#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see #getLabel()
   * @generated
   */
  void setLabel(String value);

  /**
   * Returns the value of the '<em><b>Tool Tip</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tool Tip</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tool Tip</em>' attribute.
   * @see #setToolTip(String)
   * @see localRef.LocalRefPackage#getLocalField_ToolTip()
   * @model unique="false"
   *        extendedMetaData="kind='element' name='toolTip'"
   * @generated
   */
  String getToolTip();

  /**
   * Sets the value of the '{@link localRef.LocalField#getToolTip <em>Tool Tip</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tool Tip</em>' attribute.
   * @see #getToolTip()
   * @generated
   */
  void setToolTip(String value);

  /**
   * Returns the value of the '<em><b>For Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>For Application</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>For Application</em>' attribute.
   * @see #setForApplication(String)
   * @see localRef.LocalRefPackage#getLocalField_ForApplication()
   * @model unique="false"
   *        extendedMetaData="kind='attribute' name='forApplication'"
   * @generated
   */
  String getForApplication();

  /**
   * Sets the value of the '{@link localRef.LocalField#getForApplication <em>For Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>For Application</em>' attribute.
   * @see #getForApplication()
   * @generated
   */
  void setForApplication(String value);

  /**
   * Returns the value of the '<em><b>Group Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Group Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group Name</em>' attribute.
   * @see #setGroupName(String)
   * @see localRef.LocalRefPackage#getLocalField_GroupName()
   * @model unique="false"
   *        extendedMetaData="kind='attribute' name='groupName'"
   * @generated
   */
  String getGroupName();

  /**
   * Sets the value of the '{@link localRef.LocalField#getGroupName <em>Group Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Group Name</em>' attribute.
   * @see #getGroupName()
   * @generated
   */
  void setGroupName(String value);

  /**
   * Returns the value of the '<em><b>Local Ref Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Local Ref Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Local Ref Id</em>' attribute.
   * @see #setLocalRefId(String)
   * @see localRef.LocalRefPackage#getLocalField_LocalRefId()
   * @model unique="false"
   *        extendedMetaData="kind='attribute' name='localRefId'"
   * @generated
   */
  String getLocalRefId();

  /**
   * Sets the value of the '{@link localRef.LocalField#getLocalRefId <em>Local Ref Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Local Ref Id</em>' attribute.
   * @see #getLocalRefId()
   * @generated
   */
  void setLocalRefId(String value);

} // LocalField
