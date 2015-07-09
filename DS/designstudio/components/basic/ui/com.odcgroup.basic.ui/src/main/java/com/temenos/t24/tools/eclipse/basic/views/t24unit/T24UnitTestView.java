package com.temenos.t24.tools.eclipse.basic.views.t24unit;

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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestConstants;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestSuite;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeView;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

/**
 * The view class responsible for the T24 Unit test view in the workbench. This
 * class builds all the components of the view and adds necessary listeners and
 * is updated from the View controller {@link T24UnitTestViewController}
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestView extends ViewPart implements IT24TreeView {

    /** ID string of the view */
    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.viewT24UnitTest";
    /** View controller which supplies data to this view */
    private T24UnitTestViewController viewController = T24UnitTestViewController.getInstance();
    /** Tree viewer */
    private TreeViewer tUnitViewer;
    private MenuManager menuMgr = null;
    /** Execution time label */
    private Label executionTimeLabel;
    /** Runs count label */
    private Label runsCountLabel;
    /** Error count label */
    private Label errorCountLabel;
    /** Failure count label */
    private Label failureCountLabel;
    /** Actions on the pop-up menu */
    private Action clearViewAction;
    private Action rerunAction;
    private Action failedOnlyAction;
    private boolean failedTestsOnly = false;
    private final String POPUP_MENU = "#PopupMenu";

    /**
     * Builds up the layouts and components which are part of the T24 Unit test
     * view
     */
    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.verticalSpacing = 2;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);
        // Execution time layout
        GridLayout executionTimeLayout = new GridLayout();
        executionTimeLayout.numColumns = 1;
        GridData executionTimeLayoutData = new GridData(GridData.FILL);
        executionTimeLayoutData.minimumWidth = 300;
        executionTimeLabel = new Label(parent, SWT.HORIZONTAL);
        executionTimeLabel.setLayoutData(executionTimeLayoutData);
        parent.setLayout(executionTimeLayout);
        // Execution count layout
        GridLayout layout1 = new GridLayout();
        layout1.numColumns = 6;
        layout1.makeColumnsEqualWidth = true;
        Group resultCountGroup = new Group(parent, SWT.HORIZONTAL);
        resultCountGroup.setParent(parent);
        Label runsLabel1 = new Label(resultCountGroup, SWT.HORIZONTAL);
        runsLabel1.setText("Runs:");
        runsCountLabel = new Label(resultCountGroup, SWT.HORIZONTAL);
        Label runsLabel2 = new Label(resultCountGroup, SWT.HORIZONTAL);
        runsLabel2.setText("Error:");
        errorCountLabel = new Label(resultCountGroup, SWT.HORIZONTAL);
        Label runsLabel3 = new Label(resultCountGroup, SWT.HORIZONTAL);
        runsLabel3.setText("Failed:");
        failureCountLabel = new Label(resultCountGroup, SWT.HORIZONTAL);
        GridData layoutData1 = new GridData(SWT.FILL);
        layoutData1.grabExcessHorizontalSpace = true;
        layoutData1.minimumWidth = 100;
        layoutData1.horizontalAlignment = GridData.FILL;
        resultCountGroup.setLayoutData(layoutData1);
        resultCountGroup.setLayout(layout1);
        // Progress bar to show result
        // this functionality can be removed until there is a proper swt
        // available.
        // resultBar = new ProgressBar(parent, SWT.SMOOTH);
        // resultBar.setBackground(new Color(null, 255, 255, 255));
        // GridData resultBarData = new GridData();
        // resultBarData.grabExcessHorizontalSpace = true;
        // resultBarData.horizontalAlignment = GridData.FILL;
        // resultBar.setLayoutData(resultBarData);
        // Tree viewer component
        tUnitViewer = new TreeViewer(parent);
        tUnitViewer.setLabelProvider(new T24TreeViewLabelProvider());
        tUnitViewer.setUseHashlookup(true);
        GridData layoutData = new GridData();
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        layoutData.horizontalAlignment = GridData.FILL;
        layoutData.verticalAlignment = GridData.FILL;
        tUnitViewer.getControl().setLayoutData(layoutData);
        // Actions
        createActions();
        // Toolbar to hold Rerun and Failure only actions
        createToolbar();
        setExecutionLabelData();
        setTestCounts();
        // Listeners to the components
        addListeners();
        tUnitViewer.setContentProvider(new T24TreeViewContentProvider());
        tUnitViewer.setInput(getInput());
        initContextMenu();
        // initProgressBar();
    }

    /**
     * {@inheritDoc}
     */
    public void refresh() {
        setExecutionLabelData();
        setTestCounts();
        // refreshProgressBar();
        tUnitViewer.setInput(getInput());
    }

    @Override
    public void setFocus() {
    }

    /**
     * Adds listeners to the tree viewer
     */
    private void addListeners() {
        TUnitViewListener listener = new TUnitViewListener();
        tUnitViewer.addSelectionChangedListener(listener);
        tUnitViewer.getTree().addMouseListener(listener);
    }

    /**
     * Initializes context menu of the tree viewer
     */
    private void initContextMenu() {
        menuMgr = new MenuManager(POPUP_MENU);
        Tree tree = tUnitViewer.getTree();
        Menu menu = menuMgr.createContextMenu(tree);
        tree.setMenu(menu);
        menuMgr.setRemoveAllWhenShown(true);
    }

    /**
     * Sets the execution time to the label
     */
    private void setExecutionLabelData() {
        Double time = viewController.getExecutionTime();
        String data = "Finished in " + time.toString() + " milliseconds";
        executionTimeLabel.setText(data);
        executionTimeLabel.setSize(200, 20);
    }

    /**
     * Sets the counts to the label
     */
    private void setTestCounts() {
        String data = viewController.getPassedTestsCount() + "/" + viewController.getAllTestsCount();
        runsCountLabel.setText(data);
        runsCountLabel.setSize(30, 15);
        data = "" + viewController.getErrorTestsCount();
        errorCountLabel.setText(data);
        errorCountLabel.setSize(30, 15);
        data = "" + viewController.getFailedTestsCount();
        failureCountLabel.setText(data);
        failureCountLabel.setSize(30, 15);
    }

    /**
     * Initialises the progress bar
     */
    // private void initProgressBar() {
    // resultBar.setSelection(resultBar.getMaximum());
    // resultBar.setForeground(new Color(null, 139, 131, 134));
    // }
    /**
     * Refreshes the progress bar to show the progress
     */
    // private void refreshProgressBar() {
    // int testCount = viewController.getTestsToRun();
    // int progressBarLength = resultBar.getMaximum();
    // if (testCount <= 0) {
    // initProgressBar();
    // return;
    // }
    // Integer progressLength = new Integer(progressBarLength / testCount);
    // int tests = viewController.getAllTestsCount();
    // int value = progressLength * tests;
    // if ((progressBarLength - value) < progressLength) {
    // value = progressBarLength;
    // }
    // resultBar.setSelection(value);
    // if (viewController.atleastOneFailed()) {
    // resultBar.setForeground(new Color(null, 176, 23, 41));
    // } else {
    // resultBar.setForeground(new Color(null, 0, 255, 0));
    // }
    // }
    /**
     * Creates the actions that sit on the toolbar of the view
     */
    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil
                .getImage(T24UnitTestConstants.IMAGE_CLEAR_VIEW));
        clearViewAction = new Action("Clear view", imageDescriptor) {

            public void run() {
                viewController.clearView();
                refresh();
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage(T24UnitTestConstants.IMAGE_FILE_RERUN));
        rerunAction = new Action("Re run test", imageDescriptor) {

            public void run() {
                viewController.reRun();
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage(T24UnitTestConstants.IMAGE_FILE_TSUITFAIL));
        failedOnlyAction = new Action("Show failures only", imageDescriptor) {

            public void run() {
                failedTestsOnly = failedOnlyAction.isChecked();
                tUnitViewer.setInput(getInput());
            }
        };
        failedOnlyAction.setChecked(failedTestsOnly);
    }

    /**
     * Creates actions in the tool bar
     */
    private void createToolbar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(clearViewAction);
        toolbarManager.add(rerunAction);
        toolbarManager.add(failedOnlyAction);
    }

    /**
     * Gets the test suit from the controller
     * 
     * @return
     */
    private T24UnitTestSuite getInput() {
        return viewController.getTestSuite(failedTestsOnly);
    }

    /**
     * Dedicated listener for this view class
     */
    private class TUnitViewListener implements ISelectionChangedListener, MouseListener, IMenuListener {

        private ISelection selection;
        private IT24TreeViewNode selectedNode;

        /**
         * {@inheritDoc}
         */
        public void mouseDown(MouseEvent event) {
            // do nothing!
        }

        /**
         * {@inheritDoc}
         */
        public void mouseUp(MouseEvent event) {
            if (event.button == 3 && selectedNode != null) {
                menuMgr.addMenuListener(this);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void mouseDoubleClick(MouseEvent event) {
            if (event.button == 1 && selectedNode != null) {
                viewController.openSelectedNode(selectedNode);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void selectionChanged(SelectionChangedEvent event) {
            selectedNode = null;
            menuMgr.removeMenuListener(this);
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

        /**
         * {@inheritDoc}
         */
        public void menuAboutToShow(IMenuManager manager) {
            Action action = new Action() {

                public void run() {
                    super.run();
                    viewController.openSelectedNode(selectedNode);
                }
            };
            action.setText("Go to file");
            manager.add(action);
            action = new Action() {

                public void run() {
                    super.run();
                    viewController.run(selectedNode);
                }
            };
            action.setText("Run");
            manager.add(action);
        }
    }
}
