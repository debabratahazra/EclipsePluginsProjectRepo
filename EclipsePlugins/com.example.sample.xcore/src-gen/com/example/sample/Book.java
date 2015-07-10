/**
 */
package com.example.sample;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.example.sample.Book#getBookName <em>Book Name</em>}</li>
 *   <li>{@link com.example.sample.Book#getPages <em>Pages</em>}</li>
 *   <li>{@link com.example.sample.Book#getCategory <em>Category</em>}</li>
 *   <li>{@link com.example.sample.Book#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.example.sample.Book#getAuther <em>Auther</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.example.sample.SamplePackage#getBook()
 * @model
 * @generated
 */
public interface Book extends EObject {
	/**
	 * Returns the value of the '<em><b>Book Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Book Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Book Name</em>' attribute.
	 * @see #setBookName(String)
	 * @see com.example.sample.SamplePackage#getBook_BookName()
	 * @model unique="false"
	 * @generated
	 */
	String getBookName();

	/**
	 * Sets the value of the '{@link com.example.sample.Book#getBookName <em>Book Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Book Name</em>' attribute.
	 * @see #getBookName()
	 * @generated
	 */
	void setBookName(String value);

	/**
	 * Returns the value of the '<em><b>Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pages</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pages</em>' attribute.
	 * @see #setPages(int)
	 * @see com.example.sample.SamplePackage#getBook_Pages()
	 * @model unique="false"
	 * @generated
	 */
	int getPages();

	/**
	 * Sets the value of the '{@link com.example.sample.Book#getPages <em>Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pages</em>' attribute.
	 * @see #getPages()
	 * @generated
	 */
	void setPages(int value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * The literals are from the enumeration {@link com.example.sample.BookCategory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see com.example.sample.BookCategory
	 * @see #setCategory(BookCategory)
	 * @see com.example.sample.SamplePackage#getBook_Category()
	 * @model unique="false"
	 * @generated
	 */
	BookCategory getCategory();

	/**
	 * Sets the value of the '{@link com.example.sample.Book#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see com.example.sample.BookCategory
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(BookCategory value);

	/**
	 * Returns the value of the '<em><b>Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Library#getBook <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' container reference.
	 * @see #setLibrary(Library)
	 * @see com.example.sample.SamplePackage#getBook_Library()
	 * @see com.example.sample.Library#getBook
	 * @model opposite="book" transient="false"
	 * @generated
	 */
	Library getLibrary();

	/**
	 * Sets the value of the '{@link com.example.sample.Book#getLibrary <em>Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' container reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(Library value);

	/**
	 * Returns the value of the '<em><b>Auther</b></em>' reference list.
	 * The list contents are of type {@link com.example.sample.Writer}.
	 * It is bidirectional and its opposite is '{@link com.example.sample.Writer#getBook <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auther</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auther</em>' reference list.
	 * @see com.example.sample.SamplePackage#getBook_Auther()
	 * @see com.example.sample.Writer#getBook
	 * @model opposite="book"
	 * @generated
	 */
	EList<Writer> getAuther();

} // Book
