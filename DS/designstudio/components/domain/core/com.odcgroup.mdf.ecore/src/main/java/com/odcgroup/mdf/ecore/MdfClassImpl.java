/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfClassImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfClassImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfClassImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfClassImpl#isSecured <em>Secured</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfClassImpl#isReferenceable <em>Referenceable</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfClassImpl extends MdfEntityImpl implements MdfClass {

    private static final ThreadLocal<Set<MdfClass>> CALLSTACK = new ThreadLocal<Set<MdfClass>>() {

        @Override
        protected Set<MdfClass> initialValue() {
            return new HashSet<MdfClass>();
        }
    };

    /**
	 * The cached value of the '{@link #getBaseClass() <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBaseClass()

	 * @ordered
	 */
    protected MdfClass baseClass;

    /**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAbstract()

	 * @ordered
	 */
    protected static final boolean ABSTRACT_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAbstract()

	 * @ordered
	 */
    protected boolean abstract_ = ABSTRACT_EDEFAULT;

    /**
     * The cached value of the '{@link #getProperties() <em>Properties</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getProperties()
     * @ordered
     */
    protected EList properties = null;

    /**
	 * The default value of the '{@link #isSecured() <em>Secured</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isSecured()

	 * @ordered
	 */
    protected static final boolean SECURED_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isSecured() <em>Secured</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isSecured()

	 * @ordered
	 */
    protected boolean secured = SECURED_EDEFAULT;

    /**
	 * The default value of the '{@link #isReferenceable() <em>Referenceable</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isReferenceable()

	 * @ordered
	 */
    protected static final boolean REFERENCEABLE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isReferenceable() <em>Referenceable</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isReferenceable()

	 * @ordered
	 */
    protected boolean referenceable = REFERENCEABLE_EDEFAULT;

    /**
	 * This is true if the Referenceable attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 * @ordered
	 */
    protected boolean referenceableESet;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
    protected MdfClassImpl() {
		super();
		//setName("NewClass");
	}


    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_CLASS;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public boolean isAbstract() {
		return abstract_;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_CLASS__ABSTRACT, oldAbstract, abstract_));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    public MdfClass getBaseClass() {
        if ((baseClass instanceof EObject) && ((EObject) baseClass).eIsProxy()) {
            InternalEObject oldBaseClass = (InternalEObject) baseClass;
            baseClass = (MdfClass) eResolveProxy(oldBaseClass);
            if (baseClass != oldBaseClass) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            MdfPackage.MDF_CLASS__BASE_CLASS, oldBaseClass,
                            baseClass));
            }
        }
        return baseClass;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public MdfClass basicGetBaseClass() {
		return baseClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setBaseClass(MdfClass newBaseClass) {
		MdfClass oldBaseClass = baseClass;
		baseClass = newBaseClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_CLASS__BASE_CLASS, oldBaseClass, baseClass));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public List getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList(MdfProperty.class, this, MdfPackage.MDF_CLASS__PROPERTIES);
		}
		return properties;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_CLASS__PROPERTIES:
				return ((InternalEList)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_CLASS__BASE_CLASS:
				if (resolve) return getBaseClass();
				return basicGetBaseClass();
			case MdfPackage.MDF_CLASS__ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case MdfPackage.MDF_CLASS__PROPERTIES:
				return getProperties();
			case MdfPackage.MDF_CLASS__REFERENCEABLE:
				return isReferenceable() ? Boolean.TRUE : Boolean.FALSE;
			case MdfPackage.MDF_CLASS__SECURED:
				return isSecured() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_CLASS__BASE_CLASS:
				setBaseClass((MdfClass)newValue);
				return;
			case MdfPackage.MDF_CLASS__ABSTRACT:
				setAbstract(((Boolean)newValue).booleanValue());
				return;
			case MdfPackage.MDF_CLASS__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_CLASS__SECURED:
				setSecured(((Boolean)newValue).booleanValue());
				return;
			case MdfPackage.MDF_CLASS__REFERENCEABLE:
				setReferenceable(((Boolean)newValue).booleanValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_CLASS__BASE_CLASS:
				setBaseClass((MdfClass)null);
				return;
			case MdfPackage.MDF_CLASS__ABSTRACT:
				setAbstract(ABSTRACT_EDEFAULT);
				return;
			case MdfPackage.MDF_CLASS__PROPERTIES:
				getProperties().clear();
				return;
			case MdfPackage.MDF_CLASS__SECURED:
				setSecured(SECURED_EDEFAULT);
				return;
			case MdfPackage.MDF_CLASS__REFERENCEABLE:
				unsetReferenceable();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_CLASS__BASE_CLASS:
				return baseClass != null;
			case MdfPackage.MDF_CLASS__ABSTRACT:
				return abstract_ != ABSTRACT_EDEFAULT;
			case MdfPackage.MDF_CLASS__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case MdfPackage.MDF_CLASS__SECURED:
				return secured != SECURED_EDEFAULT;
			case MdfPackage.MDF_CLASS__REFERENCEABLE:
				return isSetReferenceable();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (abstract: ");
		result.append(abstract_);
		result.append(", secured: ");
		result.append(secured);
		result.append(", referenceable: ");
		if (referenceableESet) result.append(referenceable); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#getPrimaryKeys(boolean)
     */
    public List getPrimaryKeys(boolean includeSuperClasses) {
        if (includeSuperClasses == false) {
            return getPrimaryKeys();
        }

        List allPrimaryKeys = new ArrayList();

        MdfClass baseClass = getBaseClass();

        if (baseClass != null) {
            // This code is here to prevent infinite loops in case the class
            // hierarchy is inconsistent
            if (!isProxy(baseClass)
                    && !CALLSTACK.get().contains(baseClass)) {
                try {
                    CALLSTACK.get().add(baseClass);
                    allPrimaryKeys.addAll(baseClass.getPrimaryKeys(true));
                } finally {
                    CALLSTACK.get().remove(baseClass);
                }
            }
        }

        allPrimaryKeys.addAll(getPrimaryKeys());
        return Collections.unmodifiableList(allPrimaryKeys);
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#getPrimaryKeys()
     */
    public List getPrimaryKeys() {
        List pks = new ArrayList();
        Iterator it = getProperties().iterator();

        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
            if (p.isPrimaryKey()) {
                pks.add(p);
            }
        }

        return Collections.unmodifiableList(pks);
    }
    
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.metamodel.MdfClass#getBusinessKeys(boolean)
	 */
	public List getBusinessKeys(boolean includeSuperClasses) {
		List properties = getProperties(includeSuperClasses);
        List businessKeys = new ArrayList();
        for (Iterator iter = properties.iterator(); iter.hasNext(); ) {
        	MdfProperty property = (MdfProperty)iter.next();
        	if (property.isBusinessKey()) {
        		businessKeys.add(property);
        	}
        }
        return Collections.unmodifiableList(businessKeys);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.metamodel.MdfClass#getAttributesRef()
	 */
	public List getAttributesRef() {
		// DS-1934: if the class has primary keys, use them
		// if not uses business keys
		List primaryKeys = getPrimaryKeys(true);
		if (primaryKeys.size() > 0) {
			return primaryKeys;
		}
		return getBusinessKeys(true);
	}

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#getProperties(boolean)
     */
    public List getProperties(boolean includeSuperClasses) {
        if (!includeSuperClasses) {
            return getProperties();
        }

        List allProperties = new ArrayList();

        MdfClass baseClass = getBaseClass();

        if (baseClass != null) {
            // This code is here to prevent infinite loops in case the class
            // hierarchy is inconsistent
            if (!isProxy(baseClass)
                    && !CALLSTACK.get().contains(baseClass)) {
                try {
                    CALLSTACK.get().add(baseClass);
                    allProperties.addAll(baseClass.getProperties(true));
                } finally {
                    CALLSTACK.get().remove(baseClass);
                }
            }
        }

        allProperties.addAll(getProperties());
        return Collections.unmodifiableList(allProperties);
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#getProperty(java.lang.String)
     */
    public MdfProperty getProperty(String name) {
        Iterator it = getProperties().iterator();
        while (it.hasNext()) {
            MdfProperty property = (MdfProperty) it.next();
            if (property.getName()!=null && property.getName().equals(name)) return property;
        }

        MdfClass baseClass = getBaseClass();
        return (baseClass == null) ? null : baseClass.getProperty(name);
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#getVisibility()
     */
    public int getVisibility() {
    	throw new UnsupportedOperationException("Removed during the mdf-api/core split");
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#hasPrimaryKey(boolean)
     */
    public boolean hasPrimaryKey(boolean includeSuperClasses) {
        if (hasPrimaryKey()) return true;

        if (includeSuperClasses) {
            MdfClass baseClass = getBaseClass();
            if (baseClass != null) {
                // This code is here to prevent infinite loops in case the class
                // hierarchy is inconsistent
                if (!isProxy(baseClass)
                        && !CALLSTACK.get().contains(baseClass)) {
                    try {
                        CALLSTACK.get().add(baseClass);
                        return baseClass.hasPrimaryKey(true);
                    } finally {
                        CALLSTACK.get().remove(baseClass);
                    }
                }
            }
        }

        return false;
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfClass#hasPrimaryKey()
     */
    public boolean hasPrimaryKey() {
        Iterator it = getProperties().iterator();

        while (it.hasNext()) {
            MdfProperty p = (MdfProperty) it.next();
            if (p.isPrimaryKey()) return true;
        }

        return false;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public boolean isSecured() {
		return secured;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setSecured(boolean newSecured) {
		boolean oldSecured = secured;
		secured = newSecured;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_CLASS__SECURED, oldSecured, secured));
	}


    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public boolean isReferenceable() {
        return !isAbstract() && hasPrimaryKey(true);
    }


    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public void setReferenceable(boolean newReferenceable) {
        throw new UnsupportedOperationException();
    }


    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void unsetReferenceable() {
		boolean oldReferenceable = referenceable;
		boolean oldReferenceableESet = referenceableESet;
		referenceable = REFERENCEABLE_EDEFAULT;
		referenceableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MdfPackage.MDF_CLASS__REFERENCEABLE, oldReferenceable, REFERENCEABLE_EDEFAULT, oldReferenceableESet));
	}


    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public boolean isSetReferenceable() {
		return referenceableESet;
	}

} // MdfClassImpl
