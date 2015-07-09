package com.odcgroup.integrationfwk.ui.editors.listeners;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;

import com.odcgroup.integrationfwk.ui.editors.ConsumerEditorManager;
import com.odcgroup.integrationfwk.ui.editors.FlowsEditor;
import com.odcgroup.integrationfwk.ui.model.Flow;

/**
 * Class which act as a wrapper/contractor between Flows Editor and its
 * listeners.
 * 
 * @author sbharathraja
 * 
 */
public abstract class AbstractFlowsEditorListener implements ModifyListener,
		SelectionListener, IDoubleClickListener, ISelectionChangedListener {

	/**
	 * Get the most recent flow object from the factory of
	 * {@link ConsumerEditorManager}
	 * 
	 * @param editor
	 *            - instance of {@link FlowsEditor}
	 * @return instance of {@link Flow}
	 */
	public Flow getFlow(FlowsEditor editor) {
		return ConsumerEditorManager.getInstance().getFlow(
				editor.getProjectName(), editor.getFlowName());
	}

	/**
	 * Method which helps to modify the dirty property of flow editor where this
	 * flow page has been placed.
	 * 
	 * @param editor
	 *            - instance of {@link FlowsEditor}
	 */
	public void setEditorModified(FlowsEditor editor) {
		// if was not already dirty then modified the editor
		boolean wasDirty = editor.isDirty();
		if (!wasDirty) {
			editor.setPageModified(true);
		}
		// update the source page content.
		editor.updateSourcePage();
	}

	/**
	 * Helps to set the given error message on the form page.
	 * 
	 * @param errorMessage
	 */
	public abstract void setError(String errorMessage);

	/**
	 * Method which helps to update the flow object which has been stored in
	 * factory of {@link ConsumerEditorManager} with the given most recent
	 * updated flow object.
	 * 
	 * @param editor
	 *            - instance of {@link FlowsEditor}
	 * @param flow
	 *            - instance of {@link Flow}
	 */
	public void updateFlow(FlowsEditor editor, Flow flow) {
		ConsumerEditorManager.getInstance().addToFactory(
				editor.getProjectName(), flow);
	}

}
