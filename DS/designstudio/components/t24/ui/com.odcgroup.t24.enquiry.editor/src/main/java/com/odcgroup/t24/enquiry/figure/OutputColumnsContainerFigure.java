package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;


/**
 * container for enquiry columns
 * 
 * @author phanikumark
 *
 */
public class OutputColumnsContainerFigure  extends ContainerFigure {

	/**
	 * columns container with horizontal layout
	 */
	public OutputColumnsContainerFigure(String label) {
		super(label, ContainerFigure.HORIZONTAL_LAYOUT, false);
	}
	
	/**
	 * @return the bodyFigure
	 */
	public IFigure getContentPane() {
		IFigure figure = super.getContentPane();
		ColumnsLayout layout = new ColumnsLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(true);
		layout.setHorizontal(true);
		layout.setSpacing(8);
		figure.setLayoutManager(layout);
		return figure;
	}
	

}
