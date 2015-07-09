package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.SWT;

/**
 * 
 * @author phanikumark
 * 
 */
public class RoutineFigure extends Figure {
	
	private Label label;

	public RoutineFigure() {
		setLayoutManager(new GridLayout());

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);

		label = new Label();
		label.setLabelAlignment(PositionConstants.LEFT);

		add(label);
		
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		getLayoutManager().setConstraint(label, gridData);
	}

	public Label getLabel() {
		return label;
	}
}
