package com.odcgroup.t24.enquiry.figure;


/**
 * 
 * 
 * @author bneeraj
 *
 */
public class SelectionContainerFigure extends ContainerFigure {

	/**
	 *  selection container with vertical layout
	 */
	public SelectionContainerFigure(String label) {
		super(label, ContainerFigure.VERTICAL_LAYOUT);
	}
	
	public SelectionContainerFigure(String label, boolean border) {
		super(label, ContainerFigure.VERTICAL_LAYOUT, border);
	}
	
	
}
