/**
 */
package com.odcgroup.edge.t24ui;

import com.odcgroup.edge.t24ui.common.Translation;
import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Screen</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.CompositeScreen#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.CompositeScreen#getMenuDslName <em>Menu Dsl Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.CompositeScreen#isTopLevelCOS <em>Top Level COS</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.T24UIPackage#getCompositeScreen()
 * @model
 * @generated
 */
public interface CompositeScreen extends AbstractCOS {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' attribute.
	 * @see #setDomain(String)
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getCompositeScreen_Domain()
	 * @model required="true"
	 * @generated
	 */
	String getDomain();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.CompositeScreen#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(String value);

	/**
	 * Returns the value of the '<em><b>Menu Dsl Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menu Dsl Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menu Dsl Name</em>' attribute.
	 * @see #setMenuDslName(String)
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getCompositeScreen_MenuDslName()
	 * @model
	 * @generated
	 */
	String getMenuDslName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.CompositeScreen#getMenuDslName <em>Menu Dsl Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menu Dsl Name</em>' attribute.
	 * @see #getMenuDslName()
	 * @generated
	 */
	void setMenuDslName(String value);

    /**
	 * Returns the value of the '<em><b>Top Level COS</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Top Level COS</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Top Level COS</em>' attribute.
	 * @see #setTopLevelCOS(boolean)
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getCompositeScreen_TopLevelCOS()
	 * @model
	 * @generated
	 */
    boolean isTopLevelCOS();

    /**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.CompositeScreen#isTopLevelCOS <em>Top Level COS</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Level COS</em>' attribute.
	 * @see #isTopLevelCOS()
	 * @generated
	 */
    void setTopLevelCOS(boolean value);

} // CompositeScreen
