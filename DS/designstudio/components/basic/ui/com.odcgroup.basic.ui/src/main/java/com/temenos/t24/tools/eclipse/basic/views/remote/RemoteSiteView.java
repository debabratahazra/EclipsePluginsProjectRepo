package com.temenos.t24.tools.eclipse.basic.views.remote;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestConstants;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

/**
 * This view class has a tree viewer which holds the remote system files from
 * the associated {@link RemoteSite}. It has an associated
 * {@link RemoteSiteViewController} which feeds data and depends on
 * {@link RemoteSiteViewHelper} to perform actions.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSiteView extends ViewPart {

    /**
     * Associated view controller
     */
    private RemoteSiteViewController viewController;
    /**
     * Associated view id which holds primary and secondary ids
     */
    private RemoteSiteViewID viewId;
    /**
     * Tree viewer
     */
    private TreeViewer remoteSystemViewer;
    /**
     * Action to move to parent directory
     */
    private Action actionUp;
    /**
     * Action to refresh the view
     */
    private Action actionRefresh;
    /**
     * Pop-up menu manager
     */
    private MenuManager menuMgr;

    public RemoteSiteView() {
    }

    @Override
    public void createPartControl(Composite parent) {
        createTreeView(parent);
        createActions();
        createToolBar();
        addListeners();
        initContextMenu();
        parent.pack();
        checkEmptyView();
    }

    private void createTreeView(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.verticalSpacing = 2;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);
        GridLayout layout3 = new GridLayout();
        layout3.numColumns = 1;
        remoteSystemViewer = new TreeViewer(parent,SWT.SINGLE);
        remoteSystemViewer.setLabelProvider(new T24TreeViewLabelProvider());
        remoteSystemViewer.setContentProvider(new T24TreeViewContentProvider());
        GridData treeData = new GridData();
        treeData.grabExcessHorizontalSpace = true;
        treeData.grabExcessVerticalSpace = true;
        treeData.horizontalAlignment = GridData.FILL;
        treeData.verticalAlignment = GridData.FILL;
        remoteSystemViewer.getControl().setLayoutData(treeData);
        parent.setLayout(layout3);
    }

    private void addListeners() {
        RemoteSystemViewListener listener = new RemoteSystemViewListener();
        remoteSystemViewer.addSelectionChangedListener(listener);
        remoteSystemViewer.getTree().addMouseListener(listener);
        remoteSystemViewer.getTree().addKeyListener(listener);
    }

    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage("/icons/up.gif"));
        actionUp = new Action("Go Up", imageDescriptor) {

            public void run() {
            	 if (viewController.getParentNode() == null) {
                  return;
                 }
                if (viewController.changeToParentDir()) {
                    remoteSystemViewer.setInput(viewController.getTree());
                    setViewTooltip();
                }
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage("/icons/refresh.gif"));
        actionRefresh = new Action("Refresh View", imageDescriptor) {

            public void run() {
                refreshView();
            }
        };
    }

    private void createToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(actionUp);
        toolbarManager.add(actionRefresh);
    }

    private void initContextMenu() {
        menuMgr = new MenuManager("#PopupMenu");
        Tree tree = remoteSystemViewer.getTree();
        Menu menu = menuMgr.createContextMenu(tree);
        tree.setMenu(menu);
        menuMgr.setRemoveAllWhenShown(true);
    }

    @Override
    public void setFocus() {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dispose() {
        viewController.closeView();
        super.dispose();
    }

    /**
     * Called to load the view with the content.
     */
    public void loadView() {
        if (viewController != null) {
            setViewName();
            setViewTooltip();
            remoteSystemViewer.setInput(viewController.getTree());
        }
    }

    /**
     * Clears the view.
     */
    public void clearView() {
        if (viewController != null) {
            remoteSystemViewer.setInput(viewController.emptyView());
        }
    }

    /**
     * Refreshes the view by loading the appropriate content.
     */
    private void refreshView() {
        if (viewController != null) {
            remoteSystemViewer.setInput(viewController.getTree());
        }
    }

    /**
     * Sets the view id.
     * 
     * @param viewId
     */
    public void setViewId(RemoteSiteViewID viewId) {
        this.viewId = viewId;
    }

    /**
     * Returns the view id.
     * 
     * @return
     */
    public RemoteSiteViewID getViewId() {
        return viewId;
    }

    /**
     * Sets the associated view controller.
     * 
     * @param viewController
     */
    public void setViewController(RemoteSiteViewController viewController) {
        this.viewController = viewController;
    }

    /**
     * Returns the associated view controller.
     * 
     * @return
     */
    public RemoteSiteViewController getViewController() {
        return viewController;
    }

    /**
     * sets the name to the view.
     */
    private void setViewName() {
        setPartName(viewController.getViewName());
    }

    /**
     * sets the tool tip to the view.
     */
    private void setViewTooltip() {
        setTitleToolTip(viewController.getViewTooltip());
    }

    private void checkEmptyView() {
        // TODO - Inform not to open from show view action
    }

    private class RemoteSystemViewListener implements ISelectionChangedListener, IMenuListener, MouseListener, KeyListener {

        private ISelection selection;
        private IT24TreeViewNode selectedNode;

        public void selectionChanged(SelectionChangedEvent event) {
            menuMgr.removeMenuListener(this);
            selectedNode = null;
            if (event != null) {
                selection = event.getSelection();
            }
            if (selection instanceof IStructuredSelection) {
                Object obj = ((IStructuredSelection) selection).getFirstElement();
                if (obj instanceof IT24TreeViewNode) {
                    selectedNode = (IT24TreeViewNode) obj;
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.widget == remoteSystemViewer.getTree() && e.keyCode == SWT.CR && selectedNode instanceof RemoteFileNode) {
                handleOpen((RemoteFileNode) selectedNode);
            }
        }

        public void mouseDoubleClick(MouseEvent event) {
            if (event.button != 1 || selectedNode == null) {
                return;
            }
            if (selectedNode instanceof RemoteFileNode) {
                handleOpen((RemoteFileNode) selectedNode);
            }
        }

        public void mouseDown(MouseEvent e) {
            // Nothing to do
        }

        public void mouseUp(MouseEvent e) {
            if (e.button == 3 && e.widget == remoteSystemViewer.getTree() && selectedNode != null) {
                menuMgr.addMenuListener(this);
            }
        }

        public void menuAboutToShow(IMenuManager manager1) {
            Action action = new Action() {

                public void run() {
                    boolean pwdChanged = viewController.goIntoDirectory((RemoteFileNode) selectedNode);
                    if (pwdChanged) {
                        remoteSystemViewer.setInput(viewController.getTree((RemoteFileNode) selectedNode));
                        setViewTooltip();
                    }
                }
            };
            action.setText("Go Into");
            if (selectedNode instanceof RemoteFileNode && ((RemoteFileNode) selectedNode).isDirectory()) {
                manager1.add(action);
            }
            action = new Action() {

                public void run() {
                    if (selectedNode instanceof RemoteFileNode) {
                        handleOpen((RemoteFileNode) selectedNode);
                    }
                }
            };
            action.setText("Open");
            manager1.add(action);
            action = new Action() {

                public void run() {
                    if (selectedNode instanceof RemoteFileNode) {
                        boolean deleted = RemoteSiteViewHelper.deleteSelected(viewController, (RemoteFileNode) selectedNode);
                        if (deleted) {
                            refreshView();
                        }
                    }
                }
            };
            action.setText("Delete");
            manager1.add(action);
            action = new Action() {

                public void run() {
                    boolean tested = RemoteSiteViewHelper.runAsTest(viewController, (RemoteFileNode) selectedNode);
                    if (!tested) {
                        RemoteOperationsLog.error("Unable to execute unit test " + selectedNode.getLabel());
                    }
                }
            };
            action.setText("Run As T24Unit");
            if (isUnitTest(selectedNode)) {
                manager1.add(action);
            }
        }

        private void handleOpen(RemoteFileNode node) {
            if (node.isDirectory()) {
                if (remoteSystemViewer.getExpandedState(node)) {
                    remoteSystemViewer.collapseToLevel(node, 1);
                } else {
                    remoteSystemViewer.expandToLevel(node, 1);
                }
            } else {
                RemoteSiteViewHelper.openSelected(viewController, node);
            }
        }

        public void keyPressed(KeyEvent e) {
            // Nothing to do
        }

        private boolean isUnitTest(IT24TreeViewNode selectedNode) {
            String fileName = "";
            if (selectedNode instanceof RemoteFileNode) {
                fileName = ((RemoteFileNode) selectedNode).getRemoteFile().getName();
            }
            if (fileName.startsWith(T24UnitTestConstants.T24UNIT_TEST_PART)) {
                return true;
            }
            return false;
        }
    }
}
