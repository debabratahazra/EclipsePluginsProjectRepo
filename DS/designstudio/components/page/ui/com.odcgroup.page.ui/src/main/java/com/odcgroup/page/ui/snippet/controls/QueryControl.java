package com.odcgroup.page.ui.snippet.controls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.dialog.PageModelSelectionDialog;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * Control to edit/define query snippet for an event
 * 
 * @author pkk
 *
 */
public class QueryControl extends AbstractSnippetControl<Event>{
	
	/** */
	private Text attributesText;
	/** */
	private Text mappingText;
	/** */
	private Text moduleText;
	/** */
	private Text tabDisplayText;
	/** */
	private Text maxRowText;
	/** */
	private Event event;
	/** */
	private IQuery query;	
	/**  */
	private Button attrbBrowse = null;	
	/**  */
	private Button mappBrowse = null;
	/**  */
	private Button tabBrowse = null;
	/** */
	private Widget eventWidget = null;
	/** */
	private DataTypeCombo selectionCombo = null;
	/** */
	private DataTypeCombo tabCombo = null;
	/** */
	private Button runCheck = null;
	

	/**
	 * @param parent
	 * @param style 
	 * @param widget 
	 */
	public QueryControl(Composite parent, int style, Widget widget) {
		createContents(parent, style);
		this.eventWidget = widget;
	}
	
	/**
	 * @param parent
	 * @param style 
	 * @return composite
	 */
	protected Composite createContents(Composite parent, int style) {
		
		Composite body = new Composite(parent, SWT.FILL);
		body.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		
		if (!(parent.getLayout() instanceof FillLayout)) {
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.grabExcessHorizontalSpace = true;
			body.setLayoutData(gd);
		}
		
		createQueryControls(body);
		
		return body;
	}
	
	/**
	 * @param body
	 */
	private void createQueryControls(Composite body) {	
		
		Composite queryControlGroup = new Composite(body, SWT.FILL);
		queryControlGroup.setBackground(body.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		queryControlGroup.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		queryControlGroup.setLayoutData(gd);		

		// search criteria module control
		createCriteriaModuleControl(queryControlGroup);
		// attributes selection control
		createAttributesControl(queryControlGroup);
		// attributes mapping control
		createMappingsControl(queryControlGroup);
		// tabdisplay control
		//createTabDisplayControl(queryControlGroup);
		createLabel(queryControlGroup, "Tab to display");
		tabCombo = new DataTypeCombo(queryControlGroup, true);
		tabCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String value = tabCombo.getSelectedValue().getValue();
				if (!StringUtils.isEmpty(value)) {
					if (query.getTabDisplay() == null) {
						ISnippetFactory factory = SnippetUtil.getSnippetFactory();						
						IQueryTabDisplay tabDisplay = factory.createQueryTabDisplay(query);
						query.setTabDisplay(tabDisplay);
					}
					query.getTabDisplay().setSelection(value);
				}
			}
			
		});
		
		createLabel(queryControlGroup, "Selection Mode");
		selectionCombo = new DataTypeCombo(queryControlGroup, SnippetUtil.getSelectionModes(), true);
		selectionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String mode = query.getSelectionMode();
				String sel = selectionCombo.getSelectedValue().getValue();
				if (!StringUtils.isEmpty(mode) && !mode.equals(sel)) {
					query.setSelectionMode(sel);
					refresh();
				}
			}			
		});
		

		createLabel(queryControlGroup, "Run at Start");
		runCheck = new Button(queryControlGroup, SWT.CHECK);
		runCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				query.setRunAtStart(runCheck.getSelection());
			}
		});
		
		createLabel(queryControlGroup, "Max row count");
		maxRowText = new Text(queryControlGroup, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		maxRowText.setLayoutData(gd);
		maxRowText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				int count = query.getMaxRowCount();
				try {
					String val = ((Text) e.widget).getText();
					count = Integer.valueOf(val).intValue();
					query.setMaxRowCount(count);
				} catch (Exception ex) {
					maxRowText.setText(count+"");
				}
				
			}
		});
		
	}
	
	/**
	 * @param queryControlGroup
	 */
	@SuppressWarnings("unused")
	private void createTabDisplayControl(Composite queryControlGroup) {		
		createLabel(queryControlGroup, "Tabs Display");
		Composite textComp = new Composite(queryControlGroup, SWT.FILL);
		textComp.setBackground(queryControlGroup.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textComp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		textComp.setLayoutData(gd);
		
		tabDisplayText = new Text(textComp, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		tabDisplayText.setLayoutData(gd);
		
		tabDisplayText.setEditable(false);
		tabDisplayText.setBackground(ColorConstants.white);
		
		tabBrowse = new Button(textComp, SWT.NONE);
		GridData data = new GridData();
		tabBrowse.setLayoutData(data);
		tabBrowse.setText("&Browse...");
		tabBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Model model = query.getSearchCriteriaModuleModel();
				if (model == null) {
					return;
				}
				TabDisplayDialog dialog = new TabDisplayDialog(getShell(), model, query);
				if (dialog.open() == Window.OK) {
					query.setTabDisplay(dialog.getTabDisplay());
					refresh();
				}
			}			
		});
			
	}
	
	/**
	 * @param queryControlGroup
	 */
	private void createCriteriaModuleControl(Composite queryControlGroup) {
		
		createLabel(queryControlGroup, "Search Criteria Module");
		Composite textComp = new Composite(queryControlGroup, SWT.FILL);
		textComp.setBackground(queryControlGroup.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textComp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		textComp.setLayoutData(gd);
		
		moduleText = new Text(textComp, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		moduleText.setLayoutData(gd);
		
		moduleText.setEditable(false);
		moduleText.setBackground(ColorConstants.white);
		
		Button browse = new Button(textComp, SWT.NONE);
		GridData data = new GridData();
		browse.setLayoutData(data);
		browse.setText("&Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Set<String> extns = new HashSet<String>();
				extns.add("module");
				int filter = PageModelSelectionDialog.SEARCH_OUTPUT_FILTER;
				PageModelSelectionDialog dialog = PageModelSelectionDialog.createDialog(extns, filter);
				if (dialog.open() == Window.OK) {
					IOfsModelResource resource = (IOfsModelResource)dialog.getFirstResult();
					if (resource == null) {
						return;
					}					
					String moduleuri = query.getSearchCriteriaModule();
					String uri = resource.getURI().toString();
					if(!StringUtils.isEmpty(moduleuri) && uri.equals(moduleuri)) {
						return;
					} else {
						Model model = null;
						try {
							List<EObject> list = resource.getEMFModel();
							model = (Model) list.get(0);
						} catch (Exception ex) {
							return;
						}
						query.setSearchCriteriaModule(model);
						query.removeTabDisplay();
						query.removeAttributes();
						query.removeFilterSets();
					}
					refresh();
				}
			}			
		});
			
	}
	
	
	/**
	 * @param queryControlGroup
	 */
	private void createMappingsControl(Composite queryControlGroup) {
		
		createLabel(queryControlGroup, "Attributes mapping");
		Composite textComp = new Composite(queryControlGroup, SWT.FILL);
		textComp.setBackground(queryControlGroup.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textComp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		textComp.setLayoutData(gd);
		
		mappingText = new Text(textComp, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		mappingText.setLayoutData(gd);
		
		mappingText.setEditable(false);
		mappingText.setBackground(ColorConstants.white);
		
		mappBrowse = new Button(textComp, SWT.NONE);
		GridData data = new GridData();
		mappBrowse.setLayoutData(data);
		mappBrowse.setText("&Browse...");
		mappBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String entity = getRootWidgetDomainEntity();
				if (StringUtils.isEmpty(entity)) {
					return;
				}
				String[] attributes = getDatasetAttributes(entity);
				String title = "Attribute Mapping";
				String desc = "Select attribute(s) to map between field(s) in form and search output columns";
				QueryAttributesSelectionDialog dialog = new QueryAttributesSelectionDialog(getShell(), attributes, query.getMappings(), title, desc);
				if (dialog.open() == Window.OK) {
					String[] selectedAttributes = dialog.getSelectedAttributes();
					query.setMappings(selectedAttributes);
					refresh();
				}
			}			
		});
	
		
	
		
	}
	
	/**
	 * @param queryControlGroup
	 */
	private void createAttributesControl(Composite queryControlGroup) {
		
		createLabel(queryControlGroup, "Attributes selection");
		Composite textComp = new Composite(queryControlGroup, SWT.FILL);
		textComp.setBackground(queryControlGroup.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textComp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		textComp.setLayoutData(gd);
		
		attributesText = new Text(textComp, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		attributesText.setLayoutData(gd);
		
		attributesText.setEditable(false);
		attributesText.setBackground(ColorConstants.white);
		
		attrbBrowse = new Button(textComp, SWT.NONE);
		GridData data = new GridData();
		attrbBrowse.setLayoutData(data);
		attrbBrowse.setText("&Browse...");
		attrbBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String entity = null;
				if (query != null)
					entity = query.getSearchModuleDomainEntity();
				if (StringUtils.isEmpty(entity)) {
					return;
				}
				String[] attributes = getDatasetAttributes(entity);
				String title = "Attribute Selection";
				String desc = "Select attributes that can participate in the search";
				QueryAttributesSelectionDialog dialog = new QueryAttributesSelectionDialog(getShell(), attributes, query.getAttributes(), title, desc);
				if (dialog.open() == Window.OK) {
					String[] selectedAttributes = dialog.getSelectedAttributes();
					query.setAttributes(selectedAttributes);
					refresh();
				}
			}			
		});		
	}	
	
	
	/**
	 * 
	 */
	public void refresh() {
		initQueryControls();
	}
	
	
	/**
	 * @param composite
	 * @param text
	 */
	private void createLabel(Composite composite, String text) {
		CLabel label = new CLabel(composite, SWT.NONE);
		label.setBackground(composite.getBackground());
		label.setText(text);
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.AbstractSnippetControl#getContainer()
	 */
	public Event getContainer() {
		return event;
	}

	/**
	 * @see com.odcgroup.page.ui.snippet.controls.AbstractSnippetControl#setInput(java.lang.Object)
	 */
	public void setInput(Event container) {
		this.event = container;
		if (event != null) {
			ISnippetFactory factory = SnippetUtil.getSnippetFactory();
			query = factory.adaptQuerySnippet(event.getSnippets());
			if (query == null) {
				query = factory.createQuery();
				event.getSnippets().add(query.getSnippet());
			}
			initQueryControls();
		}
	}
	
	/**
	 * @return domainEntity
	 */
	private String getRootWidgetDomainEntity() {
		if (eventWidget != null && eventWidget.getRootWidget() != null) {
			return eventWidget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		}
		return null;
	}
	
	/**
	 * @param module
	 * @return string[]
	 */
	protected Map<String, String> fetchTabs(Model module) {
		Map<String, String> tabs = new HashMap<String, String>();
		if (module == null) {
			return tabs;
		}
		if (module.getWidget() == null) {
			// invalid module reference
			URI uri = EcoreUtil.getURI(module);
			PageUIPlugin.getDefault().logError("Cannot load "+uri.trimFragment(), null); // //$NON-NLS-1$
			return tabs;
		}
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
	 * @param module 
	 * @return dataType
	 */
	private DataType getTabContents(Model module) {
		Map<String, String> availableTabs = fetchTabs(module);
		DataType dataType = MetaModelFactory.eINSTANCE.createDataType();
		DataValue dValue = null;
		int ii = 0;
		for(String tabName :availableTabs.keySet()) {
			dValue = MetaModelFactory.eINSTANCE.createDataValue();
			dValue.setDisplayName(tabName);
			dValue.setValue(getTabID(availableTabs, tabName));
			dValue.setOrdinal(ii);
			dataType.getValues().add(dValue);
			ii++;
		}
		return dataType;
	}
	
	/**
	 * @param availableTabs 
	 * @param tabName
	 * @return tabId
	 */
	private String getTabID(Map<String, String> availableTabs, String tabName) {
		if (availableTabs.containsKey(tabName)) {
			return availableTabs.get(tabName);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 */
	private void initQueryControls() {
		
		if (StringUtils.isEmpty(getRootWidgetDomainEntity())) {
			mappBrowse.setEnabled(false);
		} else {
			mappBrowse.setEnabled(true);
		}
		if  (query != null) {
			String dataset = query.getSearchModuleDomainEntity();
			if (!StringUtils.isEmpty(dataset)) {
				attrbBrowse.setEnabled(true);
			} else {
				attrbBrowse.setEnabled(false);
			}
			String[] attributes = query.getAttributes();
			if (attributes != null) {
				attributesText.setText(query.getAttributesAsString());
			} else {
				attributesText.setText("");
			}
			String[] mappings = query.getMappings();
			if (mappings != null) {
				mappingText.setText(query.getMappingsAsString());
			}
			
			String searchMod = query.getSearchCriteriaModule();
			if (!StringUtils.isEmpty(searchMod)) {
				moduleText.setText(searchMod);
				tabCombo.getCombo().setEnabled(true);
				tabCombo.setType(getTabContents(query.getSearchCriteriaModuleModel()));
				tabCombo.getCombo().getParent().layout(true);
			} else {
				tabCombo.getCombo().setEnabled(false);
			}
			
			if (query.getTabDisplay() != null) {
				String tabdisplay = query.getTabDisplay().getSelection();
				tabCombo.select(tabdisplay);
			} 
			
			if (query.getSelectionMode() != null) {
				selectionCombo.select(query.getSelectionMode());
			}
			
			if (query.runAtStart()) {
				runCheck.setSelection(true);
			} else {
				runCheck.setSelection(false);
			}		

			maxRowText.setText(query.getMaxRowCount()+"");
		}
	}

}
