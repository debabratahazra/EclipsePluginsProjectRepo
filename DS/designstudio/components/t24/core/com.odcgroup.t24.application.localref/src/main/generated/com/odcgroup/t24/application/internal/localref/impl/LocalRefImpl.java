/**
 */
package com.odcgroup.t24.application.internal.localref.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.application.internal.localref.LocalRef;
import com.odcgroup.t24.application.internal.localref.LocalrefPackage;
import com.odcgroup.t24.application.internal.localref.NoInputChange;
import com.odcgroup.t24.application.internal.localref.VettingTable;
import com.odcgroup.translation.translationDsl.Translations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getLocalRefID <em>Local Ref ID</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getMaximumChars <em>Maximum Chars</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getMinimumChars <em>Minimum Chars</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getCharType <em>Char Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#isOverridePossible <em>Override Possible</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getNoInputChange <em>No Input Change</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#isDefaultPossible <em>Default Possible</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getApplicationEnrich <em>Application Enrich</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getVettingTable <em>Vetting Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getVirtualTable <em>Virtual Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getApplicationVET <em>Application VET</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalRefImpl extends EObjectImpl implements LocalRef {
	/**
	 * The default value of the '{@link #getLocalRefID() <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalRefID()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCAL_REF_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocalRefID() <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalRefID()
	 * @generated
	 * @ordered
	 */
	protected String localRefID = LOCAL_REF_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaximumChars() <em>Maximum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximumChars()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAXIMUM_CHARS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaximumChars() <em>Maximum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximumChars()
	 * @generated
	 * @ordered
	 */
	protected Integer maximumChars = MAXIMUM_CHARS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinimumChars() <em>Minimum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimumChars()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MINIMUM_CHARS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinimumChars() <em>Minimum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimumChars()
	 * @generated
	 * @ordered
	 */
	protected Integer minimumChars = MINIMUM_CHARS_EDEFAULT;

	/**
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANDATORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean mandatory = MANDATORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getCharType() <em>Char Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharType()
	 * @generated
	 * @ordered
	 */
	protected static final String CHAR_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCharType() <em>Char Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharType()
	 * @generated
	 * @ordered
	 */
	protected String charType = CHAR_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isOverridePossible() <em>Override Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverridePossible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OVERRIDE_POSSIBLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOverridePossible() <em>Override Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverridePossible()
	 * @generated
	 * @ordered
	 */
	protected boolean overridePossible = OVERRIDE_POSSIBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNoInputChange() <em>No Input Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNoInputChange()
	 * @generated
	 * @ordered
	 */
	protected static final NoInputChange NO_INPUT_CHANGE_EDEFAULT = NoInputChange.NONE;

	/**
	 * The cached value of the '{@link #getNoInputChange() <em>No Input Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNoInputChange()
	 * @generated
	 * @ordered
	 */
	protected NoInputChange noInputChange = NO_INPUT_CHANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDefaultPossible() <em>Default Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultPossible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_POSSIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDefaultPossible() <em>Default Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultPossible()
	 * @generated
	 * @ordered
	 */
	protected boolean defaultPossible = DEFAULT_POSSIBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getApplicationEnrich() <em>Application Enrich</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationEnrich()
	 * @generated
	 * @ordered
	 */
	protected static final String APPLICATION_ENRICH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getApplicationEnrich() <em>Application Enrich</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationEnrich()
	 * @generated
	 * @ordered
	 */
	protected String applicationEnrich = APPLICATION_ENRICH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getShortName() <em>Short Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortName()
	 * @generated
	 * @ordered
	 */
	protected EList<Translations> shortName;

	/**
	 * The cached value of the '{@link #getVettingTable() <em>Vetting Table</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVettingTable()
	 * @generated
	 * @ordered
	 */
	protected EList<VettingTable> vettingTable;

	/**
	 * The default value of the '{@link #getVirtualTable() <em>Virtual Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVirtualTable()
	 * @generated
	 * @ordered
	 */
	protected static final String VIRTUAL_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVirtualTable() <em>Virtual Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVirtualTable()
	 * @generated
	 * @ordered
	 */
	protected String virtualTable = VIRTUAL_TABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getApplicationVET() <em>Application VET</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationVET()
	 * @generated
	 * @ordered
	 */
	protected MdfClass applicationVET;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LocalrefPackage.Literals.LOCAL_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalRefID() {
		return localRefID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalRefID(String newLocalRefID) {
		String oldLocalRefID = localRefID;
		localRefID = newLocalRefID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__LOCAL_REF_ID, oldLocalRefID, localRefID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMaximumChars() {
		return maximumChars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaximumChars(Integer newMaximumChars) {
		Integer oldMaximumChars = maximumChars;
		maximumChars = newMaximumChars;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__MAXIMUM_CHARS, oldMaximumChars, maximumChars));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMinimumChars() {
		return minimumChars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinimumChars(Integer newMinimumChars) {
		Integer oldMinimumChars = minimumChars;
		minimumChars = newMinimumChars;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__MINIMUM_CHARS, oldMinimumChars, minimumChars));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMandatory(boolean newMandatory) {
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__MANDATORY, oldMandatory, mandatory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCharType() {
		return charType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharType(String newCharType) {
		String oldCharType = charType;
		charType = newCharType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__CHAR_TYPE, oldCharType, charType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOverridePossible() {
		return overridePossible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverridePossible(boolean newOverridePossible) {
		boolean oldOverridePossible = overridePossible;
		overridePossible = newOverridePossible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__OVERRIDE_POSSIBLE, oldOverridePossible, overridePossible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoInputChange getNoInputChange() {
		return noInputChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoInputChange(NoInputChange newNoInputChange) {
		NoInputChange oldNoInputChange = noInputChange;
		noInputChange = newNoInputChange == null ? NO_INPUT_CHANGE_EDEFAULT : newNoInputChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__NO_INPUT_CHANGE, oldNoInputChange, noInputChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefaultPossible() {
		return defaultPossible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultPossible(boolean newDefaultPossible) {
		boolean oldDefaultPossible = defaultPossible;
		defaultPossible = newDefaultPossible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__DEFAULT_POSSIBLE, oldDefaultPossible, defaultPossible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass getApplicationVET() {
		if (applicationVET != null && ((EObject)applicationVET).eIsProxy()) {
			InternalEObject oldApplicationVET = (InternalEObject)applicationVET;
			applicationVET = (MdfClass)eResolveProxy(oldApplicationVET);
			if (applicationVET != oldApplicationVET) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LocalrefPackage.LOCAL_REF__APPLICATION_VET, oldApplicationVET, applicationVET));
			}
		}
		return applicationVET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfClass basicGetApplicationVET() {
		return applicationVET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApplicationVET(MdfClass newApplicationVET) {
		MdfClass oldApplicationVET = applicationVET;
		applicationVET = newApplicationVET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__APPLICATION_VET, oldApplicationVET, applicationVET));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getApplicationEnrich() {
		return applicationEnrich;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApplicationEnrich(String newApplicationEnrich) {
		String oldApplicationEnrich = applicationEnrich;
		applicationEnrich = newApplicationEnrich;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__APPLICATION_ENRICH, oldApplicationEnrich, applicationEnrich));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VettingTable> getVettingTable() {
		if (vettingTable == null) {
			vettingTable = new EObjectContainmentEList<VettingTable>(VettingTable.class, this, LocalrefPackage.LOCAL_REF__VETTING_TABLE);
		}
		return vettingTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translations> getShortName() {
		if (shortName == null) {
			shortName = new EObjectContainmentEList<Translations>(Translations.class, this, LocalrefPackage.LOCAL_REF__SHORT_NAME);
		}
		return shortName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVirtualTable() {
		return virtualTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVirtualTable(String newVirtualTable) {
		String oldVirtualTable = virtualTable;
		virtualTable = newVirtualTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LocalrefPackage.LOCAL_REF__VIRTUAL_TABLE, oldVirtualTable, virtualTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LocalrefPackage.LOCAL_REF__SHORT_NAME:
				return ((InternalEList<?>)getShortName()).basicRemove(otherEnd, msgs);
			case LocalrefPackage.LOCAL_REF__VETTING_TABLE:
				return ((InternalEList<?>)getVettingTable()).basicRemove(otherEnd, msgs);
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
			case LocalrefPackage.LOCAL_REF__LOCAL_REF_ID:
				return getLocalRefID();
			case LocalrefPackage.LOCAL_REF__MAXIMUM_CHARS:
				return getMaximumChars();
			case LocalrefPackage.LOCAL_REF__MINIMUM_CHARS:
				return getMinimumChars();
			case LocalrefPackage.LOCAL_REF__MANDATORY:
				return isMandatory();
			case LocalrefPackage.LOCAL_REF__CHAR_TYPE:
				return getCharType();
			case LocalrefPackage.LOCAL_REF__OVERRIDE_POSSIBLE:
				return isOverridePossible();
			case LocalrefPackage.LOCAL_REF__NO_INPUT_CHANGE:
				return getNoInputChange();
			case LocalrefPackage.LOCAL_REF__DEFAULT_POSSIBLE:
				return isDefaultPossible();
			case LocalrefPackage.LOCAL_REF__APPLICATION_ENRICH:
				return getApplicationEnrich();
			case LocalrefPackage.LOCAL_REF__SHORT_NAME:
				return getShortName();
			case LocalrefPackage.LOCAL_REF__VETTING_TABLE:
				return getVettingTable();
			case LocalrefPackage.LOCAL_REF__VIRTUAL_TABLE:
				return getVirtualTable();
			case LocalrefPackage.LOCAL_REF__APPLICATION_VET:
				if (resolve) return getApplicationVET();
				return basicGetApplicationVET();
			case LocalrefPackage.LOCAL_REF__DESCRIPTION:
				return getDescription();
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
			case LocalrefPackage.LOCAL_REF__LOCAL_REF_ID:
				setLocalRefID((String)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__MAXIMUM_CHARS:
				setMaximumChars((Integer)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__MINIMUM_CHARS:
				setMinimumChars((Integer)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__MANDATORY:
				setMandatory((Boolean)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__CHAR_TYPE:
				setCharType((String)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__OVERRIDE_POSSIBLE:
				setOverridePossible((Boolean)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__NO_INPUT_CHANGE:
				setNoInputChange((NoInputChange)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__DEFAULT_POSSIBLE:
				setDefaultPossible((Boolean)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__APPLICATION_ENRICH:
				setApplicationEnrich((String)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__SHORT_NAME:
				getShortName().clear();
				getShortName().addAll((Collection<? extends Translations>)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__VETTING_TABLE:
				getVettingTable().clear();
				getVettingTable().addAll((Collection<? extends VettingTable>)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__VIRTUAL_TABLE:
				setVirtualTable((String)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__APPLICATION_VET:
				setApplicationVET((MdfClass)newValue);
				return;
			case LocalrefPackage.LOCAL_REF__DESCRIPTION:
				setDescription((String)newValue);
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
			case LocalrefPackage.LOCAL_REF__LOCAL_REF_ID:
				setLocalRefID(LOCAL_REF_ID_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__MAXIMUM_CHARS:
				setMaximumChars(MAXIMUM_CHARS_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__MINIMUM_CHARS:
				setMinimumChars(MINIMUM_CHARS_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__MANDATORY:
				setMandatory(MANDATORY_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__CHAR_TYPE:
				setCharType(CHAR_TYPE_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__OVERRIDE_POSSIBLE:
				setOverridePossible(OVERRIDE_POSSIBLE_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__NO_INPUT_CHANGE:
				setNoInputChange(NO_INPUT_CHANGE_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__DEFAULT_POSSIBLE:
				setDefaultPossible(DEFAULT_POSSIBLE_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__APPLICATION_ENRICH:
				setApplicationEnrich(APPLICATION_ENRICH_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__SHORT_NAME:
				getShortName().clear();
				return;
			case LocalrefPackage.LOCAL_REF__VETTING_TABLE:
				getVettingTable().clear();
				return;
			case LocalrefPackage.LOCAL_REF__VIRTUAL_TABLE:
				setVirtualTable(VIRTUAL_TABLE_EDEFAULT);
				return;
			case LocalrefPackage.LOCAL_REF__APPLICATION_VET:
				setApplicationVET((MdfClass)null);
				return;
			case LocalrefPackage.LOCAL_REF__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case LocalrefPackage.LOCAL_REF__LOCAL_REF_ID:
				return LOCAL_REF_ID_EDEFAULT == null ? localRefID != null : !LOCAL_REF_ID_EDEFAULT.equals(localRefID);
			case LocalrefPackage.LOCAL_REF__MAXIMUM_CHARS:
				return MAXIMUM_CHARS_EDEFAULT == null ? maximumChars != null : !MAXIMUM_CHARS_EDEFAULT.equals(maximumChars);
			case LocalrefPackage.LOCAL_REF__MINIMUM_CHARS:
				return MINIMUM_CHARS_EDEFAULT == null ? minimumChars != null : !MINIMUM_CHARS_EDEFAULT.equals(minimumChars);
			case LocalrefPackage.LOCAL_REF__MANDATORY:
				return mandatory != MANDATORY_EDEFAULT;
			case LocalrefPackage.LOCAL_REF__CHAR_TYPE:
				return CHAR_TYPE_EDEFAULT == null ? charType != null : !CHAR_TYPE_EDEFAULT.equals(charType);
			case LocalrefPackage.LOCAL_REF__OVERRIDE_POSSIBLE:
				return overridePossible != OVERRIDE_POSSIBLE_EDEFAULT;
			case LocalrefPackage.LOCAL_REF__NO_INPUT_CHANGE:
				return noInputChange != NO_INPUT_CHANGE_EDEFAULT;
			case LocalrefPackage.LOCAL_REF__DEFAULT_POSSIBLE:
				return defaultPossible != DEFAULT_POSSIBLE_EDEFAULT;
			case LocalrefPackage.LOCAL_REF__APPLICATION_ENRICH:
				return APPLICATION_ENRICH_EDEFAULT == null ? applicationEnrich != null : !APPLICATION_ENRICH_EDEFAULT.equals(applicationEnrich);
			case LocalrefPackage.LOCAL_REF__SHORT_NAME:
				return shortName != null && !shortName.isEmpty();
			case LocalrefPackage.LOCAL_REF__VETTING_TABLE:
				return vettingTable != null && !vettingTable.isEmpty();
			case LocalrefPackage.LOCAL_REF__VIRTUAL_TABLE:
				return VIRTUAL_TABLE_EDEFAULT == null ? virtualTable != null : !VIRTUAL_TABLE_EDEFAULT.equals(virtualTable);
			case LocalrefPackage.LOCAL_REF__APPLICATION_VET:
				return applicationVET != null;
			case LocalrefPackage.LOCAL_REF__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
		result.append(" (localRefID: ");
		result.append(localRefID);
		result.append(", maximumChars: ");
		result.append(maximumChars);
		result.append(", minimumChars: ");
		result.append(minimumChars);
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", charType: ");
		result.append(charType);
		result.append(", overridePossible: ");
		result.append(overridePossible);
		result.append(", noInputChange: ");
		result.append(noInputChange);
		result.append(", defaultPossible: ");
		result.append(defaultPossible);
		result.append(", applicationEnrich: ");
		result.append(applicationEnrich);
		result.append(", virtualTable: ");
		result.append(virtualTable);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //LocalRefImpl
