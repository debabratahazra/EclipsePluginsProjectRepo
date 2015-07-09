package com.odcgroup.page.external.mdf;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.swt.dnd.DND;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.domain.ui.dnd.DomainElementTransfer;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.AggregationUtils;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.transformmodel.ui.builder.WidgetBuilder;
import com.odcgroup.page.transformmodel.ui.builder.WidgetBuilderFactory;
import com.odcgroup.page.ui.request.IncludeRequest;
import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Enable certain types of resources to be dragged onto the Page / Module / Fragment.
 * 
 * @author atr
 */
public class MdfElementTransferDropListener extends AbstractTransferDropTargetListener {
	
	/** true whenever the mdf element is dropped in a grid cell widget */
	private boolean dropInGridCell = false;

	/** true whenever the mdf element is dropped in a Table/Tree widget */
	private boolean dropInTableTree = false;
	
	/** true whenever the mdf element is dropped in a MatrixContentCell widget */
	private boolean dropInMatrixContentCell = false;
	
	/** true whenever the mdf element is dropped in a MatrixAxis widget */
	private boolean dropInMatrixAxis = false;
	
	/** true whenever the mdf element is dropped in a MatrixExtraColumn widget */
	private boolean dropInMatrixExtraColumn = false;
	
	private boolean dropInMatrixContentCellBox = false;

	/**
	 * Create a new ResourceTransferDropTargetListener.
	 * 
	 * @param viewer
	 *            The EditPartViewer
	 */
	public MdfElementTransferDropListener(EditPartViewer viewer) {
		super(viewer, DomainElementTransfer.getInstance());
	}

	/**
	 * Returns true if the transfer should be accepted.
	 * 
	 * @param targetWidget
	 *            The Widget to test
	 * @return boolean True if the transfer should be accepted
	 */
	private boolean acceptTransfer(Widget targetWidget) {
		
		WidgetType type = targetWidget.getType();
		if (!type.isCanDropDomainElement()) {
			// dataset attribute cannot be dropped in this widget
			return false;
		}
		
		// check now special contextual conditions.
		
		//DS-3926
		if (targetWidget.getTypeName().equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
			if (isContainedInTableColumn(targetWidget.getParent()))  {
				return false;
			}
		}
		
		return true;
		
	}

	/**
	 * Override the base-class version to change the current event's detail from DND.DROP_MOVE to DND.DROP_COPY since
	 * this is essentially what we are doing when we include another resource. Note that this method is not strictly
	 * correct since we set the detail to DND.DROP_COPY all the time even if the EditPart did not accept the drop.
	 */
	protected void handleDragOver() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		Widget targetWidget = (Widget) targetEditPart.getAdapter(Widget.class);

		getCurrentEvent().detail = DND.DROP_NONE;

		if (targetWidget != null) {
			if (!AggregationUtils.isInAggregation(targetWidget) && acceptTransfer(targetWidget)) {
				getCurrentEvent().detail = DND.DROP_COPY;
			}
		}
	}

	/**
	 * Handles the drop of multiple mdf elements.
	 */
	protected void handleDrop() {

		// the list of domain elements to be dropped
		List<MdfModelElement> domainElements = (List<MdfModelElement>) getCurrentEvent().data;
		if (domainElements.size() != 0) {
		
			EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
			Widget parentWidget = (Widget) targetEditPart.getAdapter(Widget.class);
			
			IOfsProject ofsProject = EclipseUtils.findCurrentProject();
			WidgetBuilder builder = WidgetBuilderFactory.createRootBuilder(ofsProject, parentWidget, domainElements);
			
			// TODO refactor : must be model driven
			String type = parentWidget.getTypeName();
			dropInGridCell = type.equals("GridCell");
			dropInTableTree = type.equals("TableTree");
			dropInMatrixContentCell = type.equals(WidgetTypeConstants.MATRIX_CONTENTCELL);
			dropInMatrixAxis = type.equals(WidgetTypeConstants.MATRIX_AXIS);
			dropInMatrixExtraColumn = type.equals(WidgetTypeConstants.MATRIX_EXTRACOLUMN);
			dropInMatrixContentCellBox = type.equals(WidgetTypeConstants.BOX) && isContainedInMatrixCell(parentWidget.getParent());
			
			//DS-3926
			if (type.equals(WidgetTypeConstants.CONDITIONAL_BODY) 
					&& isContainedInTableColumn(parentWidget)) {
				return;
			}
			((MultipleWidgetCreateRequest) getTargetRequest()).setNewObjects(builder.buildWidgets());
			
			try {
				super.handleDrop();
			} catch (Exception ex) {
				Logger.error("Unable to drop element/s into the edit view", ex);
			}
		}
	}	
	
	// return true if table-column is found
	private boolean isContainedInTableColumn(Widget widget) {
		while (widget != null && ! widget.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
			widget = widget.getParent();
		}
		return widget != null;
	}
	
//	/**
//	 * @param parent
//	 * @return
//	 */
//	private boolean isContainedInPlaceHolderColumn(Widget parent) {
//		if(parent.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
//			ITableColumn column = TableHelper.getTableColumn(parent);
//			if (column != null && column.isPlaceHolder()) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * @param parent
	 * @return
	 */
	private boolean isContainedInMatrixCell(Widget parent) {
		Widget container = MatrixHelper.getParentMatrixContentCell(parent);
		return container != null;
	}
	
	
	/**
	 * The target request is an MdfRequest.
	 * 
	 * @return Request The target request
	 */
	protected Request createTargetRequest() {
		Request request = null;
		if (dropInGridCell) {
			request = new MdfGridRequest();
			dropInGridCell = false;
		} else if (dropInTableTree) {
			request = new MdfTableRequest();
			dropInTableTree = false;
		} else if (dropInMatrixContentCell) {
			request = new MdfMatrixContentCellRequest();
			dropInMatrixContentCell = false;
		} else if (dropInMatrixAxis) {
			request = new MdfMatrixAxisRequest();
			dropInMatrixAxis = false;
		} else if (dropInMatrixExtraColumn) {
			request = new MdfExtraColumnRequest();
			dropInMatrixExtraColumn = false;
		} else if (dropInMatrixContentCellBox) {
			request = new MdfMatrixContentBoxRequest();
			dropInMatrixContentCellBox = false;
		} else {
			request = new MdfRequest();
		}
		return request;
	}

	/**
	 * Assumes that the target request is a {@link IncludeRequest}.
	 */
	protected void updateTargetRequest() {
		((MultipleWidgetCreateRequest) getTargetRequest()).setLocation(getDropLocation());
	}

	/**
	 * Override the base-class version to set the target EditPart correctly. The target EditPart is simply the EditPart
	 * which contains the figure which contains the location of the mouse when the user releases the mouse button.
	 */
	protected void updateTargetEditPart() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		setTargetEditPart(targetEditPart);
	}
}