/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Single Component Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel#getInitialContent <em>Initial Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getSingleComponentPanel()
 * @model
 * @generated
 */
public interface SingleComponentPanel extends COSPanel {
	/**
	 * Returns the value of the '<em><b>Initial Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Content</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Content</em>' containment reference.
	 * @see #setInitialContent(InitialPanelContentSpec)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getSingleComponentPanel_InitialContent()
	 * @model containment="true"
	 * @generated
	 */
	InitialPanelContentSpec getInitialContent();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel#getInitialContent <em>Initial Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Content</em>' containment reference.
	 * @see #getInitialContent()
	 * @generated
	 */
	void setInitialContent(InitialPanelContentSpec value);

} // SingleComponentPanel
