package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

public class UDEEnumerationPropertiesBinding extends
		AbstractUDEPropertiesBinding {
	
	private MdfEnumeration model;
	private Text relatedAttribute;

	public UDEEnumerationPropertiesBinding(MdfEnumeration model,
			Text relatedAttribute) {
		this.model = model;
		this.relatedAttribute = relatedAttribute;
	}

	@Override
	public String getDescription() {
		return Messages.getString("aaa.mdf.page.enumeration.description");
	}

	@Override
	public void doUpdateModelToText() {
		String entitySQLNameRef = replaceHash(UDEHelper.getInstance().removeBrackets(AAAAspect.getTripleAEntitySQLName(model)));
		String attributeSQLNameRef = replaceHash(UDEHelper.getInstance().removeBrackets(AAAAspect.getTripleAAttributeSQLName(model)));
		
		if (StringUtils.isNotEmpty(attributeSQLNameRef)) {
			relatedAttribute.setText(attributeSQLNameRef);
		} else if (StringUtils.isNotEmpty(nullSafeTrimmedString(entitySQLNameRef))) {
			relatedAttribute.setText(nullSafeTrimmedString(entitySQLNameRef));
		} else {
			relatedAttribute.setText("");
		}
	}

	@Override
	public void doUpdateTextToModel(boolean modifyEvent) {
		AAAAspectDS.setTripleAEntitySQLName(model, replaceDot(UDEHelper.getInstance().addBrackets(nullIfEmptyOrTrimmed(relatedAttribute.getText()))));
		AAAAspectDS.setTripleAAttributeSQLName(model, replaceDot(UDEHelper.getInstance().addBrackets(nullIfEmptyOrTrimmed(relatedAttribute.getText()))));
	}

}
