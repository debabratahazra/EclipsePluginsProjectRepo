package com.odcgroup.page.ui.edit.table;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.table.ColumnBody;
import com.odcgroup.page.ui.figure.table.Item;

/**
 * Handles direct-edit requests for column bodies.
 * 
 * @author Gary Hayes
 */
public class ColumnBodyDirectEditPolicy extends DirectEditPolicy {
	
	/**
	 * Gets the Command to be executed.
	 * 
	 * @param request The request
	 * @return Command The Command to be executed
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		SelectedItemAndIndex si = getSelectedChildItem(request);
		Widget w = si.item.getWidget();
		
		String pv = (String) request.getCellEditor().getValue();
		
		String newPv = WidgetUtils.getPreviewValue(w, pv, si.index);
		
		Property p = w.findProperty(PropertyTypeConstants.PREVIEW_VALUE);
		if (p != null) {
			return new UpdatePropertyCommand(p, newPv);
		}
		
		return null;
	}

	/**
	 * Show the current edited value.
	 * 
	 * @param request The request
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
	}
	
	/**
	 * Gets the selected child Item and its index. 
	 * Returns null if no child Item has been selected.
	 * 
	 * @param request The request
	 * @return SelectedItemAndIndex The SelectedItemAndIndex
	 */
	private SelectedItemAndIndex getSelectedChildItem(DirectEditRequest request) {
		WidgetEditPart wep = (WidgetEditPart) getHost();
		ColumnBody cb = (ColumnBody) wep.getFigure();

		// The location is null when we arrive here. I do not know why. Therefore I use
		// the position of the SWT Text object
		org.eclipse.swt.graphics.Rectangle rrr = (org.eclipse.swt.graphics.Rectangle) request.getCellEditor().getControl().getBounds();
		Point l = new Point(rrr.x, rrr.y);
		
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
			if (! (awf instanceof Item)) {
				continue;
			}
			
			Rectangle cr = awf.getBounds();

			for (int j = 0; j < pageSize; ++j) {
				Rectangle dr = new Rectangle(cr);
				dr.y = (int) (dr.y + y * j);
				
				if (dr.contains(rl)) {
					SelectedItemAndIndex si = new SelectedItemAndIndex();
					si.index = j;
					si.item = awf;
					return si;
				}
			}
			
		}

		// Not found
		return null;
	}

	/**
	 * Utility class to return the actual Item and the index.
	 * 
	 * @author Gary Hayes
	 */
	private static class SelectedItemAndIndex {
		/** The item. */
		public IWidgetFigure item;
		/** The index. */
		public int index;
	}
}