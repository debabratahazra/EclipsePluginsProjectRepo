/**
 */
package com.example.sample;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.example.sample.Library#getType <em>Type</em>}</li>
 *   <li>{@link com.example.sample.Library#isStateOwner <em>State Owner</em>}</li>
 *   <li>{@link com.example.sample.Library#getBook <em>Book</em>}</li>
 *   <li>{@link com.example.sample.Library#getAuther <em>Auther</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.example.sample.SamplePackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.example.sample.SamplePackage#getLibrary_Type()
	 * @model unique="false"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.example.sample.Library#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>State Owner</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Owner</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Owner</em>' attribute.
	 * @see #setStateOwner(boolean)
	 * @see com.example.sample.SamplePackage#getLibrary_StateOwner()
	 * @model default="true" unique="false"
	 * @generated
	 */
	boolean isStateOwner();

	/**
	 * Sets the value of the '{@link com.example.sample.Library#isStateOwner <em>State Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Owner</em>' attribute.
	 * @see #isStateOwner()
	 * @generated
	 */
	void setStateOwner(boolean value);

	/**
	 * Returns the value of the '<em><b>Book</b></em>' containment reference list.
	 * The list contents are of type {@link com.example.sample.Book}.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Book#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Book</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Book</em>' containment reference list.
	 * @see com.example.sample.SamplePackage#getLibrary_Book()
	 * @see com.example.sample.Book#getLibrary
	 * @model opposite="library" containment="true"
	 * @generated
	 */
	EList<Book> getBook();

	/**
	 * Returns the value of the '<em><b>Auther</b></em>' containment reference list.
	 * The list contents are of type {@link com.example.sample.Writer}.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Writer#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auther</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auther</em>' containment reference list.
	 * @see com.example.sample.SamplePackage#getLibrary_Auther()
	 * @see com.example.sample.Writer#getLibrary
	 * @model opposite="library" containment="true"
	 * @generated
	 */
	EList<Writer> getAuther();

} // Library
