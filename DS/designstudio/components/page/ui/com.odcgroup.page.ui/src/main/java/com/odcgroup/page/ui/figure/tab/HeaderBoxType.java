package com.odcgroup.page.ui.figure.tab;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.ui.figure.AbstractBoxType;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.table.TableGridLayout;

/**
 *
 */
public class HeaderBoxType extends AbstractBoxType {

	/**
	 * @param box
	 */
	public HeaderBoxType(BoxFigure box) {
		super(box);
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		GridLayout gd = new TableGridLayout(3, false);
		if (figureContext.isPreviewMode()) {
			gd.marginHeight= 0;
			gd.marginWidth = 1;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 0;
		} else {
			gd.marginHeight= 0;
			gd.marginWidth = 0;
			gd.horizontalSpacing = 0;
			gd.verticalSpacing = 1;
		}
		return gd;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getBoxType()
	 */
	@Override
	public String getBoxType() {
		return "H";
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxHeight()
	 */
	public int getMaxHeight() {
		return 16;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxWidth()
	 */
	public int getMaxWidth() {
		return -1;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
	 */
	public int getMinHeight() {
		return 16;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinWidth()
	 */
	public int getMinWidth() {
		List<?> children = getChildren();
		int width = 0;
		for (Object object : children) {
			if (object instanceof Figure) {				
				width += Math.max(0, ((Figure)object).getBounds().width);
			}
		}
		return width;
	}

}
