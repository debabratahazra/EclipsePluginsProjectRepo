/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Palette Group Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getSmallImage <em>Small Image</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getLargeImage <em>Large Image</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getTooltip <em>Tooltip</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getWidgetTemplate <em>Widget Template</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupItemImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PaletteGroupItemImpl extends MinimalEObjectImpl.Container implements PaletteGroupItem {
	/**
	 * The default value of the '{@link #getSmallImage() <em>Small Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallImage()
	 * @generated
	 * @ordered
	 */
	protected static final String SMALL_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSmallImage() <em>Small Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallImage()
	 * @generated
	 * @ordered
	 */
	protected String smallImage = SMALL_IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLargeImage() <em>Large Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLargeImage()
	 * @generated
	 * @ordered
	 */
	protected static final String LARGE_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLargeImage() <em>Large Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLargeImage()
	 * @generated
	 * @ordered
	 */
	protected String largeImage = LARGE_IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLTIP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected String tooltip = TOOLTIP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWidgetTemplate() <em>Widget Template</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetTemplate()
	 * @generated
	 * @ordered
	 */
	protected WidgetTemplate widgetTemplate;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteGroupItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.PALETTE_GROUP_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSmallImage() {
		return smallImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newSmallImage
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSmallImage(String newSmallImage) {
		String oldSmallImage = smallImage;
		smallImage = newSmallImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP_ITEM__SMALL_IMAGE, oldSmallImage, smallImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLargeImage() {
		return largeImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLargeImage
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLargeImage(String newLargeImage) {
		String oldLargeImage = largeImage;
		largeImage = newLargeImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP_ITEM__LARGE_IMAGE, oldLargeImage, largeImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLabel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP_ITEM__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newTooltip
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP_ITEM__TOOLTIP, oldTooltip, tooltip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetTemplate getWidgetTemplate() {
		if (widgetTemplate != null && widgetTemplate.eIsProxy()) {
			InternalEObject oldWidgetTemplate = (InternalEObject)widgetTemplate;
			widgetTemplate = (WidgetTemplate)eResolveProxy(oldWidgetTemplate);
			if (widgetTemplate != oldWidgetTemplate) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE, oldWidgetTemplate, widgetTemplate));
			}
		}
		return widgetTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetTemplate basicGetWidgetTemplate() {
		return widgetTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidgetTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetTemplate(WidgetTemplate newWidgetTemplate) {
		WidgetTemplate oldWidgetTemplate = widgetTemplate;
		widgetTemplate = newWidgetTemplate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE, oldWidgetTemplate, widgetTemplate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		return getLabel();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.PALETTE_GROUP_ITEM__SMALL_IMAGE:
				return getSmallImage();
			case UIModelPackage.PALETTE_GROUP_ITEM__LARGE_IMAGE:
				return getLargeImage();
			case UIModelPackage.PALETTE_GROUP_ITEM__LABEL:
				return getLabel();
			case UIModelPackage.PALETTE_GROUP_ITEM__TOOLTIP:
				return getTooltip();
			case UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE:
				if (resolve) return getWidgetTemplate();
				return basicGetWidgetTemplate();
			case UIModelPackage.PALETTE_GROUP_ITEM__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UIModelPackage.PALETTE_GROUP_ITEM__SMALL_IMAGE:
				setSmallImage((String)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__LARGE_IMAGE:
				setLargeImage((String)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__LABEL:
				setLabel((String)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE:
				setWidgetTemplate((WidgetTemplate)newValue);
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
			case UIModelPackage.PALETTE_GROUP_ITEM__SMALL_IMAGE:
				setSmallImage(SMALL_IMAGE_EDEFAULT);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__LARGE_IMAGE:
				setLargeImage(LARGE_IMAGE_EDEFAULT);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
			case UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE:
				setWidgetTemplate((WidgetTemplate)null);
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
			case UIModelPackage.PALETTE_GROUP_ITEM__SMALL_IMAGE:
				return SMALL_IMAGE_EDEFAULT == null ? smallImage != null : !SMALL_IMAGE_EDEFAULT.equals(smallImage);
			case UIModelPackage.PALETTE_GROUP_ITEM__LARGE_IMAGE:
				return LARGE_IMAGE_EDEFAULT == null ? largeImage != null : !LARGE_IMAGE_EDEFAULT.equals(largeImage);
			case UIModelPackage.PALETTE_GROUP_ITEM__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case UIModelPackage.PALETTE_GROUP_ITEM__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case UIModelPackage.PALETTE_GROUP_ITEM__WIDGET_TEMPLATE:
				return widgetTemplate != null;
			case UIModelPackage.PALETTE_GROUP_ITEM__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
		}
		return super.eIsSet(featureID);
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
		result.append(" (smallImage: ");
		result.append(smallImage);
		result.append(", largeImage: ");
		result.append(largeImage);
		result.append(", label: ");
		result.append(label);
		result.append(", tooltip: ");
		result.append(tooltip);
		result.append(')');
		return result.toString();
	}

} //PaletteGroupItemImpl