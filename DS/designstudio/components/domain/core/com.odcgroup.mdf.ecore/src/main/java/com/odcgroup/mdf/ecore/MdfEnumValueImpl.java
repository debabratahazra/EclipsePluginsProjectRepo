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
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfEnumValueImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfEnumValueImpl extends MdfModelElementImpl implements MdfEnumValue {
    /**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getValue()

	 * @ordered
	 */
    protected static final String VALUE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getValue()

	 * @ordered
	 */
    protected String value = VALUE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected MdfEnumValueImpl() {
        super();
        //setName("NEW_VALUE");
        //setValue("NewValue");
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getQualifiedName()
     */
    public MdfName getQualifiedName() {
        MdfEnumeration parent = getParentEnumeration();

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
		return MdfPackage.Literals.MDF_ENUM_VALUE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String getValue() {
		return value;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ENUM_VALUE__VALUE, oldValue, value));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_ENUM_VALUE__VALUE:
				return getValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_ENUM_VALUE__VALUE:
				setValue((String)newValue);
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
			case MdfPackage.MDF_ENUM_VALUE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case MdfPackage.MDF_ENUM_VALUE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

    public MdfEnumeration getParentEnumeration() {
    	EObject container = eContainer();
    	if (container instanceof MdfEnumeration) {
            return (MdfEnumeration) container;
        } else {
            return null;
        }
    }

} //MdfEnumValueImpl