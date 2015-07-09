/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Problem Management</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.pageflow.model.PageflowPackage#getProblemManagement()
 * @model
 * @generated
 */
public final class ProblemManagement extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The '<em><b>Continue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Continue</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONTINUE_LITERAL
	 * @model name="Continue"
	 * @generated
	 * @ordered
	 */
	public static final int CONTINUE = 0;

	/**
	 * The '<em><b>Validation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Validation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VALIDATION_LITERAL
	 * @model name="Validation"
	 * @generated
	 * @ordered
	 */
	public static final int VALIDATION = 1;

	/**
	 * The '<em><b>Back</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Back</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BACK_LITERAL
	 * @model name="Back" literal="Go back"
	 * @generated
	 * @ordered
	 */
	public static final int BACK = 2;

	/**
	 * The '<em><b>Continue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTINUE
	 * @generated
	 * @ordered
	 */
	public static final ProblemManagement CONTINUE_LITERAL = new ProblemManagement(CONTINUE, "Continue", "Continue");

	/**
	 * The '<em><b>Validation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VALIDATION
	 * @generated
	 * @ordered
	 */
	public static final ProblemManagement VALIDATION_LITERAL = new ProblemManagement(VALIDATION, "Validation", "Validation");

	/**
	 * The '<em><b>Back</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BACK
	 * @generated
	 * @ordered
	 */
	public static final ProblemManagement BACK_LITERAL = new ProblemManagement(BACK, "Back", "Go back");

	/**
	 * An array of all the '<em><b>Problem Management</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ProblemManagement[] VALUES_ARRAY =
		new ProblemManagement[] {
			CONTINUE_LITERAL,
			VALIDATION_LITERAL,
			BACK_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Problem Management</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Problem Management</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProblemManagement get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProblemManagement result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Problem Management</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProblemManagement getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProblemManagement result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Problem Management</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProblemManagement get(int value) {
		switch (value) {
			case CONTINUE: return CONTINUE_LITERAL;
			case VALIDATION: return VALIDATION_LITERAL;
			case BACK: return BACK_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ProblemManagement(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ProblemManagement
