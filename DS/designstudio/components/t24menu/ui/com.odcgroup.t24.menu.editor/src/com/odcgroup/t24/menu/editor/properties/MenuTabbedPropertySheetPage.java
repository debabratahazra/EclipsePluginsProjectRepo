package com.odcgroup.t24.menu.editor.properties;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IActionBars;

import com.odcgroup.t24.menu.menu.presentation.MenuEditor;
import com.odcgroup.workbench.editors.properties.AdvancedTabbedPropertySheetPage;

/**
 * @author pkk
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

	@Override
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
		//actionBars.getToolBarManager().add(OfsHelpHelper.createHelpAction(IOfsHelpContextIds.FRAGMENT_MODULE_PAGE_PROPERTIES));
        makeContributions(actionBars.getMenuManager(), actionBars.getToolBarManager(), actionBars.getStatusLineManager());
	}

	@Override
	public EditingDomain getEditingDomain() {
		if (menuEditor != null) {
			return menuEditor.getEditingDomain();
		}
		return null;
	}

}
