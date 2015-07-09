package com.odcgroup.aaa.ui.internal.model.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author yan
 */
public class MetaDictionnaryHelper {

    public static MdfDomain getAAAMetadictionaryEntities(IOfsProject ofsProject) {
	return getAAAMetadictionaryModel(ofsProject, AAACore.getFindroot()+AAACore.AAA_ENTITIES_MODEL, IOfsProject.SCOPE_ALL);
    }
    
    public static MdfDomain getAAAMetadictionaryEnums(IOfsProject ofsProject) {
	return getAAAMetadictionaryModel(ofsProject, AAACore.getFindroot()+AAACore.AAA_ENUMS_MODEL, IOfsProject.SCOPE_ALL);
    }
    
    public static MdfDomain getAAAMetadictionaryBusinessTypes(IOfsProject ofsProject) {
	return getAAAMetadictionaryModel(ofsProject, AAACore.getFindroot()+AAACore.AAA_BUSINESS_TYPE_MODEL, IOfsProject.SCOPE_ALL);
    }
    
    private static MdfDomain getAAAMetadictionaryModel(IOfsProject ofsProject, String model, int scope) {
		try {
			IOfsElement aaaElement = ofsProject.getOfsModelResource(URI.createURI("resource:///" + model));
			if (aaaElement instanceof IOfsModelResource) {
				IOfsModelResource aaaModelResource = (IOfsModelResource)aaaElement;
				if((aaaModelResource.getScope() & scope) == 0) return null;
				EList<EObject> elist = aaaModelResource.getEMFModel();
				if (elist.size() != 0) {
					EObject eobject = elist.get(0);
					if (eobject instanceof MdfDomain) {
						return (MdfDomain)eobject;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
    }
    
    public static String getAAAMetadictionaryFolder(IOfsProject ofsProject) {
		try {
			IOfsModelFolder domains = ofsProject.getModelFolder("mml");
			return domains.getResource().getLocation().addTrailingSeparator().toPortableString() + AAACore.getFindroot() + AAACore.AAA_MODELS_FOLDER;
		} catch (Exception e) {
			return null;
		}
    }
    
}
