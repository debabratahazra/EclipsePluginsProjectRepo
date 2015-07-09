package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.filter.DataTypeFilter;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.ui.command.matrix.UpdateMatrixItemComputationCommand;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;

/**
 *
 * @author pkk
 *
 */
public class MatrixItemComputationComposite implements SpecialControl<IMatrixComputationItem> {
	/** */
	private IMatrixComputationItem input;
	/** */
	private DataTypeCombo computationCombo;
	/** */
	private CommandStack commandStack;

	/**
	 * @param parent
	 */
	public MatrixItemComputationComposite(Composite parent) {
		createControls(parent);
	}
	
	/**
	 * @param value
	 */
	protected void selectionChange(String value) {
	}
	
	/**
	 * @param parent
	 */
	protected void createControls(Composite parent) {
		Composite body = new Composite(parent, SWT.NONE);
		body.setBackground(parent.getBackground());
		GridLayout gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);		
		new Label(body, SWT.NONE).setText("Computation");
		computationCombo = new DataTypeCombo(body, TableHelper.getTableUtilities().getColumnComputations(), new ComputationFilter(), true);
		computationCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				DataValue data = computationCombo.getSelectedValue();
				selectionChange(data.getValue());
				Command command = new UpdateMatrixItemComputationCommand(input, data.getValue());
				commandStack.execute(command);
			}
		});
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.SpecialControl#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(IMatrixComputationItem input) {
		this.input = input;
		String value = input.getComputation().getValue();
		computationCombo.resetItems();
		computationCombo.select(value);
		selectionChange(value);
	}	
	
	/**
	 * @param commandStack
	 */
	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	/**
	 *
	 */
	class ComputationFilter implements DataTypeFilter {
		/** */
		private List<DataValue> values = null;
		/** */
		private DataType type = null;

		/**
		 * @see com.odcgroup.page.model.filter.DataTypeFilter#filter(com.odcgroup.page.metamodel.DataType)
		 */
		public List<DataValue> filter(DataType dataType) {
			if (input != null) {
				type = dataType;
				values = new ArrayList<DataValue>();
				values.addAll(dataType.getValues());
				removeDataValue("concatenate");
				removeDataValue("cumul");
				removeDataValue("difference");
				removeDataValue("exchange");
				removeDataValue("fallback");
				removeDataValue("multiply");
				removeDataValue("same");
				removeDataValue("sum");				
				removeDataValue("variation-percent");
				return values;
			}
			return dataType.getValues();
		}
		
		/**
		 * @param value
		 */
		private void removeDataValue(String value) {
			for (DataValue dataValue : type.getValues()) {
				if (dataValue.getValue().equals(value)) {
					values.remove(dataValue);
				}
			}
		}
		
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.SpecialControl#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		computationCombo.setEnabled(enabled);		
	}

}
