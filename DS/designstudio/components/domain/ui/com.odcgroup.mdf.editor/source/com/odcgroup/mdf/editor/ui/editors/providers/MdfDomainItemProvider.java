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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;


/**
 * This is the item provider adapter for a
 * {@link com.odcgroup.mdf.metamodel.MdfDomain} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class MdfDomainItemProvider extends MdfModelElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "";
    
    private final String ANNOTATION_AAA_NAMESPACE = "http://www.odcgroup.com/mdf/aaa";
    
    private final String AAA = "TripleA";
    
    private final String DATASET_UD_ENTITIES = "UDEntities";
    
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public MdfDomainItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addNamespacePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Namespace feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addNamespacePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_MdfDomain_namespace_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_MdfDomain_namespace_feature", "_UI_MdfDomain_type"),
                MdfPackage.Literals.MDF_DOMAIN__NAMESPACE, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Collection getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(MdfPackage.Literals.MDF_DOMAIN__BUSINESS_TYPES);
            childrenFeatures.add(MdfPackage.Literals.MDF_DOMAIN__ENUMERATIONS);
            childrenFeatures.add(MdfPackage.Literals.MDF_DOMAIN__CLASSES);
            childrenFeatures.add(MdfPackage.Literals.MDF_DOMAIN__DATASETS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns MdfDomain.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/MdfDomain"));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     */
    public String getText(Object object) {
        String label = ((MdfDomain) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_MdfDomain_type")
                : label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(MdfDomain.class)) {
            case MdfPackage.MDF_DOMAIN__NAMESPACE:
            case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
            case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
            case MdfPackage.MDF_DOMAIN__CLASSES:
            case MdfPackage.MDF_DOMAIN__DATASETS:
                fireNotifyChanged(new ViewerNotification(notification,
                        notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of
     * {@link org.eclipse.emf.edit.command.CommandParameter}s describing all of
     * the children that can be created under this object. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, IProject project,
            Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, project, object);

        // DS-1349        
        MdfBusinessTypeImpl createMdfBusinessType = (MdfBusinessTypeImpl) MdfFactory.eINSTANCE.createMdfBusinessType(project);
        createMdfBusinessType.setName("NewBusinessType");
		newChildDescriptors.add(createChildParameter(
                MdfPackage.Literals.MDF_DOMAIN__BUSINESS_TYPES,
                createMdfBusinessType));
        
        MdfEnumerationImpl createMdfEnumeration = (MdfEnumerationImpl) MdfFactory.eINSTANCE.createMdfEnumeration(project);
        createMdfEnumeration.setName("NewEnumeration");
		newChildDescriptors.add(createChildParameter(
                MdfPackage.Literals.MDF_DOMAIN__ENUMERATIONS,
                createMdfEnumeration));

        MdfClassImpl createMdfClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass(project);
        createMdfClass.setName("NewClass");
		newChildDescriptors.add(createChildParameter(
                MdfPackage.Literals.MDF_DOMAIN__CLASSES,
                createMdfClass));

		// Condition added for DS-6376
		if (object instanceof MdfDomain) {
			MdfDomain domain = (MdfDomain) object;
			MdfAnnotation annotation = domain.getAnnotation(ANNOTATION_AAA_NAMESPACE, AAA);
			if(annotation != null) {
				MdfAnnotationProperty property = annotation.getProperty(DATASET_UD_ENTITIES);
				if(property != null && Boolean.FALSE.toString().equals(property.getValue())) {
					createMdfDataset(newChildDescriptors, project);
				} else if(property == null) {
					createMdfDataset(newChildDescriptors, project);
				}
			} else {
				createMdfDataset(newChildDescriptors, project);
			}
		}
    }

	/**
	 * @param newChildDescriptors
	 * @param project
	 */
	private void createMdfDataset(Collection newChildDescriptors, IProject project) {
		MdfDatasetImpl createMdfDataset = (MdfDatasetImpl) MdfFactory.eINSTANCE.createMdfDataset(project);
		createMdfDataset.setName("NewDataset");
		newChildDescriptors.add(createChildParameter(
		        MdfPackage.Literals.MDF_DOMAIN__DATASETS,
		        createMdfDataset));
	}

    /**
     * Return the resource locator for this item provider's resources.
     */
    public ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

    @Override
    protected Command createCopyCommand(EditingDomain domain, EObject owner,
            Helper helper) {
        return UnexecutableCommand.INSTANCE;
    }

}
