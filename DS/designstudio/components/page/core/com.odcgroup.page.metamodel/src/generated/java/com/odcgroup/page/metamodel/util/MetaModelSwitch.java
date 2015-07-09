/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.util;

import com.odcgroup.page.metamodel.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * @param <T>
 * <!-- end-user-doc -->
 * @see com.odcgroup.page.metamodel.MetaModelPackage
 * @generated
 */
public class MetaModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MetaModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModelSwitch() {
		if (modelPackage == null) {
			modelPackage = MetaModelPackage.eINSTANCE;
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
			case MetaModelPackage.WIDGET_TYPE: {
				WidgetType widgetType = (WidgetType)theEObject;
				T result = caseWidgetType(widgetType);
				if (result == null) result = caseNamedType(widgetType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.PROPERTY_TYPE: {
				PropertyType propertyType = (PropertyType)theEObject;
				T result = casePropertyType(propertyType);
				if (result == null) result = caseNamedType(propertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.META_MODEL: {
				MetaModel metaModel = (MetaModel)theEObject;
				T result = caseMetaModel(metaModel);
				if (result == null) result = caseNamedType(metaModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.WIDGET_TEMPLATE: {
				WidgetTemplate widgetTemplate = (WidgetTemplate)theEObject;
				T result = caseWidgetTemplate(widgetTemplate);
				if (result == null) result = caseNamedType(widgetTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.ACCOUNTABILITY_RULE: {
				AccountabilityRule accountabilityRule = (AccountabilityRule)theEObject;
				T result = caseAccountabilityRule(accountabilityRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.WIDGET_LIBRARY: {
				WidgetLibrary widgetLibrary = (WidgetLibrary)theEObject;
				T result = caseWidgetLibrary(widgetLibrary);
				if (result == null) result = caseNamedType(widgetLibrary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.ACCOUNTABILITY: {
				Accountability accountability = (Accountability)theEObject;
				T result = caseAccountability(accountability);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.PROPERTY_VALUE_CONVERTER: {
				PropertyValueConverter propertyValueConverter = (PropertyValueConverter)theEObject;
				T result = casePropertyValueConverter(propertyValueConverter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = caseNamedType(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.NAMED_TYPE: {
				NamedType namedType = (NamedType)theEObject;
				T result = caseNamedType(namedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.DATA_VALUE: {
				DataValue dataValue = (DataValue)theEObject;
				T result = caseDataValue(dataValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.PROPERTY_TEMPLATE: {
				PropertyTemplate propertyTemplate = (PropertyTemplate)theEObject;
				T result = casePropertyTemplate(propertyTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.DATA_TYPES: {
				DataTypes dataTypes = (DataTypes)theEObject;
				T result = caseDataTypes(dataTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.INCLUDEABILITY_RULE: {
				IncludeabilityRule includeabilityRule = (IncludeabilityRule)theEObject;
				T result = caseIncludeabilityRule(includeabilityRule);
				if (result == null) result = caseAccountabilityRule(includeabilityRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.EVENT_MODEL: {
				EventModel eventModel = (EventModel)theEObject;
				T result = caseEventModel(eventModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.WIDGET_EVENT: {
				WidgetEvent widgetEvent = (WidgetEvent)theEObject;
				T result = caseWidgetEvent(widgetEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.FUNCTION_TYPE: {
				FunctionType functionType = (FunctionType)theEObject;
				T result = caseFunctionType(functionType);
				if (result == null) result = caseNamedType(functionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.PARAMETER_TYPE: {
				ParameterType parameterType = (ParameterType)theEObject;
				T result = caseParameterType(parameterType);
				if (result == null) result = caseNamedType(parameterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.EVENT_TEMPLATE: {
				EventTemplate eventTemplate = (EventTemplate)theEObject;
				T result = caseEventTemplate(eventTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.PARAMETER_TEMPLATE: {
				ParameterTemplate parameterTemplate = (ParameterTemplate)theEObject;
				T result = caseParameterTemplate(parameterTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.EVENT_TYPE: {
				EventType eventType = (EventType)theEObject;
				T result = caseEventType(eventType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.SNIPPET_TYPE: {
				SnippetType snippetType = (SnippetType)theEObject;
				T result = caseSnippetType(snippetType);
				if (result == null) result = caseNamedType(snippetType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.SNIPPET_MODEL: {
				SnippetModel snippetModel = (SnippetModel)theEObject;
				T result = caseSnippetModel(snippetModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.WIDGET_SNIPPET: {
				WidgetSnippet widgetSnippet = (WidgetSnippet)theEObject;
				T result = caseWidgetSnippet(widgetSnippet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetaModelPackage.EVENT_SNIPPET: {
				EventSnippet eventSnippet = (EventSnippet)theEObject;
				T result = caseEventSnippet(eventSnippet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetType(WidgetType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyType(PropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetTemplate(WidgetTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Accountability Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Accountability Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccountabilityRule(AccountabilityRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetLibrary(WidgetLibrary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Accountability</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Accountability</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccountability(Accountability object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Value Converter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Value Converter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyValueConverter(PropertyValueConverter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedType(NamedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataValue(DataValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Meta Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Meta Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetaModel(MetaModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyTemplate(PropertyTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataTypes(DataTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Includeability Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Includeability Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncludeabilityRule(IncludeabilityRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventModel(EventModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetEvent(WidgetEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionType(FunctionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterType(ParameterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventTemplate(EventTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterTemplate(ParameterTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventType(EventType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Snippet Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Snippet Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSnippetType(SnippetType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Snippet Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Snippet Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSnippetModel(SnippetModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Snippet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Snippet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetSnippet(WidgetSnippet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Snippet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Snippet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventSnippet(EventSnippet object) {
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

} //MetaModelSwitch
