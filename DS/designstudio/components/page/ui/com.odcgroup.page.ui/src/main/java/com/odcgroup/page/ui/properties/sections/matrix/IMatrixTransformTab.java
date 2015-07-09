package com.odcgroup.page.ui.properties.sections.matrix;

import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.widgets.matrix.IMatrix;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public interface IMatrixTransformTab {
	
	/**
	 * @param input
	 * @param part
	 */
	void setInput(IMatrix input, IWorkbenchPart part);
	
	/**
	 * 
	 */
	void refresh();
	
	/**
	 * @param enabled
	 */
	void setEnabled(boolean enabled);



}
