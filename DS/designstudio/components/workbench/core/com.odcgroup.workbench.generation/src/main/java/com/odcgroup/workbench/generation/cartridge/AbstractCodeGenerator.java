package com.odcgroup.workbench.generation.cartridge;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.GenerationCore;

public abstract class AbstractCodeGenerator implements CodeGenerator {

	private static Logger logger = LoggerFactory.getLogger(AbstractCodeGenerator.class);
	
    public AbstractCodeGenerator() {
        super();
    }

    public final void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
            IFolder outputFolder, List<IStatus> nonOkStatuses) {
    	doRun(project, outputFolder, modelResources, nonOkStatuses);
    }

	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources,
			List<IStatus> nonOkStatuses) {
	}

    protected CoreException newCoreException(String message, Throwable t) throws CoreException {
        throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID,
                0, message, t));
    }
    
	protected CoreException newCoreException(Throwable t) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID,	0, t.toString(), t));
	}
	
	protected void newCoreException(Exception e, List<IStatus> nonOkStatuses, String errorMsg) {
		logger.error(errorMsg, e);
		final IStatus errorStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg + ": " + e.getMessage(), e);
		nonOkStatuses.add(errorStatus);
	}

}
