package com.odcgroup.t24.enquiry.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.translation.MdfTranslationProvider;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.FieldAlignment;
import com.odcgroup.t24.applicationimport.util.ApplicationFieldHelper;
import com.odcgroup.t24.enquiry.enquiry.AlignmentKind;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.ProcessingMode;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 *
 *
 */
public class FieldHelper {
	
	private IProject project;
	
	public FieldHelper(IProject project) {
		this.project = project;
	}
	
	public FieldHelper() {
		
	}
	
	public void manageFields(Enquiry enquiry, MdfClass application, MdfProperty[] elements) {
		if (project == null) {
			project = OfsResourceHelper.getProject(enquiry.eResource());
		}
		MdfTranslationProvider provider = new MdfTranslationProvider();
		int columnNo = computeColumnNumber(enquiry.getFields());
		Field field = null;
		List<Field> fields = new ArrayList<Field>();
		for(MdfProperty property :elements){
			field = getField(enquiry, property);
			boolean setcolumnpos = false;
			if (field == null) {
				field = EnquiryFactory.eINSTANCE.createField();
				field.setName(getFieldName(property));
				setcolumnpos = true;
				columnNo++;
			} 
			if (!isCalculatedField(property, application)) {
				updateField(project, field, provider, columnNo, property, setcolumnpos);
			}
			fields.add(field);
		}
		enquiry.getFields().clear();
		enquiry.getFields().addAll(fields);
		//EnquiryUtil.sortEnquiryFields(enquiry.getFields());		
	}
	
	public void manageFields(Enquiry enquiry, MdfProperty[] elements) {
		MdfClass application = EnquiryUtil.getEnquiryApplication(enquiry.getFileName(), enquiry);
		manageFields(enquiry, application, elements);
	}
	
	private boolean isCalculatedField(MdfProperty property, MdfClass application) {
		List<MdfProperty> propertyList = ApplicationFieldHelper.getAllProperties(application);
		String fldname = property.getName();
		fldname = property.getName().replace("_", ".");
		for (MdfProperty prop : propertyList) {
			String name = T24Aspect.getT24Name(prop);
			//If T24 name is not present, then property name itself should be used
			//Added : DS-8810
			if(name == null){
				return false;
			}
			if (name.equals(fldname)) {
				return false;
			}
		}
		return true;
	}
	
	private Field getField(Enquiry enquiry, MdfProperty property) {
		String name = getFieldName(property);
		List<Field> fields = enquiry.getFields();
		for (Field field : fields) {
			if (name.equals(field.getName())) {
				return field;
			}
		}
		return null;
	}
	
	public static String getFieldName(MdfProperty property) {
		String name = T24Aspect.getT24Name(property);
		if (name == null) {
			name = property.getName().replace("_", ".");
		} 
		return name;
	}

	private void updateField(IProject project,  Field field, MdfTranslationProvider provider, int index, MdfProperty property, boolean setcolumnpos) {
		setFieldTranslation(field, property, provider, project);
		setFieldProperties(field,property,index,setcolumnpos);
	}
	
	private Enquiry getEnquiryFromField(EObject model) {
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
	
	public static MdfProperty getMdfProperty(Field field, MdfClass clazz) {
    	List<MdfProperty> propertyList = ApplicationFieldHelper.getAllProperties(clazz);
    	String fldname = field.getName();
    	if(fldname == null) {
    		return null;
    	}
    	fldname = fldname.replace("_", ".");
    	for (MdfProperty property : propertyList) {
    		String name = T24Aspect.getT24Name(property);
    		if (name != null && name.equals(fldname)) {
    			return property;
    		}
    	}
    	return null;
    } 
	
	private static MdfClass getMdfClass(String fileName, Enquiry enquiry) {
		if (enquiry != null && fileName == null) {
			fileName = enquiry.getFileName();
		}
		MdfClass klass = EnquiryUtil.getEnquiryApplication(fileName, enquiry);
		return klass;
	}
	
	public void resetField(EObject model, String name) {
		if (!name.isEmpty()) {
			Enquiry enquiry = getEnquiryFromField(model);
			MdfClass klass = getMdfClass(null, enquiry);
			MdfProperty property = null;
			if(model instanceof Field) {
				Field field = (Field) model;
				int index = enquiry.getFields().indexOf(field);
				if (klass!=null) {
					for (Object obj : klass.getProperties()) {
						if (obj instanceof MdfProperty
								&& ((MdfProperty) obj).getName().replaceAll("_", ".").equals(name)) {
							property = (MdfProperty) obj;
							break;
						}
					}
					int columnNo = computeColumnNumber(enquiry.getFields());
					if (property != null) {
						Resource applicationResource = ((EObject) klass).eResource();
						MdfTranslationProvider provider = new MdfTranslationProvider();

						if (applicationResource != null) {
							IProject project = OfsResourceHelper.getProject(applicationResource);
							updateField(project, field, provider, columnNo + 1, property, false);
						}
					} else {
						clearField(name, enquiry, index);
					}
				}  else {
					clearField(name, enquiry, index);
				}
			}

		}
	}

	private void clearField(String name, Enquiry enquiry, int index) {
		Field field;
		field = EnquiryFactoryImpl.eINSTANCE.createField();
		field.setName(name);
		enquiry.getFields().remove(index);
		enquiry.getFields().add(index, field);
	}
	
	/**
	 * @return highest column number in fields
	 */
	private int computeColumnNumber(EList<Field> fields) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (Field field : fields) {
			if (field.getPosition()!=null) {
				arrayList.add(field.getPosition().getColumn());
			}
		}
		Integer columnNumber = (arrayList.size() >= 2) ? Collections.max(arrayList) : 1;
		return columnNumber;
	}

	private void setFieldProperties(Field field, MdfProperty property, int index, boolean setcolumnpos) {
		if (setcolumnpos) {
			FieldPosition position = EnquiryFactoryImpl.eINSTANCE.createFieldPosition();
			position.setColumn(index);
			field.setPosition(position);
		}
		FieldAlignment alignment = T24Aspect.getAlignment(property);
		if (alignment != null) {
			if (alignment.name().equals(FieldAlignment.L.name())) {
				field.setAlignment(AlignmentKind.LEFT);
			} else if (alignment.name().equals(FieldAlignment.R.name())) {
				field.setAlignment(AlignmentKind.RIGHT);
			}
		}
		Integer length = T24Aspect.getMaxLength(property);
		if (length != null) {
			field.setLength(length);
		}
		
		if(((EObject)property).eContainer() instanceof MdfClass){
			boolean hasPrimaryKey = false;
			for (Object mdfEle :  ((MdfClass)((EObject)property).eContainer()).getProperties()) {
				if((mdfEle instanceof MdfAssociation && ((MdfAssociation)mdfEle).isPrimaryKey()) ||
						(mdfEle instanceof MdfAttribute && ((MdfAttribute)mdfEle).isPrimaryKey()) ){
					hasPrimaryKey = true;
					break;
				}
			}
			
			if(hasPrimaryKey){
				field.setSingleMulti(ProcessingMode.SINGLE);
			} else
				field.setSingleMulti(ProcessingMode.MULTI); 
			
		}
		
		ApplicationFieldNameOperation operation = EnquiryFactoryImpl.eINSTANCE.createApplicationFieldNameOperation();
		operation.setField(field.getName());
		field.setOperation(operation);	
	}
	
	private void setFieldTranslation(Field field, MdfProperty property, MdfTranslationProvider provider ,IProject project) {
		try {
			ITranslation translation = provider.getTranslation(project, property);
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			List<Locale> locales = new ArrayList<Locale>();
			locales.add(manager.getPreferences().getDefaultLocale());
			locales.addAll(manager.getPreferences().getAdditionalLocales());
		    LocalTranslations localTranslations = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslations();
			for(Locale locale : locales){
				String nameString = translation.getText(ITranslationKind.NAME, locale);
				if (nameString != null ) {
					LocalTranslation localTranslation = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslation();
					localTranslation.setLocale(locale.getLanguage());
					localTranslation.setText(nameString);
					localTranslations.getTranslations().add(localTranslation);
			   }
			} 
			if(!localTranslations.getTranslations().isEmpty()) {
			   field.setLabel(localTranslations);
			}
		} catch (TranslationException e) {
			
		}
	}


}
