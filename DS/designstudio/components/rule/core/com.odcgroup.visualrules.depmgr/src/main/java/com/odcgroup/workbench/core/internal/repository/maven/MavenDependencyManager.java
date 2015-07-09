package com.odcgroup.workbench.core.internal.repository.maven;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.ZipFileHelper;
import com.odcgroup.workbench.core.repository.IDependencyChangeListener;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.resources.OfsModelContainer;

import de.visualrules.artifact.core.project.ArtifactProjectChange;
import de.visualrules.artifact.core.project.IArtifactProjectChangeListener;
import de.visualrules.artifact.eclipse.ArtifactActivator;
import de.visualrules.artifact.eclipse.IArtifactManager;
import de.visualrules.artifact.eclipse.IInternalArtifactManager;
import de.visualrules.artifact.model.core.IArtifact;
import de.visualrules.artifact.model.core.IArtifactProject;
import de.visualrules.artifact.model.core.IResolutionNode;
import de.visualrules.artifact.model.core.IResolutionResult;
import de.visualrules.artifact.model.util.Identifier;

/**
 * This is a specific implementation of a dependency manager for the VisualRules
 * Maven API. As VisualRules itself makes use of Maven and comes with an
 * easy-to-use facade for the Maven APIs, we decided to base our dependency
 * mechanism on top of this. If we one day have to get rid of VisualRules, we
 * will have to implement our own facade for Maven, but the architecture is set
 * up in a way that this should be easy to swap (see DependencyManagerFactory).
 * 
 * @author Kai Kreuzer
 * 
 */
public class MavenDependencyManager implements IDependencyManager, IArtifactProjectChangeListener {

	protected static final Logger logger = LoggerFactory.getLogger(MavenDependencyManager.class);
	protected static boolean trace = OfsCore.shouldTrace(OfsCore.TRACE_DEPMGMT);
	
	protected static final String VR_MARKER = "de.visualrules.artifact.eclipse.validationProblem"; 
	
	final protected static Map<IProject, Set<IOfsModelContainer>> cachedDeps = new HashMap<IProject, Set<IOfsModelContainer>>();
	final protected static Map<String, Set<URI>> cachedFileURIs = new HashMap<String, Set<URI>>();
	final protected static Map<String, Set<String>> cachedSubDirs = new HashMap<String, Set<String>>();
	
	final protected static Set<IProject> projectsToRefresh = new HashSet<IProject>();
	protected Set<IDependencyChangeListener> changeListeners = new HashSet<IDependencyChangeListener>(); 
	
	public MavenDependencyManager() {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if(artifactManager!=null) {
			artifactManager.addArtifactProjectChangeListener(this);
		}
	}
	
	public void dispose() {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if(artifactManager!=null) {
			artifactManager.removeArtifactProjectChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.core.internal.repository.IDependencyManager#
	 * getDependencies(com.odcgroup.workbench.core.IOfsModelContainer)
	 */
	@SuppressWarnings("unchecked")
	public synchronized Set<IOfsModelContainer> getDependencies(IOfsProject ofsProject) {

		if(!cachedDeps.containsKey(ofsProject.getProject())) {		
			Set<IOfsModelContainer> deps = new HashSet<IOfsModelContainer>();
	
			IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
			if (artifactManager != null) {
				IArtifact artifact = getArtifact(ofsProject);
		
				if(artifact instanceof IArtifactProject) {
					IArtifactProject artifactProject = (IArtifactProject) artifact;
					if(artifactProject.getLastResolutionResult()==null) {
						// for tracing OfsCore.getDefault().logWarning("No dependency resolution result available for project " + ofsProject.getName(), null);
						return deps;
					}
					for(IArtifact dependency : (List<IArtifact>) artifactProject.getLastResolutionResult().getResolvedArtifacts()) {
						if (dependency.getArtifactId().equals("rule-core-models")
								&& dependency.getGroupId().equals("com.odcgroup.ds"))
							continue;
						if(!dependency.getArtifactId().endsWith("-models")) continue;
						if (dependency.isProject() && dependency instanceof IArtifactProject) {
							deps.add(OfsCore.getOfsProjectManager().getOfsProject(
									artifactManager.getIProject((IArtifactProject) dependency)));
						} else {
							deps.add(new OfsModelContainer(new VRMavenContainerIdentifier(dependency.getGroupId(), dependency
									.getArtifactId(), dependency.getVersion())));
						}
					}
				}
				cachedDeps.put(ofsProject.getProject(), deps);
			} else {
				logger.warn("No ArtifactManager available, ignoring cache refresh in MavenDependencyManager.getDependencies");
				return Collections.emptySet();
			}
		}
		return cachedDeps.get(ofsProject.getProject());
	}

	protected IArtifact getArtifact(IOfsModelContainer container) {
		IArtifact artifact = null;
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			if (container instanceof IOfsProject) {
				IOfsProject ofsProject = (IOfsProject) container;
				IArtifactProject artifactProject = artifactManager.getArtifactProject(ofsProject.getProject());
				artifact = artifactProject;
			} else {
				if (container.getIdentifier() instanceof Identifier) {
					artifact = artifactManager.getArtifact((Identifier) container.getIdentifier(), false);
				}
			}
			return artifact;
		} else {
			logger.warn("No ArtifactManager available, assuming no artifact found (MavenDependencyManager.getArtifact)");
			return null;
		}
	}
	
	protected IArtifact getArtifact(IProject project) {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			return artifactManager.getArtifactProject(project);
		} else {
			logger.warn("No ArtifactManager available, assuming no artifact found (MavenDependencyManager.getArtifact(IProject))");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.core.internal.repository.IDependencyManager#
	 * getSubDirectories(com.odcgroup.workbench.core.IOfsModelContainer,
	 * org.eclipse.core.runtime.IPath)
	 */
	public Set<String> getSubDirectories(IOfsModelContainer container, IPath path) {
		Set<String> subDirs = null; 
		if (container instanceof IOfsProject) {
			subDirs = new HashSet<String>();
			IProject project = ((IOfsProject) container).getProject();
			try {
				for (IResource resource : project.getFolder(path).members()) {
					if (resource instanceof IFolder) {
						subDirs.add(resource.getName());
					}
				}
			} catch (CoreException e) {
				// Path does not exist in project, so do nothing
			}
		} else {
			synchronized(path) {
				subDirs = cachedSubDirs.get(getHashKey(container, path)); 
				if(subDirs==null) {
					subDirs = new HashSet<String>();
					IArtifact artifact = getArtifact(container);
					if (artifact != null) {
						Enumeration<? extends ZipEntry> zipEntries;
						try {
							zipEntries = new ZipFile(artifact.getLocation()).entries();
							subDirs.addAll(ZipFileHelper.getSubDirectories(zipEntries, path));
						} catch (ZipException e) {
							OfsCore.getDefault().logError("Error while browsing zip file " + artifact.getLocation().getName(), e);
						} catch (IOException e) {
							OfsCore.getDefault().logError("Error while browsing zip file " + artifact.getLocation().getName(), e);
						}
					}
					cachedSubDirs.put(getHashKey(container, path), subDirs);
				}
			}
		}
		return subDirs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.core.internal.repository.IDependencyManager#
	 * getIdentifier(org.eclipse.core.resources.IProject)
	 */
	public IContainerIdentifier getIdentifier(IProject project) {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();	
		if (artifactManager != null) {
			IArtifactProject artifactProject = artifactManager.getArtifactProject(project);
			if (artifactProject == null)
				return null;
			return new VRMavenContainerIdentifier(artifactProject.getGroupId(), artifactProject.getArtifactId(),
					artifactProject.getVersion());
		} else {
			logger.warn("No ArtifactManager available, assuming no identifier found (MavenDependencyManager.getIdentifier(IProject))");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.core.internal.repository.IDependencyManager#getFiles
	 * (com.odcgroup.workbench.core.IOfsModelContainer,
	 * org.eclipse.core.runtime.IPath)
	 */
	public Set<URI> getFileURIs(IOfsModelContainer container, IPath path) {
		Set<URI> uris = null;
		if (container instanceof IOfsProject) {
			uris = new HashSet<URI>();
			IProject project = ((IOfsProject) container).getProject();
			try {
				for (IResource resource : project.getFolder(path).members()) {
					if (resource instanceof IFile) {
						uris.add(resource.getLocation().toFile().toURI());
					}
				}
			} catch (CoreException e) {
				// Path does not exist in project, so do nothing
			}
		} else {
			synchronized(path) {
				uris = cachedFileURIs.get(getHashKey(container, path));
				if(uris==null) {
					uris = new HashSet<URI>();
					IArtifact artifact = getArtifact(container);
					if (artifact != null) {
						try {
							Enumeration<? extends ZipEntry> zipEntries = new ZipFile(artifact.getLocation()).entries();
							for (IPath filePath : ZipFileHelper.getFiles(zipEntries, path)) {
								URI url = new URI("jar:" + artifact.getLocation().toURI().toURL().toString() + "!/"
										+ filePath.toString());
								uris.add(url);
							}
						} catch (ZipException e) {
							OfsCore.getDefault().logError("Error while browsing zip file " + artifact.getLocation().getName(), e);
						} catch (IOException e) {
							OfsCore.getDefault().logError("Error while browsing zip file " + artifact.getLocation().getName(), e);
						} catch (URISyntaxException e) {
							OfsCore.getDefault().logError("Error while browsing zip file " + artifact.getLocation().getName(), e);
						}
					}
					cachedFileURIs.put(getHashKey(container, path), uris);
				}
			}
		}
		return uris;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.core.internal.repository.IDependencyManager#
	 * getUnresolvedDependencies(com.odcgroup.workbench.core.IOfsProject)
	 */
	public IContainerIdentifier[] getUnresolvedDependencies(final IProject project) {
		Set<IContainerIdentifier> unresolvedIdentifiers = new HashSet<IContainerIdentifier>();

		IArtifactProject artifactProject = (IArtifactProject) getArtifact(project);

		if(artifactProject==null) {
			unresolvedIdentifiers.add(new IContainerIdentifier() {
				public String getGroupId() {
					return "";
				}

				public String getName() {
					return project.getName();
				}
				
				public String getVersion() {
					return "";
				}
				
				public String toString() {
					return "any dependency of '" + project.getName() + "'";
				}
			});
			logger.debug("No artifact project available for project {}", project.getName());
		} else { 		
			if(artifactProject.getLastResolutionResult()!=null) {
				Iterator<? extends IResolutionNode> it = artifactProject.getLastResolutionResult().getAllNodes();
				while(it.hasNext()) {
					IResolutionNode node = it.next();
					if(node.isEnabled() && !node.isOptional() && node.getArtifact()==null) {
						unresolvedIdentifiers.add(new VRMavenContainerIdentifier(node.getGroupId(), node
								.getArtifactId(), node.getVersionSpecification()));
					}
				}
			} else {
				// resolution result must not be null - if it is, the dependencies have not
				// yet been successfully resolved, so we add the project itself to be missing
				logger.debug("No last resolution result available for project {}", project.getName());
			}
		}

		return unresolvedIdentifiers.toArray(new IContainerIdentifier[unresolvedIdentifiers.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.core.internal.repository.IDependencyManager#
	 * hasUnresolvedDependencies(com.odcgroup.workbench.core.IOfsProject)
	 */
	public boolean hasUnresolvedDependencies(IProject project) {
		IArtifactProject artifactProject = (IArtifactProject) getArtifact(project);

		if(artifactProject==null) {
			logger.debug("No artifact project available for project {}", project.getName());
			return true; // project itself does not seem to be resolved
		}
		
		if(artifactProject.getLastResolutionResult()!=null) {
			Iterator<? extends IResolutionNode> it = artifactProject.getLastResolutionResult().getAllNodes();
			while(it.hasNext()) {
				IResolutionNode node = it.next();
				// DS-4002 added ds-model exception to workaround resolution of ds-models artifact
				// when running OCS packaging.
				if(node.isEnabled() && !node.isOptional() && node.getArtifact()==null
						&& !node.getType().equals("ds-models")) {
					logger.debug("Unresolved node '{}' found for project {}", node.getArtifactId(), project.getName());
					return true;
				}
			}
		} else {
			// resolution result must not be null - if it is, the dependencies have not
			// yet been successfully resolved, so we return true to make sure that this is noticed.
			logger.debug("No last resolution result available for project {}", project.getName());
			return true;
		}
		return false;
	}


	public void waitForResolutionJob() {
		if (Job.getJobManager().find(IArtifactManager.FAMILY_ARTIFACT_JOB).length > 0) {
			try {
				Job.getJobManager().join(IArtifactManager.FAMILY_ARTIFACT_JOB, null);
			} catch (Exception e) {
				logger.debug(OfsCore.LOG_MARKER, "Joining resolution job failed!", e);
			}
		}
	}
	
	/**
	 * Reset the maven settings
	 */
	public void resetMavenSettings() {
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					ArtifactActivator.getDefault().resetMaven(null);
				}
			}, null);
		} catch (CoreException e) {
			logger.error(OfsCore.LOG_MARKER, "Failed reloading Maven settings", e);
		}
	}

	/* (non-Javadoc)
	 * @see de.visualrules.artifact.core.project.IArtifactProjectChangeListener#handleArtifactProjectsChanged(de.visualrules.artifact.core.project.ArtifactProjectChange[])
	 */
	public void handleArtifactProjectsChanged(ArtifactProjectChange[] projectChanges) {
		Set<IArtifactProject> changedProjects = new HashSet<IArtifactProject>();
		
		for(ArtifactProjectChange change : projectChanges) {
			if(change.getArtifactProject()!=null) changedProjects.add(change.getArtifactProject());
		}
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			for(final IArtifactProject artifactProject : changedProjects) {
				final IProject project = artifactManager.getIProject(artifactProject);
				cachedDeps.remove(project);
				if(project.exists() && project.isOpen()) {
					if(!isAutoResolutionEnabled()) projectsToRefresh.add(project);
					// we need to notify the listeners about the change
					for(IDependencyChangeListener listener : changeListeners) {
						listener.dependenciesChanged(project);
					}
	
					// do some debug logging
					if(logger.isDebugEnabled(OfsCore.LOG_MARKER)) {
						logger.debug(OfsCore.LOG_MARKER, "Dependencies of project '{}' have changed and were removed from cache.", project.getName());
						IResolutionResult lastResult = artifactProject.getLastResolutionResult();
						if(lastResult!=null) {
							for(Object obj : artifactProject.getLastResolutionResult().getResolvedArtifacts()) {
								IArtifact artifact = (IArtifact) obj;
								if (artifact.getArtifactId().equals("rule-core-models")
										&& artifact.getGroupId().equals("com.odcgroup.ds"))
									continue;
								logger.debug(OfsCore.LOG_MARKER, "Dependency '{}' located at '{}'", artifact.getArtifactId(), artifact.getLocation().toString());
							}
						}
					}					
				}
				cachedFileURIs.clear();
				cachedSubDirs.clear();
			}
		} else {
			logger.warn("No ArtifactManager available, ignoring MavenDependencyManager.handleArtifactProjectsChanged");
		}
	}

	protected String getHashKey(IOfsModelContainer container, IPath path) {
		return container.getIdentifier().toString() + path.toString();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.repository.IDependencyManager#disableAutoResolution()
	 */
	public void disableAutoResolution() {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			if(((IInternalArtifactManager) artifactManager).isResolvingDependencies()) {
				logger.debug(OfsCore.LOG_MARKER, "Disabling dependency auto-resolution.");
				((IInternalArtifactManager) artifactManager).setResolveDependencies(false);
			}
		} else {
			logger.warn("No ArtifactManager available, ignoring MavenDependencyManager.disableAutoResolution");
			return;
		}
	}

	public void enableAutoResolution() {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			if(!((IInternalArtifactManager) artifactManager).isResolvingDependencies()) {
				logger.debug(OfsCore.LOG_MARKER, "Enabling dependency auto-resolution.");
				// let's refresh all projects that have change while auto-resolution was turned off
				resolveDependencies(projectsToRefresh.toArray(new IProject[0]));
				projectsToRefresh.clear();
				((IInternalArtifactManager) artifactManager).setResolveDependencies(true);
			}		
		} else {
			logger.warn("No ArtifactManager available, ignoring MavenDependencyManager.enableAutoResolution");
			return;
		}
	}

	private boolean isAutoResolutionEnabled() {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			return ((IInternalArtifactManager) artifactManager).isResolvingDependencies();
		} else {
			logger.warn("No ArtifactManager available, assuming AutoResolution turned off (MavenDependencyManager.isAutoResolutionEnabled)");
			return false;
		}
	}
	
	public void resolveDependencies() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		resolveDependencies(projects);
	}

	public void resolveDependencies(IProject project) {
		resolveDependencies(new IProject[] {project});
	}

	public void resolveDependencies(IProject[] projects) {
		IArtifactManager artifactManager = ArtifactActivator.getArtifactManager();
		if (artifactManager != null) {
			Set<IArtifactProject> modelProjects = new HashSet<IArtifactProject>();
			for(final IProject project : projects) {
				IArtifactProject artifactProject = null;
				int timeout = 10;
				while((artifactProject = artifactManager.getArtifactProject(project))==null) {
					logger.debug(OfsCore.LOG_MARKER, "Waiting for IArtifactProject for project '{}'.", project.getName());
					try {
						timeout--;
						if(timeout==0) {
							logger.debug(OfsCore.LOG_MARKER, "Timeout reached waiting for artifact to be resolved for project '{}'.", project.getName());
							break;
						}
						else Thread.sleep(500L);
					} catch (InterruptedException e) {}
				}
				if(artifactProject==null) {
					logger.debug(OfsCore.LOG_MARKER, "Failed retrieving project artifact for project '{}'.", project.getName());
					try {
						IMarker[] markers = project.findMarkers(VR_MARKER, true, IResource.DEPTH_ONE);
						for(IMarker marker : markers) {
							logger.debug(OfsCore.LOG_MARKER, marker.getAttribute("message", ""));
						}
					} catch (CoreException e) {
						logger.debug(OfsCore.LOG_MARKER, "Failed reading markers for project '{}'", project.getName());
					}
					continue;
				}
				modelProjects.add(artifactProject);
			}
			
			if(modelProjects.size() > 0) {
				logger.debug(OfsCore.LOG_MARKER, "Resolving dependencies...");
				artifactManager.forceUpdate(modelProjects.toArray(new IArtifactProject[modelProjects.size()]), false);
				waitForResolutionJob();
				
				logger.debug(OfsCore.LOG_MARKER, "Finished resolving dependencies.");
			}
		} else {
			logger.warn("No ArtifactManager available, ignoring MavenDependencyManager.resolveDependencies");
			return;
		}
	}

	public void addDependencyChangeListener(IDependencyChangeListener listener) {
		changeListeners.add(listener);
	}

	public void removeDependencyChangeListener(IDependencyChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	/**
	 * @return the local repository to force, or <code>null</code> if
	 * not defined.
	 */
//	public static String getForcedLocalRepository() {
//		return System.getProperty("maven.repo.local");		
//	}
}
