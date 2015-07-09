/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.Event#getEventName <em>Event Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getFunctionName <em>Function Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getNature <em>Nature</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Event#getMessages <em>Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.model.ModelPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends EObject {
	/**
	 * Returns the value of the '<em><b>Event Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Name</em>' attribute.
	 * @see #setEventName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_EventName()
	 * @model default="" required="true" ordered="false"
	 * @generated
	 */
	String getEventName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Event#getEventName <em>Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Name</em>' attribute.
	 * @see #getEventName()
	 * @generated
	 */
	void setEventName(String value);

	/**
	 * Returns the value of the '<em><b>Function Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Name</em>' attribute.
	 * @see #setFunctionName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_FunctionName()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getFunctionName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Event#getFunctionName <em>Function Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Name</em>' attribute.
	 * @see #getFunctionName()
	 * @generated
	 */
	void setFunctionName(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Parameters()
	 * @model containment="true" keys="name"
	 * @generated
	 */
	EList<Parameter> getParameters();
	
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Description()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Event#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Snippets</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Snippet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Snippets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Snippets</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Snippets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Snippet> getSnippets();

	/**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Messages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getMessages();

	/**
	 * Returns the value of the '<em><b>Nature</b></em>' attribute.
	 * The default value is <code>"ADVANCED"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.model.EventNature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nature</em>' attribute.
	 * @see com.odcgroup.page.model.EventNature
	 * @see #setNature(EventNature)
	 * @see com.odcgroup.page.model.ModelPackage#getEvent_Nature()
	 * @model default="ADVANCED"
	 * @generated
	 */
	EventNature getNature();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Event#getNature <em>Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nature</em>' attribute.
	 * @see com.odcgroup.page.model.EventNature
	 * @see #getNature()
	 * @generated
	 */
	void setNature(EventNature value);

	/**
	 * Gets the Widget that this Event is for.
	 * 
	 * @return Widget The Widget that this Event is for
	 */
	Widget getWidget();
	
	
	/**
	 * returns the parent widget, set incase if its a temporary event
	 * 
	 * @return
	 */
	Widget getParent();
	
	
	/**
	 * @param widget
	 */
	void setParent(Widget widget);
	
	/**
	 * Gets the type of the Event.
	 * 
	 * @return EventType
	 */
	EventType getEventType();
	
    /**
     * Gets the type of the Function.
     * 
     * @return FunctionType
     */
    FunctionType getFunctionType();	
    
    /**
     * Finds the Parameter. Returns null if the Parameter does not exist
     * 
     * @param name The name of the parameter
     * @return Parameter The Parameter or null if it does not exist
     */
    Parameter findParameter(String name);
    
    /**
     * Finds the Property. Returns null if the Property does not exist
     * 
     * @param name The name of the parameter
     * @return Property The Property or null if it does not exist
     */
    Property findProperty(String name);   
    
    /**
     * @return boolean
     */
    boolean isSimplifiedEvent();

    /**
     * @return boolean
     */
    boolean isAdvancedEvent();
    
    /**
     * Sets the translation identifier
     * @param tid
     */
    void setTranslationId(long tid);

	/**
	 * @return the translation identifier
	 */
	long getTranslationId();

} // Event
