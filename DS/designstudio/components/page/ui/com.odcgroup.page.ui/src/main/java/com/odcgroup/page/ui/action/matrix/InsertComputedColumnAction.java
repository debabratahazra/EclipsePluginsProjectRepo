package com.odcgroup.page.ui.action.matrix;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.ui.action.AbstractGenericAction;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.matrix.InsertMatrixContentCellItemCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertComputedColumnAction extends AbstractGenericAction {
	
	private Widget boxWidget = null;

	/**
	 * @param parameters
	 */
	public InsertComputedColumnAction(ActionParameters parameters) {
		super(parameters);
		setEnabled(calculateEnabled());
	}
	
	/**
	 * @return
	 */
	protected IMatrixContentCell getContentCell() {
		Widget widget = getSelectedWidget();
		IMatrixContentCell contentCell = null;
		String wt = widget.getTypeName();
		if (WidgetTypeConstants.MATRIX_CONTENTCELL.equals(wt)) {
			contentCell = MatrixHelper.getMatrixContentCell(widget);
			boxWidget = null;
		} else if (WidgetTypeConstants.BOX.equals(wt)) {
			Widget parent = MatrixHelper.getParentMatrixContentCell(widget);
			contentCell = MatrixHelper.getMatrixContentCell(parent);
			boxWidget = widget;
		} else if (WidgetTypeConstants.CONDITIONAL_BODY.equals(wt)) {
			Widget parent = MatrixHelper.getParentMatrixContentCell(widget);
			contentCell = MatrixHelper.getMatrixContentCell(parent);
			boxWidget = widget;			
		}
		return contentCell;
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		Widget widget = getSelectedWidget();
		String wt = widget.getTypeName();
		if (WidgetTypeConstants.MATRIX_CONTENTCELL.equals(wt)) {
			return true;
		} else if (WidgetTypeConstants.BOX.equals(wt) 
				|| WidgetTypeConstants.CONDITIONAL_BODY.equals(wt)) {
			Widget parent = MatrixHelper.getParentMatrixContentCell(widget);
			if (parent != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		execute(new InsertMatrixContentCellItemCommand(getContentCell(), boxWidget));
	}


}
