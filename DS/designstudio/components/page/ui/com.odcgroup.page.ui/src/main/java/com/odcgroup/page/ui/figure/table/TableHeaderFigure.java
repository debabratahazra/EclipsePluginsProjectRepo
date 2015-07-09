package com.odcgroup.page.ui.figure.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * Represents a Header in a complex Widget such as a Table or Tabbed-Pane.
 * 
 * @author Gary Hayes
 */
public class TableHeaderFigure extends AbstractHeaderFigure {

	/** The type of Widget that we should create Headers for. */
	private String bodyType;

	/**
	 * If this is not null then the Column Header widths must be the same size of the widths of the corresponding
	 * children in the body figure.
	 */
	private IWidgetFigure body;

	/**
	 * Creates a new HeaderFigure.
	 * 
	 * @param parentFigure
	 *            the parent figure
	 * @param bodyType
	 *            The WidgetType that we should create headers for
	 * @param body
	 *            If this is not null then the Column Header widths must be the same size of the widths of the
	 *            corresponding children in the body figure
	 */
	public TableHeaderFigure(IWidgetFigure parentFigure, String bodyType, IWidgetFigure body) {
		super(parentFigure);
		this.bodyType = bodyType;
		this.body = body;
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
	 * Gets the text associated with a specific item. This is used in the Header.
	 * 
	 * @param widget
	 *            The Widget to get the text for
	 * @param index
	 *            The index
	 * @return String The text
	 */
	protected String getItemText(Widget widget, int index) {
		// The Column contains a ColumnHeader as the first item and a ColumnBody as the second ones
		Widget header = widget.getContents().get(0);
		Property p = header.findProperty(PropertyTypeConstants.COLUMN_NAME);
		String s = "";
		if (p != null) {
			s = p.getValue();
		}

		if (StringUtils.isEmpty(s)) {
			return "            ";
		}

		return s;
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
		if (body != null) {
			IFigure bf = (IFigure) body.getChildren().get(index);
			return bf.getBounds().width;
		}

		Rectangle rect = getParent().getBounds();
		// -6 to add a little empty space at the end of the figure
		return (rect.width - 6) / nbItems;
	}


	/**
	 * @see com.odcgroup.page.ui.figure.AbstractHeaderFigure#getCaptionFont()
	 */
	protected Font getCaptionFont() {
		return getFigureConstants().getCaptionFont(false);
	}
}
