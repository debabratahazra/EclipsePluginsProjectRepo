/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.common.Translation;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>COS Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableContentAll <em>Hostable Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableBespokeCOSContentAll <em>Hostable Bespoke COS Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableBespokeCOSContent <em>Hostable Bespoke COS Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableCOSContentAll <em>Hostable COS Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableCOSContent <em>Hostable COS Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableEnquiryContentAll <em>Hostable Enquiry Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableEnquiryContent <em>Hostable Enquiry Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableScreenContentAll <em>Hostable Screen Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHostableScreenContent <em>Hostable Screen Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getTitle <em>Title</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHeight <em>Height</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getWidth <em>Width</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHorizontalOverflowOption <em>Horizontal Overflow Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getVerticalOverflowOption <em>Vertical Overflow Option</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel()
 * @model abstract="true"
 * @generated
 */
public interface COSPanel extends EObject {
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
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Hostable Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Content All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Content All</em>' attribute.
	 * @see #setHostableContentAll(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableContentAll()
	 * @model
	 * @generated
	 */
	boolean isHostableContentAll();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableContentAll <em>Hostable Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostable Content All</em>' attribute.
	 * @see #isHostableContentAll()
	 * @generated
	 */
	void setHostableContentAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Hostable Bespoke COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Bespoke COS Content All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Bespoke COS Content All</em>' attribute.
	 * @see #setHostableBespokeCOSContentAll(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableBespokeCOSContentAll()
	 * @model
	 * @generated
	 */
	boolean isHostableBespokeCOSContentAll();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableBespokeCOSContentAll <em>Hostable Bespoke COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostable Bespoke COS Content All</em>' attribute.
	 * @see #isHostableBespokeCOSContentAll()
	 * @generated
	 */
	void setHostableBespokeCOSContentAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Hostable Bespoke COS Content</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.BespokeCompositeScreen}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Bespoke COS Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Bespoke COS Content</em>' reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableBespokeCOSContent()
	 * @model
	 * @generated
	 */
	EList<BespokeCompositeScreen> getHostableBespokeCOSContent();

	/**
	 * Returns the value of the '<em><b>Hostable COS Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable COS Content All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable COS Content All</em>' attribute.
	 * @see #setHostableCOSContentAll(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableCOSContentAll()
	 * @model
	 * @generated
	 */
	boolean isHostableCOSContentAll();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableCOSContentAll <em>Hostable COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostable COS Content All</em>' attribute.
	 * @see #isHostableCOSContentAll()
	 * @generated
	 */
	void setHostableCOSContentAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Hostable COS Content</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.CompositeScreen}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable COS Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable COS Content</em>' reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableCOSContent()
	 * @model
	 * @generated
	 */
	EList<CompositeScreen> getHostableCOSContent();

	/**
	 * Returns the value of the '<em><b>Hostable Enquiry Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Enquiry Content All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Enquiry Content All</em>' attribute.
	 * @see #setHostableEnquiryContentAll(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableEnquiryContentAll()
	 * @model
	 * @generated
	 */
	boolean isHostableEnquiryContentAll();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableEnquiryContentAll <em>Hostable Enquiry Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostable Enquiry Content All</em>' attribute.
	 * @see #isHostableEnquiryContentAll()
	 * @generated
	 */
	void setHostableEnquiryContentAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Hostable Enquiry Content</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Enquiry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Enquiry Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Enquiry Content</em>' reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableEnquiryContent()
	 * @model
	 * @generated
	 */
	EList<Enquiry> getHostableEnquiryContent();

	/**
	 * Returns the value of the '<em><b>Hostable Screen Content All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Screen Content All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Screen Content All</em>' attribute.
	 * @see #setHostableScreenContentAll(boolean)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableScreenContentAll()
	 * @model
	 * @generated
	 */
	boolean isHostableScreenContentAll();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#isHostableScreenContentAll <em>Hostable Screen Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostable Screen Content All</em>' attribute.
	 * @see #isHostableScreenContentAll()
	 * @generated
	 */
	void setHostableScreenContentAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Hostable Screen Content</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.t24.version.versionDSL.Version}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostable Screen Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostable Screen Content</em>' reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HostableScreenContent()
	 * @model
	 * @generated
	 */
	EList<Version> getHostableScreenContent();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.edge.t24ui.common.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference list.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_Title()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getTitle();

	/**
	 * Returns the value of the '<em><b>Background Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Background Color</em>' attribute.
	 * @see #setBackgroundColor(String)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_BackgroundColor()
	 * @model
	 * @generated
	 */
	String getBackgroundColor();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getBackgroundColor <em>Background Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background Color</em>' attribute.
	 * @see #getBackgroundColor()
	 * @generated
	 */
	void setBackgroundColor(String value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * The default value is <code>"auto"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(String)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_Height()
	 * @model default="auto"
	 * @generated
	 */
	String getHeight();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(String value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"auto"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(String)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_Width()
	 * @model default="auto"
	 * @generated
	 */
	String getWidth();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(String value);

	/**
	 * Returns the value of the '<em><b>Horizontal Overflow Option</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Horizontal Overflow Option</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Horizontal Overflow Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @see #setHorizontalOverflowOption(PanelOverflowOption)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_HorizontalOverflowOption()
	 * @model
	 * @generated
	 */
	PanelOverflowOption getHorizontalOverflowOption();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getHorizontalOverflowOption <em>Horizontal Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Horizontal Overflow Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @see #getHorizontalOverflowOption()
	 * @generated
	 */
	void setHorizontalOverflowOption(PanelOverflowOption value);

	/**
	 * Returns the value of the '<em><b>Vertical Overflow Option</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vertical Overflow Option</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vertical Overflow Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @see #setVerticalOverflowOption(PanelOverflowOption)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getCOSPanel_VerticalOverflowOption()
	 * @model
	 * @generated
	 */
	PanelOverflowOption getVerticalOverflowOption();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel#getVerticalOverflowOption <em>Vertical Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vertical Overflow Option</em>' attribute.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption
	 * @see #getVerticalOverflowOption()
	 * @generated
	 */
	void setVerticalOverflowOption(PanelOverflowOption value);

} // COSPanel
