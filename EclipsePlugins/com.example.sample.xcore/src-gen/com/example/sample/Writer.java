/**
 */
package com.example.sample;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Writer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.example.sample.Writer#getWriterName <em>Writer Name</em>}</li>
 *   <li>{@link com.example.sample.Writer#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.example.sample.Writer#getBook <em>Book</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.example.sample.SamplePackage#getWriter()
 * @model
 * @generated
 */
public interface Writer extends EObject {
	/**
	 * Returns the value of the '<em><b>Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Writer Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Writer Name</em>' attribute.
	 * @see #setWriterName(String)
	 * @see com.example.sample.SamplePackage#getWriter_WriterName()
	 * @model unique="false"
	 * @generated
	 */
	String getWriterName();

	/**
	 * Sets the value of the '{@link com.example.sample.Writer#getWriterName <em>Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Writer Name</em>' attribute.
	 * @see #getWriterName()
	 * @generated
	 */
	void setWriterName(String value);

	/**
	 * Returns the value of the '<em><b>Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Library#getAuther <em>Auther</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' container reference.
	 * @see #setLibrary(Library)
	 * @see com.example.sample.SamplePackage#getWriter_Library()
	 * @see com.example.sample.Library#getAuther
	 * @model opposite="auther" transient="false"
	 * @generated
	 */
	Library getLibrary();

	/**
	 * Sets the value of the '{@link com.example.sample.Writer#getLibrary <em>Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' container reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(Library value);

	/**
	 * Returns the value of the '<em><b>Book</b></em>' reference list.
	 * The list contents are of type {@link com.example.sample.Book}.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Book#getAuther <em>Auther</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Book</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Book</em>' reference list.
	 * @see com.example.sample.SamplePackage#getWriter_Book()
	 * @see com.example.sample.Book#getAuther
	 * @model opposite="auther"
	 * @generated
	 */
	EList<Book> getBook();

} // Writer
