package com.temenos.t24.tools.eclipse.basic.help;

import java.util.Iterator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;
import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

public class TextHover implements ITextHover {

    /**
     * @param tv - TextViewer
     * @param offset - offset within document
     * @return IRegion - region with the passed offset
     */
    public IRegion getHoverRegion(ITextViewer tv, int offset) {
        return new Region(offset, 0);
    }

    /**
     * Returns a String of help text associated with the word hovered with the
     * mouse.
     * 
     * @param tv - TextViewer for the document's info
     * @param region - region hovered, needed to get the offset
     * @return String - text associated with the hovered word
     */
    public String getHoverInfo(ITextViewer tv, IRegion region) {
        String wordHovered = getWordHovered(tv, region.getOffset());
        findAndAnnotateHoveredWord(tv.getDocument(), wordHovered);
        String info = getHoverInfo(tv, region, region.getOffset());
        if (info != null && !StringUtil.isEmpty(info)) {
            return info;
        }
        return "";
    }

    /**
     * Returns a String of help text associated with the word hovered with the
     * mouse. If nothing found, it'll return null.
     */
    private String getHoverInfo(ITextViewer tv, IRegion region, int offset) {
        String help = null;
        try {
            String wordHovered = getWordHovered(tv, offset);
            String keywordHelpText = getHelpText(wordHovered);
            if (keywordHelpText.equals("")) {
                // No help found
                help = DocumentViewerUtil.getCommentDocument(tv.getDocument(), region);
            } else {
                // Help text found
                help = wordHovered + " " + keywordHelpText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return help;
    }

    // commented because of the old logic for showing in-line doc which stored
    // in xml
    // private String getDoc(String wordHovered) {
    // T24BasicEditor activeEditor = (T24BasicEditor)
    // EditorDocumentUtil.activeT24BasicMultiPageEditor.getSourceEditor();
    // String subroutineName = activeEditor.getBasicFilenameNoPrefix();
    // String t24Doc =
    // SubroutineDocumentSupplier.getGosubDocument(subroutineName, wordHovered);
    // return t24Doc;
    // }
    private String getWordHovered(ITextViewer tv, int offset) {
        IDocument doc = tv.getDocument();
        String docContent = doc.get();
        String wordHovered = (new StringUtil()).getWord(docContent, offset);
        return wordHovered;
    }

    /**
     * Returns a string of help associated to the word passed
     * 
     * @param word - word for which a help string is required
     * @return String - text with help. If no help is available it returns an
     *         empty string ""
     */
    private String getHelpText(String word) {
        // search for help text. If something found returns the text
        // otherwise returns ""
        String help = HelpCache.getInstance().getHelpTextFor(word);
        if (help == null)
            return "";
        else
            return help;
    }

    /**
     * Finds all the instances of the wordHovered passed, and adds annotations
     * to the right editor ruler.
     */
    @SuppressWarnings("unchecked")
    private void findAndAnnotateHoveredWord(IDocument document, String wordHovered) {
        try {
            setActiveEditor();
            /** Get current active editor */
            T24BasicEditor activeEditor = (T24BasicEditor) EditorDocumentUtil.activeT24BasicMultiPageEditor.getSourceEditor();
            ISourceViewer sv = activeEditor.getEditorSourceViewer();
            /** Clear previous annotations */
            IAnnotationModel annModel = sv.getAnnotationModel();
            Iterator<Annotation> it = annModel.getAnnotationIterator();
            while (it.hasNext()) {
                Annotation ann = (Annotation) it.next();
                if (ann.getType().equals("annotation.type.hovered.word")) {
                    sv.getAnnotationModel().removeAnnotation(ann);
                }
            }
            Color c = (new ColorManager()).getColor(PluginConstants.DEFAULT_COLOR_HOVERED_WORD_ANNOTATION);
            IOverviewRuler fOverviewRuler = activeEditor.getOverviewRuler();
            /**
             * Find next match of hovered word and keep on iterating and adding
             * annotations.
             */
            FindReplaceDocumentAdapter fr = new FindReplaceDocumentAdapter(document);
            IRegion foundRegion = fr.find(0, wordHovered, true, true, true, false);
            while (foundRegion != null) {
                if (fOverviewRuler != null) {
                    Annotation ann = new Annotation("annotation.type.hovered.word", false, "");
                    fOverviewRuler.setAnnotationTypeColor(ann.getType(), c);
                    fOverviewRuler.addAnnotationType(ann.getType());
                    fOverviewRuler.update();
                    fOverviewRuler.setAnnotationTypeLayer(ann.getType(), 1);
                    Position position = new Position(foundRegion.getOffset());
                    sv.getAnnotationModel().addAnnotation(ann, position);
                }
                /** Next search */
                foundRegion = fr.find(foundRegion.getOffset() + 1, wordHovered, true, true, true, false);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the active T24 Multipage editor if editor is null. This scenario
     * happen while the pre-opened editor presumed as open stage while eclipse
     * is loading.
     */
    private void setActiveEditor() {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

            public void run() {
                IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                IEditorPart editorPart = activePage.getActiveEditor();
                EditorDocumentUtil.activeT24BasicMultiPageEditor=(T24BasicMultiPageEditor)editorPart;
            }
        });
    }
}
