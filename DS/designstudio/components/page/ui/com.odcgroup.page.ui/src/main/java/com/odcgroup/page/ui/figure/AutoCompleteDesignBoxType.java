package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.ui.figure.table.TableGridLayout;

/**
 * @author pkk
 *
 */
public class AutoCompleteDesignBoxType extends AbstractBoxType {

	/**
	 * @param box
	 */
	public AutoCompleteDesignBoxType(BoxFigure box) {
		super(box);
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		GridLayout gd = new TableGridLayout(1, false);
		if (figureContext.isPreviewMode()) {
			gd.marginHeight= 0;
			gd.marginWidth = 1;
			gd.horizontalSpacing = 1;
			gd.verticalSpacing = 0;
		} else {
			gd.marginHeight= 0;
			gd.marginWidth = 4;
			gd.horizontalSpacing = 5;
			gd.verticalSpacing = 0;
		}
		return gd;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getBoxType()
	 */
	public String getBoxType() {
		return "F";
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxHeight()
	 */
	public int getMaxHeight() {
		return 0;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMaxWidth()
	 */
	public int getMaxWidth() {
		return 0;
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinHeight()
	 */
	public int getMinHeight() {
		return 140;	
	}

	/**
	 * @see com.odcgroup.page.ui.figure.BoxType#getMinWidth()
	 */
	public int getMinWidth() {		
		return 570;	
	}

}
