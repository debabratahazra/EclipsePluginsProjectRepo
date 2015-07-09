package com.odcgroup.aaa.connector.mdfmml;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.aaa.connector.internal.nls.Language;
import com.odcgroup.aaa.connector.internal.nls.Translateable;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.ext.tangij.TANGIJTranslationAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * @author yan
 */
public class MetaDict2MmlTest extends AbstractMetaDictTst {

	public MetaDict2MmlTest() throws IOException {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testIfInstrumentEndDHasSQLRequiredAsPerDS3925() {
		MdfProperty p = domains.entitiesDomain.getClass("Instrument").getProperty("endD");
		Assert.assertTrue(SQLAspect.isSQLRequired(p));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHebrewMapping_DS4403() {
		MetaDict2Mml metadict2mdf = new MetaDict2Mml();
		
		MdfDomain domain = MdfFactory.eINSTANCE.createMdfDomain();
		MdfClass mdfEntity = MdfFactory.eINSTANCE.createMdfClass();
		domain.getClasses().add(mdfEntity);
		
		Translateable translateable = new Translateable() {
			public boolean hasTranslatedName(Language lang) {
				return true;
			}
			
			public String getTranslatedName(Language lang)
					throws IllegalArgumentException {
				return "Hebrew text";
			}
			
			public Set<Language> getTranslatedLanguages() {
				Language hebrew = new Language() {
					public String getName() {
						return "Hebrew";
					}
					
					public String getCode() {
						return "he";
					}
				};
				return Collections.singleton(hebrew);
			}
		};
		
		metadict2mdf.addTranslations(mdfEntity, translateable);
		Map<String, String> translations = TANGIJTranslationAspect.getTranslations(mdfEntity);
		for(String lang : translations.keySet()) {
			Assert.assertEquals("iw", lang);
		}
	}
}
