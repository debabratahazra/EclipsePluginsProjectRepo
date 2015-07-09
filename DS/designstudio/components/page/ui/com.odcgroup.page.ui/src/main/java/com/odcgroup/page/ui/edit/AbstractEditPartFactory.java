/**
 * 
 */
package com.odcgroup.page.ui.edit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureFactory;
import com.odcgroup.page.uimodel.EditPolicyRole;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.ClassUtils;

/**
 * This is the base class for all edit part factory.
 * 
 * @author atr
 */
public abstract class AbstractEditPartFactory implements EditPartFactory {

	/** The project being edited. */
	private IProject project;

	/** The command stack used to execute command */
	private CommandStack commandStack;

	/**
	 * Creates a new EditPartFactory.
	 * 
	 * @param project
	 *            The IOfsProject being edited
	 * @param commandStack
	 *            The command stack used to execute command
	 */
	public AbstractEditPartFactory(IProject project, CommandStack commandStack) {
		Assert.isNotNull(project);
		Assert.isNotNull(commandStack);

		this.project = project;
		this.commandStack = commandStack;

	}

	/**
	 * Creates an EditPart.
	 * 
	 * @param context
	 *            The context
	 * @param model
	 *            The model Object to create the EditPart for
	 * @return EditPart The newly created EditPart
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if(model==null) return null;
		
		// Check the model
		if (!(model instanceof Widget)) {
			String error = "Cannot create Edit Part for this unknown model object: "
					+ model.getClass().getName();
			Logger.error(error);
			throw new IllegalArgumentException(error);
		}
		
		Widget widget = (Widget)model;
		
		// Create a figure Factory
		FigureFactory figureFactory = createFigureFactory();

		// Retrieve the renderer info descriptor for the current model
		RendererInfo ri = getRendererInfo(widget);
		
		// Create a new widget edit part according to the renderer info descriptor
		WidgetEditPart editPart = getWidgetEditPart(ri);
		editPart.setDesignMode(isDesignMode());
		editPart.setFigureFactory(figureFactory);
		editPart.setCommandStack(getCommandStack());
		editPart.setModel(widget);
		
		// Finally, install the required edit policies
		installEditPolicies(editPart, ri);

		return editPart;
	}

	/**
	 * Creates the FigureFactory used to create figures.
	 * 
	 * @return FigureFactory The figure factory
	 */
	protected FigureFactory createFigureFactory() {
		return new FigureFactory(isDesignMode(), project);
	}

	/**
	 * @return the command stack
	 */
	protected final CommandStack getCommandStack() {
		return commandStack;
	}

	/**
	 * @return the default widget edit part
	 */
	protected WidgetEditPart getDefaultWidgetEditPart() {
		return new WidgetEditPart();
	}

	/**
	 * Returns the renderer info descriptor for the designated widget
	 * 
	 * @param widget
	 *            a widget
	 * @return the renderer info descriptor
	 */
	protected RendererInfo getRendererInfo(Widget widget) {
		RendererInfo ri = null;
		WidgetType wt = widget.getType();
		if (wt != null) {
			UIModel uiModel = UIModelRegistry.getUIModel();
			ri = uiModel.getRenderers().findRendererInfo(widget.getType());
		}
		return ri;
	}
	
	/**
	 * Returns the widget edit part designated by the renderer info descriptor,
	 * or a default widget edit part if nothing is mentioned.
	 * 
	 * @param ri
	 *            the Renderer info descriptor
	 * @return the widget edit part
	 */
	protected final WidgetEditPart getWidgetEditPart(RendererInfo ri) {
		WidgetEditPart editPart = null;
		if (ri != null && !StringUtils.isEmpty(ri.getEditPart())) {
			// Use the specific EditPart
			editPart = (WidgetEditPart) ClassUtils.newInstance(getClass().getClassLoader(), ri.getEditPart());
		} else {
			// Use the default EditPart
			editPart = getDefaultWidgetEditPart();
		}
		return editPart;
	}

	/**
	 * Install the edit policies for the new concrete widget edit-part
	 * 
	 * @param editPart the new widget edit part
	 * @param ri The RendererInfo
	 */
	protected void installEditPolicies(WidgetEditPart editPart, RendererInfo ri) {
		// First install the EditPolicies which have been defined in the metamodel
		Set<String> definedRoles = new HashSet<String>();
		for (EditPolicyRole epr : ri.getRoles()) {
			String role = epr.getRole().getLiteral();
			definedRoles.add(role);
			
			EditorMode m = epr.getMode();
			if (acceptMode(m)) {
				EditPolicy ep = (EditPolicy) ClassUtils.newInstance(getClass().getClassLoader(), epr.getImplementationClass());
				editPart.installEditPolicy(role, ep);	
			}
		}
		
		// Now install the 'standard' or default policies which we not defined in the metamodel
		// Note that if the EditPolicy was defined in the metamodel with an EditorMode of NONE
		// the 'standard' policy will not be added
		for (Map.Entry<String, Class<?>> entry : getDefaultPolicies().entrySet()) {
			String role = entry.getKey();
			if (definedRoles.contains(role)) {
				// Already defined
				continue;
			}
			EditPolicy ep = (EditPolicy) ClassUtils.newInstance(getClass().getClassLoader(), entry.getValue().getName());
			editPart.installEditPolicy(role, ep);	
		}
		
	}

	/**
	 * @return <code>True</code> if the concrete EditPartFactory is related to design mode
	 *         otherwise it must return <code>False</code>
	 */
	protected abstract boolean isDesignMode();
	
	/**
	 * Returns true if the EditorMode should be accepted.
	 * 
	 * @param mode The mode to test
	 * @return boolean
	 */
	abstract protected boolean acceptMode(EditorMode mode);
	
	/**
	 * Gets the default EditPolicies.
	 * 
	 *  @return Map
	 */
	abstract protected Map<String, Class<?>> getDefaultPolicies();
}
