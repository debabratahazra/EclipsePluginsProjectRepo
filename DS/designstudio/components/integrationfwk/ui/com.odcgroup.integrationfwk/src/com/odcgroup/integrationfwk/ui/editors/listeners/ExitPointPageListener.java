package com.odcgroup.integrationfwk.ui.editors.listeners;

import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.integrationfwk.ui.controller.ConnectionController;
import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.ExitPointPage;
import com.odcgroup.integrationfwk.ui.model.Application;
import com.odcgroup.integrationfwk.ui.model.ComponentService;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.TSAService;
import com.odcgroup.integrationfwk.ui.model.Version;

/**
 * Class which helps to listen and perform the respective events on the ui
 * components of {@link ExitPointPage}
 * 
 * @author sbharathraja
 * 
 */
public class ExitPointPageListener extends AbstractEventsEditorListener {

	/** instance of {@link ExitPointPage} */
	private final ExitPointPage page;
	/** instance of {@link ConnectionController} */
	private final ConnectionController connectionCntrl;
	/** recently selected version */
	private String globalVersion = "";
	/** recently selected version exit point */
	private String globalVersionExitPoint = "";
	/** recently selected application */
	private String globalApplication = "";
	/** recently selected application exit point */
	private String globalApplicationExitPoint = "";
	/** recently selected service */
	private String globalService = "";
	/** recently selected operation */
	private String globalOperation = "";
	/** recently selected TSA Service */
	private String globalTSAService = "";
	/** flag to decide whether the version list already generated or not */
	private boolean isFirstVersionList;
	/**
	 * flag to decide whether the version exit point list already generated or
	 * not
	 */
	private boolean isFirstVersionExitPoint;
	/**
	 * flag to decide whether the application name list already generated or not
	 */
	private boolean isFirstApplicationList;
	/**
	 * flag to decide whether the application exit point list already generated
	 * or not
	 */
	private boolean isFirstApplicationExitPoint;
	/**
	 * flag to decide whether the service combo box list is already generated or
	 * not
	 */
	private boolean isFirstServiceList;

	/**
	 * Constructor of {@link ExitPointPageListener}
	 * 
	 * @param page
	 *            - instance of {@link ExitPointPage}
	 */
	public ExitPointPageListener(ExitPointPage page) {
		this.page = page;
		connectionCntrl = getConnectionController(getEventEditor());
	}

	/**
	 * Method which helps to perform the action when the application exit point
	 * combo box gained focus.
	 */
	private void applicationExitPointComboGainedFocus() {
		if (!isFirstApplicationExitPoint) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							String aExitPoint = page
									.getApplicationExitPointCombo().getText();
							List<String> exitPoints = getExitPoints(
									connectionCntrl, getEventEditor());
							String[] exitPointsArray = null;
							exitPointsArray = exitPoints.toArray(new String[0]);
							page.getApplicationExitPointCombo().setItems(
									exitPointsArray);
							isFirstApplicationExitPoint = true;
							page.getApplicationExitPointCombo().setText(
									aExitPoint);
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when key released on application
	 * exit point combo box.
	 */
	private void applicationExitPointComboKeyReleased() {
		if (!isFirstApplicationExitPoint) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							String aExitPoint = page
									.getApplicationExitPointCombo().getText();
							List<String> exitPoints = getExitPoints(
									connectionCntrl, getEventEditor());
							String[] exitPointsArray = null;
							exitPointsArray = exitPoints.toArray(new String[0]);
							page.getApplicationExitPointCombo().setItems(
									exitPointsArray);
							isFirstApplicationExitPoint = true;
							page.getApplicationExitPointCombo().setText(
									aExitPoint);
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when text modified in
	 * application exit point combo.
	 * 
	 * @param event
	 */
	private void applicationExitPointTextModified(ModifyEvent event) {
		String value = ((Combo) event.widget).getText();
		if (value != null && !value.equals("") && isFirstApplicationExitPoint) {
			if (!value.equals(globalApplicationExitPoint)
					&& getExitPoints(connectionCntrl, getEventEditor())
							.contains(value)) {
				Event consumerEvent = getEvent(getEventEditor());
				consumerEvent.getExitPointType().setExitPoint(value);
				updateEvent(getEventEditor(), consumerEvent);
				getEventEditor().getOverridesPage().refresh();
				if (!globalApplicationExitPoint.equalsIgnoreCase("")) {
					setEditorModified(getEventEditor());
				}
			}
			globalApplicationExitPoint = value;
		}
	}

	/**
	 * Method which helps to perform the action when the application name combo
	 * box gained focus.
	 */
	private void applicationNameComboGainedFocus() {
		if (!isFirstApplicationList) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							page.getApplicationNameCombo().getText();
							List<String> list = connectionCntrl
									.getApplicationList(getEventEditor()
											.getCurrentProject());
							String[] items = null;
							if (list != null) {
								items = list.toArray(new String[0]);
							}
							page.getApplicationNameCombo().setItems(items);
							String contractName = getEvent(getEventEditor())
									.getExitPointType().getContractName();
							if (contractName != null) {
								page.getApplicationNameCombo().setText(
										contractName);
							}
							isFirstApplicationList = true;
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when key pressed on application
	 * name combo box.
	 */
	private void applicationNameComboKeyPressed() {
		if (!isFirstApplicationList) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							page.getApplicationNameCombo().getText();
							List<String> list = connectionCntrl
									.getApplicationList(getEventEditor()
											.getCurrentProject());
							String[] items = null;
							if (list != null) {
								items = list.toArray(new String[0]);
							}
							page.getApplicationNameCombo().setItems(items);
							// do the same for the key press
							String contractName = getEvent(getEventEditor())
									.getExitPointType().getContractName();
							if (contractName != null) {
								page.getApplicationNameCombo().setText(
										contractName);
							}
							isFirstApplicationList = true;
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when text of the application
	 * name combo box modified.
	 * 
	 * @param event
	 *            - instance of {@link ModifyEvent}
	 */
	private void applicationNameComboTextModified(ModifyEvent event) {
		String application = ((Combo) event.widget).getText();
		if (application != null && isValidApplicationName(application)
				&& isFirstApplicationList) {
			Event consumerEvent = getEvent(getEventEditor());
			consumerEvent.getExitPointType().setContractName(application);
			if (!application.equals(globalApplication)) {
				updateEvent(getEventEditor(), consumerEvent);
				setEditorModified(getEventEditor());
			}
			globalApplication = application;
		}
	}

	/**
	 * Method which helps to perform the action when the application radio
	 * button of {@link ExitPointPage} has been selected.
	 */
	private void applicationRadioButtonSelected() {
		ExitPointType exitPointType = new Application();
		Event event = getEvent(getEventEditor());
		event.setExitPointType(exitPointType);
		updateEvent(getEventEditor(), event);

		page.getApplicationSection().setEnabled(true);

		page.getTSAServiceSection().setEnabled(false);
		page.getTSAServiceNameCombo().setText("");
		page.getComponentServiceSection().setEnabled(false);
		page.getVersionSection().setEnabled(false);
		page.getVersionNameCombo().setText("");
		page.getVersionExitPointCombo().setText("");
		page.getComponentServiceCombo().setText("");
		page.getComponentOperationCombo().setText("");
		globalService = "";
		globalOperation = "";
		setEditorModified(getEventEditor());
	}

	/**
	 * Method which helps to perform the event when the operation combo of
	 * Component Service Section within the {@link ExitPointPage} got focus.
	 */
	private void componentOperationComboGainedFocus() {
		if (globalOperation == null || globalOperation.equalsIgnoreCase("")) {
			globalOperation = getEvent(getEventEditor()).getExitPointType()
					.getExitPoint();
		}
		String selectedService = page.getComponentServiceCombo().getText();
		page.getComponentOperationCombo().setItems(
				connectionCntrl.getOperationList(getEventEditor()
						.getCurrentProject(), selectedService));
		if (globalOperation != null) {
			page.getComponentOperationCombo().setText(globalOperation);
		}
	}

	/**
	 * Method which helps to perform the action when the operation combo box of
	 * Component Service Section within the {@link ExitPointPage} has been
	 * selected.
	 */
	private void componentOperationComboSelected() {
		String selectedOperation = page.getComponentOperationCombo().getText();
		if (selectedOperation.equalsIgnoreCase(globalOperation)) {
			return;
		}
		globalOperation = selectedOperation;
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.getExitPointType().setExitPoint(selectedOperation);
		updateEvent(getEventEditor(), consumerEvent);
		setEditorModified(getEventEditor());
	}

	/**
	 * Method which helps to perform when the component service combo box gained
	 * focus.
	 */
	private void componentServiceComboGainedFocus() {
		if (!isFirstServiceList) {
			page.getComponentServiceCombo().setItems(
					connectionCntrl.getServiceList(getEventEditor()
							.getCurrentProject()));
			isFirstServiceList = true;
		}
		if (globalService == null || globalService.equalsIgnoreCase("")) {
			globalService = getEvent(getEventEditor()).getExitPointType()
					.getContractName();
		}
		if (globalService != null) {
			page.getComponentServiceCombo().setText(globalService);
		}
	}

	/**
	 * Method which helps to perform the action when the service combo box of
	 * Component Service Section within the {@link ExitPointPage} has been
	 * selected.
	 */
	private void componentServiceComboSelected() {
		String selectedService = page.getComponentServiceCombo().getText();
		if (selectedService.equalsIgnoreCase(globalService)) {
			return;
		}
		globalService = selectedService;
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.getExitPointType().setContractName(selectedService);
		consumerEvent.getExitPointType().setExitPoint("");
		updateEvent(getEventEditor(), consumerEvent);
		setEditorModified(getEventEditor());
		page.getComponentOperationCombo().setText("");
		globalOperation = "";
	}

	/**
	 * Method which helps to perform the action when the component service radio
	 * button of {@link ExitPointPage} has been selected.
	 */
	private void componentServiceRadioButtonSelected() {
		ExitPointType exitPointType = new ComponentService();
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.setExitPointType(exitPointType);
		updateEvent(getEventEditor(), consumerEvent);
		setEditorModified(getEventEditor());
		page.getComponentServiceCombo().setItems(
				connectionCntrl.getServiceList(getEventEditor()
						.getCurrentProject()));

		page.getComponentServiceSection().setEnabled(true);

		page.getTSAServiceSection().setEnabled(false);
		page.getTSAServiceNameCombo().setText("");
		page.getVersionSection().setEnabled(false);
		page.getVersionNameCombo().setText("");
		page.getVersionExitPointCombo().setText("");
		page.getApplicationSection().setEnabled(false);
		page.getApplicationNameCombo().setText("");
		page.getApplicationExitPointCombo().setText("");
		getEventEditor().getOverridesPage().refresh();
	}

	public void doubleClick(DoubleClickEvent doubleClickEvent) {
		// TODO Auto-generated method stub
	}

	public void focusGained(FocusEvent focusEvent) {
		Object source = focusEvent.getSource();
		if (source.equals(page.getComponentOperationCombo())) {
			componentOperationComboGainedFocus();
		} else if (source.equals(page.getVersionNameCombo())) {
			versionNameComboGainedFocus();
		} else if (source.equals(page.getVersionExitPointCombo())) {
			versionComboExitPointGainedFocus();
		} else if (source.equals(page.getApplicationNameCombo())) {
			applicationNameComboGainedFocus();
		} else if (source.equals(page.getApplicationExitPointCombo())) {
			applicationExitPointComboGainedFocus();
		} else if (source.equals(page.getComponentServiceCombo())) {
			componentServiceComboGainedFocus();
		}
	}

	public void focusLost(FocusEvent focusEvent) {
		// TODO Auto-generated method stub

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
	 * check whether the exit point page has any valid changes or not.
	 * 
	 * @return true if any valid changes occur, false otherwise
	 */
	public boolean hasValidChanges() {
		if (page.getVersionNameCombo() == null
				|| page.getVersionExitPointCombo() == null
				|| page.getApplicationExitPointCombo() == null
				|| page.getApplicationNameCombo() == null
				|| page.getComponentServiceCombo() == null
				|| page.getComponentOperationCombo() == null
				|| page.getTSAServiceNameCombo() == null) {
			// shouldn't happen
			return false;
		}
		if (page.getVersionRadioButton().getSelection()) {
			return getExitPoints(connectionCntrl, getEventEditor()).contains(
					page.getVersionExitPointCombo().getText())
					&& isValidVersionName(page.getVersionNameCombo().getText());
		} else if (page.getApplicationRadioButton().getSelection()) {
			return getExitPoints(connectionCntrl, getEventEditor()).contains(
					page.getApplicationExitPointCombo().getText())
					&& isValidApplicationName(page.getApplicationNameCombo()
							.getText());
		} else if (page.getComponentServiceRadioButton().getSelection()) {
			return isValidComponentServiceDetails(page
					.getComponentServiceCombo().getText())
					&& isValidComponentOperationDetails(page
							.getComponentOperationCombo().getText());
		} else if (page.getTSAServiceRadioButton().getSelection()) {
			return isValidTSAServiceDetails(page.getTSAServiceNameCombo()
					.getText());
		} else {
			return false;
		}
	}

	/**
	 * Method which handles the blank/mismatched application name.
	 * <p>
	 * The user won't be allowed to save the unknown application name and the
	 * name should not be blank too.
	 * 
	 * @param typedName
	 *            - application name chose/typed by user.
	 * @return true if the given name is valid, false otherwise.
	 */
	private boolean isValidApplicationName(String typedName) {
		if (page.getApplicationSection() == null
				|| getEventEditor().getCurrentProject() == null) {
			return false;
		}
		List<String> availableApplication = connectionCntrl
				.getApplicationList(getEventEditor().getCurrentProject());
		return availableApplication.contains(typedName)
				&& !"".equalsIgnoreCase(typedName);
	}

	/**
	 * Helps to validate the given operation name for component services
	 * 
	 * @param operation
	 * @return true if valid, false otherwise.
	 */
	private boolean isValidComponentOperationDetails(String operation) {
		if (page.getComponentServiceSection() == null
				|| page.getComponentOperationCombo() == null
				|| getEventEditor().getCurrentProject() == null
				|| page.getComponentServiceCombo() == null
				|| page.getComponentServiceCombo().getText()
						.equalsIgnoreCase("")) {
			return false;
		}
		String[] availableOperations = connectionCntrl.getOperationList(
				getEventEditor().getCurrentProject(), page
						.getComponentServiceCombo().getText());
		for (String availOperation : availableOperations) {
			if (operation.equalsIgnoreCase(availOperation)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helps to validate the service details given in exit point page.
	 * 
	 * @param service
	 * @return true if valid details, false otherwise.
	 */
	private boolean isValidComponentServiceDetails(String service) {
		if (page.getComponentServiceSection() == null
				|| getEventEditor().getCurrentProject() == null) {
			return false;
		}
		String[] availableServices = connectionCntrl
				.getServiceList(getEventEditor().getCurrentProject());
		for (String availService : availableServices) {
			if (service.equalsIgnoreCase(availService)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Decide whether the given selected service is valid or not.
	 * 
	 * @param selectedService
	 * @return true if valid, false otherwise.
	 */
	private boolean isValidTSAServiceDetails(String selectedService) {
		if (selectedService == null || selectedService.length() == 0) {
			return false;
		}
		List<String> availableTSAServices = connectionCntrl
				.getTsaServicesList(getEventEditor().getCurrentProject());
		return availableTSAServices.contains(selectedService);
	}

	/**
	 * Method which handles the blank/mismatched version name.
	 * <p>
	 * The user won't be allowed to save the unknown version name and the name
	 * should not be blank too.
	 * 
	 * @param typedName
	 *            - version name chose/typed by user.
	 * @return true if the given name is valid, false otherwise
	 */
	public boolean isValidVersionName(String typedName) {
		if (getEventEditor().getCurrentProject() == null) {
			return false;
		}
		List<String> availableList = connectionCntrl
				.getVersionList(getEventEditor().getCurrentProject());
		if (availableList.contains(typedName)
				&& !"".equalsIgnoreCase(typedName)) {
			return true;
		}
		return false;
	}

	public void keyPressed(KeyEvent keyEvent) {
		Object source = keyEvent.getSource();
		if (source.equals(page.getVersionNameCombo())) {
			versionNameComboKeyPressed();
		} else if (source.equals(page.getVersionExitPointCombo())) {
			versionExitPointComboKeyPressed();
		} else if (source.equals(page.getApplicationNameCombo())) {
			applicationNameComboKeyPressed();
		}
	}

	public void keyReleased(KeyEvent keyEvent) {
		Object source = keyEvent.getSource();
		if (source.equals(page.getApplicationExitPointCombo())) {
			applicationExitPointComboKeyReleased();
		}

	}

	public void modifyText(ModifyEvent modifyEvent) {
		Object source = modifyEvent.getSource();
		if (source.equals(page.getVersionNameCombo())) {
			versionNameComboTextModified(modifyEvent);
		} else if (source.equals(page.getVersionExitPointCombo())) {
			versionExitPointComboTextModified(modifyEvent);
		} else if (source.equals(page.getApplicationNameCombo())) {
			applicationNameComboTextModified(modifyEvent);
		} else if (source.equals(page.getApplicationExitPointCombo())) {
			applicationExitPointTextModified(modifyEvent);
		}
	}

	public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
		// TODO Auto-generated method stub
	}

	/**
	 * Helps to perform the even when an item selected from tsa service name
	 * combo box.
	 */
	private void tsaServiceNameComboSelected() {
		String selectedTsaService = page.getTSAServiceNameCombo().getText();
		if (selectedTsaService == null || selectedTsaService.length() == 0
				|| selectedTsaService.equalsIgnoreCase(globalTSAService)) {
			return;
		}
		globalTSAService = selectedTsaService;
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.getExitPointType().setContractName(selectedTsaService);
		consumerEvent.getExitPointType().setExitPoint("");
		updateEvent(getEventEditor(), consumerEvent);
		setEditorModified(getEventEditor());
		page.getComponentOperationCombo().setText("");
	}

	/**
	 * Helps to perform the event when the TSA Service radio button is selected.
	 */
	private void tsaServiceRadioButtonSelected() {
		ExitPointType exitPointType = new TSAService();
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.setExitPointType(exitPointType);
		updateEvent(getEventEditor(), consumerEvent);
		setEditorModified(getEventEditor());

		page.getTSAServiceSection().setEnabled(true);

		page.getComponentServiceSection().setEnabled(false);
		page.getVersionSection().setEnabled(false);
		page.getVersionNameCombo().setText("");
		page.getVersionExitPointCombo().setText("");
		page.getApplicationSection().setEnabled(false);
		page.getApplicationNameCombo().setText("");
		page.getApplicationExitPointCombo().setText("");
		getEventEditor().getOverridesPage().refresh();

		// get the content for TSA Service Name combo box
		List<String> contentForTsaServiceNameCombo = connectionCntrl
				.getTsaServicesList(getEventEditor().getCurrentProject());
		// set the content to the combo
		// additional work!
		String[] tsaServices = new String[contentForTsaServiceNameCombo.size()];
		int index = 0;
		for (String value : contentForTsaServiceNameCombo) {
			if (value != null) {
				tsaServices[index] = value;
				index++;
			}
		}
		page.getTSAServiceNameCombo().setItems(tsaServices);
	}

	/**
	 * Method which helps to perform the action when the version exit point
	 * combo box gained focus.
	 */
	private void versionComboExitPointGainedFocus() {
		if (!isFirstVersionExitPoint) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							String vExitPoint = page.getVersionExitPointCombo()
									.getText();
							List<String> exitPoints = getExitPoints(
									connectionCntrl, getEventEditor());
							String[] exitPointsArray = null;
							exitPointsArray = exitPoints.toArray(new String[0]);
							page.getVersionExitPointCombo().setItems(
									exitPointsArray);
							isFirstVersionExitPoint = true;
							page.getVersionExitPointCombo().setText(vExitPoint);
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when key pressed on version exit
	 * point combo box.
	 */
	private void versionExitPointComboKeyPressed() {
		if (!isFirstVersionExitPoint) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							String vExitPoint = page.getVersionExitPointCombo()
									.getText();
							List<String> exitPoints = getExitPoints(
									connectionCntrl, getEventEditor());
							String[] exitPointsArray = null;
							exitPointsArray = exitPoints.toArray(new String[0]);
							page.getVersionExitPointCombo().setItems(
									exitPointsArray);
							isFirstVersionExitPoint = true;
							page.getVersionExitPointCombo().setText(vExitPoint);
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when text of version exit point
	 * combo modified.
	 * 
	 * @param event
	 *            - instance of {@link ModifyEvent}
	 */
	private void versionExitPointComboTextModified(ModifyEvent event) {
		String value = ((Combo) event.widget).getText();
		if (value != null && !value.equals("") && isFirstVersionExitPoint) {
			if (!value.equals(globalVersionExitPoint)
					&& getExitPoints(connectionCntrl, getEventEditor())
							.contains(value)) {
				Event consumerEvent = getEvent(getEventEditor());
				consumerEvent.getExitPointType().setExitPoint(value);
				updateEvent(getEventEditor(), consumerEvent);
				getEventEditor().getOverridesPage().refresh();
				if (!value.equalsIgnoreCase(globalVersionExitPoint)
						&& !globalVersionExitPoint.equalsIgnoreCase("")) {
					setEditorModified(getEventEditor());
				}
			}
		}
		globalVersionExitPoint = value;
	}

	/**
	 * Method which helps to perform the action when the version name combo box
	 * gained the focus.
	 */
	private void versionNameComboGainedFocus() {
		if (!isFirstVersionList) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							List<String> list = connectionCntrl
									.getVersionList(getEventEditor()
											.getCurrentProject());
							String[] items = null;
							if (list != null) {
								items = list.toArray(new String[0]);
							}
							page.getVersionNameCombo().setItems(items);
							String contractName = getEvent(getEventEditor())
									.getExitPointType().getContractName();
							if (contractName != null) {
								page.getVersionNameCombo()
										.setText(contractName);
							}
							isFirstVersionList = true;
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action when key pressed on version name
	 * combo box.
	 */
	private void versionNameComboKeyPressed() {
		if (!isFirstVersionList) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			BusyIndicator.showWhile(window.getShell().getDisplay(),
					new Runnable() {
						public void run() {
							List<String> list = connectionCntrl
									.getVersionList(getEventEditor()
											.getCurrentProject());
							String[] items = null;
							if (list != null) {
								items = list.toArray(new String[0]);
							}
							page.getVersionNameCombo().setItems(items);
							String contractName = getEvent(getEventEditor())
									.getExitPointType().getContractName();
							if (contractName != null) {
								page.getVersionNameCombo()
										.setText(contractName);
							}
							isFirstVersionList = true;
						}
					});
		}
	}

	/**
	 * Method which helps to perform the action whenever the text in version
	 * name combo box modified.
	 * 
	 * @param event
	 *            - modify event.
	 */
	private void versionNameComboTextModified(ModifyEvent event) {
		// check if the change really took place
		String version = ((Combo) event.widget).getText();
		if (version != null && isValidVersionName(version)
				&& isFirstVersionList) {
			Event consumerEvent = getEvent(getEventEditor());
			consumerEvent.getExitPointType().setContractName(version);
			updateEvent(getEventEditor(), consumerEvent);
			if (!version.equals(globalVersion)) {
				setEditorModified(getEventEditor());
			}
			globalVersion = version;
		}
	}

	/**
	 * Method which helps to perform the action when the version radio button of
	 * {@link ExitPointPage} has been selected.
	 */
	private void versionRadioButtonSelected() {
		ExitPointType exitPointType = new Version();
		Event event = getEvent(getEventEditor());
		event.setExitPointType(exitPointType);
		updateEvent(getEventEditor(), event);
		page.getApplicationSection().setEnabled(false);
		page.getComponentServiceSection().setEnabled(false);
		page.getApplicationNameCombo().setText("");
		page.getApplicationExitPointCombo().setText("");
		page.getComponentOperationCombo().setText("");
		page.getComponentServiceCombo().setText("");
		page.getTSAServiceSection().setEnabled(false);
		page.getTSAServiceNameCombo().setText("");

		page.getVersionSection().setEnabled(true);
		globalService = "";
		globalOperation = "";
		setEditorModified(getEventEditor());
	}

	public void widgetDefaultSelected(SelectionEvent selectionEvent) {

	}

	public void widgetSelected(SelectionEvent selectionEvent) {
		Object source = selectionEvent.getSource();
		if (source.equals(page.getApplicationRadioButton())) {
			applicationRadioButtonSelected();
		} else if (source.equals(page.getVersionRadioButton())) {
			versionRadioButtonSelected();
		} else if (source.equals(page.getComponentServiceRadioButton())) {
			componentServiceRadioButtonSelected();
		} else if (source.equals(page.getTSAServiceRadioButton())) {
			tsaServiceRadioButtonSelected();
		} else if (source.equals(page.getComponentServiceCombo())) {
			componentServiceComboSelected();
		} else if (source.equals(page.getComponentOperationCombo())) {
			componentOperationComboSelected();
		} else if (source.equals(page.getTSAServiceNameCombo())) {
			tsaServiceNameComboSelected();
		}

	}

}
