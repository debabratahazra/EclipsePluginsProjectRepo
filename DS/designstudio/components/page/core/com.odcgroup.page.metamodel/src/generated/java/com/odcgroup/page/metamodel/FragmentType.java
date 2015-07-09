/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Fragment Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @model
 * @generated
 */
public enum FragmentType implements Enumerator {
	/**
	 * The '<em><b>Initialisation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INITIALISATION_VALUE
	 * @generated
	 * @ordered
	 */
	INITIALISATION(0, "initialisation", "initialisation"),

	/**
	 * The '<em><b>Header</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HEADER_VALUE
	 * @generated
	 * @ordered
	 */
	HEADER(0, "header", "header"),

	/**
	 * The '<em><b>Body</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BODY_VALUE
	 * @generated
	 * @ordered
	 */
	BODY(0, "body", "body");

	/**
	 * The '<em><b>Initialisation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Initialisation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INITIALISATION
	 * @model name="initialisation"
	 * @generated
	 * @ordered
	 */
	public static final int INITIALISATION_VALUE = 0;

	/**
	 * The '<em><b>Header</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Header</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HEADER
	 * @model name="header"
	 * @generated
	 * @ordered
	 */
	public static final int HEADER_VALUE = 0;

	/**
	 * The '<em><b>Body</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Body</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BODY
	 * @model name="body"
	 * @generated
	 * @ordered
	 */
	public static final int BODY_VALUE = 0;

	/**
	 * An array of all the '<em><b>Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FragmentType[] VALUES_ARRAY =
		new FragmentType[] {
			INITIALISATION,
			HEADER,
			BODY,
		};

	/**
	 * A public read-only list of all the '<em><b>Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FragmentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal The literal
	 * @return Fragment type The corresponding fragment type
	 * @generated
	 */
	public static FragmentType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FragmentType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param name The name of the fragment type
	 * @return FragmentType The corresponding FragmentType
	 * @generated
	 */
	public static FragmentType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FragmentType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Fragment Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value The value of the FragmentType
	 * @return FragmentType The corresponding FragmentType
	 * @generated
	 */
	public static FragmentType get(int value) {
		switch (value) {
			case INITIALISATION_VALUE: return INITIALISATION;
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
	private FragmentType(int value, String name, String literal) {
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
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //FragmentType
