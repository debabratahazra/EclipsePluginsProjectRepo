package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateMatrixExtraCommand extends BaseCommand {
	/** */
	private MdfDatasetProperty oldValue;
	/** */
	private MdfDatasetProperty newValue;
	/** */
	private String newType;
	/** */
	private String oldType;
	/** */
	private IMatrixExtra extra;

	/**
	 * @param matrix
	 * @param extra
	 * @param newValue
	 */
	public UpdateMatrixExtraCommand(IMatrix matrix, IMatrixExtra extra, MdfDatasetProperty newValue, String aggregationType) {
		this.oldValue = extra.getAttribute();
		this.oldType = extra.getAggregationType();
		this.newValue = newValue;
		this.extra = extra;
		this.newType = aggregationType;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		extra.setAttribute(newValue);
		extra.setAggregationType(newType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		extra.setAttribute(oldValue);
		extra.setAggregationType(oldType);
	}



}
