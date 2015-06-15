/**
 */
package com.example.sample;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.example.sample.SampleFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='com.example'"
 * @generated
 */
public interface SamplePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sample";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "com.example.sample";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sample";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamplePackage eINSTANCE = com.example.sample.impl.SamplePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.example.sample.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.example.sample.impl.LibraryImpl
	 * @see com.example.sample.impl.SamplePackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__TYPE = 0;

	/**
	 * The feature id for the '<em><b>State Owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__STATE_OWNER = 1;

	/**
	 * The feature id for the '<em><b>Book</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__BOOK = 2;

	/**
	 * The feature id for the '<em><b>Auther</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__AUTHER = 3;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.example.sample.impl.BookImpl <em>Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.example.sample.impl.BookImpl
	 * @see com.example.sample.impl.SamplePackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 1;

	/**
	 * The feature id for the '<em><b>Book Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__BOOK_NAME = 0;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__PAGES = 1;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__CATEGORY = 2;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__LIBRARY = 3;

	/**
	 * The feature id for the '<em><b>Auther</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__AUTHER = 4;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.example.sample.impl.WriterImpl <em>Writer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.example.sample.impl.WriterImpl
	 * @see com.example.sample.impl.SamplePackageImpl#getWriter()
	 * @generated
	 */
	int WRITER = 2;

	/**
	 * The feature id for the '<em><b>Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__WRITER_NAME = 0;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__LIBRARY = 1;

	/**
	 * The feature id for the '<em><b>Book</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER__BOOK = 2;

	/**
	 * The number of structural features of the '<em>Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRITER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.example.sample.BookCategory <em>Book Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.example.sample.BookCategory
	 * @see com.example.sample.impl.SamplePackageImpl#getBookCategory()
	 * @generated
	 */
	int BOOK_CATEGORY = 3;


	/**
	 * Returns the meta object for class '{@link com.example.sample.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see com.example.sample.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Library#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.example.sample.Library#getType()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Library#isStateOwner <em>State Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Owner</em>'.
	 * @see com.example.sample.Library#isStateOwner()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_StateOwner();

	/**
	 * Returns the meta object for the containment reference list '{@link com.example.sample.Library#getBook <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Book</em>'.
	 * @see com.example.sample.Library#getBook()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Book();

	/**
	 * Returns the meta object for the containment reference list '{@link com.example.sample.Library#getAuther <em>Auther</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Auther</em>'.
	 * @see com.example.sample.Library#getAuther()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Auther();

	/**
	 * Returns the meta object for class '{@link com.example.sample.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Book</em>'.
	 * @see com.example.sample.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Book#getBookName <em>Book Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Book Name</em>'.
	 * @see com.example.sample.Book#getBookName()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_BookName();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Book#getPages <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pages</em>'.
	 * @see com.example.sample.Book#getPages()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Pages();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Book#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.example.sample.Book#getCategory()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Category();

	/**
	 * Returns the meta object for the container reference '{@link com.example.sample.Book#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see com.example.sample.Book#getLibrary()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Library();

	/**
	 * Returns the meta object for the reference list '{@link com.example.sample.Book#getAuther <em>Auther</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Auther</em>'.
	 * @see com.example.sample.Book#getAuther()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Auther();

	/**
	 * Returns the meta object for class '{@link com.example.sample.Writer <em>Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Writer</em>'.
	 * @see com.example.sample.Writer
	 * @generated
	 */
	EClass getWriter();

	/**
	 * Returns the meta object for the attribute '{@link com.example.sample.Writer#getWriterName <em>Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Writer Name</em>'.
	 * @see com.example.sample.Writer#getWriterName()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_WriterName();

	/**
	 * Returns the meta object for the container reference '{@link com.example.sample.Writer#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see com.example.sample.Writer#getLibrary()
	 * @see #getWriter()
	 * @generated
	 */
	EReference getWriter_Library();

	/**
	 * Returns the meta object for the reference list '{@link com.example.sample.Writer#getBook <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Book</em>'.
	 * @see com.example.sample.Writer#getBook()
	 * @see #getWriter()
	 * @generated
	 */
	EReference getWriter_Book();

	/**
	 * Returns the meta object for enum '{@link com.example.sample.BookCategory <em>Book Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Book Category</em>'.
	 * @see com.example.sample.BookCategory
	 * @generated
	 */
	EEnum getBookCategory();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SampleFactory getSampleFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.example.sample.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.example.sample.impl.LibraryImpl
		 * @see com.example.sample.impl.SamplePackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__TYPE = eINSTANCE.getLibrary_Type();

		/**
		 * The meta object literal for the '<em><b>State Owner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__STATE_OWNER = eINSTANCE.getLibrary_StateOwner();

		/**
		 * The meta object literal for the '<em><b>Book</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__BOOK = eINSTANCE.getLibrary_Book();

		/**
		 * The meta object literal for the '<em><b>Auther</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__AUTHER = eINSTANCE.getLibrary_Auther();

		/**
		 * The meta object literal for the '{@link com.example.sample.impl.BookImpl <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.example.sample.impl.BookImpl
		 * @see com.example.sample.impl.SamplePackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Book Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__BOOK_NAME = eINSTANCE.getBook_BookName();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__PAGES = eINSTANCE.getBook_Pages();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__CATEGORY = eINSTANCE.getBook_Category();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__LIBRARY = eINSTANCE.getBook_Library();

		/**
		 * The meta object literal for the '<em><b>Auther</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__AUTHER = eINSTANCE.getBook_Auther();

		/**
		 * The meta object literal for the '{@link com.example.sample.impl.WriterImpl <em>Writer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.example.sample.impl.WriterImpl
		 * @see com.example.sample.impl.SamplePackageImpl#getWriter()
		 * @generated
		 */
		EClass WRITER = eINSTANCE.getWriter();

		/**
		 * The meta object literal for the '<em><b>Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WRITER__WRITER_NAME = eINSTANCE.getWriter_WriterName();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WRITER__LIBRARY = eINSTANCE.getWriter_Library();

		/**
		 * The meta object literal for the '<em><b>Book</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WRITER__BOOK = eINSTANCE.getWriter_Book();

		/**
		 * The meta object literal for the '{@link com.example.sample.BookCategory <em>Book Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.example.sample.BookCategory
		 * @see com.example.sample.impl.SamplePackageImpl#getBookCategory()
		 * @generated
		 */
		EEnum BOOK_CATEGORY = eINSTANCE.getBookCategory();

	}

} //SamplePackage
