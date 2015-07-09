/**
 * <copyright>
 * </copyright>
 *

 */
package com.odcgroup.menu.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.menu.model.menu.MenuPackage
 * @generated
 */
public interface MenuFactory extends EFactory
{
  /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  MenuFactory eINSTANCE = com.odcgroup.menu.model.impl.MenuFactoryImpl.init();

  /**
	 * Returns a new object of class '<em>Root</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root</em>'.
	 * @generated
	 */
  MenuRoot createMenuRoot();

  /**
	 * Returns a new object of class '<em>Item</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
  MenuItem createMenuItem();

  /**
	 * Returns a new object of class '<em>Translation</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Translation</em>'.
	 * @generated
	 */
  Translation createTranslation();

  /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
  MenuPackage getMenuPackage();

} //MenuFactory
