package com.temenos.t24.tools.eclipse.basic.editors;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.ContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.editors.hyperlink.T24HyperlinkDetector;
import com.temenos.t24.tools.eclipse.basic.editors.regions.RegionReconcilingStrategy;
import com.temenos.t24.tools.eclipse.basic.editors.scanners.CommentScanner;
import com.temenos.t24.tools.eclipse.basic.editors.scanners.DefaultScanner;
import com.temenos.t24.tools.eclipse.basic.editors.scanners.LiteralScanner;
import com.temenos.t24.tools.eclipse.basic.editors.scanners.T24BasicPartitionScanner;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;
import com.temenos.t24.tools.eclipse.basic.help.TextHover;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * Within the <code>SourceViewerConfiguration</code> subclasses is where most
 * of the UI-related helper objects, that Eclipse framework provides, lie.
 * Examples: Syntax highlighting, text hovers, code completion, document
 * partitioner, ... These are useful while creating the editor. They must be
 * configured in here.
 */
public class T24BasicSourceViewerConfiguration extends SourceViewerConfiguration {

    private static final boolean TEXT_HOVER_SWITCHED_ON = true;
    private LiteralScanner literalScanner;
    private CommentScanner commentScanner;
    private DefaultScanner defaultScanner;
    private ColorManager colorManager;
    private T24BasicEditor editor;

    /**
     * Constructor
     * 
     * @param colorManager
     */
    public T24BasicSourceViewerConfiguration(T24BasicEditor editor, ColorManager colorManager) {
        this.editor = editor;
        this.colorManager = colorManager;
    }

    /**
     * Initiates the mechanisms for opening a window with help text whenever the
     * mouse hovers a keyword
     */
    @Override
    public ITextHover getTextHover(ISourceViewer sv, String contentType) {
        if (TEXT_HOVER_SWITCHED_ON) {
            return new TextHover();
        } else {
            // No context-based help provided. No window with help will be
            // opened
            return null;
        }
    }

    @Override
    public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
        return super.getUndoManager(sourceViewer);
    }

    protected CommentScanner getCommentScanner() {
        if (commentScanner == null) {
            commentScanner = new CommentScanner(colorManager);
        }
        return commentScanner;
    }

    protected LiteralScanner getLiteralScanner() {
        if (literalScanner == null) {
            literalScanner = new LiteralScanner(colorManager);
        }
        return literalScanner;
    }

    protected DefaultScanner getDefaultScanner() {
        if (defaultScanner == null) {
            defaultScanner = new DefaultScanner(colorManager);
        }
        return defaultScanner;
    }

    /**
     * IPresentationReconciler is responsible for tracking changes to the
     * underlying IDocument. For each partition (or content) type (see scanner),
     * it holds a reference to an IPresentationDamager and IPresentationRepairer
     * instance.
     * 
     * Whenever a change is made to the underlying document, a notification is
     * sent to the IPresentationDamager for each content type that is the
     * affected. The IPresentationDamager in turn returns an IRegion indicating
     * the are of the document which needs to be rebuilt as a result of the
     * change.
     * 
     * This information is then passed on to the IPresentationRepairer, which is
     * responsible for reapplying the textual presentation for the affected
     * region.
     */
    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler = new PresentationReconciler();
        // Add damager/repairer for LITERAL
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getLiteralScanner());
        reconciler.setDamager(dr, T24BasicPartitionScanner.BASIC_LITERAL);
        reconciler.setRepairer(dr, T24BasicPartitionScanner.BASIC_LITERAL);
        // Add damager/repairer for COMMENT
        dr = new DefaultDamagerRepairer(getCommentScanner());
        reconciler.setDamager(dr, T24BasicPartitionScanner.BASIC_COMMENT);
        reconciler.setRepairer(dr, T24BasicPartitionScanner.BASIC_COMMENT);
        // Add damager/repairer for DEFAULT region
        dr = new DefaultDamagerRepairer(getDefaultScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        return reconciler;
    }

    /**
     * Returns all configured content types for the given source viewer. This
     * list tells the caller which content types must be configured for the
     * given source viewer, i.e. for which content types the given source
     * viewer's functionalities must be specified. It should always include
     * <code> IDocument.DEFAULT_CONTENT_TYPE </code>.
     * 
     * @param sourceViewer the source viewer to be configured by this
     *            configuration
     * @return the configured content types for the given viewer
     */
    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] { IDocument.DEFAULT_CONTENT_TYPE, T24BasicPartitionScanner.BASIC_COMMENT,
                T24BasicPartitionScanner.BASIC_LITERAL };
    }

    /**
     * Method overwritten Returns the content formatter ready to be used with
     * the given source viewer.
     * 
     * @param sourceViewer the source viewer to be configured by this
     *            configuration
     * @return a content formatter or <code>null</code> if formatting should
     *         not be supported
     */
    @Override
    public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
        ContentFormatter formatter = new ContentFormatter();
        return formatter;
    }

    /**
     * Builds a reconciler that handles collapsible regions.
     * 
     * @param
     * @return
     */
    @Override
    public IReconciler getReconciler(ISourceViewer sourceViewer) {
        RegionReconcilingStrategy strategy = new RegionReconcilingStrategy();
        strategy.setEditor(editor);
        MonoReconciler reconciler = new MonoReconciler(strategy, false);
        return reconciler;
    }

    @Override
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
        ContentAssistant ca = new ContentAssistant();
        ca.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        ca.setContentAssistProcessor(new T24BasicContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
        ca.setAutoActivationDelay(0);
        ca.enableAutoActivation(true);
        ca.setProposalSelectorBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        return ca;
    }

    /**
     * Gets the hyper link presenter which shows the hyper links in the text
     * viewer
     */
    @Override
    public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
        RGB hyperlinkColor = PreferenceConverter.getColor(EclipseUtil.getPreferenceStore(),
                PluginConstants.T24_EDITOR_COLOR_HYPERLINK);
        return new DefaultHyperlinkPresenter(hyperlinkColor);
    }

    /**
     * Detects the hyper link at the given region in the text viewer
     */
    @Override
    public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
        // Only one hyper link is detected at a time
        IHyperlinkDetector[] t24HyperlinkDetectors = new IHyperlinkDetector[1];
        t24HyperlinkDetectors[0] = new T24HyperlinkDetector();
        return t24HyperlinkDetectors;
    }

    /**
     * Returns the hyper link activation key
     */
    @Override
    public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
        return SWT.MOD1;
    }

   
    /**
     * Returns the annotation hover for the source viewer configuration
     */
    @Override
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return editor.getAnnotationHover();
    }
}
