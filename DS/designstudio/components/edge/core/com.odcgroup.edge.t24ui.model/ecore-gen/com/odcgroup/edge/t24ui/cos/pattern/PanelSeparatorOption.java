/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Panel Separator Option</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getPanelSeparatorOption()
 * @model
 * @generated
 */
public enum PanelSeparatorOption implements Enumerator {
	/**
	 * The '<em><b>NO SEPARATORS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_SEPARATORS_VALUE
	 * @generated
	 * @ordered
	 */
	NO_SEPARATORS(0, "NO_SEPARATORS", "NO_SEPARATORS"),

	/**
	 * The '<em><b>FIXED POSITION SEPARATORS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_POSITION_SEPARATORS_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED_POSITION_SEPARATORS(1, "FIXED_POSITION_SEPARATORS", "FIXED_POSITION_SEPARATORS"),

	/**
	 * The '<em><b>PANEL RESIZE BAR SEPARATORS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PANEL_RESIZE_BAR_SEPARATORS_VALUE
	 * @generated
	 * @ordered
	 */
	PANEL_RESIZE_BAR_SEPARATORS(2, "PANEL_RESIZE_BAR_SEPARATORS", "PANEL_RESIZE_BAR_SEPARATORS");

	/**
	 * The '<em><b>NO SEPARATORS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NO SEPARATORS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_SEPARATORS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NO_SEPARATORS_VALUE = 0;

	/**
	 * The '<em><b>FIXED POSITION SEPARATORS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIXED POSITION SEPARATORS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXED_POSITION_SEPARATORS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_POSITION_SEPARATORS_VALUE = 1;

	/**
	 * The '<em><b>PANEL RESIZE BAR SEPARATORS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PANEL RESIZE BAR SEPARATORS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PANEL_RESIZE_BAR_SEPARATORS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PANEL_RESIZE_BAR_SEPARATORS_VALUE = 2;

	/**
	 * An array of all the '<em><b>Panel Separator Option</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PanelSeparatorOption[] VALUES_ARRAY =
		new PanelSeparatorOption[] {
			NO_SEPARATORS,
			FIXED_POSITION_SEPARATORS,
			PANEL_RESIZE_BAR_SEPARATORS,
		};

	/**
	 * A public read-only list of all the '<em><b>Panel Separator Option</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PanelSeparatorOption> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Panel Separator Option</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelSeparatorOption get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelSeparatorOption result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel Separator Option</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelSeparatorOption getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelSeparatorOption result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel Separator Option</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelSeparatorOption get(int value) {
		switch (value) {
			case NO_SEPARATORS_VALUE: return NO_SEPARATORS;
			case FIXED_POSITION_SEPARATORS_VALUE: return FIXED_POSITION_SEPARATORS;
			case PANEL_RESIZE_BAR_SEPARATORS_VALUE: return PANEL_RESIZE_BAR_SEPARATORS;
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
	private PanelSeparatorOption(int value, String name, String literal) {
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
	
} //PanelSeparatorOption
