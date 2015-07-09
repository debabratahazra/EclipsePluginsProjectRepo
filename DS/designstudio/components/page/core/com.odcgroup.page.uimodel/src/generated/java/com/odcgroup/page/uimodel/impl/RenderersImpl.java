/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.Renderers;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Renderers</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RenderersImpl#getRendererInfos <em>Renderer Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RenderersImpl extends MinimalEObjectImpl.Container implements Renderers {
	/**
	 * The cached value of the '{@link #getRendererInfos() <em>Renderer Infos</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRendererInfos()
	 * @generated
	 * @ordered
	 */
	protected EList<RendererInfo> rendererInfos;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RenderersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.RENDERERS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RendererInfo> getRendererInfos() {
		if (rendererInfos == null) {
			rendererInfos = new EObjectContainmentEList<RendererInfo>(RendererInfo.class, this, UIModelPackage.RENDERERS__RENDERER_INFOS);
		}
		return rendererInfos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UIModelPackage.RENDERERS__RENDERER_INFOS:
				return ((InternalEList<?>)getRendererInfos()).basicRemove(otherEnd, msgs);
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
			case UIModelPackage.RENDERERS__RENDERER_INFOS:
				return getRendererInfos();
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
			case UIModelPackage.RENDERERS__RENDERER_INFOS:
				getRendererInfos().clear();
				getRendererInfos().addAll((Collection<? extends RendererInfo>)newValue);
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
			case UIModelPackage.RENDERERS__RENDERER_INFOS:
				getRendererInfos().clear();
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
			case UIModelPackage.RENDERERS__RENDERER_INFOS:
				return rendererInfos != null && !rendererInfos.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Finds the RendererInfo for the given WidgetType.
	 * 
	 * @param widgetType The WidgetType to find the RendererInfo for
	 * @return RendererInfo or null if no RendererInfo can be found
	 */
	public RendererInfo findRendererInfo(WidgetType widgetType) {
		for (RendererInfo ri : getRendererInfos()) {
			if (ri.getWidgetType().getName().equals(widgetType.getName())) {
				return ri;
			}
		}		
		// Not found
		return null;
	}

} //RenderersImpl