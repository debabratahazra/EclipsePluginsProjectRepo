package com.odcgroup.integrationfwk.ui.editors;

import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.editors.listeners.AbstractFlowsEditorListener;
import com.odcgroup.integrationfwk.ui.editors.listeners.VisualFlowPageListener;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.FlowAttribute;
import com.odcgroup.integrationfwk.ui.model.SourceType;

/**
 * Class which helps to create the ui components of visual flow page which will
 * be placed in flows editor.
 * 
 */
public class VisualFlowPage extends FormPage {
	/** selected flows table viewer */
	private TableViewer tableViewer;
	/** application,version/component service combo box */
	private Combo cmbApplicationName;
	/** checkbox, Include before image */
	private Button chkBoxInclB4Img;
	/** checkbox, Include before image */
	private Button chkBoxProcessOnlyUpdated;
	/** section which holds the ui components of event inputs */
	private Section eventInputSection;
	/** section which contains the table */
	private Section tableSection;
	/** table instance. */
	private Table table;
	/** instance of {@link AbstractFlowsEditorListener} */
	private final AbstractFlowsEditorListener editorListener;
	/** available flows list */
	private StructuredViewer listTableViewer;
	/** add button */
	private Button btnAdd;
	/** add all button */
	private Button buttonAddAll;
	/** remove button */
	private Button btnRemove;
	/** move up button */
	private Button buttonMoveUp;
	/** move down button */
	private Button buttonMoveDown;
	/** Join Definition Button which initiate the Join Definition Dialog */
	private Button buttonAddJoin;
	/** Button which helps to open the property field designer dialog */
	private Button buttonAddPropertyField;
	/** instance of visual flow page's scrolled form */
	private ScrolledForm scrolledForm;
	private GridData gd_1;
	/** checkbox, To Enable Digital Signature */
	private Button enableDigitalSignature;

	public VisualFlowPage(FormEditor editor) {
		super(editor, "Flow page", "Flow page");
		editorListener = new VisualFlowPageListener(this);
	}

	/**
	 * Helps to get the label name which has to be displayed on Flow Page.
	 * 
	 * @return Application - for Application or Version type of Exit point,
	 *         Component Services : - for Component Service type of exit point,
	 *         TSA Services : - for TSA Service type of exit point.
	 * @throws TWSConsumerPluginException
	 */
	private String computeLabelName() throws TWSConsumerPluginException {
		SourceType exitPointType = getFlow().getExitPointType();
		if (exitPointType == null) {
			// comes only when the flow has been designed with the plug-in which
			// doesn't have the TSA Service changes.
			if (getFlow().isComponentService()) {
				// update the exit point type
				getFlow().setExitPointType(SourceType.COMPONENT_SERVICE);
				// return the respective name
				return "Component Services :";
			} else {
				// update the exit point type
				// set exit point type as version in order to make line with
				// default flow creation.
				getFlow().setExitPointType(SourceType.VERSION);
				// return the respective name
				return "Application : ";
			}
		}
		switch (exitPointType) {
		case VERSION:
			return "Application : ";
		case APPLICATION:
			return "Application : ";
		case COMPONENT_SERVICE:
			return "Component Services :";
		case TSA_SERVICE:
			return "Application : ";
		}
		// should happen only when trying to open flow which has been designed
		// the plug-in which doesn't have the component service functionality.
		throw new TWSConsumerPluginException(
				"UnSupported ExitPoint Type - The latest plug-in is not supported for flow which has been created using previous version of plug-in.");
	}

	/**
	 * Method which helps to create the event input section ui components using
	 * given tool kit, within the given form page.
	 * 
	 * @param toolkit
	 *            - kit which help to create the ui components.
	 * 
	 * @param title
	 *            - title for the section.
	 * @throws TWSConsumerPluginException
	 */
	private void createEventInputSection(FormToolkit toolkit, String title)
			throws TWSConsumerPluginException {
		eventInputSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		eventInputSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(eventInputSection);
		Composite client = toolkit.createComposite(eventInputSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;
		client.setLayout(layout);

		Label lblName = null;

		// create composite for holding Exit Point Type and Attributes
		// Information
		Composite attributeComposite = toolkit.createComposite(client);
		attributeComposite.setLayout(new GridLayout(2, true));
		GridData attribGd = new GridData(GridData.FILL_BOTH);
		attribGd.verticalAlignment = SWT.TOP;
		attributeComposite.setLayoutData(attribGd);
		lblName = toolkit.createLabel(attributeComposite, computeLabelName());
		cmbApplicationName = new Combo(attributeComposite, SWT.BORDER);
		cmbApplicationName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1));

		List<String> list = ((VisualFlowPageListener) editorListener)
				.getEventInput();
		String[] items = { "" };
		if (list != null) {
			items = list.toArray(new String[0]);
		}
		String baseEvent = getFlow().getBaseEvent();
		if (baseEvent.contains(",")) {
			int val = baseEvent.indexOf(",");
			String temp = baseEvent.substring(0, val);
			baseEvent = temp;
		}

		cmbApplicationName.setItems(items);
		cmbApplicationName.setText(baseEvent);
		cmbApplicationName.addModifyListener(editorListener);

		// Attributes
		// Create a check box, assign text, set tool tip and register listener
		chkBoxInclB4Img = toolkit.createButton(attributeComposite,
				FlowAttribute.INCLUDE_BEFORE_IMAGE.getLabel(), SWT.CHECK);
		boolean enable = FlowAttribute.INCLUDE_BEFORE_IMAGE
				.isExitPointAllowed(getFlow());
		if (enable) {
			chkBoxInclB4Img.setEnabled(true);
			chkBoxInclB4Img.setSelection(getFlow().getAttributes().contains(
					FlowAttribute.INCLUDE_BEFORE_IMAGE));
		} else {
			chkBoxInclB4Img.setEnabled(false);
			getFlow().getAttributes().removeFlowAttribute(
					FlowAttribute.INCLUDE_BEFORE_IMAGE);
		}
		chkBoxInclB4Img.setToolTipText(FlowAttribute.INCLUDE_BEFORE_IMAGE
				.getToolTip());
		chkBoxInclB4Img.addSelectionListener(editorListener);
		new Label(attributeComposite, SWT.NONE);

		// Create a check box, assign text, set tool tip and register listener
		chkBoxProcessOnlyUpdated = toolkit.createButton(attributeComposite,
				FlowAttribute.PROCESS_ONLY_UPDATES.getLabel(), SWT.CHECK);
		enable = FlowAttribute.PROCESS_ONLY_UPDATES
				.isExitPointAllowed(getFlow());
		if (enable) {
			chkBoxProcessOnlyUpdated.setEnabled(true);
			chkBoxProcessOnlyUpdated.setSelection(getFlow().getAttributes()
					.contains(FlowAttribute.PROCESS_ONLY_UPDATES));
		} else {
			chkBoxProcessOnlyUpdated.setEnabled(false);
			getFlow().getAttributes().removeFlowAttribute(
					FlowAttribute.PROCESS_ONLY_UPDATES);
		}
		chkBoxProcessOnlyUpdated
				.setToolTipText(FlowAttribute.PROCESS_ONLY_UPDATES.getToolTip());
		chkBoxProcessOnlyUpdated.addSelectionListener(editorListener);
		new Label(attributeComposite, SWT.NONE);

		// Enable Digital Signature
		// Create a check box, assign text, set tool tip and register listener
		enableDigitalSignature = toolkit.createButton(attributeComposite,
				FlowAttribute.INCLUDE_DIGITAL_SIGNATURE.getLabel(), SWT.CHECK);

		enableDigitalSignature.setSelection(false);
		enableDigitalSignature.setSelection(getFlow().getAttributes().contains(
				FlowAttribute.INCLUDE_DIGITAL_SIGNATURE));

		enableDigitalSignature
				.setToolTipText(FlowAttribute.INCLUDE_DIGITAL_SIGNATURE
						.getToolTip());
		enableDigitalSignature.addSelectionListener(editorListener);
		new Label(attributeComposite, SWT.NONE);

		if (isEmpty(baseEvent)) {
			// clearing the null string
			baseEvent = "";
		}

		lblName = toolkit.createLabel(client, "Field:");
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		lblName.setLayoutData(gd);
		listTableViewer = new TableViewer(client, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI);
		listTableViewer.setLabelProvider(new FieldListLabelProvider());
		listTableViewer.setContentProvider(new ArrayContentProvider());
		Table listTable = ((TableViewer) listTableViewer).getTable();
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_list.verticalAlignment = SWT.TOP;
		gd_list.widthHint = 250;
		gd_list.heightHint = 180;
		listTable.setLayoutData(gd_list);
		listTableViewer.addDoubleClickListener(editorListener);
		listTableViewer.addSelectionChangedListener(editorListener);

		// create composite for holding the buttons
		Composite actionComposite = toolkit.createComposite(client);
		actionComposite.setLayout(new GridLayout(1, true));
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.TOP;
		actionComposite.setLayoutData(gd);

		btnAdd = toolkit.createButton(actionComposite, "Add", SWT.FLAT);
		btnAdd.addSelectionListener(editorListener);
		// disable the button initially
		btnAdd.setEnabled(false);

		// create add all button
		buttonAddAll = toolkit.createButton(actionComposite, "Add All",
				SWT.FLAT);
		buttonAddAll.addSelectionListener(editorListener);

		// create add join button
		buttonAddJoin = toolkit.createButton(actionComposite, "Add Join",
				SWT.FLAT);
		buttonAddJoin.addSelectionListener(editorListener);
		// disable the button initially
		buttonAddJoin.setEnabled(false);

		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		btnAdd.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		buttonAddAll.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		buttonAddJoin.setLayoutData(gd);

		gd = new GridData();
		((VisualFlowPageListener) editorListener).loadFields();
		if (((VisualFlowPageListener) editorListener).isAllFieldsSelected()) {
			buttonAddAll.setEnabled(false);
		}

		eventInputSection.setText("Event Input");

		eventInputSection.setClient(client);

		gd_1 = new GridData(GridData.FILL_BOTH);
		gd_1.verticalAlignment = SWT.CENTER;
		gd_1.horizontalAlignment = SWT.LEFT;
		gd_1.grabExcessVerticalSpace = false;
		gd_1.grabExcessHorizontalSpace = false;
		eventInputSection.setLayoutData(gd_1);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		scrolledForm = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		scrolledForm.setText("Flow");
		scrolledForm.setBackgroundImage(IntegrationToolingActivator
				.getDefault().getImage(
						"icons/" + IntegrationToolingActivator.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		// this will ensure that there will be 2 columns
		layout.numColumns = 1;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;

		scrolledForm.getBody().setLayout(layout);
		eventInputSection = toolkit.createSection(scrolledForm.getBody(),
				Section.TITLE_BAR);
		tableSection = toolkit.createSection(scrolledForm.getBody(),
				Section.TITLE_BAR);
		try {
			createEventInputSection(toolkit, "Event input");
			createTableSection(toolkit, "Flow Enrichments");
		} catch (TWSConsumerPluginException e) {
			TWSConsumerLog.logError(e);
			// showing the error in form page
			setError(e.getMessage());
			// TODO: remove this error dialog if this is not really requires.
			MessageDialog
					.openError(
							getSite().getShell(),
							"Integration Tooling Plugin",
							"This flow is not designed with the current version of Event Designer Plug-in. Please re-create the Flow.");
		}
	}

	/**
	 * Method which helps to create the table section ui components using given
	 * toolkit.
	 * 
	 * 
	 * @param toolkit
	 *            - kit which helped to create the ui components.
	 * @param title
	 *            - title to be shown on the section.
	 */
	private void createTableSection(FormToolkit toolkit, String title) {
		tableSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		tableSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(tableSection);

		Composite client = toolkit.createComposite(tableSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		client.setLayout(layout);

		tableViewer = new TableViewer(client, SWT.BORDER | SWT.V_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.RESIZE);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		toolkit.paintBordersFor(table);
		String[] columnNames = new String[] { "Field Definition",
				"Display Name", "Field type" };
		int[] columnAllignments = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT };
		int[] columnWidths = new int[] { 300, 200, 100 };
		for (int i = 0; i < columnNames.length; i++) {
			TableViewerColumn tableColumn = new TableViewerColumn(tableViewer,
					columnAllignments[i]);
			tableColumn.getColumn().setWidth(columnWidths[i]);
			tableColumn.getColumn().setText(columnNames[i]);
			if (i == 1) {
				tableColumn
						.setEditingSupport(new FlowTableTextCellEditingSupport(
								tableViewer, editorListener));
			}
		}
		tableViewer.setLabelProvider(new FlowTableLabelProvider());

		tableViewer.setContentProvider(new ArrayContentProvider());

		tableViewer.setInput(getFlow().getFields().getInputFields());
		GridData gd = new GridData();
		gd.heightHint = 450;
		table.setLayoutData(gd);
		tableViewer.addDoubleClickListener(editorListener);
		tableSection.setClient(client);
		tableSection.setText(title);

		gd = new GridData(GridData.FILL_BOTH);
		tableSection.setLayoutData(gd);

		// create composite for holding the buttons
		Composite actionComposite = toolkit.createComposite(client);
		actionComposite.setLayout(new GridLayout(1, true));
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.TOP;
		actionComposite.setLayoutData(gd);

		// remove button creation
		btnRemove = toolkit.createButton(actionComposite, "Remove", SWT.FLAT);
		// btnRemove.setEnabled(false);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		btnRemove.setLayoutData(gd);

		// move up button creation
		buttonMoveUp = toolkit.createButton(actionComposite, "Move Up",
				SWT.FLAT);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		buttonMoveUp.setLayoutData(gd);

		// move down button creation
		buttonMoveDown = toolkit.createButton(actionComposite, "Move Down",
				SWT.FLAT);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		buttonMoveDown.setLayoutData(gd);

		// add property field button creation
		buttonAddPropertyField = toolkit.createButton(actionComposite,
				"Add Custom Field", SWT.FLAT);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		buttonAddPropertyField.setLayoutData(gd);

		// adding listener to buttons
		btnRemove.addSelectionListener(editorListener);
		buttonMoveUp.addSelectionListener(editorListener);
		buttonMoveDown.addSelectionListener(editorListener);
		buttonAddPropertyField.addSelectionListener(editorListener);
	}

	/**
	 * 
	 * @return instance of {@link #buttonAddAll}
	 */
	public Button getAddAllButton() {
		return buttonAddAll;
	}

	/**
	 * 
	 * @return instance of {@link #btnAdd}
	 */
	public Button getAddButton() {
		return btnAdd;
	}

	/**
	 * 
	 * @return instance of {@link #buttonAddJoin}
	 */
	public Button getAddJoinButton() {
		return buttonAddJoin;
	}

	/**
	 * 
	 * @return instance of {@link #buttonAddPropertyField}
	 */
	public Button getAddPropertyFieldButton() {
		return buttonAddPropertyField;
	}

	/**
	 * 
	 * @return instance of {@link #cmbApplicationName}
	 */
	public Combo getAvailableApplicationCombo() {
		return cmbApplicationName;
	}

	/**
	 * @return instance of {@link #chkBoxInclB4Img}
	 */
	public Button getChkBoxInclB4Img() {
		return chkBoxInclB4Img;
	}

	/**
	 * @return instance of {@link #chkBoxProcessOnlyUpdated}
	 */
	public Button getChkBoxProcessOnlyUpdated() {
		return chkBoxProcessOnlyUpdated;
	}

	/**
	 * @return instance of {@link #enableDigitalSignature}
	 */
	public Button getEnableDigitalSignature() {
		return enableDigitalSignature;
	}

	/**
	 * Get the most recent flow object from the factory of
	 * {@link ConsumerEditorManager}
	 * 
	 * @return instance of {@link Flow}
	 */
	private Flow getFlow() {
		return ConsumerEditorManager.getInstance()
				.getFlow(getFlowEditor().getProjectName(),
						getFlowEditor().getFlowName());
	}

	/**
	 * Get the flows editor where this flow page has been placed.
	 * 
	 * @return instance of {@link FlowsEditor}
	 */
	private FlowsEditor getFlowEditor() {
		return ((FlowsEditor) getEditor());
	}

	/**
	 * 
	 * @return instance of {@link #listTableViewer}
	 */
	public StructuredViewer getListTableViewer() {
		return listTableViewer;
	}

	/**
	 * 
	 * @return the instance of {@link #buttonMoveDown}
	 */
	public Button getMoveDownButton() {
		return buttonMoveDown;
	}

	/**
	 * 
	 * @return the instance of {@link #buttonMoveUp}
	 */
	public Button getMoveUpButton() {
		return buttonMoveUp;
	}

	/**
	 * get the remove button.
	 * 
	 * @return instance of {@link #btnRemove}
	 */
	public Button getRemoveButton() {
		return btnRemove;
	}

	/**
	 * Get the table viewer where the selected flows will be shown.
	 * 
	 * @return instance of {@link #tableViewer}
	 */
	public TableViewer getSelectedFlowsTable() {
		return tableViewer;
	}

	/**
	 * Decide whether the given string is empty or not.
	 * 
	 * @param value
	 * @return true if the given value's
	 *         <code> length is zero | is Null | is equals to string null | is equals to string null,null </code>
	 *         , false otherwise.
	 */
	private boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		if (value.equalsIgnoreCase("null")
				|| value.equalsIgnoreCase("null,null")) {
			return true;
		}
		return false;
	}

	/**
	 * Helps to set error message on the top of Visual Flow Page.
	 * <p>
	 * If the given error message is empty or <code>null</code>, then this
	 * method will clear the error message which has already set to the form
	 * page.
	 * 
	 * @param errorMessage
	 */
	public void setError(String errorMessage) {
		if (errorMessage == null || errorMessage.length() == 0) {
			scrolledForm.setMessage(null, IMessageProvider.NONE);
		} else {
			scrolledForm.setMessage(errorMessage, IMessageProvider.ERROR);
		}
	}

}
