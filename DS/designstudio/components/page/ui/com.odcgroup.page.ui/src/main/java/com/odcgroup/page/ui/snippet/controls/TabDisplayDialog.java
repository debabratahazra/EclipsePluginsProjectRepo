package com.odcgroup.page.ui.snippet.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;

/**
 *
 * @author pkk
 *
 */
public class TabDisplayDialog extends TitleAreaDialog {
	/** */
	private CheckboxTreeViewer attributesList = null;	
	/** */
	private Set<String> selectedAttributes = new HashSet<String>();
	/** */
	private Map<String, String> availableTabs;
	/** */
	private IQueryTabDisplay tabDisplay;
	/** */
	private DataTypeCombo tabCombo = null;
	
	/**
	 * @param parentShell
	 * @param module 
	 * @param query 
	 */
	public TabDisplayDialog(Shell parentShell, Model module, IQuery query) {
		super(parentShell);
		availableTabs = fetchTabs(module);
		tabDisplay = query.getTabDisplay();
		if (tabDisplay == null) {
			tabDisplay = SnippetUtil.getSnippetFactory().createQueryTabDisplay(query);
		}
	}
	
	/** 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle("Tab Display");
		this.setMessage("Configure tab display in the search page");
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Tab Display");
	}

	/**
	 * @param module
	 * @return string[]
	 */
	protected Map<String, String> fetchTabs(Model module) {
		if (module == null) {
			return null;
		}
		Map<String, String> tabs = new HashMap<String, String>();
		Widget boxWidget = module.getWidget().getContents().get(0);
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetType type = metamodel.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABBED_PANE);
		for (Widget tabbedPane : boxWidget.getWidgets(type, false)) {
			for (Widget widget : tabbedPane.getContents()) {
				if (widget.getTypeName().equals(WidgetTypeConstants.TAB)) {
					String value = widget.getPropertyValue(PropertyTypeConstants.TAB_NAME);
					String id =  widget.getID();
					if (StringUtils.isEmpty(value)) {
						value = id;
					}
					tabs.put(value, id);
				}
			}
		}
		return tabs;
	}
	
	/**
	 * @return dataType
	 */
	private DataType getTabContents() {
		DataType dataType = MetaModelFactory.eINSTANCE.createDataType();
		DataValue dValue = null;
		int ii = 0;
		for(String tabName :availableTabs.keySet()) {
			dValue = MetaModelFactory.eINSTANCE.createDataValue();
			dValue.setDisplayName(tabName);
			dValue.setValue(getTabID(tabName));
			dValue.setOrdinal(ii);
			dataType.getValues().add(dValue);
			ii++;
		}
		return dataType;
	}
	
	/**
	 * @param tabName
	 * @return tabId
	 */
	private String getTabID(String tabName) {
		if (availableTabs.containsKey(tabName)) {
			return availableTabs.get(tabName);
		}
		return null;
	}
	
	/**
	 * @return string[]
	 */
	private String[] getTabNames() {
		return availableTabs.keySet().toArray(new String[0]);
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
		
		Composite comp = new Composite(composite, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
		Label label = new Label(comp, SWT.NONE);
		label.setText("Choose the tab to display");
		
		tabCombo = new DataTypeCombo(comp, getTabContents(), true);
		tabCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String value = tabCombo.getSelectedValue().getValue();
				if (!StringUtils.isEmpty(value)) {
					tabDisplay.setSelection(value);
				}
			}
			
		});
		if (tabDisplay.getSelection() != null) {
			tabCombo.select(tabDisplay.getSelection());
		}
		
		Label title = new Label(composite, SWT.NONE);
		title.setText("Check the tab(s) that you want to be available in search");
		
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
		attributesList.setInput(getTabNames());
		
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
				attributesList.setCheckedElements(getTabNames());
				for (String attribute : getTabNames()) {
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
		
		attributesList.setComparator(new ViewerComparator());
	}	
	
	/**
	 * 
	 */
	protected void updateAttributesList() {
		if (tabDisplay.getEnabledTabs() == null) {
			return;
		}
		for (String attribute : tabDisplay.getEnabledTabs()) {
			selectedAttributes.add(attribute);
		}
		attributesList.setCheckedElements(tabDisplay.getEnabledTabs());
	}
	
	/**
	 * @return string[]
	 */
	protected IQueryTabDisplay getTabDisplay() {
		return tabDisplay;
	}
	
	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		List<String> tabIds = new ArrayList<String>();
		for (String string : selectedAttributes) {
			tabIds.add(getTabID(string));
		}
		if (!tabIds.isEmpty()) {
			tabDisplay.setEnabledTabs(null);
			tabDisplay.setEnabledTabs(tabIds.toArray(new String[0]));
		}
		super.okPressed();
	}

}
