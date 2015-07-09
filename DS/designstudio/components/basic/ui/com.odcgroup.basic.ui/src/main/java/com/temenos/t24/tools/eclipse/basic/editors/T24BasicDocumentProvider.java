package com.temenos.t24.tools.eclipse.basic.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.temenos.t24.tools.eclipse.basic.editors.scanners.T24BasicPartitionScanner;

/**
 * The document provider creates a bridge between a file on a disk
 * and its representation as a document (<code>IDocument</code>) in memory.
 * It handles tasks such as loading files from disk and saving them, 
 * telling whether the document has changed since it was last loaded, etc.  
 */
public class T24BasicDocumentProvider extends FileDocumentProvider {

     private T24BasicEditor editor;
    
	/**
	 * Creates a document of type IDocument, and partitions is in
	 * regions. This is done by scanning the document.
	 * @param element - typically an IEditorInput type
	 */
    @Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = null; 
		
		if (element instanceof T24BasicEditorInput) {
	        T24BasicEditorInput editorInput = (T24BasicEditorInput)element;
	        document = new Document();
	        document.set(editorInput.getContents());
	        
		} else {
		    document = super.createDocument(element);
		}
		    
		/*
		 * Add the document partitioner.
		 */
		if (document != null) {
			// Create a document partitioner. The default FastPartitioner provided 
            // by the framework is used. Two parameters are passed:
			// 1.- The scanner used to partition the document
			// 2.- An array of Strings defining the legal regions into which 
			// the document will be partitioned.
			// The portion of document which does not fit within the passed
			// regions is treated as a DEFAULT category of region.
			IDocumentPartitioner partitioner = new FastPartitioner(
					new T24BasicPartitionScanner(),
					new String[] {
						T24BasicPartitionScanner.BASIC_COMMENT,
						T24BasicPartitionScanner.BASIC_LITERAL});
            // Associate partitioner with document
	        document.setDocumentPartitioner(partitioner);
			partitioner.connect(document);
		}
		return document;
	}
    
    @Override
    protected IDocument createEmptyDocument() {
        return super.createEmptyDocument();
    }
    
    public T24BasicEditor getEditor() {
        return editor;
    }
    
    /**
     * @param element - typically an instance implementation of IEditorInput (e.g. FileEditorInput)
     */
    @Override
    public IAnnotationModel getAnnotationModel(Object element) {
        return super.getAnnotationModel(element);
    }
    
    @Override
    public boolean isModifiable(Object element) {
        return true;
    }
}