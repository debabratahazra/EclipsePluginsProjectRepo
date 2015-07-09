package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestConstants;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeView;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;

public class CallsHierarchyView extends ViewPart implements IT24TreeView {

    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.view.callsHierarchyView";
    private Label subroutineNameLable;
    private Text subroutineNameText;
    private Label siteNameLabel;
    private Combo siteCombo;
    private Button goButton;
    private TreeViewer callsHierarchyViewer;
    private CallsHierarchyViewController viewController = CallsHierarchyViewController.getInstance();
    private Action clearViewAction;
    private MenuManager menuMgr;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(Composite parent) {
        createTreeView(parent);
        createActions();
        createToolBar();
        addListeners();
        initContextMenu();
        initSiteCombo();
        updateButton();
        parent.pack();
    }

    /**
     * Creates the view
     * 
     * @param parent
     */
    private void createTreeView(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        parent.setLayout(layout);
        Group group1 = new Group(parent, SWT.HORIZONTAL);
        group1.setParent(parent);
        GridLayout layout2 = new GridLayout();
        layout2.numColumns = 5;
        GridData layoutData = new GridData(SWT.FILL);
        subroutineNameLable = new Label(group1, SWT.NORMAL);
        subroutineNameLable.setText("Subroutine Name:");
        subroutineNameText = new Text(group1, SWT.BORDER);
        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.minimumWidth = 300;
        subroutineNameText.setLayoutData(data);
        siteNameLabel = new Label(group1, SWT.NORMAL);
        siteNameLabel.setText("Remote Site");
        siteCombo = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
        goButton = new Button(group1, SWT.PUSH);
        goButton.setText("Search");
        callsHierarchyViewer = new TreeViewer(parent);
        callsHierarchyViewer.setLabelProvider(new CallsHierarchyLabelProvider());
        callsHierarchyViewer.setContentProvider(new T24TreeViewContentProvider());
        GridData treeData = new GridData();
        treeData.grabExcessHorizontalSpace = true;
        treeData.grabExcessVerticalSpace = true;
        treeData.horizontalAlignment = GridData.FILL;
        treeData.verticalAlignment = GridData.FILL;
        group1.setLayout(layout2);
        group1.setLayoutData(layoutData);
        callsHierarchyViewer.getControl().setLayoutData(treeData);
        ColumnViewerToolTipSupport.enableFor((ColumnViewer) callsHierarchyViewer);
    }

    private void addListeners() {
        ViewListener listener = new ViewListener();
        subroutineNameText.addKeyListener(listener);
        siteCombo.addMouseListener(listener);
        siteCombo.addSelectionListener(listener);
        goButton.addMouseListener(listener);
        callsHierarchyViewer.getTree().addKeyListener(listener);
        callsHierarchyViewer.getTree().addMouseListener(listener);
        callsHierarchyViewer.addSelectionChangedListener(listener);
    }

    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil
                .getImage(T24UnitTestConstants.IMAGE_CLEAR_VIEW));
        clearViewAction = new Action("Clear view", imageDescriptor) {

            public void run() {
                clearView();
            }
        };
    }

    private void initSiteCombo() {
        loadCurrentSite();
    }

    private void updateButton() {
        String subroutineName = subroutineNameText.getText();
        String siteName = siteCombo.getText();
        if (subroutineName != null && subroutineName.length() > 0 && siteName != null && siteName.length() > 0) {
            goButton.setEnabled(true);
        } else {
            goButton.setEnabled(false);
        }
    }

    private void clearView() {
        callsHierarchyViewer.setInput(new CallsHierarchyRoot());
    }

    private void createToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(clearViewAction);
    }

    private void initContextMenu() {
        menuMgr = new MenuManager("#PopupMenu");
        Tree tree = callsHierarchyViewer.getTree();
        Menu menu = menuMgr.createContextMenu(tree);
        tree.setMenu(menu);
        menuMgr.setRemoveAllWhenShown(true);
    }

    @Override
    public void setFocus() {
        // Nothing to do
    }

    private class ViewListener implements ISelectionChangedListener, IMenuListener, MouseListener, KeyListener, SelectionListener {

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
            if (e.widget == callsHierarchyViewer.getTree() && e.keyCode == SWT.CR && selectedNode instanceof CallsHierarchyNode) {
                handleOpen((CallsHierarchyNode) selectedNode);
            }
            if (e.widget == subroutineNameText) {
                updateButton();
            }
        }

        public void mouseDoubleClick(MouseEvent event) {
            if (event.button != 1 || selectedNode == null) {
                return;
            }
            if (selectedNode instanceof CallsHierarchyNode) {
                handleOpen((CallsHierarchyNode) selectedNode);
            }
        }

        public void mouseDown(MouseEvent e) {
            // Nothing to do
        }

        public void mouseUp(MouseEvent e) {
            if (e.button == 1 && e.widget == goButton) {
                String subroutineName = subroutineNameText.getText();
                if (subroutineName != null && subroutineName.length() > 0) {
                    IT24TreeViewRoot tree = new CallsHierarchyRoot();
                    tree.addParentNode(viewController.getParentNode(siteCombo.getText(), subroutineName));
                    callsHierarchyViewer.setInput(tree);
                }
            }
            if (e.button == 3 && selectedNode instanceof CallsHierarchyNode) {
                menuMgr.addMenuListener(this);
            }
            if (e.widget == siteCombo) {
                loadSiteCombo();
                updateButton();
            }
        }

        public void menuAboutToShow(IMenuManager manager1) {
            Action action = new Action() {

                public void run() {
                    if (selectedNode instanceof CallsHierarchyNode) {
                        handleOpen((CallsHierarchyNode) selectedNode);
                    }
                }
            };
            action.setText("Open");
            manager1.add(action);
        }

        private void handleOpen(CallsHierarchyNode node) {
            T24Subroutine subroutine = ((CallsHierarchyNode) node).getSubroutine();
            String fileName = subroutine.getName();
            viewController.openFile(fileName);
        }

        public void keyPressed(KeyEvent e) {
            // Nothing to do
        }

        public void widgetDefaultSelected(SelectionEvent e) {
            // Nothing to do
        }

        public void widgetSelected(SelectionEvent e) {
            if (e.widget == siteCombo) {
                String selectedSite = siteCombo.getText();
                siteCombo.setText(selectedSite);
                updateButton();
            }
        }
    }

    public void refresh() {
        // Nothing to do
    }

    private void loadSiteCombo() {
        String[] sites = viewController.getRemoteSites();
        if (sites != null && sites.length > 0) {
            String selectedSite = siteCombo.getText();
            siteCombo.removeAll();
            siteCombo.setItems(sites);
            if (selectedSite != null) {
                siteCombo.setText(selectedSite);
            }
        }
    }

    private void loadCurrentSite() {
        String defaultSite = viewController.getCurrentSite();
        if (defaultSite != null && defaultSite.length() > 0) {
            siteCombo.setText(defaultSite);
        } else {
            String[] items = siteCombo.getItems();
            if (items != null && items.length > 0 && items[0] != null) {
                siteCombo.setText(items[0]);
            }
        }
    }
}
