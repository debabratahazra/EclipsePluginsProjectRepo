/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.editor.ui.editors.providers;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

/**
 * This is the item provider adapter for a {@link com.odcgroup.mdf.metamodel.MdfAttribute} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MdfAttributeItemProvider
    extends MdfPropertyItemProvider
    implements
        IEditingDomainItemProvider,
        IStructuredItemContentProvider,
        ITreeItemContentProvider,
        IItemLabelProvider,
        IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "";

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MdfAttributeItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * @generated
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addTypePropertyDescriptor(object);
            addPrimaryKeyPropertyDescriptor(object);
            addDefaultPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfAttribute_type_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfAttribute_type_feature", "_UI_MdfAttribute_type"),
                 MdfPackage.Literals.MDF_ATTRIBUTE__TYPE,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Default feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDefaultPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfAttribute_default_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfAttribute_default_feature", "_UI_MdfAttribute_type"),
                 MdfPackage.Literals.MDF_ATTRIBUTE__DEFAULT,
                 true,
                 true,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Primary Key feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addPrimaryKeyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfProperty_primaryKey_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfProperty_primaryKey_feature", "_UI_MdfProperty_type"),
                 MdfPackage.Literals.MDF_PROPERTY__PRIMARY_KEY,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This returns MdfAttribute.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/MdfAttribute"));
    }

    /**
     * This returns the label text for the adapted class.
     */
    public String getText(Object object) {
        String label = ((MdfAttribute)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MdfAttribute_type") : label;
    }

    @Override
	public Collection<?> getChildren(Object object) {

		Collection<Object> result = (Collection<Object>) super.getChildren(object);
		if (object instanceof MdfAttribute) {
			MdfAttribute mdfAttribute = (MdfAttribute) object;
			if (mdfAttribute.getType() instanceof MdfEnumeration) {
				MdfEnumeration mdfEnumeration = (MdfEnumeration) mdfAttribute.getType();
				MdfEnumerationItemProvider mdfEnumerationItemProvider = new MdfEnumerationItemProvider(
						getAdapterFactory());
				Collection<?> MdfEnumerationItemProviderCollection = mdfEnumerationItemProvider.getChildren(mdfEnumeration);
				result.addAll(MdfEnumerationItemProviderCollection);
			}
		}

		return result;
	}

	/**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(MdfAttribute.class)) {
        case MdfPackage.MDF_PROPERTY__PRIMARY_KEY:
            case MdfPackage.MDF_ATTRIBUTE__DEFAULT:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing all of the children that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    public ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

}
