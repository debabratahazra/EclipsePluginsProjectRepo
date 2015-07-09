package com.odcgroup.page.ui.properties.sections.table;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection;
import com.odcgroup.page.ui.properties.table.controls.TableColumnComputationParamsComposite;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * The class {@code BaseTablePropertySection} provides common behaviors to all
 * section related to Table widget.
 *
 * @author atr,pkk
 * @since DS 1.40.0
 *
 */
public class TableColumnPropertySection extends AbstractPagePropertySection {

	/** The selected column in the table. */
	private ITableColumn column = null;
	
	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;

	/** list of parameters */
	private TableColumnComputationParamsComposite computationParameters;
	
	/**
	 * @return the ITableColumn adapter
	 */
	protected final ITableColumn getTableColumn() {
		return this.column;
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
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object input = ((IStructuredSelection) selection).getFirstElement();
		Widget widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null) {
			this.column = TableHelper.getTableColumn(widget);
		} else {
			// TODO - manage exception
		}
		
		if (computationParameters != null) {
			computationParameters.setInput(this.column, part);
		}

		super.setInput(part, selection);
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
		
		Composite container = widgetFactory.createComposite(parent);
		container.setLayout(new GridLayout(1, false));
		
		computationParameters = new TableColumnComputationParamsComposite(container, SWT.FILL, true);
		
	}
	

}
