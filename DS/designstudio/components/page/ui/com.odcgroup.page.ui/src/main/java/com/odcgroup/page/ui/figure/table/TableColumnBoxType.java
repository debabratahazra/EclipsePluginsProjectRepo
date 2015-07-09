package com.odcgroup.page.ui.figure.table;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.FigureUtilities;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.ui.figure.BoxFigure;
import com.odcgroup.page.ui.figure.FigureConstants;
import com.odcgroup.page.ui.figure.HorizontalBoxType;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * @author phanikumark
 *
 */
public class TableColumnBoxType extends HorizontalBoxType {

	/**
	 * @param box
	 */
	public TableColumnBoxType(BoxFigure box) {
		super(box);
	}
	


	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMinWidth() {
		List children = getChildren();
		int width = 0;
		int wspace = getFigureConstants().getWidgetSpacing() * 2;
		int maxwidth = 0;
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			if (child instanceof CompartmentFigure) {
				maxwidth = Math.max(child.getMinWidth()+wspace*2, maxwidth);
			} else {
				if (isNewLine(child)) {
					maxwidth += width;
					width = 0;
				} else {
					maxwidth += child.getMinWidth()+wspace;
				}
			}
		}	
		width = maxwidth;	
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (width < fc.getSimpleWidgetDefaultWidth()) {
			width = fc.getSimpleWidgetDefaultWidth();
		}
		
		TableColumnFigure tcf = (TableColumnFigure) getBoxFigure();
		String caption = tcf.getText();
		ITableColumn tableColumn = TableHelper.getTableColumn(tcf.getWidget());
		int more = 28;
		if (tableColumn.isPlaceHolder()) {
			caption = "[ ] " + caption;
			more += 10;
		} else if (tableColumn.isComputed()) {
			more += 30;
		} else if (tableColumn.isDynamic()) {
			more += 30;
		}
		int twidth = FigureUtilities.getTextWidth(caption, getFigureConstants().getCaptionFont(false))+more;
		if (width < twidth) {
			width = twidth;
		}
		if (width < 50) {
			width = 50;
		}
		return width;
	}	
	

	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	@SuppressWarnings("rawtypes")
	public int getMinHeight() {	
		int height = 0;		
		int wspace = getFigureConstants().getWidgetSpacing() * 2;
		int maxheight = 0;
		List children = getChildren();
		for (Iterator it = children.iterator(); it.hasNext();) {
			IWidgetFigure child = (IWidgetFigure) it.next();
			if (child instanceof CompartmentFigure) {
				height += child.getMinHeight()+wspace;
			} else {
				maxheight = Math.max(maxheight, child.getMinHeight()+wspace);
			}
			if (isNewLine(child)) {
				height += maxheight+child.getMinHeight()+wspace;
				maxheight = 0;
			}
		}	
		if (maxheight > 0) {
			height += maxheight;
		}	
		
		// We need to provide a minWidth for the box otherwise we would
		// not be able to add Widgets to add
		FigureConstants fc = getFigureConstants();
		if (height < fc.getSimpleWidgetDefaultHeight()) {
			height = fc.getSimpleWidgetDefaultHeight();
		}
		return height > 200 ? height : 200;	
	}
	
	
	
	/**
	 * @param figure
	 * @return
	 */
	private boolean isNewLine(IWidgetFigure figure) {
		if (figure instanceof TableColumnItem) {
			ITableColumnItem item = TableHelper.getTableColumnItem(figure.getWidget());
			return item.isNewLine();
		} 
		return false;
	}

}
