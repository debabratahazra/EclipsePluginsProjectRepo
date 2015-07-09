package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.google.common.collect.Lists;
import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.t24.enquiry.enquiry.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.EnquiryType;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.ScreenType;
import com.odcgroup.t24.enquiry.enquiry.TabbedScreenType;
import com.odcgroup.t24.enquiry.properties.celleditors.BooleanCellEditor;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.dialogs.ResourceSelectionDialogCreator.XtextResourceSelectionDialog;
import com.odcgroup.t24.enquiry.properties.util.EnquiryDialogUtil;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 *
 * @author phanikumark
 *
 */
public class ValueEditingSupportProvider {
	
	private static final Logger logger = Logger.getLogger(ValueEditingSupportProvider.class);

	private static final String VERSION = "Version";
	private static final String ENQUIRY = "Enquiry";
	private static final String COMPOSITE_SCREEN = "CompositeScreen";
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;
	
	
	/**
	 * @param eObjectSearch
	 * @param globalDescriptionLabelProvider
	 */
	public ValueEditingSupportProvider(LanguageXtextEObjectSearch eObjectSearch, GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}
	
	/**
	 * returns the suitable CellEditor based on the type
	 * 
	 * @param ddtype
	 * @param composite
	 * @param enquiry
	 * @return
	 */
	public CellEditor getDrillDownTypeCellEditor(final DrillDownType ddtype, Composite composite, final Enquiry enquiry) {
		if (ddtype instanceof ApplicationType || ddtype instanceof ScreenType 
				|| ddtype instanceof EnquiryType || ddtype instanceof CompositeScreenType 
				|| ddtype instanceof FromFieldType || ddtype instanceof TabbedScreenType) {
			ExtendedTextDialogCellEditor cellEditor = new ExtendedTextDialogCellEditor(composite,
					new LabelProvider(),false) {
				@Override
				protected Object openDialogBox(Control cellEditorWindow) {
					Shell shell = cellEditorWindow.getShell();
					if (ddtype instanceof ApplicationType) {
						return EnquiryDialogUtil.selectApplicationName(shell, false);
					} else if (ddtype instanceof ScreenType) {
						String qname = handleResourceSelection(shell, VERSION);
						if (qname != null) {
							qname = qname.replace('_', '.');
						}
						return qname;

					} else if (ddtype instanceof EnquiryType) {
						return handleResourceSelection(shell, ENQUIRY);
					} else if (ddtype instanceof CompositeScreenType || ddtype instanceof TabbedScreenType) {
						return handleResourceSelection(shell, COMPOSITE_SCREEN);						
					} else if (ddtype instanceof FromFieldType) {
						String val = ((FromFieldType) ddtype).getValue();
						List<String> list = Lists.newArrayList(val);
						return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(shell, enquiry, "Enquiry Field Selection", 
								"Select an Enquiry Field", list, false);					
					} else {
						return this.doGetValue();
					}
				}
				
			};
			return cellEditor;				
		} else if (ddtype instanceof BlankType) {
			return new BooleanCellEditor(composite);
		}
		return new TextCellEditor(composite);
	}
	
	/**
	 * returns the qualifiedName of the selected resource from the selection dialog
	 */
	private IEObjectDescription getSelectedResourceQName(Shell shell, String type, ResourceSelectionFilter filter) {
		ResourceSelectionDialogCreator creator = new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, type, filter);
		XtextResourceSelectionDialog dialog = creator.createDialog(shell);
		if (dialog.open() ==  Dialog.OK) {
			Object obj = dialog.getSelection();
			if (obj != null && obj instanceof IEObjectDescription) {
				return (IEObjectDescription) obj;
			}
		}
		return null;	
	}
	
	/**
	 * handling of resource selection dialog
	 */
	public String handleResourceSelection(Shell shell, String type) {
		if (type.equals(ENQUIRY)) {
			setEnquiryReference();
		} else if (type.equals(VERSION)) {
			setVersionReference();
		} else if(type.equals(COMPOSITE_SCREEN)){
			setComponentReference();
		} else {
			logger.warn("No ERefernce set for EObjectSearch for the selection type: " + type);
		}
		IEObjectDescription objDesc = getSelectedResourceQName(shell, type, null);
		if (objDesc != null) {
			return objDesc.getName().toString();
		}
		return null;
	}
	
	private void setEnquiryReference(){
		EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setEType(EnquiryPackage.Literals.ENQUIRY);
		eObjectSearch.setReference(ref);
	}
	
	private void setVersionReference(){
		EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setEType(VersionDSLPackage.Literals.VERSION);
		eObjectSearch.setReference(ref);
	}
	
	private void setComponentReference(){
		EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setEType(T24UIPackage.Literals.COMPOSITE_SCREEN);
		eObjectSearch.setReference(ref);
	}

}