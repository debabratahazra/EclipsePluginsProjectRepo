package com.odcgroup.page.translation.generation.internal;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.translation.generation.PageTranslationKey;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.xls.IColumnProvider;

/**
 * This class implements DS-4750, i.e. it adds an extra column to the XLS generation.
 * 
 * @author Kai Kreuzer
 * @since 6.0.0
 *
 */
public class WidgetIdColumnProvider implements IColumnProvider {

	public WidgetIdColumnProvider() {
	}

	@Override
	public String getHeader() {
		return "ReportKey";
	}

	@Override
	public boolean isBeforeTranslations() {
		return false;
	}

	@Override
	public String getContent(ITranslationKey key, ITranslationKind kind) {
		if(key instanceof PageTranslationKey) {
			PageTranslationKey pageKey = (PageTranslationKey) key;
			Object owner = pageKey.getTranslation().getOwner();
			if(owner instanceof Widget) {
				Widget widget = (Widget) owner;
				String id = widget.getID();
				if(!StringUtils.isEmpty(id)) {
					return id + "." + pageKey.getKeySuffix(kind);
				}
			}
		}
		return key.getKey(kind);
	}
}
