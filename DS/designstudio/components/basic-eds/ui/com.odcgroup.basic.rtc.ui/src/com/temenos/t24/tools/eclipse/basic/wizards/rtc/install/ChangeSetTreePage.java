package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.temenos.t24.tools.eclipse.basic.views.tree.ICheckBoxTreeNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewContentProvider;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

/**
 * 
 * @author ssethupathi
 * 
 */
public class ChangeSetTreePage extends WizardPage implements Listener {

    private CheckboxTreeViewer treeViewer;
    private Composite container;
    private InstallChangeSetWizard wizard;
    private Composite buttonComposite = null;    

    /**
     * Constructs this page object
     * 
     * @param selection
     */
    public ChangeSetTreePage(InstallChangeSetWizard wizard) {
        super("ChangesetTreePage");
        this.wizard = wizard;
        super.setDescription("Installs Changes into T24");
    }

    /**
     * {@inheritDoc}
     */
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NULL);
        drawLayouts(container);
        setControl(container);
    }

    /**
     * Builds up page by layouts and components
     * 
     * @param container
     */
    private void drawLayouts(Composite container) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        container.setLayout(gridLayout);
        //
        GridData gridData = new GridData(SWT.CENTER);
        gridData.minimumHeight = 200;
        gridData.minimumWidth = 500;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        //
        Label workitemReferenceLabel = new Label(container, SWT.NORMAL);
        workitemReferenceLabel.setText("Select and install");
        treeViewer = new CheckboxTreeViewer(container);
        treeViewer.setContentProvider(new T24TreeViewContentProvider());
        treeViewer.setLabelProvider(new T24TreeViewLabelProvider());
        treeViewer.getTree().setLayoutData(gridData);
        treeViewer.getTree().setLayout(gridLayout);
        
        // Select/Unselect All buttons - DS-6643 : BEGIN
        buttonComposite = new Composite(container, SWT.RESIZE);
		buttonComposite.setLayout(new GridLayout(3, false));
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);
		
		Label label = new Label(buttonComposite, SWT.RIGHT);
		GridData gd1 = new GridData();
		gd1.widthHint = 150;
		label.setLayoutData(gd1);
		
        Button selectButton = new Button(buttonComposite, SWT.PUSH);
		selectButton.setLayoutData(new GridData(SWT.END));
		selectButton.setText("Select All");
		selectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(true);
			}
		});

		Button deselectButton = new Button(buttonComposite, SWT.PUSH);
		deselectButton.setLayoutData(new GridData(SWT.END));
		deselectButton.setText("Unselect All");
		deselectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(false);
			}
		});
        
		addListeners();
        initWizard();
    }
    
    @SuppressWarnings("deprecation")
	public void setAllChecked(boolean state) {
    	TreeListener listener = new TreeListener();
		if (treeViewer != null) {	
			treeViewer.expandAll();
			treeViewer.setAllChecked(state);
			Object input = treeViewer.getInput();
			if (input instanceof IT24TreeViewRoot) {
				IT24TreeViewNode[] nodes = ((IT24TreeViewRoot) input).getParentNodes();
				for (IT24TreeViewNode node : nodes) {
					if (node instanceof ChangesetNode) {
						ChangesetNode changesetNode = (ChangesetNode) node;
						listener.checkNodes(changesetNode.getChildren(), state);
						changesetNode.setChecked(state);
					}
				}
			}
		}
	}
    // Select/Unselect All buttons - DS-6643 : END
    
    private void addListeners() {
        treeViewer.addCheckStateListener(new TreeListener());
    }

    private void initWizard() {
    }

    public void handleEvent(Event event) {
    }

    public boolean isPageComplete() {
        if (getErrorMessage() == null && inputComplete()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean inputComplete() {
        return true;
    }

    @SuppressWarnings("deprecation")
	public void setInput(IT24TreeViewRoot root) {
        treeViewer.setInput(root);
        treeViewer.expandAll();
        treeViewer.setGrayedElements(root.getParentNodes());
        treeViewer.setAllChecked(true);
    }

    public List<T24ChangeSet> getSelectedItems() {
        Object input = treeViewer.getInput();
        if (input instanceof IT24TreeViewRoot) {
            return wizard.getViewController().getChangeSets((IT24TreeViewRoot) input);
        }
        return null;
    }

    private class TreeListener implements ICheckStateListener {

        public void checkStateChanged(CheckStateChangedEvent arg0) {
            Object obj = arg0.getElement();
            boolean checked = arg0.getChecked();
            if (obj instanceof ChangesetNode) {
                ChangesetNode checkedNode = ((ChangesetNode) obj);
                ICheckBoxTreeNode[] children = checkedNode.getChildren();
                checkNodes(children, checked);
                checkedNode.setChecked(checked);
            }
            if (obj instanceof ChangeNode) {
                ChangeNode checkedNode = ((ChangeNode) obj);
                ICheckBoxTreeNode parent = checkedNode.getParent();
                if (checked) {
                    treeViewer.setChecked(parent, true);
                    parent.setChecked(checked);
                } else {
                    boolean checkIt = atleastOneChecked(parent.getChildren());
                    treeViewer.setChecked(parent, checkIt);
                    parent.setChecked(checkIt);
                }
                checkedNode.setChecked(checked);
            }
        }

        private boolean atleastOneChecked(IT24TreeViewNode[] children) {
            for (IT24TreeViewNode node : children) {
                if (treeViewer.getChecked(node)) {
                    return true;
                }
            }
            return false;
        }

        private void checkNodes(ICheckBoxTreeNode[] children, boolean checked) {
            for (ICheckBoxTreeNode node : children) {
                treeViewer.setChecked(node, checked);
                node.setChecked(checked);
            }
        }
    }
}
