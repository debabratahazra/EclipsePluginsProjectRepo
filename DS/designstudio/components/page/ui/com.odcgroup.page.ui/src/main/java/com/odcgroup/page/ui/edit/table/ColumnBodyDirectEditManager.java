package com.odcgroup.page.ui.edit.table;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.table.ColumnBody;
import com.odcgroup.page.ui.figure.table.Item;

/**
 * The DirectEditManager for Column bodies. This works differently from other Widgets since the Items contained within
 * the ColumnBody are painted n times where n is the page size. However in reality there is only one Item.
 * 
 * <pre>
 * Item (exists)
 * ----
 * Item (repainted)
 * ----
 * Item (repainted)
 * </pre>
 * 
 * If the user clicks the first Item then this Item is selected. If the user clicks the second or third Item's then
 * normally the ColumnBody would be selected. This DirectEditManager and corresponding classes, Locators... ensures that
 * the TextEditor is placed over the repainted Item. It also ensures that the Property 'previewValue' is correctly taken
 * from the real Item Widget. Finally it correctly updates the property 'previewValue' Item Widget. Note that for simple
 * Widgets like TextFields the property 'previewValue' applies to the Widget itself. In the case of an Item the property
 * 'previewValue' is actually stored as a String of comma-separated values one for each element determined by the page
 * size of the parent Table.
 * 
 * @author Gary Hayes
 */
public class ColumnBodyDirectEditManager extends DirectEditManager {

	/** The request. */
	private LocationRequest request;

	/** The selected child Item. */
	private IWidgetFigure selectedChildItem;

	/** The selected index. */
	private int selectedIndex;

	/**
	 * Constructs a new ColumnBodyDirectEditManager for the given source edit part.
	 * 
	 * @param source
	 *            the source edit part
	 * @param request
	 *            The LocationRequest
	 */
	public ColumnBodyDirectEditManager(GraphicalEditPart source, LocationRequest request) {
		super(source, TextCellEditor.class, new ColumnBodyCellEditorLocator((IWidgetFigure) source.getFigure(),
				request));

		this.request = request;
	}

	/**
	 * Override the base-class version to only show the CellEditor if a childItem has been selected.
	 */
	public void show() {
		if (getSelectedChildItem() != null) {
			super.show();
		}
	}

	/**
	 * Initialises the CellEditor.
	 */
	protected void initCellEditor() {
		IWidgetFigure awf = getSelectedChildItem();
		Widget w = awf.getWidget();
		List<String> pvs = WidgetUtils.getPreviewValues(w);
		String s = "";
		if (pvs.size() > selectedIndex) {
			s = pvs.get(selectedIndex);
		}

		getCellEditor().setValue(s);
	}

	/**
	 * Gets the LocationRequest.
	 * 
	 * @return LocationRequest
	 */
	protected LocationRequest getRequest() {
		return request;
	}

	/**
	 * Gets the selected child Item. Returns null if no child Item has been selected.
	 * 
	 * @return IWidgetFigure The child Item
	 */
	private IWidgetFigure getSelectedChildItem() {
		if (selectedChildItem != null) {
			return selectedChildItem;
		}
		
		WidgetEditPart wep = (WidgetEditPart) getEditPart();
		ColumnBody cb = (ColumnBody) wep.getFigure();

		LocationRequest sr = getRequest();	
		Point l = sr.getLocation();

		// Calculate the relative position of the mouse-click
		Rectangle r = new Rectangle(cb.getBounds());
		cb.translateToAbsolute(r);
		Point rl = new Point(l.x - r.x, l.y - r.y);

		int pageSize = cb.getParentTable().getPageSize();
		float y;
		if (cb.isGrouped()) {
			y = ((float) cb.getBounds().height - ColumnBody.IMAGE_HEIGHT) / (float) pageSize;
		} else {
			y = ((float) cb.getBounds().height) / (float) pageSize;
		}

		for (int i = 0; i < wep.getChildren().size(); ++i) {
			WidgetEditPart cwep = (WidgetEditPart) wep.getChildren().get(i);

			IWidgetFigure awf = (IWidgetFigure) cwep.getFigure();
			if (!(awf instanceof Item)) {
				continue;
			}

			Rectangle cr = awf.getBounds();

			for (int j = 0; j < pageSize; ++j) {
				Rectangle dr = new Rectangle(cr);
				dr.y = (int) (dr.y + y * j);

				if (dr.contains(rl)) {
					selectedChildItem = awf;
					selectedIndex = j;
					return selectedChildItem;
				}
			}

		}

		// Not found
		return null;
	}
}