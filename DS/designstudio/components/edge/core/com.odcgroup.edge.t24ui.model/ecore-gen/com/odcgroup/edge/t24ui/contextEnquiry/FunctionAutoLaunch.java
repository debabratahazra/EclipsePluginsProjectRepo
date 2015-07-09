/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Auto Launch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch#getFunctions <em>Functions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getFunctionAutoLaunch()
 * @model
 * @generated
 */
public interface FunctionAutoLaunch extends AutoLaunch {
	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.contextEnquiry.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage#getFunctionAutoLaunch_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Function> getFunctions();

} // FunctionAutoLaunch
