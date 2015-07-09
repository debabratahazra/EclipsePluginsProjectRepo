/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.odcgroup.page.metamodel.MetaModelPackage;
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
import com.odcgroup.page.transformmodel.SnippetTransformation;
import com.odcgroup.page.transformmodel.SnippetTransformations;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelFactory;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformModelPackageImpl extends EPackageImpl implements TransformModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transformModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simplePropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementPropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extraParentWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericPropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullPropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTransformationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childrenWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass snippetTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass snippetTransformationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementNameWidgetTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractPropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeNamePropertyTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetTransformerEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransformModelPackageImpl() {
		super(eNS_URI, TransformModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TransformModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * @return TransformModelPackage
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransformModelPackage init() {
		if (isInited) return (TransformModelPackage)EPackage.Registry.INSTANCE.getEPackage(TransformModelPackage.eNS_URI);

		// Obtain or create and register package
		TransformModelPackageImpl theTransformModelPackage = (TransformModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TransformModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TransformModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		MetaModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTransformModelPackage.createPackageContents();

		// Initialize created meta-data
		theTransformModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransformModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransformModelPackage.eNS_URI, theTransformModelPackage);
		return theTransformModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransformModel() {
		return transformModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_Namespaces() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_WidgetTransformers() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_DefaultNamespace() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_PropertyTransformers() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_DefaultWidgetLibrary() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransformModel_EventTransformations() {
		return (EReference)transformModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamespace() {
		return namespaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespace_Uri() {
		return (EAttribute)namespaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespace_Prefix() {
		return (EAttribute)namespaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespace_Origin() {
		return (EAttribute)namespaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleWidgetTransformer() {
		return simpleWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleWidgetTransformer_WidgetType() {
		return (EReference)simpleWidgetTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleWidgetTransformer_Namespace() {
		return (EReference)simpleWidgetTransformerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimplePropertyTransformer() {
		return simplePropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimplePropertyTransformer_PropertyType() {
		return (EReference)simplePropertyTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementPropertyTransformer() {
		return elementPropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementPropertyTransformer_ElementName() {
		return (EAttribute)elementPropertyTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementPropertyTransformer_Namespace() {
		return (EReference)elementPropertyTransformerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtraParentWidgetTransformer() {
		return extraParentWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtraParentWidgetTransformer_ParentElementName() {
		return (EAttribute)extraParentWidgetTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtraParentWidgetTransformer_OnlyAddIfRoot() {
		return (EAttribute)extraParentWidgetTransformerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtraParentWidgetTransformer_ParentNamespace() {
		return (EReference)extraParentWidgetTransformerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericPropertyTransformer() {
		return genericPropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericPropertyTransformer_ImplementationClass() {
		return (EAttribute)genericPropertyTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericWidgetTransformer() {
		return genericWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericWidgetTransformer_ImplementationClass() {
		return (EAttribute)genericWidgetTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNullWidgetTransformer() {
		return nullWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNullPropertyTransformer() {
		return nullPropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventTransformations() {
		return eventTransformationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventTransformations_Transformations() {
		return (EReference)eventTransformationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventTransformation() {
		return eventTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventTransformation_EventType() {
		return (EReference)eventTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventTransformation_TypeName() {
		return (EAttribute)eventTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildrenWidgetTransformer() {
		return childrenWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSnippetTransformation() {
		return snippetTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetTransformation_SnippetType() {
		return (EReference)snippetTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSnippetTransformations() {
		return snippetTransformationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetTransformations_Transformations() {
		return (EReference)snippetTransformationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyTransformer() {
		return propertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractWidgetTransformer() {
		return abstractWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractWidgetTransformer_Model() {
		return (EReference)abstractWidgetTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementNameWidgetTransformer() {
		return elementNameWidgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementNameWidgetTransformer_ElementName() {
		return (EAttribute)elementNameWidgetTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractPropertyTransformer() {
		return abstractPropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractPropertyTransformer_Model() {
		return (EReference)abstractPropertyTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeNamePropertyTransformer() {
		return attributeNamePropertyTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeNamePropertyTransformer_AttributeName() {
		return (EAttribute)attributeNamePropertyTransformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetTransformer() {
		return widgetTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModelFactory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModelFactory getTransformModelFactory() {
		return (TransformModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		transformModelEClass = createEClass(TRANSFORM_MODEL);
		createEReference(transformModelEClass, TRANSFORM_MODEL__NAMESPACES);
		createEReference(transformModelEClass, TRANSFORM_MODEL__WIDGET_TRANSFORMERS);
		createEReference(transformModelEClass, TRANSFORM_MODEL__DEFAULT_NAMESPACE);
		createEReference(transformModelEClass, TRANSFORM_MODEL__PROPERTY_TRANSFORMERS);
		createEReference(transformModelEClass, TRANSFORM_MODEL__DEFAULT_WIDGET_LIBRARY);
		createEReference(transformModelEClass, TRANSFORM_MODEL__EVENT_TRANSFORMATIONS);

		namespaceEClass = createEClass(NAMESPACE);
		createEAttribute(namespaceEClass, NAMESPACE__URI);
		createEAttribute(namespaceEClass, NAMESPACE__PREFIX);
		createEAttribute(namespaceEClass, NAMESPACE__ORIGIN);

		abstractWidgetTransformerEClass = createEClass(ABSTRACT_WIDGET_TRANSFORMER);
		createEReference(abstractWidgetTransformerEClass, ABSTRACT_WIDGET_TRANSFORMER__MODEL);

		simpleWidgetTransformerEClass = createEClass(SIMPLE_WIDGET_TRANSFORMER);
		createEReference(simpleWidgetTransformerEClass, SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE);
		createEReference(simpleWidgetTransformerEClass, SIMPLE_WIDGET_TRANSFORMER__NAMESPACE);

		elementNameWidgetTransformerEClass = createEClass(ELEMENT_NAME_WIDGET_TRANSFORMER);
		createEAttribute(elementNameWidgetTransformerEClass, ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME);

		abstractPropertyTransformerEClass = createEClass(ABSTRACT_PROPERTY_TRANSFORMER);
		createEReference(abstractPropertyTransformerEClass, ABSTRACT_PROPERTY_TRANSFORMER__MODEL);

		simplePropertyTransformerEClass = createEClass(SIMPLE_PROPERTY_TRANSFORMER);
		createEReference(simplePropertyTransformerEClass, SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE);

		attributeNamePropertyTransformerEClass = createEClass(ATTRIBUTE_NAME_PROPERTY_TRANSFORMER);
		createEAttribute(attributeNamePropertyTransformerEClass, ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME);

		elementPropertyTransformerEClass = createEClass(ELEMENT_PROPERTY_TRANSFORMER);
		createEAttribute(elementPropertyTransformerEClass, ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME);
		createEReference(elementPropertyTransformerEClass, ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE);

		extraParentWidgetTransformerEClass = createEClass(EXTRA_PARENT_WIDGET_TRANSFORMER);
		createEAttribute(extraParentWidgetTransformerEClass, EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME);
		createEAttribute(extraParentWidgetTransformerEClass, EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT);
		createEReference(extraParentWidgetTransformerEClass, EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE);

		widgetTransformerEClass = createEClass(WIDGET_TRANSFORMER);

		propertyTransformerEClass = createEClass(PROPERTY_TRANSFORMER);

		genericPropertyTransformerEClass = createEClass(GENERIC_PROPERTY_TRANSFORMER);
		createEAttribute(genericPropertyTransformerEClass, GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS);

		genericWidgetTransformerEClass = createEClass(GENERIC_WIDGET_TRANSFORMER);
		createEAttribute(genericWidgetTransformerEClass, GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS);

		nullWidgetTransformerEClass = createEClass(NULL_WIDGET_TRANSFORMER);

		nullPropertyTransformerEClass = createEClass(NULL_PROPERTY_TRANSFORMER);

		eventTransformationsEClass = createEClass(EVENT_TRANSFORMATIONS);
		createEReference(eventTransformationsEClass, EVENT_TRANSFORMATIONS__TRANSFORMATIONS);

		eventTransformationEClass = createEClass(EVENT_TRANSFORMATION);
		createEAttribute(eventTransformationEClass, EVENT_TRANSFORMATION__TYPE_NAME);
		createEReference(eventTransformationEClass, EVENT_TRANSFORMATION__EVENT_TYPE);

		childrenWidgetTransformerEClass = createEClass(CHILDREN_WIDGET_TRANSFORMER);

		snippetTransformationEClass = createEClass(SNIPPET_TRANSFORMATION);
		createEReference(snippetTransformationEClass, SNIPPET_TRANSFORMATION__SNIPPET_TYPE);

		snippetTransformationsEClass = createEClass(SNIPPET_TRANSFORMATIONS);
		createEReference(snippetTransformationsEClass, SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		MetaModelPackage theMetaModelPackage = (MetaModelPackage)EPackage.Registry.INSTANCE.getEPackage(MetaModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		abstractWidgetTransformerEClass.getESuperTypes().add(this.getWidgetTransformer());
		simpleWidgetTransformerEClass.getESuperTypes().add(this.getAbstractWidgetTransformer());
		elementNameWidgetTransformerEClass.getESuperTypes().add(this.getSimpleWidgetTransformer());
		abstractPropertyTransformerEClass.getESuperTypes().add(this.getPropertyTransformer());
		simplePropertyTransformerEClass.getESuperTypes().add(this.getAbstractPropertyTransformer());
		attributeNamePropertyTransformerEClass.getESuperTypes().add(this.getSimplePropertyTransformer());
		elementPropertyTransformerEClass.getESuperTypes().add(this.getSimplePropertyTransformer());
		extraParentWidgetTransformerEClass.getESuperTypes().add(this.getElementNameWidgetTransformer());
		genericPropertyTransformerEClass.getESuperTypes().add(this.getSimplePropertyTransformer());
		genericWidgetTransformerEClass.getESuperTypes().add(this.getSimpleWidgetTransformer());
		nullWidgetTransformerEClass.getESuperTypes().add(this.getSimpleWidgetTransformer());
		nullPropertyTransformerEClass.getESuperTypes().add(this.getSimplePropertyTransformer());
		childrenWidgetTransformerEClass.getESuperTypes().add(this.getSimpleWidgetTransformer());

		// Initialize classes and features; add operations and parameters
		initEClass(transformModelEClass, TransformModel.class, "TransformModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransformModel_Namespaces(), this.getNamespace(), null, "namespaces", null, 0, -1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformModel_WidgetTransformers(), this.getAbstractWidgetTransformer(), this.getAbstractWidgetTransformer_Model(), "widgetTransformers", null, 0, -1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformModel_DefaultNamespace(), this.getNamespace(), null, "defaultNamespace", null, 0, 1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformModel_PropertyTransformers(), this.getAbstractPropertyTransformer(), this.getAbstractPropertyTransformer_Model(), "propertyTransformers", null, 0, -1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformModel_DefaultWidgetLibrary(), theMetaModelPackage.getWidgetLibrary(), null, "defaultWidgetLibrary", null, 0, 1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformModel_EventTransformations(), this.getEventTransformations(), null, "eventTransformations", null, 0, 1, TransformModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namespaceEClass, Namespace.class, "Namespace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamespace_Uri(), ecorePackage.getEString(), "uri", null, 0, 1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamespace_Prefix(), ecorePackage.getEString(), "prefix", null, 0, 1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamespace_Origin(), ecorePackage.getEString(), "origin", "metaModel", 0, 1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractWidgetTransformerEClass, AbstractWidgetTransformer.class, "AbstractWidgetTransformer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractWidgetTransformer_Model(), this.getTransformModel(), this.getTransformModel_WidgetTransformers(), "model", null, 0, 1, AbstractWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleWidgetTransformerEClass, SimpleWidgetTransformer.class, "SimpleWidgetTransformer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleWidgetTransformer_WidgetType(), theMetaModelPackage.getWidgetType(), null, "widgetType", null, 0, 1, SimpleWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimpleWidgetTransformer_Namespace(), this.getNamespace(), null, "namespace", null, 0, 1, SimpleWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementNameWidgetTransformerEClass, ElementNameWidgetTransformer.class, "ElementNameWidgetTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElementNameWidgetTransformer_ElementName(), ecorePackage.getEString(), "elementName", null, 0, 1, ElementNameWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractPropertyTransformerEClass, AbstractPropertyTransformer.class, "AbstractPropertyTransformer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractPropertyTransformer_Model(), this.getTransformModel(), this.getTransformModel_PropertyTransformers(), "model", null, 0, 1, AbstractPropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simplePropertyTransformerEClass, SimplePropertyTransformer.class, "SimplePropertyTransformer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimplePropertyTransformer_PropertyType(), theMetaModelPackage.getPropertyType(), null, "propertyType", null, 0, 1, SimplePropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeNamePropertyTransformerEClass, AttributeNamePropertyTransformer.class, "AttributeNamePropertyTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeNamePropertyTransformer_AttributeName(), ecorePackage.getEString(), "attributeName", null, 0, 1, AttributeNamePropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementPropertyTransformerEClass, ElementPropertyTransformer.class, "ElementPropertyTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElementPropertyTransformer_ElementName(), ecorePackage.getEString(), "elementName", null, 0, 1, ElementPropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementPropertyTransformer_Namespace(), this.getNamespace(), null, "namespace", null, 0, 1, ElementPropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extraParentWidgetTransformerEClass, ExtraParentWidgetTransformer.class, "ExtraParentWidgetTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtraParentWidgetTransformer_ParentElementName(), ecorePackage.getEString(), "parentElementName", null, 0, 1, ExtraParentWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtraParentWidgetTransformer_OnlyAddIfRoot(), ecorePackage.getEBoolean(), "onlyAddIfRoot", null, 0, 1, ExtraParentWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtraParentWidgetTransformer_ParentNamespace(), this.getNamespace(), null, "parentNamespace", null, 0, 1, ExtraParentWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetTransformerEClass, WidgetTransformer.class, "WidgetTransformer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(propertyTransformerEClass, PropertyTransformer.class, "PropertyTransformer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericPropertyTransformerEClass, GenericPropertyTransformer.class, "GenericPropertyTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericPropertyTransformer_ImplementationClass(), ecorePackage.getEString(), "implementationClass", null, 0, 1, GenericPropertyTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericWidgetTransformerEClass, GenericWidgetTransformer.class, "GenericWidgetTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericWidgetTransformer_ImplementationClass(), ecorePackage.getEString(), "implementationClass", null, 0, 1, GenericWidgetTransformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nullWidgetTransformerEClass, NullWidgetTransformer.class, "NullWidgetTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nullPropertyTransformerEClass, NullPropertyTransformer.class, "NullPropertyTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eventTransformationsEClass, EventTransformations.class, "EventTransformations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventTransformations_Transformations(), this.getEventTransformation(), null, "transformations", null, 0, -1, EventTransformations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTransformationEClass, EventTransformation.class, "EventTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventTransformation_TypeName(), ecorePackage.getEString(), "typeName", null, 0, 1, EventTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventTransformation_EventType(), theMetaModelPackage.getEventType(), null, "eventType", null, 0, 1, EventTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(childrenWidgetTransformerEClass, ChildrenWidgetTransformer.class, "ChildrenWidgetTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(snippetTransformationEClass, SnippetTransformation.class, "SnippetTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSnippetTransformation_SnippetType(), theMetaModelPackage.getSnippetType(), null, "snippetType", null, 0, 1, SnippetTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(snippetTransformationsEClass, SnippetTransformations.class, "SnippetTransformations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSnippetTransformations_Transformations(), this.getSnippetTransformation(), null, "transformations", null, 0, -1, SnippetTransformations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TransformModelPackageImpl
