/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getWidgetLibraries <em>Widget Libraries</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getVersion <em>Version</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getContainability <em>Containability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getIncludeability <em>Includeability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getDataTypes <em>Data Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getEventModel <em>Event Model</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.MetaModel#getSnippetModel <em>Snippet Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel()
 * @model
 * @generated
 */
public interface MetaModel extends NamedType {
	/**
	 * Returns the value of the '<em><b>Widget Libraries</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetLibrary}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Libraries</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_WidgetLibraries()
	 * @model
	 * @generated
	 */
	EList<WidgetLibrary> getWidgetLibraries();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Data Types</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Types</em>' reference.
	 * @see #setDataTypes(DataTypes)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_DataTypes()
	 * @model
	 * @generated
	 */
	DataTypes getDataTypes();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getDataTypes <em>Data Types</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Types</em>' reference.
	 * @see #getDataTypes()
	 * @generated
	 */
	void setDataTypes(DataTypes value);

	/**
	 * Returns the value of the '<em><b>Event Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Model</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Model</em>' containment reference.
	 * @see #setEventModel(EventModel)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_EventModel()
	 * @model containment="true"
	 * @generated
	 */
	EventModel getEventModel();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getEventModel <em>Event Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Model</em>' containment reference.
	 * @see #getEventModel()
	 * @generated
	 */
	void setEventModel(EventModel value);

	/**
	 * Returns the value of the '<em><b>Snippet Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Snippet Model</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Snippet Model</em>' containment reference.
	 * @see #setSnippetModel(SnippetModel)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_SnippetModel()
	 * @model containment="true"
	 * @generated
	 */
	SnippetModel getSnippetModel();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getSnippetModel <em>Snippet Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Snippet Model</em>' containment reference.
	 * @see #getSnippetModel()
	 * @generated
	 */
	void setSnippetModel(SnippetModel value);

	/**
	 * Returns the value of the '<em><b>Containability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containability</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containability</em>' containment reference.
	 * @see #setContainability(Accountability)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_Containability()
	 * @model containment="true"
	 * @generated
	 */
	Accountability getContainability();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getContainability <em>Containability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containability</em>' containment reference.
	 * @see #getContainability()
	 * @generated
	 */
	void setContainability(Accountability value);

	/**
	 * Returns the value of the '<em><b>Includeability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Includeability</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includeability</em>' containment reference.
	 * @see #setIncludeability(Accountability)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getMetaModel_Includeability()
	 * @model containment="true"
	 * @generated
	 */
	Accountability getIncludeability();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.MetaModel#getIncludeability <em>Includeability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Includeability</em>' containment reference.
	 * @see #getIncludeability()
	 * @generated
	 */
	void setIncludeability(Accountability value);

	/**
	 * Finds the WidgetLibrary given its name.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @return WidgetLibrary The WidgetLibrary or null if no WidgetLibrary can be found
	 */
	public WidgetLibrary findWidgetLibrary(String libraryName);	

	/**
	 * Finds the WidgetType given its name and the name of the library that it 
	 * belongs to.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @param name The name of the WidgetType
	 * @return WidgetType The WidgetType or null if no WidgetType can be found
	 */
	public WidgetType findWidgetType(String libraryName, String name);
	
	/**
	 * Finds the PropertyType given its name and the name of the library that it 
	 * belongs to.
	 * 
	 * @param libraryName The name of the WidgetLibrary
	 * @param name The name of the PropertyType
	 * @return PropertyType The PropertyType or null if no PropertyType can be found
	 */
	public PropertyType findPropertyType(String libraryName, String name);	

} // MetaModel