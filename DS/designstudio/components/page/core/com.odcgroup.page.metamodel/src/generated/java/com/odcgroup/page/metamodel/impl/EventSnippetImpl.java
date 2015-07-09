/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.metamodel.EventSnippet;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.SnippetType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Snippet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.EventSnippetImpl#getSnippets <em>Snippets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventSnippetImpl extends MinimalEObjectImpl.Container implements EventSnippet {
	/**
	 * The cached value of the '{@link #getSnippets() <em>Snippets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippets()
	 * @generated
	 * @ordered
	 */
	protected EList<SnippetType> snippets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventSnippetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.EVENT_SNIPPET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SnippetType> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectResolvingEList<SnippetType>(SnippetType.class, this, MetaModelPackage.EVENT_SNIPPET__SNIPPETS);
		}
		return snippets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.EVENT_SNIPPET__SNIPPETS:
				return getSnippets();
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
			case MetaModelPackage.EVENT_SNIPPET__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends SnippetType>)newValue);
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
			case MetaModelPackage.EVENT_SNIPPET__SNIPPETS:
				getSnippets().clear();
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
			case MetaModelPackage.EVENT_SNIPPET__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EventSnippetImpl
