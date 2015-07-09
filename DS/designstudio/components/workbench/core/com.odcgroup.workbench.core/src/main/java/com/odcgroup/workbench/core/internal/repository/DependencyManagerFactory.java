package com.odcgroup.workbench.core.internal.repository;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyChangeListener;
import com.odcgroup.workbench.core.repository.IDependencyManager;

/**
 * The dependency manager factory simply provides the current implementation of the 
 * dependency manager.
 * If we want to choose a different dependency mechanism than the VR Maven APIs, this
 * is the place to plug in a different implementation.
 *
 * @author Kai Kreuzer
 *
 */
public class DependencyManagerFactory {
	
	static private Logger logger = LoggerFactory.getLogger(DependencyManagerFactory.class);
	
	static private volatile IDependencyManager defaultDepManager = null;

	// map for storing singletons for the ofsProjects
	final protected static Map<IProject, IDependencyManager> depMgrs = new HashMap<IProject, IDependencyManager>();

	private static Object lock = new Object();

	/**
	 * Returns an IDependencyManager instance
	 *  
	 * @return an IDependencyManager instance
	 */
	public static IDependencyManager getDependencyManager() {
		if(defaultDepManager==null) {
			synchronized(lock) {
				if(defaultDepManager==null) {
					IDependencyManager depMgr = null;
					IConfigurationElement[] depMgrExts = OfsCore.getExtensions(OfsCore.DEPENDENCY_MANAGER_ID);
					IConfigurationElement highestPrio = null;
					int prio = -1;
					for(IConfigurationElement ext : depMgrExts) {
						try {
							int extPrio = Integer.parseInt(ext.getAttribute("priority"));
							if(extPrio>prio) {
								prio = extPrio;
								depMgr = (IDependencyManager) ext.createExecutableExtension("class");
							}
						} catch (Exception e) {
							logger.warn("Cannot read instantiate dependency manager class", e);
						}
					}
					if(depMgr!=null) {
						defaultDepManager = depMgr;
					} else {
						// create a dummy implementation
						defaultDepManager = new IDependencyManager() {
							public Set<IOfsModelContainer> getDependencies(
									IOfsProject ofsProject) {
								return Collections.emptySet();
							}
	
							public Set<String> getSubDirectories(
									IOfsModelContainer container, IPath path) {
								return Collections.emptySet();
							}
	
							public IContainerIdentifier getIdentifier(
									final IProject project) {
								return new IContainerIdentifier() {
									public String getVersion() {
										return "1.0";
									}
									
									public String getName() {
										return project.getName();
									}
									
									public String getGroupId() {
										return project.getName();
									}
								};
							}
	
							public Set<URI> getFileURIs(IOfsModelContainer container, IPath path) {
								return Collections.emptySet();
							}
	
							public boolean hasUnresolvedDependencies(IProject project) {
								return false;
							}
	
							public IContainerIdentifier[] getUnresolvedDependencies(IProject project) {
								return new IContainerIdentifier[0];
							}
	
							public void disableAutoResolution() {}
	
							public void enableAutoResolution() {}
	
							public void resolveDependencies() {}
	
							public void resolveDependencies(IProject project) {}
	
							public void resolveDependencies(IProject[] projects) {}
	
							public void addDependencyChangeListener(IDependencyChangeListener listener) {}
	
							public void removeDependencyChangeListener(IDependencyChangeListener listener) {}

							public void waitForResolutionJob() {}

							public void resetMavenSettings() {}
						};
					}
				}
			}
		}
		return defaultDepManager; 
	}

	/**
	 * Returns an IDependencyManager instance
	 *  
	 * @return an IDependencyManager instance
	 */
	public static IDependencyManager getDependencyManager(IProject project) {
		if(project == null) return getDependencyManager();
		
		if(!project.exists() || !project.isOpen()) return getDependencyManager();
		
		synchronized (project) {
			IDependencyManager depMgr = depMgrs.get(project);
			if(depMgr==null) {
				IConfigurationElement[] natExts = OfsCore.getExtensions(OfsCore.NATUREEXT_EXTENSION_ID);
				IConfigurationElement highestPrio = null;
				int prio = -1;
				for(IConfigurationElement ext : natExts) {
					String natureId = ext.getAttribute("natureId");
					try {
						if(project.hasNature(natureId)) {
							for(IConfigurationElement child : ext.getChildren()) {
								if("dependencyMgr".equals(child.getName())) {
									int childPrio = Integer.parseInt(child.getAttribute("priority"));
									if(childPrio>prio) {
										prio = childPrio;
										depMgr = (IDependencyManager) child.createExecutableExtension("class");
									}
								}
							}
						}
					} catch (Exception e) {
						logger.warn("Cannot read instantiate dependency manager class", e);
					}
				}
				if(depMgr==null) depMgr = getDependencyManager(); // get the default implementation
				depMgrs.put(project, depMgr);
			}
			return depMgr;
		}
	}
}
