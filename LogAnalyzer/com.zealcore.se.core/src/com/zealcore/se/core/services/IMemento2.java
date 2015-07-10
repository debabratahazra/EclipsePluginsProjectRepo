package com.zealcore.se.core.services;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.ui.IMemento;

public interface IMemento2 extends IMemento {

    /**
     * Saves this memento's document current values to the specified writer.
     * 
     * @param writer
     *                the writer used to save the memento's document
     * @throws IOException
     *                 if there is a problem serializing the document to the
     *                 stream.
     */
    void save(Writer writer) throws IOException;

    /**
     * Saves this memento's document current values to the specified writer.
     * 
     * @throws IOException
     *                 if there is a problem serializing the document to the
     *                 stream.
     */
    void save() throws IOException;
}
