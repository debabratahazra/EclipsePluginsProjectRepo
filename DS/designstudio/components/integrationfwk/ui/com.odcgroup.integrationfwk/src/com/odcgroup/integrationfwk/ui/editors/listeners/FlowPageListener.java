package com.odcgroup.integrationfwk.ui.editors.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.FlowPage;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Class which helps to listen the ui components of Flow page and perform the
 * action whenever it required.
 * 
 * @author sbharathraja
 * 
 */
public class FlowPageListener extends AbstractEventsEditorListener {

	/** instance of {@link FlowPage} */
	private final FlowPage page;
	/** recent flow name */
	private String globalFlowName = "";

	/**
	 * constructor of {@link FlowPageListener}
	 * 
	 * @param page
	 */
	public FlowPageListener(FlowPage page) {
		this.page = page;
	}

	private void browseButtonSelected() {

		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.NULL);
		String currentFolderPath = getFlowsFolderPath();
		dialog.setFilterPath(currentFolderPath);
		String[] array = { "*.flow" };
		dialog.setFilterExtensions(array);
		dialog.open();
		String fileName = dialog.getFileName();
		// validate selection
		if (validateSelection(fileName, currentFolderPath)) {
			if (!"".equals(fileName)) {
				String fileWithoutExtention = Utils.removeFileExtention(
						fileName, "flow");
				page.getFlowNameTextBox().setText(fileWithoutExtention);
				globalFlowName = fileWithoutExtention;
				setEditorModified(getEventEditor());
			}
			// set buttons
			if (globalFlowName.equals("")) {
				page.getEditButton().setEnabled(false);
			} else {
				page.getEditButton().setEnabled(true);
			}
		} else {
			MessageBox message = new MessageBox(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.ERROR);
			message.setText(IntegrationToolingActivator
					.getEventString("_ERROR_FILE_SHALL_BE_CHOSEN_FROM_CURRENT_DIRECTORY_TITLE"));
			message.setMessage(IntegrationToolingActivator
					.getEventString("_ERROR_FILE_SHALL_BE_CHOSEN_FROM_CURRENT_DIRECTORY"));
		}

	}

	public void doubleClick(DoubleClickEvent doubleClickEvent) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method which helps to perform the action when edit button clicked.
	 */
	private void editButtonSelected() {
		// set the flow name to corresponding event
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.setFlowName(page.getFlowNameTextBox().getText());
		// update the event in factory
		updateEvent(getEventEditor(), consumerEvent);
		getEventEditor().updateSourcePage();
		String baseEventName = getBaseEventName();
		IFile iFile = getFileFromTextBox(baseEventName);
		Utils.openEditor(iFile);
	}

	/**
	 * Method which helps to perform the action when text modified on flow name
	 * text box.
	 * 
	 * @param event
	 *            - instance of {@link ModifyEvent}
	 */
	private void flowNameTextBoxTextModified(ModifyEvent event) {
		String text = (((Text) event.widget).getText());
		if (validate(text)) {
			page.getManagedForm().getForm()
					.setMessage(null, IMessageProvider.NONE);
			globalFlowName = text;
			if (globalFlowName.equals("")) {
				page.getEditButton().setEnabled(false);
			} else {
				page.getManagedForm().getForm()
						.setMessage(null, IMessageProvider.NONE);
				page.getEditButton().setEnabled(true);
			}
			setEditorModified(getEventEditor());
		} else {
			page.getFlowNameTextBox().getMessage();
			page.getEditButton().setEnabled(false);
		}
	}

	public void focusGained(FocusEvent focusEvent) {
		// TODO Auto-generated method stub
	}

	public void focusLost(FocusEvent focusEvent) {
		// TODO Auto-generated method stub
	}

	/**
	 * get the base event name. This could be the name of either an application,
	 * or a version or both with comma separated if the type is component
	 * service. Empty if TSA service type of event.
	 * 
	 * @return application name.
	 */
	private String getBaseEventName() {
		String applicationName = "";
		Event consumerEvent = getEvent(getEventEditor());
		SourceType sourceType = SourceType.valueOf(consumerEvent
				.getExitPointType().getSourceType());
		if (sourceType == null) {
			// shouldn't happen
			return applicationName;
		}
		switch (sourceType) {
		case COMPONENT_SERVICE:
			return consumerEvent.getExitPointType().getContractName() + ","
					+ consumerEvent.getExitPointType().getExitPoint();
		case TSA_SERVICE:
			// make the base event name / application name is empty if the
			// designed event type is TSA Service.
			return "";
		case VERSION:
			return consumerEvent.getExitPointType().getContractName();
		case APPLICATION:
			return consumerEvent.getExitPointType().getContractName();
		}
		// shouldn't reach.
		return applicationName;
	}

	/**
	 * get the event editor where this Exit point page has been placed.
	 * 
	 * @return instance of {@link EventsEditor}
	 */
	private EventsEditor getEventEditor() {
		return ((EventsEditor) page.getEditor());
	}

	/**
	 * Helps to get the type of the exit point.
	 * 
	 * @return VERSION - for version type of exit point, APPLICATION - for
	 *         application type of exit point, COMPONENT_SERVICE - for component
	 *         service type of exit point, TSA_SERVICE - for TSA service type of
	 *         exit point.
	 * @throws TWSConsumerPluginException
	 */
	private String getExitPointType() throws TWSConsumerPluginException {
		ExitPointType exitPointType = getEvent(getEventEditor())
				.getExitPointType();
		switch (SourceType.valueOf(exitPointType.getSourceType())) {
		case VERSION:
			return SourceType.VERSION.toString();
		case APPLICATION:
			return SourceType.APPLICATION.toString();
		case COMPONENT_SERVICE:
			return SourceType.COMPONENT_SERVICE.toString();
		case TSA_SERVICE:
			return SourceType.TSA_SERVICE.toString();
		}
		// should happen only when a flow which has been designed with
		// previous version of plug-in, but opened with recent version.
		throw new TWSConsumerPluginException("UnSupported ExitPoint Type.");
	}

	/**
	 * Method which helps to load the physical .flow file as {@link IFile}
	 * compatible instance, which could be opened in the editor.
	 */
	private IFile getFileFromTextBox(String applicationName) {
		String flowFilePath = getFlowsFolderPath() + File.separator
				+ page.getFlowNameTextBox().getText() + StringConstants.FLOW;
		File file = new File(flowFilePath);
		// Assuming the flowName field is populated with the valid file name.
		// shall add some validations
		if (!file.exists() || !file.isFile()) {
			// if file did not already exist then create a new file with empty
			// content
			// TODO change this later to reflect the new structure for the
			// flow.java
			try {
				String charSet = "UTF-8";
				String newContents = "<?xml version=\"1.0\" encoding=\""
						+ charSet + "\"?>\n";
				newContents += "<Flow>\n<InputFields></InputFields>\n<BaseEvent>"
						+ applicationName
						+ "</BaseEvent>\n"
						+ "<ExitPointType>"
						+ getExitPointType()
						+ "</ExitPointType>" + "\n" + "</Flow>";

				FileUtils.writeStringToFile(file, newContents);
			} catch (IOException e) {
				TWSConsumerLog.logError(e);
			} catch (TWSConsumerPluginException e) {
				TWSConsumerLog.logError(e);
			}
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath location = Path.fromOSString(file.getAbsolutePath());

		IFile iFile = workspace.getRoot().getFileForLocation(location);

		// important to refresh the workbench after creation of the file
		try {
			iFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}
		return iFile;
	}

	/**
	 * get the path of the flow's folder which is usually within the project
	 * folder.
	 * 
	 * @return string representation of flows folder path.
	 */
	private String getFlowsFolderPath() {
		String projectPath = getEventEditor().getCurrentProject()
				.getPathString();
		return projectPath + File.separator + StringConstants.FLOW_FOLDER_NAME;
	}

	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
	}

	public void modifyText(ModifyEvent modifyEvent) {
		Object source = modifyEvent.getSource();
		if (source.equals(page.getFlowNameTextBox())) {
			flowNameTextBoxTextModified(modifyEvent);
		}
	}

	public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method which helps to validate the given text with T24 terminology in
	 * mind.
	 * <p>
	 * The validation goes here is : the given flow name should have Minimum 3
	 * characters. No spaces.No 'special' characters which will cause a problem
	 * if embedded in the XML message emitted from T24 at run-time.
	 * 
	 * @param text
	 *            - most possibly a flow name.
	 * @return true if valid name, false otherwise.
	 */
	private boolean validate(String text) {
		boolean result = true;
		if (text == null) {
			return false;
		}
		if (text.length() < 3 && text.length() > 0) {
			page.getManagedForm()
					.getForm()
					.setMessage("FlowName cannot be less than 3 characters",
							IMessageProvider.ERROR);
			result = false;
		}
		if (text.contains(" ")) {
			result = false;
		}
		if (Utils.containsInvalidChar(text)) {
			page.getManagedForm()
					.getForm()
					.setMessage(
							"FlowName cannot have special character or start with a number",
							IMessageProvider.ERROR);
			result = false;
		}
		if (text.contains("_")) {
			page.getManagedForm()
					.getForm()
					.setMessage("FlowName cannot have special character or start with a number",
							IMessageProvider.ERROR);
			result = false;
		}
		return result;
	}

	/**
	 * Method which helps to validate the given file name (flow name) and folder
	 * path(flows folder path).
	 * 
	 * @param fileName
	 *            - name of the flow file.
	 * @param folderPath
	 *            - path to the flows folder.
	 * @return true if the flow file exists in given path with given name, false
	 *         otherwise.
	 */
	private boolean validateSelection(String fileName, String folderPath) {
		File f = new File(folderPath + File.separator + fileName);
		return f.exists();
	}

	public void widgetDefaultSelected(SelectionEvent selectionEvent) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent selectionEvent) {
		Object source = selectionEvent.getSource();
		if (source.equals(page.getEditButton())) {
			editButtonSelected();
		} else if (source.equals(page.getBrowseButton())) {
			browseButtonSelected();
		}
	}

}
