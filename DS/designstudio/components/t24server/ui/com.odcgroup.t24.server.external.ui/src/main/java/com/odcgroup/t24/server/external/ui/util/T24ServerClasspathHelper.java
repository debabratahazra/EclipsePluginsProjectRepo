package com.odcgroup.t24.server.external.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaModelException;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.util.DSProjectUtil;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployConsole;
import com.odcgroup.t24.server.external.ui.builder.T24PrepareDeploymentFacade;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;

public class T24ServerClasspathHelper {

	private static T24ServerClasspathHelper INSTANCE = new T24ServerClasspathHelper();
	
	protected T24ServerClasspathHelper() {
	}
	
	public static T24ServerClasspathHelper getInstance() {
		return INSTANCE;
	}

	/**
	 * @param t24Server
	 * @param addedDsProjects
	 * @param removedDsProjects
	 * @throws JavaModelException
	 */
	public void updateServerBuildPath(IExternalServer t24Server,
			List<IDSProject> addedDsProjects, List<IDSProject> removedDsProjects) throws JavaModelException {
		
		// Added projects
		List<IDSProject> dsProjects = new ArrayList<IDSProject>(t24Server.getDsProjects());
		for (IDSProject dsProject: addedDsProjects) {
			dsProjects.add(dsProject);
		}
		
		// Removed projects
		for (IDSProject project: removedDsProjects) {
			for (IDSProject dsProject: dsProjects) {
				if (dsProject.getName().equals(project.getName())) {
					dsProjects.remove(dsProject);
					break;
				}
			}
			continue;
		}
		
		t24Server.updateProjectList(dsProjects.toArray(new IDSProject[dsProjects.size()]));
		updateDeployedProjects(t24Server,dsProjects.toArray(new IDSProject[dsProjects.size()]));
		T24PrepareDeploymentFacade.makeProjectsDeployable(DSProjectUtil.convertToIProjects(dsProjects));
	}
	
	 /**
     * @param projects
     */
    public void updateDeployedProjects(IExternalServer t24Server,IDSProject[] projects) {
    	T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
    	if (t24Server instanceof ExternalT24Server) {
    		ExternalT24Server externalT24Server = (ExternalT24Server) t24Server;
			Properties prop = new Properties();
			File localFile = externalT24Server.getServerPropertiesFile();
			FileInputStream finStream = null;
			try {
				if (localFile.exists()) {
					finStream = new FileInputStream(localFile);
					prop.load(finStream);
				}
			} catch (FileNotFoundException e) {
				deployConsole.printError(localFile.getName() + " file Not Found ");
			} catch (IOException e) {
				deployConsole.printError(localFile.getName() + " file read error ");
			} finally {
				 IOUtils.closeQuietly(finStream);
			}
			if (localFile != null && localFile.exists()) {
				StringBuffer sb = new StringBuffer();
				IDSProject project = null;
				for (int ii = 0; ii < projects.length; ii++) {
					project = (IDSProject) projects[ii];
					sb.append(project.getName());
					if (ii != projects.length - 1) {
						sb.append(",");
					}
				}
				ServerPropertiesHelper.setDeployedProjects(prop, sb.toString());
				FileOutputStream fosStream = null;
				try {
					fosStream = new FileOutputStream(localFile);
					prop.store(fosStream,null);
				} catch (FileNotFoundException e) {
					deployConsole.printError(localFile.getName() + " file Not Found ");
				} catch (IOException e) {
					deployConsole.printError(localFile.getName() + " file write error ");
				}
				finally {
					IOUtils.closeQuietly(fosStream);
				}
			}
		}
    }
    
}
