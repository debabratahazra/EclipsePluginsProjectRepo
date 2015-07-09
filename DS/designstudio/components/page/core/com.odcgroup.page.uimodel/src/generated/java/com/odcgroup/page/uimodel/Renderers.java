/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Renderers</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.Renderers#getRendererInfos <em>Renderer Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getRenderers()
 * @model
 * @generated
 */
public interface Renderers extends EObject {
	/**
	 * Returns the value of the '<em><b>Renderer Infos</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.RendererInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Renderer Infos</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Renderer Infos</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRenderers_RendererInfos()
	 * @model containment="true"
	 * @generated
	 */
	EList<RendererInfo> getRendererInfos();

	/**
	 * Finds the RendererInfo for the given WidgetType.
	 * 
	 * @param widgetType The WidgetType to find the RendererInfo for
	 * @return RendererInfo or null if no RendererInfo can be found
	 */
	public RendererInfo findRendererInfo(WidgetType widgetType);

} // Renderers