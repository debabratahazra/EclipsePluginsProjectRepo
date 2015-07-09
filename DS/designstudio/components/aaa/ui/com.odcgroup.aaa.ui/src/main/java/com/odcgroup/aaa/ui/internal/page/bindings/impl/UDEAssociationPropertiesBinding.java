package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.domain.annotations.SQLAspectDS;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;

public class UDEAssociationPropertiesBinding extends
		AbstractUDEPropertiesBinding {
	
	private MdfAssociation model;
	private Text attributeSQLNameField;
	private Text attributeNameField;
	private Text attributeTypeField;
	private Text parentTypeAttributeField;	


	public UDEAssociationPropertiesBinding(MdfAssociation model,
			Text attributeSQLNameField, Text attributeNameField, 
			Text attributeTypeField,	Text parentTypeAttributeField) {
		this.model = model;
		this.attributeSQLNameField = attributeSQLNameField;
		this.attributeNameField = attributeNameField;
		this.attributeTypeField = attributeTypeField;
		this.parentTypeAttributeField = parentTypeAttributeField;
	}

	@Override
	public String getDescription() {
		return Messages.getString("aaa.mdf.page.attribute.description");
	}

	@Override
	public void doUpdateModelToText() {
		attributeSQLNameField.setText(nullSafeTrimmedString(AAAAspect.getTripleAAttributeSQLName(model)));
		attributeNameField.setText(nullSafeTrimmedString(AAAAspect.getTripleAAttributeName(model)));
		
		String attrDataType = AAAAspect.getTripleAAttributeDataType(model);
		if (StringUtils.isEmpty(attrDataType)) {
			attributeTypeField.setText("id_t");
		} else {
			attributeTypeField.setText(nullSafeTrimmedString(attrDataType));
		}
		String parentTypeEntityRef = replaceHash(UDEHelper.getInstance().removeBrackets(nullSafeTrimmedString(AAAAspect.getTripleAParentTypeEntity(model))));
		String parentTypeAttrRef = replaceHash(UDEHelper.getInstance().removeBrackets(nullSafeTrimmedString(AAAAspect.getTripleAParentTypeAttr(model))));		
		if (StringUtils.isNotEmpty(parentTypeAttrRef)) {
			parentTypeAttributeField.setText(parentTypeAttrRef);
		} else if (StringUtils.isNotEmpty(nullSafeTrimmedString(parentTypeEntityRef))) {
			parentTypeAttributeField.setText(nullSafeTrimmedString(parentTypeEntityRef));
		} else {
			parentTypeAttributeField.setText("");
		}
	}

	@Override
	public void doUpdateTextToModel(boolean modifyEvent) {
		if (modifyEvent) {
			AAAAspectDS.setTripleAAttributeSQLName(model, nullIfEmptyOrTrimmed(attributeSQLNameField.getText()));
			AAAAspectDS.setTripleAAttributeName(model, nullIfEmptyOrTrimmed(attributeNameField.getText()));
			SQLAspectDS.setSQLName(model, nullIfEmptyOrTrimmed(attributeSQLNameField.getText()));
			AAAAspectDS.setAAAParamsType(model, nullIfEmptyOrTrimmed(attributeTypeField.getText()));
		} else {
			String sqlName = nullIfEmptyOrTrimmed(attributeSQLNameField.getText());
			String name = nullIfEmptyOrTrimmed(attributeNameField.getText());
			String attributeType =  nullIfEmptyOrTrimmed(attributeTypeField.getText());
			boolean changed = false;
			if (StringUtils.isEmpty(sqlName)) {
				sqlName = getUDESqlName(model);
				changed = true;
			}	
			if (StringUtils.isEmpty(name)) {
				name = getUDEName(model);
				changed = true;	
			}
			AAAAspectDS.setTripleAAttributeSQLName(model, sqlName);
			AAAAspectDS.setTripleAAttributeName(model, name);
			SQLAspectDS.setSQLName(model, sqlName);
			AAAAspectDS.setAAAParamsType(model, attributeType);
			if (changed) {
				updateModelToText();
			}
		}
		AAAAspectDS.setTripleAParentTypeEntity(model, replaceDot(UDEHelper.getInstance().addBrackets(nullIfEmptyOrTrimmed(parentTypeAttributeField.getText()))));
		AAAAspectDS.setTripleAParentTypeAttr(model, replaceDot(UDEHelper.getInstance().addBrackets(nullIfEmptyOrTrimmed(parentTypeAttributeField.getText()))));
		AAAAspectDS.setTripleAAttributeDataType(model, nullIfEmptyOrTrimmed(attributeTypeField.getText()));
		
	}

}
