/**
 */
package com.example.sample.impl;

import com.example.sample.Book;
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
 * An implementation of the model object '<em><b>Writer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.example.sample.impl.WriterImpl#getWriterName <em>Writer Name</em>}</li>
 *   <li>{@link com.example.sample.impl.WriterImpl#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.example.sample.impl.WriterImpl#getBook <em>Book</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WriterImpl extends MinimalEObjectImpl.Container implements Writer {
	/**
	 * The default value of the '{@link #getWriterName() <em>Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWriterName()
	 * @generated
	 * @ordered
	 */
	protected static final String WRITER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWriterName() <em>Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWriterName()
	 * @generated
	 * @ordered
	 */
	protected String writerName = WRITER_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBook() <em>Book</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBook()
	 * @generated
	 * @ordered
	 */
	protected EList<Book> book;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WriterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplePackage.Literals.WRITER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWriterName() {
		return writerName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWriterName(String newWriterName) {
		String oldWriterName = writerName;
		writerName = newWriterName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.WRITER__WRITER_NAME, oldWriterName, writerName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library getLibrary() {
		if (eContainerFeatureID() != SamplePackage.WRITER__LIBRARY) return null;
		return (Library)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Library basicGetLibrary() {
		if (eContainerFeatureID() != SamplePackage.WRITER__LIBRARY) return null;
		return (Library)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibrary(Library newLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLibrary, SamplePackage.WRITER__LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(Library newLibrary) {
		if (newLibrary != eInternalContainer() || (eContainerFeatureID() != SamplePackage.WRITER__LIBRARY && newLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLibrary != null)
				msgs = ((InternalEObject)newLibrary).eInverseAdd(this, SamplePackage.LIBRARY__AUTHER, Library.class, msgs);
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.WRITER__LIBRARY, newLibrary, newLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Book> getBook() {
		if (book == null) {
			book = new EObjectWithInverseResolvingEList.ManyInverse<Book>(Book.class, this, SamplePackage.WRITER__BOOK, SamplePackage.BOOK__AUTHER);
		}
		return book;
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
			case SamplePackage.WRITER__LIBRARY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLibrary((Library)otherEnd, msgs);
			case SamplePackage.WRITER__BOOK:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getBook()).basicAdd(otherEnd, msgs);
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
			case SamplePackage.WRITER__LIBRARY:
				return basicSetLibrary(null, msgs);
			case SamplePackage.WRITER__BOOK:
				return ((InternalEList<?>)getBook()).basicRemove(otherEnd, msgs);
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
			case SamplePackage.WRITER__LIBRARY:
				return eInternalContainer().eInverseRemove(this, SamplePackage.LIBRARY__AUTHER, Library.class, msgs);
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
			case SamplePackage.WRITER__WRITER_NAME:
				return getWriterName();
			case SamplePackage.WRITER__LIBRARY:
				if (resolve) return getLibrary();
				return basicGetLibrary();
			case SamplePackage.WRITER__BOOK:
				return getBook();
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
			case SamplePackage.WRITER__WRITER_NAME:
				setWriterName((String)newValue);
				return;
			case SamplePackage.WRITER__LIBRARY:
				setLibrary((Library)newValue);
				return;
			case SamplePackage.WRITER__BOOK:
				getBook().clear();
				getBook().addAll((Collection<? extends Book>)newValue);
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
			case SamplePackage.WRITER__WRITER_NAME:
				setWriterName(WRITER_NAME_EDEFAULT);
				return;
			case SamplePackage.WRITER__LIBRARY:
				setLibrary((Library)null);
				return;
			case SamplePackage.WRITER__BOOK:
				getBook().clear();
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
			case SamplePackage.WRITER__WRITER_NAME:
				return WRITER_NAME_EDEFAULT == null ? writerName != null : !WRITER_NAME_EDEFAULT.equals(writerName);
			case SamplePackage.WRITER__LIBRARY:
				return basicGetLibrary() != null;
			case SamplePackage.WRITER__BOOK:
				return book != null && !book.isEmpty();
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
		result.append(" (writerName: ");
		result.append(writerName);
		result.append(')');
		return result.toString();
	}

} //WriterImpl
