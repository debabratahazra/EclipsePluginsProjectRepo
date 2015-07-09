package com.odcgroup.t24.deployment.tests;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

import com.google.common.base.Preconditions;
import com.odcgroup.t24.common.importer.tests.T24ImporterTest;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.util.T24ExternalServerManager;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;


public class T24DeploymentTest extends T24ImporterTest {
	
	private Properties serverPropertiesTAFJ =null;
	private Properties serverPropertiesTAFC =null;
	private IProject serverProjectTAFJ = null;
	private IProject serverProjectTAFC = null;
	private IProject genProject = null;
	private ExternalT24Server externalServerTAFC = null;
	private ExternalT24Server externalServerTAFJ = null;

	protected void createAndSetupServerProject() throws CoreException, IOException, T24ServerException {
		// TAFJ server
		serverProjectTAFJ = createServerProject("deployment-testTAFJ-server");
		createServerConfig(serverProjectTAFJ, true);
		// TAFC server
		serverProjectTAFC = createServerProject("deployment-testTAFC-server");
		createServerConfig(serverProjectTAFC, false);
		serverProjectTAFJ.open(null);
		serverProjectTAFC.open(null);
		serverPropertiesTAFC = getTAFCExternalServer().readPropertiesFile();
		serverPropertiesTAFJ = getTAFJExternalServer().readPropertiesFile();
	}
	
	protected void createCodeGenModelProject(IProject project) throws CoreException{
		 new CodeGenInitializer().updateConfiguration(project, null);
		 genProject= ResourcesPlugin.getWorkspace().getRoot().getProject("default-models-gen");
	}

	protected ExternalT24Server getTAFCExternalServer() {
		if (externalServerTAFC != null) {
			return externalServerTAFC;
		}
		for (IExternalServer server : T24ExternalServerManager.getInstance().getExternalServers()) {
			if (server.getId().equals("com.odcgroup.t24.server.deployment-testTAFC-server")) {
				externalServerTAFC = (ExternalT24Server) server;
			}
		}
		// DS-7723 (NullPointer Exception check)
		if(externalServerTAFC == null){
			// Re-check for null value
			throw new NullPointerException("External TAFC Server not initialized properly.");
		}
		return externalServerTAFC;
	}

	protected ExternalT24Server getTAFJExternalServer() {
		if (externalServerTAFJ != null) {
			return externalServerTAFJ;
		}
		for (IExternalServer server : T24ExternalServerManager.getInstance().getExternalServers()) {
			if (server.getId().equals("com.odcgroup.t24.server.deployment-testTAFJ-server")) {
				externalServerTAFJ = (ExternalT24Server) server;

			}
		}
		return externalServerTAFJ;
	}
	
	public Properties getTAFJServerProperties() {
		return serverPropertiesTAFJ;
	}

	public Properties getTAFCServerProperties() {
		return serverPropertiesTAFC;
	}	
	
	public IProject getServerProjectTAFC() {
		return serverProjectTAFC;
	}

	public IProject getServerProjectTAFJ() {
		return serverProjectTAFJ;
	}
	
	protected MessageConsole getT24DeploymentCosole() {
		IConsole[] consoleArray = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		IConsole t24DeployConsole =null;
		for(IConsole console:consoleArray){
			if(console.getName().equals("T24 Deployment Console")){
				t24DeployConsole = console;
			}
		}
		return (MessageConsole)t24DeployConsole;
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	protected String getDeploymentLogMessage(List<String> errors){
		StringBuffer deploymentError = new StringBuffer();
		deploymentError.append("\n");
		for(String error : errors){
			deploymentError.append(error+"\n");
		}
		return deploymentError.toString();
	}
	
	protected void setAutobuild(boolean flag) throws CoreException{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(flag);
		workspace.setDescription(description);
	}
	
	/**
	 * Converts proprietary DS resource:// model URI to standard EMF platform:// URIs.
	 * 
	 * @param project the IProject, which will necessarily have to have an IOfsProject - needed to transform resource:// model URIs (which do not contain the project name anymore) 
	 * @param uri the DS resource:// model URI
	 * @return the EMF platform:// URI
	 */
	protected URI convertToStandardURI(IOfsProject modelsOfsProject, URI uri) {
		Preconditions.checkNotNull(uri, "uri == null");
		// Here, we're still expecting it to be DS resource:// model URI, else that's very strange.. 
		if (!ModelURIConverter.isModelUri(uri)) {
			return uri;
		}
		return new ModelURIConverter(modelsOfsProject).toPlatformURI(uri);
	}

}
