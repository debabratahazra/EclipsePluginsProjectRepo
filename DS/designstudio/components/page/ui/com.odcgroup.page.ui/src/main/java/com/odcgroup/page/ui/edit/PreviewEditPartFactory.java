package com.odcgroup.page.ui.edit;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.uimodel.EditorMode;

/**
 * This is the EditPartFactory for the preview editor. 
 * It creates a WidgetEditPart which has NO installed edit policies.
 * 
 * @author Gary Hayes
 */
public class PreviewEditPartFactory extends AbstractEditPartFactory {
	
	/** The default EditPolicies. */
	private static Map<String, Class<?>> DEFAULT_POLICIES;
	static {
		DEFAULT_POLICIES = new HashMap<String, Class<?>>();
		DEFAULT_POLICIES.put(EditPolicy.LAYOUT_ROLE, WidgetLayoutEditPolicy.class);
	}	

	/** 
	 * Creates a new PreviewEditPartFactory.
	 * 
	 * @param project The IProject being edited
	 * @param commandStack The command stack used to execute command
	 */
	public PreviewEditPartFactory(IProject project, CommandStack commandStack) {
		super(project, commandStack);
	}

	/**
	 * @return <code>True</code> if the concrete EditPartFactory is related to design mode
	 *         otherwise it must return <code>False</code>
	 */
	protected final boolean isDesignMode() {
		return false;
	}
	
	/**
	 * Returns true if the EditorMode should be accepted.
	 * 
	 * @param mode The mode to test
	 * @return boolean
	 */
	protected boolean acceptMode(EditorMode mode) {
		if (EditorMode.ALL.equals(mode) || EditorMode.PREVIEW_MODE.equals(mode)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the default EditPolicies.
	 * 
	 *  @return Map
	 */
	protected Map<String, Class<?>> getDefaultPolicies() {
		return DEFAULT_POLICIES;
	}
}