/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>COS Pattern Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPanelSeparatorOption <em>Panel Separator Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#isAccordionPatternMultiExpandable <em>Accordion Pattern Multi Expandable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPatternContainer()
 * @model abstract="true"
 * @generated
 */
public interface COSPatternContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pattern</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' reference.
	 * @see #setPattern(COSPattern)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPatternContainer_Pattern()
	 * @model required="true"
	 * @generated
	 */
	COSPattern getPattern();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPattern <em>Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' reference.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(COSPattern value);

	/**
	 * Returns the value of the '<em><b>Panel Separator Option</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panel Separator Option</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panel Separator Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption
	 * @see #setPanelSeparatorOption(PanelSeparatorOption)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPatternContainer_PanelSeparatorOption()
	 * @model
	 * @generated
	 */
	PanelSeparatorOption getPanelSeparatorOption();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#getPanelSeparatorOption <em>Panel Separator Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Panel Separator Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption
	 * @see #getPanelSeparatorOption()
	 * @generated
	 */
	void setPanelSeparatorOption(PanelSeparatorOption value);

	/**
	 * Returns the value of the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accordion Pattern Multi Expandable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accordion Pattern Multi Expandable</em>' attribute.
	 * @see #setAccordionPatternMultiExpandable(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPatternContainer_AccordionPatternMultiExpandable()
	 * @model
	 * @generated
	 */
	boolean isAccordionPatternMultiExpandable();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer#isAccordionPatternMultiExpandable <em>Accordion Pattern Multi Expandable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accordion Pattern Multi Expandable</em>' attribute.
	 * @see #isAccordionPatternMultiExpandable()
	 * @generated
	 */
	void setAccordionPatternMultiExpandable(boolean value);

} // COSPatternContainer
