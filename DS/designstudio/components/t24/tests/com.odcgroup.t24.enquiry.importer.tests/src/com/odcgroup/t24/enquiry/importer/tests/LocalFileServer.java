package com.odcgroup.t24.enquiry.importer.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class LocalFileServer extends DSServer implements IExternalServer {

	private IProject serverProject;
	
	private File dataFolder;
	
	public LocalFileServer(String id, String name, IProject serverProject, File dataFolder) {
		super(id, name);
		this.installDir = serverProject.getLocationURI().getPath();
		this.serverProject = serverProject;
		this.currentState = STATE_STARTED_DEBUG;
		this.dataFolder = dataFolder;
	}

	@Override
	public String getListeningPort() {
		return null;
	}

	@Override
	public String getLogDirectory() {
		return null;
	}

	@Override
	public String getInstallationDirectory() {
		return null;
	}

	@Override
	public IProject getServerProject() {
		return serverProject;
	}

	@Override
	public boolean canDeploy(IProject project) {
		return false;
	}

	@Override
	public List<IDSProject> getDeployedProjects() {
		return null;
	}

	@Override
	public Properties readPropertiesFile() throws IOException {
		return new Properties();
	}

	@Override
	public final <E extends IExternalObject> IExternalLoader getExternalLoader(Class<E> type) throws T24ServerException {
		IExternalLoader loader = null; 
		try {
			if (EnquiryDetail.class == type) {
				loader = new LocalEnquiryLoader(readPropertiesFile(), dataFolder);
			}
		} catch (IOException e) {
			throw new T24ServerException("Unable to read the server properties", e);
		}
		return loader;
	}

}
