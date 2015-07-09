package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfClass;

public class UDEClassPropertiesBinding extends
		AbstractUDEPropertiesBinding {
	
	private MdfClass model;
	private Text entitySQLName;
	private Text entityName;
	private Button securedButton;
	
	public UDEClassPropertiesBinding(MdfClass model, Text entitySQLName, Text entityName, Button securedButton) {
		this.model = model;
		this.entitySQLName = entitySQLName;
		this.entityName = entityName;
		this.securedButton = securedButton;
	}

	@Override
	public String getDescription() {
		return Messages.getString("aaa.mdf.page.entity.description");
	}

	@Override
	public void doUpdateModelToText() {
		entitySQLName.setText(nullSafeTrimmedString(AAAAspect.getTripleAEntitySQLName(model)));
		entityName.setText(nullSafeTrimmedString(AAAAspect.getTripleAEntityName(model)));
		securedButton.setSelection(AAAAspect.getTripleAEntitySecured(model));
	}

	@Override
	public void doUpdateTextToModel(boolean modifyEvent) {	
		if (modifyEvent) {
			AAAAspectDS.setTripleAEntitySQLName(model, nullIfEmptyOrTrimmed(entitySQLName.getText()));
			AAAAspectDS.setTripleAEntityName(model, nullIfEmptyOrTrimmed(entityName.getText()));
		} else {
			String sqlName = nullIfEmptyOrTrimmed(entitySQLName.getText());
			String name = nullIfEmptyOrTrimmed(entityName.getText());
			boolean changed = false;
			if (StringUtils.isEmpty(entitySQLName.getText())) {
				sqlName = "udt_"+getUDESqlName(model);
				changed = true;
			}
			if (StringUtils.isEmpty(entityName.getText())) {
				name = getUDEName(model);
				changed = true;
			}
			AAAAspectDS.setTripleAEntitySQLName(model, sqlName);
			AAAAspectDS.setTripleAEntityName(model, name);
			if (changed) {
				updateModelToText();
			}
		}
		AAAAspectDS.setTripleAEntitySecured(model, securedButton.getSelection());	
	}
	
}
