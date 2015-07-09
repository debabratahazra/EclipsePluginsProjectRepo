package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.IStyleProvider;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.UpdateCssClassTypeCommand;
import com.odcgroup.page.ui.command.matrix.UpdateSpecificCssClassCommand;
import com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.util.AdaptableUtils;

/**
 *
 * @author pkk
 */
public class MatrixCssClassPropertySection extends AbstractPagePropertySection {

	/** The selected table. */
	private IStyleProvider matrix = null;
	/** */
	private DataTypeCombo cssTypeCombo;
	/** */
	private Text specificClass;
	/** */
	private Text conditionClass;
	/** */
	private Button browse;
	/** */
	private CommandStack commandStack;
	
	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;
	
	private Composite container;

	/**
	 * @return the IStyleProvider adapter
	 */
	protected final IStyleProvider getMatrix() {
		return this.matrix;
	}

	/**
	 * Notifies the section that the workbench selection has changed.
	 * 
	 * @param part
	 *            The active workbench part.
	 * @param selection
	 *            The active selection in the workbench part.
	 * 
	 *            Implementation note: the selected table widget is wrapped into
	 *            a ITable adapter
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		commandStack = getCommandStack(part);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		Object input = ((IStructuredSelection) selection).getFirstElement();
		Widget widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null) {
			this.matrix = MatrixHelper.getStyleProvider(widget);
			initValues();
		} else {
			// TODO - manage exception
		}
		super.setInput(part, selection);
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection#setReadOnly(org.eclipse.ui.IWorkbenchPart)
	 */
	protected void setReadOnly(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			IEditorInput editInput = ((IEditorPart) part).getEditorInput();
			boolean enabled = true;
			if (editInput instanceof IFileEditorInput) {
				enabled = true;
			} else {
				enabled = false;
			}
			if (container != null) {
				container.setEnabled(enabled);
			}
		}
	}
	
	/**
	 * Create the controls of the TableSortSection.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param aTabbedPropertySheetPage
	 *            The property sheet page
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		widgetFactory = getWidgetFactory();
		Composite body = createContainerAndBody(parent);
		createCssClassTypeLabelAndCombo(body);
		createSpecificCssClassLabelAndTextfield(body);
		createCssClassBasedOnConditionsLabelAndTextfield(body);
	}

	/**
	 * Extracted from createControls. 
	 * 
	 * @param body
	 * @return the body (child) of the container.
	 */
	private Composite createContainerAndBody(Composite parent) {
		container = widgetFactory.createFlatFormComposite(parent);
		GridLayout gd = new GridLayout();
		container.setLayout(gd);

		Composite body = widgetFactory.createFlatFormComposite(container);
		gd = new GridLayout(3, false);
		body.setLayout(gd);

		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		body.setLayoutData(gridData);
		return body;
	}

	/**
	 * Extracted from createControls. 
	 * 
	 * @param body
	 */
	private void createCssClassBasedOnConditionsLabelAndTextfield(Composite body) {
		Label label = new Label(body, SWT.NONE);
		label.setText("CSS class based on conditions");
		label.setBackground(body.getBackground());
		conditionClass = widgetFactory.createText(body, "", SWT.None);
		GridData data = new GridData();
		data.widthHint = 250;
		conditionClass.setLayoutData(data);
		conditionClass.setEditable(false);

		browse = widgetFactory.createButton(body, "..", SWT.None);
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Shell shell = getPart().getSite().getShell();
				ICssClass cssClass = matrix.getCssClass();
				ConditionalCssClassDefinitionDialog dialog = new ConditionalCssClassDefinitionDialog(cssClass, shell,
						commandStack);
				if (dialog != null) {
					dialog.open();
					if (dialog.getReturnCode() == Dialog.OK) {
						setCssConditions();
					}
				}
				super.widgetSelected(e);
			}

		});
	}

	/**
	 * Extracted from createControls. 
	 * 
	 * @param body
	 */
	private void createSpecificCssClassLabelAndTextfield(Composite body) {
		Label label = new Label(body, SWT.NONE);
		label.setText("Specific CSS class");
		label.setBackground(body.getBackground());
		specificClass = widgetFactory.createText(body, "", SWT.None);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = 250;
		specificClass.setLayoutData(data);
		specificClass.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = specificClass.getText();
				if (!StringUtils.isEmpty(text)) {
					BaseCommand cmd = new UpdateSpecificCssClassCommand(matrix, text);
					if (cmd != null && cmd.canExecute()) {
						commandStack.execute(cmd);
					}					
				}
			}			
		});
	}

	/**
	 * extracted from createControls. 
	 * 
	 * @param body
	 */
	private void createCssClassTypeLabelAndCombo(Composite body) {
		Label label = new Label(body, SWT.NONE);
		label.setText("CSS class type");
		label.setBackground(body.getBackground());
		cssTypeCombo = new DataTypeCombo(body, MatrixHelper.getCssClassTypes(), true);		
		cssTypeCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				DataValue data = cssTypeCombo.getSelectedValue();
				BaseCommand cmd = new UpdateCssClassTypeCommand(matrix, data.getValue());
				if (cmd != null && cmd.canExecute()) {
					commandStack.execute(cmd);
				}
				ICssClass cssClass = matrix.getCssClass();
				if (cssClass.isSpecific()) {
					specificClass.setEnabled(true);
					specificClass.setEditable(true);
					browse.setEnabled(false);
					conditionClass.setEnabled(false);
				} else if (cssClass.isConditional()) {
					specificClass.setEnabled(false);
					specificClass.setEditable(false);
					browse.setEnabled(true);
					conditionClass.setEnabled(true);
					setCssConditions();
				} else {
					specificClass.setEnabled(false);
					specificClass.setEditable(false);
					browse.setEnabled(false);
					conditionClass.setEnabled(false);
				}
				
			}
		});
		GridData data = new GridData();
		data.horizontalSpan = 2;
		cssTypeCombo.getCombo().setLayoutData(data);
	}
	
	/**
	 * 
	 */
	private void initValues() {
		if (matrix.getCssClass() != null) {
			ICssClass cssClass = matrix.getCssClass();
			cssTypeCombo.select(cssClass.getCssClassType().getValue());
			if (cssClass.isSpecific()) {
				specificClass.setText(cssClass.getSpecificCssClass().getValue());
				specificClass.setEnabled(true);
				specificClass.setEditable(true);
				browse.setEnabled(false);
				conditionClass.setEnabled(false);
			} else if (cssClass.isConditional()) {
				specificClass.setEnabled(false);
				specificClass.setEditable(false);
				browse.setEnabled(true);
				setCssConditions();
				conditionClass.setEnabled(true);
			} else {
				specificClass.setEnabled(false);
				specificClass.setEditable(false);
				browse.setEnabled(false);	
				conditionClass.setEnabled(false);			
			}
		} else {
			cssTypeCombo.getCombo().select(0);
			DataValue data = cssTypeCombo.getSelectedValue();
			BaseCommand cmd = new UpdateCssClassTypeCommand(matrix, data.getValue());
			if (cmd != null && cmd.canExecute()) {
				commandStack.execute(cmd);
			}
			browse.setEnabled(false);
			specificClass.setText("");
			specificClass.setEnabled(false);
			specificClass.setEditable(false);
		}
	}
	
	/**
	 * 
	 */
	private void setCssConditions() {
		ICssClass cssClass = matrix.getCssClass();
		if (!cssClass.getConditionalCssClasses().isEmpty()) {
			List<IConditionalCssClass> conditions = cssClass.getConditionalCssClasses();
			StringBuilder sb = new StringBuilder();
			for (IConditionalCssClass condition : conditions) {
				sb.append(condition.getResult().getValue());
				if (conditions.indexOf(condition) < (conditions.size()-1)) {
					sb.append(",");
				}
			}
			conditionClass.setText(sb.toString());
		} else {
			conditionClass.setText("");
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
	}
	
	/**
	 * @return CommandStack
	 */
	protected CommandStack getCommandStack(IWorkbenchPart part) {
		return (CommandStack) part.getAdapter(CommandStack.class);	
	}	

}
