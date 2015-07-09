package com.odcgroup.menu.editor.properties;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IActionBars;

import com.odcgroup.menu.editor.ui.MenuEditor;
import com.odcgroup.workbench.editors.properties.AdvancedTabbedPropertySheetPage;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.help.OfsHelpHelper;

/**
 * @author pkk
 *
 */
public class MenuTabbedPropertySheetPage extends AdvancedTabbedPropertySheetPage implements IEditingDomainProvider {
	
	private MenuEditor menuEditor = null;

	/**
	 * @param tabbedPropertySheetPageContributor
	 */
	public MenuTabbedPropertySheetPage(MenuEditor menuEditor) {
		super(menuEditor);
		setSelectionAdapter(new MenuValidationProvider());
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage#setActionBars(org.eclipse.ui.IActionBars)
	 */
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
		actionBars.getToolBarManager().add(OfsHelpHelper.createHelpAction(IOfsHelpContextIds.FRAGMENT_MODULE_PAGE_PROPERTIES));
        makeContributions(actionBars.getMenuManager(), actionBars.getToolBarManager(), actionBars.getStatusLineManager());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		if (menuEditor != null) {
			return menuEditor.getEditingDomain();
		}
		return null;
	}

}
