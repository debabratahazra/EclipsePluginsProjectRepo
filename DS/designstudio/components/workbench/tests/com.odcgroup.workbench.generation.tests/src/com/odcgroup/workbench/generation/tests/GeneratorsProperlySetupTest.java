package com.odcgroup.workbench.generation.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;

/**
 * @author yan
 */
public class GeneratorsProperlySetupTest extends AbstractDSGenerationTest {

	@Before
	public void setUp() throws Exception {
		createModelAndGenProject();
		copyResourceInModelsProject("domain/ds3626/DS-3626.domain");
		getGenProject().getFolder("generated").create(true, true, null);
		
		// Required to enable VR dependency management
		OfsCore.addNature(getProject(), "de.visualrules.core.visualrulesNature");
		
		OfsCore.getDependencyManager().disableAutoResolution();
		OfsCore.getDependencyManager().resolveDependencies();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	/**
	 * This test try to execute all cartridges (except Visual Rule
	 * ones) to check the classpath is correctly setup. 
	 */
	@Test
	public void testCartridgeExecution() throws CoreException {
		CodeCartridge[] registeredCartridges = GenerationCore.getRegisteredCodeCartridges();
		for (CodeCartridge cartridge : registeredCartridges) {
			try {
				Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
				List<IStatus> nonOkStatus = new ArrayList<IStatus>();
				resources.add(getOfsProject().getOfsModelResource(URI.createURI("resource:///ds3626/DS-3626.domain")));
				if (!cartridge.getCodeGen().getClass().getName().startsWith("com.odcgroup.visualrules")) {
					IProgressMonitor monitor = new NullProgressMonitor();
					cartridge.getCodeGen().run(monitor, getProject(), resources, getGenProject().getFolder("generated"), nonOkStatus);
				}
			} catch (Exception e) {
				throw new RuntimeException("Unable to execute this cartridge properly: " + cartridge.getCategory().name() + "//" + cartridge.getName(), e);
			}
		}
	}
}
