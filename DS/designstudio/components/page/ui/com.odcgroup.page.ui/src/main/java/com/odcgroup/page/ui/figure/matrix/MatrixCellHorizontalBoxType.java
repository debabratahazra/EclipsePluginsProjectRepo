package com.odcgroup.page.ui.figure.matrix;

import java.util.List;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.ui.figure.AbstractBoxType;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.table.TableGridLayout;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class MatrixCellHorizontalBoxType extends AbstractBoxType {
	
	/**
	 * @return table widget
	 */
	protected IMatrixExtraColumn getMatrixExtraColumn() {
		return MatrixHelper.getMatrixExtraColumn(getBoxFigure().getWidget());
	}
	
	/** The absolute box type character used to display. */
	private static String BOX_TYPE_TO_DISPLAY = "";
	
	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		GridLayout gd = new TableGridLayout(getMatrixExtraColumn().getItems().size(), false);
		if (figureContext.isPreviewMode()) {
			gd.marginHeight= 0;
			gd.marginWidth = 1;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 0;
		} else {
			gd.marginHeight= 3;
			gd.marginWidth = 9;
			gd.horizontalSpacing = 8;
			gd.verticalSpacing = 1;
		}
		return gd;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getBoxType()
	 */
	public String getBoxType() {
		return BOX_TYPE_TO_DISPLAY;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxHeight()
	 */
	public int getMaxHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;		
		
		IMatrixExtraColumn extra = getMatrixExtraColumn();
		int nbCols = extra.getItems().size();
		
		List<?> children = getChildren();
		IWidgetFigure child = null;
		int maxHeight = 0;
		for (int cx = 0; cx < nbCols; cx++) {
			child = (IWidgetFigure)children.get(cx);
			maxHeight = Math.max(maxHeight, child.getMaxHeight());
		}
		height += maxHeight;
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxWidth()
	 */
	public int getMaxWidth() {
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (getPixelWidth() > 0) {
			return getPixelWidth();
		}		
		
		return -1;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
	 */
	public int getMinHeight() {
		// Users can define a fixed height for the HorizontalBox. If this is smaller
		// than the needed height then scrollbars are added to the Box.
		if (getHeight() > 0) {
			return getHeight();
		}
		
		int height = 0;		
		
		IMatrixExtraColumn extra = getMatrixExtraColumn();
		int nbCols = extra.getItems().size();
		
		List<?> children = getChildren();
		IWidgetFigure child = null;
		int maxHeight = 0;
		for (int cx = 0; cx < nbCols; cx++) {
			child = (IWidgetFigure)children.get(cx);
			maxHeight = Math.max(maxHeight, child.getMinHeight());
		}
		height += maxHeight;
		
		if (children.size() > 0) {
			// Allow a space above and below the tallest Widget
			height += getFigureConstants().getWidgetSpacing() * 2;
		}		
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		
		return height;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinWidth()
	 */
	@SuppressWarnings("unchecked")
	public int getMinWidth() {
		
		int width = 0;
		
		IMatrixExtraColumn extra = getMatrixExtraColumn();
		int nbCols = extra.getItems().size();
		
		List children = getChildren();
		
		IWidgetFigure child = null;
		for (int cx = 0; cx < nbCols; cx++) {
			child = (IWidgetFigure)children.get(cx);
			width += Math.max(0, child.getMinWidth());
		}
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		// Users can define a fixed width for the HorizontalBox. However if the width
		// needed to display all its components is larger than this width then the
		// defined width is ignored.
		if (width < getPixelWidth()) {
			width = getPixelWidth();
		}
		
		return width;
	}

	/**
	 * @param box
	 */
	public MatrixCellHorizontalBoxType(BoxFigure box) {
		super(box);
	}



}
