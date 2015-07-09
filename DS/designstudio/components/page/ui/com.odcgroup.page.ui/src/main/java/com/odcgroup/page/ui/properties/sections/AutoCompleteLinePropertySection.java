package com.odcgroup.page.ui.properties.sections;

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
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.ui.properties.autocomplete.tab.AutoCompleteLineTab;
import com.odcgroup.page.ui.properties.autocomplete.tab.IAutoCompleteLineTab;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * @author pkk
 *
 */
public class AutoCompleteLinePropertySection extends AbstractPagePropertySection {
	

	/** tab mapping */
	private List<IAutoCompleteLineTab> tabs = new ArrayList<IAutoCompleteLineTab>();
	/** The selected table. */
	private IAutoCompleteDesign autoDesign = null;
	
	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;	
	
	
	/** */
	private Font tabFolderFont = null;

	/**
	 * @return the IAutoCompleteDesign adapter
	 */
	protected final IAutoCompleteDesign getAutoCompleteDesign() {
		return this.autoDesign;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection#
	 * setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object input = ((IStructuredSelection) selection).getFirstElement();
		Widget widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null) {
			this.autoDesign = WidgetHelper.getAutoCompleteDesign(widget);
			setReflectiveInput(autoDesign, part);
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
			for (IAutoCompleteLineTab tab : tabs) {
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
	private void setReflectiveInput(IAutoCompleteDesign autoDes, IWorkbenchPart part) {
		tabs.get(0).setInput(autoDes.getFirstLine(), part);
		tabs.get(1).setInput(autoDes.getSecondLine(), part);
		tabs.get(2).setInput(autoDes.getThirdLine(), part);
	}
	
	/**
	 * @param tabFolder
	 */
	private void buildTabs(CTabFolder tabFolder) {
		tabs.add(new AutoCompleteLineTab(tabFolder, SWT.FILL, "Line1", true));
		tabs.add(new AutoCompleteLineTab(tabFolder, SWT.FILL, "Line2", true));
		tabs.add(new AutoCompleteLineTab(tabFolder, SWT.FILL, "Line3", true));
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
		for (IAutoCompleteLineTab tab : tabs) {
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
