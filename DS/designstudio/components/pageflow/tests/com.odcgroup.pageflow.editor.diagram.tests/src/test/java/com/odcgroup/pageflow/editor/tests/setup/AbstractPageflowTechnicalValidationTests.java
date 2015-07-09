package com.odcgroup.pageflow.editor.tests.setup;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Assert;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorUtil;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public abstract class AbstractPageflowTechnicalValidationTests extends
		AbstractPageflowEditorTests {
	
	private static final String PROJECT_NAME = "pageflowTest";
	private String PAGEFLOW_NAME;
	
	/**
	 * @param name
	 */
	public AbstractPageflowTechnicalValidationTests() {
		PAGEFLOW_NAME = "";
	}
	
	/**
	 * @param name
	 * @param pageflowName
	 */
	public AbstractPageflowTechnicalValidationTests(String pageflowName) {		
		PAGEFLOW_NAME = pageflowName;
	}	
    
    /*
     * (non-Javadoc)
     * 
     * @see com.odcgroup.pageflow.editor.tests.AbstractEditorTests#createDiagram()
     */
    protected void createDiagram() throws Exception {
    	IProject project = getProject(PROJECT_NAME);
        String pathName = project.getFullPath().toOSString() + "/"
                + PAGEFLOW_NAME + ".pageflow";
        URI diagramUri = URI.createPlatformResourceURI(pathName, false);
        Resource diagram = PageflowDiagramEditorUtil.createDiagram(diagramUri,
                new NullProgressMonitor(), PAGEFLOW_NAME, PAGEFLOW_NAME+" desc");
        String path = diagram.getURI().toPlatformString(true);
        IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(
                new Path(path));
        if (workspaceResource instanceof IFile) {
            setDiagramFile((IFile) workspaceResource);
            setResource(diagram);
            openDiagram();
        }
    }
    
	@Override
	protected IProject createProject() throws Exception {
    	IWorkspace workspace = null;
        String aProjectName = "pageflowTest"; //$NON-NLS-1$
        workspace = ResourcesPlugin.getWorkspace();
        
        IWorkspaceDescription description = workspace.getDescription();
        description.setAutoBuilding(false);
        workspace.setDescription(description);
        
        IWorkspaceRoot wsroot = workspace.getRoot();
        IProject project = wsroot.getProject(aProjectName);
        project.create(null);
        project.open(null);
        IProjectDescription desc = workspace.newProjectDescription(project.getName());
        
        new OfsProjectInitializer().updateConfiguration(project, null);
        Assert.assertTrue(OfsCore.isOfsProject(project));
        
        GenerationCore.toggleNature(project);
        Assert.assertTrue(GenerationCore.isTechnical(project));
        
        IPath locationPath = Platform.getLocation();
        locationPath = null;
        desc.setLocation(locationPath);
        if (!project.exists()) project.create(desc, null);
        if (!project.isOpen()) project.open(null);

        return project;    
	}

}
