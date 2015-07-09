package com.odcgroup.integrationfwk.ui.editors;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.integrationfwk.ui.editors.listeners.AbstractFlowsEditorListener;
import com.odcgroup.integrationfwk.ui.editors.listeners.VisualFlowPageListener;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Responsible for providing the editing facility to the text cell of Flow Table
 * available in {@link VisualFlowPage}
 * 
 * @author sbharathraja
 * 
 */
public class FlowTableTextCellEditingSupport extends EditingSupport {

	/** cell editor instance */
	private final TextCellEditor textCellEditor;
	/** cell validator */
	private ICellEditorValidator validator;
	/** flag to decide whether the entered text is valid or not */
	private boolean isValidText;
	/** instance of {@link AbstractFlowsEditorListener} */
	private final AbstractFlowsEditorListener editorListener;
	/** instance of flow enrichments table viewer */
	private final TableViewer tableViewer;
	/** display name before edited */
	private String previousDisplayName;

	/**
	 * constructor of {@link FlowTableTextCellEditingSupport}
	 * 
	 * @param tableViewer
	 *            - flow table viewer as instance of {@link TableViewer}
	 * @param editorListener
	 *            - instance of {@link AbstractFlowsEditorListener}
	 */
	public FlowTableTextCellEditingSupport(TableViewer tableViewer,
			AbstractFlowsEditorListener editorListener) {
		super(tableViewer);
		this.tableViewer = tableViewer;
		this.editorListener = editorListener;
		textCellEditor = new TextCellEditor(tableViewer.getTable());
		createValidator();
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof Field) {
			// capturing the edited cell's original display name
			previousDisplayName = ((Field) element).getDisplayName();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Helps to create the cell validator which could validate the text typed in
	 * that cell.
	 */
	private void createValidator() {
		validator = new ICellEditorValidator() {

			public String isValid(Object element) {
				String value = element.toString();
				String errorMessage = null;
				if (value.equals("")) {
					isValidText = false;
					errorMessage = "Display name cannot be empty.";
				} else if (Utils.containsInvalidChar(value)) {
					isValidText = false;
					errorMessage = null;
					// alert the user on invalid text at the moment of the
					// character typed.
					MessageDialog.openError(Display.getCurrent()
							.getActiveShell(),
							"Event Designer - Invalid schema name",
							"Invalid character.");
				} else if (isDuplicateDisplayName(value)) {
					isValidText = false;
					errorMessage = "\"" + value + "\""
							+ " is a duplicate display name.";
				} else {
					isValidText = true;
				}
				return errorMessage;
			}
		};
		textCellEditor.setValidator(validator);
		// adding listener to cell editor
		textCellEditor.addListener(new ICellEditorListener() {

			public void applyEditorValue() {
				editorListener.setError(null);
			}

			public void cancelEditor() {
				editorListener.setError(null);
			}

			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				editorListener.setError(textCellEditor.getErrorMessage());
			}
		});
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if (!(element instanceof Field)) {
			return null;
		}
		String schemaName = ((Field) element).getDisplayName();
		validator.isValid(schemaName);
		return textCellEditor;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof Field) {
			return ((Field) element).getDisplayName();
		}
		return "";
	}

	/**
	 * Helps to decide whether the given value is already presented in display
	 * name of flow enrichments table or not.
	 * 
	 * @param value
	 * @return true if a duplicate value, false otherwise.
	 */
	private boolean isDuplicateDisplayName(String value) {
		// entered value is as same as previous value
		if (previousDisplayName != null
				&& previousDisplayName.equalsIgnoreCase(value)) {
			return false;
		}
		// get the table input
		Object input = tableViewer.getInput();
		// table could be empty
		if (!(input instanceof List<?>) || ((List<?>) input).isEmpty()) {
			return false;
		}
		for (Field field : ((List<Field>) input)) {
			// finding out the duplicate value
			if (field.getDisplayName().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (!(element instanceof Field) || !(value instanceof String)) {
			return;
		}
		if (value != null && isValidText) {
			Field field = (Field) element;
			if (field.getDisplayName().equals(value)) {
				return;
			}
			field.setDisplayName(value.toString());
			getViewer().refresh(element, false);
			editorListener
					.setEditorModified(((VisualFlowPageListener) editorListener)
							.getFlowEditor());
		}
	}
}
