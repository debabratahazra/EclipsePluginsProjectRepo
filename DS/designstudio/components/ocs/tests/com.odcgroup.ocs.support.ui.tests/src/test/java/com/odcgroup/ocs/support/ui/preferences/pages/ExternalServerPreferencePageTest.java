package com.odcgroup.ocs.support.ui.preferences.pages;

import org.junit.Assert;
import org.junit.Test;

public class ExternalServerPreferencePageTest  {

	@Test
	public void testPerformOKWillReturnTrueWhenFieldsAreNotPopulated() throws Exception {
		ExternalServerPreferencePage externalServerPreferencePage = new ExternalServerPreferencePage();
		boolean performOk = externalServerPreferencePage.performOk();
		Assert.assertTrue(performOk);
	}

	
	@Test
	public void testExtractOcsBinariesCalledOnceWhenAPropertyHasChanged() throws Exception {
		class ExternalServerPreferencePageMock extends ExternalServerPreferencePage {
			boolean schemaResolverReset = false;
			@Override
			public boolean anyPropertiesChanged(String oldInstallDir,
					String oldOcsSourceJar, String newInstallDir,
					String newOcsSourceJar) {
				return true;
			}
			
			@Override
			public void resetSchemaResolver() {
				schemaResolverReset = true;
			}
		};

		ExternalServerPreferencePageMock externalServerPreferencePage = new ExternalServerPreferencePageMock();
		boolean performOk = externalServerPreferencePage.performOk();
		Assert.assertTrue(externalServerPreferencePage.schemaResolverReset);
		Assert.assertTrue(performOk);
	}

}

