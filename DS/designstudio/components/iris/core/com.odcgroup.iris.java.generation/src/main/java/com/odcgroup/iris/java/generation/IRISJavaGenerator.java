package com.odcgroup.iris.java.generation;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.odcgroup.iris.generation.FileSystemHelper;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemNotifier;
import com.temenos.interaction.rimdsl.generator.RIMDslGeneratorSpringPRD;

public class IRISJavaGenerator implements CodeGenerator2, CodeGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(IRISJavaGenerator.class);
	private final SimplerEclipseResourceFileSystemNotifier changesNotifier = new SimplerEclipseResourceFileSystemNotifier();
	private final FileSystemHelper fileSystemHelper = new FileSystemHelper();

	// NOTE This String is DUPLICATED here in this class and in the pluin.xml
	public static final String ID = "com.odcgroup.iris.generation.java";

	private static String RIM_FILE_EXTENSION = "rim";

	private @Inject RIMDslGeneratorSpringPRD generator;

	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		try {
			if (!input.fileExtension().equals(RIM_FILE_EXTENSION))
				return;

			IFileSystemAccess tmpFsa = fileSystemHelper.determineFsa(fsa);

			/*
			* This will automatically update the lastChanges file everytimes a file is updated.
			*/
			if (tmpFsa instanceof SimplerEclipseResourceFileSystemAccess2){
				((SimplerEclipseResourceFileSystemAccess2)tmpFsa).setPostProcessor(changesNotifier);
			}
			
			generator.doGenerate(loader.getResource(input), tmpFsa);
		} catch (Exception e) {
			LOGGER.error("An error occurred while generating Java code for RIM ["+input+"]", e);
		}
	}		

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new NotImplementedException();
	}

}
