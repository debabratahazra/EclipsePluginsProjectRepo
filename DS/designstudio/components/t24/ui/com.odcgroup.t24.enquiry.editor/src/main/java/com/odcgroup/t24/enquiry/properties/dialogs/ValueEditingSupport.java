package com.odcgroup.t24.enquiry.properties.dialogs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.DescriptorOperation;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.GetFromConversion;
import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.TotalOperation;
import com.odcgroup.t24.enquiry.properties.celleditors.BooleanCellEditor;
import com.odcgroup.t24.enquiry.properties.celleditors.ExtendedTextDialogCellEditor;
import com.odcgroup.t24.enquiry.properties.celleditors.IntegerCellEditor;
import com.odcgroup.t24.enquiry.properties.util.EnquiryDialogUtil;
import com.odcgroup.t24.enquiry.properties.util.OperationPropertiesUtil;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * 
 * @author mumesh
 * 
 */
public class ValueEditingSupport extends EditingSupport {

	private TableViewer viewer = null;
	private EObject model;
	private final static Logger logger = LoggerFactory.getLogger(ValueEditingSupport.class);
	private EObject parentModel;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	/**
	 * @param viewer
	 * @param model
	 * @param parentModel
	 */
	public ValueEditingSupport(TableViewer viewer, EObject model, EObject parentModel) {
		this(viewer, model, parentModel, null, null);
	}
	
	/**
	 * @param viewer
	 * @param model
	 * @param parentModel
	 * @param eObjectSearch
	 * @param globalDescriptionLabelProvider
	 */
	public ValueEditingSupport(TableViewer viewer, EObject model, EObject parentModel, 
			LanguageXtextEObjectSearch eObjectSearch, GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		super(viewer);
		this.viewer = viewer;
		this.model = model;
		this.parentModel = parentModel;
		this.eObjectSearch = eObjectSearch;
		if(eObjectSearch != null){
			this.eObjectSearch.setLanguageRepository(EnquiryUtil.getEnquiryRepository());
		}
		
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if (element instanceof EAttribute) {
			EAttribute obj = (EAttribute) element;
			if (obj.getEAttributeType().getName().equals("EInt")) {
				return new IntegerCellEditor(viewer.getTable());
			} else if (obj.getEAttributeType().getName().equals("EString")
					&& StringUtils.containsIgnoreCase(obj.getName(), "field")) {
				ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(viewer.getTable(), new LabelProvider(),false) {
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						String fileName =null;
						if(model instanceof Operation  && belongsToOperationWithEnquiryFields(model)){
							List<String> list = new ArrayList<String>();
							Enquiry enquiry = (Enquiry) parentModel.eContainer();
							return EnquiryFieldSelectionDialog.openEnquiryFieldsDialog(cellEditorWindow.getShell(), enquiry,
									"Enquiry Field Selection", "Select an Enquiry Field as Break Field", list, false);
						}
						if(model instanceof GetFromConversion){
							GetFromConversion getFromConversion = (GetFromConversion)model;
							if(getFromConversion.getApplication() != null && !getFromConversion.getApplication().isEmpty()){
								EReference ref = EcoreFactory.eINSTANCE.createEReference();
								   ref.setEType(MdfPackage.Literals.MDF_CLASS);
								fileName = EnquiryUtil.getDomainRepository().getApplicationQualifiedNameFromT24Name(parentModel.eContainer(),getFromConversion.getApplication(),ref);
							}
						}
						com.odcgroup.t24.enquiry.enquiry.Field field = (com.odcgroup.t24.enquiry.enquiry.Field) parentModel;
						com.odcgroup.t24.enquiry.enquiry.Field format = EnquiryFactory.eINSTANCE.createField();
						Operation operation = field.getOperation();
						if (operation instanceof BreakOnChangeOperation) {
							format.setName(((BreakOnChangeOperation) operation).getField());
						} else if(operation instanceof DescriptorOperation) {
							format.setName(((DescriptorOperation) operation).getField());
						} else if(operation instanceof ApplicationFieldNameOperation) {
							format.setName(((ApplicationFieldNameOperation) operation).getField());							
						} else if(operation instanceof FieldExtractOperation) {
							format.setName(((FieldExtractOperation) operation).getField());							
						} else if(operation instanceof SelectionOperation) {
							format.setName(((SelectionOperation) operation).getField());							
						} else if(operation instanceof TotalOperation) {
							format.setName(((TotalOperation) operation).getField());							
						}
						return EnquiryFieldSelectionDialog.openDialog(cellEditorWindow.getShell(), (Enquiry) parentModel.eContainer(), format, fileName, false);
					}

				};
				return dialog;
			} else if (obj.getEAttributeType().getName().equals("EString")
					&& StringUtils.containsIgnoreCase(obj.getName(), "application")) {
				ExtendedTextDialogCellEditor dialog = new ExtendedTextDialogCellEditor(viewer.getTable(), new LabelProvider(),false) {
						@Override
						protected Object openDialogBox(Control cellEditorWindow) {
							return EnquiryDialogUtil.selectApplicationName(cellEditorWindow.getShell(), false);
						}
					};
					return dialog;
			} else if (obj.getEAttributeType().getName().equals("EString") && obj.isMany()) {
				if (model instanceof CalcOperation && obj.getName().equals("operator")) {
					ExtendedDialogCellEditor cellEditor = new ExtendedDialogCellEditor(viewer.getTable(),
							new LabelProvider()) {
						@Override
						protected Object openDialogBox(Control cellEditorWindow) {
							List<String> operatorExpressions = new ArrayList<String>();
							CalcOperation calc = (CalcOperation) model;
							operatorExpressions = OperationPropertiesUtil.getCalcFieldOperatorExpression(calc);
							OperatorExpressionSelectionDialog dialog = new OperatorExpressionSelectionDialog(
									cellEditorWindow.getShell(), "Following Expression Selection", operatorExpressions,
									true, parentModel, model,"Operator And Field");
							int i = dialog.open();
							if (i == Dialog.OK) {
								return dialog.getSelectedOptions();
							}
							return null;
						}
					};
					return cellEditor;
				}

			} else if (obj.getEAttributeType().getName().equals("EString")) {
				return new TextCellEditor(viewer.getTable());
			} else if (obj.getEAttributeType().getName().equals("EBoolean")) {
				return new BooleanCellEditor(viewer.getTable());
			} else if (obj.getEAttributeType() instanceof EEnum) {
				Field values;
				try {
					values = obj.getEType().getInstanceClass().getField("VALUES");
					return new ExtendedComboBoxCellEditor(viewer.getTable(), (List<?>) values.get(null),
							new LabelProvider());
				} catch (SecurityException e) {
					logger.error(e.getMessage());
				} catch (NoSuchFieldException e) {
					logger.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				}
			}
		} else if(element.equals("Translations") && model instanceof LabelOperation){

			ExtendedDialogCellEditor cellEditor = new ExtendedDialogCellEditor(viewer.getTable(),
					new LabelProvider()) {
				@Override
				protected Object openDialogBox(Control cellEditorWindow) {
					LabelOperation labelOp = (LabelOperation) model;
					Shell shell = new Shell();
					try {
						TranslationPropertyDialog dialog = new TranslationPropertyDialog(shell/*viewer.getTable().getShell()*/, parentModel,labelOp);
						if (Dialog.OK == dialog.open()) {
							return dialog.getLabel();
						}
					} finally {
						shell.dispose();
					}
					return null;
				}
			};
			return cellEditor;
		
		} else if (element instanceof DrillDownType) {
			final DrillDownType ddtype = (DrillDownType) element;
			Enquiry enquiry = (Enquiry) parentModel.eContainer();
			ValueEditingSupportProvider supportProvider = new ValueEditingSupportProvider(eObjectSearch, globalDescriptionLabelProvider);
			return supportProvider.getDrillDownTypeCellEditor(ddtype, viewer.getTable(), enquiry);
		}
		return new TextCellEditor(viewer.getTable());
	}

	private boolean belongsToOperationWithEnquiryFields(EObject model) {
		if (model instanceof CalcOperation || model instanceof BreakOnChangeOperation || model instanceof SelectionOperation
				|| model instanceof TotalOperation || model instanceof FieldExtractOperation){
			return true;
		}
		return false;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof EAttribute) {
			EAttribute attribute = (EAttribute) element;
			if (model instanceof CalcOperation) {
				CalcOperation calc = (CalcOperation) model;
				return OperationPropertiesUtil.getValueForCalcOperation(attribute, calc);
			}
			else if (model.eGet(attribute) == null) {
				return "";
			} else {
				return model.eGet(attribute);
			}
		} else if(element.equals("Translations") && model instanceof LabelOperation){
			return OperationPropertiesUtil.getTranslationText((LabelOperation) model);
		} else if (element instanceof DrillDownType) {
			if (element instanceof DrillDownStringType) {
				return ((DrillDownStringType) element).getValue();
			} else {
				return ((BlankType) element).getValue()+"";
			}
		}
		return null;
	}
	
	
	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof EAttribute) {
			EAttribute obj = (EAttribute) element;
			if (value != null && value != model.eGet(obj)) {
				if (model instanceof CalcOperation) {
					CalcOperation calc = (CalcOperation) model;
					if (obj.getName().equals("field")) {
						if (calc.getField().size() > 0) {
							calc.getField().remove(0);
							calc.getField().add(0, (String) value);
						} else {
							calc.getField().add(0, (String) value);
						}
					}
					if (obj.getName().equals("operator")) {
						if(value instanceof List){
							List<String> expressionList = (ArrayList) value;
							calc.getOperator().clear();
							String firstField = calc.getField().get(0);
							calc.getField().clear();
							calc.getField().add(firstField);
							for (String expression : expressionList) {
								String[] array = StringUtils.split(expression, " ");
								calc.getOperator().add(array[0]);
								calc.getField().add(array[1]);
							}
						}
					}
				} else if (obj.getEAttributeType().getName().contains("EBoolean")) {
					model.eSet(obj, (Object) Boolean.valueOf((String) value));
				} else {
					model.eSet(obj, value);
				}
			}
		} else if (element instanceof DrillDownType) {
			if (value != null) {
				if (element instanceof DrillDownStringType) {
					DrillDownStringType stype = (DrillDownStringType) element;
					if (value != stype.getValue()) {
						stype.setValue((String) value);
					} 
				} else {
					BlankType btype = (BlankType) element;
					btype.setValue(Boolean.valueOf((String)value));
				}
			}			
		}
		viewer.update(element, null);
	}	

}
