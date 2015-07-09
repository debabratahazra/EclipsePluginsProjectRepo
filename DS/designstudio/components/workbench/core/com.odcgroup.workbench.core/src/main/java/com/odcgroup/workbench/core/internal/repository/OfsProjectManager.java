package com.odcgroup.workbench.core.internal.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyChangeListener;
import com.odcgroup.workbench.core.repository.IOfsProjectManager;
import com.odcgroup.workbench.core.resources.OfsProject;

public class OfsProjectManager implements IResourceChangeListener, IOfsProjectManager, IDependencyChangeListener {

	static private Logger logger = LoggerFactory.getLogger(OfsProjectManager.class);
	
	static private OfsProjectManager instance;

	static public synchronized IOfsProjectManager getInstance() {
		if (instance == null) {
			instance = new OfsProjectManager();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(instance, IResourceChangeEvent.POST_CHANGE);
			OfsCore.getDependencyManager().addDependencyChangeListener(instance);
		}
		return instance;
	}
	
	// map for storing singletons for the model ofsProjects
	final protected static Map<IProject, IOfsProject> ofsProjects = Collections.synchronizedMap(new HashMap<IProject, IOfsProject>());

	/**
	 * Returns the singleton ofs model project for a given project
	 * 
	 * @param project
	 *            the project for which the ofs model project is requested
	 * @return the singleton instance of the ofs model project
	 */
	public IOfsProject getOfsProject(IProject project) {
		if(project==null) return null;
		if(!OfsCore.isOfsProject(project)) return null;
		
		synchronized (project) {
			IOfsProject ofsProject = ofsProjects.get(project);
			if (ofsProject == null) {
				ofsProject = createOfsProject(project);
				if(ofsProject!=null) {
					ofsProjects.put(project, ofsProject);
				}
			}
			return ofsProject;
		}
	}

	/**
	 * Returns a set of all ofs projects currently in the workspace
	 * 
	 * @return the set of all ofs projects
	 */
	public Set<IOfsProject> getAllOfsProjects() {
		Set<IOfsProject> ofsProjects = new HashSet<IOfsProject>();
		
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject project : projects) {
			if(project.isOpen()) {
				IOfsProject ofsProject = getOfsProject(project);
				if(ofsProject!=null) ofsProjects.add(ofsProject);
			}
		}
		return ofsProjects;
	}
	
	/**
	 * Creates a new IOfsProject by checking all nature extensions for
	 * possible custom implementations
	 * 
	 * @param project the physical project for which the ofs project should be created
	 * @return an IOfsProject implementation
	 */
	protected IOfsProject createOfsProject(IProject project) {
		if(project == null) return null;
		
		IOfsProject ofsProject = null;
		
		if(!project.exists() || !project.isOpen()) return null;
		
		IConfigurationElement[] natExts = OfsCore.getExtensions(OfsCore.NATUREEXT_EXTENSION_ID);
		for(IConfigurationElement ext : natExts) {
			IConfigurationElement highestPrio = null;
			int prio = -1;
			String natureId = ext.getAttribute("natureId");
			try {
				if(project.hasNature(natureId)) {
					for(IConfigurationElement child : ext.getChildren()) {
						if("projectImpl".equals(child.getName())) {
							int childPrio = Integer.parseInt(child.getAttribute("priority"));
							if(childPrio>prio) {
								prio = childPrio;
								ofsProject = (IOfsProject) child.createExecutableExtension("class");
								ofsProject.setProject(project);
							}
						}
					}
				}
			} catch (Exception e) {
				logger.warn("Cannot instanciate IOfsProject class", e);
			}
		}
		if(ofsProject==null) {
			ofsProject = new OfsProject(project);
		}
		return ofsProject;
	}
	
	static public void dispose() {
		for (IProject project : ofsProjects.keySet()) {
			_invalidateOfsProject(project);
		}
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(instance);
		instance = null;
	}

	public synchronized void resourceChanged(IResourceChangeEvent event) {
		switch (event.getType()) {
		case IResourceChangeEvent.POST_CHANGE:
			IResource res = event.getDelta().getResource();

			// remove project from map, if it has been deleted from the workspace
			if((res instanceof IWorkspaceRoot) ) {
				IResourceDelta[] children = event.getDelta().getAffectedChildren();
				if(children.length==1) {
					IResource child = children[0].getResource();
					if((child instanceof IProject) && children[0].getKind()==IResourceDelta.REMOVED) {
						invalidateOfsProject((IProject) child);
					}
				}
			}
						
			// notify all ofs projects of the change
			for(IOfsProject ofsProject : ofsProjects.values()) {
				if(ofsProject!=null) ofsProject.resourceChanged(event);
			}
		}
	}

	public void invalidateOfsProject(IProject project) {
		_invalidateOfsProject(project);
	}
	
	private static void _invalidateOfsProject(IProject project) {
		IOfsProject ofsProject = ofsProjects.get(project);
		if (ofsProject != null)
			ofsProject.clearCache();
		ofsProjects.remove(project);
	}

	@Override
	public void dependenciesChanged(IProject project) {
		IOfsProject ofsProject = getOfsProject(project);
		if(ofsProject!=null) ofsProject.refresh();
	}
}
