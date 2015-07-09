/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transform Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getNamespaces <em>Namespaces</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getWidgetTransformers <em>Widget Transformers</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultNamespace <em>Default Namespace</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getPropertyTransformers <em>Property Transformers</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultWidgetLibrary <em>Default Widget Library</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.TransformModel#getEventTransformations <em>Event Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel()
 * @model
 * @generated
 */
public interface TransformModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Namespaces</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.transformmodel.Namespace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespaces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespaces</em>' containment reference list.
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_Namespaces()
	 * @model containment="true"
	 * @generated
	 */
	EList<Namespace> getNamespaces();

	/**
	 * Returns the value of the '<em><b>Widget Transformers</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.transformmodel.AbstractWidgetTransformer}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.transformmodel.AbstractWidgetTransformer#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Transformers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Transformers</em>' containment reference list.
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_WidgetTransformers()
	 * @see com.odcgroup.page.transformmodel.AbstractWidgetTransformer#getModel
	 * @model opposite="model" containment="true"
	 * @generated
	 */
	EList<AbstractWidgetTransformer> getWidgetTransformers();

	/**
	 * Returns the value of the '<em><b>Default Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Namespace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Namespace</em>' reference.
	 * @see #setDefaultNamespace(Namespace)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_DefaultNamespace()
	 * @model
	 * @generated
	 */
	Namespace getDefaultNamespace();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultNamespace <em>Default Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Namespace</em>' reference.
	 * @see #getDefaultNamespace()
	 * @generated
	 */
	void setDefaultNamespace(Namespace value);

	/**
	 * Returns the value of the '<em><b>Property Transformers</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Transformers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Transformers</em>' containment reference list.
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_PropertyTransformers()
	 * @see com.odcgroup.page.transformmodel.AbstractPropertyTransformer#getModel
	 * @model opposite="model" containment="true"
	 * @generated
	 */
	EList<AbstractPropertyTransformer> getPropertyTransformers();

	/**
	 * Returns the value of the '<em><b>Default Widget Library</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Widget Library</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Widget Library</em>' reference.
	 * @see #setDefaultWidgetLibrary(WidgetLibrary)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_DefaultWidgetLibrary()
	 * @model
	 * @generated
	 */
	WidgetLibrary getDefaultWidgetLibrary();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.TransformModel#getDefaultWidgetLibrary <em>Default Widget Library</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Widget Library</em>' reference.
	 * @see #getDefaultWidgetLibrary()
	 * @generated
	 */
	void setDefaultWidgetLibrary(WidgetLibrary value);

	/**
	 * Returns the value of the '<em><b>Event Transformations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Transformations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Transformations</em>' containment reference.
	 * @see #setEventTransformations(EventTransformations)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getTransformModel_EventTransformations()
	 * @model containment="true"
	 * @generated
	 */
	EventTransformations getEventTransformations();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.TransformModel#getEventTransformations <em>Event Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Transformations</em>' containment reference.
	 * @see #getEventTransformations()
	 * @generated
	 */
	void setEventTransformations(EventTransformations value);

	/**
	 * Finds the PropertyTransformer. Returns the default one if no PropertyTransformer can be found.
	 * 
	 * @param property The Property
	 * @return PropertyTransformer The PropertyTransformer
	 */
	public PropertyTransformer findPropertyTransformer(Property property);	

	/**
	 * Finds the WidgetTransformer given a widget. 
	 * Returns the default one if no WidgetTransformer can be found.
	 * 
	 * @param widget The Widget
	 * @return WidgetTransformer The WidgetTransformer
	 */
	public WidgetTransformer findWidgetTransformer(Widget widget);
	
	/**
	 * Finds the WidgetTransformer given a widget type. 
	 * Returns the default one if no WidgetTransformer can be found.
	 * 
	 * @param widgetType The WidgetType
	 * @return WidgetTransformer The WidgetTransformer
	 */
	public WidgetTransformer findWidgetTransformer(WidgetType widgetType);

	
	/**
	 * Finds the Namespace. Returns the default one if no Namespace can be found.
	 * 
	 * @param uri The URI
	 * @return Namespace The Namespace
	 */
	public Namespace findNamespace(String uri);	

	/**
	 * Finds the Namespace by it's prefix. Returns the default one if no Namespace can be found.
	 * 
	 * @param prefix The URI
	 * @return Namespace The Namespace
	 */
	public Namespace findNamespaceByPrefix(String prefix);	

	/**
	 * Finds the Namespaces by it's origin.
	 * 
	 * @param origin
	 * 			The origin
	 * @return List
	 * 			The list of namespaces
	 * 		
	 */
	public List findNamespacesByOrigin(String origin);
	
} // TransformModel