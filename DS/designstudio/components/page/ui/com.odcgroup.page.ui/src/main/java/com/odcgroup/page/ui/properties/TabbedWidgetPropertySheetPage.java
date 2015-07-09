package com.odcgroup.page.ui.properties;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.odcgroup.page.ui.help.HelpAction;
import com.odcgroup.workbench.editors.properties.AdvancedTabbedPropertySheetPage;

/**
 * 
 * A tabbed property sheet page for displaying properties of the selected widget.
 *  
 * @author atr
 * @author pkk
 *
 * @version 1.0
 */
public class TabbedWidgetPropertySheetPage extends AdvancedTabbedPropertySheetPage {

	/**
	 * Creates a new TabbedWidgetPropertySheetPage.
	 * 
	 * @param tabbedPropertySheetPageContributor The ITabbedPropertySheetPageContributor
	 */
	public TabbedWidgetPropertySheetPage(ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
		setSelectionAdapter(new WidgetValidationProvider());
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#setActionBars(org.eclipse.ui.IActionBars)
	 */
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
		//actionBars.getToolBarManager().add(OfsHelpHelper.createHelpAction(IOfsHelpContextIds.FRAGMENT_MODULE_PAGE_PROPERTIES));
		actionBars.getToolBarManager().add(new HelpAction());
        makeContributions(actionBars.getMenuManager(), actionBars.getToolBarManager(), actionBars.getStatusLineManager());
	}
	
	
}
