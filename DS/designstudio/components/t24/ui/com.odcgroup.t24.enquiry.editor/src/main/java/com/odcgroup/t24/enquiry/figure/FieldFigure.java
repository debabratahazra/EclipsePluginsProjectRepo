package com.odcgroup.t24.enquiry.figure;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.editor.Activator;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * 
 * @author phanikumark
 * 
 */
public class FieldFigure extends Figure {
	
	private Label label;
	private CompartmentFigure attributeFigure = new CompartmentFigure(0);
	private CompartmentFigure headerFigure = new CompartmentFigure(0);
	private ImageFigure imgFigure;
	private Field field;

	/**
	 * @return the attributeFigure
	 */
	public CompartmentFigure getAttributeFigure() {
		return attributeFigure;
	}

	public FieldFigure() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayoutManager(layout);	
		setBackgroundColor(ColorConstants.gray);
		setOpaque(true);
		LineBorder border = new LineBorder();
		border.setStyle(SWT.LINE_DASH);
		setBorder(border);
		label = new Label();
		label.setLabelAlignment(PositionConstants.MIDDLE);
		
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 20;
		getLayoutManager().setConstraint(label, gridData);
		
		add(label);
		add(attributeFigure);
		
		final GridData gd = new GridData(GridData.FILL_BOTH);
		getLayoutManager().setConstraint(attributeFigure, gd);
	}

	public Label getLabel() {
		return label;
	}
	
	public void markInvisible(){
		attributeFigure.setBackgroundColor(ColorConstants.lightGray);
	}
	
	public void markFooter() {
		add(getHeaderFooterFigure());
	}

	public void markHeader() {
		add(getHeaderFooterFigure(), 1);
	}
	
	private CompartmentFigure getHeaderFooterFigure() {
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 40;
		headerFigure.setBackgroundColor(ColorConstants.lightGray);
		getLayoutManager().setConstraint(headerFigure, gridData);
		return headerFigure;
	}
	
	public void markRelative(String line, String relative) {
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url;
		if (relative.equals(EnquiryUtil.RELATIVE_LEFT)) {
			url = FileLocator.find(bundle, new Path("icons/arrow-left-icon.png"), null);
		} else {
			url = FileLocator.find(bundle, new Path("icons/arrow-right-icon.png"), null);
		}
		attributeFigure.removeAll();
		imgFigure = new ImageFigure(ImageDescriptor.createFromURL(url).createImage());
		imgFigure.setAlignment(PositionConstants.RIGHT);
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 20;
		gridLayout.marginWidth = 10;
		attributeFigure.setLayoutManager(gridLayout);
		attributeFigure.add(imgFigure);
		Label value = new Label();
		value.setText(line);
		attributeFigure.add(value);
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}

}
