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
 * A representation of the literals of the enumeration '<em><b>Scripting Language</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.odcgroup.process.model.ProcessPackage#getScriptingLanguage()
 * @model
 * @generated
 */
public final class ScriptingLanguage extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The '<em><b>Jython</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Jython</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JYTHON_LITERAL
	 * @model name="Jython" literal="jython"
	 * @generated
	 * @ordered
	 */
	public static final int JYTHON = 0;

	/**
	 * The '<em><b>Bean Shell</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bean Shell</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BEAN_SHELL_LITERAL
	 * @model name="BeanShell" literal="beanshell"
	 * @generated
	 * @ordered
	 */
	public static final int BEAN_SHELL = 1;

	/**
	 * The '<em><b>Javascript</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Javascript</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVASCRIPT_LITERAL
	 * @model name="Javascript" literal="javascript"
	 * @generated
	 * @ordered
	 */
	public static final int JAVASCRIPT = 2;

	/**
	 * The '<em><b>Jython</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JYTHON
	 * @generated
	 * @ordered
	 */
	public static final ScriptingLanguage JYTHON_LITERAL = new ScriptingLanguage(JYTHON, "Jython", "jython");

	/**
	 * The '<em><b>Bean Shell</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BEAN_SHELL
	 * @generated
	 * @ordered
	 */
	public static final ScriptingLanguage BEAN_SHELL_LITERAL = new ScriptingLanguage(BEAN_SHELL, "BeanShell", "beanshell");

	/**
	 * The '<em><b>Javascript</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVASCRIPT
	 * @generated
	 * @ordered
	 */
	public static final ScriptingLanguage JAVASCRIPT_LITERAL = new ScriptingLanguage(JAVASCRIPT, "Javascript", "javascript");

	/**
	 * An array of all the '<em><b>Scripting Language</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ScriptingLanguage[] VALUES_ARRAY =
		new ScriptingLanguage[] {
			JYTHON_LITERAL,
			BEAN_SHELL_LITERAL,
			JAVASCRIPT_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Scripting Language</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Scripting Language</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScriptingLanguage get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScriptingLanguage result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scripting Language</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScriptingLanguage getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScriptingLanguage result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scripting Language</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScriptingLanguage get(int value) {
		switch (value) {
			case JYTHON: return JYTHON_LITERAL;
			case BEAN_SHELL: return BEAN_SHELL_LITERAL;
			case JAVASCRIPT: return JAVASCRIPT_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ScriptingLanguage(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ScriptingLanguage
