package com.odcgroup.t24.enquiry.properties.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.editor.ui.dialogs.MdfPropertySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.ui.util.FieldHelper;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeContentProvider;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeLabelProvider;

/**
 * @author satishnangi
 *
 */
public class EnquiryFieldSelectionDialog {   
	
	public static Object openDialog(Shell shell, EObject model, String fileName, boolean multiSelection) {
		Enquiry enquiry = getEnquiryFromField(model);
		Field field = null;
		if (model instanceof Field) {
			field = (Field) model;
		} else if (model instanceof FixedSelection) {
			FixedSelection sel = (FixedSelection) model;
			field = EnquiryFactory.eINSTANCE.createField();
			field.setName(sel.getField());
		} else if (model instanceof Selection) {
			Selection sel = (Selection) model;
			field = EnquiryFactory.eINSTANCE.createField();
			field.setName(sel.getField());
		}
		return openDialog(shell, enquiry, field, fileName, multiSelection);
	}
	
	public static Object openDialog(Shell shell, Enquiry enquiry, String fileName, boolean multiSelection) {
		return openDialog(shell, enquiry, null, fileName, multiSelection);
	}

	public static Object openDialog(Shell shell, Enquiry enquiry, Field selection, String fileName, boolean multiSelection) {		
		MdfPropertySelectionDialog dialog = new MdfPropertySelectionDialog(shell, multiSelection);
		MdfClass klass = getMdfClass(fileName, enquiry);
		dialog.setLabelProvider(new ApplicationTreeLabelProvider(klass));
		dialog.setContentProvider(new ApplicationTreeContentProvider());
		if (klass != null) {
			Resource applicationResource = ((EObject) klass).eResource();
			List<Field> list = enquiry.getFields();
			List<MdfProperty> properties = new ArrayList<MdfProperty>();
			dialog.setInput(klass);
			if (!multiSelection & selection != null) {
				properties.add(FieldHelper.getMdfProperty(selection, klass));
			} else if(multiSelection){
				for (Field field : list) {
					MdfProperty prop = FieldHelper.getMdfProperty(field, klass);
					if (prop != null) {
						properties.add(prop);
					} else {
						MdfAttributeImpl attr = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
						attr.setName(field.getName());
						properties.add(attr);
					}
				}
			}
			dialog.setSelectedFields(properties);
			if (applicationResource != null) {
				int i = dialog.open();
				if (i == Dialog.OK) {
					if (dialog.getResult() != null && dialog.getResult().length > 0) {
						Object[] objects = dialog.getResult();
						if (!multiSelection && objects.length == 1) {
							MdfProperty property = (MdfProperty) objects[0];
							return FieldHelper.getFieldName(property);
						} 
						return dialog.getResult();
					}
				} else if (i == MdfPropertySelectionDialog.ADD) {
					return dialog.getNewFieldName();
				}
			}
		} else {
			MessageDialog mDialog = new MessageDialog(null, "Missing Application for this Enquiry", null,
					"The Application for the Enquiry does not exists!", MessageDialog.ERROR, new String[] { "OK" }, 0);
			mDialog.open();
		}
		return null;
	}

	private static MdfClass getMdfClass(String fileName, Enquiry enquiry) {
		if (enquiry != null && fileName == null) {
			fileName = enquiry.getFileName();
		}
		if(fileName == null) {
			// Application not set with Enquiry
			return null;
		}
		MdfClass klass = EnquiryUtil.getEnquiryApplication(fileName, enquiry);
		return klass;
	}


	private static Enquiry getEnquiryFromField(EObject model) {
		Resource modelResource = model.eResource();
		Enquiry enquiry = null;
		if (modelResource != null && !modelResource.getContents().isEmpty()) {
			EObject object = modelResource.getContents().get(0);
			if (object instanceof Enquiry) {
				enquiry = (Enquiry) object;
			}
		}
		return enquiry;
	}
	
	
	/**
	 * opens the dialog with the list of enquiry field names
	 * 
	 * @param shell
	 * @param enquiry
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Object openEnquiryFieldsDialog(Shell shell, Enquiry enquiry, String title, String msg, List<String> initialSelection, boolean multiSelection) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Field) {
					return ((Field) element).getName();
				}
				return super.getText(element);
			}					
			
		});
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setMultipleSelection(multiSelection);
		List<String> fieldNames = new ArrayList<String>();
		for(Field field : enquiry.getFields()) {
			fieldNames.add(field.getName());
		}
		dialog.setElements(fieldNames.toArray(new String[0]));
		dialog.setInitialElementSelections(initialSelection);
		int i = dialog.open();
		if (i == Dialog.OK) {
			Object[] result = dialog.getResult();
			if (result != null && result.length > 0) {
				if (!multiSelection) {
					return (String) result[0];
				} else {
					return StringUtils.join(result, ',');
				}
			}
		}
		return null;
	}
	
}
