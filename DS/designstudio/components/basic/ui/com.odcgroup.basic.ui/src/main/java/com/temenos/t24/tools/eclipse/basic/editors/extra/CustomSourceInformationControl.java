package com.temenos.t24.tools.eclipse.basic.editors.extra;

/**
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * Contributors:
 *     IBM Corporation - initial API and implementation
 */
import org.eclipse.jdt.internal.ui.text.java.hover.SourceViewerInformationControl;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Source viewer used to display quick diff hovers.
 * @since 3.0
 */
public class CustomSourceInformationControl extends SourceViewerInformationControl {

     /** The font name for the viewer font - the same as the java editor's. */ 
    private static final String SYMBOLIC_FONT_NAME = "org.eclipse.jdt.ui.editors.textfont";

     /** The maximum width of the control, set in <code>setSizeConstraints(int, int)</code>. */ 
    int fMaxWidth = 100;//Integer.MAX_VALUE;

     /** The maximum height of the control, set in <code>setSizeConstraints(int, int)</code>. */ 
    int fMaxHeight = 100;//Integer.MAX_VALUE;

     /** The partition type to be used as the starting partition type by the paritition scanner. */ 
    private String fPartition;

    /** The horizontal scroll index. */
    private int fHorizontalScrollPixel;

    public void setSizeConstraints(int maxWidth, int maxHeight) {
        fMaxWidth = maxWidth;
        fMaxHeight = maxHeight;
    }

    /**
     * Creates a new information control.
     * 
     * @param parent the shell that is the parent of this hover / control
     * @param partition the initial partition type to be used for the underlying viewer
     */
    public CustomSourceInformationControl(Shell parent, String partition) {
        super(parent,true, SWT.NONE, null);
        setViewerFont();
        setStartingPartitionType(partition);
    }

    public Point computeSizeHint() {
        Point size = super.computeSizeHint();
        size.x = Math.min(size.x, fMaxWidth);
        size.y = Math.min(size.y, fMaxHeight);
        return size;
    }

    /**
     * Sets the font for this viewer sustaining selection and scroll position.
     */
    private void setViewerFont() {
        Font font = JFaceResources.getFont(SYMBOLIC_FONT_NAME);
        IDocument document = getViewer().getDocument();
        if (document != null) {
            Point selection = getViewer().getSelectedRange();
            int topIndex = getViewer().getTopIndex();
            StyledText styledText = getViewer().getTextWidget();
            Control parent = styledText;
            if (getViewer() instanceof ITextViewerExtension) {
                ITextViewerExtension extension = (ITextViewerExtension) getViewer();
                parent = extension.getControl();
            }
            parent.setRedraw(false);
            styledText.setFont(font);
            getViewer().setSelectedRange(selection.x, selection.y);
            getViewer().setTopIndex(topIndex);
            if (parent instanceof Composite) {
                Composite composite = (Composite) parent;
                composite.layout(true);
            }
            parent.setRedraw(true);
        } else { 
            StyledText styledText = getViewer().getTextWidget();
            styledText.setFont(font);
        }
    }

    /**
     * Sets the initial partition for the underlying source viewer.
     * 
     * @param partition the partition type
     */
    public void setStartingPartitionType(String partition) {
        if (partition == null)
            fPartition = IDocument.DEFAULT_CONTENT_TYPE;
        else
            fPartition = partition;
    }

    public void setInformation(String content) {
        super.setInformation(content);
        IDocument doc = getViewer().getDocument();
        if (doc == null)
            return;
        ensureScrollable();
        String start = null;
        if (IJavaPartitions.JAVA_DOC.equals(fPartition)) {
            start = "/**" + doc.getLegalLineDelimiters()[0];
        } else if (IJavaPartitions.JAVA_MULTI_LINE_COMMENT.equals(fPartition)) {
            start = "/*" + doc.getLegalLineDelimiters()[0];
        }
        if (start != null) {
            try {
                doc.replace(0, 0, start);
                int startLen = start.length();
                getViewer().setDocument(doc, startLen, doc.getLength() - startLen);
            } catch (BadLocationException e) {
                Assert.isTrue(false);
            }
        }
        getViewer().getTextWidget().setHorizontalPixel(fHorizontalScrollPixel);
    }

    /**
     * Ensures that the control can be scrolled at least to
     * <code>fHorizontalScrollPixel</code> and adjusts <code>fMaxWidth</code>
     * accordingly.
     */
    private void ensureScrollable() {
        IDocument doc = getViewer().getDocument();
        if (doc == null)
            return;
        StyledText widget = getViewer().getTextWidget();
        if (widget == null || widget.isDisposed())
            return;
        int last = doc.getNumberOfLines() - 1;
        GC gc = new GC(widget);
        gc.setFont(widget.getFont());
        int maxWidth = 0;
        String content = new String();
        try {
            for (int i = 0; i <= last; i++) {
                IRegion line;
                line = doc.getLineInformation(i);
                content = doc.get(line.getOffset(), line.getLength());
                int width = gc.textExtent(content).x;
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
        } catch (BadLocationException e) {
            return;
        } finally {
            gc.dispose();
        }
        fMaxWidth = Math.max(0, Math.min(fMaxWidth, maxWidth - fHorizontalScrollPixel + 8));
    }

    public boolean hasContents() {
        return super.hasContents() && fMaxWidth > 0;
    }

    /**
     * Sets the horizontal scroll index in pixels.
     *  
     * @param scrollIndex the new horizontal scroll index
     */
    public void setHorizontalScrollPixel(int scrollIndex) {
        scrollIndex = Math.max(0, scrollIndex);
        fHorizontalScrollPixel = scrollIndex;
    }
}
