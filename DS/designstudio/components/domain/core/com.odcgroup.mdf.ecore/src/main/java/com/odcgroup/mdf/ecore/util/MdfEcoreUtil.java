package com.odcgroup.mdf.ecore.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.ecore.MdfPrimitiveImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author pkk
 *
 */
public class MdfEcoreUtil {
	
	public static MdfClassImpl getMdfClassProxy(MdfName qname) {
		return (MdfClassImpl) getMdfEntityProxy(MdfPackage.eINSTANCE.getMdfClass(), qname);
	}
	
	public static MdfPrimitiveImpl getMdfPrimitiveProxy(MdfName qname) {
		return (MdfPrimitiveImpl) getMdfEntityProxy(MdfPackage.eINSTANCE.getMdfPrimitive(), qname);
	}
	
	public static MdfEnumerationImpl getMdfEnumerationProxy(MdfName qname) {
		return (MdfEnumerationImpl) getMdfEntityProxy(MdfPackage.eINSTANCE.getMdfPrimitive(), qname);
	}
	
	public static MdfDatasetImpl getMdfDatasetProxy(MdfName qname) {
		return (MdfDatasetImpl) getMdfEntityProxy(MdfPackage.eINSTANCE.getMdfDataset(), qname);
	}
	
	public static MdfBusinessTypeImpl getMdfBusinessTypeProxy(Resource repository, MdfName qname) {
		return (MdfBusinessTypeImpl) getMdfEntityProxy(MdfPackage.eINSTANCE.getMdfBusinessType(), qname);
	}
	
	public static boolean isProxy(MdfModelElement model) {
		if (model instanceof EObject && ((EObject)model).eIsProxy()) {
			InternalEObject proxy = (InternalEObject)model;
			if ( (MdfNameURIUtil.getMdfName(proxy.eProxyURI()).toString().equals(model.toString()))){
				return true;
			}
		}
		return false;
	}
	
	public static MdfModelElement resolve(MdfModelElement element, Resource resource) {
		MdfModelElement model = null;
		if (resource == null) {
			return PrimitivesDomain.DOMAIN.getEntity(element.getQualifiedName().getLocalName());
		}
		if (element != null && isProxy(element)) {
			final InternalEObject proxy = (InternalEObject) element;
        	final MdfName qname = MdfNameURIUtil.getMdfName(proxy.eProxyURI());
			try {
				model = getMdfEntity(resource, qname);
			} catch(RuntimeException e) {
				// if we have a resource set, which is not part of an IOfsProject, we might get a runtime exception here.
				// this happens e.g. during validation, but we can ignore it and simply fail the resolution
				return null;
			}
		}
		return (MdfModelElement) model;
	}
	
	public static MdfEntity getMdfEntity(Resource resource, MdfName qname) {
		if (resource != null && qname != null) {
			DomainRepository repository = null;
			if (resource.getURI().isPlatform()) {
				IFile file = OfsResourceHelper.getFile(resource);
				IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
				repository = DomainRepository.getInstance(ofsProject);
			} else {
				ResourceSet rs = resource.getResourceSet();
				repository = DomainRepository.getInstance(rs);				
			}
			if(repository==null) return null;
			MdfDomain domain = repository.getDomain(qname.getDomain());
			if(domain==null) return null;
			return domain.getEntity(qname.getLocalName());
		}
		return null;
	}
	
	// TODO Remove DUPLICATION... virtually same code (?) is in com.odcgroup.domain.linking.DomainLinkingService.createProxy(EClass, MdfName)
	private static MdfModelElement getMdfEntityProxy(EClass klass, MdfName qname) {
		MdfModelElementImpl model =  null;
	    try {
			if (qname != null) {
				MdfFactory modelFactory = MdfFactory.eINSTANCE;
				InternalEObject eObject = (InternalEObject)modelFactory.create(klass);
				eObject.eSetProxyURI(MdfNameURIUtil.createURI(qname));
				model = (MdfModelElementImpl) eObject;
				model.setName(qname.getLocalName());
			}
		} catch (IllegalArgumentException e) {
		    return model;
		}
		return model;
	}
	
    public static List<MdfEntity> getAllEntities(Resource resource) {
    	if (resource == null) {
    		return Collections.EMPTY_LIST;
    	}
    	List<MdfEntity> entities = new ArrayList<MdfEntity>();
    	DomainRepository repository = DomainRepository.getInstance(resource.getResourceSet());
    	for(MdfDomain domain : repository.getDomains()) {
   			entities.addAll(domain.getEntities());
    	}    	
    	return entities;    	
	}
}
