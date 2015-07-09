package com.odcgroup.page.ui.snippet.controls;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * @author pkk
 *
 */
public class ParameterListControl extends ListControl<IParameterSnippet, ISearchField> {
	
	/**
	 * @param parent
	 * @param style
	 */
	public ParameterListControl(Composite parent, int style) {
		super(parent, style, false);
	}

	@Override
	public IParameterSnippet[] getTableElements(List<IParameterSnippet> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new IParameterSnippet[0]);
		}
		return null;
	}

	@Override
	public String getPropertyTableColumnText(IParameterSnippet element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getName();
			} else if(columnIndex == 1) {
				return element.getValue();
			}
		}
		return "";
	}

	@Override
	public ViewerSorter getTableSorter() {
		return null;
	}


	@Override
	public IParameterSnippet getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (IParameterSnippet)selection.getFirstElement();
	}

	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Name", 50, 1, true));
		columns.add(new PropertyColumn("Value", 50, 2, true));			
	}

	@Override
	public List<IParameterSnippet> getTableInput(ISearchField input) {
		if (input != null) {
			return input.getParameters();
		}
		return null;
	}

	@Override
	protected SpecialControl<ISearchField> createOtherControls(Composite parent) {
		return null;
	}

	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new ParameterDefinitionDialog(getInput(), getShell(), getCommandStack(), getSelection());
			
		} else {
			return new ParameterDefinitionDialog(getInput(), getShell(), getCommandStack());
		}
	}

	@Override
	protected BaseCommand getDeleteCommand(ISearchField input,
			IParameterSnippet element) {
		return new BaseCommand() {
			
			int index;
			Snippet snippet;
			
			public void execute() {
				snippet = getSelection().getSnippet();
				index = getInput().getWidget().getSnippets().indexOf(snippet);
				getInput().removeParameter(getSelection());
			}

			@Override
			public void undo() {
				getInput().getWidget().getSnippets().add(index, snippet);
			}
			
		};
	}

	@Override
	protected BaseCommand getMoveUpCommand(ISearchField input,
			IParameterSnippet element) {
		return null;
	}

	@Override
	protected BaseCommand getMoveDownCommand(ISearchField input,
			IParameterSnippet element) {
		return null;
	}
	
}
