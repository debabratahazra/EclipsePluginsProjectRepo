package com.odcgroup.workbench.core.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.OfsCore;

/**
 * This is a helper class that defined an internal data structure that groups 
 * the resources to be validated by {project, modelType, modelURI}.<p>
 * This structure keeps data in alphabetical order.<p>
 *
 * @author atripod
 */
public class ModelsPartition {

	private Map<String /*Project*/, 
				Map<String /*model/extension*/, Set<String /*model uri*/>>> partition = 
					new TreeMap<String, Map<String, Set<String>>>();
	
	private Set<IProject> projects = new HashSet<IProject>();

	private int nbProjects;
	private int nbModelTypes;
	private int nbModels;
	
	private IProject getProject(String name) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot(); 
		return root.getProject(name);
	}

	private boolean isOfsProject(IProject project) {
		return OfsCore.isOfsProject(project);
	}
	
	private void initialize(Collection<IResource> resources) {
		for (IResource resource : resources) {
			// normalize the URI of the resource
			URI uri = getPlatformResourceURI(resource);
			// extract the project name
			String projectName = uri.segment(1);
			IProject project = getProject(projectName);
			if (isOfsProject(project)) {
				projects.add(project);
				// put the uri in the right partition
				Map<String, Set<String>> models = partition.get(projectName);
				if (models == null) {
					models = new TreeMap<String, Set<String>>();
					partition.put(projectName, models);
					nbProjects++;
				}
				// extract the type of the model
				String modelType = uri.fileExtension();
				Set<String> uriSet = models.get(modelType);
				if (uriSet == null) {
					uriSet = new TreeSet<String>();
					models.put(modelType, uriSet);
					nbModelTypes++;
				}
				uriSet.add(uri.toString());
				nbModels++;
			}
		}
	}
	
	protected URI getPlatformResourceURI(IResource resource) {
		return URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
	}
	
	public final int getProjectCount() {
		return partition.keySet().size(); 
	}
	
	public final int getModelTypeCount() {
		return nbModelTypes;
	}

	public final int getAllModelCount() {
		return nbModels;
	}
	
	public final Collection<String> getProjectNames() {
		return partition.keySet();
	}
	
	public final Map<String, Set<String>> getModels(String projectName) {
		return partition.get(projectName);
	}
	
	public final Collection<IProject> getProjects() {
		return projects;
	}
	
	public boolean isEmpty() {
		return partition.isEmpty();
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for (String project : partition.keySet()) {
			buf.append("Partition for project [");
			buf.append(project);
			buf.append("]\n");
			for (String modelType : partition.get(project).keySet()) {
				buf.append("\t");
				buf.append(modelType);
				buf.append("\n");
				for (String modelURI : partition.get(project).get(modelType)) {
					buf.append("\t\t");
					buf.append(modelURI);
					buf.append("\n");
				}
			}
		}
		return buf.toString();
	}
	
	public ModelsPartition(Collection<IResource> resources) {
		initialize(resources);
	}
}

