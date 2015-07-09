package com.odcgroup.visualrules.integration.datasync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.init.RulesInitializer;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.RulePackage;

public class DataTypeBuilder extends IncrementalProjectBuilder {

	private static final Logger logger = LoggerFactory.getLogger(DataTypeBuilder.class);
	
	class DomainDeltaVisitor implements IResourceDeltaVisitor {
		private IProgressMonitor monitor;
		
		public DomainDeltaVisitor(IProgressMonitor monitor) {
			this.monitor = monitor; 
		}

		public boolean visit(IResourceDelta delta) throws CoreException {
			final IResource resource = delta.getResource();
			
			if(!(resource instanceof IFile)) return true;
			final IFile file = (IFile) resource;
			
			if(file.exists() 
					&& ("pom.xml".equalsIgnoreCase(file.getName()))
					&& RulesIntegrationPlugin.isRulesEnabled(resource.getProject())) {	
				syncProjectDependencies(OfsCore.getOfsProject(file.getProject()), monitor);
			}

			if(!ArrayUtils.contains(DomainRepository.EXTENSIONS, file.getFileExtension())) return false;
			final IOfsModelResource modelResource = (IOfsModelResource) file.getAdapter(IOfsElement.class);
			if(modelResource==null) {
				RulesIntegrationPlugin.getDefault().logError("Cannot find model for file '" + file.getName() + "'", null);
				return false;
			}

			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					boolean wasLoaded = modelResource.isLoaded();
					try {
						if(file.exists() 
								&& (modelResource.getEMFModel().get(0) instanceof MdfDomain)
								&& RulesIntegrationPlugin.isRulesEnabled(file.getProject())
								&& modelResource!=null) {
							syncDomainModels(getProject(), Collections.singleton((MdfDomain)modelResource.getEMFModel().get(0)), false, monitor);
						}
					} catch (IOException e) {
						// if there is actually no resource or it cannot be read, ignore it
					} catch (InvalidMetamodelVersionException e) {
						RulesIntegrationPlugin.getDefault().logInfo("Model file '" + modelResource.getName() + "' has an invalid metamodel version!", e);
					}
					if(!wasLoaded) modelResource.unload();
					if(file.getFileExtension()!=null && RulesIntegrationPlugin.isRulesEnabled(file.getProject()) &&
						((file.getFileExtension().equals("mml") || file.getFileExtension().equals("domain") || file.getFileExtension().startsWith("vr")))) {
						markOutdatedRules(resource.getProject(), monitor);
					}
				}
			};
			ResourcesPlugin.getWorkspace().run(runnable, getProject(), IWorkspace.AVOID_UPDATE, monitor);

			return false;
		}
	}

	@SuppressWarnings("unchecked")
	static public void syncDomainModels(final IProject project, final Collection<MdfDomain> domainModels, boolean fullBuild, IProgressMonitor monitor) throws CoreException {
		if(domainModels.size()==0) return;

		String rootPackagePath = RulesIntegrationPlugin.getVRBasePath(project);
		final String internalPackagePath = rootPackagePath + "/" + RulesIntegrationPlugin.DOMAIN_PKG_NAME;
		
		monitor.beginTask("Synchronizing data types...", 2 + domainModels.size() + (fullBuild?4:0));

		if(RulesIntegrationPlugin.isRulesEnabled(project)) {
			monitor.subTask("Preparing the data type synchronization...");
			
			if(!(new RulesInitializer()).checkConfiguration(project).isOK()) return;
						
			for(MdfDomain domain : domainModels) {
				IFile modelFile = OfsResourceHelper.getFile(((MdfDomainImpl)domain).eResource());
				try {
					if(modelFile!=null) {
						modelFile.deleteMarkers(OfsCore.MARKER_ID, true, IResource.DEPTH_ZERO);
					}
				} catch (CoreException e) {
		        	RulesIntegrationPlugin.getDefault().logWarning(
		        			"Cannot delete problem markers on domain file " 
		        			+ modelFile.getName(), e);
				}
			}
			monitor.worked(1);
			if(monitor.isCanceled()) return;
				    	
			monitor.subTask("Data type synchronization: Transforming and merging data types...");
			try {				
				final IDataModelIntegration integration = IntegrationCore.getDataModelIntegration(project);

				// create the internal package if it does not yet exist
				boolean internalCreated = false;
				if(!integration.getSubPackages(rootPackagePath).contains(RulesIntegrationPlugin.DOMAIN_PKG_NAME)) {
					RulePackage internalPackage = IntegrationFactory.eINSTANCE.createRulePackage();
					internalPackage.setName(RulesIntegrationPlugin.DOMAIN_PKG_NAME);
					internalPackage.setExternal(true);
					try {
						integration.merge(internalPackage, rootPackagePath, null);
						integration.save(rootPackagePath);
					} catch (IntegrationException e) {
						newCoreException("Could not create internal package", e);
					}
					internalCreated = true;
				}

				for(MdfDomain domainModel : domainModels) {
					if(domainModel.getName()==null) continue;
					final EList<MdfEntity> entities = new BasicEList<MdfEntity>();
					entities.addAll(domainModel.getClasses());
					entities.addAll(domainModel.getDatasets());
					entities.addAll(domainModel.getEnumerations());
					monitor.subTask("Data type synchronization: Processing " + entities.size() + " data types of domain '" + domainModel.getName() + "'");

					RulePackage newPackage = DataTypeTransformer.transformToVRDataTypes(domainModel.getName(), entities);
					Date startTime = new Date();
					monitor.subTask("Data type synchronization: Merging " + entities.size() + " data types of domain '" + domainModel.getName() + "'");
					try {
						integration.merge(newPackage, internalPackagePath, null);
						integration.save(internalPackagePath);
					} catch (IntegrationException e) {
						logger.warn(RulesIntegrationPlugin.LOG_MARKER, "Merging data type package failed.", e);
					}
					logger.debug(RulesIntegrationPlugin.LOG_MARKER, 
							"{} data types of {} synchronized in {}s.", 
							new Object[] {String.valueOf(entities.size()), domainModel.getName(), ((new Date()).getTime()-startTime.getTime()) / 1000.0});
			    	if(monitor.isCanceled()) break;
			    	monitor.worked(1);
				}
				integration.clearUndoHistory(internalPackagePath);
				monitor.subTask("Data type synchronization: Saving data type packages...");
				for(MdfDomain domainModel : domainModels) {
					// let's save all new packages
					integration.save(internalPackagePath + "/" + domainModel.getName().toLowerCase());
				}
				if (!internalCreated) {
					integration.save(internalPackagePath);
				}
			} catch (IntegrationException e) {
				newCoreException("Cannot synchronize data types to Visual Rules", e);
			} catch (Exception e) {
				newCoreException("Cannot synchronize data types to Visual Rules", e);
			}
			
			if(fullBuild) {
				try {
					monitor.subTask("Data type synchronization: Removing outdated packages...");
					IDataModelIntegration integration = IntegrationCore.getDataModelIntegration(project);
					Collection<String> packages = integration.getSubPackages(internalPackagePath);
					for(MdfDomain domainModel : domainModels) {
						packages.remove(domainModel.getName().toLowerCase());
					}
					for(String pkg : packages) {
						integration.deletePackage(internalPackagePath + "/" + pkg);
					}
				} catch (IntegrationException e) {
				} catch(Exception e) {
					newCoreException("Cannot delete outdated package from VisualRules", e);
				} finally {
					monitor.worked(1);
				}
				try {
					monitor.subTask("Data type synchronization: Updating reuse settings...");
	
					IDataModelIntegration integration = IntegrationCore.getDataModelIntegration(project);
					
					boolean reusesChanged = false;
					
					List<String> oldReuses = integration.getPackageReuses(rootPackagePath);
					List<String> newReuses = new ArrayList<String>();
					for(MdfDomain domainModel : domainModels) {
						newReuses.add(internalPackagePath + "/" + domainModel.getName().toLowerCase());
					}
					monitor.worked(1);
					
					// Remove all old (local) reuses that are not in the new list
					for(String reuse : oldReuses) {
						if(reuse.startsWith(internalPackagePath) && !newReuses.contains(reuse)) {
							integration.removePackageReuse(rootPackagePath, reuse);
							reusesChanged = true;
						}
					}
					monitor.worked(1);
	
					// Add all new reuses that are not in the old list
					for(String reuse : newReuses) {
						if(!oldReuses.contains(reuse)) {
							integration.addPackageReuse(rootPackagePath, reuse);
							reusesChanged = true;
						}
					}
		
					if(reusesChanged) {
						integration.clearUndoHistory(rootPackagePath);
						integration.save(rootPackagePath);
					} else {
						integration.clearUndoHistory(internalPackagePath);
						integration.save(internalPackagePath);
					}
				} catch (IntegrationException e) {
				} catch (Exception e) {
					newCoreException("Cannot update package reuse settings", e);
				}
			}
		} else {
			for(MdfDomain domain : domainModels) {
				try {
					IFile file = OfsResourceHelper.getFile(((MdfDomainImpl)domain).eResource());
					if(file!=null) {
						if(file.findMarkers(OfsCore.MARKER_ID, false, IResource.DEPTH_ZERO).length==0) {
							IMarker marker = file.createMarker(OfsCore.MARKER_ID);
				 		    if (marker.exists()) {
						         marker.setAttribute(IMarker.MESSAGE, "Domain entities cannot be propagated to the rule editor as there are problems in the project configuration.");
						         marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
						   }
						}
					}
		        } catch (CoreException e) {
		        	RulesIntegrationPlugin.getDefault().logWarning("Cannot create problem marker on mml file.", e);
		        }
			}
		}
		monitor.done();
		return;
	}
	
	@SuppressWarnings("unchecked")
	static private void syncProjectDependencies(IOfsProject ofsProject, IProgressMonitor monitor) {

		monitor.subTask("Synchronizing project dependencies to rule model for project '" 
				+ ofsProject.getName() + "'");
		try {		
			IDataModelIntegration dataIntegration = IntegrationCore.getDataModelIntegration(ofsProject.getProject());
			String basePath = RulesIntegrationPlugin.getVRBasePath(ofsProject.getProject());
			
			if(basePath==null) return;

			List<String> newReuses = createReusesFromDependencies(ofsProject);
			List<String> existingReuses = dataIntegration.getPackageReuses(basePath);
	
			Collections.sort(newReuses);
			Collections.sort(existingReuses);
			
			List<String> diffs = (List<String>) CollectionUtils.disjunction(newReuses, existingReuses);

			// remove read-only flag if necessary
			if(diffs.size()>0) {
				IFile ruleFile = RulesIntegrationUtils.getRulesFile(ofsProject.getProject());
				RulesIntegrationUtils.removeReadOnlyFlags(ruleFile);
			}
			
			boolean reusesChanged = false;
			
			for(String diff : diffs) {
				if(existingReuses.contains(diff) &&
					!diff.equals("vrpath:/core") &&
					!diff.startsWith(basePath)) {
					try {
						dataIntegration.removePackageReuse(basePath + "/" + RulesIntegrationPlugin.DOMAIN_PKG_NAME, diff);
						reusesChanged = true;
					} catch(IntegrationException e) {
						RulesIntegrationPlugin.getDefault().logError("cannot remove reuse " + diff, e);
					}
				} else if(newReuses.contains(diff)) {
					try {
						dataIntegration.addPackageReuse(basePath, diff);						
					} catch(IllegalArgumentException e) {
						// do a second attempt to workaround DS-2823
						dataIntegration.addPackageReuse(basePath, diff);						
					}
					reusesChanged = true;
				}
			}
			if(reusesChanged) {
				dataIntegration.save(basePath);
			}
		} catch (Exception e) {
			RulesIntegrationPlugin.getDefault().logError("Could not configure rule imports on project " + ofsProject.getName(), e);
		}
	}

	public static List<String> createReusesFromDependencies(IOfsProject ofsProject) {
		List<String> reuses = new ArrayList<String>();

		Set<IOfsModelContainer> dependencies = OfsCore.getDependencyManager().getDependencies(ofsProject);
		for(IOfsModelContainer dependency : dependencies) {
			String ruleModelName = getRuleModelName(dependency);
			if(ruleModelName!=null) {
				String remoteRootVRPath = "vrpath:/" + ruleModelName;
				try {
					reuses.addAll(IntegrationCore.getDataModelIntegration(ofsProject.getProject()).getPackageReuses(remoteRootVRPath));
				} catch (IntegrationException e) {
					RulesIntegrationPlugin.getDefault().logError("Cannot determine rule package imports of dependent projects", e);
				}
			}
		}
		return reuses;
	}

	private static String getRuleModelName(IOfsModelContainer dependency) {
		for(String subdir : OfsCore.getDependencyManager().getSubDirectories(dependency, new Path("rule/rules"))) {
			if (subdir.startsWith("rules_")) {
				return subdir;
	        }	        
		} 
	    return null;
	}

	private void markOutdatedRules(IProject project, IProgressMonitor monitor) {
		// Deactivated due to DS-1528
//		monitor.setTaskName("Marking outdated rules for project '" 
//				+ project.getName() + "'");
//		try {
//			IFile rulesFile = RulesIntegrationPlugin.getRulesFile(project);
//			if(rulesFile==null) return;
//			rulesFile.deleteMarkers(RulesIntegrationPlugin.RULE_MARKER_ID, true, IResource.DEPTH_ZERO);
//		} catch (CoreException e) {
//        	RulesIntegrationPlugin.getDefault().logWarning("Cannot delete problem markers on rule model file.", e);
//		}
//		String[] outdatedRules = RulesIntegrationPlugin.getOutdatedRules(project);
//
//		for(String rule : outdatedRules) {
//			addErrorMarkerForRule(project, rule, 
//					"Rule '" + rule + "' belongs to an entity that does not exist anymore!");
//		}
	}

//	private void addErrorMarkerForRule(IProject project, String vrpath, String message) {
//		try {
//			URI uri = URI.createURI(vrpath);
//			IFile ruleFile = VisualRulesIntegration.getDataModelIntegration().getFile(uri);
//			IMarker marker = ruleFile.createMarker(RulesIntegrationPlugin.RULE_MARKER_ID);
//			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
//			marker.setAttribute(IMarker.MESSAGE, message);
//		} catch (CoreException e) {
//			RulesIntegrationPlugin.getDefault().logWarning(
//					"Cannot create problem marker on rule " + vrpath, e);
//		} catch (IntegrationException e) {
//			RulesIntegrationPlugin.getDefault().logWarning(
//					"Cannot get physical file for rule " + vrpath, e);
//		}	
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(final int kind, Map args, IProgressMonitor monitor)
			throws CoreException {

		if(RulesIntegrationPlugin.projectHasConfigurationErrors(getProject())) return null;
		if(!RulesIntegrationPlugin.isRulesEnabled(getProject())) return null;
		if(!(new RulesInitializer()).checkConfiguration(getProject()).isOK()) return null;

		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		
		// launch the VisualRules validation builder from within here
		// this way, we have it under our control, if all prerequisites for the validation are fulfilled
		if(!dependenciesHaveErrors()) {
			// we launch it as a separate job (and schedule it only after a few seconds) as
			// otherwise the VR validation might come back with phantom errors. It seems that
			// if being called too quickly after changes in the rule model, the validation does
			// not get hold of the latest changes and thus returns errors.
			// Doing a pause of 5 seconds seems to workaround this problem, although not very elegantly.
			WorkspaceJob job = new WorkspaceJob("Rule Validation") {
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					getProject().build(kind, RulesInitializer.VR_VALIDATION_BUILDER_ID, Collections.<String, String> emptyMap(), monitor);
					return Status.OK_STATUS;
				}
			};
			job.setPriority(Job.DECORATE);
			job.schedule(5000L);
		}
		
		return null;
	}

	private boolean dependenciesHaveErrors() {
		for(IProject project : getProjectDependencies(getProject())) {
			if(RulesIntegrationPlugin.projectHasConfigurationErrors(project)) return true;
			if(!(new RulesInitializer()).checkConfiguration(project).isOK()) return true;
		}
		return false;
	}

	private static IProject[] getProjectDependencies(IProject project) {
		Set<IProject> projects = new HashSet<IProject>();
		Set<IOfsModelContainer> dependencies = OfsCore.getDependencyManager().getDependencies(OfsCore.getOfsProject(project));
		for(IOfsModelContainer dependency : dependencies) {
			if(dependency instanceof IOfsProject) {
				projects.add(((IOfsProject) dependency).getProject());
			}
		}
		return projects.toArray(new IProject[projects.size()]);
	}
	
	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(getProject());
		final DomainRepository repo = DomainRepository.getInstance(ofsProject);
		final Collection<MdfDomain> domainModels = repo.getDomains(IOfsProject.SCOPE_ALL-IOfsProject.SCOPE_DEPENDENCY);
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				syncDomainModels(getProject(), domainModels, true, monitor);
				syncProjectDependencies(ofsProject, monitor);
				markOutdatedRules(getProject(), monitor);
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, getProject(), IWorkspace.AVOID_UPDATE, monitor);
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new DomainDeltaVisitor(monitor));
	}
	
	static protected CoreException newCoreException(String msg, Throwable t) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID,
				0, msg, t));
	}
	
}
