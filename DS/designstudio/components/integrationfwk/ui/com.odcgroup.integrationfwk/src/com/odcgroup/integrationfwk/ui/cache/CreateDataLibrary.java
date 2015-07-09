package com.odcgroup.integrationfwk.ui.cache;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.services.connectivity.FlowServices;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.CreateDataLibraryService;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

public class CreateDataLibrary implements IWorkbenchWindowActionDelegate {
	private ISelection selection;

	/**
	 * Responsible for create the data library via JAgent using the connection
	 * details attached with given project.
	 * 
	 * @param project
	 */
	private void createViaJAgent(TWSConsumerProject project) {
		ConfigEntity configEntity = populateConfigEntity(project.getHost(),
				project.getPort(), project.getOfsSource());
		CreateDataLibraryService CreateDataLibraryService = new CreateDataLibraryService(
				configEntity, project.getProject().getName());
		CreateDataLibraryService.getResponse(project.getProject().getName());
	}

	/**
	 * Helps to create the data library via web-service mode using the
	 * connection details attached with given project.
	 * 
	 * @param project
	 */
	private void createViaWebService(TWSConsumerProject project) {
		FlowServices flowService = new FlowServices(getFlowServiceUrl(project),
				project.getPassword(), project.getUserName());
		flowService.createDataLibrary();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * Helps to get the flow service url using the given project.
	 * 
	 * @param project
	 * @return component framework flow service url.
	 */
	private String getFlowServiceUrl(TWSConsumerProject project) {
		String componetFrameworkServiceUrl = project.getComponentServiceURL();
		return TWSConsumerProjectUtil
				.getFlowServiceURL(componetFrameworkServiceUrl);
	}

	public void init(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub

	}

	private ConfigEntity populateConfigEntity(String hostName, int portNumber,
			String ofsSource) {
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setHostName(hostName);
		configEntity.setPortNumber(portNumber);
		configEntity.setOfsSource(ofsSource);
		return configEntity;
	}

	public void run(IAction arg0) {
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (!(obj instanceof IProject)) {
			return;
		}
		IProject events = (IProject) obj;
		TWSConsumerProject project = new TWSConsumerProject(
				events.getProject(), "dummy");
		if (project.isAgentConnection()) {
			createViaJAgent(project);
		} else {
			createViaWebService(project);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		action.setEnabled(!selection.isEmpty());
	}

}
