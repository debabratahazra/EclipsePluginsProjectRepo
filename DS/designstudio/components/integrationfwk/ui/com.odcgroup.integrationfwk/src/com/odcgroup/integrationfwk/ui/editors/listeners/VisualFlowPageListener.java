package com.odcgroup.integrationfwk.ui.editors.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.odcgroup.integrationfwk.ui.controller.ConnectionController;
import com.odcgroup.integrationfwk.ui.dialog.JoinDefinitionDialog;
import com.odcgroup.integrationfwk.ui.dialog.PropertyFieldDesignerDialog;
import com.odcgroup.integrationfwk.ui.editors.FlowsEditor;
import com.odcgroup.integrationfwk.ui.editors.VisualFlowPage;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.FlowAttribute;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Class which helps to listen the ui components of Visual flow page and perform
 * the action whenever it required.
 * 
 * @author sbharathraja
 * 
 */
public class VisualFlowPageListener extends AbstractFlowsEditorListener {

	/** instance of {@link VisualFlowPage} */
	private final VisualFlowPage page;

	/** instance of {@link ConnectionController} */
	private final ConnectionController connectionCntrl;

	/**
	 * constructor of {@link VisualFlowPageListener}
	 * 
	 * @param page
	 *            - instance of {@link VisualFlowPage}
	 */
	public VisualFlowPageListener(VisualFlowPage page) {
		this.page = page;
		connectionCntrl = ConnectionController
				.createConnectionController(getFlowEditor().getCurrentProject());
	}

	/**
	 * Method which helps to add a single given field into flow enrichments
	 * which has been selected from available field list.
	 * 
	 * @param field
	 *            - instance of {@link Field}
	 */
	private void addField(Field field) {
		Flow flowInstance = getFlow(getFlowEditor());
		if (field.getDisplayName() == null
				|| field.getDisplayName().length() == 0) {
			// set the field name only if it is empty priorly
			field.setDisplayName(computeDisplayName(flowInstance.getFields()
					.getInputFields(), field.getFieldName()));
		}
		boolean isAddedNewly = flowInstance.getFields().addField(field);
		if (!isAddedNewly) {
			return;
		}
		page.getSelectedFlowsTable().add(field);
		field.setSelected(true);
		updateAccessibilityOfField(field);
		updateFlow(getFlowEditor(), flowInstance);
	}

	/**
	 * Method which helps to perform the action when text modified on
	 * application/version/component service combo box.
	 * <p>
	 * please note that all the three is same combo box.
	 * 
	 * @param event
	 *            - instance of {@link ModifyEvent}
	 */
	private void availableApplicationComboTextModified(ModifyEvent event) {
		Flow flow = getFlow(getFlowEditor());
		String application = ((Combo) event.widget).getText();
		if (flow.getBaseEvent() == null
				|| flow.getBaseEvent().equalsIgnoreCase(application)) {
			return;
		}
		flow.setBaseEvent(application);
		updateFlow(getFlowEditor(), flow);
		List<Field> data = getFieldsForList();
		if (data != null) {
			page.getListTableViewer().setInput(data);
		} else {
			page.getListTableViewer().setInput("");
		}
		setEditorModified(getFlowEditor());
	}

	/**
	 * Method which helps to perform the action when add all button is selected.
	 * 
	 */
	private void buttonAddAllSelected() {
		final List<Field> allFields = getFieldsForList();
		// disable add button
		page.getAddButton().setEnabled(false);
		// disable add all button
		page.getAddAllButton().setEnabled(false);
		for (Field field : allFields) {
			addField(field);
		}
		// make the editor dirty
		setEditorModified(getFlowEditor());
	}

	/**
	 * Helps to perform the event when Add Join button is clicked.
	 */
	private void buttonAddJoinSelected() {
		// get the selected field
		IStructuredSelection selection = (IStructuredSelection) page
				.getListTableViewer().getSelection();
		if (selection == null || selection.isEmpty()) {
			return;
		}
		// disable the button and eanble only when something has been
		// selected again
		page.getAddJoinButton().setEnabled(false);
		if (selection.size() == 1) {
			Field field = (Field) selection.getFirstElement();
			field = constructJoinField(field);
			if (field != null) {
				addField(field);
				setEditorModified(getFlowEditor());
			}
		} else {
			// more than one field selected!!!
			return;
		}

	}

	/**
	 * Helps to perform the action when add property field button of visual flow
	 * page is been selected.
	 */
	private void buttonAddPropertyFieldButtonSelected() {
		PropertyFieldDesignerDialog dialog = new PropertyFieldDesignerDialog(
				page.getSite().getShell(), getFlow(getFlowEditor()).getFields()
						.getInputFields());
		if (dialog.open() == Window.OK) {
			Field designedField = dialog.getDesignedPropertyField();
			if (designedField == null) {
				return;
			}
			addField(designedField);
			setEditorModified(getFlowEditor());
		}
	}

	/**
	 * Method which helps to perform the action when add button clicked.
	 * 
	 * @param event
	 */
	private void buttonAddSelected(SelectionEvent event) {
		IStructuredSelection selection = (IStructuredSelection) page
				.getListTableViewer().getSelection();
		if (selection == null || selection.isEmpty()) {
			return;
		}
		// disable the add button
		page.getAddButton().setEnabled(false);
		if (selection.size() == 1) {
			Field field = (Field) selection.getFirstElement();
			addField(field);
		} else {
			// more than one field has been selected via UI using ctrl/Shift
			// click
			for (Object selected : selection.toArray()) {
				Field field = (Field) selected;
				addField(field);
			}
		}
		setEditorModified(getFlowEditor());
		if (isAllFieldsSelected()) {
			page.getAddAllButton().setEnabled(false);
		}
	}

	/**
	 * Method which helps to perform the action when the move down button is
	 * selected.
	 */
	private void buttonMoveDownSelected() {
		IStructuredSelection selection = (IStructuredSelection) page
				.getSelectedFlowsTable().getSelection();

		if (selection == null || selection.isEmpty()) {
			return;
		}
		if (selection.size() == 1) {
			Field field = (Field) selection.getFirstElement();
			if (moveDownField(field)) {
				setEditorModified(getFlowEditor());
			}
		} else {
			// more than one field has been selected via UI using ctrl/Shift
			// click
			for (Object selected : selection.toArray()) {
				Field field = (Field) selected;
				moveDownField(field);
			}
			setEditorModified(getFlowEditor());
		}
		page.getSelectedFlowsTable().refresh();
	}

	/**
	 * Method which helps to perform the action when the move up button is
	 * selected.
	 */
	private void buttonMoveUpSelected() {
		IStructuredSelection selection = (IStructuredSelection) page
				.getSelectedFlowsTable().getSelection();

		if (selection == null || selection.isEmpty()) {
			return;
		}
		if (selection.size() == 1) {
			Field field = (Field) selection.getFirstElement();
			if (moveUpField(field)) {
				setEditorModified(getFlowEditor());
			}

		} else {
			// more than one field has been selected via UI using ctrl/Shift
			// click
			for (Object selected : selection.toArray()) {
				Field field = (Field) selected;
				moveUpField(field);
			}
			setEditorModified(getFlowEditor());
		}
		page.getSelectedFlowsTable().refresh();
	}

	/**
	 * Method which helps to perform the action when remove button clicked.
	 */
	private void buttonRemoveSelected() {
		IStructuredSelection selection = (IStructuredSelection) page
				.getSelectedFlowsTable().getSelection();

		if (selection == null || selection.isEmpty()) {
			return;
		}
		if (selection.size() == 1) {
			Field field = (Field) selection.getFirstElement();
			removeField(field);
		} else {
			// more than one field has been selected via UI using ctrl/Shift
			// click
			for (Object selected : selection.toArray()) {
				Field field = (Field) selected;
				removeField(field);
			}
		}
		setEditorModified(getFlowEditor());
		page.getAddAllButton().setEnabled(false);
	}

	/**
	 * Helps to compute the display name for flow enrichment.
	 * 
	 * @param availableFields
	 *            - list of field in flow enrichment.
	 * @param value
	 *            - field name
	 * @return display name which is valid for schema
	 */
	private String computeDisplayName(List<Field> availableFields, String value) {
		String schemaName = Utils.getSchemaName(value);
		// number which we are going to append in the last digit of display
		// name if the previous calculated display name is duplicate.
		int numberToBeAppend = 1;
		// used for removing the last digit of computed display name after the
		// first calculation
		int loopCount = 0;
		// while looping is not advisable, what would be the better
		// replacement for this scenario???
		while (isDuplicateDisplayName(availableFields, schemaName)) {
			// append the number directly if it doesn't contains any digit in
			// last.
			if (loopCount == 0 && !schemaName.matches("^.*\\d$")) {
				schemaName += numberToBeAppend;
			} else {
				// remove the last digit which is actually a number which may
				// added recently else incoming as it is.
				schemaName = schemaName.substring(0, schemaName.length() - 1);
				// added the increased number.
				schemaName += numberToBeAppend;
			}
			// increase the loop count; so that the forth coming display name
			// will added the number to be append as expected behavior.
			loopCount++;
			// increase the number since the previous appended number has not
			// remove the duplication
			numberToBeAppend++;
		}
		return schemaName;
	}

	/**
	 * Responsible for opening the Join Definition Creation Dialog and construct
	 * the field.
	 * 
	 * @param field
	 *            - initial field
	 * @return null if nothing has been designed, else return the constructed
	 *         field instance.
	 */
	private Field constructJoinField(Field field) {
		JoinDefinitionDialog dialog = new JoinDefinitionDialog(page.getSite()
				.getShell(), Utils.convertVersionToApplication(page
				.getAvailableApplicationCombo().getText()), field,
				connectionCntrl, getFlowEditor().getCurrentProject(), getFlow(
						getFlowEditor()).getFields().getInputFields());
		if (dialog.open() == Window.OK) {
			return dialog.getDesignedJoinDefinitionField();
		}
		return null;
	}

	public void doubleClick(DoubleClickEvent doubleClickEvent) {
		Object source = doubleClickEvent.getSource();
		if (source.equals(page.getListTableViewer())) {
			listTableViewerDoubleClicked(doubleClickEvent);
		} else if (source.equals(page.getSelectedFlowsTable())) {
			selectedFlowsTableDoubleClicked(doubleClickEvent);
		}
	}

	/**
	 * Method which helps to build the component services list based on given
	 * event name.
	 * 
	 * @param baseEvent
	 *            - name of the related event.
	 * @return list of field.
	 */
	private List<Field> getComponentServiceFields(String baseEvent) {
		String[] serviceAndOperation = baseEvent.split(",");
		String service = serviceAndOperation[0];
		String operation = serviceAndOperation[1];
		return connectionCntrl.getParameterFieldList(getFlowEditor()
				.getCurrentProject(), service, operation);
	}

	/**
	 * Method which helps to get the event input for visual flow page based on
	 * the exit point type.
	 * 
	 * @return
	 */
	public List<String> getEventInput() {
		return connectionCntrl.getEventInputList(getFlowEditor()
				.getCurrentProject(), getFlow(getFlowEditor())
				.getExitPointType());
	}

	/**
	 * Method which helps to build the list based on selected
	 * application,version/component service.
	 * 
	 * @return list of field
	 */
	private List<Field> getFieldsForList() {
		String baseEvent = getFlow(getFlowEditor()).getBaseEvent();
		if (baseEvent != null && !"".equals(baseEvent)) {
			if (getFlow(getFlowEditor()).getExitPointType().equals(
					SourceType.COMPONENT_SERVICE)) {
				return getComponentServiceFields(baseEvent);
			}
			return connectionCntrl.getApplicationFields(getFlowEditor()
					.getCurrentProject(), baseEvent);
		}
		return new ArrayList<Field>();
	}

	/**
	 * Get the flows editor where this flow page has been placed.
	 * 
	 * @return instance of {@link FlowsEditor}
	 */
	public FlowsEditor getFlowEditor() {
		return ((FlowsEditor) page.getEditor());
	}

	/**
	 * Helps to determine whether all of the available flow selected for
	 * encrichments or not.
	 * 
	 * @return true if all been selected, false otherwise.
	 */
	public boolean isAllFieldsSelected() {
		// get the all available T24 fields
		List<Field> allT24Fields = getFieldsForList();
		if (allT24Fields == null) {
			return false;
		}
		// get the enriched fields
		List<Field> enrichedFields = getFlow(getFlowEditor()).getFields()
				.getInputFields();
		// if the enriched fields list contains all of the available T24 fields
		// as elements, then we could make sure that all available T24 fields
		// has enriched. Even if the enriched fields contains any Join/Custom
		// type of fields, this check ensures that whether we are really include
		// the all original T24 fields to enrichments or not.
		return enrichedFields.containsAll(allT24Fields);
	}

	/**
	 * Determine about the duplication possibility of given display name.
	 * 
	 * @param availableFields
	 *            - list of field which has been selected for flow enrichments
	 *            already.
	 * @param displayName
	 *            - flow enrichment display name value
	 * @return true if the given display name is duplicate, false otherwise.
	 */
	private boolean isDuplicateDisplayName(List<Field> availableFields,
			String displayName) {
		if (availableFields == null || availableFields.isEmpty()) {
			return false;
		}
		for (Field field : availableFields) {
			// ignoring case since we are not case sensitive
			if (field.getDisplayName().equalsIgnoreCase(displayName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method which helps to perform the action when item of the list table
	 * viewer double clicked.
	 * 
	 * @param event
	 *            - instance of {@link DoubleClickEvent}
	 */
	private void listTableViewerDoubleClicked(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		Field field = (Field) selection.getFirstElement();
		Flow flowInstance = getFlow(getFlowEditor());
		field.setDisplayName(computeDisplayName(flowInstance.getFields()
				.getInputFields(), field.getFieldName()));
		boolean isAddedNewly = flowInstance.getFields().addField(field);
		if (isAddedNewly) {
			page.getSelectedFlowsTable().add(field);
			field.setSelected(true);
			updateAccessibilityOfField(field);
			updateFlow(getFlowEditor(), flowInstance);
			setEditorModified(getFlowEditor());
		}
	}

	/**
	 * Method which helps to perform the action when selection changed on list
	 * table viewer item.
	 * 
	 * @param selectionChangedEvent
	 *            - event instance
	 */
	private void listTableViewerSelectionChanged(
			SelectionChangedEvent selectionChangedEvent) {
		IStructuredSelection selection = (IStructuredSelection) selectionChangedEvent
				.getSelection();
		if (selection == null || selection.isEmpty()) {
			page.getAddButton().setEnabled(false);
			page.getAddJoinButton().setEnabled(false);
			// add all should be enabled only if all fields has not been added
			// to flow enrichment already
			page.getAddAllButton().setEnabled(!isAllFieldsSelected());
			return;
		} else if (selection.size() == 1) {
			// one item is selected
			Field selectedField = (Field) selection.getFirstElement();
			// enable add button only if the selected field has not been added
			// into flow enrichment already.
			page.getAddButton().setEnabled(!selectedField.isSelected());
			// add all should be enabled only if all fields has not been added
			// to flow enrichment already
			page.getAddAllButton().setEnabled(!isAllFieldsSelected());
			// add join button can be enabled even if the selected field already
			// added to flow
			page.getAddJoinButton().setEnabled(true);
		} else {
			// more than one field has been selected via UI using ctrl/Shift
			// click
			// add join cannot happen with more than one selection
			page.getAddJoinButton().setEnabled(false);
			page.getAddAllButton().setEnabled(!isAllFieldsSelected());
			// initially disable the add button
			page.getAddButton().setEnabled(false);
			// looking for the selected field and enable the add button if there
			// is any one of the selected field has not been added into flow
			// already.
			for (Object selected : selection.toArray()) {
				Field field = (Field) selected;
				if (!field.isSelected()) {
					page.getAddButton().setEnabled(true);
					// return immediately after enabling the button to break the
					// loop
					return;
				}
			}
		}

	}

	/**
	 * Helps to load the fields into {@link VisualFlowPage#getListTableViewer()}
	 * . Before to load the data into table viewer, this method would update
	 * it's properties if it is an enriched field already.
	 * 
	 */
	public void loadFields() {
		// get the enriched fields from flow
		List<Field> enrichedFields = getFlow(getFlowEditor()).getFields()
				.getInputFields();

		if (enrichedFields == null) {
			// exceptional scenario
			return;
		}
		// get the list of T24 fields
		List<Field> allFieldsFromT24 = getFieldsForList();
		if (isAllFieldsSelected()) {
			allFieldsFromT24 = markT24FieldAsSelected(allFieldsFromT24);
			// set the updated input to table viewer
			page.getListTableViewer().setInput(allFieldsFromT24);
			return;
		}

		// iterate over the all available T24 fields to update the field which
		// has been selected for enrichments already.
		for (ListIterator<Field> allFieldsFromT24Iterator = allFieldsFromT24
				.listIterator(); allFieldsFromT24Iterator.hasNext();) {
			Field fieldFromT24 = allFieldsFromT24Iterator.next();
			if (enrichedFields.contains(fieldFromT24)) {
				// update the field since it has been enriched already
				fieldFromT24.setSelected(true);
				allFieldsFromT24Iterator.set(fieldFromT24);
			}
		}
		// set the updated input to the table viewer
		page.getListTableViewer().setInput(allFieldsFromT24);
	}

	/**
	 * Helps to mark all of the field available in the given t24 fields as
	 * selected.
	 * 
	 * @param t24Fields
	 *            -
	 * @return updated fields.
	 */
	private List<Field> markT24FieldAsSelected(List<Field> t24Fields) {
		if (t24Fields == null) {
			// exceptional scenario
			return new ArrayList<Field>();
		}
		// iterate over the all available T24 Fields to update it's
		// properties.
		for (ListIterator<Field> allFieldsFromT24Iterator = t24Fields
				.listIterator(); allFieldsFromT24Iterator.hasNext();) {
			Field fieldFromT24 = allFieldsFromT24Iterator.next();
			// update the member
			fieldFromT24.setSelected(true);
			// update the object into list
			allFieldsFromT24Iterator.set(fieldFromT24);
		}
		return t24Fields;
	}

	public void modifyText(ModifyEvent modifyEvent) {
		Object source = modifyEvent.getSource();
		if (source.equals(page.getAvailableApplicationCombo())) {
			availableApplicationComboTextModified(modifyEvent);
		}
	}

	/**
	 * Method which helps to move one position down of the given field.
	 * 
	 * @param field
	 *            - instance of {@link Field}
	 * @return true - if any changes occur in the position, false otherwise.
	 */
	private boolean moveDownField(Field field) {
		Flow flowInstance = getFlow(getFlowEditor());
		List<Field> enrichmentFields = flowInstance.getFields()
				.getInputFields();
		// get the index position of selected field
		int previousPosition = enrichmentFields.indexOf(field);
		// the field cannot be move down, if it placed in last position of the
		// list
		if (previousPosition < 0
				|| previousPosition == enrichmentFields.size() - 1) {
			return false;
		}
		Collections.swap(enrichmentFields, previousPosition,
				previousPosition + 1);
		return true;
	}

	/**
	 * Method which helps to move one position up of the given field
	 * 
	 * @param field
	 *            - instance of {@link Field}
	 * @return true if any position changes occur, false otherwise.
	 */
	private boolean moveUpField(Field field) {
		Flow flowInstance = getFlow(getFlowEditor());
		List<Field> enrichmentFields = flowInstance.getFields()
				.getInputFields();
		// get the index position of selected field
		int previousPosition = enrichmentFields.indexOf(field);
		// the field cannot be move up, if it's index is 0 or less than that
		if (previousPosition <= 0) {
			return false;
		}
		Collections.swap(enrichmentFields, previousPosition,
				previousPosition - 1);
		return true;
	}

	/**
	 * Method which helps to remove a single given field from flow enrichments
	 * table.
	 * 
	 * @param field
	 *            - instance of {@link Field}
	 */
	private void removeField(Field field) {
		page.getSelectedFlowsTable().remove(field);
		field.setSelected(false);
		updateAccessibilityOfField(field);
		Flow flow = getFlow(getFlowEditor());
		flow.getFields().removeField(field);
		updateFlow(getFlowEditor(), flow);
	}

	/**
	 * Method which helps to perform the action when item on selected flows
	 * table double clicked.
	 * 
	 * @param event
	 *            - instance of {@link DoubleClickEvent}
	 */
	private void selectedFlowsTableDoubleClicked(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		Field field = (Field) selection.getFirstElement();
		page.getSelectedFlowsTable().remove(field);
		field.setSelected(false);
		Flow flow = getFlow(getFlowEditor());
		flow.getFields().removeField(field);
		updateFlow(getFlowEditor(), flow);
		updateAccessibilityOfField(field);
		setEditorModified(getFlowEditor());
		page.getAddAllButton().setEnabled(true);
	}

	public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
		Object source = selectionChangedEvent.getSource();
		if (source.equals(page.getListTableViewer())) {
			listTableViewerSelectionChanged(selectionChangedEvent);
		}
	}

	@Override
	public void setError(String errorMessage) {
		page.setError(errorMessage);
	}

	/**
	 * Method to add/remove flow attribute from Flow
	 * 
	 * @param flowAttribute
	 * @param chkBox
	 */
	private void toggleFlowAttributes(FlowAttribute flowAttribute, Button chkBox) {
		Flow flow = getFlow(getFlowEditor());
		if (chkBox.getSelection()) {
			flow.addAttribute(flowAttribute);
		} else {
			flow.removeAttribute(flowAttribute);
		}
		setEditorModified(getFlowEditor());
	}

	/**
	 * Changing the foreground color of incoming field in
	 * {@link #listTableViewer}
	 * 
	 * .
	 */
	private void updateAccessibilityOfField(Field filedToBeUpdated) {
		if (filedToBeUpdated == null) {
			// exceptional scenario
			return;
		}
		if (isAllFieldsSelected()) {
			// handle if the all of the fields has been selected for
			// flow enrichment
			List<Field> allT24Fields = getFieldsForList();
			page.getListTableViewer().setInput(
					markT24FieldAsSelected(allT24Fields));
			return;
		}
		page.getListTableViewer().update(filedToBeUpdated, null);

	}

	public void widgetDefaultSelected(SelectionEvent selectionEvent) {
		// nothing to implement
	}

	public void widgetSelected(SelectionEvent selectionEvent) {
		Object source = selectionEvent.getSource();
		if (source.equals(page.getAddButton())) {
			buttonAddSelected(selectionEvent);
		} else if (source.equals(page.getRemoveButton())) {
			buttonRemoveSelected();
		} else if (source.equals(page.getAddAllButton())) {
			buttonAddAllSelected();
		} else if (source.equals(page.getAddJoinButton())) {
			buttonAddJoinSelected();
		} else if (source.equals(page.getMoveUpButton())) {
			buttonMoveUpSelected();
		} else if (source.equals(page.getMoveDownButton())) {
			buttonMoveDownSelected();
		} else if (source.equals(page.getAddPropertyFieldButton())) {
			buttonAddPropertyFieldButtonSelected();
		} else if (source.equals(page.getChkBoxInclB4Img())) {
			toggleFlowAttributes(FlowAttribute.INCLUDE_BEFORE_IMAGE,
					(Button) selectionEvent.widget);
		} else if (source.equals(page.getChkBoxProcessOnlyUpdated())) {
			toggleFlowAttributes(FlowAttribute.PROCESS_ONLY_UPDATES,
					(Button) selectionEvent.widget);
		} else if (source.equals(page.getEnableDigitalSignature())) {
			toggleFlowAttributes(FlowAttribute.INCLUDE_DIGITAL_SIGNATURE,
					(Button) selectionEvent.widget);
		}
	}
}
