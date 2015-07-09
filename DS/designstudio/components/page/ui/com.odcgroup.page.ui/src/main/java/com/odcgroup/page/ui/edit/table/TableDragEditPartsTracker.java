package com.odcgroup.page.ui.edit.table;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.swt.SWT;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.figure.table.Table;
import com.odcgroup.page.ui.figure.table.TableHeaderFigure;

/**
 * Drag tracker edit part for the Table widget.
 * 
 * @author Gary Hayes
 */
public class TableDragEditPartsTracker extends DragEditPartsTracker{
	
	/**
	 * Constructs a new DragEditPartsTracker with the given source edit part.
	 * @param sourceEditPart the source edit part
	 */
	public TableDragEditPartsTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}
	
	/**
	 * Performs the appropriate selection action based on the selection state of the source.
	 * We select the ColumnHeader edit part from the selected TableHeader edit part
	 * if the selection is outside the bounds of the TableHeader then the table edit part
	 * is returned. 
	 */
	protected void performSelection() {
		// Copied from the base class
		if (hasSelectionOccurred())
			return;
		setFlag(FLAG_SELECTION_PERFORMED, true);
		EditPartViewer viewer = getCurrentViewer();
		List selectedObjects = viewer.getSelectedEditParts();
		
		Point location = getLocation();
		// table edit part
		EditPart currentEditPart = getSourceEditPart();
		// columns edit part
		List<EditPart> editParts = currentEditPart.getChildren();
		EditPart columnEditPart = searchColumn(editParts, location);
		EditPart chep = null;
		if (columnEditPart != null) {
			chep = getColumnHeaderEditPart(columnEditPart);	
		}		
		if (chep == null) {
			// No Column Header was selected. Therefore he must have not
			// Have clicked on the TableHeader but on the Table itself
			chep = getSourceEditPart();
		}
		
		// Almost copied from the base class except that we don't use the SourceEditPart
		if (getCurrentInput().isModKeyDown(SWT.MOD1)) {
			if (selectedObjects.contains(chep))
				viewer.deselect(chep);
			else
				viewer.appendSelection(chep);
		} else if (getCurrentInput().isShiftKeyDown())
			viewer.appendSelection(chep);
		else {
			viewer.select(chep);
		}
		viewer.select(chep);
		chep.setSelected(EditPart.SELECTED_PRIMARY);
		viewer.setFocus(chep);		
	}

	/**
	 * Search the column that include the specified location
	 * 
	 * @param editParts
	 * 			The list of edit parts
	 * @param location
	 * 			The actual location of the user selection
	 * @return EditPart returns the corresponding column edit part of the user selection
	 * 	
	 */
	private EditPart searchColumn(List<EditPart> editParts, Point location) {
		WidgetEditPart tableEditPart = (WidgetEditPart) getSourceEditPart();
		
		Table t = (Table) tableEditPart.getFigure();
		TableHeaderFigure th  = (TableHeaderFigure) t.getHeaderFigure();
		
		Point p = new Point(location);
		t.translateToRelative(p);
		Rectangle b = t.getBounds();
		p.x = p.x - b.x;
		p.y = p.y - b.y;
		
		int index = th.getSelectedHeader(p);
		if (index > -1) {
			return editParts.get(index);
		}
		return null;
	}
	
	
	/**
	 * Gets the ColumnHeader of the selected column
	 * 
	 * @param columnEditPart
	 * 			The selected column edit part
	 * @return EditPart returns the corresponding ColumnHeader edit part
	 */
	private EditPart getColumnHeaderEditPart(EditPart columnEditPart){
		return (EditPart)columnEditPart.getChildren().get(WidgetTypeConstants.COLUMN_HEADER_INDEX);
	}
	
}
