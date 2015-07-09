package com.odcgroup.page.ui.properties.sections.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection;
import com.odcgroup.page.ui.properties.table.tab.ITransformTableTab;
import com.odcgroup.page.ui.properties.table.tab.TableAggregateTab;
import com.odcgroup.page.ui.properties.table.tab.TableExtraTab;
import com.odcgroup.page.ui.properties.table.tab.TableGroupTab;
import com.odcgroup.page.ui.properties.table.tab.TableKeepFilterTab;
import com.odcgroup.page.ui.properties.table.tab.TableSortTab;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * The class {@code BaseTablePropertySection} provides common behaviors to all
 * section related to Table widget.
 *
 * @author atr,pkk
 * @since DS 1.40.0
 *
 */
public class TableTransformationPropertySection extends AbstractPagePropertySection {

	/** The selected table. */
	private ITable table = null;
	
	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;
	
	
	/** tab mapping */
	private List<ITransformTableTab> tabs = new ArrayList<ITransformTableTab>();
	
	/** */
	private Font tabFolderFont = null;

	/**
	 * @return the ITable adapter
	 */
	protected final ITable getTable() {
		return this.table;
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
			this.table = TableHelper.getTable(widget);
			setReflectiveInput(TableHelper.getTable(widget), part);
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
			for (ITransformTableTab tab : tabs) {
				tab.setEnabled(enabled);
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
		
		Composite container = widgetFactory.createFlatFormComposite(parent);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		
		CTabFolder tabFolder = widgetFactory.createTabFolder(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
		tabFolderFont = new Font(Display.getCurrent(), "Arial", 8, SWT.BOLD);
		tabFolder.setFont(tabFolderFont);
		tabFolder.setBackground(container.getBackground());
		
		buildTabs(tabFolder);		
	}
	
	/**
	 * @param table
	 * @param part 
	 */
	private void setReflectiveInput(ITable table, IWorkbenchPart part) {
		for (ITransformTableTab tabItem : tabs) {
			tabItem.setInput(table, part);
		}
	}
	
	/**
	 * @param tabFolder
	 */
	private void buildTabs(CTabFolder tabFolder) {
		tabs.add(new TableSortTab(tabFolder, SWT.FILL, "Sort", true));
		tabs.add(new TableGroupTab(tabFolder, SWT.FILL, "Group", true));
		tabs.add(new TableAggregateTab(tabFolder, SWT.FILL, "Aggregate", false));
		tabs.add(new TableKeepFilterTab(tabFolder, SWT.FILL, "Keep Filter", false));
		tabs.add(new TableExtraTab(tabFolder, SWT.FILL, "Extra", false));
		tabFolder.setSelection(tabFolder.getItem(0));
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
		for (ITransformTableTab tab : tabs) {
			tab.refresh();
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#dispose()
	 */
	public void dispose() {
		if (tabFolderFont != null) {
			tabFolderFont.dispose();
		}
		super.dispose();
	}
	
	

}
