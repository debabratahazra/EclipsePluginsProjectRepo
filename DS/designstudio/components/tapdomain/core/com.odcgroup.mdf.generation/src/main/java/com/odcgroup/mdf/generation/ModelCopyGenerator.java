package com.odcgroup.mdf.generation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.mdf.generation.legacy.java.ModelCopyWriter;
import com.odcgroup.mdf.generation.legacy.mml.MmlModelWriter;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class ModelCopyGenerator extends MDFGenerator {

	private static final String META_INF = "/META-INF";
	private static final String META_INF_MODELS = META_INF + "/models";
	private static final String META_INF_MODELS_DOT_MODELS = META_INF_MODELS + "/.models";
	private static final String[] DOMAIN_MML_FILTER = new String[]{"domain"};

	public ModelCopyGenerator() throws IOException {
		super(new ModelCopyWriter());
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.mdf.generation.MDFGenerator#doRun(org.eclipse.core.resources
	 * .IProject, org.eclipse.core.resources.IContainer, java.util.Collection)
	 */
	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {

		// Get the selected resource of the project
		Collection<IOfsModelResource> domainResources = OfsResourceHelper.filter(modelResources, DOMAIN_MML_FILTER);

		// Copy the models (using mdf)
		super.doRun(project, outputContainer, modelResources, nonOkStatuses);

		
		try {
			// Create META-INF folders
			createMetaInfFolders(outputContainer);
			
			// copyMml(project, outputContainer, domainResources);

			// Create .models file
			createOrUpdateDotModels(project, outputContainer, domainResources);
		} catch (CoreException e) {
			String errorMsg = "Error during ModelCopy generation.";
			newCoreException(e, nonOkStatuses, errorMsg);
		}
	}

	/**
	 * This is a simplified version of copy models
	 * WARNING: Before using this simplification more analysis is required. 
	 * The existing copy model do not simply copy the models, but read it and 
	 * rewrite it. During this process the mml change from the original:
	 * <ul>
	 * <li>The order of xml tags are mixed up</li>
	 * <li>Some default value are explicitly written<br/>
	 * Before: {@code <mml:dataset name="LinkDataSetToMultipleByRef" base-class="DsT:Root">}<br>
	 * After: {@code <mml:dataset name="LinkDataSetToMultipleByRef" base-class="DsT:Root" linked="false" sync="false">}</li>
	 * <li>The files get renamed using a name formatter (XMLNameFormat which uses the model name and apply a transformation)<br/>
	 * Before: JPAPoc.mml<br/>
	 * After: jpa-poc.mml</li>
	 * <li>The import clauses get updated according the renaming<br/>
	 * Before : {@code <mml:import location="dataset-tests.mml" namespace="http://www.odcgroup.com/dataset-tests"/>}</br>
	 * After : {@code <mml:import location="dst.mml" namespace="http://www.odcgroup.com/dataset-tests"/>} </li>
	 * </ul>
	 * @param project
	 * @param outputContainer
	 * @param domainResources
	 * @throws CoreException
	 */
	@SuppressWarnings("unused")
	private void copyMml(IProject project, IContainer outputContainer, Collection<IOfsModelResource> domainResources)
			throws CoreException {

		// Create or update the mml file in the models folder
		IPath metaInfModelsPath = new Path(META_INF_MODELS);
		for (IOfsModelResource domainResource : domainResources) {
			String targetFileName = MmlModelWriter.getModelFileName(getDomain(domainResource).getName());
			IFile outputFile = outputContainer.getFile(metaInfModelsPath.append(targetFileName));
			if (outputFile.exists()) {
				outputFile.setContents(domainResource.getContents(), true, true, null);
			} else {
				outputFile.create(domainResource.getContents(), true, null);
			}
		}

	}

	private void createOrUpdateDotModels(IProject project, IContainer outputContainer,
			Collection<IOfsModelResource> domainResources) throws CoreException {
		// Create or update the .models file
		IFile dotModelsFile = outputContainer.getFile(new Path(META_INF_MODELS_DOT_MODELS));

		Set<String> loadedClasses = new HashSet<String>();

		if (dotModelsFile.exists()) {
			readExistingDotModels(dotModelsFile, loadedClasses);
		}

		// Populate the .models
		for (IOfsModelResource domainResource : domainResources) {
			MdfDomain domain = getDomain(domainResource);
			if (domain != null) {
				loadedClasses.add(JavaCore.getModelPackage(domain) + "." + JavaCore.getDomainModelClassName(domain));
			}
		}

		// Write the .models content
		List<String> models = new ArrayList<String>(loadedClasses);
		Collections.sort(models);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(os, true);
		for (String model : models) {
			pw.println(model);
		}

		// Create or update the .models file
		InputStream temporaryInputStream = new ByteArrayInputStream(os.toByteArray());
		if (dotModelsFile.exists()) {
			dotModelsFile.setContents(temporaryInputStream, true, true, null);
		} else {
			dotModelsFile.create(temporaryInputStream, true, null);
		}
	}

	private MdfDomain getDomain(IOfsModelResource domainResource) throws CoreException {
		MdfDomain domain = null;
		try {
			EList<EObject> model = domainResource.getEMFModel();
			if (model.size() == 1 && (model.get(0) instanceof MdfDomain)) {
				domain = (MdfDomain) model.get(0);
			}
		} catch (IOException e) {
			newCoreException("Could not load domain file " + domainResource.getName(), e);
		} catch (InvalidMetamodelVersionException e) {
			newCoreException("Could not load domain file " + domainResource.getName(), e);
		}
		return domain;
	}

	private void createMetaInfFolders(IContainer outputContainer) throws CoreException {
		// Create (if required) the META-INF folder
		IPath metaInfPath = new Path(META_INF);
		IFolder metaInfFolder = outputContainer.getFolder(metaInfPath);
		if (!outputContainer.exists(metaInfPath)) {
			metaInfFolder.create(true, true, null);
		}

		// Create (if required) the META-INF/models folder
		IPath metaInfModelsPath = new Path(META_INF_MODELS);
		IFolder metaInfModelsFolder = outputContainer.getFolder(metaInfModelsPath);
		if (!outputContainer.exists(metaInfModelsPath)) {
			metaInfModelsFolder.create(true, true, null);
		}
	}

	private void readExistingDotModels(IFile dotModelsFile, Set<String> loadedClasses) throws CoreException {
		InputStream in = dotModelsFile.getContents();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				loadedClasses.add(line);
			}
		} catch (IOException e) {
			newCoreException("Unable to read the .models", e);
		}
	}

}
