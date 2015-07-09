/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import com.odcgroup.page.transformmodel.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer;
import com.odcgroup.page.transformmodel.ChildrenWidgetTransformer;
import com.odcgroup.page.transformmodel.ElementNameWidgetTransformer;
import com.odcgroup.page.transformmodel.ElementPropertyTransformer;
import com.odcgroup.page.transformmodel.EventTransformation;
import com.odcgroup.page.transformmodel.EventTransformations;
import com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer;
import com.odcgroup.page.transformmodel.GenericPropertyTransformer;
import com.odcgroup.page.transformmodel.GenericWidgetTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.NullPropertyTransformer;
import com.odcgroup.page.transformmodel.NullWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelFactory;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformModelFactoryImpl extends EFactoryImpl implements TransformModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * @return TransformModelFactory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransformModelFactory init() {
		try {
			TransformModelFactory theTransformModelFactory = (TransformModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/page/transformmodel"); 
			if (theTransformModelFactory != null) {
				return theTransformModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TransformModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TransformModelPackage.TRANSFORM_MODEL: return createTransformModel();
			case TransformModelPackage.NAMESPACE: return createNamespace();
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER: return createElementNameWidgetTransformer();
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER: return createAttributeNamePropertyTransformer();
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER: return createElementPropertyTransformer();
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER: return createExtraParentWidgetTransformer();
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER: return createGenericPropertyTransformer();
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER: return createGenericWidgetTransformer();
			case TransformModelPackage.NULL_WIDGET_TRANSFORMER: return createNullWidgetTransformer();
			case TransformModelPackage.NULL_PROPERTY_TRANSFORMER: return createNullPropertyTransformer();
			case TransformModelPackage.EVENT_TRANSFORMATIONS: return createEventTransformations();
			case TransformModelPackage.EVENT_TRANSFORMATION: return createEventTransformation();
			case TransformModelPackage.CHILDREN_WIDGET_TRANSFORMER: return createChildrenWidgetTransformer();
			case TransformModelPackage.SNIPPET_TRANSFORMATION: return createSnippetTransformation();
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS: return createSnippetTransformations();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModel createTransformModel() {
		TransformModelImpl transformModel = new TransformModelImpl();
		return transformModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace createNamespace() {
		NamespaceImpl namespace = new NamespaceImpl();
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ElementPropertyTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementPropertyTransformer createElementPropertyTransformer() {
		ElementPropertyTransformerImpl elementPropertyTransformer = new ElementPropertyTransformerImpl();
		return elementPropertyTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ExtraParentWidgetTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtraParentWidgetTransformer createExtraParentWidgetTransformer() {
		ExtraParentWidgetTransformerImpl extraParentWidgetTransformer = new ExtraParentWidgetTransformerImpl();
		return extraParentWidgetTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ElementNameWidgetTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementNameWidgetTransformer createElementNameWidgetTransformer() {
		ElementNameWidgetTransformerImpl elementNameWidgetTransformer = new ElementNameWidgetTransformerImpl();
		return elementNameWidgetTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return AttributeNamePropertyTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeNamePropertyTransformer createAttributeNamePropertyTransformer() {
		AttributeNamePropertyTransformerImpl attributeNamePropertyTransformer = new AttributeNamePropertyTransformerImpl();
		return attributeNamePropertyTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return GenericPropertyTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericPropertyTransformer createGenericPropertyTransformer() {
		GenericPropertyTransformerImpl genericPropertyTransformer = new GenericPropertyTransformerImpl();
		return genericPropertyTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return GenericWidgetTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericWidgetTransformer createGenericWidgetTransformer() {
		GenericWidgetTransformerImpl genericWidgetTransformer = new GenericWidgetTransformerImpl();
		return genericWidgetTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return NullWidgetTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullWidgetTransformer createNullWidgetTransformer() {
		NullWidgetTransformerImpl nullWidgetTransformer = new NullWidgetTransformerImpl();
		return nullWidgetTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return NullPropertyTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullPropertyTransformer createNullPropertyTransformer() {
		NullPropertyTransformerImpl nullPropertyTransformer = new NullPropertyTransformerImpl();
		return nullPropertyTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventTransformations
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventTransformations createEventTransformations() {
		EventTransformationsImpl eventTransformations = new EventTransformationsImpl();
		return eventTransformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventTransformation
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventTransformation createEventTransformation() {
		EventTransformationImpl eventTransformation = new EventTransformationImpl();
		return eventTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ChildrenWidgetTransformer
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildrenWidgetTransformer createChildrenWidgetTransformer() {
		ChildrenWidgetTransformerImpl childrenWidgetTransformer = new ChildrenWidgetTransformerImpl();
		return childrenWidgetTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return SnippetTransformation
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetTransformation createSnippetTransformation() {
		SnippetTransformationImpl snippetTransformation = new SnippetTransformationImpl();
		return snippetTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return SnippetTransformations
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetTransformations createSnippetTransformations() {
		SnippetTransformationsImpl snippetTransformations = new SnippetTransformationsImpl();
		return snippetTransformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModelPackage
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModelPackage getTransformModelPackage() {
		return (TransformModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModelFactory
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TransformModelPackage getPackage() {
		return TransformModelPackage.eINSTANCE;
	}

} //TransformModelFactoryImpl
