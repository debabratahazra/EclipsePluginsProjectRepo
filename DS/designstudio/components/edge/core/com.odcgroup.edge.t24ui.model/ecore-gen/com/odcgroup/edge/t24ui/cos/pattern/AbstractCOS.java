/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import com.odcgroup.edge.t24ui.common.Translation;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract COS</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getPanels <em>Panels</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getAbstractCOS()
 * @model abstract="true"
 * @generated
 */
public interface AbstractCOS extends COSPatternContainer {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getAbstractCOS_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Panels</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panels</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getAbstractCOS_Panels()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<COSPanel> getPanels();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.common.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getAbstractCOS_Title()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getTitle();

} // AbstractCOS
