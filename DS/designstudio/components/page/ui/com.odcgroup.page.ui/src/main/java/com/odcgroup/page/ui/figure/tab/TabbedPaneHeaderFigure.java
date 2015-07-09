package com.odcgroup.page.ui.figure.tab;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Font;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * HeaderFigure for TabbedPane's (and ConditionalWidget's).
 * 
 * @author Gary Hayes
 */
public class TabbedPaneHeaderFigure extends AbstractHeaderFigure {
	
	/** The type of Widget that we should create Headers for. */
	private String bodyType;	
	
	/**
	 * @see com.odcgroup.page.ui.figure.AbstractHeaderFigure#getCaptionFont()
	 */
	protected Font getCaptionFont() {
		return getFigureConstants().getCaptionFont(false);
	}
	
	/**
	 * Gets the width of an Item. This depends upon whether the Item is compacted or not.
	 * 
	 * @param item
	 *            The item to get the width for
	 * @param index
	 *            The index of the Item
	 * @param nbItems
	 *            The number of items
	 * @param itemFont
	 *            The Font of the item
	 * @return int The Width of the Item
	 */
	protected int getItemWidth(Widget item, int index, int nbItems, Font itemFont) {
		String text = getItemText(item, index);
		// +4 to add space between the text and the figure
		return FigureUtilities.getTextWidth(" "+text+" ", itemFont) + 4+PaintUtils.getWidth(item);
	}
	
	@Override
	protected String getItemText(Widget widget, int index) {
 		String text = "";
		IFigure parent = getParent();
		while (parent != null && !(parent instanceof ITabbedPaneFigure)) {
			parent = parent.getParent();
		}
		if (parent != null) {
			ITabFigure tab = ((ITabbedPaneFigure)parent).getTab(index);
			text = tab != null ? tab.getName() : "???";
		}
		return text;
	}	

	/**
	 * Gets the items which need to have a Header.
	 * 
	 * @return List The List of items which should have a Header
	 */
	protected List<Widget> getItems() {
		List<Widget> result = new ArrayList<Widget>();
		for (Widget w : getWidget().getContents()) {
			if (w.getTypeName().equals(bodyType)) {
				result.add(w);
			}
		}
		return result;
	}

	/**
	 * Creates a new TabbedPaneHeaderFigure.
	 * 
	 * @param parentFigure
	 *            the parent figure
	 * @param bodyType The type of Widget that we should create Headers for
	 */
	public TabbedPaneHeaderFigure(IWidgetFigure parentFigure, String bodyType) {
		super(parentFigure);
		this.bodyType = bodyType;
	}

}
