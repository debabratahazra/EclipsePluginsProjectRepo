package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.properties.util.DrilldownEnum;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;


/**
 * This Dialog class provides user option to view/create Operation for associated field and modify
 * its properties.
 * 
 * @author phanikumark
 * 
 */
public class DrillDownTypesDialog extends AbstractTypePropertiesDialog {
	

	private static String[] drilldownTypes = DrilldownEnum.getDrilldownNames().toArray(new String[0]);	
	private DrillDown model;
	private DrillDownType ddtype;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	/**
	 * @param parentShell
	 * @param dialogName
	 */
	public DrillDownTypesDialog(Shell parentShell, DrillDown model, LanguageXtextEObjectSearch eObjectSearch, 
			GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		super(parentShell, "Drilldown Type Selection", "Drilldown", drilldownTypes, false);
		this.model = model;
		this.ddtype = model.getType();
		if (ddtype == null) {
			ddtype = EnquiryFactory.eINSTANCE.createDrillDownType();
			model.setType(ddtype);
		}
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}

	@Override
	protected void updateModel() {
		String type = typeCombo.getText();
		if (!type.equals(DrilldownEnum.getDrilldownName(ddtype.getProperty()))) {
			ddtype = DrilldownEnum.getDrillDownType(type);
			ddtype.setProperty(DrilldownEnum.getDrilldownValue(type));
			if (ddtype instanceof DrillDownStringType) {
				((DrillDownStringType) ddtype).setValue("");
			} else if (ddtype instanceof BlankType){
				((BlankType) ddtype).setValue(false);
			}
		}
		updateTable();
	}

	@Override
	protected void updateTable() {
		if(ddtype != null){
			List<Object> list = new ArrayList<Object>();
			list.add(ddtype);
			tableViewer.setInput(list);
			tableColumnViewerValue.setEditingSupport(new ValueEditingSupport(tableViewer, ddtype, model, 
					eObjectSearch, globalDescriptionLabelProvider));
		} else {
			tableViewer.setInput(null);
		}		
	}

	@Override
	protected void updateSummary() {		
	}
	
	@Override
	protected String getCellEditorColumnText(Object element) {
		if (element instanceof DrillDownStringType){
			return ((DrillDownStringType) element).getValue();
		} else if (element instanceof BlankType) {
			return ((BlankType) element).getValue().toString();			
		}
		return "";
	}
	
	@Override
	protected void addLabelProviders() {
		tableColumnViewerProperty.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof DrillDownType){
					return ((DrillDownType) element).getProperty();
				}
				return super.getText(element);
			}
		});
		
		tableColumnViewerValue.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return getCellEditorColumnText(element);
			}		
		});		
	}

	@Override
	protected void updateCombo() {
		if(ddtype == null){
			typeCombo.setText("None");
		} else {
			String[] items = typeCombo.getItems();
			String dtype = ddtype.getProperty();
			for(String item: items){
				if(item.equals(DrilldownEnum.getDrilldownName(dtype))){
					typeCombo.setText(item);
				}
			}
		}		
	}
	
	public DrillDownType getDrillDownType() {
		return ddtype;
	}
	
}
