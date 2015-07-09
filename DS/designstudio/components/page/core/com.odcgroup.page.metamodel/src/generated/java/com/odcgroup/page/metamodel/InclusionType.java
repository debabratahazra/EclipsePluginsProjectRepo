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
 * A representation of the literals of the enumeration '<em><b>Inclusion Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getInclusionType()
 * @model
 * @generated
 */
public enum InclusionType implements Enumerator
{
	/**
	 * The '<em><b>CINCLUDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CINCLUDE
	 * @generated
	 * @ordered
	 */
	CINCLUDE_LITERAL(1, "CINCLUDE", "CINCLUDE"),
	/**
	 * The '<em><b>XINCLUDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XINCLUDE
	 * @generated
	 * @ordered
	 */
	XINCLUDE_LITERAL(0, "XINCLUDE", "XINCLUDE"),
	/**
	 * The '<em><b>SOURCE INCLUDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOURCE_INCLUDE
	 * @generated
	 * @ordered
	 */
	SOURCE_INCLUDE_LITERAL(2, "SOURCE_INCLUDE", "SOURCE_INCLUDE");
	/**
	 * The '<em><b>CINCLUDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CINCLUDE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CINCLUDE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CINCLUDE = 1;

	/**
	 * The '<em><b>XINCLUDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XINCLUDE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XINCLUDE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int XINCLUDE = 0;

	/**
	 * The '<em><b>SOURCE INCLUDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SOURCE INCLUDE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOURCE_INCLUDE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SOURCE_INCLUDE = 2;

	/**
	 * An array of all the '<em><b>Inclusion Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final InclusionType[] VALUES_ARRAY =
		new InclusionType[] {
			CINCLUDE_LITERAL,
			XINCLUDE_LITERAL,
			SOURCE_INCLUDE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Inclusion Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<InclusionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Inclusion Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * @param literal
	 * @return InclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InclusionType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InclusionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inclusion Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * @param name
	 * @return InclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InclusionType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InclusionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inclusion Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return InclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InclusionType get(int value) {
		switch (value) {
			case CINCLUDE: return CINCLUDE_LITERAL;
			case XINCLUDE: return XINCLUDE_LITERAL;
			case SOURCE_INCLUDE: return SOURCE_INCLUDE_LITERAL;
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
	private InclusionType(int value, String name, String literal) {
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
}
