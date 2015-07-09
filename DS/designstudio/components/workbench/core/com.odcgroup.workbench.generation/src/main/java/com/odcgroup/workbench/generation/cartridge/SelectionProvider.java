package com.odcgroup.workbench.generation.cartridge;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.odcgroup.workbench.core.IOfsModelResource;

public interface SelectionProvider {

    /**
     * This method is called in order to give the code generator a chance 
     * @param selectedResourcesPerProject
     */
    public void completeModelResourceSelection(Map<IProject, Collection<IOfsModelResource>> selectedResourcesPerProject);
    
}
