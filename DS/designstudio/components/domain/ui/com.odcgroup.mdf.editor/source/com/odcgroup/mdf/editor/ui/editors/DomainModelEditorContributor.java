package com.odcgroup.mdf.editor.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.editor.ui.actions.DeriveFromBaseClassAction;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * This is the action bar contributor for the Mdf model editor.
 */
public class DomainModelEditorContributor extends EditingDomainActionBarContributor implements
		ISelectionChangedListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainModelEditorContributor.class);
	private static final String EXTENSION_ID = "com.odcgroup.mdf.editor.domainmodeleditorcontributor_override";

	protected IEditorPart activeEditorPart;
	protected ISelectionProvider selectionProvider;
	// DS-1698 - action to copy the baseclass attributes to the dataset
	DeriveFromBaseClassAction copyBaseClassAction = null;

	protected IAction showPropertiesViewAction = new Action("Show &Properties View") {

		public void run() {
			try {
				getPage().showView("org.eclipse.ui.views.PropertySheet");
			} catch (PartInitException exception) {
				LOGGER.error("Cannot open property sheet", exception);
			}
		}
	};

	protected IAction refreshViewerAction = new Action("&Refresh") {

		public boolean isEnabled() {
			return activeEditorPart instanceof IViewerProvider;
		}

		public void run() {
			if (activeEditorPart instanceof IViewerProvider) {
				Viewer viewer = ((IViewerProvider) activeEditorPart).getViewer();
				if (viewer != null) {
					viewer.refresh();
				}
			}
		}
	};

	protected Collection createChildActions;
	protected IMenuManager createChildMenuManager;

	public DomainModelEditorContributor() {
		super(ADDITIONS_LAST_STYLE);
	}

	@Override
	public void init(IActionBars actionBars) {
		super.init(actionBars);
		actionBars.getGlobalActionHandler(ActionFactory.DELETE.getId());
		// loadResourceAction = new LoadModelAction();
		// validateAction = new ValidateAction();
		// controlAction = new ControlAction();
	}

	/**
	 * This adds Separators for editor additions to the tool bar.
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator("settings"));
		toolBarManager.add(new Separator("actions"));
		toolBarManager.add(new Separator("additions"));
	}

	/**
	 * This adds to the menu bar a menu and some separators for editor
	 * additions, as well as the sub-menus for object creation items.
	 */
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);

		IMenuManager submenuManager = new MenuManager("&Domain Editor", "com.odcgroup.mdf.editor.menuId");
		menuManager.insertAfter("additions", submenuManager);
		submenuManager.add(new Separator("settings"));
		submenuManager.add(new Separator("actions"));
		submenuManager.add(new Separator("additions"));
		submenuManager.add(new Separator("additions-end"));

		// Prepare for CreateChild item addition or removal.
		createChildMenuManager = new MenuManager("&New", "new");
		submenuManager.insertBefore("additions", createChildMenuManager);

		// Force an update because Eclipse hides empty menus now.
		submenuManager.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager menuManager) {
				menuManager.updateAll(true);
			}
		});

		addGlobalActions(submenuManager);
	}

	/**
	 * When the active editor changes, this remembers the change and registers
	 * with it as a selection provider.
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		activeEditorPart = part;

		// Switch to the new selection provider.
		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
		}

		if (part == null) {
			selectionProvider = null;
		} else {
			selectionProvider = part.getSite().getSelectionProvider();
			selectionProvider.addSelectionChangedListener(this);

			// Fake a selection changed event to update the menus.
			//
			if (selectionProvider.getSelection() != null) {
				selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()));
			}
		}
	}

	/**
	 * This implements
	 * {@link org.eclipse.jface.viewers.ISelectionChangedListener}, handling
	 * {@link org.eclipse.jface.viewers.SelectionChangedEvent}s by querying for
	 * the children and siblings that can be added to the selected object and
	 * updating the menus accordingly.
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		// Remove any menu items for old selection.
		if (createChildMenuManager != null) {
			depopulateManager(createChildMenuManager, createChildActions);
		}

		// Query the new selection for appropriate new child/sibling descriptors
		Collection newChildDescriptors = null;

		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			EditingDomain domain = ((IEditingDomainProvider) activeEditorPart).getEditingDomain();
			newChildDescriptors = domain.getNewChildDescriptors(object, null);
		}

		// Generate actions for selection; populate and redraw the menus.
		createChildActions = generateCreateChildActions(newChildDescriptors, selection);

		if (createChildMenuManager != null) {
			populateManager(createChildMenuManager, createChildActions, null);
			createChildMenuManager.update(true);
		}
		// DS-2011
		if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() > 1) {
			Iterator iter = ((IStructuredSelection) selection).iterator();
			List<MdfModelElement> list = new ArrayList<MdfModelElement>();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof MdfModelElement) {
					list.add((MdfModelElement) obj);
				}
			}
			if (EditionSupportFactory.INSTANCE().selectionContainsElements(list)) {
				getActionBars().getGlobalActionHandler(ActionFactory.DELETE.getId()).setEnabled(false);
			}
		}
	}

	
	
	/**
	 * This generates a {@link org.eclipse.emf.edit.ui.action.CreateChildAction}
	 * for each object in <code>descriptors</code>, and returns the collection
	 * of these actions.
	 */
	protected Collection generateCreateChildActions(Collection descriptors, ISelection selection) {
		//CHECK FOR EXTENSION
		DomainModelActionHandler handler = null;
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);
        
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if ("domaineditorcontributor".equals(elements[j].getName())) {
					try {
						DomainEditorContributorProvider provider = (DomainEditorContributorProvider) elements[j].createExecutableExtension("class");
						handler = provider.getActionHandler(copyBaseClassAction, activeEditorPart);						
						
					} catch (CoreException e) {
						LOGGER.error(e.toString());
					}
				}
			}
		}
		if(handler == null) {
			handler = new DomainModelActionHandler(copyBaseClassAction, activeEditorPart);
		}
		Collection actions = handler.generateActions(descriptors, selection);
		return handler.defaultActions(descriptors, selection, actions);
	}

	/**
	 * This populates the specified <code>manager</code> with
	 * {@link org.eclipse.jface.action.ActionContributionItem}s based on the
	 * {@link org.eclipse.jface.action.IAction}s contained in the
	 * <code>actions</code> collection, by inserting them before the specified
	 * contribution item <code>contributionID</code>. If <code>ID</code> is
	 * <code>null</code>, they are simply added.
	 */
	protected void populateManager(IContributionManager manager, Collection actions, String contributionID) {
		if (actions != null) {
			for (Iterator i = actions.iterator(); i.hasNext();) {
				IAction action = (IAction) i.next();
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				} else {
					manager.add(action);
				}
			}
		}
	}

	/**
	 * This removes from the specified <code>manager</code> all
	 * {@link org.eclipse.jface.action.ActionContributionItem}s based on the
	 * {@link org.eclipse.jface.action.IAction}s contained in the
	 * <code>actions</code> collection.
	 */
	protected void depopulateManager(IContributionManager manager, Collection actions) {
		if (actions != null) {
			IContributionItem[] items = manager.getItems();
			for (int i = 0; i < items.length; i++) {
				// Look into SubContributionItems
				//
				IContributionItem contributionItem = items[i];
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((SubContributionItem) contributionItem).getInnerItem();
				}

				// Delete the ActionContributionItems with matching action.
				//
				if (contributionItem instanceof ActionContributionItem) {
					IAction action = ((ActionContributionItem) contributionItem).getAction();
					if (actions.contains(action)) {
						manager.remove(contributionItem);
					}
					if (copyBaseClassAction != null && copyBaseClassAction.equals(action)) {
						manager.remove(contributionItem);
					}
				}
			}
		}

	}

	/**
	 * This populates the pop-up menu before it appears.
	 */
	public void menuAboutToShow(IMenuManager menuManager) {
		super.menuAboutToShow(menuManager);
		MenuManager submenuManager = null;

		submenuManager = new MenuManager("&New");
		populateManager(submenuManager, createChildActions, null);
		menuManager.insertBefore("edit", submenuManager);
	}

	/**
	 * This inserts global actions before the "additions-end" separator.
	 */
	protected void addGlobalActions(IMenuManager menuManager) {
		menuManager.insertAfter("additions-end", new Separator("ui-actions"));
		menuManager.insertAfter("ui-actions", showPropertiesViewAction);

		refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());
		menuManager.insertAfter("ui-actions", refreshViewerAction);

		super.addGlobalActions(menuManager);
	}

	/**
	 * This ensures that a delete action will clean up all references to deleted
	 * objects.
	 */
	protected boolean removeAllReferencesOnDelete() {
		return false;
	}

}
