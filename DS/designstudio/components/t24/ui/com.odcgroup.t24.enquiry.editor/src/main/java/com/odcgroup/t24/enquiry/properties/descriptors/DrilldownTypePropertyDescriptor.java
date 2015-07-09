package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.properties.dialogs.DrillDownTypesDialog;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * @author phanikumark
 *
 */
public class DrilldownTypePropertyDescriptor extends PropertyDescriptor {
	
	private DrillDown model;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	public DrilldownTypePropertyDescriptor(EObject model, LanguageXtextEObjectSearch eObjectSearch, 
			GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		super(EnquiryPackage.eINSTANCE.getDrillDown_Type(), "Drill Down Type and Resource");
		this.model = (DrillDown) model;
		this.eObjectSearch = eObjectSearch;
		this.eObjectSearch.setLanguageRepository(EnquiryUtil.getEnquiryRepository());
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
		setCategory("General:");
	}

	public CellEditor createPropertyEditor(Composite parent) {
		LabelProvider lableProvider = new LabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof DrillDownType){
					DrillDownType type = (DrillDownType) element;
					if (type != null) {
						String val = type.getProperty()+" ";
						if (type instanceof DrillDownStringType) {
							val += ((DrillDownStringType) type).getValue();
						} else {
							val += ((BlankType) type).getValue().toString();
						}
						return val;				
					}
				}
				return super.getText(element);
			}
		};
		ExtendedDialogCellEditor dilogCellEditor = new ExtendedDialogCellEditor(parent, lableProvider) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				DrillDownTypesDialog dialog = new DrillDownTypesDialog(cellEditorWindow.getShell(), model, 
						eObjectSearch, globalDescriptionLabelProvider);
				int i = dialog.open();
				if (i == Dialog.OK) {					
					return dialog.getDrillDownType();
				}
				return null;
			}
			
			@Override
			public void dispose() {
			}
		};
		return dilogCellEditor;
	}

}
