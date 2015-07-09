package com.odcgroup.page.ui.help;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.help.OfsHelpHelper;

public class HelpAction extends Action {

	private static final String DEFAULT_WIDGET_HELP_PROPERTIES = "WIDGET_DEFAULT_PROPERTIES";

	public HelpAction() {
		super("Help");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_LCL_LINKTO_HELP));
	}

	public void run() {
		IEditorPart ep = EclipseUtils.findActiveEditor();
		if (ep != null) {
			// find the current selection
			IStructuredSelection selection = (IStructuredSelection) ep
					.getEditorSite().getSelectionProvider().getSelection();
			WidgetEditPart wep = (WidgetEditPart) selection.getFirstElement();
			if (wep != null) {
				WidgetType type = wep.getWidget().getType();
				String helpID = type.getPropertiesHelpID();
				if (StringUtils.isBlank(helpID)) {
					helpID = DEFAULT_WIDGET_HELP_PROPERTIES;
				}
				String contextId = PageUIPlugin.getDefault().getHelpKeys().getProperty(helpID);
				if (StringUtils.isNotBlank(contextId)) {
					String hid = IOfsHelpContextIds.CONTEXT_PREFIX + contextId.trim();
					//PageUIPlugin.getDefault().logInfo("Display Help for [" + type.getDisplayName() + "]=" + hid, null);
					OfsHelpHelper.displayHelp(hid);
				} else {
					PageUIPlugin.getDefault().logError(
							"Contextual Help for widget [" + type.getDisplayName() + "] cannot be found. (help key:"+helpID+")", null);

				}
			}
		}
	}

}
