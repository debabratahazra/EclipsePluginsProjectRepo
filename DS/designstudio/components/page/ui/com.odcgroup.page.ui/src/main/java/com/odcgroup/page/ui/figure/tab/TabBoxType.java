package com.odcgroup.page.ui.figure.tab;

import org.eclipse.draw2d.LayoutManager;

import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureContext;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalBoxType;
import com.odcgroup.page.ui.figure.conditional.ConditionalBodyFigure;

/**
 *
 */
public class TabBoxType extends VerticalBoxType {

	/**
	 * @param box
	 */
	public TabBoxType(BoxFigure box) {
		super(box);
	}
	
	/**
	 * @see com.odcgroup.page.ui.figure.VerticalBoxType#createLayoutManager(com.odcgroup.page.ui.figure.FigureContext)
	 */
	public LayoutManager createLayoutManager(FigureContext figureContext) {
		return new TabBoxLayout(figureContext);
	}

	@Override
	public int getMinWidth() {
		int width = 0;
		for (Object  obj : getChildren()) {
			IWidgetFigure child = (IWidgetFigure) obj;
			if (child instanceof ConditionalBodyFigure) {
				width = Math.max(width, child.getMinWidth());
			}
		}
		if (getChildren().size() > 0) {
			// Allow a spacing before and after the widest Widget
			width += getFigureConstants().getWidgetSpacing() * 2;
		}
		int pixwidth = getBoxFigure().getPixelWidth();
		return width < pixwidth ? pixwidth : width;
	}
	
}
