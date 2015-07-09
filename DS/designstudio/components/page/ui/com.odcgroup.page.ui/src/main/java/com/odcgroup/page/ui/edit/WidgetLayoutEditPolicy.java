package com.odcgroup.page.ui.edit;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.HORIZONTAL_ALIGNMENT;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.odcgroup.page.external.mdf.MdfExtraColumnRequest;
import com.odcgroup.page.external.mdf.MdfGridRequest;
import com.odcgroup.page.external.mdf.MdfMatrixAxisRequest;
import com.odcgroup.page.external.mdf.MdfMatrixContentBoxRequest;
import com.odcgroup.page.external.mdf.MdfMatrixContentCellRequest;
import com.odcgroup.page.external.mdf.MdfRequest;
import com.odcgroup.page.external.mdf.MdfTableColumnRequest;
import com.odcgroup.page.external.mdf.MdfTableRequest;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.AggregationUtils;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.SearchModuleUtils;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.GridCell;
import com.odcgroup.page.model.widgets.impl.GridCellAdapter;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.command.AddWidgetCommand;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.CreateWidgetCommand;
import com.odcgroup.page.ui.command.MoveChildWidgetCommand;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.command.matrix.InsertMatrixContentCellItemBoxCommand;
import com.odcgroup.page.ui.command.matrix.MatrixCellInsertItemWidgetCommand;
import com.odcgroup.page.ui.command.matrix.MatrixExtraColumnInsertItemCommand;
import com.odcgroup.page.ui.command.matrix.UpdateMatrixAxisCommand;
import com.odcgroup.page.ui.command.table.TableInsertColumnWidgetCommand;
import com.odcgroup.page.ui.figure.CalculatingLayout;
import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;
import com.odcgroup.page.ui.util.EclipseUtils;


/**
 * This layout policy is responsable for the layout and resizing of Widgets.
 * 
 * @author Gary Hayes
 */
public class WidgetLayoutEditPolicy extends LayoutEditPolicy {
	
	/**
	 * Creates an EditPolicy which adds selection handles to the Widgets, thus allowing them to 
	 * be resized. This method is called by {@link LayoutEditPolicy#decorateChild(EditPart)}
	 * whenever a new EditPart is added to the container.
	 * 
	 * @param child The child EditPart to create the EditPolicy for
	 * @return EditPolicy The newly created EditPolicy
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new WidgetNonResizableEditPolicy();
	}
	
	/**
	 * Factors incoming requests into various specific methods.
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	public Command getCommand(Request request) {

		if (EclipseUtils.modelIsReadonly()) {
			return null;
		}
		
		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_GRID.equals(request.getType())) {
			return getCreateInGridCommand((MdfGridRequest) request);
		}

		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_TABLE.equals(request.getType())) {
			return getCreateInTableCommand((MdfTableRequest) request);
		}

		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_TABLE_COLUMN.equals(request.getType())) {
			return getCreateInTableColumnCommand((MdfTableColumnRequest) request);
		}

		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE.equals(request.getType())) {
			return getCreateCommand((MultipleWidgetCreateRequest) request);
		}

		if (PageUIRequestConstants.REQ_MODIFY.equals(request.getType())) {
			return getModifyCommand((MdfRequest) request);
		}	
		
		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXCELL.equals(request.getType())) {
			return getCreateInMatrixCellCommand((MdfMatrixContentCellRequest) request);
		}	
		
		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXAXIS.equals(request.getType())) {
			return getCreateInMatrixAxisCommand((MdfMatrixAxisRequest) request);
		}	
		
		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXEXTRA.equals(request.getType())) {
			return getCreateInMatrixExtraCommand((MdfExtraColumnRequest) request);
		}
		
		if (PageUIRequestConstants.REQ_MULTIPLE_CREATE_IN_MATRIXCELLBOX.equals(request.getType())) {
			return getCreateInMatrixCellBoxCommand((MdfMatrixContentBoxRequest) request);
		}

		return super.getCommand(request);
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected Command getCreateInMatrixAxisCommand(MdfMatrixAxisRequest request) {
		
		CompoundCommand cc = new CompoundCommand();
		Widget matrixAxisWidget = (Widget)getHost().getModel();
		List<Widget> widgets = (List<Widget>)request.getNewObjects();
		IMatrixAxis contentCell = MatrixHelper.getMatrixAxis(matrixAxisWidget);

		if (widgets.size() > 1) {
			return null;
		}
		Collections.reverse(widgets);
		for (Widget widget : widgets) {
			UpdateMatrixAxisCommand tic = new UpdateMatrixAxisCommand(contentCell, widget);
			cc.add(tic);
		}
		
		return cc;	
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected Command getCreateInMatrixExtraCommand(MdfExtraColumnRequest request) {		
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget matrixCellWidget = (Widget)getHost().getModel();
		// build the widgets list
		List<Widget> widgets = (List<Widget>)request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(matrixCellWidget)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(matrixCellWidget, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		IMatrixExtraColumn contentCell = MatrixHelper.getMatrixExtraColumn(matrixCellWidget);

		Collections.reverse(widgets);
		for (Widget widget : widgets) {
			MatrixExtraColumnInsertItemCommand tic = new MatrixExtraColumnInsertItemCommand(contentCell, widget);
			cc.add(tic);
		}
		return cc;
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected Command getCreateInMatrixCellBoxCommand(MdfMatrixContentBoxRequest request) {
		
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget box = (Widget)getHost().getModel();
		// build the widgets list
		List<Widget> widgets = (List<Widget>)request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(box)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(box, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		

		Collections.reverse(widgets);
		for (Widget widget : widgets) {
			BaseCommand tic = new InsertMatrixContentCellItemBoxCommand(box, widget);
			cc.add(tic);
		}
		
		return cc;		
	
		
	}
	
	
	
	
	/**
	 * @param request
	 * @return
	 */
	protected Command getCreateInMatrixCellCommand(MdfMatrixContentCellRequest request) {
		
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget matrixCellWidget = (Widget)getHost().getModel();
		// build the widgets list
		List<Widget> widgets = (List<Widget>)request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(matrixCellWidget)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(matrixCellWidget, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		IMatrixContentCell contentCell = MatrixHelper.getMatrixContentCell(matrixCellWidget);

		Collections.reverse(widgets);
		for (Widget widget : widgets) {
			MatrixCellInsertItemWidgetCommand tic = new MatrixCellInsertItemWidgetCommand(contentCell, widget);
			cc.add(tic);
		}
		
		return cc;		
	}

	/**
	 * Gets the ModifyCommand.
	 * 
	 * @param request
	 *            The MdfRequest probably received via drag-and-drop
	 * @return Command The Command
	 */
	protected Command getModifyCommand(MdfRequest request) {
		if (request.getProperties().size() == 0) {
			return null;
		}
		
		CompoundCommand cc = new CompoundCommand();
		Widget pw = getWidgetEditPart().getWidget();
		for (Property p : request.getProperties()) {
			Property pp = pw.findProperty(p.getType());
			if (pp != null) {
				UpdatePropertyCommand upc = new UpdatePropertyCommand(pp, p.getValue());
				cc.add(upc);
			}
		}
		
		return cc;
	}

	/**
	 * Gets the CreateCommand.
	 * 
	 * @param request
	 *            The CreateRequest received from (probably) the CreationTool.
	 * @return Command The CreationCommand or null if the model Object cannot be created here.
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Widget parent = (Widget) getHost().getModel();
		Widget child = (Widget) request.getNewObject();
		
		if (AggregationUtils.isEnteringAggregation(parent)) {
			// We cannot add Widgets to aggregations
			return null;
		}

		if (WidgetUtils.canContainChild(parent, child) == false) {
			return null;
		}
		
		if (SearchModuleUtils.canContainChild(parent, child) == false) {
			return null;
		}
		//DS-3911
		if (WidgetTypeConstants.BOX.equals(parent.getTypeName())) {
			Widget matrixcell = MatrixHelper.getParentMatrixContentCell(parent);
			if (matrixcell != null) {
				String childType = child.getTypeName();
				if (!(WidgetTypeConstants.CONDITIONAL.equals(childType) 
						|| WidgetTypeConstants.BOX.equals(childType)
						|| WidgetTypeConstants.SPACER.equals(childType)
						|| WidgetTypeConstants.GLUE.equals(childType))) {
					return null;
				}
			}
		}
		
		if (WidgetTypeConstants.CONDITIONAL_BODY.equals(parent.getTypeName())) {
			Widget matrixcell = MatrixHelper.getParentMatrixContentCell(parent);
			if (matrixcell != null) {
				String childType = child.getTypeName();
				if (!(WidgetTypeConstants.BOX.equals(childType) 
						|| WidgetTypeConstants.SPACER.equals(childType)
						|| WidgetTypeConstants.GLUE.equals(childType))) {
					return null;
				}
			}
		}
		// end ds-3911
		
		// Calculate the index of the new Widget
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		int index = calculateIndex(location);
				
		return WidgetCommandFactory.getCreateWidgetCommand(parent, child, index, location);
	}
	
	/**
	 * Gets the CreateCommand.
	 * 
	 * @param request
	 *            The MultipleWidgetCreateRequest received from (probably) the BOM tree.
	 * @return Command The CreationCommand or null if the model Object cannot be created here.
	 */
	protected Command getCreateCommand(MultipleWidgetCreateRequest request) {
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget parent = (Widget)getHost().getModel();
		// build the widgets list
		List widgets = request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(parent)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(parent, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		// Calculate the index of the added Widget		
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		int newIndex = calculateIndex(location);
		
		for (Iterator it = widgets.iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			CreateWidgetCommand awc = new CreateWidgetCommand(parent, child, newIndex, location);
			cc.add(awc);
			
			newIndex +=1;
		}
		
		return cc;	
	}

	/**
	 * Gets the CreateInGridCommand.
	 * 
	 * @param request
	 *            The MultipleWidgetCreateRequest received from (probably) the BOM tree.
	 * @return Command The CreationCommand or null if the model Object cannot be created here.
	 */
	protected Command getCreateInGridCommand(MdfGridRequest request) {
		
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget cellWidget = (Widget)getHost().getModel();
		// build the widgets list
		List widgets = request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(cellWidget)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(cellWidget, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		// Calculate the index of the added Widget		
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		int insertPosition = calculateIndex(location);
		
		GridCell cell = new GridCellAdapter(cellWidget);
		int rowIndex = cell.getRowIndex();
		int colIndex = cell.getColumnIndex();
		
		Grid grid = cell.getGrid();
		int nbRows = grid.getRowCount();
		int nbColumns = grid.getColumnCount();
		
		if (colIndex < nbColumns-1) {
			int index = 0;
			int nbWidgets = widgets.size();
			while (index < nbWidgets) {
				// label widget
				Widget label = (Widget) widgets.get(index);
				label.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}");
				
				cellWidget = grid.getCellWidgetAt(rowIndex, colIndex);
				AddWidgetCommand awc = new AddWidgetCommand(cellWidget, label, insertPosition);
				cc.add(awc);
				index++;
				// field widget
				if (index < nbWidgets) {
					Widget field = (Widget) widgets.get(index);
					field.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}");

					cellWidget = grid.getCellWidgetAt(rowIndex, colIndex+1);
					awc = new AddWidgetCommand(cellWidget, field);
					cc.add(awc);
					index++;
				}
				rowIndex++;
				if (rowIndex >= nbRows) {
					break;
				}
	
			}
		}
		
		return cc;	
	}
	
	/**
	 * Gets the CreateInTableCommand.
	 * 
	 * @param request
	 *            The MultipleWidgetCreateRequest received from (probably) the BOM tree.
	 * @return Command The CreationCommand or null if the model Object cannot be created here.
	 */
	protected Command getCreateInTableCommand(MdfTableRequest request) {
		
		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget tableWidget = (Widget)getHost().getModel();
		// build the widgets list
		List<Widget> widgets = (List<Widget>)request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(tableWidget)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(tableWidget, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		// Calculate the index of the added Widget		
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		int insertPosition = calculateIndex(location);
		
		// TODO
		ITable table = TableHelper.getTable(tableWidget);

		Collections.reverse(widgets);
		for (Widget widget : widgets) {
			TableInsertColumnWidgetCommand tic = new TableInsertColumnWidgetCommand(table, widget, insertPosition);
			cc.add(tic);
		}
		
		return cc;
	}
	
	/**
	 * Gets the CreateInTableCommand.
	 * 
	 * @param request
	 *            The MultipleWidgetCreateRequest received from (probably) the BOM tree.
	 * @return Command The CreationCommand or null if the model Object cannot be created here.
	 */
	protected Command getCreateInTableColumnCommand(MdfTableColumnRequest request) {

		CompoundCommand cc = new CompoundCommand();

		// retrieve the parent widget
		Widget columnWidget = (Widget)getHost().getModel();
		// build the widgets list
		List widgets = request.getNewObjects();
		
		if (AggregationUtils.isEnteringAggregation(columnWidget)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(columnWidget, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		// Calculate the index of the added Widget		
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		int insertPosition = calculateIndex(location);

		ITableColumn column = TableHelper.getTableColumn(columnWidget);
		int colIndex = column.getColumnIndex();
		
		ITable table = column.getTable();
		// TODO
		
		
		return cc;
	}

	/**
	 * Gets the Container (host) figure.
	 * 
	 * @return IFigure The Container (host) figure
	 */
	private IFigure getContainerFigure() {
		return ((GraphicalEditPart) getHost()).getFigure();
	}	
	
	/**
	 * A move is interpreted here as a change in order of the children. This method obtains
	 * the proper index, and then creates a CompoundCommand containing a ReorderWidgetCommand
	 * for each Widget to be moved.
	 * 
	 * @param request The Request (this MUST be a ChangeBoundsRequest)
	 * @return Command The Command to execute
	 */
	protected Command getMoveChildrenCommand(Request request) {
		ChangeBoundsRequest r = (ChangeBoundsRequest) request;
		
		WidgetEditPart wep = (WidgetEditPart) r.getEditParts().get(0);
		Widget child = wep.getWidget();
		
		if (AggregationUtils.isInAggregation(child)) {
			// Cannot move children which are part of Aggregations
			return null;
		}	
		
		// Calculate the index of the moved Widget
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), r.getLocation());
		int newIndex = calculateIndex(location);
		
		Widget parent = wep.getParentWidget();
		
		WidgetEditPart pwep = (WidgetEditPart) wep.getParent();
		int relativeX = location.x - pwep.getFigure().getBounds().x;
		int relativeY = location.y - pwep.getFigure().getBounds().y;
		Point relativeLocation = new Point(relativeX, relativeY);
		
		MoveChildWidgetCommand command = new MoveChildWidgetCommand(child, parent, newIndex, relativeLocation);

		return command;		
	}
	
	/**
	 * Gets the AddCommand. This is executed when, for example, a Widget
	 * is moved outside the bounds of the Container widget.
	 * 
	 * @param request The Request
	 * @return Command The Command to execute
	 */
	protected Command getAddCommand(Request request) {
		ChangeBoundsRequest r = (ChangeBoundsRequest) request;
		CompoundCommand cc = new CompoundCommand();
		
		WidgetEditPart parentWep = getWidgetEditPart();
		Widget parent = parentWep.getWidget();
		List editParts = r.getEditParts();
		
		// Calculate the index of the added Widget
		Point location = r.getLocation();
		int newIndex = calculateIndex(location);
		
		// Only allow the drap-and-drop if ALL the children can be added to the parent
		List widgets = WidgetEditPartUtils.getWidgets(editParts);
		
		if (AggregationUtils.isChangingAggregation(widgets, parent)) {
			// At least one child is passing an aggregation boundary
			return null;
		}
		
		if (! WidgetUtils.canContainChildren(parent, widgets)) {
			// At least one child is not allowed in the parent
			return null;
		}		
		
		for (Iterator it = editParts.iterator(); it.hasNext();) {
			WidgetEditPart wep = (WidgetEditPart) it.next();
			AddWidgetCommand awc = new AddWidgetCommand(parentWep.getWidget(), wep.getWidget(), newIndex);
			cc.add(awc);
			
			newIndex +=1;
		}
		
		return cc;
	}
		
	/**
	 * This method calculates the index of the IFigure in the parent IFigure collection
	 * which was clicked. If the user clicked between Widgets then the index
	 * after the previous IFigure is retuned.
	 * 
	 * @param location The location
	 * @return index The index
	 */
	private int calculateIndex(Point location) {
		IFigure container = getContainerFigure();		
		LayoutManager lm = container.getLayoutManager();
		if (lm instanceof CalculatingLayout) {
			CalculatingLayout cl = (CalculatingLayout) lm;
			return cl.calculateIndex(container, location);
		}
		
		 return 0;
	}
	
	/**
	 * Gets the host WidgetEditPart.
	 * 
	 * @return WidgetEditPart The host WidgetEditPart
	 */
	private WidgetEditPart getWidgetEditPart() {
		return (WidgetEditPart) getHost();
	}
}
