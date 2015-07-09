package com.odcgroup.edge.t24.generation.languagemaps;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;

import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;

/**
 * <code>SupplementaryTranslationsManager</code> manages 
 *
 * @author shayes
 *
 */
public class SupplementaryTranslationsManager
{
    private static final Logger LOGGER = GenLogger.getLogger(SupplementaryTranslationsManager.class);
	private static final String LANGUAGE_RESOURCEBUNDLE_BASE_NAME = "templates.Messages.Messages";
	private static final HashMap<ISOLanguageCode,Properties> s_auxTranslationPropsByLanguageCode = new HashMap<ISOLanguageCode,Properties>();
	
	public static String getTranslation(String p_englishValue, ISOLanguageCode p_isoLanguageCode)
	{
		Properties auxTranslationPropsForLanguage = s_auxTranslationPropsByLanguageCode.get(p_isoLanguageCode);
		
		if ((auxTranslationPropsForLanguage == null) && ! s_auxTranslationPropsByLanguageCode.containsKey(p_isoLanguageCode))
		{
			// We've yet to try loading the resource bundle for p_languageCode. Attempt to do that now...
			
			try
			{
				final ResourceBundle bundleForLanguage = ResourceBundle.getBundle(LANGUAGE_RESOURCEBUNDLE_BASE_NAME, p_isoLanguageCode.locale);
				
				// We convert the ResourceBundle to Properties (rather than simply storing the ResourceBundle itself) for performance reasons - ResourceBundle throws MissingResource
				// exceptions when asked for a key that is undefined.
				
				auxTranslationPropsForLanguage = toPropertiesObject(bundleForLanguage);
			}
			
			catch (MissingResourceException mre)
			{
				LOGGER.error("No resource bundle found for base name: " + LANGUAGE_RESOURCEBUNDLE_BASE_NAME + " and language code: " + p_isoLanguageCode);			
			}
			
			finally
			{
				s_auxTranslationPropsByLanguageCode.put(p_isoLanguageCode, auxTranslationPropsForLanguage);
			}
		}
		
		return (auxTranslationPropsForLanguage == null) ? null : auxTranslationPropsForLanguage.getProperty(p_englishValue);
	}
		
	private static Properties toPropertiesObject(ResourceBundle p_bundle)
	{
		Properties result = null;
		
		if (p_bundle != null)
		{
			final Enumeration<String> bundleKeysEnum = p_bundle.getKeys();
			result = new Properties();
			
			while(bundleKeysEnum.hasMoreElements())
			{
				final String key = bundleKeysEnum.nextElement();
				final String value = StringUtils.trimEmptyToNull(p_bundle.getString(key));
				
				if (value != null)
					result.setProperty(key, value);
			}
		}
		
		return result;
	}
}
