package com.odcgroup.integrationfwk.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.controller.ConnectionController;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Responsible for providing the ui contents of Join Definition dialog which is
 * actually creating the Join Definition.
 * 
 * @author sbharathraja
 * 
 */
public class JoinDefinitionDialog extends TitleAreaDialog {

	/**
	 * Responsible for providing the listener to ui components of
	 * {@link JoinDefinitionDialog}
	 * 
	 * @author sbharathraja
	 * 
	 */
	private class JoinDefinitionDialogListener extends SelectionAdapter
			implements KeyListener, FocusListener {

		/**
		 * Helps to perform the event when an item is selected from application
		 * combo
		 */
		private void applicationComboSelected() {
			String selectedApplication = comboApplication.getText();
			if (selectedApplication == null
					|| selectedApplication.length() == 0) {
				return;
			}
			comboFieldName.setItems((buildFieldName(selectedApplication)));
			// set the accessibility of add button
			buttonAdd.setEnabled(isValidField(comboFieldName.getText(),
					selectedApplication));
		}

		/**
		 * Helps to build the field name corresponding to given selected
		 * application
		 * 
		 * @param selectedApplication
		 * @return string array.
		 */
		private String[] buildFieldName(String selectedApplication) {
			List<Field> availableFields = connectionController
					.getApplicationFields(currentProject, selectedApplication);

			if (availableFields == null || availableFields.isEmpty()) {
				return new String[0];
			}

			String[] fieldNames = new String[availableFields.size()];
			int index = 0;
			// iterate over the available fields to build field name array.
			for (Field field : availableFields) {
				fieldNames[index] = field.getFieldName();
				index++;
			}

			return fieldNames;
		}

		/**
		 * Helps to perform the event when add button is selected
		 */
		private void buttonAddSelected() {
			String application = comboApplication.getText();
			String field = comboFieldName.getText();
			String joinDefinition = textJoinDefinition.getText();

			if (!isValidDefinition(application, field)) {
				// set error message
				setMessage("Invalid definition.", IMessageProvider.ERROR);
				return;
			}
			// build the join definition
			joinDefinition = joinDefinition + JOIN_DEFINITION_DELIMITER
					+ application + JOIN_DEFINITION_DELIMITER + field;

			// set the value
			textJoinDefinition.setText(joinDefinition);

			// override the existing field type and change the type to the
			// recently added record(which should be the data type for this join
			// definition)
			selectedField.setFieldType(getFieldType(application, field));

			// clear the application and field name combo value
			comboApplication.setText("");
			comboFieldName.setText("");

			// set the button accessibility
			setButtonAccessibility();

			// set return code to dialog box
			setReturnCode(IDialogConstants.NO_ID);
		}

		/**
		 * Helps to perform the event when finish button is selected
		 */
		private void buttonFinishSelected() {
			if (!isValidDefinition()) {
				// set error message
				setMessage("Invalid definition.", IMessageProvider.ERROR);
				return;
			}
			// design the join field with the definition
			joinField = new ApplicationVersionField();
			// set the display name
			joinField.setDisplayName(textDisplayName.getText());
			// set the field definition
			joinField.setFieldName(textJoinDefinition.getText());
			// set the field type
			// storing the field type as the type which the last field has.
			joinField.setFieldType(selectedField.getFieldType());

			// set the return code to Ok.
			setReturnCode(OK);
			// close the dialog only if the designed join definition is valid.
			close();
		}

		/**
		 * Helps to perform the event when undo button is selected
		 */
		private void buttonUndoSelected() {
			if (!canPerformUndo()) {
				// set error message
				setMessage("Cannot undo.", IMessageProvider.ERROR);
				// disable the undo button
				buttonUndo.setEnabled(false);
				return;
			}

			// split the join definition using delimiter
			String[] applicationAndField = textJoinDefinition.getText().split(
					JOIN_DEFINITION_DELIMITER);
			// array length
			int length = applicationAndField.length;

			String undoDefinition = "";

			// iterate the array for build the definition. Do not include the
			// last two elements of array since its been added newly and which
			// is what need to be undo.
			for (int i = 0; i < length - 2; i++) {
				undoDefinition += applicationAndField[i]
						+ JOIN_DEFINITION_DELIMITER;
			}
			// remove the last delimiter
			undoDefinition = undoDefinition.substring(0,
					undoDefinition.length() - 1);

			textJoinDefinition.setText(undoDefinition);
			// set return code to dialog box
			setReturnCode(IDialogConstants.NO_ID);
			// set the accessibility of buttons
			setButtonAccessibility();
		}

		/**
		 * Decide whether the designed definition can be undo or not. It can be
		 * undo only if the definition has more than one(the initial definition)
		 * 
		 * @return true if the user can undo, false otherwise.
		 */
		private boolean canPerformUndo() {
			// split the join definition using delimiter
			String[] applicationAndField = textJoinDefinition.getText().split(
					JOIN_DEFINITION_DELIMITER);
			// array length
			int length = applicationAndField.length;

			// length can be zero in case of exception, and if the length is 2
			// we are not supposed to undo it since its the starting point of
			// definition.
			if (length == 0 || length == 2) {
				return false;
			}
			return true;
		}

		/**
		 * Helps to perform the event when field name combo gained focus.
		 */
		private void comboFieldNameGainedFocus() {
			// perform the same operation when an item selected from application
			// combo
			applicationComboSelected();
		}

		/**
		 * Helps to perform the event when field name combo is selected
		 */
		private void fieldNameComboSelected() {
			// set the accessibility of add button
			buttonAdd.setEnabled(isValidField(comboFieldName.getText(),
					comboApplication.getText()));
		}

		public void focusGained(FocusEvent event) {
			Object source = event.getSource();
			if (source.equals(comboFieldName)) {
				comboFieldNameGainedFocus();
			}
		}

		public void focusLost(FocusEvent event) {
			// implement only when requires
		}

		/**
		 * Helps to get the field type equivalent to given field name in the
		 * given application.
		 * 
		 * @param application
		 *            - selected T24 application
		 * @param fieldName
		 *            - T24 field name
		 * @return corresponding field type if found, empty otherwise
		 */
		private String getFieldType(String application, String fieldName) {
			List<Field> availableFields = connectionController
					.getApplicationFields(currentProject, application);
			for (Field field : availableFields) {
				if (fieldName.equalsIgnoreCase(field.getFieldName())) {
					return field.getFieldType();
				}
			}
			return "";
		}

		/**
		 * Check whether the given display name is already in enriched fields
		 * are not.
		 * 
		 * @param displayName
		 * @return true if duplicate, false otherwise.
		 */
		private boolean isDuplicateDisplayName(String displayName) {
			if (enrichedFields == null || enrichedFields.isEmpty()) {
				return false;
			}
			for (Field field : enrichedFields) {
				// ignoring case since we are not case sensitive
				if (field.getDisplayName().equalsIgnoreCase(displayName)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Decide whether the designed join definition is valid or not.
		 * 
		 * @return true only if everything is fine (the display name should be
		 *         valid and join definition should be valid), false otherwise.
		 */
		private boolean isValidDefinition() {
			if (getErrorMessage() != null
					|| textDisplayName.getText().length() == 0) {
				// if error message is available which means that there is
				// something
				// wrong with display name, else the display name can be empty.
				return false;
			}
			if (!canPerformUndo()) {
				// if we cannot perform undo which is actually means that
				// nothing has been designed and the definition has the initial
				// field only.
				return false;
			}
			// hopefully everything is fine.
			return true;
		}

		/**
		 * Helps to validate the given application and field as whether it is
		 * valid to add a definition or not.
		 * 
		 * @param application
		 * @param field
		 * @return true if valid, false otherwise.
		 */
		private boolean isValidDefinition(String application, String field) {

			if (application == null || field == null) {
				return false;
			}

			if (application.length() == 0 || field.length() == 0) {
				return false;
			}

			return connectionController.getApplicationList(currentProject)
					.contains(application) && isValidField(field, application);
		}

		/**
		 * Decide whether the given field is associated with given application
		 * or not.
		 * 
		 * @param field
		 * @param application
		 * @return true if valid, false otherwise.
		 */
		private boolean isValidField(String field, String application) {
			if (field == null || field.length() == 0) {
				return false;
			}

			List<Field> availableFields = connectionController
					.getApplicationFields(currentProject, application);
			if (availableFields == null || availableFields.isEmpty()) {
				return false;
			}
			for (Field singleField : availableFields) {
				// return true only if the field name is match.
				if (field.equalsIgnoreCase(singleField.getFieldName())) {
					return true;
				}
			}
			return false;
		}

		public void keyPressed(KeyEvent event) {
			// do nothing
		}

		public void keyReleased(KeyEvent event) {
			Object source = event.getSource();
			if (source.equals(textDisplayName)) {
				textDisplayNameKeyReleased();
			} else if (source.equals(comboApplication)) {
				// as of now doing only set the accessibility of buttons. This
				// should be moved to a method if more work done here in future.
				buttonAdd.setEnabled(isValidDefinition(
						comboApplication.getText(), comboFieldName.getText()));
			} else if (source.equals(comboFieldName)) {
				// as of now doing only set the accessibility of buttons. This
				// should be moved to a method if more work done here in future.
				buttonAdd.setEnabled(isValidDefinition(
						comboApplication.getText(), comboFieldName.getText()));
			}
		}

		/**
		 * Helps to set the accessibility of the buttons.
		 */
		private void setButtonAccessibility() {
			buttonAdd.setEnabled(isValidDefinition(comboApplication.getText(),
					comboFieldName.getText()));
			buttonUndo.setEnabled(canPerformUndo());
			buttonFinish.setEnabled(isValidDefinition());
		}

		/**
		 * Helps to perform the event when key pressed on display name text box
		 */
		private void textDisplayNameKeyReleased() {
			String displayName = textDisplayName.getText();

			if (displayName == null || displayName.length() == 0) {
				setMessage("Display name cannot be empty.",
						IMessageProvider.ERROR);
				// set the finish button accessibility
				buttonFinish.setEnabled(false);
			} else if (Utils.containsInvalidChar(displayName)) {
				setMessage("Display name contains invalid character.",
						IMessageProvider.ERROR);
				// set the finish button accessibility
				buttonFinish.setEnabled(false);
			} else if (isDuplicateDisplayName(displayName)) {
				setMessage("Duplicate display name.", IMessageProvider.ERROR);
				// set the finish button accessibility
				buttonFinish.setEnabled(false);
			} else {
				setMessage(null);
				// set the finish button accessibility
				buttonFinish.setEnabled(canPerformUndo());
			}
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			Object source = event.getSource();
			if (source.equals(buttonAdd)) {
				buttonAddSelected();
			} else if (source.equals(buttonUndo)) {
				buttonUndoSelected();
			} else if (source.equals(buttonFinish)) {
				buttonFinishSelected();
			} else if (source.equals(comboApplication)) {
				applicationComboSelected();
			} else if (source.equals(comboFieldName)) {
				fieldNameComboSelected();
			}
		}

	}

	/**
	 * display name text box
	 */
	private Text textDisplayName;
	/**
	 * join definition text area
	 */
	private Text textJoinDefinition;
	/**
	 * add button
	 */
	private Button buttonAdd;
	/**
	 * undo button
	 */
	private Button buttonUndo;
	/**
	 * finish button
	 */
	private Button buttonFinish;

	/**
	 * application which is the starting point of join.
	 */
	private final String selectedApplication;

	/**
	 * initial field which has been selected for creating join definition
	 */
	private final Field selectedField;

	/**
	 * connection controller
	 */
	private final ConnectionController connectionController;

	/**
	 * associated integration project
	 */
	private final TWSConsumerProject currentProject;

	/**
	 * listener instance
	 */
	private final JoinDefinitionDialogListener listener;

	/**
	 * Delimiter which has been used for delimiting the application and field.
	 */
	private final String JOIN_DEFINITION_DELIMITER = ">";
	/**
	 * application list combo
	 */
	private Combo comboApplication;
	/**
	 * field list combo
	 */
	private Combo comboFieldName;
	/**
	 * field which contains the join definition
	 */
	private Field joinField;

	/**
	 * list of field which has been already selected for flow enrichments.
	 */
	private final List<Field> enrichedFields;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param selectedApplication
	 *            - starting application.
	 * @param selectedField
	 *            - starting field.
	 * @param connectionController
	 *            - T24 connection controller.
	 * @param currentProject
	 *            - associated Integration project
	 * @param enrichedFields
	 *            - fields selected for flow enrichments
	 */
	public JoinDefinitionDialog(Shell parentShell, String selectedApplication,
			Field selectedField, ConnectionController connectionController,
			TWSConsumerProject currentProject, List<Field> enrichedFields) {
		super(parentShell);
		setHelpAvailable(false);
		this.selectedApplication = selectedApplication;
		this.selectedField = selectedField;
		this.connectionController = connectionController;
		this.currentProject = currentProject;
		this.enrichedFields = enrichedFields;
		// initialize the listener
		listener = new JoinDefinitionDialogListener();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Integration Tooling");
		newShell.setImage(IntegrationToolingActivator.getImage("icons/integration.gif"));
	}

	/**
	 * Helps to construct the initial definition of the Join field.
	 * 
	 * 
	 * @return starting point of join definition.
	 */
	private String constructInitialDefinition() {
		String rawFormatOfField = selectedField.getFieldName();
		String startingApplication = selectedApplication;
		String fieldName = "";
		// AA type of application/version returns a field name as
		// <APPLICATION_NAME>:<FIELD_NAME> (AA.ARRANGEMENT.ACTIVITY:@ID), where
		// the tooling needs FIELD_NAME (@ID) alone. On another scenario of AA,
		// it have a property field which actually holds the application and its
		// field (AA.ARR.CUSTOMER:PRIMARY.OWNER) where the root application
		// holds this property field is AA.ARRANGEMENT.ACTIVITY. In this
		// scenario the application itself needs to be changed from root
		// application to property field application (using the above said
		// example, the application should be AA.ARR.CUSTOMER should not be
		// AA.ARRANGEMENT.ACTIVITY)
		if (rawFormatOfField.contains(":")) {
			String[] applicationAndFieldName = rawFormatOfField.split(":");
			// what if the raw format of field name came with 2 colons(:)?
			// Hopefully T24 will return a string with only one colon.

			if (!applicationAndFieldName[0]
					.equalsIgnoreCase(startingApplication)) {
				// if the field name doesn't in the format of
				// ApplicationName:Filed.Name, then it's an AA property field.
				// Where we should take the starting application from property
				// field.
				startingApplication = applicationAndFieldName[0];
			}
			fieldName = applicationAndFieldName[1];
		} else {
			// return the raw format as it is since it's the actual field name.
			fieldName = rawFormatOfField;
		}
		return startingApplication + JOIN_DEFINITION_DELIMITER + fieldName;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create add button
		buttonAdd = createButton(parent, IDialogConstants.NO_ID, "Add", false);
		// create undo button
		buttonUndo = createButton(parent, IDialogConstants.NO_ID, "Undo", false);
		// create finish button
		buttonFinish = createButton(parent, IDialogConstants.FINISH_ID,
				IDialogConstants.FINISH_LABEL, true);
		// create cancel button
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		// set the button accessibility
		buttonAdd.setEnabled(listener.isValidDefinition(
				comboApplication.getText(), comboFieldName.getText()));
		buttonUndo.setEnabled(false);
		buttonFinish.setEnabled(false);

		// add listeners to the buttons.
		buttonAdd.addSelectionListener(listener);
		buttonUndo.addSelectionListener(listener);
		buttonFinish.addSelectionListener(listener);

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Create Join Definition");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label labelDisplayName = new Label(container, SWT.NONE);
		labelDisplayName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, true, 1, 1));
		labelDisplayName.setText("Display Name : ");

		textDisplayName = new Text(container, SWT.BORDER);
		textDisplayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		// add listener to display name text box
		textDisplayName.addKeyListener(listener);

		Label labelApplication = new Label(container, SWT.NONE);
		labelApplication.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, true, 1, 1));
		labelApplication.setText("Application : ");

		comboApplication = new Combo(container, SWT.NONE);
		comboApplication.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		// set items to the application combo

		// Controller is providing list of string which
		// could convert to object array, but the combo box requires string
		// array!
		List<String> availableApplicationList = connectionController
				.getApplicationList(currentProject);
		if (availableApplicationList != null
				&& !availableApplicationList.isEmpty()) {
			comboApplication.setItems(availableApplicationList
					.toArray(new String[0]));
		}

		// add listener to application combo
		comboApplication.addSelectionListener(listener);
		comboApplication.addKeyListener(listener);

		Label labelFieldName = new Label(container, SWT.NONE);
		labelFieldName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				true, 1, 1));
		labelFieldName.setText("Field Name : ");

		comboFieldName = new Combo(container, SWT.NONE);
		comboFieldName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		// add listener to field name combo
		comboFieldName.addSelectionListener(listener);
		comboFieldName.addFocusListener(listener);

		Label labelJoinDefinition = new Label(container, SWT.NONE);
		labelJoinDefinition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, true, 1, 1));
		labelJoinDefinition.setText("Join Definition : ");

		textJoinDefinition = new Text(container, SWT.BORDER | SWT.READ_ONLY
				| SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		GridData gd_textJoinDefinition = new GridData(SWT.FILL, SWT.CENTER,
				true, true, 1, 1);
		gd_textJoinDefinition.heightHint = 79;
		textJoinDefinition.setLayoutData(gd_textJoinDefinition);

		// set the initial value to the join definition dialog
		String initialDefinition = constructInitialDefinition();
		textJoinDefinition.setText(initialDefinition);

		return area;
	}

	/**
	 * Helps to get the field which contains the designed join definition.
	 * 
	 * @return instance of {@link Field} which has the join definition in
	 *         {@link Field#getFieldName()}
	 */
	public Field getDesignedJoinDefinitionField() {
		if (joinField == null) {
			// happens only when exceptional scenario.
			return selectedField;
		}
		return joinField;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(550, 400);
	}

}
