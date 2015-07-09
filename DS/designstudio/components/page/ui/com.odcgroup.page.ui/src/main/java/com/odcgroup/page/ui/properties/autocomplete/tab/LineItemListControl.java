package com.odcgroup.page.ui.properties.autocomplete.tab;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * @author pkk
 *
 */
public class LineItemListControl extends ListControl<ILineItemSnippet, ILineSnippet>{

	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 */
	public LineItemListControl(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	@Override
	public ILineItemSnippet[] getTableElements(
			List<ILineItemSnippet> inputElement) {
		return inputElement.toArray(new ILineItemSnippet[0]);
	}

	@Override
	public String getPropertyTableColumnText(ILineItemSnippet element,
			int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getAttributeName();
			} else if (columnIndex == 1) {
				return element.isTranslation()+"";
			} else {
				return element.getCSSClass();
			}
		}
		return null;
	}

	@Override
	public ViewerSorter getTableSorter() {
		return null;
	}

	@Override
	public List<ILineItemSnippet> getTableInput(ILineSnippet input) {
		if (input != null) {
			return input.getLineItems();
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public ILineItemSnippet getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (ILineItemSnippet)selection.getFirstElement();	
	}

	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Attribute Name", 0, 1, false));
		columns.add(new PropertyColumn("Translation", 0, 1, false));	
		columns.add(new PropertyColumn("CSS Class", 0, 1, false));			
	}

	@Override
	protected SpecialControl<ILineSnippet> createOtherControls(Composite parent) {
		return null;
	}

	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new LineItemDefinitionDialog(getShell(), getCommandStack(), getInput(), getSelection());			
		} else {
			if (getInput().getThirdLineItem() != null) {
				return null;
			}
			return new LineItemDefinitionDialog(getShell(), getCommandStack(), getInput());
		}
	}

	@Override
	protected BaseCommand getDeleteCommand(ILineSnippet input,
			ILineItemSnippet element) {
		return new DeleteLineItemCommand(input, element);
	}

	@Override
	protected BaseCommand getMoveUpCommand(ILineSnippet input,
			ILineItemSnippet element) {
		return new MoveLineItemCommand(input, element, true);
	}

	@Override
	protected BaseCommand getMoveDownCommand(ILineSnippet input,
			ILineItemSnippet element) {
		return new MoveLineItemCommand(input, element, false);
	}
	
	/**
	 * command implementation for deleting line item
	 *
	 */
	static class DeleteLineItemCommand extends BaseCommand {
		
		private ILineSnippet line;
		private ILineItemSnippet item;
		private int index;
		
		public DeleteLineItemCommand(ILineSnippet line, ILineItemSnippet item) {
			this.line = line;
			this.item = item;
		}

		@Override
		public void execute() {
			index = line.getSnippet().getContents().indexOf(item.getSnippet());
			line.getSnippet().getContents().remove(index);
		}

		@Override
		public void undo() {
			line.getSnippet().getContents().add(index, item.getSnippet());
		}
		
	}
	
	/**
	 *
	 */
	static class MoveLineItemCommand extends BaseCommand {
		
		private ILineSnippet line;
		private ILineItemSnippet item;
		private boolean upwards;
		
		public MoveLineItemCommand(ILineSnippet line, ILineItemSnippet item, boolean up) {
			this.line = line;
			this.item = item;
			this.upwards = up;
		}
		

		/**
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		public void execute() {
			List<Snippet> items = line.getSnippet().getContents();
			int pos = items.indexOf(item.getSnippet());
			if (upwards) {
				if (pos != -1 && pos > 0) {
					items.add(pos-1, items.remove(pos));
				}
			} else {
				if (pos != -1 && pos < items.size()) {
					items.add(pos+1, items.remove(pos));
				}
			}
		}

		/**
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		public void undo() {
			List<Snippet> items = line.getSnippet().getContents();
			int pos = items.indexOf(item.getSnippet());
			if (upwards) {
				if (pos != -1 && pos < items.size()) {
					items.add(pos+1, items.remove(pos));
				}
			} else {
				if (pos != -1 && pos > 0) {
					items.add(pos-1, items.remove(pos));
				}
			}
		}
	}

}
