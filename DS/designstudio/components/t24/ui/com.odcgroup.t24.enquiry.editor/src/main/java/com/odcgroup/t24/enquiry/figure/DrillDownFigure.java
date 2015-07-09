package com.odcgroup.t24.enquiry.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.swt.SWT;

/**
 * 
 * @author phanikumark
 * 
 */
public class DrillDownFigure extends Figure {
	
	private LabelValueFigure label;
	private LabelValueFigure typeLabel;
	private LabelValueFigure fieldLabel;
	private LabelValueFigure paramLabel;
	private CompartmentFigure attributeFigure = new CompartmentFigure(0);
	private SelectionContainerFigure selectionContainerFigure = new SelectionContainerFigure("Criteria:", true);

	public DrillDownFigure() {
		setLayoutManager(new GridLayout());

		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);		
		
		add(attributeFigure);
		
		final GridData gd = new GridData(GridData.FILL_BOTH);
		getLayoutManager().setConstraint(attributeFigure, gd);		
		
		GridData data = new GridData(GridData.FILL_BOTH);
		getLayoutManager().setConstraint(selectionContainerFigure, data);
		add(selectionContainerFigure);

		typeLabel = new LabelValueFigure();			
		label = new LabelValueFigure();		
		fieldLabel = new LabelValueFigure();			
		paramLabel = new LabelValueFigure();
		
		attributeFigure.add(label);
		attributeFigure.add(typeLabel);
		attributeFigure.add(fieldLabel);
		attributeFigure.add(paramLabel);
	}

	public LabelValueFigure getLabel() {
		return label;
	}
	
	public LabelValueFigure getParamLabel() {
		return paramLabel;
	}
	
	public LabelValueFigure getTypeLabel() {
		return typeLabel;
	}
	
	public LabelValueFigure getFieldLabel() {
		return fieldLabel;
	}
	
	public CompartmentFigure getAttributeFigure() {
		return attributeFigure;
	}
	
	public ImageFigure getSelectionCriteriaAddIcon(){
		selectionContainerFigure.getImageFigure().setToolTip(new Label("Add Selection Criteria"));
		return selectionContainerFigure.getImageFigure();
	}
	
	public SelectionContainerFigure getSelectionContainerFigure() {
		return selectionContainerFigure;
	}
}
