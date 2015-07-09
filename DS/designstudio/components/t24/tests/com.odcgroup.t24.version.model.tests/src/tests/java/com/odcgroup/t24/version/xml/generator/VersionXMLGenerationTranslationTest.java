package com.odcgroup.t24.version.xml.generator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class VersionXMLGenerationTranslationTest extends AbstractDSGenerationTest {
	@Inject
	VersionXMLGenerator xmlGenerator;

	@Before
	public void setUp() throws CoreException, IOException, T24ServerException {
		createModelAndGenProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProject(AbstractDSUnitTest.DEFAULT_MODELS_PROJECT);
	}

	@Test
	public void testInheritedTranslationGeneration() throws Exception {
		copyResourceInModelsProject("version/ds8843/BusinessTypes.domain");
		copyResourceInModelsProject("version/ds8843/AA_Accounting.domain");
		copyResourceInModelsProject("version/ds8843/AA_ARR_ACCOUNTING,AA.version");
		waitForAutoBuild();
		Collection<IOfsModelResource> modelResources = getOfsProject().getModelFolder("version")
				.getOfsPackage("ds8843").getModels();

		for (IOfsModelResource resource : modelResources) {
			Resource emfResource = getOfsProject().getModelResourceSet().getResource(resource.getURI(), true);
			emfResource.load(Collections.EMPTY_MAP);
			//based on current deployment test
			xmlGenerator.generateXML(emfResource, null);
		}
		IFile file = getGenProject().getFile("src/xml-t24i/AA_ARR_ACCOUNTING,AA.version.xml");
		InputStream input = null;
		try {
			if (file != null) {
				input = file.getContents();
				String gen = IOUtils.toString(input);
				assertTrue(gen.contains("<translations locale=\"en\" text=\"Activity\"/>"));
			}
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
		}
	}

}
