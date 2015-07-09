package com.odcgroup.page.domain.ui.dialog;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.page.domain.filter.DomainFilter;
import com.odcgroup.page.domain.ui.view.DomainContentProvider;
import com.odcgroup.page.domain.ui.view.DomainLabelProvider;
import com.odcgroup.page.domain.ui.view.DomainSorter;

/**
 * A Dialog that helps the user to choose an domain element  
 * 
 * @author atr
 */
public class DomainChooserDialog extends TitleAreaDialog implements ISelectionChangedListener {
	
	/** */
	private String title = "Undefined Title";
	
	/** */
	private String message = "Undefined message";
	
	/** */
	private TreeViewer treeViewer;
	
	/** */
	private DomainRepository repository;
	
	/** */
	private DomainFilter filter;
	
	/** */
	private MdfName entityName;
	
	/** */
	private MdfName attributeName;

	/** */
	private String selectedPath = null;
	
	/** */
	private boolean collapsed = false;

	private boolean acceptChildrenOfDatasetMappedProperty = false;
	
	public void setAcceptChildrenOfDatasetMappedProperty(boolean value) {
		 acceptChildrenOfDatasetMappedProperty = value;
	}
	/**
	 * @param parentShell
	 * @param title
	 * @param message
	 * @param repository
	 * @param filter
	 * @param entityName
	 * @param attributeName
	 */
	public DomainChooserDialog(Shell parentShell, String title, String message, DomainRepository repository, DomainFilter filter, MdfName entityName, MdfName attributeName) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.FILL);
		setHelpAvailable(false);
		this.title = title;
		this.message = message;
		this.repository = repository;
		this.filter = filter;
		this.entityName = entityName;
		this.attributeName = attributeName;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(message);
	}	
	
	/**
	 * @param collapsed
	 */
	public void setTreeCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	
	/**
	 * Configures the shell.
	 * 
	 * @param shell
	 *            The shell to configure
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
		shell.setSize(450, 550);
	}	
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite top = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);		
		top.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		top.setLayoutData(gridData);
		
        treeViewer = new TreeViewer(top, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		gridData = new GridData(GridData.FILL_BOTH);
		treeViewer.getControl().setLayoutData(gridData);
        treeViewer.addSelectionChangedListener(this);

		treeViewer.setContentProvider(new DomainContentProvider(repository, acceptChildrenOfDatasetMappedProperty));
        treeViewer.setLabelProvider(new DomainLabelProvider());
        treeViewer.setSorter(new DomainSorter());
        
        ViewerFilter domainFilter = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (parentElement == entityName) return true;
				return filter != null ? filter.accept((MdfModelElement)element) : true;
			}
        };
        treeViewer.setFilters(new ViewerFilter[]{domainFilter});

		treeViewer.setInput(entityName);
		treeViewer.expandAll();
		
        // update the selection in the tree view
		Tree tree = treeViewer.getTree();
		tree.deselectAll();
		
		TreeItem selection = null;
		if (tree.getItemCount() > 0) {
			TreeItem root = tree.getItem(0);
			for (TreeItem item : root.getItems()) {
				MdfModelElement element = (MdfModelElement)item.getData();
				if (attributeName.equals(element.getQualifiedName())) {
					selection = item;
					break;
				}
			}
		}		
		
		if (collapsed) {
			TreeItem root = tree.getItem(0);
			for (TreeItem item : root.getItems()) {
				if (item.getItems().length > 0) {
					item.setExpanded(false);
				}
			}
		} else {
			treeViewer.expandToLevel(1);
		}
		if (selection != null) {
			tree.setSelection(new TreeItem[]{selection});
			tree.showSelection();
		}

		return top;
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		
		TreeSelection ts = (TreeSelection) selection;
		TreePath[] paths = ts.getPaths();
		TreePath path = paths[0];
		StringBuilder builder = new StringBuilder();
		final int MAX = path.getSegmentCount();
		for (int sx = 1; sx < MAX; sx++) {
			// sx starts at 1, we skip the dataset name.
			MdfModelElement mdf = (MdfModelElement)path.getSegment(sx);
			builder.append(mdf.getName());
			if (sx < MAX-1) {
				builder.append(".");
			}
		}
		
		selectedPath = builder.toString();		
		getButton(IDialogConstants.OK_ID).setEnabled(StringUtils.isNotBlank(selectedPath));
	}	
	
	/**
	 * @return MdfName
	 */
	public String getSelectedPath() {
		return selectedPath;
	}

}
