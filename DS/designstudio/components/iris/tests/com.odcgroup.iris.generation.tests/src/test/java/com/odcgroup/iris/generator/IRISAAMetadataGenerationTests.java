package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.MultiplatformTestUtil;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * Verify AA metadata generates correctly
 *
 * @author sjunejo
 *
 */
public class IRISAAMetadataGenerationTests extends AbstractIRISMetadataGenerator {
	
	// AAA Version
	private static String VERSION_MODEL = "version/aaa/AA_ARRANGEMENT_ACTIVITY,AA_NEW.version";
    
    // Product Line and Associated Domains
	private static String AAA_APPLICATION_MODEL = "domain/aa/AA_Framework.domain";
	private static String AA_PRODUCT_LINE_MODEL = "domain/aa/ProductLines.domain";
	private static String AA_ASSOCIATED_ACCOUNT_MODEL = "domain/aa/AA_Account.domain";
	private static String AA_ASSOCIATED_ACCOUNTING_MODEL = "domain/aa/AA_Accounting.domain";
	
	@BeforeClass
	public static void beforeTestClass() {
		EcorePackage.eINSTANCE.getEClass();
		XMLTypePackage.eINSTANCE.getID();
	}
	
	@Before
	public void setup() {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testIRISAAMetadataGeneration() throws Exception {
		// Add Domains and Version to project
		copyFromClasspathToModelsProject(AAA_APPLICATION_MODEL, VERSION_MODEL);
		
		Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
		resources.addAll(getOfsProject().getModelResourceSet().getAllOfsModelResources());
		
		ModelLoaderImpl loader = new ModelLoaderImpl(new ResourceSetImpl());
		IRISMetadataGenerator2 generator = new IRISMetadataGenerator2();

		IFolder outputFolder = getProject().getFolder("output");
		outputFolder.create(true, true, null);
		IFileSystemAccess fsa = getFileSystemAccess(getProject(), outputFolder);
		for (IOfsModelResource mresource : resources) {
			generator.doGenerate(getPlatformURI(mresource), loader, fsa);
		}

		outputFolder.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
		
		IFile genfile = outputFolder.getFile("metadata-verAaArrangementActivity_AaNew.xml");
		Assert.assertTrue(genfile.exists());
		String genxml = fetchGenDocumentAsString(genfile);
		Assert.assertFalse(StringUtils.isEmpty(genxml));
		String expectedXml = readFileFromClasspath("/com/odcgroup/iris/aa/generation/tests/metadata-verAaArrangementActivity_AaNew.xml");
		MultiplatformTestUtil.assertEqualsIgnoringEOL(expectedXml, genxml);
		
		IFile propfile = outputFolder.getFile("T24-verAaArrangementActivity_AaNew.properties");
		Assert.assertTrue(propfile.exists());
		String propText = fetchGenDocumentAsString(propfile);
		String expectedText = readFileFromClasspath("/com/odcgroup/iris/aa/generation/tests/T24-verAaArrangementActivity_AaNew.properties");
		MultiplatformTestUtil.assertEqualsIgnoringEOL(expectedText, propText);
		
		Properties properties = readPropertiesFile(propfile);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertEquals("name: AA.ARRANGEMENT.ACTIVITY,AA.NEW", properties.getProperty("default-models.verAaArrangementActivity_AaNew"));
	}		
	
	@Test
	public void testIRISAAClassGenerationSkip() throws Exception {
		// Add Domains to project
		copyFromClasspathToModelsProject(AA_PRODUCT_LINE_MODEL, AA_ASSOCIATED_ACCOUNT_MODEL, AA_ASSOCIATED_ACCOUNTING_MODEL);
		
		Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
		resources.addAll(getOfsProject().getModelResourceSet().getAllOfsModelResources());
		
		ModelLoaderImpl loader = new ModelLoaderImpl(new ResourceSetImpl());
		IRISMetadataGenerator2 generator = new IRISMetadataGenerator2();

		IFolder outputFolder = getProject().getFolder("output");
		outputFolder.create(true, true, null);
		IFileSystemAccess fsa = getFileSystemAccess(getProject(), outputFolder);
		for (IOfsModelResource mresource : resources) {
			generator.doGenerate(getPlatformURI(mresource), loader, fsa);
		}
		
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		// we should not have anything generated for the loaded AA domains
		IResource[] members = outputFolder.members();
		Assert.assertTrue(members.length == 0);
	}		
	
	protected IFileSystemAccess getFileSystemAccess(IProject modelsProject, IFolder outputFolder) {
		SimplerEclipseResourceFileSystemAccess2 fsa = new SimplerEclipseResourceFileSystemAccess2();
		fsa.setProject(outputFolder.getProject());
		fsa.setOutputPath(outputFolder.getProjectRelativePath().toString());
		fsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).setCreateOutputDirectory(true);
		fsa.setMonitor(new NullProgressMonitor());
		return fsa;
	}		
	
	/**
	 * @param mresource
	 * @return
	 */
	private URI getPlatformURI(IOfsModelResource mresource) {
		IResource res = mresource.getResource();
		return URI.createPlatformResourceURI(res.getFullPath().toOSString(), false);
	}
}
