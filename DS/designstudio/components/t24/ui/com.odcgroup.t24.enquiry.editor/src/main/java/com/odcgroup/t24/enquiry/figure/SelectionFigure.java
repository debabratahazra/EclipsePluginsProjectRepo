package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.swt.SWT;

/**
 * 
 * @author phanikumark
 * 
 */
public class SelectionFigure extends Figure {

	private Label fieldLabel;
	private Label operatorLabel;

	public SelectionFigure() {
		setLayoutManager(new GridLayout(2, true));

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);
		
		fieldLabel = new Label();
		add(fieldLabel);
		
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;
		gd.heightHint = 16;
		getLayoutManager().setConstraint(fieldLabel, gd);
		
		operatorLabel = new Label();
		
		add(operatorLabel);
		
		final GridData gridData = new GridData(GridData.END, GridData.END, true, false);
		gridData.horizontalIndent = 20;
		getLayoutManager().setConstraint(operatorLabel, gridData);
	}

	public Label getFieldLabel() {
		return fieldLabel;
	}
	
	public Label getOperatorLabel() {
		return operatorLabel;
	}

}
