package com.odcgroup.page.ui.properties.table.dialog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * @author ramapriyamn
 *
 */
public class AppliesOnGroupSelectionDialog extends TitleAreaDialog {
	/** */
	private CheckboxTreeViewer attributesList = null;	
	/** */
	private String[] validAttributes = null;
	/** */
	private Set<String> selectedAttributes = new HashSet<String>();
	/** */
	private String[] previousSelection;
	/** */
	private String title;
	/** */
	private String description;
	
	/**
	 * @param parentShell
	 * @param attributes 
	 * @param previousSelection 
	 * @param title 
	 * @param desc 
	 */
	public AppliesOnGroupSelectionDialog(Shell parentShell, String[] attributes, String[] previousSelection, String title, String desc) {
		super(parentShell);
		this.validAttributes = attributes;
		this.title = title;
		this.description = desc;
		this.previousSelection = previousSelection;
	}
	
	/** 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(title);
		this.setMessage(description);
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
		//Label title = new Label(composite, SWT.NONE);
		//title.setText(description);
		
		Composite listComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.makeColumnsEqualWidth = false;
		listComposite.setLayout(layout);
		listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		attributesList = new CheckboxTreeViewer(listComposite, SWT.BORDER);
		GridData listData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		attributesList.getControl().setLayoutData(listData);
		
		configureCheckboxTreeViewer();
		createSelectionButtons(listComposite);
		attributesList.setInput(validAttributes);
		
		updateAttributesList();
		return composite;
	}
	
	/**
	 * @param listComposite
	 */
	private void createSelectionButtons(Composite listComposite) {
		Composite buttonsComposite = new Composite(listComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);

		buttonsComposite.setLayoutData(new GridData(
				GridData.VERTICAL_ALIGN_BEGINNING));

		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText("Select All");
		selectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				attributesList.setCheckedElements(validAttributes);
				for (String attribute : validAttributes) {
					selectedAttributes.add(attribute);
				}
			}
		});
		Dialog.applyDialogFont(selectAll);
		setButtonLayoutData(selectAll);

		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText("Deselect All");
		deselectAll.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				attributesList.setCheckedElements(new Object[0]);
				selectedAttributes.clear();
			}
		});
		Dialog.applyDialogFont(deselectAll);
		setButtonLayoutData(deselectAll);

		Button refresh = new Button(buttonsComposite, SWT.PUSH);
		refresh.setText("Reset");
		refresh.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent e) {
				updateAttributesList();
			}
		});
		Dialog.applyDialogFont(refresh);
		setButtonLayoutData(refresh);
		updateAttributesList();		
	}
	
	/**
	 * 
	 */
	private void configureCheckboxTreeViewer() {
		
		attributesList.setContentProvider(new ITreeContentProvider() {
			
			public Object[] getChildren(Object parentElement) {
				return null;
			}

			public Object getParent(Object element) {
				return null;
			}

			public boolean hasChildren(Object element) {
				return false;
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof String[]) {
					return (String[])inputElement;
				}
				return null;
			}

			public void dispose() {				
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {				
			}
			
		});
		
		attributesList.setLabelProvider(new LabelProvider() {			
			public String getText(Object element) {
				return (String) element;
			}
		});

		attributesList.addCheckStateListener(new ICheckStateListener() {			
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					selectedAttributes.add((String)event.getElement());
				} else {
					selectedAttributes.remove((String)event.getElement());
				}
			}
		});
	}
	
	/**
	 * 
	 */
	protected void updateAttributesList() {
		if (previousSelection == null) {
			return;
		}
		for (String attribute : previousSelection) {
			if (isValidAttribute(attribute.trim())) {
				selectedAttributes.add(attribute.trim());
			} 
		}
		attributesList.setCheckedElements(selectedAttributes.toArray(new String[0]));
	}
	
	/**
	 * @param attribute
	 * @return boolean
	 */
	private boolean isValidAttribute(String attribute) {
		for (String string : validAttributes) {
			if (attribute.equals(string)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @return string[]
	 */
	public String[] getSelectedAttributes() {
		String[] tmp = selectedAttributes.toArray(new String[0]);
		List<String> selectList = Arrays.asList(tmp);
		List<String> inputList = Arrays.asList(validAttributes);
		String[] target = new String[tmp.length];
		int j=0;
		if (tmp.length>0) {
			for (String selec: inputList) {
				if (selectList.contains(selec)) {
					target[j] = selec;
					j++;
				}
			}
		}
		return target;
	}
	
	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}

}
