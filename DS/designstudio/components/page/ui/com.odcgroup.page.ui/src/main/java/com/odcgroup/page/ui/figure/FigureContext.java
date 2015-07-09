package com.odcgroup.page.ui.figure;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;

/**
 * The FigureContext is used to pass contextual information to the different figures.
 * 
 * @author atr
 */
public class FigureContext {

	/**
	 * Flag indicating that we are in design mode. In this mode hidden features, such as outlines, code Widgets... are
	 * displayed.
	 */
	private boolean designMode;

	/** The FigureConstants. */
	private FigureConstants figureConstants;
	
	/** The project. */
	private IProject project;

	/**
	 * Creates a new FigureContext.
	 * 
	 * @param project
	 * @param isDesignMode
	 * @param fc
	 */
	public FigureContext(IProject project, boolean isDesignMode, FigureConstants fc) {
		this.project = project;
		this.designMode = isDesignMode;
		this.figureConstants = fc;
	}

	/**
	 * Returns true if we are in design mode.In this mode hidden features, such as outlines, code Widgets... are
	 * displayed.
	 * 
	 * @return boolean
	 */
	public final boolean isDesignMode() {
		return designMode;
	}

	/**
	 * Returns true if we are in preview mode.
	 * 
	 * @return boolean
	 */
	public final boolean isPreviewMode() {
		return !designMode;
	}
	
	/**
	 * Gets the figure constants.
	 * 
	 * @return FigureConstants The FigureConstants
	 */
	public final FigureConstants getFigureConstants() {
		return figureConstants;
	}
	
	/**
	 * Gets the project that this figure is for.
	 * 
	 * @return IProject The project 
	 */
	public final IProject getProject() {
		return project;
	}
	
	/**
	 * @return CorporateDesign
	 */
	public final CorporateDesign getCorporateDesign() {
		return CorporateDesignUtils.getCorporateDesign(project);
		
	}
	
}