package com.odcgroup.mdf.editor.ui.editors.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupport;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.editor.ui.editors.commands.RemoveOrDeprecateCommand;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;


/**
 * This is the item provider adapter for a
 * {@link com.odcgroup.mdf.metamodel.MdfModelElement} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class MdfModelElementItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "";

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public MdfModelElementItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addNamePropertyDescriptor(object);
            addDocumentationPropertyDescriptor(object);
            // addQualifiedNamePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Documentation feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDocumentationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_MdfModelElement_documentation_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_MdfModelElement_documentation_feature",
                        "_UI_MdfModelElement_type"),
                MdfPackage.Literals.MDF_MODEL_ELEMENT__DOCUMENTATION, true,
                true, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_MdfModelElement_name_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_MdfModelElement_name_feature",
                        "_UI_MdfModelElement_type"),
                MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME, true, false,
                false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns the label text for the adapted class.
     */
    public String getText(Object object) {
        String label = ((MdfModelElement) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_MdfModelElement_type")
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

        switch (notification.getFeatureID(MdfModelElement.class)) {
            case MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION:
            case MdfPackage.MDF_MODEL_ELEMENT__NAME:
            case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
                fireNotifyChanged(new ViewerNotification(notification,
                        notification.getNotifier(), false, true));
                return;
        }
        super.notifyChanged(notification);
    }
    
    /**
     * @param object
     * @return
     */
    private Collection<? extends EStructuralFeature> getAnyChildrenFeatures(
			Object object) {
		Collection<? extends EStructuralFeature> result = getChildrenFeatures(object);
		return result.isEmpty() ? getChildrenReferences(object) : result;
	}
    
	/*
	 * (non-Javadoc)
	 * DS-1349
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getNewChildDescriptors(java.lang.Object,
	 *      org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	public Collection<?> getNewChildDescriptors(Object object,
			EditingDomain editingDomain, Object sibling) {

		EObject eObject = (EObject) object;

		// Build the collection of new child descriptors.
		//
		Collection<Object> newChildDescriptors = new ArrayList<Object>();		
		IProject project = OfsResourceHelper.getProject(eObject.eResource());
		collectNewChildDescriptors(newChildDescriptors, project, object);

		// If a sibling has been specified, add the best index possible to each
		// CommandParameter.
		//
		if (sibling != null) {
			sibling = unwrap(sibling);

			// Find the index of a feature containing the sibling, or an
			// equivalent value, in the collection of children
			// features.
			//
			Collection<? extends EStructuralFeature> childrenFeatures = getAnyChildrenFeatures(object);
			int siblingFeatureIndex = -1;
			int i = 0;

			FEATURES_LOOP: for (EStructuralFeature feature : childrenFeatures) {
				Object featureValue = eObject.eGet(feature);
				if (feature.isMany()) {
					for (Object value : (Collection<?>) featureValue) {
						if (isEquivalentValue(sibling, value)) {
							siblingFeatureIndex = i;
							break FEATURES_LOOP;
						}
					}
				} else if (isEquivalentValue(sibling, featureValue)) {
					siblingFeatureIndex = i;
					break FEATURES_LOOP;
				}
				++i;
			}

			// For each CommandParameter with a non-null, multi-valued
			// structural feature...
			//
			DESCRIPTORS_LOOP: for (Object descriptor : newChildDescriptors) {
				if (descriptor instanceof CommandParameter) {
					CommandParameter parameter = (CommandParameter) descriptor;
					EStructuralFeature childFeature = parameter
							.getEStructuralFeature();
					if (childFeature == null || !childFeature.isMany()) {
						continue DESCRIPTORS_LOOP;
					}

					// Look for the sibling value or an equivalent in the new
					// child's feature. If it is found, the child should
					// immediately follow it.
					//
					i = 0;
					for (Object v : (Collection<?>) eObject.eGet(childFeature)) {
						if (isEquivalentValue(sibling, v)) {
							parameter.index = i + 1;
							continue DESCRIPTORS_LOOP;
						}
						++i;
					}

					// Otherwise, if a sibling feature was found, iterate
					// through the children features to find the index of
					// the child feature...
					//
					if (siblingFeatureIndex != -1) {
						i = 0;
						for (EStructuralFeature feature : childrenFeatures) {
							if (feature == childFeature) {
								// If the child feature follows the sibling
								// feature, the child should be first in its
								// feature.
								//
								if (i > siblingFeatureIndex) {
									parameter.index = 0;
								}
								continue DESCRIPTORS_LOOP;
							}
							++i;
						}
					}
				}
			}
		}
		return newChildDescriptors;

	}
    /**
     * This adds to the collection of
     * {@link org.eclipse.emf.edit.command.CommandParameter}s describing all of
     * the children that can be created under this object. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors,
    		IProject project,
            Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources.
     */
    public ResourceLocator getResourceLocator() {
        return MdfPlugin.getDefault().getResourceLocator();
    }

    /**
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
     */
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection collection) {
        return RemoveOrDeprecateCommand.create(owner, collection);
    }

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createDragAndDropCommand(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object, float, int, int, java.util.Collection)
	 */
	protected Command createDragAndDropCommand(EditingDomain domain,
			Object owner, float location, int operations, int operation,
			Collection<?> collection) {
		return new DragAndDropCommand(domain, owner, location, operations, operation, collection) {			
			public void execute() {
				super.execute();
				EditionSupport support = EditionSupportFactory.INSTANCE();
				Collection<?> affectedObjs = getAffectedObjects();
				List list = new ArrayList();
				list.addAll(affectedObjs);
				support.updateAfterDrop(list);
			}
			
		};
	}

}
