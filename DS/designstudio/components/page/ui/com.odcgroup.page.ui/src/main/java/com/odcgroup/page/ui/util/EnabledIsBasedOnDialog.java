package com.odcgroup.page.ui.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.page.domain.filter.DomainFilter;
import com.odcgroup.page.domain.ui.view.DomainContentProvider;
import com.odcgroup.page.domain.ui.view.DomainLabelProvider;
import com.odcgroup.page.domain.ui.view.DomainSorter;
import com.odcgroup.page.model.properties.enabled.EnabledIsBaseOnCondition;

/**
 * Dialog used to edit the "Enabled is based on" property
 * @author yan
 */
public class EnabledIsBasedOnDialog extends TitleAreaDialog implements ISelectionChangedListener {

	/** Window title */
	private static final String TITLE = "Enabled is based on";
	
	/** Font used in coding text box */
	private Font codeFont;
	
	/** Simplified radio button */
	private Button simplifiedRadio;

	/** Text area used to enter code (advanced condition) */
	private Text advancedText;

	/** Initial condition */
	private EnabledIsBaseOnCondition initialCondition;
	
	/** Result of the dialog input */
	private EnabledIsBaseOnCondition resultCondition;
	
	/** Stack panel use to flip between simplified and advanced condition */
	private Composite stackPanel; 

	/** Simplified panel used for simple condition */
	private Composite simplifiedPanel; 
	
	/** Advanced panel used for advanced condition */
	private Composite advancedPanel;
	
	/** Tree viewer used in simplified condition */
	private TreeViewer simplifiedTreeViewer;
	
	/** The domain repository */
	private DomainRepository repository;
	
	/** The domain filter, used by the domain tree */
	private DomainFilter filter;
	
	/** The root Domain Entity's name */
	private MdfName entityName;

	/** The Domain attribute name */
	private MdfName attributeName;

	/** The domain attribute the user has selected in the domain tree view*/
	private MdfName selectedAttributeName = null;
	
	/**
	 * @param parentShell
	 * @param repository
	 * @param filter
	 * @param domainEntity 
	 * @param initialCondition 
	 */
	public EnabledIsBasedOnDialog(Shell parentShell, DomainRepository repository, DomainFilter filter, MdfName domainEntity, EnabledIsBaseOnCondition initialCondition) {
		super(parentShell);
		setHelpAvailable(false);
		
		if (initialCondition == null) {
			throw new IllegalArgumentException("The initial condition cannot be null");
		}
		
		if (repository == null) {
			throw new IllegalArgumentException("The Domain Repository cannot be null");
		}

		this.initialCondition = initialCondition;
		this.repository = repository;
		this.filter = filter;
		this.entityName = domainEntity;
		this.attributeName = initialCondition.getSimplifiedCondition();
		
	}
	
	/**
	 * Make the dialog resizable
	 * @return true
	 */
	public boolean isResizable() {
		return true;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle(TITLE);
		setMessage("Enter the condition");
	}

	/**
	 * Configures the shell.
	 * @param shell
	 *            The shell to configure
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(TITLE);
		shell.setLocation(300, 100);
		shell.setSize(600, 408);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite top = (Composite) super.createDialogArea(parent);
		
    	// Font setup
    	codeFont = new Font(parent.getDisplay(), new FontData("Courier New", 10, SWT.NORMAL));
		
    	// Simplified/Advanced radio buttons
    	Composite radioPanel = new Composite(top, SWT.None);
    	GridLayout radioPanelLayout = new GridLayout(2, true);
    	radioPanelLayout.marginTop = 5;
    	radioPanelLayout.marginLeft = 8;
    	radioPanel.setLayout(radioPanelLayout);
    	
    	simplifiedRadio = new Button(radioPanel, SWT.RADIO);
    	simplifiedRadio.setSelection(initialCondition.isSimplified());
    	simplifiedRadio.setText("Simplified");
    	
    	Button advancedRadio = new Button(radioPanel, SWT.RADIO);
    	advancedRadio.setSelection(!initialCondition.isSimplified());
    	advancedRadio.setText("Advanced");

    	stackPanel = new Composite(top, SWT.NONE);
    	stackPanel.setLayout(new StackLayout());
    	
    	// Stack layout used to switch between simplified/advanced condition
    	GridData stackLayoutData  = new GridData();
    	stackLayoutData.horizontalAlignment = SWT.FILL;
    	stackLayoutData.verticalAlignment = SWT.FILL;
    	stackLayoutData.grabExcessHorizontalSpace = true;
    	stackLayoutData.grabExcessVerticalSpace = true;
    	stackPanel.setLayoutData(stackLayoutData);
    	
    	// Simplified panel
    	simplifiedPanel = new Composite(stackPanel, SWT.NONE);
    	GridLayout simplifiedPanelGridLayout = new GridLayout();
    	simplifiedPanelGridLayout.marginLeft = 8;
    	simplifiedPanelGridLayout.marginRight = 8;
    	simplifiedPanel.setLayout(simplifiedPanelGridLayout);

    	simplifiedTreeViewer = new TreeViewer(simplifiedPanel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        GridData gridData = new GridData(GridData.FILL_BOTH);
		simplifiedTreeViewer.getControl().setLayoutData(gridData);
        simplifiedTreeViewer.addSelectionChangedListener(this);

		simplifiedTreeViewer.setContentProvider(new DomainContentProvider(repository));
    	simplifiedTreeViewer.setLabelProvider(new DomainLabelProvider());
    	simplifiedTreeViewer.setSorter(new DomainSorter());
        
        ViewerFilter domainFilter = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (parentElement == entityName) return true;
				return filter != null ? filter.accept((MdfModelElement)element) : true;
			}
        };
        simplifiedTreeViewer.setFilters(new ViewerFilter[]{domainFilter});
		simplifiedTreeViewer.setInput(entityName);
		simplifiedTreeViewer.expandAll();
		
    	// Advanced panel
    	advancedPanel = new Composite(stackPanel, SWT.NONE);
    	GridLayout advancedPanelGridLayout = new GridLayout();
    	advancedPanelGridLayout.marginLeft = 8;
    	advancedPanelGridLayout.marginRight = 8;
    	advancedPanel.setLayout(advancedPanelGridLayout);
    	
    	Composite labelComp = new Composite(advancedPanel, SWT.NONE);
    	GridLayout labelCompGridLayout = new GridLayout(2, false);
    	labelCompGridLayout.marginWidth = 0;
    	labelCompGridLayout.marginHeight = 0;
    	labelComp.setLayout(labelCompGridLayout);
    	Label advancedLabel = new Label(labelComp, SWT.NONE);
    	advancedLabel.setText("The proper value must be assigned to the existing boolean variable");
    	Label conditionValLabel = new Label(labelComp, SWT.NONE);
    	conditionValLabel.setFont(codeFont);
    	conditionValLabel.setText("conditionVal");
		
    	advancedText = new Text(advancedPanel, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		advancedText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		advancedText.setFont(codeFont);
		String advancedCondition = initialCondition.getAdvancedCondition();
		advancedText.setText(advancedCondition!=null?advancedCondition:"");
		int pos = advancedText.getText().length();
		advancedText.setSelection(pos);
    	
    	// Manage events
    	simplifiedRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				simplifiedPanelOnTop();
				updateErrorMessage();
			}
		});
    	
    	advancedRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				advancedPanelOnTop();
				updateErrorMessage();
			}
		});
    	
    	advancedText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateErrorMessage();
			}
    	});
    	
    	// Initial setup: display the appropriate panel (simplified/advanced) on top
    	if (initialCondition.isSimplified()) {
    		simplifiedPanelOnTop();
    	} else {
    		advancedPanelOnTop();
    	}
    	
    	updateTreeSelection();
    	
		return top;
	}
	
	/**
	 * Update the selection of the Domain Tree Viewer
	 */
	private void updateTreeSelection() {
		if (attributeName == null) {
			return;
		}
		Tree tree = simplifiedTreeViewer.getTree();
		tree.deselectAll();
		TreeItem root = tree.getItem(0);
		TreeItem selection = null;
		for (TreeItem item : root.getItems()) {
			MdfModelElement element = (MdfModelElement)item.getData();
			if (attributeName.equals(element.getQualifiedName())) {
				selection = item;
				break;
			}
		}
		if (selection != null) {
			tree.setSelection(new TreeItem[]{selection});
			tree.showSelection();
		}
    	this.selectedAttributeName = attributeName;
	}
	
	/**
	 * Update the error message
	 */
	private void updateErrorMessage() {
		if (!simplifiedRadio.getSelection() && 
				advancedText.getText().length() == 0) {
			setErrorMessage("Empty condition forbidden");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			setErrorMessage(null);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}

	/**
	 * Put the simplified panel on top
	 */
	private void simplifiedPanelOnTop() {
    	((StackLayout)stackPanel.getLayout()).topControl = simplifiedPanel;
    	stackPanel.layout();
//    	simplifiedTreeViewer.getTree().setFocus();
		Button btn = getButton(IDialogConstants.OK_ID);
		if (btn != null) {
			boolean hasSelection = simplifiedTreeViewer.getTree().getSelectionCount()>0;
			btn.setEnabled(hasSelection);
		}
	}
	
	/**
	 * Put the advanced panel on top
	 */
	private void advancedPanelOnTop() {
		((StackLayout)stackPanel.getLayout()).topControl =  advancedPanel;
    	stackPanel.layout();
    	advancedText.setFocus();
	}
	
	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
    	Label separator = new Label(parent,SWT.SEPARATOR | SWT.HORIZONTAL);
    	separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL)); 
		Control buttonBar = super.createButtonBar(parent);
		updateErrorMessage();
		return buttonBar;
		
	}
	
	
	
	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	@Override
	public boolean close() {
		if (codeFont != null) {
			codeFont.dispose();
		}
		return super.close();
	}

	/** 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		resultCondition = new EnabledIsBaseOnCondition(
				simplifiedRadio.getSelection(),
				selectedAttributeName,
				advancedText.getText());
		super.okPressed();
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		if (selection.getFirstElement() == null)
			return;
		
		MdfName name = ((MdfModelElement)selection.getFirstElement()).getQualifiedName();
		if (name.equals(entityName)) {
			selectedAttributeName = null;
		} else {
			selectedAttributeName = name;
		}
		getButton(IDialogConstants.OK_ID).setEnabled(selectedAttributeName != null);
	}		

	/**
	 * @return the resultCondition
	 */
	public EnabledIsBaseOnCondition getResultCondition() {
		return resultCondition;
	}

}
