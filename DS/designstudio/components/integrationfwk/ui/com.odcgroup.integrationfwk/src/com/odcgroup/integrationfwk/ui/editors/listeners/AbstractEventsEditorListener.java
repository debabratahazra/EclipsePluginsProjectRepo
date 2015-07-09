package com.odcgroup.integrationfwk.ui.editors.listeners;

import java.util.List;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;

import com.odcgroup.integrationfwk.ui.controller.ConnectionController;
import com.odcgroup.integrationfwk.ui.editors.ConsumerEditorManager;
import com.odcgroup.integrationfwk.ui.editors.EventsEditor;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/**
 * Class which act as a wrapper/contractor between Events Editor and its
 * listeners.
 * 
 * @author sbharathraja
 * 
 */
public abstract class AbstractEventsEditorListener implements
		SelectionListener, ModifyListener, FocusListener, KeyListener,
		IDoubleClickListener, ISelectionChangedListener {

	/** instance of {@link ConnectionController} */
	private ConnectionController connectionController;

	/**
	 * Helps to get the connection controller using the connection details
	 * associated with the project of given editor instance.
	 * 
	 * @param eventsEditor
	 * @return instance of {@link ConnectionController}
	 */
	public ConnectionController getConnectionController(
			EventsEditor eventsEditor) {
		return getConnectionController(eventsEditor.getCurrentProject());
	}

	/**
	 * Helps to get the connection controller using connection details
	 * associated with given project.
	 * 
	 * @param currentProject
	 * @return instance of {@link ConnectionController}
	 */
	public ConnectionController getConnectionController(
			TWSConsumerProject currentProject) {
		if (connectionController == null) {
			connectionController = ConnectionController
					.createConnectionController(currentProject);
		}
		return connectionController;
	}

	/**
	 * get the recent event object from factory of {@link ConsumerEditorManager}
	 * .
	 * 
	 * @param editor
	 *            - instance of {@link EventsEditor}
	 * @return recent event object.
	 */
	public Event getEvent(EventsEditor editor) {
		return ConsumerEditorManager.getInstance().getEvent(
				editor.getProjectName(), editor.getEventName());
	}

	/**
	 * method which helps to get the exit point list from T24.
	 * 
	 * @param connectionCntrl
	 *            - instance of {@link ConnectionController}
	 * @param editor
	 *            - instance of {@link EventsEditor}
	 * @return list of exit point string representation.
	 */
	public List<String> getExitPoints(ConnectionController connectionCntrl,
			EventsEditor editor) {
		return connectionCntrl.getExitPointList(editor.getCurrentProject());
	}

	/**
	 * method which helps to set the dirty property of the editor where this
	 * Exit point page has been placed.
	 */
	public void setEditorModified(EventsEditor editor) {
		// if was not already dirty then modified the editor
		boolean wasDirty = editor.isDirty();
		if (!wasDirty) {
			editor.setPageModified(true);
		}
		// update the source page of Events Editor.
		editor.updateSourcePage();
	}

	/**
	 * Method which helps to update the event object of
	 * {@link ConsumerEditorManager} where we have store the most recent event
	 * object.
	 * 
	 * @param editor
	 *            - instance of {@link EventsEditor}
	 * @param event
	 *            - most recent event object.
	 */
	public void updateEvent(EventsEditor editor, Event event) {
		ConsumerEditorManager.getInstance().addToFactory(
				editor.getProjectName(), event);
	}

}
