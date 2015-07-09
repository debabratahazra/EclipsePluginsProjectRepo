/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Panel Overflow Option</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getPanelOverflowOption()
 * @model
 * @generated
 */
public enum PanelOverflowOption implements Enumerator {
	/**
	 * The '<em><b>HIDE OVERFLOW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HIDE_OVERFLOW_VALUE
	 * @generated
	 * @ordered
	 */
	HIDE_OVERFLOW(0, "HIDE_OVERFLOW", "HIDE_OVERFLOW"),

	/**
	 * The '<em><b>SHOW SCROLLBAR ALWAYS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHOW_SCROLLBAR_ALWAYS_VALUE
	 * @generated
	 * @ordered
	 */
	SHOW_SCROLLBAR_ALWAYS(0, "SHOW_SCROLLBAR_ALWAYS", "SHOW_SCROLLBAR_ALWAYS"),

	/**
	 * The '<em><b>SHOW SCROLLBAR IF NEEDED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHOW_SCROLLBAR_IF_NEEDED_VALUE
	 * @generated
	 * @ordered
	 */
	SHOW_SCROLLBAR_IF_NEEDED(0, "SHOW_SCROLLBAR_IF_NEEDED", "SHOW_SCROLLBAR_IF_NEEDED");

	/**
	 * The '<em><b>HIDE OVERFLOW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HIDE OVERFLOW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HIDE_OVERFLOW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int HIDE_OVERFLOW_VALUE = 0;

	/**
	 * The '<em><b>SHOW SCROLLBAR ALWAYS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHOW SCROLLBAR ALWAYS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHOW_SCROLLBAR_ALWAYS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SHOW_SCROLLBAR_ALWAYS_VALUE = 0;

	/**
	 * The '<em><b>SHOW SCROLLBAR IF NEEDED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHOW SCROLLBAR IF NEEDED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHOW_SCROLLBAR_IF_NEEDED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SHOW_SCROLLBAR_IF_NEEDED_VALUE = 0;

	/**
	 * An array of all the '<em><b>Panel Overflow Option</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PanelOverflowOption[] VALUES_ARRAY =
		new PanelOverflowOption[] {
			HIDE_OVERFLOW,
			SHOW_SCROLLBAR_ALWAYS,
			SHOW_SCROLLBAR_IF_NEEDED,
		};

	/**
	 * A public read-only list of all the '<em><b>Panel Overflow Option</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PanelOverflowOption> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Panel Overflow Option</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelOverflowOption get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelOverflowOption result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel Overflow Option</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelOverflowOption getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelOverflowOption result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel Overflow Option</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelOverflowOption get(int value) {
		switch (value) {
			case HIDE_OVERFLOW_VALUE: return HIDE_OVERFLOW;
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
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PanelOverflowOption(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
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
	
} //PanelOverflowOption
