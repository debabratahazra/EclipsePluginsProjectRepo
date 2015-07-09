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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getImportedDomains <em>Imported Domains</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getClasses <em>Classes</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getDatasets <em>Datasets</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getBusinessTypes <em>Business Types</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getEnumerations <em>Enumerations</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getPrimitives <em>Primitives</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfDomainImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 * </ul>
 * </p>
 */
public class MdfDomainImpl extends MdfModelElementImpl implements MdfDomain {
	
    private static final long serialVersionUID = 707304883368578165L;

	/**
	 * The cached value of the '{@link #getImportedDomains() <em>Imported Domains</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getImportedDomains()

	 * @ordered
	 */
    protected EList importedDomains;

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
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getClasses()

	 * @ordered
	 */
    protected EList classes;

    /**
	 * The cached value of the '{@link #getDatasets() <em>Datasets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDatasets()

	 * @ordered
	 */
    protected EList datasets;

    /**
	 * The cached value of the '{@link #getBusinessTypes() <em>Business Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessTypes()

	 * @ordered
	 */
	protected EList businessTypes;

				/**
	 * The cached value of the '{@link #getEnumerations() <em>Enumerations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerations()

	 * @ordered
	 */
	protected EList enumerations;

				/**
	 * The cached value of the '{@link #getPrimitives() <em>Primitives</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPrimitives()

	 * @ordered
	 */
    protected EList primitives;

				/**
	 * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()

	 * @ordered
	 */
	protected static final String METAMODEL_VERSION_EDEFAULT = null;

				/**
	 * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()

	 * @ordered
	 */
	protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 */
    protected MdfDomainImpl() {
		super();
		//setName("NewDomain");
		setNamespace("http://www.odcgroup.com/new-domain");
	}

    /**
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getQualifiedName()
     */
    public MdfName getQualifiedName() {
    	if(getName()!=null) {
    		return MdfNameFactory.createMdfName(getName());
    	} else {
    		return null;
    	}
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_DOMAIN;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getImportedDomains() {
		if (importedDomains == null) {
			importedDomains = new EObjectResolvingEList(MdfDomain.class, this, MdfPackage.MDF_DOMAIN__IMPORTED_DOMAINS);
		}
		return importedDomains;
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
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DOMAIN__NAMESPACE, oldNamespace, namespace));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getPrimitives() {
		if (primitives == null) {
			primitives = new EObjectContainmentEList(MdfPrimitive.class, this, MdfPackage.MDF_DOMAIN__PRIMITIVES);
		}
		return primitives;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public String getMetamodelVersion() {
		return metamodelVersion;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public void setMetamodelVersion(String newMetamodelVersion) {
		String oldMetamodelVersion = metamodelVersion;
		metamodelVersion = newMetamodelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_DOMAIN__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getClasses() {
		if (classes == null) {
			classes = new EObjectContainmentEList(MdfClass.class, this, MdfPackage.MDF_DOMAIN__CLASSES);
		}
		return classes;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public List getDatasets() {
		if (datasets == null) {
			datasets = new EObjectContainmentEList(MdfDataset.class, this, MdfPackage.MDF_DOMAIN__DATASETS);
		}
		return datasets;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public List getBusinessTypes() {
		if (businessTypes == null) {
			businessTypes = new EObjectContainmentEList(MdfBusinessType.class, this, MdfPackage.MDF_DOMAIN__BUSINESS_TYPES);
		}
		return businessTypes;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public List getEnumerations() {
		if (enumerations == null) {
			enumerations = new EObjectContainmentEList(MdfEnumeration.class, this, MdfPackage.MDF_DOMAIN__ENUMERATIONS);
		}
		return enumerations;
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_DOMAIN__CLASSES:
				return ((InternalEList)getClasses()).basicRemove(otherEnd, msgs);
			case MdfPackage.MDF_DOMAIN__DATASETS:
				return ((InternalEList)getDatasets()).basicRemove(otherEnd, msgs);
			case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
				return ((InternalEList)getBusinessTypes()).basicRemove(otherEnd, msgs);
			case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
				return ((InternalEList)getEnumerations()).basicRemove(otherEnd, msgs);
			case MdfPackage.MDF_DOMAIN__PRIMITIVES:
				return ((InternalEList)getPrimitives()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_DOMAIN__IMPORTED_DOMAINS:
				return getImportedDomains();
			case MdfPackage.MDF_DOMAIN__NAMESPACE:
				return getNamespace();
			case MdfPackage.MDF_DOMAIN__CLASSES:
				return getClasses();
			case MdfPackage.MDF_DOMAIN__DATASETS:
				return getDatasets();
			case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
				return getBusinessTypes();
			case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
				return getEnumerations();
			case MdfPackage.MDF_DOMAIN__PRIMITIVES:
				return getPrimitives();
			case MdfPackage.MDF_DOMAIN__METAMODEL_VERSION:
				return getMetamodelVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_DOMAIN__IMPORTED_DOMAINS:
				getImportedDomains().clear();
				getImportedDomains().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__CLASSES:
				getClasses().clear();
				getClasses().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__DATASETS:
				getDatasets().clear();
				getDatasets().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
				getBusinessTypes().clear();
				getBusinessTypes().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
				getEnumerations().clear();
				getEnumerations().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__PRIMITIVES:
				getPrimitives().clear();
				getPrimitives().addAll((Collection)newValue);
				return;
			case MdfPackage.MDF_DOMAIN__METAMODEL_VERSION:
				setMetamodelVersion((String)newValue);
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
			case MdfPackage.MDF_DOMAIN__IMPORTED_DOMAINS:
				getImportedDomains().clear();
				return;
			case MdfPackage.MDF_DOMAIN__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case MdfPackage.MDF_DOMAIN__CLASSES:
				getClasses().clear();
				return;
			case MdfPackage.MDF_DOMAIN__DATASETS:
				getDatasets().clear();
				return;
			case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
				getBusinessTypes().clear();
				return;
			case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
				getEnumerations().clear();
				return;
			case MdfPackage.MDF_DOMAIN__PRIMITIVES:
				getPrimitives().clear();
				return;
			case MdfPackage.MDF_DOMAIN__METAMODEL_VERSION:
				setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
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
			case MdfPackage.MDF_DOMAIN__IMPORTED_DOMAINS:
				return importedDomains != null && !importedDomains.isEmpty();
			case MdfPackage.MDF_DOMAIN__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case MdfPackage.MDF_DOMAIN__CLASSES:
				return classes != null && !classes.isEmpty();
			case MdfPackage.MDF_DOMAIN__DATASETS:
				return datasets != null && !datasets.isEmpty();
			case MdfPackage.MDF_DOMAIN__BUSINESS_TYPES:
				return businessTypes != null && !businessTypes.isEmpty();
			case MdfPackage.MDF_DOMAIN__ENUMERATIONS:
				return enumerations != null && !enumerations.isEmpty();
			case MdfPackage.MDF_DOMAIN__PRIMITIVES:
				return primitives != null && !primitives.isEmpty();
			case MdfPackage.MDF_DOMAIN__METAMODEL_VERSION:
				return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
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
		result.append(", metamodelVersion: ");
		result.append(metamodelVersion);
		result.append(')');
		return result.toString();
	}

    public MdfDataset getDataset(String name) {
        Iterator it = getDatasets().iterator();
        while (it.hasNext()) {
            MdfDataset dataset = (MdfDataset) it.next();
            if (dataset.getName().equals(name)) return dataset;
        }
        return null;
    }

    public List getEntities() {
        List entities = new ArrayList();
        if (classes != null) entities.addAll(classes);
        if (datasets != null) entities.addAll(datasets);
        if (enumerations != null) entities.addAll(enumerations);
        if (businessTypes != null) entities.addAll(businessTypes);
        if (primitives != null) entities.addAll(primitives);
        return Collections.unmodifiableList(entities);
    }

    public MdfEntity getEntity(String name) {
        Iterator it = getEntities().iterator();
        while (it.hasNext()) {
            MdfEntity entity = (MdfEntity) it.next();
            if (entity.getName().equals(name)) return entity;
        }
        return null;
    }

    public MdfClass getClass(String name) {
        Iterator it = getClasses().iterator();
        while (it.hasNext()) {
            MdfClass klass = (MdfClass) it.next();
            if (klass.getName().equals(name)) return klass;
        }
        return null;
    }

    public MdfPrimitive getPrimitive(String name) {
        Iterator it = getPrimitives().iterator();
        while (it.hasNext()) {
            MdfPrimitive primitive = (MdfPrimitive) it.next();
            if (primitive.getName().equals(name)) return primitive;
        }
        return null;
    }

	
	public MdfBusinessType getBusinessType(String name) {
        Iterator it = getBusinessTypes().iterator();
        while (it.hasNext()) {
        	MdfBusinessType primitive = (MdfBusinessType) it.next();
            if (primitive.getName().equals(name)) return primitive;
        }
        return null;
	}
	
	public MdfEnumeration getEnumeration(String name) {
        Iterator it = getEnumerations().iterator();
        while (it.hasNext()) {
        	MdfEnumeration primitive = (MdfEnumeration) it.next();
            if (primitive.getName().equals(name)) return primitive;
        }
        return null;
	}
	
} //MdfDomainImpl