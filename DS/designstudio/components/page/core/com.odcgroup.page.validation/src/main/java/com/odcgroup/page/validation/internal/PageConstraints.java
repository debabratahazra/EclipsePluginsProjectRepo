package com.odcgroup.page.validation.internal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;
import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.model.widgets.table.ITableFeature;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.model.widgets.table.ITableUtilities;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class defines a set of validation constraints invoked by OAW script.
 *
 * @author atr
 * @since DS 1.40.0
 */
public class PageConstraints {	

	private static final String[] FRAG_EXTN = {PageConstants.FRAGMENT_FILE_EXTENSION};
	
	
    private static class PathResolver extends PathVisitor {

        private final MdfDataset root;
        private MdfEntity type;
        private MdfProperty prop = null;
        private MdfAssociation assoc = null;
        
        PathResolver(MdfDataset root) {
        	this.root = root;
            this.type = root.getBaseClass();
        }

        public boolean visit(String name) {
            if (type instanceof MdfClass) {
                prop = ((MdfClass) type).getProperty(name);

                if (prop == null) {
                    prop = ModelHelper.getReverseAssociation(
                            root.getParentDomain(), (MdfClass) type, name);
                }

                if (prop == null) {
                    // Property <name> does not exist for class <type.getQualifiedName()>
                    return false;
                } else {
                	
                	if (prop instanceof MdfAssociation) {
                		assoc = (MdfAssociation)prop;
                	}
                	
                	
                    type = prop.getType();
                    return true;
                }
            } else {
                // Property <name> does not exist for tyoe <type.getQualifiedName()>
                return false;
            }
        }
        
        @SuppressWarnings("unused")
		public final MdfAssociation getAssociation() {
        	return this.assoc;
        }
        
        public MdfProperty resolve(String path) {
        	PathVisitor.visitPath(path, this);
        	return prop;
        }

    }	
	
    
    
	/**
	 * @param widget
	 * @return ITable
	 */
	public static ITable getTable(Widget widget) {
		ITable table = null;
		if (widget.getTypeName().equals("TableTree")) {
			table = TableHelper.getTable(widget);
		}
		return table;
	}
	
	/**
	 * @param widget
	 * @return ITableColumn
	 */
	public static ITableColumn getTableColumn(Widget widget) {
		ITableColumn column = null;
		if (widget.getTypeName().equals("TableColumn")) {
			column = TableHelper.getTableColumn(widget);
		}
		return column;
	}
	
	/**
	 * @param widget
	 * @return ITableAggregate
	 */
	private static ITableAggregate getTableAggregate(Widget widget) {
		ITableAggregate aggregate = null;
		if (widget.getTypeName().equals("TableAggregate")) {
			aggregate = TableHelper.getTableAggreage(widget);
		}
		return aggregate;
	}
	
	public static String getModelName(Widget widget) {
		return WidgetUtils.getModelName(widget);
	}
	
	public static String getDomainAttributeName(Widget widget) {
		return WidgetUtils.getDomainAttribute(widget);
	}
	
	public static String getImageName(Widget widget) {
		String result = "";
		Property p = widget.findProperty("icon");
		if (p != null) {
			result = p.getValue();
		}
		return result;
	}

	public static String getImageLocation(Widget widget) {
		String result = "";
		Property p = widget.findProperty("icon");
		if (p != null) {
			
			String imageName = p.getValue();
			if (StringUtils.isNotEmpty(imageName)) {

				Resource res = widget.eResource();
				if (res == null) return "";
				
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
				if (ofsProject == null) return "";
				
				ImageDescriptor descriptor = 
					CorporateImagesUtils.getCorporateImages(ofsProject).getImageDescriptor(imageName);
				
				if (descriptor != null) {
					try {
						result = descriptor.toURL().toString();
					} catch (MalformedURLException e) {
						e.printStackTrace();
						result = "";
					}
				}
				
			}
		}
		return result;
	}

	/**
	 * @param widget
	 * @return
	 */
	public static String getTableColumnName(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null)
			return column.getColumnName();
		else 
			return "";
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableAggregateColumnName(Widget widget) {
		ITableAggregate aggregate = TableHelper.getTableAggreage(widget);
		return aggregate.getColumnName();
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableGroupColumnName(Widget widget) {
		ITableGroup group = TableHelper.getTableGroup(widget);
		return group.getColumnName();
	}		
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableKeepFilterColumnName(Widget widget) {
		ITableKeepFilter group = TableHelper.getTableKeepFilter(widget);
		return group.getColumnName();
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableSortColumnName(Widget widget) {
		ITableSort sort = TableHelper.getTableSort(widget);
		return sort.getColumnName();
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableExtraColumnName(Widget widget) {
		ITableExtra extra = TableHelper.getTableExtra(widget);
		return extra.getColumnName();
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkFragmentsWithDefaultLinkedDataset(Widget widget) {
    	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
    	if (ofsProject == null) {
    		return true;
    	}
		
		if (!isCardinalityManyFragment(widget) && isDefaultLinkedFragment(widget, ofsProject, false)) {
			// get all fragments
			String domainEntity = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			try {
				List<Widget> widgets = getFragmentWidgets(ofsProject, domainEntity);
				for (Widget fragment : widgets) {
					if (!fragment.equals(widget) 
							&& isDefaultLinkedFragment(fragment, ofsProject, true)
							&& !isCardinalityManyFragment(fragment)) {
						return false;
					}
				}	
			} catch (Exception e) {
	    		Activator.getDefault().logWarning(e.getLocalizedMessage(), e);
			}
		}
		return true;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	private static boolean isCardinalityManyFragment(Widget widget) {    	
    	if (WidgetTypeConstants.FRAGMENT.equals(widget.getTypeName()) 
    			&&  PropertyTypeConstants.CARDINALITY_MANY.equals(widget.getPropertyValue(PropertyTypeConstants.CARDINALITY))) {
    		return true;
    	}
    	return false;
	}
	
	
	/**
	 * @param ofsProject
	 * @return
	 * @throws Exception
	 */
	private static List<Widget> getFragmentWidgets(IOfsProject ofsProject, String domainEntity) {
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, FRAG_EXTN);
		Set<IOfsModelResource> mResources = lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL);
		List<Widget> widgets = new ArrayList<Widget>();
		for (IOfsModelResource mResource : mResources) {
			try {
				List<EObject> eObjects = mResource.getEMFModel();
				if (!eObjects.isEmpty() && (eObjects.get(0) instanceof Model)) {
					Model model = (Model)eObjects.get(0);
					Widget widget = model.getWidget();
					if(widget!=null) {
						if (domainEntity.equals(widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY))) {
							widgets.add(model.getWidget());
						}
					}
				}
			} catch (IOException e) {
				// ignore if a fragment cannot be read
			} catch (InvalidMetamodelVersionException e) {
				// ignore if a fragment has an invalid metamodel version
			}
		}
		return widgets;
	}
	
	/**
	 * @param widget
	 * @param ofsProject
	 * @return
	 */
	private static boolean isDefaultLinkedFragment(Widget widget, IOfsProject ofsProject, boolean other) {
		if (ofsProject == null) {
			return false;
		}
		if (widget.getTypeName().equals(WidgetTypeConstants.FRAGMENT)) {
			if (other) {	
				Resource res = widget.eResource();	
				try {
					if (!res.isLoaded()) {
						res.load(Collections.EMPTY_MAP);
					}
				} catch (IOException e) {
					// ignore
				}
				Model model = (Model) res.getContents().get(0);
				widget = model.getWidget();
			}
			if ("true".equals(widget.getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableHasColumns(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null) {
			result = table.getColumnCount() > 0;
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkConditionWidgetCode(Widget widget) {
		if (widget.getTypeName().equals("ConditionalBody")) {
			String code = widget.getPropertyValue("javaCode");
			if(StringUtils.isEmpty(code)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkRadioButtonGroup(Widget widget) {
		if (widget.getTypeName().equals("RadioButton")) {
			String group = widget.getPropertyValue("group");
			if (StringUtils.isEmpty(group)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkRadioButtonID(Widget widget) {
		if (widget.getTypeName().equals("RadioButton")) {
			if (StringUtils.isEmpty(widget.getID())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkSearchEvent(Widget widget) {
		if (WidgetTypeConstants.SEARCH_FIELD.equals(widget.getTypeName()) 
				|| WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
			ISearchField sf = WidgetHelper.getSearchField(widget);
			if (sf.isAutoCompleteAndSearch() || sf.isSearchOnly()) {
				List<String> events = parseEvents(widget.getEvents());
				if (!events.contains("Search")) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkWidgetGroupOnSearchField(Widget widget) {
		if (WidgetTypeConstants.SEARCH_FIELD.equals(widget.getTypeName())
				|| WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
			ISearchField sf = WidgetHelper.getSearchField(widget);
			List<String> events = parseEvents(widget.getEvents());
			if (events.contains("Search")) {
				Event search = getEvent(widget, "Search");
				String val = search.findParameter("widget-group-ref").getValue();
				if (!sf.getWidgetGroup().startsWith(val)) {
					return false;
				}
			}
		}
		return true;		
	}
	
	/**
	 * @param widget
	 * @param eventName
	 * @return
	 */
	private static Event getEvent(Widget widget, String eventName) {
		for (Event event : widget.getEvents()) {
			if(eventName.equals(event.getEventName())) {
				return event;
			}
		}
		return null;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkOtherEventsOnSearchField(Widget widget) {
		if (WidgetTypeConstants.SEARCH_FIELD.equals(widget.getTypeName())
				|| WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
			ISearchField sf = WidgetHelper.getSearchField(widget);
			if (sf.isAutoCompleteAndSearch() || sf.isSearchOnly()) {
				List<String> events = parseEvents(widget.getEvents());
				if (events.contains("Search")) {
					if (events.contains("OnClick")
							|| events.contains("OnEnter")
							|| events.contains("OnChange")) {
						return false;
					}
				}
			}
		}
		return true;
	}	
	
	/**
	 * @param events
	 * @return
	 */
	private static List<String> parseEvents(List<Event> events) {
		List<String> eventNames = new ArrayList<String>();
		for (Event event : events) {
			eventNames.add(event.getEventName());
		}
		return eventNames;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkCodeWidgetCode(Widget widget) {
		if (widget.getTypeName().equals("Code")) {
			String code = widget.getPropertyValue("code");
			if(StringUtils.isEmpty(code)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkHierarchyDefinitionLevel(Widget groupWidget) {
		if(groupWidget.getTypeName().equals("TableGroup")) {
			ITableGroup group = TableHelper.getTableGroup(groupWidget);
			if (group.isHierarchy() && group.getRank() != 1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkMultipleHierarchies(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			int ii = 0;
			for (ITableGroup group : table.getGroups()) {
				if (group.isHierarchy()) {
					ii++;
				}
			}
			if (ii > 1) {
				return false;
			}
		}
		return true;
	}
	 
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkDisplayGroupingColumnNotanAggregate(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isDisplayGrouping()) {
			if (isAggregatedColumn(column)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkAggregatesInSummaryMode(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.getTable().getRenderingMode().getValue().equals("summary-delegated")) {
			if (!column.isPlaceHolder()) {
				if (!isAggregateDefined(column.getTable(), column.getColumnName())) {
					return false;
				}
				if (column.isComputed()) {
					for(String str :column.getParameters()) {
						if (!isAggregateDefined(column.getTable(), str)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkGroupingsInSummaryMode(Widget widget) {
		ITable table = getTable(widget);
		if (table != null && table.getRenderingMode().getValue().equals("summary-delegated")) {
			if (table.getGroups().isEmpty()) {
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * @param table
	 * @param columnName
	 * @return
	 */
	private static boolean isAggregateDefined(ITable table, String columnName) {
		for (ITableAggregate aggregate : table.getAggregatedColumns()) {
			if (columnName.equals(aggregate.getColumnName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTypeforaCheckboxPlaceholder(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isPlaceHolder() && (column.getCheckbox() != null)) {
			if (!column.getDisplayType().getValue().equals("boolean")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getValidAggregatedComputation(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			String renderingMode = table.getRenderingMode().getValue();
			if (renderingMode.equals("detailed-delegated")|| renderingMode.equals("summary-delegated")) {
				String computation = null;
				for (ITableAggregate aggregate : table.getAggregatedColumns()) {
					computation = aggregate.getComputation();
					if (computation.equals("sum") || computation.equals("max")|| computation.equals("weighted-mean")) {
						return null;
					} else {
						return computation;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkIsSortDefinedOnDetailedDelegate(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			String renderingMode = table.getRenderingMode().getValue();
			if (renderingMode.equals("detailed-delegated") ) {
				if (table.getSorts().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkIsValidComputation(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			String computation = column.getComputation().getValue();
			String renderingMode = column.getTable().getRenderingMode().getValue();
			if (renderingMode.equals("summary-delegated") ) {
				if (computation.equals("make-amount") || computation.equals("same") || computation.equals("weighted-mean")) {
					return true;
				} else {
					return false;
				}
			} else if(renderingMode.equals("detailed-delegated")) {	
				if (computation.equals("make-amount") || computation.equals("same")) {
					return true;
				} else {
					return false;
				}	
				
			}
		}
		return true;
	}	
	
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getColumnComputation(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			return column.getComputation().getValue();
		}
		return null;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getTableRenderingMode(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null) {
			return column.getTable().getRenderingMode().getValue();
		}
		return "";
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static String getRenderingMode(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			return table.getRenderingMode().getValue();
		}
		return "";
	}
	
	/**
	 * @param table
	 * @param column
	 * @return
	 */
	private static boolean isAggregatedColumn(ITableColumn column) {
		List<ITableAggregate> aggregates = column.getTable().getAggregatedColumns();
		for (ITableAggregate aggregate : aggregates) {
			if (column.getColumnName().equals(aggregate.getColumnName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableDuplicateDomainColumn(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isBoundToDomain()) {
			ITable table = column.getTable();
			for (ITableColumn c : table.getColumns()) {
				if (c.isBoundToDomain() && (widget != c.getWidget())) {
					if (StringUtils.equals(column.getColumnName(), c.getColumnName())) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}			


	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableDomainColumnHasGroup(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isBoundToDomain()) {
			String columnName = column.getColumnName();
			ITable table = column.getTable();
			for (ITableGroup group : table.getGroups()) {
				if (StringUtils.equals(columnName, group.getColumnName())) {
					result = false;
					break;
				}
			}
		}
		return result;
	}			

	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableComputedColumnHasComputation(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			Property prop = column.getComputation();
			result = prop != null && StringUtils.isNotEmpty(prop.getValue());
		}
		return result;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableComputedColumnHasParameters(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			List<String> parameters = column.getParameters();
			result = parameters.size() > 0;
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableComputedColumnComputation(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			Property prop = column.getComputation();
			if (prop != null && "make-amount".equalsIgnoreCase(prop.getValue())) {
				Property p = column.getDisplayType();
				return p != null && "amount".equals(p.getValue());
			}
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableComputedColumnHasDisplayType(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isComputed()) {
			Property p = column.getDisplayType();
			return p != null && StringUtils.isNotEmpty(p.getValue());
		}
		return result;
	}		
	
	/**
	 * Checkbox on tree nodes requires atleast one group on the table 
	 * @param widget
	 * @return
	 */
	public static boolean checkTableGroupsOnDisplayCheckbox(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null && table.displayCheckboxOnTreeNodes()) {
			return table.hasGroups();
		}
		return result;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkCheckboxOnPlaceholder(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null && 
				(table.displayCheckboxOnTreeNodes() || table.makeCheckboxesExclusive())) {
			List<ITableColumn> columns = table.getColumns();
			boolean found = false;
			for (ITableColumn column : columns) {
				if (column.isPlaceHolder()) {
					if(column.getCheckbox() != null) {
						found = true;
						break;
					}
				}
			}
			result = found;			
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkSeveralCheckboxesWhenExclusive(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null && table.makeCheckboxesExclusive()) {
			List<ITableColumn> columns = table.getColumns();
			int count = 0;
			for (ITableColumn column : columns) {
				if(column.isPlaceHolder()) {
					if (column.getCheckbox() != null) {
						count++;
					}
				}
			}
			if (count < 2) {
				result = false;
			}			
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkCheckBoxIdentifierIsNotEmpty(Widget widget) {
		boolean result = true;
		if (widget.getTypeName().equals(WidgetTypeConstants.CHECKBOX) && isParentTableColumn(widget)) {
			ITableColumn column = TableHelper.getTableColumn(widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN));
			if (column.isPlaceHolder() && !column.isDisplayGrouping()) {
				String value = widget.getPropertyValue("column-checkbox-identifier");
				if (StringUtils.isEmpty(value)) {
					result = false;
				}
			}
		}
		return result;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkSecurityColumnOnCheckbox(Widget widget) {
		boolean result = true;
		if (widget.getTypeName().equals(WidgetTypeConstants.CHECKBOX) && isParentTableColumn(widget)) {
			String enabled = widget.getPropertyValue(PropertyTypeConstants.ENABLED);
			String secAttr = widget.getPropertyValue("column-checkbox-security");
			if (!"true".equals(enabled) && !StringUtils.isEmpty(secAttr)) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean isParentTableColumn(Widget widget) {
		return widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN) != null;
	}
	
	
	/**
	 * The multi-selection applies only if for delegating detailed table
	 * @param widget
	 * @return
	 */
	public static boolean checkTableMultiSelectionAndRenderingMode(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isPlaceHolder() && (column.getCheckbox() != null)) {
			ITableUtilities utils = TableHelper.getTableUtilities();
			return utils.isDetailedDelegatedRenderingMode(column.getTable().getRenderingMode());
		}
		return result;
	}
	
	/**
	 * if multiple checkboxes are defined, select/deselect all cannot be true
	 * @param widget
	 * @return
	 */
	public static boolean checkTableMultiSelectionAndSelectDeselectAll(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null && table.makeCheckboxesExclusive() && table.hasSelectDeselectAll()) {
			return false;
		}
		return result;
	}	
	
	/**
	 * placeholder column cannot use display grouping and checkbox together
	 * @param widget
	 * @return
	 */
	public static boolean checkPlaceHolderDisplayGroupingOnCheckbox(Widget widget) {
		boolean result = true;
		ITableColumn column = getTableColumn(widget);
		if (column != null && column.isPlaceHolder() && (column.getCheckbox()!=null)) {
			return !column.isDisplayGrouping();
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableFilterOnPlaceholderWithCheckbox(Widget widget) {
		boolean result = true;
		ITable table = getTable(widget);
		if (table != null && table.hasMultiSelection() && table.hasFilter()) {
			return false;
		}
		return result;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableAggregateHasGroup(Widget widget) {
		boolean result = true;
		ITableAggregate aggregate = getTableAggregate(widget);
		if (aggregate != null) {
			return aggregate.getGroupNames().length > 0;
		}
		return result;
	}		
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableAggregateNotOnFirstColumn(Widget widget) {
		boolean result = true;
		ITableAggregate aggregate = getTableAggregate(widget);
		if (aggregate != null) {
			ITableColumn column = aggregate.getTable().getColumn(aggregate.getColumnName());
			if (column != null) {
				result = column.getColumnIndex() > 0;
			}
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkImageIsAvailable(Widget widget) {
		boolean result = true;
		Property p = widget.findProperty("icon");
		if (p != null) {
			
			String imageName = p.getValue();
			if (StringUtils.isNotEmpty(imageName)) {

				Resource res = widget.eResource();
				if (res == null) return true;
				
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
				if (ofsProject == null) return true;
				
				ImageDescriptor descriptor = 
					CorporateImagesUtils.getCorporateImages(ofsProject).getImageDescriptor(imageName);
				
				if (descriptor == null) {
					result = false;
				}
				
			}
		}
		return result;
	}	
	
	public static boolean checkImageExists(Widget widget) {
		boolean result = true;
		Property p = widget.findProperty("icon");
		if (p != null) {
			
			String imageName = p.getValue();
			if (StringUtils.isNotEmpty(imageName)) {

				Resource res = widget.eResource();
				if (res == null) return true;
				
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
				if (ofsProject == null) return true;
				
				result = CorporateImagesUtils.getCorporateImages(ofsProject).imageExists(imageName);
				
			}
		}
		return result;
	}	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkMultipleDisplayGroupingColumns(Widget widget) {
		int ii = 0;
		ITable table = getTable(widget);
		if (table != null && !table.getGroups().isEmpty()) {
			for (ITableColumn column : table.getColumns()) {
				if (column.isDisplayGrouping()) {
					ii++;
				}
			}
			if (ii != 1) {
				return false;
			}
		}
		return true;
	}
	
    /** Services Namespace URI */
    private static final String SERVICES_NAMESPACE_URI = "http://www.odcgroup.com/mdf/services";
    
    /** name of the annotation for services */
    private static final String SERVICES_ANNOTATION = "Services";
    
    /** name of the property for loading-permitted values */
    private static final String SERVICES_PROPERTY_PERMITTED_VALUES = "LoadPermittedValues";
	
    /**
     * @param model
     * @param namespace
     * @param annotationName
     * @return MdfAnnotation
     */
    private static MdfAnnotation getAnnotation(MdfModelElement model, String namespace, String annotationName) {
        MdfAnnotation annotation = model.getAnnotation(namespace, annotationName);
        return annotation;
    }	
    
    /**
     * @param annotation
     * @param propertyName
     * @return
     */
    private static String getAnnotationProperty(MdfAnnotation annotation, String propertyName) {
        MdfAnnotationProperty prop = annotation.getProperty(propertyName);
        return (prop == null) ? null : prop.getValue();
    }
    
    /**
     * @param include
     * @return
     */
    public static boolean checkComboboxValidationInIncludes(Widget include) {
    	if (isInclude(include)) {
    		Resource res = include.eResource();
    		if (res == null) return true;
    		
    		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
    		if (ofsProject == null) return true;
    		
    		DomainRepository repository = DomainRepository.getInstance(ofsProject);
    		if (repository == null) return true;
    		
    		String domainAssociation = include.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
			if (include.getModel() == null || StringUtils.isEmpty(domainAssociation)) {
				return true;
			}

	    	MdfModelElement element = include.findDomainObject(repository, domainAssociation);
	    	MdfDatasetMappedProperty mapped = null;
	    	if (element != null && element instanceof MdfDatasetMappedProperty) {
	    		mapped = (MdfDatasetMappedProperty) element;
	    	} else {
	    		return true;
	    	}
			MdfAssociation assoc = getMdfAssociation(domainAssociation, mapped);
			if (!isDomainAssociationByRef(assoc)) {
				return true;
			}
			
			Property prop = include.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
			Widget includeWidget = null;
        	if (prop != null) {
              	Model model = prop.getModel();
              	includeWidget = (model != null) ? model.getWidget() : null;
        	}
			if (includeWidget != null) {
				Iterator<EObject> contents = includeWidget.eAllContents();
				while (contents.hasNext()) {
					EObject eObject = (EObject) contents.next();
					if (eObject instanceof Widget) {
						Widget widget = (Widget) eObject;
						if (widget.getTypeName().equals(WidgetTypeConstants.COMBOBOX)) {
							MdfProperty property = fetchComboProperty(widget, repository);
							if (property != null) {
								// if combo is not based on enumeration
								if (property.getType() != null && !(property.getType() instanceof MdfEnumeration)) {
									//check for service annotation on dataset property
									boolean exists = checkServiceAnnotation(mapped);
									if (exists) {
										continue;
									}
									// later check for service annotation on the base class
									if (!checkServiceAnnotation(assoc)) {
										return false;
									}								
								}
							}
						}
					}
				}
			}			
    	}
    	return true;
    }
    
    /**
     * @param element
     * @return
     */
    private static boolean checkServiceAnnotation(MdfModelElement element) {
    	MdfAnnotation annotation = getAnnotation(element, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION);
		if (annotation == null) {
			return false;
		}
		// check for LoadPermittedValues property
		String value = getAnnotationProperty(annotation, SERVICES_PROPERTY_PERMITTED_VALUES);
		if (value == null) {
			return false;
		} else {
			return Boolean.valueOf(value).booleanValue();
		}
    }
    
    /**
     * @param domainAssoc
     * @param rs
     * @return
     */
    private static MdfAssociation getMdfAssociation(String domainAssoc, MdfDatasetMappedProperty mapped) {
    	MdfAssociation assoc = null;
    	if (mapped != null) {
    		MdfDataset ds = mapped.getParentDataset();
    		if (ds != null) {
    			MdfProperty property = ds.getBaseClass().getProperty(mapped.getPath());
    			if (property instanceof MdfAssociation) {
    				assoc = (MdfAssociation) property;
    			}
    		}
    	}
    	return assoc;
    }
    
    /**
     * @param domainAssoc
     * @return
     */
    private static boolean isDomainAssociationByRef(MdfAssociation assoc) {
		if (assoc != null && assoc.getContainment() == MdfContainment.BY_REFERENCE 
				&& assoc.getMultiplicity() == MdfMultiplicity.ONE) {
			return true;
		}
    	return false;
    }

	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isInclude(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.INCLUDE);
	}
	
	/**
	 * @param widget
	 * @param repository
	 * @return
	 */
	private static MdfProperty fetchComboProperty(Widget widget, DomainRepository repository) {
		MdfModelElement element = widget.findDomainObject(repository);
		if (element != null) {
			if (element instanceof MdfDatasetMappedProperty) {
				MdfDatasetMappedProperty dsmProperty = (MdfDatasetMappedProperty) element;
				PathResolver resolver = new PathResolver(dsmProperty.getParentDataset());
				MdfProperty prop = resolver.resolve(dsmProperty.getPath());			
				return prop;
			}
		}
		return null;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkComboboxInEditableTableDomainHasPermittedValues(Widget widget) {
		if (!WidgetTypeConstants.TABLE_COLUMN_COMBOBOX_ITEM.equals(widget.getTypeName())) {
			return true;
		}

		Resource res = widget.eResource();
		if (res == null) return true;
		
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
		if (ofsProject == null) return true;
		
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		if (repository == null) return true;
		
		Widget editTable = widget.findAncestor(WidgetTypeConstants.TABLE_TREE);
		if (editTable == null) return true;
		String datasetName = editTable.getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
		if (StringUtils.isBlank(datasetName)) return true;

		String attributeName = widget.getPropertyValue(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
		if (StringUtils.isBlank(attributeName)) return true;

		MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
		if (dataset == null) return true; 

		MdfDatasetProperty dsProperty = null;
		int pos = attributeName.indexOf(".");
		if (pos != -1) {
			// the attribute is defined in a linked dataset (association)
			String assoc = attributeName.substring(0,pos);
			// attribute in the linked dataset
			attributeName = attributeName.substring(pos+1);
			if (StringUtils.isNotBlank(attributeName)) {
				// extract association.
				// load the editable dataset.
	    		MdfDatasetProperty dp = dataset.getProperty(assoc);
	    		if (dp == null) return true;

	    		MdfDataset linkedDataset = null;
	    		if (dp instanceof MdfDatasetMappedProperty) {
	    			MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty) dp;
	    			if (dmp.isTypeDataset()) {
	    				linkedDataset = ((MdfDatasetMappedPropertyImpl) dmp).getLinkDataset();
	    			}
	    		}
	    		if (linkedDataset == null) return true;
	    		// load the property in the linked dataset
	    		datasetName = linkedDataset.getQualifiedName().toString();
	    		dsProperty = linkedDataset.getProperty(attributeName);
	    		
			}
		} else {
			// computed or regular attribute.
			dsProperty = dataset.getProperty(attributeName);
		}

		
		if (dsProperty instanceof MdfDatasetMappedProperty) {
			
//			MdfDatasetMappedProperty dsmProperty = (MdfDatasetMappedProperty) dsProperty;
//			PathResolver resolver = new PathResolver(dsmProperty.getParentDataset());
//			MdfProperty property = resolver.resolve(dsmProperty.getPath());			
			
			if (dsProperty!=null && dsProperty.getType() != null && !(dsProperty.getType() instanceof MdfEnumeration)) {
				// if it is not an enumeration, the property and the association
				// must have the specific annotations is present.
				
				MdfAnnotation annotation = getAnnotation(dsProperty, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION);
				if (annotation == null) {
					return false;
				}
				
				String value = getAnnotationProperty(annotation, SERVICES_PROPERTY_PERMITTED_VALUES);
				return value != null && Boolean.valueOf(value);			
			} 
			
		} else if (dsProperty instanceof MdfDatasetDerivedProperty) {
			// computed field based on enumeration is ok.
			MdfDatasetDerivedProperty property = (MdfDatasetDerivedProperty) dsProperty;
			if (property.getType() instanceof MdfEnumeration) {
				return true;
			}
			MdfAnnotation annotation = getAnnotation(dsProperty, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION);
			if (annotation == null) {
				return false;
			}
			String value = getAnnotationProperty(annotation, SERVICES_PROPERTY_PERMITTED_VALUES);
			return value != null && Boolean.valueOf(value);					
		}
		
		return true;
		
	}
    
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkComboboxDomainHasPermittedValues(Widget widget) {
		if (!WidgetTypeConstants.COMBOBOX.equals(widget.getTypeName())) {
			return true;
		}
			
		Resource res = widget.eResource();
		if (res == null) return true;
		
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(res);
		if (ofsProject == null) return true;
		
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		if (repository == null) return true;
		
		MdfModelElement element = widget.findDomainObject(repository);
		if (element == null) return true;

		
		if (element instanceof MdfDatasetMappedProperty) {
			
			MdfDatasetMappedProperty dsmProperty = (MdfDatasetMappedProperty) element;
			PathResolver resolver = new PathResolver(dsmProperty.getParentDataset());
			MdfProperty property = resolver.resolve(dsmProperty.getPath());			
			
			if (property!=null && property.getType() != null && !(property.getType() instanceof MdfEnumeration)) {
				// if it is not an enumeration, the property and the association
				// must have the specific annotations is present.
				
				MdfAnnotation annotation = getAnnotation(element, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION);
				if (annotation == null) {
					return false;
				}
				
				String value = getAnnotationProperty(annotation, SERVICES_PROPERTY_PERMITTED_VALUES);
				return value != null && Boolean.valueOf(value);			
			} 
			
		} else if (element instanceof MdfDatasetDerivedProperty) {
			// computed field based on enumeration is ok.
			MdfDatasetDerivedProperty property = (MdfDatasetDerivedProperty) element;
			if (property.getType() instanceof MdfEnumeration) {
				return true;
			}
			MdfAnnotation annotation = getAnnotation(element, SERVICES_NAMESPACE_URI, SERVICES_ANNOTATION);
			if (annotation == null) {
				return false;
			}
			String value = getAnnotationProperty(annotation, SERVICES_PROPERTY_PERMITTED_VALUES);
			return value != null && Boolean.valueOf(value);					
		}
		
		return true;
		
	}

	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkTableAtLeastOnColumnInFilter(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			if (!table.hasFilter()) {
				return true;
			}
			boolean hasDomainColumn = false;
			for (ITableColumn column : table.getColumns()) {
				if ("domain".equals(column.getColumnType().getValue())) {
					hasDomainColumn = true;
					break;
				}
			}
			if (hasDomainColumn) {
				for (ITableColumn column : table.getColumns()) {
					if (column.isBoundToDomain() && column.isPartOfTheFilter()) {
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param table
	 * @return boolean
	 */
	private static boolean isSummaryTreeView(ITable table) {
		ITableUtilities utils = TableHelper.getTableUtilities();
		return utils.isSummaryRenderingMode(table.getRenderingMode());
	}
	
	/**
	 * @param table
	 * @return
	 */
	private static Set<String> getUsedDomainAttributes(ITable table) {
		Set<String> domainAttributes = new TreeSet<String>();
		if (!isSummaryTreeView(table)) {
			// domain columns
			for (ITableColumn column : table.getColumns()) {
				String columnName = column.getColumnName();
				if (column.isBoundToDomain() && StringUtils.isNotEmpty(columnName)) {
					domainAttributes.add(columnName);
				}
			}
			// groups
			for (ITableGroup group : table.getGroups()) {
				String columnName = group.getColumnName();
				domainAttributes.add(columnName);
			}
			
			// sorts
			for (ITableSort sort : table.getSorts()) {
				String columnName = sort.getColumnName();
				domainAttributes.add(columnName);
			}
			
			// aggregates
			for (ITableAggregate aggregate : table.getAggregatedColumns()) {
				String aggColumn = aggregate.getColumnName();
				String otherColumnName = aggregate.getOtherColumnName();
				domainAttributes.add(aggColumn);
				domainAttributes.add(otherColumnName);
			}
			
			// extras
			for (ITableFeature extra : table.getExtras()) {
				String columnName = extra.getColumnName();
				domainAttributes.add(columnName);
			}
			
			//  computed columns
			for (ITableColumn column : table.getColumns()) {
				if (column.isComputed()) {
					for (String parameter : column.getParameters()) {
						domainAttributes.add(parameter);						
					}
				}
			}		
			
		}
		return domainAttributes;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkSortColumnExistsAsColumn(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			List<ITableGroup> groups = table.getGroups();
			for (ITableGroup group : groups) {
				String sortColumn = group.getSortingColumnName();
				Set<String> attrbs = getUsedDomainAttributes(table);
				if (!attrbs.isEmpty() && !attrbs.contains(sortColumn)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * only placeholder column should be marked as display-grouping 
	 * in case of "raw data" hierarchy
	 * @param widget
	 * @return
	 */
	public static boolean checkDisplayGroupingForPlaceHolder(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			List<ITableGroup> groups = table.getGroupsByRank();
			List<ITableColumn> columns = table.getColumns();			
			if(!groups.isEmpty()) {
				ITableGroup group = groups.iterator().next();
				if (group.isHierarchy() 
						&& !group.isAggregatedData() ) {
					for (ITableColumn column : columns) {
						if (column.isDisplayGrouping() 
								&& !column.isPlaceHolder()) {
							return false;
						}
					}
				}
			}
		}
		return true;		
	}
	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkDomainAttributesInConditionForPlaceHolder(Widget widget) {
		if(WidgetTypeConstants.CONDITIONAL_BODY.equals(widget.getTypeName())) {
			ITableColumn column = getTableColumn(widget.getParent().getParent());
			if (column != null && column.isPlaceHolder()) {
				for (Widget child : widget.getContents()) {
					String domattr = child.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
					if (!StringUtils.isEmpty(domattr)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * check if the raw data hierarchy is speicified for summary-detailed rendering mode table
	 * 
	 * @param widget
	 * @return
	 */
	public static boolean checkRenderingModeForHierarchyRawData(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			List<ITableGroup> groups = table.getGroupsByRank();
			if(!groups.isEmpty()) {
				ITableGroup group = groups.iterator().next();
				if (group.isHierarchy() 
						&& !group.isAggregatedData()) {
					boolean summary = TableHelper.getTableUtilities().isSummaryRenderingMode(
							table.getRenderingMode());
					if (summary) {
						return false;
					}
				}
			}
		}
		return true;		
	}
	
	/**
	 * no aggregates should be defined for raw data hierarchy grouping
	 * 
	 * @param widget
	 * @return
	 */
	public static boolean checkAggregatesForHierarchyRawData(Widget widget) {
		ITable table = getTable(widget);
		if (table != null) {
			List<ITableGroup> groups = table.getGroupsByRank();
			if(!groups.isEmpty()) {
				ITableGroup group = groups.iterator().next();
				if (group.isHierarchy() 
						&& !group.isAggregatedData() 
						&& !table.getAggregatedColumns().isEmpty()) {
					return false;
				}
			}
		}
		return true;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static int checkTableItemWidth(Widget widget) {
		ITableColumn column = getTableColumn(widget);
		if (column != null) {
			List<List<ITableColumnItem>> rows = parseItems(column.getItems());
			for (List<ITableColumnItem> list : rows) {
				if (!rowItemsWidthMatched(list)) {
					return rows.indexOf(list);
				}
			}
		}
		return -1;
	}
	
	/**
	 * @param list
	 * @return
	 */
	private static boolean rowItemsWidthMatched(List<ITableColumnItem> list) {
		int count = 0;
		boolean allEmpty = true;
		for (ITableColumnItem item : list) {
			String width = item.getItemPercentageWidth();
			if (!StringUtils.isEmpty(width)) {
				count +=item.getWidget().findProperty("item-width").getIntValue();
				allEmpty = false;
			}
		}
		if (!allEmpty && count != 100){
			return false;
		}
		return true;
	}
	
	/**
	 * @param widgets
	 * @param current
	 * @return
	 */
	private static List<List<ITableColumnItem>> parseItems(List<ITableColumnItem> items) {
		List<List<ITableColumnItem>> rows = new ArrayList<List<ITableColumnItem>>();
		int start = 0;		
		for (int i = 0; i < items.size(); ++i) {
			ITableColumnItem child = items.get(i);
			if (child.isNewLine()) {
				List<ITableColumnItem> slist = new ArrayList<ITableColumnItem>();
				slist.addAll(items.subList(start, i));
				rows.add(slist);	
				start = i;
			}
			if (i == items.size()-1) {
				List<ITableColumnItem> slist = new ArrayList<ITableColumnItem>();
				slist.addAll(items.subList(start, items.size()));
				rows.add(slist);	
			}
		}
		return rows;
	}
	
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkGroupsForHierarchyRawData(Widget columnWidget) {
	    if(columnWidget.getTypeName().equals("TableColumn")) {
		ITableColumn column = TableHelper.getTableColumn(columnWidget);
		if(column.isDisplayGrouping()) {
		    column.getGroups();
		    List<ITableGroup> groups = column.getGroups();
			if(!groups.isEmpty()) {
				ITableGroup group = groups.iterator().next();
				if (group.isHierarchy() 
						&& !group.isAggregatedData() 
						&& groups.size() > 1) {
					return false;
				}
			}
		}
	    }
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static boolean checkGridColumnTotalWidth(Widget widget) {
		if (widget.getTypeName().equals("Grid")) {
			GridAdapter grid = new GridAdapter(widget);
			int nbColumns = grid.getColumnCount();
			int totalWidths = 0;
			for (int cx = 0; cx < nbColumns; cx++) {
				totalWidths += grid.getColumnWidth(cx);
			}
			if (totalWidths > 100) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	public static int checkGridColumnsWidthNotSet(Widget widget) {
		int val = -1;
		if (widget.getTypeName().equals("Grid")) {
			GridAdapter grid = new GridAdapter(widget);
			int nbColumns = grid.getColumnCount();
			for (int cx = 0; cx < nbColumns; cx++) {
				int width = grid.getColumnWidth(cx);
				if (width <= 0) {
					return cx+1;
				}
			}
		}
		return val;
	}
	
	public static boolean checkTranslationId(Widget widget) {
		boolean valid = true;
		if (widget.getType().translationSupported()) {
			if (widget.getTranslationId() == 0) {
				valid = widget.getLabels().isEmpty() && widget.getToolTips().isEmpty();
			}
		}
		return valid;
	}

	public static ITableColumnItem getTableColumnItem(Widget widget) {
        ITableColumnItem item = null;
        if (widget.getTypeName().equals("TableColumnItem")) {
              item = TableHelper.getTableColumnItem(widget);
        }
        return item;
  }

	public static MdfDataset getDatasetFromWidget(Widget widget) {
		IOfsProject ofsProject = null;
		if (widget.eResource() != null)
			ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
		String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		if(datasetName.isEmpty()) {
			return null;
		}
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		return repository.getDataset(MdfNameFactory.createMdfName(datasetName));
	}

	public static boolean checkTableColumnType(Widget widget) {
		if(widget.getTypeName().equals("TableColumn")) {
			ITableColumn tableColumn = TableHelper.getTableColumn(widget);
			if(tableColumn.getGroups().size() > 0) {
				if(tableColumn.isDomain()) {
					return false;
				}
			}
		}
		return true;
	}

     public static boolean isDynamicGroup(Widget widget){
	 if(widget.getTypeName().equals("TableGroup")) {
	    Property pro= widget.findProperty(PropertyTypeConstants.GROUP_DYNAMIC_COLUMN);
	    if(pro!=null && pro.getBooleanValue()){
		return true;
	    }
	 }
	 return false;
     }
     
 	/**
 	 * @return the domain object provider
 	 */
     public static boolean checkGroupNameInTable(Widget widget,String applyOnGroup) {
		ITable table = getTable(widget);
		List<ITableGroup> groups = table.getGroups();
		List<String> groupNames = new ArrayList<String>();
		for (ITableGroup tablGroup : groups) {
			if (tablGroup.getWidget().findProperty("group-column-name") != null) {
				Property dom = tablGroup.getWidget().findProperty(
						"group-column-name");
				if (dom.getValue() != null
						&& dom.getValue().trim().length() > 0 ) {
					groupNames.add(dom.getValue().trim());
				}
			}
		}
		if(applyOnGroup.contains(",")){
			String[] split = applyOnGroup.split(",");
			List<String> chkBoxGrpList = new ArrayList<String>();
			for (String chkBoxName : Arrays.asList(split)) {
				chkBoxGrpList.add(chkBoxName.trim());
			}
			if(groupNames.containsAll(chkBoxGrpList)){
				return true;
			}
		}
		else if(groupNames.contains(applyOnGroup.trim())){
			return true;
		}

		return false;
 	}
 
}
