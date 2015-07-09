/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Menu Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenuType()
 * @model
 * @generated
 */
public enum MenuType implements Enumerator {
	/**
	 * The '<em><b>Contextual</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTEXTUAL_VALUE
	 * @generated
	 * @ordered
	 */
	CONTEXTUAL(0, "Contextual", "Contextual"),

	/**
	 * The '<em><b>Toolbar</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TOOLBAR_VALUE
	 * @generated
	 * @ordered
	 */
	TOOLBAR(1, "Toolbar", "Toolbar");

	/**
	 * The '<em><b>Contextual</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Contextual</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONTEXTUAL
	 * @model name="Contextual"
	 * @generated
	 * @ordered
	 */
	public static final int CONTEXTUAL_VALUE = 0;

	/**
	 * The '<em><b>Toolbar</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Toolbar</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TOOLBAR
	 * @model name="Toolbar"
	 * @generated
	 * @ordered
	 */
	public static final int TOOLBAR_VALUE = 1;

	/**
	 * An array of all the '<em><b>Menu Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MenuType[] VALUES_ARRAY =
		new MenuType[] {
			CONTEXTUAL,
			TOOLBAR,
		};

	/**
	 * A public read-only list of all the '<em><b>Menu Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MenuType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Menu Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * @param literal
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MenuType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MenuType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Menu Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * @param name
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MenuType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MenuType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Menu Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MenuType get(int value) {
		switch (value) {
			case CONTEXTUAL_VALUE: return CONTEXTUAL;
			case TOOLBAR_VALUE: return TOOLBAR;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @param name
	 * @param literal
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private MenuType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //MenuType
