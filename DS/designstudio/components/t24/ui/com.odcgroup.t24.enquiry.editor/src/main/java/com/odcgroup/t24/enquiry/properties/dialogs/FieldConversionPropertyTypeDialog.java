package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.ConvertConversion;
import com.odcgroup.t24.enquiry.enquiry.ExtractConversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.ReplaceConversion;
import com.odcgroup.t24.enquiry.properties.util.ConversionEnum;
import com.odcgroup.t24.enquiry.properties.util.ConversionPropertiesUtil;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * @author satishnangi
 *
 */
public class FieldConversionPropertyTypeDialog extends AbstractFieldPropertyTypesDialog {

	private boolean isEditMode = false;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	
	/**
	 * constructor
	 * 
	 * @param parentShell
	 * @param parentModel
	 * @param conversion
	 * @param types
	 * @param mode
	 * @param eObjectSearch
	 * @param globalDescriptionLabelProvider
	 */
	public FieldConversionPropertyTypeDialog(Shell parentShell, Field parentModel, Conversion conversion,
			String[] types, boolean mode, LanguageXtextEObjectSearch eObjectSearch,
			GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		super(parentShell, parentModel, conversion, "Conversion", types);
		this.isEditMode = mode;
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
		
	}

	@Override
	protected void updateModel() {
		String operationText = typeCombo.getText();
		String conversionType = ConversionEnum.getConversionEnum(operationText).name();
		if (model != null && model.eClass().getName().equals(conversionType)) {
			return;
		} else if (initialModel != null && initialModel.eClass().getName().equals(conversionType)) {
			model = initialModel;
		} else {
			model = (EObject) ConversionPropertiesUtil.createConversion(conversionType);
		}
		updateTable();
		updateSummary();
	}

	@Override
	protected void updateSummary() {
           summaryLabel.setText(ConversionPropertiesUtil.getConversionSummary((Conversion)model));
	}
	
	protected void updateTable(){
		if(model instanceof CallRoutine){
			tableViewer.setInput(((CallRoutine) model).getRoutine().eClass().getEAllAttributes());
			tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer,((EObject)((CallRoutine) model).getRoutine()),fieldModel,eObjectSearch,globalDescriptionLabelProvider));
		}else {
			if(model != null){
				tableViewer.setInput(model.eClass().getEAllAttributes());
				tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer,model,fieldModel,eObjectSearch,globalDescriptionLabelProvider));
			} else {
				tableViewer.setInput(null);
			}
		}
	
	}
	
	@Override
	protected String getCellEditorColumnText(Object element) {
		if(model instanceof CallRoutine){
			return ((CallRoutine) model).getRoutine().getName();
		}
		return super.getCellEditorColumnText(element);
	}
	
	@Override
	protected void updateCombo() {
		if(model == null){
			typeCombo.setText("None");
		} else {
			String[] items = typeCombo.getItems();
			for(String item: items){
				if(checkConversion(model, item)){
					typeCombo.setText(item);
				}
			}
		}
		if(isEditMode){
			typeCombo.setEnabled(false);
		}
	}
	
	private boolean checkConversion(EObject model, String operationText){
		if(model.eClass().getName().equals(ConversionEnum.getConversionEnum(operationText).name())){
			return true;
		}
		return false;
	}

	@Override
	protected void addLabelProvider() {
		tableColumnViewerProperty.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof EAttribute){
					EAttribute attribute = (EAttribute)element;
					String key = attribute.getName();
					if(model instanceof ExtractConversion){
						if(key.equals("from")){
							return "From Field Number";
						}
						if(key.equals("to")){
							return "To Field Number";
						}
					}
					if(model instanceof ConvertConversion || model instanceof ReplaceConversion){
						if(key.equals("oldData")){
							return "Old Data";
						}
						if(key.equals("newData")){
							return "New Data";
						}
					}
					if (key.length() == 0) return key;
			        return key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();
				}
				return super.getText(element);
			}
		});
	}

	public Conversion openDilaog() {
		if (super.open() == Dialog.OK) {
			return (Conversion)model;
		}
		return null;
	}

}