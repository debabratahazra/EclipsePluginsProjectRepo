/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.metamodel.ParameterTemplate;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyTemplate;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyValueConverter;
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
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetaModelPackageImpl extends EPackageImpl implements MetaModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accountabilityRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accountabilityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyValueConverterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includeabilityRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass snippetTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass snippetModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetSnippetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventSnippetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum inclusionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyCategoryEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum translationSupportEEnum = null;

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MetaModelPackageImpl() {
		super(eNS_URI, MetaModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MetaModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * @return MetaModelPackage
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MetaModelPackage init() {
		if (isInited) return (MetaModelPackage)EPackage.Registry.INSTANCE.getEPackage(MetaModelPackage.eNS_URI);

		// Obtain or create and register package
		MetaModelPackageImpl theMetaModelPackage = (MetaModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MetaModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MetaModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMetaModelPackage.createPackageContents();

		// Initialize created meta-data
		theMetaModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMetaModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MetaModelPackage.eNS_URI, theMetaModelPackage);
		return theMetaModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetType() {
		return widgetTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetType_Parent() {
		return (EReference)widgetTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetType_PropertyTypes() {
		return (EReference)widgetTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetType_Library() {
		return (EReference)widgetTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetType_ExcludedPropertyTypes() {
		return (EReference)widgetTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_DisplayName() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_PropertyFilterClass() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_Derivable() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_StrictAccountability() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_DomainWidget() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_CanDropDomainElement() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidgetType_Richtext() {
		return (EAttribute)widgetTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyType() {
		return propertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_DefaultValue() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyType_DataType() {
		return (EReference)propertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyType_Library() {
		return (EReference)propertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_Category() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_DisplayName() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * @return EAttribute
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyType_SourceAdapter() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_SubCategory() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(6);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_VisibleInDomain() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(7);
	}

				/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetTemplate() {
		return widgetTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetTemplate_Type() {
		return (EReference)widgetTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetTemplate_Contents() {
		return (EReference)widgetTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetTemplate_PropertyTemplates() {
		return (EReference)widgetTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetTemplate_EventTemplates() {
		return (EReference)widgetTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAccountabilityRule() {
		return accountabilityRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAccountabilityRule_Child() {
		return (EReference)accountabilityRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAccountabilityRule_Parent() {
		return (EReference)accountabilityRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccountabilityRule_MinOccurs() {
		return (EAttribute)accountabilityRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccountabilityRule_MaxOccurs() {
		return (EAttribute)accountabilityRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccountabilityRule_Name() {
		return (EAttribute)accountabilityRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetLibrary() {
		return widgetLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetLibrary_WidgetTypes() {
		return (EReference)widgetLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetLibrary_PropertyTypes() {
		return (EReference)widgetLibraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetLibrary_WidgetTemplates() {
		return (EReference)widgetLibraryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAccountability() {
		return accountabilityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAccountability_Rules() {
		return (EReference)accountabilityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyValueConverter() {
		return propertyValueConverterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataType_Values() {
		return (EReference)dataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_EditorName() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_ValidatorName() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_ConverterName() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataType_Editable() {
		return (EAttribute)dataTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedType() {
		return namedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedType_Name() {
		return (EAttribute)namedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedType_Description() {
		return (EAttribute)namedTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedType_TranslationSupport() {
		return (EAttribute)namedTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedType_PropertiesHelpID() {
		return (EAttribute)namedTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedType_ConceptHelpID() {
		return (EAttribute)namedTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataValue() {
		return dataValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataValue_Value() {
		return (EAttribute)dataValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataValue_Ordinal() {
		return (EAttribute)dataValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * @return EAttribute
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDataValue_DisplayName() {
		return (EAttribute)dataValueEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetaModel() {
		return metaModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_WidgetLibraries() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetaModel_Version() {
		return (EAttribute)metaModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_DataTypes() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_EventModel() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_SnippetModel() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_Containability() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaModel_Includeability() {
		return (EReference)metaModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyTemplate() {
		return propertyTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyTemplate_Value() {
		return (EAttribute)propertyTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyTemplate_Readonly() {
		return (EAttribute)propertyTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyTemplate_Type() {
		return (EReference)propertyTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataTypes() {
		return dataTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataTypes_Types() {
		return (EReference)dataTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncludeabilityRule() {
		return includeabilityRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIncludeabilityRule_InclusionType() {
		return (EAttribute)includeabilityRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIncludeabilityRule_LimitedAccountability() {
		return (EAttribute)includeabilityRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventModel() {
		return eventModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventModel_Functions() {
		return (EReference)eventModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventModel_Events() {
		return (EReference)eventModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventModel_EventTypes() {
		return (EReference)eventModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetEvent() {
		return widgetEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetEvent_WidgetType() {
		return (EReference)widgetEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetEvent_EventTypes() {
		return (EReference)widgetEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionType() {
		return functionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionType_Parameters() {
		return (EReference)functionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionType_EventTypes() {
		return (EReference)functionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionType_AllowUserParameters() {
		return (EAttribute)functionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterType() {
		return parameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterType_DefaultValue() {
		return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterType_Type() {
		return (EReference)parameterTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventTemplate() {
		return eventTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventTemplate_ParameterTemplates() {
		return (EReference)eventTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventTemplate_FunctionType() {
		return (EReference)eventTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventTemplate_EventType() {
		return (EAttribute)eventTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventTemplate_Nature() {
		return (EAttribute)eventTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterTemplate() {
		return parameterTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EReference
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterTemplate_Type() {
		return (EReference)parameterTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterTemplate_Value() {
		return (EAttribute)parameterTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterTemplate_UserDefined() {
		return (EAttribute)parameterTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterTemplate_Name() {
		return (EAttribute)parameterTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getInclusionType() {
		return inclusionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPropertyCategory() {
		return propertyCategoryEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTranslationSupport() {
		return translationSupportEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EEnum
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_Name() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EAttribute
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventType_PropertyTypes() {
		return (EReference)eventTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSnippetType() {
		return snippetTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetType_Parent() {
		return (EReference)snippetTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetType_PropertyTypes() {
		return (EReference)snippetTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSnippetModel() {
		return snippetModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetModel_Snippets() {
		return (EReference)snippetModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetModel_PropertyTypes() {
		return (EReference)snippetModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetModel_Widgets() {
		return (EReference)snippetModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSnippetModel_Events() {
		return (EReference)snippetModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidgetSnippet() {
		return widgetSnippetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetSnippet_WidgetType() {
		return (EReference)widgetSnippetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidgetSnippet_Snippets() {
		return (EReference)widgetSnippetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventSnippet() {
		return eventSnippetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventSnippet_Snippets() {
		return (EReference)eventSnippetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return MetaModelFactory
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaModelFactory getMetaModelFactory() {
		return (MetaModelFactory)getEFactoryInstance();
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
		widgetTypeEClass = createEClass(WIDGET_TYPE);
		createEReference(widgetTypeEClass, WIDGET_TYPE__PARENT);
		createEReference(widgetTypeEClass, WIDGET_TYPE__PROPERTY_TYPES);
		createEReference(widgetTypeEClass, WIDGET_TYPE__LIBRARY);
		createEReference(widgetTypeEClass, WIDGET_TYPE__EXCLUDED_PROPERTY_TYPES);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__DISPLAY_NAME);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__PROPERTY_FILTER_CLASS);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__DERIVABLE);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__STRICT_ACCOUNTABILITY);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__DOMAIN_WIDGET);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__CAN_DROP_DOMAIN_ELEMENT);
		createEAttribute(widgetTypeEClass, WIDGET_TYPE__RICHTEXT);

		propertyTypeEClass = createEClass(PROPERTY_TYPE);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__DEFAULT_VALUE);
		createEReference(propertyTypeEClass, PROPERTY_TYPE__DATA_TYPE);
		createEReference(propertyTypeEClass, PROPERTY_TYPE__LIBRARY);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__CATEGORY);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__DISPLAY_NAME);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__SOURCE_ADAPTER);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__SUB_CATEGORY);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__VISIBLE_IN_DOMAIN);

		metaModelEClass = createEClass(META_MODEL);
		createEReference(metaModelEClass, META_MODEL__WIDGET_LIBRARIES);
		createEAttribute(metaModelEClass, META_MODEL__VERSION);
		createEReference(metaModelEClass, META_MODEL__CONTAINABILITY);
		createEReference(metaModelEClass, META_MODEL__INCLUDEABILITY);
		createEReference(metaModelEClass, META_MODEL__DATA_TYPES);
		createEReference(metaModelEClass, META_MODEL__EVENT_MODEL);
		createEReference(metaModelEClass, META_MODEL__SNIPPET_MODEL);

		widgetTemplateEClass = createEClass(WIDGET_TEMPLATE);
		createEReference(widgetTemplateEClass, WIDGET_TEMPLATE__TYPE);
		createEReference(widgetTemplateEClass, WIDGET_TEMPLATE__CONTENTS);
		createEReference(widgetTemplateEClass, WIDGET_TEMPLATE__PROPERTY_TEMPLATES);
		createEReference(widgetTemplateEClass, WIDGET_TEMPLATE__EVENT_TEMPLATES);

		accountabilityRuleEClass = createEClass(ACCOUNTABILITY_RULE);
		createEReference(accountabilityRuleEClass, ACCOUNTABILITY_RULE__CHILD);
		createEReference(accountabilityRuleEClass, ACCOUNTABILITY_RULE__PARENT);
		createEAttribute(accountabilityRuleEClass, ACCOUNTABILITY_RULE__MIN_OCCURS);
		createEAttribute(accountabilityRuleEClass, ACCOUNTABILITY_RULE__MAX_OCCURS);
		createEAttribute(accountabilityRuleEClass, ACCOUNTABILITY_RULE__NAME);

		widgetLibraryEClass = createEClass(WIDGET_LIBRARY);
		createEReference(widgetLibraryEClass, WIDGET_LIBRARY__WIDGET_TYPES);
		createEReference(widgetLibraryEClass, WIDGET_LIBRARY__PROPERTY_TYPES);
		createEReference(widgetLibraryEClass, WIDGET_LIBRARY__WIDGET_TEMPLATES);

		accountabilityEClass = createEClass(ACCOUNTABILITY);
		createEReference(accountabilityEClass, ACCOUNTABILITY__RULES);

		propertyValueConverterEClass = createEClass(PROPERTY_VALUE_CONVERTER);

		dataTypeEClass = createEClass(DATA_TYPE);
		createEReference(dataTypeEClass, DATA_TYPE__VALUES);
		createEAttribute(dataTypeEClass, DATA_TYPE__EDITOR_NAME);
		createEAttribute(dataTypeEClass, DATA_TYPE__VALIDATOR_NAME);
		createEAttribute(dataTypeEClass, DATA_TYPE__CONVERTER_NAME);
		createEAttribute(dataTypeEClass, DATA_TYPE__EDITABLE);

		namedTypeEClass = createEClass(NAMED_TYPE);
		createEAttribute(namedTypeEClass, NAMED_TYPE__NAME);
		createEAttribute(namedTypeEClass, NAMED_TYPE__DESCRIPTION);
		createEAttribute(namedTypeEClass, NAMED_TYPE__TRANSLATION_SUPPORT);
		createEAttribute(namedTypeEClass, NAMED_TYPE__PROPERTIES_HELP_ID);
		createEAttribute(namedTypeEClass, NAMED_TYPE__CONCEPT_HELP_ID);

		dataValueEClass = createEClass(DATA_VALUE);
		createEAttribute(dataValueEClass, DATA_VALUE__VALUE);
		createEAttribute(dataValueEClass, DATA_VALUE__ORDINAL);
		createEAttribute(dataValueEClass, DATA_VALUE__DISPLAY_NAME);

		propertyTemplateEClass = createEClass(PROPERTY_TEMPLATE);
		createEAttribute(propertyTemplateEClass, PROPERTY_TEMPLATE__VALUE);
		createEAttribute(propertyTemplateEClass, PROPERTY_TEMPLATE__READONLY);
		createEReference(propertyTemplateEClass, PROPERTY_TEMPLATE__TYPE);

		dataTypesEClass = createEClass(DATA_TYPES);
		createEReference(dataTypesEClass, DATA_TYPES__TYPES);

		includeabilityRuleEClass = createEClass(INCLUDEABILITY_RULE);
		createEAttribute(includeabilityRuleEClass, INCLUDEABILITY_RULE__INCLUSION_TYPE);
		createEAttribute(includeabilityRuleEClass, INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY);

		eventModelEClass = createEClass(EVENT_MODEL);
		createEReference(eventModelEClass, EVENT_MODEL__FUNCTIONS);
		createEReference(eventModelEClass, EVENT_MODEL__EVENTS);
		createEReference(eventModelEClass, EVENT_MODEL__EVENT_TYPES);

		widgetEventEClass = createEClass(WIDGET_EVENT);
		createEReference(widgetEventEClass, WIDGET_EVENT__WIDGET_TYPE);
		createEReference(widgetEventEClass, WIDGET_EVENT__EVENT_TYPES);

		functionTypeEClass = createEClass(FUNCTION_TYPE);
		createEReference(functionTypeEClass, FUNCTION_TYPE__PARAMETERS);
		createEReference(functionTypeEClass, FUNCTION_TYPE__EVENT_TYPES);
		createEAttribute(functionTypeEClass, FUNCTION_TYPE__ALLOW_USER_PARAMETERS);

		parameterTypeEClass = createEClass(PARAMETER_TYPE);
		createEAttribute(parameterTypeEClass, PARAMETER_TYPE__DEFAULT_VALUE);
		createEReference(parameterTypeEClass, PARAMETER_TYPE__TYPE);

		eventTemplateEClass = createEClass(EVENT_TEMPLATE);
		createEReference(eventTemplateEClass, EVENT_TEMPLATE__PARAMETER_TEMPLATES);
		createEReference(eventTemplateEClass, EVENT_TEMPLATE__FUNCTION_TYPE);
		createEAttribute(eventTemplateEClass, EVENT_TEMPLATE__EVENT_TYPE);
		createEAttribute(eventTemplateEClass, EVENT_TEMPLATE__NATURE);

		parameterTemplateEClass = createEClass(PARAMETER_TEMPLATE);
		createEReference(parameterTemplateEClass, PARAMETER_TEMPLATE__TYPE);
		createEAttribute(parameterTemplateEClass, PARAMETER_TEMPLATE__VALUE);
		createEAttribute(parameterTemplateEClass, PARAMETER_TEMPLATE__USER_DEFINED);
		createEAttribute(parameterTemplateEClass, PARAMETER_TEMPLATE__NAME);

		eventTypeEClass = createEClass(EVENT_TYPE);
		createEAttribute(eventTypeEClass, EVENT_TYPE__NAME);
		createEReference(eventTypeEClass, EVENT_TYPE__PROPERTY_TYPES);

		snippetTypeEClass = createEClass(SNIPPET_TYPE);
		createEReference(snippetTypeEClass, SNIPPET_TYPE__PARENT);
		createEReference(snippetTypeEClass, SNIPPET_TYPE__PROPERTY_TYPES);

		snippetModelEClass = createEClass(SNIPPET_MODEL);
		createEReference(snippetModelEClass, SNIPPET_MODEL__SNIPPETS);
		createEReference(snippetModelEClass, SNIPPET_MODEL__PROPERTY_TYPES);
		createEReference(snippetModelEClass, SNIPPET_MODEL__WIDGETS);
		createEReference(snippetModelEClass, SNIPPET_MODEL__EVENTS);

		widgetSnippetEClass = createEClass(WIDGET_SNIPPET);
		createEReference(widgetSnippetEClass, WIDGET_SNIPPET__WIDGET_TYPE);
		createEReference(widgetSnippetEClass, WIDGET_SNIPPET__SNIPPETS);

		eventSnippetEClass = createEClass(EVENT_SNIPPET);
		createEReference(eventSnippetEClass, EVENT_SNIPPET__SNIPPETS);

		// Create enums
		inclusionTypeEEnum = createEEnum(INCLUSION_TYPE);
		propertyCategoryEEnum = createEEnum(PROPERTY_CATEGORY);
		translationSupportEEnum = createEEnum(TRANSLATION_SUPPORT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		widgetTypeEClass.getESuperTypes().add(this.getNamedType());
		propertyTypeEClass.getESuperTypes().add(this.getNamedType());
		metaModelEClass.getESuperTypes().add(this.getNamedType());
		widgetTemplateEClass.getESuperTypes().add(this.getNamedType());
		widgetLibraryEClass.getESuperTypes().add(this.getNamedType());
		dataTypeEClass.getESuperTypes().add(this.getNamedType());
		includeabilityRuleEClass.getESuperTypes().add(this.getAccountabilityRule());
		functionTypeEClass.getESuperTypes().add(this.getNamedType());
		parameterTypeEClass.getESuperTypes().add(this.getNamedType());
		snippetTypeEClass.getESuperTypes().add(this.getNamedType());

		// Initialize classes and features; add operations and parameters
		initEClass(widgetTypeEClass, WidgetType.class, "WidgetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidgetType_Parent(), this.getWidgetType(), null, "parent", null, 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetType_PropertyTypes(), this.getPropertyType(), null, "propertyTypes", null, 0, -1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetType_Library(), this.getWidgetLibrary(), this.getWidgetLibrary_WidgetTypes(), "library", null, 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetType_ExcludedPropertyTypes(), this.getPropertyType(), null, "excludedPropertyTypes", null, 0, -1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_PropertyFilterClass(), ecorePackage.getEString(), "propertyFilterClass", null, 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_Derivable(), ecorePackage.getEBoolean(), "derivable", "false", 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_StrictAccountability(), ecorePackage.getEBoolean(), "strictAccountability", "false", 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_DomainWidget(), ecorePackage.getEBoolean(), "domainWidget", "false", 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_CanDropDomainElement(), ecorePackage.getEBoolean(), "canDropDomainElement", "false", 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidgetType_Richtext(), ecorePackage.getEBoolean(), "richtext", "false", 0, 1, WidgetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyTypeEClass, PropertyType.class, "PropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyType_DefaultValue(), ecorePackage.getEString(), "defaultValue", "", 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyType_DataType(), this.getDataType(), null, "dataType", null, 1, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyType_Library(), this.getWidgetLibrary(), this.getWidgetLibrary_PropertyTypes(), "library", null, 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyType_Category(), this.getPropertyCategory(), "category", "None", 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyType_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyType_SourceAdapter(), ecorePackage.getEString(), "sourceAdapter", null, 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyType_SubCategory(), ecorePackage.getEString(), "subCategory", null, 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyType_VisibleInDomain(), ecorePackage.getEBoolean(), "visibleInDomain", "false", 0, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaModelEClass, MetaModel.class, "MetaModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetaModel_WidgetLibraries(), this.getWidgetLibrary(), null, "widgetLibraries", null, 0, -1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetaModel_Version(), ecorePackage.getEString(), "version", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaModel_Containability(), this.getAccountability(), null, "containability", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaModel_Includeability(), this.getAccountability(), null, "includeability", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaModel_DataTypes(), this.getDataTypes(), null, "dataTypes", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaModel_EventModel(), this.getEventModel(), null, "eventModel", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaModel_SnippetModel(), this.getSnippetModel(), null, "snippetModel", null, 0, 1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetTemplateEClass, WidgetTemplate.class, "WidgetTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidgetTemplate_Type(), this.getWidgetType(), null, "type", null, 0, 1, WidgetTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetTemplate_Contents(), this.getWidgetTemplate(), null, "contents", null, 0, -1, WidgetTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetTemplate_PropertyTemplates(), this.getPropertyTemplate(), null, "propertyTemplates", null, 0, -1, WidgetTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetTemplate_EventTemplates(), this.getEventTemplate(), null, "eventTemplates", null, 0, -1, WidgetTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(accountabilityRuleEClass, AccountabilityRule.class, "AccountabilityRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAccountabilityRule_Child(), this.getWidgetType(), null, "child", null, 1, 1, AccountabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAccountabilityRule_Parent(), this.getWidgetType(), null, "parent", null, 1, 1, AccountabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountabilityRule_MinOccurs(), ecorePackage.getEInt(), "minOccurs", "0", 0, 1, AccountabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountabilityRule_MaxOccurs(), ecorePackage.getEInt(), "maxOccurs", "-1", 0, 1, AccountabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountabilityRule_Name(), ecorePackage.getEString(), "name", null, 0, 1, AccountabilityRule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetLibraryEClass, WidgetLibrary.class, "WidgetLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidgetLibrary_WidgetTypes(), this.getWidgetType(), this.getWidgetType_Library(), "widgetTypes", null, 0, -1, WidgetLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetLibrary_PropertyTypes(), this.getPropertyType(), this.getPropertyType_Library(), "propertyTypes", null, 0, -1, WidgetLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetLibrary_WidgetTemplates(), this.getWidgetTemplate(), null, "widgetTemplates", null, 0, -1, WidgetLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(accountabilityEClass, Accountability.class, "Accountability", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAccountability_Rules(), this.getAccountabilityRule(), null, "rules", null, 0, -1, Accountability.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyValueConverterEClass, PropertyValueConverter.class, "PropertyValueConverter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(propertyValueConverterEClass, ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(propertyValueConverterEClass, ecorePackage.getEJavaObject(), "toObject", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(propertyValueConverterEClass, ecorePackage.getEString(), "toString", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(dataTypeEClass, DataType.class, "DataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDataType_Values(), this.getDataValue(), null, "values", null, 0, -1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataType_EditorName(), ecorePackage.getEString(), "editorName", "com.odcgroup.page.ui.properties.PropertyTextEditor", 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataType_ValidatorName(), ecorePackage.getEString(), "validatorName", null, 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataType_ConverterName(), ecorePackage.getEString(), "converterName", "com.odcgroup.page.model.converter.StringValueConverter", 0, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataType_Editable(), ecorePackage.getEBoolean(), "editable", "true", 1, 1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedTypeEClass, NamedType.class, "NamedType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedType_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedType_Description(), ecorePackage.getEString(), "description", null, 0, 1, NamedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedType_TranslationSupport(), this.getTranslationSupport(), "translationSupport", "NONE", 1, 1, NamedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedType_PropertiesHelpID(), ecorePackage.getEString(), "propertiesHelpID", null, 0, 1, NamedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedType_ConceptHelpID(), ecorePackage.getEString(), "ConceptHelpID", null, 0, 1, NamedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataValueEClass, DataValue.class, "DataValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataValue_Value(), ecorePackage.getEString(), "value", null, 1, 1, DataValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataValue_Ordinal(), ecorePackage.getEInt(), "ordinal", null, 1, 1, DataValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataValue_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, DataValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyTemplateEClass, PropertyTemplate.class, "PropertyTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyTemplate_Value(), ecorePackage.getEString(), "value", null, 0, 1, PropertyTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyTemplate_Readonly(), ecorePackage.getEBoolean(), "readonly", null, 0, 1, PropertyTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyTemplate_Type(), this.getPropertyType(), null, "type", null, 0, 1, PropertyTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataTypesEClass, DataTypes.class, "DataTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDataTypes_Types(), this.getDataType(), null, "types", null, 0, -1, DataTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(includeabilityRuleEClass, IncludeabilityRule.class, "IncludeabilityRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIncludeabilityRule_InclusionType(), this.getInclusionType(), "inclusionType", "XINCLUDE", 0, 1, IncludeabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIncludeabilityRule_LimitedAccountability(), ecorePackage.getEBoolean(), "limitedAccountability", "false", 0, 1, IncludeabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventModelEClass, EventModel.class, "EventModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventModel_Functions(), this.getFunctionType(), null, "functions", null, 0, -1, EventModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventModel_Events(), this.getWidgetEvent(), null, "events", null, 0, -1, EventModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventModel_EventTypes(), this.getEventType(), null, "eventTypes", null, 0, -1, EventModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetEventEClass, WidgetEvent.class, "WidgetEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidgetEvent_WidgetType(), this.getWidgetType(), null, "widgetType", null, 1, 1, WidgetEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetEvent_EventTypes(), this.getEventType(), null, "eventTypes", null, 0, -1, WidgetEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionTypeEClass, FunctionType.class, "FunctionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionType_Parameters(), this.getParameterType(), null, "parameters", null, 0, -1, FunctionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionType_EventTypes(), this.getEventType(), null, "eventTypes", null, 0, -1, FunctionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunctionType_AllowUserParameters(), ecorePackage.getEBoolean(), "allowUserParameters", "false", 0, 1, FunctionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameterType_DefaultValue(), ecorePackage.getEString(), "defaultValue", "", 0, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameterType_Type(), this.getDataType(), null, "type", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTemplateEClass, EventTemplate.class, "EventTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventTemplate_ParameterTemplates(), this.getParameterTemplate(), null, "parameterTemplates", null, 0, -1, EventTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventTemplate_FunctionType(), this.getFunctionType(), null, "functionType", null, 0, 1, EventTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventTemplate_EventType(), ecorePackage.getEString(), "eventType", null, 0, 1, EventTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventTemplate_Nature(), ecorePackage.getEString(), "nature", null, 0, 1, EventTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterTemplateEClass, ParameterTemplate.class, "ParameterTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterTemplate_Type(), this.getParameterType(), null, "type", null, 0, 1, ParameterTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterTemplate_Value(), ecorePackage.getEString(), "value", null, 0, 1, ParameterTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterTemplate_UserDefined(), ecorePackage.getEBoolean(), "userDefined", null, 0, 1, ParameterTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterTemplate_Name(), ecorePackage.getEString(), "name", null, 0, 1, ParameterTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventType_Name(), ecorePackage.getEString(), "name", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventType_PropertyTypes(), this.getPropertyType(), null, "propertyTypes", null, 0, -1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(snippetTypeEClass, SnippetType.class, "SnippetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSnippetType_Parent(), this.getSnippetType(), null, "parent", null, 0, 1, SnippetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSnippetType_PropertyTypes(), this.getPropertyType(), null, "propertyTypes", null, 0, -1, SnippetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(snippetModelEClass, SnippetModel.class, "SnippetModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSnippetModel_Snippets(), this.getSnippetType(), null, "snippets", null, 0, -1, SnippetModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSnippetModel_PropertyTypes(), this.getPropertyType(), null, "propertyTypes", null, 0, -1, SnippetModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSnippetModel_Widgets(), this.getWidgetSnippet(), null, "widgets", null, 0, -1, SnippetModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSnippetModel_Events(), this.getEventSnippet(), null, "events", null, 0, -1, SnippetModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widgetSnippetEClass, WidgetSnippet.class, "WidgetSnippet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidgetSnippet_WidgetType(), this.getWidgetType(), null, "widgetType", null, 1, 1, WidgetSnippet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWidgetSnippet_Snippets(), this.getSnippetType(), null, "snippets", null, 0, -1, WidgetSnippet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventSnippetEClass, EventSnippet.class, "EventSnippet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventSnippet_Snippets(), this.getSnippetType(), null, "snippets", null, 0, -1, EventSnippet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(inclusionTypeEEnum, InclusionType.class, "InclusionType");
		addEEnumLiteral(inclusionTypeEEnum, InclusionType.CINCLUDE_LITERAL);
		addEEnumLiteral(inclusionTypeEEnum, InclusionType.XINCLUDE_LITERAL);
		addEEnumLiteral(inclusionTypeEEnum, InclusionType.SOURCE_INCLUDE_LITERAL);

		initEEnum(propertyCategoryEEnum, PropertyCategory.class, "PropertyCategory");
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.NONE_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.GENERAL_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.PRESENTATION_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.EVENT_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.DESCRIPTION_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.LIMITATION_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.TECHNICAL_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.LOCALIZATION_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.AUTOCOMPLETE_LITERAL);
		addEEnumLiteral(propertyCategoryEEnum, PropertyCategory.XTOOLTIP_LITERAL);

		initEEnum(translationSupportEEnum, TranslationSupport.class, "TranslationSupport");
		addEEnumLiteral(translationSupportEEnum, TranslationSupport.NONE);
		addEEnumLiteral(translationSupportEEnum, TranslationSupport.NAME);
		addEEnumLiteral(translationSupportEEnum, TranslationSupport.TEXT);
		addEEnumLiteral(translationSupportEEnum, TranslationSupport.NAME_AND_TEXT);

		// Create resource
		createResource(eNS_URI);
	}

} //MetaModelPackageImpl
