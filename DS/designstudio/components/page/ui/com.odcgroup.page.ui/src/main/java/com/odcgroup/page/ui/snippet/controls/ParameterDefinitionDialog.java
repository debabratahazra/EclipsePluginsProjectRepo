package com.odcgroup.page.ui.snippet.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * @author pkk
 *
 */
public class ParameterDefinitionDialog extends TableTransformDialog {
	
	/** */
	private Text nameTxt;
	/** */
	private Text valueTxt;
	/** */
	private IParameterSnippet parameter = null;
	/** */
	private ISearchField search = null;

	/**
	 * @param parentShell
	 */
	public ParameterDefinitionDialog(ISearchField searchField, Shell parentShell, CommandStack commandStack) {
		this(searchField, parentShell, commandStack, null);
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param parameter
	 */
	public ParameterDefinitionDialog(ISearchField searchField, Shell parentShell, CommandStack commandStack, IParameterSnippet parameter) {
		super(parentShell, commandStack);
		this.search = searchField;
		if (parameter != null) {
			setEditMode(true);
			this.parameter = parameter;
		}
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Parameter");
		setMessage("SearchField Parameter definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Parameter");
	}

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
		
	    GridData gridData = new GridData();
	    gridData.widthHint = 60;

		Label label = new Label(body, SWT.NONE);
		label.setText("Name");
		label.setLayoutData(gridData);
		
		nameTxt = new Text(body, SWT.SINGLE | SWT.BORDER);
	    gridData = new GridData(GridData.FILL_HORIZONTAL);
	    nameTxt.setLayoutData(gridData);
		
		new Label(body, SWT.NONE).setText("Value");
		valueTxt = new Text(body, SWT.SINGLE | SWT.BORDER);
		valueTxt.setLayoutData(gridData);

	    if (isEditMode()) {
	    	nameTxt.setText(parameter.getName());
	    	valueTxt.setText(parameter.getValue());
	    }
		
		nameTxt.addModifyListener(this);
		valueTxt.addModifyListener(this);
		
		return composite;
	}

	@Override
	protected boolean validChanges() {
		if (StringUtils.isEmpty(nameTxt.getText()) || StringUtils.isEmpty(valueTxt.getText()))
			return false;
		return true;
	}

	@Override
	protected BaseCommand getCommand() {
		if (parameter == null) {
			parameter = SnippetUtil.getSnippetFactory().createParameter();
			parameter.setName(nameTxt.getText());
			parameter.setValue(valueTxt.getText());
		}
		
		BaseCommand cmd = null;
		if (isEditMode()) {	
			cmd = new UpdateParameterCommand(parameter, nameTxt.getText(), valueTxt.getText());
		} else {			
			cmd = new AddParameterCommand(search, parameter);
		}		
		return cmd;
	}
	
	/**
	 * @author pkk
	 *
	 */
	static class UpdateParameterCommand extends BaseCommand {
		
		private IParameterSnippet snippet;
		private String oldName;
		private String oldVal;
		private String newName;
		private String newVal;
		
		/**
		 * @param search
		 * @param snippet
		 */
		public UpdateParameterCommand(IParameterSnippet snippet, String newName, String newVal) {
			this.snippet = snippet;
			this.newName = newName;
			this.newVal = newVal;
			this.oldName = snippet.getName();
			this.oldVal = snippet.getValue();
		}
		
		/**
		 * (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		public void execute() {
			snippet.setName(newName);
			snippet.setValue(newVal);
		}
		
		/**
		 * (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		public void undo() {
			snippet.setName(oldName);
			snippet.setValue(oldVal);
		}
	}
	
	/**
	 * @author pkk
	 *
	 */
	static class AddParameterCommand extends BaseCommand {
		
		private ISearchField search;
		private IParameterSnippet snippet;
		
		/**
		 * @param search
		 * @param snippet
		 */
		public AddParameterCommand(ISearchField search, IParameterSnippet snippet) {
			this.search = search;
			this.snippet = snippet;
		}
		/**
		 * (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		public void execute() {
			search.addParameter(snippet);
		}
		
		/**
		 * (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		public void undo() {
			search.removeParameter(snippet);
		}
	}

}
