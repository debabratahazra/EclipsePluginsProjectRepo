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
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.editor.Activator;

/**
 * 
 * @author phanikumark
 * 
 */
public class TargetFigure extends Figure {

	private IFigure bodyFigure;

	private Label applicationLabel;
	private Label screenLabel;
	private ScrollPane pane;
	private CompartmentFigure fieldMappingsContainer;

	private ImageFigure imgFigure;

	public TargetFigure(String label) {
		setLayoutManager(new GridLayout());

		Panel panel = new Panel();
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginHeight = 2;
		gridLayout.marginWidth = 10;

		panel.setLayoutManager(gridLayout);

		final Label psLabel = new Label(label);
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
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(2);
		bodyFigure.setLayoutManager(layout);
		bodyFigure.setOpaque(true);
		bodyFigure.setBorder(new MarginBorder(5));	

		applicationLabel = new Label();
		applicationLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		applicationLabel.setPreferredSize(new Dimension(-1, 40));
		applicationLabel.setLabelAlignment(PositionConstants.MIDDLE);		
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		applicationLabel.setBackgroundColor(ColorConstants.buttonDarker);
		applicationLabel.setOpaque(true);
		getLayoutManager().setConstraint(applicationLabel, gd);

		screenLabel = new Label();
		screenLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		screenLabel.setLabelAlignment(PositionConstants.MIDDLE);		
		screenLabel.setPreferredSize(-1, 24);
		screenLabel.setBackgroundColor(ColorConstants.buttonDarker);
		screenLabel.setOpaque(true);
		getLayoutManager().setConstraint(screenLabel, gd);

		bodyFigure.add(applicationLabel);
		bodyFigure.add(screenLabel);
		
		pane = new ScrollPane();
		pane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
		fieldMappingsContainer = new CompartmentFigure(0);
		pane.setContents(fieldMappingsContainer);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		bodyFigure.getLayoutManager().setConstraint(pane, gridData);
		bodyFigure.add(pane);
		
		add(bodyFigure);
		getLayoutManager().setConstraint(bodyFigure, new GridData(GridData.FILL_BOTH));
	}

	/**
	 * @return the bodyFigure
	 */
	public IFigure getContentPane() {
		return fieldMappingsContainer;
	}

	public Label getApplicationLabel() {
		return applicationLabel;
	}

	public Label getScreenLabel() {
		return screenLabel;
	}

	public CompartmentFigure getFieldMappingsContainer() {
		return fieldMappingsContainer;
	}

	/**
	 * @return the imgFigure
	 */
	public ImageFigure getImgFigure() {
		return imgFigure;
	}

}
