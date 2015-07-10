/*
 * 
 */
package com.zealcore.se.core.services.internal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;

/**
 * The Class MementoService.
 */
public class MementoService implements IMementoService {

    /**
     * {@inheritDoc}
     */
    public IMemento createReadRoot(final IPath path)
            throws FileNotFoundException {
        try {
            return XMLMemento.createReadRoot(new FileReader(path.toFile()));
        } catch (final WorkbenchException e) {
            throw new IllegalArgumentException("The path " + path
                    + " cannot be read");
        }
    }

    /**
     * {@inheritDoc}
     */
    public IMemento2 createWriteRoot(final String root, final IPath path) {
        return new MementoWrapper(root, path);
    }

    /**
     * The Class MementoWrapper.
     */
    private static final class MementoWrapper implements IMemento2 {

        /** The path. */
        private final IPath path;

        /** The forward. */
        private final XMLMemento forward;

        /**
         * The Constructor.
         * 
         * @param root
         *            the root
         * @param path
         *            the path
         */
        private MementoWrapper(final String root, final IPath path) {

            this.path = path;
            forward = XMLMemento.createWriteRoot(root);
        }

        public IMemento createChild(final String type, final String id) {
            return forward.createChild(type, id);
        }

        public IMemento createChild(final String type) {
            return forward.createChild(type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return forward.equals(obj);
        }

        public IMemento getChild(final String type) {
            return forward.getChild(type);
        }

        public IMemento[] getChildren(final String type) {
            return forward.getChildren(type);
        }

        /**
         * {@inheritDoc}
         */
        public Float getFloat(final String key) {
            return forward.getFloat(key);
        }

        /**
         * {@inheritDoc}
         */
        public String getID() {
            return forward.getID();
        }

        /**
         * {@inheritDoc}
         */
        public Integer getInteger(final String key) {
            return forward.getInteger(key);
        }

        /**
         * {@inheritDoc}
         */
        public String getString(final String key) {
            return forward.getString(key);
        }

        /**
         * {@inheritDoc}
         */
        public String getTextData() {
            return forward.getTextData();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return forward.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        public void putFloat(final String key, final float f) {
            forward.putFloat(key, f);
        }

        /**
         * {@inheritDoc}
         */
        public void putInteger(final String key, final int n) {
            forward.putInteger(key, n);
        }

        /**
         * {@inheritDoc}
         */
        public void putMemento(final IMemento memento) {
            forward.putMemento(memento);
        }

        /**
         * {@inheritDoc}
         */
        public void putString(final String key, final String value) {
            forward.putString(key, value);
        }

        /**
         * {@inheritDoc}
         */
        public void putTextData(final String data) {
            forward.putTextData(data);
        }

        /**
         * {@inheritDoc}
         */
        public void save(final Writer writer) throws IOException {
            forward.save(writer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return forward.toString();
        }

        /**
         * {@inheritDoc}
         */
        public void save() throws IOException {
            this.save(new FileWriter(path.toFile()));
        }

        public String[] getAttributeKeys() {
            return forward.getAttributeKeys();
        }

        public Boolean getBoolean(final String key) {
            return forward.getBoolean(key);
        }

        public String getType() {
            return forward.getType();
        }

        public void putBoolean(final String key, final boolean value) {
            forward.putBoolean(key, value);
        }

		@Override
		public IMemento[] getChildren() {
			// TODO Auto-generated method stub
			return null;
		}
    }
}
