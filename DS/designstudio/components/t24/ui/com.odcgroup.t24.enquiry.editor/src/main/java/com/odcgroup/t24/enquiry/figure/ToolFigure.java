package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;

/**
 * 
 * @author phanikumark
 * 
 */
public class ToolFigure extends Figure {

	private Label fieldLabel;
	private CompartmentFigure attributeFigure = new CompartmentFigure(20);

	public ToolFigure() {
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayoutManager(layout);
		setBackgroundColor(ColorConstants.buttonDarker);

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		setOpaque(true);
		
		fieldLabel = new Label();
		fieldLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		fieldLabel.setLabelAlignment(PositionConstants.MIDDLE);		
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 16;
		getLayoutManager().setConstraint(fieldLabel, gd);
		

		add(fieldLabel);
		add(attributeFigure);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		getLayoutManager().setConstraint(attributeFigure, gridData);
	}

	public Label getFieldLabel() {
		return fieldLabel;
	}

	public CompartmentFigure getAttributesCompartment() {
		return attributeFigure;
	}
}
