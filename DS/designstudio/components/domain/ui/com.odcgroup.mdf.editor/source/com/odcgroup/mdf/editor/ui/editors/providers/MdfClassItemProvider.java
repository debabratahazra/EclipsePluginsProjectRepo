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
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfClass;

/**
 * This is the item provider adapter for a {@link com.odcgroup.mdf.metamodel.MdfClass} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MdfClassItemProvider
    extends MdfEntityItemProvider
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
    public MdfClassItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * @generated
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addBaseClassPropertyDescriptor(object);
            addAbstractPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Base Class feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addBaseClassPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfClass_baseClass_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfClass_baseClass_feature", "_UI_MdfClass_type"),
                 MdfPackage.Literals.MDF_CLASS__BASE_CLASS,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Abstract feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAbstractPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MdfClass_abstract_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MdfClass_abstract_feature", "_UI_MdfClass_type"),
                 MdfPackage.Literals.MDF_CLASS__ABSTRACT,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
            childrenFeatures.add(MdfPackage.Literals.MDF_CLASS__BASE_CLASS);
            childrenFeatures.add(MdfPackage.Literals.MDF_CLASS__PROPERTIES);
        }
        return childrenFeatures;
    }

    /**
     * This returns MdfClass.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Object getImage(Object object) {
    	if (((MdfClass)object).getPrimaryKeys().size() == 0) {
    		return overlayImage(object, getResourceLocator().getImage("full/obj16/MdfClass"));
    	} else {
    		return overlayImage(object, getResourceLocator().getImage("full/obj16/MdfMainClass"));
    	}
    }

    /**
     * This returns the label text for the adapted class.
     */
    public String getText(Object object) {
        String label = ((MdfClass)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MdfClass_type") : label;
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

        switch (notification.getFeatureID(MdfClass.class)) {
            case MdfPackage.MDF_CLASS__ABSTRACT:
            case MdfPackage.MDF_CLASS__PROPERTIES:
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
     * @generated
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, IProject project, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, project, object);

        // DS-1349
        MdfAttributeImpl createMdfAttribute = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute(project);
        createMdfAttribute.setName("newAttribute");
		newChildDescriptors.add
            (createChildParameter
                (MdfPackage.Literals.MDF_CLASS__PROPERTIES,
                        createMdfAttribute));
 
        MdfAssociationImpl createMdfAssociation = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation(project);
        createMdfAssociation.setName("newAssociation");
		newChildDescriptors.add
            (createChildParameter
                (MdfPackage.Literals.MDF_CLASS__PROPERTIES,
                        createMdfAssociation));
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    public ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

}
