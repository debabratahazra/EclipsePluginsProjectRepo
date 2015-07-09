package com.odcgroup.domain.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.workbench.core.repository.LanguageRepository;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

public class DomainRepositoryX extends LanguageRepository implements IDomainRepository {

	// Common to all products, indicates if a class has a primary key
	private static final String CLASS_HAS_PRIMARY_KEY = "HAS_PRIMARY_KEY";
	
	@Override
	public void createUserData(EObject eObject, Map<String, String> userData) {
		
		// class with a primary key (shared by T24/TAP)
		if (eObject instanceof MdfClass) {;
			if (((MdfClass) eObject).hasPrimaryKey()) {
				// do not put a null value, otherwise isClassWithPrimaryKey won't work
				userData.put(CLASS_HAS_PRIMARY_KEY, CLASS_HAS_PRIMARY_KEY);
			}
		}	
	}

	@Override
	public Iterable<IEObjectDescription> getAllMdfClassesWithPrimaryKeys() {
		return getExportedObjectsByType(MdfPackage.Literals.MDF_CLASS, 
				new Function1<IEObjectDescription,Boolean>() {
					public Boolean apply(IEObjectDescription p) {
						return isClassWithPrimaryKey(p);
					}
				});
	}

	@Override
	public boolean isClassWithPrimaryKey(IEObjectDescription eObjectDescription) {
		String value = eObjectDescription.getUserData(CLASS_HAS_PRIMARY_KEY);
		return value != null;
	}
	
	public List<IEObjectDescription> getMdfDomains(QualifiedName name) {
		return getMdfElementsByName(MdfPackage.Literals.MDF_DOMAIN, name);
	}
	
	public List<IEObjectDescription> getAllMdfEntities() {
		return Lists.newArrayList(getExportedObjectsByType(MdfPackage.Literals.MDF_ENTITY));
	}
	
	public List<IEObjectDescription> getAllMdfClasses() {
		return getMdfElementsOfType(MdfPackage.Literals.MDF_CLASS);		
	}
	
	public List<IEObjectDescription> getAllMdfDomains() {
		return getMdfElementsOfType(MdfPackage.Literals.MDF_DOMAIN);			
	}
	
	public List<IEObjectDescription> getMdfElementsOfType(EClass eClass) {
		return Lists.newArrayList(getExportedObjectsByType(eClass));
	}
	
	public List<IEObjectDescription> getMdfElementsByName(EClass type, QualifiedName name) {
		return Lists.newArrayList(getExportedObjects(type, name));
	}
	
	public Optional<MdfDomain> getMdfDomain(QualifiedName name, Resource res) {
		IEObjectDescription found = 
				Iterables.getFirst(getExportedObjects(res, MdfPackage.Literals.MDF_DOMAIN, name, false), null);
		if (found != null) {
			MdfDomain domain = (MdfDomain) res.getResourceSet().getEObject(found.getEObjectURI(), true);
			return Optional.of(domain);
		} else {
			return Optional.absent();
		}
	}
	
	public Optional<MdfDomain> getMdfDomain(QualifiedName name, IProject project) {
		List<MdfDomain> domains = getMdfDomains(project);
		return getMdfDomain(name, domains);		
	}
	
	public List<MdfDomain> getMdfDomain(IProject project) {
		return getMdfDomains(project);
	}
	
	private Optional<MdfDomain> getMdfDomain(QualifiedName name, List<MdfDomain> domains) {
		for (MdfDomain mdfDomain : domains) {
			QualifiedName qname = QualifiedName.create(mdfDomain.getName());
			if (name.equals(qname)) {
				return Optional.of(mdfDomain);
			}
		}
		return Optional.absent();
	}
	
	public Optional<MdfClass> getMdfClass(String domainName, String localName, Resource resource) {
		Optional<MdfDomain> domain = getMdfDomain(QualifiedName.create(domainName), resource);
		if (domain.isPresent()) {
			return Optional.of(domain.get().getClass(localName));
		}
		return Optional.absent();
	}
 	
	public Optional<MdfEntity> getMdfEntity(String domainName, String localName, Resource resource) {
		if (PrimitivesDomain.NAME.equals(domainName)) {
			return Optional.of(PrimitivesDomain.DOMAIN.getEntity(localName));
		}
		Optional<MdfDomain> domain = getMdfDomain(QualifiedName.create(domainName), resource);
		if (domain.isPresent()) {
			return Optional.of(domain.get().getEntity(localName));
		}
		return Optional.absent();		
	}
	
	public List<MdfEntity> getMdfClassesWithPK(Resource resource) {
		return getMdfClassesWithPK(resource.getResourceSet());
	}
	
	public List<MdfEntity> getMdfClassesWithPK(ResourceSet rs) {
		 Iterable<IEObjectDescription> edescs = getAllMdfClassesWithPrimaryKeys();
		 return getEntitiesFromDesc(rs, edescs);
	}
	
	public List<MdfEntity> getMdfClassesWithPKProxy(ResourceSet rs) {
		 Iterable<IEObjectDescription> edescs = getAllMdfClassesWithPrimaryKeys();
		 List<MdfEntity> list = new ArrayList<MdfEntity>();
		 for (IEObjectDescription edesc : edescs) {
			 MdfEntityImpl entity = (MdfEntityImpl) edesc.getEObjectOrProxy();
			 entity.setName(edesc.getName().toString());
			 list.add(entity);
		}
		 return list;
	}
	
	public List<MdfDomain> getMdfDomains(Resource resource) {
		Iterable<IEObjectDescription> edescs = getAllDomains(resource);
		List<MdfDomain> list = new ArrayList<MdfDomain>();
		ResourceSet rs = resource.getResourceSet();
		for (IEObjectDescription edesc : edescs) {
			MdfDomain domain = (MdfDomain) rs.getEObject(edesc.getEObjectURI(), true);
			list.add(domain);
		}
		return list;
	}
	
	public List<IEObjectDescription> getMdfDomainsByName(QualifiedName qname, Resource resource) {
		return Lists.newArrayList(getExportedObjects(resource, MdfPackage.Literals.MDF_DOMAIN, qname, false));
	}
	
	public List<MdfDomain> getMdfDomains(IProject project) {
		Iterable<IEObjectDescription> edescs = getAllMdfDomains();
		XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
		List<MdfDomain> list = new ArrayList<MdfDomain>();
		for (IEObjectDescription edesc : edescs) {
			MdfDomain domain = (MdfDomain) rs.getEObject(edesc.getEObjectURI(), true);
			list.add(domain);
		}
		return list;
	}
	
	public List<MdfEntity> getAllMdfPrimitives(Resource resource) {
		List<IEObjectDescription> edescs = getMdfElementsOfType(resource, MdfPackage.Literals.MDF_ENUMERATION, 
				MdfPackage.Literals.MDF_BUSINESS_TYPE);
		return getProxyEntitiesFromDesc(resource.getResourceSet(), edescs);		
	}
	
	public List<MdfEntity> getMdfEntitiesOfTypes(Resource resource, EClass...types) {
		Iterable<IEObjectDescription> edescs = getMdfElementsOfType(resource, types);
		return getEntitiesFromDesc(resource.getResourceSet(), edescs);		
	}
	

	/**
	 * @param languageName
	 */
	public DomainRepositoryX(String languageName) {
		super(languageName);
	}	

	@Override
	public  Iterable<IEObjectDescription> getAllClasses(EObject context) {
		return getExportedObjectsByType(context, MdfPackage.Literals.MDF_CLASS);
	}
	
	public Iterable<IEObjectDescription> getAllDomains(Resource resource) {
		return getExportedObjectsByType(resource, MdfPackage.Literals.MDF_DOMAIN);
	}
	
	public List<MdfEntity> getMdfClasses(Resource resource) {
		Iterable<IEObjectDescription> edescs = getExportedObjectsByType(resource, MdfPackage.Literals.MDF_CLASS);
		return getEntitiesFromDesc(resource.getResourceSet(), edescs);
	}
	
	public List<MdfEntity> getMdfClassesFromProxy(Resource resource) {
		Iterable<IEObjectDescription> edescs = getExportedObjectsByType(resource, MdfPackage.Literals.MDF_CLASS);
		return getProxyEntitiesFromDesc(resource.getResourceSet(), edescs);
	}
	
	public List<MdfEntity> getAllMdfEntities(Resource resource) {
		Iterable<IEObjectDescription> edescs = getMdfElementsByType(resource, MdfPackage.Literals.MDF_ENTITY);
		return getEntitiesFromDesc(resource.getResourceSet(), edescs);
	}
	
	public Iterable<IEObjectDescription> getMdfElementsByType(EObject context, EClass eClass) {
		return getExportedObjectsByType(context, eClass);
	}
	
	public Iterable<IEObjectDescription> getMdfElementsByType(Resource resource, EClass eClass) {
		return getExportedObjectsByType(resource, eClass);		
	}
	
	public List<MdfEntity> getMdfEntitiesOfType(Resource resource, EClass type) {
		Iterable<IEObjectDescription> edescs = getMdfElementsByType(resource, type);
		return getEntitiesFromDesc(resource.getResourceSet(), edescs);
	}
	
	public List<IEObjectDescription> getMdfElementsOfType(Resource resource, EClass...classes) {
		List<IEObjectDescription> list = new ArrayList<IEObjectDescription>();
		for (EClass eClass : classes) {
			list.addAll(Lists.newArrayList(getExportedObjectsByType(resource, eClass)));			
		}
		return list;
	}
	
	private List<MdfEntity> getEntitiesFromDesc(ResourceSet context, Iterable<IEObjectDescription> edescs) {
		List<MdfEntity> list = new ArrayList<MdfEntity>();
		for (IEObjectDescription edesc : edescs) {
			MdfEntity entity = (MdfEntity) context.getEObject(edesc.getEObjectURI(), true);
			list.add(entity);
		}
		return list;
	}
	
	private List<MdfEntity> getProxyEntitiesFromDesc(ResourceSet context, Iterable<IEObjectDescription> edescs) {
		List<MdfEntity> list = new ArrayList<MdfEntity>();
		for (IEObjectDescription edesc : edescs) {
			 MdfEntityImpl entity = (MdfEntityImpl) edesc.getEObjectOrProxy();
			 entity.setName(edesc.getName().toString());
			 list.add(entity);
		}
		return list;
	}

}
