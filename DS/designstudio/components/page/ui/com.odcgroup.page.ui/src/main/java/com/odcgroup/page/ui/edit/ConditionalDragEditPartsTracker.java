package com.odcgroup.page.ui.edit;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.swt.SWT;

import com.odcgroup.page.ui.figure.AbstractHeaderFigure;
import com.odcgroup.page.ui.figure.conditional.ConditionalFigure;

/**
 * Drag tracker edit part for the Conditional widget.
 * 
 * @author Gary Hayes
 */
public class ConditionalDragEditPartsTracker extends DragEditPartsTracker{
	
	/**
	 * Constructs a new DragEditPartsTracker with the given source edit part.
	 * @param sourceEditPart the source edit part
	 */
	public ConditionalDragEditPartsTracker(EditPart sourceEditPart) {
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
		EditPart conditionalEditPart = getSourceEditPart();
		List<EditPart> conditionEditParts = conditionalEditPart.getChildren();
		EditPart conditionEditPart = searchConditionalBody(conditionEditParts, location);
		EditPart chep = conditionEditPart;		
		if (chep == null) {
			// No Tab Header was selected. Therefore he must have not
			// Have clicked on the TabHeader but on the TabbedPane itself
			chep = getSourceEditPart();
		}
		
		// Almost copied from the base class except that we don't use the SourceEditPart
		if (getCurrentInput().isModKeyDown(SWT.MOD1)) {
			if (selectedObjects.contains(chep))
				viewer.deselect(chep);
			else
				viewer.appendSelection(chep);
		} else if (getCurrentInput().isShiftKeyDown()) {
			viewer.appendSelection(chep);
		}
		if (chep.isSelectable()) {
			viewer.select(chep);
			chep.setSelected(EditPart.SELECTED_PRIMARY);
			viewer.setFocus(chep);	
		}	
	}

	/**
	 * Search the conditional body that include the specified location
	 * 
	 * @param editParts
	 * 			The list of edit parts
	 * @param location
	 * 			The actual location of the user selection
	 * @return EditPart returns the corresponding tab edit part of the user selection
	 * 	
	 */
	private EditPart searchConditionalBody(List<EditPart> editParts, Point location) {
		WidgetEditPart conditionalEditPart = (WidgetEditPart) getSourceEditPart();
		
		ConditionalFigure t = (ConditionalFigure) conditionalEditPart.getFigure();
		AbstractHeaderFigure th  = (AbstractHeaderFigure) t.getHeaderFigure();
		
		Point p = new Point(location);
		th.translateToRelative(p);
		
		int index = th.getSelectedHeader(p);
		if (index > -1) {
			return editParts.get(index);
		}
		return null;
	}
}
