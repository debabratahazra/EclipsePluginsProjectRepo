package com.odcgroup.t24.applicationimport;

import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.mapper.MappingException;
import com.odcgroup.t24.applicationimport.mapper.T24MetaModel2MdfMapper;
import com.odcgroup.t24.applicationimport.reader.Reader;
import com.odcgroup.t24.applicationimport.schema.ApplicationEntity;
import com.odcgroup.t24.applicationimport.schema.Component;
import com.odcgroup.t24.applicationimport.schema.Module;
import com.odcgroup.t24.applicationimport.util.MdfDomainUtil;

public class ApplicationsImport {
	@Inject
	private T24MetaModel2MdfMapper mapper;
	@Inject
	private MdfDomainUtil mdfDomainUtil;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;
	
	private Reader reader;
	private Map<String, String> docs = new HashMap<String, String>();
	
	protected Reader getXMLReader(ApplicationsImportProblemsException problems) throws ApplicationsImportProblemsException {
		if (reader == null) {
			try {
				reader = new Reader();
			} catch (JAXBException e) {
				throw problems.addProblem("JAXB initialization failed", e);
			} catch (SAXException e) {
				throw problems.addProblem("JAXB initialization failed", e);
			}

		}
		return reader;
	}

	public void importApplications(File directoryToImport, IFolder folder, ResourceSet resourceSet, IProgressMonitor monitor, Set<MdfDomain> readDomains) throws ApplicationsImportProblemsException, InterruptedException {
		File[] files = directoryToImport.listFiles(new FilenameFilter() {
			public boolean accept(File dir , String name) {
				return name.endsWith(".xml");
			}
		});
		importApplications(Arrays.asList(files), folder, resourceSet, monitor, readDomains);
	}
	
	/**
	 * @return Domains read. These have already been saved - 
	 * they are just returned e.g. for tests to check stuff in them more easily.
	 */
	public void importApplications(final String xmlString, final IFolder folder, ResourceSet resourceSet,
			Set<MdfDomain> readDomains, Set<MdfDomain> inMemoryDomainSet) throws ApplicationsImportProblemsException,
			InterruptedException {
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		ApplicationsImportProblemsException problems = new ApplicationsImportProblemsException();

		// 1. Prepare the XML reader
		Reader reader = getXMLReader(problems);

		// 2. transform the XML stream
		List<Module> modules = new LinkedList<Module>();
		try {
			modules.addAll(reader.unmarshalApplications(xmlString));
		} catch (JAXBException e) {
			problems.addProblem("", e);
		} catch (SAXException e) {
			problems.addProblem("", e);
		} catch (UnsupportedEncodingException e) {
			problems.addProblem("", e);
		}
				// 3. Mapping XML to Domain
		for (Module module : modules) {
			final Component component = module.getComponents().get(0);
			String description = "Mapping Module " + module.getName() + ", Component " + component.getName()
					+ ", Application " + component.getApplications().get(0).getName();
			try {
				readDomains.addAll(collectMdfDomains(module, folder, resourceSet,index));
			} catch (MappingException e) {
				problems.addProblem(description + " failed with " + e.getMessage(), e);
			}
		}

		if (!problems.getProblems().isEmpty()) {
			throw problems;
		}

	}

	/**
	 * @return Domains read. These have already been saved - they are just returned e.g. for tests to check stuff in them more easily.
	 */
	public void importApplications(final Collection<File> xmlFilesToImport, final IFolder folder, ResourceSet resourceSet, IProgressMonitor monitor, Set<MdfDomain> readDomains) throws ApplicationsImportProblemsException, InterruptedException {
		// Note we're doing 2*files (reading & mapping)
		// 1. Reading XML
		ApplicationsImportProblemsException problems = new ApplicationsImportProblemsException();
		monitor.beginTask("Reading " + xmlFilesToImport.size() + " XML files...", 2 * xmlFilesToImport.size());
		List<Module> modules = new LinkedList<Module>();
		Reader reader;
		try {
			reader = new Reader();
		} catch (JAXBException e) {
			throw problems.addProblem("JAXB initialization failed", e);
		} catch (SAXException e) {
			throw problems.addProblem("JAXB initialization failed", e);
		}
	    for (File xmlFile : xmlFilesToImport) {
	    	try {
				monitor.subTask("Reading XML " + xmlFile.toString());
				modules.addAll(reader.unmarshalApplications(xmlFile.toURI().toURL()));
				monitor.worked(1);
				if (monitor.isCanceled())
					break;
			} catch (JAXBException e) {
				problems.addProblem(xmlFile.toString(), e);
			} catch (MalformedURLException e) {
				problems.addProblem(xmlFile.toString(), e);
			} catch (SAXException e) {
				problems.addProblem(xmlFile.toString(), e);
			}
	    }
	    if (monitor.isCanceled())
	    	throw new InterruptedException();
		
	    IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);

		// 2. Mapping XML to Domain
	    for (Module module : modules) {
	    	final Component component = module.getComponents().get(0);
			String description = "Mapping Module " + module.getName() + ", Component " + component.getName() + ", Application " + component.getApplications().get(0).getName();
			monitor.subTask(description);
	    	try {
				 readDomains.addAll(collectMdfDomains(module, folder, resourceSet,index));
			} catch (MappingException e) {
				problems.addProblem(description + " failed with " + e.getMessage(), e);
			}
			monitor.worked(1);
			if (monitor.isCanceled())
				break;
		}
	    if (!problems.getProblems().isEmpty())
	    	throw problems;
	    if (monitor.isCanceled())
	    	throw new InterruptedException();
		monitor.done();
	}
	
	/**
	 * Get the MdfDomain for the given Module.
	 */
	public Set<MdfDomain> collectMdfDomains(Module module, IFolder folder, ResourceSet resourceSet,
			IResourceDescriptions index) throws MappingException {
		
		Set<MdfDomain> domains = new HashSet<MdfDomain>();
		for (Component component : module.getComponents()) {
			IFolder domainFolder = folder.getProject().getFolder(folder.getProjectRelativePath());
			IFile domainFile = domainFolder.getFile(component.getName() + ".domain");
			URI uri = URI.createPlatformResourceURI(domainFile.getFullPath().toString(), true);
			MdfDomainImpl domain = getExistingDomain(component, resourceSet, domains, index, uri);
			if (domain == null) {
				domain = mapper.map(component.getName(), module.getName(), folder.getProject());
				domain.setDocumentation(component.getDocumentation());
				Resource resource = resourceSet.getResource(uri, false);
				if(resource == null)
					resource = resourceSet.createResource(uri);
				resource.getContents().add(domain);
			}
			mapper.map(component.getApplications(), (MdfDomainImpl) domain, folder.getProject());
			applyMdfClassDocumentation(domain, component);
			domains.add(domain);
		}
		return domains;
	}

	/**
	 * Get the domain if it exists with the given component name.
	 */
	public MdfDomainImpl getExistingDomain(Component component, ResourceSet resourceSet,Set<MdfDomain> domains,IResourceDescriptions index,URI uri) throws MappingException {
		MdfDomainImpl existingDomian =null;
		String name = component.getName();
		if (name == null || name.trim().isEmpty()) {
			throw new MappingException("No Component Name - BUG in T24 XML");		
		}
		if (domains != null) {
			for (MdfDomain domain : domains) {
				if (domain.getName().equals(name)) {
					existingDomian = (MdfDomainImpl) domain;
					break;
				}
			}
		}
		if(existingDomian == null){
			Iterable<IEObjectDescription> objDescriptions = ApplicationImportUtils.getAllDomainsinAProject(uri, index);
			for(IEObjectDescription objectDes : objDescriptions){
				QualifiedName qName =objectDes.getName();
				if (qName!=null && qName.getSegmentCount() == 1 && qName.getFirstSegment().equals(name)){
				   existingDomian =(MdfDomainImpl) resourceSet.getEObject(objectDes.getEObjectURI(), true);
				   break;
				}
			}
		}
		if(existingDomian !=null){
			if (component.getApplications().size() > 0
					&& component.getApplications().get(0) instanceof ApplicationEntity) {
				MdfClassImpl baseClass = (MdfClassImpl) existingDomian.getClass(((ApplicationEntity) component
						.getApplications().get(0)).getName());
				getMdfClassDocumentations(baseClass);
			}
			mdfDomainUtil.resolveAll(existingDomian);
		}
		return  existingDomian ;
	}
	
	
	/**
	 * Fetch all Documentations part before re-import the exiting Application
	 * Implement JUnit test case
	 */
	public Map<String, String> getMdfClassDocumentations(MdfClassImpl mdfClass) {
		if(mdfClass == null){
			return Collections.emptyMap();
		}
		
		fetchMdfClassPropertiesDocumentation(mdfClass);
		return docs;
	}
	
	/**
	 * Fetch the all documentation part from existing domain.
	 */
	@SuppressWarnings("unchecked")
	private void fetchMdfClassPropertiesDocumentation(MdfClassImpl mdfClass) {
		if(mdfClass != null && mdfClass.getDocumentation() != null) {
			docs.put(mdfClass.getQualifiedName().toString(), mdfClass.getDocumentation());
		}
		List<MdfProperty> properties = mdfClass.getProperties();
		for (MdfProperty mdfProperty : properties) {
			if (mdfProperty instanceof MdfAttribute) {
				MdfAttributeImpl attribute = (MdfAttributeImpl) mdfProperty;
				if (attribute.getDocumentation() != null) {
					docs.put(attribute.getQualifiedName().toString(), attribute.getDocumentation());
				}
			} else if (mdfProperty instanceof MdfAssociation) {
				MdfAssociationImpl association = (MdfAssociationImpl) mdfProperty;
				if (association.getDocumentation() != null) {
					docs.put(association.getQualifiedName().toString(), association.getDocumentation());
				}
				if(association.getContainment() == MdfContainment.BY_VALUE && association.getType() instanceof MdfClass) {
					MdfClassImpl associationMdfClass = (MdfClassImpl) association.getType();
					fetchMdfClassPropertiesDocumentation(associationMdfClass);
				}
			}
		}
	}

	/**
	 * Re-Apply the Documentation part in existing Application
	 * during re-import sample Application
	 */
	@SuppressWarnings("unchecked")
	private void applyMdfClassDocumentation(MdfDomainImpl existingDomian, Component component) {
		if (docs == null || docs.isEmpty() || existingDomian == null || component== null) {
			return;
		}
		MdfClassImpl mdfClass = null;
		if(component.getApplications().size() > 0 && component.getApplications().get(0) instanceof ApplicationEntity){
			mdfClass = (MdfClassImpl)existingDomian.getClass(((ApplicationEntity)component.getApplications().get(0)).getName());
		}
		if(mdfClass == null){
			return;
		}
		// Documentation set or Main PK class.
		((MdfClassImpl) mdfClass).setDocumentation(docs.get(mdfClass.getQualifiedName().toString()));
		setAssociationDocumentation(mdfClass);
		existingDomian.getClasses().add(mdfClass);
	}
	
	@SuppressWarnings("unchecked")
	private void setAssociationDocumentation(MdfClassImpl mdfClass) {
		if(mdfClass != null && mdfClass.getDocumentation() != null) {
			// Documentation set for each Association By-Value class.
			((MdfClassImpl) mdfClass).setDocumentation(docs.get(mdfClass.getQualifiedName().toString()));
		}
		List<MdfProperty> properties = mdfClass.getProperties();
		for (MdfProperty mdfProperty : properties) {
			if (mdfProperty instanceof MdfAttribute) {
				MdfAttributeImpl attribute = (MdfAttributeImpl) mdfProperty;
				String attDocumentation = docs.get(attribute.getQualifiedName().toString());
				if(attDocumentation == null) {
					continue;
				}
				attribute.setDocumentation(attDocumentation);
				mdfClass.getProperties().add(attribute);
			} else if (mdfProperty instanceof MdfAssociation) {
				MdfAssociationImpl association = (MdfAssociationImpl) mdfProperty;
				String assDocumentation = docs.get(association.getQualifiedName().toString());
				if(assDocumentation == null) {
					if(association.getContainment() == MdfContainment.BY_VALUE && association.getType() instanceof MdfClass) {
						MdfClassImpl associationMdfClass = (MdfClassImpl) association.getType();
						setAssociationDocumentation(associationMdfClass);
					}
					continue;
				}
				association.setDocumentation(assDocumentation);
				mdfClass.getProperties().add(association);
			}
		}
	}
}
