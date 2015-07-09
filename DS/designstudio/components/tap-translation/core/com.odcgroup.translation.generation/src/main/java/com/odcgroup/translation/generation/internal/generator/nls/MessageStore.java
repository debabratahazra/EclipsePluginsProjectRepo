package com.odcgroup.translation.generation.internal.generator.nls;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 *
 * @author amc
 *
 */
public class MessageStore {

	private static Logger logger = LoggerFactory.getLogger(NLSGenerator.class);
	
	private Map<Locale, Map<String, String>> messagesByLocale = new HashMap<Locale, Map<String,String>>();
	
	
	public void addTranslation(ITranslationKey key, List<Locale> locales) {
		for (ITranslationKind kind : key.getTranslationKinds()) {
			String msgKey = key.getKey(kind);
			if (msgKey != null) {
				for (Locale locale : locales) {
					Map<String, String> keyValueMap = messagesByLocale.get(locale);
					if (keyValueMap == null) {
						keyValueMap = new HashMap<String, String>();
						messagesByLocale.put(locale, keyValueMap);
					}
					try {
						String msg = key.getMessage(kind, locale);
						String message = msg; 
						if (RichTextUtils.isRichRext(msg)) {
							message = msg != null ? RichTextUtils.convertRichTextToHTML(msg) : "";
						}
						keyValueMap.put(msgKey, message);
					} catch (TranslationException ex) {
						logger.trace("(ERROR) Error while exporting a translation. " +
								"(owner=" + 
								key.getTranslation().toString() + 
								", key=" + msgKey + ")", ex);
					} catch (Exception ex) {
						logger.trace("(ERROR) Error while exporting a translation. " +
								"(owner=" + 
								key.getTranslation().toString() + 
								", key=" + msgKey + ")", ex);
					}
				} 
			}
		}
	}


	public Map<String, String> get(Locale locale) {
		Map<String, String> messagesForLocale = messagesByLocale.get(locale);
		if(messagesForLocale == null) {
			messagesForLocale = new HashMap<String, String>();
		}
		return messagesForLocale;
	}
}
