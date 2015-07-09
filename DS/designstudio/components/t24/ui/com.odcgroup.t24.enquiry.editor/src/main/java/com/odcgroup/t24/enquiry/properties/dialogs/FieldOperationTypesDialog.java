package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperation;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.properties.util.OperationEnum;
import com.odcgroup.t24.enquiry.properties.util.OperationPropertiesUtil;

/**
 * This Dialog class provides user option to view/create Operation for associated field and modify
 * its properties.
 * 
 * @author mumesh
 * 
 */
public class FieldOperationTypesDialog extends AbstractTypePropertiesDialog {
	
	private Field fieldModel;
	private EObject initialModel;
	private EObject model;

	/**
	 * @param parentShell
	 * @param model
	 * @param propertyType
	 * @param types
	 */
	public FieldOperationTypesDialog(Shell parentShell, Field model,
			String propertyType, String[] types) {
		super(parentShell, "", propertyType, types, true);
		this.fieldModel = model;
		this.initialModel = model.getOperation();
		this.model = model.getOperation();
	}

	@Override
	protected void updateCombo() {
		if(model == null){
			typeCombo.setText("None");
		} else {
			String[] items = typeCombo.getItems();
			for(String item: items){
				if(checkOperation(model, item)){
					typeCombo.setText(item);
				}
			}
		}
	}
	protected void updateModel() {
		String type = typeCombo.getText();
		if (model != null && checkOperation(model, type)) {
			return;
		} else if (initialModel != null && checkOperation(initialModel, type)) {
			model = initialModel;
		} else {
			String operationType = OperationEnum.getOperationEnum(type).name();
			model = (EObject) OperationPropertiesUtil.createOperation(operationType);
			if(model instanceof LabelOperation){
				fieldModel.setOperation((Operation) model);
			}
		}
		updateTable();
		updateSummary();
	}

	@Override
	protected void updateSummary() {
		summaryLabel.setText(OperationPropertiesUtil.getOperationSummary((Operation) model));
	}
	
	private boolean checkOperation(EObject model, String operationText){
		if(model.eClass().getName().equals(OperationEnum.getOperationEnum(operationText).name())){
			return true;
		}
		return false;
	}
	
	@Override
	protected String getCellEditorColumnText(Object element) {
		if (element instanceof EAttribute) {
			EAttribute attribute = (EAttribute) element;
			if (model instanceof CalcOperation) {
				CalcOperation calc = (CalcOperation) model;
				return OperationPropertiesUtil.getValueForCalcOperation(attribute, calc).toString();
			} else if (model.eGet(attribute) == null) {
				return "";
			} else {
				return model.eGet(attribute).toString();
			}
		} else if (element.equals("Translations") && model instanceof LabelOperation) {
			return OperationPropertiesUtil.getTranslationText((LabelOperation) model);
		}
		return "";
	}
	
	@Override
	protected void addLabelProviders() {
		tableColumnViewerProperty.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof EAttribute){
					EAttribute attribute = (EAttribute)element;
					String key = attribute.getName();
					if(model instanceof CalcOperation){
						if(key.equals("field")){
							return "First Field";
						}
						if(key.equals("operator")){
							return "Following Expression";
						}
					}
					else if(model instanceof DecisionOperation){
						if(key.equals("leftField")){
							return "First Operand";
						}
						if(key.equals("rightField")){
							return "Second Operand";
						}
						if(key.equals("operand")){
							return "Operator";
						}
						if(key.equals("firstField")){
							return "Assignment For True";
						}
						if(key.equals("secondField")){
							return "Assignment For False";
						}
					}
					if (key.length() == 0) return key;
			        return key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();
				}
				return super.getText(element);
			}
		});
		
		tableColumnViewerValue.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				updateSummary();
				return getCellEditorColumnText(element);
			}		
		});		

	}

	@Override
	protected void updateTable() {
		if(model != null){
			if(model instanceof LabelOperation){
				tableViewer.setInput(new String[]{"Translations"});
				tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer,(LabelOperation) model,fieldModel));
			} else {
				tableViewer.setInput(model.eClass().getEAllAttributes());
				tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer,model,fieldModel));
			}
		} else {
			tableViewer.setInput(null);
		}
	}
	
	public Operation getFieldOpertaion(){
		return (Operation) model;
	}

}
