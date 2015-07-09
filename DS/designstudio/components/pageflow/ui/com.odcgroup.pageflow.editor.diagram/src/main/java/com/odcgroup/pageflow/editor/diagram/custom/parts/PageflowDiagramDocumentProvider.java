package com.odcgroup.pageflow.editor.diagram.custom.parts;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramEditorInput;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramModificationListener;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorUtil;

/**
 * @author pkk
 *
 */
public class PageflowDiagramDocumentProvider extends AbstractDocumentProvider implements IDiagramDocumentProvider {

	/**
	 * Bundle of all required information to allow {@link org.eclipse.core.resources.IStorage} as underlying document resources.
	 */
	protected class DiagramResourceInfo extends ElementInfo {

		/** The flag representing the cached state whether the storage is modifiable. */
		public boolean fIsModifiable= false;
		/** The flag representing the cached state whether the storage is read-only. */
		public boolean fIsReadOnly= true;
		/** The flag representing the need to update the cached flag.  */
		public boolean fUpdateCache= true;
		
		public DiagramModificationListener fListener = null;
	
		/**
		 * Creates a new storage info.
		 *
		 * @param document the document
		 * @param model the annotation model
		 */
		public DiagramResourceInfo(IDocument document, DiagramModificationListener listener) {
			super(document);
			fListener = listener;
		}
	}
	
	/*
	 * @see AbstractDocumentProvider#createDocument(Object)
	 */
	protected IDocument createDocument(Object element) throws CoreException {
	
		if (element instanceof IDiagramEditorInput) {
			IDocument document= createEmptyDocument();
			if (setDocumentContent(document, (IEditorInput) element)) {
				setupDocument(element, document);
				return document;
			}
		}
	
		return null;
	}
	
	/**
	 * Sets up the given document as it would be provided for the given element. The
	 * content of the document is not changed. This default implementation is empty.
	 * Subclasses may reimplement.
	 *
	 * @param element the blue-print element
	 * @param document the document to set up
	 */
	protected void setupDocument(Object element, IDocument document) {
		// for subclasses
	}
	
	/**
	 * Factory method for creating empty documents.
	 * @return the newly created document
	 * 
	 */
	protected IDocument createEmptyDocument() {
		DiagramDocument document = new DiagramDocument();
		document.setEditingDomain(createEditingDomain());
		return document;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.AbstractDocumentProvider#createElementInfo(java.lang.Object)
	 */
	protected ElementInfo createElementInfo(Object element) throws CoreException {
		if (element instanceof IDiagramEditorInput) {
			IDiagramEditorInput input = (IDiagramEditorInput)element;
			Diagram diagram = input.getDiagram();
			IDocument document= null;
			IStatus status= null;
	
			try {
				document= createDocument(element);
			} catch (CoreException x) {
				handleCoreException(x, "PageflowDiagramDocumentProvider.createElementInfo");
				status= x.getStatus();
				document= createEmptyDocument();
			}
	
			DiagramResourceInfo info= new DiagramResourceInfo(document, null);			
			info.fStatus= status;
	
			return info;
		}
	
		return super.createElementInfo(element);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider#disposeElementInfo(java.lang.Object, org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider.ElementInfo)
	 */
	protected void disposeElementInfo(Object element, ElementInfo info) {
		super.disposeElementInfo(element, info);
	}
	
	/**
	 * Initializes the given document from the given editor input using the given character encoding.
	 *
	 * @param document the document to be initialized
	 * @param editorInput the input from which to derive the content of the document
	 * @param encoding the character encoding used to read the editor input
	 * @return <code>true</code> if the document content could be set, <code>false</code> otherwise
	 * @throws CoreException if the given editor input cannot be accessed
	 * 
	 */
	protected boolean setDocumentContent(IDocument document, IEditorInput editorInput) throws CoreException {
		if (editorInput instanceof IDiagramEditorInput) {
			Diagram diagram = ((IDiagramEditorInput) editorInput).getDiagram();
			document.setContent(diagram);
			return true;
		}
		return false;
	}
	
	/*
	 * @see AbstractDocumentProvider#doSaveDocument(IProgressMonitor, Object, IDocument, boolean)
	 */
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
		// for subclasses
		DiagramResourceInfo info= (DiagramResourceInfo) getElementInfo(element);
		if (info != null) {
			if (!info.fIsReadOnly){
				try {
					info.fListener.stopListening();	
					monitor.beginTask("Saving Diagram", 1);
					if (element instanceof IDiagramEditorInput) {
						Diagram diagram = ((IDiagramEditorInput) element).getDiagram();
						diagram.eResource().save(PageflowDiagramEditorUtil.getSaveOptions());					
					}
					monitor.worked(1);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					info.fListener.startListening();
				}
				monitor.done();
			}
		}
	}
	
	/**
	 * Defines the standard procedure to handle <code>CoreExceptions</code>. Exceptions
	 * are written to the plug-in log.
	 *
	 * @param exception the exception to be logged
	 * @param message the message to be logged
	 * 
	 */
	protected void handleCoreException(CoreException exception, String message) {
	
		Bundle bundle = Platform.getBundle(PlatformUI.PLUGIN_ID);
		ILog log= Platform.getLog(bundle);
	
		if (message != null)
			log.log(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message, exception));
		else
			log.log(exception.getStatus());
	}
	
	/**
	 * Updates the internal cache for the given input.
	 *
	 * @param input the input whose cache will be updated
	 * @throws CoreException if the storage cannot be retrieved from the input
	 * 
	 */
	protected void updateCache(IDiagramEditorInput input) throws CoreException {
		DiagramResourceInfo info= (DiagramResourceInfo) getElementInfo(input);
		if (info != null) {
						
			info.fIsModifiable = false;
			info.fIsReadOnly = true;			
			info.fUpdateCache= false;
		}
	}
	
	/*
	 * @see IDocumentProviderExtension#isReadOnly(Object)
	 * 
	 */
	public boolean isReadOnly(Object element) {
		return true;
	}
	
	/*
	 * @see IDocumentProviderExtension#isModifiable(Object)
	 * 
	 */
	public boolean isModifiable(Object element) {
		return false;
	}
	
	/*
	 * @see AbstractDocumentProvider#doUpdateStateCache(Object)
	 * 
	 */
	protected void doUpdateStateCache(Object element) throws CoreException {
		if (element instanceof IDiagramEditorInput) {
			DiagramResourceInfo info= (DiagramResourceInfo) getElementInfo(element);
			if (info != null)
				info.fUpdateCache= true;
		}
		super.doUpdateStateCache(element);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.AbstractDocumentProvider#getOperationRunner(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editor.IDiagramDocumentProvider#getDiagramDocument(java.lang.Object)
	 */
	public IDiagramDocument getDiagramDocument(Object element) {
		IDocument doc = getDocument(element);
		if(doc instanceof IDiagramDocument)
			return (IDiagramDocument)doc;
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider#createInputWithEditingDomain(org.eclipse.ui.IEditorInput, org.eclipse.gmf.runtime.emf.core.edit.MEditingDomain)
	 */
	public IEditorInput createInputWithEditingDomain(IEditorInput editorInput, TransactionalEditingDomain domain) {
		return editorInput;
	}
	
	/**
	 * @return
	 */
	private TransactionalEditingDomain createEditingDomain() {
		TransactionalEditingDomain editingDomain = DiagramEditingDomainFactory
				.getInstance().createEditingDomain();
		editingDomain
				.setID("com.odcgroup.pageflow.editor.diagram.EditingDomain"); //$NON-NLS-1$
		final NotificationFilter diagramResourceModifiedFilter = NotificationFilter
				.createNotifierFilter(editingDomain.getResourceSet()).and(
						NotificationFilter
								.createEventTypeFilter(Notification.ADD)).and(
						NotificationFilter.createFeatureFilter(
								ResourceSet.class,
								ResourceSet.RESOURCE_SET__RESOURCES));
		editingDomain.getResourceSet().eAdapters().add(new Adapter() {

			private Notifier myTarger;

			public Notifier getTarget() {
				return myTarger;
			}

			public boolean isAdapterForType(Object type) {
				return false;
			}

			public void notifyChanged(Notification notification) {
				if (diagramResourceModifiedFilter.matches(notification)) {
					Object value = notification.getNewValue();
					if (value instanceof Resource) {
						((Resource) value).setTrackingModification(true);
					}
				}
			}

			public void setTarget(Notifier newTarget) {
				myTarger = newTarget;
			}

		});

		return editingDomain;
	}

}
