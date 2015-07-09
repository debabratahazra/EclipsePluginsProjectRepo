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

import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAttributeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAttributeImpl#getDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfAttributeImpl extends MdfPropertyImpl implements MdfAttribute {
    /**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getType()

	 * @ordered
	 */
    protected MdfPrimitive type;

    /**
	 * The default value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDefault()

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected MdfAttributeImpl() {
        super();
        //setName("newAttribute");
        setType(PrimitivesDomain.DOMAIN.getPrimitive(PrimitivesDomain.STRING.getName()));
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ATTRIBUTE;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public MdfEntity getType() {
        if ((type instanceof EObject) && ((EObject)type).eIsProxy()) {
            InternalEObject oldType = (InternalEObject)type;
            type = (MdfPrimitive)eResolveProxy(oldType);
            if (type != oldType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_ATTRIBUTE__TYPE, oldType, type));
            }
        }
        return type;
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfPrimitive basicGetType() {
		return type;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setType(MdfPrimitive newType) {
		MdfPrimitive oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ATTRIBUTE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ATTRIBUTE__DEFAULT, oldDefault, default_));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_ATTRIBUTE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case MdfPackage.MDF_ATTRIBUTE__DEFAULT:
				return getDefault();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_ATTRIBUTE__TYPE:
				setType((MdfPrimitive)newValue);
				return;
			case MdfPackage.MDF_ATTRIBUTE__DEFAULT:
				setDefault((String)newValue);
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
			case MdfPackage.MDF_ATTRIBUTE__TYPE:
				setType((MdfPrimitive)null);
				return;
			case MdfPackage.MDF_ATTRIBUTE__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
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
			case MdfPackage.MDF_ATTRIBUTE__TYPE:
				return type != null;
			case MdfPackage.MDF_ATTRIBUTE__DEFAULT:
				return DEFAULT_EDEFAULT == null ? default_ != null : !DEFAULT_EDEFAULT.equals(default_);
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
		result.append(')');
		return result.toString();
	}

} //MdfAttributeImpl