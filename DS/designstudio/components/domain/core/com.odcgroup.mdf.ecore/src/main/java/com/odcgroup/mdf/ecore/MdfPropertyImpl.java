/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfPropertyImpl#isBusinessKey <em>Business Key</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfPropertyImpl#isRequired <em>Required</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfPropertyImpl#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfPropertyImpl#isPrimaryKey <em>Primary Key</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public abstract class MdfPropertyImpl extends MdfModelElementImpl implements MdfProperty {
    /**
	 * The default value of the '{@link #isBusinessKey() <em>Business Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBusinessKey()

	 * @ordered
	 */
	protected static final boolean BUSINESS_KEY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBusinessKey() <em>Business Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBusinessKey()

	 * @ordered
	 */
	protected boolean businessKey = BUSINESS_KEY_EDEFAULT;

				/**
	 * The default value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isRequired()

	 * @ordered
	 */
    protected static final boolean REQUIRED_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isRequired() <em>Required</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isRequired()

	 * @ordered
	 */
    protected boolean required = REQUIRED_EDEFAULT;

    /**
     * The default value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMultiplicity()
     * @ordered
     */
    protected static final int MULTIPLICITY_EDEFAULT = MdfMultiplicity.ONE;

    /**
     * The cached value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMultiplicity()
     * @ordered
     */
    protected int multiplicity = MULTIPLICITY_EDEFAULT;

    /**
	 * The default value of the '{@link #isPrimaryKey() <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrimaryKey()

	 * @ordered
	 */
	protected static final boolean PRIMARY_KEY_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isPrimaryKey() <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrimaryKey()

	 * @ordered
	 */
	protected boolean primaryKey = PRIMARY_KEY_EDEFAULT;

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected MdfPropertyImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public abstract MdfEntity basicGetType();

    public MdfClass getParentClass() {
        if (eContainer() instanceof MdfClass) {
            return (MdfClass) eContainer();
        } else {
            return null;
        }
    }

	/**
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getQualifiedName()
     */
    public MdfName getQualifiedName() {
        MdfClass parent = getParentClass();

        if (parent != null) {
            MdfName parentqn = parent.getQualifiedName();

            if (parentqn != null) {
                StringBuffer localName = new StringBuffer();
                localName.append(parentqn.getLocalName()).append('#').append(getName());
                return MdfNameFactory.createMdfName(parentqn.getDomain(), localName.toString());
            }
        }

        return null;
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_PROPERTY;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isBusinessKey() {
		return businessKey;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setBusinessKey(boolean newBusinessKey) {
		boolean oldBusinessKey = businessKey;
		businessKey = newBusinessKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_PROPERTY__BUSINESS_KEY, oldBusinessKey, businessKey));
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public boolean isRequired() {
		return required;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setRequired(boolean newRequired) {
		boolean oldRequired = required;
		required = newRequired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_PROPERTY__REQUIRED, oldRequired, required));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public int getMultiplicity() {
        return multiplicity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setMultiplicity(com.odcgroup.mdf.metamodel.MdfMultiplicity newMultiplicity) {
        MdfMultiplicity oldMultiplicity = MdfMultiplicity.get(multiplicity);
        multiplicity = newMultiplicity == null ? MULTIPLICITY_EDEFAULT : newMultiplicity.getValue();
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_PROPERTY__MULTIPLICITY, oldMultiplicity, newMultiplicity));
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public boolean isPrimaryKey() {
		return primaryKey;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setPrimaryKey(boolean newPrimaryKey) {
		boolean oldPrimaryKey = primaryKey;
		primaryKey = newPrimaryKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_PROPERTY__PRIMARY_KEY, oldPrimaryKey, primaryKey));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setMultiplicity(int newMultiplicity) {
        MdfMultiplicity oldMultiplicity = MdfMultiplicity.get(multiplicity);
        multiplicity = newMultiplicity;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_PROPERTY__MULTIPLICITY, oldMultiplicity, MdfMultiplicity.get(multiplicity)));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MdfPackage.MDF_PROPERTY__REQUIRED:
                return isRequired() ? Boolean.TRUE : Boolean.FALSE;
            case MdfPackage.MDF_PROPERTY__MULTIPLICITY:
                return MdfMultiplicity.get(getMultiplicity());
            case MdfPackage.MDF_PROPERTY__PRIMARY_KEY:
                return isPrimaryKey() ? Boolean.TRUE : Boolean.FALSE;
            case MdfPackage.MDF_PROPERTY__BUSINESS_KEY:
                return isBusinessKey() ? Boolean.TRUE : Boolean.FALSE;
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case MdfPackage.MDF_PROPERTY__REQUIRED:
                setRequired(((Boolean)newValue).booleanValue());
                return;
            case MdfPackage.MDF_PROPERTY__MULTIPLICITY:
                setMultiplicity(((MdfMultiplicity)newValue).getValue());
                return;
            case MdfPackage.MDF_PROPERTY__PRIMARY_KEY:
                setPrimaryKey(((Boolean)newValue).booleanValue());
                return;
            case MdfPackage.MDF_PROPERTY__BUSINESS_KEY:
                setBusinessKey(((Boolean)newValue).booleanValue());
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_PROPERTY__BUSINESS_KEY:
				setBusinessKey(BUSINESS_KEY_EDEFAULT);
				return;
			case MdfPackage.MDF_PROPERTY__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case MdfPackage.MDF_PROPERTY__MULTIPLICITY:
				setMultiplicity(MULTIPLICITY_EDEFAULT);
				return;
			case MdfPackage.MDF_PROPERTY__PRIMARY_KEY:
				setPrimaryKey(PRIMARY_KEY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_PROPERTY__BUSINESS_KEY:
				return businessKey != BUSINESS_KEY_EDEFAULT;
			case MdfPackage.MDF_PROPERTY__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case MdfPackage.MDF_PROPERTY__MULTIPLICITY:
				return multiplicity != MULTIPLICITY_EDEFAULT;
			case MdfPackage.MDF_PROPERTY__PRIMARY_KEY:
				return primaryKey != PRIMARY_KEY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (businessKey: ");
		result.append(businessKey);
		result.append(", required: ");
		result.append(required);
		result.append(", multiplicity: ");
		result.append(multiplicity);
		result.append(", primaryKey: ");
		result.append(primaryKey);
		result.append(')');
		return result.toString();
	}

    public int getAccess() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
    public int getVisibility() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }

} //MdfPropertyImpl
