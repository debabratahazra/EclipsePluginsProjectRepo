/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dataset Derived Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl#getDefault <em>Default</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetDerivedPropertyImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfDatasetDerivedPropertyImpl extends MdfDatasetPropertyImpl implements MdfDatasetDerivedProperty {
	/**
	 * The default value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated NOT
	 * @ordered
	 */
	protected static final String DEFAULT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()

	 * @ordered
	 */
	protected String default_ = DEFAULT_EDEFAULT;

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
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()

	 * @ordered
	 */
	protected MdfEntity type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected MdfDatasetDerivedPropertyImpl() {
		super();
        //setName("newCalculatedAttribute");
        setType((MdfPrimitive) PrimitivesDomain.DOMAIN.getEntity("mml:string"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public String getDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setDefault(String newDefault) {
		String oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT, oldDefault, default_));
	}
	

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setMultiplicity(int newMultiplicity) {
        MdfMultiplicity oldMultiplicity = MdfMultiplicity.get(multiplicity);
        multiplicity = newMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY, oldMultiplicity, multiplicity));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY, oldMultiplicity, multiplicity));
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfEntity getType() {
		if (type != null && ((EObject)type).eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (MdfEntity)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	@Override
	public MdfEntity basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setType(MdfEntity newType) {
		MdfEntity oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT:
				return getDefault();
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY:
                return MdfMultiplicity.get(getMultiplicity());
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE:
				if (resolve) return getType();
				return basicGetType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT:
				setDefault((String)newValue);
				return;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY:
                setMultiplicity(((MdfMultiplicity)newValue).getValue());
				return;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE:
				setType((MdfEntity)newValue);
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
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY:
				setMultiplicity(MULTIPLICITY_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE:
				setType((MdfEntity)null);
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
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__DEFAULT:
				return DEFAULT_EDEFAULT == null ? default_ != null : !DEFAULT_EDEFAULT.equals(default_);
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__MULTIPLICITY:
				return multiplicity != MULTIPLICITY_EDEFAULT;
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY__TYPE:
				return type != null;
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
		result.append(" (default: ");
		result.append(default_);
		result.append(", multiplicity: ");
		result.append(multiplicity);
		result.append(')');
		return result.toString();
	}

} //MdfDatasetDerivedPropertyImpl
