package com.odcgroup.integrationfwk.ui.editors.listeners;

import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;

import com.odcgroup.integrationfwk.ui.controller.ConnectionController;
import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.editors.OverridesPage;
import com.odcgroup.integrationfwk.ui.model.Event;

/**
 * Class which helps to listen the ui components of Overrides page and perform
 * the action whenever it requires.
 * 
 * @author sbharathraja
 * 
 */
public class OverridesPageListener extends AbstractEventsEditorListener {
	/** instance of {@link OverridesPage} */
	private final OverridesPage page;
	/** instance of {@link ConnectionController} */
	private final ConnectionController connectionCntrl;

	/**
	 * constructor of {@link OverridesPageListener}
	 * 
	 * @param page
	 *            - instance of {@link OverridesPage}
	 */
	public OverridesPageListener(OverridesPage page) {
		this.page = page;
		connectionCntrl = getConnectionController(getEventEditor());
	}

	/**
	 * Method which helps to perform the action when add button selected.
	 */
	private void addButtonSelected() {
		IStructuredSelection selection = (IStructuredSelection) page
				.getSourceListviewer().getSelection();
		Event consumerEvent = getEvent(getEventEditor());
		boolean isEligible = consumerEvent.getOverrides().addOverride(
				(String) selection.getFirstElement());
		if ((selection != null) && isEligible) {
			page.getDestinationListViewer().add(selection.getFirstElement());
			updateEvent(getEventEditor(), consumerEvent);
			setEditorModified(getEventEditor());
		}
	}

	/**
	 * Method which helps to perform the action when an item of destination list
	 * viewer double clicked.
	 * 
	 * @param event
	 *            - instance of {@link DoubleClickEvent}
	 */
	private void destinationListViewerDoubleClicked(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.getOverrides().removeOverride(
				(String) selection.getFirstElement());
		updateEvent(getEventEditor(), consumerEvent);
		page.getDestinationListViewer().remove(selection.getFirstElement());
		setEditorModified(getEventEditor());
	}

	/**
	 * Method which helps to perform the action when selection changed on
	 * destination list viewer.
	 */
	private void destinationListViewerSelectionChanged() {
		page.getRemoveButton().setEnabled(true);
	}

	public void doubleClick(DoubleClickEvent doubleClickEvent) {
		Object source = doubleClickEvent.getSource();
		if (source.equals(page.getSourceListviewer())) {
			sourceListViewerDoubleClicked(doubleClickEvent);
		} else if (source.equals(page.getDestinationListViewer())) {
			destinationListViewerDoubleClicked(doubleClickEvent);
		}
	}

	public void focusGained(FocusEvent focusEvent) {
		// TODO Auto-generated method stub
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
	 * Method which helps to get the list of overrides from T24.
	 * 
	 * @return list of string representation of overrides.
	 */
	public List<String> getOverrides() {
		return connectionCntrl.getOverrideList(getEventEditor()
				.getCurrentProject());
	}

	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
	}

	public void modifyText(ModifyEvent modifyEvent) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method which helps to perform the action when remove button selected.
	 */
	private void removeButtonSelected() {
		IStructuredSelection selection = (IStructuredSelection) page
				.getDestinationListViewer().getSelection();
		if (selection == null || selection.getFirstElement() == null) {
			return;
		}
		Event consumerEvent = getEvent(getEventEditor());
		consumerEvent.getOverrides().removeOverride(
				(String) selection.getFirstElement());
		updateEvent(getEventEditor(), consumerEvent);
		page.getDestinationListViewer().remove(selection.getFirstElement());
		setEditorModified(getEventEditor());
		page.getRemoveButton().setEnabled(false);
	}

	public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
		Object source = selectionChangedEvent.getSource();
		if (source.equals(page.getSourceListviewer())) {
			sourceListViewerSelectionChanged();
		} else if (source.equals(page.getDestinationListViewer())) {
			destinationListViewerSelectionChanged();
		}
	}

	/**
	 * Method which helps to perform the action when an items in source liste
	 * viewer double clicked.
	 * 
	 * @param event
	 *            - instance of {@link DoubleClickEvent}
	 */
	private void sourceListViewerDoubleClicked(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		// if the new one is added
		Event consumerEvent = getEvent(getEventEditor());
		boolean isEligible = consumerEvent.getOverrides().addOverride(
				(String) selection.getFirstElement());
		if (isEligible) {
			page.getDestinationListViewer().add(selection.getFirstElement());
			updateEvent(getEventEditor(), consumerEvent);
			setEditorModified(getEventEditor());
		}
	}

	/**
	 * Method which helps to perform the action when selection changed on source
	 * list viewer.
	 */
	private void sourceListViewerSelectionChanged() {
		page.getAddButton().setEnabled(true);
	}

	public void widgetDefaultSelected(SelectionEvent selectionEvent) {
		// TODO Auto-generated method stub
	}

	public void widgetSelected(SelectionEvent selectionEvent) {
		Object source = selectionEvent.getSource();
		if (source.equals(page.getAddButton())) {
			addButtonSelected();
		} else if (source.equals(page.getRemoveButton())) {
			removeButtonSelected();
		}
	}

}
