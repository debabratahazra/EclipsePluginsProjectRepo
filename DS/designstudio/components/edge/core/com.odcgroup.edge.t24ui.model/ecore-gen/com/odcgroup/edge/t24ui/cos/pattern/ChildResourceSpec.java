/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Child Resource Spec</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getChildResourceSpec()
 * @model abstract="true"
 * @generated
 */
public interface ChildResourceSpec extends InitialPanelContentSpec {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute.
	 * @see #setParameters(String)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getChildResourceSpec_Parameters()
	 * @model
	 * @generated
	 */
	String getParameters();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec#getParameters <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameters</em>' attribute.
	 * @see #getParameters()
	 * @generated
	 */
	void setParameters(String value);

} // ChildResourceSpec
