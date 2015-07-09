package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.GroupBoxBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.JFaceResources;

/**
 *
 * @author phanikumark
 *
 */
public class OutputContainerFigure extends Figure {
	
	private IFigure bodyFigure;
	private Label headerLabel;

	/**
	 * 
	 */
	public OutputContainerFigure(String label, boolean isHorizontal, int fillStyle) {
		setLayoutManager(new GridLayout());		

		bodyFigure = new Figure();
		bodyFigure.setLayoutManager(new FlowLayout(isHorizontal));
		bodyFigure.setBorder(new GroupBoxBorder(label));
		headerLabel = new Label();
		headerLabel.setLabelAlignment(PositionConstants.MIDDLE);
		headerLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 20;
		getLayoutManager().setConstraint(headerLabel, gridData);
		
		bodyFigure.add(headerLabel);
		getLayoutManager().setConstraint(bodyFigure, new GridData(fillStyle));
		add(bodyFigure);
	}

	/**
	 * @return the bodyFigure
	 */
	public IFigure getContentPane() {
		return bodyFigure;
	}

	/**
	 * @param headerLabel the headerLabel to set
	 */
	public Label getHeaderLabel() {
		return headerLabel;
	}
	

}
