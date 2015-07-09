/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModelFactory;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * This is the item provider adapter for a {@link com.odcgroup.page.uimodel.RendererInfo} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RendererInfoItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RendererInfoItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addWidgetTypePropertyDescriptor(object);
			addFigurePropertyDescriptor(object);
			addEditPartPropertyDescriptor(object);
			addDesignWidgetSpacingPropertyDescriptor(object);
			addPreviewWidgetSpacingPropertyDescriptor(object);
			addRequestHandlerPropertyDescriptor(object);
			addDirectEditModePropertyDescriptor(object);
			addDirectEditManagerPropertyDescriptor(object);
			addDeleteCommandPropertyDescriptor(object);
			addDragTrackerPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Widget Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addWidgetTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_widgetType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_widgetType_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__WIDGET_TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Figure feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFigurePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_figure_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_figure_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__FIGURE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Edit Part feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditPartPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_editPart_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_editPart_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__EDIT_PART,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Design Widget Spacing feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDesignWidgetSpacingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_designWidgetSpacing_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_designWidgetSpacing_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__DESIGN_WIDGET_SPACING,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Preview Widget Spacing feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPreviewWidgetSpacingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_previewWidgetSpacing_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_previewWidgetSpacing_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__PREVIEW_WIDGET_SPACING,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Request Handler feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRequestHandlerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_requestHandler_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_requestHandler_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__REQUEST_HANDLER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Direct Edit Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDirectEditModePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_directEditMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_directEditMode_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__DIRECT_EDIT_MODE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Direct Edit Manager feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDirectEditManagerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_directEditManager_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_directEditManager_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__DIRECT_EDIT_MANAGER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Delete Command feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDeleteCommandPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_deleteCommand_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_deleteCommand_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__DELETE_COMMAND,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Drag Tracker feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDragTrackerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RendererInfo_dragTracker_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RendererInfo_dragTracker_feature", "_UI_RendererInfo_type"),
				 UIModelPackage.Literals.RENDERER_INFO__DRAG_TRACKER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(UIModelPackage.Literals.RENDERER_INFO__ROLES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns RendererInfo.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/RendererInfo"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((RendererInfo)object).getFigure();
		return label == null || label.length() == 0 ?
			getString("_UI_RendererInfo_type") :
			getString("_UI_RendererInfo_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(RendererInfo.class)) {
			case UIModelPackage.RENDERER_INFO__FIGURE:
			case UIModelPackage.RENDERER_INFO__EDIT_PART:
			case UIModelPackage.RENDERER_INFO__DESIGN_WIDGET_SPACING:
			case UIModelPackage.RENDERER_INFO__PREVIEW_WIDGET_SPACING:
			case UIModelPackage.RENDERER_INFO__REQUEST_HANDLER:
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MODE:
			case UIModelPackage.RENDERER_INFO__DIRECT_EDIT_MANAGER:
			case UIModelPackage.RENDERER_INFO__DELETE_COMMAND:
			case UIModelPackage.RENDERER_INFO__DRAG_TRACKER:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case UIModelPackage.RENDERER_INFO__ROLES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(UIModelPackage.Literals.RENDERER_INFO__ROLES,
				 UIModelFactory.eINSTANCE.createEditPolicyRole()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return PageUIModelEditPlugin.INSTANCE;
	}

}
