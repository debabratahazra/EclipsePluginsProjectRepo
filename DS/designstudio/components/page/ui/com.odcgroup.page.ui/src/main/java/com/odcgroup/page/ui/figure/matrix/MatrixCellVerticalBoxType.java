package com.odcgroup.page.ui.figure.matrix;

import java.util.List;

import com.odcgroup.page.ui.figure.AbstractWidgetFigure;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.VerticalBoxType;

/**
 *
 * @author pkk
 *
 */
public class MatrixCellVerticalBoxType extends VerticalBoxType {

	/**
	 * @param box
	 */
	public MatrixCellVerticalBoxType(BoxFigure box) {
		super(box);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.VerticalBoxType#getMinHeight()
	 */
	@SuppressWarnings("unchecked")
	public int getMinHeight() {
		List children = getChildren();
		int cellHeight = getHeight();
		int spacing = getFigureConstants().getWidgetSpacing();
		int height = 15+ (2* spacing);	
		for (Object object : children) {
			if (object instanceof AbstractWidgetFigure) {
				AbstractWidgetFigure fig = (AbstractWidgetFigure) object;
				height += (fig.getMinHeight()+ spacing * 2);
			}
		}
		return Math.max(cellHeight, height);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.figure.VerticalBoxType#getMaxHeight()
	 */
	@SuppressWarnings("unchecked")
	public int getMaxHeight() {
		List children = getChildren();
		int cellHeight = getHeight();
		int height = 0;	
		for (Object object : children) {
			if (object instanceof AbstractWidgetFigure) {
				AbstractWidgetFigure fig = (AbstractWidgetFigure) object;
				height += (fig.getMinHeight()+getFigureConstants().getWidgetSpacing() * 2);
			}
		}
		return Math.max(cellHeight, height);
	}
	
	

}
