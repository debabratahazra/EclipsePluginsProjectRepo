package com.odcgroup.page.ui.snippet.controls;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.util.DatasetAttribute;
import com.odcgroup.page.model.util.SearchDomainObjectUtil;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 *
 * @author pkk
 *
 */
public abstract class FilterListControl extends ListControl<IFilter, IFilterSet> {
	

	/**
	 * @param parent
	 * @param style
	 */
	public FilterListControl(Composite parent, int style) {
		super(parent, style, false);
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	protected SpecialControl<IFilterSet> createOtherControls(Composite parent) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object, java.lang.Object)
	 */
	protected BaseCommand getDeleteCommand(IFilterSet input, IFilter element) {
		input.removeFilter(element);
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object, java.lang.Object)
	 */
	protected BaseCommand getMoveDownCommand(IFilterSet input, IFilter element) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object, java.lang.Object)
	 */
	protected BaseCommand getMoveUpCommand(IFilterSet input, IFilter element) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	protected TableTransformDialog getPopupDialog(boolean edit) {
		if (edit) {
			return new FilterDefinitionDialog(getShell(), getInput(), getSelection(), getTargetDatasetName());
		} else {
			return new FilterDefinitionDialog(getShell(), getInput(), getTargetDatasetName());
		}	
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Id", 10, 1, true));
		columns.add(new PropertyColumn("Attribute", 20, 2, true));	
		columns.add(new PropertyColumn("Operator", 20, 2, true));	
		columns.add(new PropertyColumn("Value(1)", 20, 2, true));	
		columns.add(new PropertyColumn("Value(2)", 20, 2, true));	
		columns.add(new PropertyColumn("Mode", 10, 1, true));			
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	public String getPropertyTableColumnText(IFilter element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				List<Snippet> filters = element.getParent().getSnippet().getContents(); 
				int index = filters.indexOf(element.getSnippet());
				return (index+1)+"";
			} else if(columnIndex == 1) {
				return element.getDatasetAttributeName();
			} else if(columnIndex == 2) {
				String operator = getFilterOperatorName(element);
				return (operator != null) ? operator : element.getOperator();
			} else if(columnIndex == 3) {
				if (element.getValue() != null)
					return element.getValue();
			} else if(columnIndex == 4) {
				if (element.getOtherValue() != null)
					return  element.getOtherValue();
			} else if(columnIndex == 5) {
				return element.getEditMode();
			}
		}
		return "";
	}
	
	/**
	 * @param filter
	 * @return string
	 */
	private String getFilterOperatorName(IFilter filter) {
		MdfEntity entity = getFilterAttributeType(filter);
		if (entity == null) {
			return null;
		}
		PropertyType pType = MetaModelRegistry.findOperatorPropertyTypeFor(entity);
		List<DataValue> vals = pType.getDataType().getValues();
		for (DataValue dataValue : vals) {
			if (dataValue.getValue().equals(filter.getOperator())) {
				return dataValue.getDisplayName();
			}
		}
		return null;
	}
	
	/**
	 * @param filter
	 * @return entity
	 */
	private MdfEntity getFilterAttributeType(IFilter filter) {
		List<DatasetAttribute> attributes = SearchDomainObjectUtil.getDomainAttributes(getTargetDatasetName(), getOfsProject());
		for (DatasetAttribute mdfProperty : attributes) {
			if(mdfProperty.getDisplayName().equals(filter.getDatasetAttributeName())) {
				return mdfProperty.getType();
			}
		}
		return null;
	}
	
	

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	public IFilter getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (IFilter)selection.getFirstElement();
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	public IFilter[] getTableElements(List<IFilter> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new IFilter[0]);
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	public List<IFilter> getTableInput(IFilterSet input) {
		return input.getFilters();
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	public ViewerSorter getTableSorter() {
		return null;
	}
	
	/**
	 * @return ofsProject
	 */
	protected IOfsProject getOfsProject() {
		if (getInput() instanceof EObject) {
			Resource resource = ((EObject) getInput()).eResource();
			if (resource != null) {
				return OfsResourceHelper.getOfsProject(resource);
			}
		}
		return EclipseUtils.findCurrentProject();
	}
	
	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#refreshView(boolean)
	 */
	protected void refreshView(boolean reload) {
		super.refreshView(reload);
		if (StringUtils.isEmpty(getTargetDatasetName())) {
			propertyTable.getAddButton().setEnabled(false);
		} else {
			propertyTable.getAddButton().setEnabled(true);
		}
	}
	
	/**
	 * @return string
	 */
	public abstract String getTargetDatasetName();
	
}
