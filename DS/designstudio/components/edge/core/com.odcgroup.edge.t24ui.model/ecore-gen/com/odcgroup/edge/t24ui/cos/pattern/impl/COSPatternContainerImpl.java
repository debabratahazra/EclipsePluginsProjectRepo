/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;
import com.odcgroup.edge.t24ui.cos.pattern.PanelSeparatorOption;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>COS Pattern Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl#getPanelSeparatorOption <em>Panel Separator Option</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternContainerImpl#isAccordionPatternMultiExpandable <em>Accordion Pattern Multi Expandable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class COSPatternContainerImpl extends EObjectImpl implements COSPatternContainer {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected COSPatternContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.COS_PATTERN_CONTAINER;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatternPackage.COS_PATTERN_CONTAINER__PATTERN, oldPattern, pattern));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.COS_PATTERN_CONTAINER__PATTERN, oldPattern, pattern));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION, oldPanelSeparatorOption, panelSeparatorOption));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE, oldAccordionPatternMultiExpandable, accordionPatternMultiExpandable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.COS_PATTERN_CONTAINER__PATTERN:
				if (resolve) return getPattern();
				return basicGetPattern();
			case PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION:
				return getPanelSeparatorOption();
			case PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				return isAccordionPatternMultiExpandable();
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
			case PatternPackage.COS_PATTERN_CONTAINER__PATTERN:
				setPattern((COSPattern)newValue);
				return;
			case PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION:
				setPanelSeparatorOption((PanelSeparatorOption)newValue);
				return;
			case PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				setAccordionPatternMultiExpandable((Boolean)newValue);
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
			case PatternPackage.COS_PATTERN_CONTAINER__PATTERN:
				setPattern((COSPattern)null);
				return;
			case PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION:
				setPanelSeparatorOption(PANEL_SEPARATOR_OPTION_EDEFAULT);
				return;
			case PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				setAccordionPatternMultiExpandable(ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT);
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
			case PatternPackage.COS_PATTERN_CONTAINER__PATTERN:
				return pattern != null;
			case PatternPackage.COS_PATTERN_CONTAINER__PANEL_SEPARATOR_OPTION:
				return panelSeparatorOption != PANEL_SEPARATOR_OPTION_EDEFAULT;
			case PatternPackage.COS_PATTERN_CONTAINER__ACCORDION_PATTERN_MULTI_EXPANDABLE:
				return accordionPatternMultiExpandable != ACCORDION_PATTERN_MULTI_EXPANDABLE_EDEFAULT;
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
		result.append(" (panelSeparatorOption: ");
		result.append(panelSeparatorOption);
		result.append(", accordionPatternMultiExpandable: ");
		result.append(accordionPatternMultiExpandable);
		result.append(')');
		return result.toString();
	}

} //COSPatternContainerImpl
