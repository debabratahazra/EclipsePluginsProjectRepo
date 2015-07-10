/**
 */
package com.example.sample.impl;

import com.example.sample.Book;
import com.example.sample.BookCategory;
import com.example.sample.Library;
import com.example.sample.SamplePackage;
import com.example.sample.Writer;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.example.sample.impl.BookImpl#getBookName <em>Book Name</em>}</li>
 *   <li>{@link com.example.sample.impl.BookImpl#getPages <em>Pages</em>}</li>
 *   <li>{@link com.example.sample.impl.BookImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.example.sample.impl.BookImpl#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.example.sample.impl.BookImpl#getAuther <em>Auther</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BookImpl extends MinimalEObjectImpl.Container implements Book {
	/**
	 * The default value of the '{@link #getBookName() <em>Book Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookName()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOK_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBookName() <em>Book Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBookName()
	 * @generated
	 * @ordered
	 */
	protected String bookName = BOOK_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPages() <em>Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPages()
	 * @generated
	 * @ordered
	 */
	protected static final int PAGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPages() <em>Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPages()
	 * @generated
	 * @ordered
	 */
	protected int pages = PAGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final BookCategory CATEGORY_EDEFAULT = BookCategory.SCI_FI;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected BookCategory category = CATEGORY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuther() <em>Auther</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuther()
	 * @generated
	 * @ordered
	 */
	protected EList<Writer> auther;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BookImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplePackage.Literals.BOOK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBookName(String newBookName) {
		String oldBookName = bookName;
		bookName = newBookName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.BOOK__BOOK_NAME, oldBookName, bookName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPages(int newPages) {
		int oldPages = pages;
		pages = newPages;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.BOOK__PAGES, oldPages, pages));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BookCategory getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(BookCategory newCategory) {
		BookCategory oldCategory = category;
		category = newCategory == null ? CATEGORY_EDEFAULT : newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.BOOK__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library getLibrary() {
		if (eContainerFeatureID() != SamplePackage.BOOK__LIBRARY) return null;
		return (Library)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library basicGetLibrary() {
		if (eContainerFeatureID() != SamplePackage.BOOK__LIBRARY) return null;
		return (Library)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibrary(Library newLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLibrary, SamplePackage.BOOK__LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(Library newLibrary) {
		if (newLibrary != eInternalContainer() || (eContainerFeatureID() != SamplePackage.BOOK__LIBRARY && newLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLibrary != null)
				msgs = ((InternalEObject)newLibrary).eInverseAdd(this, SamplePackage.LIBRARY__BOOK, Library.class, msgs);
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.BOOK__LIBRARY, newLibrary, newLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Writer> getAuther() {
		if (auther == null) {
			auther = new EObjectWithInverseResolvingEList.ManyInverse<Writer>(Writer.class, this, SamplePackage.BOOK__AUTHER, SamplePackage.WRITER__BOOK);
		}
		return auther;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamplePackage.BOOK__LIBRARY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLibrary((Library)otherEnd, msgs);
			case SamplePackage.BOOK__AUTHER:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAuther()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamplePackage.BOOK__LIBRARY:
				return basicSetLibrary(null, msgs);
			case SamplePackage.BOOK__AUTHER:
				return ((InternalEList<?>)getAuther()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SamplePackage.BOOK__LIBRARY:
				return eInternalContainer().eInverseRemove(this, SamplePackage.LIBRARY__BOOK, Library.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamplePackage.BOOK__BOOK_NAME:
				return getBookName();
			case SamplePackage.BOOK__PAGES:
				return getPages();
			case SamplePackage.BOOK__CATEGORY:
				return getCategory();
			case SamplePackage.BOOK__LIBRARY:
				if (resolve) return getLibrary();
				return basicGetLibrary();
			case SamplePackage.BOOK__AUTHER:
				return getAuther();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SamplePackage.BOOK__BOOK_NAME:
				setBookName((String)newValue);
				return;
			case SamplePackage.BOOK__PAGES:
				setPages((Integer)newValue);
				return;
			case SamplePackage.BOOK__CATEGORY:
				setCategory((BookCategory)newValue);
				return;
			case SamplePackage.BOOK__LIBRARY:
				setLibrary((Library)newValue);
				return;
			case SamplePackage.BOOK__AUTHER:
				getAuther().clear();
				getAuther().addAll((Collection<? extends Writer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SamplePackage.BOOK__BOOK_NAME:
				setBookName(BOOK_NAME_EDEFAULT);
				return;
			case SamplePackage.BOOK__PAGES:
				setPages(PAGES_EDEFAULT);
				return;
			case SamplePackage.BOOK__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case SamplePackage.BOOK__LIBRARY:
				setLibrary((Library)null);
				return;
			case SamplePackage.BOOK__AUTHER:
				getAuther().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SamplePackage.BOOK__BOOK_NAME:
				return BOOK_NAME_EDEFAULT == null ? bookName != null : !BOOK_NAME_EDEFAULT.equals(bookName);
			case SamplePackage.BOOK__PAGES:
				return pages != PAGES_EDEFAULT;
			case SamplePackage.BOOK__CATEGORY:
				return category != CATEGORY_EDEFAULT;
			case SamplePackage.BOOK__LIBRARY:
				return basicGetLibrary() != null;
			case SamplePackage.BOOK__AUTHER:
				return auther != null && !auther.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (bookName: ");
		result.append(bookName);
		result.append(", pages: ");
		result.append(pages);
		result.append(", category: ");
		result.append(category);
		result.append(')');
		return result.toString();
	}

} //BookImpl
