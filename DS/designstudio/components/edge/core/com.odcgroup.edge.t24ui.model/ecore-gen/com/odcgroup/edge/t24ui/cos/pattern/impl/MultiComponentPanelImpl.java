/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.common.Translation;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.edge.t24ui.cos.pattern.COSPanel;
import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;
import com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel;
import com.odcgroup.edge.t24ui.cos.pattern.PanelOverflowOption;
import com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Component Panel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isHostableContentAll <em>Hostable Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isHostableBespokeCOSContentAll <em>Hostable Bespoke COS Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHostableBespokeCOSContent <em>Hostable Bespoke COS Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isHostableCOSContentAll <em>Hostable COS Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHostableCOSContent <em>Hostable COS Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isHostableEnquiryContentAll <em>Hostable Enquiry Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHostableEnquiryContent <em>Hostable Enquiry Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isHostableScreenContentAll <em>Hostable Screen Content All</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHostableScreenContent <em>Hostable Screen Content</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getHorizontalOverflowOption <em>Horizontal Overflow Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getVerticalOverflowOption <em>Vertical Overflow Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getPanelSeparatorOption <em>Panel Separator Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#isAccordionPatternMultiExpandable <em>Accordion Pattern Multi Expandable</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.MultiComponentPanelImpl#getChildPanels <em>Child Panels</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MultiComponentPanelImpl extends ChildResourceSpecImpl implements MultiComponentPanel {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #isHostableContentAll() <em>Hostable Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableContentAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOSTABLE_CONTENT_ALL_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHostableContentAll() <em>Hostable Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableContentAll()
	 * @generated
	 * @ordered
	 */
	protected boolean hostableContentAll = HOSTABLE_CONTENT_ALL_EDEFAULT;
	/**
	 * The default value of the '{@link #isHostableBespokeCOSContentAll() <em>Hostable Bespoke COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableBespokeCOSContentAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOSTABLE_BESPOKE_COS_CONTENT_ALL_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHostableBespokeCOSContentAll() <em>Hostable Bespoke COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableBespokeCOSContentAll()
	 * @generated
	 * @ordered
	 */
	protected boolean hostableBespokeCOSContentAll = HOSTABLE_BESPOKE_COS_CONTENT_ALL_EDEFAULT;
	/**
	 * The cached value of the '{@link #getHostableBespokeCOSContent() <em>Hostable Bespoke COS Content</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostableBespokeCOSContent()
	 * @generated
	 * @ordered
	 */
	protected EList<BespokeCompositeScreen> hostableBespokeCOSContent;
	/**
	 * The default value of the '{@link #isHostableCOSContentAll() <em>Hostable COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableCOSContentAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOSTABLE_COS_CONTENT_ALL_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHostableCOSContentAll() <em>Hostable COS Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableCOSContentAll()
	 * @generated
	 * @ordered
	 */
	protected boolean hostableCOSContentAll = HOSTABLE_COS_CONTENT_ALL_EDEFAULT;
	/**
	 * The cached value of the '{@link #getHostableCOSContent() <em>Hostable COS Content</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostableCOSContent()
	 * @generated
	 * @ordered
	 */
	protected EList<CompositeScreen> hostableCOSContent;
	/**
	 * The default value of the '{@link #isHostableEnquiryContentAll() <em>Hostable Enquiry Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableEnquiryContentAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOSTABLE_ENQUIRY_CONTENT_ALL_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHostableEnquiryContentAll() <em>Hostable Enquiry Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableEnquiryContentAll()
	 * @generated
	 * @ordered
	 */
	protected boolean hostableEnquiryContentAll = HOSTABLE_ENQUIRY_CONTENT_ALL_EDEFAULT;
	/**
	 * The cached value of the '{@link #getHostableEnquiryContent() <em>Hostable Enquiry Content</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostableEnquiryContent()
	 * @generated
	 * @ordered
	 */
	protected EList<Enquiry> hostableEnquiryContent;
	/**
	 * The default value of the '{@link #isHostableScreenContentAll() <em>Hostable Screen Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableScreenContentAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HOSTABLE_SCREEN_CONTENT_ALL_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHostableScreenContentAll() <em>Hostable Screen Content All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHostableScreenContentAll()
	 * @generated
	 * @ordered
	 */
	protected boolean hostableScreenContentAll = HOSTABLE_SCREEN_CONTENT_ALL_EDEFAULT;
	/**
	 * The cached value of the '{@link #getHostableScreenContent() <em>Hostable Screen Content</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostableScreenContent()
	 * @generated
	 * @ordered
	 */
	protected EList<Version> hostableScreenContent;
	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected EList<Translation> title;
	/**
	 * The default value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackgroundColor()
	 * @generated
	 * @ordered
	 */
	protected static final String BACKGROUND_COLOR_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackgroundColor()
	 * @generated
	 * @ordered
	 */
	protected String backgroundColor = BACKGROUND_COLOR_EDEFAULT;
	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final String HEIGHT_EDEFAULT = "auto";
	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected String height = HEIGHT_EDEFAULT;
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final String WIDTH_EDEFAULT = "auto";
	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected String width = WIDTH_EDEFAULT;
	/**
	 * The default value of the '{@link #getHorizontalOverflowOption() <em>Horizontal Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHorizontalOverflowOption()
	 * @generated
	 * @ordered
	 */
	protected static final PanelOverflowOption HORIZONTAL_OVERFLOW_OPTION_EDEFAULT = PanelOverflowOption.HIDE_OVERFLOW;
	/**
	 * The cached value of the '{@link #getHorizontalOverflowOption() <em>Horizontal Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHorizontalOverflowOption()
	 * @generated
	 * @ordered
	 */
	protected PanelOverflowOption horizontalOverflowOption = HORIZONTAL_OVERFLOW_OPTION_EDEFAULT;
	/**
	 * The default value of the '{@link #getVerticalOverflowOption() <em>Vertical Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerticalOverflowOption()
	 * @generated
	 * @ordered
	 */
	protected static final PanelOverflowOption VERTICAL_OVERFLOW_OPTION_EDEFAULT = PanelOverflowOption.HIDE_OVERFLOW;
	/**
	 * The cached value of the '{@link #getVerticalOverflowOption() <em>Vertical Overflow Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerticalOverflowOption()
	 * @generated
	 * @ordered
	 */
	protected PanelOverflowOption verticalOverflowOption = VERTICAL_OVERFLOW_OPTION_EDEFAULT;
	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected COSPattern pattern;
	/**
	 * The default value of the '{@link #getPanelSeparatorOption() <em>Panel Separator Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanelSeparatorOption()
	 * @generated
	 * @ordered
	 */
	protected static final PanelSeparatorOption PANEL_SEPARATOR_OPTION_EDEFAULT = PanelSeparatorOption.NO_SEPARATORS;
	/**
	 * The cached value of the '{@link #getPanelSeparatorOption() <em>Panel Separator Option</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanelSeparatorOption()
	 * @generated
	 * @ordered
	 */
	protected PanelSeparatorOption panelSeparatorOption = PANEL_SEPARATOR_OPTION_EDEFAULT;
	/**
	 * The default value of the '{@link #isAccordionPatternMultiExpandable() <em>Accordion Pattern Multi Expandable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAccordionPatternMultiExpandable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isAccordionPatternMultiExpandable() <em>Accordion Pattern Multi Expandable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAccordionPatternMultiExpandable()
	 * @generated
	 * @ordered
	 */
	protected boolean accordionPatternMultiExpandable = ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getChildPanels() <em>Child Panels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildPanels()
	 * @generated
	 * @ordered
	 */
	protected EList<COSPanel> childPanels;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiComponentPanelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.MULTI_COMPONENT_PANEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHostableContentAll() {
		return hostableContentAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostableContentAll(boolean newHostableContentAll) {
		boolean oldHostableContentAll = hostableContentAll;
		hostableContentAll = newHostableContentAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL, oldHostableContentAll, hostableContentAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHostableBespokeCOSContentAll() {
		return hostableBespokeCOSContentAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostableBespokeCOSContentAll(boolean newHostableBespokeCOSContentAll) {
		boolean oldHostableBespokeCOSContentAll = hostableBespokeCOSContentAll;
		hostableBespokeCOSContentAll = newHostableBespokeCOSContentAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL, oldHostableBespokeCOSContentAll, hostableBespokeCOSContentAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BespokeCompositeScreen> getHostableBespokeCOSContent() {
		if (hostableBespokeCOSContent == null) {
			hostableBespokeCOSContent = new EObjectResolvingEList<BespokeCompositeScreen>(BespokeCompositeScreen.class, this, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT);
		}
		return hostableBespokeCOSContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHostableCOSContentAll() {
		return hostableCOSContentAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostableCOSContentAll(boolean newHostableCOSContentAll) {
		boolean oldHostableCOSContentAll = hostableCOSContentAll;
		hostableCOSContentAll = newHostableCOSContentAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL, oldHostableCOSContentAll, hostableCOSContentAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CompositeScreen> getHostableCOSContent() {
		if (hostableCOSContent == null) {
			hostableCOSContent = new EObjectResolvingEList<CompositeScreen>(CompositeScreen.class, this, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT);
		}
		return hostableCOSContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHostableEnquiryContentAll() {
		return hostableEnquiryContentAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostableEnquiryContentAll(boolean newHostableEnquiryContentAll) {
		boolean oldHostableEnquiryContentAll = hostableEnquiryContentAll;
		hostableEnquiryContentAll = newHostableEnquiryContentAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL, oldHostableEnquiryContentAll, hostableEnquiryContentAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Enquiry> getHostableEnquiryContent() {
		if (hostableEnquiryContent == null) {
			hostableEnquiryContent = new EObjectResolvingEList<Enquiry>(Enquiry.class, this, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT);
		}
		return hostableEnquiryContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHostableScreenContentAll() {
		return hostableScreenContentAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostableScreenContentAll(boolean newHostableScreenContentAll) {
		boolean oldHostableScreenContentAll = hostableScreenContentAll;
		hostableScreenContentAll = newHostableScreenContentAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL, oldHostableScreenContentAll, hostableScreenContentAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Version> getHostableScreenContent() {
		if (hostableScreenContent == null) {
			hostableScreenContent = new EObjectResolvingEList<Version>(Version.class, this, PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT);
		}
		return hostableScreenContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translation> getTitle() {
		if (title == null) {
			title = new EObjectContainmentEList<Translation>(Translation.class, this, PatternPackage.MULTI_COMPONENT_PANEL__TITLE);
		}
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackgroundColor(String newBackgroundColor) {
		String oldBackgroundColor = backgroundColor;
		backgroundColor = newBackgroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR, oldBackgroundColor, backgroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(String newHeight) {
		String oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(String newWidth) {
		String oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelOverflowOption getHorizontalOverflowOption() {
		return horizontalOverflowOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHorizontalOverflowOption(PanelOverflowOption newHorizontalOverflowOption) {
		PanelOverflowOption oldHorizontalOverflowOption = horizontalOverflowOption;
		horizontalOverflowOption = newHorizontalOverflowOption == null ? HORIZONTAL_OVERFLOW_OPTION_EDEFAULT : newHorizontalOverflowOption;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION, oldHorizontalOverflowOption, horizontalOverflowOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelOverflowOption getVerticalOverflowOption() {
		return verticalOverflowOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVerticalOverflowOption(PanelOverflowOption newVerticalOverflowOption) {
		PanelOverflowOption oldVerticalOverflowOption = verticalOverflowOption;
		verticalOverflowOption = newVerticalOverflowOption == null ? VERTICAL_OVERFLOW_OPTION_EDEFAULT : newVerticalOverflowOption;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION, oldVerticalOverflowOption, verticalOverflowOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public COSPattern getPattern() {
		if (pattern != null && pattern.eIsProxy()) {
			InternalEObject oldPattern = (InternalEObject)pattern;
			pattern = (COSPattern)eResolveProxy(oldPattern);
			if (pattern != oldPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.MULTI_COMPONENT_PANEL__PATTERN, oldPattern, pattern));
			}
		}
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public COSPattern basicGetPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPattern(COSPattern newPattern) {
		COSPattern oldPattern = pattern;
		pattern = newPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__PATTERN, oldPattern, pattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelSeparatorOption getPanelSeparatorOption() {
		return panelSeparatorOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPanelSeparatorOption(PanelSeparatorOption newPanelSeparatorOption) {
		PanelSeparatorOption oldPanelSeparatorOption = panelSeparatorOption;
		panelSeparatorOption = newPanelSeparatorOption == null ? PANEL_SEPARATOR_OPTION_EDEFAULT : newPanelSeparatorOption;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION, oldPanelSeparatorOption, panelSeparatorOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAccordionPatternMultiExpandable() {
		return accordionPatternMultiExpandable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccordionPatternMultiExpandable(boolean newAccordionPatternMultiExpandable) {
		boolean oldAccordionPatternMultiExpandable = accordionPatternMultiExpandable;
		accordionPatternMultiExpandable = newAccordionPatternMultiExpandable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE, oldAccordionPatternMultiExpandable, accordionPatternMultiExpandable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<COSPanel> getChildPanels() {
		if (childPanels == null) {
			childPanels = new EObjectContainmentEList<COSPanel>(COSPanel.class, this, PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS);
		}
		return childPanels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PatternPackage.MULTI_COMPONENT_PANEL__TITLE:
				return ((InternalEList<?>)getTitle()).basicRemove(otherEnd, msgs);
			case PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS:
				return ((InternalEList<?>)getChildPanels()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.MULTI_COMPONENT_PANEL__NAME:
				return getName();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL:
				return isHostableContentAll();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL:
				return isHostableBespokeCOSContentAll();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT:
				return getHostableBespokeCOSContent();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL:
				return isHostableCOSContentAll();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT:
				return getHostableCOSContent();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL:
				return isHostableEnquiryContentAll();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT:
				return getHostableEnquiryContent();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL:
				return isHostableScreenContentAll();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT:
				return getHostableScreenContent();
			case PatternPackage.MULTI_COMPONENT_PANEL__TITLE:
				return getTitle();
			case PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR:
				return getBackgroundColor();
			case PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT:
				return getHeight();
			case PatternPackage.MULTI_COMPONENT_PANEL__WIDTH:
				return getWidth();
			case PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION:
				return getHorizontalOverflowOption();
			case PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION:
				return getVerticalOverflowOption();
			case PatternPackage.MULTI_COMPONENT_PANEL__PATTERN:
				if (resolve) return getPattern();
				return basicGetPattern();
			case PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION:
				return getPanelSeparatorOption();
			case PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				return isAccordionPatternMultiExpandable();
			case PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS:
				return getChildPanels();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PatternPackage.MULTI_COMPONENT_PANEL__NAME:
				setName((String)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL:
				setHostableContentAll((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL:
				setHostableBespokeCOSContentAll((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT:
				getHostableBespokeCOSContent().clear();
				getHostableBespokeCOSContent().addAll((Collection<? extends BespokeCompositeScreen>)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL:
				setHostableCOSContentAll((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT:
				getHostableCOSContent().clear();
				getHostableCOSContent().addAll((Collection<? extends CompositeScreen>)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL:
				setHostableEnquiryContentAll((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT:
				getHostableEnquiryContent().clear();
				getHostableEnquiryContent().addAll((Collection<? extends Enquiry>)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL:
				setHostableScreenContentAll((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT:
				getHostableScreenContent().clear();
				getHostableScreenContent().addAll((Collection<? extends Version>)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__TITLE:
				getTitle().clear();
				getTitle().addAll((Collection<? extends Translation>)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR:
				setBackgroundColor((String)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT:
				setHeight((String)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__WIDTH:
				setWidth((String)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION:
				setHorizontalOverflowOption((PanelOverflowOption)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION:
				setVerticalOverflowOption((PanelOverflowOption)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__PATTERN:
				setPattern((COSPattern)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION:
				setPanelSeparatorOption((PanelSeparatorOption)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				setAccordionPatternMultiExpandable((Boolean)newValue);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS:
				getChildPanels().clear();
				getChildPanels().addAll((Collection<? extends COSPanel>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PatternPackage.MULTI_COMPONENT_PANEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL:
				setHostableContentAll(HOSTABLE_CONTENT_ALL_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL:
				setHostableBespokeCOSContentAll(HOSTABLE_BESPOKE_COS_CONTENT_ALL_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT:
				getHostableBespokeCOSContent().clear();
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL:
				setHostableCOSContentAll(HOSTABLE_COS_CONTENT_ALL_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT:
				getHostableCOSContent().clear();
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL:
				setHostableEnquiryContentAll(HOSTABLE_ENQUIRY_CONTENT_ALL_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT:
				getHostableEnquiryContent().clear();
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL:
				setHostableScreenContentAll(HOSTABLE_SCREEN_CONTENT_ALL_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT:
				getHostableScreenContent().clear();
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__TITLE:
				getTitle().clear();
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR:
				setBackgroundColor(BACKGROUND_COLOR_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION:
				setHorizontalOverflowOption(HORIZONTAL_OVERFLOW_OPTION_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION:
				setVerticalOverflowOption(VERTICAL_OVERFLOW_OPTION_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__PATTERN:
				setPattern((COSPattern)null);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION:
				setPanelSeparatorOption(PANEL_SEPARATOR_OPTION_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				setAccordionPatternMultiExpandable(ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT);
				return;
			case PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS:
				getChildPanels().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PatternPackage.MULTI_COMPONENT_PANEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL:
				return hostableContentAll != HOSTABLE_CONTENT_ALL_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL:
				return hostableBespokeCOSContentAll != HOSTABLE_BESPOKE_COS_CONTENT_ALL_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT:
				return hostableBespokeCOSContent != null && !hostableBespokeCOSContent.isEmpty();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL:
				return hostableCOSContentAll != HOSTABLE_COS_CONTENT_ALL_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT:
				return hostableCOSContent != null && !hostableCOSContent.isEmpty();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL:
				return hostableEnquiryContentAll != HOSTABLE_ENQUIRY_CONTENT_ALL_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT:
				return hostableEnquiryContent != null && !hostableEnquiryContent.isEmpty();
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL:
				return hostableScreenContentAll != HOSTABLE_SCREEN_CONTENT_ALL_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT:
				return hostableScreenContent != null && !hostableScreenContent.isEmpty();
			case PatternPackage.MULTI_COMPONENT_PANEL__TITLE:
				return title != null && !title.isEmpty();
			case PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR:
				return BACKGROUND_COLOR_EDEFAULT == null ? backgroundColor != null : !BACKGROUND_COLOR_EDEFAULT.equals(backgroundColor);
			case PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT:
				return HEIGHT_EDEFAULT == null ? height != null : !HEIGHT_EDEFAULT.equals(height);
			case PatternPackage.MULTI_COMPONENT_PANEL__WIDTH:
				return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT.equals(width);
			case PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION:
				return horizontalOverflowOption != HORIZONTAL_OVERFLOW_OPTION_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION:
				return verticalOverflowOption != VERTICAL_OVERFLOW_OPTION_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__PATTERN:
				return pattern != null;
			case PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION:
				return panelSeparatorOption != PANEL_SEPARATOR_OPTION_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				return accordionPatternMultiExpandable != ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT;
			case PatternPackage.MULTI_COMPONENT_PANEL__CHILD_PANELS:
				return childPanels != null && !childPanels.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == COSPanel.class) {
			switch (derivedFeatureID) {
				case PatternPackage.MULTI_COMPONENT_PANEL__NAME: return PatternPackage.COS_PANEL__NAME;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL: return PatternPackage.COS_PANEL__HOSTABLE_CONTENT_ALL;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL: return PatternPackage.COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT: return PatternPackage.COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL: return PatternPackage.COS_PANEL__HOSTABLE_COS_CONTENT_ALL;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT: return PatternPackage.COS_PANEL__HOSTABLE_COS_CONTENT;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL: return PatternPackage.COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT: return PatternPackage.COS_PANEL__HOSTABLE_ENQUIRY_CONTENT;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL: return PatternPackage.COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL;
				case PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT: return PatternPackage.COS_PANEL__HOSTABLE_SCREEN_CONTENT;
				case PatternPackage.MULTI_COMPONENT_PANEL__TITLE: return PatternPackage.COS_PANEL__TITLE;
				case PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR: return PatternPackage.COS_PANEL__BACKGROUND_COLOR;
				case PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT: return PatternPackage.COS_PANEL__HEIGHT;
				case PatternPackage.MULTI_COMPONENT_PANEL__WIDTH: return PatternPackage.COS_PANEL__WIDTH;
				case PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION: return PatternPackage.COS_PANEL__HORIZONTAL_OVERFLOW_OPTION;
				case PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION: return PatternPackage.COS_PANEL__VERTICAL_OVERFLOW_OPTION;
				default: return -1;
			}
		}
		if (baseClass == COSPatternContainer.class) {
			switch (derivedFeatureID) {
				case PatternPackage.MULTI_COMPONENT_PANEL__PATTERN: return PatternPackage.COS_PATTERN_CONTAINER__PATTERN;
				case PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION: return PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION;
				case PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE: return PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == COSPanel.class) {
			switch (baseFeatureID) {
				case PatternPackage.COS_PANEL__NAME: return PatternPackage.MULTI_COMPONENT_PANEL__NAME;
				case PatternPackage.COS_PANEL__HOSTABLE_CONTENT_ALL: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_CONTENT_ALL;
				case PatternPackage.COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT_ALL;
				case PatternPackage.COS_PANEL__HOSTABLE_BESPOKE_COS_CONTENT: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_BESPOKE_COS_CONTENT;
				case PatternPackage.COS_PANEL__HOSTABLE_COS_CONTENT_ALL: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT_ALL;
				case PatternPackage.COS_PANEL__HOSTABLE_COS_CONTENT: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_COS_CONTENT;
				case PatternPackage.COS_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT_ALL;
				case PatternPackage.COS_PANEL__HOSTABLE_ENQUIRY_CONTENT: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_ENQUIRY_CONTENT;
				case PatternPackage.COS_PANEL__HOSTABLE_SCREEN_CONTENT_ALL: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT_ALL;
				case PatternPackage.COS_PANEL__HOSTABLE_SCREEN_CONTENT: return PatternPackage.MULTI_COMPONENT_PANEL__HOSTABLE_SCREEN_CONTENT;
				case PatternPackage.COS_PANEL__TITLE: return PatternPackage.MULTI_COMPONENT_PANEL__TITLE;
				case PatternPackage.COS_PANEL__BACKGROUND_COLOR: return PatternPackage.MULTI_COMPONENT_PANEL__BACKGROUND_COLOR;
				case PatternPackage.COS_PANEL__HEIGHT: return PatternPackage.MULTI_COMPONENT_PANEL__HEIGHT;
				case PatternPackage.COS_PANEL__WIDTH: return PatternPackage.MULTI_COMPONENT_PANEL__WIDTH;
				case PatternPackage.COS_PANEL__HORIZONTAL_OVERFLOW_OPTION: return PatternPackage.MULTI_COMPONENT_PANEL__HORIZONTAL_OVERFLOW_OPTION;
				case PatternPackage.COS_PANEL__VERTICAL_OVERFLOW_OPTION: return PatternPackage.MULTI_COMPONENT_PANEL__VERTICAL_OVERFLOW_OPTION;
				default: return -1;
			}
		}
		if (baseClass == COSPatternContainer.class) {
			switch (baseFeatureID) {
				case PatternPackage.COS_PATTERN_CONTAINER__PATTERN: return PatternPackage.MULTI_COMPONENT_PANEL__PATTERN;
				case PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION: return PatternPackage.MULTI_COMPONENT_PANEL__PANEL_SEPARATOR_OPTION;
				case PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE: return PatternPackage.MULTI_COMPONENT_PANEL__ACCORDION_PATTERN_MULTI_EXPANDABLE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", hostableContentAll: ");
		result.append(hostableContentAll);
		result.append(", hostableBespokeCOSContentAll: ");
		result.append(hostableBespokeCOSContentAll);
		result.append(", hostableCOSContentAll: ");
		result.append(hostableCOSContentAll);
		result.append(", hostableEnquiryContentAll: ");
		result.append(hostableEnquiryContentAll);
		result.append(", hostableScreenContentAll: ");
		result.append(hostableScreenContentAll);
		result.append(", backgroundColor: ");
		result.append(backgroundColor);
		result.append(", height: ");
		result.append(height);
		result.append(", width: ");
		result.append(width);
		result.append(", horizontalOverflowOption: ");
		result.append(horizontalOverflowOption);
		result.append(", verticalOverflowOption: ");
		result.append(verticalOverflowOption);
		result.append(", panelSeparatorOption: ");
		result.append(panelSeparatorOption);
		result.append(", accordionPatternMultiExpandable: ");
		result.append(accordionPatternMultiExpandable);
		result.append(')');
		return result.toString();
	}

} //MultiComponentPanelImpl
