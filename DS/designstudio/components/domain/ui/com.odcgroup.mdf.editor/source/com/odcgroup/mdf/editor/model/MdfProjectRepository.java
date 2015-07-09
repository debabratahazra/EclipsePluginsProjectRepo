package com.odcgroup.mdf.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.utils.RepositorySynchronizer;


/**
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 2.0
 */
public class MdfProjectRepository {
    static final Logger LOGGER = Logger.getLogger(MdfProjectRepository.class);
    private static MdfProjectRepository instance = null;
    private static final Map modelChangeListeners = new IdentityHashMap();

    /** Do not use a map because project names can change over time */
    private Set projects = new HashSet();
    private boolean initialized = false;

    /**
     * Constructor for MdfProjectRepository
     */
    private MdfProjectRepository() {
        super();
    }

    public static synchronized MdfProjectRepository getInstance() {
        if (instance == null) {
            instance = new MdfProjectRepository();
        }

        return instance;
    }

    public static synchronized void dispose() {
        instance = null;
    }

    public static void addModelChangeListener(ModelChangeListener listener) {
        synchronized (modelChangeListeners) {
            modelChangeListeners.put(listener, listener);
        }
    }

    public static void fireModelChangedEvent(
        Object source, Object element, int event, IProgressMonitor monitor)
        throws CoreException {

        if (element instanceof Resource) {
            Resource resource = (Resource) element;
            if (resource.getContents().isEmpty()) {
                return;
            } else {
                element = resource.getContents().get(0);
            }
        }

        fireModelChangedEvent(new ModelChangedEvent(source, element, event));
    }

    public static void removeModelChangeListener(ModelChangeListener listener) {
        synchronized (modelChangeListeners) {
            modelChangeListeners.remove(listener);
        }
    }
    
    public MdfProject getProject(String name) {
        synchronized (projects) {
            Iterator it = projects.iterator();

            while (it.hasNext()) {
                MdfProject project = (MdfProject) it.next();

                if (name.equals(project.getName()))
                    return project;
            }

            return null;
        }
    }

    public Collection getProjects() {
        synchronized (projects) {
            Collection copy = new ArrayList(projects);
            return Collections.unmodifiableCollection(copy);
        }
    }

    public void addProject(MdfProject project) {
        synchronized (projects) {
            projects.add(project);
        }
    }

    public MdfProject removeProject(String name) {
        synchronized (projects) {
            Iterator it = projects.iterator();

            while (it.hasNext()) {
                MdfProject project = (MdfProject) it.next();

                if (name.equals(project.getName())) {
                    it.remove();
                    return project;
                }
            }

            return null;
        }
    }

    protected static void fireModelChangedEvent(final ModelChangedEvent event) {
        synchronized (modelChangeListeners) {
            Iterator it = modelChangeListeners.keySet().iterator();

            while (it.hasNext()) {
                final ModelChangeListener listener =
                    (ModelChangeListener) it.next();

                if (listener != event.getSource()) {
                    listener.onModelChanged(event);
                }
            }
        }
    }

    public IStatus init() {
        if (!initialized) {
            try {
                loadProjects(new NullProgressMonitor());
            } catch (CoreException e) {
                LOGGER.error(e, e);
                return e.getStatus();
            }
            
            MdfPlugin.getWorkspace().addResourceChangeListener(
                    new RepositorySynchronizer(), IResourceChangeEvent.POST_CHANGE);
            
            initialized = true;
        }

        return Status.OK_STATUS;
    }

    private void loadProjects(IProgressMonitor monitor)
        throws CoreException {
        IWorkspaceRoot root = MdfPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        monitor.beginTask("Loading MDF projects", 2 * projects.length);

        try {
            for (int i = 0; i < projects.length; i++) {
                IProject proj = projects[i];

                if (monitor.isCanceled())
                    return;

                if (proj.isOpen() && proj.hasNature(MdfCore.NATURE_ID)) {
                    MdfProject project = new MdfProject(proj);
                    project.load(new SubProgressMonitor(monitor, 1));
                    addProject(project);

                    /*
                     * fireModelChangedEvent(this, project,
                     * ModelChangedEvent.ELEMENT_ADDED, new
                     * SubProgressMonitor(monitor, 1));
                     */
                } else {
                    monitor.worked(2);
                }
            }
        } finally {
            monitor.done();
        }
    }
}
