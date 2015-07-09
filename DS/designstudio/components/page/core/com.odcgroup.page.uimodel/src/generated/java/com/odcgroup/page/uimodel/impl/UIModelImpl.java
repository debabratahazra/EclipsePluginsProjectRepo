/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.uimodel.ActionGroups;
import com.odcgroup.page.uimodel.Actions;
import com.odcgroup.page.uimodel.Menus;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.Renderers;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UI Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getPalette <em>Palette</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getRenderers <em>Renderers</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getPaletteGroupItems <em>Palette Group Items</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getDefaultPalette <em>Default Palette</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getMenus <em>Menus</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getActionGroups <em>Action Groups</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.UIModelImpl#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UIModelImpl extends MinimalEObjectImpl.Container implements UIModel {
	/**
	 * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPalette()
	 * @generated
	 * @ordered
	 */
	protected EList<Palette> palette;

	/**
	 * The cached value of the '{@link #getRenderers() <em>Renderers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRenderers()
	 * @generated
	 * @ordered
	 */
	protected Renderers renderers;

	/**
	 * The cached value of the '{@link #getPaletteGroupItems() <em>Palette Group Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPaletteGroupItems()
	 * @generated
	 * @ordered
	 */
	protected EList<PaletteGroupItem> paletteGroupItems;

	/**
	 * The cached value of the '{@link #getDefaultPalette() <em>Default Palette</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPalette()
	 * @generated
	 * @ordered
	 */
	protected Palette defaultPalette;

	/**
	 * The cached value of the '{@link #getMenus() <em>Menus</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMenus()
	 * @generated
	 * @ordered
	 */
	protected Menus menus;

	/**
	 * The cached value of the '{@link #getActionGroups() <em>Action Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionGroups()
	 * @generated
	 * @ordered
	 */
	protected ActionGroups actionGroups;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Actions> actions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UIModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.UI_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Palette> getPalette() {
		if (palette == null) {
			palette = new EObjectContainmentEList<Palette>(Palette.class, this, UIModelPackage.UI_MODEL__PALETTE);
		}
		return palette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Renderers
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Renderers getRenderers() {
		return renderers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newRenderers
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRenderers(Renderers newRenderers, NotificationChain msgs) {
		Renderers oldRenderers = renderers;
		renderers = newRenderers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__RENDERERS, oldRenderers, newRenderers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newRenderers
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRenderers(Renderers newRenderers) {
		if (newRenderers != renderers) {
			NotificationChain msgs = null;
			if (renderers != null)
				msgs = ((InternalEObject)renderers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__RENDERERS, null, msgs);
			if (newRenderers != null)
				msgs = ((InternalEObject)newRenderers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__RENDERERS, null, msgs);
			msgs = basicSetRenderers(newRenderers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__RENDERERS, newRenderers, newRenderers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PaletteGroupItem> getPaletteGroupItems() {
		if (paletteGroupItems == null) {
			paletteGroupItems = new EObjectContainmentEList<PaletteGroupItem>(PaletteGroupItem.class, this, UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS);
		}
		return paletteGroupItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Palette
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Palette getDefaultPalette() {
		return defaultPalette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultPalette
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultPalette(Palette newDefaultPalette, NotificationChain msgs) {
		Palette oldDefaultPalette = defaultPalette;
		defaultPalette = newDefaultPalette;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__DEFAULT_PALETTE, oldDefaultPalette, newDefaultPalette);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultPalette
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultPalette(Palette newDefaultPalette) {
		if (newDefaultPalette != defaultPalette) {
			NotificationChain msgs = null;
			if (defaultPalette != null)
				msgs = ((InternalEObject)defaultPalette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__DEFAULT_PALETTE, null, msgs);
			if (newDefaultPalette != null)
				msgs = ((InternalEObject)newDefaultPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__DEFAULT_PALETTE, null, msgs);
			msgs = basicSetDefaultPalette(newDefaultPalette, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__DEFAULT_PALETTE, newDefaultPalette, newDefaultPalette));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Menus
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Menus getMenus() {
		return menus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMenus
	 * @param msgs
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMenus(Menus newMenus, NotificationChain msgs) {
		Menus oldMenus = menus;
		menus = newMenus;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__MENUS, oldMenus, newMenus);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newMenus
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMenus(Menus newMenus) {
		if (newMenus != menus) {
			NotificationChain msgs = null;
			if (menus != null)
				msgs = ((InternalEObject)menus).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__MENUS, null, msgs);
			if (newMenus != null)
				msgs = ((InternalEObject)newMenus).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__MENUS, null, msgs);
			msgs = basicSetMenus(newMenus, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__MENUS, newMenus, newMenus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ActionGroups
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionGroups getActionGroups() {
		return actionGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newActionGroups
	 * @param msgs
	 * @return
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActionGroups(ActionGroups newActionGroups, NotificationChain msgs) {
		ActionGroups oldActionGroups = actionGroups;
		actionGroups = newActionGroups;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__ACTION_GROUPS, oldActionGroups, newActionGroups);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newActionGroups
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionGroups(ActionGroups newActionGroups) {
		if (newActionGroups != actionGroups) {
			NotificationChain msgs = null;
			if (actionGroups != null)
				msgs = ((InternalEObject)actionGroups).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__ACTION_GROUPS, null, msgs);
			if (newActionGroups != null)
				msgs = ((InternalEObject)newActionGroups).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UIModelPackage.UI_MODEL__ACTION_GROUPS, null, msgs);
			msgs = basicSetActionGroups(newActionGroups, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.UI_MODEL__ACTION_GROUPS, newActionGroups, newActionGroups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Actions> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<Actions>(Actions.class, this, UIModelPackage.UI_MODEL__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UIModelPackage.UI_MODEL__PALETTE:
				return ((InternalEList<?>)getPalette()).basicRemove(otherEnd, msgs);
			case UIModelPackage.UI_MODEL__RENDERERS:
				return basicSetRenderers(null, msgs);
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
				return ((InternalEList<?>)getPaletteGroupItems()).basicRemove(otherEnd, msgs);
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
				return basicSetDefaultPalette(null, msgs);
			case UIModelPackage.UI_MODEL__MENUS:
				return basicSetMenus(null, msgs);
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
				return basicSetActionGroups(null, msgs);
			case UIModelPackage.UI_MODEL__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
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
			case UIModelPackage.UI_MODEL__PALETTE:
				return getPalette();
			case UIModelPackage.UI_MODEL__RENDERERS:
				return getRenderers();
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
				return getPaletteGroupItems();
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
				return getDefaultPalette();
			case UIModelPackage.UI_MODEL__MENUS:
				return getMenus();
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
				return getActionGroups();
			case UIModelPackage.UI_MODEL__ACTIONS:
				return getActions();
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
			case UIModelPackage.UI_MODEL__PALETTE:
				getPalette().clear();
				getPalette().addAll((Collection<? extends Palette>)newValue);
				return;
			case UIModelPackage.UI_MODEL__RENDERERS:
				setRenderers((Renderers)newValue);
				return;
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
				getPaletteGroupItems().clear();
				getPaletteGroupItems().addAll((Collection<? extends PaletteGroupItem>)newValue);
				return;
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
				setDefaultPalette((Palette)newValue);
				return;
			case UIModelPackage.UI_MODEL__MENUS:
				setMenus((Menus)newValue);
				return;
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
				setActionGroups((ActionGroups)newValue);
				return;
			case UIModelPackage.UI_MODEL__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Actions>)newValue);
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
			case UIModelPackage.UI_MODEL__PALETTE:
				getPalette().clear();
				return;
			case UIModelPackage.UI_MODEL__RENDERERS:
				setRenderers((Renderers)null);
				return;
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
				getPaletteGroupItems().clear();
				return;
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
				setDefaultPalette((Palette)null);
				return;
			case UIModelPackage.UI_MODEL__MENUS:
				setMenus((Menus)null);
				return;
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
				setActionGroups((ActionGroups)null);
				return;
			case UIModelPackage.UI_MODEL__ACTIONS:
				getActions().clear();
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
			case UIModelPackage.UI_MODEL__PALETTE:
				return palette != null && !palette.isEmpty();
			case UIModelPackage.UI_MODEL__RENDERERS:
				return renderers != null;
			case UIModelPackage.UI_MODEL__PALETTE_GROUP_ITEMS:
				return paletteGroupItems != null && !paletteGroupItems.isEmpty();
			case UIModelPackage.UI_MODEL__DEFAULT_PALETTE:
				return defaultPalette != null;
			case UIModelPackage.UI_MODEL__MENUS:
				return menus != null;
			case UIModelPackage.UI_MODEL__ACTION_GROUPS:
				return actionGroups != null;
			case UIModelPackage.UI_MODEL__ACTIONS:
				return actions != null && !actions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Finds the Palette for the WidgetType. If no Palette can be found
	 * for the WidgetType the default Palette is returned.
	 * 
	 * @param widgetType The WidgetType to find the Palette for
	 * @return Palette The Palette
	 */
	public Palette findPalette(WidgetType widgetType) {
		for (Palette p : getPalette() ) {
			if (p.getWidgetType().equals(widgetType)) {
				return p;
			}
		}
		
		// Not found
		return getDefaultPalette();
	}

	@Override
	public Palette findPalette(WidgetType widgetType,
			PropertyType propertyType, String propertyValue) {
		for (Palette p : getPalette() ) {
			if (p.getWidgetType().equals(widgetType) 
					&& p.getPropertyType().equals(propertyType)
					&& p.getPropertyValue().equals(propertyValue)) {				
				return p;
			}
		}
		
		// Not found
		return getDefaultPalette();
	}

} //UIModelImpl