package com.odcgroup.t24.server.external.model;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * Interface of the external server managed by the ServerView
 */
public interface IExternalServer extends IDSServer {
	
	/**
	 * @return
	 * @throws T24ServerException
	 */
	List<IDSProject> getDeployedProjects();
	
	/**
	 * @return
	 * @throws IOException
	 */
	Properties readPropertiesFile() throws IOException;
	
	/**
	 * @param type
	 * @return the loader for the given type.
	 * @throws T24ServerException 
	 */
	<T extends IExternalObject> IExternalLoader getExternalLoader(Class<T> type) throws T24ServerException;

}
