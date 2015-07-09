package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionGroup;

import com.odcgroup.mdf.editor.MdfCore;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfActionGroup extends ActionGroup {
	private MdfAction filterEnumsAction = null;
	private MdfAction filterClassesAction = null;
	private MdfAction filterMainClassesAction = null;
    private MdfAction filterDatasetsAction = null;

	/**
	 * Constructor for MdfActionGroup
	 */
	public MdfActionGroup() {
		this(null);
	}

    /**
     * Constructor for MdfActionGroup
     */
    public MdfActionGroup(MdfActionListener listener) {
        super();

		filterClassesAction =
			new MdfAction(listener, MdfCore.ACTION_HIDE_CLASSES, MdfCore.ICON_CLASS);

		filterMainClassesAction =
				new MdfAction(listener, MdfCore.ACTION_HIDE_MAIN_CLASSES, MdfCore.ICON_MAIN_CLASS);

		filterDatasetsAction  =
            new MdfAction(listener, MdfCore.ACTION_HIDE_DATASETS, MdfCore.ICON_DATASET);

		filterEnumsAction =
			new MdfAction(listener, MdfCore.ACTION_HIDE_ENUMS, MdfCore.ICON_ENUM);

        filterClassesAction.setChecked(false);
        filterMainClassesAction.setChecked(true);
        filterDatasetsAction.setChecked(false);
        filterEnumsAction.setChecked(false);
	}

	public static void setGlobalActionHandler(IActionBars bars, MdfActionGroup group) {
		if (group != null) {
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_CLASSES, group.filterClassesAction);
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_MAIN_CLASSES, group.filterMainClassesAction);
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_ENUMS, group.filterEnumsAction);
            bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_DATASETS, group.filterDatasetsAction);
		} else {
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_CLASSES, null);
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_MAIN_CLASSES, null);
			bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_ENUMS, null);
            bars.setGlobalActionHandler(MdfCore.ACTION_HIDE_DATASETS, null);
		}
	}
    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);
        IToolBarManager toolbar = actionBars.getToolBarManager();
        toolbar.add(filterEnumsAction);
        toolbar.add(filterClassesAction);
        toolbar.add(filterMainClassesAction);
        toolbar.add(filterDatasetsAction);
		toolbar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        IMenuManager menu = actionBars.getMenuManager();
        menu.add(filterEnumsAction);
        menu.add(filterClassesAction);
        menu.add(filterMainClassesAction);
        menu.add(filterDatasetsAction);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        super.fillContextMenu(menu);

        MenuManager filters = new MenuManager();
        filters.add(filterEnumsAction);
        filters.add(filterClassesAction);
        filters.add(filterMainClassesAction);
        filters.add(filterDatasetsAction);

        menu.insertAfter(IWorkbenchActionConstants.MB_ADDITIONS, filters);
    }

}
