package com.odcgroup.integrationfwk.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
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
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Responsible for providing the ui contents of property field designer dialog
 * which have the ability to create a Field as instance of {@link Field}.
 * 
 * @author sbharathraja
 * 
 */
public class PropertyFieldDesignerDialog extends TitleAreaDialog {

	/**
	 * Responsible for listening the ui components of
	 * {@link PropertyFieldDesignerDialog} and fire the event whenever it
	 * requires.
	 * 
	 * @author sbharathraja
	 * 
	 */
	private class PropertyFieldDesignerDialgListener extends SelectionAdapter
			implements KeyListener {

		/**
		 * Helps to perform the event when a typed key released from display
		 * name text box
		 */
		private void displayNameTextKeyReleased() {
			String displayName = textDisplayName.getText();

			if (displayName == null || displayName.length() == 0) {
				setMessage("Display name cannot be empty.",
						IMessageProvider.ERROR);
				// disable the ok button
				getOkButton().setEnabled(false);
			} else if (Utils.containsInvalidChar(displayName)) {
				setMessage("Display name contains invalid character.",
						IMessageProvider.ERROR);
				// disable the ok button
				getOkButton().setEnabled(false);
			} else if (isDuplicateDisplayName(displayName)) {
				setMessage("Duplicate display name.", IMessageProvider.ERROR);
				// disable the ok button
				getOkButton().setEnabled(false);
			} else {
				setMessage(null);
				// enable the ok button, if everything is fine
				getOkButton().setEnabled(isValidFieldDesign());
			}

		}

		/**
		 * Helps to perform the event when a typed key released from field
		 * definition text box
		 */
		private void fieldDefinitionTextKeyReleased() {
			String fieldDefinition = textFieldDefinition.getText().trim();

			if (fieldDefinition == null || fieldDefinition.length() == 0) {
				setMessage("Field Definition cannot be empty.",
						IMessageProvider.ERROR);
				// disable the ok button
				getOkButton().setEnabled(false);
			}
			// TODO: This contains check should be replaced by regex pattern if
			// in case of more validation requires on this, in future.
			else if (fieldDefinition.contains("\"")) {
				setMessage("Field Definition contains invalid character.",
						IMessageProvider.ERROR);
				// disable the ok button
				getOkButton().setEnabled(false);
			} else {
				setMessage(null);
				// enable the ok button, if everything is fine
				getOkButton().setEnabled(isValidFieldDesign());
			}

		}

		/**
		 * Decide whether the given display name is duplicate or not.
		 * 
		 * @param displayName
		 * @return true if duplicate, false otherwise.
		 */
		private boolean isDuplicateDisplayName(String displayName) {
			if (enrichedFields == null) {
				// happens only when exceptional scenario
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
		 * Decide whether the designed field from this dialog is valid or not.
		 * 
		 * @return true if valid, false otherwise.
		 */
		private boolean isValidFieldDesign() {
			String displayName = textDisplayName.getText().trim();
			String fieldDefinition = textFieldDefinition.getText().trim();
			String fieldType = comboFieldType.getText();

			if (getMessage() != null && getMessage().length() != 0) {
				// some error message is in the dialog which means the designed
				// definition is not valid
				return false;
			}

			// this check is requires if a text box never touched for an entry!
			if (displayName.length() == 0 || fieldDefinition.length() == 0
					|| fieldType.length() == 0) {
				return false;
			}

			// checking the duplicate display name
			if (isDuplicateDisplayName(displayName)) {
				setMessage("Duplicate display name.", IMessageProvider.ERROR);
				return false;
			}
			// hopefully everything is fine...
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public void keyPressed(KeyEvent event) {
			// implement only when requires

		}

		/**
		 * {@inheritDoc}
		 */
		public void keyReleased(KeyEvent event) {
			Object source = event.getSource();
			if (source.equals(textDisplayName)) {
				displayNameTextKeyReleased();
			} else if (source.equals(textFieldDefinition)) {
				fieldDefinitionTextKeyReleased();
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			super.widgetDefaultSelected(e);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(SelectionEvent event) {
			Object source = event.getSource();
			if (source.equals(comboFieldType)) {
				// as of now set the accessibility of ok button only. If in case
				// more need to be done here in future, extract this into
				// separate method.
				getOkButton().setEnabled(isValidFieldDesign());
			}
		}

	}

	/**
	 * Display name text box
	 */
	private Text textDisplayName;

	/**
	 * Field definition text box
	 */
	private Text textFieldDefinition;

	/**
	 * Field type combo box
	 */
	private Combo comboFieldType;

	/**
	 * list of fields which has been already selected for flow enrichments
	 */
	private final List<Field> enrichedFields;

	/**
	 * instance of listener
	 */
	private final PropertyFieldDesignerDialgListener listener;

	/**
	 * holds the property field designed using this dialog
	 */
	private Field designedPropertyField;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param enrichedFields
	 *            - list of field already selected for flow enrichments
	 */
	public PropertyFieldDesignerDialog(Shell parentShell,
			List<Field> enrichedFields) {
		super(parentShell);
		setHelpAvailable(false);
		this.enrichedFields = enrichedFields;

		// initialize the listener
		listener = new PropertyFieldDesignerDialgListener();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Integration Tooling");
		newShell.setImage(IntegrationToolingActivator.getImage("icons/integration.gif"));
	}

	/**
	 * Helps to construct the property field using the value captured from this
	 * dialog.
	 * 
	 * @return instance of {@link Field}
	 */
	private Field constructPropertyField() {
		String displayName = textDisplayName.getText();
		String fieldDefinition = textFieldDefinition.getText();
		String fieldType = comboFieldType.getText();

		// XXX: Important Note : Constructing the property field as normal
		// application/version field. Redesign is require if the property field
		// needs to be handle differently
		Field field = new ApplicationVersionField();
		field.setDisplayName(displayName);
		field.setFieldName(Utils.convertToDoubleQuotedString(fieldDefinition));
		field.setFieldType(fieldType);

		return field;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		// disable the ok button initially, enabled only when there is a valid
		// field design
		getOkButton().setEnabled(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Create Custom Field");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label labelDisplayName = new Label(container, SWT.NONE);
		labelDisplayName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, true, 1, 1));
		labelDisplayName.setText("Display Name : ");

		// display name text box creation
		textDisplayName = new Text(container, SWT.BORDER);
		textDisplayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		// add the listener to display name text box
		textDisplayName.addKeyListener(listener);

		Label lblFieldDefinition = new Label(container, SWT.NONE);
		lblFieldDefinition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, true, 1, 1));
		lblFieldDefinition.setText("Field Definition : ");

		// field definition text box creation
		textFieldDefinition = new Text(container, SWT.BORDER);
		textFieldDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, true, 1, 1));

		// add the listener to the field definition text box
		textFieldDefinition.addKeyListener(listener);

		Label lblFieldType = new Label(container, SWT.NONE);
		lblFieldType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				true, 1, 1));
		lblFieldType.setText("Field Type : ");

		// field type combo box creation
		comboFieldType = new Combo(container, SWT.NONE | SWT.READ_ONLY);
		comboFieldType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		// set the items to the combo box
		comboFieldType.setItems(fieldTypes());
		// add listner to combo box
		comboFieldType.addSelectionListener(listener);

		return area;
	}

	/**
	 * 
	 * @return array of field types(The T24 Field Types)
	 */
	// TODO: convert this to enum in case of more data types need to be handle
	// in future.
	private String[] fieldTypes() {
		String[] fieldTypes = { "string", "date", "decimal" };
		return fieldTypes;
	}

	/**
	 * Helps to get the property field designed using
	 * {@link PropertyFieldDesignerDialog}
	 * 
	 * @return instance of {@link Field}
	 */
	public Field getDesignedPropertyField() {
		return designedPropertyField;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	/**
	 * Helps to get the ok button presents in this dialog
	 * 
	 * @return instance of {@link Button}
	 */
	private Button getOkButton() {
		return getButton(OK);
	}

	/**
	 * Helps to perform the event when ok button is pressed.
	 */
	@Override
	protected void okPressed() {
		// update the respective instance with constructed field entries.
		designedPropertyField = constructPropertyField();
		// set the return code to Ok.
		setReturnCode(OK);
		// close the dialog
		close();
	}

}
