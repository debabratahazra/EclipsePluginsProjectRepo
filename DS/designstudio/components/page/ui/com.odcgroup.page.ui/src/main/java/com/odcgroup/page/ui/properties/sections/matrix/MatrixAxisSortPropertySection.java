package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.UpdateMatrixSortCommand;
import com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.util.AdaptableUtils;

/**
 *
 * @author pkk
 *
 */
public class MatrixAxisSortPropertySection extends AbstractPagePropertySection {

	/** The selected table. */
	private IMatrixAxis matrixAxis = null;
	/** */
	private Combo columnCombo;	
	/** */
	private ITypeCombo<DataValue> directionCombo;
	/** */
	private CommandStack commandStack;
	
	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;
	
	/** */
	private Composite body;

	/**
	 * @return the IMatrixAxis adapter
	 */
	protected final IMatrixAxis getMatrixAxis() {
		return this.matrixAxis;
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
			this.matrixAxis = MatrixHelper.getMatrixAxis(widget);
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
			body.setEnabled(enabled);
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
		
		Composite container = widgetFactory.createFlatFormComposite(parent);
		GridLayout gd = new GridLayout();
		gd.verticalSpacing = 1;
		gd.marginHeight = 3;
		container.setLayout(gd);

		body = widgetFactory.createFlatFormComposite(container);
		gd = new GridLayout(2, false);
		gd.verticalSpacing = 1;
		body.setLayout(gd);
		
		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		body.setLayoutData(gridData);

		new Label(body, SWT.NONE).setText("Sorting Column");
		columnCombo = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
		columnCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleChange();
			}			
		});
		
		new Label(body, SWT.NONE).setText("Sorting Direction");
		directionCombo = new DataTypeCombo(body, TableHelper.getTableUtilities().getSortingDirections(), true);
		directionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleChange();
			}
		});
			
	}
	
	/**
	 * 
	 */
	private void handleChange() {
		int index = columnCombo.getSelectionIndex();
		if (index > -1) {
			String selection = columnCombo.getItem(index);
			String direction = "ascending";
			if (directionCombo.getSelectedValue() != null)
				direction = directionCombo.getSelectedValue().getValue();
			BaseCommand cmd = new UpdateMatrixSortCommand(matrixAxis, selection, direction);
			if (cmd.canExecute()) {
				commandStack.execute(cmd);
			}
		}		
	}
 	
	/**
	 * 
	 */
	private void initValues() {	
		columnCombo.removeAll();
		List<String> list = new ArrayList<String>();
		list.add(0, "");
		list.addAll(TableDomainObjectUtil.getDomainAttributeNames(matrixAxis.getParent()));
		for (String string : list) {
			columnCombo.add(string);
		}
		int index = list.indexOf(matrixAxis.getSortingColumnName());
		columnCombo.select(index);
		directionCombo.select(matrixAxis.getSortingDirection());
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
