/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Translation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getPools <em>Pools</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#isInverted <em>Inverted</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getFilename <em>Filename</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ProcessImpl#getTranslations <em>Translations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessImpl extends MinimalEObjectImpl.Container implements com.odcgroup.process.model.Process {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
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
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

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
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPools() <em>Pools</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPools()
	 * @generated
	 * @ordered
	 */
	protected EList pools;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList transitions;

	/**
	 * The default value of the '{@link #isInverted() <em>Inverted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInverted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INVERTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInverted() <em>Inverted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInverted()
	 * @generated
	 * @ordered
	 */
	protected boolean inverted = INVERTED_EDEFAULT;

	/**
	 * The default value of the '{@link #getFilename() <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilename()
	 * @generated
	 * @ordered
	 */
	protected static final String FILENAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilename() <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilename()
	 * @generated
	 * @ordered
	 */
	protected String filename = FILENAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String METAMODEL_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTranslations() <em>Translations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslations()
	 * @generated
	 * @ordered
	 */
	protected EList translations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ProcessPackage.Literals.PROCESS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPools() {
		if (pools == null) {
			pools = new EObjectContainmentEList(Pool.class, this, ProcessPackage.PROCESS__POOLS);
		}
		return pools;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList(Flow.class, this, ProcessPackage.PROCESS__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInverted(boolean newInverted) {
		boolean oldInverted = inverted;
		inverted = newInverted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__INVERTED, oldInverted, inverted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilename(String newFilename) {
		String oldFilename = filename;
		filename = newFilename;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__FILENAME, oldFilename, filename));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMetamodelVersion() {
		return metamodelVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetamodelVersion(String newMetamodelVersion) {
		String oldMetamodelVersion = metamodelVersion;
		metamodelVersion = newMetamodelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.PROCESS__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTranslations() {
		if (translations == null) {
			translations = new EObjectContainmentEList(Translation.class, this, ProcessPackage.PROCESS__TRANSLATIONS);
		}
		return translations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessPackage.PROCESS__POOLS:
				return ((InternalEList)getPools()).basicRemove(otherEnd, msgs);
			case ProcessPackage.PROCESS__TRANSITIONS:
				return ((InternalEList)getTransitions()).basicRemove(otherEnd, msgs);
			case ProcessPackage.PROCESS__TRANSLATIONS:
				return ((InternalEList)getTranslations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProcessPackage.PROCESS__NAME:
				return getName();
			case ProcessPackage.PROCESS__COMMENT:
				return getComment();
			case ProcessPackage.PROCESS__DESCRIPTION:
				return getDescription();
			case ProcessPackage.PROCESS__DISPLAY_NAME:
				return getDisplayName();
			case ProcessPackage.PROCESS__POOLS:
				return getPools();
			case ProcessPackage.PROCESS__TRANSITIONS:
				return getTransitions();
			case ProcessPackage.PROCESS__INVERTED:
				return isInverted() ? Boolean.TRUE : Boolean.FALSE;
			case ProcessPackage.PROCESS__FILENAME:
				return getFilename();
			case ProcessPackage.PROCESS__METAMODEL_VERSION:
				return getMetamodelVersion();
			case ProcessPackage.PROCESS__TRANSLATIONS:
				return getTranslations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProcessPackage.PROCESS__NAME:
				setName((String)newValue);
				return;
			case ProcessPackage.PROCESS__COMMENT:
				setComment((String)newValue);
				return;
			case ProcessPackage.PROCESS__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ProcessPackage.PROCESS__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case ProcessPackage.PROCESS__POOLS:
				getPools().clear();
				getPools().addAll((Collection)newValue);
				return;
			case ProcessPackage.PROCESS__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection)newValue);
				return;
			case ProcessPackage.PROCESS__INVERTED:
				setInverted(((Boolean)newValue).booleanValue());
				return;
			case ProcessPackage.PROCESS__FILENAME:
				setFilename((String)newValue);
				return;
			case ProcessPackage.PROCESS__METAMODEL_VERSION:
				setMetamodelVersion((String)newValue);
				return;
			case ProcessPackage.PROCESS__TRANSLATIONS:
				getTranslations().clear();
				getTranslations().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProcessPackage.PROCESS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__POOLS:
				getPools().clear();
				return;
			case ProcessPackage.PROCESS__TRANSITIONS:
				getTransitions().clear();
				return;
			case ProcessPackage.PROCESS__INVERTED:
				setInverted(INVERTED_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__FILENAME:
				setFilename(FILENAME_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__METAMODEL_VERSION:
				setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
				return;
			case ProcessPackage.PROCESS__TRANSLATIONS:
				getTranslations().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProcessPackage.PROCESS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ProcessPackage.PROCESS__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case ProcessPackage.PROCESS__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ProcessPackage.PROCESS__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case ProcessPackage.PROCESS__POOLS:
				return pools != null && !pools.isEmpty();
			case ProcessPackage.PROCESS__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case ProcessPackage.PROCESS__INVERTED:
				return inverted != INVERTED_EDEFAULT;
			case ProcessPackage.PROCESS__FILENAME:
				return FILENAME_EDEFAULT == null ? filename != null : !FILENAME_EDEFAULT.equals(filename);
			case ProcessPackage.PROCESS__METAMODEL_VERSION:
				return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
			case ProcessPackage.PROCESS__TRANSLATIONS:
				return translations != null && !translations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", comment: ");
		result.append(comment);
		result.append(", description: ");
		result.append(description);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", inverted: ");
		result.append(inverted);
		result.append(", filename: ");
		result.append(filename);
		result.append(", metamodelVersion: ");
		result.append(metamodelVersion);
		result.append(')');
		return result.toString();
	}

} //ProcessImpl
