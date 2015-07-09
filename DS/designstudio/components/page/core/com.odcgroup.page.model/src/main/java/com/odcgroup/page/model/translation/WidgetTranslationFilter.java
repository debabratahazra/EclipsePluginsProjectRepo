package com.odcgroup.page.model.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.IFilter;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class WidgetTranslationFilter implements IFilter {

	private static Logger logger = LoggerFactory.getLogger(WidgetTranslationFilter.class);
	
	@Override
	public boolean select(Object toTest) {
		boolean accept = true;
		if (toTest instanceof Widget) {
			WidgetType type = ((Widget)toTest).getType();
			if(type!=null) accept = type.translationSupported();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Wrong type of argument, Widget expected: "+toTest.getClass().getName());
			}
		}
		return accept;
	}

}
