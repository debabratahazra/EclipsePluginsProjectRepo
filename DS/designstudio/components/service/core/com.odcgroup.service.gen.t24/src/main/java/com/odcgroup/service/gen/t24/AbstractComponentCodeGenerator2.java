package com.odcgroup.service.gen.t24;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.service.gen.t24.internal.cartridges.mapper.ServiceModel2ServiceDescriptionMapper;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.utils.ComponentNameHelper;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
import com.odcgroup.service.model.component.Component;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * abstract component code generator
 * 
 * @author phanikumark
 * 
 */
public abstract class AbstractComponentCodeGenerator2 implements CodeGenerator, CodeGenerator2 {

	Logger logger = LoggerFactory.getLogger(AbstractComponentCodeGenerator2.class);

	public static final String COMPONENT_EXTN = "component";

	@Override
	public final void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		if (isInterested(input)) {
			ServiceModel2ServiceDescriptionMapper mapper = new ServiceModel2ServiceDescriptionMapper();
			Component component = loader.getRootEObject(input, Component.class);
			ServiceDescriptor sd = mapper.map(component);
			sd.setComponentName(StringUtils.upperInitialCharacter(ComponentNameHelper.getComponentName(component
					.getName())));

			IFolder folder = getOutputFolder(fsa);
			String outputfolder = (folder != null) ? folder.getLocation().toOSString() : "output";
			for (ServiceGenerator generator : getServiceGenerator()) {
				generator.generate(sd, null, outputfolder);
			}
		}
	}

	protected abstract List<ServiceGenerator> getServiceGenerator() throws Exception;

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new UnsupportedOperationException();
	}

	private IFolder getOutputFolder(IFileSystemAccess fsa) {
		if (fsa instanceof SimplerEclipseResourceFileSystemAccess2) {
			SimplerEclipseResourceFileSystemAccess2 efsa = (SimplerEclipseResourceFileSystemAccess2) fsa;
			IProject project = efsa.getProject();
			OutputConfiguration outputConfig = efsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT);
			return project.getFolder(new Path(outputConfig.getOutputDirectory()));
		}
		return null;
	}

	protected boolean isInterested(URI uri) {
		String ext = uri.fileExtension();
		return ext.equals(COMPONENT_EXTN);
	}

}
