package com.odcgroup.page.ui.edit;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.RendererInfo;

/**
 * The EditPartFactory for the Design Editor. This wraps the 
 * PreviewEditPartFactory installing the EditPolicies required to support
 * creating / deleting Widgets, drag-and-drop etc.
 * 
 * @author Gary Hayes
 */
public class DesignEditPartFactory extends AbstractEditPartFactory {
	
	/** The default EditPolicies. */
	private static Map<String, Class<?>> DEFAULT_POLICIES;
	static {
		DEFAULT_POLICIES = new HashMap<String, Class<?>>();
		DEFAULT_POLICIES.put(EditPolicy.LAYOUT_ROLE, WidgetLayoutEditPolicy.class);
		DEFAULT_POLICIES.put(EditPolicy.COMPONENT_ROLE, WidgetComponentEditPolicy.class);
		DEFAULT_POLICIES.put(EditPolicy.DIRECT_EDIT_ROLE, WidgetDirectEditPolicy.class);
	}
	
	/** The default EditPolicies for readonly resource. */
	private static Map<String, Class<?>> READONLY_DEFAULT_POLICIES;
	static {
		READONLY_DEFAULT_POLICIES = new HashMap<String, Class<?>>();
		READONLY_DEFAULT_POLICIES.put(EditPolicy.LAYOUT_ROLE, WidgetLayoutEditPolicy.class);
	}		
	
	/** Must be True whenever the PageDesigner display a readonly model */
	private boolean readonly = false;

	/** 
	 * Creates a new PreviewEditPartFactory.
	 * 
	 * @param project The project being edited
	 * @param readonly True when the displayed model is readonly
	 * @param commandStack The command stack used to execute command
	 */
	public DesignEditPartFactory(IProject project, boolean readonly, CommandStack commandStack) {
		super(project, commandStack);
		this.readonly = readonly;
	}
	
	/**
	 * Install the edit policies for the new concrete widget edit-part.
	 * Override the base-class version to add the SelectionFeedback Role.
	 * 
	 * @param editPart the new widget edit part
	 * @param ri The RendererInfo
	 */
	protected void installEditPolicies(WidgetEditPart editPart, RendererInfo ri) {
		super.installEditPolicies(editPart, ri);
		
		if (!editPart.isRootEditPart()) {
			// IMPROVE GHA Performance. We should only add this EditPolicy to Widgets which can
			// contain other Widgets
			if ( ! readonly) {
				editPart.installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
					new WidgetContainerHighlightEditPolicy());
			}
		}
	}
	
	/**
	 * @return <code>True</code> if the concrete EditPartFactory is related to design mode
	 *         otherwise it must return <code>False</code>
	 */
	protected final boolean isDesignMode() {
		return true;
	}

	/**
	 * Returns true if the EditorMode should be accepted.
	 * 
	 * @param mode The mode to test
	 * @return boolean
	 */
	protected boolean acceptMode(EditorMode mode) {
		if (EditorMode.ALL.equals(mode) || EditorMode.DESIGN_MODE.equals(mode)) {
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
		if (readonly) {
			return READONLY_DEFAULT_POLICIES;
		}
		return DEFAULT_POLICIES;
	}	
}