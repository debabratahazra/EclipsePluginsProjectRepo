package com.odcgroup.t24.enquiry.properties.sources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.Format;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.properties.descriptors.FieldFormatFieldPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.util.ConversionPropertiesUtil;
import com.odcgroup.t24.enquiry.properties.util.OperationPropertiesUtil;

/**
 * @author satishnangi
 *
 */
public class FieldPropertySourceWrapper implements IPropertySource {
	
	private IPropertySource source;
	private Field field = null;
	private FieldPosition fieldPosition ;
	private BreakCondition breakCondition;
	private Format fieldFormat;
	private IPropertySourceProvider sourceProvider;
	private IPropertySource  fieldPositionPropertySource;
	private IPropertySource breakConditionPropertySource ;
	private IPropertySource formtPropertySource;
	
	EnquiryPackage pack = EnquiryPackage.eINSTANCE;
	
	
	public FieldPropertySourceWrapper(IPropertySource source, IPropertySourceProvider sourceProvider, Field model) {
		this.source = source;
		this.field = model;
		this.sourceProvider = sourceProvider;
		initPropertySources();
		
	}

	public void initPropertySources() {
		if (field.getPosition() != null) {
			fieldPosition = field.getPosition();
		} else {
			fieldPosition = EnquiryFactory.eINSTANCE.createFieldPosition();
			fieldPosition.setPageThrow(false);
		}
        fieldPositionPropertySource = sourceProvider.getPropertySource(fieldPosition);
        if (field.getBreakCondition() != null) {
        	breakCondition = field.getBreakCondition();
		} else {
			breakCondition = EnquiryFactory.eINSTANCE.createBreakCondition();
		}
        breakConditionPropertySource =  sourceProvider.getPropertySource(breakCondition);
        if (field.getFormat() != null) {
        	fieldFormat = field.getFormat();
		} else {
			fieldFormat = EnquiryFactory.eINSTANCE.createFormat();
		}
        formtPropertySource =  sourceProvider.getPropertySource(fieldFormat);
	}
	@Override
	public Object getEditableValue() {
		return source.getEditableValue();
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return source.getPropertyDescriptors();
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (isBreakConditionId(id)) {
			return breakConditionPropertySource.getPropertyValue(id);
		}
		if (isFieldPositionId(id)) {
			return getFieldPositionPropertyValue(id);
		}
		if (isFormatId(id)) {
			if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD)){
				id = pack.getFormat_Field().getName();
			}
			return formtPropertySource.getPropertyValue(id);
		}
		
		if(id instanceof Conversion){
			return ConversionPropertiesUtil.getConversionSummary((Conversion)id);
		}
		
		if(isBooleanType(id)){
			if(source.getPropertyValue(id) == null)
				return false;
		}
		if(id.equals(EnquiryPackage.eINSTANCE.getOperation().getName())){
			return OperationPropertiesUtil.getOperationSummary(field.getOperation());
		}
		return source.getPropertyValue(id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		if(isFieldPositionId(id)){
			 return fieldPositionPropertySource.isPropertySet(id);
		}
		if(isBreakConditionId(id)){
			return breakConditionPropertySource.isPropertySet(id);
		}
		if(isFormatId(id)){
			if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD)){
				id = pack.getFormat_Field().getName();
			}
			return formtPropertySource.isPropertySet(id);
		}
		if(id instanceof Conversion){
			return true;
		}
		if(id.equals(EnquiryPackage.eINSTANCE.getOperation().getName())){
			return true;
		}
		return source.isPropertySet(id);
	}

	
	/**
	 * This method is used to identify the properties which are of type Boolean 
	 * and used to set a default value of false if tag is missing in the DSL
	 * 
	 * @param id
	 * @return
	 */
	private boolean isBooleanType(Object id) {
		return (id.equals(pack.getFieldPosition_MultiLine().getName()) || id.equals(pack.getFieldPosition_PageThrow().getName()) 
				|| id.equals(pack.getField_NoColumnLabel().getName()) || id.equals(pack.getField_NoHeader().getName()) || id.equals(pack.getField_Hidden().getName()));
	}

	@Override
	public void resetPropertyValue(Object id) {
		if (isFormatId(id)) {
			resetFormt(id);
		} else if(id.equals(EnquiryPackage.eINSTANCE.getOperation().getName())){
			resetOperation();
		} else if(id.equals(EnquiryPackage.eINSTANCE.getConversion().getName())){
			field.getConversion().clear();
		} else if(isBreakConditionId(id)){
			resetBreakCondition(id);
		} else {
			source.resetPropertyValue(id);
		}
	}

	/**
	 * 
	 */
	private void resetOperation() {
		field.setOperation(null);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (isBreakConditionId(id)) {
			setBreakCondition(id, value);
			breakConditionPropertySource.setPropertyValue(id, value);
		} else if (isFieldPositionId(id)) {
			setFieldPostionProperty(id, value);
		} else if (isFormatId(id)) {
			setFormat(id, value);
		} else if (id.equals(EnquiryPackage.eINSTANCE.getOperation().getName())) {
			if(value instanceof Operation){
				field.setOperation((Operation) value);
			}
			else if(value instanceof String && value.equals("None")){
				field.setOperation(null);
			}
		} else if(id instanceof Conversion){
			// Already values are updated in pop-up dialog
		} else {
			source.setPropertyValue(id, value);
		}
		/*
		TODO: DS-8293 - commenting out the field sorting for time-being
		if (id.equals(pack.getFieldPosition_Column().getName()) || id.equals(pack.getField_DisplaySection().getName())
				|| id.equals(pack.getFieldPosition_Line().getName())
				|| id.equals(pack.getFieldPosition_Relative().getName())) {
			Enquiry enquiry = (Enquiry) field.eContainer();
			EnquiryUtil.sortEnquiryFields(enquiry.getFields());
		}
		*/
	}


	/**
	 * @return
	 */
	private boolean isIdRelatedToLengthMask(Object id) {
		if (id.equals(pack.getField_Length().getName()) || id.equals(pack.getField_CommaSeparator().getName())
				|| id.equals(pack.getField_NumberOfDecimals().getName()) || id.equals(pack.getField_Alignment().getName())
				|| id.equals(pack.getField_EscapeSequence().getName()) || id.equals(pack.getField_FmtMask().getName())) {
			return true;
		}
		return false;
	}

	/**
	 * @param id
	 * @return
	 */
	private boolean isFieldPositionId(Object id) {
		if (id.equals(pack.getFieldPosition_Column().getName()) || id.equals(pack.getFieldPosition_Line().getName())
				|| id.equals(pack.getFieldPosition_Relative().getName())
				|| id.equals(pack.getFieldPosition_MultiLine().getName())
				|| id.equals(pack.getFieldPosition_PageThrow().getName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param id
	 * @param value
	 */
	private void setFieldPostionProperty(Object id, Object value) {
		if (field.getPosition() == null) {
			field.setPosition(fieldPosition);
		}
		if (id.equals(pack.getFieldPosition_PageThrow().getName())) {
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_PageThrow().getName(),
					(Boolean.valueOf((String) value)));
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_Relative().getName(), null);
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_MultiLine().getName(), null);
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_Line().getName(), null);
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_Column().getName(), 0);
		} else if (id.equals(pack.getFieldPosition_Relative().getName()) && value.equals("None")) {
			fieldPositionPropertySource.setPropertyValue(id, null);
		} else if (id.equals(pack.getFieldPosition_MultiLine().getName())) {
			fieldPositionPropertySource.setPropertyValue(id, Boolean.parseBoolean((String) value));
		} else if (id.equals(pack.getFieldPosition_Line().getName()) && value.equals("")) {
			fieldPositionPropertySource.setPropertyValue(id, null);
		} else {
			fieldPositionPropertySource.setPropertyValue(id, value);
		}
		if (!(id.equals(pack.getFieldPosition_PageThrow().getName())) && field.getPosition().getPageThrow() != null) {
			fieldPositionPropertySource.setPropertyValue(pack.getFieldPosition_PageThrow(), null);
		}

	}

	/**
	 * @param id
	 */
	private Object getFieldPositionPropertyValue(Object id) {
		Object value = fieldPositionPropertySource.getPropertyValue(id);
		if(value == null){
			if(isBooleanType(id)){
				return "false";
			}
		}
		return value;
	}
	
	/**
	 * @return
	 */
	private boolean isBreakConditionId(Object id) {
	  if(id.equals(pack.getBreakCondition_Break().getName()) ||id.equals(pack.getBreakCondition_Field().getName())){
		return true;
	  }	
	  return false;
	}
	
	/**
	 * @param id
	 * @param value
	 */
	private void setBreakCondition(Object id, Object value) {
		if(id.equals(pack.getBreakCondition_Break().getName())) {
			if( breakCondition.getField() !=null){
				breakConditionPropertySource.setPropertyValue(pack.getBreakCondition_Field().getName(), null);
			}
		}
		if(id.equals(pack.getBreakCondition_Field().getName())) {
			if( breakCondition.getBreak() !=null){
				breakConditionPropertySource.setPropertyValue(pack.getBreakCondition_Break().getName(), null);
			}
		}
		BreakCondition condition = field.getBreakCondition();
		if(condition == null){
			field.setBreakCondition(breakCondition);
		}
	}

	/**
	 * @param id
	 * @param value
	 */
	private void resetBreakCondition(Object id) {
		if(id.equals(pack.getBreakCondition_Field().getName())) {
			if( breakCondition.getField() !=null){
				breakConditionPropertySource.setPropertyValue(pack.getBreakCondition_Field().getName(), null);
			}
		}
		if(id.equals(pack.getBreakCondition_Break().getName())) {
			if( breakCondition.getBreak() !=null){
				breakConditionPropertySource.setPropertyValue(pack.getBreakCondition_Break().getName(), null);
			}
		}
		field.setBreakCondition(null);
	}

	
	/**
	 * @return
	 */
	private boolean isFormatId(Object id) {
	  if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD) ||id.equals(pack.getFormat_Format().getName()) || id.equals(pack.getFormat_Pattern().getName())){
		return true;
	  }	
	  return false;
	}
	
	/**
	 * @param id
	 * @param value
	 */
	private void setFormat(Object id, Object value) {
		if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD)) {
			if( fieldFormat.getField() !=null){
				formtPropertySource.setPropertyValue(pack.getFormat_Field().getName(), value);
			}
		}
		if(id.equals(pack.getFormat_Pattern().getName())) {
			if( fieldFormat.getPattern() !=null){
				formtPropertySource.setPropertyValue(pack.getFormat_Pattern().getName(), value);
			}
		}
		if(id.equals(pack.getFormat_Format().getName())) {
			if( fieldFormat.getFormat() !=null){
				formtPropertySource.setPropertyValue(pack.getFormat_Format().getName(), value);
			}
		}
		if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD)){
			id = pack.getFormat_Field().getName();
			formtPropertySource.setPropertyValue(id, value);
		}
		Format format = field.getFormat();
		if(format == null){
			field.setFormat(fieldFormat);
		}
	}

	private void resetFormt(Object id){

		if(id.equals(FieldFormatFieldPropertyDescriptor.FORMAT_FIELD)) {
			if( fieldFormat.getField() !=null){
				formtPropertySource.resetPropertyValue(pack.getFormat_Field().getName());
			}
		}
		if(id.equals(pack.getFormat_Pattern().getName())) {
			if( fieldFormat.getPattern() !=null){
				formtPropertySource.resetPropertyValue(pack.getFormat_Pattern().getName());
			}
		}
		if(id.equals(pack.getFormat_Format().getName())) {
			if( fieldFormat.getFormat() !=null){
				formtPropertySource.resetPropertyValue(pack.getFormat_Format().getName());
			}
		}
		Format format = field.getFormat();
		if(format == null){
			field.setFormat(fieldFormat);
		}
	
	}

}
