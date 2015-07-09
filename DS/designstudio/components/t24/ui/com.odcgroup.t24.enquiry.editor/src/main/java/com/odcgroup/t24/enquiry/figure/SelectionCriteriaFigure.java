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
public class SelectionCriteriaFigure extends Figure {

	private Label fieldLabel;

	public SelectionCriteriaFigure() {
		setLayoutManager(new GridLayout(2, true));

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DOT);
		setBorder(border);
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);
		
		fieldLabel = new Label();
		add(fieldLabel);
		
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;
		gd.heightHint = 16;
		getLayoutManager().setConstraint(fieldLabel, gd);		
		
	}

	public Label getFieldLabel() {
		return fieldLabel;
	}

}
