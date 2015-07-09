/**
 * <copyright>
 * </copyright>
 *

 */
package com.odcgroup.menu.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.menu.model.Enabled;
import com.odcgroup.menu.model.MenuFactory;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.menu.model.Translation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MenuFactoryImpl extends EFactoryImpl implements MenuFactory
{
  /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static MenuFactory init()
  {
		try {
			MenuFactory theMenuFactory = (MenuFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/menu/model/Menu"); 
			if (theMenuFactory != null) {
				return theMenuFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MenuFactoryImpl();
	}

  /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MenuFactoryImpl()
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
		switch (eClass.getClassifierID()) {
			case MenuPackage.MENU_ROOT: return createMenuRoot();
			case MenuPackage.MENU_ITEM: return createMenuItem();
			case MenuPackage.TRANSLATION: return createTranslation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
		switch (eDataType.getClassifierID()) {
			case MenuPackage.ENABLED:
				return createEnabledFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
		switch (eDataType.getClassifierID()) {
			case MenuPackage.ENABLED:
				return convertEnabledToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MenuRoot createMenuRoot()
  {
		MenuRootImpl menuRoot = new MenuRootImpl();
		return menuRoot;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MenuItem createMenuItem()
  {
		MenuItemImpl menuItem = new MenuItemImpl();
		return menuItem;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Translation createTranslation()
  {
		TranslationImpl translation = new TranslationImpl();
		return translation;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Enabled createEnabledFromString(EDataType eDataType, String initialValue)
  {
		Enabled result = Enabled.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertEnabledToString(EDataType eDataType, Object instanceValue)
  {
		return instanceValue == null ? null : instanceValue.toString();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MenuPackage getMenuPackage()
  {
		return (MenuPackage)getEPackage();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
  @Deprecated
  public static MenuPackage getPackage()
  {
		return MenuPackage.eINSTANCE;
	}

} //MenuFactoryImpl
