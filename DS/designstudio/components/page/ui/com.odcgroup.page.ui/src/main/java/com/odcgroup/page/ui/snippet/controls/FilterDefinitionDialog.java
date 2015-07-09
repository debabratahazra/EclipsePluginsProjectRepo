package com.odcgroup.page.ui.snippet.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.util.FilterUtil;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.metamodel.util.OperatorTypeRegistry.Operator;
import com.odcgroup.page.metamodel.util.OperatorTypeRegistry.Type;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.DatasetAttribute;
import com.odcgroup.page.model.util.SearchDomainObjectUtil;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.page.ui.properties.table.controls.ITypeCombo;
import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * dialog to manage filter in a filterset
 * 
 * @author pkk
 */
public class FilterDefinitionDialog extends TableTransformDialog implements SelectionListener, FocusListener {
	/** */
	private IFilterSet filterSet;
	/** */
	private IFilter filter;
	/** */
	private ITypeCombo<String> attributeCombo;
	/** */
	private DataTypeCombo operatorCombo;
	/** */
	private DataTypeCombo editModeCombo;
	/** */	
	private List<DatasetAttribute> domainAttributes;
	/** */
	private Text valueTwo;
	/** */
	private Label valueOneTip;
	/** */
	private Font valueOneTipFont;
	/** */
	private Label valueTwoTip;
	/** */
	private Font valueTwoTipFont;
	/** */
	private Composite valueComp;
	/** */
	private FilterValueControl valueControl;
	/** */
	private Composite controlBody;
	/** */
	private boolean editModeEnabled = true;
	/** */
	private boolean operatorEnabled = true;
	/** */
	private boolean attributeEnabled = true;

	/**
	 * @param parentShell
	 * @param filterSet 
	 * @param dataset 
	 */
	public FilterDefinitionDialog(Shell parentShell, IFilterSet filterSet, String dataset) {
		super(parentShell, null);
		this.filterSet = filterSet;
		if (filterSet != null) {			
			EObject eObj = filterSet.getSnippet().eContainer();
			String[] mappings = null;
			if (eObj instanceof Event) {
				IQuery query = SnippetUtil.getQuery((Event) eObj);
				mappings = query != null ? query.getMappings() : null;
			}
			domainAttributes = SearchDomainObjectUtil.getDomainAttributes(dataset, mappings, getOfsProject());
		}
	}
	
	/**
	 * @param parentShell
	 * @param filterSet
	 * @param filter
	 * @param dataset 
	 */
	public FilterDefinitionDialog(Shell parentShell, IFilterSet filterSet, IFilter filter, String dataset) {
		this(parentShell, filterSet, dataset);
		if (filter != null) {
			setEditMode(true);
			this.filter = filter;
		}
	}
	
	/** 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Filter");
		setMessage("Filter definition");
	}

	/** 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Filter");
	}

	/** 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("unchecked")
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		composite.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);
		
		Group group = new Group(composite, SWT.FILL);
		gridLayout = new GridLayout(2, false);
		group.setLayout(gridLayout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));		
		
		// attribute comboBox
		new Label(group, SWT.NONE).setText("Attribute");
		List<String> attributes = new ArrayList<String>();
		if(domainAttributes != null) {
			attributes.addAll(getDomainAttributes(domainAttributes));
		}
		attributeCombo = new StringValueCombo(group, attributes, Collections.EMPTY_LIST, isEditMode(), true);		
		attributeCombo.setEnabled(attributeEnabled);
		
		// operator comboBox
		new Label(group, SWT.NONE).setText("Operator");
		operatorCombo = new DataTypeCombo(group, isEditMode());	
		operatorCombo.setEnabled(operatorEnabled);
		
		// editMode comboBox
		new Label(group, SWT.NONE).setText("Edit Mode");
		DataType type = findDataType("EditMode");
		editModeCombo = new DataTypeCombo(group, type, isEditMode());	
		editModeCombo.setEnabled(editModeEnabled);

		// value control
		new Label(group, SWT.NONE).setText("Value1");
		valueComp = new Composite(group, SWT.FILL);
		gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		valueComp.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		valueComp.setLayoutData(gd);		
		
		// value toolTip
		new Label(group, SWT.NONE);
		valueOneTip = new Label(group, SWT.NONE);
		valueOneTip.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		valueOneTipFont = new Font(Display.getCurrent(), "Arial", 8, SWT.ITALIC);
		valueOneTip.setFont(valueOneTipFont);
		valueOneTip.setEnabled(true);
		
		// value2 text
		new Label(group, SWT.NONE).setText("Value2");
		valueTwo = new Text(group, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		valueTwo.setLayoutData(gd);
		valueTwo.setEnabled(false);
		
		// value2 toolTip
		new Label(group, SWT.NONE);
		valueTwoTip = new Label(group, SWT.NONE);
		valueTwoTip.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		valueTwoTipFont = new Font(Display.getCurrent(), "Arial", 8, SWT.ITALIC);
		valueTwoTip.setFont(valueTwoTipFont);
		valueTwoTip.setEnabled(true);
		
		
		if (isEditMode()) {
			initInput();
		} else {
			filter = SnippetUtil.getSnippetFactory().createFilter(filterSet);
			createSimpleValueComposite();
		}
		
		// add selection listeners
		attributeCombo.addSelectionListener(this);
		operatorCombo.addSelectionListener(this);
		editModeCombo.addSelectionListener(this);
		valueTwo.addFocusListener(this);
		
		return composite;
	}
	
	/**
	 * @param attributes
	 * @return array
	 */
	private List<String> getDomainAttributes(List<DatasetAttribute> attributes) {
		List<String> names = new ArrayList<String>();
		for (DatasetAttribute datasetAttribute : attributes) {
			names.add(datasetAttribute.getDisplayName());
		}
		return names;
	}
	
	/**
	 * @return ofsProject
	 */
	private IOfsProject getOfsProject() {
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(filterSet.getSnippet().eResource());
		if (ofsProject == null) {
			ofsProject = EclipseUtils.findCurrentProject();
		}
		return ofsProject;
	}
	
	/**
	 * @param body
	 */
	private void createControlBody(Composite body) {
		if (controlBody != null) {
			controlBody.dispose();
			controlBody = null;
		}
		controlBody = new Composite(body, SWT.FILL);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 2;
		gridLayout.verticalSpacing = 2;
		gridLayout.marginHeight = 3;
		gridLayout.marginWidth = 2;
		controlBody.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		controlBody.setLayoutData(gridData);
	}
	
	/**
	 * set the values to the controls
	 */
	private void initInput() {
		
		attributeCombo.select(filter.getDatasetAttributeName());
		MdfEntity entity = getDomainAttributeType(filter.getDatasetAttributeName());
		updateOtherControls(entity);
		
		String value = filter.getOtherValue();
		if (!StringUtils.isEmpty(value))
			valueTwo.setText(value);
		editModeCombo.select(filter.getEditMode());
	}
	
	/**
	 * @param entity 
	 * 
	 */
	private void updateOtherControls(MdfEntity entity) {
		if (entity != null) {
			PropertyType type = MetaModelRegistry.findOperatorPropertyTypeFor(entity);
			operatorCombo.setType(type.getDataType());
			operatorCombo.select(filter.getOperator());
			
			adaptValueComposite(entity);		
		}
		
	}
	
	/**
	 * @param isDateAttribute 
	 * 
	 */
	private void enableValueTwo(boolean isDateAttribute) {
		boolean enableVal = true;
		if (isMappedAttribute() || isNullOperator()) {
			enableVal = false;
		}
		valueControl.enableControl(enableVal);
		
		enableVal = false;
		DataValue operator = operatorCombo.getSelectedValue();
		if (isDateAttribute) {	
			if (operator != null && (Operator.BETWEEN.name().equals(operator.getValue()) 
				|| Operator.NOT_BETWEEN.name().equals(operator.getValue()))) {
				enableVal = true;
			}
		}	
		if (!enableVal) {
			valueTwo.setText("");
		}		
		valueTwo.setEditable(enableVal);
		valueTwo.setEnabled(enableVal);
	}
	
	/**
	 * adapts the value control by selected attribute
	 * 
	 * @param entity
	 */
	private void adaptValueComposite(MdfEntity entity) {
		boolean enableBrowse = false;	
		// enumeration types require a special control
		// comboBox for single, browse for multiple (depends on operator)
		if (isEnumeration(entity)) {
			DataValue operator = operatorCombo.getSelectedValue();
			if (operator == null) {
				return;
			}
			if (operator != null && (Operator.IN.name().equals(operator.getValue()) 
					|| Operator.NOT_IN.name().equals(operator.getValue()))) {
				enableBrowse = true;
			}		
			createControlBody(valueComp);
			String prevVal = null;
			if (filter != null && filter.getValue() != null) {
				prevVal = filter.getValue();
			} 
			valueControl = new FilterValueControl(controlBody, enableBrowse, 
					getEnumerationValues((MdfEnumeration) entity), prevVal) {
				public void valueWidgetSelected(Widget widget) {
					filter.setValue(getValue());
					validChanges();					
				}
				public void valueModified(Widget widget) {
					validChanges();
				}				
			};
			valueComp.layout();
		} else {
			// a simple textBox
			createSimpleValueComposite();
			
		}
		// enable/disable value2 control by attribute
		enableValueTwo(isDateAttribute(entity));
		
		// set the tooltip for value controls
		String msg = getValueTooltip(attributeCombo.getSelectedValue(), null);
		if (msg != null) {
			valueOneTip.setText(msg);
			valueOneTip.redraw();
			if (valueTwo.isEnabled()) {
				valueTwoTip.setText(msg);
				valueTwoTip.redraw();
			}
		}

	}
	
	/**
	 * a simple value composite with a textField
	 */
	private void createSimpleValueComposite() {
		createControlBody(valueComp);
		String prevVal = null;
		if (filter != null && filter.getValue() != null) {
			prevVal = filter.getValue();
		} 
		valueControl = new FilterValueControl(controlBody, prevVal) {
			public void valueWidgetSelected(Widget widget) {
				filter.setValue(getValue());
				validChanges();
			}			
			public void valueModified(Widget widget) {
				validChanges();
			}				
		};
		valueComp.layout();
	}	
	
	
	/**
	 * @param enumeration
	 * @return enumVals
	 */
	private List<String> getEnumerationValues(MdfEnumeration enumeration) {
		List<?> values = enumeration.getValues();
		List<String> enums = new ArrayList<String>();
		for (Object obj : values) {
			MdfEnumValue val = (MdfEnumValue) obj;
			enums.add(val.getName());
		}
		return enums;
	}


	/**
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent event) {

		String attribute = attributeCombo.getSelectedValue();
		MdfEntity entity = getDomainAttributeType(attribute);
		if (event.widget == attributeCombo.getCombo()) {
			updateOtherControls(entity);
		} else if (event.widget == operatorCombo.getCombo()) {
			adaptValueComposite(entity);
			String msg = getValueTooltip(attribute, operatorCombo.getSelectedValue().getValue());
			if (msg != null) {
				valueOneTip.setText(msg);
				valueOneTip.redraw();
			}
		}
		validChanges();
		super.widgetSelected(event);
	}	
	
	/**
	 * @param entity
	 * @return boolean
	 */
	private boolean isEnumeration(MdfEntity entity) {
		if (entity instanceof MdfEnumeration) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param entity
	 * @return boolean
	 */
	private boolean isDateAttribute(MdfEntity entity) {
		MdfPrimitive primitive = null;
		if (entity instanceof MdfBusinessType) {
			primitive = ((MdfBusinessType) entity).getType();
		} else if (entity.isPrimitiveType()) {
			primitive = (MdfPrimitive) entity;			
		}
		if (primitive != null) {
			if (primitive.equals(PrimitivesDomain.DATE) || primitive.equals(PrimitivesDomain.DATE_TIME)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param attribute
	 * @param operator 
	 * @return string
	 */
	private String getValueTooltip(String attribute, String operator) {
		if (isNullOperator() || isMappedAttribute()) {
			return null;
		}
		MdfEntity entity = getDomainAttributeType(attribute);
		Type operatorType = FilterUtil.getOperatorKeyType(entity);
		if (Type.STRING == operatorType) {
			if (operator != null) {
				return "Meta-character is %";
			}
		} else if (Type.NUMBER ==operatorType) {
			return "Supports [0..9] with No thousand separator. Decimal separator is “.”";
		} else if (Type.FLAG == operatorType) {
			return "Supports [true,false] only";
		} else if (Type.DATE == operatorType) {
			return "Date format: (YYYY-MM-DDTHH:MM:SSZ)";
		} else  {
			return "";
		}
		return null;
	}
	
	/**
	 * @return isMappedAttribute
	 */
	private boolean isMappedAttribute() {
		String selection = attributeCombo.getSelectedValue();
		for (DatasetAttribute attribute : domainAttributes) {
			if (attribute.getDisplayName().equals(selection)) {
				return attribute.isMapped();
			}
		}
		return false;
	}
	
	/**
	 * @param name
	 * @return mdfEntity
	 */
	private MdfEntity getDomainAttributeType(String name) {
		for (DatasetAttribute attribute : domainAttributes) {
			if (attribute.getDisplayName().equals(name)) {
				return attribute.getType();
			}
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#getCommand()
	 */
	protected BaseCommand getCommand() {
		return null;
	}
	
	
	
	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	@Override
	public boolean close() {
		if (valueOneTipFont != null) {
			valueOneTipFont.dispose();
		}
		if (valueTwoTipFont != null) {
			valueTwoTipFont.dispose();
		}
		return super.close();
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#okPressed()
	 */
	protected final void okPressed() {		
		filter.setDatasetAttribute(attributeCombo.getSelectedValue());
		filter.setOperator(operatorCombo.getSelectedValue().getValue());
		filter.setValue(valueControl.getValue());
		if (valueTwo.isEnabled()) {
			filter.setOtherValue(valueTwo.getText());
		} else {
			filter.setOtherValue("");
		}
		filter.setEditMode(editModeCombo.getSelectedValue().getValue());		
		filterSet.addFilter(filter);
		super.okPressed();
	}
	
	/**
	 * @return boolean
	 */
	private boolean isNullOperator() {
		DataValue operator = operatorCombo.getSelectedValue();
		if (operator != null && (Operator.IS_NULL.name().equals(operator.getValue()) 
				|| Operator.IS_NOT_NULL.name().equals(operator.getValue()))) {
			return true;
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog#validChanges()
	 */
	protected boolean validChanges() {
		boolean retVal = false;
		if (attributeCombo.getSelectedValue() == null) {
			setErrorMessage("Attribute is required");
			return false;
		}

		if (operatorCombo.getSelectedValue() == null) {
			setErrorMessage("Operator is required");
			return false;			
		}
		
		if (editModeCombo.getSelectedValue() == null) {
			setErrorMessage("Edit mode is required");
			return false;			
		}
		
		DataValue editMode = editModeCombo.getSelectedValue();
		
		String value = valueControl.getValue();
		
		if ((editMode.getValue().equals("editable") && StringUtils.isEmpty(value)) 
				|| isNullOperator() 
				|| isMappedAttribute()) {
			retVal = true;
		} 
		
		if (!retVal && StringUtils.isEmpty(value)) {
			setErrorMessage("Please specify Value");
			retVal = false;
		} 
		
		if (!StringUtils.isEmpty(value)) {
			if (valueControl.isEnabled()) {
				retVal = isValidValue(attributeCombo.getSelectedValue(), value, 1);
			}
			if (retVal && valueTwo.isEnabled()) {
				retVal = isValidValue(attributeCombo.getSelectedValue(), valueTwo.getText(), 2);
			}
		}
		if (retVal) {
			setErrorMessage(null);
			this.getButton(OK).setEnabled(true);
		} else {
			this.getButton(OK).setEnabled(false);
		}
		return retVal;
	}
	
	/**
	 * @param attribute
	 * @param value 
	 * @param valueType 
	 * @return boolean
	 */
	private boolean isValidValue(String attribute, String value, int valueType) {
		//removed this validation to fix DS-4707 . refer file version 49668
		//the same validation logic can be used  while implementing A new UI for filter setting.
		//TODO 
		
			return true;
		}
	
	/**
	 * @param name
	 * @return datatype
	 */
	private DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		//do nothing
	}

	/**
	 * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		validChanges();		
	}
	
	
	/**
	 * Enables the field "EditMode" if the argument is <code>true</code>,
     * and disables it otherwise.
     * 
	 * @param enabled the new enabled state
	 */
	public void setEditModeEnabled(boolean enabled) {
		editModeEnabled = enabled;
	}
	
	/**
	 * Enables the field "Operator" if the argument is <code>true</code>,
     * and disables it otherwise.
     * 
	 * @param enabled the new enabled state
	 */
	public void setOperatorEnabled(boolean enabled) {
		operatorEnabled = enabled;
	}
	
	/**
	 * Enables the field "Attribute" if the argument is <code>true</code>,
     * and disables it otherwise.
     * 
	 * @param enabled the new enabled state
	 */
	public void setAttributeEnabled(boolean enabled) {
		attributeEnabled = enabled;
	}
	
}
