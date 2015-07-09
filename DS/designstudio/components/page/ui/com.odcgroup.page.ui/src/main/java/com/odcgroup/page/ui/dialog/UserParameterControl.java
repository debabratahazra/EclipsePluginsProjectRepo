package com.odcgroup.page.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.ui.properties.table.controls.BasicListControl;
import com.odcgroup.page.ui.properties.table.controls.PropertyColumn;
import com.odcgroup.page.ui.properties.table.controls.ReferenceDefinitionDialog;
import com.odcgroup.page.ui.properties.table.controls.SpecialControl;

/**
 * User Parameter Control : add/modify/delete event user parameters
 *
 * @author pkk
 *
 */
public class UserParameterControl extends BasicListControl<Parameter, Event> {

	/**
	 * @param parent
	 * @param style
	 */
	public UserParameterControl(Composite parent, int style) {
		this(parent, style, false);
	}
	
	/**
	 * @param parent
	 * @param style
	 * @param enableCellEdit
	 */
	public UserParameterControl(Composite parent, int style, boolean enableCellEdit) {
		super(parent, style, false, enableCellEdit);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected SpecialControl<Event> createOtherControls(Composite parent) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Name", 30, 5, false));
		columns.add(new PropertyColumn("Value", 30, 5, false));			
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object, int)
	 */
	@Override
	public String getPropertyTableColumnText(Parameter element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getName();
			}  else {
				return element.getValue();
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public Parameter getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection == null) {
			return null;
		}
		return (Parameter)selection.getFirstElement();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public Parameter[] getTableElements(List<Parameter> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new Parameter[0]);
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<Parameter> getTableInput(Event input) {
		List<Parameter> userParam = new ArrayList<Parameter>();
		if (input != null) {
			List<Parameter> existingParameters = new ArrayList<Parameter>();
			existingParameters.addAll(input.getParameters());
			for (Parameter parameter : input.getParameters()) {
				if (parameter.isUserDefined()) {
					userParam.add(parameter);
				}
			}
		}
		return userParam;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#getPopupDialog(boolean)
	 */
	@Override
	protected ReferenceDefinitionDialog<Parameter> getPopupDialog(boolean edit) {
		if (edit) {
			return new UserParameterDefinitionDialog(getShell(), getSelection());
		} else {
			return new UserParameterDefinitionDialog(getShell());
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#addReference(java.lang.Object)
	 */
	@Override
	protected void addReference(Parameter reference) {
		getInput().getParameters().add(reference);		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#deleteReference(java.lang.Object)
	 */
	@Override
	protected void deleteReference(Parameter reference) {
		getInput().getParameters().remove(reference);
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#updateReference(java.lang.Object)
	 */
	@Override
	protected void updateReference(Parameter reference) {
		Parameter p = getSelection();
		if (p != null) {
			List<Parameter> parameters = getInput().getParameters();
			int ii = parameters.indexOf(p);
			if (ii > -1) {
				parameters.remove(ii);
				parameters.add(ii, reference);
			}
		}
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#createReference()
	 */
	@Override
	protected Parameter createReference() {
		Parameter parameter = ModelFactory.eINSTANCE.createParameter();
		parameter.setUserDefined(true);
		parameter.setName("");
		parameter.setValue("");
		return parameter;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#getCellValue(java.lang.Object, java.lang.String)
	 */
	public Object getCellValue(Parameter element, String property) {
		if (property.equals("Name")) {
			return element.getName();
		} else {
			return element.getValue();
		}
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.controls.BasicListControl#modifyCellValue(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modifyCellValue(Parameter element, String property, Object value) {
		String str = (String) value;
		if (property.equals("Name")) {
			element.setName(str);
		} else {
			element.setValue(str);
		}
	}

}
