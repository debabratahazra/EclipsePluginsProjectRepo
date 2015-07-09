/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.NamedType;
import com.odcgroup.page.metamodel.TranslationSupport;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl#getTranslationSupport <em>Translation Support</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl#getPropertiesHelpID <em>Properties Help ID</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.NamedTypeImpl#getConceptHelpID <em>Concept Help ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class NamedTypeImpl extends MinimalEObjectImpl.Container implements NamedType {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated NOT
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTranslationSupport() <em>Translation Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslationSupport()
	 * @generated
	 * @ordered
	 */
	protected static final TranslationSupport TRANSLATION_SUPPORT_EDEFAULT = TranslationSupport.NONE;

	/**
	 * The cached value of the '{@link #getTranslationSupport() <em>Translation Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslationSupport()
	 * @generated
	 * @ordered
	 */
	protected TranslationSupport translationSupport = TRANSLATION_SUPPORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertiesHelpID() <em>Properties Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertiesHelpID()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTIES_HELP_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertiesHelpID() <em>Properties Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertiesHelpID()
	 * @generated
	 * @ordered
	 */
	protected String propertiesHelpID = PROPERTIES_HELP_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getConceptHelpID() <em>Concept Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptHelpID()
	 * @generated
	 * @ordered
	 */
	protected static final String CONCEPT_HELP_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConceptHelpID() <em>Concept Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptHelpID()
	 * @generated
	 * @ordered
	 */
	protected String conceptHelpID = CONCEPT_HELP_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamedTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.NAMED_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newName
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setName(String newName) {
		String oldName = name;
		name = (newName != null) ? newName : NAME_EDEFAULT;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.NAMED_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDescription
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.NAMED_TYPE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslationSupport getTranslationSupport() {
		return translationSupport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslationSupport(TranslationSupport newTranslationSupport) {
		TranslationSupport oldTranslationSupport = translationSupport;
		translationSupport = newTranslationSupport == null ? TRANSLATION_SUPPORT_EDEFAULT : newTranslationSupport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT, oldTranslationSupport, translationSupport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertiesHelpID() {
		return propertiesHelpID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertiesHelpID(String newPropertiesHelpID) {
		String oldPropertiesHelpID = propertiesHelpID;
		propertiesHelpID = newPropertiesHelpID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.NAMED_TYPE__PROPERTIES_HELP_ID, oldPropertiesHelpID, propertiesHelpID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConceptHelpID() {
		return conceptHelpID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConceptHelpID(String newConceptHelpID) {
		String oldConceptHelpID = conceptHelpID;
		conceptHelpID = newConceptHelpID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.NAMED_TYPE__CONCEPT_HELP_ID, oldConceptHelpID, conceptHelpID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.NAMED_TYPE__NAME:
				return getName();
			case MetaModelPackage.NAMED_TYPE__DESCRIPTION:
				return getDescription();
			case MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT:
				return getTranslationSupport();
			case MetaModelPackage.NAMED_TYPE__PROPERTIES_HELP_ID:
				return getPropertiesHelpID();
			case MetaModelPackage.NAMED_TYPE__CONCEPT_HELP_ID:
				return getConceptHelpID();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.NAMED_TYPE__NAME:
				setName((String)newValue);
				return;
			case MetaModelPackage.NAMED_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT:
				setTranslationSupport((TranslationSupport)newValue);
				return;
			case MetaModelPackage.NAMED_TYPE__PROPERTIES_HELP_ID:
				setPropertiesHelpID((String)newValue);
				return;
			case MetaModelPackage.NAMED_TYPE__CONCEPT_HELP_ID:
				setConceptHelpID((String)newValue);
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
			case MetaModelPackage.NAMED_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MetaModelPackage.NAMED_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT:
				setTranslationSupport(TRANSLATION_SUPPORT_EDEFAULT);
				return;
			case MetaModelPackage.NAMED_TYPE__PROPERTIES_HELP_ID:
				setPropertiesHelpID(PROPERTIES_HELP_ID_EDEFAULT);
				return;
			case MetaModelPackage.NAMED_TYPE__CONCEPT_HELP_ID:
				setConceptHelpID(CONCEPT_HELP_ID_EDEFAULT);
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
			case MetaModelPackage.NAMED_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MetaModelPackage.NAMED_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case MetaModelPackage.NAMED_TYPE__TRANSLATION_SUPPORT:
				return translationSupport != TRANSLATION_SUPPORT_EDEFAULT;
			case MetaModelPackage.NAMED_TYPE__PROPERTIES_HELP_ID:
				return PROPERTIES_HELP_ID_EDEFAULT == null ? propertiesHelpID != null : !PROPERTIES_HELP_ID_EDEFAULT.equals(propertiesHelpID);
			case MetaModelPackage.NAMED_TYPE__CONCEPT_HELP_ID:
				return CONCEPT_HELP_ID_EDEFAULT == null ? conceptHelpID != null : !CONCEPT_HELP_ID_EDEFAULT.equals(conceptHelpID);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", translationSupport: ");
		result.append(translationSupport);
		result.append(", propertiesHelpID: ");
		result.append(propertiesHelpID);
		result.append(", ConceptHelpID: ");
		result.append(conceptHelpID);
		result.append(')');
		return result.toString();
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public final boolean translationSupported() {
		return ! TranslationSupport.NONE.equals(getTranslationSupport());
	}

} //NamedTypeImpl