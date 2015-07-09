package com.odcgroup.process.editor.tests.setup;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.process.diagram.part.ProcessDiagramEditorUtil;

/**
 * @author pkk
 * @author mka
 *
 */
public abstract class AbstractProcessTechValidationTests extends
		AbstractProcessEditorTests {
	
	private String PROCESS_NAME = "";
	private static final String PROJECT_NAME ="processTech";
	
	public void setProcessName (String processName) {		
		PROCESS_NAME = processName;
	}
    
    /*
     * (non-Javadoc)
     * 
     * @see com.odcgroup.process.editor.tests.AbstractEditorTests#createDiagram()
     */
    protected void createDiagram() throws Exception {
        String pathName = getProject(PROJECT_NAME).getFullPath().toOSString() + "/"
                + PROCESS_NAME + ".workflow";
        URI diagramUri = URI.createPlatformResourceURI(pathName, false);
        Resource diagram = ProcessDiagramEditorUtil.createDiagram(diagramUri,
                new NullProgressMonitor(), PROCESS_NAME, PROCESS_NAME+" desc");
        String path = diagram.getURI().toPlatformString(true);
        IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(
                new Path(path));
        if (workspaceResource instanceof IFile) {
            setDiagramFile((IFile) workspaceResource);
            setResource(diagram);
            openDiagram();
        }
    }

}
