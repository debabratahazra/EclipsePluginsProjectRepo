package com.odcgroup.translation.generation.ui.internal.wizard;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author SCN
 */
public class TranslationImporterTest extends AbstractDSUnitTest {

	private static final String MODULE_MODEL = "module/Default/module/DS4902.module";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(MODULE_MODEL);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testXLSImport() {

		URL url = FileLocator.find(TranslationUICore.getDefault().getBundle(),
				new Path("resources/importer/DS4902.xls"), null);
		File xlsFile = null;
		try {
			xlsFile = new File(FileLocator.toFileURL(url).getPath());
			Assert.assertTrue("Unable to find file [resources/importer/DS4902.xls]",
					xlsFile.exists());
		} catch (IOException ex) {
			ex.printStackTrace();
			Assert.fail("Unable to find file [resources/importer/DS4902.xls]");
		}

		XLSImporter importer = new XLSImporter();
		try {
			importer.importTranslations(xlsFile.getPath());
		} catch (CoreException ex) {
			ex.printStackTrace();
			Assert.fail("Unable to import translations file [resources/importer/DS4902.xls]");
		}
	}

}
