package com.odcgroup.workbench.core.repository;

import java.util.Set;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;



public class ModelResourceLookup {

    private final IOfsProject ofsProject;
    private final String[] extensions;

    public ModelResourceLookup(IOfsProject ofsProject, String[] extensions) {
        this.ofsProject = ofsProject;
        this.extensions = extensions.clone();
    }

    public Set<IOfsModelResource> getAllOfsModelResources() {
    	return ofsProject.getModelResourceSet().getAllOfsModelResources(extensions, IOfsProject.SCOPE_ALL);
    }

    public Set<IOfsModelResource> getAllOfsModelResources(int scope) {
    	return ofsProject.getModelResourceSet().getAllOfsModelResources(extensions, scope);
    }
}