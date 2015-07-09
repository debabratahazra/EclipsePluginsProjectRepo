package com.odcgroup.t24.enquiry.figure;

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
public class TargetMappingFigure extends Figure {

	private Label fieldLabel;
	private Label valueLabel;

	public TargetMappingFigure() {
		setLayoutManager(new GridLayout(2, true));

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		
		fieldLabel = new Label();
		add(fieldLabel);
		fieldLabel.setLabelAlignment(PositionConstants.LEFT);
		
		valueLabel = new Label();
		add(valueLabel);
		valueLabel.setLabelAlignment(PositionConstants.LEFT);
		
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		getLayoutManager().setConstraint(fieldLabel, gridData);
		getLayoutManager().setConstraint(valueLabel, gridData);
	}

	public Label getFieldLabel() {
		return fieldLabel;
	}
	
	public Label getValueLabel() {
		return valueLabel;
	}
}
