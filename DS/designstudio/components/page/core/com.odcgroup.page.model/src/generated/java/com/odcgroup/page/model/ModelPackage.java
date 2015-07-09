/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.odcgroup.page.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/page/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.odcgroup.page.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = com.odcgroup.page.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.WidgetImpl <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.WidgetImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getWidget()
	 * @generated
	 */
	int WIDGET = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__CONTENTS = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__PARENT = 2;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__TYPE_NAME = 3;

	/**
	 * The feature id for the '<em><b>Library Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__LIBRARY_NAME = 4;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__MODEL = 5;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__EVENTS = 6;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__SNIPPETS = 7;

	/**
	 * The feature id for the '<em><b>Tool Tips</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__TOOL_TIPS = 8;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__LABELS = 9;

	/**
	 * The number of structural features of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.PropertyImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__READONLY = 1;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE_NAME = 2;

	/**
	 * The feature id for the '<em><b>Library Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__LIBRARY_NAME = 3;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__WIDGET = 4;

	/**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__MODEL = 5;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.ModelImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 2;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__WIDGET = 0;

	/**
	 * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__METAMODEL_VERSION = 1;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.EventImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 3;

	/**
	 * The feature id for the '<em><b>Event Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__EVENT_NAME = 0;

	/**
	 * The feature id for the '<em><b>Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__FUNCTION_NAME = 1;

	/**
	 * The feature id for the '<em><b>Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NATURE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SNIPPETS = 6;

	/**
	 * The feature id for the '<em><b>Messages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__MESSAGES = 7;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.ParameterImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 1;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__USER_DEFINED = 2;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.SnippetImpl <em>Snippet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.SnippetImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getSnippet()
	 * @generated
	 */
	int SNIPPET = 5;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET__TYPE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET__PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET__CONTENTS = 2;

	/**
	 * The number of structural features of the '<em>Snippet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.impl.TranslationImpl <em>Translation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.impl.TranslationImpl
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getTranslation()
	 * @generated
	 */
	int TRANSLATION = 6;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__MESSAGE = 1;

	/**
	 * The number of structural features of the '<em>Translation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.page.model.EventNature <em>Event Nature</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.page.model.EventNature
	 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getEventNature()
	 * @generated
	 */
	int EVENT_NATURE = 7;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see com.odcgroup.page.model.Widget
	 * @generated
	 */
	EClass getWidget();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.page.model.Widget#getProperties()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.odcgroup.page.model.Widget#getContents()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Contents();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.model.Widget#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see com.odcgroup.page.model.Widget#getParent()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Parent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Widget#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see com.odcgroup.page.model.Widget#getTypeName()
	 * @see #getWidget()
	 * @generated
	 */
	EAttribute getWidget_TypeName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Widget#getLibraryName <em>Library Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Library Name</em>'.
	 * @see com.odcgroup.page.model.Widget#getLibraryName()
	 * @see #getWidget()
	 * @generated
	 */
	EAttribute getWidget_LibraryName();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.model.Widget#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Model</em>'.
	 * @see com.odcgroup.page.model.Widget#getModel()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Model();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see com.odcgroup.page.model.Widget#getEvents()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Events();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getSnippets <em>Snippets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Snippets</em>'.
	 * @see com.odcgroup.page.model.Widget#getSnippets()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Snippets();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getToolTips <em>Tool Tips</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tool Tips</em>'.
	 * @see com.odcgroup.page.model.Widget#getToolTips()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_ToolTips();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Widget#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Labels</em>'.
	 * @see com.odcgroup.page.model.Widget#getLabels()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Labels();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.odcgroup.page.model.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.page.model.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Property#isReadonly <em>Readonly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Readonly</em>'.
	 * @see com.odcgroup.page.model.Property#isReadonly()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Readonly();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Property#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see com.odcgroup.page.model.Property#getTypeName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_TypeName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Property#getLibraryName <em>Library Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Library Name</em>'.
	 * @see com.odcgroup.page.model.Property#getLibraryName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_LibraryName();

	/**
	 * Returns the meta object for the container reference '{@link com.odcgroup.page.model.Property#getWidget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Widget</em>'.
	 * @see com.odcgroup.page.model.Property#getWidget()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Widget();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.page.model.Property#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model</em>'.
	 * @see com.odcgroup.page.model.Property#getModel()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Model();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see com.odcgroup.page.model.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the containment reference '{@link com.odcgroup.page.model.Model#getWidget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Widget</em>'.
	 * @see com.odcgroup.page.model.Model#getWidget()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Widget();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Model#getMetamodelVersion <em>Metamodel Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metamodel Version</em>'.
	 * @see com.odcgroup.page.model.Model#getMetamodelVersion()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_MetamodelVersion();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see com.odcgroup.page.model.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Event#getEventName <em>Event Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Name</em>'.
	 * @see com.odcgroup.page.model.Event#getEventName()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_EventName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Event#getFunctionName <em>Function Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Name</em>'.
	 * @see com.odcgroup.page.model.Event#getFunctionName()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_FunctionName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Event#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.odcgroup.page.model.Event#getParameters()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Event#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.page.model.Event#getProperties()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Properties();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Event#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.odcgroup.page.model.Event#getDescription()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Event#getSnippets <em>Snippets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Snippets</em>'.
	 * @see com.odcgroup.page.model.Event#getSnippets()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Snippets();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Event#getMessages <em>Messages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Messages</em>'.
	 * @see com.odcgroup.page.model.Event#getMessages()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Messages();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Event#getNature <em>Nature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nature</em>'.
	 * @see com.odcgroup.page.model.Event#getNature()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Nature();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see com.odcgroup.page.model.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.odcgroup.page.model.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.page.model.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Parameter#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see com.odcgroup.page.model.Parameter#isUserDefined()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_UserDefined();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Snippet <em>Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet</em>'.
	 * @see com.odcgroup.page.model.Snippet
	 * @generated
	 */
	EClass getSnippet();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Snippet#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see com.odcgroup.page.model.Snippet#getTypeName()
	 * @see #getSnippet()
	 * @generated
	 */
	EAttribute getSnippet_TypeName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Snippet#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.odcgroup.page.model.Snippet#getProperties()
	 * @see #getSnippet()
	 * @generated
	 */
	EReference getSnippet_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.page.model.Snippet#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.odcgroup.page.model.Snippet#getContents()
	 * @see #getSnippet()
	 * @generated
	 */
	EReference getSnippet_Contents();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.page.model.Translation <em>Translation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Translation</em>'.
	 * @see com.odcgroup.page.model.Translation
	 * @generated
	 */
	EClass getTranslation();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Translation#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.odcgroup.page.model.Translation#getLanguage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Language();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.page.model.Translation#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see com.odcgroup.page.model.Translation#getMessage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Message();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.page.model.EventNature <em>Event Nature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Event Nature</em>'.
	 * @see com.odcgroup.page.model.EventNature
	 * @generated
	 */
	EEnum getEventNature();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.WidgetImpl <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.WidgetImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getWidget()
		 * @generated
		 */
		EClass WIDGET = eINSTANCE.getWidget();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__PROPERTIES = eINSTANCE.getWidget_Properties();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__CONTENTS = eINSTANCE.getWidget_Contents();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__PARENT = eINSTANCE.getWidget_Parent();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET__TYPE_NAME = eINSTANCE.getWidget_TypeName();

		/**
		 * The meta object literal for the '<em><b>Library Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET__LIBRARY_NAME = eINSTANCE.getWidget_LibraryName();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__MODEL = eINSTANCE.getWidget_Model();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__EVENTS = eINSTANCE.getWidget_Events();

		/**
		 * The meta object literal for the '<em><b>Snippets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__SNIPPETS = eINSTANCE.getWidget_Snippets();

		/**
		 * The meta object literal for the '<em><b>Tool Tips</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__TOOL_TIPS = eINSTANCE.getWidget_ToolTips();

		/**
		 * The meta object literal for the '<em><b>Labels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__LABELS = eINSTANCE.getWidget_Labels();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.PropertyImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

		/**
		 * The meta object literal for the '<em><b>Readonly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__READONLY = eINSTANCE.getProperty_Readonly();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE_NAME = eINSTANCE.getProperty_TypeName();

		/**
		 * The meta object literal for the '<em><b>Library Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__LIBRARY_NAME = eINSTANCE.getProperty_LibraryName();

		/**
		 * The meta object literal for the '<em><b>Widget</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__WIDGET = eINSTANCE.getProperty_Widget();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__MODEL = eINSTANCE.getProperty_Model();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.ModelImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Widget</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__WIDGET = eINSTANCE.getModel_Widget();

		/**
		 * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__METAMODEL_VERSION = eINSTANCE.getModel_MetamodelVersion();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.EventImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Event Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__EVENT_NAME = eINSTANCE.getEvent_EventName();

		/**
		 * The meta object literal for the '<em><b>Function Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__FUNCTION_NAME = eINSTANCE.getEvent_FunctionName();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__PARAMETERS = eINSTANCE.getEvent_Parameters();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__PROPERTIES = eINSTANCE.getEvent_Properties();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__DESCRIPTION = eINSTANCE.getEvent_Description();

		/**
		 * The meta object literal for the '<em><b>Snippets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__SNIPPETS = eINSTANCE.getEvent_Snippets();

		/**
		 * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__MESSAGES = eINSTANCE.getEvent_Messages();

		/**
		 * The meta object literal for the '<em><b>Nature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__NATURE = eINSTANCE.getEvent_Nature();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.ParameterImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__USER_DEFINED = eINSTANCE.getParameter_UserDefined();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.SnippetImpl <em>Snippet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.SnippetImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getSnippet()
		 * @generated
		 */
		EClass SNIPPET = eINSTANCE.getSnippet();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SNIPPET__TYPE_NAME = eINSTANCE.getSnippet_TypeName();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET__PROPERTIES = eINSTANCE.getSnippet_Properties();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SNIPPET__CONTENTS = eINSTANCE.getSnippet_Contents();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.impl.TranslationImpl <em>Translation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.impl.TranslationImpl
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getTranslation()
		 * @generated
		 */
		EClass TRANSLATION = eINSTANCE.getTranslation();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__LANGUAGE = eINSTANCE.getTranslation_Language();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__MESSAGE = eINSTANCE.getTranslation_Message();

		/**
		 * The meta object literal for the '{@link com.odcgroup.page.model.EventNature <em>Event Nature</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.page.model.EventNature
		 * @see com.odcgroup.page.model.impl.ModelPackageImpl#getEventNature()
		 * @generated
		 */
		EEnum EVENT_NATURE = eINSTANCE.getEventNature();

	}

} //ModelPackage
