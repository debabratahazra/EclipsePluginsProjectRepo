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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.example.sample.impl.LibraryImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.example.sample.impl.LibraryImpl#isStateOwner <em>State Owner</em>}</li>
 *   <li>{@link com.example.sample.impl.LibraryImpl#getBook <em>Book</em>}</li>
 *   <li>{@link com.example.sample.impl.LibraryImpl#getAuther <em>Auther</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LibraryImpl extends MinimalEObjectImpl.Container implements Library {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStateOwner() <em>State Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStateOwner()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATE_OWNER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isStateOwner() <em>State Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStateOwner()
	 * @generated
	 * @ordered
	 */
	protected boolean stateOwner = STATE_OWNER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBook() <em>Book</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBook()
	 * @generated
	 * @ordered
	 */
	protected EList<Book> book;

	/**
	 * The cached value of the '{@link #getAuther() <em>Auther</em>}' containment reference list.
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
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplePackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.LIBRARY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStateOwner() {
		return stateOwner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateOwner(boolean newStateOwner) {
		boolean oldStateOwner = stateOwner;
		stateOwner = newStateOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplePackage.LIBRARY__STATE_OWNER, oldStateOwner, stateOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Book> getBook() {
		if (book == null) {
			book = new EObjectContainmentWithInverseEList<Book>(Book.class, this, SamplePackage.LIBRARY__BOOK, SamplePackage.BOOK__LIBRARY);
		}
		return book;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Writer> getAuther() {
		if (auther == null) {
			auther = new EObjectContainmentWithInverseEList<Writer>(Writer.class, this, SamplePackage.LIBRARY__AUTHER, SamplePackage.WRITER__LIBRARY);
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
			case SamplePackage.LIBRARY__BOOK:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getBook()).basicAdd(otherEnd, msgs);
			case SamplePackage.LIBRARY__AUTHER:
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
			case SamplePackage.LIBRARY__BOOK:
				return ((InternalEList<?>)getBook()).basicRemove(otherEnd, msgs);
			case SamplePackage.LIBRARY__AUTHER:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamplePackage.LIBRARY__TYPE:
				return getType();
			case SamplePackage.LIBRARY__STATE_OWNER:
				return isStateOwner();
			case SamplePackage.LIBRARY__BOOK:
				return getBook();
			case SamplePackage.LIBRARY__AUTHER:
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
			case SamplePackage.LIBRARY__TYPE:
				setType((String)newValue);
				return;
			case SamplePackage.LIBRARY__STATE_OWNER:
				setStateOwner((Boolean)newValue);
				return;
			case SamplePackage.LIBRARY__BOOK:
				getBook().clear();
				getBook().addAll((Collection<? extends Book>)newValue);
				return;
			case SamplePackage.LIBRARY__AUTHER:
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
			case SamplePackage.LIBRARY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case SamplePackage.LIBRARY__STATE_OWNER:
				setStateOwner(STATE_OWNER_EDEFAULT);
				return;
			case SamplePackage.LIBRARY__BOOK:
				getBook().clear();
				return;
			case SamplePackage.LIBRARY__AUTHER:
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
			case SamplePackage.LIBRARY__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case SamplePackage.LIBRARY__STATE_OWNER:
				return stateOwner != STATE_OWNER_EDEFAULT;
			case SamplePackage.LIBRARY__BOOK:
				return book != null && !book.isEmpty();
			case SamplePackage.LIBRARY__AUTHER:
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
		result.append(" (type: ");
		result.append(type);
		result.append(", stateOwner: ");
		result.append(stateOwner);
		result.append(')');
		return result.toString();
	}

} //LibraryImpl
