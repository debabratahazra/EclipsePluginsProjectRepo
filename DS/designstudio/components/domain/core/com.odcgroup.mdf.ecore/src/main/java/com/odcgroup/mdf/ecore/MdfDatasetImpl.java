/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dataset</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetImpl#getBaseClass <em>Base Class</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetImpl#isLinked <em>Linked</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDatasetImpl#isSync <em>Sync</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfDatasetImpl extends MdfEntityImpl implements MdfDataset {
    /**
	 * The cached value of the '{@link #getBaseClass() <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBaseClass()

	 * @ordered
	 */
    protected MdfClass baseClass;

    /**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProperties()

	 * @ordered
	 */
    protected EList properties;

    /**
	 * The default value of the '{@link #isLinked() <em>Linked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLinked()

	 * @ordered
	 */
	protected static final boolean LINKED_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isLinked() <em>Linked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLinked()

	 * @ordered
	 */
	protected boolean linked = LINKED_EDEFAULT;

				/**
	 * The default value of the '{@link #isSync() <em>Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSync()

	 * @ordered
	 */
	protected static final boolean SYNC_EDEFAULT = false;

				/**
	 * The cached value of the '{@link #isSync() <em>Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSync()

	 * @ordered
	 */
	protected boolean sync = SYNC_EDEFAULT;

				/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected MdfDatasetImpl() {
        super();
        //setName("NewDataset");
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_DATASET;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public MdfClass getBaseClass() {
        if ((baseClass instanceof EObject) && ((EObject)baseClass).eIsProxy()) {
        	MdfClass oldBaseClass = baseClass;
            baseClass = (MdfClass)eResolveProxy((InternalEObject)oldBaseClass);
            if (baseClass != oldBaseClass) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, MdfPackage.MDF_DATASET__BASE_CLASS, oldBaseClass, baseClass));
            }
        }
        return baseClass;
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public MdfClass basicGetBaseClass() {
		return baseClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setBaseClass(MdfClass newBaseClass) {
		MdfClass oldBaseClass = baseClass;
		baseClass = newBaseClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET__BASE_CLASS, oldBaseClass, baseClass));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList(MdfDatasetProperty.class, this, MdfPackage.MDF_DATASET__PROPERTIES);
		}
		return properties;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isLinked() {
		return linked;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setLinked(boolean newLinked) {
		boolean oldLinked = linked;
		linked = newLinked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET__LINKED, oldLinked, linked));
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public boolean isSync() {
		return sync;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setSync(boolean newSync) {
		boolean oldSync = sync;
		sync = newSync;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DATASET__SYNC, oldSync, sync));
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET__PROPERTIES:
				return ((InternalEList)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET__BASE_CLASS:
				if (resolve) return getBaseClass();
				return basicGetBaseClass();
			case MdfPackage.MDF_DATASET__PROPERTIES:
				return getProperties();
			case MdfPackage.MDF_DATASET__LINKED:
				return isLinked() ? Boolean.TRUE : Boolean.FALSE;
			case MdfPackage.MDF_DATASET__SYNC:
				return isSync() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_DATASET__BASE_CLASS:
				setBaseClass((MdfClass)newValue);
				return;
			case MdfPackage.MDF_DATASET__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DATASET__LINKED:
				setLinked(((Boolean)newValue).booleanValue());
				return;
			case MdfPackage.MDF_DATASET__SYNC:
				setSync(((Boolean)newValue).booleanValue());
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
			case MdfPackage.MDF_DATASET__BASE_CLASS:
				setBaseClass((MdfClass)null);
				return;
			case MdfPackage.MDF_DATASET__PROPERTIES:
				getProperties().clear();
				return;
			case MdfPackage.MDF_DATASET__LINKED:
				setLinked(LINKED_EDEFAULT);
				return;
			case MdfPackage.MDF_DATASET__SYNC:
				setSync(SYNC_EDEFAULT);
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
			case MdfPackage.MDF_DATASET__BASE_CLASS:
				return baseClass != null;
			case MdfPackage.MDF_DATASET__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case MdfPackage.MDF_DATASET__LINKED:
				return linked != LINKED_EDEFAULT;
			case MdfPackage.MDF_DATASET__SYNC:
				return sync != SYNC_EDEFAULT;
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
		result.append(" (linked: ");
		result.append(linked);
		result.append(", sync: ");
		result.append(sync);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.metamodel.MdfDataset#getProperty(java.lang.String)
	 */
	public MdfDatasetProperty getProperty(String name) {
        Iterator it = getProperties().iterator();
        while (it.hasNext()) {
            MdfDatasetProperty property = (MdfDatasetProperty) it.next();
            if (property.getName().equals(name)) return property;
        }
        return null;
    }

} //MdfDatasetImpl