/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.transformmodel.TransformModelFactory
 * @model kind="package"
 * @generated
 */
public interface TransformModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "transformmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/page/transformmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.odcgroup.page.transformmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformModelPackage eINSTANCE = com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl <em>Transform Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getTransformModel()
	 * @generated
	 */
	int TRANSFORM_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Namespaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__NAMESPACES = 0;

	/**
	 * The feature id for the '<em><b>Widget Transformers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__WIDGET_TRANSFORMERS = 1;

	/**
	 * The feature id for the '<em><b>Default Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__DEFAULT_NAMESPACE = 2;

	/**
	 * The feature id for the '<em><b>Property Transformers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__PROPERTY_TRANSFORMERS = 3;

	/**
	 * The feature id for the '<em><b>Default Widget Library</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY = 4;

	/**
	 * The feature id for the '<em><b>Event Transformations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL__EVENT_TRANSFORMATIONS = 5;

	/**
	 * The number of structural features of the '<em>Transform Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORM_MODEL_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.NamespaceImpl <em>Namespace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.NamespaceImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNamespace()
	 * @generated
	 */
	int NAMESPACE = 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__URI = 0;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__PREFIX = 1;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__ORIGIN = 2;

	/**
	 * The number of structural features of the '<em>Namespace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.WidgetTransformer <em>Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getWidgetTransformer()
	 * @generated
	 */
	int WIDGET_TRANSFORMER = 10;

	/**
	 * The number of structural features of the '<em>Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_TRANSFORMER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl <em>Abstract Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAbstractWidgetTransformer()
	 * @generated
	 */
	int ABSTRACT_WIDGET_TRANSFORMER = 2;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_WIDGET_TRANSFORMER__MODEL = WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_WIDGET_TRANSFORMER_FEATURE_COUNT = WIDGET_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl <em>Simple Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSimpleWidgetTransformer()
	 * @generated
	 */
	int SIMPLE_WIDGET_TRANSFORMER = 3;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_WIDGET_TRANSFORMER__MODEL = ABSTRACT_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE = ABSTRACT_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_WIDGET_TRANSFORMER__NAMESPACE = ABSTRACT_WIDGET_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simple Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT = ABSTRACT_WIDGET_TRANSFORMER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.PropertyTransformer <em>Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getPropertyTransformer()
	 * @generated
	 */
	int PROPERTY_TRANSFORMER = 11;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.AbstractPropertyTransformerImpl <em>Abstract Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.AbstractPropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAbstractPropertyTransformer()
	 * @generated
	 */
	int ABSTRACT_PROPERTY_TRANSFORMER = 5;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.SimplePropertyTransformerImpl <em>Simple Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.SimplePropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSimplePropertyTransformer()
	 * @generated
	 */
	int SIMPLE_PROPERTY_TRANSFORMER = 6;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl <em>Element Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getElementPropertyTransformer()
	 * @generated
	 */
	int ELEMENT_PROPERTY_TRANSFORMER = 8;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl <em>Element Name Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getElementNameWidgetTransformer()
	 * @generated
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER = 4;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER__MODEL = SIMPLE_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER__WIDGET_TYPE = SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER__NAMESPACE = SIMPLE_WIDGET_TRANSFORMER__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Element Name Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_NAME_WIDGET_TRANSFORMER_FEATURE_COUNT = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TRANSFORMER_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TRANSFORMER__MODEL = PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TRANSFORMER_FEATURE_COUNT = PROPERTY_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TRANSFORMER__MODEL = ABSTRACT_PROPERTY_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE = ABSTRACT_PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT = ABSTRACT_PROPERTY_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl <em>Extra Parent Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getExtraParentWidgetTransformer()
	 * @generated
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER = 9;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.AttributeNamePropertyTransformerImpl <em>Attribute Name Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.AttributeNamePropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAttributeNamePropertyTransformer()
	 * @generated
	 */
	int ATTRIBUTE_NAME_PROPERTY_TRANSFORMER = 7;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__MODEL = SIMPLE_PROPERTY_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__PROPERTY_TYPE = SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE;

	/**
	 * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Name Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_NAME_PROPERTY_TRANSFORMER_FEATURE_COUNT = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PROPERTY_TRANSFORMER__MODEL = SIMPLE_PROPERTY_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PROPERTY_TRANSFORMER__PROPERTY_TYPE = SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE;

	/**
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Element Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PROPERTY_TRANSFORMER_FEATURE_COUNT = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__MODEL = ELEMENT_NAME_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__WIDGET_TYPE = ELEMENT_NAME_WIDGET_TRANSFORMER__WIDGET_TYPE;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__NAMESPACE = ELEMENT_NAME_WIDGET_TRANSFORMER__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__ELEMENT_NAME = ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME;

	/**
	 * The feature id for the '<em><b>Parent Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME = ELEMENT_NAME_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Only Add If Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT = ELEMENT_NAME_WIDGET_TRANSFORMER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE = ELEMENT_NAME_WIDGET_TRANSFORMER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Extra Parent Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTRA_PARENT_WIDGET_TRANSFORMER_FEATURE_COUNT = ELEMENT_NAME_WIDGET_TRANSFORMER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.GenericPropertyTransformerImpl <em>Generic Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.GenericPropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getGenericPropertyTransformer()
	 * @generated
	 */
	int GENERIC_PROPERTY_TRANSFORMER = 12;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PROPERTY_TRANSFORMER__MODEL = SIMPLE_PROPERTY_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PROPERTY_TRANSFORMER__PROPERTY_TYPE = SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE;

	/**
	 * The feature id for the '<em><b>Implementation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Generic Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PROPERTY_TRANSFORMER_FEATURE_COUNT = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.GenericWidgetTransformerImpl <em>Generic Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.GenericWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getGenericWidgetTransformer()
	 * @generated
	 */
	int GENERIC_WIDGET_TRANSFORMER = 13;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_WIDGET_TRANSFORMER__MODEL = SIMPLE_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_WIDGET_TRANSFORMER__WIDGET_TYPE = SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_WIDGET_TRANSFORMER__NAMESPACE = SIMPLE_WIDGET_TRANSFORMER__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Implementation Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Generic Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_WIDGET_TRANSFORMER_FEATURE_COUNT = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.NullWidgetTransformerImpl <em>Null Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.NullWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNullWidgetTransformer()
	 * @generated
	 */
	int NULL_WIDGET_TRANSFORMER = 14;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_WIDGET_TRANSFORMER__MODEL = SIMPLE_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_WIDGET_TRANSFORMER__WIDGET_TYPE = SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_WIDGET_TRANSFORMER__NAMESPACE = SIMPLE_WIDGET_TRANSFORMER__NAMESPACE;

	/**
	 * The number of structural features of the '<em>Null Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_WIDGET_TRANSFORMER_FEATURE_COUNT = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.NullPropertyTransformerImpl <em>Null Property Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.NullPropertyTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNullPropertyTransformer()
	 * @generated
	 */
	int NULL_PROPERTY_TRANSFORMER = 15;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_PROPERTY_TRANSFORMER__MODEL = SIMPLE_PROPERTY_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_PROPERTY_TRANSFORMER__PROPERTY_TYPE = SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE;

	/**
	 * The number of structural features of the '<em>Null Property Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_PROPERTY_TRANSFORMER_FEATURE_COUNT = SIMPLE_PROPERTY_TRANSFORMER_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.EventTransformationsImpl <em>Event Transformations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.EventTransformationsImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getEventTransformations()
	 * @generated
	 */
	int EVENT_TRANSFORMATIONS = 16;

	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TRANSFORMATIONS__TRANSFORMATIONS = 0;

	/**
	 * The number of structural features of the '<em>Event Transformations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TRANSFORMATIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.EventTransformationImpl <em>Event Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.EventTransformationImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getEventTransformation()
	 * @generated
	 */
	int EVENT_TRANSFORMATION = 17;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TRANSFORMATION__TYPE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Event Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TRANSFORMATION__EVENT_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Event Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TRANSFORMATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.ChildrenWidgetTransformerImpl <em>Children Widget Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.ChildrenWidgetTransformerImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getChildrenWidgetTransformer()
	 * @generated
	 */
	int CHILDREN_WIDGET_TRANSFORMER = 18;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILDREN_WIDGET_TRANSFORMER__MODEL = SIMPLE_WIDGET_TRANSFORMER__MODEL;

	/**
	 * The feature id for the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILDREN_WIDGET_TRANSFORMER__WIDGET_TYPE = SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILDREN_WIDGET_TRANSFORMER__NAMESPACE = SIMPLE_WIDGET_TRANSFORMER__NAMESPACE;

	/**
	 * The number of structural features of the '<em>Children Widget Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILDREN_WIDGET_TRANSFORMER_FEATURE_COUNT = SIMPLE_WIDGET_TRANSFORMER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationImpl <em>Snippet Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.SnippetTransformationImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSnippetTransformation()
	 * @generated
	 */
	int SNIPPET_TRANSFORMATION = 19;

	/**
	 * The feature id for the '<em><b>Snippet Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TRANSFORMATION__SNIPPET_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Snippet Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TRANSFORMATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationsImpl <em>Snippet Transformations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.transformmodel.impl.SnippetTransformationsImpl
	 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSnippetTransformations()
	 * @generated
	 */
	int SNIPPET_TRANSFORMATIONS = 20;

	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS = 0;

	/**
	 * The number of structural features of the '<em>Snippet Transformations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_TRANSFORMATIONS_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.TransformModel <em>Transform Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transform Model</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel
	 * @generated
	 */
	EClass getTransformModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.transformmodel.TransformModel#getNamespaces <em>Namespaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Namespaces</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getNamespaces()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_Namespaces();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.transformmodel.TransformModel#getWidgetTransformers <em>Widget Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Widget Transformers</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getWidgetTransformers()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_WidgetTransformers();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultNamespace <em>Default Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Namespace</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getDefaultNamespace()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_DefaultNamespace();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.transformmodel.TransformModel#getPropertyTransformers <em>Property Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Transformers</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getPropertyTransformers()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_PropertyTransformers();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultWidgetLibrary <em>Default Widget Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Widget Library</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getDefaultWidgetLibrary()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_DefaultWidgetLibrary();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.transformmodel.TransformModel#getEventTransformations <em>Event Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event Transformations</em>'.
	 * @see com.odcgroup.page.transformmodel.TransformModel#getEventTransformations()
	 * @see #getTransformModel()
	 * @generated
	 */
	EReference getTransformModel_EventTransformations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.Namespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace</em>'.
	 * @see com.odcgroup.page.transformmodel.Namespace
	 * @generated
	 */
	EClass getNamespace();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.Namespace#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.odcgroup.page.transformmodel.Namespace#getUri()
	 * @see #getNamespace()
	 * @generated
	 */
	EAttribute getNamespace_Uri();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.Namespace#getPrefix <em>Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see com.odcgroup.page.transformmodel.Namespace#getPrefix()
	 * @see #getNamespace()
	 * @generated
	 */
	EAttribute getNamespace_Prefix();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.Namespace#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Origin</em>'.
	 * @see com.odcgroup.page.transformmodel.Namespace#getOrigin()
	 * @see #getNamespace()
	 * @generated
	 */
	EAttribute getNamespace_Origin();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer <em>Simple Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.SimpleWidgetTransformer
	 * @generated
	 */
	EClass getSimpleWidgetTransformer();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getWidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget Type</em>'.
	 * @see com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getWidgetType()
	 * @see #getSimpleWidgetTransformer()
	 * @generated
	 */
	EReference getSimpleWidgetTransformer_WidgetType();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Namespace</em>'.
	 * @see com.odcgroup.page.transformmodel.SimpleWidgetTransformer#getNamespace()
	 * @see #getSimpleWidgetTransformer()
	 * @generated
	 */
	EReference getSimpleWidgetTransformer_Namespace();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.SimplePropertyTransformer <em>Simple Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.SimplePropertyTransformer
	 * @generated
	 */
	EClass getSimplePropertyTransformer();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.SimplePropertyTransformer#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property Type</em>'.
	 * @see com.odcgroup.page.transformmodel.SimplePropertyTransformer#getPropertyType()
	 * @see #getSimplePropertyTransformer()
	 * @generated
	 */
	EReference getSimplePropertyTransformer_PropertyType();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer <em>Element Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.ElementPropertyTransformer
	 * @generated
	 */
	EClass getElementPropertyTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getElementName <em>Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Name</em>'.
	 * @see com.odcgroup.page.transformmodel.ElementPropertyTransformer#getElementName()
	 * @see #getElementPropertyTransformer()
	 * @generated
	 */
	EAttribute getElementPropertyTransformer_ElementName();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.ElementPropertyTransformer#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Namespace</em>'.
	 * @see com.odcgroup.page.transformmodel.ElementPropertyTransformer#getNamespace()
	 * @see #getElementPropertyTransformer()
	 * @generated
	 */
	EReference getElementPropertyTransformer_Namespace();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer <em>Extra Parent Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extra Parent Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer
	 * @generated
	 */
	EClass getExtraParentWidgetTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentElementName <em>Parent Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Element Name</em>'.
	 * @see com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentElementName()
	 * @see #getExtraParentWidgetTransformer()
	 * @generated
	 */
	EAttribute getExtraParentWidgetTransformer_ParentElementName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#isOnlyAddIfRoot <em>Only Add If Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Only Add If Root</em>'.
	 * @see com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#isOnlyAddIfRoot()
	 * @see #getExtraParentWidgetTransformer()
	 * @generated
	 */
	EAttribute getExtraParentWidgetTransformer_OnlyAddIfRoot();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentNamespace <em>Parent Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Namespace</em>'.
	 * @see com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentNamespace()
	 * @see #getExtraParentWidgetTransformer()
	 * @generated
	 */
	EReference getExtraParentWidgetTransformer_ParentNamespace();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.GenericPropertyTransformer <em>Generic Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.GenericPropertyTransformer
	 * @generated
	 */
	EClass getGenericPropertyTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.GenericPropertyTransformer#getImplementationClass <em>Implementation Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Class</em>'.
	 * @see com.odcgroup.page.transformmodel.GenericPropertyTransformer#getImplementationClass()
	 * @see #getGenericPropertyTransformer()
	 * @generated
	 */
	EAttribute getGenericPropertyTransformer_ImplementationClass();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.GenericWidgetTransformer <em>Generic Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.GenericWidgetTransformer
	 * @generated
	 */
	EClass getGenericWidgetTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.GenericWidgetTransformer#getImplementationClass <em>Implementation Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Class</em>'.
	 * @see com.odcgroup.page.transformmodel.GenericWidgetTransformer#getImplementationClass()
	 * @see #getGenericWidgetTransformer()
	 * @generated
	 */
	EAttribute getGenericWidgetTransformer_ImplementationClass();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.NullWidgetTransformer <em>Null Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.NullWidgetTransformer
	 * @generated
	 */
	EClass getNullWidgetTransformer();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.NullPropertyTransformer <em>Null Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.NullPropertyTransformer
	 * @generated
	 */
	EClass getNullPropertyTransformer();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.EventTransformations <em>Event Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Transformations</em>'.
	 * @see com.odcgroup.page.transformmodel.EventTransformations
	 * @generated
	 */
	EClass getEventTransformations();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.transformmodel.EventTransformations#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformations</em>'.
	 * @see com.odcgroup.page.transformmodel.EventTransformations#getTransformations()
	 * @see #getEventTransformations()
	 * @generated
	 */
	EReference getEventTransformations_Transformations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.EventTransformation <em>Event Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Transformation</em>'.
	 * @see com.odcgroup.page.transformmodel.EventTransformation
	 * @generated
	 */
	EClass getEventTransformation();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.EventTransformation#getEventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Event Type</em>'.
	 * @see com.odcgroup.page.transformmodel.EventTransformation#getEventType()
	 * @see #getEventTransformation()
	 * @generated
	 */
	EReference getEventTransformation_EventType();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.EventTransformation#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see com.odcgroup.page.transformmodel.EventTransformation#getTypeName()
	 * @see #getEventTransformation()
	 * @generated
	 */
	EAttribute getEventTransformation_TypeName();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.ChildrenWidgetTransformer <em>Children Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Children Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.ChildrenWidgetTransformer
	 * @generated
	 */
	EClass getChildrenWidgetTransformer();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.SnippetTransformation <em>Snippet Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet Transformation</em>'.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformation
	 * @generated
	 */
	EClass getSnippetTransformation();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.transformmodel.SnippetTransformation#getSnippetType <em>Snippet Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Snippet Type</em>'.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformation#getSnippetType()
	 * @see #getSnippetTransformation()
	 * @generated
	 */
	EReference getSnippetTransformation_SnippetType();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.SnippetTransformations <em>Snippet Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet Transformations</em>'.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformations
	 * @generated
	 */
	EClass getSnippetTransformations();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.transformmodel.SnippetTransformations#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformations</em>'.
	 * @see com.odcgroup.page.transformmodel.SnippetTransformations#getTransformations()
	 * @see #getSnippetTransformations()
	 * @generated
	 */
	EReference getSnippetTransformations_Transformations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.PropertyTransformer <em>Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer
	 * @generated
	 */
	EClass getPropertyTransformer();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.AbstractWidgetTransformer <em>Abstract Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.AbstractWidgetTransformer
	 * @generated
	 */
	EClass getAbstractWidgetTransformer();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.transformmodel.AbstractWidgetTransformer#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Model</em>'.
	 * @see com.odcgroup.page.transformmodel.AbstractWidgetTransformer#getModel()
	 * @see #getAbstractWidgetTransformer()
	 * @generated
	 */
	EReference getAbstractWidgetTransformer_Model();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.ElementNameWidgetTransformer <em>Element Name Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Name Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.ElementNameWidgetTransformer
	 * @generated
	 */
	EClass getElementNameWidgetTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.ElementNameWidgetTransformer#getElementName <em>Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Name</em>'.
	 * @see com.odcgroup.page.transformmodel.ElementNameWidgetTransformer#getElementName()
	 * @see #getElementNameWidgetTransformer()
	 * @generated
	 */
	EAttribute getElementNameWidgetTransformer_ElementName();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer <em>Abstract Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.AbstractPropertyTransformer
	 * @generated
	 */
	EClass getAbstractPropertyTransformer();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Model</em>'.
	 * @see com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel()
	 * @see #getAbstractPropertyTransformer()
	 * @generated
	 */
	EReference getAbstractPropertyTransformer_Model();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer <em>Attribute Name Property Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Name Property Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer
	 * @generated
	 */
	EClass getAttributeNamePropertyTransformer();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer#getAttributeName <em>Attribute Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Name</em>'.
	 * @see com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer#getAttributeName()
	 * @see #getAttributeNamePropertyTransformer()
	 * @generated
	 */
	EAttribute getAttributeNamePropertyTransformer_AttributeName();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.transformmodel.WidgetTransformer <em>Widget Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget Transformer</em>'.
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer
	 * @generated
	 */
	EClass getWidgetTransformer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransformModelFactory getTransformModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.TransformModelImpl <em>Transform Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getTransformModel()
		 * @generated
		 */
		EClass TRANSFORM_MODEL = eINSTANCE.getTransformModel();

		/**
		 * The meta object literal for the '<em><b>Namespaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__NAMESPACES = eINSTANCE.getTransformModel_Namespaces();

		/**
		 * The meta object literal for the '<em><b>Widget Transformers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__WIDGET_TRANSFORMERS = eINSTANCE.getTransformModel_WidgetTransformers();

		/**
		 * The meta object literal for the '<em><b>Default Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__DEFAULT_NAMESPACE = eINSTANCE.getTransformModel_DefaultNamespace();

		/**
		 * The meta object literal for the '<em><b>Property Transformers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__PROPERTY_TRANSFORMERS = eINSTANCE.getTransformModel_PropertyTransformers();

		/**
		 * The meta object literal for the '<em><b>Default Widget Library</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY = eINSTANCE.getTransformModel_DefaultWidgetLibrary();

		/**
		 * The meta object literal for the '<em><b>Event Transformations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORM_MODEL__EVENT_TRANSFORMATIONS = eINSTANCE.getTransformModel_EventTransformations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.NamespaceImpl <em>Namespace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.NamespaceImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNamespace()
		 * @generated
		 */
		EClass NAMESPACE = eINSTANCE.getNamespace();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE__URI = eINSTANCE.getNamespace_Uri();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE__PREFIX = eINSTANCE.getNamespace_Prefix();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE__ORIGIN = eINSTANCE.getNamespace_Origin();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl <em>Simple Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSimpleWidgetTransformer()
		 * @generated
		 */
		EClass SIMPLE_WIDGET_TRANSFORMER = eINSTANCE.getSimpleWidgetTransformer();

		/**
		 * The meta object literal for the '<em><b>Widget Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE = eINSTANCE.getSimpleWidgetTransformer_WidgetType();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_WIDGET_TRANSFORMER__NAMESPACE = eINSTANCE.getSimpleWidgetTransformer_Namespace();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.SimplePropertyTransformerImpl <em>Simple Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.SimplePropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSimplePropertyTransformer()
		 * @generated
		 */
		EClass SIMPLE_PROPERTY_TRANSFORMER = eINSTANCE.getSimplePropertyTransformer();

		/**
		 * The meta object literal for the '<em><b>Property Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE = eINSTANCE.getSimplePropertyTransformer_PropertyType();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl <em>Element Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getElementPropertyTransformer()
		 * @generated
		 */
		EClass ELEMENT_PROPERTY_TRANSFORMER = eINSTANCE.getElementPropertyTransformer();

		/**
		 * The meta object literal for the '<em><b>Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME = eINSTANCE.getElementPropertyTransformer_ElementName();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE = eINSTANCE.getElementPropertyTransformer_Namespace();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl <em>Extra Parent Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getExtraParentWidgetTransformer()
		 * @generated
		 */
		EClass EXTRA_PARENT_WIDGET_TRANSFORMER = eINSTANCE.getExtraParentWidgetTransformer();

		/**
		 * The meta object literal for the '<em><b>Parent Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME = eINSTANCE.getExtraParentWidgetTransformer_ParentElementName();

		/**
		 * The meta object literal for the '<em><b>Only Add If Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT = eINSTANCE.getExtraParentWidgetTransformer_OnlyAddIfRoot();

		/**
		 * The meta object literal for the '<em><b>Parent Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE = eINSTANCE.getExtraParentWidgetTransformer_ParentNamespace();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.GenericPropertyTransformerImpl <em>Generic Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.GenericPropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getGenericPropertyTransformer()
		 * @generated
		 */
		EClass GENERIC_PROPERTY_TRANSFORMER = eINSTANCE.getGenericPropertyTransformer();

		/**
		 * The meta object literal for the '<em><b>Implementation Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS = eINSTANCE.getGenericPropertyTransformer_ImplementationClass();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.GenericWidgetTransformerImpl <em>Generic Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.GenericWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getGenericWidgetTransformer()
		 * @generated
		 */
		EClass GENERIC_WIDGET_TRANSFORMER = eINSTANCE.getGenericWidgetTransformer();

		/**
		 * The meta object literal for the '<em><b>Implementation Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS = eINSTANCE.getGenericWidgetTransformer_ImplementationClass();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.NullWidgetTransformerImpl <em>Null Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.NullWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNullWidgetTransformer()
		 * @generated
		 */
		EClass NULL_WIDGET_TRANSFORMER = eINSTANCE.getNullWidgetTransformer();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.NullPropertyTransformerImpl <em>Null Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.NullPropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getNullPropertyTransformer()
		 * @generated
		 */
		EClass NULL_PROPERTY_TRANSFORMER = eINSTANCE.getNullPropertyTransformer();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.EventTransformationsImpl <em>Event Transformations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.EventTransformationsImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getEventTransformations()
		 * @generated
		 */
		EClass EVENT_TRANSFORMATIONS = eINSTANCE.getEventTransformations();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_TRANSFORMATIONS__TRANSFORMATIONS = eINSTANCE.getEventTransformations_Transformations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.EventTransformationImpl <em>Event Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.EventTransformationImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getEventTransformation()
		 * @generated
		 */
		EClass EVENT_TRANSFORMATION = eINSTANCE.getEventTransformation();

		/**
		 * The meta object literal for the '<em><b>Event Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_TRANSFORMATION__EVENT_TYPE = eINSTANCE.getEventTransformation_EventType();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TRANSFORMATION__TYPE_NAME = eINSTANCE.getEventTransformation_TypeName();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.ChildrenWidgetTransformerImpl <em>Children Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.ChildrenWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getChildrenWidgetTransformer()
		 * @generated
		 */
		EClass CHILDREN_WIDGET_TRANSFORMER = eINSTANCE.getChildrenWidgetTransformer();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationImpl <em>Snippet Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.SnippetTransformationImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSnippetTransformation()
		 * @generated
		 */
		EClass SNIPPET_TRANSFORMATION = eINSTANCE.getSnippetTransformation();

		/**
		 * The meta object literal for the '<em><b>Snippet Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_TRANSFORMATION__SNIPPET_TYPE = eINSTANCE.getSnippetTransformation_SnippetType();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationsImpl <em>Snippet Transformations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.SnippetTransformationsImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getSnippetTransformations()
		 * @generated
		 */
		EClass SNIPPET_TRANSFORMATIONS = eINSTANCE.getSnippetTransformations();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS = eINSTANCE.getSnippetTransformations_Transformations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.PropertyTransformer <em>Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.PropertyTransformer
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getPropertyTransformer()
		 * @generated
		 */
		EClass PROPERTY_TRANSFORMER = eINSTANCE.getPropertyTransformer();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl <em>Abstract Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAbstractWidgetTransformer()
		 * @generated
		 */
		EClass ABSTRACT_WIDGET_TRANSFORMER = eINSTANCE.getAbstractWidgetTransformer();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_WIDGET_TRANSFORMER__MODEL = eINSTANCE.getAbstractWidgetTransformer_Model();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl <em>Element Name Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getElementNameWidgetTransformer()
		 * @generated
		 */
		EClass ELEMENT_NAME_WIDGET_TRANSFORMER = eINSTANCE.getElementNameWidgetTransformer();

		/**
		 * The meta object literal for the '<em><b>Element Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME = eINSTANCE.getElementNameWidgetTransformer_ElementName();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.AbstractPropertyTransformerImpl <em>Abstract Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.AbstractPropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAbstractPropertyTransformer()
		 * @generated
		 */
		EClass ABSTRACT_PROPERTY_TRANSFORMER = eINSTANCE.getAbstractPropertyTransformer();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PROPERTY_TRANSFORMER__MODEL = eINSTANCE.getAbstractPropertyTransformer_Model();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.impl.AttributeNamePropertyTransformerImpl <em>Attribute Name Property Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.impl.AttributeNamePropertyTransformerImpl
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getAttributeNamePropertyTransformer()
		 * @generated
		 */
		EClass ATTRIBUTE_NAME_PROPERTY_TRANSFORMER = eINSTANCE.getAttributeNamePropertyTransformer();

		/**
		 * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME = eINSTANCE.getAttributeNamePropertyTransformer_AttributeName();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.transformmodel.WidgetTransformer <em>Widget Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.transformmodel.WidgetTransformer
		 * @see com.odcgroup.page.transformmodel.impl.TransformModelPackageImpl#getWidgetTransformer()
		 * @generated
		 */
		EClass WIDGET_TRANSFORMER = eINSTANCE.getWidgetTransformer();

	}

} //TransformModelPackage
