/**
 */
package com.odcgroup.t24.menu.menu;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuRoot#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.menu.menu.MenuRoot#getRootItem <em>Root Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuRoot()
 * @model
 * @generated
 */
public interface MenuRoot extends EObject
{
  /**
   * Returns the value of the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metamodel Version</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metamodel Version</em>' attribute.
   * @see #setMetamodelVersion(String)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuRoot_MetamodelVersion()
   * @model
   * @generated
   */
  String getMetamodelVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuRoot#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metamodel Version</em>' attribute.
   * @see #getMetamodelVersion()
   * @generated
   */
  void setMetamodelVersion(String value);

  /**
   * Returns the value of the '<em><b>Root Item</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Root Item</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root Item</em>' containment reference.
   * @see #setRootItem(MenuItem)
   * @see com.odcgroup.t24.menu.menu.MenuPackage#getMenuRoot_RootItem()
   * @model containment="true"
   * @generated
   */
  MenuItem getRootItem();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.menu.menu.MenuRoot#getRootItem <em>Root Item</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root Item</em>' containment reference.
   * @see #getRootItem()
   * @generated
   */
  void setRootItem(MenuItem value);

} // MenuRoot
