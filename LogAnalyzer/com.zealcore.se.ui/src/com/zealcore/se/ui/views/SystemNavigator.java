package com.zealcore.se.ui.views;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.NewWizardMenu;
import org.eclipse.ui.actions.RenameResourceAction;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.views.navigator.IResourceNavigator;
import org.eclipse.ui.views.navigator.MainActionGroup;
import org.eclipse.ui.views.navigator.ResourceNavigator;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.SystemExplorerNature;
import com.zealcore.se.ui.UseAsLogFile;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.core.report.AbstractReport;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.core.report.TreeReportItem;
import com.zealcore.se.ui.core.report.TreeReportItem.TreeNode;
import com.zealcore.se.ui.internal.DirectoryLogSession;
import com.zealcore.se.ui.internal.LogViewInput;
import com.zealcore.se.ui.preferences.MiscPreferences;

/**
 * Behaves as a AbstractResource Navigator for ZealCore Casefiles.
 * 
 * @author stch
 */
public class SystemNavigator extends ResourceNavigator implements
        IReportContributor {

    public static final String VIEW_ID = SeUiPlugin.PLUGIN_ID
            + ".views.SystemNavigator";

    public static final String ASSERTION_FILE_NAME = ".assertions";

    private final IChangeListener importListener;

    public SystemNavigator() {
        super();
        importListener = new IChangeListener() {
            public void update(final boolean changed) {
                final UIJob job = new AbstractSafeUIJob(
                        "Updating Navigator View") {
                    @Override
                    public IStatus runInUIThreadSafe(
                            final IProgressMonitor monitor) {
                        if (changed && getViewer() != null
                                && !getViewer().getControl().isDisposed()) {
                            // getViewer().refresh(true);
                            IWorkspaceRoot root = ResourcesPlugin
                                    .getWorkspace().getRoot();
                            try {
                                root.refreshLocal(IResource.DEPTH_INFINITE,
                                        null);
                            } catch (CoreException e) {}

                        }
                        return Status.OK_STATUS;
                    }
                };
                job.setSystem(true);
                job.schedule();
            }
        };

        IFWFacade.addChangeListener(importListener);
    }

    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
    }

    @Override
    protected void handleDoubleClick(final DoubleClickEvent event) {
        IPreferenceStore preferenceStore = SeUiPlugin.getDefault()
                .getPreferenceStore();

        boolean enableDouble = preferenceStore
                .getBoolean(MiscPreferences.TAG_ENABLE_DOUBLECLICK_IMPORT);
        if (enableDouble) {
            Object source = event.getSelection();
            if (source instanceof TreeSelection) {
                TreeSelection selected = (TreeSelection) source;
                Object source2 = selected.getFirstElement();
                if (source2 instanceof IFile) {
                    Logset logset = CaseFileManager.getLogset((IFile) source2);
                    if (logset != null && !logset.isLocked()) {
                        UseAsLogFile useAsLogFile = new UseAsLogFile();
                        useAsLogFile.handleImport((IFile) source2);
                        return;
                    }

                }
            }
        }
        super.handleDoubleClick(event);
    }

    @Override
    protected void initContentProvider(final TreeViewer viewer) {
        viewer.setContentProvider(new NavigatorContentProvider(viewer));
    }

    @Override
    protected void initFilters(final TreeViewer viewer) {
        super.initFilters(viewer);
        if (!viewer.getControl().isDisposed()) {
            viewer.addFilter(new LogSetConfigFilter());
        }
    }

    @Override
    protected void initLabelProvider(final TreeViewer viewer) {
        final DecoratingLabelProvider provider = new DecoratingLabelProvider(
                new NavigatorLabelProvider(), PlatformUI.getWorkbench()
                        .getDecoratorManager().getLabelDecorator());
        viewer.setLabelProvider(provider);
    }

    @Override
    protected void makeActions() {
        setActionGroup(new SystemActionGroup(this));
    }

    final class LogSetConfigFilter extends ViewerFilter {
        private final String[] alwaysHide = { ".logset", ".project",
                ASSERTION_FILE_NAME, DirectoryLogSession.BIN_FILE,
                "logset.bin.tmp" };

        @Override
        public boolean select(final Viewer viewer, final Object parentElement,
                final Object element) {
            if (element instanceof IFile) {
                final IFile file = (IFile) element;
                for (final String string : alwaysHide) {
                    if (file.getName().equalsIgnoreCase(string)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * This class is meant to override the structure of some of the action
     * groups defined in the ResourceNavigator
     * 
     * @author stch
     * 
     */
    private static class SystemActionGroup extends MainActionGroup {

        private final NewWizardMenu newWizardMenu;

        private DeleteAction deleteAction;

        private RenameAction renameAction;

        public SystemActionGroup(final IResourceNavigator navigator) {
            super(navigator);

            // TODO Should try subclass NewWizardMenu to introduce groupmarker
            // for contents (middle)
            newWizardMenu = new NewWizardMenu(navigator.getSite()
                    .getWorkbenchWindow());
        }

        @Override
        /*
         * Removes some of the action groups that do not contribute to this
         * navigator. Also places a groupmarker under the newWizarMenu
         * 
         * @param menu the menu to fill
         */
        public void fillContextMenu(final IMenuManager menu) {
            final IStructuredSelection selection = (IStructuredSelection) getContext()
                    .getSelection();

            final MenuManager newMenu = new MenuManager("New", "group.new");
            menu.add(newMenu);
            newMenu.add(newWizardMenu);
            newMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
            menu.add(new Separator());
            menu.add(new GroupMarker("group.viewset"));
            menu.add(new GroupMarker("group.logset"));
            menu.add(new Separator());

            menu.add(importAction);
            menu.add(exportAction);

            importAction.selectionChanged(selection);
            exportAction.selectionChanged(selection);
            if (deleteAction != null && renameAction != null) {
                deleteAction.selectionChanged(selection);
                renameAction.selectionChanged(selection);
            }

            menu.add(new Separator());

            workspaceGroup.fillContextMenu(menu);

            menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
            menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
                    + "-end"));
            menu.add(new Separator());

            if (selection.size() == 1) {
                propertyDialogAction.selectionChanged(selection);
                menu.add(propertyDialogAction);
            }
        }

        @Override
        public void updateActionBars() {
            super.updateActionBars();
            final IStructuredSelection selection = (IStructuredSelection) getContext()
                    .getSelection();
            if (deleteAction != null && renameAction != null) {
                deleteAction.selectionChanged(selection);
                renameAction.selectionChanged(selection);
            }
        }

        @Override
        public void fillActionBars(IActionBars actionBars) {
            super.fillActionBars(actionBars);
            if (PlatformUI.getWorkbench().getDisplay().getActiveShell() == null) {
                return;
            }
            deleteAction = new DeleteAction(PlatformUI.getWorkbench()
                    .getDisplay().getActiveShell());
            renameAction = new RenameAction(PlatformUI.getWorkbench()
                    .getDisplay().getActiveShell());
            actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                    deleteAction);
            actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                    renameAction);
        }

        public class DeleteAction extends DeleteResourceAction {
            public DeleteAction(Shell shell) {
                super(shell);
            }

            public void run() {
                List resources = getSelectedResources();
                IResource resource = null;
                if (resources.size() == 1) {
                    resource = (IResource) resources.get(0);
                }
                if (resource == null) {
                    return;
                }
                if (checkResource(resource)) {
                    super.run();
                }
            }
        }

        public class RenameAction extends RenameResourceAction {
            public RenameAction(Shell shell) {
                super(shell);
            }

            public void run() {
                List resources = getSelectedResources();
                IResource resource = null;
                if (resources.size() == 1) {
                    resource = (IResource) resources.get(0);
                }
                if (resource == null) {
                    return;
                }
                if (checkResource(resource)) {
                    super.run();
                }
            }
        }
    }

    public void fillReport(final AbstractReport report) {

        final String topNodeTitle = "Project Hierarchy";
        final TreeNode node = new TreeNode(topNodeTitle);
        final TreeItem[] items = getViewer().getTree().getItems();
        for (final TreeItem item : items) {
            final TreeNode myNode = new TreeNode(item.getText());
            node.addNode(myNode);
            fillNode(myNode, item);
        }

        final TreeReportItem reportItem = new TreeReportItem();
        reportItem.addNode(node);
        reportItem.setName(topNodeTitle);
        reportItem.setDescription("Expanded nodes");
        report.addReportData(reportItem);
    }

    @Override
    protected void restoreState(final IMemento memento) {
        try {
            super.restoreState(memento);
        } catch (final SWTException ex) {
            // FIXME Try investigate why this happens SeUiPlugin.logError(ex);
        }
    }

    private void fillNode(final TreeNode parent, final TreeItem item) {
        for (final TreeItem treeItem : item.getItems()) {
            final TreeNode node = new TreeNode(treeItem.getText());
            parent.addNode(node);
            if (treeItem.getExpanded()) {
                fillNode(node, treeItem);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        IFWFacade.removeChangeListener(importListener);
    }

    @Override
    protected void editorActivated(final IEditorPart editor) {
        if (!isLinkingEnabled()) {
            return;
        }
        if (editor != null) {
            IEditorInput input = editor.getEditorInput();
            if (input instanceof LogViewInput) {
                LogViewInput lvi = (LogViewInput) input;
                IFolder folder = lvi.getLogsetDirectory();

                ISelection newSelection = new StructuredSelection(folder);
                if (getTreeViewer().getSelection().equals(newSelection)) {
                    getTreeViewer().getTree().showSelection();
                } else {
                    getTreeViewer().setSelection(newSelection, true);
                }
            }
        }
    }

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        IResource resourceSelected = null;
        resourceSelected = getResource(event.getSource());
        boolean flag = false;
        if (resourceSelected != null) {

            switch (event.keyCode) {
            case SWT.DEL:
            case SWT.F2:
                flag = checkResource(resourceSelected);
                if (!flag) {
                    event.doit = false;
                    event.stateMask = 1;
                }
                break;
            }
        }
        super.handleKeyPressed(event);
    }

    public static boolean checkResource(IResource resource) {
        boolean flag = false;
        if (resource instanceof IFile) {
            flag = check((IFile) resource);
        } else if (resource instanceof IFolder) {
            flag = check((IFolder) resource);
        } else if (resource instanceof IProject) {
            flag = check((IProject) resource);
        }
        return flag;
    }

    private static boolean check(IFile resource) {
        Logset logset = CaseFileManager.getLogset(resource);
        if (logset == null) {
            return true;
        }
        if (logset.isLocked()) {
            showError();
            return false;
        } else if (logset.contains(resource)) {
            showError2();
            return false;
        } else if (resource.getFileExtension().equals("bin")) {
            return false;
        }
        return true;

    }

    private static boolean check(IFolder resource) {
        DirectoryLogSession lSession;
        if (DirectoryLogSession.isLogSession(resource)) {
            lSession = (DirectoryLogSession) DirectoryLogSession.valueOf(resource);    
            if (lSession != null) {
                Logset logset = Logset.valueOf(lSession.getId());
                if (logset == null) {
                    return true;
                }
                if (logset.isLocked()) {
                    showError();
                    return false;
                } else {
                    logset.closeIMBinaryStream();
                }
            }
        }
        return true;
    }

    private static boolean check(IProject resource) {
        try {
            if (resource.hasNature(SystemExplorerNature.NATURE_ID)) {
                IResource[] resources = resource.members();
                if (resources != null) {
                    for (int i = 0; i < resources.length; i++) {
                        if (resources[i] instanceof IFolder) {
                            if (((IFolder) resources[i])
                                    .findMember(DirectoryLogSession.CONFIG_FILE) != null) {
                                Logset logset = Logset
                                        .valueOf(DirectoryLogSession.valueOf(
                                                resources[i]).getId());
                                if (logset.isLocked()) {
                                    showError();
                                    return false;
                                } else {
                                    logset.closeIMBinaryStream();
                                }
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
            return false;
        }
        return true;
    }

    private static void showError() {
        MessageDialog
                .openError(PlatformUI.getWorkbench().getDisplay()
                        .getActiveShell(), "LogAnalyzer Error",
                        "Operation not allowed while a import/deimport operation is running.");
    }

    private static void showError2() {
        MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
                .getActiveShell(), "LogAnalyzer Error",
                "Operation not allowed when log is configured.");
    }

    private IResource getResource(Object source) {
        IResource resource = null;
        Tree tree = null;
        if (source instanceof TreeViewer) {
            TreeViewer tv = (TreeViewer) source;
            tree = tv.getTree();
        } else if (source instanceof Tree) {
            tree = (Tree) source;
        }
        if (tree != null) {
            Object elementSelected = null;
            TreeItem tItem[] = tree.getSelection();
            if (tItem.length != 0) {
                elementSelected = tItem[0].getData();

                if (elementSelected instanceof IFile
                        || elementSelected instanceof IFolder
                        || elementSelected instanceof IProject) {
                    resource = (IResource) elementSelected;
                }
            }
        }
        return resource;
    }
}
