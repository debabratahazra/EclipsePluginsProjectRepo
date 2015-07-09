package com.odcgroup.page.ui.properties.table.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.EventListControl;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * 
 * @author pkk
 * 
 */
public class TableGroupDefinitionDialog extends TableTransformDialog {

	/** table */
	private ITable table;	
	/** table group for edit*/
	private ITableGroup groupForEdit;
	/** */
	private ITypeCombo<String> columnCombo;
	/** */	
	private ITypeCombo<String> sortCombo;	
	/** */
	private ITypeCombo<DataValue> directionCombo;
	/** */
	private ITypeCombo<DataValue> eventLevelCombo;
	/** */
	private EventListControl eventControl = null;
	/** */
	private Text maxGrouping;
	/** */	
	private Button toolTipChk;
	/** */	
	private Button collapseChk;
	/** */	
	private Button usedForDynamicColumnChk;
	/** */	
	private Button hierarchyChk;
	/** */	
	private Label toolTipLabel;
    /**  */
    private Group tooltipgroup;
    /**  */
    private Button aggDataRadio;
    /**  */
    private Button rawDataRadio;

	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 */
	public TableGroupDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table) {
		super(parentShell, commandStack);
		this.table = table;
	}
	
	/**
	 * @param parentShell
	 * @param commandStack
	 * @param table
	 * @param groupForEdit
	 */
	public TableGroupDefinitionDialog(Shell parentShell, CommandStack commandStack, ITable table, ITableGroup groupForEdit) {
		this(parentShell, commandStack, table);
		this.groupForEdit = groupForEdit;
		if (groupForEdit != null) {
			setEditMode(true);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Group");
		setMessage("Table Group definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Group");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		composite.setLayout(gridLayout);

		Composite body = new Group(composite, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(1, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
		Composite maingroup = new Composite(body, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(4, false);
		gridLayout.horizontalSpacing = 10;
		maingroup.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		maingroup.setLayoutData(gd);

		new Label(maingroup, SWT.NONE).setText("Grouping Column");
		List<String> domainAttributes = TableDomainObjectUtil.getDomainAttributeNames(table);
		//List<String> compColumns = TableHelper.getComputedColumnNames(table);		
		columnCombo = new StringValueCombo(maingroup, domainAttributes, getGroupedColumns(table), isEditMode(), true);
		
		new Label(maingroup, SWT.NONE).setText("Max. grouping");
		maxGrouping = new Text(maingroup, SWT.BORDER);
		GridData gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint = 15;
		maxGrouping.setTextLimit(3);
		maxGrouping.setLayoutData(gridData);		
		
		Set<String> sortColumns = new TreeSet<String>();
		sortColumns.add("");
		sortColumns.addAll(domainAttributes);
		//sortColumns.addAll(compColumns);
		new Label(maingroup, SWT.NONE).setText("Sorting Column");
		sortCombo = new StringValueCombo(maingroup, sortColumns, getSortedColumns(table), isEditMode(), true);
		
		new Label(maingroup, SWT.NONE).setText("Collapse");
		collapseChk = new Button(maingroup, SWT.CHECK);		

		new Label(maingroup, SWT.NONE).setText("Sorting Direction");
		directionCombo = new DataTypeCombo(maingroup, TableHelper.getTableUtilities().getSortingDirections(), isEditMode());
		directionCombo.setEnabled(false);
		
		new Label(maingroup, SWT.NONE).setText("Used for Dynamic Column");
		usedForDynamicColumnChk = new Button(maingroup, SWT.CHECK);		

		Group hierarchygroup = new Group(body, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(3, false);
		hierarchygroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		hierarchygroup.setLayoutData(gd);
		
		hierarchyChk = new Button(hierarchygroup, SWT.CHECK);
		hierarchyChk.setText("  Hierarchy        ");
		//new Label(hierarchygroup, SWT.NONE).setText("Hierarchy");
		if (TableHelper.getTableUtilities().isSummaryRenderingMode(table.getRenderingMode())) {
			hierarchyChk.setEnabled(true);
		} else if (TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode())) {
			hierarchyChk.setEnabled(true);
		} else {
			hierarchyChk.setEnabled(false);
		}
		
		aggDataRadio = new Button(hierarchygroup, SWT.RADIO);
		aggDataRadio.setText("with aggregate data");		
		aggDataRadio.setEnabled(false);

		new Label(hierarchygroup, SWT.None);
		new Label(hierarchygroup, SWT.None);
		
		rawDataRadio = new Button(hierarchygroup, SWT.RADIO);
		rawDataRadio.setText("with raw data (only valid for detailed delegated)");
		rawDataRadio.setEnabled(false);
		
		Composite levelGroup = new Composite(hierarchygroup, SWT.NONE);
		gridLayout = new GridLayout(2, false);
		levelGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		levelGroup.setLayoutData(gd);

		new Label(levelGroup, SWT.NONE).setText("Event(s) applies to ");
		eventLevelCombo = new DataTypeCombo(levelGroup, TableHelper.getTableUtilities().getEventLevels(), isEditMode());
		
		eventLevelCombo.setEnabled(false);
		
		Group eventGroup = new Group (body, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		eventGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		eventGroup.setLayoutData(gd);
		eventGroup.setText("Events");
		
		if (groupForEdit == null) {
			groupForEdit = TableHelper.getTableUtilities().getFactory().createTableGroup();
		}
		
		eventControl = new EventListControl(eventGroup, SWT.FILL, false) {
			protected void refreshView(boolean reload) {
				super.refreshView(reload);
				refreshEventLevel();
			}
			
		};
		eventControl.setInput(groupForEdit, null);
		eventControl.setCommandStack(getCommandStack());
		
		Composite tooltipComposite = new Composite(body, SWT.FILL);
		gridLayout = new GridLayout(2, false);
		tooltipComposite.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_BOTH);
		tooltipComposite.setLayoutData(gd);
		toolTipChk = new Button(tooltipComposite, SWT.CHECK);
		toolTipLabel = new Label(tooltipComposite, SWT.NONE);
		toolTipLabel.setText("Tooltip (it will only be displayed if an event exist)");
		
		
        if (getWidget(groupForEdit).eResource() != null) {
        	boolean exists = translationExists(groupForEdit);
        	toolTipChk.setSelection(exists);
        	if (exists) {
        		createTooltipEditor(toolTipChk.getParent());
	        }
        } else {
        	toolTipChk.setEnabled(false);
        	toolTipLabel.setText("Tooltip (TableGroup must first be created before you can edit a tooltip)");
        }
		
		if (isEditMode()) {
			columnCombo.select(groupForEdit.getColumnName());
			columnCombo.setEnabled(false);
			sortCombo.select(groupForEdit.getSortingColumnName());
			if (!StringUtils.isEmpty(groupForEdit.getSortingDirection())) {
				directionCombo.setEnabled(true);
				directionCombo.select(groupForEdit.getSortingDirection());
			}
			eventControl.setInput(groupForEdit, null);
			maxGrouping.setText(String.valueOf(groupForEdit.getMaxGrouping()));
			collapseChk.setSelection(groupForEdit.isCollapsed());
			usedForDynamicColumnChk.setSelection(groupForEdit.isUsedForDynamicColumn());
			hierarchyChk.setSelection(groupForEdit.isHierarchy());
			if (groupForEdit.getRank()!= 1 ) {
				hierarchyChk.setEnabled(false);
			}
			eventLevelCombo.select(groupForEdit.getEventLevel());			
		} else {
			// default selection
			directionCombo.select(PropertyTypeConstants.SORT_ASCENDING);
			if (!table.getGroups().isEmpty()) {
				hierarchyChk.setEnabled(false);
			}
		}
		
		if (hierarchyChk.isEnabled() && groupForEdit.isHierarchy()) {
			aggDataRadio.setEnabled(true);
			rawDataRadio.setEnabled(true);
			if (groupForEdit.isAggregatedData()) {
				aggDataRadio.setSelection(true);
			} else {
				rawDataRadio.setSelection(true);
			}
		}
		
		boolean selected = usedForDynamicColumnChk.getSelection();
		collapseChk.setEnabled(!selected);
		maxGrouping.setEnabled(!selected);
		if (selected) {
			collapseChk.setSelection(false);
			maxGrouping.setText("");
		}		
		refreshEventLevel();
		
		columnCombo.addSelectionListener(this);
		sortCombo.addSelectionListener(this);
		directionCombo.addSelectionListener(this);
		maxGrouping.addModifyListener(this);
		collapseChk.addSelectionListener(this);
		usedForDynamicColumnChk.addSelectionListener(this);
		hierarchyChk.addSelectionListener(this);
		toolTipChk.addSelectionListener(this);
		rawDataRadio.addSelectionListener(this);
		aggDataRadio.addSelectionListener(this);
		return composite;
	}	
	
	/**
	 * 
	 */
	private void refreshEventLevel() {
		if (TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode())) {
			if (rawDataRadio.getSelection() && !groupForEdit.getEvents().isEmpty()) {
				eventLevelCombo.setEnabled(true);
			} else {
				eventLevelCombo.setEnabled(false);
			}
		}
	}
	
	/**
	 * @param body
	 */
	private void createTooltipEditor(Composite body) {
		tooltipgroup = new Group (body, SWT.SHADOW_ETCHED_IN);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		tooltipgroup.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		gd.horizontalSpan = 2;
		tooltipgroup.setLayoutData(gd);
		tooltipgroup.setText("Tooltip translation");
		
        Resource resource = getWidget(groupForEdit).eResource();
		if ( resource != null) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			if(OfsCore.isOfsProject(ofsProject.getProject())) {
				
				ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
				ITranslation translation = manager.getTranslation(getWidget(groupForEdit));
				if (translation != null) {
					ITranslationPreferences prefs = manager.getPreferences();
					ITranslationModel model = new TranslationModel(prefs, translation);
					ITranslationViewer viewer = TranslationUICore.getTranslationViewer(ofsProject.getProject(), tooltipgroup);
					viewer.hideKindSelector();
					// viewer.setRowCount(1);
					viewer.setTranslationModel(model);
				}
			}
		}
		
		
		
	}
	
	private boolean translationExists(ITableGroup group) {
        Resource resource = getWidget(group).eResource();
		if ( resource != null) {
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
			if (ofsProject != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
				ITranslation translation = manager.getTranslation(group.getWidget());
				if (translation != null) {
					ITranslationPreferences preferences = manager.getPreferences();
					List<Locale> locales = new ArrayList<Locale>();
					locales.add(preferences.getDefaultLocale());
					locales.addAll(preferences.getAdditionalLocales());
					try {
						for (ITranslationKind kind : translation.getAllKinds()) {
							for (Locale locale : locales) {
								if (translation.getText(kind, locale) != null) {
									return true;
								}
							}
						}
					} catch (TranslationException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent event) {
		if (event.widget==sortCombo.getCombo()) {
			if (sortCombo.getSelectedValue() != null) {
				directionCombo.setEnabled(true);
			}
		} else if (event.widget == toolTipChk){
			if (toolTipChk.getSelection() && getWidget(groupForEdit).eResource() != null) {
				createTooltipEditor(toolTipChk.getParent());
				this.getShell().pack();
			} else if (!toolTipChk.getSelection()) {
				if (tooltipgroup != null && !tooltipgroup.isDisposed()) {
					tooltipgroup.dispose();
					tooltipgroup = null;
					this.getShell().pack();
				}
			}
		} else if (event.widget == hierarchyChk) {
			if (hierarchyChk.isEnabled() && hierarchyChk.getSelection()) {
				aggDataRadio.setEnabled(true);
				rawDataRadio.setEnabled(true);
				aggDataRadio.setSelection(true);
				rawDataRadio.setSelection(false);
			} else {
				aggDataRadio.setEnabled(false);
				rawDataRadio.setEnabled(false);				
			}
		} else if (event.widget == aggDataRadio){
			if (aggDataRadio.isEnabled() && aggDataRadio.getSelection()) {
				eventLevelCombo.setEnabled(false);					
			}			
		} else if (event.widget == rawDataRadio) {
			if (rawDataRadio.isEnabled() && rawDataRadio.getSelection()) {
				boolean detailedDel = TableHelper.getTableUtilities().isDetailedDelegatedRenderingMode(table.getRenderingMode());
				// allowed only for detailed-delegated rendering mode
				if (!detailedDel) {
					String msg = "It is not possible to enable raw data on " +
							"table with "+table.getRenderingMode().getValue()+" rendering mode";
					MessageDialog.openError(getShell(), "Aggregates found", msg);
					aggDataRadio.setSelection(true);
					rawDataRadio.setSelection(false);
					return;
				}
				
				if (detailedDel && !groupForEdit.getEvents().isEmpty()) {
					if (rawDataRadio.getSelection()) {
						eventLevelCombo.setEnabled(true);
					} else {
						eventLevelCombo.setEnabled(false);						
					}
				}
				
				// check if no aggregates are defined
				/*
				List<ITableAggregate> aggrCols = table.getAggregatedColumns();				
				if (!aggrCols.isEmpty()) {
					String msg = "It is not possible to enable raw data, as " +
							"aggregates are defined for the Table";
					MessageDialog.openError(getShell(), "Aggregates found", msg);
					aggDataRadio.setSelection(true);
					rawDataRadio.setSelection(false);
					return;
				}
				*/
			}
		} else if (event.widget == usedForDynamicColumnChk) {
			boolean selected = usedForDynamicColumnChk.getSelection();
			collapseChk.setEnabled(!selected);
			maxGrouping.setEnabled(!selected);
			if (selected) {
				collapseChk.setSelection(false);
				maxGrouping.setText("");
			}
		}
		super.widgetSelected(event);
	}

	/**
	 * @param group 
	 * @return Widget
	 */
	private Widget getWidget(ITableGroup group) {
		IWidgetAdapter widgetAdapter = (IWidgetAdapter)AdaptableUtils.getAdapter(group, IWidgetAdapter.class);
		return widgetAdapter.getWidget();
	}
	
	/**
	 * @param table
	 * @return List
	 */
	private List<String> getGroupedColumns(ITable table) {
		List<String> list = new ArrayList<String>();
		for (ITableGroup group : table.getGroups()) {
			if (!isEditMode() || (isEditMode() && !groupForEdit.getColumnName().equals(group.getColumnName()))) {
				list.add(group.getColumnName());
			}
		}
		Collections.sort(list);
		return list;
	}
	
	/**
	 * @param table
	 * @return List
	 */
	private List<String> getSortedColumns(ITable table) {
		return Collections.emptyList();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#getCommand()
	 */
	@Override
	protected BaseCommand getCommand() {
		BaseCommand cmd = null;

		String groupColumn = columnCombo.getSelectedValue();
		String sortColumn = sortCombo.getSelectedValue();
		if (!isEditMode())
			groupForEdit.setColumnName(groupColumn);
		if (!StringUtils.isEmpty(sortColumn)) {
			String direction = directionCombo.getSelectedValue().getValue();
			groupForEdit.setSortingColumnName(sortColumn);
			groupForEdit.setSortingDirection(direction);
		} else {
			groupForEdit.setSortingColumnName("");
			groupForEdit.setSortingDirection("");			
		}
		if (!StringUtils.isEmpty(maxGrouping.getText()) && isNumber(maxGrouping.getText())) {
			groupForEdit.setMaxGrouping(new Integer(maxGrouping.getText()).intValue());
		}
		
		groupForEdit.setCollapsed(collapseChk.getSelection());
		groupForEdit.setUsedForDynamicColumn(usedForDynamicColumnChk.getSelection());
		if (hierarchyChk.isEnabled()) {
			groupForEdit.setHierarchy(hierarchyChk.getSelection());
			groupForEdit.setAggregatedData(aggDataRadio.getSelection());
		} else {
			groupForEdit.setHierarchy(false);
		}
		
		if (eventLevelCombo.isEnabled()) {
			groupForEdit.setEventLevel(eventLevelCombo.getSelectedValue().getValue());
		}
		
		if (isEditMode()) {		
			cmd = null;			
		} else {
			groupForEdit.setRank(TableHelper.getNextGroupRank(table));
			cmd = null;
		}		
		return cmd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (!isEditMode()) {
			if (StringUtils.isEmpty(columnCombo.getSelectedValue())) {
				setErrorMessage("Grouping Column is not specified");
				return false;
			} else if (!StringUtils.isEmpty(sortCombo.getSelectedValue()) && directionCombo.getSelectedValue() == null) {
				setErrorMessage("Sorting Direction is not specified");
				return false;
			} else if(!StringUtils.isEmpty(maxGrouping.getText()) && !isNumber(maxGrouping.getText())) {
				setErrorMessage("Max. Grouping should be an integer");	
				return false;			
			} 
		} else if (isEditMode()) {
			if (!StringUtils.isEmpty(sortCombo.getSelectedValue()) && directionCombo.getSelectedValue() == null) {
				setErrorMessage("Sorting Direction is not specified");
				return false;
			} else if(!StringUtils.isEmpty(maxGrouping.getText()) && !isNumber(maxGrouping.getText())) {
				setErrorMessage("Max. Grouping should be an integer");	
				return false;			
			}
		}
		setErrorMessage(null);
		return true;
	}
	
	/**
	 * @param value
	 * @return boolean
	 */
	private boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

}
