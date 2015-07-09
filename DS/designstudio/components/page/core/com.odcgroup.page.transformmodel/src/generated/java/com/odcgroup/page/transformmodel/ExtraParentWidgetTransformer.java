/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel;


/**
 * <!-- begin-user-doc -->
 * The ExtraParentWidgetTransformer can optionally include an extra parent Element
 * which encapsulates the Element generated for the Widget. The flag OnlyAddIfRoot
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentElementName <em>Parent Element Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#isOnlyAddIfRoot <em>Only Add If Root</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentNamespace <em>Parent Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getExtraParentWidgetTransformer()
 * @model
 * @generated
 */
public interface ExtraParentWidgetTransformer extends ElementNameWidgetTransformer {
	/**
	 * Returns the value of the '<em><b>Parent Element Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Element Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Element Name</em>' attribute.
	 * @see #setParentElementName(String)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getExtraParentWidgetTransformer_ParentElementName()
	 * @model
	 * @generated
	 */
	String getParentElementName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentElementName <em>Parent Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Element Name</em>' attribute.
	 * @see #getParentElementName()
	 * @generated
	 */
	void setParentElementName(String value);

	/**
	 * Returns the value of the '<em><b>Only Add If Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Only Add If Root</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Only Add If Root</em>' attribute.
	 * @see #setOnlyAddIfRoot(boolean)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getExtraParentWidgetTransformer_OnlyAddIfRoot()
	 * @model
	 * @generated
	 */
	boolean isOnlyAddIfRoot();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#isOnlyAddIfRoot <em>Only Add If Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Only Add If Root</em>' attribute.
	 * @see #isOnlyAddIfRoot()
	 * @generated
	 */
	void setOnlyAddIfRoot(boolean value);

	/**
	 * Returns the value of the '<em><b>Parent Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Namespace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Namespace</em>' reference.
	 * @see #setParentNamespace(Namespace)
	 * @see com.odcgroup.page.transformmodel.TransformModelPackage#getExtraParentWidgetTransformer_ParentNamespace()
	 * @model
	 * @generated
	 */
	Namespace getParentNamespace();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer#getParentNamespace <em>Parent Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Namespace</em>' reference.
	 * @see #getParentNamespace()
	 * @generated
	 */
	void setParentNamespace(Namespace value);

} // ExtraParentWidgetTransformer