package com.odcgroup.edge.t24.generation;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.junit.After;
import org.junit.Before;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

/**
 * Helper to write tests.
 * 
 * THIS IS.. WRONG - THE "REAL" GENERATOR DOESN'T USE EIO, BUT OFS STUFF,
 * SO... THIS... "CHEATS"... ;-) WE MAY CLEAN THIS UP IN http://rd.oams.com/browse/DS-6999 (TODO).
 *
 * @author Michael Vorburger
 */
public abstract class AbstractEdgeGeneratorTest extends AbstractDSGenerationTest {

	@Inject
	private ResourceSet resourceSet;
	
	@Inject XtextResourceFactory resourceFactory;
	
	private EIO eio;
	
  	@Before
	public void setUp() {
  		

		createModelAndGenProject();
		copyFromClasspathToModelsProject(getModelsNeededForTests());
		
        // HACK to allow components
        //LicenseInfo l = ConnectIDE.getLicenseInfo();
        //l.setAllowComponents(true);

		resourceSet = getOfsProject().getModelResourceSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("domain", resourceFactory);
		//eio = new EIO(resourceSet);
		
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
		resourceSet = null;
	}

	// could, of course, also just use com.odcgroup.workbench.core.resources.OfsProject.getOfsModelResource(URI); ...

	private URI getURI(String modelPath) {
		URI uri = URI.createPlatformResourceURI(getProject().getName() + modelPath, true);
		return uri;
	}
	
	protected final ResourceSet getResourceSet() {
		return resourceSet;
	}
	
	protected Version getVersion(String modelPath) throws IOException, DiagnosticException {
        Resource resource = getResourceSet().getResource(getURI(modelPath), true);
        return (Version)resource.getContents().get(0);
	}

	protected Enquiry getEnquiry(String modelPath) throws IOException, DiagnosticException {
        Resource resource = getResourceSet().getResource(getURI(modelPath), true);
        return (Enquiry)resource.getContents().get(0);
	}
	
	abstract protected String[] getModelsNeededForTests(); 
}
