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
 * A representation of the literals of the enumeration '<em><b>Editor Mode</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditorMode()
 * @model
 * @generated
 */
public enum EditorMode implements Enumerator {
	/**
	 * The '<em><b>All</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL_VALUE
	 * @generated
	 * @ordered
	 */
	ALL(0, "All", "All"),

	/**
	 * The '<em><b>Design Mode</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESIGN_MODE_VALUE
	 * @generated
	 * @ordered
	 */
	DESIGN_MODE(1, "DesignMode", "DesignMode"),

	/**
	 * The '<em><b>Preview Mode</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PREVIEW_MODE_VALUE
	 * @generated
	 * @ordered
	 */
	PREVIEW_MODE(2, "PreviewMode", "PreviewMode"),

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(3, "None", "None");

	/**
	 * The '<em><b>All</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>All</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALL
	 * @model name="All"
	 * @generated
	 * @ordered
	 */
	public static final int ALL_VALUE = 0;

	/**
	 * The '<em><b>Design Mode</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Design Mode</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DESIGN_MODE
	 * @model name="DesignMode"
	 * @generated
	 * @ordered
	 */
	public static final int DESIGN_MODE_VALUE = 1;

	/**
	 * The '<em><b>Preview Mode</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Preview Mode</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PREVIEW_MODE
	 * @model name="PreviewMode"
	 * @generated
	 * @ordered
	 */
	public static final int PREVIEW_MODE_VALUE = 2;

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model name="None"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Editor Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EditorMode[] VALUES_ARRAY =
		new EditorMode[] {
			ALL,
			DESIGN_MODE,
			PREVIEW_MODE,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Editor Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EditorMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Editor Mode</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * @param literal
	 * @return EditorMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditorMode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EditorMode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Editor Mode</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * @param name
	 * @return EditorMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditorMode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EditorMode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Editor Mode</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return EditorMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditorMode get(int value) {
		switch (value) {
			case ALL_VALUE: return ALL;
			case DESIGN_MODE_VALUE: return DESIGN_MODE;
			case PREVIEW_MODE_VALUE: return PREVIEW_MODE;
			case NONE_VALUE: return NONE;
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
	private EditorMode(int value, String name, String literal) {
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
	
} //EditorMode
