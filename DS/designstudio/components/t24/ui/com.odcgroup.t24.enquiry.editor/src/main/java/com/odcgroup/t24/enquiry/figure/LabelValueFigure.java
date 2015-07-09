package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.JFaceResources;

/**
 *
 * @author phanikumark
 *
 */
public class LabelValueFigure extends Figure {
	
	private Label typeLabel;

	private Label valueLabel;
	
	public LabelValueFigure() {
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 10;
		setLayoutManager(gridLayout);
		
		GridData gridData = new GridData();
		gridData.widthHint = 80;	
		typeLabel = new Label();
		typeLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		typeLabel.setLabelAlignment(PositionConstants.LEFT);		
		getLayoutManager().setConstraint(typeLabel, gridData);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		valueLabel = new Label();
		valueLabel.setLabelAlignment(PositionConstants.LEFT);
		getLayoutManager().setConstraint(valueLabel, data);
		
		add(typeLabel);
		add(valueLabel);
	}
	
	/**
	 * @return the typeLabel
	 */
	public Label getTypeLabel() {
		return typeLabel;
	}

	/**
	 * @return the valueLabel
	 */
	public Label getValueLabel() {
		return valueLabel;
	}

}
