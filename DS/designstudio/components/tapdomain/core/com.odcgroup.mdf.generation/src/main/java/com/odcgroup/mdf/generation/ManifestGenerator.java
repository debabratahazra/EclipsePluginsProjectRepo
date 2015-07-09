package com.odcgroup.mdf.generation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;

public class ManifestGenerator extends AbstractCodeGenerator {

	private static final String META_INF = "/META-INF";
	private static final String MANIFEST_MF = META_INF + "/MANIFEST.MF";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.odcgroup.mdf.generation.MDFGenerator#doRun
	 */
	@Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
		try {
			createMetaInfFolders(outputContainer);
			createManifest(outputContainer);
		} catch (CoreException e) {
			String errorMsg = "Error during Manifest generation.";
			newCoreException(e, nonOkStatuses, errorMsg);
		}
	}

	private void createMetaInfFolders(IContainer outputContainer) throws CoreException {
		// Create (if required) the META-INF folder
		IPath metaInfPath = new Path(META_INF);
		IFolder metaInfFolder = outputContainer.getFolder(metaInfPath);
		if (!outputContainer.exists(metaInfPath)) {
			metaInfFolder.create(true, true, null);
		}
	}

	private void createManifest(IContainer outputContainer) throws CoreException {
		Manifest manifest = new Manifest();
		Attributes attributes = manifest.getMainAttributes();
		attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		attributes.put(new Attributes.Name("Built-By"), "DS");
		attributes.put(Attributes.Name.IMPLEMENTATION_VENDOR, "Odyssey Financial Technologies");
		attributes.put(Attributes.Name.SPECIFICATION_VENDOR, "Odyssey Financial Technologies");
		attributes.put(Attributes.Name.CLASS_PATH, "all.jar");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			manifest.write(os);
		} catch (IOException e) {
			newCoreException("Unable to write the manifest", e);
		}

		// Create or update the MANIFEST.MF file
		IFile dotModelsFile = outputContainer.getFile(new Path(MANIFEST_MF));
		InputStream temporaryInputStream = new ByteArrayInputStream(os.toByteArray());
		if (dotModelsFile.exists()) {
			dotModelsFile.setContents(temporaryInputStream, true, true, null);
		} else {
			dotModelsFile.create(temporaryInputStream, true, null);
		}
	}

}
