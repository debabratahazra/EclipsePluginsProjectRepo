package com.odcgroup.domain.repository;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

/**
 *
 * @author phanikumark
 *
 */
public class DomainRepositoryUtil {
	
	private static final DomainRepositoryX getDomainRepository() {
		return LanguageRepositoryProvider.getLanguageRepository(IDomainRepository.DOMAIN_LANGUAGE_NAME);
	}
	
	public static int getMdfDomainsSize() {
		List<IEObjectDescription> edescs = getDomainRepository().getAllMdfDomains();
		return Lists.newArrayList(edescs).size();		
	}
	
	public static boolean checkIfDomainExists(String domainName, Resource context) {
		QualifiedName qname = QualifiedName.create(domainName);
		Iterable<IEObjectDescription> edescs = getDomainRepository().getAllDomains(context);
		for (IEObjectDescription desc : edescs) {
			if (qname.equals(desc.getQualifiedName())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkMdfClassExists(QualifiedName qname) {
		List<IEObjectDescription> edescs = getDomainRepository().getAllMdfClasses();
		for (IEObjectDescription edesc : edescs) {
			if (qname.equals(edesc.getQualifiedName())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDuplicateDomain(QualifiedName qname, Resource resource) {
		List<IEObjectDescription> edescs = getDomainRepository().getMdfDomainsByName(qname, resource);
		if (edescs.size() > 1) {
			return true;
		}
		return false;
	}
	
	public static MdfEntity getMdfEntity(MdfName qname, Resource resource) {
		return getDomainRepository().getMdfEntity(qname.getDomain(), qname.getLocalName(), resource).orNull();
	}
	
	public static MdfClass getMdfClass(MdfName qname, Resource resource) {
		return getDomainRepository().getMdfClass(qname.getDomain(), qname.getLocalName(), resource).orNull();
	}
	
	public static List<MdfEntity> getMdfClassesWithPK(Resource resource) {
		return getDomainRepository().getMdfClassesWithPK(resource);
	}
	
	public static List<MdfEntity> getMdfClassesWithPK(ResourceSet rs) {
		return getDomainRepository().getMdfClassesWithPKProxy(rs);
	}
	
	public static List<MdfEntity> getMdfClasses(Resource resource) {
		return getDomainRepository().getMdfClasses(resource);
	}
	
	public static List<MdfEntity> getMdfClassesFromProxy(Resource resource) {
		return getDomainRepository().getMdfClassesFromProxy(resource);
	}
	
	public static List<MdfEntity> getAllMdfPrimitives(Resource resource) {
		return getDomainRepository().getAllMdfPrimitives(resource);
	}
	
	public static List<MdfDomain> getAllMdfDomains(Resource resource) {
		return getDomainRepository().getMdfDomains(resource);
	}
	
	public static List<MdfEntity> getAllMdfDatasets(Resource resource) {
		return getDomainRepository().getMdfEntitiesOfTypes(resource, MdfPackage.Literals.MDF_DATASET);
	}
	
	public static List<MdfEntity> getAllBusinessTypes(Resource resource) {
		return getDomainRepository().getMdfEntitiesOfType(resource, MdfPackage.Literals.MDF_BUSINESS_TYPE);
	}
	
	public static List<MdfEntity> getAllMdfEntities(Resource resource) {
		return getDomainRepository().getAllMdfEntities(resource);
	}
	
	public static MdfDomain getMdfDomain(Resource context, QualifiedName name) {
		return getDomainRepository().getMdfDomain(name, context).orNull();
	}
	
	public static MdfDomain getMdfDomain(QualifiedName name, IProject project) {
		return getDomainRepository().getMdfDomain(name, project).orNull();
	}
	
	public static List<MdfDomain> getAllMdfDomain(IProject project) {
		return getDomainRepository().getMdfDomain(project);
	}

}
