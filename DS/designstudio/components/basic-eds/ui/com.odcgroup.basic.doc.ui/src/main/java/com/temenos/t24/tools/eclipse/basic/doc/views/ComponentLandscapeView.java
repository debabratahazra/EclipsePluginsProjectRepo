package com.temenos.t24.tools.eclipse.basic.doc.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.dialogs.CreateDocumentDialog;
import com.temenos.t24.tools.eclipse.basic.document.DocumentCostants;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;
import com.temenos.t24.tools.eclipse.basic.views.document.ComponentFilterController;
import com.temenos.t24.tools.eclipse.basic.views.document.IDocumentNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24BatchDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24CobDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ComponentDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ComponentViewController;
import com.temenos.t24.tools.eclipse.basic.views.document.T24DataFileDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24ProductDocNode;
import com.temenos.t24.tools.eclipse.basic.views.document.T24SourcesChildDocNOde;
import com.temenos.t24.tools.eclipse.basic.views.document.T24TablesDocNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

public class ComponentLandscapeView extends ViewPart {

    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.view.ComponentView";
    private T24ComponentViewController viewController = T24ComponentViewController.getInstance();
    public static ComponentFilterController filterController;
    private TreeViewer treeViewer;
    private MenuManager menuMgr;
    private String selectedProduct;
    private String selectedComponent;
    /**
     * Action to refresh the view
     */
    private Action actionRefresh;
    private Action actionSort;
    private Action actionFilter;

    @Override
    public void createPartControl(Composite parent) {
        createTreeView(parent);
        createActions();
        createToolBar();
        filterController = new ComponentFilterController(treeViewer, viewController, actionFilter);
        addListeners();
        initContextMenu();
        parent.pack();
        filterController.loadView();
    }

    @Override
    public void setFocus() {
        // Nothing to do
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
        treeViewer = new TreeViewer(parent);
        treeViewer.setLabelProvider(new T24TreeViewLabelProvider());
        treeViewer.setContentProvider(new T24TreeViewContentProvider());
        GridData treeData = new GridData();
        treeData.grabExcessHorizontalSpace = true;
        treeData.grabExcessVerticalSpace = true;
        treeData.horizontalAlignment = GridData.FILL;
        treeData.verticalAlignment = GridData.FILL;
        treeViewer.getControl().setLayoutData(treeData);
        parent.setLayout(layout3);
    }

    private void createToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(actionRefresh);
        toolbarManager.add(actionSort);
        toolbarManager.add(actionFilter);
    }

    private void addListeners() {
        ComponentViewListener listener = new ComponentViewListener();
        treeViewer.addSelectionChangedListener(listener);
        treeViewer.addDoubleClickListener(new DocumentViewListener());
        treeViewer.getTree().addMouseListener(listener);
        treeViewer.getTree().addKeyListener(listener);
    }

    private class ComponentViewListener implements ISelectionChangedListener, IMenuListener, MouseListener, KeyListener {

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
                    if (selectedNode instanceof T24ProductDocNode) {
                        selectedProduct = selectedNode.getLabel();
                        selectedComponent = null;
                    } else if (selectedNode instanceof T24ComponentDocNode) {
                        selectedComponent = selectedNode.getLabel();
                        selectedProduct = ((T24ComponentDocNode) selectedNode).getParent().getLabel();
                    } else if (selectedNode instanceof T24TablesDocNode) {
                        IT24TreeViewNode compNode = ((T24TablesDocNode) selectedNode).getParent();
                        selectedComponent = compNode.getLabel();
                        selectedProduct = compNode.getParent().getLabel();
                    } else if (selectedNode instanceof T24DataFileDocNode) {
                        IT24TreeViewNode compNode = ((T24DataFileDocNode) selectedNode).getParent().getParent();
                        selectedComponent = compNode.getLabel();
                        selectedProduct = compNode.getParent().getLabel();
                    } else if (selectedNode instanceof T24CobDocNode) {
                        IT24TreeViewNode compNode = ((T24CobDocNode) selectedNode).getParent();
                        selectedComponent = compNode.getLabel();
                        selectedProduct = compNode.getParent().getLabel();
                    } else if (selectedNode instanceof T24BatchDocNode) {
                        IT24TreeViewNode compNode = ((T24BatchDocNode) selectedNode).getParent().getParent();
                        selectedComponent = compNode.getLabel();
                        selectedProduct = compNode.getParent().getLabel();
                    }
                }
            }
        }

        public void menuAboutToShow(IMenuManager manager1) {
            Action action = new Action() {

                public void run() {
                    CreateDocumentDialog dialog = new CreateDocumentDialog(getShell(), selectedComponent, selectedProduct);
                    dialog.open();
                }
            };
            action.setText("Create Document ");
            manager1.add(action);
        }

        public void mouseDoubleClick(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        public void mouseDown(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        public void mouseUp(MouseEvent e) {
            if (e.button == 3 && e.widget == treeViewer.getTree() && selectedNode != null) {
                menuMgr.addMenuListener(this);
            }
        }

        public void keyPressed(KeyEvent arg0) {
            // TODO Auto-generated method stub
        }

        public void keyReleased(KeyEvent arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage("/icons/refresh.gif"));
        actionRefresh = new Action("Refresh View", imageDescriptor) {

            public void run() {
                refreshView();
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage(DocumentCostants.IMG_SORT_ACTION));
        actionSort = new Action("Sort", imageDescriptor) {

            public void run() {
                if (actionSort.isChecked()) {
                    treeViewer.setComparator(null);
                    actionSort.setChecked(false);
                } else {
                    treeViewer.setComparator(new ViewerComparator());
                    actionSort.setChecked(true);
                }
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage("/icons/component_filter.gif"));
        actionFilter = new RetargetAction("Filter", "Component Specific Filter", IAction.AS_CHECK_BOX);
        actionFilter.setImageDescriptor(imageDescriptor);
        actionFilter.setEnabled(true);
        actionFilter.setChecked(true);
        actionFilter.addPropertyChangeListener(new IPropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent arg0) {
                filterController.loadView();
            }
        });
    }

    /**
     * Refreshes the view by loading the appropriate content.
     */
    private void refreshView() {
        filterController.doRefresh();
    }

    private class DocumentViewListener implements IDoubleClickListener {

        private ISelection selection;
        private IDocumentNode selectedNode;

        public void doubleClick(DoubleClickEvent event) {
            selectedNode = null;
            if (event != null) {
                selection = event.getSelection();
            }
            if (selection instanceof IStructuredSelection) {
                Object obj = ((IStructuredSelection) selection).getFirstElement();
                if (obj instanceof IDocumentNode) {
                    if (obj instanceof T24ComponentDocNode) {
                        loadSubRoutineInfo((T24ComponentDocNode) obj);
                        viewController.loadComponentInteractionView((T24ComponentDocNode) obj);
                    }
                    if (obj instanceof T24ComponentDocNode || obj instanceof T24ProductDocNode || obj instanceof T24TablesDocNode
                            || obj instanceof T24CobDocNode) {
                        selectedNode = (IDocumentNode) obj;
                        T24ComponentViewController.getInstance().setT24DocViewAsNeedToBeActivated(true);
                        viewController.loadDocView(selectedNode);
                    }
                    if (obj instanceof T24SourcesChildDocNOde) {
                        selectedNode = (IDocumentNode) obj;
                        viewController.loadInLineDocView((T24SourcesChildDocNOde) selectedNode);
                    }
                }
            }
        }
    }

    private void initContextMenu() {
        menuMgr = new MenuManager("#PopupMenu");
        Tree tree = treeViewer.getTree();
        Menu menu = menuMgr.createContextMenu(tree);
        tree.setMenu(menu);
        menuMgr.setRemoveAllWhenShown(true);
    }

    private Shell getShell() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return window.getShell();
    }

    public String getViewID() {
        return VIEW_ID;
    }

    /**
     * load the subroutine information for opening the word document.
     * 
     * @param componentDocNode - T24ComponentDocNode
     */
    private void loadSubRoutineInfo(T24ComponentDocNode componentDocNode) {
        String componentName = componentDocNode.getLabel();
        String productName = componentDocNode.getParent().getLabel();
        // subroutine name args as "" because of without specific subroutine
        // access we have to open the word document.
        T24DocViewerUtil.subRoutineInfo = new SubroutineInfo("", productName, componentName);
    }
}
