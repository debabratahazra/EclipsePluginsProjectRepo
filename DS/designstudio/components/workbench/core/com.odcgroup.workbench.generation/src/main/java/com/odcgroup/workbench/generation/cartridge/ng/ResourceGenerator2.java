package com.odcgroup.workbench.generation.cartridge.ng;

import javax.inject.Inject;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.odcgroup.workbench.generation.GenerationCore;

/**
 * Generator for a given resource.
 * 
 * As opposed to {@link CodeGenerator2}, which is for a set of models (incl. an entire project).
 *
 * @author Michael Vorburger
 */
public class ResourceGenerator2 {
	private static final Logger logger = LoggerFactory.getLogger(ResourceGenerator2.class);

	private @Inject SimplerEclipseResourceFileSystemAccess2 fsa;

	// do NOT @com.google.inject.Inject these, not even (optional=true) 
	// because I do not know how to do this correctly, as it depends on context.. :( "URI scope" idea, may be later
	private CodeGenerator2 codeGenerator2;
	private URI uri;

	public void generate(IProject modelsProject, ModelLoader loader, IFolder outputFolder) throws CoreException {
		try {
			configureFileSystemAccess(modelsProject, outputFolder, new NullProgressMonitor() /* TODO DS-7029, monitor*/);
			getCodeGenerator2().doGenerate(uri, loader, fsa);
		} catch (Throwable t) {
			String msg = "Problem occured during Code Generation for: " + uri.toString();
			logger.error(msg, t);
			throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0, msg , t));
		}
		// TODO DS-7029 monitor.worked(1);
	}
	
	private CodeGenerator2 getCodeGenerator2() {
		Preconditions.checkNotNull(codeGenerator2, "CodeGenerator2 neither @Inject'd, nor setCodeGenerator2() ... :(");
		return codeGenerator2;
	}

	public void setCodeGenerator2(CodeGenerator2 codeGenerator2) {
		this.codeGenerator2 = codeGenerator2;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	// NOTE: code copy/pasted :( into com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator.getConfiguredIRISJavaGeneratorFSA(IFileSystemAccess)
	protected void configureFileSystemAccess(IProject modelsProject, IFolder outputFolder, IProgressMonitor monitor) {
		fsa.setProject(outputFolder.getProject());
		fsa.setOutputPath(outputFolder.getProjectRelativePath().toString());
		fsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).setCreateOutputDirectory(true);
		fsa.setMonitor(monitor); // NOTE EclipseResourceFileSystemAccess2 *HAS* to have an IProgressMonitor (it doesn't check for null)
	}

}
