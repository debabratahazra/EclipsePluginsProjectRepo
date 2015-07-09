package com.odcgroup.process.diagram.custom.figures;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;

import com.odcgroup.process.diagram.custom.util.TaskSize;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

/**
 * @author pkk
 *
 */
public class TaskFigure extends org.eclipse.draw2d.RoundedRectangle {
	
	private String taskIconloc = null;
	
	/**
	 * OCS-27175 size preferences included
	 * @generated NOT
	 */
	public TaskFigure(String image) {
		this.setPreferredSize(TaskSize.getPreferredStateSize());
		this.setCornerDimensions(new org.eclipse.draw2d.geometry.Dimension(16,16));
		this.setForegroundColor(ColorConstants.black);
		this.setLayoutManager(new StackLayout() {
			@SuppressWarnings("unchecked")
			public void layout(IFigure figure) {
				Rectangle r = figure.getClientArea();
				List children = figure.getChildren();
				IFigure child;
				Dimension d;
				for (int i = 0; i < children.size(); i++) {
					child = (IFigure) children.get(i);
					if (child instanceof WrapLabel){
						d = child.getPreferredSize(r.width, r.height);
						d.width = Math.min(d.width, r.width);
						d.height = Math.min(d.height, r.height);
						Rectangle childRect = new Rectangle(r.x
								+ (r.width - d.width) / 2, r.y
								+ (r.height - d.height) / 2, d.width, d.height);
						child.setBounds(childRect);
					} else {
						child.setBounds(r);
					}
				}
			}
		});
		this.taskIconloc = image;
		createContents();
	}

	/**
	 * @generated NOT
	 */
	private void createContents() {
		taskName = new WrapLabel();
		setFigureActivityNameFigure(taskName);
		this.add(taskName);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalAlignment = GridData.BEGINNING;
		ImageFigure img = new ImageFigure();
		img.setImage(ProcessDiagramEditorPlugin.getInstance().getBundledImage(taskIconloc));
		img.setAlignment(PositionConstants.NORTH);
		this.add(img, gridData);
	
	}

	/**
	 * @generated NOT
	 */
	private WrapLabel taskName;

	/**
	 * @generated
	 */
	public org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel getFigureActivityNameFigure() {
		return taskName;
	}

	/**
	 * @generated
	 */
	private void setFigureActivityNameFigure(WrapLabel fig) {
		taskName = fig;
	}

	/**
	 * @generated
	 */
	private boolean myUseLocalCoordinates = false;

	/**
	 * @generated
	 */
	protected boolean useLocalCoordinates() {
		return myUseLocalCoordinates;
	}

	/**
	 * @generated
	 */
	protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
		myUseLocalCoordinates = useLocalCoordinates;
	}

}
