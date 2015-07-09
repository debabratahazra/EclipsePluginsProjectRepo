package com.odcgroup.integrationfwk.ui.editors;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.editors.listeners.AbstractEventsEditorListener;
import com.odcgroup.integrationfwk.ui.editors.listeners.ExitPointPageListener;
import com.odcgroup.integrationfwk.ui.model.ComponentService;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.SourceType;

/**
 * Class which helps to draw the ui components of Exit Point page which will be
 * placed in Events Editor.
 * 
 */
public class ExitPointPage extends FormPage {
	private Button btnVersion;
	private Button btnApplication;
	/** Component Service Radio Button */
	private Button btnComponentService;
	/** Radio Button for TSA service type of exit point */
	private Button buttonTSAService;
	private Combo cmbVersionName;
	private Combo cmbVersionExitPoint;
	private Combo cmbApplicationName;
	private Combo cmbApplicationExitPoint;
	/** component service combo box */
	private Combo comboComponentService;
	/** component operation combo box */
	private Combo comboComponentOperation;
	/** combo box for providing the list of TSA services */
	private Combo comboTSAService;

	private Section versionSection;
	private Section applicationSection;
	private Section exitPointSection;
	/** section which contains the ui components for component services */
	private Section componentServiceSection;
	/** section which contains the ui components for tsa related services */
	private Section tsaServiceSection;

	/** instance of {@link AbstractEventsEditorListener} */
	private final AbstractEventsEditorListener editorListener;

	public ExitPointPage(FormEditor editor) {
		super(editor, "Exit Point", "Exit Point");
		editorListener = new ExitPointPageListener(this);
	}

	/**
	 * method which helps to create the application section ui components with
	 * in the given form, using given toolkit.
	 * 
	 * @param form
	 *            - scrolled form in the form page.
	 * @param toolkit
	 *            - kit to help draw the ui components.
	 * @param title
	 *            - title for the application section.
	 */
	private void createApplicationSection(final ScrolledForm form,
			FormToolkit toolkit, String title) {
		applicationSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		applicationSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(applicationSection);
		Composite client = toolkit
				.createComposite(applicationSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;

		client.setLayout(layout);
		Label lblName = toolkit.createLabel(client, "Name:");
		GridData gd = new GridData();
		lblName.setLayoutData(gd);

		cmbApplicationName = new Combo(client, SWT.BORDER);
		gd = new GridData();

		gd.widthHint = 350;

		cmbApplicationName.setLayoutData(gd);

		Label lblExitPoint = toolkit.createLabel(client, "Exit Point:");
		gd = new GridData();

		lblExitPoint.setLayoutData(gd);

		cmbApplicationExitPoint = new Combo(client, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 350;

		cmbApplicationExitPoint.setLayoutData(gd);

		ExitPointType exitPointType = getEvent().getExitPointType();
		String resourceType = null;
		if (exitPointType != null) {
			resourceType = exitPointType.getSourceType();
		}
		if ((resourceType != null)
				&& (resourceType.equalsIgnoreCase("APPLICATION"))) {
			String contractName = getEvent().getExitPointType()
					.getContractName();
			if (contractName != null) {
				cmbApplicationName.setText(contractName);
			}
			String exitPoint = getEvent().getExitPointType().getExitPoint();
			if (exitPoint != null) {
				cmbApplicationExitPoint.setText(exitPoint);
			}
		} else {
			// not an application hence disable the section
			applicationSection.setEnabled(false);
		}
		cmbApplicationName.addModifyListener(editorListener);
		cmbApplicationName.addKeyListener(editorListener);
		cmbApplicationName.addFocusListener(editorListener);
		cmbApplicationExitPoint.addModifyListener(editorListener);
		cmbApplicationExitPoint.addKeyListener(editorListener);
		cmbApplicationExitPoint.addFocusListener(editorListener);
		applicationSection.setText(title);
		applicationSection.setClient(client);
		gd = new GridData(GridData.FILL_BOTH);
		applicationSection.setLayoutData(gd);
	}

	/**
	 * Method which helps to create the component service section ui components
	 * in the given form, using given toolkit.
	 * 
	 * @param form
	 *            - scrolled form within the form page.
	 * @param toolkit
	 *            - kit which helps to draw the ui components.
	 * @param title
	 *            - title of the component service section
	 */
	private void createComponentServiceSection(ScrolledForm form,
			FormToolkit toolkit, String title) {
		componentServiceSection.setActiveToggleColor(toolkit
				.getHyperlinkGroup().getActiveForeground());
		componentServiceSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		componentServiceSection.setText(title);
		toolkit.createCompositeSeparator(componentServiceSection);
		Composite client = toolkit.createComposite(componentServiceSection,
				SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;
		client.setLayout(layout);
		toolkit.paintBordersFor(client);
		componentServiceSection.setClient(client);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		componentServiceSection.setLayoutData(gridData);

		/** service label */
		Label serviceLabel = toolkit.createLabel(client, "Service : ");
		gridData = new GridData(SWT.RIGHT);
		serviceLabel.setLayoutData(gridData);

		comboComponentService = new Combo(client, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 350;
		comboComponentService.setLayoutData(gridData);

		/** operation label */
		Label operationLabel = toolkit.createLabel(client, "Operation : ");
		gridData = new GridData(SWT.RIGHT);
		operationLabel.setLayoutData(gridData);

		comboComponentOperation = new Combo(client, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 350;
		comboComponentOperation.setLayoutData(gridData);
		componentServiceSection
				.setEnabled(getEvent().getExitPointType() instanceof ComponentService);
		if (componentServiceSection.isEnabled()) {
			comboComponentService.setText(getEvent().getExitPointType()
					.getContractName());
			comboComponentOperation.setText(getEvent().getExitPointType()
					.getExitPoint());
		}
		// listening the component service combo box
		comboComponentService.addFocusListener(editorListener);
		comboComponentService.addSelectionListener(editorListener);
		// listening the component operation combo box
		comboComponentOperation.addSelectionListener(editorListener);
		comboComponentOperation.addFocusListener(editorListener);
	}

	/**
	 * method which helps to create the exit point section ui components in the
	 * given form, using given toolkit.
	 * 
	 * @param form
	 *            - scrolled form with in the form page.
	 * @param toolkit
	 *            - kit which helps to draw the ui components.
	 * @param title
	 *            - title of the exit point section.
	 */
	private void createExitPointSection(final ScrolledForm form,
			FormToolkit toolkit, String title) {

		exitPointSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());

		exitPointSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(exitPointSection);

		Composite client = toolkit.createComposite(exitPointSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;

		client.setLayout(layout);
		toolkit.paintBordersFor(client);

		Group group1 = new Group(client, SWT.SHADOW_IN);
		group1.setText("Exit Point type");
		group1.setLayout(new RowLayout(SWT.VERTICAL));

		btnVersion = toolkit.createButton(group1, "Version", SWT.RADIO);
		btnApplication = toolkit.createButton(group1, "Application", SWT.RADIO);
		// button component service initialization
		btnComponentService = toolkit.createButton(group1, "Component Service",
				SWT.RADIO);
		// initialize the button TSA Service
		buttonTSAService = toolkit.createButton(group1, "TSA Service",
				SWT.RADIO);

		// get the source type from persisted event
		String sourceType = getEvent().getExitPointType().getSourceType();
		// radio button selection based on the source type
		btnApplication.setSelection(sourceType
				.equalsIgnoreCase(SourceType.APPLICATION.toString()));
		btnApplication.addSelectionListener(editorListener);
		btnVersion.setSelection(sourceType.equalsIgnoreCase(SourceType.VERSION
				.toString()));
		btnVersion.addSelectionListener(editorListener);
		btnComponentService.setSelection(sourceType
				.equalsIgnoreCase(SourceType.COMPONENT_SERVICE.toString()));
		// listening the component service radio button
		btnComponentService.addSelectionListener(editorListener);
		buttonTSAService.setSelection(sourceType
				.equalsIgnoreCase(SourceType.TSA_SERVICE.toString()));
		// add listener to TSA Service radio button
		buttonTSAService.addSelectionListener(editorListener);

		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		group1.setLayoutData(gd);
		exitPointSection.setText(title);
		// exitPointSection.setDescription(title);
		exitPointSection.setClient(client);
		// exitPointSection.setExpanded(true);
		// exitPointSection.addExpansionListener(new ExpansionAdapter() {
		// public void expansionStateChanged(ExpansionEvent e) {
		// form.reflow(false);
		// }
		// });
		gd = new GridData(GridData.FILL_BOTH);
		exitPointSection.setLayoutData(gd);

	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText("Exit Point");
		form.setBackgroundImage(IntegrationToolingActivator.getDefault().getImage(
				"icons/" + IntegrationToolingActivator.IMG_FORM_BG));
		Composite parentComposite = form.getBody();
		GridLayout layout = new GridLayout();
		// this will ensure that there will be 2 columns
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		parentComposite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		parentComposite.setLayoutData(data);
		exitPointSection = toolkit.createSection(parentComposite,
				Section.TITLE_BAR);
		componentServiceSection = toolkit.createSection(parentComposite,
				Section.TITLE_BAR);
		versionSection = toolkit.createSection(parentComposite,
				Section.TITLE_BAR);
		tsaServiceSection = toolkit.createSection(parentComposite,
				Section.TITLE_BAR);
		applicationSection = toolkit.createSection(parentComposite,
				Section.TITLE_BAR);

		createExitPointSection(form, toolkit, "Exit Point Details");

		createComponentServiceSection(form, toolkit,
				"Component Service Details");

		createVersionSection(form, toolkit, "Version Details");

		createTSAServiceSection(form, toolkit, "TSA Service Details");

		createApplicationSection(form, toolkit, "Application Details");

	}

	private void createTSAServiceSection(ScrolledForm form,
			FormToolkit toolkit, String title) {
		tsaServiceSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		tsaServiceSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		tsaServiceSection.setText(title);
		toolkit.createCompositeSeparator(tsaServiceSection);
		Composite client = toolkit.createComposite(tsaServiceSection, SWT.WRAP);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;
		client.setLayout(layout);
		toolkit.paintBordersFor(client);
		tsaServiceSection.setClient(client);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		tsaServiceSection.setLayoutData(gridData);

		// disable the section
		tsaServiceSection.setEnabled(false);

		/** service name label */
		Label serviceLabel = toolkit.createLabel(client, "Service Name : ");
		gridData = new GridData(SWT.RIGHT);
		serviceLabel.setLayoutData(gridData);

		comboTSAService = new Combo(client, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 350;
		comboTSAService.setLayoutData(gridData);

		// get the items for service name combo
		List<String> servicesList = editorListener.getConnectionController(
				getEventEditor()).getTsaServicesList(
				getEventEditor().getCurrentProject());
		if (servicesList != null) {
			String[] items = servicesList.toArray(new String[0]);
			comboTSAService.setItems(items);
		}

		// add listener to combo box
		comboTSAService.addSelectionListener(editorListener);

		ExitPointType exitPointType = getEvent().getExitPointType();
		if (exitPointType == null) {
			return;
		}
		// enable the section if the type is tsa
		if (exitPointType.getSourceType().equalsIgnoreCase(
				SourceType.TSA_SERVICE.toString())) {
			tsaServiceSection.setEnabled(true);
			// get the persisted service name
			String contractName = exitPointType.getContractName();
			if (contractName != null && contractName.length() != 0) {
				comboTSAService.setText(contractName);
			}
		}

	}

	/**
	 * method which helps to create the version section ui components within the
	 * given form, using given toolkit.
	 * 
	 * @param form
	 *            - scrolled form within the form page.
	 * @param toolkit
	 *            - kit which helps to draw the ui components.
	 * @param title
	 *            - title of the version section
	 */
	private void createVersionSection(final ScrolledForm form,
			FormToolkit toolkit, String title) {
		versionSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		versionSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(versionSection);

		Composite client = toolkit.createComposite(versionSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;
		client.setLayout(layout);
		Label lblName = toolkit.createLabel(client, "Name:");
		GridData gd = new GridData();
		lblName.setLayoutData(gd);

		cmbVersionName = new Combo(client, SWT.BORDER);
		gd = new GridData();

		gd.widthHint = 350;

		cmbVersionName.setLayoutData(gd);

		Label lblExitPoint = toolkit.createLabel(client, "Exit Point:");
		gd = new GridData();

		lblExitPoint.setLayoutData(gd);

		cmbVersionExitPoint = new Combo(client, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 350;

		cmbVersionExitPoint.setLayoutData(gd);

		ExitPointType exitPointType = getEvent().getExitPointType();
		String resourceType = null;
		if (exitPointType != null) {
			resourceType = exitPointType.getSourceType();
		}
		if ((resourceType != null)
				&& (resourceType
						.equalsIgnoreCase(SourceType.VERSION.toString()))) {
			String contractName = getEvent().getExitPointType()
					.getContractName();
			if (contractName != null) {
				cmbVersionName.setText(contractName);
			}
			String exitPoint = getEvent().getExitPointType().getExitPoint();
			if (exitPoint != null) {
				cmbVersionExitPoint.setText(exitPoint);
			}
		} else {
			// not an application hence disable the section
			versionSection.setEnabled(false);
		}
		cmbVersionName.addModifyListener(editorListener);
		cmbVersionName.addKeyListener(editorListener);
		cmbVersionName.addFocusListener(editorListener);
		cmbVersionExitPoint.addModifyListener(editorListener);
		cmbVersionExitPoint.addKeyListener(editorListener);
		cmbVersionExitPoint.addFocusListener(editorListener);
		versionSection.setText(title);
		versionSection.setClient(client);
		gd = new GridData(GridData.FILL_BOTH);
		versionSection.setLayoutData(gd);
	}

	/**
	 * 
	 * @return instance of {@link #cmbApplicationExitPoint}
	 */
	public Combo getApplicationExitPointCombo() {
		return cmbApplicationExitPoint;
	}

	/**
	 * 
	 * @return instance of {@link #cmbApplicationName}
	 */
	public Combo getApplicationNameCombo() {
		return cmbApplicationName;
	}

	/**
	 * 
	 * @return instance of {@link #btnApplication}
	 */
	public Button getApplicationRadioButton() {
		return btnApplication;
	}

	/**
	 * 
	 * @return instance of {@link #applicationSection} which contains the
	 *         application related ui components.
	 */
	public Section getApplicationSection() {
		return applicationSection;
	}

	/**
	 * 
	 * @return instance of {@link #comboComponentOperation}
	 */
	public Combo getComponentOperationCombo() {
		return comboComponentOperation;
	}

	/**
	 * 
	 * @return instance of {@link #comboComponentService}
	 */
	public Combo getComponentServiceCombo() {
		return comboComponentService;
	}

	/**
	 * 
	 * @return instance of {@link #btnComponentService}
	 */
	public Button getComponentServiceRadioButton() {
		return btnComponentService;
	}

	/**
	 * 
	 * @return instance of {@link #componentServiceSection} which contains the
	 *         component services related ui components
	 */
	public Section getComponentServiceSection() {
		return componentServiceSection;
	}

	/**
	 * get the recent event object from factory of {@link ConsumerEditorManager}
	 * .
	 * 
	 * @return recent event object.
	 */
	private Event getEvent() {
		return ConsumerEditorManager.getInstance().getEvent(
				getEventEditor().getProjectName(),
				getEventEditor().getEventName());
	}

	/**
	 * get the event editor where this Exit point page has been placed.
	 * 
	 * @return instance of {@link EventsEditor}
	 */
	private EventsEditor getEventEditor() {
		return ((EventsEditor) getEditor());
	}

	/**
	 * 
	 * @return instance of {@link #comboTSAService}
	 */
	public Combo getTSAServiceNameCombo() {
		return comboTSAService;
	}

	/**
	 * 
	 * @return instance of {@link #buttonTSAService}
	 */
	public Button getTSAServiceRadioButton() {
		return buttonTSAService;
	}

	/**
	 * 
	 * @return instance of {@link #tsaServiceSection} which contains the tsa
	 *         services related ui components.
	 */
	public Section getTSAServiceSection() {
		return tsaServiceSection;
	}

	/**
	 * 
	 * @return instance of {@link #cmbVersionExitPoint}
	 */
	public Combo getVersionExitPointCombo() {
		return cmbVersionExitPoint;
	}

	/**
	 * 
	 * @return instance of {@link #cmbVersionName}
	 */
	public Combo getVersionNameCombo() {
		return cmbVersionName;
	}

	/**
	 * 
	 * @return instance of {@link #btnVersion}
	 */
	public Button getVersionRadioButton() {
		return btnVersion;
	}

	/**
	 * 
	 * @return instance of {@link #versionSection} which contains the version
	 *         related ui components.
	 */
	public Section getVersionSection() {
		return versionSection;
	}

	/**
	 * check whether the exit point page has any valid changes or not.
	 * 
	 * @return true if any valid changes occur, false otherwise
	 */
	public boolean hasValidChanges() {
		return ((ExitPointPageListener) editorListener).hasValidChanges();
	}

	/**
	 * Method which helps to refresh the ui components of Exit point page.
	 */
	public void refresh() {
		Event event = getEvent();
		if (event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.VERSION.toString())) {
			refreshVersion();
		}
		if (event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.APPLICATION.toString())) {
			refreshApplication();
		}
		refreshSelectionEnable();
	}

	/**
	 * Method which helps to refresh the ui components of application section.
	 */
	private void refreshApplication() {
		Event event = getEvent();
		if (event.getExitPointType() != null
				&& event.getExitPointType().getContractName() != null) {
			cmbApplicationName.setText(event.getExitPointType()
					.getContractName());
		} else {
			cmbApplicationName.setText("");
		}
		if (event.getExitPointType() != null
				&& event.getExitPointType().getExitPoint() != null) {
			cmbApplicationExitPoint.setText(event.getExitPointType()
					.getExitPoint());
		} else {
			cmbApplicationExitPoint.setText("");
		}
	}

	/**
	 * Method which helps to refresh the enable property of ui components.
	 */
	private void refreshSelectionEnable() {
		Event event = getEvent();
		btnApplication.setSelection(event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.APPLICATION.toString()));
		btnVersion.setSelection(event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.VERSION.toString()));
		applicationSection.setEnabled(event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.APPLICATION.toString()));
		versionSection.setEnabled(event.getExitPointType().getSourceType()
				.equalsIgnoreCase(SourceType.VERSION.toString()));
	}

	/**
	 * Method which helps to refresh the version section of ui component.
	 */
	private void refreshVersion() {
		Event event = getEvent();
		if (event.getExitPointType() != null
				&& event.getExitPointType().getContractName() != null) {
			cmbVersionName.setText(event.getExitPointType().getContractName());
		} else {
			cmbVersionName.setText("");
		}
		if (event.getExitPointType() != null
				&& event.getExitPointType().getExitPoint() != null) {
			cmbVersionExitPoint
					.setText(event.getExitPointType().getExitPoint());
		} else {
			cmbVersionExitPoint.setText("");
		}
	}

}
