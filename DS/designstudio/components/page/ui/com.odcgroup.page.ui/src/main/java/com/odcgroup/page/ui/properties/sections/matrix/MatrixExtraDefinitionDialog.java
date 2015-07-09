package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.filter.DataTypeFilter;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.InsertMatrixExtraCommand;
import com.odcgroup.page.ui.command.matrix.UpdateMatrixExtraCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.DomainAttributesCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtraDefinitionDialog extends TableTransformDialog {

	/** matrix */
	private IMatrix matrix;
	
	/** table extra for edit*/
	private IMatrixExtra extraForEdit;

	/** */
	private ITypeCombo<MdfDatasetProperty> columnCombo;

	/** */
	private DataTypeCombo aggregationCombo;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param matrix
	 */
	public MatrixExtraDefinitionDialog(Shell parentShell, CommandStack commandStack, IMatrix matrix) {
		super(parentShell, commandStack);
		this.matrix = matrix;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param matrix
	 * @param extraForEdit
	 */
	public MatrixExtraDefinitionDialog(Shell parentShell, CommandStack commandStack, IMatrix matrix, IMatrixExtra extraForEdit) {
		this(parentShell, commandStack, matrix);
		this.extraForEdit = extraForEdit;
		if (extraForEdit != null) {
			setEditMode(true);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Extra Data");
		setMessage("Table Extra data definitions");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Extra Data");
	}
	

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		composite.setLayout(gridLayout);

		Composite body = new Group(composite, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		new Label(body, SWT.NONE).setText("Data");		
		columnCombo = new DomainAttributesCombo(body, TableDomainObjectUtil.getDomainAttributes(matrix), getAddedAttributes(matrix), isEditMode());
		columnCombo.addSelectionListener(this);

		new Label(body, SWT.NONE).setText("Aggregation Type");	
		aggregationCombo = new DataTypeCombo(body, MatrixHelper.getAggregationTypes(), 
				new AggregationTypeFilter(), isEditMode());
		aggregationCombo.addSelectionListener(this);
		
		if (isEditMode()) {
			columnCombo.select(extraForEdit.getAttribute().getName());
			aggregationCombo.select(extraForEdit.getAggregationType());
		} else {
			aggregationCombo.getCombo().select(0);
		}

		return composite;
	}	
	
	/**
	 * @param table
	 * @return List
	 */
	private List<MdfDatasetProperty> getAddedAttributes(IMatrix matrix) {
		List<MdfDatasetProperty> list = new ArrayList<MdfDatasetProperty>();
		for (IMatrixExtra sort : matrix.getExtras()) {
			if (!isEditMode() || (isEditMode() && !extraForEdit.getAttribute().equals(sort.getAttribute()))) {		
				list.add(sort.getAttribute());
			}
		}
		return list;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#getCommand()
	 */
	@Override
	protected BaseCommand getCommand() {
		BaseCommand cmd = null;
		if (isEditMode()) {	
			MdfDatasetProperty attribute = columnCombo.getSelectedValue();
			cmd = new UpdateMatrixExtraCommand(matrix, extraForEdit, attribute, aggregationCombo.getSelectedValue().getValue());
		} else {
			MdfDatasetProperty attribute = columnCombo.getSelectedValue();
			if (attribute == null) {
				return null;
			}
			IMatrixExtra extra = MatrixHelper.createMatrixExtra();
			extra.setAttribute(attribute);
			extra.setAggregationType(aggregationCombo.getSelectedValue().getValue());
			cmd = new InsertMatrixExtraCommand(matrix, extra);
		}		
		return cmd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (columnCombo.getSelectedValue()== null) {
			setErrorMessage("Data is not specified");
			return false;
		} 
		setErrorMessage(null);
		return true;
	}
	

	/**
	 *
	 */
	static class AggregationTypeFilter implements DataTypeFilter {

		/**
		 * @see com.odcgroup.page.model.filter.DataTypeFilter#filter(com.odcgroup.page.metamodel.DataType)
		 */
		public List<DataValue> filter(DataType dataType) {
			List<DataValue> values = new ArrayList<DataValue>();
			values.addAll(dataType.getValues());
			for (DataValue dataValue : dataType.getValues()) {
				if (dataValue.getValue().equals("weighted-mean")) {
					values.remove(dataValue);
				}
			}
			return values;
		}
		
	}

}
