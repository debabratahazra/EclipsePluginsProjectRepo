package com.odcgroup.page.ui.dnd;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.AggregationUtils;
import com.odcgroup.page.model.util.SearchModuleUtils;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.PageUIConstants;
import com.odcgroup.page.ui.WidgetCreationFactory;
import com.odcgroup.page.ui.util.PaletteUtils;

/**
 * This class manages the drag&drop of widget from the Palette
 *
 * @author atr
 * @since DS 1.40.0
 */
public class WidgetTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {
	
	/** The normal background color of the Widget (before it has been highlighted. */
	private Color normalBackgroundColor = null;
	
	/** last visited edit part */
	private GraphicalEditPart lastEditPart = null;
	/** dropped widget */
	Widget droppedWidget =null;
	/**
	 * Check special D&N rules that cannot yet be expressed using accountability rules.
	 * 
	 * @param targetWidget
	 * @param droppedWidget
	 * 
	 * @return {@code true} if the droppedWidget can be dropped into the target Widget.
	 */
	private boolean checkSpecialAccountabilityRules(Widget targetWidget, Widget droppedWidget) {
		boolean accept = true;
		// check search conditions
		accept = SearchModuleUtils.canContainChild(targetWidget, droppedWidget);
		// check table column conditions
		try {
			ITableColumn column = TableHelper.getTableColumn(targetWidget);
			accept = !(column.isPlaceHolder() && column.getTable().displayCheckboxOnTreeNodes());
		} catch (IllegalArgumentException ex) {
			// not a table column. ignore.
		}
		return accept;
	}
	
	/**
	 * @param viewer
	 */
	public WidgetTemplateTransferDropTargetListener(EditPartViewer viewer) {
		super(viewer);
		setEnablementDeterminedByCommand(false);
	}
	
 	/**
 	 * @see org.eclipse.gef.dnd.TemplateTransferDropTargetListener#getFactory(java.lang.Object)
 	 */
 	protected CreationFactory getFactory(Object template) {
    	if (template instanceof CreationFactory) {
    		return (CreationFactory)template;
    	}
		return new WidgetCreationFactory((WidgetTemplate) template);
    }
	
	/**
	 * @see org.eclipse.gef.dnd.TemplateTransferDropTargetListener#handleDragOver()
	 */
	protected void handleDragOver() {
		getCurrentEvent().detail = DND.DROP_NONE;
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		
		if (targetEditPart instanceof GraphicalEditPart) {
			if (lastEditPart != targetEditPart) {
				if (lastEditPart != null && normalBackgroundColor != null) {
					lastEditPart.getFigure().setBackgroundColor(normalBackgroundColor);
				}
				lastEditPart = (GraphicalEditPart)targetEditPart;
				normalBackgroundColor = lastEditPart.getFigure().getBackgroundColor();
			}
		}
		
		CreationFactory factory = (CreationFactory)TemplateTransfer.getInstance().getTemplate();
		 droppedWidget = (Widget)factory.getNewObject();
		WidgetType droppedWidgetType = (WidgetType)factory.getObjectType();
		Object obj = targetEditPart.getModel();
		if (obj instanceof Widget) {
			Widget targetWidget = (Widget) targetEditPart.getModel();
			
			if (! AggregationUtils.isEnteringAggregation(targetWidget)) {
				if (WidgetUtils.canContainChild(targetWidget, droppedWidgetType, false)) {
					if (checkSpecialAccountabilityRules(targetWidget, droppedWidget)) {
						getCurrentEvent().detail = DND.DROP_COPY;
						((GraphicalEditPart)targetEditPart).getFigure().setBackgroundColor(PageUIConstants.getHighLightColor());
					}
				}
			}
		} 
		
		return;
	}

	/**
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent event) {
		if (lastEditPart != null && normalBackgroundColor != null) {
			lastEditPart.getFigure().setBackgroundColor(normalBackgroundColor);
		}
		super.dragLeave(event);
	}
	
	/**
	 * handle drop the widget.
	 * check if the drop of the widget is in the palette list.
	 */
	protected void handleDrop() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		Object obj = targetEditPart.getModel();
		if (obj instanceof Widget) {
			Widget targetWidget=((Widget)obj);
		if (PaletteUtils.isWidgetInPaletteList(droppedWidget,targetWidget.getRootWidget())) {
			super.handleDrop();
		  }
		}
	
	  }
	
}