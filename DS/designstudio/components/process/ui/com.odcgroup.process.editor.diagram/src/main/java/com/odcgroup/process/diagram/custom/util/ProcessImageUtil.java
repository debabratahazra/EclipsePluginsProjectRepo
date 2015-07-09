package com.odcgroup.process.diagram.custom.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

public class ProcessImageUtil {
	
	/**
	 * @param file
	 * @param path
	 * @param imageFormat
	 * @throws CoreException
	 */
	public static void createImage(URI uri, String fileName, IPath path, ImageFileFormat imageFormat) throws CoreException {
		Resource notationModel = getDiagramNotationalModel(uri);
		if(notationModel == null)
			throw new RuntimeException("Unable to load the diagram from the given file");
		Iterator<EObject> rootContents = notationModel.getContents().iterator();
		while(rootContents.hasNext()) {
			EObject rootElement = (EObject)rootContents.next();
			if(rootElement instanceof Diagram){
				Diagram diagram =  (Diagram)rootElement;
				IPath destinationPath = path.append(fileName+"."+imageFormat.getName().toLowerCase());
				new CopyToImageUtil().copyToImage(diagram, destinationPath, imageFormat, 
						new NullProgressMonitor(), ProcessDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);					
			}
		}
	}
	
	/**
	 * @param file
	 * @param path
	 * @param imageFormat
	 * @throws CoreException
	 */
	public static void createImage(IFile file, IPath path, ImageFileFormat imageFormat) throws CoreException{
		if (file != null) {
			String fileName = file.getLocation().removeFileExtension().lastSegment();
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			
			createImage(uri, fileName, path, imageFormat);
		}
	}
	
	/**
	 * @return
	 */
	private static TransactionalEditingDomain getEditingDomain() {
		TransactionalEditingDomain editingDomain = DiagramEditingDomainFactory.getInstance().createEditingDomain();
		editingDomain.setID("com.odcgroup.process.editor.diagram.EditingDomain");
		return editingDomain;
	}
	
	/**
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Resource getDiagramNotationalModel(URI uri) {
		TransactionalEditingDomain domain = getEditingDomain();
		Resource diagram = domain.getResourceSet().getResource(uri, false);
		if (diagram == null) {
			diagram = domain.getResourceSet().createResource(uri);
        }
		if (!diagram.isLoaded()) {
			Map loadingOptions = new HashMap(GMFResourceFactory.getDefaultLoadOptions());     
            try {
            	diagram.load(loadingOptions);
            } catch (IOException e) {
            	diagram.unload();
            	e.printStackTrace();
            }
		}
		return diagram;
	}

}
