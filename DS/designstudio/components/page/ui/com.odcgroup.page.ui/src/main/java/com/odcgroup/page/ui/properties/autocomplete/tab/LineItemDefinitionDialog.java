package com.odcgroup.page.ui.properties.autocomplete.tab;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 * @author pkk
 *
 */
public class LineItemDefinitionDialog extends TableTransformDialog {
	
	private ILineItemSnippet item;
	private ILineSnippet line;
	/** */
	private ITypeCombo<String> domainAttributesCombo;
	private ITypeCombo<DataValue> translationCombo;
	private Text cssText;

	/**
	 * @param parentShell
	 * @param commandStack
	 */
	public LineItemDefinitionDialog(Shell parentShell, CommandStack commandStack, ILineSnippet line) {
		this(parentShell, commandStack, line, null);
	}
	

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param line
	 * @param item
	 */
	public LineItemDefinitionDialog(Shell parentShell, CommandStack commandStack, ILineSnippet line, ILineItemSnippet item) {
		super(parentShell, commandStack);
		this.line = line;
		if (item != null) {
			this.item = item;
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
		setTitle("Line Item");
		setMessage("Line Item definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Line Item");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
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
		
		new Label(body, SWT.NONE).setText("Attribute");
		Set<String> columns = new TreeSet<String>();
		columns.addAll(TableDomainObjectUtil.getDomainAttributeNames(line.getParentWidget()));
		domainAttributesCombo = new StringValueCombo(body, columns, new ArrayList<String>(), isEditMode());		

		new Label(body, SWT.NONE).setText("Translation");
		translationCombo = new DataTypeCombo(body, findDataType("Boolean"), isEditMode());
		
		new Label(body, SWT.NONE).setText("CSS Class");
		cssText = new Text(body, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		cssText.setLayoutData(gridData);
		
		if (isEditMode()) {
			domainAttributesCombo.select(item.getAttributeName());
			translationCombo.select(item.isTranslation()+"");
			cssText.setText(item.getCSSClass());
		} else {
			translationCombo.select("false");
		}
		
		domainAttributesCombo.addSelectionListener(this);
		translationCombo.addSelectionListener(this);

		return composite;
	}
	
	/**
	 * Find the DataType given its  name
	 * @param name
	 * @return DataType
	 */
	private DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}

	@Override
	protected boolean validChanges() {
		if(domainAttributesCombo.getSelectedValue() == null) {
			setErrorMessage("Attribute is not specified");
			return false;
		}  else {
			setErrorMessage(null);
			return true;
		}
	}

	@Override
	protected BaseCommand getCommand() {
		BaseCommand cmd = null;
		String attribute = domainAttributesCombo.getSelectedValue();
		boolean translation = Boolean.valueOf(translationCombo.getSelectedValue().getValue()).booleanValue();
		String css = cssText.getText();
		if (isEditMode()) {		
			cmd = new UpdateLineItemCommand(item, attribute, translation, css);
		} else {
			item = SnippetUtil.getSnippetFactory().createLineItem(line);
			item.setAttributeName(attribute);
			item.setTranslation(translation);
			item.setCSSClass(css);
			cmd = new InsertLineItemCommand(line, item);
		}
		return cmd;
	}
	
	/**
	 *
	 */
	static class InsertLineItemCommand extends BaseCommand {
		
		private ILineSnippet line;
		private ILineItemSnippet item;
		private boolean lineExists = true;
		
		public InsertLineItemCommand(ILineSnippet line, ILineItemSnippet item) {
			this.line = line;
			this.item = item;
		}

		@Override
		public void execute() {
			Snippet snippet = line.getSnippet();
			Widget widget = line.getParentWidget().getWidget();
			if (line.getParentWidget().getWidget().getSnippets().contains(snippet)) {
				snippet.getContents().add(item.getSnippet());
			} else {
				lineExists = false;
				snippet.getContents().add(item.getSnippet());
				widget.getSnippets().add(line.getSnippet());
			}
		}

		@Override
		public void undo() {
			if (lineExists) {
				line.getSnippet().getContents().remove(item);
			} else {
				line.getParentWidget().getWidget().getSnippets().remove(line.getSnippet());
			}
		}
	}
	
	static class UpdateLineItemCommand extends BaseCommand {
		private ILineItemSnippet item;
		private String attribute;
		private boolean translation;
		private String css;
		
		private String oldAttribute;
		private boolean oldTranslation;
		private String oldCss;
		
		public UpdateLineItemCommand(ILineItemSnippet item, String attribute, boolean translation, String css) {
			this.item = item;
			this.attribute = attribute;
			this.translation = translation;
			this.css = css;
		}
		@Override
		public void execute() {
			oldAttribute = item.getAttributeName();
			oldTranslation = item.isTranslation();
			oldCss = item.getCSSClass();
			
			item.setAttributeName(attribute);
			item.setTranslation(translation);
			item.setCSSClass(css);
		}
		@Override
		public void undo() {			
			item.setAttributeName(oldAttribute);
			item.setTranslation(oldTranslation);
			item.setCSSClass(oldCss);			
		}
	}

}
