/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.Widget#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getContents <em>Contents</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getLibraryName <em>Library Name</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getModel <em>Model</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getEvents <em>Events</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getToolTips <em>Tool Tips</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Widget#getLabels <em>Labels</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.model.ModelPackage#getWidget()
 * @model
 * @generated
 */
public interface Widget extends EObject {

    /**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Widget}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Widget#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Contents</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Contents()
	 * @see com.odcgroup.page.model.Widget#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
    EList<Widget> getContents();

    /**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Property}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Property#getWidget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Properties()
	 * @see com.odcgroup.page.model.Property#getWidget
	 * @model opposite="widget" containment="true"
	 * @generated
	 */
    EList<Property> getProperties();

    /**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Widget#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Widget)
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Parent()
	 * @see com.odcgroup.page.model.Widget#getContents
	 * @model opposite="contents" transient="false"
	 * @generated
	 */
    Widget getParent();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.model.Widget#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
    void setParent(Widget value);

    /**
     * Returns the value of the '<em><b>Type Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Type Name</em>' attribute.
     * @see #setTypeName(String)
     * @see com.odcgroup.page.model.ModelPackage#getWidget_TypeName()
     * @model
     * @generated
     */
    String getTypeName();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.model.Widget#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
    void setTypeName(String value);

    /**
	 * Returns the value of the '<em><b>Library Name</b></em>' attribute.
	 * The default value is <code>"xgui"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Library Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Name</em>' attribute.
	 * @see #setLibraryName(String)
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_LibraryName()
	 * @model default="xgui"
	 * @generated
	 */
    String getLibraryName();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.model.Widget#getLibraryName <em>Library Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library Name</em>' attribute.
	 * @see #getLibraryName()
	 * @generated
	 */
    void setLibraryName(String value);

    /**
	 * Returns the value of the '<em><b>Model</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Model#getWidget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Model</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' container reference.
	 * @see #setModel(Model)
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Model()
	 * @see com.odcgroup.page.model.Model#getWidget
	 * @model opposite="widget" transient="false"
	 * @generated
	 */
    Model getModel();

    /**
	 * Sets the value of the '{@link com.odcgroup.page.model.Widget#getModel <em>Model</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' container reference.
	 * @see #getModel()
	 * @generated
	 */
    void setModel(Model value);

    /**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Event}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Events</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Events()
	 * @model containment="true"
	 * @generated
	 */
    EList<Event> getEvents();

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
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Snippets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Snippet> getSnippets();

				/**
	 * Returns the value of the '<em><b>Tool Tips</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tool Tips</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tool Tips</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_ToolTips()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getToolTips();

				/**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.model.Translation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Labels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see com.odcgroup.page.model.ModelPackage#getWidget_Labels()
	 * @model containment="true"
	 * @generated
	 */
	EList<Translation> getLabels();

				/**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model valueRequired="true"
     * @param value
     * @generated NOT
     */
    void setVisible(boolean value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @return boolean
     * @generated NOT
     */
    boolean isVisible();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model valueRequired="true"
     * @param value
     * @generated NOT
     */
    void setSelected(boolean value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @return boolean 
     * @generated NOT
     */
    boolean isSelected();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model indexRequired="true"
     * @param index
     * @generated NOT
     */
    void setIndexOfSelectedChild(int index);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @return int 
     * @generated NOT
     */
    int getIndexOfSelectedChild();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @return boolean
     * @generated NOT
     */
    boolean isSelectable();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @return boolean
     * @generated NOT
     */
    boolean isDeletable();

    /**
     * Gets the value of the property. Returns null if the PropertyType is null
     * of the property does not exist.
     * 
     * @param propertyType The PropertyType
     * @return String The value of the Property
     */
    String getPropertyValue(PropertyType propertyType);
    
    /**
     * Gets the value of the property. Returns null if the PropertyType is null
     * of the property does not exist.
     * 
     * @param propertyName The name of the Property
     * @return String The value of the Property
     */
    String getPropertyValue(String propertyName);   
    
    /**
     * Gets the Library containing this WidgetType.
     * 
     * @return Library The Library containing this WidgetType
     */
    WidgetLibrary getLibrary();

    /**
     * Gets the WidgetType containing the meta-information about this Widget.
     * 
     * @return WidgetType The WidgetType containing the meta-information about
     *         this Widget
     */
    WidgetType getType();

    /**
     * Gets the Widgets of the specified type which are contained within this
     * Widget. This method is generic and takes into account WidgetTypes which
     * are children of the specified type.
     * 
     * @param type
     *                The WidgetType to look for
     * @param inspectInclude 
     *                When true, compare the type with the child widget of any 
     *                encountered Include Widget
     * @return Unmodifiable List of Widgets which match the type
     */
    List<Widget> getWidgets(WidgetType type, boolean inspectInclude);

    /**
     * Finds the property given the name.
     * 
     * @param name
     *                The name of the Property
     * @return Property The Property or null if no Property can be found
     */
    Property findProperty(String name);
    
    /**
     * Finds the property given the type.
     * 
     * @param type
     *                The type of the Property
     * @return Property The Property or null if no Property can be found
     */
    Property findProperty(PropertyType type); 

    /**
     * Gets the first Widget in the Widget hierarchy which has a Property with
     * the specified name and value. The search starts with this Widget.
     * 
     * @param propertyName
     *                The name to look for
     * @param value
     *                The value to look for
     * @return The first Widget which has this PropertyType or null if no Widget
     *         has this role
     */
    Widget findWidgetIncludeParent(String propertyName, String value);

    /**
     * Gets the xmi:id of the model. This method returns null if the resource is
     * NOT an XMLResource.
     * 
     * @return String The xmi:id of the Model
     * @deprecated
     */
    String getXmiId();

    /**
     * Can we move left to the selected child ?
     * 
     * @return boolean The value
     */
    boolean canMoveLeftSelectedChild();

    /**
     * Can we move right to the selected child ?
     * 
     * @return boolean The value
     */

    boolean canMoveRightSelectedChild();

    /**
     * Can we delete the selected child ?
     * 
     * @return boolean The value
     */

    boolean canDeleteSelectedChild();
    
    /**
     * Sets the value of the Property.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     */
    void setPropertyValue(String propertyName, String value);
    
    /**
     * Sets the value of the Property.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     */
    void setPropertyValue(String propertyName, String value, boolean readonly);

    /**
     * Sets the Widget's properties. If a Property does not exist
     * it is ignored.
     * 
     * @param properties The properties to set
     * 		Key: Property Name
     * 		Value: Property Value
     */
    void setPropertyValues(Map<String, String> properties);
    
    /**
     * Gets the root Widget. This is the root of the Widget hierarchy.
     * 
     * @return Widget The root Widget
     */
    Widget getRootWidget();
    
    /**
     * Finds the first specified property in this Widget and its ancestor widgets. Returns null if the Property cannot
     * be found.
     * 
     * @param name The name of the Property
     * @return Property The Property or null if no Property could be found
     */
    Property findPropertyInParent(String name); 
    
    /**
     * Finds the value of the first specified property in this Widget and its ancestor widgets. Returns null if the Property cannot
     * be found.
     * 
     * @param name The name of the Property
     * @return String The value of the Property or null if no Property could be found
     */
    String findPropertyValueInParent(String name);    

    /**
     * Finds the MdfModelElement associated with this Widget.
     * 
     * @param repository The DomainRepository
     * @return MdfModelElement or null if no MdfModelElement is associated with this Widget
     */
    MdfModelElement findDomainObject(DomainRepository repository);

    /**
     * Finds the MdfModelElement associated with this Widget.
     * 
     * @param repository The DomainRepository
     * @param name the name of a mdf element (generally an attribute, association)
     * @return MdfModelElement or null if no MdfModelElement is associated with this Widget
     */
    MdfModelElement findDomainObject(DomainRepository repository, String name);

    /**
     * Find the dataset attribute bound to this widget, or null if not found
     * 
     * @param repository The DomainRepository
     * @param datasetName The name of the dataset
     * @param attribute name The name of an attribute within the dataset 
     * @return MdfModelElement or null if no dataset property is associated with this Widget
     */
    MdfModelElement findDatasetProperty(DomainRepository repository, String datasetName, String attributeName);
    /**
     * @return <code>true</code> if the widget is bound to a domain element, 
     * otherwise it returns <code>false</false>.
     */
    boolean isDomainWidget();
    
    /**
     * Sets the translation identifier
     * @param tid the new translation tid.
     * @generated NOT
     */
    void setTranslationId(long tid);
    
    /**
     * @return the translation identifier
     * @generated NOT
     */
    long getTranslationId();
    
    /**
     * Change the identifier of this widget. 
     * @param newId the new identifier. 
     */
    void setID(String newId);

    /**
     * Returns an identifier for this widget. Depending on the widget the returned value
     * can be empty. This identifier can be used as an html id.
     * 
     * @return the identifier of this witdget.
     */
    String getID();

    /**
     * @param widgetType
     * @return the ancestor widget with the give type.
     */
	Widget findAncestor(String widgetType);
    
    
} // Widget} // Widget
