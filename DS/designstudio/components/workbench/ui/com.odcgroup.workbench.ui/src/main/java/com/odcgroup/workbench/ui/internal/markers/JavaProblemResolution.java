package com.odcgroup.workbench.ui.internal.markers;

import net.sf.eclipsecs.core.config.ICheckConfiguration;
import net.sf.eclipsecs.core.nature.CheckstyleNature;
import net.sf.eclipsecs.core.projectconfig.FileMatchPattern;
import net.sf.eclipsecs.core.projectconfig.FileSet;
import net.sf.eclipsecs.core.projectconfig.IProjectConfiguration;
import net.sf.eclipsecs.core.projectconfig.ProjectConfigurationFactory;
import net.sf.eclipsecs.core.projectconfig.ProjectConfigurationWorkingCopy;
import net.sf.eclipsecs.core.util.CheckstylePluginException;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;


/**
 * This class is used to resolve problem that can occur on Java project.
 * This resolver can add Checksyle and Findbugs support to the project   
 * 
 * @author mmu
 * 
 */
public class JavaProblemResolution implements IMarkerResolution {

	/**
	 * 
	 */
	private static final String ODYSSEY_CODING_CONVENTIONS_CURRENT = "Design Studio Coding Conventions (Current)";
	private static final String FINDBUGS_NATURE_ID = "edu.umd.cs.findbugs.plugin.eclipse.findbugsNature";

	public String getDescription() {
		return "Run this quick fix to automatically enable Findbugs and CheckStyle on your JAVA project";
	}

	public String getLabel() {
		return "Enable Findbugs and Checkstyle";
	}

	public void run(IMarker marker) {
		IResource res = marker.getResource();
		
		if (res instanceof IProject)  {
			IProject project = (IProject) res;
			try {
				String[] natureArray = project.getDescription().getNatureIds();
				if (!ArrayUtils.contains(natureArray, CheckstyleNature.NATURE_ID)) {
					OfsCore.addNature(project, CheckstyleNature.NATURE_ID);
					setOdysseyCheckstyleConfiguration(project);
				}
				if (!ArrayUtils.contains(natureArray, FINDBUGS_NATURE_ID)) {
					OfsCore.addNature(project, FINDBUGS_NATURE_ID);
				}

				try {
					marker.delete();
				}catch(CoreException e) {
					OfsUICore.getDefault().logWarning("Cannot delete marker :" + marker.getType(), e);
				}
				
			} catch (CoreException e) {
				OfsUICore.getDefault().logWarning("Cannot add a new nature to the project :" + project.getName(), e);
			}
		}
	}

	/**
	 * @param project
	 */
	@SuppressWarnings("unchecked")
	private void setOdysseyCheckstyleConfiguration(IProject project) {
		try {
	        IProjectConfiguration config = ProjectConfigurationFactory.getConfiguration(project);
	        ProjectConfigurationWorkingCopy workingCopy = new ProjectConfigurationWorkingCopy(config);
	        ICheckConfiguration odysseyCheckConfig = workingCopy.getGlobalCheckConfigByName(ODYSSEY_CODING_CONVENTIONS_CURRENT);
	        if(odysseyCheckConfig!=null) {
	        	FileSet allFileSet = new FileSet("all", odysseyCheckConfig);
	        	FileMatchPattern fileMatchPattern = new FileMatchPattern(".");
	        	allFileSet.getFileMatchPatterns().add(fileMatchPattern);
	        	workingCopy.setUseSimpleConfig(true);
	        	workingCopy.getFileSets().clear();
	        	workingCopy.getFileSets().add(allFileSet);
	        	workingCopy.store();
	        }
		} catch (CheckstylePluginException e) {
			OfsUICore.getDefault().logWarning("Cannot set Design Studio Checkstyle configuration", e);
		}		
	}
}
