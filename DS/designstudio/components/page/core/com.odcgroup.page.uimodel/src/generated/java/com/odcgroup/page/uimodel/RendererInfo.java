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
 * A representation of the model object '<em><b>Renderer Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getFigure <em>Figure</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getEditPart <em>Edit Part</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getDesignWidgetSpacing <em>Design Widget Spacing</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getPreviewWidgetSpacing <em>Preview Widget Spacing</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getRequestHandler <em>Request Handler</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditMode <em>Direct Edit Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditManager <em>Direct Edit Manager</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getDeleteCommand <em>Delete Command</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.RendererInfo#getDragTracker <em>Drag Tracker</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo()
 * @model
 * @generated
 */
public interface RendererInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Type</em>' reference.
	 * @see #setWidgetType(WidgetType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_WidgetType()
	 * @model
	 * @generated
	 */
	WidgetType getWidgetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getWidgetType <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Type</em>' reference.
	 * @see #getWidgetType()
	 * @generated
	 */
	void setWidgetType(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Figure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Figure</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Figure</em>' attribute.
	 * @see #setFigure(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_Figure()
	 * @model
	 * @generated
	 */
	String getFigure();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getFigure <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Figure</em>' attribute.
	 * @see #getFigure()
	 * @generated
	 */
	void setFigure(String value);

	/**
	 * Returns the value of the '<em><b>Edit Part</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Part</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Part</em>' attribute.
	 * @see #setEditPart(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_EditPart()
	 * @model
	 * @generated
	 */
	String getEditPart();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getEditPart <em>Edit Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Part</em>' attribute.
	 * @see #getEditPart()
	 * @generated
	 */
	void setEditPart(String value);

	/**
	 * Returns the value of the '<em><b>Design Widget Spacing</b></em>' attribute.
	 * The default value is <code>"8"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Design Widget Spacing</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Design Widget Spacing</em>' attribute.
	 * @see #setDesignWidgetSpacing(int)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_DesignWidgetSpacing()
	 * @model default="8" required="true"
	 * @generated
	 */
	int getDesignWidgetSpacing();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getDesignWidgetSpacing <em>Design Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Design Widget Spacing</em>' attribute.
	 * @see #getDesignWidgetSpacing()
	 * @generated
	 */
	void setDesignWidgetSpacing(int value);

	/**
	 * Returns the value of the '<em><b>Preview Widget Spacing</b></em>' attribute.
	 * The default value is <code>"4"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preview Widget Spacing</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preview Widget Spacing</em>' attribute.
	 * @see #setPreviewWidgetSpacing(int)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_PreviewWidgetSpacing()
	 * @model default="4" required="true"
	 * @generated
	 */
	int getPreviewWidgetSpacing();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getPreviewWidgetSpacing <em>Preview Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preview Widget Spacing</em>' attribute.
	 * @see #getPreviewWidgetSpacing()
	 * @generated
	 */
	void setPreviewWidgetSpacing(int value);

	/**
	 * Returns the value of the '<em><b>Request Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Request Handler</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Request Handler</em>' attribute.
	 * @see #setRequestHandler(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_RequestHandler()
	 * @model
	 * @generated
	 */
	String getRequestHandler();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getRequestHandler <em>Request Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Request Handler</em>' attribute.
	 * @see #getRequestHandler()
	 * @generated
	 */
	void setRequestHandler(String value);

	/**
	 * Returns the value of the '<em><b>Direct Edit Mode</b></em>' attribute.
	 * The default value is <code>"None"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.uimodel.EditorMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direct Edit Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direct Edit Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #setDirectEditMode(EditorMode)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_DirectEditMode()
	 * @model default="None"
	 * @generated
	 */
	EditorMode getDirectEditMode();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditMode <em>Direct Edit Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direct Edit Mode</em>' attribute.
	 * @see com.odcgroup.page.uimodel.EditorMode
	 * @see #getDirectEditMode()
	 * @generated
	 */
	void setDirectEditMode(EditorMode value);

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.EditPolicyRole}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_Roles()
	 * @model containment="true"
	 * @generated
	 */
	EList<EditPolicyRole> getRoles();

	/**
	 * Returns the value of the '<em><b>Direct Edit Manager</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direct Edit Manager</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direct Edit Manager</em>' attribute.
	 * @see #setDirectEditManager(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_DirectEditManager()
	 * @model
	 * @generated
	 */
	String getDirectEditManager();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getDirectEditManager <em>Direct Edit Manager</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direct Edit Manager</em>' attribute.
	 * @see #getDirectEditManager()
	 * @generated
	 */
	void setDirectEditManager(String value);

	/**
	 * Returns the value of the '<em><b>Delete Command</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Command</em>' attribute.
	 * @see #setDeleteCommand(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_DeleteCommand()
	 * @model default=""
	 * @generated
	 */
	String getDeleteCommand();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getDeleteCommand <em>Delete Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Command</em>' attribute.
	 * @see #getDeleteCommand()
	 * @generated
	 */
	void setDeleteCommand(String value);

	/**
	 * Returns the value of the '<em><b>Drag Tracker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Drag Tracker</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Drag Tracker</em>' attribute.
	 * @see #setDragTracker(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getRendererInfo_DragTracker()
	 * @model
	 * @generated
	 */
	String getDragTracker();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.RendererInfo#getDragTracker <em>Drag Tracker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Drag Tracker</em>' attribute.
	 * @see #getDragTracker()
	 * @generated
	 */
	void setDragTracker(String value);

} // RendererInfo