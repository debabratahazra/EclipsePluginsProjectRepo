package com.odcgroup.page.pageflow.integration.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.odcgroup.page.metamodel.EventTypeConstants;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.pageflow.integration.ui.PagePageflowIntegrationUICore;
import com.odcgroup.page.pageflow.integration.ui.dialog.controls.AdvancedUserParamControl;
import com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventModelChangeListener;
import com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventUtil;
import com.odcgroup.page.pageflow.integration.ui.dialog.controls.GenericEventControl;
import com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl;
import com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog;
import com.odcgroup.page.ui.snippet.controls.EventFilterSetControl;
import com.odcgroup.page.ui.snippet.controls.QueryControl;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Dialog to manage an Event for the specified widget
 *
 * @author pkk
 *
 */
public class EventDefinitionDialog extends TitleAreaDialog implements IPageEventDefinitionDialog, EventModelChangeListener {

	/** Widget to which the event to be defined*/
	private Widget widget;
	/** Event instance*/
	private Event event;
	/** title of the event dialog*/
	private String title;
	/** message describing the event dialog*/
	private String message;
	/** description of the event */
	private Text eventDescription;
    /** general tab event control*/
    private IEventControl eventControl;
	/** user params tab control*/
	private AdvancedUserParamControl userParamControl = null;
	/**  mapping of all filterset controls*/
	private Map<String, EventFilterSetControl> filterSetControls = new HashMap<String, EventFilterSetControl>();
    /** tab folder for the event tabs */
    private CTabFolder tabFolder;

    private static final String TRANSLATION_TAB = "Translations";
    private static final String FILTERSET_TAB = "Filter Set";

	private static Image ADD_FS = createImage("/icons/add.png"); //$NON-NLS-1$
	/**  */
	private QueryControl queryControl = null;


	/**
	 * @param parentShell
	 * @param widget
	 * @param event
	 * @param title
	 * @param message
	 */
	public EventDefinitionDialog(Shell parentShell, Widget widget, Event event, String title, String message) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.FILL);
		this.widget = widget;
		if (event == null) {
            this.event = EventUtil.createSimpleEvent();
        } else {
            // We need to copy the original event in case the user cancels his changes
            this.event = WidgetCopier.copy(event);
        }
		this.title = title;
		this.message = message;
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
	 * The user has just close the FilterSet tab. Removes the corresponding
	 * FilterSet from the current event.
	 *
	 * @param index of the filterSet to be removed.
	 */
	private void handleCloseFilterSetTab(int index) {
		List<IFilterSet> filterSets =  SnippetUtil.getFilterSets(event);
		if (!filterSets.isEmpty()) {
			IFilterSet fs = filterSets.get(index);
			event.getSnippets().remove(fs.getSnippet());
		}
	}

	private synchronized void handleAddFilterSetTab() {
    	int index = filterSetControls.size();
		CTabItem fsItem = new CTabItem(tabFolder, SWT.CLOSE);
		String tabID = FILTERSET_TAB+"("+index+")";
		fsItem.setText(/*tabID*/FILTERSET_TAB);
		fsItem.setData(tabID);
		fsItem.setControl(createFilterGroup(tabFolder, tabID, false, index));
		tabFolder.setSelection(fsItem);
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
		gridData.heightHint = 450;
		gridData.widthHint=550;
		top.setLayoutData(gridData);

		// search check
		createSearchBar(top);
		// create tabbed-pane
		createTabbedPaneSection(top);
		// Event Description
		createDescription(top);

		Composite body = new Composite(top, SWT.FILL);
		gridLayout = new GridLayout(1, false);
		body.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(gridData);

		// Build the separator line
		Label titleBarSeparator = new Label(top, SWT.HORIZONTAL
				| SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return top;
	}

	/**
	 * @param comp
	 */
	private void createTabbedPaneSection(Composite comp) {

		tabFolder = new CTabFolder(comp, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);

		// general tab
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("General");
		tabItem.setControl(createGeneralTab(tabFolder));

		// parameters tab
		final CTabItem paramItem = new CTabItem(tabFolder, SWT.NONE);
		paramItem.setText("Parameters");
		paramItem.setControl(createParameterTab(tabFolder));


		// translations tab for advanced event
		CTabItem transItem = new CTabItem(tabFolder, SWT.NONE);
		transItem.setText(TRANSLATION_TAB);
		transItem.setControl( createEventTranslationControl(tabFolder));

		// search tab
		CTabItem searchItem = new CTabItem(tabFolder, SWT.NONE);
		searchItem.setText("Search");
		searchItem.setControl(createSearchGroup(tabFolder));

		// filterset
		List<IFilterSet> filterSets =  SnippetUtil.getFilterSets(event);
		for (IFilterSet filterset : filterSets) {
			int index = filterSets.indexOf(filterset);
			CTabItem fsItem = new CTabItem(tabFolder, SWT.CLOSE);
			String tabID = FILTERSET_TAB+"("+index+")";
			fsItem.setText(/*tabID*/FILTERSET_TAB);
			fsItem.setData(tabID);
			fsItem.setControl(createFilterGroup(tabFolder, tabID, false, index));
		}

		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean advanced = event.isAdvancedEvent();
				if (tabFolder.getSelection().equals(paramItem) && advanced) {
					FunctionType fType = getFunctionTypeByName(event.getFunctionName());
					if (fType != null && fType.isAllowUserParameters()) {
						userParamControl.setEnabled(true);
					} else {
						userParamControl.setEnabled(false);
					}
				} else if (TRANSLATION_TAB.equals(tabFolder.getSelection().getText()) ) {
// DS-3322 - Page Designer - Refactoring - begin
//					if (event.isAdvancedEvent()) {
//		                event.eAdapters().remove(localizable);
//		                localizable = (Localizable) EventLocalizableAdapterFactory.INSTANCE.adapt(event, Localizable.class);
//		                editor.setLocalizable(localizable);
//		                editor.setReadOnly(isReadOnly(widget));
//					} else {
//						editor.setReadOnly(true);
//					}
// DS-3322 - Page Designer - Refactoring - end
				} else if (tabFolder.getSelection().getText().startsWith(FILTERSET_TAB)) {
					String tabID = (String)tabFolder.getSelection().getData();
					filterSetControls.get(tabID).updateDatasetControls();
				}
			}

		});

		// DS-3015
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				CTabItem tabItem = (CTabItem)event.item;
				CTabFolder folder = (CTabFolder)event.getSource();
				int fsIndex = -1;
				for (CTabItem tab : folder.getItems()) {
					if (tab.getText().startsWith(FILTERSET_TAB)) {
						fsIndex++;
						if (tab == tabItem) {
							handleCloseFilterSetTab(fsIndex);
						}
					}
				}
			}
		});

		tabFolder.setSelection(tabFolder.getItem(0));
	}

	/**
     * @param functionName
     * @return FunctionType
     */
    private FunctionType getFunctionTypeByName(String functionName) {
    	 MetaModelRegistry.getEventsFor(widget.getType());
         Set<FunctionType> functions = MetaModelRegistry.getFunctionsFor(event.getEventType());
         for (FunctionType ft : functions) {
        	 if (ft.getName().equals(functionName)) {
        		 return ft;
        	 }
         }
         return null;
    }


	/**
	 * event parameter tab
	 *
	 * @param body
	 * @return
	 */
	private Composite createParameterTab(Composite body) {
		Composite paramGroup = new Composite(body, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData);
        userParamControl = new AdvancedUserParamControl(paramGroup, SWT.FILL, true);
        userParamControl.setInput(event, null);
        userParamControl.setSimplifiedEvent(event.isSimplifiedEvent());
        return paramGroup;
	}

	/**
	 * create the translation editor for editing event translations
	 *
	 * @param body
	 */
	private Composite createEventTranslationControl(Composite body) {
		Composite def = new Composite(body, SWT.FILL | SWT.BORDER);
		GridLayout gridLayout = new GridLayout(1, false);
		def.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        def.setLayoutData(gridData);

		IOfsProject ofsProject = EclipseUtils.findCurrentProject();
		if(OfsCore.isOfsProject(ofsProject.getProject())) {
			ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
			ITranslation translation = manager.getTranslation(event);
			if (translation != null) {
				ITranslationPreferences prefs = manager.getPreferences();
				ITranslationModel model = new TranslationModel(prefs, translation);
				ITranslationViewer viewer = TranslationUICore.getTranslationViewer(ofsProject.getProject(), def);
				viewer.hideKindSelector();
				// viewer.setRowCount(2);
				viewer.setTranslationModel(model);
			}
		}

        return def;
	}

	/**
	 * @param eObj
	 * @return boolean
	 */
	protected boolean isReadOnly(EObject eObj) {
		boolean isReadOnly = false;
		Resource resource = eObj.eResource();
		if (resource != null) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			if (ofsProject != null) {
				try {
					URI uri = eObj.eResource().getURI();
					if (uri.isPlatform()) {
						String[] segments = resource.getURI().segments();
						StringBuffer path = new StringBuffer();
						for (int kx = 3; kx < segments.length; kx++) {
							path.append("/" + segments[kx]);
						}
						uri = ModelURIConverter.createModelURI(path.toString());
					}
					IOfsModelResource ofsResource = ofsProject.getOfsModelResource(uri);
					isReadOnly = ofsResource.isReadOnly();
				} catch (ModelNotFoundException ex) {
					;;// ignore
				}
			}
		}
		return isReadOnly;
	}


	/**
	 * @param body
	 */
	protected void createDescription(Composite body) {
		Composite descrGroup = new Composite(body, SWT.BORDER | SWT.FILL);
        GridLayout gridLayout = new GridLayout(1, true);
        descrGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		descrGroup.setLayoutData(gridData);
		new Label(descrGroup, SWT.NONE).setText(" Event description ");
		eventDescription = new Text(descrGroup, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 60;
		eventDescription.setLayoutData(gd);

        String description = event.getDescription();
        eventDescription.setText(description == null ? "" : description);
	}

	/**
	 * creates a checkbox for search events and an add filterset button
	 *
	 * @param body
	 */
	protected void createSearchBar(Composite body) {

		Composite composite = new Composite(body, SWT.NONE);
    	GridLayout layout = new GridLayout(2, false);
    	layout.horizontalSpacing = 0;
    	layout.verticalSpacing = 0;
    	layout.marginHeight = 0;
    	layout.marginLeft = 0;
    	composite.setLayout(layout);
    	composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    	composite.setFont(body.getFont());

    	new Label(composite, SWT.NONE);

		Composite comp = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		comp.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		comp.setLayoutData(data);
		comp.setFont(composite.getFont());

		createAddFilterSetButton(comp);

        data.grabExcessHorizontalSpace = true;

	}

	/**
	 * @param body
	 * @return
	 */
	private Composite createSearchGroup(Composite body) {
		Composite paramGroup = new Composite(body, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData);
        queryControl = new QueryControl(paramGroup, SWT.FILL, widget) {
			public void refresh() {
				super.refresh();
				for (EventFilterSetControl control : filterSetControls.values()) {
					control.setInput(event);
				}
			}

        };
        queryControl.setInput(event);
        return paramGroup;
	}

	/**
	 * @param body
	 * @param name
	 * @param required
	 * @param index
	 * @return
	 */
	private Composite createFilterGroup(Composite body, String name, boolean required, int index) {
		Composite paramGroup = new Composite(body, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData);
        EventFilterSetControl filterSetControl = new EventFilterSetControl(paramGroup, SWT.FILL, required, index);
        filterSetControl.setInput(event);
        filterSetControls.put(name, filterSetControl);
        return paramGroup;
	}

	/**
	 * @param body
	 * @return
	 */
	private Composite createGeneralTab(Composite body) {
		Composite paramGroup = new Composite(body, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		paramGroup.setLayoutData(gridData);

		eventControl = new GenericEventControl(paramGroup, widget, event);
		eventControl.addEventModelChangeListener(this);
		return paramGroup;
	}

	/**
	 * @see com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog#getDefinedEvent()
	 */
	public Event getDefinedEvent() {
        return event;
	}

	/**
	 * @return
	 */
	private boolean isValid() {
		if (eventControl != null && !eventControl.isValid()) {
			setErrorMessage(eventControl.getErrorMessage());
			return false;
		}

		for (EventFilterSetControl control : filterSetControls.values()) {
			IFilterSet fs = control.getFilterSet();
			if (fs != null && fs.isCancelled()) {
				if (fs.getLevel() == -1) {
					int index = control.getIndex()+1;
					setErrorMessage("Level value (integer) is required for FilterSet ("+index+");for instance between 1 and 10.Details available in documentation section:Page Designer>Reference>Event Properties.");
					return false;
				}
			}
		}
		setErrorMessage(null);
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (isValid()) {
			event.setDescription(eventDescription.getText());
			cleanupUnrelatedParameters();
			cleanupEventSnippets();
			super.okPressed();
		}
	}
	/**
	 * Remove any parameters that are not relevant to the current function type selected
	 * in the event
	 */
	private void cleanupUnrelatedParameters() {
		FunctionType ft = event.getFunctionType();

		List<Parameter> cleanEventParameters = new ArrayList<Parameter>();
		List<Parameter> eventParameters = event.getParameters();
		cleanEventParameters.addAll(eventParameters);

		for (Parameter parameter : eventParameters) {
			String name = parameter.getName();
			if (!parameter.isUserDefined()) {
				ParameterType pt = ft.findParameterType(name);
				if (pt==null) {
					cleanEventParameters.remove(parameter);
				}
			}
		}

		this.event.getParameters().clear();
		this.event.getParameters().addAll(cleanEventParameters);
		
		//DS-5619
		if (event.isAdvancedEvent() && EventTypeConstants.ON_CLICK_EVENT.equals(event.getEventName())) {
			boolean isSubmitFct = FunctionTypeConstants.SUBMIT_FUNCTION.equals(event.getFunctionName());
			if (isSubmitFct) {
				Parameter flowAction = event.findParameter(ParameterTypeConstants.FLOW_ACTION_PARAMETER);
				if (flowAction != null) {
					event.getParameters().remove(flowAction);
				}
			}
		}
		
	}

	/**
	 *
	 */
	private void cleanupEventSnippets() {
		// check if query
		IQuery query = SnippetUtil.getQuery(event);
		boolean searchbased = true;
		if (query != null && StringUtils.isEmpty(query.getSearchCriteriaModule())
				&& query.getAttributes() == null
				&& query.getMappings() == null
				) {
			event.getSnippets().remove(query.getSnippet());
			searchbased = false;
		}
		List<IFilterSet> filterSets = SnippetUtil.getFilterSets(event);
		query = SnippetUtil.getQuery(event);
		for (IFilterSet fs : filterSets) {
			if (fs.getFilters().isEmpty()
					&& fs.getLevel()  == -1
					&& !fs.isCancelled()
					) {
				if (searchbased
						|| ((!searchbased
								&& StringUtils.isEmpty(fs.getTargetDatasetName())))) {
					event.getSnippets().remove(fs.getSnippet());

				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	public boolean close() {
    	return super.close();
    }

	/*
     * Creates a button with a help image. This is only used if there
     * is an image available.
     */
	private ToolBar createAddFilterSetButton(Composite parent) {
        ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS| SWT.RIGHT);
        ((GridLayout) parent.getLayout()).numColumns++;
		toolBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		final Cursor cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);
		toolBar.setCursor(cursor);
		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				cursor.dispose();
			}
		});
        ToolItem item = new ToolItem(toolBar, SWT.NONE );
		item.setImage(ADD_FS);
		item.setText("Add Filter Set"); //$NON-NLS-1$
		item.setToolTipText("Add FilterSet to the Event"); //$NON-NLS-1$
		item.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
            	handleAddFilterSetTab();
            }
        });
		return toolBar;
	}

	/**
	 * @param name
	 * @return
	 */
	private static Image createImage(String name) {
		String id = PagePageflowIntegrationUICore.PLUGIN_ID;
		return PagePageflowIntegrationUICore.imageDescriptorFromPlugin(id, name).createImage();
	}


	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventModelChangeListener#eventModelChanged(com.odcgroup.page.model.Event)
	 */
	public void eventModelChanged(Event changeEvent) {
		this.event = changeEvent;
		userParamControl.setSimplifiedEvent(event.isSimplifiedEvent());
		userParamControl.setInput(event, null);
	}

}
