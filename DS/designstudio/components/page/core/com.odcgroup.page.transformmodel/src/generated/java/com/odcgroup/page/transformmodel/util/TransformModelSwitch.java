/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.util;

import com.odcgroup.page.transformmodel.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * @param <T>
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.transformmodel.TransformModelPackage
 * @generated
 */
public class TransformModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TransformModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModelSwitch() {
		if (modelPackage == null) {
			modelPackage = TransformModelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param theEClass
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * @param classifierID
	 * @param theEObject
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TransformModelPackage.TRANSFORM_MODEL: {
				TransformModel transformModel = (TransformModel)theEObject;
				T result = caseTransformModel(transformModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.NAMESPACE: {
				Namespace namespace = (Namespace)theEObject;
				T result = caseNamespace(namespace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER: {
				AbstractWidgetTransformer abstractWidgetTransformer = (AbstractWidgetTransformer)theEObject;
				T result = caseAbstractWidgetTransformer(abstractWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(abstractWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER: {
				SimpleWidgetTransformer simpleWidgetTransformer = (SimpleWidgetTransformer)theEObject;
				T result = caseSimpleWidgetTransformer(simpleWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(simpleWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(simpleWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER: {
				ElementNameWidgetTransformer elementNameWidgetTransformer = (ElementNameWidgetTransformer)theEObject;
				T result = caseElementNameWidgetTransformer(elementNameWidgetTransformer);
				if (result == null) result = caseSimpleWidgetTransformer(elementNameWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(elementNameWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(elementNameWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER: {
				AbstractPropertyTransformer abstractPropertyTransformer = (AbstractPropertyTransformer)theEObject;
				T result = caseAbstractPropertyTransformer(abstractPropertyTransformer);
				if (result == null) result = casePropertyTransformer(abstractPropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER: {
				SimplePropertyTransformer simplePropertyTransformer = (SimplePropertyTransformer)theEObject;
				T result = caseSimplePropertyTransformer(simplePropertyTransformer);
				if (result == null) result = caseAbstractPropertyTransformer(simplePropertyTransformer);
				if (result == null) result = casePropertyTransformer(simplePropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER: {
				AttributeNamePropertyTransformer attributeNamePropertyTransformer = (AttributeNamePropertyTransformer)theEObject;
				T result = caseAttributeNamePropertyTransformer(attributeNamePropertyTransformer);
				if (result == null) result = caseSimplePropertyTransformer(attributeNamePropertyTransformer);
				if (result == null) result = caseAbstractPropertyTransformer(attributeNamePropertyTransformer);
				if (result == null) result = casePropertyTransformer(attributeNamePropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER: {
				ElementPropertyTransformer elementPropertyTransformer = (ElementPropertyTransformer)theEObject;
				T result = caseElementPropertyTransformer(elementPropertyTransformer);
				if (result == null) result = caseSimplePropertyTransformer(elementPropertyTransformer);
				if (result == null) result = caseAbstractPropertyTransformer(elementPropertyTransformer);
				if (result == null) result = casePropertyTransformer(elementPropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER: {
				ExtraParentWidgetTransformer extraParentWidgetTransformer = (ExtraParentWidgetTransformer)theEObject;
				T result = caseExtraParentWidgetTransformer(extraParentWidgetTransformer);
				if (result == null) result = caseElementNameWidgetTransformer(extraParentWidgetTransformer);
				if (result == null) result = caseSimpleWidgetTransformer(extraParentWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(extraParentWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(extraParentWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.WIDGET_TRANSFORMER: {
				WidgetTransformer widgetTransformer = (WidgetTransformer)theEObject;
				T result = caseWidgetTransformer(widgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.PROPERTY_TRANSFORMER: {
				PropertyTransformer propertyTransformer = (PropertyTransformer)theEObject;
				T result = casePropertyTransformer(propertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER: {
				GenericPropertyTransformer genericPropertyTransformer = (GenericPropertyTransformer)theEObject;
				T result = caseGenericPropertyTransformer(genericPropertyTransformer);
				if (result == null) result = caseSimplePropertyTransformer(genericPropertyTransformer);
				if (result == null) result = caseAbstractPropertyTransformer(genericPropertyTransformer);
				if (result == null) result = casePropertyTransformer(genericPropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER: {
				GenericWidgetTransformer genericWidgetTransformer = (GenericWidgetTransformer)theEObject;
				T result = caseGenericWidgetTransformer(genericWidgetTransformer);
				if (result == null) result = caseSimpleWidgetTransformer(genericWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(genericWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(genericWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.NULL_WIDGET_TRANSFORMER: {
				NullWidgetTransformer nullWidgetTransformer = (NullWidgetTransformer)theEObject;
				T result = caseNullWidgetTransformer(nullWidgetTransformer);
				if (result == null) result = caseSimpleWidgetTransformer(nullWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(nullWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(nullWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.NULL_PROPERTY_TRANSFORMER: {
				NullPropertyTransformer nullPropertyTransformer = (NullPropertyTransformer)theEObject;
				T result = caseNullPropertyTransformer(nullPropertyTransformer);
				if (result == null) result = caseSimplePropertyTransformer(nullPropertyTransformer);
				if (result == null) result = caseAbstractPropertyTransformer(nullPropertyTransformer);
				if (result == null) result = casePropertyTransformer(nullPropertyTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.EVENT_TRANSFORMATIONS: {
				EventTransformations eventTransformations = (EventTransformations)theEObject;
				T result = caseEventTransformations(eventTransformations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.EVENT_TRANSFORMATION: {
				EventTransformation eventTransformation = (EventTransformation)theEObject;
				T result = caseEventTransformation(eventTransformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.CHILDREN_WIDGET_TRANSFORMER: {
				ChildrenWidgetTransformer childrenWidgetTransformer = (ChildrenWidgetTransformer)theEObject;
				T result = caseChildrenWidgetTransformer(childrenWidgetTransformer);
				if (result == null) result = caseSimpleWidgetTransformer(childrenWidgetTransformer);
				if (result == null) result = caseAbstractWidgetTransformer(childrenWidgetTransformer);
				if (result == null) result = caseWidgetTransformer(childrenWidgetTransformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.SNIPPET_TRANSFORMATION: {
				SnippetTransformation snippetTransformation = (SnippetTransformation)theEObject;
				T result = caseSnippetTransformation(snippetTransformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS: {
				SnippetTransformations snippetTransformations = (SnippetTransformations)theEObject;
				T result = caseSnippetTransformations(snippetTransformations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transform Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transform Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformModel(TransformModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Namespace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamespace(Namespace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleWidgetTransformer(SimpleWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimplePropertyTransformer(SimplePropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementPropertyTransformer(ElementPropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extra Parent Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extra Parent Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtraParentWidgetTransformer(ExtraParentWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericPropertyTransformer(GenericPropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericWidgetTransformer(GenericWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullWidgetTransformer(NullWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullPropertyTransformer(NullPropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Transformations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventTransformations(EventTransformations object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventTransformation(EventTransformation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Children Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Children Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChildrenWidgetTransformer(ChildrenWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Snippet Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Snippet Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSnippetTransformation(SnippetTransformation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Snippet Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Snippet Transformations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSnippetTransformations(SnippetTransformations object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyTransformer(PropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractWidgetTransformer(AbstractWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Name Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Name Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementNameWidgetTransformer(ElementNameWidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractPropertyTransformer(AbstractPropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Name Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Name Property Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeNamePropertyTransformer(AttributeNamePropertyTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetTransformer(WidgetTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //TransformModelSwitch
