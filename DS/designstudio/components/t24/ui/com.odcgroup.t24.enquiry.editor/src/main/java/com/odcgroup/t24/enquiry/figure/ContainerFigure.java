package com.odcgroup.t24.enquiry.figure;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.editor.Activator;

/**
 * container figure for lists etc.
 *
 * @author phanikumark
 *
 */
public class ContainerFigure extends Figure {
	
	private IFigure bodyFigure;
	private ImageFigure imgFigure;
	protected Panel panel;

	static final int VERTICAL_LAYOUT = 0;
	static final int HORIZONTAL_LAYOUT = 1;

	/**
	 * @return the bodyFigure
	 */
	public IFigure getContentPane() {
		return bodyFigure;
	}
	
	
	public ContainerFigure(String label, int layoutStyle, boolean border, boolean addIcon) {
		setLayoutManager(new GridLayout());
		// add label and add icon
		panel = new Panel();
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginHeight = 2;
		gridLayout.marginWidth = 5;
		panel.setLayoutManager(gridLayout);
		
		final Label psLabel = new Label(label);
		psLabel.setLabelAlignment(PositionConstants.LEFT);
		psLabel.setOpaque(true);
		psLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		panel.add(psLabel);
		if (addIcon) {
			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
			addImage(bundle);	
		}
		add(panel);
		
		// add the container figure
		ScrollPane pane = new ScrollPane();
		if (border) {
			pane.setBorder(new LineBorder(1));
		}
		bodyFigure = new RectangleFigure();
		ToolbarLayout layout = null;
		if (layoutStyle == ContainerFigure.HORIZONTAL_LAYOUT) {
			layout = new CustomLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setHorizontal(true);
			layout.setSpacing(2);
			pane.setVerticalScrollBarVisibility(ScrollPane.NEVER);
		} else  {
			layout = new CustomLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(true);
			layout.setSpacing(2);	
			pane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
		}
		bodyFigure.setLayoutManager(layout);	
		if (border) {
			bodyFigure.setBorder(new MarginBorder(5));
		}
		pane.setContents(bodyFigure);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalIndent = 0;
		getLayoutManager().setConstraint(pane, gd);
		add(pane);
		
	}
	
	/**
	 * container with a specified layout type
	 * 
	 * @param label
	 * @param layoutStyle
	 */
	public ContainerFigure(String label, int layoutStyle) {
		this(label, layoutStyle, true, true);
	}
	
	public ContainerFigure(String label, int layoutStyle, boolean addIcon) {
		this(label, layoutStyle, true, addIcon);		
	}

	/**
	 * 
	 */
	protected void addImage(Bundle bundle) {
		URL url = FileLocator.find(bundle, new Path("icons/add-icon.png"), null);
		imgFigure = new ImageFigure(ImageDescriptor.createFromURL(url).createImage());
		imgFigure.setAlignment(PositionConstants.RIGHT);
		panel.add(imgFigure);
	}

	public ImageFigure getImageFigure(){
		return imgFigure;
	}
}
