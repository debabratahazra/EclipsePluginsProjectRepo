/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Component Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel#getChildPanels <em>Child Panels</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getMultiComponentPanel()
 * @model
 * @generated
 */
public interface MultiComponentPanel extends ChildResourceSpec, COSPanel, COSPatternContainer {
	/**
	 * Returns the value of the '<em><b>Child Panels</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Panels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Panels</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getMultiComponentPanel_ChildPanels()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<COSPanel> getChildPanels();

} // MultiComponentPanel
