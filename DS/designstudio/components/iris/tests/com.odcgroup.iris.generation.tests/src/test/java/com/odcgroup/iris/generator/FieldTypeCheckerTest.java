package com.odcgroup.iris.generator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class FieldTypeCheckerTest extends AbstractDSUnitTest {
	
	public FieldTypeCheckerTest() {
		super();
	}

	// AAA Version
	private static String VERSION_MODEL = "version/aaa/AA_ARRANGEMENT_ACTIVITY,AA_NEW.version";
    // Product Line and Associated Domains
	private static String AAA_APPLICATION_MODEL = "domain/aa/AA_Framework.domain";
	private static String TYPE_SAFETY_FILE = "typeSafeFields.txt";
	
	@BeforeClass
	public static void beforeTestClass() {
		EcorePackage.eINSTANCE.getEClass();
		XMLTypePackage.eINSTANCE.getID();
	}
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		createFile(DEFAULT_MODELS_PROJECT);
		copyFromClasspathToModelsProject(AAA_APPLICATION_MODEL, VERSION_MODEL);
	}

	private void createFile(String projectName) throws Exception {
		String projectLocation = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation()
				.toOSString();

		// create new file
		File file = new File(projectLocation + File.separatorChar + TYPE_SAFETY_FILE);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		URL url = Resources.getResource(FieldTypeCheckerTest.class, TYPE_SAFETY_FILE);
		String fileContent = Resources.toString(url, Charsets.UTF_8);
		FileUtils.writeStringToFile(file, fileContent, Charsets.UTF_8.name());
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testFieldTypeChecker() throws Exception {
		Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
		resources.addAll(getOfsProject().getModelResourceSet().getAllOfsModelResources());
		
		ModelLoaderImpl loader = new ModelLoaderImpl(new ResourceSetImpl());

		for (IOfsModelResource mresource : resources) {
			URI platformURI = getPlatformURI(mresource);
			String extn = platformURI.fileExtension();
			
			if (extn.equals(GeneratorConstants.VERSION_EXT)) {
				Version version = loader.getRootEObject(platformURI, Version.class);
				//actual
				FieldTypeChecker ftc = new FieldTypeChecker(version);
				//expected
				URL url = Resources.getResource(FieldTypeCheckerTest.class, TYPE_SAFETY_FILE);
				File resourceFile;
				try {
				java.net.URI uri = FileLocator.toFileURL(url).toURI();
				resourceFile = new File(uri);
				} catch (Exception e) {
					throw new IllegalArgumentException("Available resource on classpath couldn't be converted to URI or File: " + url.toString());
				}
				
			    String textValue = "\r\n" + FileUtils.readFileToString(resourceFile, Charsets.UTF_8.name()) + "\r\n";
				assertEquals(textValue, ftc.getText());
				break;
			}
		}
	}
	
	private URI getPlatformURI(IOfsModelResource mresource) {
		IResource res = mresource.getResource();
		return URI.createPlatformResourceURI(res.getFullPath().toOSString(), false);
	}

}
