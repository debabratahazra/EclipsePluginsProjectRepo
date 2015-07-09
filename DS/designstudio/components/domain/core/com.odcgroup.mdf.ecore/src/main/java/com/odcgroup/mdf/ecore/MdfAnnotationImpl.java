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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfAnnotationImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfAnnotationImpl extends MinimalEObjectImpl.Container implements MdfAnnotation {
    /**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @ordered
	 */
    protected static final String NAMESPACE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @ordered
	 */
    protected String namespace = NAMESPACE_EDEFAULT;

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
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @ordered
	 */
    protected EList properties;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected MdfAnnotationImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ANNOTATION;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public String getNamespace() {
		return namespace;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void setNamespace(String newNamespace) {
		String oldNamespace = namespace;
		namespace = newNamespace != null ? newNamespace.intern() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ANNOTATION__NAMESPACE, oldNamespace, namespace));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_ANNOTATION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList(MdfAnnotationProperty.class, this, MdfPackage.MDF_ANNOTATION__PROPERTIES);
		}
		return properties;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_ANNOTATION__PROPERTIES:
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
			case MdfPackage.MDF_ANNOTATION__NAMESPACE:
				return getNamespace();
			case MdfPackage.MDF_ANNOTATION__NAME:
				return getName();
			case MdfPackage.MDF_ANNOTATION__PROPERTIES:
				return getProperties();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_ANNOTATION__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case MdfPackage.MDF_ANNOTATION__NAME:
				setName((String)newValue);
				return;
			case MdfPackage.MDF_ANNOTATION__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
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
			case MdfPackage.MDF_ANNOTATION__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case MdfPackage.MDF_ANNOTATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MdfPackage.MDF_ANNOTATION__PROPERTIES:
				getProperties().clear();
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
			case MdfPackage.MDF_ANNOTATION__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case MdfPackage.MDF_ANNOTATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MdfPackage.MDF_ANNOTATION__PROPERTIES:
				return properties != null && !properties.isEmpty();
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
		result.append(" (namespace: ");
		result.append(namespace);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

    public MdfAnnotationProperty getProperty(String name) {
        Iterator it = getProperties().iterator();
        
        while (it.hasNext()) {
            MdfAnnotationProperty property = (MdfAnnotationProperty) it.next();
            if (property.getName().equals(name)) return property;
        }
        
        return null;
    }

} //MdfAnnotationImpl