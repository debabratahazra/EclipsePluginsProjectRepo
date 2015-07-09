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
 * A representation of the literals of the enumeration '<em><b>Edit Policy Role Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.uimodel.UIModelPackage#getEditPolicyRoleType()
 * @model
 * @generated
 */
public enum EditPolicyRoleType implements Enumerator {
	/**
	 * The '<em><b>Direct Edit Policy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIRECT_EDIT_POLICY_VALUE
	 * @generated
	 * @ordered
	 */
	DIRECT_EDIT_POLICY(0, "DirectEditPolicy", "DirectEditPolicy");

	/**
	 * The '<em><b>Direct Edit Policy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Direct Edit Policy</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DIRECT_EDIT_POLICY
	 * @model name="DirectEditPolicy"
	 * @generated
	 * @ordered
	 */
	public static final int DIRECT_EDIT_POLICY_VALUE = 0;

	/**
	 * An array of all the '<em><b>Edit Policy Role Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EditPolicyRoleType[] VALUES_ARRAY =
		new EditPolicyRoleType[] {
			DIRECT_EDIT_POLICY,
		};

	/**
	 * A public read-only list of all the '<em><b>Edit Policy Role Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EditPolicyRoleType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Edit Policy Role Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * @param literal
	 * @return EditPolicyRoleType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditPolicyRoleType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EditPolicyRoleType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Edit Policy Role Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * @param name
	 * @return EditPolicyRoleType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditPolicyRoleType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EditPolicyRoleType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Edit Policy Role Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * @param value
	 * @return EditPolicyRoleType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditPolicyRoleType get(int value) {
		switch (value) {
			case DIRECT_EDIT_POLICY_VALUE: return DIRECT_EDIT_POLICY;
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
	private EditPolicyRoleType(int value, String name, String literal) {
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
	
} //EditPolicyRoleType
