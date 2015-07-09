/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.transformmodel.TransformModelPackage
 * @generated
 */
public interface TransformModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformModelFactory eINSTANCE = com.odcgroup.page.transformmodel.impl.TransformModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Transform Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transform Model</em>'.
	 * @generated
	 */
	TransformModel createTransformModel();

	/**
	 * Returns a new object of class '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Namespace</em>'.
	 * @generated
	 */
	Namespace createNamespace();

	/**
	 * Returns a new object of class '<em>Element Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Property Transformer</em>'.
	 * @generated
	 */
	ElementPropertyTransformer createElementPropertyTransformer();

	/**
	 * Returns a new object of class '<em>Extra Parent Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extra Parent Widget Transformer</em>'.
	 * @generated
	 */
	ExtraParentWidgetTransformer createExtraParentWidgetTransformer();

	/**
	 * Returns a new object of class '<em>Element Name Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Name Widget Transformer</em>'.
	 * @generated
	 */
	ElementNameWidgetTransformer createElementNameWidgetTransformer();

	/**
	 * Returns a new object of class '<em>Attribute Name Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Name Property Transformer</em>'.
	 * @generated
	 */
	AttributeNamePropertyTransformer createAttributeNamePropertyTransformer();

	/**
	 * Returns a new object of class '<em>Generic Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Property Transformer</em>'.
	 * @generated
	 */
	GenericPropertyTransformer createGenericPropertyTransformer();

	/**
	 * Returns a new object of class '<em>Generic Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Widget Transformer</em>'.
	 * @generated
	 */
	GenericWidgetTransformer createGenericWidgetTransformer();

	/**
	 * Returns a new object of class '<em>Null Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Widget Transformer</em>'.
	 * @generated
	 */
	NullWidgetTransformer createNullWidgetTransformer();

	/**
	 * Returns a new object of class '<em>Null Property Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Property Transformer</em>'.
	 * @generated
	 */
	NullPropertyTransformer createNullPropertyTransformer();

	/**
	 * Returns a new object of class '<em>Event Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Transformations</em>'.
	 * @generated
	 */
	EventTransformations createEventTransformations();

	/**
	 * Returns a new object of class '<em>Event Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Transformation</em>'.
	 * @generated
	 */
	EventTransformation createEventTransformation();

	/**
	 * Returns a new object of class '<em>Children Widget Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Children Widget Transformer</em>'.
	 * @generated
	 */
	ChildrenWidgetTransformer createChildrenWidgetTransformer();

	/**
	 * Returns a new object of class '<em>Snippet Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Snippet Transformation</em>'.
	 * @generated
	 */
	SnippetTransformation createSnippetTransformation();

	/**
	 * Returns a new object of class '<em>Snippet Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Snippet Transformations</em>'.
	 * @generated
	 */
	SnippetTransformations createSnippetTransformations();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransformModelPackage getTransformModelPackage();

} //TransformModelFactory
