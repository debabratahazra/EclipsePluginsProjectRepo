/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Translation Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.process.model.ProcessPackage#getTranslationKind()
 * @model
 * @generated
 */
public final class TranslationKind extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The '<em><b>Text</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Text</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEXT_LITERAL
	 * @model name="Text"
	 * @generated
	 * @ordered
	 */
	public static final int TEXT = 1;

	/**
	 * The '<em><b>Name</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Name</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NAME_LITERAL
	 * @model name="Name"
	 * @generated
	 * @ordered
	 */
	public static final int NAME = 0;

	/**
	 * The '<em><b>Text</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEXT
	 * @generated
	 * @ordered
	 */
	public static final TranslationKind TEXT_LITERAL = new TranslationKind(TEXT, "Text", "Text");

	/**
	 * The '<em><b>Name</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NAME
	 * @generated
	 * @ordered
	 */
	public static final TranslationKind NAME_LITERAL = new TranslationKind(NAME, "Name", "Name");

	/**
	 * An array of all the '<em><b>Translation Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TranslationKind[] VALUES_ARRAY =
		new TranslationKind[] {
			TEXT_LITERAL,
			NAME_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Translation Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Translation Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TranslationKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TranslationKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Translation Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TranslationKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TranslationKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Translation Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TranslationKind get(int value) {
		switch (value) {
			case TEXT: return TEXT_LITERAL;
			case NAME: return NAME_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TranslationKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //TranslationKind
