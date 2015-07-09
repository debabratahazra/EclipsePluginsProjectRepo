package com.odcgroup.page.transformmodel.table;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.transformmodel.translation.WidgetAdapterTranslationKeyFactory;
import com.odcgroup.page.transformmodel.util.AttributeRenderer;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author amc
 *
 */
public class NestedGroupAttributeRenderer {
	
	private WidgetAdapterTranslationKeyFactory keyFactory;
	private final StringBuilder script;
	
	protected NestedGroupAttributeRenderer(StringBuilder script, WidgetAdapterTranslationKeyFactory keyFactory) {
		this.script = script;
		this.keyFactory = keyFactory;
	}
	
	public void render(ITableColumn column, String groupName) {
		ITranslationKey key = getTranslationKey(column, groupName);
		if(key == null) {
			appendGroupAttributeItem(script, groupName);
		}
		else {
			appendGroupAttributeWithTranslation(script, key, groupName);
		}
	}

	private ITranslationKey getTranslationKey(ITableColumn column, String groupName) {
		MdfEnumeration mdfEnum = getMdfEnumeration(column, groupName);
		ITranslationKey key = null;
		if(mdfEnum != null) {
			key = keyFactory.getKey(column, mdfEnum);
		}
		return key;
	}
	
	private MdfEnumeration getMdfEnumeration(ITableColumn column, String groupName) {
		MdfDatasetProperty datasetProperty = column.getDatasetProperty(groupName);
		MdfEnumeration mdfEnum = null;
		if(datasetProperty != null) {
			datasetProperty.getType();
			MdfEntity type = datasetProperty.getType();
			if (type instanceof MdfEnumeration) {
				mdfEnum = (MdfEnumeration)type;
			}
		}
		return mdfEnum;
	}

	private void appendGroupAttributeItem(StringBuilder script, String groupName) {
		script.append(getGroupAttributeItem(groupName));
	}
	
	private void appendGroupAttributeWithTranslation(StringBuilder script, ITranslationKey key, String groupName) {
		script.append(getGroupAttributeI18Tag(key, groupName));
	}
	
	private String getGroupAttributeI18Tag(ITranslationKey key, String groupName) {
		return getGroupAttributeI18Start(key) +
		       getGroupAttributeItem(groupName) +
		       getGroupAttributeI18End(key);
	}
	
	private String getGroupAttributeI18Start(ITranslationKey key) {
		return "<i18n:text>"+getKeyStringWithoutKind(key);
	}
	
	private String getGroupAttributeItem(String groupName) {
		StringBuilder tempBuilder = new StringBuilder();
		tempBuilder.append("<udp:item");
		AttributeRenderer.getInstance(tempBuilder).render("column", groupName);
		tempBuilder.append("/>");
		return tempBuilder.toString();
	}
	
	private String getGroupAttributeI18End(ITranslationKey key) {
		return getKeyStringKind(key)+"</i18n:text>";
	}
	
	private String getKeyStringKind(ITranslationKey key) {
		String keyString = key.getKey(ITranslationKind.NAME);
		String keyKindStringWithDot = keyString.substring(keyString.lastIndexOf("."));
		return keyKindStringWithDot;
	}

	private String getKeyStringWithoutKind(ITranslationKey key) {
		String keyString = key.getKey(ITranslationKind.NAME);
		String keyStringWithoutKind = keyString.substring(0, keyString.lastIndexOf(".")+1);
		return keyStringWithoutKind;
	}
	
	public static NestedGroupAttributeRenderer getInstance(StringBuilder script) {
		return new NestedGroupAttributeRenderer(script, new WidgetAdapterTranslationKeyFactory());
	}	

}
