/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.util;

import com.odcgroup.page.transformmodel.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.transformmodel.AbstractPropertyTransformer;
import com.odcgroup.page.transformmodel.AbstractWidgetTransformer;
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
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.SimplePropertyTransformer;
import com.odcgroup.page.transformmodel.SimpleWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.transformmodel.TransformModelPackage
 * @generated
 */
public class TransformModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TransformModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TransformModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformModelSwitch<Adapter> modelSwitch =
		new TransformModelSwitch<Adapter>() {
			@Override
			public Adapter caseTransformModel(TransformModel object) {
				return createTransformModelAdapter();
			}
			@Override
			public Adapter caseNamespace(Namespace object) {
				return createNamespaceAdapter();
			}
			@Override
			public Adapter caseAbstractWidgetTransformer(AbstractWidgetTransformer object) {
				return createAbstractWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseSimpleWidgetTransformer(SimpleWidgetTransformer object) {
				return createSimpleWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseElementNameWidgetTransformer(ElementNameWidgetTransformer object) {
				return createElementNameWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseAbstractPropertyTransformer(AbstractPropertyTransformer object) {
				return createAbstractPropertyTransformerAdapter();
			}
			@Override
			public Adapter caseSimplePropertyTransformer(SimplePropertyTransformer object) {
				return createSimplePropertyTransformerAdapter();
			}
			@Override
			public Adapter caseAttributeNamePropertyTransformer(AttributeNamePropertyTransformer object) {
				return createAttributeNamePropertyTransformerAdapter();
			}
			@Override
			public Adapter caseElementPropertyTransformer(ElementPropertyTransformer object) {
				return createElementPropertyTransformerAdapter();
			}
			@Override
			public Adapter caseExtraParentWidgetTransformer(ExtraParentWidgetTransformer object) {
				return createExtraParentWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseWidgetTransformer(WidgetTransformer object) {
				return createWidgetTransformerAdapter();
			}
			@Override
			public Adapter casePropertyTransformer(PropertyTransformer object) {
				return createPropertyTransformerAdapter();
			}
			@Override
			public Adapter caseGenericPropertyTransformer(GenericPropertyTransformer object) {
				return createGenericPropertyTransformerAdapter();
			}
			@Override
			public Adapter caseGenericWidgetTransformer(GenericWidgetTransformer object) {
				return createGenericWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseNullWidgetTransformer(NullWidgetTransformer object) {
				return createNullWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseNullPropertyTransformer(NullPropertyTransformer object) {
				return createNullPropertyTransformerAdapter();
			}
			@Override
			public Adapter caseEventTransformations(EventTransformations object) {
				return createEventTransformationsAdapter();
			}
			@Override
			public Adapter caseEventTransformation(EventTransformation object) {
				return createEventTransformationAdapter();
			}
			@Override
			public Adapter caseChildrenWidgetTransformer(ChildrenWidgetTransformer object) {
				return createChildrenWidgetTransformerAdapter();
			}
			@Override
			public Adapter caseSnippetTransformation(SnippetTransformation object) {
				return createSnippetTransformationAdapter();
			}
			@Override
			public Adapter caseSnippetTransformations(SnippetTransformations object) {
				return createSnippetTransformationsAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.TransformModel <em>Transform Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.TransformModel
	 * @generated
	 */
	public Adapter createTransformModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.Namespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.Namespace
	 * @generated
	 */
	public Adapter createNamespaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer <em>Simple Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.SimpleWidgetTransformer
	 * @generated
	 */
	public Adapter createSimpleWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.SimplePropertyTransformer <em>Simple Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.SimplePropertyTransformer
	 * @generated
	 */
	public Adapter createSimplePropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer <em>Element Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.ElementPropertyTransformer
	 * @generated
	 */
	public Adapter createElementPropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer <em>Extra Parent Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer
	 * @generated
	 */
	public Adapter createExtraParentWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.GenericPropertyTransformer <em>Generic Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.GenericPropertyTransformer
	 * @generated
	 */
	public Adapter createGenericPropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.GenericWidgetTransformer <em>Generic Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.GenericWidgetTransformer
	 * @generated
	 */
	public Adapter createGenericWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.NullWidgetTransformer <em>Null Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.NullWidgetTransformer
	 * @generated
	 */
	public Adapter createNullWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.NullPropertyTransformer <em>Null Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.NullPropertyTransformer
	 * @generated
	 */
	public Adapter createNullPropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.EventTransformations <em>Event Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.EventTransformations
	 * @generated
	 */
	public Adapter createEventTransformationsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.EventTransformation <em>Event Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.EventTransformation
	 * @generated
	 */
	public Adapter createEventTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.ChildrenWidgetTransformer <em>Children Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.ChildrenWidgetTransformer
	 * @generated
	 */
	public Adapter createChildrenWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.SnippetTransformation <em>Snippet Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformation
	 * @generated
	 */
	public Adapter createSnippetTransformationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.SnippetTransformations <em>Snippet Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformations
	 * @generated
	 */
	public Adapter createSnippetTransformationsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.PropertyTransformer <em>Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer
	 * @generated
	 */
	public Adapter createPropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.AbstractWidgetTransformer <em>Abstract Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.AbstractWidgetTransformer
	 * @generated
	 */
	public Adapter createAbstractWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.ElementNameWidgetTransformer <em>Element Name Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.ElementNameWidgetTransformer
	 * @generated
	 */
	public Adapter createElementNameWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer <em>Abstract Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.AbstractPropertyTransformer
	 * @generated
	 */
	public Adapter createAbstractPropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer <em>Attribute Name Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer
	 * @generated
	 */
	public Adapter createAttributeNamePropertyTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.transformmodel.WidgetTransformer <em>Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer
	 * @generated
	 */
	public Adapter createWidgetTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TransformModelAdapterFactory
