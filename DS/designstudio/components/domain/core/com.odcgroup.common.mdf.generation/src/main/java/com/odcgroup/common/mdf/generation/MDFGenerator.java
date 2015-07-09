package com.odcgroup.common.mdf.generation;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.build.FileOutputStreamFactory;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;


public class MDFGenerator extends AbstractCodeGenerator {

	private final ModelWriter writer;
	private Map<MdfDomain, Collection<MdfDomain>> importedDomain = new HashMap<MdfDomain, Collection<MdfDomain>>();
	
	
	protected final ModelWriter getWriter() {
		return this.writer;
	}
	
    public MDFGenerator(ModelWriter writer) {
        super();
        this.writer = writer;
    }

    @Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
		Collection<IOfsModelResource> domainResources = 
			OfsResourceHelper.filter(modelResources, new String[]{"domain"});
    	if (domainResources.isEmpty()) return; 

    	File javaSource = outputContainer.getLocation().toFile();
        OutputStreamFactory streamFactory =
                new FileOutputStreamFactory(javaSource);
        
        for (IOfsModelResource domainResource : domainResources) {
            try {
	        	EList<EObject> model = domainResource.getEMFModel();
	        	if(model.size() > 0 && (model.get(0) instanceof MdfDomain)) {
	            	EcoreUtil.resolveAll(model.get(0));
	        		MdfDomain domain = (MdfDomain) model.get(0);
	            	IStatus status = checkUnresolvedBundles(domain);
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
						+ domainResource.getName() + "'";
                newCoreException(e, nonOkStatuses, errorMsg);
            } 
        }
    }

	/**
	 * @param domain
	 * @return
	 */
	private IStatus checkUnresolvedBundles(MdfDomain domain) {
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((MdfDomainImpl)domain).eResource());
		IDependencyManager dependencyManager = OfsCore.getDependencyManager();
		if(dependencyManager.hasUnresolvedDependencies(ofsProject.getProject())) {
			IContainerIdentifier[] identifiers = dependencyManager.getUnresolvedDependencies(ofsProject.getProject());
			return new Status(IStatus.ERROR, CommonMdfGenerationCore.PLUGIN_ID, getErrorMessage(identifiers));
		} else {
			return Status.OK_STATUS;
		}
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
	
	private Collection<MdfDomain> getUsedImports(MdfDomain domain) {
		if (!importedDomain.containsKey(domain)) {
			importedDomain.put(domain, computeUsedImport(domain));
		}
		return importedDomain.get(domain);    	
	}
	
	@SuppressWarnings("unchecked")
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


}
