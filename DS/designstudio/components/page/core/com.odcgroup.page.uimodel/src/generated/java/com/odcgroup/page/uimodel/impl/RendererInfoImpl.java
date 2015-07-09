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

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.uimodel.EditPolicyRole;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Renderer Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getFigure <em>Figure</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getEditPart <em>Edit Part</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getDesignWidgetSpacing <em>Design Widget Spacing</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getPreviewWidgetSpacing <em>Preview Widget Spacing</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getRequestHandler <em>Request Handler</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getDirectEditMode <em>Direct Edit Mode</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getDirectEditManager <em>Direct Edit Manager</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getDeleteCommand <em>Delete Command</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.RendererInfoImpl#getDragTracker <em>Drag Tracker</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RendererInfoImpl extends MinimalEObjectImpl.Container implements RendererInfo {
	/**
	 * The cached value of the '{@link #getWidgetType() <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetType()
	 * @generated
	 * @ordered
	 */
	protected WidgetType widgetType;

	/**
	 * The default value of the '{@link #getFigure() <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFigure()
	 * @generated
	 * @ordered
	 */
	protected static final String FIGURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFigure() <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFigure()
	 * @generated
	 * @ordered
	 */
	protected String figure = FIGURE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditPart() <em>Edit Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPart()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_PART_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditPart() <em>Edit Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPart()
	 * @generated
	 * @ordered
	 */
	protected String editPart = EDIT_PART_EDEFAULT;

	/**
	 * The default value of the '{@link #getDesignWidgetSpacing() <em>Design Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignWidgetSpacing()
	 * @generated
	 * @ordered
	 */
	protected static final int DESIGN_WIDGET_SPACING_EDEFAULT = 8;

	/**
	 * The cached value of the '{@link #getDesignWidgetSpacing() <em>Design Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignWidgetSpacing()
	 * @generated
	 * @ordered
	 */
	protected int designWidgetSpacing = DESIGN_WIDGET_SPACING_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreviewWidgetSpacing() <em>Preview Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreviewWidgetSpacing()
	 * @generated
	 * @ordered
	 */
	protected static final int PREVIEW_WIDGET_SPACING_EDEFAULT = 4;

	/**
	 * The cached value of the '{@link #getPreviewWidgetSpacing() <em>Preview Widget Spacing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreviewWidgetSpacing()
	 * @generated
	 * @ordered
	 */
	protected int previewWidgetSpacing = PREVIEW_WIDGET_SPACING_EDEFAULT;

	/**
	 * The default value of the '{@link #getRequestHandler() <em>Request Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestHandler()
	 * @generated
	 * @ordered
	 */
	protected static final String REQUEST_HANDLER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRequestHandler() <em>Request Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestHandler()
	 * @generated
	 * @ordered
	 */
	protected String requestHandler = REQUEST_HANDLER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDirectEditMode() <em>Direct Edit Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectEditMode()
	 * @generated
	 * @ordered
	 */
	protected static final EditorMode DIRECT_EDIT_MODE_EDEFAULT = EditorMode.NONE;

	/**
	 * The cached value of the '{@link #getDirectEditMode() <em>Direct Edit Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectEditMode()
	 * @generated
	 * @ordered
	 */
	protected EditorMode directEditMode = DIRECT_EDIT_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRoles() <em>Roles</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoles()
	 * @generated
	 * @ordered
	 */
	protected EList<EditPolicyRole> roles;

	/**
	 * The default value of the '{@link #getDirectEditManager() <em>Direct Edit Manager</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectEditManager()
	 * @generated
	 * @ordered
	 */
	protected static final String DIRECT_EDIT_MANAGER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDirectEditManager() <em>Direct Edit Manager</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirectEditManager()
	 * @generated
	 * @ordered
	 */
	protected String directEditManager = DIRECT_EDIT_MANAGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeleteCommand() <em>Delete Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeleteCommand()
	 * @generated
	 * @ordered
	 */
	protected static final String DELETE_COMMAND_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDeleteCommand() <em>Delete Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeleteCommand()
	 * @generated
	 * @ordered
	 */
	protected String deleteCommand = DELETE_COMMAND_EDEFAULT;

	/**
	 * The default value of the '{@link #getDragTracker() <em>Drag Tracker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDragTracker()
	 * @generated
	 * @ordered
	 */
	protected static final String DRAG_TRACKER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDragTracker() <em>Drag Tracker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDragTracker()
	 * @generated
	 * @ordered
	 */
	protected String dragTracker = DRAG_TRACKER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RendererInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.RENDERER_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getWidgetType() {
		if (widgetType != null && widgetType.eIsProxy()) {
			InternalEObject oldWidgetType = (InternalEObject)widgetType;
			widgetType = (WidgetType)eResolveProxy(oldWidgetType);
			if (widgetType != oldWidgetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UIModelPackage.RENDERER_INFO__WIDGET_TYPE, oldWidgetType, widgetType));
			}
		}
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetWidgetType() {
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetType(WidgetType newWidgetType) {
		WidgetType oldWidgetType = widgetType;
		widgetType = newWidgetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__WIDGET_TYPE, oldWidgetType, widgetType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFigure() {
		return figure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newFigure
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFigure(String newFigure) {
		String oldFigure = figure;
		figure = newFigure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__FIGURE, oldFigure, figure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditPart() {
		return editPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEditPart
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditPart(String newEditPart) {
		String oldEditPart = editPart;
		editPart = newEditPart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__EDIT_PART, oldEditPart, editPart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDesignWidgetSpacing() {
		return designWidgetSpacing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDesignWidgetSpacing
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesignWidgetSpacing(int newDesignWidgetSpacing) {
		int oldDesignWidgetSpacing = designWidgetSpacing;
		designWidgetSpacing = newDesignWidgetSpacing;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING, oldDesignWidgetSpacing, designWidgetSpacing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return int
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPreviewWidgetSpacing() {
		return previewWidgetSpacing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newPreviewWidgetSpacing
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreviewWidgetSpacing(int newPreviewWidgetSpacing) {
		int oldPreviewWidgetSpacing = previewWidgetSpacing;
		previewWidgetSpacing = newPreviewWidgetSpacing;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING, oldPreviewWidgetSpacing, previewWidgetSpacing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRequestHandler() {
		return requestHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newRequestHandler
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestHandler(String newRequestHandler) {
		String oldRequestHandler = requestHandler;
		requestHandler = newRequestHandler;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__REQUEST_HANDLER, oldRequestHandler, requestHandler));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EditorMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorMode getDirectEditMode() {
		return directEditMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDirectEditMode
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirectEditMode(EditorMode newDirectEditMode) {
		EditorMode oldDirectEditMode = directEditMode;
		directEditMode = newDirectEditMode == null ? DIRECT_EDIT_MODE_EDEFAULT : newDirectEditMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE, oldDirectEditMode, directEditMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EditPolicyRole> getRoles() {
		if (roles == null) {
			roles = new EObjectContainmentEList<EditPolicyRole>(EditPolicyRole.class, this, UIModelPackage.RENDERER_INFO__ROLES);
		}
		return roles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDirectEditManager() {
		return directEditManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDirectEditManager
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirectEditManager(String newDirectEditManager) {
		String oldDirectEditManager = directEditManager;
		directEditManager = newDirectEditManager;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER, oldDirectEditManager, directEditManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDeleteCommand() {
		return deleteCommand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDeleteCommand
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteCommand(String newDeleteCommand) {
		String oldDeleteCommand = deleteCommand;
		deleteCommand = newDeleteCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__DELETE_COMMAND, oldDeleteCommand, deleteCommand));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDragTracker() {
		return dragTracker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDragTracker
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDragTracker(String newDragTracker) {
		String oldDragTracker = dragTracker;
		dragTracker = newDragTracker;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.RENDERER_INFO__DRAG_TRACKER, oldDragTracker, dragTracker));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UIModelPackage.RENDERER_INFO__ROLES:
				return ((InternalEList<?>)getRoles()).basicRemove(otherEnd, msgs);
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
			case UIModelPackage.RENDERER_INFO__WIDGET_TYPE:
				if (resolve) return getWidgetType();
				return basicGetWidgetType();
			case UIModelPackage.RENDERER_INFO__FIGURE:
				return getFigure();
			case UIModelPackage.RENDERER_INFO__EDIT_PART:
				return getEditPart();
			case UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING:
				return getDesignWidgetSpacing();
			case UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING:
				return getPreviewWidgetSpacing();
			case UIModelPackage.RENDERER_INFO__REQUEST_HANDLER:
				return getRequestHandler();
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE:
				return getDirectEditMode();
			case UIModelPackage.RENDERER_INFO__ROLES:
				return getRoles();
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER:
				return getDirectEditManager();
			case UIModelPackage.RENDERER_INFO__DELETE_COMMAND:
				return getDeleteCommand();
			case UIModelPackage.RENDERER_INFO__DRAG_TRACKER:
				return getDragTracker();
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
			case UIModelPackage.RENDERER_INFO__WIDGET_TYPE:
				setWidgetType((WidgetType)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__FIGURE:
				setFigure((String)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__EDIT_PART:
				setEditPart((String)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING:
				setDesignWidgetSpacing((Integer)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING:
				setPreviewWidgetSpacing((Integer)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__REQUEST_HANDLER:
				setRequestHandler((String)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE:
				setDirectEditMode((EditorMode)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__ROLES:
				getRoles().clear();
				getRoles().addAll((Collection<? extends EditPolicyRole>)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER:
				setDirectEditManager((String)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__DELETE_COMMAND:
				setDeleteCommand((String)newValue);
				return;
			case UIModelPackage.RENDERER_INFO__DRAG_TRACKER:
				setDragTracker((String)newValue);
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
			case UIModelPackage.RENDERER_INFO__WIDGET_TYPE:
				setWidgetType((WidgetType)null);
				return;
			case UIModelPackage.RENDERER_INFO__FIGURE:
				setFigure(FIGURE_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__EDIT_PART:
				setEditPart(EDIT_PART_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING:
				setDesignWidgetSpacing(DESIGN_WIDGET_SPACING_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING:
				setPreviewWidgetSpacing(PREVIEW_WIDGET_SPACING_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__REQUEST_HANDLER:
				setRequestHandler(REQUEST_HANDLER_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE:
				setDirectEditMode(DIRECT_EDIT_MODE_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__ROLES:
				getRoles().clear();
				return;
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER:
				setDirectEditManager(DIRECT_EDIT_MANAGER_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__DELETE_COMMAND:
				setDeleteCommand(DELETE_COMMAND_EDEFAULT);
				return;
			case UIModelPackage.RENDERER_INFO__DRAG_TRACKER:
				setDragTracker(DRAG_TRACKER_EDEFAULT);
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
			case UIModelPackage.RENDERER_INFO__WIDGET_TYPE:
				return widgetType != null;
			case UIModelPackage.RENDERER_INFO__FIGURE:
				return FIGURE_EDEFAULT == null ? figure != null : !FIGURE_EDEFAULT.equals(figure);
			case UIModelPackage.RENDERER_INFO__EDIT_PART:
				return EDIT_PART_EDEFAULT == null ? editPart != null : !EDIT_PART_EDEFAULT.equals(editPart);
			case UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING:
				return designWidgetSpacing != DESIGN_WIDGET_SPACING_EDEFAULT;
			case UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING:
				return previewWidgetSpacing != PREVIEW_WIDGET_SPACING_EDEFAULT;
			case UIModelPackage.RENDERER_INFO__REQUEST_HANDLER:
				return REQUEST_HANDLER_EDEFAULT == null ? requestHandler != null : !REQUEST_HANDLER_EDEFAULT.equals(requestHandler);
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE:
				return directEditMode != DIRECT_EDIT_MODE_EDEFAULT;
			case UIModelPackage.RENDERER_INFO__ROLES:
				return roles != null && !roles.isEmpty();
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER:
				return DIRECT_EDIT_MANAGER_EDEFAULT == null ? directEditManager != null : !DIRECT_EDIT_MANAGER_EDEFAULT.equals(directEditManager);
			case UIModelPackage.RENDERER_INFO__DELETE_COMMAND:
				return DELETE_COMMAND_EDEFAULT == null ? deleteCommand != null : !DELETE_COMMAND_EDEFAULT.equals(deleteCommand);
			case UIModelPackage.RENDERER_INFO__DRAG_TRACKER:
				return DRAG_TRACKER_EDEFAULT == null ? dragTracker != null : !DRAG_TRACKER_EDEFAULT.equals(dragTracker);
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
		result.append(" (figure: ");
		result.append(figure);
		result.append(", editPart: ");
		result.append(editPart);
		result.append(", designWidgetSpacing: ");
		result.append(designWidgetSpacing);
		result.append(", previewWidgetSpacing: ");
		result.append(previewWidgetSpacing);
		result.append(", requestHandler: ");
		result.append(requestHandler);
		result.append(", directEditMode: ");
		result.append(directEditMode);
		result.append(", directEditManager: ");
		result.append(directEditManager);
		result.append(", deleteCommand: ");
		result.append(deleteCommand);
		result.append(", dragTracker: ");
		result.append(dragTracker);
		result.append(')');
		return result.toString();
	}

} //RendererInfoImpl