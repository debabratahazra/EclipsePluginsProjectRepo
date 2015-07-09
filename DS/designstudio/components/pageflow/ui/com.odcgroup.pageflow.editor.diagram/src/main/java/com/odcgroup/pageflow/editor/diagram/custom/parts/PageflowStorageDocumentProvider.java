package com.odcgroup.pageflow.editor.diagram.custom.parts;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageEditorInputProxy;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author pkk
 * DocumentProvider for Pageflow Model resources (IStorageType)
 */
public class PageflowStorageDocumentProvider extends StorageDocumentProvider implements IDiagramDocumentProvider {
	
	private class DiagramStorageInfo extends StorageInfo {
		public DiagramStorageInfo(IDocument document) {
			super(document);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.StorageDocumentProvider#createEmptyDocument()
	 */
	protected IDocument createEmptyDocument() {
		return new DiagramDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.IDiagramDocumentProvider#getDiagramDocument(java.lang.Object)
	 */
	public IDiagramDocument getDiagramDocument(Object element) {
		IDocument doc = getDocument(element);
		if (doc instanceof IDiagramDocument)
			return (IDiagramDocument) doc;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.AbstractDocumentProvider#disposeElementInfo(java.lang.Object,
	 *      org.eclipse.gmf.runtime.diagram.ui.editor.AbstractDocumentProvider.ElementInfo)
	 */
	protected void disposeElementInfo(Object element, ElementInfo info) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.StorageDocumentProvider#setDocumentContentFromStorage(org.eclipse.gmf.runtime.diagram.ui.editor.IDocument,
	 *      org.eclipse.core.resources.IStorage)
	 */
	protected void setDocumentContentFromStorage(IDocument document,
			IStorage storage) throws CoreException {
		IDiagramDocument diagramDocument = (IDiagramDocument) document;
		TransactionalEditingDomain domain = diagramDocument.getEditingDomain();
		URI resourceUri = null;
		// set the modelURIConverter
		Diagram diagram = null;
		if (storage instanceof IOfsModelResource){
			IOfsModelResource mResource = (IOfsModelResource)storage;
			resourceUri = mResource.getURI();
			IOfsProject ofsProject = mResource.getOfsProject();
			URIConverter converter = ofsProject.getModelResourceSet().getURIConverter();
			domain.getResourceSet().setURIConverter(converter);
			Resource resource = null;
			try {				
				resource = domain.getResourceSet().getResource(resourceUri.trimFragment(), false);
				if (resource == null) {
					resource = domain.getResourceSet().createResource(resourceUri.trimFragment());
				}
				if (!resource.isLoaded()) {
					try {
						resource.load(Collections.EMPTY_MAP);
					} catch (IOException e) {
						resource.unload();
						throw e;
					}
				}
				if (resourceUri.fragment() != null) {
					EObject rootElement = resource.getEObject(resourceUri.fragment());
					if (rootElement instanceof Diagram) {
						document.setContent((Diagram) rootElement);
						return;
					}
				} else {
					for (EObject  rootElement : resource.getContents()) {
						if (rootElement instanceof Diagram) {
							document.setContent((Diagram) rootElement);
							return;
						}
					}
				}
			} catch (IOException e) {
				handleError(e);
			}
		} else {
			URI uri = URI.createURI(storage.getName());
			Resource resource = domain.getResourceSet().createResource(uri);
			try {
				resource.load(storage.getContents(), Collections.EMPTY_MAP);
				diagram = fetchDiagram(resource.getContents());
			} catch (IOException e) {
				handleError(e);
			}
		}
		// set the resource URI
		if (resourceUri != null) {
			diagram.eResource().setURI(resourceUri);
		}
		document.setContent(diagram);
	}
	
	/**
	 * @param exception
	 * @throws CoreException
	 */
	private void handleError(Throwable exception) throws CoreException {
		String reason = exception.getLocalizedMessage();
		IStatus status = new Status(IStatus.ERROR, PageflowDiagramEditorPlugin.ID, 1, reason, exception);
		throw new CoreException(status);
	}
	
	/**
	 * @param contents
	 * @return
	 */
	private Diagram fetchDiagram(List<EObject> contents) {
		for (EObject obj : contents) {
			if (obj instanceof Diagram) {
				return (Diagram) obj;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDocumentProvider#setDocumentContent(org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument, org.eclipse.ui.IEditorInput)
	 */
	protected boolean setDocumentContent(IDocument document,
			IEditorInput editorInput) throws CoreException {
		if (editorInput instanceof StorageEditorInputProxy) {
			StorageEditorInputProxy diagramElement = (StorageEditorInputProxy) editorInput;
			((IDiagramDocument) document).setEditingDomain(diagramElement.getEditingDomain());
			boolean docContentSet = super.setDocumentContent(document,	editorInput);
			return docContentSet;
		}
		return super.setDocumentContent(document, editorInput);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider#createInputWithEditingDomain(org.eclipse.ui.IEditorInput,
	 *      org.eclipse.gmf.runtime.emf.core.edit.MEditingDomain)
	 */
	public IEditorInput createInputWithEditingDomain(IEditorInput editorInput,
			TransactionalEditingDomain domain) {
		if (editorInput instanceof IStorageEditorInput)
			return new StorageEditorInputProxy((IStorageEditorInput) editorInput, domain);
		assert false;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDocumentProvider#createNewElementInfo(org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument)
	 */
	public ElementInfo createNewElementInfo(IDocument document) {
		DiagramStorageInfo info = new DiagramStorageInfo(document);
		return info;
	}

}
