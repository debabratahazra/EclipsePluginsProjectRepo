package com.odcgroup.mdf.editor.model.utils;

import java.io.IOException;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.editor.model.MdfMarkerUtils;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFileVisitor;


public class ProjectLoader implements ModelFileVisitor {
    private static final Logger LOGGER = Logger.getLogger(ProjectLoader.class);
    private final MdfProject project;

    /**
     * Creates a new MdfDomainLoader object.
     */
    public ProjectLoader(MdfProject project) {
        this.project = project;
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelFileVisitor#accept(org.eclipse.core.resources.IFile)
     */
    public boolean accept(IFile file) {
        int eventType = ModelChangedEvent.ELEMENT_UPDATED;
        Resource resource = project.getResource(file, false);
        if (resource == null) {
            resource = project.createResource(file);
            eventType = ModelChangedEvent.ELEMENT_ADDED;
        }

        try {
            resource.load(Collections.EMPTY_MAP);
            MdfProjectRepository.fireModelChangedEvent(
                null, resource, eventType,
                new NullProgressMonitor());
        } catch (IOException e) {
            LOGGER.error(e, e);
            MdfMarkerUtils.createTransientMarker(file, e);
        } catch (CoreException e) {
            LOGGER.error(e, e);
            MdfMarkerUtils.createTransientMarker(file, e.getStatus());
        }

        return true;
    }
}
