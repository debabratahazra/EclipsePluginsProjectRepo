package com.odcgroup.t24.version.model.translation.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.t24.version.editor.utils.VersionUtils;
import com.odcgroup.t24.version.translation.VersionTranslationProvider;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.TranslationDslUtill;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Tests for Version and Field Translation interfaces 
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class VersionTranslationTest extends AbstractDSUnitTest {
	@Inject
	ResourceSet resourceSet;

	public final static int JBC_ROUTINE = 1;
        
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		waitForJavaProjectNature(getProject());
	}
	
	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}

	@Test
	public void testEmptyVersionTranslation() throws TranslationException {
		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new VersionTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, version);
		
		Assert.assertTrue(translation.getOwner() == version);
		
		for (ITranslationKind kind : translation.getAllKinds()) {
			Assert.assertEquals(translation.getText(kind, Locale.ENGLISH), null);
		}

	}
	
	@Test
	public void testInheritedTranslations()  throws TranslationException {

		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new VersionTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, version);

		Assert.assertFalse(translation.isInheritable());

		for (ITranslationKind kind : translation.getAllKinds()) {
			Assert.assertFalse(translation.isInherited(kind, Locale.ENGLISH));
		}
	
	}

	@Test
	public void testUpdateVersionTranslations() throws TranslationException {
		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new VersionTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, version);

		Assert.assertFalse(translation.isInheritable());

		// set and get translation for all supported kinds
		int val = 100;
		for (ITranslationKind kind : translation.getAllKinds()) {
			String expectedText = kind.toString()+val++;
			translation.setText(kind, Locale.ENGLISH, expectedText);
			String text = translation.getText(kind, Locale.ENGLISH);
			Assert.assertEquals(expectedText, text);
		}

	}
	
	@Test
	public void testDeleteVersionTranslations() throws TranslationException {
		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new VersionTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, version);

		Assert.assertFalse(translation.isInheritable());

		// set and get translation for all kinds
		int val = 100;
		for (ITranslationKind kind : translation.getAllKinds()) {
			String expectedText = kind.toString()+val++;
			translation.setText(kind, Locale.ENGLISH, expectedText);
			String text = translation.getText(kind, Locale.ENGLISH);
			// ensure the text has been stored in the object
			Assert.assertEquals(expectedText, text);
			// delete the translation
			translation.delete(kind, Locale.ENGLISH);
			text = translation.getText(kind, Locale.ENGLISH);
			Assert.assertNull("The translation "+expectedText+" has not been deleted", text);
			
		}
	}

	@Test
	public void testVersionHeaderAndFooterTranslations() throws TranslationException {
	    IProject project = getOfsProject().getProject();
	    copyResourceInModelsProject("version/ds5626/ds5626.version");
	    Version version =null;
	        URI uri = URI.createPlatformResourceURI(getProject().getName()+"/version/ds5626/ds5626.version", true);
	        try {
	              Resource resource = resourceSet.getResource(uri, true);
		      version =  (Version) resource.getContents().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Cannot load the model from the resource:///ds5626/ds5626.version");
		}
	    //Assert the Version
	    Assert.assertNotNull(version); 
	    ITranslationProvider provider = new VersionTranslationProvider(); 
	    ITranslation translation = provider.getTranslation(project, version);
	    //get the Translation kinds length for the Version.
	    int kindslength = translation.getAllKinds().length ;
	    Assert.assertEquals(5, kindslength);
	    //set the Text for the Header Kind Translation
	    String exceptedTextForHeader = "HeaderTransaltionText" ;
	    translation.setText(ITranslationKind.HEADER, Locale.ENGLISH, exceptedTextForHeader);
	    String actualHeaderText = translation.getText(ITranslationKind.HEADER,Locale.ENGLISH );
	    Assert.assertEquals(exceptedTextForHeader, actualHeaderText);
	    //set the Footer Kind Translation text
	    String exceptedTextForFooter = "FooterTranslationText" ;
	    translation.setText(ITranslationKind.FOOTER, Locale.ENGLISH, exceptedTextForFooter);
	    String actualFooterText = translation.getText(ITranslationKind.FOOTER,Locale.ENGLISH );
	    Assert.assertEquals(exceptedTextForFooter, actualFooterText);
	    
	}
	
	@Test
	public void testDS6272RoutineSelection() throws CoreException {
		IProject project = null;
		try {
			IProgressMonitor nullProgressMonitor = new NullProgressMonitor();
			
			// create a simple project
			project = ResourcesPlugin.getWorkspace().getRoot().getProject("javaProject");
			project.create(nullProgressMonitor);
			project.open(nullProgressMonitor);
			
			// add java nature to the project
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, nullProgressMonitor);
			
			// create required folders & files in the project to test			
			IFolder folder = project.getFolder("testDS6272");
			folder.create(true, true, nullProgressMonitor);

			StringBuilder sb = new StringBuilder();
			sb.append("Sample File");
			InputStream source = new ByteArrayInputStream(sb.toString().getBytes());	
			
			IFile file = folder.getFile("DS6272.b");
					
			file.create(source, true, nullProgressMonitor);
			
			// create bin(java output) folder and same file created above into the folder
			folder = project.getFolder("bin");
			folder.create(true, true, nullProgressMonitor);
			
			file = folder.getFile("DS6272.b");
			file.create(source, true, nullProgressMonitor);
			
			// Should fetch only one DS6272.b file
			Collection<IResource> collections = VersionUtils.listFiles((IResource)project, JBC_ROUTINE, true);
	        Assert.assertEquals(1, collections.size());
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			if (project != null) {
				project.delete(true,  null);
			}
		}
	}
	
	@Test
	public void testVersionTransaltionContainCRLF()  {
		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		String tranlsationHeader = "HeaderTransaltionText \r\n" ;
		LocalTranslation localTranslation = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslation();
		localTranslation.setLocale(Locale.ENGLISH.getLanguage());
		localTranslation.setText(tranlsationHeader);
		LocalTranslations localTranslations = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslations();
		localTranslations.getTranslations().add(localTranslation);
		version.setHeader(localTranslations);
		List<Translations> tranlsationList =com.odcgroup.t24.version.utils.VersionUtils.getAllTransaltionForVersion(version);
		assertNotNull(tranlsationList);
		assertTrue(tranlsationList.size() == 1);
		String errorMessage = null;
		Translations translations = tranlsationList.get(0);
		assertNotNull(translations);
		EList<LocalTranslation> localtranslation=((LocalTranslations)translations).getTranslations();
		assertNotNull(localtranslation);
		errorMessage = TranslationDslUtill.getTranslationError(localtranslation.get(0));
		assertNotNull(errorMessage);
	}
}
