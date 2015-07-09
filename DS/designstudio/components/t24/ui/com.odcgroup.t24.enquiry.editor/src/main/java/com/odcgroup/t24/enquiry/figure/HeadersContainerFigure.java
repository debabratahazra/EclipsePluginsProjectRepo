package com.odcgroup.t24.enquiry.figure;


/**
 * Figure representing the container for enquiry headers
 *
 * @author phanikumark
 *
 */
public class HeadersContainerFigure extends ContainerFigure {

	/**
	 * headers container with horizontal layout
	 * @param label
	 * @param layoutStyle
	 */
	public HeadersContainerFigure(String label) {
		super(label, ContainerFigure.VERTICAL_LAYOUT, false, false);
	}
	

}
