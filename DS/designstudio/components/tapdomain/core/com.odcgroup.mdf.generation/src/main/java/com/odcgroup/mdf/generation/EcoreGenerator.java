package com.odcgroup.mdf.generation;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.odcgroup.mdf.ecore.Mdf2EcoreMapper;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;

public class EcoreGenerator extends AbstractCodeGenerator {

	private static final String META_INF_MODELS = "META-INF/models";

	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {

		Mdf2EcoreMapper mapper = new Mdf2EcoreMapper();
		ResourceSet rs = new ResourceSetImpl();	
		Set<EPackage> ePackages = new HashSet<EPackage>();
		
		URI outputFolderURI = URI.createPlatformResourceURI(outputContainer.getFullPath().append(META_INF_MODELS).addTrailingSeparator().toString(), true);

		for(IOfsModelResource modelResource : modelResources) {
			if(modelResource.getURI().fileExtension().equals("domain")) {
				MdfDomain domain;
				try {
					domain = (MdfDomain) modelResource.getEMFModel().get(0);
					EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
					ePkg.setName(domain.getName());
					ePkg.setNsPrefix(domain.getName());
					ePkg.setNsURI("http://www.temenos.com/domain/" + domain.getName());
					for(Object obj : domain.getEntities()) {
						if(!(obj instanceof MdfClass)) {
							MdfEntityImpl entity = (MdfEntityImpl) obj;
							EClassifier eClassifier = mapper.map(entity);
							ePkg.getEClassifiers().add(eClassifier);
						}
					}
					Resource resource = rs.createResource(outputFolderURI.appendSegment(domain.getName()).appendFileExtension(getResourceFileExtension()));
					resource.getContents().add(ePkg);
					ePackages.add(ePkg);
				} catch (IOException e) {
					String errorMsg = "Error during Ecore generation";
					newCoreException(e, nonOkStatuses, errorMsg);
				} catch (InvalidMetamodelVersionException e) {
					String errorMsg = "Error during Ecore generation";
					newCoreException(e, nonOkStatuses, errorMsg);
				}
			}
		}
		for(EPackage ePkg : ePackages) {
			try {
				ePkg.eResource().save(null);
			} catch (IOException e) {
				String errorMsg = "Error while writing Ecore file for " + ePkg.getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}
	}

	public String getResourceFileExtension() {
		return  "ecore";
	}
}
