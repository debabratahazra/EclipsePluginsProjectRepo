/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.util;

import com.odcgroup.page.metamodel.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.Accountability;
import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataTypes;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventModel;
import com.odcgroup.page.metamodel.EventSnippet;
import com.odcgroup.page.metamodel.EventTemplate;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.IncludeabilityRule;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.metamodel.ParameterTemplate;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyValueConverter;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.WidgetEvent;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetSnippet;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.metamodel.MetaModelPackage
 * @generated
 */
public class MetaModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MetaModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MetaModelPackage.eINSTANCE;
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
	protected MetaModelSwitch<Adapter> modelSwitch =
		new MetaModelSwitch<Adapter>() {
			@Override
			public Adapter caseWidgetType(WidgetType object) {
				return createWidgetTypeAdapter();
			}
			@Override
			public Adapter casePropertyType(PropertyType object) {
				return createPropertyTypeAdapter();
			}
			@Override
			public Adapter caseMetaModel(MetaModel object) {
				return createMetaModelAdapter();
			}
			@Override
			public Adapter caseWidgetTemplate(WidgetTemplate object) {
				return createWidgetTemplateAdapter();
			}
			@Override
			public Adapter caseAccountabilityRule(AccountabilityRule object) {
				return createAccountabilityRuleAdapter();
			}
			@Override
			public Adapter caseWidgetLibrary(WidgetLibrary object) {
				return createWidgetLibraryAdapter();
			}
			@Override
			public Adapter caseAccountability(Accountability object) {
				return createAccountabilityAdapter();
			}
			@Override
			public Adapter casePropertyValueConverter(PropertyValueConverter object) {
				return createPropertyValueConverterAdapter();
			}
			@Override
			public Adapter caseDataType(DataType object) {
				return createDataTypeAdapter();
			}
			@Override
			public Adapter caseNamedType(NamedType object) {
				return createNamedTypeAdapter();
			}
			@Override
			public Adapter caseDataValue(DataValue object) {
				return createDataValueAdapter();
			}
			@Override
			public Adapter casePropertyTemplate(PropertyTemplate object) {
				return createPropertyTemplateAdapter();
			}
			@Override
			public Adapter caseDataTypes(DataTypes object) {
				return createDataTypesAdapter();
			}
			@Override
			public Adapter caseIncludeabilityRule(IncludeabilityRule object) {
				return createIncludeabilityRuleAdapter();
			}
			@Override
			public Adapter caseEventModel(EventModel object) {
				return createEventModelAdapter();
			}
			@Override
			public Adapter caseWidgetEvent(WidgetEvent object) {
				return createWidgetEventAdapter();
			}
			@Override
			public Adapter caseFunctionType(FunctionType object) {
				return createFunctionTypeAdapter();
			}
			@Override
			public Adapter caseParameterType(ParameterType object) {
				return createParameterTypeAdapter();
			}
			@Override
			public Adapter caseEventTemplate(EventTemplate object) {
				return createEventTemplateAdapter();
			}
			@Override
			public Adapter caseParameterTemplate(ParameterTemplate object) {
				return createParameterTemplateAdapter();
			}
			@Override
			public Adapter caseEventType(EventType object) {
				return createEventTypeAdapter();
			}
			@Override
			public Adapter caseSnippetType(SnippetType object) {
				return createSnippetTypeAdapter();
			}
			@Override
			public Adapter caseSnippetModel(SnippetModel object) {
				return createSnippetModelAdapter();
			}
			@Override
			public Adapter caseWidgetSnippet(WidgetSnippet object) {
				return createWidgetSnippetAdapter();
			}
			@Override
			public Adapter caseEventSnippet(EventSnippet object) {
				return createEventSnippetAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.WidgetType <em>Widget Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.WidgetType
	 * @generated
	 */
	public Adapter createWidgetTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.PropertyType
	 * @generated
	 */
	public Adapter createPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.WidgetTemplate <em>Widget Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.WidgetTemplate
	 * @generated
	 */
	public Adapter createWidgetTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.AccountabilityRule <em>Accountability Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.AccountabilityRule
	 * @generated
	 */
	public Adapter createAccountabilityRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.WidgetLibrary <em>Widget Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.WidgetLibrary
	 * @generated
	 */
	public Adapter createWidgetLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.Accountability <em>Accountability</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.Accountability
	 * @generated
	 */
	public Adapter createAccountabilityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.PropertyValueConverter <em>Property Value Converter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.PropertyValueConverter
	 * @generated
	 */
	public Adapter createPropertyValueConverterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.DataType
	 * @generated
	 */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.NamedType <em>Named Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.NamedType
	 * @generated
	 */
	public Adapter createNamedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.DataValue <em>Data Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.DataValue
	 * @generated
	 */
	public Adapter createDataValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.MetaModel <em>Meta Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.MetaModel
	 * @generated
	 */
	public Adapter createMetaModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.PropertyTemplate <em>Property Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.PropertyTemplate
	 * @generated
	 */
	public Adapter createPropertyTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.DataTypes <em>Data Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.DataTypes
	 * @generated
	 */
	public Adapter createDataTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.IncludeabilityRule <em>Includeability Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.IncludeabilityRule
	 * @generated
	 */
	public Adapter createIncludeabilityRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.EventModel <em>Event Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.EventModel
	 * @generated
	 */
	public Adapter createEventModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.WidgetEvent <em>Widget Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.WidgetEvent
	 * @generated
	 */
	public Adapter createWidgetEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.FunctionType
	 * @generated
	 */
	public Adapter createFunctionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.ParameterType
	 * @generated
	 */
	public Adapter createParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.EventTemplate <em>Event Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.EventTemplate
	 * @generated
	 */
	public Adapter createEventTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.ParameterTemplate <em>Parameter Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.ParameterTemplate
	 * @generated
	 */
	public Adapter createParameterTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.EventType
	 * @generated
	 */
	public Adapter createEventTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.SnippetType <em>Snippet Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.SnippetType
	 * @generated
	 */
	public Adapter createSnippetTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.SnippetModel <em>Snippet Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.SnippetModel
	 * @generated
	 */
	public Adapter createSnippetModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.WidgetSnippet <em>Widget Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.WidgetSnippet
	 * @generated
	 */
	public Adapter createWidgetSnippetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.page.metamodel.EventSnippet <em>Event Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.page.metamodel.EventSnippet
	 * @generated
	 */
	public Adapter createEventSnippetAdapter() {
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

} //MetaModelAdapterFactory
