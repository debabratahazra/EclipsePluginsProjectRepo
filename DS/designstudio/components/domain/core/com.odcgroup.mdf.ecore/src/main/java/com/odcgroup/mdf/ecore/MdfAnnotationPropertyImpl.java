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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl#isCDATA <em>CDATA</em>}</li>
 * </ul>
 * </p>
 *
 */
@SuppressWarnings("serial")
public class MdfAnnotationPropertyImpl extends MinimalEObjectImpl.Container implements MdfAnnotationProperty {
    /**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()

	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()

	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

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
	 * The default value of the '{@link #isCDATA() <em>CDATA</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCDATA()

	 * @ordered
	 */
	protected static final boolean CDATA_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isCDATA() <em>CDATA</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCDATA()

	 * @ordered
	 */
	protected boolean cDATA = CDATA_EDEFAULT;

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected MdfAnnotationPropertyImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ANNOTATION_PROPERTY;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setName(String newName) {
		String oldName = name;
		name = newName != null ? newName.intern() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ANNOTATION_PROPERTY__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ANNOTATION_PROPERTY__VALUE, oldValue, value));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isCDATA() {
		return cDATA;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setCDATA(boolean newCDATA) {
		boolean oldCDATA = cDATA;
		cDATA = newCDATA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ANNOTATION_PROPERTY__CDATA, oldCDATA, cDATA));
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_ANNOTATION_PROPERTY__NAME:
				return getName();
			case MdfPackage.MDF_ANNOTATION_PROPERTY__VALUE:
				return getValue();
			case MdfPackage.MDF_ANNOTATION_PROPERTY__CDATA:
				return isCDATA() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_ANNOTATION_PROPERTY__NAME:
				setName((String)newValue);
				return;
			case MdfPackage.MDF_ANNOTATION_PROPERTY__VALUE:
				setValue((String)newValue);
				return;
			case MdfPackage.MDF_ANNOTATION_PROPERTY__CDATA:
				setCDATA(((Boolean)newValue).booleanValue());
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
			case MdfPackage.MDF_ANNOTATION_PROPERTY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MdfPackage.MDF_ANNOTATION_PROPERTY__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case MdfPackage.MDF_ANNOTATION_PROPERTY__CDATA:
				setCDATA(CDATA_EDEFAULT);
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
			case MdfPackage.MDF_ANNOTATION_PROPERTY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MdfPackage.MDF_ANNOTATION_PROPERTY__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case MdfPackage.MDF_ANNOTATION_PROPERTY__CDATA:
				return cDATA != CDATA_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(", cDATA: ");
		result.append(cDATA);
		result.append(')');
		return result.toString();
	}

    public MdfAnnotation getParentAnnotation() {
        return (MdfAnnotation) eContainer();
    }

} //MdfAnnotationPropertyImpl