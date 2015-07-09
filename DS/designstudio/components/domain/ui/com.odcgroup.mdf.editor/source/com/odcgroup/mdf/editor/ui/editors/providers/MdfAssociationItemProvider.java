/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.editor.ui.editors.providers;


import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
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

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;

/**
 * This is the item provider adapter for a {@link com.odcgroup.mdf.metamodel.MdfAssociation} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MdfAssociationItemProvider
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
    public MdfAssociationItemProvider(AdapterFactory adapterFactory) {
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
            addContainmentPropertyDescriptor(object);
            addReversePropertyDescriptor(object);
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
                 getString("_UI_MdfAssociation_type_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfAssociation_type_feature", "_UI_MdfAssociation_type"),
                 MdfPackage.Literals.MDF_ASSOCIATION__TYPE,
                 false,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Containment feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addContainmentPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfAssociation_containment_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfAssociation_containment_feature", "_UI_MdfAssociation_type"),
                 MdfPackage.Literals.MDF_ASSOCIATION__CONTAINMENT,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Reverse feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addReversePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfAssociation_reverse_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfAssociation_reverse_feature", "_UI_MdfAssociation_type"),
                 MdfPackage.Literals.MDF_ASSOCIATION__REVERSE,
                 false,
                 false,
                 true,
                 null,
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
    public Collection getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(MdfPackage.Literals.MDF_ASSOCIATION__REVERSE);
        }
        return childrenFeatures;
    }
    
    

    @Override
	public Collection<?> getChildren(Object object) {
		
    	Collection<Object> result = (Collection<Object>) super.getChildren(object);
    	
		if (object instanceof MdfAssociation) {
			MdfAssociationImpl mdfAssociationImpl = (MdfAssociationImpl) object;
			if (mdfAssociationImpl.getContainment() == MdfContainment.BY_VALUE
					&& mdfAssociationImpl.getMultiplicity() == MdfMultiplicity.MANY) {
				
				MdfClass mdfClass = (MdfClass)mdfAssociationImpl.getType();
				MdfClassItemProvider classItemProvider = new MdfClassItemProvider(getAdapterFactory());
				Collection<?> classItemProviderCollection = classItemProvider.getChildren(mdfClass);
				result.addAll(classItemProviderCollection);
			}
		}
		
		return result;
	}

	/**
     * This returns MdfAssociation.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Object getImage(Object object) {
    	Object obj = overlayImage(object, getResourceLocator().getImage("full/obj16/MdfAssociation"));
    	if (((MdfAssociation) object).getContainment() == MdfContainment.BY_VALUE) {
    		obj = overlayImage(object, getResourceLocator().getImage("value"));
    	}
    	return obj;
    }

    /**
     * This returns the label text for the adapted class.
     */
    public String getText(Object object) {
        String label = ((MdfAssociation)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MdfAssociation_type") : label;
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

        switch (notification.getFeatureID(MdfAssociation.class)) {
            case MdfPackage.MDF_ASSOCIATION__CONTAINMENT:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case MdfPackage.MDF_ASSOCIATION__REVERSE:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing all of the children that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, IProject project, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, project, object);

        // DS-1349
        newChildDescriptors.add
            (createChildParameter
                (MdfPackage.Literals.MDF_ASSOCIATION__REVERSE,
                        MdfFactory.eINSTANCE.createMdfReverseAssociation(project)));
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    public ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

}
