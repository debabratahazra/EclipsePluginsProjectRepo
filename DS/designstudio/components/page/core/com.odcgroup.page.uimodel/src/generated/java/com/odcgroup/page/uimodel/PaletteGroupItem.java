/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.WidgetTemplate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Palette Group Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getSmallImage <em>Small Image</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLargeImage <em>Large Image</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getTooltip <em>Tooltip</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getWidgetTemplate <em>Widget Template</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroupItem#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem()
 * @model
 * @generated
 */
public interface PaletteGroupItem extends EObject {
	/**
	 * Returns the value of the '<em><b>Small Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Small Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Image</em>' attribute.
	 * @see #setSmallImage(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_SmallImage()
	 * @model
	 * @generated
	 */
	String getSmallImage();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getSmallImage <em>Small Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Image</em>' attribute.
	 * @see #getSmallImage()
	 * @generated
	 */
	void setSmallImage(String value);

	/**
	 * Returns the value of the '<em><b>Large Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Large Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Large Image</em>' attribute.
	 * @see #setLargeImage(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_LargeImage()
	 * @model
	 * @generated
	 */
	String getLargeImage();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLargeImage <em>Large Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Large Image</em>' attribute.
	 * @see #getLargeImage()
	 * @generated
	 */
	void setLargeImage(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tooltip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tooltip</em>' attribute.
	 * @see #setTooltip(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_Tooltip()
	 * @model
	 * @generated
	 */
	String getTooltip();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getTooltip <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tooltip</em>' attribute.
	 * @see #getTooltip()
	 * @generated
	 */
	void setTooltip(String value);

	/**
	 * Returns the value of the '<em><b>Widget Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Template</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Template</em>' reference.
	 * @see #setWidgetTemplate(WidgetTemplate)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_WidgetTemplate()
	 * @model
	 * @generated
	 */
	WidgetTemplate getWidgetTemplate();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroupItem#getWidgetTemplate <em>Widget Template</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Template</em>' reference.
	 * @see #getWidgetTemplate()
	 * @generated
	 */
	void setWidgetTemplate(WidgetTemplate value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroupItem_Name()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

} // PaletteGroupItem