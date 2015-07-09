package com.temenos.t24.tools.eclipse.basic.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.temenos.t24.tools.eclipse.basic.IDocViewProvider;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Multipage editor which comprises 2 pages: page 0 contains a T24BasicEditor
 * (fullEditor) page 1 contains code only, no comments (codeOnlyEditor)
 * Implements ITextEditor, so this class can be extended by other editors. E.g.
 * public class MyEditor extends T24BasicMultiPageEditor Now MyEditor can be
 * added as an editor extension in the MANIFEST.MF.
 */
public class T24BasicMultiPageEditor extends MultiPageEditorPart implements IResourceChangeListener, ITextEditor {

    // full editor = holds code and comments
    private T24RemoteFileEditor fullEditor;
    // code only = no comments
    private T24RemoteFileEditor codeOnlyEditor;

    /**
     * Creates a multi-page editor
     */
    public T24BasicMultiPageEditor() {
        super();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    }

    /**
     * Update the editor's title based upon the content being edited.
     */
    void updateTitle() {
        IEditorInput input = getEditorInput();
        setPartName(input.getName());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class adapter) {
        return fullEditor.getAdapter(adapter);
    }

    /**
     * Creates the pages of the multi-page editor.
     */
    @Override
    protected void createPages() {
        /** Page with code and comments. */
        createFullPage();
        addMouseListener(fullEditor);
        /** Page with only code, no comments. */
        createCodeOnlyPage();
        /** update title of editor. */
        updateTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActivePage() {
        return super.getActivePage();
    }

    public String getTitleToolTip() {
        if (fullEditor.isLocal()) {
            return getEditorInput().getToolTipText();
        }
        return fullEditor.getServerDirectory() + "/" + fullEditor.getBasicFilenameNoPrefix();
    }

    /**
     * Creates page with code and comments
     */
    void createFullPage() {
        try {
            fullEditor = new T24RemoteFileEditor();
            int index = addPage(fullEditor, getEditorInput());
            setPageText(index, fullEditor.getTitle());
            setBackgroundColor(fullEditor);
            // Now make this editor as editable
            boolean editable = fullEditor.validateEditorInputState();
            Object control = ((AbstractTextEditor) fullEditor).getAdapter(Control.class);
            if (control instanceof StyledText) {
                ((StyledText) control).setEditable(editable);
            }
            // Link the underlying File to the T24Basic editor
            IFile iFile = (IFile) getEditorInput().getAdapter(IFile.class);
            fullEditor.setIFile(iFile);           
            IDocViewProvider provider = T24BasicPlugin.getDefault().getProvider();
            if(provider != null) {
                provider.showView(fullEditor.getPartName());
            }
//            T24DocViewerUtil.getActivePage().showView(ComponentLandscapeView.VIEW_ID);
        } catch (PartInitException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMouseListener(T24RemoteFileEditor editor) {
        new MouseListener() {

            public void mouseDoubleClick(MouseEvent e) {
                System.out.println("double click");
            }

            public void mouseDown(MouseEvent e) {
                System.out.println("mouseDown");
            }

            public void mouseUp(MouseEvent e) {
                System.out.println("mouseUp");
            }
        };
    }

    /**
     * Sets the background colour.
     * 
     * @param editor
     */
    public void setBackgroundColor(T24RemoteFileEditor editor) {
        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
        RGB colorRGB = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_BACKGROUND);
        ColorManager colorManager = new ColorManager();
        Color color = colorManager.getColor(colorRGB);
        ISourceViewer sv = editor.getEditorSourceViewer();
        sv.getTextWidget().setBackground(color);
    }

    /**
     * Creates page with code only (no comments)
     */
    void createCodeOnlyPage() {
        try {
            codeOnlyEditor = new T24RemoteFileEditor();
            int index = addPage(codeOnlyEditor, getEditorInput());
            refreshCodeOnlyDoc();
            setPageText(index, "CodeOnly");
            setBackgroundColor(codeOnlyEditor);
            // Now make this editor as read-only
            fullEditor.validateEditorInputState();
            Object control = ((AbstractTextEditor) codeOnlyEditor).getAdapter(Control.class);
            if (control instanceof StyledText) {
                ((StyledText) control).setEditable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The <code>MultiPageEditorPart</code> implementation of this
     * <code>IWorkbenchPart</code> method disposes all nested editors.
     * Subclasses may extend.
     */
    @Override
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * Saves the multi-page editor's document.
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        // Save the editor with code and comments
        fullEditor.doSave(monitor);
        refreshCodeOnlyDoc();
    }

    /**
     * Saves the multi-page editor's document as another file. Also updates the
     * text for page 0's tab, and updates this multi-page editor's input to
     * correspond to the nested editor's.
     */
    @Override
    public void doSaveAs() {
        fullEditor.doSaveAs();
        setInput(fullEditor.getEditorInput());
        updateTitle();
    }

    /**
     * Does perform SaveAs locally. Updates editor title after a successful save
     * 
     * @return true if save as succeeded. false otherwise.
     */
    public boolean performSaveAsLocal() {
        if (fullEditor.performSaveAsLocal()) {
            setInput(fullEditor.getEditorInput());
            updateTitle();
            updateTitleImage();
            return true;
        }
        return false;
    }

    /**
     * Through this method the framework knows if the editor has changed, and
     * add for example an asterisk at the top of the label name. We are only
     * interested in changes to the fullEditor not to the codeOnlyEditor.
     */
    @Override
    public boolean isDirty() {
        return fullEditor.isDirty();
    }

    /**
     * Updates the contents of the codeOnly page with whatever contents (without
     * the comments) have the full editor.
     */
    public void refreshCodeOnlyDoc() {
        IDocument fullDoc = EditorDocumentUtil.getDocument(fullEditor);
        IDocument codeOnlyDoc = EditorDocumentUtil.getDocument(codeOnlyEditor);
        String codeOnlyString = EditorDocumentUtil.getCodeOnly(fullDoc.get());
        codeOnlyDoc.set(codeOnlyString);
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart
     */
    public void gotoMarker(IMarker marker) {
        setActivePage(0);
        IDE.gotoMarker(getEditor(0), marker);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
        super.init(site, editorInput);
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart.
     */
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * Calculates the contents of page 2 when the it is activated.
     */
    protected void pageChange(int newPageIndex) {
        super.pageChange(newPageIndex);
    }

    /**
     * Closes all project files on project close.
     */
    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
                    for (int i = 0; i < pages.length; i++) {
                        if (((FileEditorInput) fullEditor.getEditorInput()).getFile().getProject().equals(event.getResource())) {
                            IEditorPart editorPart = pages[i].findEditor(fullEditor.getEditorInput());
                            pages[i].closeEditor(editorPart, true);
                        }
                    }
                }
            });
        }
    }

    /**
     * @return the T24RemoteEditor associated
     */
    public T24RemoteFileEditor getSourceEditor() {
        return this.fullEditor;
    }

    /**
     * @return the code only editor associated
     */
    public T24RemoteFileEditor getCodeOnlyEditor() {
        return codeOnlyEditor;
    }

    /**
     * Updates the title image of the editor based on the nature of the file.
     * Local and remote files will take different images.
     */
    public void updateTitleImage() {
        if (fullEditor.isLocal()) {
            setTitleImage(EclipseUtil.getImage(PluginConstants.LOCAL_FILE_IMAGE_PATH));
        } else {
            setTitleImage(EclipseUtil.getImage(PluginConstants.REMOTE_FILE_IMAGE_PATH));
        }
    }

    public void close(boolean save) {
    }

    public void doRevertToSaved() {
    }

    public IAction getAction(String actionId) {
        return getSourceEditor().getAction(actionId);
    }

    public IDocumentProvider getDocumentProvider() {
        return getSourceEditor().getDocumentProvider();
    }

    public IRegion getHighlightRange() {
        return null;
    }

    public ISelectionProvider getSelectionProvider() {
        return getSourceEditor().getSelectionProvider();
    }

    public boolean isEditable() {
        return true;
    }

    public void removeActionActivationCode(String actionId) {
    }

    public void resetHighlightRange() {
    }

    public void selectAndReveal(int offset, int length) {
    }

    public void setAction(String actionID, IAction action) {
        getSourceEditor().setAction(actionID, action);
    }

    public void setActionActivationCode(String actionId, char activationCharacter, int activationKeyCode, int activationStateMask) {
        getSourceEditor().setActionActivationCode(actionId, activationCharacter, activationKeyCode, activationStateMask);
    }

    public void setHighlightRange(int offset, int length, boolean moveCursor) {
    }

    public void showHighlightRangeOnly(boolean showHighlightRangeOnly) {
    }

    public boolean showsHighlightRangeOnly() {
        return false;
    }
}
