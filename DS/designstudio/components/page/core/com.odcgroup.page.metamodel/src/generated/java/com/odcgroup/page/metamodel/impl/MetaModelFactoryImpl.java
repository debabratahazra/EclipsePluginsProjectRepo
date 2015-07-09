/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

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
import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.ParameterTemplate;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.metamodel.WidgetEvent;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetSnippet;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetaModelFactoryImpl extends EFactoryImpl implements MetaModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * @return MetaModelFactory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MetaModelFactory init() {
		try {
			MetaModelFactory theMetaModelFactory = (MetaModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/page/metamodel"); 
			if (theMetaModelFactory != null) {
				return theMetaModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MetaModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModelFactoryImpl() {
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
			case MetaModelPackage.WIDGET_TYPE: return createWidgetType();
			case MetaModelPackage.PROPERTY_TYPE: return createPropertyType();
			case MetaModelPackage.META_MODEL: return createMetaModel();
			case MetaModelPackage.WIDGET_TEMPLATE: return createWidgetTemplate();
			case MetaModelPackage.ACCOUNTABILITY_RULE: return createAccountabilityRule();
			case MetaModelPackage.WIDGET_LIBRARY: return createWidgetLibrary();
			case MetaModelPackage.ACCOUNTABILITY: return createAccountability();
			case MetaModelPackage.DATA_TYPE: return createDataType();
			case MetaModelPackage.DATA_VALUE: return createDataValue();
			case MetaModelPackage.PROPERTY_TEMPLATE: return createPropertyTemplate();
			case MetaModelPackage.DATA_TYPES: return createDataTypes();
			case MetaModelPackage.INCLUDEABILITY_RULE: return createIncludeabilityRule();
			case MetaModelPackage.EVENT_MODEL: return createEventModel();
			case MetaModelPackage.WIDGET_EVENT: return createWidgetEvent();
			case MetaModelPackage.FUNCTION_TYPE: return createFunctionType();
			case MetaModelPackage.PARAMETER_TYPE: return createParameterType();
			case MetaModelPackage.EVENT_TEMPLATE: return createEventTemplate();
			case MetaModelPackage.PARAMETER_TEMPLATE: return createParameterTemplate();
			case MetaModelPackage.EVENT_TYPE: return createEventType();
			case MetaModelPackage.SNIPPET_TYPE: return createSnippetType();
			case MetaModelPackage.SNIPPET_MODEL: return createSnippetModel();
			case MetaModelPackage.WIDGET_SNIPPET: return createWidgetSnippet();
			case MetaModelPackage.EVENT_SNIPPET: return createEventSnippet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case MetaModelPackage.INCLUSION_TYPE:
				return createInclusionTypeFromString(eDataType, initialValue);
			case MetaModelPackage.PROPERTY_CATEGORY:
				return createPropertyCategoryFromString(eDataType, initialValue);
			case MetaModelPackage.TRANSLATION_SUPPORT:
				return createTranslationSupportFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case MetaModelPackage.INCLUSION_TYPE:
				return convertInclusionTypeToString(eDataType, instanceValue);
			case MetaModelPackage.PROPERTY_CATEGORY:
				return convertPropertyCategoryToString(eDataType, instanceValue);
			case MetaModelPackage.TRANSLATION_SUPPORT:
				return convertTranslationSupportToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType createWidgetType() {
		WidgetTypeImpl widgetType = new WidgetTypeImpl();
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyType() {
		PropertyTypeImpl propertyType = new PropertyTypeImpl();
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetTemplate createWidgetTemplate() {
		WidgetTemplateImpl widgetTemplate = new WidgetTemplateImpl();
		return widgetTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return AccountabilityRule
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccountabilityRule createAccountabilityRule() {
		AccountabilityRuleImpl accountabilityRule = new AccountabilityRuleImpl();
		return accountabilityRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetLibrary
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetLibrary createWidgetLibrary() {
		WidgetLibraryImpl widgetLibrary = new WidgetLibraryImpl();
		return widgetLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return AccountabilityImpl
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Accountability createAccountability() {
		AccountabilityImpl accountability = new AccountabilityImpl();
		return accountability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataType() {
		DataTypeImpl dataType = new DataTypeImpl();
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataValue
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataValue createDataValue() {
		DataValueImpl dataValue = new DataValueImpl();
		return dataValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MetaModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModel createMetaModel() {
		MetaModelImpl metaModel = new MetaModelImpl();
		return metaModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyTemplate createPropertyTemplate() {
		PropertyTemplateImpl propertyTemplate = new PropertyTemplateImpl();
		return propertyTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return DataTypes
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypes createDataTypes() {
		DataTypesImpl dataTypes = new DataTypesImpl();
		return dataTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return IncludeabilityRule
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncludeabilityRule createIncludeabilityRule() {
		IncludeabilityRuleImpl includeabilityRule = new IncludeabilityRuleImpl();
		return includeabilityRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventModel createEventModel() {
		EventModelImpl eventModel = new EventModelImpl();
		return eventModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetEvent
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetEvent createWidgetEvent() {
		WidgetEventImpl widgetEvent = new WidgetEventImpl();
		return widgetEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return FunctionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionType createFunctionType() {
		FunctionTypeImpl functionType = new FunctionTypeImpl();
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ParameterType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType createParameterType() {
		ParameterTypeImpl parameterType = new ParameterTypeImpl();
		return parameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventTemplate createEventTemplate() {
		EventTemplateImpl eventTemplate = new EventTemplateImpl();
		return eventTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return ParameterTemplate
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterTemplate createParameterTemplate() {
		ParameterTemplateImpl parameterTemplate = new ParameterTemplateImpl();
		return parameterTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EventType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType createEventType() {
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetType createSnippetType() {
		SnippetTypeImpl snippetType = new SnippetTypeImpl();
		return snippetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetModel createSnippetModel() {
		SnippetModelImpl snippetModel = new SnippetModelImpl();
		return snippetModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetSnippet createWidgetSnippet() {
		WidgetSnippetImpl widgetSnippet = new WidgetSnippetImpl();
		return widgetSnippet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventSnippet createEventSnippet() {
		EventSnippetImpl eventSnippet = new EventSnippetImpl();
		return eventSnippet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param eDataType
	 * @param initialValue
	 * @return InclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InclusionType createInclusionTypeFromString(EDataType eDataType, String initialValue) {
		InclusionType result = InclusionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param eDataType
	 * @param instanceValue
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInclusionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param eDataType
	 * @param initialValue
	 * @return PropertyCategory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyCategory createPropertyCategoryFromString(EDataType eDataType, String initialValue) {
		PropertyCategory result = PropertyCategory.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param eDataType
	 * @param instanceValue
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyCategoryToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslationSupport createTranslationSupportFromString(EDataType eDataType, String initialValue) {
		TranslationSupport result = TranslationSupport.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTranslationSupportToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MetaModelPackage
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModelPackage getMetaModelPackage() {
		return (MetaModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MetaModelPackage
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MetaModelPackage getPackage() {
		return MetaModelPackage.eINSTANCE;
	}

} //MetaModelFactoryImpl
