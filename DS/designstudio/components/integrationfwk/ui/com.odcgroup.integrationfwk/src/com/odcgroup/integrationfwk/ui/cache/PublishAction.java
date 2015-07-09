package com.odcgroup.integrationfwk.ui.cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.decorators.FilePropertyPage;
import com.odcgroup.integrationfwk.ui.dialog.SaveAndPublishDialog;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.utils.CacheManager;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;

public class PublishAction implements IWorkbenchWindowActionDelegate {

	private ISelection selection;
	private TWSConsumerProject project;
	private CacheManager cacheManager;
	private String projectPath;
	private StringBuffer publishLog;
	private CreateFlowServiceFactory serviceFactory;
	private ConfigEntity configEntity;

	private FilePropertyPage filePropertyPage;

	private IStatus callIntegrationService(ConfigEntity configEntity,
			List<EventFlow> eventFlow, IProgressMonitor monitor)
			throws TWSConsumerPluginException {

		monitor.beginTask("Publishing T24 Events...", 100 * eventFlow.size());

		serviceFactory = new CreateFlowServiceFactory(project);
		Iterator iter = eventFlow.iterator();
		String projectName = project.getProject().getName();

		serviceFactory.deleteAllFlows(projectName);

		clearExistSchemas();

		while (iter.hasNext()) {
			int elementCount = 1;
			if (!monitor.isCanceled()) {

				log("**********************************************");
				EventFlow event = (EventFlow) iter.next();
				if (validateEventFlow(event)) {

					log(event);
					monitor.worked(100 * elementCount);
					monitor.subTask("Publishing   "
							+ event.getEvent().getEventName());

					serviceFactory.createFlow(event, publishLog, filePropertyPage,
						        projectName);
					serviceFactory.createSchema(event);

					elementCount++;

				} else {
					errorDecorator(publishLog, filePropertyPage, event);
					log(event);
					log("Publish failed because field names in the flow contains invalid characters");
				}
			} else {
				log("**********************************************");
				log("Publish cancelled by user");
				log("**********************************************");
				writeToFile();
				monitor.done();
				return new Status(Status.CANCEL, "Publish",
						"T24 events publish cancelled by user");
			}
		}

		log("**********************************************");
		writeToFile();
		monitor.done();

		return new Status(Status.OK, "Publish",
				"T24 events published successfully");

	}

	private void clearExistSchemas() {
		if (project == null) {
			return;
		}
		File mainProjectFile = new File(project.getPathString()
				+ File.separator + StringConstants.SCHEMA_FOLDER_NAME);
		String[] subFolders = mainProjectFile.list();

		for (int folderCount = 0; folderCount < subFolders.length; folderCount++) {
			try {
				FileUtil.deleteDirectory(new File(project.getPathString()
						+ File.separator + StringConstants.SCHEMA_FOLDER_NAME
						+ File.separator + subFolders[folderCount]));
			} catch (IOException e) {
				log("Exception while trying to delete the existing schema");
			}
		}

	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	private void errorDecorator(StringBuffer publishLog,
			FilePropertyPage filePropertyPage, EventFlow event) {
		LogConsole.getSoaConsole().logMessage("Status : FAILURE");
		LogConsole
				.getSoaConsole()
				.logMessage(
						"Reason : "
								+ "Field Names in the flow contains invalid characters");
		publishLog.append("Status : FAILURE \n");
		publishLog.append("Reason : "
				+ "Field Names in the flow contains invalid characters" + "\n");
		filePropertyPage.performPublish(true, event.getEvent().getEventName());

	}

	private EventFlow getEventFlow(String projFullPath, String eventFullPath,
			String eventName) {
		Event event;
		Flow flow;
		String ProjectName = project.getProject().getName();
		EventFlow eventFlow = new EventFlow();
		event = cacheManager.getEvent(eventFullPath);
		event.setEventName(eventName);
		String flowFullPath = projFullPath + File.separatorChar
				+ StringConstants.FLOWS + File.separatorChar
				+ event.getFlowName() + StringConstants.FLOW;
		flow = cacheManager.getFlow(flowFullPath);
		event.setFlowName(withProjectName(ProjectName, event.getFlowName()));
		String exitPoint = event.getExitPointType().getExitPoint();
		event.getExitPointType().setExitPoint(
				withProjectName(exitPoint, ProjectName));
		eventFlow.setEvent(event);
		eventFlow.setFlow(flow);
		return eventFlow;
	}

	private List<EventFlow> getEventFlows(String projFullPath) {
		String eventFullPath = projFullPath + File.separatorChar
				+ StringConstants.EVENTS;
		File file = new File(eventFullPath);
		List<EventFlow> eventFlows = new ArrayList<EventFlow>();
		if (file.isDirectory()) {
			File[] events = file.listFiles();
			for (int i = 0; i < events.length; i++) {
				if (events[i].isFile()
						&& getFileExtension(events[i]).equalsIgnoreCase(
								StringConstants.EVENT)) {
					eventFlows.add(getEventFlow(projFullPath, eventFullPath
							+ File.separatorChar + events[i].getName(),
							events[i].getName()));
				}
			}
		}
		return eventFlows;
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		int lastIntex = fileName.lastIndexOf(".");
		fileName = fileName.substring(lastIntex);
		return fileName;
	}

	/**
	 * Method which helps to handle the partial(unsaved) events/flows.
	 */
	private void handlePartialEvents() {
		List<IEditorPart> dirtyEditors = Utils
				.getDirtyIntegrationProjectEditors(project);
		// we are not interesting if all events and flows are saved properly.
		if (dirtyEditors.isEmpty()) {
			return;
		}
		// handling if the project preference is requested to always save the
		// resource before publishing it.
		if (project != null
				&& project.getResourcesToBeAlwaysSaveBeforePublishing()) {
			for (IEditorPart editor : dirtyEditors) {
				editor.doSave(null);
			}
			return;
		}
		SaveAndPublishDialog dialog = new SaveAndPublishDialog(Display
				.getCurrent().getActiveShell(), dirtyEditors, project);

		dialog.open();
	}

	public void init(IWorkbenchWindow arg0) {
		cacheManager = new CacheManager();
	}

	private void log(EventFlow eventFlow) {
		if (eventFlow != null) {
			Event event = eventFlow.getEvent();
			if (event != null) {
				if (event.getEventName() != null) {
					log("Event Name: " + event.getEventName());
				} else {
					log("Event Name: ''");
				}
				ExitPointType exitPointType = event.getExitPointType();
				if (exitPointType != null) {
					if (exitPointType.getContractName() != null) {
						log("Contract Name: " + exitPointType.getContractName());
					} else {
						log("Contract Name: ''");
					}
				} else {
					log("Contract Name: ''");
				}
			}
			Flow flow = eventFlow.getFlow();
			if (flow != null) {
				log("Flow Name: " + event.getFlowName().toString());
			} else {
				log("Flow Name: ''");
			}
		}
	}

	private void log(String logInfo) {
		LogConsole.getSoaConsole().logMessage(logInfo);
		publishLog.append(logInfo + "\n");
	}

	/**
	 * Helps to refresh the given project
	 * 
	 * @param project
	 */
	private void refreshProject(IProject project) {

		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			TWSConsumerLog.logError(
					"Error occurs while trying to refresh the project", e);
		}
	}

	public void run(IAction action) {
		cacheManager = new CacheManager();
		publishLog = new StringBuffer();
		final Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj instanceof IProject) {
			filePropertyPage = new FilePropertyPage((IProject) obj);// .performPublish(true);
			IProject events = (IProject) obj;
			project = new TWSConsumerProject(events.getProject(), "dummy");
			handlePartialEvents();

			Job publishJob = new Job("Publishing T24 Events") {
				@Override
				public IStatus run(IProgressMonitor monitor) {
					try {

						projectPath = project.getPathString();
						LogConsole.getSoaConsole().logMessage(
								"Publish: " + projectPath);
						publishLog.append("Publish: " + projectPath + "\n");

						IStatus status = callIntegrationService(configEntity,
								getEventFlows(project.getPathString()), monitor);
						return status;

					} catch (Exception e) {
						LogConsole.getSoaConsole().logMessage(
								"Exception while publishing events for "
										+ projectPath + e.getMessage());
						monitor.setCanceled(true);
						writeToFile();
						return new Status(Status.CANCEL, "Publish",
								"Event publish encountered an exception");
					} finally {
						monitor.done();
						// refresh the project
						refreshProject((IProject) obj);

					}

				}

			};

			publishJob.setUser(true);
			publishJob.schedule();

		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		action.setEnabled(!selection.isEmpty());
	}

	public boolean validateEventFlow(EventFlow event) {

		boolean isValid = true;
		for (int i = 0; i < event.getFlow().getFields().getInputFields().size(); i++) {
			if (Utils.containsInvalidChar(event.getFlow().getFields()
					.getInputFields().get(i).getDisplayName())) {
				isValid = false;
			}
		}

		return isValid;
	}

	private String withProjectName(String str, String str1) {
		return str + "-" + str1;
	}

	private void writeToFile() {
		File file = new File(projectPath + File.separatorChar
				+ StringConstants.LOG_FOLDER_NAME + File.separatorChar
				+ "publish-" + Utils.getTimeStamp(true) + ".log");
		try {

			FileUtils.writeStringToFile(file, publishLog.toString());
		} catch (IOException e) {
			TWSConsumerLog.logError(
					"Error occurs while writing the publish log.", e);
		}
	}
}
