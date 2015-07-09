package com.odcgroup.mdf.editor.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.junit.After;
import org.junit.Before;

import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * base TestCase specific to Domain Model Editor
 * 
 * @author yan
 * 
 */
public class AbstractDomainEditorTestCase extends AbstractDSUnitTest {

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
    	copyResourceInModelsProject("domain/mdf-editor-tests/DS-3053.domain",
    			"domain/mdf-editor-tests/DS-3232.domain", "domain/mdf-editor-tests/DS-3294.domain",
    			"domain/mdf-editor-tests/DS-4573.domain",
    			"domain/mdf-editor-tests/SyncDataset.domain");
    }
    
    @After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
    
    /**
     * @param filename
     * @return
     * @throws PartInitException
     */
    protected DomainModelEditor openDomainModelEditor(String filename) throws PartInitException {
    	IFile domainFile = getProject().getFolder("domain").getFolder("mdf-editor-tests").getFile(filename);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return (DomainModelEditor)page.openEditor(new FileEditorInput((IFile) domainFile), "com.odcgroup.domain.Domain");
    }
    
    /**
     * Retrieve the mdf domain from the editing domain and the resource name
     * @param editingDomain
     * @param resourceName
     * @return
     */
    protected MdfDomain getMdfDomain(EditingDomain editingDomain, String resourceName) {
    	if (!resourceName.startsWith("resource://")) {
    		resourceName = "resource:///mdf-editor-tests/" + resourceName;
    	}
    	ResourceSet resourceSet = editingDomain.getResourceSet();
		resourceSet.setURIConverter(new ModelURIConverter(getOfsProject()));
		Resource resource = resourceSet.getResource(URI.createURI(resourceName), true);
		return (MdfDomain) resource.getContents().get(0);
    }

}
