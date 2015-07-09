package com.temenos.t24.tools.eclipse.basic.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.IAbstractTextEditorHelpContextIds;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.ui.texteditor.ResourceAction;
import org.eclipse.ui.texteditor.TextOperationAction;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.extra.CustomSourceInformationControl;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * T24BasicEditor extends TextEditor class which is the standard text editor for
 * file resources.
 */
public class T24BasicEditor extends TextEditor {

    private ColorManager colorManager;
    private Map<ProjectionAnnotation, Position> curAnnotations = new HashMap<ProjectionAnnotation, Position>();
    private ProjectionAnnotationModel annotationModel;
    private ProjectionSupport projectionSupport;
    /** Filename without prefix (e.g. ACCOUNT.MODULE) */
    private String basicFilenameNoPrefix = "";
    
    /*
     * Remote directory from where the file was retrieved e.g. GLOBUS.BP, BP
     * This field might be empty, if a file was opened locally.
     */
    private String serverDirectory = "";
    // Editors have a local file associated.
    // iFile is a reference to it.
    private IFile iFile = null;
    // isLocal: true => this editor represents a file opened locally,
    // false => this editor shows a file retrieved and opened from the server.
    private boolean isLocal = true;

    public T24BasicEditor() {
        super();
    }

    /**
     * InitialiSes this editor.
     */
    @Override
    protected void initializeEditor() {
        colorManager = new ColorManager();
        // Two elements are configured here:
        // 1.- SourceViewerConfiguration: adds new features to the editor, such
        // as formatting, syntax highlighting, text hovering, etc.
        // 2.- DocumentProvider: establishes a bridge between a file on a disk,
        // and
        // its representation as a document in memory.
        setSourceViewerConfiguration(new T24BasicSourceViewerConfiguration(this, colorManager));
        setDocumentProvider(new T24BasicDocumentProvider());
        super.initializeEditor();
    }

    public ISourceViewer getEditorSourceViewer() {
        return getSourceViewer();
    }

    public ProjectionAnnotationModel getProjectionAnnotationModel() {
        return annotationModel;
    }

    /**
     * Returns the OverviewRuler associated with this editor. This ruler is an
     * overview annotation presentation area for the text editor. Typically it
     * is the ruler on the right hand side to the editor.
     */
    @Override
    public IOverviewRuler getOverviewRuler() {
        return super.getOverviewRuler();
    }

    /**
     * Saves current editor contents.
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSaveAs() {
        super.doSaveAs();
    }

    /**
     * Does perform the SaveAs action by calling the performSaveAs method of the
     * super class.
     * 
     * @return true if save as succeeded. false otherwise.
     */
    public boolean performSaveAsLocal() {
        // Instead of doSaveAs, performSaveAs is called to identify if the user
        // has cancelled the operation.
        IProgressMonitor monitor = getProgressMonitor();
        performSaveAs(monitor);
        if (monitor.isCanceled()) {
            return false;
        }
        return true;
    }

    public void setIFile(IFile iFile) {
        this.iFile = iFile;
    }

    /**
     * Returns the IFile associated with this TextEditor.
     * 
     * @return IFile associated with current TextEditor.
     */
    public IFile getIFile() {
        return this.iFile;
    }

    public void setServerDirectory(String serverDirectory) {
        this.serverDirectory = serverDirectory;
    }

    public String getServerDirectory() {
        return serverDirectory;
    }

    public void setBasicFilenameNoPrefix(String basicFilenameNoPrefix) {
        this.basicFilenameNoPrefix = basicFilenameNoPrefix;
    }

    public String getBasicFilenameNoPrefix() {
        return basicFilenameNoPrefix;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    // ***************************************************************************************
    // START REGIONS - FOLDERS
    // ***************************************************************************************
    /**
     * Method overwritten in order to handle collapsible regions.
     */
    @Override
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        createAnnotationModel();
    }

    private void createAnnotationModel() {
        ProjectionViewer projectionViewer = (ProjectionViewer) getSourceViewer();
        projectionSupport = new ProjectionSupport(projectionViewer, getAnnotationAccess(), getSharedColors());
        projectionSupport.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error");
        projectionSupport.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning");
        projectionSupport.setHoverControlCreator(new IInformationControlCreator() {

            public IInformationControl createInformationControl(Shell shell) {
                IInformationControl iic = (IInformationControl) new CustomSourceInformationControl(shell,
                        IDocument.DEFAULT_CONTENT_TYPE);
                iic.setVisible(true);
                iic.setFocus();
                return iic;
            }
        });
        projectionSupport.install();
        // turn projection mode on
        if (!projectionViewer.isProjectionMode()) {
            projectionViewer.doOperation(ProjectionViewer.TOGGLE);
        }
        annotationModel = projectionViewer.getProjectionAnnotationModel();
    }

    /**
     * Updates the annotations registered with this editor.
     * 
     * @param positions
     */
    public void updateFoldingStructure(ArrayList<Position> newPositions) {
        Map<ProjectionAnnotation, Position> saveAllAnnotations = new HashMap<ProjectionAnnotation, Position>();
        saveAllAnnotations.putAll(curAnnotations);
             
        try {
            String setDefaultCollapsedProperty = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24_ALWAYS_COLLAPSE_REGION);        
            boolean setDefaultCollapsed  = Boolean.parseBoolean(setDefaultCollapsedProperty);
            // this will hold the new annotations along
            // with their corresponding positions
            Map<ProjectionAnnotation, Position> newAnnotations = new HashMap<ProjectionAnnotation, Position>();
            for (int i = 0; i < newPositions.size(); i++) {
                ProjectionAnnotation an = annotationAlreadyExists((Position) newPositions.get(i));
                ProjectionAnnotation annotation;
                if (an == null) {
                    // The annotation doesn't exist yet, so create it brand new.
                    annotation = new ProjectionAnnotation();
                    annotation.setRangeIndication(false);
                    annotation.setText("Annotation " + i);
                    if (setDefaultCollapsed) {
                        annotation.markCollapsed();
                    } else {
                        annotation.markExpanded();
                    }
                    Position pos = newPositions.get(i);
                    newAnnotations.put(annotation, pos);
                    saveAllAnnotations.put(annotation, pos);
                }else{
                    if (setDefaultCollapsed) {
                        an.markCollapsed();
                    } else {
                        an.markExpanded();
                    }
                    newAnnotations.put(an, newPositions.get(i));
                    saveAllAnnotations.put(an, newPositions.get(i));
                }
                
            }
            Set<ProjectionAnnotation> keys = curAnnotations.keySet();
            Iterator<ProjectionAnnotation> keyIt = keys.iterator();
            Annotation[] annotations = new Annotation[curAnnotations.size()];
            int annotationCount = 0;
            while (keyIt.hasNext()) {
                ProjectionAnnotation an = (ProjectionAnnotation) keyIt.next();
                annotations[annotationCount++] = an;
                saveAllAnnotations.remove(an);
            }
            if (annotationModel != null) {
                annotationModel.modifyAnnotations(annotations, newAnnotations, null);
            } else {
                createAnnotationModel();
            }
            curAnnotations = saveAllAnnotations;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if in the passed position exists an annotation already. If it
     * does, then return the annotation, otherwise it'll return null.
     * 
     * @param pos
     */
    public ProjectionAnnotation annotationAlreadyExists(Position pos) {
        Set<ProjectionAnnotation> keySet = curAnnotations.keySet();
        Iterator<ProjectionAnnotation> keyIt = keySet.iterator();
        while (keyIt.hasNext()) {
            ProjectionAnnotation an = (ProjectionAnnotation) keyIt.next();
            Position p = (Position) curAnnotations.get((Object) an);
            if (p.getOffset() == pos.getOffset()) {
                curAnnotations.remove(an);
                return an;
            }
        }
        // if this point is reached then the position wasn't found.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class clazz) {
        if (projectionSupport != null) {
            Object adapter = projectionSupport.getAdapter(getSourceViewer(), clazz);
            if (adapter != null)
                return adapter;
        }
        return super.getAdapter(clazz);
    }

    /**
     * {@inheritDoc} The viewer is responsible for displaying the content of the
     * document and is defined by
     * <code>org.eclipse.jface.text.ITextViewer</code> interface.
     * 
     * @param parent the parent control
     * @param ruler the vertical ruler
     * @param styles style bits, <code>SWT.WRAP</code> is currently not
     *            supported
     * @return the source viewer
     */
    @Override
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
        ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
        // ensure decoration support has been created and configured.
        getSourceViewerDecorationSupport(viewer);
        return viewer;
    }

    /**
     * Needed for content assistant in the Text viewer. This ensures actions
     * such as key press would trigger content assistant
     * 
     */
    @SuppressWarnings("deprecation")
    protected void createActions() {
        super.createActions();
        ResourceBundle bundle = T24BasicPlugin.getDefault().getResourceBundle();
        String id = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
        Action action = new ContentAssistAction(bundle, "ContentAssistProposal", this);
        action.setActionDefinitionId(id);
        setAction("ContentAssistProposal", action);
        this.markAsContentDependentAction(id, true);
        ResourceAction printAction = new TextOperationAction(bundle, "Editor.Print.", this, ITextOperationTarget.PRINT, true);
        String printid = IWorkbenchActionDefinitionIds.PRINT;
        printAction.setHelpContextId(IAbstractTextEditorHelpContextIds.PRINT_ACTION);
        printAction.setActionDefinitionId(printid);
        setAction(ITextEditorActionConstants.PRINT, new PrintActionDecorator(printAction));
        this.markAsContentDependentAction(printid, true);
    }

    /**
     * Returns annotation hover instance to the source viewer configuration. For
     * the subclasses to override
     * 
     * @return null
     */
    protected IAnnotationHover getAnnotationHover() {
        // Subclasses may override to provide their own annotation hover objects
        return null;
    }
    // ***************************************************************************************
    // START OUTLINE
    // ***************************************************************************************
    /**
     * Note: this code is not yet to be used. It is here for future development
     * and proof of concept purposes. It'll be used for sending the editor
     * contents to the Outline View in the form of a Tree
     */
    // private T24EditorContentOutlinePage outlinePage;
    // public Object getAdapter(Class adapterType) {
    // if (IContentOutlinePage.class.equals(adapterType)) {
    // if (outlinePage == null) {
    // outlinePage = new T24EditorContentOutlinePage(this);
    // if (getEditorInput() != null) {
    // outlinePage.setInput(getEditorInput());
    // }
    // }
    // return outlinePage;
    // }
    // return super.getAdapter(adapterType);
    // }
    // ***************************************************************************************
    // END OUTLINE
    // ***************************************************************************************
    
   
}
