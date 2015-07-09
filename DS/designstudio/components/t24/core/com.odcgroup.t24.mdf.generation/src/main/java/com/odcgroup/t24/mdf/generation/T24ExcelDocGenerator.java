package com.odcgroup.t24.mdf.generation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.common.mdf.generation.CommonMdfGenerationCore;
import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.build.FileOutputStreamFactory;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.t24.mdf.generation.xls.T24ExcelWriter;
import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;

public class T24ExcelDocGenerator extends AbstractCodeGenerator implements DocGenerator {
	
	private final ModelWriter writer;
	private Map<MdfDomain, Collection<MdfDomain>> importedDomain = new HashMap<MdfDomain, Collection<MdfDomain>>();

    public T24ExcelDocGenerator() {
        super();
        writer = new T24ExcelWriter();
    }
    
	public boolean isValidForProject(IProject project) {
		return true;
	}


	@SuppressWarnings("unchecked")
	public void run2(IProject project, Collection<?> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException, InterruptedException {
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		// keep only domain model resources located in domain/applications
		Collection<Resource> filteredResources = new ArrayList<Resource>();
		if(modelResources instanceof Collection){
			for (IResource resource : (Collection<IResource>)modelResources) {
				URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(),true);
				if (uri.fileExtension().equals("domain")) {
					XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
					Resource ter = rs.createResource(uri);
					filteredResources.add(ter);
				}
			}
		}
		if (filteredResources.size() > 0) {
			try {
				getExcelWriter().open();
				run(project, outputFolder, filteredResources, nonOkStatuses);
				outputFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				if (monitor != null && monitor.isCanceled()) {
					throw new InterruptedException();
				}
			} finally {
				try {
					getExcelWriter().close();
				} catch (IOException ex) {
					IStatus ioEx = new Status(IStatus.ERROR, T24MdfGenerationCore.PLUGIN_ID, ex.getMessage(), ex);
					throw new CoreException(ioEx);
				}
			}
		}
	}

 
	public void run(IProject project, IContainer outputContainer, Collection<Resource> modelResources,
			List<IStatus> nonOkStatuses) {
    	if (modelResources.isEmpty()) return; 

    	File javaSource = outputContainer.getLocation().toFile();
        OutputStreamFactory streamFactory =
                new FileOutputStreamFactory(javaSource);
        
        for (Resource domainResource : modelResources) {
            try {
            	domainResource.load(null);
	        	EList<EObject> model = domainResource.getContents();
	        	if(model.size() > 0 && (model.get(0) instanceof MdfDomain)) {
	            	EcoreUtil.resolveAll(model.get(0));
	        		MdfDomain domain = (MdfDomain) model.get(0);
	            	IStatus status = checkUnresolvedBundles(project);
	            	if (status != null && status.getSeverity()!=IStatus.OK) {
	            		throw new CoreException(status);	                		
	            	}
	            	if (writer instanceof SkipImportDomainComputation) {
	            		writer.write(domain, Collections.<MdfDomain>emptyList(), streamFactory);
	            	} else {
	            		writer.write(domain, getUsedImports(domain), streamFactory);
	            	}
	        	}
            } catch (Exception e) {
				String errorMsg = "Error while generating code in " + getClass().getName() + " for domain '"
						+ domainResource.getURI().lastSegment() + "'";
                newCoreException(e, nonOkStatuses, errorMsg);
            } 
        }
    }
	
	private Collection<MdfDomain> getUsedImports(MdfDomain domain) {
		if (!importedDomain.containsKey(domain)) {
			importedDomain.put(domain, computeUsedImport(domain));
		}
		return importedDomain.get(domain);    	
	}
	
	private Collection<MdfDomain> computeUsedImport(MdfDomain domain) {
		Map<String, MdfDomain> domainMap = new HashMap<String, MdfDomain>();
		
		// process all entities
		for(MdfClass clazz : (List<MdfClass>) domain.getClasses()) {
			// check the super class
			if (clazz.getBaseClass() != null) {
				String domainName = clazz.getBaseClass().getQualifiedName().getDomain();
				if(!domain.getName().equals(domainName)) {
					if (!domainMap.containsKey(domainName)) {
						MdfDomain usedDomain = clazz.getBaseClass().getParentDomain();
						if(usedDomain!=null) domainMap.put(domainName, usedDomain);
					}
				}
			}
			// check all non-primitive properties
			for(MdfProperty property : (List<MdfProperty>) clazz.getProperties()) {
				if(property instanceof MdfReverseAssociation) continue;				
				MdfEntity type = property.getType();				
				if(type != null && (!type.isPrimitiveType() || type instanceof MdfEnumeration) && type.getQualifiedName()!=null)  {
					String domainName = type.getQualifiedName().getDomain();
					if (!domain.getName().equals(domainName) && !domainName.equals(PrimitivesDomain.NAME)) {
						if (!domainMap.containsKey(domainName)) {
							MdfDomain usedDomain = type.getParentDomain();
							if(usedDomain!=null) domainMap.put(domainName, usedDomain);
						}
					}
				}
			}    		
		}
		
		// process all datasets
		for(MdfDataset dataset : (List<MdfDataset>) domain.getDatasets()) {
			// check-for datasets with no baseclass specified
			if (dataset.getBaseClass() != null) {
				String domainName = dataset.getBaseClass().getQualifiedName()!=null ? dataset.getBaseClass().getQualifiedName().getDomain() : "";
				if (!domain.getName().equals(domainName)){
					if (!domainMap.containsKey(domainName)) {
						MdfDomain usedDomain = dataset.getBaseClass().getParentDomain();
						if(usedDomain!=null) domainMap.put(domainName, usedDomain);
					}
				}
			}
			// check all non-primitive properties which are not null
			for(MdfDatasetProperty property : (List<MdfDatasetProperty>) dataset.getProperties()) {
				MdfEntity type = property.getType();
				
				if(type != null && !type.isPrimitiveType()
						&& type.getQualifiedName() != null 
						&& !domain.getQualifiedName().equals(type.getParentDomain().getQualifiedName())) {
					String domainName = type.getQualifiedName().getDomain();
					if (!domainMap.containsKey(domainName)) {
						MdfDomain usedDomain = type.getParentDomain();
						if(usedDomain!=null) domainMap.put(domainName, usedDomain);
					}
				}
			}
		}
		return domainMap.values();
	}
	
	/**
	 * @param domain
	 * @return
	 */
	private IStatus checkUnresolvedBundles(IProject project) {
		IDependencyManager dependencyManager = OfsCore.getDependencyManager();
		if(dependencyManager.hasUnresolvedDependencies(project)) {
			IContainerIdentifier[] identifiers = dependencyManager.getUnresolvedDependencies(project);
			return new Status(IStatus.ERROR, CommonMdfGenerationCore.PLUGIN_ID, getErrorMessage(identifiers));
		} else {
			return Status.OK_STATUS;
		}
	}
	   
	protected final T24ExcelWriter getExcelWriter() {
		return (T24ExcelWriter) this.writer;
	}
	
	private String getErrorMessage(IContainerIdentifier[] identifiers) {
		StringBuffer sb = new StringBuffer();
		if (identifiers.length==1) {
			sb.append("Unresolved dependency " + identifiers[0].toString() + " ");
		}
		if (identifiers.length>1) {
			sb.append("Unresolved dependencies ");
			for(IContainerIdentifier id : identifiers)
				sb.append(" " + id + " ");
		}
		sb.append("found.  Code generation aborted!!!");
		return sb.toString();
	}

	@Override
	public void run(IProject project, Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException, InterruptedException {
		Collection<IResource> res = new HashSet<IResource>();
		for (final IOfsModelResource entry : modelResources) {
			res.add((IResource)OfsResourceHelper.getFile(entry).getAdapter(IResource.class));
		}
		run2(project,res,outputFolder,monitor);
	}
}
