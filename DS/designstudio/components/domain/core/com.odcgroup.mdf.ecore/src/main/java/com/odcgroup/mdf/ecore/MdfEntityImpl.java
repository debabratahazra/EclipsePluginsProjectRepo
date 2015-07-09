/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.workbench.core.repository.ModelURIConverter;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfEntityImpl extends MdfModelElementImpl implements
        MdfEntity {

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected MdfEntityImpl() {
		super();
	}

    public MdfDomain getParentDomain() {
        if (eContainer() instanceof MdfDomain) {
            return (MdfDomain) eContainer();
        } else {
            return null;
        }
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfEntity#isPrimitiveType()
     */
    public boolean isPrimitiveType() {
        return false;
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfEntity#getQualifiedName()
     */
    public MdfName getQualifiedName() {
    	if(eContainer instanceof MdfDomain) {
    		String safeName = getName();
    		if (StringUtils.isBlank(name))
    			safeName = "ThingThatHasNoNameYet"; // also @see DomainQualifiedNameProvider
			return MdfNameFactory.createMdfName(((MdfDomain)eContainer).getName(), safeName);
    	}
        if (eIsProxy()) {
        	if(MdfNameURIUtil.isMdfNameURI(eProxyURI())){
            	return MdfNameURIUtil.getMdfName(eProxyURI());
        	}
        	return null;
        } else {
            MdfDomain parent = getParentDomain();

            if (parent != null) {
                return MdfNameFactory.createMdfName(parent.getName(), getName());
            }

            return null;
        }
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_ENTITY;
	}

    /**
     * @param entity
     * @return
     */
    public static boolean isProxy(MdfEntity entity) {
        if ((entity instanceof EObject) && ((EObject) entity).eIsProxy()) {
        	InternalEObject proxy = (InternalEObject)entity;
        	URI proxyURI = proxy.eProxyURI();
        	// let go proxies with "resource" scheme
        	if (proxyURI.scheme().equals(ModelURIConverter.SCHEME)){
        		return false;
        	} else if (MdfNameURIUtil.isMdfNameURI(proxyURI) && MdfNameURIUtil.getMdfName(proxyURI).equals(entity.getQualifiedName())) {
        		return false;
        	}
            return true;
        } else {
            return false;
        }
    }

} // MdfEntityImpl
