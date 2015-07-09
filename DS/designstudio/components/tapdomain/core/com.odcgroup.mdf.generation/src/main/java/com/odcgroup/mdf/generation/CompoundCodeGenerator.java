package com.odcgroup.mdf.generation;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;


public class CompoundCodeGenerator implements CodeGenerator {

    private final CodeGenerator[] generators;

    public CompoundCodeGenerator(CodeGenerator... generators) {
        this.generators = generators;
    }

    public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
            IFolder outputFolder, List<IStatus> nonOkStatuses) {
        for (CodeGenerator generator : generators) {
            generator.run(monitor, project, modelResources, outputFolder, nonOkStatuses);
        }
    }
}
