/*
 * 
 */
package com.zealcore.se.ui.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Utility class for providing icons and text to a single column TableViewer
 * 
 * @author stch
 * 
 */
public class IconListLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    private final Map<Object, Image> images = new HashMap<Object, Image>();

    /**
     * {@inheritDoc}
     */
    public final Image getColumnImage(final Object element,
            final int columnIndex) {
        if (images.containsKey(element)) {
            return images.get(element);
        }
        final Image image = getImage(element);
        images.put(element, image);
        return image;
    }

    /**
     * {@inheritDoc}
     */
    public final String getColumnText(final Object element,
            final int columnIndex) {
        return getText(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {}
}
