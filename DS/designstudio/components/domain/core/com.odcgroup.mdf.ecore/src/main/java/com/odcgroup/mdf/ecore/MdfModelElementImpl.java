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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfModelElementImpl#getDeprecationInfo <em>Deprecation Info</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfModelElementImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfModelElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.mdf.ecore.MdfModelElementImpl#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 * </p>
 */
public abstract class MdfModelElementImpl extends MinimalEObjectImpl.Container implements
        MdfModelElement {

	private static final long serialVersionUID = -5263686625409037685L;

	static final private Logger logger = LoggerFactory.getLogger(MdfModelElementImpl.class);

    /**
	 * The cached value of the '{@link #getDeprecationInfo() <em>Deprecation Info</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDeprecationInfo()

	 * @ordered
	 */
    protected EObject deprecationInfo;

    /**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDocumentation()

	 * @ordered
	 */
    protected static final String DOCUMENTATION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDocumentation()

	 * @ordered
	 */
    protected String documentation = DOCUMENTATION_EDEFAULT;

    /**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()

	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = "";

    /**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()

	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

    /**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAnnotations()

	 * @ordered
	 */
    protected EList annotations;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected MdfModelElementImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_MODEL_ELEMENT;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_MODEL_ELEMENT__NAME, oldName, name));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    public abstract MdfName getQualifiedName();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public List getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList(MdfAnnotation.class, this, MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS);
		}
		return annotations;
	}

    public MdfAnnotation getAnnotation(String namespace, String name) {
        Iterator it = getAnnotations().iterator();

        while (it.hasNext()) {
            MdfAnnotation annot = (MdfAnnotation) it.next();
            if (namespace.equals(annot.getNamespace())
                    && name.equals(annot.getName())) {
                return annot;
            }
        }

        return null;
    }

    public List getAnnotations(String namespace) {
        List annotationsForNamespace = new ArrayList();
        Iterator it = getAnnotations().iterator();

        while (it.hasNext()) {
            MdfAnnotation annot = (MdfAnnotation) it.next();
            if (namespace.equals(annot.getNamespace())) {
                annotationsForNamespace.add(annot);
            }
        }

        return annotationsForNamespace;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO:
				return basicSetDeprecationInfo(null, msgs);
			case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
				return ((InternalEList)getAnnotations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    public boolean isDeprecated() {
        return (deprecationInfo != null);
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public EObject getDeprecationInfo() {
		return deprecationInfo;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public NotificationChain basicSetDeprecationInfo(
    		EObject newDeprecationInfo, NotificationChain msgs) {
    	EObject oldDeprecationInfo = deprecationInfo;
		deprecationInfo = newDeprecationInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO, oldDeprecationInfo, newDeprecationInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setDeprecationInfo(EObject newDeprecationInfo) {
		if (newDeprecationInfo != deprecationInfo) {
			NotificationChain msgs = null;
			if (deprecationInfo != null)
				msgs = ((InternalEObject)deprecationInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO, null, msgs);
			if (newDeprecationInfo != null)
				msgs = ((InternalEObject)newDeprecationInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO, null, msgs);
			msgs = basicSetDeprecationInfo(newDeprecationInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO, newDeprecationInfo, newDeprecationInfo));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public String getDocumentation() {
    	return documentation;
	} 
    
	private ITranslation getTranslation() {
		if (this.eContainer()!=null) {
			IProject project = OfsResourceHelper.getProject(this.eResource());
			if (project != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(project);
				if (manager != null) {
					return manager.getTranslation(this);
				}
			}
		}
		return null;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void setDocumentation(String newDocumentation) {
        String oldDocumentation = documentation;
        documentation = newDocumentation;
        if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION, oldDocumentation, documentation));
    }

	/**
	 * @param newDocumentation
	 * @param oldDocumentation
	 */
	private void setDefaultDocumentation(String newDocumentation, String oldDocumentation) {
		// The translation is not (yet?) supported, keep the old way
		// It means that some definitions are missing in the plugin.xml
		documentation = newDocumentation;
		if (eNotificationRequired())
		                eNotify(new ENotificationImpl(this, 
		                                Notification.SET, MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION, 
		                                oldDocumentation, newDocumentation));
	}

    	

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO:
				return getDeprecationInfo();
			case MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION:
				return getDocumentation(); 
			case MdfPackage.MDF_MODEL_ELEMENT__NAME:
				return getName();
			case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
				return getAnnotations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO:
				setDeprecationInfo((EObject)newValue);
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION:
				setDocumentation((String)newValue); 
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public void eUnset(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO:
				setDeprecationInfo((EObject)null);
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
				getAnnotations().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MdfPackage.MDF_MODEL_ELEMENT__DEPRECATION_INFO:
				return deprecationInfo != null;
			case MdfPackage.MDF_MODEL_ELEMENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case MdfPackage.MDF_MODEL_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MdfPackage.MDF_MODEL_ELEMENT__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
     * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#toString()
     */
    public String toString() {
        if (eIsProxy()) {
        	try {
        		return MdfNameURIUtil.getMdfName(eProxyURI()).toString();
        	} catch (Exception e) {
        		// DS-7137: MdfNameURIUtil frequently throw IllegalArgumentException - but toString() should be reliable and still return.. something, so:
        		return super.toString();
        	}
        } else if (getQualifiedName() != null){
            return getQualifiedName().toString();
        } else {
        	return getName();
        }
    }

    /**
     * Support for extensions, deprecated.
     */
    @Deprecated
    private final Map extensions = new HashMap();

    /**
     * Support for extensions, deprecated.
     */
    @Deprecated
    public void setExtension(String namespace, Object extension) {
        if (extension != null) {
            extensions.put(namespace, extension);
        } else {
            extensions.remove(namespace);
        }
    }

    /**
     * Support for extensions, deprecated.
     * 
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getExtension(java.lang.String)
     */
    @Deprecated
    public Object getExtension(String namespace) {
        return extensions.get(namespace);
    }

    /**
     * Support for extensions, deprecated.
     * 
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getExtensions()
     */
    @Deprecated
    public Map getExtensions() {
        return Collections.unmodifiableMap(extensions);
    }

    /**
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eResolveProxy(org.eclipse.emf.ecore.InternalEObject)
	 */
    public EObject eResolveProxy(InternalEObject proxy) {    	
        URI proxyURI = proxy.eProxyURI();
        if (ModelURIConverter.SCHEME.equals(proxyURI.scheme())) {
        	// with URI fragments, let the superclass handle
        	return eResolveProxyResourceURI(proxy);
        } else if (MdfNameURIUtil.isMdfNameURI(proxyURI)) {
        	return eResolveProxyMdfNameURI(proxy, proxyURI);
        } else {
        	return eResolveProxyUnknownURI(proxy);
        }
    }
    
    // methods factored out from main eResolveProxy() above just so it jumps out in a Profiler
    private final EObject eResolveProxyResourceURI(InternalEObject proxy) {
    	return super.eResolveProxy(proxy);
    }
    private final EObject eResolveProxyMdfNameURI(InternalEObject proxy, URI proxyURI) {
    	// This should soon never be called anymore.. DomainLinkingService doesn't anymore, only MdfEcoreUtil is left doing & needing this
		MdfName proxyMdfName = MdfNameURIUtil.getMdfName(proxyURI);
		if("mml".equals(proxyMdfName.getDomain())) {
			return (MdfEntityImpl) PrimitivesDomain.DOMAIN.getEntity(proxyMdfName.getLocalName());
		}
        return proxy;
    }
    private final EObject eResolveProxyUnknownURI(InternalEObject proxy) {
    	// super(), NOT just return proxy; of course - as that would mean that they would never resolved!! :(
    	return super.eResolveProxy(proxy);
    }
    
    // TODO to be removed at the end of the mdf-api/core split
    public List getComments() { return new ArrayList(); }
} // MdfModelElementImpl
