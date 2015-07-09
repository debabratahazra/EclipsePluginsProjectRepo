package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.editors.listeners.AbstractEventsEditorListener;
import com.odcgroup.integrationfwk.ui.editors.listeners.FlowPageListener;

/**
 * Class which helps to create the ui of Flow page which will be placed in
 * Events Editor.
 * 
 */
public class FlowPage extends FormPage {
	/** flow name text box */
	private Text flowName;
	/** edit button */
	private Button btnEdit;
	/** instance of {@link AbstractEventsEditorListener} */
	private final AbstractEventsEditorListener editorListener;
	/** Browse button */
	private Button btnBrowse;
	/** string which represents the priorly mapped flow name */
	private String mappedFlowName = "";

	public FlowPage(FormEditor editor) {
		super(editor, "Flow page", "Flow page");
		editorListener = new FlowPageListener(this);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText("Flow");
		form.setBackgroundImage(IntegrationToolingActivator.getDefault().getImage(
			        "icons/" + IntegrationToolingActivator.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		// this will ensure that there will be 2 columns
		layout.numColumns = 4;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;

		form.getBody().setLayout(layout);
		Section section = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR);

		section.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(section);
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 4;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;

		client.setLayout(layout);
		toolkit.createLabel(client, "Flow name:");

		toolkit.paintBordersFor(client);
		flowName = toolkit.createText(client, "", SWT.SINGLE);
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 3;
		gd.widthHint = 250;
		flowName.setLayoutData(gd);
		section.setText("Flow");
		section.setClient(client);

		gd = new GridData(GridData.FILL_BOTH);
		section.setLayoutData(gd);
		flowName.setText(mappedFlowName);
		flowName.addModifyListener(editorListener);
		new Label(client, SWT.NONE);
		btnEdit = toolkit.createButton(client, "Edit", SWT.FLAT);
		if (flowName.getText().equals("")) {
			form.setMessage(null, IMessageProvider.NONE);
			btnEdit.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
		}
		btnEdit.addSelectionListener(editorListener);
		btnBrowse = toolkit.createButton(client, "Browse", SWT.FLAT);
		btnBrowse.addSelectionListener(editorListener);
		new Label(managedForm.getForm().getBody(), SWT.NONE);
	}

	/**
	 * 
	 * @return instance of {@link #btnBrowse}
	 */
	public Button getBrowseButton() {
		return btnBrowse;
	}

	/**
	 * 
	 * @return instance of {@link #btnEdit}
	 */
	public Button getEditButton() {
		return btnEdit;
	}

	/**
	 * get the text entered in flow name text box.
	 * 
	 * @return
	 */
	public String getFlowName() {
		if (flowName == null) {
			return "";
		}
		return flowName.getText();
	}

	/**
	 * 
	 * @return instance of {@link #flowName}
	 */
	public Text getFlowNameTextBox() {
		return flowName;
	}

	/**
	 * Method which helps to refresh the ui component of flow page.
	 */
	public void refresh() {
		if (flowName.getText().equals("")) {
			btnEdit.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
		}
	}

	/**
	 * Helps to set the given string into flow name text box
	 * 
	 * @param flowName
	 */
	public void setFlowNameText(String mappedFlowName) {
		this.mappedFlowName = mappedFlowName;
	}

}
