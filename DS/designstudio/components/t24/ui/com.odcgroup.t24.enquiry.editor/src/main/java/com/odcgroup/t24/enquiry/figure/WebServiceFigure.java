package com.odcgroup.t24.enquiry.figure;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.editor.Activator;

/**
 * 
 * @author phanikumark
 * 
 */
public class WebServiceFigure extends Figure {

	private IFigure bodyFigure;

	private Label activityLabel;
	private CompartmentFigure servicesContainer = new CompartmentFigure(20);

	private ImageFigure imgFigure;

	public WebServiceFigure() {
		setLayoutManager(new GridLayout());

		Panel panel = new Panel();
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginHeight = 2;
		gridLayout.marginWidth = 10;

		panel.setLayoutManager(gridLayout);

		final Label psLabel = new Label("Web Service");
		psLabel.setLabelAlignment(PositionConstants.LEFT);
		psLabel.setOpaque(true);
		psLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		panel.add(psLabel);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		
		URL url = FileLocator.find(bundle, new Path("icons/add-icon.png"), null);
		imgFigure = new ImageFigure(ImageDescriptor.createFromURL(url).createImage());
		imgFigure.setAlignment(PositionConstants.RIGHT);
		panel.add(imgFigure);

		add(panel);
		bodyFigure = new RectangleFigure();
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(2);
		bodyFigure.setLayoutManager(layout);
		bodyFigure.setBackgroundColor(ColorConstants.button);
		bodyFigure.setOpaque(true);
		bodyFigure.setBorder(new MarginBorder(5));		


		activityLabel = new Label();
		activityLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		activityLabel.setLabelAlignment(PositionConstants.MIDDLE);		
		activityLabel.setBackgroundColor(ColorConstants.buttonDarker);
		activityLabel.setOpaque(true);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		getLayoutManager().setConstraint(activityLabel, gd);

		bodyFigure.add(activityLabel);
		bodyFigure.add(servicesContainer);
		
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		bodyFigure.getLayoutManager().setConstraint(servicesContainer, gridData);
		

		getLayoutManager().setConstraint(bodyFigure, new GridData(GridData.FILL_BOTH));
		add(bodyFigure);
	}

	/**
	 * @return the bodyFigure
	 */
	public IFigure getContentPane() {
		return servicesContainer;
	}

	public Label getActivityLabel() {
		return activityLabel;
	}

	public CompartmentFigure getServicesContainer() {
		return servicesContainer;
	}

	/**
	 * @return the imgFigure
	 */
	public ImageFigure getImgFigure() {
		return imgFigure;
	}

}
