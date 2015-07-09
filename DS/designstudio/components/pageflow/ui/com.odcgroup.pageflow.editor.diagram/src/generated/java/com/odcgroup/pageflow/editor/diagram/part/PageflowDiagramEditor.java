/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.part;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorInput;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.odcgroup.pageflow.editor.diagram.custom.dnd.PageElementTransferDropListener;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowDiagramDocumentProvider;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowPaletteViewerProvider;
import com.odcgroup.pageflow.editor.diagram.custom.parts.PageflowStorageDocumentProvider;
import com.odcgroup.pageflow.editor.diagram.custom.properties.PageflowPropertySheetPage;
import com.odcgroup.pageflow.editor.diagram.navigator.PageflowNavigatorItem;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * @generated
 */
public class PageflowDiagramEditor extends DiagramDocumentEditor implements IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "com.odcgroup.pageflow.editor.diagram.ui.diagramContext"; //$NON-NLS-1$

	private PageflowDiagramDocumentProvider pdiagramProvider = null;

	/**
	 * @generated
	 */
	public PageflowDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * overriden - to configure droptargetlistener to listen to drop of local resources
	 * (from navigator/explorer) and to configure help
	 */
	protected void initializeGraphicalViewer() {
		getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_NEVER);
		super.initializeGraphicalViewer();
		getGraphicalViewer().addDropTargetListener(new PageElementTransferDropListener(getGraphicalViewer()));
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getGraphicalViewer().getControl(), IOfsHelpContextIds.WORKBENCH);
	}

	/**
	 * @generated
	 */
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new PageflowPaletteFactory().fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	protected PreferencesHint getPreferencesHint() {
		return PageflowDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	public String getContributorId() {
		return PageflowDiagramEditorPlugin.ID;
	}

	/**
	 * @generated NOT
	 */
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		} else if (type == IPropertySheetPage.class) {
			return new PageflowPropertySheetPage(this);
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated NOT
	 */
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			return PageflowDiagramEditorPlugin.getInstance().getDocumentProvider();
		} else if (input instanceof DiagramEditorInput) {
			if (pdiagramProvider == null) {
				pdiagramProvider = new PageflowDiagramDocumentProvider();
			}
		} else if (input instanceof IStorageEditorInput) {
			return new PageflowStorageDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider().getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated NOT
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			setDocumentProvider(PageflowDiagramEditorPlugin.getInstance().getDocumentProvider());
		} else if (input instanceof DiagramEditorInput) {
			if (pdiagramProvider == null) {
				pdiagramProvider = new PageflowDiagramDocumentProvider();
			}
			setDocumentProvider(pdiagramProvider);
		} else if (input instanceof IStorageEditorInput) {
			setDocumentProvider(new PageflowStorageDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input).getFile() : null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS.bind(Messages.PageflowDiagramEditor_SavingDeletedFile, original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor().getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog.openWarning(shell, Messages.PageflowDiagramEditor_SaveAsErrorTitle,
						Messages.PageflowDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor, newInput,
					getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog.openError(shell, Messages.PageflowDiagramEditor_SaveErrorTitle,
						Messages.PageflowDiagramEditor_SaveErrorMessage, x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			PageflowNavigatorItem item = new PageflowNavigatorItem(diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		DiagramEditorContextMenuProvider provider = new DiagramEditorContextMenuProvider(this,
				getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU, provider, getDiagramGraphicalViewer());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette#createPaletteViewerProvider()
	 * 
	 * OFSFMK-217 Add the possibility to "lock" palette item
	 * custom PaletteViewerProvider to adapt the palette mouselistener of 
	 * the palette viewer to lock the palette item holding SHIFT
	 */
	protected PaletteViewerProvider createPaletteViewerProvider() {
		getEditDomain().setPaletteRoot(createPaletteRoot(null));
		return new PageflowPaletteViewerProvider(getEditDomain(), getDiagramGraphicalViewer());
	}

}
