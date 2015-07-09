package com.temenos.t24.tools.eclipse.basic.editors.regions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

public class RegionReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
    private T24BasicEditor editor;
    private IDocument doc;
    private ArrayList<Position> positions = new ArrayList<Position>();
    
    public RegionReconcilingStrategy(){
        doc = EditorDocumentUtil.getActiveDocument();
    }

    public T24BasicEditor getEditor() {
        return editor;
    }
    public void setEditor(T24BasicEditor editor) {
        this.editor = editor;
    }
    public void setDocument(IDocument document) {
        this.doc = document;
    }

    // Triggers reconcilation
    public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
        initialReconcile();
    }

    // Triggers reconcilation
    public void reconcile(IRegion partition) {
        initialReconcile();
    }

    /**
     * Scan the whole document 
     */
    public void initialReconcile() {
        calculatePositions();
    }

    /**
     * uses {@link #fDocument}, {@link #fOffset} and {@link #fRangeEnd} to
     * calculate {@link #fPositions}. About syntax errors: this method is not a
     * validator, it is useful.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void calculatePositions() {
        // Reset and clear all the current positions (if any)
        positions.clear();
        
        // Parse document and find all the existing regions (if any)
        RegionUtil ru = new RegionUtil();
        Map regions = ru.findRegions(doc.get());
        Iterator<Integer> it = regions.keySet().iterator();
        while(it.hasNext()){
            int regionStartOffset = (int)(Integer)it.next();
            int regionEndOffset   = (int)(Integer)regions.get((Integer)regionStartOffset);
            this.addPosition(regionStartOffset, (regionEndOffset - regionStartOffset));
        }

        // Update the document folding structure in a separate thread.
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                editor.updateFoldingStructure(positions);
            }
        });
    }

    /**
     * Add a new position
     * @param startOffset - position offset starting point
     * @param length - length of the position
     */
    public void addPosition(int startOffset, int length) {
        positions.add(new Position(startOffset, length));
    }
    
    public void setProgressMonitor(IProgressMonitor monitor) {
    }    
}
