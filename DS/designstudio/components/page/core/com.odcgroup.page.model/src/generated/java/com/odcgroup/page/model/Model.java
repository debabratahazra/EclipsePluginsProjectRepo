/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.Model#getWidget <em>Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.model.Model#getMetamodelVersion <em>Metamodel Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.model.ModelPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
	/**
	 * Returns the value of the '<em><b>Widget</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.model.Widget#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget</em>' containment reference.
	 * @see #setWidget(Widget)
	 * @see com.odcgroup.page.model.ModelPackage#getModel_Widget()
	 * @see com.odcgroup.page.model.Widget#getModel
	 * @model opposite="model" containment="true"
	 * @generated
	 */
	Widget getWidget();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Model#getWidget <em>Widget</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget</em>' containment reference.
	 * @see #getWidget()
	 * @generated
	 */
	void setWidget(Widget value);

	/**
	 * Returns the value of the '<em><b>Metamodel Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel Version</em>' attribute.
	 * @see #setMetamodelVersion(String)
	 * @see com.odcgroup.page.model.ModelPackage#getModel_MetamodelVersion()
	 * @model unique="false"
	 * @generated
	 */
	String getMetamodelVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.model.Model#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel Version</em>' attribute.
	 * @see #getMetamodelVersion()
	 * @generated
	 */
	void setMetamodelVersion(String value);

	/**
	 * Gets the xmi:id of the model. This method returns null if the
	 * resource is NOT an XMLResource.
	 * 
	 * @return String The xmi:id of the Model
	 */
	public String getXmiId();

} // Model